<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="crayoncms_main" />
        <g:set var="entityName" value="${message(code: 'menu.label', default: 'Menu')}" />
        <crayoncms:title prefix="${message(code:'default.new.label', args: [entityName])}" />
    </head>
    <body>
    	<content tag="header">
        	<g:message code="default.new.label" args="[entityName]" />
        </content>
          
        <content class="right-menu">
                
        </content>
        
        <div id="create-menu" class="content scaffold-create" role="main">
            
            <g:hasErrors bean="${this.menu}">
            <ul class="errors" role="alert">
                <g:eachError bean="${this.menu}" var="error">
                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                </g:eachError>
            </ul>
            </g:hasErrors>
            <g:form action="save">
                <f:all bean="menu"/>
                <crayoncms:adminSaveButtons action="create" />
            </g:form>
        </div>
    </body>
</html>
