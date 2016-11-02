package com.crayoncms

import com.crayoncms.enums.MenuType

import groovy.transform.ToString

class Menu {

	String name
	MenuType menuType
	String menuTypeValue
	MenuGroup menuGroup
	int position = 1
	int parent = 0
	boolean targetBlank = false
	String access
	String cssClass
	Date dateCreated
	Date lastUpdated
	
	static belongsTo = [menuGroup: MenuGroup]
	
    static constraints = {
		name maxSize: 50, blank: false
		menuType blank: false
		menuTypeValue blank: false, maxSize: 70
		menuGroup [:]
		position [:]
		parent [:]
		targetBlank [:]
		access blank: true
		cssClass maxSize:20, blank: true
    }
}
