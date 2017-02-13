package com.crayoncms.core

import grails.plugin.springsecurity.SpringSecurityUtils

class ButtonsTagLib {

    static namespace = "crayoncms"

    static defaultEncodeAs = [taglib:'html']
    static encodeAsForTags = [adminSaveButtons: [taglib:'none']]

    def adminSaveButtons = { attrs ->
        if(attrs.action == 'edit' && attrs.ifAllGranted) {
            if(SpringSecurityUtils.ifAllGranted(attrs.ifAllGranted)) {
                out << submitButton(name: attrs.action, class: 'btn btn-primary btn-space', value: message(code: 'default.button.update.label'))
                if(!attrs.modal) {
                    out << submitButton(name: attrs.action, class: 'btn btn-primary btn-space', value: message(code: 'default.button.updateAndExit.label'))
                }
            }
        }

        if(attrs.action == 'create') {
            out << submitButton(name: attrs.action, class: 'btn btn-primary btn-space', value: message(code: 'default.button.save.label'))
            if(!attrs.modal) {
                out << submitButton(name: attrs.action, class: 'btn btn-primary btn-space', value: message(code: 'default.button.saveAndExit.label'))
            }
        }

        def cancelController = attrs.cancelController?: controllerName

        if(attrs.modal && attrs.modal == "true") {
            out << '<a href="' + cancelController + '" class="btn btn-default" data-dismiss="modal">' + message(code: 'default.button.cancel.label') + '</a>'
        } else {
            out << '<a href="/' + cancelController + '" class="btn btn-default">' + message(code: 'default.button.cancel.label') + '</a>'
        }

        out
    }
}
