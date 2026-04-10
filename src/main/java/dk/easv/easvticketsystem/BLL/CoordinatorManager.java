package dk.easv.easvticketsystem.BLL;

import dk.easv.easvticketsystem.DAL.EventCoordinatorDAO;

public class CoordinatorManager {

    private EventCoordinatorDAO dao;

    public CoordinatorManager() {
        dao = new EventCoordinatorDAO();
    }

    public void assignCoordinator(int eventId, int userId)
            throws Exception {

        dao.assignCoordinator(eventId, userId);
    }

    public String getCoordinatorName(int eventId)
            throws Exception {

        return dao.getCoordinatorName(eventId);
    }
}
