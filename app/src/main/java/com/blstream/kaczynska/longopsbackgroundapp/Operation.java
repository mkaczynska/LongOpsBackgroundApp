package com.blstream.kaczynska.longopsbackgroundapp;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.DecimalFormat;

public class Operation extends OperationOption implements Parcelable {
    public static final Creator<Operation> CREATOR = new Creator<Operation>() {
        @Override
        public Operation createFromParcel(Parcel in) {
            return new Operation(in);
        }

        @Override
        public Operation[] newArray(int size) {
            return new Operation[size];
        }
    };
    private static int idValue = 0;
    private double remainingTime;

    public Operation(OperationOption operation) {
        super(operation);
        setRemainingTime(getDurationTime());
        idValue++;
        setId(idValue);
        setDurationTime(getDurationTime() * 1000);
    }

    protected Operation(Parcel in) {
        super();
        setId(in.readInt());
        setDurationTime(in.readLong());
        setTimeOption(in.readString());
    }

    public double getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(double remainingTime) {
        this.remainingTime = remainingTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(getId());
        dest.writeLong(getDurationTime());
        dest.writeString(toString());
    }

    @Override
    public String toString() {
        DecimalFormat decimalFormat = new DecimalFormat("####0.00");
        return getId() + ". operacja. Pozostały czas: " + String.valueOf(decimalFormat.format(remainingTime / 1000)) + " sekund.";
    }
}

