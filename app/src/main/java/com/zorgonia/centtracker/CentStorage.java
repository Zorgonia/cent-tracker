package com.zorgonia.centtracker;

import java.io.Serializable;
import java.util.ArrayList;

public class CentStorage implements Serializable {

    public void increment(int position) {
        int temp = cents.get(position);
        this.cents.remove(position);
        cents.add(position, temp + 1);
    }

    public boolean decrement(int position) {
        int temp = cents.get(position);
        if (temp == 0) {
            return false;
        }
        this.cents.remove(position);
        cents.add(position, temp - 1);
        return true;
    }

    public Integer getElementAt(int position) {
        return cents.get(position);
    }

    ArrayList<Integer> cents = new ArrayList<>();

    CentStorage() {
        for (int i = 0; i != 5; i++) {
            cents.add(0);
        }
    }

    public Integer totalCents() {
        return cents.get(0) * -2 + cents.get(1) * -1 + cents.get(3) + cents.get(4) * 2;
    }
}
