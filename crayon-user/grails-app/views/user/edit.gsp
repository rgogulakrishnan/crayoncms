<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="crayoncms_main" />
        <g:set var="entityName" value="${message(code: 'user.label', default: 'User')}" />
        <crayoncms:title prefix="${message(code:'default.edit.label', args: [entityName])}" />
    </head>
    <body>
        <content tag="header">
            <sec:ifAllGranted roles="ROLE_CRAYONCMS_USER_UPDATE">
                <g:message code="default.edit.label" args="[entityName]" />
            </sec:ifAllGranted>
            <sec:ifNotGranted roles="ROLE_CRAYONCMS_USER_UPDATE">
                <g:message code="default.view.label" args="[entityName]" />
            </sec:ifNotGranted>
        </content>

        <div id="edit-user" class="content scaffold-edit" role="main">
            <g:hasErrors bean="${this.user}">
            <ul class="errors" role="alert">
                <g:eachError bean="${this.user}" var="error">
                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                </g:eachError>
            </ul>
            </g:hasErrors>
            <g:form resource="${this.user}" method="PUT">
                <g:hiddenField name="version" value="${this.user?.version}" />
                <f:all bean="user"/>
                <crayoncms:adminSaveButtons action="edit" ifAllGranted="ROLE_CRAYONCMS_USER_UPDATE" />
            </g:form>
        </div>
    </body>
</html>
