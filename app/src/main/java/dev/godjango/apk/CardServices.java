package dev.godjango.apk;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class CardServices implements Parcelable {
    private final String category;
    private final int image;
    private final String price;
    private final String time;
    private final String title;
    private boolean isPopular;

    public CardServices(int image, String title, String category, String price, String time, boolean isPopular) {
        this.image = image;
        this.title = title;
        this.category = category;
        this.price = price;
        this.time = time;
        this.isPopular = isPopular;
    }

    protected CardServices(Parcel in) {
        category = in.readString();
        image = in.readInt();
        price = in.readString();
        time = in.readString();
        title = in.readString();
    }

    public static final Creator<CardServices> CREATOR = new Creator<CardServices>() {
        @Override
        public CardServices createFromParcel(Parcel in) {
            return new CardServices(in);
        }

        @Override
        public CardServices[] newArray(int size) {
            return new CardServices[size];
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
