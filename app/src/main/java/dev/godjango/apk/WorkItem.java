package dev.godjango.apk;

import android.os.Bundle;

public class WorkItem {
    private final String ProgressText;
    private final int button;
    private String date;
    private int iconResId;
    private int progress;
    private String category;
    private String title;
    private final Bundle cardData;

    public WorkItem(int button, String progressText, String date, String title, String category, int progress, int iconResId,Bundle cardData) {
        this.button = button;
        this.ProgressText = progressText;
        this.date = date;
        this.title = title;
        this.category = category;
        this.progress = progress;
        this.iconResId = iconResId;
        this.cardData = cardData;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String subtitle) {
        this.category = subtitle;
    }

    public int getProgress() {
        return this.progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getIconResId() {
        return this.iconResId;
    }

    public void setIconResId(int iconResId) {
        this.iconResId = iconResId;
    }

    public String getProgressText() {
        return this.ProgressText;
    }

    public int getButton() {
        return this.button;
    }

    public Bundle getCardData() {
        return cardData;
    }

}
