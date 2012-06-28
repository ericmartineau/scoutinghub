package scoutinghub.meritbadge

class MeritBadge {

    String name
    String category
    boolean required

    static constraints = {
        category(nullable: true)
    }

    @Override
    String toString() {
        return name;
    }


}
