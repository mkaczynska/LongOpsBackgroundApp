package com.blstream.kaczynska.longopsbackgroundapp;

public class Operation extends OperationOption {
    private static int idValue = 0;
    public Operation(OperationOption operation) {
        super(operation);
        idValue++;
        setId(idValue);
    }

//    private final int id;
//    private long durationTime;
//    private String timeOption;
//    private static int idValue = 0;
//
//
//    public Operation(long durationTime) {
//        idValue++;
//        id = idValue;
//        this.durationTime = durationTime;
//        timeOption = durationTime + " sekund";
//    }
//
//    public int getId() {
//        return id;
//    }
//
//    public long getDurationTime() {
//        return durationTime;
//    }
//
//    @Override
//    public String toString() {
//        return timeOption;
//    }
}

