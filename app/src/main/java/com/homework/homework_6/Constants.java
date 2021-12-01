package com.homework.homework_6;

import android.os.Parcel;
import android.os.Parcelable;

public class Constants implements Parcelable {
    String header;
    String description;

    protected Constants(Parcel in) {
        header = in.readString();
        description = in.readString();
    }

    public static final Creator<Constants> CREATOR = new Creator<Constants>() {
        @Override
        public Constants createFromParcel(Parcel in) {
            return new Constants(in);
        }

        @Override
        public Constants[] newArray(int size) {
            return new Constants[size];
        }
    };

    public Constants() {

    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(header);
        parcel.writeString(description);
    }
}
