import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

/**
 * Copyright (c) Anton on 05.04.2019.
 */
class Parking {
    private ArrayList <Ticket> availableTickets;
    private ArrayList <Car> cars = new ArrayList <>();

    public Parking() {
        initAvailableTickets(System.in);
    }

    public Parking(InputStream in) {
        initAvailableTickets(in);
    }

    public void park(int numberOfCars) {
        Iterator <Ticket> iter = availableTickets.iterator();
        while (iter.hasNext()) {
            if (numberOfCars == 0) return;
            Ticket ticket = iter.next();
            Car car = new Car();
            car.setParkingTicket(ticket);
            cars.add(car);
            iter.remove();
            numberOfCars--;
        }
    }

    public void unPark(int ticketNumber) {
        Iterator <Car> iter = cars.iterator();
        while (iter.hasNext()) {
            Car car = iter.next();
            if (car.getParkingTicket().getNumber() == ticketNumber) {
                availableTickets.add(car.getParkingTicket());
                iter.remove();
            }
        }
    }

    public void unPark(int[] ticketNumbers) {
        for (int i = 0; i < ticketNumbers.length - 1; i++) {
            unPark(ticketNumbers[i]);
        }
    }


    public void list() {
        for (Car car : cars) {
            System.out.println(car);
        }
    }

    public void count() {
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

    private String validateInput(Scanner keyboard) {
        String line = keyboard.nextLine();
        while (!line.matches("[1-9]*[0-9]")) {
            System.out.print("parking size must be int! Please, input correct value: ");
            line = keyboard.nextLine();
        }
        return line;
    }

    public ArrayList <Car> getCars() {
        return cars;
    }

    public ArrayList <Ticket> getAvailableTickets() {
        return availableTickets;
    }
}
