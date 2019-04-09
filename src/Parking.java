import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Copyright (c) Anton on 05.04.2019.
 */
class Parking {
    public static final String TIME_TO_ENTER = "timeToEnter";
    private String timeToEnter;
    private volatile ArrayList <Ticket> availableTickets;
    private volatile ArrayList <Car> cars = new ArrayList <>();
    private ExecutorService gates = Executors.newFixedThreadPool(2);
    private ArrayList <Future> futures = new ArrayList <>();

    Parking() {
        initAvailableTickets(System.in);
        initParkTime();
    }

    /* for tests */
    Parking(InputStream in) {
        initAvailableTickets(in);
        initParkTime();
    }

    void park(int numberOfCars) {
        Iterator <Ticket> iter = availableTickets.iterator();
        while (iter.hasNext()) {
            if (numberOfCars == 0) return;
            Ticket ticket = iter.next();
            Car car = new Car();
            futures.add(gates.submit(() -> {
                park(car, ticket);
                try {
                    System.out.println("Parking " + car + " in process..");
                    Thread.sleep(TimeUnit.SECONDS.toMillis(new Integer(timeToEnter)));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.out.println("Problem with " + car + " on Parking");
                }
            }));
            iter.remove();
            numberOfCars--;
        }
    }

    private void park(Car car, Ticket ticket) {
        car.setParkingTicket(ticket);
        cars.add(car);
    }

    void unPark(int ticketNumber) {
        for (Ticket availableTicket : availableTickets) {
            if (availableTicket.getNumber() == ticketNumber) {
                System.out.println(ticketNumber + " is free!");
                return;
            }

            boolean isFound = false;
            for (Car car : cars) {
                if (car.getParkingTicket().getNumber() == ticketNumber) {
                    isFound = true;
                    break;
                }
            }
            if (!isFound) {
                System.out.println("Parking doesnt contain ticket =  " + ticketNumber);
                return;
            }

            for (Car car : cars) {
                if (!(car.getParkingTicket().getNumber() == ticketNumber)) continue;
                Ticket ticket = car.getParkingTicket();
                if (ticket.getNumber() == ticketNumber) {
                    futures.add(gates.submit(() -> {
                        availableTickets.add(ticket);
                        cars.remove(car);
                        try {
                            System.out.println("Unparking " + car + " in process..");
                            Thread.sleep(TimeUnit.SECONDS.toMillis(1));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            System.out.println("Problem with " + car + " on Parking");
                        }
                    }));
                }
            }
        }
    }

    void list() {
        for (Car car : cars) {
            System.out.println(car);
        }
    }

    void count() {
        System.out.println(availableTickets.size());
    }

    private void initAvailableTickets(InputStream in) {
        Scanner keyboard = new Scanner(in);
        System.out.print("Please, enter parking size: ");
        String line = validateInput(keyboard);
        int size = Integer.parseInt(line);
        availableTickets = new ArrayList <>(size);
        for (int i = 0; i < size; i++)
            availableTickets.add(new Ticket());
    }

    private void initParkTime() {
        Properties prop = new Properties();
        InputStream input = null;
        try {
            input = new FileInputStream("config.properties");
            prop.load(input);
            timeToEnter = prop.getProperty(TIME_TO_ENTER);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private String validateInput(Scanner keyboard) {
        String line = keyboard.nextLine().trim();
        while (!line.matches("[1-9]*[0-9]")) {
            System.out.print("Parking size must be a positive number! Please, input correct value: ");
            line = keyboard.nextLine();
        }
        return line;
    }

    ArrayList <Future> getFutures() {
        return futures;
    }

    ArrayList <Car> getCars() {
        return cars;
    }

    ArrayList <Ticket> getAvailableTickets() {
        return availableTickets;
    }
}
