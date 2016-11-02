package com.crayoncms.enums

enum MenuType {
	
	PLUGIN("Plugin"), 
	PAGE("Page"), 
	URL("URL"),
	URI("Uri")
	
	final String type
	
	MenuType(type) {
		this.type = type
	}
	
	String toString() {
		type
	}
	
	String getKey() {
		name()
	}
}