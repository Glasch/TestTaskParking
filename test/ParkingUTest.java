import junit.framework.TestCase;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.InputStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

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
            Parking parking = new Parking(in);
        } catch (NoSuchElementException e) {
            assert(true);
        }
    }

    public void testParkTicketsBiggerThanCars() throws InterruptedException {
        ByteArrayInputStream in = new ByteArrayInputStream("10".getBytes());
        Parking parking = new Parking(in);
        parking.park(6);
        assertEquals(4,parking.getAvailableTickets().size());
        assertEquals(6,parking.getCars().size());
    }

    public void testParkTicketsEqualsThanCars() throws InterruptedException {
        ByteArrayInputStream in = new ByteArrayInputStream("6".getBytes());
        Parking parking = new Parking(in);
        parking.park(6);
        assertEquals(0,parking.getAvailableTickets().size());
        assertEquals(6,parking.getCars().size());
    }

    public void testParkTicketsSmallerThanCars() throws InterruptedException {
        ByteArrayInputStream in = new ByteArrayInputStream("4".getBytes());
        Parking parking = new Parking(in);
        parking.park(6);
        assertEquals(0,parking.getAvailableTickets().size());
        assertEquals(4,parking.getCars().size());
    }
}
