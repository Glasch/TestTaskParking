/**
 * Copyright (c) Anton on 05.04.2019.
 */
public class Car{
    private volatile static int id = 100;
    private String number;
    private Ticket parkingTicket;

    Car() {
        number = "XX" + id++ + "X99";
    }

    Ticket getParkingTicket() {
        return parkingTicket;
    }

    void setParkingTicket(Ticket parkingTicket) {
        this.parkingTicket = parkingTicket;
    }

    @Override
    public String toString() {
        return "car number " + this.number + " carTicket: " + this.getParkingTicket().getNumber();
    }
}
