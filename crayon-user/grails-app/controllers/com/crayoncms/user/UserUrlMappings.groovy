package com.crayoncms.user

class UserUrlMappings {

    static mappings = {
       
	    "/admin/user" resources: "user", namespace: "admin"
	    "/admin/roleGroup" resources: "roleGroup", namespace: "admin"

        "/admin/roleGroup/addRole" controller: "roleGroup", action: "addRole", namespace: "admin"
        "/admin/roleGroup/removeRole" controller: "roleGroup", action: "removeRole", namespace: "admin"
    }
}
