package com.crayoncms.user

import grails.validation.Validateable

class ProfilePictureCommand implements Validateable {

    String profilePicture
    Long id
    Integer version

    static constraints = {
        id nullable: false
        version nullable: false
        profilePicture nullable: false, blank: false
    }
}
