package com.blstream.kaczynska.longopsbackgroundapp;


public class OperationOption {

    private int id;
    private long durationTime;
    private String timeOption;
    private static int idValue = 0;

    public OperationOption() {
    }

    public OperationOption(long durationTime) {
        idValue++;
        id = idValue;
        this.durationTime = durationTime;
        timeOption = durationTime + " sekund";
    }

    public OperationOption(OperationOption option) {
        this.id = option.id;
        this.durationTime = option.durationTime;
        timeOption = option.durationTime + " sekund";
    }

    public int getId() {
        return id;
    }

    public long getDurationTime() {
        return durationTime;
    }

    protected void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return timeOption;
    }

    public void setDurationTime(long durationTime) {
        this.durationTime = durationTime;
    }

    public void setTimeOption(String timeOption) {
        this.timeOption = timeOption;
    }
}
