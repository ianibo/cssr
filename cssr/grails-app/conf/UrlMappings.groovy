class UrlMappings {

	static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/"(controller:'home',action:'index')
        "/$la/$sch"(controller:'home',action:'schhome')
        "/$la/$sch/report"(controller:'home',action:'report')

        "500"(view:'/error')
	}
}
