package com.KeyValueStore.main;

import java.util.HashMap;


public class Transaction {
    HashMap<String, Entry> operations;

    public Transaction(HashMap<String, Entry> operations) {
        this.operations = operations;
    }

    public Transaction() {
    }

    public HashMap<String, Entry> getOperations() {
        return new HashMap<>(operations);
    }

    public void addItem(String key, Entry value) {
        operations.put(key, value);
    }
}
