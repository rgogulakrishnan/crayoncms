<g:set var="entityName" value="${message(code: 'roleGroup.label', default: 'Role Group')}" />

<div class="modal-header"> 
	<button type="button" class="close" data-dismiss="modal" aria-label="Close">
		<span aria-hidden="true">x</span>
	</button> 
	<h4 class="modal-title" id="myLargeModalLabel"><g:message code="default.edit.label" args="[entityName]" /></h4> 
</div> 
<div class="modal-body">	
	<g:form resource="${this.roleGroup}"  method="PUT">
		<f:field bean="roleGroup" property="name" />
		<crayoncms:adminSaveButtons action="edit" ifAllGranted="ROLE_CRAYONCMS_USER_MANAGE_ROLEGROUPS" modal="true" />
	</g:form>
</div> 

