package Model;

public class VolunteerParticipation {
    int volunteerID, scheduleID;
    String confirmationStatus, role;

    public VolunteerParticipation(int volunteerID, int scheduleID, String confirmationStatus, String role) {
        this.volunteerID = volunteerID;
        this.scheduleID = scheduleID;
        this.confirmationStatus = confirmationStatus;
        this.role = role;
    }

    public VolunteerParticipation() {}

    public int getVolunteerID() {
        return volunteerID;
    }

    public int getScheduleID() {
        return scheduleID;
    }

    public String getConfirmationStatus() {
        return confirmationStatus;
    }

    public String getRole() {
        return role;
    }
}