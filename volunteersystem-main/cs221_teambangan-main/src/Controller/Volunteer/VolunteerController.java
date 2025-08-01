package Controller.Volunteer;

import Controller.DatabaseConnection;
import Model.Schedule;
import Model.Session;
import Model.Volunteer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class VolunteerController {
    public static void populateCommunityService(JTable table) {
        Volunteer volunteer = Session.getLoggedInVolunteer();

        // Check if a volunteer is logged in
        if (volunteer == null) {
            System.out.println("No volunteer is logged in.");
            return;
        }

        int volunteerId = volunteer.getId();

        // Query to fetch community services the volunteer has NOT registered for
        String query = """
        SELECT 
            cs.service_id, 
            cs.service_name, 
            cs.service_type, 
            cs.description 
        FROM community_service cs
        WHERE cs.service_id NOT IN (
            SELECT s.service_id 
            FROM schedule s
            JOIN volunteer_participation vp ON s.schedule_id = vp.schedule_id
            WHERE vp.volunteer_id = ?
        )
    """;

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Set the volunteer ID parameter
            stmt.setInt(1, volunteerId);

            ResultSet rs = stmt.executeQuery();

            // Get the table model
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0); // Clear the table before adding new data

            // Populate the table with the result set
            while (rs.next()) {
                int serviceId = rs.getInt("service_id");
                String serviceName = rs.getString("service_name");
                String serviceType = rs.getString("service_type");
                String description = rs.getString("description");

                // Add the data to the table model
                model.addRow(new Object[] {
                        serviceId,
                        serviceName,
                        serviceType,
                        description
                });
            }

            System.out.println("Available community services loaded successfully.");
            VolunteerRefreshTable.refreshCommunityServiceTable(table);
        } catch (SQLException e) {
            System.out.println("Error fetching community service data: " + e.getMessage());
        }
    }

    // Method to get the schedule for a selected community service
    public static Schedule getScheduleForService(String serviceId) {
        Schedule schedule = null;
        String query = "SELECT * FROM schedule WHERE service_id = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, Integer.parseInt(serviceId));  //Set the serviceId in the query

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                schedule = new Schedule(
                        rs.getInt("schedule_id"),
                        rs.getInt("service_id"),
                        rs.getInt("max_volunteers"),
                        rs.getDate("schedule_date"),
                        rs.getTime("start_time"),
                        rs.getTime("end_time"),
                        rs.getString("location")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return schedule;
    }

    public static void updateSchedulePanel(
            Schedule schedule,
            JLabel scheduleIdLabel,
            JLabel serviceIdLabel,
            JLabel dateLabel,
            JLabel maxLabel,
            JLabel startLabel,
            JLabel endLabel,
            JLabel locationLabel) {

        scheduleIdLabel.setText(String.valueOf(schedule.getScheduleId()));
        serviceIdLabel.setText(String.valueOf(schedule.getServiceId()));
        dateLabel.setText(String.valueOf(schedule.getScheduledDate()));
        maxLabel.setText(String.valueOf(schedule.getMaxVolunteers()));
        startLabel.setText(String.valueOf(schedule.getStartTime()));
        endLabel.setText(String.valueOf(schedule.getEndTime()));
        locationLabel.setText(schedule.getLocation());
    }

    public static boolean registerToCommunityService(int scheduleId, JTable table) {
        Volunteer volunteer = Session.getLoggedInVolunteer();

        if (volunteer == null) {
            System.out.println("Login as volunteer");
            return false;
        }

        int volunteerId = volunteer.getId();
        String query = "INSERT INTO volunteer_participation (volunteer_id, schedule_id, confirmation_status, role) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, volunteerId);
            stmt.setInt(2, scheduleId);
            stmt.setString(3, "Pending");
            stmt.setString(4, "Attendee");

            int rowsInserted = stmt.executeUpdate();
            VolunteerRefreshTable.refreshCommunityServiceTable(table);
            return rowsInserted > 0;

        } catch (SQLException e) {
            System.out.println("Register error: " + e.getMessage());
            return false;
        }
    }

    public static boolean updateVolunteerProfile(Volunteer volunteer) {
        String query = "UPDATE volunteer SET password = ?, first_name = ?, last_name = ?, phone_number = ? WHERE volunteer_id = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, volunteer.getPassword());
            stmt.setString(2, volunteer.getFirstName());
            stmt.setString(3, volunteer.getLastName());
            stmt.setString(4, volunteer.getPhoneNumber());
            stmt.setInt(5, volunteer.getId());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Error updating volunteer: " + e.getMessage());
            return false;
        }
    }

    public static void refreshVolunteerProfile(JPanel informationPanel) {
        Volunteer currentVolunteer = Session.getLoggedInVolunteer();

        // Update Labels inside the Volunteer Profile Information Panel
        JLabel volunteerIDLabel = (JLabel) informationPanel.getComponent(1);
        volunteerIDLabel.setText(String.valueOf(currentVolunteer.getId()));

        JLabel firstNameLabel = (JLabel) informationPanel.getComponent(3);
        firstNameLabel.setText(currentVolunteer.getFirstName());

        JLabel lastNameLabel = (JLabel) informationPanel.getComponent(5);
        lastNameLabel.setText(currentVolunteer.getLastName());

        JLabel emailLabel = (JLabel) informationPanel.getComponent(7);
        emailLabel.setText(currentVolunteer.getEmail());

        JLabel phoneLabel = (JLabel) informationPanel.getComponent(9);
        phoneLabel.setText(currentVolunteer.getPhoneNumber());

        JLabel statusLabel = (JLabel) informationPanel.getComponent(11);
        statusLabel.setText(currentVolunteer.getStatus());

        JLabel dateJoinedLabel = (JLabel) informationPanel.getComponent(13);
        dateJoinedLabel.setText(String.valueOf(currentVolunteer.getDateJoined()));

        informationPanel.revalidate();
        informationPanel.repaint();
    }

    // Method to get service type from community_service
    public static String getServiceType(int serviceId) {
        String query = "SELECT service_type FROM community_service WHERE service_id = ?";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, serviceId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString("service_type");
            }

        } catch (SQLException e) {
            System.out.println("Error fetching service type: " + e.getMessage());
        }

        return null;
    }

    // Method to get location from schedule
    public static String getLocation(int serviceId) {
        String query = "SELECT location FROM schedule WHERE service_id = ?";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, serviceId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString("location");
            }

        } catch (SQLException e) {
            System.out.println("Error fetching location: " + e.getMessage());
        }

        return null;
    }

    // Method to get role from volunteer_participation
    public static String getRole(int volunteerId, int serviceId) {
        String query = """
            SELECT role 
            FROM volunteer_participation vp
            JOIN schedule s ON vp.schedule_id = s.schedule_id
            WHERE vp.volunteer_id = ? AND s.service_id = ?
        """;

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, volunteerId);
            stmt.setInt(2, serviceId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString("role");
            }

        } catch (SQLException e) {
            System.out.println("Error fetching role: " + e.getMessage());
        }

        return "Unknown";
    }

    // Method to update the information panel
    public static void updateInformationPanel(int serviceId, JLabel serviceTypeLabel, JLabel locationLabel, JLabel roleLabel) {
        // Get the logged-in volunteer
        Volunteer volunteer = Session.getLoggedInVolunteer();

        if (volunteer == null) {
            System.out.println("No volunteer is logged in.");
            return;
        }

        // Fetch data from the database
        String serviceType = getServiceType(serviceId);
        String location = getLocation(serviceId);
        String role = getRole(volunteer.getId(), serviceId);

        // Update the labels
        serviceTypeLabel.setText(serviceType);
        locationLabel.setText(location);
        roleLabel.setText(role);
    }

    // Method to get service_id from community_service by service_name
    public static int getServiceIdByName(String serviceName) {
        String query = "SELECT service_id FROM community_service WHERE service_name = ?";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, serviceName);  // Set the service name parameter
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("service_id");  // Return the service ID
            }

        } catch (SQLException e) {
            System.out.println("Error fetching service ID: " + e.getMessage());
        }

        // Return -1 if the service name is not found
        return -1;
    }

    // Method to populate the volunteer hours table
    public static void populateVolunteerParticipation(JTable table) {
        Volunteer volunteer = Session.getLoggedInVolunteer();

        // Check if a volunteer is logged in
        if (volunteer == null) {
            System.out.println("No volunteer is logged in.");
            return;
        }

        int volunteerId = volunteer.getId();

        // Query to fetch volunteer hours data
        String query = """
            SELECT
                                         cs.service_id,
                                         cs.service_name,
                                         vp.confirmation_status,
                                         COALESCE(vh.date_logged, NULL) AS date_logged,  -- Use NULL if date is missing
                                         COALESCE(vh.hours_logged, NULL) AS hours_logged  -- Use NULL if hours are missing
                                     FROM community_service cs
                                     JOIN schedule s ON cs.service_id = s.service_id
                                     JOIN volunteer_participation vp ON s.schedule_id = vp.schedule_id
                                     LEFT JOIN volunteer_hours vh
                                         ON vp.volunteer_id = vh.volunteer_id
                                         AND vp.schedule_id = vh.schedule_id
                                     WHERE vp.volunteer_id = ?
                                     GROUP BY cs.service_id, vp.confirmation_status
                                     ORDER BY cs.service_name;
                                     
    """;

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, volunteerId);
            ResultSet rs = stmt.executeQuery();

            // Set up table model
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setColumnIdentifiers(new String[] {
                    "Service Name",
                    "Confirmation Status",
                    "Date Logged",
                    "Hours Logged"
            });
            model.setRowCount(0);  // Clear the table before adding new data

            // Populate the table
            while (rs.next()) {
                String serviceName = rs.getString("service_name");
                String confirmationStatus = rs.getString("confirmation_status");
                String dateLogged = rs.getString("date_logged");  // Already formatted as string or 'N/A'
                String hoursLogged = rs.getString("hours_logged");

                model.addRow(new Object[] {
                        serviceName,
                        confirmationStatus,
                        dateLogged,
                        hoursLogged
                });
            }

            System.out.println("Volunteer hours loaded successfully.");
            VolunteerRefreshTable.refreshVolunteerParticipationTable(table);
        } catch (SQLException e) {
            System.out.println("Error fetching volunteer hours data: " + e.getMessage());
        }
    }

    public static void addVolunteerHours(int serviceId, String serviceDate, int serviceHours, JTable table) {
        int volunteerId = Session.getLoggedInVolunteerId();

        // Validate input data
        if (serviceId == 0 || serviceDate.isEmpty() || serviceHours <= 0) {
            JOptionPane.showMessageDialog(null, "Please fill in all fields correctly!");
            return;
        }

        // Prepare the SQL statement to insert data
        String sql = "INSERT INTO volunteer_hours (volunteer_id, schedule_id, hours_logged, date_logged) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.connect()) {
            // Prepare the statement
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, volunteerId);   // Set volunteer_id
            pstmt.setInt(2, serviceId);      // Set schedule_id
            pstmt.setInt(3, serviceHours);   // Set hours_logged
            pstmt.setString(4, serviceDate); // Set date_logged

            // Execute the insert statement
            int result = pstmt.executeUpdate();

            // Provide feedback
            if (result > 0) {
                JOptionPane.showMessageDialog(null, "Volunteer hours added successfully!");
                VolunteerRefreshTable.refreshVolunteerParticipationTable(table);
            } else {
                JOptionPane.showMessageDialog(null, "Failed to add volunteer hours.");
            }
        } catch (SQLException e) {
            // Handle SQL exceptions
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void updateVolunteerHours(int serviceId, String serviceDate, int newServiceHours, JTable table) {
        int volunteerId = Session.getLoggedInVolunteerId();

        // Validate input data
        if (serviceId == 0 || serviceDate.isEmpty() || newServiceHours <= 0) {
            JOptionPane.showMessageDialog(null, "Please fill in all fields correctly!");
            return;
        }

        // Prepare the SQL statement to update data
        String sql = "UPDATE volunteer_hours SET hours_logged = ? WHERE volunteer_id = ? AND schedule_id = ? AND date_logged = ?";

        try (Connection conn = DatabaseConnection.connect()) {
            // Prepare the statement
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, newServiceHours);  // Set new hours_logged
            pstmt.setInt(2, volunteerId);      // Set volunteer_id
            pstmt.setInt(3, serviceId);        // Set schedule_id
            pstmt.setString(4, serviceDate);   // Set date_logged

            // Execute the update statement
            int result = pstmt.executeUpdate();

            // Provide feedback
            if (result > 0) {
                JOptionPane.showMessageDialog(null, "Volunteer hours updated successfully!");
                VolunteerRefreshTable.refreshVolunteerParticipationTable(table);
            } else {
                JOptionPane.showMessageDialog(null, "No matching record found to update.");
            }
        } catch (SQLException e) {
            // Handle SQL exceptions
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
