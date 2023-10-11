package com.auction.flab.application.replication;

import java.util.List;

public class CircularList<T> {

    private final List<T> list;
    private int counter = 0;

    public CircularList(List<T> list) {
        this.list = list;
    }

    public T getOne() {
        if (counter + 1 >= list.size()) {
            counter = -1;
        }
        return list.get(++counter);
    }

}
