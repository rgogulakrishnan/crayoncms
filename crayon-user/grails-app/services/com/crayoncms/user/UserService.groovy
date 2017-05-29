package com.crayoncms.user

import grails.transaction.Transactional
import java.awt.image.BufferedImage
import javax.imageio.ImageIO
import org.imgscalr.Scalr
import groovy.util.logging.Log4j

@Log4j
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

        String contentType = cmd.profilePicture.contentType

        log.info "Profile picture actual size:" + cmd.profilePicture.bytes.length
        BufferedImage srcImage = ImageIO.read(cmd.profilePicture.inputStream); // Load image
        BufferedImage scaledImage = Scalr.resize(srcImage, Scalr.Method.ULTRA_QUALITY, Scalr.Mode.FIT_TO_WIDTH, 200, 200, Scalr.OP_ANTIALIAS); // Scale image

        def saveStream = new ByteArrayOutputStream()
        ImageIO.write(scaledImage, "jpg", saveStream)
        byte[] bytes1 = saveStream.toByteArray()
        log.info "Profile picture after resize:" + bytes1.length

        User user = User.get(cmd.id)

        if(!user) {
            return null
        }

        user.version = cmd.version + 1
        user.profilePicture = bytes1
        user.profilePictureContentType = contentType
        user.save flush: true
        user
    }
}
