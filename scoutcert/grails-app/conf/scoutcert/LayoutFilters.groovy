package scoutcert

import org.geeks.browserdetection.UserAgentIdentService

class LayoutFilters {

    UserAgentIdentService userAgentIdentService

    def filters = {
        all(controller:'*', action:'*') {
            before = {
                if(userAgentIdentService.getPlatform() == "iPhone" || params.isMobile || session.isMobile) {
                    session.isMobile = true
                    request.layoutName = "iwebkit"
                } else {
                    session.isMobile = false
                    request.layoutName = "main"
                }

            }
            after = {
                
            }
            afterView = {
                
            }
        }
    }
    
}
