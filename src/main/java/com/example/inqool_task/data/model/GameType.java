package com.example.inqool_task.data.model;

public enum GameType {
    SINGLES_MATCH (1),
    DOUBLES_MATCH (1.5);

    private final double charge;

    GameType(double charge) {
        this.charge = charge;
    }

    public double getCharge() {
        return this.charge;
    }

}
