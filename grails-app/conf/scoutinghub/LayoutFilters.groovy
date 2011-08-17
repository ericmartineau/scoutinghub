package scoutinghub

class LayoutFilters {

    def filters = {
        all(controller: '*', action: '*') {
            before = {

//                if (params.isMobile) {
                //                    session.isMobile = true
                //                    request.layoutName = "iwebkit"
                //                } else if (!session.isMobile) {
                //                    session.isMobile = false
                //                    request.layoutName = "main"
                //                } else {
                //                    request.layoutName = "iwebkit"
                //                }
                if (request.getHeader('user-agent') =~ /(?i)iphone/ || params.isMobile || session.isMobile) {
                    session.isMobile = true
                    request.layoutName = "iwebkit"
                    request.dialogLayoutName = "iwebkit"
                } else {
                    session.isMobile = false
                    request.layoutName = "main"
                    request.dialogLayoutName = "dialog"
                }

            }
            after = {

            }
            afterView = {

            }
        }
    }

}
