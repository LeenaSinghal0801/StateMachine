package com.docusign.app;

public class Headwear extends Actionable {
    public Headwear() {
        super();
        type.put(Temperature.HOT, "sun visor");
        type.put(Temperature.COLD, "hat");
    }
}
