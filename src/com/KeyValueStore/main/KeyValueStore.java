package com.KeyValueStore.main;


import java.util.HashMap;
import java.util.Stack;

public class KeyValueStore {
    private Stack<Transaction> transactions;
    private HashMap<String, Entry> globalMap, currentState;

    KeyValueStore() {
        transactions = new Stack<>();
        globalMap = new HashMap<>();
        resetCurrentTransaction();
    }

    private void resetCurrentTransaction() {
        currentState = new HashMap<>();
        //when resetting the current transaction state,
        //we also need to remove current Tx from the current context which
        //pop it out from the stack
        if (!transactions.isEmpty()) transactions.pop();
    }

    public int get(String key) {
        //query from global Map
        if (transactions.isEmpty()) {
            return globalMap.get(key).getValue();
        }
        //If active transaction is already running, then get the value
        //from the current context
        return getValueInInteger(transactions.peek(), key);
    }

    public int getValueInInteger(Transaction curTransaction, String key) {
        return curTransaction.operations.get(key).getValue();

    }

    public void set(String key, Entry value) {
        //when we are trying to set any key and there is no context available
        //in the runtime; then we set the key in the global Space
        if (transactions.isEmpty()) {
            globalMap.put(key, value);
            return;
        }

        //add this operation into the list of operations
        //which are maintained in the transaction
        var cur = transactions.peek();
        cur.addItem(key, value);
    }

    public void unSet(String key) {
    }


    public void commit() {
        if (transactions.isEmpty()) {
            throw new RuntimeException("There is no transaction available to commit");
        }
        globalMap = currentState;
        resetCurrentTransaction();

    }

    public void rollBack() {
        if (transactions.isEmpty()) return;
        //when rollBack operation is executed, we remove
        //all the operations of this transaction
        //from the current context
        transactions.pop();
    }

    public void begin() {
        //initiate a new Tx
        Transaction newTransaction = new Transaction();
        transactions.push(newTransaction);
    }

    private void checkAndIncrement(Entry entry, int value, int freqCount) {
        if (entry.getValue() == value) {
            ++freqCount;
        }
    }

    public int getNumberWithValue(int value) {
        int freqCount = 0;
        //If there is no active Tx then we get the count from globalMap
        if (transactions.isEmpty()) {
            for (var entry : globalMap.entrySet()) {
                checkAndIncrement(entry.getValue(), value, freqCount);
            }
        } else {
            for (var entry : currentState.entrySet()) {
                checkAndIncrement(entry.getValue(), value, freqCount);
            }
        }
        return freqCount;
    }
}
