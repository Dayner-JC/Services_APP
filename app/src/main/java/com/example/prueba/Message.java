package com.example.prueba;

public class Message {
    private boolean isFecha;
    private final boolean isOutgoing;
    private boolean isSelected;
    private final String message;
    private final Message repliedMessage;
    private final String senderName;
    private long timestamp;

    public Message(String message, boolean isOutgoing) {
        this(message, isOutgoing, null, null, false);
    }

    public Message(String message, boolean isOutgoing, Message repliedMessage, String senderName) {
        this(message, isOutgoing, repliedMessage, senderName, false);
    }

    public Message(String message, boolean isOutgoing, long timestamp) {
        this(message, isOutgoing, null, null, true);
        this.timestamp = timestamp;
    }

    public Message(String message, boolean isOutgoing, Message repliedMessage, String senderName, boolean isFecha) {
        this.message = message;
        this.isOutgoing = isOutgoing;
        this.repliedMessage = repliedMessage;
        this.senderName = senderName;
        this.timestamp = System.currentTimeMillis();
        this.isFecha = isFecha;
    }

    public boolean isFecha() {
        return this.isFecha;
    }

    public void setFecha(boolean fecha) {
        this.isFecha = fecha;
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

    public Message getRepliedMessage() {
        return this.repliedMessage;
    }

    public boolean isReply() {
        return this.repliedMessage != null;
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
