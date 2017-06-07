<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="crayoncms_main" />
        <g:set var="entityName" value="${message(code: 'user.label', default: 'User')}" />
        <crayoncms:title prefix="${message(code:'default.view.label', args: [entityName])}" />
    </head>
    <body>
         <div class="vertical-align margin-10-30">
            <div class="text-left">
                <h1><g:message code="default.show.label" args="[entityName]" /></h1>
            </div>
            <div class="text-right">

                <g:form resource="${this.user}" method="DELETE">
                <g:link class="btn btn-info" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link>
                    <g:link class="btn btn-warning" action="edit" resource="${this.user}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
                    <input class="btn btn-danger" type="submit" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
                </g:form>
            </div>
        </div>
        <div id="show-user" class="content scaffold-show" role="main">
            <h1></h1>
            <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
            </g:if>
            <f:display bean="user" />

        </div>
    </body>
</html>
