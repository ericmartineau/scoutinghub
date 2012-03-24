package scoutinghub

/**
 * User: ericm
 * Date: 10/19/11
 * Time: 5:19 PM
 */
class MergedRecord {

    static belongsTo = [mergedLeader:MergedLeader]

    MergedRecord() {

    }

    MergedRecord(Class mergeRecordClass, String mergeRecordField, Long recordId) {
        this.mergeRecordClass = mergeRecordClass
        this.mergeRecordField = mergeRecordField
        this.recordId = recordId
    }

    MergedRecord(Class mergeRecordClass, Long recordId) {
        this(mergeRecordClass, "leader", recordId)
    }

    MergedLeader mergedLeader
    Class mergeRecordClass
    String mergeRecordField
    int recordId
}
