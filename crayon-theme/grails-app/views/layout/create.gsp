<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="crayoncms_main" />
        <g:set var="entityName" value="${message(code: 'layout.label', default: 'Layout')}" />
        <crayoncms:title prefix="${message(code:'default.new.label', args: [entityName])}" />
    </head>
    <body>
        <content tag="header">
            <g:message code="default.new.label" args="[entityName]" />
        </content>
        <content class="right-menu">
            
        </content>
    
        <div id="create-layout" class="content scaffold-create" role="main">
            <g:hasErrors bean="${this.layout}">
            <ul class="errors" role="alert">
                <g:eachError bean="${this.layout}" var="error">
                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                </g:eachError>
            </ul>
            </g:hasErrors>
            <g:form action="save">
                <f:field bean="layout" property="name" />
                <f:field bean="layout" property="slug" />
                <div class="form-group">
                    <label class="control-label" for="code">Code</label>
                    <textarea class="form-control" name="code" id="code"></textarea>
                </div>
                <g:submitButton name="create" class="btn btn-primary" value="${message(code: 'default.button.save.label', default: 'Save')}" />
                <g:link class="btn btn-default" action="index"><g:message code="default.button.cancel.label" /></g:link>
            </g:form>
        </div>
    </body>
</html>
