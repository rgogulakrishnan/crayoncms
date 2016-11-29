package com.crayoncms.menu.enums

enum MenuType {
	
	PLUGIN("Plugin"), 
	PAGE("Page"),
	LOGIN("Login"),
	LOGOUT("Logout"),
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