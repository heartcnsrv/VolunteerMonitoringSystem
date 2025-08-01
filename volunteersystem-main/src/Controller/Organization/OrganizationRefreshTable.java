package Controller.Organization;

import Controller.DatabaseConnection;
import Model.Session;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

/**
 * This class provides methods to refresh various JTable components used to display database data in the application.
 * It uses a common refreshTable method to reduce redundancy and improve code maintainability.
 */
public class OrganizationRefreshTable {

    /**
     * Refreshes the data in a JTable based on the given SQL query and parameters.
     *
     * @param table The JTable to be refreshed.
     * @param query The SQL query to execute.
     * @param params Optional parameters for the SQL query.
     */
    public static void refreshTable(JTable table, String query, Object... params) {
        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            // Set parameters if any
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }

            ResultSet rs = stmt.executeQuery();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);  // Clear existing data

            // Populate the table with the result set data
            int columnCount = rs.getMetaData().getColumnCount();
            while (rs.next()) {
                Object[] rowData = new Object[columnCount];
                for (int i = 0; i < columnCount; i++) {
                    rowData[i] = rs.getObject(i + 1);
                }
                model.addRow(rowData);
            }

            System.out.println("Table refreshed successfully.");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error refreshing table: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Refreshes the community service table for the currently logged-in organization.
     *
     * @param table The JTable to be refreshed.
     */
    public static void refreshCommunityServiceTable(JTable table) {
        int orgId = Session.getLoggedInOrganizationId();
        String query = "SELECT service_id, service_name, service_type, description FROM community_service WHERE org_id = ?";
        refreshTable(table, query, orgId);
    }

    /**
     * Refreshes the schedule table with all schedule entries.
     *
     * @param table The JTable to be refreshed.
     */
    public static void refreshScheduleTable(JTable table) {
        String query = "SELECT schedule_id, service_id, schedule_date, start_time, end_time, location, max_volunteers FROM schedule";
        refreshTable(table, query);
    }

    /**
     * Refreshes the registered volunteers table with all volunteer entries.
     *
     * @param table The JTable to be refreshed.
     */
    public static void refreshRegisteredVolunteersTable(JTable table) {
        String query = "SELECT volunteer_id, first_name, last_name, email, phone_number, status, date_joined FROM volunteer";
        refreshTable(table, query);
    }

    /**
     * Refreshes the volunteer participation requests table, including schedule and service details.
     *
     * @param table The JTable to be refreshed.
     */
    public static void refreshVolunteerParticipationRequestsTable(JTable table) {
        String query = """
            SELECT 
                vp.volunteer_id, 
                vp.schedule_id, 
                s.service_id, 
                vp.confirmation_status, 
                vp.role
            FROM volunteer_participation vp
            INNER JOIN schedule s ON vp.schedule_id = s.schedule_id
            ORDER BY vp.volunteer_id;
            """;
        refreshTable(table, query);
    }

    /**
     * Refreshes the volunteer participation records table, including volunteer and schedule details.
     *
     * @param table The JTable to be refreshed.
     */
    public static void refreshVolunteerParticipationRecordsTable(JTable table) {
        String query = """
            SELECT 
                vp.volunteer_id, 
                vp.schedule_id, 
                s.service_id, 
                s.location, 
                v.first_name, 
                v.last_name, 
                v.phone_number
            FROM volunteer_participation vp
            INNER JOIN volunteer v ON vp.volunteer_id = v.volunteer_id
            INNER JOIN schedule s ON vp.schedule_id = s.schedule_id
            ORDER BY vp.volunteer_id;
            """;
        refreshTable(table, query);
    }

    /**
     * Refreshes the volunteer hour logs table, including schedule and hours details.
     *
     * @param table The JTable to be refreshed.
     */
    public static void refreshVolunteerHourLogsTable(JTable table) {
        String query = """
            SELECT 
                vh.volunteer_id, 
                vh.schedule_id, 
                s.service_id,
                vh.hours_id, 
                vh.date_logged, 
                vh.hours_logged
            FROM volunteer_hours vh
            INNER JOIN schedule s ON vh.schedule_id = s.schedule_id
            ORDER BY vh.volunteer_id;
            """;
        refreshTable(table, query);
    }
}