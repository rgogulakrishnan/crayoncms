<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="crayoncms_main" />
    <g:set var="entityName" value="${message(code: 'dashboard.label', default: 'Dashboard')}" />
    <crayoncms:title prefix="${message(code:'default.lists.label', args: [entityName])}" />
</head>
<body>
    <content tag="header">
        <g:message code="default.lists.label" args="[entityName]" /></h1>
    </content>
    <content tag="right-menu">
        <!-- <g:link class="btn btn-info" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link> -->
    </content>

</body>
</html>