<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="crayoncms_main" />
        <g:set var="entityName" value="${message(code: 'roleGroup.label', default: 'Role Group')}" />
        <crayoncms:title prefix="${message(code:'default.lists.label', args: [entityName])}" />
    </head>
    <body>
        <content tag="header">
        	<g:message code="default.lists.label" args="[entityName]" /></h1>
        </content>
        <content tag="right-menu">
        	<g:link class="btn btn-primary" action="create" data-toggle="modal" data-target=".modal">
        		<i class="fa fa-file-text"></i>
        		<span class="hidden-xs"><g:message code="default.new.label" args="[entityName]" /></span>
        	</g:link>
        </content>
        
        <div id="list-roleGroup" class="content scaffold-list" role="main">

			<g:if test="${allRoleGroups}">
				<div class="row">

                    <div class="col-md-3">
                        <ul class="">
                            <g:each in="${allRoleGroups}" var="rolGrp">
                                <g:if test="${rolGrp.name != 'Anonymous'}">
                                    <li class="list <g:if test="${rolGrp.name == roleGroup.name}">active</g:if>" >
                                    <div class="row">
                                        <div class="col-md-8">
                                            <g:link action="show" resource="${rolGrp}">${rolGrp.name}</g:link>
                                        </div>
                                        <g:if test="${rolGrp.name != 'Administrator'}">
                                            <div class="col-md-1">
                                                <g:link action="edit" resource="${rolGrp}" data-toggle="modal" data-target=".modal"><i class="fa fa-pencil"></i></g:link>
                                            </div>
                                            <div class="col-md-1 text-right">
                                                <g:form resource="${rolGrp}" method="DELETE">
                                                    <input type="submit" class="fa fa-trash-o" value="&#xf014;" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
                                                </g:form>
                                            </div>
                                         </g:if></div>
                                    </li>
                                </g:if>
                            </g:each>
                        </ul>
                        <br />
                    </div>

                    <div class="col-md-9 col-sm-9">
                        <g:each in="${allRoles}" var="plugin">
                            <div class="panel panel-default">
                                <div class="panel-heading">
                                    <h3 class="panel-title">${plugin.key}</h3>
                                </div>
                                <div class="panel-body">
                                    <ul>
                                    <g:each in="${plugin.value}" var="role">
                                        <li class="list">
                                            <div class="row">
                                                <div class="col-xs-10 col-md-11">${role}</div>
                                                <div class="col-xs-2 col-md-1 text-right">
                                                    <g:if test="${ authoritiesGrouped.find { it.key == plugin.key}?.value?.contains(role) }">
                                                        <g:checkBox id="${role.authority}_removeRole" name="${role.authority}_removeRole"
                                                        value="${true}"
                                                        onclick="${ remoteFunction(action: 'removeRole', method: 'POST', params: [roleGroup: roleGroup, role: role]) }"/>
                                                    </g:if>
                                                    <g:else>
                                                        <g:checkBox id="${role.authority}_addRole" name="${role.authority}_addRole"
                                                        onclick="${ remoteFunction(action: 'addRole', method: 'POST', params: [roleGroup: roleGroup, role: role]) }"/>
                                                    </g:else>
                                                </div>
                                            </div>
                                        </li>
                                    </g:each>
                                    </ul>
                                </div>
                             </div>
                        </g:each>
				    </div>

				</div>
			</g:if>
		</div>
    </body>
</html>