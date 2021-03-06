package com.crayoncms

import grails.transaction.Transactional
import groovy.util.logging.Slf4j

import com.crayoncms.user.User

@Slf4j
@Transactional
class UserService {

    def springSecurityService

    def updatePassword(String currentPassword, String newPassword) {

        User user = springSecurityService.currentUser

        if(springSecurityService.passwordEncoder.isPasswordValid(user.password, currentPassword, null)) {

            user.password = newPassword
            user.save flush: true

            if (springSecurityService.loggedIn &&
                    springSecurityService.principal.username == user.username) {
                springSecurityService.reauthenticate user.username
            }

            return springSecurityService.currentUser
        }

        return null
    }

    def generateDefaultPassword() {

        def pool = ['a'..'z','A'..'Z',0..9,'_'].flatten()
        Random rand = new Random(System.currentTimeMillis())

        def passChars = (0..10).collect { pool[rand.nextInt(pool.size())] }
        def dummyPassword = passChars.join()

        dummyPassword

    }

    @Transactional
    User uploadProfilePicture(ProfilePictureCommand cmd) {

        User user = User.get(cmd.id)

        if(!user) {
            return null
        }

        user.version = cmd.version + 1
        user.profilePicture = cmd.profilePicture.split(",")[1].decodeBase64()
        println user.profilePicture.length
        //user.profilePictureContentType = contentType
        user.profilePictureContentType = "image/jpeg"
        user.save flush: true
        user
    }
}
