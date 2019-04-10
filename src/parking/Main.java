package parking;

import java.util.Scanner;

/**
 * Copyright (c) Anton on 05.04.2019.
 */
public class Main {

    public static void main(String[] args) {
        Parking parking = new Parking(System.in);
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Please, input command: ");
            String line = scanner.nextLine().trim();
            if (line.matches("p:[1-9]*[0-9]")) {
                //park N cars
                String[] split = line.split(":");
                parking.park(Integer.parseInt(split[1]));
            } else if (line.matches("u:[1-9]*[0-9]")) {
                //unpark a car with selected ticket number
                String[] split = line.split(":");
                parking.unPark(Integer.parseInt(split[1]));
            } else if (line.matches("u:\\[([0-9]*,?)+]")) {
                //unpark cars with selected ticket numbers
                String[] ticketNumbers = findTicketNumbers(line);
                parking.unpark(ticketNumbers);
            } else if (line.matches("l")) {
                System.out.println("Cars list: ");
                parking.list();
            } else if (line.matches("c")) {
                System.out.print("Count of free parking spaces: ");
                parking.count();
            } else if (line.matches("e")) {
                System.out.println("Bye!");
                System.exit(0);
            }
            else{
                System.out.println("Unrecognized command: " + line);
            }
        }
    }

    private static String[] findTicketNumbers(String line) {
        String[] split = line.split(":");
        String numbersString = split[1];
        String substring = numbersString.substring(1, numbersString.indexOf("]"));
        return substring.split(",");
    }
}
