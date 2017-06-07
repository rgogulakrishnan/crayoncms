<!DOCTYPE HTML>
<html>
<head>
    <meta name="layout" content="crayoncms_installer" />
    <title>CrayonCMS &raquo; Login</title>
</head>
<body>

<content tag="section-title"><center>Login</center></content>

<g:if test="${flash.message}">
    <div id="notifyMess" class="alert alert-danger alert-dismissible" role="status">
        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
            <span aria-hidden="true">&times;</span>
        </button>
        ${flash.message}
    </div>
</g:if>

<form id="loginForm" autocomplete="off" method="post" action="${postUrl ?: '/login/authenticate'}">
    <div class="form-group">
        <label for="username">Username</label>
        <input type="text" required class="form-control" name="${usernameParameter ?: 'username'}" id="username">
    </div>
    <div class="form-group">
        <label for="password">Password</label>
        <input type="password" required class="form-control" name="${passwordParameter ?: 'password'}" id="password">
    </div>
    <div class="form-group login-group-checkbox" id="remember_me_holder">
        <input type="checkbox" name="${rememberMeParameter ?: 'remember-me'}" id="remember_me" <g:if test='${hasCookie}'>checked="checked"</g:if>/>
        <span>remember</span>
    </div>
    <input type="hidden" name="spring-security-redirect" value="/admin/dashboard" />
    <button type="submit" id="submit" class="btn btn-primary">Login</button>
    <a href="#">forgot password?</a>
</form>

</body>
</html>