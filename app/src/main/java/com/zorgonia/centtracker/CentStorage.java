package com.zorgonia.centtracker;

import java.util.ArrayList;

public class CentStorage {

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

}
