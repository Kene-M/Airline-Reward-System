/**
 * This interface represents a level in the rewards program that every
 * Passenger belongs to. It is based on the miles flown within the span
 * of a year, which itself is based on the number of cancelled flights.
 */
public interface Tier {
    /**
     * This method returns the miles earned so far.
     * @return The total number of miles the Passenger earned based on the cancelled flights.
     */
    public int getMiles();

    /**
     * This method returns the current number of flights the
     * passenger was supposed to take but were cancelled.
     * @return Number of flights the passenger was supposed to take but were cancelled.
     */
    public int getCancelledFlights();

    /**
     * This method returns the current total number of flights
     * the passenger took, including the cancelled flights.
     * @return The total number of flights a Passenger has taken, or was supposed to take.
     */
    public int getFlights();

    /**
     * This method adds a single new flight, it also adds a
     * new cancelled flight if the flight was cancelled.
     * @param isCancelled Flag indicating whether a flight was cancelled.
     */
    public void addFlight(boolean isCancelled);

    /**
     * This method sets the number of flights a Passenger has taken, whenever there is a Tier upgrade.
     * @param numFlights The total number of flights a Passenger has taken.
     */
    public void setNumFlights(int numFlights);
}
