<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="crayoncms_main" />
        <g:set var="entityName" value="${message(code: 'page.label', default: 'Page')}" />
        <crayoncms:title prefix="${message(code:'default.new.label', args: [entityName])}" />
    </head>
    <body>
        <content tag="header">
            <g:message code="default.new.label" args="[entityName]" /></h1>
        </content>
        <content tag="right-menu">

        </content>
        <div id="create-page" class="content scaffold-create" role="main">
            <g:hasErrors bean="${this.page}">
            <ul class="errors" role="alert">
                <g:eachError bean="${this.page}" var="error">
                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                </g:eachError>
            </ul>
            </g:hasErrors>
            <g:form action="save">
            	<div class="row">
            		<div class="col-md-9">
            			<f:field bean="page" property="name" />
						<f:field bean="page" property="slug" />
						<f:field bean="page" property="status" />
						<div class="form-group">
							<label class="control-label" for="content">Content</label>
							<textarea class="form-control" name="content" id="content"></textarea>
						</div>
            		</div>
            		<div class="col-md-3">
            			<f:field bean="page" property="description" />
						<f:field bean="page" property="keywords" />
						<f:field bean="page" property="roleGroup" />
						<f:field bean="page" property="layout" />
						<f:field bean="page" property="bodyCss" />
	            	</div>
               </div>
                <crayoncms:adminSaveButtons action="create" />
            </g:form>
        </div>
    </body>
</html>
