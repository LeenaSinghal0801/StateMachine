package com.docusign.app;

import java.util.Hashtable;

public class Factory {

    public static Factory getInstance() {
        if (instance == null) {
            instance = new Factory();
        }

        return instance;
    }

    public Actionable getFootwear() {
        return footwear;
    }

    public Actionable getHeadwear() {
        return headwear;
    }

    public Actionable getHouse() {
        return house;
    }

    public Actionable getJacket() {
        return jacket;
    }

    public Actionable getPajamas() {
        return pajamas;
    }

    public Actionable getPants() {
        return pants;
    }

    public Actionable getShirt() {
        return shirt;
    }

    public Actionable getSocks() {
        return socks;
    }

    public CommandsExecutor getCommandsExecutor() {
        return commandsExecutor;
    }

    private static Factory instance = null;


    private Actionable footwear;
    private Actionable headwear;
    private Actionable house;
    private Actionable jacket;
    private Actionable pajamas;
    private Actionable pants;
    private Actionable shirt;
    private Actionable socks;
    private CommandsExecutor commandsExecutor;

    private Factory() {
        footwear = new Footwear();
        headwear = new Headwear();
        house = new House();
        jacket = new Jacket();
        pajamas = new Pajamas();
        pants = new Pants();
        shirt = new Shirt();
        socks = new Socks();

        // set dependencies
        footwear.setDependsOn(new Actionable[]{pajamas, pants, socks});
        headwear.setDependsOn(new Actionable[]{pajamas, shirt});
        house.setDependsOn(new Actionable[]{pajamas, pants, jacket, shirt, socks, headwear, footwear});
        jacket.setDependsOn(new Actionable[]{pajamas, shirt});
        pajamas.setDependsOn(new Actionable[]{});
        pants.setDependsOn(new Actionable[]{pajamas});
        shirt.setDependsOn(new Actionable[]{pajamas});
        socks.setDependsOn(new Actionable[]{pajamas});

        // Set commands
        Hashtable<Integer, Actionable> commands = new Hashtable<Integer, Actionable>();
        commands.put(1, footwear);
        commands.put(2, headwear);
        commands.put(3, socks);
        commands.put(4, shirt);
        commands.put(5, jacket);
        commands.put(6, pants);
        commands.put(7, house);
        commands.put(8, pajamas);
        commandsExecutor = new CommandsExecutor(commands, 7);
    }
}
