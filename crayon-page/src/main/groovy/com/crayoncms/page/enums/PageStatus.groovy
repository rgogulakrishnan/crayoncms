package com.crayoncms.page.enums

enum PageStatus {
	
	DRAFT("Draft"), 
	PUBLISHED("Published"), 
	ARCHIVED("Archived")
	
	final String status
	
	PageStatus(status) {
		this.status = status
	}
	
	String toString() {
		status
	} 
	
	String getKey() {
		name()
	}
}