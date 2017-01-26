package com.crayoncms.menu

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.plugin.springsecurity.annotation.Secured

@Secured("ROLE_MANAGE_MENUGROUPS")
@Transactional(readOnly = true)
class MenuGroupController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
		def menuGroups = MenuGroup.list(params)	
        respond menuGroups, model:[menuGroupCount: MenuGroup.count()]
    }

    def create() {
        respond new MenuGroup(params)
    }

    @Transactional
    def save(MenuGroup menuGroup) {
        if (menuGroup == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (menuGroup.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond menuGroup.errors, view:'create'
            return
        }

        menuGroup.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'menuGroup.label', default: 'Menu Group'), menuGroup.name])
                flash.outcome = "success"
                redirect action: "index"
            }
            '*' { respond menuGroup, [status: CREATED] }
        }
    }

    def edit(MenuGroup menuGroup) {
        respond menuGroup
    }

    @Transactional
    def update(MenuGroup menuGroup) {
        if (menuGroup == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (menuGroup.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond menuGroup.errors, view:'edit'
            return
        }

        menuGroup.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'menuGroup.label', default: 'Menu Group'), menuGroup.name])
                flash.outcome = "success"
                redirect action: "index"
            }
            '*'{ respond menuGroup, [status: OK] }
        }
    }

    @Transactional
    def delete(MenuGroup menuGroup) {

        if (menuGroup == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        menuGroup.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'menuGroup.label', default: 'Menu Group'), menuGroup.name])
                flash.outcome = "success"
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'menuGroup.label', default: 'Menu Group'), params.name])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
