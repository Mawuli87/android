package com.messieyawo.unityassembly.EvntManager;

public class EventObject {
    private String EventName;
    private String EventDate;
    private String EventDescription;
    private String Eventlocaton;
    private String EventType;


    public EventObject() {
    }

    public EventObject(String eventName, String eventDate, String eventDescription, String eventlocaton, String eventType) {
       EventName =eventName;
        EventDate = eventDate;
       EventDescription = eventDescription;
        Eventlocaton = eventlocaton;
        EventType = eventType;

    }

    public String getEventName() {
        return EventName;
    }

    public void setEventName(String eventName) {
        EventName = eventName;
    }

    public String getEventDate() {
        return EventDate;
    }

    public void setEventDate(String eventDate) {
        EventDate = eventDate;
    }

    public String getEventDescription() {
        return EventDescription;
    }

    public void setEventDescription(String eventDescription) {
        EventDescription = eventDescription;
    }

    public String getEventlocaton() {
        return Eventlocaton;
    }

    public void setEventlocaton(String eventlocaton) {
        Eventlocaton = eventlocaton;
    }

    public String getEventType() {
        return EventType;
    }

    public void setEventType(String eventType) {
        EventType = eventType;
    }
}
