import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Copyright (c) Anton on 05.04.2019.
 */
public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        Parking parking = new Parking();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("What next: ");
            String line = scanner.nextLine().trim();
            if (line.matches("p:[1-9]*[0-9]")) {
                String[] split = line.split(":");
                parking.park(Integer.parseInt(split[1]));
                waitAllFutures(parking.getFutures());
            } else if (line.matches("u:[1-9]*[0-9]")) {
                String[] split = line.split(":");
                parking.unPark(Integer.parseInt(split[1]));
            } else if (line.matches("u:\\[([0-9]*,?)+\\]")) {
                String[] ticketNumbers = findTicketNumbers(line);
                for (String ticketNumber : ticketNumbers) {
                    parking.unPark(new Integer(ticketNumber));
                }
                waitAllFutures(parking.getFutures());
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
        }
    }

    private static String[] findTicketNumbers(String line) {
        String[] split = line.split(":");
        String substring = split[1].substring(1, split[1].indexOf("]"));
        return substring.split(",");
    }


    private static void waitAllFutures(List <Future> futures) throws InterruptedException {
        for (Future future : futures) {
            try {
                future.get();
            } catch (ExecutionException e) {
                throw new IllegalStateException("Future problem", e);
            }
        }
        futures.clear();
    }
}
