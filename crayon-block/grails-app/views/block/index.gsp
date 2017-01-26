<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="crayoncms_main" />
        <g:set var="entityName" value="${message(code: 'block.label', default: 'Block')}" />
        <crayoncms:title prefix="${message(code:'default.lists.label', args: [entityName])}" />
    </head>
    <body>
        <content tag="header">
            <g:message code="default.lists.label" args="[entityName]" />
        </content>
        <content tag="right-menu">
            <sec:ifAllGranted roles="ROLE_CRAYONCMS_BLOCK_CREATE">
                <a class="btn btn-primary" href="#" data-toggle="modal" data-target="#blockCreateModal">
                    <i class="fa fa-file-text"></i>
                    <span class="hidden-xs"><g:message code="default.new.label" args="[entityName]" /></span>
                </a>
            </sec:ifAllGranted>
        </content>
    
        <div id="list-block" class="content scaffold-list" role="main">
            
            <crayoncms:table collection="${blockList}" properties="['name', 'slug', 'type', 'dateCreated', 'lastUpdated']"
                deleteRole="ROLE_CRAYONCMS_BLOCK_DELETE"/>

			<g:if test="${blockCount > 20 }">
            	<div class="pagination">
                	<g:paginate total="${blockCount ?: 0}" />
            	</div>
            </g:if>
        </div>

        <div class="modal fade" id="blockCreateModal" tabindex="-1" role="dialog">
            <div class="modal-dialog modal-lg" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                    	<button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    		<span aria-hidden="true">x</span>
                    	</button>
                    	<h4 class="modal-title" id="myLargeModalLabel"><g:message code="default.new.label" args="['Block']" /></h4>
                    </div>
                    <div class="modal-body">
                        <g:set var="block" value="${new com.crayoncms.block.Block()}" />
                        <g:form action="create" method="GET">
                            <f:field bean="block" property="type" />
                            <g:submitButton controller="block" name="create" class="btn btn-primary" value="${message(code: 'default.button.continue.label', default: 'Continue')}" />
                            <a class="btn btn-default" data-dismiss="modal" aria-label="Cancel">
                                <g:message code="default.button.cancel.label" />
                            </a>
                        </g:form>
                    </div>
                </div>
            </div>
        </div>

    </body>
</html>

