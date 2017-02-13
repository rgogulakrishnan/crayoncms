<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="crayoncms_main" />
        <g:set var="entityName" value="\${message(code: '${propertyName}.label', default: '${className}')}" />
        <crayoncms:title prefix="\${message(code:'default.new.label', args: [entityName])}" />
    </head>
    <body>
        <content tag="header">
            <g:message code="default.new.label" args="[entityName]" />
        </content>
        <content class="right-menu">
            
        </content>
    
        <div id="create-${propertyName}" class="content scaffold-create" role="main">
            <g:hasErrors bean="\${this.${propertyName}}">
            <ul class="errors" role="alert">
                <g:eachError bean="\${this.${propertyName}}" var="error">
                <li <g:if test="\${error in org.springframework.validation.FieldError}">data-field-id="\${error.field}"</g:if>><g:message error="\${error}"/></li>
                </g:eachError>
            </ul>
            </g:hasErrors>
            <g:form action="save">
                <f:all bean="${propertyName}"/>
                <crayoncms:adminSaveButtons action="create" ifAllGranted="" />
            </g:form>
        </div>
    </body>
</html>
