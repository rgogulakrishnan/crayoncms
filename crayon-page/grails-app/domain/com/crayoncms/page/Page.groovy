package com.crayoncms.page

import com.crayoncms.page.enums.PageStatus
import com.crayoncms.user.Role
import com.crayoncms.user.RoleGroup
import com.crayoncms.theme.Layout

class Page {

	String name
	String slug
	String content
	PageStatus status
	Layout layout
	String keywords
	String description
	short parent
	int menuOrder
	RoleGroup roleGroup
	String bodyCss
	Date dateCreated
	Date lastUpdated

	static hasMany = [roleGroup: RoleGroup]

    static constraints = {
		name maxSize:160
		slug maxSize: 170, unique: true
		content widget: 'textarea', nullable: true
        layout [:]
		description maxSize: 250, widget: 'textarea'
		keywords maxSize: 200
    	parent maxSize: 1, display: true
    	menuOrder maxSize: 4, display: false
		roleGroup [:]
		bodyCss blank: true, maxSize: 50
    }
	
	static mapping = {
		content type: 'text'
		//access column: 'role_group_id'
	}
}
