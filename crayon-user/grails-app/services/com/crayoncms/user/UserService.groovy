package com.crayoncms.user

import grails.transaction.Transactional

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
}
