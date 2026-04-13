package dk.easv.easvticketsystem.BLL;

import dk.easv.easvticketsystem.DAL.EventDAO;
import dk.easv.easvticketsystem.model.Event;

import java.util.List;

public class EventManager {

    private EventDAO eventDAO;

    public EventManager() throws Exception {
        eventDAO = new EventDAO();
    }

    // GET ALL EVENTS
    public List<Event> getAllEvents() throws Exception {
        return eventDAO.getAllEvents();
    }

    // CREATE EVENT
    public int createEvent(Event event) throws Exception {
        if (event.getTitle() == null || event.getTitle().isEmpty())
            throw new Exception("Title required");
        if (event.getLocation() == null || event.getLocation().isEmpty())
            throw new Exception("Location required");
        if (event.getNotes() == null)
            event.setNotes("");
        if (event.getMaxCapacity() <= 0)
            throw new Exception("Capacity must be > 0");
        return eventDAO.createEvent(event);
    }

    // UPDATE
    public void updateEvent(Event event) throws Exception {

        if(event.getMaxCapacity() <= 0)
            throw new Exception("Invalid capacity");

        eventDAO.updateEvent(event);
    }

    // DELETE
    public void deleteEvent(int eventId) throws Exception {
        eventDAO.deleteEvent(eventId);
    }

    // GET SOLD TICKETS
    public int getTicketCount(int eventId) throws Exception {
        return eventDAO.getTicketCountForEvent(eventId);
    }
}
