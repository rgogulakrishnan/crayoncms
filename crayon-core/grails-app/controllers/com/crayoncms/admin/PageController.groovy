package com.crayoncms.admin

import com.crayoncms.enums.PageStatus
import org.grails.plugins.BinaryGrailsPlugin

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.plugin.springsecurity.annotation.Secured

import com.crayoncms.Page

@Transactional(readOnly = true)
class PageController {

    static namespace = "admin"
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def pluginManager
    def pageService

	@Secured(["ROLE_CRAYONCMS_PAGE_CREATE", "ROLE_CRAYONCMS_PAGE_EDIT", "ROLE_CRAYONCMS_PAGE_DELETE"])
    def index(Integer max) {
        params.max = Math.min(max ?: 20, 100)
        respond Page.list(params), model:[pageCount: Page.count()]
    }

	@Secured("ROLE_CRAYONCMS_PAGE_CREATE")
    def create() {
        def page = new Page(params)

        def activeTheme = pluginManager.getGrailsPlugin("helios-theme")
        if(activeTheme instanceof BinaryGrailsPlugin) {
            def files = new File(activeTheme.getProjectDirectory(), "grails-app/views").list()
            page.layout = files as List
        }

        respond page
    }

	@Secured("ROLE_CRAYONCMS_PAGE_CREATE")
    @Transactional
    def save(Page page) {
        if (page == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (page.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond page.errors, view:'create'
            return
        }

        page.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'page.label', default: 'Page'), page.name])
				flash.outcome = "success"
                if(params.create == message(code: 'default.button.save.label')) {
                    redirect action: "edit", id: page.id, method: "GET"
                } else {
                    redirect action: "index", method: "GET"
                }
            }
            '*' { respond page, [status: CREATED, view: "index"] }
        }
    }

	@Secured("ROLE_CRAYONCMS_PAGE_EDIT")
    def edit(Page page) {
        respond page
    }

	@Secured("ROLE_CRAYONCMS_PAGE_EDIT")
    @Transactional
    def update(Page page) {
        if (page == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (page.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond page.errors, view:'edit'
            return
        }

        page.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'page.label', default: 'Page'), page.name])
				flash.outcome = "success"
                if(params.edit == message(code: 'default.button.update.label')) {
                    redirect action: "edit", id: page.id, method: "GET"
                } else {
                    redirect action: "index", method: "GET"
                }
            }
            '*'{ respond page, [status: OK] }
        }
    }

	@Secured("ROLE_CRAYONCMS_PAGE_DELETE")
    @Transactional
    def delete(Page page) {

        if (page == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        page.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'page.label', default: 'Page'), page.name])
                flash.outcome = "success"
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'page.label', default: 'Page'), params.name])
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
