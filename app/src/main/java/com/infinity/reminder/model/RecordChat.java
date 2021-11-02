package com.infinity.reminder.model;

public class RecordChat {
    private String message;
    private String urlAudio;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUrlAudio() {
        return urlAudio;
    }

    public void setUrlAudio(String urlAudio) {
        this.urlAudio = urlAudio;
    }

    public RecordChat(String message, String urlAudio) {
        this.message = message;
        this.urlAudio = urlAudio;
    }
}
