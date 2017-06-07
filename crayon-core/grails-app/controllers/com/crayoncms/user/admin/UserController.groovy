package com.crayoncms.user.admin

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.plugin.springsecurity.annotation.Secured

import com.crayoncms.user.User
import com.crayoncms.user.ProfilePictureCommand

@Transactional(readOnly = true)
class UserController {

	static namespace = "admin"
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE",
                             changepwd: "PUT", changeProfilePic: "POST"]

    def springSecurityService
    def userService

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

        user.password = userService.generateDefaultPassword()
        user.registeredOn = new Date()

        if (user.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond user.errors, view:'create'
            return
        }

        user.save flush:true

        request.withFormat {
            form multipartForm {
                flash.outcome = "success"
                flash.message = message(code: 'default.created.message', args: [message(code: 'user.label', default: 'User'), user.username])
                if(params.create == message(code: 'default.button.save.label')) {
                    redirect action: "edit", id: user.id, method: "GET"
                } else {
                    flash.message = message(code: 'default.updated.message', args: [message(code: 'user.label', default: 'User'), user.username])
                    redirect action: "index", method: "GET"
                }
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

                flash.outcome = "success"
                if(params.editMyProfile) {
                    flash.message = message(code: 'success.field.changed', args: [message(code: 'profile.label')])
                    redirect action: "myprofile"
                } else if(params.edit == message(code: 'default.button.update.label')) {
                    flash.message = message(code: 'default.updated.message', args: [message(code: 'user.label', default: 'User'), user.username])
                    redirect action: "edit", id: user.id, method: "GET"
                } else {
                    flash.message = message(code: 'default.updated.message', args: [message(code: 'user.label', default: 'User'), user.username])
                    redirect action: "index", method: "GET"
                }
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

    @Secured("ROLE_CRAYONCMS_USER_UPDATE_PROFILE")
    def myprofile() {
        def user = springSecurityService.getCurrentUser()
        render view: "myprofile", model: [user: user]
    }

    @Secured("ROLE_CRAYONCMS_USER_UPDATE_PROFILE")
    def changeProfilePic(ProfilePictureCommand cmd) {

        if (cmd == null) {
            notFound()
            return
        }

        if (cmd.hasErrors()) {
            respond(cmd.errors, model: [user: User.get(cmd.id)], view: 'myprofile')
            redirect action: "myprofile"
            return
        }

        User user = userService.uploadProfilePicture(cmd)

        if (user == null) {
            notFound()
            return
        }

        if (user.hasErrors()) {
            respond(user.errors, model: [user: user], view: 'myprofile')
            return
        }

        render(contentType: "application/json") {

        }


        /*request.withFormat {
            form multipartForm {
                flash.message = message(code: 'success.field.changed', args: [message(code: 'profilePicture.label')])
                flash.outcome = "success"
                redirect action: "myprofile"
            }
            '*'{ respond user, [status: OK] }
        }*/
    }

    @Secured("ROLE_CRAYONCMS_USER_UPDATE_PROFILE")
    @Transactional
    def changepwd() {

        if(params.curPassword && params.newPassword) {

            if(params.newPassword == params.confirmPassword) {

                User user = userService.updatePassword(params.curPassword, params.newPassword)
                if(user) {
                    flash.message = message(code: "success.field.changed", args: [message(code: "password.label")])
                    flash.outcome = "success"
                    redirect action: "myprofile"
                    return
                } else {
                    flash.message = message(code: 'error.currentPassword.wrong')
                }

            } else {
                flash.message = message(code: 'error.newPassword.doesntmatch')
            }
        } else {
            flash.message = message(code: 'error.currAndNew.required')
        }

        transactionStatus.setRollbackOnly()
        flash.outcome = "danger"
        redirect action: "myprofile"
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
