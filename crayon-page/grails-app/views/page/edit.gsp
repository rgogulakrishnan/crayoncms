<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="crayoncms_main" />
        <g:set var="entityName" value="${message(code: 'page.label', default: 'Page')}" />
        <crayoncms:title prefix="${message(code:'default.edit.label', args: [entityName])}" />
    </head>
    <body>
    	<content tag="header">
            <sec:ifAllGranted roles="ROLE_CRAYONCMS_PAGE_EDIT">
                <g:message code="default.edit.label" args="[entityName]" />
            </sec:ifAllGranted>
            <sec:ifNotGranted roles="ROLE_CRAYONCMS_PAGE_EDIT">
                <g:message code="default.view.label" args="[entityName]" />
            </sec:ifNotGranted>
       	</content>

        <div id="edit-page" class="content scaffold-edit" role="main">
            <g:hasErrors bean="${this.page}">
            <ul class="errors" role="alert">
                <g:eachError bean="${this.page}" var="error">
                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                </g:eachError>
            </ul>
            </g:hasErrors>
            <g:form resource="${this.page}" method="PUT">
                <g:hiddenField name="version" value="${this.page?.version}" />

               	<div class="row">
            		<div class="col-md-9">
            			<f:field bean="page" property="name" />
						<f:field bean="page" property="slug" />
						<f:field bean="page" property="status" />
						<f:field bean="page" property="content" />
            		</div>
            		<div class="col-md-3">
            			<f:field bean="page" property="description" />
						<f:field bean="page" property="keywords" />
						<f:field bean="page" property="roleGroup" />
						<f:field bean="page" property="layout" />
						<f:field bean="page" property="bodyCss" />
	            	</div>
               </div>

                <sec:ifAllGranted roles="ROLE_CRAYONCMS_PAGE_EDIT">
                    <input class="btn btn-primary" type="submit" value="${message(code: 'default.button.update.label', default: 'Update')}" />
                    <g:link class="btn btn-default" action="index"><g:message code="default.button.cancel.label" /></g:link>
                </sec:ifAllGranted>
            </g:form>
        </div>
    </body>
</html>
