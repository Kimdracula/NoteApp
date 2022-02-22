package com.my.notes.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.firestore.Exclude;

import java.util.Date;

public class CardData implements Parcelable{
    private  String header;
    private String description;
    private int picture;
    private Date date;
    @Exclude
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public CardData(String header, String description, int picture, Date date) {
        this.header = header;
        this.description = description;
        this.picture = picture;
        this.date = date;
    }

    protected CardData(Parcel in) {
        header = in.readString();
        description = in.readString();
        picture = in.readInt();
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

    public CardData() {
    }

    public String getHeader() {
        return header;
    }

    public String getDescription() {
        return description;
    }

    public int getPicture() {
        return picture;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(header);
        parcel.writeString(description);
        parcel.writeInt(picture);
    }
}
