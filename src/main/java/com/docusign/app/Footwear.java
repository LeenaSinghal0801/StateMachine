package com.docusign.app;

public class Footwear extends Actionable {
    public Footwear() {
        super();
        type.put(Temperature.HOT, "sandals");
        type.put(Temperature.COLD, "boots");
    }
}
