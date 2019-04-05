import java.io.IOException;
import java.util.Scanner;

/**
 * Copyright (c) Anton on 05.04.2019.
 */
public class Main {

    public static void main(String[] args) throws IOException {
        Parking parking = new Parking();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("What next: ");
            String line = scanner.nextLine();
            if (line.matches("p:[1-9]*[0-9]")) {
                String[] split = line.split(":");
                parking.park(Integer.parseInt(split[1]));
            } else if (line.matches("l")) {
                parking.list();
            } else if (line.matches("c")){
                parking.count();
            } else if (line.matches("e")){
                System.exit(0);
            }
        }
    }


}
