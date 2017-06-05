package com.crayoncms.page

class PageUrlMappings {

    static mappings = {

        "/admin/page" resources: "page", namespace: "admin"

        "/page/?**" controller: "page", action:"show"
		"/$slug**?" controller: "page", action:"show"
        "/" controller: "page", action:"show"

    }
}
