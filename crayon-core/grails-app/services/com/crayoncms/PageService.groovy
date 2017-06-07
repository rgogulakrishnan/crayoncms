package com.crayoncms

import com.crayoncms.user.RoleGroup
import com.crayoncms.user.UserRoleGroup
import grails.transaction.Transactional

@Transactional
class PageService {

    def springSecurityService
    def groovyPagesTemplateEngine

    def hasAccess() {

        if(! springSecurityService.isLoggedIn()) {
            return true
        }

        def user = springSecurityService.currentUser
        if (user.authorities.contains(RoleGroup.findByName("Administrator"))
                || UserRoleGroup.exists((long)user?.id, (long)page?.roleGroup?.id)) {
            return true
        }

        return false
    }

    def mergeContentWithLayout(Page page) {
        if(page) {
            def binding = [:] << [content: page.content.encodeAsRaw()]
            return groovyPagesTemplateEngine
                    .createTemplate(new ByteArrayInputStream(page?.layout?.code?.getBytes("UTF-8")))
                    .make(binding)
        }

        return ""
    }
}
