package com.example.expeditionsupportapp;

// Class to create the object for inserting the data into the firebase database.
public class CurrentExpeditionData {

    private String source, destination;

    public CurrentExpeditionData()
    {

    }

    public CurrentExpeditionData(String source, String destination) {
        this.source = source;
        this.destination = destination;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}
