package scoutcert

/**
 * User: eric
 * Date: 3/29/11
 * Time: 9:59 PM
 */
enum LeaderCertificationEnteredType {
    Imported("Imported"),ManuallyEntered("Manually entered");

    public final String label

    LeaderCertificationEnteredType(String label) {
        this.label = label
    }
}
