package parking;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Copyright (c) Anton on 05.04.2019.
 */
public class Car{
    private static AtomicInteger nextNumber = new AtomicInteger(100);
    private String number;
    private Ticket parkingTicket;

    Car() {
        number = "XX" + nextNumber.getAndIncrement() + "X99";
    }

    Ticket getParkingTicket() {
        return parkingTicket;
    }

    void setParkingTicket(Ticket parkingTicket) {
        this.parkingTicket = parkingTicket;
    }

    @Override
    public String toString() {
        return "parking.Car number " + this.number + ", ticket: " + this.getParkingTicket().getNumber();
    }
}
