package com.docusign.app;

import java.util.HashMap;

public abstract class Actionable {

    public Actionable[] getDependsOn() {
        return dependsOn;
    }

    public void setDependsOn(Actionable[] dependsOn) {
        this.dependsOn = dependsOn;
    }

    public String action(Temperature temperature) throws Exception {
        if (actionTaken || !type.containsKey(temperature)) {
            throw new Exception("Fail");
        }

        for (Actionable d : dependsOn) {
            if (d.type.containsKey(temperature) && !d.actionTaken) {
                throw new Exception("Fail");
            }
        }

        actionTaken = true;
        return type.get(temperature);
    }

    protected HashMap<Temperature, String> type;

    protected Actionable() {
        this.actionTaken = false;
        this.type = new HashMap<Temperature, String>();
        this.dependsOn = new Actionable[]{};
    }

    private Actionable[] dependsOn;

    private boolean actionTaken;
}
