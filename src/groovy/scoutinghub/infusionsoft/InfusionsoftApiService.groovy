package scoutinghub.infusionsoft

import groovy.net.xmlrpc.XMLRPCServerProxy

/**
 * Uses groovy magic to essentially curry infusionsoft api calls, making it so you don't have to pass the apiKey in with each call.
 *
 * User: eric
 * Date: 6/19/11
 * Time: 6:00 PM
 */
class InfusionsoftApiService {
    XMLRPCServerProxy serverProxy
    String apiKey
    String serviceName

    def methodMissing(String name, args) {
        def newArgs = [apiKey]
        args.each{newArgs << it}
        serverProxy."${serviceName}".invokeMethod(name, newArgs)
    }
}
