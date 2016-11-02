<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="crayoncms_main" />
        <g:set var="entityName" value="${message(code: 'block.label', default: 'Block')}" />
        <crayoncms:title prefix="${message(code:'default.lists.label', args: [entityName])}" />
    </head>
    <body>
        <content tag="header">
            <h1><g:message code="default.lists.label" args="[entityName]" /></h1>
        </content>
        <content tag="right-menu">
            <sec:link class="btn btn-primary" action="create" expression="hasRole('ROLE_CRAYONCMS_BLOCK_CREATE')">
                <i class="fa fa-file-text"></i>
                <span class="hidden-xs"><g:message code="default.new.label" args="[entityName]" /></span>
            </sec:link>
        </content>
    
        <div id="list-block" class="content scaffold-list" role="main">
            
            <crayoncms:table collection="${blockList}" properties="['name', 'slug', 'dateCreated', 'lastUpdated']"
                deleteRole="ROLE_CRAYONCMS_BLOCK_DELETE"/>

			<g:if test="${blockCount > 20 }">
            	<div class="pagination">
                	<g:paginate total="${blockCount ?: 0}" />
            	</div>
            </g:if>
        </div>
    </body>
</html>