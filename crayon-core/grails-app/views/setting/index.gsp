<%@ page import="com.crayoncms.core.enums.SettingType" %>
<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="crayoncms_main" />
        <g:set var="entityName" value="${message(code: 'Setting.label', default: 'Setting')}" />
        <crayoncms:title prefix="${message(code:'default.lists.label', args: [entityName])}" />
    </head>
    <body>
        <content tag="header">
        	<g:message code="default.lists.label" args="[entityName]" />
        </content>
        <content tag="right-menu">

        </content>
        <div id="list-plugin" class="content scaffold-list" role="main">
            <g:form class="" name="settingsForm" action="save" method="PUT">
                <g:each var="setting" in="${settingList}">
                    <div class="form-group">
                        <g:if test="${setting.type != SettingType.ADMIN_FIELD}">
                            <label for="${setting.name}">${setting.name}</label>
                        </g:if>
                        <g:if test="${setting.type == SettingType.TEXT}">
                            <g:textField class="form-control" name="${setting.name}" value="${setting.value}" />
                        </g:if>
                        <g:if test="${setting.type == SettingType.TEXT_AREA}">
                            <g:textArea class="form-control" name="${setting.name}" value="${setting.value}" rows="5" />
                        </g:if>
                        <g:if test="${setting.type == SettingType.RADIO}">
                            <g:radioGroup class="hh" name="${setting.name}"
                                labels="${Eval.me(setting.options)}" values="${Eval.me(setting.options)}" value="${setting.value}" >
                                <span>${it.label} ${it.radio}</span>
                            </g:radioGroup>
                        </g:if>
                        <g:if test="${setting.type == SettingType.TIME_ZONE_SELECT}">
                            <g:timeZoneSelect class="form-control" name="${setting.name}" value="${java.util.TimeZone.getTimeZone(setting.value)}" />
                        </g:if>
                    </div>
                </g:each>
                <sec:ifAllGranted roles="ROLE_CRAYONCMS_MANAGE_SETTINGS">
                    <input class="btn btn-primary" type="submit" value="${message(code: 'default.button.update.label', default: 'Update')}" />
                </sec:ifAllGranted>
            </g:form>
        </div>
    </body>
</html>