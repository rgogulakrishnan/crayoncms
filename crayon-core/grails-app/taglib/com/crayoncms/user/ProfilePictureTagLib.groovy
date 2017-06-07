package com.crayoncms.user

class ProfilePictureTagLib {

    static namespace = "crayoncms"

    static defaultEncodeAs = [taglib:'html']
    static encodeAsForTags = [profilePicture: [taglib:'none']]

    def springSecurityService

    def profilePicture = { attrs ->

        User user = (attrs.username && attrs.username != "")? User.findByUsername(attrs?.username) : springSecurityService.currentUser

        if(user?.profilePicture && user?.profilePictureContentType) {
            out << "<img class='img-circle' src='data:${user.profilePictureContentType};base64,"
            out << user?.profilePicture?.encodeBase64()
            out << "' width='" + attrs.width + "' height='" + attrs.height + "' />"
        } else {
            out << asset.image(class: 'img-circle', src:'crayoncms/profile_default.png', width: '23px', height: '23px')
        }

        out

    }
}
