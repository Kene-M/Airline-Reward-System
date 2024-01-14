/**
 * This class represents every Passenger that has had under 25 of their
 * flights cancelled and is therefore not yet eligible for the rewards program.
 */
public class NoTier implements Tier {
    private final int MILES_PER_FLIGHT = 1000; // Number of miles per cancelled flight.
    private int numCancFlights = 0; // Number of flights the passenger was supposed to take but were cancelled.
    private int miles = 0; // The total number of miles the Passenger earned based on the cancelled flights.
    private int numFlights = 0; // The total number of flights a Passenger has taken, or was supposed to take.

    /**
     * This method returns the miles earned so far.
     * @return The total number of miles the Passenger earned based on the cancelled flights.
     */
    @Override
    public int getMiles() {
        return this.miles;
    }

    /**
     * This method returns the current number of flights the
     * passenger was supposed to take but were cancelled.
     * @return Number of flights the passenger was supposed to take but were cancelled.
     */
    @Override
    public int getCancelledFlights() {
        return this.numCancFlights;
    }

    /**
     * This method returns the current total number of flights
     * the passenger took, including the cancelled flights.
     * @return The total number of flights a Passenger has taken, or was supposed to take.
     */
    @Override
    public int getFlights() {
        return this.numFlights;
    }

    /**
     * This method adds a single new flight, it also adds a
     * new cancelled flight if the flight was cancelled.
     * @param isCancelled Flag indicating whether a flight was cancelled.
     */
    @Override
    public void addFlight(boolean isCancelled) {
        numFlights++;

        // If the Passengers flight was cancelled, update the miles earned.
        if(isCancelled) {
            numCancFlights++;
            miles += MILES_PER_FLIGHT;
        }
    }

    /**
     * This method sets the number of flights a Passenger has taken, whenever there is a Tier upgrade.
     * @param numFlights The total number of flights a Passenger has taken, or was supposed to take.
     */
    public void setNumFlights(int numFlights) {
        this.numFlights = numFlights;
    }
}
