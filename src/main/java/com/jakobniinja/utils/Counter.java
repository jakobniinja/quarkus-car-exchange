package com.jakobniinja.utils;

public class Counter {
    private int val;

    public Counter() {
        val = 0;
    }

    public void increment() {
        ++val;
    }

    public void decrement() {
        --val;
    }

    public int getVal() {
        return val;
    }
}
