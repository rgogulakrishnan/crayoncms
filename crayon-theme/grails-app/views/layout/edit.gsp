<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="crayoncms_main" />
        <g:set var="entityName" value="${message(code: 'layout.label', default: 'Layout')}" />
        <crayoncms:title prefix="${message(code:'default.edit.label', args: [entityName])}" />
    </head>
    <body>
    	
        <content tag="header">
            <sec:ifAllGranted roles="ROLE_CRAYONCMS_LAYOUT_MANAGE">
                <g:message code="default.edit.label" args="[entityName]" />
            </sec:ifAllGranted>
            <sec:ifNotGranted roles="ROLE_CRAYONCMS_LAYOUT_MANAGE">
                <g:message code="default.view.label" args="[entityName]" />
            </sec:ifNotGranted>
        </content>
    
        <div id="edit-layout" class="content scaffold-edit" role="main">
            <g:hasErrors bean="${this.layout}">
            <ul class="errors" role="alert">
                <g:eachError bean="${this.layout}" var="error">
                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                </g:eachError>
            </ul>
            </g:hasErrors>
            <g:form resource="${this.layout}" method="PUT">
                <g:hiddenField name="version" value="${this.layout?.version}" />
                <f:all bean="layout"/>
                <crayoncms:adminSaveButtons action="edit" ifAllGranted="ROLE_CRAYONCMS_LAYOUT_MANAGE" />
            </g:form>
        </div>
    </body>
</html>
