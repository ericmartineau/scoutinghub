class UrlMappings {

	static mappings = {

      "/login/auth" {
         controller = 'openId'
         action = 'auth'
      }

        /*
        "/login/openIdCreateAccount" {
         controller = 'login'
         action = 'accountLink'
      }

         */


		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}


		"500"{
            controller = "errorRendering"
            action = "fiveHundred"
        }

        "/"
        {
            controller = "homePage"
            action = "index"
        }
	}
}
