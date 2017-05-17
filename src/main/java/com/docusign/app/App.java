package com.docusign.app;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        try {
            Factory factory = Factory.getInstance();

            System.out.println("Enter temperature: ");
            Scanner sc = new Scanner(System.in);
            Temperature temperature = Temperature.fromString(sc.nextLine());

            System.out.println("Enter command number: ");
            while (sc.hasNext()) {
                int commandNumber = sc.nextInt();
                String result = factory.getCommandsExecutor().execute(commandNumber, temperature);
                System.out.println(result);

                if (commandNumber == factory.getCommandsExecutor().getFinalCommandNumber()) {
                    return;
                }
                System.out.println("Enter command number: ");
            }


        } catch (Exception e) {
            System.out.print("fail");
        }
    }
}
