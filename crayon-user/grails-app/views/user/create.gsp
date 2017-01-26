<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="crayoncms_main" />
        <g:set var="entityName" value="${message(code: 'user.label', default: 'User')}" />
        <crayoncms:title prefix="${message(code:'default.create.label', args: [entityName])}" />
    </head>
    <body>
    <content tag="header">
        <g:message code="default.new.label" args="[entityName]" /></h1>
    </content>
    <content tag="right-menu">

    </content>

    <div id="create-user" class="content scaffold-create" role="main">
            <g:hasErrors bean="${this.user}">
            <ul class="errors" role="alert">
                <g:eachError bean="${this.user}" var="error">
                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                </g:eachError>
            </ul>
            </g:hasErrors>
            <g:uploadForm action="save">
                <f:field bean="user" property="username" />
                <f:field bean="user" property="email" />
                <f:field bean="user" property="firstName" />
                <f:field bean="user" property="lastName" />
                <crayoncms:adminSaveButtons action="create" />
            </g:uploadForm>
        </div>
    </body>
</html>
