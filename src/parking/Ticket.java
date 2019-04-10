package parking;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return number == ticket.number;
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }
}
