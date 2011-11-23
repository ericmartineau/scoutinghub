package scoutinghub

/**
 * User: eric
 * Date: 3/29/11
 * Time: 9:59 PM
 */
enum LeaderCertificationEnteredType {
    Imported("Imported"),ManuallyEntered("Manually entered"),Merged("Merged");

    public final String label

    LeaderCertificationEnteredType(String label) {
        this.label = label
    }
}
