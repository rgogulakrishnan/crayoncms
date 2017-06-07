package com.crayoncms

class MenuUrlMappings {

    static mappings = {

        "/admin/menu" resources: "menu", namespace: "admin"
        "/admin/menu/order" controller: "menu", action: "order", namespace: "admin"

		"/admin/menuGroup" resources: "menuGroup", namespace: "admin"

    }
}
