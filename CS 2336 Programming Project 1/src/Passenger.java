/**
 * This class represents each Passenger of the airline.
 */
public class Passenger {
    private Tier tierObj; // An object representing the Passenger's current Tier.
    private int numComplaints = 0; // The number of times a Passenger has complained.
    private static boolean isEndOfYear = false; // Flag for indicating whether the current
                                                // year is over (have reached end of file).

    /**
     * This constructor sets every new Passenger to not have a
     * Tier, because they are not yet eligible for the rewards program.
     */
    public Passenger() {
        this.tierObj = new NoTier();
    }

    /**
     * Returns (as a String) the name of the tier the passenger belongs to.
     * @return The name of the tier the passenger belongs to.
     */
    public String getTier() {
        return tierObj.getClass().getName();
    }

    /**
     * Returns the miles earned over the year. This method will simply
     * return the miles from the Tier object, by calling its getMiles method.
     * @return The total number of miles the Passenger earned based on the cancelled flights.
     */
    public int getMiles() {
        return tierObj.getMiles();
    }

    /**
     * Returns the number of flights the passenger was supposed to take but were cancelled.
     * This method will simply return the number of cancelled flights from the Tier object.
     * @return Number of flights the passenger was supposed to take but were cancelled.
     */
    public int getCancelledFlights() {
        return tierObj.getCancelledFlights();
    }

    /**
     * This method returns the current total number of flights the passenger took, including
     * cancelled flights. This method will simply return the number of flights from the Tier object.
     * @return The total number of flights a Passenger has taken, or was supposed to take.
     */
    public int getFlights() {
        return tierObj.getFlights();
    }

    /**
     * Returns true if the passenger has the mileage multiplier. This will
     * return false until the end of the year, at which point a determination
     * can be made whether the passenger earned the mileage multiplier.
     * @return Flag for indicating whether a passenger has the mileage multiplier.
     */
    public boolean hasMultiplier() {
        // Passengers without a tier or are in gold tier automatically do not qualify.
        if(getTier().equals("NoTier") || getTier().equals("Gold"))
            return false;
        if(isEndOfYear && numComplaints > 0)
            return false;
        else if(isEndOfYear && numComplaints == 0)
            return true;
        return false;
    }

    /**
     * This method adds a new flight by calling the same method in the Tier object.
     * This method will also to determine if a passenger needs a tier upgrade.
     * @param isCancelled Flag for indicating whether the current Passenger's flight was cancelled.
     */
    public void addFlight(boolean isCancelled) {
        String tierName; // The name of the tier of the Passenger.
        int numFlights; // The total number of flights Passenger has taken, or was supposed to take.

        tierObj.addFlight(isCancelled);
        numFlights = tierObj.getFlights();

        // Get the name of the tier of the passenger.
        tierName = tierObj.getClass().getName();

        // Upgrade the tier of the passenger, depending on their current tier.
        if (tierName.equals("NoTier")) {
            // Upgrade the passenger to gold tier, if they qualify.
            if(tierObj.getCancelledFlights() >= 25)
                tierObj = new Gold();
        }
        else if (tierName.equals("Gold")) {
            // Upgrade the passenger to platinum tier, if they qualify.
            if((tierObj).getCancelledFlights() >= 50)
                tierObj = new Platinum();
        }
        else if (tierName.equals("Platinum")) {
            // Upgrade the passenger to executive platinum tier, if they qualify.
            if((tierObj).getCancelledFlights() >= 100)
                tierObj = new ExecutivePlatinum();
        }
        tierObj.setNumFlights(numFlights);
    }

    /**
     * This method will update the number of times a Passenger has complained.
     */
    public void addComplaint() {
        numComplaints++;
    }

    /**
     * This method will indicate that a year has passed.
     * @param isEndOfYear Flag for indicating whether the current year is over (have reached end of file).
     */
    public void setEndOfYear(boolean isEndOfYear) {
         Passenger.isEndOfYear = isEndOfYear;
    }

    /**
     * This method will upgrade a Platinum or Executive Platinum Tier member
     * to the corresponding special Sub-Tier if they earned the mileage multiplier.
     * @param tierName The name of the Passenger's current Tier.
     */
    public void setSpecialTier(String tierName) {
        int numCancFlights = tierObj.getCancelledFlights(); // The total number of flights a Passenger has taken.
        int numFlights = tierObj.getFlights(); // The total number of flights Passenger
                                                // has taken, or was supposed to take.

        // Upgrade the passenger to the special sub-tier, depending on their current tier.
        if (tierName.equals("Platinum")) {
            tierObj = new PlatinumPro(numCancFlights, numFlights);
        }
        else if (tierName.equals("ExecutivePlatinum")) {
            tierObj = new SExecutivePlatinum(numCancFlights, numFlights);
        }
    }
}
