package parking;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.*;

/**
 * Copyright (c) Anton on 05.04.2019.
 */
class Parking {
    private static final String TIME_TO_ENTER = "timeToEnter";
    private Integer secondsToEnter;
    private Map<Integer, Ticket> availableTickets = new ConcurrentHashMap<>();
    private Map<Integer, Car> ticket2car = new ConcurrentHashMap<>();
    private ExecutorService gates = Executors.newFixedThreadPool(2);
    private ArrayList<Future> futures = new ArrayList<>();

    /* for tests */
    Parking(InputStream in) {
        initAvailableTickets(in);
        initParkTime();
    }

    void park(int numberOfCars) {
        for (Ticket ticket : availableTickets.values()) {
            if (numberOfCars == 0) break;
            futures.add(gates.submit(() -> {
                Car car = new Car();
                park(car, ticket);
            }));
            availableTickets.remove(ticket.getNumber());
            numberOfCars--;
        }
        if (numberOfCars > 0) {
            System.out.println("Not enough tickets to park " + numberOfCars + " car(s).");
        }
        waitAllFutures();
    }

    void unPark(int ticketNumber) {
        if (availableTickets.containsKey(ticketNumber)) {
            System.out.println("parking.Ticket + " + ticketNumber + " is not in use!");
            return;
        }

        Car car = ticket2car.get(ticketNumber);
        if (car == null) {
            System.out.println("parking.Parking doesn't contain car with ticket =  " + ticketNumber);
            return;
        }

        availableTickets.put(ticketNumber, car.getParkingTicket());
        ticket2car.remove(ticketNumber);
        try {
            System.out.println("Unparking of " + car + " in process..");
            Thread.sleep(TimeUnit.SECONDS.toMillis(secondsToEnter));
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }

    void unpark(String[] ticketNumbers) {
        for (String ticketNumber : ticketNumbers) {
            futures.add(gates.submit(() -> {
                unPark(new Integer(ticketNumber));
            }));
        }
        waitAllFutures();
    }

    void list() {
        ticket2car.values().forEach(System.out::println);
    }

    void count() {
        System.out.println(availableTickets.size());
    }

    private void park(Car car, Ticket ticket) {
        car.setParkingTicket(ticket);
        ticket2car.put(ticket.getNumber(), car);
        try {
            System.out.println("parking.Parking " + car + " in process..");
            Thread.sleep(TimeUnit.SECONDS.toMillis(secondsToEnter));
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }

    private void initAvailableTickets(InputStream in) {
        Scanner keyboard = new Scanner(in);
        System.out.print("Please, enter parking size: ");
        String line = validateInput(keyboard);
        int size = Integer.parseInt(line);
        for (int i = 0; i < size; i++) {
            Ticket ticket = new Ticket();
            availableTickets.put(ticket.getNumber(), ticket);
        }
    }

    private void initParkTime() {
        Properties prop = new Properties();
        try (InputStream input = new FileInputStream("config.properties")) {
            prop.load(input);
            secondsToEnter = new Integer(prop.getProperty(TIME_TO_ENTER));
        } catch (Exception e) {
            throw new IllegalStateException("Failed to load properties", e);
        }
    }

    private String validateInput(Scanner keyboard) {
        String line = keyboard.nextLine().trim();
        while (!line.matches("[1-9]*[0-9]")) {
            System.out.print("parking.Parking size must be a positive number! Please, input correct value: ");
            line = keyboard.nextLine();
        }
        return line;
    }

    ArrayList<Future> getFutures() {
        return futures;
    }

    Collection<Car> getCars() {
        return ticket2car.values();
    }

    Collection<Ticket> getAvailableTickets() {
        return availableTickets.values();
    }

    private void waitAllFutures() {
        for (Future future : futures) {
            try {
                future.get();
            } catch (Exception e) {
                throw new IllegalStateException("Waiting for a future result failed", e);
            }
        }
        futures.clear();
    }
}
