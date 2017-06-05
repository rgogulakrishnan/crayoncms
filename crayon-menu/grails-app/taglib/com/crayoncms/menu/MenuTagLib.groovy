package com.crayoncms.menu

import com.crayoncms.menu.enums.MenuType
import grails.plugin.springsecurity.SpringSecurityUtils

class MenuTagLib {
	
	static namespace = "crayoncms"
	
    static defaultEncodeAs = [taglib:'html']
    static encodeAsForTags = [adminMenu: [taglib:'none'], menu: [taglib:'none']]

	def springSecurityService

	def editMenuLink = '''<a href='menu/ID/edit' data-toggle="modal" data-target=".modal">LINK_NAME</a>'''
	def viewOnlyMenuLink = '''LINK_NAME'''

	def deleteFormOpen = '''<form action="/admin/menu/ID" method="POST">'''
	def deleteFormClose = "</form>"

	def deleteLink = '''
						<input type="hidden" name="_method" value="DELETE" id="_method">
						<input class="fa fa-trash-o" type="submit" value="&#xf014" onclick="return confirm('Are you sure?');" />
					'''
	
	def adminLiString = '''
						<li class='list' id='item_ID'>
							<div class='ui-sortable-handle'>
								DELETE_FORM_OPEN
									<i class='fa fa-arrows'></i>
									LINK_EDIT_OR_VIEWONLY
									LINK_DELETE
								DELETE_FORM_CLOSE
							</div>
						'''

	def liString = '''
						<li class="LINK_CLASS"><a href="LINK_HREF" target="LINK_TARGET">LINK_NAME</a>
					'''

	def adminMenu = { attrs ->
		def group = MenuGroup.findByName(attrs.groupName)
		def menus = Menu.findAllByMenuGroupAndParent(group, 0, [sort: 'position'])
		def menu = (menus) ? constructAdminMenuString("", menus, group) : ""
		out << menu ?: ""
	}
	
	def menu = { attrs ->
		def group = MenuGroup.findBySlug(attrs?.groupName)
		def menus = Menu.findAllByMenuGroupAndParent(group, 0, [sort: 'position'])
		def menu = (menus) ? constructMenuString("<nav id=\"nav\"><ul>", menus, group) : ""
		menu += "<ul></nav>"
		out << menu ?: ""
	}

	private String constructMenuString(String menu, def menus, def group) {
		menus.each {
			boolean liStringAppend = true
			if((it.menuType == MenuType.LOGIN && springSecurityService.isLoggedIn())
				|| (it.menuType == MenuType.LOGOUT && !springSecurityService.isLoggedIn()) ) {
				liStringAppend = false
			}

			if(liStringAppend) {
				menu += liString.replace("LINK_CLASS", it.cssClass).replace("LINK_HREF", it.menuTypeValue)
						.replace("LINK_NAME", it.name).replace("LINK_TARGET", (it.openInNewTab == 'Yes' ? "_blank" : ""))
				def children = Menu.findAllByMenuGroupAndParent(group, it.id, [sort: 'position'])
				if (children) {
					menu += "<ul>"
					menu += constructMenuString("", children, group)
					menu += "</ul>"
				}
				menu += "</li>"
			}
		}
		menu
	}
	
	private String constructAdminMenuString(String menu, def menus, def group) {
		menus?.each {

			def tempAdminLiString = ""
			if(SpringSecurityUtils.ifAllGranted("ROLE_MANAGE_MENU")) {
				tempAdminLiString += adminLiString.replace("DELETE_FORM_OPEN", deleteFormOpen).replace("LINK_EDIT_OR_VIEWONLY", editMenuLink)
								.replace("LINK_DELETE", deleteLink).replace("DELETE_FORM_CLOSE", deleteFormClose)
			} else {
				tempAdminLiString += adminLiString.replace("DELETE_FORM_OPEN", "").replace("LINK_EDIT_OR_VIEWONLY", viewOnlyMenuLink)
						.replace("LINK_DELETE", "").replace("DELETE_FORM_CLOSE", "")
			}

			menu += tempAdminLiString.replace("ID", it.id.toString()).replace("LINK_NAME", it.name)

			def children = Menu.findAllByMenuGroupAndParent(group, it.id, [sort: 'position'])
			if(children) {
				menu += "<ol>"
				menu += constructAdminMenuString("", children, group)
				menu += "</ol>"
			}
			menu += "</li>"

		}

		menu
	}
}
