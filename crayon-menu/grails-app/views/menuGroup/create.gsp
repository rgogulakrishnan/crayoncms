<g:set var="entityName" value="${message(code: 'menuGroup.label', default: 'Menu Group')}" />

<div class="modal-header"> 
	<button type="button" class="close" data-dismiss="modal" aria-label="Close">
		<span aria-hidden="true">x</span>
	</button> 
	<h4 class="modal-title" id="myLargeModalLabel"><g:message code="default.new.label" args="[entityName]" /></h4> 
</div> 
<div class="modal-body">	
	<g:form action="save">
		<f:field bean="menuGroup" property="name" />
		<g:submitButton name="create" class="btn btn-primary" value="${message(code: 'default.button.save.label', default: 'Save')}" />
		<g:link class="btn btn-default" data-dismiss="modal" aria-label="Cancel">
			<g:message code="default.button.cancel.label" />
		</g:link>
	</g:form>
</div> 
