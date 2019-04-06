import java.io.IOException;
import java.util.ArrayList;
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
            } else if (line.matches("l")) {
                parking.list();
            } else if (line.matches("c")){
                parking.count();
            } else if (line.matches("e")){
                System.exit(0);
            }
        }
    }


    public static void waitAllFutures(List <Future> futures) throws InterruptedException {
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
