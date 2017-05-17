package com.docusign.app;

public class Shirt extends Actionable {
    public Shirt() {
        super();
        type.put(Temperature.HOT, "t-shirt");
        type.put(Temperature.COLD, "shirt");
    }
}
