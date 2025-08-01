package Model;

public class Session {
    private static Volunteer loggedInVolunteer;
    private static Organization loggedInOrganization;

    public static void setLoggedInVolunteer(Volunteer volunteer) {
        loggedInVolunteer = volunteer;
    }

    public static Volunteer getLoggedInVolunteer() {
        return loggedInVolunteer;
    }

    public static int getLoggedInVolunteerId() {
        return loggedInVolunteer != null ? loggedInVolunteer.getId() : -1;
    }

    public static void setLoggedInOrganization(Organization organization) {
        loggedInOrganization = organization;
    }

    public static Organization getLoggedInOrganization() {
        return loggedInOrganization;
    }

    public static int getLoggedInOrganizationId() {
        return loggedInOrganization != null ? loggedInOrganization.getOrgID() : -1;
    }
}
