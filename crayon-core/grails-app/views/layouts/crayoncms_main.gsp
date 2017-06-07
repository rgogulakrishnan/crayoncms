<!doctype html>
<html lang="en" class="no-js" xmlns:g="http://www.w3.org/1999/html">
<head>
    <g:render template="/templates/admin_meta" />
    <g:layoutHead/>
</head>
<body>

    <div class="navbar navbar-default navbar-static-top" role="navigation">
        <div class="container">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <g:link class="navbar-brand logo" controller="dashboard"><asset:image src="crayoncms/logo.png" class="img-responsive" /></g:link>
            </div>
            <div class="navbar-collapse collapse" aria-expanded="false">
                <ul class="nav navbar-nav">
                    <sec:ifLoggedIn>
                        <li><a href="/admin/dashboard">Dashboard</a></li>
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Content <span class="caret"></span></a>
                            <ul class="dropdown-menu">
                                <li><a href="/admin/page">Pages</a></li>
                                <li><a href="/admin/block">Blocks</a></li>
                            </ul>
                        </li>
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Structure <span class="caret"></span></a>
                            <ul class="dropdown-menu">
                                <li><a href="/admin/layout">Layouts</a></li>
                                <li><a href="/admin/menuGroup">Menu Groups</a></li>
                            </ul>
                        </li>
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Users <span class="caret"></span></a>
                            <ul class="dropdown-menu">
                                <li><a href="/admin/roleGroup">Groups & Roles</a></li>
                                <li><a href="/admin/user">Users</a></li>
                            </ul>
                        </li>
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Extensions <span class="caret"></span></a>
                            <ul class="dropdown-menu">
                                <li><a href="/admin/plugin">Plugins</a></li>
                            </ul>
                        </li>
                        <li><a href="/admin/setting">Settings</a></li>
                    </sec:ifLoggedIn>
                    
                </ul>

                 <ul class="nav navbar-nav navbar-right">
                    <sec:ifLoggedIn>
                        <li class="right"><a href="/" target="_blank">View Site</a></li>
                        <li class="right dropdown">

                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
                                <crayoncms:profilePicture width="23px" height="23px" />
                                <sec:username /> <span class="caret"></span>
                            </a>
                            <ul class="dropdown-menu">
                                <li><g:link controller="user" namespace="admin" action="myprofile" >My Profile</g:link></li>
                                <li><a href="/logout">Logout</a></li>
                            </ul>
                        </li>
                    </sec:ifLoggedIn>
                 </ul>


            </div>
        </div>
    </div>

    <div class="container">
    	<div class="row margin-10-30">
            <div class="col-xs-8 col-md-7">
                <h2><g:pageProperty name="page.header" /></h2>
            </div>
            <div class="col-xs-4 col-md-5 text-right">
            	<g:pageProperty name="page.right-menu" />
            </div>
        </div>
        
        <g:if test="${flash.message}">
            <div id="notifyMess" class="alert alert-${flash.outcome} alert-dismissible" role="status">
            	<button type="button" class="close" data-dismiss="alert" aria-label="Close">
            		<span aria-hidden="true">&times;</span>
            	</button>
            	${flash.message}
            </div>
        </g:if>
        
        <g:layoutBody/>

        <footer class="navbar-bottom" role="contentinfo">
            <hr />
            <div class="row">
                <div class="col-xs-12">&copy 2017 CrayonCMS - version <g:meta name="info.app.version"/> <span class="hidden-xs"> / Made with <i class="fa fa-heart"></i> by Gogula Rajaprabhu</span></div>
                <div class="col-xs-12 hidden-sm hidden-md hidden-lg">Made with <i class="fa fa-heart"></i> by Gogula Rajaprabhu</div>

                <div class="col-md-12">
                    Environment: ${grails.util.Environment.current.name},
                    Grails: <g:meta name="info.app.grailsVersion"/>,
                    Groovy: ${GroovySystem.getVersion()},
                    <span class="hidden-sm hidden-md hidden-lg"><br /></span>
                    JVM: ${System.getProperty('java.version')},
                    Reloading: ${grails.util.Environment.reloadingAgentEnabled}
                </div>
            </div>
        </footer>
    </div>

    <div id="spinner" class="spinner" style="display:none;">
        <g:message code="spinner.alt" default="Loading&hellip;"/>
    </div>
    
    <div class="modal fade" tabindex="-1" role="dialog">
 		<div class="modal-dialog modal-lg" role="document">
   			<div class="modal-content">
     			Loading...
   			</div>
		</div>
	</div>

    <div id="loading"><img src='/assets/crayoncms/ajax-loader.gif' width="32" height="32" /></div>

    <asset:javascript src="crayoncms/crayoncms.js"/>

</body>
</html>
