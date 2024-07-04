package dev.godjango.apk;

public class WorkItem {
    private final String ProgressText;
    private final int button;
    private String date;
    private int iconResId;
    private int progress;
    private String subtitle;
    private String title;

    public WorkItem(int button, String progressText, String date, String title, String subtitle, int progress, int iconResId) {
        this.button = button;
        this.ProgressText = progressText;
        this.date = date;
        this.title = title;
        this.subtitle = subtitle;
        this.progress = progress;
        this.iconResId = iconResId;
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

    public String getSubtitle() {
        return this.subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
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
}
