package com.crayoncms.user

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@EqualsAndHashCode(includes='authority')
@ToString(includes='authority, authorityName', includeNames=true, includePackage=false)
class Role implements Serializable {

	private static final long serialVersionUID = 1

	String authority
	String authorityName
	String plugin

	static constraints = {
		authority blank: false, unique: true
		authorityName blank: false
		plugin blank: false
	}

	static mapping = {
		cache true
	}
	
	String toString() {
		authorityName
	}
}
