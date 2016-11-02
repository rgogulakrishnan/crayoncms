class CrayonPageUrlMappings {

    static mappings = {
        
		"/asset/upload" controller: "asset", action: "upload"
		
		"/$slug**?" controller: "page", action:"show"
        "/" controller: "page", action:"show"

        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}
