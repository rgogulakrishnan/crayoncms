package com.crayoncms.theme

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.plugin.springsecurity.annotation.Secured

@Transactional(readOnly = true)
class LayoutController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    @Secured("ROLE_CRAYONCMS_LAYOUT_VIEW")
    def index(Integer max) {
        params.max = Math.min(max ?: 20, 100)
        respond Layout.list(params), model:[layoutCount: Layout.count()]
    }

    @Secured("ROLE_CRAYONCMS_LAYOUT_MANAGE")
    def create() {
        respond new Layout(params)
    }

    @Secured("ROLE_CRAYONCMS_LAYOUT_MANAGE")
    @Transactional
    def save(Layout layout) {
        if (layout == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (layout.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond layout.errors, view:'create'
            return
        }

        layout.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'layout.label', default: 'Layout'), layout.name])
                flash.outcome = "success"
                if(params.create == message(code: 'default.button.save.label')) {
                    redirect action: "edit", id: layout.id
                } else {
                    redirect action: "index"
                }
            }
            '*' { respond layout, [status: CREATED] }
        }
    }

    @Secured("ROLE_CRAYONCMS_LAYOUT_MANAGE")
    def edit(Layout layout) {
        respond layout
    }

    @Secured("ROLE_CRAYONCMS_LAYOUT_MANAGE")
    @Transactional
    def update(Layout layout) {
        if (layout == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (layout.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond layout.errors, view:'edit'
            return
        }

        layout.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'layout.label', default: 'Layout'), layout.name])
                flash.outcome = "success"
                if(params.edit == message(code: 'default.button.update.label')) {
                    redirect action: "edit", id: layout.id
                } else {
                    redirect action: "index"
                }
            }
            '*'{ respond layout, [status: OK] }
        }
    }

    @Secured("ROLE_CRAYONCMS_LAYOUT_MANAGE")
    @Transactional
    def delete(Layout layout) {

        if (layout == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        layout.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'layout.label', default: 'Layout'), layout.name])
                flash.outcome = "success"
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'layout.label', default: 'Layout'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
