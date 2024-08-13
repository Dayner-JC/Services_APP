package dev.godjango.apk.models;

public class MessageData {
    private boolean isDate;
    private final boolean isOutgoing;
    private boolean isSelected;
    private final String message;
    private final MessageData repliedMessageData;
    private final String senderName;
    private long timestamp;

    public MessageData(String message, boolean isOutgoing) {
        this(message, isOutgoing, null, null, false);
    }

    public MessageData(String message, boolean isOutgoing, MessageData repliedMessageData, String senderName) {
        this(message, isOutgoing, repliedMessageData, senderName, false);
    }

    public MessageData(String message, boolean isOutgoing, long timestamp) {
        this(message, isOutgoing, null, null, true);
        this.timestamp = timestamp;
    }

    public MessageData(String message, boolean isOutgoing, MessageData repliedMessageData, String senderName, boolean isDate) {
        this.message = message;
        this.isOutgoing = isOutgoing;
        this.repliedMessageData = repliedMessageData;
        this.senderName = senderName;
        this.timestamp = System.currentTimeMillis();
        this.isDate = isDate;
    }

    public boolean isDate() {
        return this.isDate;
    }

    public void setDate(boolean date) {
        this.isDate = date;
    }

    public String getSenderName() {
        return this.senderName;
    }

    public String getMessage() {
        return this.message;
    }

    public boolean isOutgoing() {
        return this.isOutgoing;
    }

    public MessageData getRepliedMessage() {
        return this.repliedMessageData;
    }

    public boolean isReply() {
        return this.repliedMessageData != null;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public boolean isSelected() {
        return this.isSelected;
    }

    public void setSelected(boolean selected) {
        this.isSelected = selected;
    }
}
