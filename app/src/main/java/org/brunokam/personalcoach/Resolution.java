package org.brunokam.personalcoach;

public class Resolution {
    private String title;
    private String description;
    private int difficulty;
    private int interval;

    public Resolution(String _title, String _description, int _difficulty, int _interval) {
        this.title = _title;
        this.description = _description;
        this.difficulty = _difficulty;
        this.interval = _interval;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }
}
