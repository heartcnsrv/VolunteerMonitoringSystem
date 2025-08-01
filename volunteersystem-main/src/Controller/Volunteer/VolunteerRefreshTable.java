package Controller.Volunteer;

import Controller.DatabaseConnection;
import Model.Session;
import Model.Volunteer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

/**
 * This class is responsible for refreshing the tables with community service data and volunteer participation data.
 * It fetches relevant data from the database based on the currently logged-in volunteer's ID.
 */
public class VolunteerRefreshTable {

    /**
     * Refreshes the community service table with services that the logged-in volunteer has not registered for.
     * It executes a query to fetch the data and updates the table accordingly.
     *
     * @param table the JTable to populate with community service data
     */
    public static void refreshCommunityServiceTable(JTable table) {
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
            WHERE vp.volunteer_id = ?)
        """;

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Set the volunteer ID parameter for the query
            stmt.setInt(1, volunteerId);

            ResultSet rs = stmt.executeQuery();

            // Get the table model for updating the table
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0); // Clear existing data in the table

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

            System.out.println("Community services loaded successfully.");
        } catch (SQLException e) {
            System.out.println("Error fetching community service data: " + e.getMessage());
        }
    }

    /**
     * Refreshes the volunteer participation table with hours logged and participation status.
     * It fetches data about volunteer's participation in community services, including confirmation status and logged hours.
     *
     * @param table the JTable to populate with volunteer participation data
     */
    public static void refreshVolunteerParticipationTable(JTable table) {
        Volunteer volunteer = Session.getLoggedInVolunteer();

        // Check if a volunteer is logged in
        if (volunteer == null) {
            System.out.println("No volunteer is logged in.");
            return;
        }

        int volunteerId = volunteer.getId();

        // Query to fetch volunteer hours data and participation details
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

            stmt.setInt(1, volunteerId); // Set volunteer ID in the query parameter
            ResultSet rs = stmt.executeQuery();

            // Set up the table model for the volunteer participation table
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setColumnIdentifiers(new String[] {
                    "Service Name",
                    "Confirmation Status",
                    "Date Logged",
                    "Hours Logged"
            });
            model.setRowCount(0);  // Clear the table before adding new data

            // Populate the table with the result set
            while (rs.next()) {
                String serviceName = rs.getString("service_name");
                String confirmationStatus = rs.getString("confirmation_status");
                String dateLogged = rs.getString("date_logged");  // Already formatted as string or 'N/A'
                String hoursLogged = rs.getString("hours_logged");

                // Add the participation data to the table
                model.addRow(new Object[] {
                        serviceName,
                        confirmationStatus,
                        dateLogged,
                        hoursLogged
                });
            }

            System.out.println("Volunteer hours loaded successfully.");
        } catch (SQLException e) {
            System.out.println("Error fetching volunteer participation data: " + e.getMessage());
        }
    }
}