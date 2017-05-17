package com.docusign.app;

import java.util.Hashtable;

public class CommandsExecutor {
    public int getFinalCommandNumber() {
        return finalCommandNumber;
    }

    public CommandsExecutor(Hashtable<Integer, Actionable> commands, int finalCommandNumber) {
        this.commands = commands;
        this.finalCommandNumber = finalCommandNumber;
    }

    public String execute(int commandNumber, Temperature temperature) throws Exception {
        return commands.get(commandNumber).action(temperature);
    }

    public Hashtable<Integer, Actionable> getCommands() {
        return commands;
    }

    private Hashtable<Integer, Actionable> commands;
    private int finalCommandNumber;
}
