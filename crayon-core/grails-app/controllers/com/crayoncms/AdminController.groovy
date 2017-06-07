package com.crayoncms

import grails.plugin.springsecurity.annotation.Secured

@Secured("permitAll")
class AdminController {

    def index() {

        if(isLoggedIn()) {
            redirect uri: "/admin/dashboard"
            return
        }

        render view: "auth"
    }
}
