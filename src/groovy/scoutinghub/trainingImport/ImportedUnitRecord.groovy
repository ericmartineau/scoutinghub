package scoutinghub.trainingImport

/**
 * User: eric
 * Date: 3/27/11
 * Time: 1:16 PM
 */
class ImportedUnitRecord {
    String districtName
    String unitType
    String unitNumber
    String programName
    String sponseringOrg


    public String toString ( ) {
    final StringBuilder sb = new StringBuilder ( ) ;
    sb . append ( "ImportedUnitRecord" ) ;
    sb . append ( "{districtName='" ) . append ( districtName ) . append ( '\'' ) ;
    sb . append ( ", unitType='" ) . append ( unitType ) . append ( '\'' ) ;
    sb . append ( ", unitNumber='" ) . append ( unitNumber ) . append ( '\'' ) ;
    sb . append ( ", programName='" ) . append ( programName ) . append ( '\'' ) ;
    sb . append ( ", sponseringOrg='" ) . append ( sponseringOrg ) . append ( '\'' ) ;
    sb . append ( '}' ) ;
    return sb . toString ( ) ;
    }


    boolean equals(o) {
        if (this.is(o)) return true;
        if (!(o instanceof ImportedUnitRecord)) return false;

        ImportedUnitRecord that = (ImportedUnitRecord) o;

        if (districtName != that.districtName) return false;
        if (programName != that.programName) return false;
        if (sponseringOrg != that.sponseringOrg) return false;
        if (unitNumber != that.unitNumber) return false;
        if (unitType != that.unitType) return false;

        return true;
    }

    int hashCode() {
        int result;
        result = (districtName != null ? districtName.hashCode() : 0);
        result = 31 * result + (unitType != null ? unitType.hashCode() : 0);
        result = 31 * result + (unitNumber != null ? unitNumber.hashCode() : 0);
        result = 31 * result + (programName != null ? programName.hashCode() : 0);
        result = 31 * result + (sponseringOrg != null ? sponseringOrg.hashCode() : 0);
        return result;
    }
}
