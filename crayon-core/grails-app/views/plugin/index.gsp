<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="crayoncms_main" />
        <g:set var="entityName" value="${message(code: 'plugin.label', default: 'Plugin')}" />
        <crayoncms:title prefix="${message(code:'default.lists.label', args: [entityName])}" />
    </head>
    <body>
        <content tag="header">
        	<g:message code="default.lists.label" args="[entityName]" /></h1>
        </content>
        <content tag="right-menu">
                <!-- <g:link class="btn btn-info" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link> -->
        </content>
        <div id="list-plugin" class="content scaffold-list" role="main">

            <div class="panel panel-default">
            	<div class="panel-heading"><h3 class="panel-title">Core Plugins</h3></div>
				<div class="panel-body">
		  			<table class="table table-hover">
		            	<thead>
							<th width="20%">Name</th>
		            		<th width="45%">Description</th>
		            		<th width="25%">Author</th>
		            		<th width="10%">Version</th>
		            	</thead>
		            	<tbody>
		            		<g:each var="plugin" in="${crayonPlugins}">
		                	<tr>
			                	<td>${plugin?.title}</td>
			                	<td>${plugin?.description}</td>
			                	<td>${plugin?.author}</td>
			                	<td>${plugin?.version}</td>
			                </tr>
		            		</g:each>
		            	</tbody>
		            </table>
	          	</div>
            </div>
            
            
            <div class="panel panel-default">
            	<div class="panel-heading"><h3 class="panel-title">Grails Plugins</h3></div>
 				<div class="panel-body">
           
					<table class="table table-hover">
		                <thead>
		                    <th width="20%">Name</th>
		                    <th width="45%">Description</th>
		                    <th width="25%">Author</th>
		                    <th width="10%">Version</th>
		                </thead>
		                <tbody>
		                    <g:each var="plugin" in="${grailsPlugins}">
		                    <tr>
		                        <td>${plugin?.title}</td>
		                        <td>${plugin?.description}</td>
		                        <td>${plugin?.author}</td>
		                        <td>${plugin?.version}</td>
		                    </tr>
		                    </g:each>
		                </tbody>
		            </table>
				</div>
		</div>
    </body>
</html>