package com.crayoncms.menu

import com.crayoncms.menu.enums.MenuType

class Menu {

	String name
	MenuType menuType
	String menuTypeValue
	MenuGroup menuGroup
	int position = 1
	int parent = 0
	String openInNewTab
	String access
	String cssClass
	Date dateCreated
	Date lastUpdated
	
	static belongsTo = [menuGroup: MenuGroup]
	
    static constraints = {
		name maxSize: 50, blank: false
		menuType [:]
		menuTypeValue blank: false, maxSize: 70
		menuGroup [:]
		position display: false
		parent display: false
		openInNewTab inList: ['Yes', 'No']
		access blank: true
		cssClass maxSize:20, blank: true
    }

}
