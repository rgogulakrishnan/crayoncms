<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="crayoncms_main" />
        <g:set var="entityName" value="${message(code: 'menu.label', default: 'Menu')}" />
        <crayoncms:title prefix="${message(code:'default.edit.label', args: [entityName])}" />
    </head>
    <body>
    	<content tag="header">
        	<sec:ifAllGranted roles="ROLE_MANAGE_MENU">
                <g:message code="default.edit.label" args="[entityName]" />
            </sec:ifAllGranted>
            <sec:ifNotGranted roles="ROLE_MANAGE_MENU">
                <g:message code="default.view.label" args="[entityName]" />
            </sec:ifNotGranted>
        </content>
          
        <content tag="right-menu">
        	<g:form resource="${menu}" method="DELETE">
                <input class="btn btn-primary" type="submit" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
            </g:form>
        </content>
        <div id="edit-menu" class="content scaffold-edit" role="main">
            <g:hasErrors bean="${this.menu}">
            <ul class="errors" role="alert">
                <g:eachError bean="${this.menu}" var="error">
                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                </g:eachError>
            </ul>
            </g:hasErrors>
            <g:form resource="${this.menu}" method="PUT">
                <g:hiddenField name="version" value="${this.menu?.version}" />
                <f:all bean="menu"/>
                <crayoncms:adminSaveButtons cancelController="menuGroup" action="edit" ifAllGranted="ROLE_MANAGE_MENU" />
            </g:form>
        </div>
    </body>
</html>
