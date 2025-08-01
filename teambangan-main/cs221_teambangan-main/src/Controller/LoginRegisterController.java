package Controller;

import Model.Session;
import Model.Volunteer;
import Model.Organization;

import java.sql.*;

/**
 * Controller class responsible for handling user login and registration for both volunteers and organizations.
 */
public class LoginRegisterController {

    /**
     * Attempts to log in a volunteer using their email and password.
     *
     * @param email the email of the volunteer
     * @param password the password of the volunteer
     * @return true if the login is successful, false otherwise
     */
    public static boolean login(String email, String password) {
        String query = "SELECT * FROM volunteer WHERE email = ? AND password = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, email);  // Set the email in the query
            stmt.setString(2, password);  // Set the password in the query

            ResultSet rs = stmt.executeQuery();

            // If a matching record is found, log the volunteer in
            if (rs.next()) {
                // Extract data from the result set
                int volunteerId = rs.getInt("volunteer_id");
                String passworddb = rs.getString("password");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String phone = rs.getString("phone_number");
                String status = rs.getString("status");
                String dateJoined = rs.getString("date_joined");

                // Create a Volunteer object and populate it with the database data
                Volunteer volunteer = new Volunteer();
                volunteer.setId(volunteerId);
                volunteer.setPassword(passworddb);
                volunteer.setFirstName(firstName);
                volunteer.setLastName(lastName);
                volunteer.setEmail(email);
                volunteer.setPhoneNumber(phone);
                volunteer.setStatus(status);
                volunteer.setDateJoined(dateJoined);

                // Store the logged-in volunteer in the session
                Session.setLoggedInVolunteer(volunteer);

                System.out.println("Login successful!");
                return true;  // Return true if login is successful
            } else {
                // Return false if the credentials do not match any record
                System.out.println("Invalid username or password.");
                return false;
            }

        } catch (SQLException e) {
            // Print error message and return false in case of any SQLException
            System.out.println("Database error: " + e.getMessage());
            return false;
        }
    }

    /**
     * Attempts to log in an organization using their name and password.
     *
     * @param orgName the organization name
     * @param password the password of the organization
     * @return true if the login is successful, false otherwise
     */
    public static boolean loginOrganization(String orgName, String password) {
        String query = "SELECT * FROM organization WHERE org_name = ? AND password = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, orgName);  // Set the organization name in the query
            stmt.setString(2, password);  // Set the organization password in the query

            ResultSet rs = stmt.executeQuery();

            // If a matching record is found, log the organization in
            if (rs.next()) {
                // Extract data from the result set
                int orgId = rs.getInt("org_id");
                String contactInfo = rs.getString("contact_info");
                String address = rs.getString("address");
                String website = rs.getString("website");
                String missionStatement = rs.getString("mission_statement");

                // Create an Organization object and populate it with the database data
                Organization organization = new Organization();
                organization.setOrgID(orgId);
                organization.setOrgName(orgName);
                organization.setContactInfo(contactInfo);
                organization.setAddress(address);
                organization.setPassword(password);
                organization.setWebsite(website);
                organization.setMissionStatement(missionStatement);

                // Store the logged-in organization in the session
                Session.setLoggedInOrganization(organization);

                System.out.println("Login successful for organization ID: " + orgId);
                return true;  // Return true if login is successful
            } else {
                // Return false if the credentials do not match any record
                System.out.println("Invalid username or password.");
                return false;
            }

        } catch (SQLException e) {
            // Print error message and return false in case of any SQLException
            System.out.println("Database error: " + e.getMessage());
            return false;
        }
    }

    /**
     * Registers a new volunteer by saving their information into the database.
     *
     * @param email the email of the volunteer
     * @param password the password of the volunteer
     * @param firstName the first name of the volunteer
     * @param lastName the last name of the volunteer
     * @param phone the phone number of the volunteer
     * @return true if the registration is successful, false otherwise
     */
    public static boolean register(String email, String password, String firstName, String lastName, String phone) {
        try (Connection conn = DatabaseConnection.connect()) {
            String query = "{ CALL AddVolunteer(?,?,?,?,?) }";
            CallableStatement stmt = conn.prepareCall(query);

            // Set parameters for the callable statement
            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setString(3, email);
            stmt.setString(4,password);
            stmt.setString(5,phone);

            stmt.execute();
            return true;
        } catch (SQLException e) {
            // Print error message and return false in case of any SQLException
            System.out.println("Register error: " + e.getMessage());
            return false;
        }
    }
}