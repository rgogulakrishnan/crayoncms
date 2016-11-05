package com.crayoncms.menu

class MenuGroup {

	String name
	String slug
	Date dateCreated
	Date lastUpdated
	
	static hasMany = [menu: Menu]
	
    static constraints = {
		name maxSize: 40, blank: false
		slug blank: true, unique: true, nullable: true, display: false
    }
	
	static mapping = {
		menu sort: 'position'
		sort "name"
	}
	
	def beforeInsert() {
		if(name) {
			slug = name.toLowerCase().replaceAll(/[^a-zA-Z0-9]+/, "-")
		}
	}
	
	String toString() {
		name
	}
	
}
