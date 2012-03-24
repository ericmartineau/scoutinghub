package scoutinghub

import grails.plugins.springsecurity.SpringSecurityService

class CreateAccountService {

    SpringSecurityService springSecurityService;
    static transactional = false

    void mergeCreateAccountWithExistingLeader(CreateAccountCommand createAccount, Leader leader) {
        leader.firstName = createAccount?.firstName ?: leader.firstName
        leader.lastName = createAccount?.lastName ?: leader.lastName
        leader.email = createAccount?.email ?: leader.email
//        leader.phone = createAccount?.phone ?: leader.phone
//        leader.username = createAccount?.username ?: leader.username ?: createAccount?.email
//        leader.username = createAccount?.username ?: leader.username ?: createAccount?.email
        leader.password = springSecurityService.encodePassword(createAccount.password)

    }

}
