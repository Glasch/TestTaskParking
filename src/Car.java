/**
 * Copyright (c) Anton on 05.04.2019.
 */
public class Car{
    private static int nextNumber = 100;
    private String number;
    private Ticket parkingTicket;

    Car() {
        number = "XX" + getNextId() + "X99";
    }

    Ticket getParkingTicket() {
        return parkingTicket;
    }

    void setParkingTicket(Ticket parkingTicket) {
        this.parkingTicket = parkingTicket;
    }

    private synchronized int getNextId() {
        return nextNumber++;
    }

    @Override
    public String toString() {
        return "car number " + this.number + " carTicket: " + this.getParkingTicket().getNumber();
    }
}
