package dk.easv.easvticketsystem.model;

import java.time.LocalDateTime;

public class Event {

    private int eventId;
    private String title;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String location;
    private String notes;
    private String locationGuidance;
    private int maxCapacity;

    public Event(int eventId, String title, LocalDateTime startDateTime, LocalDateTime endDateTime, String location, String notes, String locationGuidance, int maxCapacity) {

        this.eventId = eventId;
        this.title = title;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.location = location;
        this.notes = notes;
        this.locationGuidance = locationGuidance;
        this.maxCapacity = maxCapacity;
    }

    // Getters

    public int getEventId() {return eventId;}
    public String getTitle() {return title;}
    public LocalDateTime getStartDateTime() {return startDateTime;}
    public LocalDateTime getEndDateTime() {return endDateTime;}
    public String getLocation() {return location;}
    public String getNotes() {return notes;}
    public String getLocationGuidance() {return locationGuidance;}
    public int getMaxCapacity() {return maxCapacity;}

    // Setters for editing

    public void setNotes(String notes) {this.notes = notes;}
    public void setTitle(String title) {this.title = title;}
    public void setStartDateTime(LocalDateTime startDateTime) {this.startDateTime = startDateTime;}
    public void setLocation(String location) {this.location = location;}
    public void setMaxCapacity(int maxCapacity) {this.maxCapacity = maxCapacity;}
    public void setEventId(int id) {this.eventId = id;}
    public void setLocationGuidance(String guidance) {this.locationGuidance = guidance;}
    public void setEndDateTime(LocalDateTime endDateTime) {this.endDateTime = endDateTime;}

    @Override
    public String toString() {
        return title;
    }
}
