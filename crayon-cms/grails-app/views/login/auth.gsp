<!DOCTYPE HTML>
<html>
<crayoncms:block name="meta" />
<body class="right-sidebar">
<div id="page-wrapper">

    <crayoncms:block name="header" />

    <!-- Main -->
    <div class="wrapper style1">

        <div class="container">
            <div class="row 200%">
                <div class="8u 12u(mobile)" id="content">
                    <section>
                        <header>
                            <h2>Login</h2>
                        </header>

                        <g:if test='${flash.message}'>
                            <div class="login_message">${flash.message}</div>
                        </g:if>

                        <form id="loginForm" autocomplete="off" method="post" action="${postUrl ?: '/login/authenticate'}">
                            <div class="form-group">
                                <label for="username" class="sr-only">Username</label>
                                <input type="text" required class="form-control" name="${usernameParameter ?: 'username'}" id="username" placeholder="username">
                            </div>
                            <div class="form-group">
                                <label for="password" class="sr-only">Password</label>
                                <input type="password" required class="form-control" name="${passwordParameter ?: 'password'}" id="password" placeholder="password">
                            </div>
                            <div class="form-group login-group-checkbox" id="remember_me_holder">
                                <input type="checkbox" name="${rememberMeParameter ?: 'remember-me'}" id="remember_me" <g:if test='${hasCookie}'>checked="checked"</g:if>/>
                                <span>remember</span>
                            </div>
                            <button type="submit" id="submit" class="login-button"><i class="fa fa-chevron-right"></i></button>
                            <a href="#">forgot password?</a>
                        </form>
                    </section>
                </div>
            </div>
        </div>

    </div>

    <crayoncms:block name="footer" />
</div>

<crayoncms:block name="scripts" />
</body>
</html>