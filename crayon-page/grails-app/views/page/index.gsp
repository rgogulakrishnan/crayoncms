<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="crayoncms_main" />
        <g:set var="entityName" value="${message(code: 'page.label', default: 'Page')}" />
        <crayoncms:title prefix="${message(code:'default.lists.label', args: [entityName])}" />
    </head>
    <body>
       	<content tag="header">
        	<g:message code="default.lists.label" args="[entityName]" />
        </content>
        <content tag="right-menu">
        	<sec:link class="btn btn-primary" action="create" expression="hasRole('ROLE_CRAYONCMS_PAGE_CREATE')">
        	    <i class="fa fa-file-text"></i>
                <span class="hidden-xs"><g:message code="default.new.label" args="[entityName]" /></span>
        	</sec:link>
        </content>
        <div id="list-page" class="content scaffold-list" role="main">
            
            <crayoncms:table collection="${pageList}" properties="['name', 'slug', 'status', 'roleGroup']"
                deleteRole="ROLE_CRAYONCMS_PAGE_DELETE" />

			<g:if test="${pageCount > 20 }">
            	<div class="pagination">
                	<g:paginate total="${pageCount ?: 0}" />
            	</div>
            </g:if>
        </div>
    </body>
</html>