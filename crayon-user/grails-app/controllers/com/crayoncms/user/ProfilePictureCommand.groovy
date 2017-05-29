package com.crayoncms.user

import grails.validation.Validateable
import org.springframework.web.multipart.MultipartFile

class ProfilePictureCommand implements Validateable {

    MultipartFile profilePicture
    Long id
    Integer version

    static constraints = {
        id nullable: false
        version nullable: false
        profilePicture validator: { val, obj ->
            if(val == null) {
                return false
            }
            if(val.empty) {
                return false
            }

            ['jpeg', 'jpg', 'png'].any { extension ->
                val.originalFilename?.toLowerCase().endsWith(extension)
            }
        }
    }
}
