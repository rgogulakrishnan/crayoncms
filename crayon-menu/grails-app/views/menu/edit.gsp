<g:set var="entityName" value="${message(code: 'menu.label', default: 'Menu')}" />

<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
        <span aria-hidden="true">x</span>
    </button>
    <h4 class="modal-title" id="myLargeModalLabel"><g:message code="default.edit.label" args="[entityName]" /></h4>
</div>
<div class="modal-body">
    <div id="edit-menu" class="content scaffold-edit" role="main">
        <g:hasErrors bean="${this.menu}">
            <ul class="errors" role="alert">
                <g:eachError bean="${this.menu}" var="error">
                    <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                </g:eachError>
            </ul>
        </g:hasErrors>
        <g:form resource="${this.menu}" method="PUT">
            <g:hiddenField name="version" value="${this.menu?.version}" />
            <f:field bean="menu" property="name" />
            <f:field bean="menu" property="menuType" />
            <f:field bean="menu" property="menuTypeValue" />
            <f:field bean="menu" property="menuGroup" />
            <f:field bean="menu" property="openInNewTab" />
            <f:field bean="menu" property="access" />
            <f:field bean="menu" property="cssClass" />
            <crayoncms:adminSaveButtons cancelController="menuGroup" action="edit" ifAllGranted="ROLE_MANAGE_MENU" modal="true" />
        </g:form>
    </div>
</div>
