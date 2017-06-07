<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="crayoncms_main" />
    <g:set var="entityName" value="${message(code: 'user.label', default: 'Profile')}" />
    <crayoncms:title prefix="${message(code:'my.profile.label', args: [entityName])}" />
</head>
<body>

<content tag="header">
    <g:message code="my.profile.label" />
</content>
<content tag="right-menu">

</content>

<div id="edit-user" class="content scaffold-list" role="main">
    <div class="row">
        <div class="col-md-3">
            <center>
                <g:if test="${!user.profilePicture}">
                    <div class="profileDefault img-rounded"></div>
                </g:if>
                <g:else>
                    <crayoncms:profilePicture username="${user.username}" width="200px" height="200px" />
                </g:else>
                <div class="margin-40"></div>
                <button class="btn btn-primary" data-toggle="modal" data-target="#updateProfilePic">
                    <i class="fa fa-user"></i> <g:message code="default.change.label" />
                </button>
            </center>
        </div>
        <div class="col-md-9">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <g:message code="personal.info.label" />
                </div>
                <div class="panel-body">
                    <g:form resource="${this.user}" method="PUT">
                        <g:hiddenField name="version" value="${this.user?.version}" />
                        <div class="row">
                            <div class="col-md-6">
                                <f:field bean="user" property="username"/>
                            </div>
                            <div class="col-md-6">
                                <f:field bean="user" property="email"/>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6">
                                <f:field bean="user" property="firstName"/>
                            </div>
                            <div class="col-md-6">
                                <f:field bean="user" property="lastName"/>
                            </div>
                        </div>
                        <g:submitButton name="editMyProfile" class="btn btn-primary" value="${message(code: 'default.button.update.label')}" />
                    </g:form>
                </div>
            </div>
            <div class="panel panel-default">
                <div class="panel-heading">
                    <g:message code="change.pwd.label" />
                </div>
                <div class="panel-body">
                    <g:form action="changepwd" method="PUT">
                        <div class="row">
                            <div class="col-md-4">
                                <div class="form-group ${invalid ? 'has-error has-feedback' : required && value ? 'has-success has-feedback' : ''}">
                                    <label class="control-label" for="curPassword">Current Password</label>
                                    <g:passwordField name="curPassword" class="form-control" value="" />
                                </div>
                            </div>

                            <div class="col-md-4">
                                <div class="form-group ${invalid ? 'has-error has-feedback' : required && value ? 'has-success has-feedback' : ''}">
                                    <label class="control-label" for="newPassword">New Password</label>
                                    <g:passwordField name="newPassword" class="form-control" value="" />
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="form-group ${invalid ? 'has-error has-feedback' : required && value ? 'has-success has-feedback' : ''}">
                                    <label class="control-label" for="confirmPassword">Confirm Password</label>
                                    <g:passwordField name="confirmPassword" class="form-control" value="" />
                                </div>
                            </div>

                        </div>
                        <g:submitButton name="edit" class="btn btn-primary" value="${message(code: 'default.button.update.label')}" />
                    </g:form>
                </div>
            </div>
        </div>
    </div>

</div>

<div class="modal fade" id="updateProfilePic" tabindex="-1" role="dialog">
    <div class="modal-dialog modal-md" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">x</span>
                </button>
                <h4 class="modal-title" id="myLargeModalLabel"><g:message code="default.update.profilepic.label" /></h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-md-12">
                        <input type="file" name="profileFile" id="profileFile" />
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <div class="profilePicWrapper margin-20"></div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <input type="submit" id="uploadProfilePic" class="btn btn-primary" value="Crop and Update" />
                    </div>
                </div>
                <g:hiddenField name="id" value="${this.user?.id}" />
                <g:hiddenField name="version" value="${this.user?.version}" />
            </div>
        </div>
    </div>
</div>

</body>
</html>