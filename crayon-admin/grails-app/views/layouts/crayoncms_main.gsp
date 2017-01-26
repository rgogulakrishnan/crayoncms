<!doctype html>
<html lang="en" class="no-js">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <title>
        <g:layoutTitle default="CrayonCMS"/>
    </title>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
	<asset:link rel="shortcut icon" href="crayoncms/favicon.ico" type="image/x-icon"/>
	<link href="https://fonts.googleapis.com/css?family=Raleway|Open+Sans" rel="stylesheet">
    <asset:stylesheet src="crayoncms/crayoncms.css"/>

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
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Content <span class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <li><a href="/page">Pages</a></li>
                            <li><a href="/block">Blocks</a></li>
                        </ul>
                    </li>
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Structure <span class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <li><a href="/layout">Layouts</a></li>
                            <li><a href="/menuGroup">Menu Groups</a></li>
                        </ul>
                    </li>
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Users <span class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <li><a href="/roleGroup">Groups & Roles</a></li>
                            <li><a href="/user">Users</a></li>
                        </ul>
                    </li>
                        <li><a href="/plugin">Plugins</a></li>
                        <li><a href="/setting">Settings</a></li>
                    </sec:ifLoggedIn>
                    
                </ul>

                 <ul class="nav navbar-nav navbar-right">
                    <sec:ifLoggedIn>
                        <li class="right"><a href="/" target="_blank">View Site</a></li>
                        <li class="right dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><sec:username /> <span class="caret"></span></a>
                            <ul class="dropdown-menu">
                                <li><g:link controller="user" action="myprofile" >My Profile</g:link></li>
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
                <div class="col-xs-12">&copy 2017 CrayonCMS - version ${applicationContext.getBean('pluginManager')?.getGrailsPlugin("crayon-admin")?.version} <span class="hidden-xs"> / Made with <i class="fa fa-heart"></i> by Gogula Rajaprabhu</span></div>
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
