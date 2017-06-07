package com.crayoncms.admin

import grails.plugin.springsecurity.annotation.Secured

@Secured("permitAll")
class DashboardController {

    static namespace = "admin"
    def springSecurityService

    def index() {

        if (springSecurityService.isLoggedIn()) {
            render view: "index"
        } else {
            redirect uri: "/admin"
        }
    }
}
