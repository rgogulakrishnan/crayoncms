package com.crayoncms

class CoreUrlMappings {

    static mappings = {

        // All CMS core mappings
        "/admin/dashboard" controller: "dashboard", namespace: "admin"
        "/admin/plugin" controller: "plugin", namespace: "admin"
        "/admin/setting" controller: "setting", namespace: "admin"
        "/admin/setting/save" controller: "setting", action: "save", namespace: "admin"

        "/asset/upload" controller: "asset", action: "upload"
		
		"/admin/user" resources: "user", namespace: "admin"
	    "/admin/roleGroup" resources: "roleGroup", namespace: "admin"

        "/admin/roleGroup/addRole" controller: "roleGroup", action: "addRole", namespace: "admin"
        "/admin/roleGroup/removeRole" controller: "roleGroup", action: "removeRole", namespace: "admin"

        "/admin/myprofile" controller: "user", action: "myprofile", namespace: "admin"
        "/admin/user/changeProfilePic" controller: "user", action: "changeProfilePic", namespace: "admin"
        "/admin/user/profilePicture" controller: "user", action: "profilePicture", namespace: "admin"
		
		"/admin/layout" resources: "layout", namespace: "admin"
		
		"/admin/page" resources: "page", namespace: "admin"

        "/page/?**" controller: "page", action:"show"
		"/$slug**?" controller: "page", action:"show"
        "/" controller: "page", action:"show"

    }
}
