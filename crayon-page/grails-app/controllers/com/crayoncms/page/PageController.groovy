package com.crayoncms.page

import com.crayoncms.page.enums.PageStatus

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.plugin.springsecurity.annotation.Secured

@Transactional(readOnly = true)
class PageController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def pluginManager
    def pageService

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
            render view: "/index", model: [
                    content: pageService.mergeContentWithLayout(page), title: page.name, bodyCss: page.bodyCss,
                    description: page.description, keywords: page.keywords
            ]
        } else {
            notFound()
            return
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
