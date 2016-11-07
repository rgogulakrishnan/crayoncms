package com.crayoncms.user

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.plugin.springsecurity.annotation.Secured

@Secured("ROLE_CRAYONCMS_USER_MANAGE_ROLEGROUPS")
@Transactional(readOnly = true)
class RoleGroupController {

    static allowedMethods = [save: "POST", addRole: "POST", update: "PUT", delete: "DELETE", removeRole: "POST"]

    def index(Integer max) {
        redirect RoleGroup.findByName("Administrator")
    }

    def show(RoleGroup roleGroup) {
        // TODO: comeback later for enhancing
        def allRoleGroups = RoleGroup.list()
        def allRoles = Role.list().with { it.groupBy({ authority -> authority.plugin})}
        def authoritiesGrouped =  roleGroup.authorities.groupBy ({ authority -> authority.plugin })
        respond roleGroup, model: [allRoleGroups: allRoleGroups, allRoles: allRoles.sort { it.key }, authoritiesGrouped: authoritiesGrouped]
    }

    def create() {
        respond new RoleGroup(params)
    }

    @Transactional
    def save(RoleGroup roleGroup) {
        if (roleGroup == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (roleGroup.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond roleGroup.errors, view:'/roleGroupRole/index'
            return
        }

        roleGroup.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'roleGroup.label', default: 'RoleGroup'), roleGroup.name])
                flash.outcome = "success"
                redirect roleGroup
            }
            '*' { respond roleGroup, [status: CREATED] }
        }
    }

    def edit(RoleGroup roleGroup) {
        respond roleGroup
    }

    @Transactional
    def update(RoleGroup roleGroup) {
        if (roleGroup == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (roleGroup.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond roleGroup.errors, view:'/roleGroupRole/index'
            return
        }

        roleGroup.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'roleGroup.label', default: 'RoleGroup'), roleGroup.name])
                flash.outcome = "success"
                redirect roleGroup
            }
            '*'{ respond roleGroup, [status: OK] }
        }
    }

    @Transactional
    def delete(RoleGroup roleGroup) {

        if (roleGroup == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        roleGroup.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'roleGroup.label', default: 'RoleGroup'), roleGroup.name])
                flash.outcome = "success"
                redirect RoleGroup.findByName("Administrator")
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    @Transactional
    def addRole() {

        if(!params.roleGroup || !params.role ) {
            transactionStatus.setRollbackOnly()
            render contentType: "text/json", status: BAD_REQUEST, text:'{"retMess": "Invalid role group and role. Please try again" }'
            return
        }

        def roleGroupRole = new RoleGroupRole(roleGroup: RoleGroup.findByName(params.roleGroup), role: Role.findByAuthorityName(params.role))

        if(roleGroupRole.hasErrors()) {
            transactionStatus.setRollbackOnly()
            render contentType: "text/json", status: BAD_REQUEST, text:'{"retMess": ' + roleGroupRole.errors + '}'
            return
        }


        roleGroupRole.save flush:true

        render contentType: "text/json", text:'{"retMess": "Role added successfully" }'

    }

    @Transactional
    def removeRole() { // TODO: come back later for making this to delete

        if(!params.roleGroup || !params.role ) {
            transactionStatus.setRollbackOnly()
            render contentType: "text/json", status: BAD_REQUEST, text:'{"retMess": "Invalid role group and role. Please try again" }'
            return
        }

        def roleGroupRole = RoleGroupRole.findWhere(roleGroup: RoleGroup.findByName(params.roleGroup), role: Role.findByAuthorityName(params.role))

        if(roleGroupRole.hasErrors()) {
            transactionStatus.setRollbackOnly()
            render contentType: "text/json", status: BAD_REQUEST, text:'{"retMess": ' + roleGroupRole.errors + '}'
            return
        }

        roleGroupRole.delete flush:true

        render contentType: "text/json", text:'{"retMess": "Role removed successfully" }'

    }


    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'roleGroup.label', default: 'RoleGroup'), params.id])
                redirect acontroller: "roleGroupRole", ction: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
