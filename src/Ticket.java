/**
 * Copyright (c) Anton on 05.04.2019.
 */
public class Ticket {
    private volatile static int id = 0;
    private int number;

    public Ticket() {
        number = id++;
    }

    public int getNumber() {
        return number;
    }
}
