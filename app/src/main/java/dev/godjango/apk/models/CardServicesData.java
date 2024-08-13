package dev.godjango.apk.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class CardServicesData implements Parcelable {
    private final String category;
    private final int image;
    private final String price;
    private final String time;
    private final String title;
    private boolean isPopular;

    public CardServicesData(int image, String title, String category, String price, String time, boolean isPopular) {
        this.image = image;
        this.title = title;
        this.category = category;
        this.price = price;
        this.time = time;
        this.isPopular = isPopular;
    }

    protected CardServicesData(Parcel in) {
        category = in.readString();
        image = in.readInt();
        price = in.readString();
        time = in.readString();
        title = in.readString();
    }

    public static final Creator<CardServicesData> CREATOR = new Creator<CardServicesData>() {
        @Override
        public CardServicesData createFromParcel(Parcel in) {
            return new CardServicesData(in);
        }

        @Override
        public CardServicesData[] newArray(int size) {
            return new CardServicesData[size];
        }
    };

    public int getImage() {
        return this.image;
    }

    public String getTitle() {
        return this.title;
    }

    public String getCategory() {
        return this.category;
    }

    public String getPrice() {
        return this.price;
    }

    public String getTime() {
        return this.time;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(category);
        parcel.writeInt(image);
        parcel.writeString(price);
        parcel.writeString(time);
        parcel.writeString(title);
    }

    public boolean getEsPopular() {
        return this.isPopular;
    }
}
