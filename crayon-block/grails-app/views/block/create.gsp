<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="crayoncms_main" />
        <g:set var="entityName" value="${message(code: 'block.label', default: 'Block')}" />
        <title><g:message code="default.new.label" args="[entityName]" /></title>
        <crayoncms:title prefix="${message(code:'default.new.label', args: [entityName])}" />
    </head>
    <body>
        <content tag="header">
            <g:message code="default.new.label" args="[entityName]" /></h1>
        </content>
        <content tag="right-menu">

        </content>
        <div id="create-block" class="content scaffold-create" role="main">
            <g:hasErrors bean="${this.block}">
            <ul class="errors" role="alert">
                <g:eachError bean="${this.block}" var="error">
                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                </g:eachError>
            </ul>
            </g:hasErrors>
            <g:form action="save">
                <f:field bean="block" property="name" />
                <f:field bean="block" property="slug" />
                <f:field bean="block" property="type" />
                <div class="form-group">
                    <label class="control-label" for="content">Content</label>
                    <textarea class="form-control" name="content" id="${(block.type == com.crayoncms.block.enums.BlockType.CODE) ? 'code' : 'content'}">${block.content}</textarea>
                </div>
                <g:submitButton name="create" class="btn btn-primary" value="${message(code: 'default.button.save.label', default: 'Save')}" />
                <g:link class="btn btn-default" action="index"><g:message code="default.button.cancel.label" /></g:link>
            </g:form>
        </div>
    </body>
</html>
