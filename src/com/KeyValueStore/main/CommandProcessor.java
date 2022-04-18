package com.KeyValueStore.main;

import java.util.List;

public interface CommandProcessor {
    void processCommand(String command);
    void bulkCommandProcessor(List<String> commands);
}
