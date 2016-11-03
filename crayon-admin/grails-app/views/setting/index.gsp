<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="crayoncms_main" />
        <g:set var="entityName" value="${message(code: 'Setting.label', default: 'Setting')}" />
        <crayoncms:title prefix="${message(code:'default.lists.label', args: [entityName])}" />
    </head>
    <body>
        <content tag="header">
        	<g:message code="default.lists.label" args="[entityName]" /></h1>
        </content>
        <content tag="right-menu">

        </content>
        <div id="list-plugin" class="content scaffold-list" role="main">
            <g:form name="settingsForm" action="save" method="PUT">
            <g:each var="setting" in="${settingList}">
                <div class="form-group">
                    <label for="${setting.name}">${setting.name}</label>
                    <g:if test="${setting.type == 'text'}">
                        <g:textField class="form-control" name="${setting.name}" value="${setting.value}" />
                    </g:if>
                    <g:if test="${setting.type == 'textarea'}">
                        <g:textArea class="form-control" name="${setting.name}" value="${setting.value}" rows="5" />
                    </g:if>
                    <g:if test="${setting.type == 'radio'}">
                        <g:radioGroup class="form-control" name="${setting.name}"
                            labels="${Eval.me(setting.options)}" values="${Eval.me(setting.options)}" value="${setting.value}" >
                            <div>${it.label} ${it.radio}</div>
                        </g:radioGroup>
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