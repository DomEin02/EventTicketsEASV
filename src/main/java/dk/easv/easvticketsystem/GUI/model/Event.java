package dk.easv.easvticketsystem.GUI.model;

//Represents an Event
public class Event {
    private String title;
    private String date;
    private String time;
    private String location;
    private int maxTickets;

    public Event(String title, String date, String time, String location, int maxTickets) {
        this.title = title;
        this.date = date;
        this.time = time;
        this.location = location;
        this.maxTickets = maxTickets;
    }

    //Getters
    public String getTitle() {return title;}
    public String getDate() {return date;}
    public String getTime() {return time;}
    public String getLocation() {return location;}
    public int getMaxTickets() {return maxTickets;}
}
