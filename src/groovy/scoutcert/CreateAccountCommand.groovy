package scoutcert

/**
 * User: eric
 * Date: 3/16/11
 * Time: 11:14 AM
 */
class CreateAccountCommand implements Serializable {

    String firstName
    String lastName
    String email
    ScoutGroup unit
    LeaderPositionType unitPosition
    String scoutid

    String username
    String password
    String confirmPassword

    public String getUsernameOrEmail() {
        return username ?: email
    }

}