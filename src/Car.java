/**
 * Copyright (c) Anton on 05.04.2019.
 */
public class Car {
    private static int id = 100;
    private String number;
    private Ticket parkingTicket;

    public Car() {
        number = "XX" + id++ + "X99";
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Ticket getParkingTicket() {
        return parkingTicket;
    }

    public void setParkingTicket(Ticket parkingTicket) {
        this.parkingTicket = parkingTicket;
    }

    @Override
    public String toString() {
        return "car number " + this.number + " carTicket: " + this.getParkingTicket().getNumber();
    }
}
