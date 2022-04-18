package com.KeyValueStore.main;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class ProcessCommandsImpl implements CommandProcessor {
    private final KeyValueStore keyValueStore;

    public ProcessCommandsImpl() {
        System.out.println("Inside ProcessCommandsImpl constructor");
        this.keyValueStore = new KeyValueStore();
    }

    /**
     *
     */
    @Override
    public void processCommand(String command) {
        String spaceSeparatedCommandRegex = " ";
        String[] commandArgs = command.split(spaceSeparatedCommandRegex);
        try {
            CommandLineCommands curCommand = CommandLineCommands.valueOf(commandArgs[0]);
            System.out.println("Value of the command: " + curCommand);
            switch (curCommand) {
                case GET -> System.out.println("Response: " + keyValueStore.get(commandArgs[1]));
                case SET ->
                        System.out.println("Response: " + keyValueStore.set(commandArgs[1], new Entry(Integer.valueOf(commandArgs[2]))));
                case BEGIN -> System.out.println("Response: " + keyValueStore.begin());
                case UNSET -> System.out.println("Response: " + keyValueStore.unSet(commandArgs[1]));
                case COMMIT -> System.out.println("Response: " + keyValueStore.commit());
            }
        } catch (Exception e) {
            System.out.println("Invalid Command");
        }
    }

    /**
     * @param commands
     */
    @Override
    public void bulkCommandProcessor(List<String> commands) {
        for (var command : commands) {
            processCommand(command);
        }
    }


}
