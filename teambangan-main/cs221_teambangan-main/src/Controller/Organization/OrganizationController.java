package Controller.Organization;

import Controller.DatabaseConnection;
import Model.Organization;
import Model.Session;
import Model.Volunteer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class OrganizationController {
    public static void populateCommunityService(JTable table) {
        int orgId = Session.getLoggedInOrganizationId();

        String query = "SELECT service_id, service_name, service_type, description FROM community_service WHERE org_id = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, orgId);
            ResultSet rs = stmt.executeQuery();

            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);  // Clear existing data

            while (rs.next()) {
                int serviceId = rs.getInt("service_id");
                String serviceName = rs.getString("service_name");
                String serviceType = rs.getString("service_type");
                String description = rs.getString("description");
                model.addRow(new Object[]{serviceId, serviceName, serviceType, description});
            }

            System.out.println("Community Service data loaded successfully.");
            OrganizationRefreshTable.refreshCommunityServiceTable(table);
        } catch (SQLException e) {
            System.out.println("Error fetching community service data: " + e.getMessage());
        }
    }

    public static void addCommunityService(int serviceId, String serviceName, String serviceType, String description, JTable table) {
        int orgId = Session.getLoggedInOrganizationId();

        try (Connection connection = DatabaseConnection.connect()) {
            String sql = "INSERT INTO community_service (service_id, org_id, service_name, service_type, description) VALUES (?, ?, ?, ?, ?)";

            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, serviceId);
                stmt.setInt(2, orgId);
                stmt.setString(3, serviceName);
                stmt.setString(4, serviceType);
                stmt.setString(5, description);
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "Community Service added successfully!");
                OrganizationRefreshTable.refreshCommunityServiceTable(table);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage());
        }
    }

    public static void editCommunityService(int serviceId, String serviceName, String serviceType, String description, JTable table) {
        int orgId = Session.getLoggedInOrganizationId();
        try (Connection connection = DatabaseConnection.connect()) {
            String sql = "UPDATE community_service SET service_name = ?, service_type = ?, description = ? WHERE service_id = ? AND org_id = ?";

            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, serviceName);
                stmt.setString(2, serviceType);
                stmt.setString(3, description);
                stmt.setInt(4, serviceId);
                stmt.setInt(5, orgId);
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "Community Service updated successfully!");
                OrganizationRefreshTable.refreshCommunityServiceTable(table);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Failed to update community service: " + e.getMessage());
        }
    }

    public static void populateSchedule(JTable table) {
        String query = "SELECT schedule_id, service_id, schedule_date, start_time, end_time, location, max_volunteers FROM schedule";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();

            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);  // Clear existing data in the table

            while (rs.next()) {
                int scheduleId = rs.getInt("schedule_id");
                int serviceId = rs.getInt("service_id");
                Date scheduledDate = rs.getDate("schedule_date");
                Time startTime = rs.getTime("start_time");
                Time endTime = rs.getTime("end_time");
                String location = rs.getString("location");
                int maxVolunteers = rs.getInt("max_volunteers");

                model.addRow(new Object[]{scheduleId, serviceId, scheduledDate, startTime, endTime, location, maxVolunteers});
            }

            System.out.println("Schedule data loaded successfully.");
            OrganizationRefreshTable.refreshScheduleTable(table);

        } catch (SQLException e) {
            System.out.println("Error fetching schedule data: " + e.getMessage());
        }
    }

    public static void addScheduleToACommunityService(int scheduleId, int serviceId, Date scheduleDate, Time startTime, Time endTime, String location, int maxVolunteers, JTable table) {
        try (Connection conn = DatabaseConnection.connect()) {
            String sql = "INSERT INTO schedule (schedule_id, service_id, schedule_date, start_time, end_time, location, max_volunteers) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, scheduleId);
            stmt.setInt(2, serviceId);
            stmt.setString(3, String.valueOf(scheduleDate));
            stmt.setString(4, String.valueOf(startTime));
            stmt.setString(5, String.valueOf(endTime));
            stmt.setString(6, location);
            stmt.setInt(7, maxVolunteers);
            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Schedule added successfully!");
            OrganizationRefreshTable.refreshScheduleTable(table);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    //GET SERVICE NAME OF THE COMMUNITY SERVICE WHEN THE USER EDITS A SCHEDULE
    public static String getServiceNameById(String serviceId) {
        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT service_name FROM community_service WHERE service_id = ?"
             )) {
            statement.setInt(1, Integer.parseInt(serviceId));
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("service_name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database error while retrieving service name.");
        }

        return null; // Return null if not found
    }

    public static void editScheduleOfACommunityService(int scheduleId, int serviceId, String scheduleDate, String startTime, String endTime, String location, int maxVolunteers, JTable table) {
        int orgId = Session.getLoggedInOrganizationId();
        String sql = "UPDATE schedule SET service_id = ?, schedule_date = ?, start_time = ?, end_time = ?, location = ?, max_volunteers = ? " +
                "WHERE schedule_id = ? AND service_id IN (SELECT service_id FROM community_service WHERE org_id = ?);";
        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, serviceId);
            stmt.setDate(2, java.sql.Date.valueOf(scheduleDate));
            stmt.setTime(3, java.sql.Time.valueOf(startTime));
            stmt.setTime(4, java.sql.Time.valueOf(endTime));
            stmt.setString(5, location);
            stmt.setInt(6, maxVolunteers);
            stmt.setInt(7, scheduleId);
            stmt.setInt(8, orgId);

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Schedule updated successfully!");
            OrganizationRefreshTable.refreshScheduleTable(table);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Failed to update schedule: " + e.getMessage());
        }
    }

    public static void populateRegisteredVolunteersTable(JTable table) {
        String query = "SELECT volunteer_id, first_name, last_name, email, phone_number, status, date_joined FROM volunteer";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            ResultSet rs = stmt.executeQuery();

            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);

            while (rs.next()) {
                int volunteerId = rs.getInt("volunteer_id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String email = rs.getString("email");
                String phoneNumber = rs.getString("phone_number");
                String status = rs.getString("status");
                String dateJoined = rs.getString("date_joined");

                model.addRow(new Object[]{volunteerId, firstName, lastName, email, phoneNumber, status, dateJoined});
            }

            System.out.println("Volunteer data loaded successfully.");
            OrganizationRefreshTable.refreshRegisteredVolunteersTable(table);

        } catch (SQLException e) {
            System.out.println("Error fetching community service data: " + e.getMessage());
        }
    }

    public static void populateVolunteerParticipationRequestsTable(JTable table) {
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

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            ResultSet rs = stmt.executeQuery();

            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0); // Clear existing rows

            while (rs.next()) {
                int volunteerId = rs.getInt("volunteer_id");
                int scheduleId = rs.getInt("schedule_id");
                int serviceId = rs.getInt("service_id");
                String confirmationStatus = rs.getString("confirmation_status");
                String role = rs.getString("role");

                model.addRow(new Object[]{volunteerId, scheduleId, serviceId, confirmationStatus, role});
            }

            System.out.println("Volunteer participation requests loaded successfully.");
            OrganizationRefreshTable.refreshVolunteerParticipationRequestsTable(table);

        } catch (SQLException e) {
            System.out.println("Error fetching volunteer participation data: " + e.getMessage());
        }
    }

    public static void populateVolunteerParticipationRecordsTable(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); // Clear existing rows

        try (Connection connection = DatabaseConnection.connect();
             Statement statement = connection.createStatement()) {

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

            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int volunteerId = resultSet.getInt("volunteer_id");
                int scheduleId = resultSet.getInt("schedule_id");
                int serviceId = resultSet.getInt("service_id");
                String location = resultSet.getString("location");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String phoneNumber = resultSet.getString("phone_number");

                model.addRow(new Object[]{volunteerId, serviceId, scheduleId, location, firstName, lastName, phoneNumber});
            }

            OrganizationRefreshTable.refreshVolunteerParticipationRecordsTable(table);

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error loading volunteer participation records.", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void updateRegisteredVolunteerStatus(JTable table, boolean isActive) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Please select a row first.");
            return;
        }

        // Assuming the volunteer ID is in the first column
        int volunteerId = (int) table.getValueAt(selectedRow, 0);
        String newStatus = isActive ? "Active" : "Inactive";

        try (Connection conn = DatabaseConnection.connect()) {
            String query = "UPDATE volunteer SET status = ? WHERE volunteer_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, newStatus);
                stmt.setInt(2, volunteerId);
                int rowsAffected = stmt.executeUpdate();

                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Volunteer status updated to " + newStatus + " successfully!");
                    // Refresh the table to reflect the changes
                    OrganizationRefreshTable.refreshRegisteredVolunteersTable(table);
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to update volunteer status.");
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage());
        }
    }

    public static void updateVolunteerParticipationRequestsStatus(JTable table) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Please select a row first.");
            return;
        }

        // Extract volunteer ID and schedule ID from the selected row
        int volunteerId = (int) table.getValueAt(selectedRow, 0);
        int scheduleId = (int) table.getValueAt(selectedRow, 2);  // Make sure the Schedule ID is in the 3rd column
        String newStatus = "Confirmed";

        try (Connection conn = DatabaseConnection.connect()) {
            String query = "UPDATE volunteer_participation SET confirmation_status = ? WHERE volunteer_id = ? AND schedule_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, newStatus);
                stmt.setInt(2, volunteerId);
                stmt.setInt(3, scheduleId);
                int rowsAffected = stmt.executeUpdate();

                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Participation request status updated to " + newStatus + " successfully!");
                    // Refresh the table to reflect the changes
                    OrganizationRefreshTable.refreshVolunteerParticipationRequestsTable(table);
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to update participation request status.");
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage());
        }
    }

    public static void populateVolunteerHourLogsTable(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); // Clear existing rows

        try (Connection connection = DatabaseConnection.connect();
             Statement statement = connection.createStatement()) {

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

            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int volunteerId = resultSet.getInt("volunteer_id");
                int scheduleId = resultSet.getInt("schedule_id");
                int serviceId = resultSet.getInt("service_id");
                int hoursId = resultSet.getInt("hours_id");
                Date dateLogged = resultSet.getDate("date_logged");
                int hoursLogged = resultSet.getInt("hours_logged");

                model.addRow(new Object[]{volunteerId, serviceId, scheduleId, hoursId, dateLogged, hoursLogged});
            }

            OrganizationRefreshTable.refreshVolunteerHourLogsTable(table);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error loading volunteer hour logs.", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static boolean updateOrganizationProfile(Organization organization) {
        String query = "UPDATE organization SET org_name = ?, contact_info = ?, address = ?, password = ?, website = ?, mission_statement = ? WHERE org_id = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, organization.getOrgName());
            stmt.setString(2, organization.getContactInfo());
            stmt.setString(3, organization.getAddress());
            stmt.setString(4, organization.getPassword());
            stmt.setString(5, organization.getWebsite());
            stmt.setString(6, organization.getMissionStatement());
            stmt.setInt(7, organization.getOrgID());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Error updating organization: " + e.getMessage());
            return false;
        }
    }

    public static void refreshOrganizationProfile(JPanel informationPanel) {
        Organization currentVolunteer = Session.getLoggedInOrganization();

        // Update Labels inside the Volunteer Profile Information Panel
        JLabel orgIDLabel = (JLabel) informationPanel.getComponent(1);
        orgIDLabel.setText(String.valueOf(currentVolunteer.getOrgID()));

        JLabel orgNameLabel = (JLabel) informationPanel.getComponent(3);
        orgNameLabel.setText(currentVolunteer.getOrgName());

        JLabel contactInfoLabel = (JLabel) informationPanel.getComponent(5);
        contactInfoLabel.setText(currentVolunteer.getContactInfo());

        JLabel websiteLabel = (JLabel) informationPanel.getComponent(7);
        websiteLabel.setText(currentVolunteer.getWebsite());

        JLabel addressLabel = (JLabel) informationPanel.getComponent(9);
        addressLabel.setText(currentVolunteer.getAddress());

        JLabel missionStatementLabel = (JLabel) informationPanel.getComponent(11);
        missionStatementLabel.setText(currentVolunteer.getMissionStatement());

        informationPanel.revalidate();
        informationPanel.repaint();
    }
}