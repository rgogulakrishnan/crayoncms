<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="crayoncms_main" />
        <g:set var="entityName" value="${message(code: 'layout.label', default: 'Layout')}" />
        <crayoncms:title prefix="${message(code:'default.lists.label', args: [entityName])}" />
    </head>
    <body>
        <content tag="header">
        	<g:message code="default.lists.label" args="[entityName]" /></h1>
        </content>
        <content tag="right-menu">
            <sec:link class="btn btn-primary" action="create" expression="hasRole('ROLE_CRAYONCMS_LAYOUT_MANAGE')">
        	    <i class="fa fa-file-text"></i>
        	    <span class="hidden-xs"><g:message code="default.new.label" args="[entityName]" /></span>
        	</sec:link>
        </content>
        
        <div id="list-layout" class="content scaffold-list" role="main">

            <crayoncms:table collection="${layoutList}" properties="['name', 'slug', 'dateCreated', 'lastUpdated']"
                deleteRole="ROLE_CRAYONCMS_LAYOUT_MANAGE" />

			<g:if test="${layoutCount > 20 }">
            	<div class="pagination">
                	<g:paginate total="${layoutCount ?: 0}" />
            	</div>
            </g:if>
        </div>
    </body>
</html>