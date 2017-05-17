package com.docusign.app;

import org.junit.Test;
import java.util.Arrays;
import java.util.Hashtable;

public class StateMachineTests {

    class ActionableInstance1 extends Actionable {
        ActionableInstance1() {
            super();
            type.put(Temperature.HOT, "hot1");
            type.put(Temperature.COLD, "cold1");
        }
    }

    class ActionableInstance2 extends Actionable {
        ActionableInstance2() {
            super();
            type.put(Temperature.HOT, "hot2");
            type.put(Temperature.COLD, "cold2");
        }
    }

    class ActionableInstance3 extends Actionable {
        ActionableInstance3() {
            super();
            type.put(Temperature.HOT, "hot3");
            type.put(Temperature.COLD, "cold3");
        }
    }

    class ActionableInstance4 extends Actionable {
        ActionableInstance4() {
            super();
            type.put(Temperature.HOT, "hot4");
        }
    }

    @Test
    public void actionableTests() throws Exception {
        // Temperature not defined for the actionable should throw
        try {
            new ActionableInstance4().action(Temperature.COLD);
            assert (false);
        } catch (Exception e) {
            assert (e.getMessage().equals("Fail"));
        }

        assert (new ActionableInstance1().action(Temperature.HOT).equals("hot1"));
        assert (new ActionableInstance1().action(Temperature.COLD).equals("cold1"));

        assert (new ActionableInstance2().action(Temperature.HOT).equals("hot2"));
        assert (new ActionableInstance3().action(Temperature.COLD).equals("cold3"));

        assert (new ActionableInstance3().action(Temperature.HOT).equals("hot3"));
        assert (new ActionableInstance3().action(Temperature.COLD).equals("cold3"));

        Actionable testInstance = new ActionableInstance1();
        assert (testInstance.action(Temperature.HOT).equals("hot1"));
        // Test redoing action should result in an exception
        try {
            testInstance.action(Temperature.HOT);
            assert (false);
        } catch (Exception e) {
            assert (e.getMessage().equals("Fail"));
        }

        // Action dependency cases
        Actionable instance1 = new ActionableInstance1();
        Actionable instance2 = new ActionableInstance2();
        Actionable instance3 = new ActionableInstance3();
        Actionable instance4 = new ActionableInstance4();

        instance1.setDependsOn(new Actionable[]{instance2, instance3});
        instance2.setDependsOn(new Actionable[]{instance3, instance4});

        try {
            instance1.action(Temperature.HOT);
            assert (false);
        } catch (Exception e) {
            assert (e.getMessage().equals("Fail"));
        }

        assert (instance3.action(Temperature.COLD).equals("cold3"));
        assert (instance2.action(Temperature.COLD).equals("cold2"));
        assert (instance1.action(Temperature.COLD).equals("cold1"));
    }

    @Test
    public void commandExecutorTests() throws Exception {

        Actionable instance1 = new ActionableInstance1();
        Actionable instance2 = new ActionableInstance2();

        Hashtable<Integer, Actionable> commands = new Hashtable<Integer, Actionable>();
        commands.put(1, instance1);
        commands.put(2, instance2);
        CommandsExecutor commandsExecutor = new CommandsExecutor(commands, 2);
        assert (commandsExecutor.getFinalCommandNumber() == 2);
        assert (commandsExecutor.execute(1, Temperature.HOT).equals("hot1"));
        assert (commandsExecutor.execute(2, Temperature.COLD).equals("cold2"));

        try {
            commandsExecutor.execute(3, Temperature.COLD);
            assert (false);
        } catch (NullPointerException e) {
        }
    }

    @Test
    public void factoryTests() throws Exception {
        Factory factory = Factory.getInstance();
        assert (factory != null);

        //tests singleton pattern
        assert (factory == Factory.getInstance());

        assert (factory.getFootwear() instanceof Footwear);
        assert (factory.getHeadwear() instanceof Headwear);
        assert (factory.getHouse() instanceof House);
        assert (factory.getJacket() instanceof Jacket);
        assert (factory.getPajamas() instanceof Pajamas);
        assert (factory.getPants() instanceof Pants);
        assert (factory.getShirt() instanceof Shirt);
        assert (factory.getSocks() instanceof Socks);

        // check dependencies
        Arrays.deepEquals(factory.getFootwear().getDependsOn(), new Actionable[]{factory.getPajamas(), factory.getPants(), factory.getPants()});
        Arrays.deepEquals(factory.getHeadwear().getDependsOn(), new Actionable[]{factory.getPajamas(), factory.getShirt()});
        Arrays.deepEquals(factory.getHouse().getDependsOn(), new Actionable[]{factory.getPajamas(), factory.getPants(), factory.getShirt(), factory.getSocks(), factory.getHeadwear(), factory.getFootwear()});
        Arrays.deepEquals(factory.getJacket().getDependsOn(), new Actionable[]{factory.getPajamas(), factory.getShirt()});
        Arrays.deepEquals(factory.getPajamas().getDependsOn(), new Actionable[]{});
        Arrays.deepEquals(factory.getPants().getDependsOn(), new Actionable[]{factory.getPajamas()});
        Arrays.deepEquals(factory.getShirt().getDependsOn(), new Actionable[]{factory.getPajamas()});
        Arrays.deepEquals(factory.getSocks().getDependsOn(), new Actionable[]{factory.getPajamas()});

        // checek commands
        assert (factory.getCommandsExecutor() != null);
        assert (factory.getCommandsExecutor().getFinalCommandNumber() == 7);
        assert (factory.getCommandsExecutor().getCommands().size() == 8);

        assert (factory.getCommandsExecutor().getCommands().get(1) == factory.getFootwear());
        assert (factory.getCommandsExecutor().getCommands().get(2) == factory.getHeadwear());
        assert (factory.getCommandsExecutor().getCommands().get(3) == factory.getSocks());
        assert (factory.getCommandsExecutor().getCommands().get(4) == factory.getShirt());
        assert (factory.getCommandsExecutor().getCommands().get(5) == factory.getJacket());
        assert (factory.getCommandsExecutor().getCommands().get(6) == factory.getPants());
        assert (factory.getCommandsExecutor().getCommands().get(7) == factory.getHouse());
        assert (factory.getCommandsExecutor().getCommands().get(8) == factory.getPajamas());
    }

    @Test
    public void actionablItemsTests() throws Exception {
        Footwear footwear = new Footwear();
        assert (footwear.type.size() == 2);
        assert (footwear.type.get(Temperature.HOT).equals("sandals"));
        assert (footwear.type.get(Temperature.COLD).equals("boots"));

        Headwear headwear = new Headwear();
        assert (headwear.type.size() == 2);
        assert (headwear.type.get(Temperature.HOT).equals("sun visor"));
        assert (headwear.type.get(Temperature.COLD).equals("hat"));

        House house = new House();
        assert (house.type.size() == 2);
        assert (house.type.get(Temperature.HOT).equals("leaving house"));
        assert (house.type.get(Temperature.COLD).equals("leaving house"));

        Jacket jacket = new Jacket();
        assert (jacket.type.size() == 1);
        assert (jacket.type.get(Temperature.COLD).equals("jacket"));

        Pajamas pajamas = new Pajamas();
        assert (pajamas.type.size() == 2);
        assert (pajamas.type.get(Temperature.HOT).equals("Removing PJs"));
        assert (pajamas.type.get(Temperature.COLD).equals("Removing PJs"));

        Pants pants = new Pants();
        assert (pants.type.size() == 2);
        assert (pants.type.get(Temperature.HOT).equals("shorts"));
        assert (pants.type.get(Temperature.COLD).equals("pants"));


        Shirt shirt = new Shirt();
        assert (shirt.type.size() == 2);
        assert (shirt.type.get(Temperature.HOT).equals("t-shirt"));
        assert (shirt.type.get(Temperature.COLD).equals("shirt"));


        Socks socks = new Socks();
        assert (socks.type.size() == 1);
        assert (socks.type.get(Temperature.COLD).equals("socks"));
    }
}