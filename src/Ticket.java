/**
 * Copyright (c) Anton on 05.04.2019.
 */
class Ticket {
    private static int nextNumber = 0;
    private int number;

    Ticket() {
        number = nextNumber++;
    }

    int getNumber() {
        return number;
    }
}
