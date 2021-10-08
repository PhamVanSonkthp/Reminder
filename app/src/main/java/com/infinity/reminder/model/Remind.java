package com.infinity.reminder.model;

public class Remind {
    private String title;
    private String time;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Remind(String title, String time) {
        this.title = title;
        this.time = time;
    }
}
