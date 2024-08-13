package dev.godjango.apk.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class CardReferencesData implements Parcelable {
    private final boolean esPopular;
    private int imageID;
    private String name;

    public CardReferencesData(String name, int imageID, boolean esPopular) {
        this.name = name;
        this.imageID = imageID;
        this.esPopular = esPopular;
    }

    protected CardReferencesData(Parcel in) {
        esPopular = in.readByte() != 0;
        imageID = in.readInt();
        name = in.readString();
    }

    public static final Creator<CardReferencesData> CREATOR = new Creator<CardReferencesData>() {
        @Override
        public CardReferencesData createFromParcel(Parcel in) {
            return new CardReferencesData(in);
        }

        @Override
        public CardReferencesData[] newArray(int size) {
            return new CardReferencesData[size];
        }
    };

    public String getName() {
        return this.name;
    }

    public boolean getEsPopular() {
        return this.esPopular;
    }

    public int getImage() {
        return this.imageID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeByte((byte) (esPopular ? 1 : 0));
        parcel.writeInt(imageID);
        parcel.writeString(name);
    }
}
