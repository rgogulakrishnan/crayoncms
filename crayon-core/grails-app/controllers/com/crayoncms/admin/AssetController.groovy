package com.crayoncms.admin

import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import groovy.xml.streamingmarkupsupport.BaseMarkupBuilder.Document
import org.apache.commons.fileupload.*
import org.apache.commons.fileupload.servlet.*
import org.apache.commons.fileupload.disk.*

@Secured("ROLE_ADMIN")
class AssetController {

	static namespace = "admin"

    def upload() {
		
		def file  = request.getFile('file')
		String root = System.getProperty("user.dir") + "/grails-app/assets/uploads/"
		file.transferTo(new File(root + file.originalFilename))
		def uri = [:] << [uri: "/assets/" + file.originalFilename]
		
		withFormat {
			'*' { render uri as JSON}
		}
    }
}
