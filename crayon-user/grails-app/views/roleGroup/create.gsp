<g:set var="entityName" value="${message(code: 'roleGroup.label', default: 'Role Group')}" />

<div class="modal-header"> 
	<button type="button" class="close" data-dismiss="modal" aria-label="Close">
		<span aria-hidden="true">x</span>
	</button> 
	<h4 class="modal-title" id="myLargeModalLabel"><g:message code="default.new.label" args="[entityName]" /></h4> 
</div> 
<div class="modal-body">	
	<g:form action="save">
		<f:field bean="roleGroup" property="name" />
		<crayoncms:adminSaveButtons action="create" modal="true" />
	</g:form>
</div> 
