package parking;

import junit.framework.TestCase;

import java.io.ByteArrayInputStream;
import java.util.NoSuchElementException;

/**
 * Copyright (c) Anton on 05.04.2019.
 */
public class ParkingUTest extends TestCase {

    public void testParkingInitCorrectInput() {
        ByteArrayInputStream in = new ByteArrayInputStream("5".getBytes());
        Parking parking = new Parking(in);
        System.out.print(parking.getAvailableTickets().size());
        assertEquals(5,parking.getAvailableTickets().size());
    }

    public void testParkingInitInvalidInput() {
        ByteArrayInputStream in = new ByteArrayInputStream(" ".getBytes());
        try {
            new Parking(in);
        } catch (NoSuchElementException e) {
            return;
        }
        throw new IllegalStateException();
    }

    public void testParkTicketsBiggerThanCars() {
        ByteArrayInputStream in = new ByteArrayInputStream("10".getBytes());
        Parking parking = new Parking(in);
        parking.park(6);
        assertEquals(4,parking.getAvailableTickets().size());
        assertEquals(6,parking.getCars().size());
    }

    public void testParkTicketsEqualsThanCars() {
        ByteArrayInputStream in = new ByteArrayInputStream("6".getBytes());
        Parking parking = new Parking(in);
        parking.park(6);
        parking.list();
        assertEquals(0,parking.getAvailableTickets().size());
        assertEquals(6,parking.getCars().size());
    }

    public void testParkTicketsSmallerThanCars() {
        ByteArrayInputStream in = new ByteArrayInputStream("4".getBytes());
        Parking parking = new Parking(in);
        parking.park(6);
        assertEquals(0,parking.getAvailableTickets().size());
        assertEquals(4,parking.getCars().size());
    }
}
