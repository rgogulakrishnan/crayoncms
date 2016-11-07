package com.crayoncms.user

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.plugin.springsecurity.annotation.Secured

@Transactional(readOnly = true)
class UserController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    @Secured("ROLE_CRAYONCMS_USER_VIEW")
    def index(Integer max) {
        params.max = Math.min(max ?: 20, 100)
        respond User.list(params), model:[userCount: User.count()]
    }

    @Secured("ROLE_CRAYONCMS_USER_VIEW")
    def show(User user) {
        respond user
    }

    @Secured("ROLE_CRAYONCMS_USER_UPDATE")
    def create() {
        respond new User(params)
    }

    @Secured("ROLE_CRAYONCMS_USER_UPDATE")
    @Transactional
    def save(User user) {
        if (user == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (user.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond user.errors, view:'create'
            return
        }

        user.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'user.label', default: 'User'), user.username])
                flash.outcome = "success"
                redirect action: "index"
            }
            '*' { respond user, [status: CREATED] }
        }
    }

    @Secured("ROLE_CRAYONCMS_USER_UPDATE")
    def edit(User user) {
        respond user
    }

    @Secured("ROLE_CRAYONCMS_USER_UPDATE")
    @Transactional
    def update(User user) {
        if (user == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (user.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond user.errors, view:'edit'
            return
        }

        user.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'user.label', default: 'User'), user.username])
                flash.outcome = "success"
                redirect action: "index"
            }
            '*'{ respond user, [status: OK] }
        }
    }

    @Secured("ROLE_CRAYONCMS_USER_UPDATE")
    @Transactional
    def delete(User user) {

        if (user == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        user.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'user.label', default: 'User'), user.username])
                flash.outcome = "success"
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
