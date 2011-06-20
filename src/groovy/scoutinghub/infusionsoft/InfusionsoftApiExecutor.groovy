package scoutinghub.infusionsoft

import groovy.net.xmlrpc.XMLRPCServerProxy

/**
 * Uses groovy magic to essentially curry infusionsoft api calls, making it so you don't have to pass the apiKey in with each call.
 *
 * User: eric
 * Date: 6/19/11
 * Time: 5:55 PM
 */
class InfusionsoftApiExecutor {
    XMLRPCServerProxy serverProxy
    String apiKey

    def propertyMissing(String name) {
        new InfusionsoftApiService(serverProxy: serverProxy, apiKey: apiKey, serviceName: name)
    }
}
