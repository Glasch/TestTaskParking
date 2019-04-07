/**
 * Copyright (c) Anton on 05.04.2019.
 */
class Ticket {
    private volatile static int id = 0;
    private int number;

    Ticket() {
        number = id++;
    }

    int getNumber() {
        return number;
    }
}
