<!doctype html>
<html lang="en" class="no-js">
<head>
    <g:render template="/templates/admin_meta" />
    <g:layoutHead/>
</head>
<body>

    <div class="container">

        <div class="row">
            <div class="col-lg-4 col-md-4 col-lg-offset-4 col-md-offset-4 ">
                <div class="margin-50" />
                <center><g:img src="crayoncms/logo_admin_small.svg" class="img-responsive admin_logo_small"/></center>
                <br/>

                <div class="panel panel-default">
                    <div class="panel-body">
                        <h3><g:pageProperty name="page.section-title" /></h3>
                        <br />
                        <g:layoutBody/>

                    </div>
                </div>
            </div>
        </div>

    </div>

    <div id="spinner" class="spinner" style="display:none;">
        <g:message code="spinner.alt" default="Loading&hellip;"/>
    </div>

    <div id="loading"><img src='/assets/crayoncms/ajax-loader.gif' width="32" height="32" /></div>

    <asset:javascript src="crayoncms/crayoncms.js"/>

</body>
</html>
