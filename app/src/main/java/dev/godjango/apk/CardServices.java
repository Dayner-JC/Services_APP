package dev.godjango.apk;

public class CardServices {
    private final String category;
    private final int image;
    private final String price;
    private final String time;
    private final String title;

    public CardServices(int image, String title, String category, String price, String time) {
        this.image = image;
        this.title = title;
        this.category = category;
        this.price = price;
        this.time = time;
    }

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
}
