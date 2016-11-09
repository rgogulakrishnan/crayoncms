<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="crayoncms_main" />
        <g:set var="entityName" value="${message(code: 'menu.label', default: 'Menu Group')}" />
        <crayoncms:title prefix="${message(code:'default.lists.label', args: [entityName])}" />
    </head>
    <body>
        <content tag="header">
        	<g:message code="default.lists.label" args="[entityName]" /></h1>
        </content>
        <content tag="right-menu">
            <sec:link class="btn btn-primary" action="create" data-toggle="modal" data-target=".modal" expression="hasRole('ROLE_MANAGE_MENUGROUPS')">
       		    <i class="fa fa-bars"></i>
       		    <span class="hidden-xs"><g:message code="default.new.label" args="[entityName]"/></span>
       		</sec:link>
            <sec:link class="btn btn-primary" controller="menu" action="create" expression="hasRole('ROLE_MANAGE_MENU')">
               <i class="fa fa-file-text"></i>
               <span class="hidden-xs"><g:message code="default.new.label" args="['Menu']" /></span>
            </sec:link>
        </content>
        
        <div id="list-menuGroup" class="content scaffold-list" role="main">
			
			<g:if test="${menuGroupList}">
				<g:each in="${menuGroupList}" var="menuGroup">
					<div class="panel panel-default">
  						<div class="panel-heading">
  							<div class="row">
  								<span class="col-xs-9 col-md-8"><h3 class="panel-title">${menuGroup.name}</h3></span>
  								<span class="col-xs-3 col-md-4 text-right">
  								    <sec:ifAllGranted roles="ROLE_MANAGE_MENUGROUPS">
                                        <g:form resource="${menuGroup}" method="DELETE">
                                            <g:link class="" action="edit" resource="${menuGroup}" data-toggle="modal" data-target=".modal">
                                                <i class="fa fa-pencil"></i>
                                            </g:link>
                                            <input class="fa fa-trash-o" type="submit" value="&#xf014" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
                                        </g:form>
            						</sec:ifAllGranted>
            					</span>
            				</div>
  						</div>
  						<div class="panel-body">
  							<ol class="menu ui-sortable">
  								<crayoncms:adminMenu groupName="${menuGroup.name}" />
    						</ol>
  						</div>
					</div>
				</g:each>
			</g:if>
			
        </div>
    </body>
</html>