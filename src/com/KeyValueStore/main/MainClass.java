package com.KeyValueStore.main;

public class MainClass {

    public static void main(String[] args) {
        CommandProcessor commandProcessor = new ProcessCommandsImpl();
        commandProcessor.processCommand("SET X 10");
        commandProcessor.processCommand("GET X");
    }
}
