package com.docusign.app;

public class House extends Actionable {
    public House() {
        super();
        type.put(Temperature.HOT, "leaving house");
        type.put(Temperature.COLD, "leaving house");
    }
}
