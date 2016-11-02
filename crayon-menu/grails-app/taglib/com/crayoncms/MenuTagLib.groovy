package com.crayoncms

import com.crayoncms.Menu
import com.crayoncms.MenuGroup

class MenuTagLib {
	
	static namespace = "crayoncms"
	
    static defaultEncodeAs = [taglib:'html']
    static encodeAsForTags = [adminMenu: [taglib:'none'], menu: [taglib:'none']]
	
	def liString = '''
						<li class='list' id='item_ID'>
							<div class='ui-sortable-handle'>
								<i class='fa fa-arrows'></i>
								<a href='/menu/edit/ID'>LINK_NAME</a>
							</div>
					'''
	
	def adminMenu = { attrs -> 
		if(attrs?.groupName) {
			def menuLinks = constructAdminMenuLinks(attrs.groupName)
			if(menuLinks) {
				out << menuLinks
			}
		}
	}
	
	def menu = { attrs ->
		out << ""
	}
	
	
	
	private String constructAdminMenuLinks(String groupName) {
		def group = MenuGroup.findByName(groupName)
		def menus = Menu.findAllByMenuGroupAndParent(group, 0, [sort: 'position'])
		def menu = (menus) ? constructAdminMenuString("", menus, group) : ""
		return menu
	}
	
	private String constructAdminMenuString(String menu, def menus, def group) {
		menus.each {
			menu += liString.replace("ID", it.id.toString()).replace("LINK_NAME", it.name)
			def children = Menu.findAllByMenuGroupAndParent(group, it.id, [sort: 'position'])
			if(children) {
				menu += "<ol>"
				menu += constructAdminMenuString("", children, group)
				menu += "</ol>"
			}
			menu += "</li>"
		}
		return menu
	}
}
