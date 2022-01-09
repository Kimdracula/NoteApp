package com.homework.homework_6.data;

import android.os.Parcel;
import android.os.Parcelable;

public class CardData implements Parcelable {
    public CardData(String header, String description) {
        this.header = header;
        this.description = description;
    }

    private String header;
   private String description;

    protected CardData(Parcel in) {
        header = in.readString();
        description = in.readString();
    }

    public static final Creator<CardData> CREATOR = new Creator<CardData>() {
        @Override
        public CardData createFromParcel(Parcel in) {
            return new CardData(in);
        }

        @Override
        public CardData[] newArray(int size) {
            return new CardData[size];
        }
    };

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
