package com.crayoncms.admin

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.plugin.springsecurity.annotation.Secured

import com.crayoncms.Setting

@Transactional(readOnly = true)
@Secured("ROLE_CRAYONCMS_MANAGE_SETTINGS")
class SettingController {

	static namespace = "admin"
	
    def index() {
        respond Setting.list(params)
    }

    @Transactional
    def save() {

        params.each { key, value ->
            if(key != "_method") {
                def setting = Setting.findByName(key)
                if(setting) {
                    setting.value = value
                    setting.save flush:true
                }
            }
        }

        // Set timezone.
        // TODO: See if this is set only when that field is changed.
        TimeZone.setDefault(TimeZone.getTimeZone(Setting.findBySlug("time-zone").value))

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'setting.label', default: 'Setting')])
                flash.outcome = "success"
                redirect action: "index"
            }
            '*' { respond page, [status: org.springframework.http.HttpStatus.CREATED, view: "index"] }
        }
    }
}
