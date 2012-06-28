package scoutinghub.seed

import scoutinghub.SeedScript
import scoutinghub.meritbadge.MeritBadge

class SeedMeritBadgeService implements SeedScript {

    static transactional = true

    def serviceMethod() {

    }

    int getOrder() {
        return 1
    }

    void execute() {
        def meritBadges = "Camping*\n" +
                "Fish and Wildlife\n" +
                "Sculpture\n" +
                "Citizenship In Community*\n" +
                "Fishing\n" +
                "Signaling#\n" +
                "Citizenship In Nation*\n" +
                "Food Systems#\n" +
                "Skating\n" +
                "Citizenship In World*\n" +
                "Forestry\n" +
                "Skiing#\n" +
                "Communication*\n" +
                "Gardening\n" +
                "Small Boat Sailing\n" +
                "Emergency Prep*\n" +
                "Genealogy\n" +
                "Soil and Water\n" +
                "Environmental Science*\n" +
                "General Science#\n" +
                "Space Exploration\n" +
                "First Aid*\n" +
                "Geology\n" +
                "Stamp Collecting\n" +
                "Lifesaving*\n" +
                "Golf\n" +
                "Surveying\n" +
                "Personal Fitness*\n" +
                "Disability Aware\n" +
                "Textile\n" +
                "Personal Management*\n" +
                "Hiking*\n" +
                "Theater\n" +
                "Safety\n" +
                "Home Repairs\n" +
                "Traffic Safety\n" +
                "Sports\n" +
                "Horsemanship\n" +
                "Truck Transport\n" +
                "Swimming*\n" +
                "Indian Lore\n" +
                "Veterinary Medicine\n" +
                "American Business\n" +
                "Insect Study\n" +
                "Water Sports\n" +
                "American Heritage\n" +
                "Journalism\n" +
                "Weather\n" +
                "American Cultures\n" +
                "Landscape Architect\n" +
                "Wilderness Survival\n" +
                "Animal Science\n" +
                "Law\n" +
                "Wood Carving\n" +
                "Archery\n" +
                "Leatherwork\n" +
                "Woodwork\n" +
                "Architecture\n" +
                "Machinery#\n" +
                "Agribusiness#\n" +
                "Art\n" +
                "Mammal Study\n" +
                "American Labor\n" +
                "Astronomy\n" +
                "Masonry#\n" +
                "Graphic Arts\n" +
                "Athletics\n" +
                "Metals Engineering#\n" +
                "Rifle Shooting\n" +
                "Nuclear Science\n" +
                "Metalwork\n" +
                "Shotgun Shooting\n" +
                "Aviation\n" +
                "Model Design\n" +
                "Whitewater\n" +
                "Backpacking\n" +
                "Motorboating\n" +
                "Cinematography\n" +
                "Basketry\n" +
                "Music\n" +
                "Auto Maintenance\n" +
                "Beekeeping#\n" +
                "Nature\n" +
                "Collections\n" +
                "Bird Study\n" +
                "Oceanography\n" +
                "Family Life*\n" +
                "Bookbinding#\n" +
                "Orienteering\n" +
                "Medicine\n" +
                "Botany#\n" +
                "Painting\n" +
                "Crime Prevention\n" +
                "Bugling\n" +
                "Pets\n" +
                "Archaeology\n" +
                "Canoeing\n" +
                "Photography\n" +
                "Climbing\n" +
                "Chemistry\n" +
                "Pioneering\n" +
                "Entrepreneur\n" +
                "Coin Collecting\n" +
                "Plant Science\n" +
                "Snow Sports\n" +
                "Computers\n" +
                "Plumbing\n" +
                "Fly Fishing\n" +
                "Consumer Buying#\n" +
                "Pottery\n" +
                "Composite Materials\n" +
                "Cooking\n" +
                "Print/Communicate#\n" +
                "Scuba Diving\n" +
                "Cycling*\n" +
                "Public Health\n" +
                "Carpentry\n" +
                "Dentistry\n" +
                "Public Speaking\n" +
                "Pathfinding\n" +
                "Dog Care\n" +
                "Pulp and Paper\n" +
                "Signaling\n" +
                "Drafting\n" +
                "Rabbit Raising#\n" +
                "Tracking\n" +
                "Electricity\n" +
                "Radio\n" +
                "Scouting Heritage\n" +
                "Electronics\n" +
                "Railroading\n" +
                "Inventing\n" +
                "Energy\n" +
                "Reading\n" +
                "Geocaching\n" +
                "Engineering\n" +
                "Reptile/Amphibian\n" +
                "Robotics\n" +
                "Farm/Ranch Mgt#\n" +
                "Rifle/Shotgun#\n" +
                "Chess\n" +
                "Farm Mechanics\n" +
                "Rowing\n" +
                "Reptile Study#\n" +
                "Fingerprinting\n" +
                "Salesmanship\n" +
                "Fire Safety\n" +
                "Scholarship"
        def splitMB = meritBadges.split("\n")
        splitMB?.each {
            MeritBadge meritBadge = new MeritBadge()
            meritBadge.name = it
            meritBadge.required = name.lastIndexOf('*') > -1
            meritBadge.save(failOnError: true)
        }
        
    }

    String getName() {
        return "Merit Badge"
    }


}
