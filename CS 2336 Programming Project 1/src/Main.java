import java.util.Scanner;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

/**
 * This program uses inheritance, polymorphism, and, hashmaps to simulate a tier based
 * rewards program for an airline that is based on the miles flown within the span
 * of a year, which itself is based on the number of flights cancelled by the airline.
 *                                        *
 * The program first reads each passenger's flight information from a file and updates their
 * information and tiers in real time. After reading the file (which represents that the year is
 * over), it then upgrades all passengers that did not complain about their cancelled flights at all to
 * special sub-tiers, then finally outputs data for passengers when their ID has been entered by the user.
 * @author Kenechukwu Maduabum
 * @version 1.0
 */
public class Main {
    private boolean isCancelled; // Flag for indicating whether the current Passenger's flight was cancelled.
    private boolean hasComplain; // Flag for indicating whether the current Passenger complained about their flight.

    /**
     * This method parse each line in the input file, and get some information about each Passenger's flight.
     * @param fileLine A line in the input file.
     * @return The look-up ID for each Passenger.
     */
    public String tokenize(String fileLine) {
        int i = 0; // An index in the file line String.
        String passengerID = ""; // The look-up key for each Passenger.

        // Tokenize the line read from the file.
        // Get the passenger ID first.
        while (Character.isDigit(fileLine.charAt(i))) {
            passengerID += fileLine.charAt(i);
            i++;
        }
        // Skip extra whitespace.
        i++;

        // Check whether a flight was cancelled and/or the passenger complained.
        if (fileLine.charAt(i) == 'Y') {
            this.isCancelled = true;
            i += 2;
            if (fileLine.charAt(i) == 'Y')
                this.hasComplain = true;
            else if (fileLine.charAt(i) == 'N')
                this.hasComplain = false;
        }
        else
            this.isCancelled = false;

        return passengerID;
    }

    /**
     * The main() method will read and update all the information about every
     * Passenger's flight, while upgrading their Tier's depending on whether
     * they qualify for an upgrade(while reading the file). It will then display.
     * @param args command-line arguments.
     */
    public static void main(String[] args) throws IOException {
        FileInputStream inStream = new FileInputStream("flight-data.txt"); // An input object for opening the file.
        Scanner scan = new Scanner(inStream); // A Scanner object for reading input.
        Passenger passObj = new Passenger(); // An object for keeping record of a Passenger.
        HashMap<String, Passenger> passengerInfo = new HashMap<>(); // A map for keeping records of all Passengers.
        Main mainObj = new Main(); // An instance of Main for accessing its fields and tokenize().
        String fileLine; // A line in the input file.
        String passengerID; // The look-up key for each Passenger.
        String tierName; // The name of the Passenger's current Tier.
        boolean hasMileageM; // Flag for indicating whether a passenger has the
                            // mileage multiplier (did not complain throughout the year).

        // Read each passenger record and update their details in real time.
        while (scan.hasNextLine()) {
            fileLine = scan.nextLine();

            passengerID = mainObj.tokenize(fileLine);

            // Check whether the passenger is new or not.
            if (passengerInfo.get(passengerID) == null) {
                passObj = new Passenger();
                passengerInfo.put(passengerID, passObj);
            }
            else
                passObj = passengerInfo.get(passengerID);
            passObj.addFlight(mainObj.isCancelled);

            // Check if the passenger complained and update the number of complaints.
            if(mainObj.isCancelled && mainObj.hasComplain)
                passObj.addComplaint();
        }
        inStream.close();
        passObj.setEndOfYear(true);

        // Iterate over all the passengers, determine if they have the mileage multiplier and/or update their tier.
        for (Passenger passObj2 : passengerInfo.values()) {
            hasMileageM = passObj2.hasMultiplier();

            // Passengers without a mileage multiplier do not qualify for an upgrade.
            if(!hasMileageM)
                continue;
            tierName = passObj2.getTier();
            passObj2.setSpecialTier(tierName);
        }

        scan = new Scanner(System.in);

        // Display the data of passengers, when the user looks up their ID.
        do {
            System.out.print("Enter the ID of the passenger (or \"-1\" to quit): ");
            passengerID = scan.next();

            if (!(passengerID.equals("-1"))) {
                // Check if the entered passenger ID exists.
                passObj = passengerInfo.get(passengerID);
                if(passObj == null) {
                    System.out.println("Passenger not found.\n");
                    continue;
                }
                tierName = passObj.getTier();
                System.out.print("Rewards tier: ");

                if (tierName.equals("NoTier"))
                    System.out.println("None");
                else if (tierName.equals("Gold"))
                    System.out.println("Gold");
                else if (tierName.equals("Platinum"))
                    System.out.println("Platinum");
                else if (tierName.equals("PlatinumPro"))
                    System.out.println("Platinum Pro");
                else if (tierName.equals("ExecutivePlatinum"))
                    System.out.println("Executive Platinum");
                else if (tierName.equals("SExecutivePlatinum"))
                    System.out.println("Super Executive Platinum");
                System.out.println("Total flights: " + passObj.getFlights());
                System.out.println("Total cancelled flights: " + passObj.getCancelledFlights());
                System.out.println("Total miles accumulated: " + passObj.getMiles());

                hasMileageM = passObj.hasMultiplier();

                if(hasMileageM)
                    System.out.println("This passenger earned the mileage multiplier\n");
                else
                    System.out.println("This passenger did not earn the mileage multiplier\n");
            }
        } while(!(passengerID.equals("-1")));

        System.out.println("Program terminated...");
    }
}