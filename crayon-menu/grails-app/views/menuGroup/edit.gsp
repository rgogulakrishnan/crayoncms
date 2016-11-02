<g:set var="entityName" value="${message(code: 'menuGroup.label', default: 'Menu Group')}" />

<div class="modal-header"> 
	<button type="button" class="close" data-dismiss="modal" aria-label="Close">
		<span aria-hidden="true">x</span>
	</button> 
	<h4 class="modal-title" id="myLargeModalLabel"><g:message code="default.edit.label" args="[entityName]" /></h4> 
</div> 
<div class="modal-body">	
	<g:form resource="${menuGroup}" method="PUT">
		<g:hiddenField name="version" value="${menuGroup?.version}" />
		<f:field bean="menuGroup" property="name" />
		<input class="btn btn-primary" type="submit" value="${message(code: 'default.button.update.label', default: 'Update')}" />
		<g:link class="btn btn-default" data-dismiss="modal" aria-label="Cancel">
			<g:message code="default.button.cancel.label" />
		</g:link>
	</g:form>
</div>
