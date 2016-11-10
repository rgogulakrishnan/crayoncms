<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="crayoncms_main" />
        <g:set var="entityName" value="${message(code: 'block.label', default: 'Block')}" />
        <crayoncms:title prefix="${message(code:'default.edit.label', args: [entityName])}" />
    </head>
    <body>
    	<content tag="header">
            <sec:ifAllGranted roles="ROLE_CRAYONCMS_BLOCK_EDIT">
                <g:message code="default.edit.label" args="[entityName]" />
            </sec:ifAllGranted>
            <sec:ifNotGranted roles="ROLE_CRAYONCMS_BLOCK_EDIT">
                <g:message code="default.view.label" args="[entityName]" />
            </sec:ifNotGranted>
       	</content>

        <div id="edit-block" class="content scaffold-edit" role="main">
            <g:hasErrors bean="${this.block}">
            <ul class="errors" role="alert">
                <g:eachError bean="${this.block}" var="error">
                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                </g:eachError>
            </ul>
            </g:hasErrors>
            <g:form resource="${this.block}" method="PUT">
                <g:hiddenField name="version" value="${this.block?.version}" />
                <f:field bean="block" property="name" />
                <f:field bean="block" property="slug" />
                <f:field bean="block" property="type" />
                <div class="form-group">
                    <label class="control-label" for="content">Content</label>
                    <textarea class="form-control" name="content" id="${(block.type == com.crayoncms.block.enums.BlockType.CODE) ? 'code' : 'content'}">${block.content}</textarea>
                </div>
                <crayoncms:adminSaveButtons action="edit" ifAllGranted="ROLE_CRAYONCMS_BLOCK_EDIT" />
            </g:form>
        </div>
    </body>
</html>
