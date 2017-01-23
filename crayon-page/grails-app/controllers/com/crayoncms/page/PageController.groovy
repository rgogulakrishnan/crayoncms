package com.crayoncms.page

import com.crayoncms.page.enums.PageStatus
import com.crayoncms.user.RoleGroup
import com.crayoncms.user.UserRoleGroup
import com.crayoncms.theme.Layout
import org.grails.plugins.BinaryGrailsPlugin

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.plugin.springsecurity.annotation.Secured

@Transactional(readOnly = true)
class PageController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
    static defaultAction = "browse"
    def pluginManager
    def pageService

	@Secured(["ROLE_CRAYONCMS_PAGE_CREATE", "ROLE_CRAYONCMS_PAGE_EDIT", "ROLE_CRAYONCMS_PAGE_DELETE"])
    def browse(Integer max) {
        params.max = Math.min(max ?: 20, 100)
        respond Page.list(params), model:[pageCount: Page.count()]
    }

	@Secured("permitAll")
    def show(String slug) {
		Page page = Page.findBySlug(slug ?: "home")
        if(page) {

            // Page should have published status
            if(page.status != PageStatus.PUBLISHED) {
                notFound()
                return
            }

            // First see if the current user has access to the requested page
            if(! pageService.hasAccess()) {
                notFound() // TODO: see if this can be shown with forbidden.
                return
            }

            // Now lets merge bind content with layout and prepare html
            render view: "/browse", model: [
                    content: pageService.mergeContentWithLayout(page), title: page.name, bodyCss: page.bodyCss,
                    description: page.description, keywords: page.keywords
            ]
        } else {
            notFound()
            return
        }
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
                    redirect action: "edit", id: page.id
                } else {
                    redirect action: "browse"
                }
            }
            '*' { respond page, [status: CREATED, view: "browse"] }
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
                    redirect action: "edit", id: page.id
                } else {
                    redirect action: "browse"
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
                redirect action:"browse", method:"GET"
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
