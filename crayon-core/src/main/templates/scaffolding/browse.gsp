<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="crayoncms_main" />
        <g:set var="entityName" value="\${message(code: '${propertyName}.label', default: '${className}')}" />
        <crayoncms:title prefix="\${message(code:'default.lists.label', args: [entityName])}" />
    </head>
    <body>
        <content tag="header">
        	<g:message code="default.lists.label" args="[entityName]" /></h1>
        </content>
        <content tag="right-menu">
        	<g:link class="btn btn-info" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link>
        </content>
        
        <div id="list-${propertyName}" class="content scaffold-list" role="main">

            <f:table collection="\${${propertyName}List}" />

			<g:if test="\${${propertyName}Count > 20 }">
            	<div class="pagination">
                	<g:paginate total="\${${propertyName}Count ?: 0}" />
            	</div>
            </g:if>
        </div>
    </body>
</html>