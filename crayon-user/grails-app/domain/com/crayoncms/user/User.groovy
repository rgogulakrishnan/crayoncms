package com.crayoncms.user

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

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
	byte[] profilePicture
	String profilePictureContentType
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
		password blank: true, nullable: true, password: true, display: false, minSize: 8
		enabled [:]
		accountExpired display: false
		passwordExpired display: false
		accountLocked display: false
		profilePicture nullable: true
		profilePictureContentType nullable: true
		registeredOn nullable: true
		lastLogin nullable: true
	}

	static mapping = {
		table 'crayon_user'
		password column: '`password`'
		profilePicture column: 'profile_picture_bytes', sqlType: 'longblob'
	}
	
	String getFullName() {
		firstName + " " + lastName
	}
}
