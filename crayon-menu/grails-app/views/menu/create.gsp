<g:set var="entityName" value="${message(code: 'menu.label', default: 'Menu')}" />

<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
        <span aria-hidden="true">x</span>
    </button>
    <h4 class="modal-title" id="myLargeModalLabel"><g:message code="default.new.label" args="[entityName]" /></h4>
</div>
<div class="modal-body">
    <div id="create-menu" class="content scaffold-create" role="main">
        <g:hasErrors bean="${this.menu}">
            <ul class="errors" role="alert">
                <g:eachError bean="${this.menu}" var="error">
                    <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                </g:eachError>
            </ul>
        </g:hasErrors>
        <g:form action="save">
            <f:all bean="menu"/>
            <crayoncms:adminSaveButtons cancelController="menuGroup" action="create" modal="true" />
        </g:form>
    </div>
</div>