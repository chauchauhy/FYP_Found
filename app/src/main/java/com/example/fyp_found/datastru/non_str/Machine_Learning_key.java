package com.example.fyp_found.datastru.non_str;

public class Machine_Learning_key {
    String id;
    String title;
    float confidence;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getConfidence() {
        return confidence;
    }

    public void setConfidence(float confidence) {
        this.confidence = confidence;
    }

    public Machine_Learning_key() {
    }

    public Machine_Learning_key(String id, String title, float confidence) {
        this.id = id;
        this.title = title;
        this.confidence = confidence;
    }
}
