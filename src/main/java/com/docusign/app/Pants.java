package com.docusign.app;

public class Pants extends Actionable {
    public Pants() {
        super();
        type.put(Temperature.HOT, "shorts");
        type.put(Temperature.COLD, "pants");
    }
}
