package com.crayoncms.user

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import com.bertramlabs.plugins.selfie.Attachment

@EqualsAndHashCode(includes='username')
@ToString(includes='username,fullName,email', includeNames=true, includePackage=false)
class User implements Serializable {

	private static final long serialVersionUID = 1

	transient springSecurityService

	String username
	String password
	String email
	String firstName
	String lastName
	String fullName
	Attachment profilePicture
	boolean enabled = true
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired
	Date registeredOn
	Date lastLogin
	Date dateCreated
	Date lastUpdated

	Set<RoleGroup> getAuthorities() {
		UserRoleGroup.findAllByUser(this)*.roleGroup
	}

	def beforeInsert() {
		encodePassword()
	}

	def beforeUpdate() {
		if (isDirty('password')) {
			encodePassword()
		}
	}

	protected void encodePassword() {
		password = springSecurityService?.passwordEncoder ? springSecurityService.encodePassword(password) : password
	}

	static transients = ['springSecurityService', 'fullName']

	static constraints = {
		username blank: false, unique: true
		firstName nullable: true, maxSize: 50
		lastName nullable: true, maxSize: 50
		email blank: false, unique: true, email: true
		password blank: true, nullable: true, password: true, display: false
		enabled blank: false
		accountExpired display: false
		passwordExpired display: false
		accountLocked display: false
		profilePicture contentType: ['image/jpeg','image/png'], fileSize:1024*1024, blank: false, nullable: true
		registeredOn blank: false, nullable: true
		lastLogin blank: false, nullable: true
	}

	static mapping = {
		table 'crayon_user'
		password column: '`password`'
	}

	static embedded = ['profilePicture']

	static attachmentOptions = [
		profilePicture: [
			styles: [
				thumb: [width: 128, height: 128, mode: 'fit']
				//medium: [width: 250, height: 250, mode: 'scale']
			]
		]
	]
	
	String getFullName() {
		firstName + " " + lastName
	}
}
