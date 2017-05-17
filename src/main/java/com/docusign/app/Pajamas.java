package com.docusign.app;

public class Pajamas extends Actionable {
    public Pajamas() {
        super();
        type.put(Temperature.HOT, "Removing PJs");
        type.put(Temperature.COLD, "Removing PJs");
    }
}
