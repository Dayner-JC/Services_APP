package com.example.prueba;

public class WorkItem {
    private final int iconResId;
    private final String progressText;
    private final String date;
    private final String title;
    private final String subtitle;
    private final int progress;
    private final int button;

    public WorkItem(int iconResId, String progressText, String date, String title, String subtitle, int progress, int button) {
        this.iconResId = iconResId;
        this.progressText = progressText;
        this.date = date;
        this.title = title;
        this.subtitle = subtitle;
        this.progress = progress;
        this.button = button;
    }

    public int getIconResId() {
        return iconResId;
    }

    public String getProgressText() {
        return progressText;
    }

    public String getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public int getProgress() {
        return progress;
    }

    public int getButton() {
        return button;
    }
}
