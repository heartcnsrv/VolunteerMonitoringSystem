package View.Volunteer;

import Controller.Volunteer.VolunteerController;
import Controller.Volunteer.VolunteerRefreshTable;
import Model.Schedule;
import Model.Session;
import Model.Volunteer;
import View.LoginView;
import View.RoundedBorder;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Date;

import static Controller.Volunteer.VolunteerController.*;

public class VolunteerView {
    private static JTable participationTable;

    public static void volunteerView() {
        // Main Frame Setup
        JFrame volunteerFrame = new JFrame("Volunteer");
        volunteerFrame.setSize(1000, 700);
        volunteerFrame.setLocationRelativeTo(null);
        volunteerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        volunteerFrame.setLayout(new BorderLayout());

        //Navigation Panel
        JPanel navigationPanel = new JPanel();
        navigationPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 5));
        navigationPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        navigationPanel.setBackground(new Color(11, 80, 140));
        navigationPanel.setPreferredSize(new Dimension(150, 0));

        //NAvigation Buttons
        JButton communityServiceButton = new JButton("Service");
        JButton volunteerParticipationButton = new JButton("Participation");
        JButton volunteerProfileButton = new JButton("Profile");
        JButton logoutButton = new JButton("Logout");

        //Customize buttons
        customizeButton(communityServiceButton);
        customizeButton(volunteerParticipationButton);
        customizeButton(volunteerProfileButton);
        customizeButton(logoutButton);

        //Add navigation panel contents to panel
        navigationPanel.add(communityServiceButton);
        navigationPanel.add(volunteerParticipationButton);
        navigationPanel.add(volunteerProfileButton);
        navigationPanel.add(logoutButton);

        volunteerFrame.add(navigationPanel, BorderLayout.WEST);

        //Main panel and card layout
        CardLayout cardLayout = new CardLayout();
        JPanel contentPanel = new JPanel(cardLayout);

        //Spacing between main panel and nav panel
        contentPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

        volunteerFrame.add(contentPanel, BorderLayout.CENTER);

        //Create Panels for CardLayout
        JPanel communityServicePanel = createCommunityServicePanel();
        JPanel participationPanel = createParticipationPanel();
        JPanel volunteerProfilePanel = createVolunteerProfile();
        //Add Panels to main panel of the frame
        contentPanel.add(communityServicePanel, "CommunityService");
        contentPanel.add(participationPanel, "Participation");
        contentPanel.add(volunteerProfilePanel, "VolunteerProfile");

        //Set the default view as community service panel
        cardLayout.show(contentPanel, "CommunityService");

        //Button Functions
        communityServiceButton.addActionListener(e ->  {
            cardLayout.show(contentPanel, "CommunityService");
        });
        volunteerParticipationButton.addActionListener(e -> {
            cardLayout.show(contentPanel, "Participation");
            VolunteerRefreshTable.refreshVolunteerParticipationTable(participationTable);
        });
        volunteerProfileButton.addActionListener(e -> cardLayout.show(contentPanel, "VolunteerProfile"));

        logoutButton.addActionListener(e -> {
            volunteerFrame.dispose();
            LoginView.loginView();
        });

        volunteerFrame.setVisible(true);
    }


    //Method for button customization for nav panel
    private static void customizeButton(JButton button) {
        Color hexColor = Color.decode("#50A3CC");
        button.setPreferredSize(new Dimension(140, 40));
        button.setForeground(Color.WHITE);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(RoundedBorder.createButtonRoundedBorder(button, 10, hexColor));
    }

    private static JPanel createCommunityServicePanel() {
    JPanel communityServicePanel = new JPanel(new BorderLayout());
    communityServicePanel.setBackground(new Color(241, 195, 91));

    //Panel for Title bar and search bar components
    JPanel topBar = new JPanel(new BorderLayout());
    topBar.setOpaque(false);

    //Set the location of the title bar to the left
    JLabel titleLabel = new JLabel("Community Service");
    titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
    titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 0));
    topBar.add(titleLabel, BorderLayout.WEST);

    //Community Service Table
    String[] columnNames = {"Service ID", "Service Name", "Service Type", "Description"};
    DefaultTableModel model = new DefaultTableModel(new Object[0][4], columnNames); // Default model with no data initially
    JTable table = new JTable(model);
    table.setRowHeight(30);
    table.setFont(new Font("Arial", Font.PLAIN, 14));
    table.setFillsViewportHeight(true);

    //Set color of cells
    table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                       boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            c.setBackground(row % 2 == 0 ? new Color(255, 255, 255) : new Color(206, 229, 242));
            return c;
        }
    });

    JScrollPane tableScrollPane = new JScrollPane(table);
    table.setPreferredScrollableViewportSize(new Dimension(800, 250));
    tableScrollPane.setBorder(BorderFactory.createEmptyBorder());

    VolunteerRefreshTable.refreshCommunityServiceTable(table);
    VolunteerController.populateCommunityService(table); // Populate the community service table

    //Schedule Panel for Community Service
    JPanel schedulePanel = new JPanel(new BorderLayout());
    schedulePanel.setOpaque(false);
    schedulePanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

    //Schedule Information Panel
    JPanel informationPanel = new JPanel(new GridBagLayout());
    informationPanel.setOpaque(false);
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.anchor = GridBagConstraints.WEST;
    gbc.insets = new Insets(10, 10, 10, 20);
    gbc.weightx = 1;
    gbc.fill = GridBagConstraints.HORIZONTAL;

    Font labelFont = new Font("Arial", Font.BOLD, 16);

    //Schedule ID
    gbc.gridy = 0;
    gbc.gridx = 0;
    JLabel scheduleIdLabel = new JLabel("Schedule ID:");
    scheduleIdLabel.setFont(labelFont);
    informationPanel.add(scheduleIdLabel, gbc);

    gbc.gridx = 1;
    JLabel scheduleIdValue = new JLabel("1234");
    scheduleIdValue.setFont(labelFont);
    informationPanel.add(scheduleIdValue, gbc);

    //Service ID
    gbc.gridx = 2;
    JLabel serviceIdLabel = new JLabel("Service ID:");
    serviceIdLabel.setFont(labelFont);
    informationPanel.add(serviceIdLabel, gbc);

    gbc.gridx = 3;
    JLabel serviceIdValue = new JLabel("1234");
    serviceIdValue.setFont(labelFont);
    informationPanel.add(serviceIdValue, gbc);

    //Schedule Date
    gbc.gridy = 1;
    gbc.gridx = 0;
    JLabel dateLabel = new JLabel("Schedule Date:");
    dateLabel.setFont(labelFont);
    informationPanel.add(dateLabel, gbc);

    gbc.gridx = 1;
    JLabel dateValue = new JLabel("April 20, 2025");
    dateValue.setFont(labelFont);
    informationPanel.add(dateValue, gbc);

    //Max Volunteers
    gbc.gridx = 2;
    JLabel maxLabel = new JLabel("Max Volunteers:");
    maxLabel.setFont(labelFont);
    informationPanel.add(maxLabel, gbc);

    gbc.gridx = 3;
    JLabel maxValue = new JLabel("5");
    maxValue.setFont(labelFont);
    informationPanel.add(maxValue, gbc);

    //Start Time
    gbc.gridy = 2;
    gbc.gridx = 0;
    JLabel startLabel = new JLabel("Start Time:");
    startLabel.setFont(labelFont);
    informationPanel.add(startLabel, gbc);

    gbc.gridx = 1;
    JLabel startValue = new JLabel("1:00 AM");
    startValue.setFont(labelFont);
    informationPanel.add(startValue, gbc);

    //End Time
    gbc.gridx = 2;
    JLabel endLabel = new JLabel("End Time:");
    endLabel.setFont(labelFont);
    informationPanel.add(endLabel, gbc);

    gbc.gridx = 3;
    JLabel endValue = new JLabel("3:00 AM");
    endValue.setFont(labelFont);
    informationPanel.add(endValue, gbc);

    //Location
    gbc.gridy = 3;
    gbc.gridx = 0;
    JLabel locationLabel = new JLabel("Location:");
    locationLabel.setFont(labelFont);
    informationPanel.add(locationLabel, gbc);

    gbc.gridx = 1;
    JLabel locationValue = new JLabel("Baguio");
    locationValue.setFont(labelFont);
    informationPanel.add(locationValue, gbc);

    // Register Button Panel
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    buttonPanel.setOpaque(false);
    JButton registerButton = new JButton("Register");
    registerButton.setFont(new Font("Arial", Font.BOLD, 16));
    buttonPanel.add(registerButton);

    //Button Customization
    Color registerButtonColor = new Color(102, 153, 204);
    registerButton.setForeground(Color.WHITE);
    registerButton.setBorder(RoundedBorder.createButtonRoundedBorder(registerButton, 10, registerButtonColor));

    //Add components to main panel
    schedulePanel.add(informationPanel, BorderLayout.CENTER);
    schedulePanel.add(buttonPanel, BorderLayout.SOUTH);

    //Show schedule information of the first row by default
    if (table.getRowCount() > 0) {
        table.setRowSelectionInterval(0, 0); // select first row
        String firstServiceId = table.getValueAt(0, 0).toString();
        Schedule firstSchedule = VolunteerController.getScheduleForService(firstServiceId);
        updateSchedulePanel(firstSchedule, scheduleIdValue, serviceIdValue, dateValue, maxValue, startValue, endValue, locationValue);
    }

    //Update schedule panel when a community service row is selected
    table.getSelectionModel().addListSelectionListener(e -> {
        if (!e.getValueIsAdjusting()) {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                // Retrieve the selected community service ID
                String serviceId = table.getValueAt(selectedRow, 0).toString();

                // Fetch the schedule information from the controller based on the selected service ID
                Schedule schedule = VolunteerController.getScheduleForService(serviceId);

                //Updates the schedule panel upon clicking a community service in the table row
                updateSchedulePanel(schedule, scheduleIdValue, serviceIdValue, dateValue, maxValue, startValue, endValue, locationValue);
            }
        }
    });

    //REGISTER TO COMMUNITY SERVICE BUTTON FUNCTION
    registerButton.addActionListener((ActionEvent e) -> {
        int scheduleId = Integer.parseInt(scheduleIdValue.getText());

        boolean success = VolunteerController.registerToCommunityService(scheduleId, table);

        if (success) {
            JOptionPane.showMessageDialog(communityServicePanel, "Registration Successful!");
        } else {
            JOptionPane.showMessageDialog(communityServicePanel, "Registration Failed. Please try again.");
        }
    });

    //Add table to the top bar to ensure sections in panel
    JPanel topSection = new JPanel();
    topSection.setLayout(new BoxLayout(topSection, BoxLayout.Y_AXIS));
    topSection.setOpaque(false);
    topSection.add(topBar);
    topSection.add(Box.createVerticalStrut(10));
    topSection.add(tableScrollPane);
    topSection.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

    //Add components to the main panel
    communityServicePanel.add(topSection, BorderLayout.NORTH);
    communityServicePanel.add(schedulePanel, BorderLayout.CENTER);

    return communityServicePanel;
}

    private static JPanel createParticipationPanel() {
        JPanel participationPanel = new JPanel(new BorderLayout());
        participationPanel.setBackground(new Color(241, 195, 91));

        //Panel for Title bar and search bar components
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setOpaque(false);

        //Set the location of the title bar to the left
        JLabel titleLabel = new JLabel("Volunteer Participation");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 0));
        topBar.add(titleLabel, BorderLayout.WEST);

        //Community Service Table
        String[] columnNames = {"Service Name", "Confirmation Status", "Date Logged", "Hours Logged"};
        DefaultTableModel model = new DefaultTableModel(new Object[0][4], columnNames); // Default model with no data initially
        participationTable = new JTable(model);
        participationTable.setRowHeight(30);
        participationPanel.setFont(new Font("Arial", Font.PLAIN, 14));
        participationTable.setFillsViewportHeight(true);

        //Set color of cells
        participationTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                           boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBackground(row % 2 == 0 ? new Color(255, 255, 255) : new Color(206, 229, 242));
                return c;
            }
        });

        JScrollPane tableScrollPane = new JScrollPane(participationTable);
        participationTable.setPreferredScrollableViewportSize(new Dimension(800, 250));
        tableScrollPane.setBorder(BorderFactory.createEmptyBorder());

        VolunteerController.populateVolunteerParticipation(participationTable);

        //Schedule Panel for Community Service
        JPanel schedulePanel = new JPanel(new BorderLayout());
        schedulePanel.setOpaque(false);
        schedulePanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        //Schedule Information Panel
        JPanel informationPanel = new JPanel(new GridBagLayout());
        informationPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 10, 20);
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font labelFont = new Font("Arial", Font.BOLD, 16);

        //Schedule ID
        gbc.gridy = 0;
        gbc.gridx = 0;
        JLabel serviceTypeLabel = new JLabel("Service Type:");
        serviceTypeLabel.setFont(labelFont);
        informationPanel.add(serviceTypeLabel, gbc);

        gbc.gridx = 1;
        JLabel serviceTypeValue = new JLabel("1234");
        serviceTypeValue.setFont(labelFont);
        informationPanel.add(serviceTypeValue, gbc);

        //Service ID
        gbc.gridx = 2;
        JLabel locationLabel = new JLabel("Location:");
        locationLabel.setFont(labelFont);
        informationPanel.add(locationLabel, gbc);

        gbc.gridx = 3;
        JLabel locationValue = new JLabel("1234");
        locationValue.setFont(labelFont);
        informationPanel.add(locationValue, gbc);

        //Schedule Date
        gbc.gridy = 1;
        gbc.gridx = 0;
        JLabel roleLabel = new JLabel("Role:");
        roleLabel.setFont(labelFont);
        informationPanel.add(roleLabel, gbc);

        gbc.gridx = 1;
        JLabel roleValue = new JLabel("2025-08-25");
        roleValue.setFont(labelFont);
        informationPanel.add(roleValue, gbc);

        // Register Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);
        JButton checkInButton = new JButton("Add Logs");
        checkInButton.setFont(new Font("Arial", Font.BOLD, 16));
        buttonPanel.add(checkInButton);

        JButton checkOutButton = new JButton("Edit Logs");
        checkOutButton.setFont(new Font("Arial", Font.BOLD, 16));
        buttonPanel.add(checkOutButton);

        //Button Customization
        Color checkInButtonColor = new Color(102, 153, 204);
        checkInButton.setForeground(Color.WHITE);
        checkInButton.setBorder(RoundedBorder.createButtonRoundedBorder(checkInButton, 10, checkInButtonColor));

        checkOutButton.setForeground(Color.WHITE);
        checkOutButton.setBorder(RoundedBorder.createButtonRoundedBorder(checkOutButton, 10, new Color(255, 153, 51)));

        //ADD HOUR LOGS
        checkInButton.addActionListener((ActionEvent e) -> {
            int selectedRow = participationTable.getSelectedRow();
            if (selectedRow != -1) {
                String serviceName = String.valueOf(participationTable.getValueAt(selectedRow, 0)); // Get the service name from the table
                int serviceId = getServiceIdByName(serviceName); // Lookup service ID based on service name
                if (serviceId != -1) {
                    VolunteerPopUpWindows.addVolunteerHoursWindow(serviceId, participationTable); // Pass service ID to pop-up window
                } else {
                    JOptionPane.showMessageDialog(participationPanel, "Service ID not found for selected service.");
                }
            } else {
                JOptionPane.showMessageDialog(participationPanel, "Please select a service first.");
            }
        });

        //EDIT HOUR LOGS
        checkOutButton.addActionListener((ActionEvent e) -> {
            int selectedRow = participationTable.getSelectedRow();
            if (selectedRow != -1) {
                String serviceName = String.valueOf(participationTable.getValueAt(selectedRow, 0));
                int serviceId = getServiceIdByName(serviceName); // Lookup service ID based on service name

                // Extract only the service date and hours logged from the selected row
                String dateLogged = (String) participationTable.getValueAt(selectedRow, 2);
                String hoursLogged = (String) participationTable.getValueAt(selectedRow, 3);

                // Pass the extracted data to the pop-up window
                VolunteerPopUpWindows.editVolunteerHoursWindow(serviceId, Date.valueOf(dateLogged), Integer.parseInt(hoursLogged), participationTable);
            } else {
                JOptionPane.showMessageDialog(participationPanel, "Please select a service first.");
            }
        });

        //Add components to main panel
        schedulePanel.add(informationPanel, BorderLayout.CENTER);
        schedulePanel.add(buttonPanel, BorderLayout.SOUTH);

        if (participationTable.getRowCount() > 0) {
            participationTable.setRowSelectionInterval(0, 0); // Select first row
            String firstServiceName = (String) participationTable.getValueAt(0, 0); // Get service name
            int firstServiceId = VolunteerController.getServiceIdByName(firstServiceName); // Convert to service ID

            if (firstServiceId != -1) {
                updateInformationPanel(firstServiceId, serviceTypeValue, locationValue, roleValue);
            }
        }

        participationTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = participationTable.getSelectedRow();
                if (selectedRow != -1) {
                    String selectedServiceName = (String) participationTable.getValueAt(selectedRow, 0);
                    int selectedServiceId = VolunteerController.getServiceIdByName(selectedServiceName);

                    if (selectedServiceId != -1) {
                        updateInformationPanel(selectedServiceId, serviceTypeValue, locationValue, roleValue);
                    }
                }
            }
        });

        //Add table to the top bar to ensure sections in panel
        JPanel topSection = new JPanel();
        topSection.setLayout(new BoxLayout(topSection, BoxLayout.Y_AXIS));
        topSection.setOpaque(false);
        topSection.add(topBar);
        topSection.add(Box.createVerticalStrut(10));
        topSection.add(tableScrollPane);
        topSection.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        //Add components to the main panel
        participationPanel.add(topSection, BorderLayout.NORTH);
        participationPanel.add(schedulePanel, BorderLayout.CENTER);

        return participationPanel;
    }

    private static JPanel createVolunteerProfile() {
        JPanel profilePanel = new JPanel(new BorderLayout());
        Color baseColor = Color.decode("#F1CC5C");
        profilePanel.setBackground(baseColor);
        profilePanel.setPreferredSize(new Dimension(820, 700));

        //Top Bar Panel
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setPreferredSize(new Dimension(820, 50));
        topBar.setOpaque(false);
        topBar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel labelP = new JLabel("Volunteer Profile");
        labelP.setFont(new Font("Inclusive Sans", Font.PLAIN, 20));
        topBar.add(labelP, BorderLayout.WEST);

        // Volunteer Profile Content Panel
        JPanel profileContentPanel = RoundedBorder.createRoundedPanel(20, Color.WHITE, Color.WHITE);
        profileContentPanel.setLayout(new BorderLayout());

        // Profile Picture Panel
        JPanel profilePicturePanel = new JPanel(new BorderLayout());
        profilePicturePanel.setOpaque(false);
        profilePicturePanel.setPreferredSize(new Dimension(100, 100));
        profileContentPanel.add(profilePicturePanel, BorderLayout.WEST);

        // Profile Icon
        ImageIcon profileIconFile = new ImageIcon("res/profileIcon.png");
        Image profileIcon = profileIconFile.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH); // Optional: scale image
        Icon profile = new ImageIcon(profileIcon);

        // Add the image to the profile picture panel using a JLabel
        JLabel profileLabel = new JLabel(profile);
        profilePicturePanel.add(profileLabel, BorderLayout.CENTER);

        //Gather Volunteer Information from the Database
        Volunteer currentVolunteer = Session.getLoggedInVolunteer();

        //Profile Information Panel
        JPanel informationPanel = new JPanel(new GridBagLayout());
        informationPanel.setOpaque(false);

        //Text Components
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridy = 0;

        //Volunteer ID Label
        gbc.gridx = 0;
        gbc.insets = new Insets(10, 20, 10, 50);
        informationPanel.add(new JLabel("Volunteer ID:"), gbc);

        //Volunteer ID Information from Database
        gbc.gridx = 1;
        gbc.insets = new Insets(10, 5, 10, 20);
        informationPanel.add(new JLabel(String.valueOf(currentVolunteer.getId())), gbc);

        //First Name Label
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.insets = new Insets(10, 20, 10, 50);
        informationPanel.add(new JLabel("First Name:"), gbc);

        //First Name Information from Database
        gbc.gridx = 1;
        gbc.insets = new Insets(10, 5, 10, 20);
        informationPanel.add(new JLabel(currentVolunteer.getFirstName()), gbc);

        //Last Name Label
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.insets = new Insets(10, 20, 10, 50);
        informationPanel.add(new JLabel("Last Name:"), gbc);

        //Last Name Information from Database
        gbc.gridx = 1;
        gbc.insets = new Insets(10, 5, 10, 20);
        informationPanel.add(new JLabel(currentVolunteer.getLastName()), gbc);

        //Email Label
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.insets = new Insets(10, 20, 10, 50);
        informationPanel.add(new JLabel("Email:"), gbc);

        //Email Information from Database
        gbc.gridx = 1;
        gbc.insets = new Insets(10, 5, 10, 20);
        informationPanel.add(new JLabel(currentVolunteer.getEmail()), gbc);

        //Phone Number Label
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.insets = new Insets(10, 20, 10, 50);
        informationPanel.add(new JLabel("Phone Number:"), gbc);

        //Phone Number Information from Database
        gbc.gridx = 1;
        gbc.insets = new Insets(10, 5, 10, 20);
        informationPanel.add(new JLabel(currentVolunteer.getPhoneNumber()), gbc);

        //Status Label
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.insets = new Insets(10, 20, 10, 50);
        informationPanel.add(new JLabel("Status:"), gbc);

        //Status Information from Database
        gbc.gridx = 1;
        gbc.insets = new Insets(10, 5, 10, 20);
        informationPanel.add(new JLabel(currentVolunteer.getStatus()), gbc);

        //Date Joined Label
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.insets = new Insets(10, 20, 10, 50);
        informationPanel.add(new JLabel("Date Joined:"), gbc);

        //Date Joined Information from Database
        gbc.gridx = 1;
        gbc.insets = new Insets(10, 5, 10, 20);
        informationPanel.add(new JLabel(String.valueOf(currentVolunteer.getDateJoined())), gbc);

        JButton editProfile = new JButton("Edit Profile");
        profileContentPanel.add(editProfile, BorderLayout.SOUTH);
        profileContentPanel.add(informationPanel, BorderLayout.CENTER);

        //Button Function
        editProfile.addActionListener((ActionEvent e) -> {
            editProfile(informationPanel);
        });

        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.setOpaque(false);
        wrapperPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        wrapperPanel.add(profileContentPanel, BorderLayout.CENTER);

        profilePanel.add(topBar, BorderLayout.NORTH);
        profilePanel.add(wrapperPanel, BorderLayout.CENTER);

        return profilePanel;
    }

    private static void editProfile(JPanel informationPanel) {
        JFrame editProfileFrame = new JFrame("Edit Volunteer Profile");
        editProfileFrame.setSize(500, 350);
        editProfileFrame.setLocationRelativeTo(null);
        editProfileFrame.setLayout(new BorderLayout());

        Volunteer currentUser = Session.getLoggedInVolunteer();

        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridy = 0;

        // Email
        gbc.gridx = 0;
        gbc.insets = new Insets(10, 20, 10, 50);
        mainPanel.add(new JLabel("Email Address:"), gbc);

        gbc.gridx = 1;
        gbc.insets = new Insets(10, 5, 10, 20);
        JTextField emailField = new JTextField(currentUser.getEmail());
        emailField.setPreferredSize(new Dimension(200, 25));
        emailField.setEditable(false);
        RoundedBorder.createTextFieldRoundedBorder(emailField, 10, Color.WHITE, Color.BLACK);
        mainPanel.add(emailField, gbc);

        // Password
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.insets = new Insets(10, 20, 10, 50);
        mainPanel.add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        gbc.insets = new Insets(10, 5, 10, 20);
        JPasswordField passwordField = new JPasswordField(currentUser.getPassword());
        passwordField.setPreferredSize(new Dimension(200, 25));
        RoundedBorder.createTextFieldRoundedBorder(passwordField, 10, Color.WHITE, Color.BLACK);
        mainPanel.add(passwordField, gbc);

        // First Name
        gbc.gridy++;
        gbc.gridx = 0;
        mainPanel.add(new JLabel("First Name:"), gbc);

        gbc.gridx = 1;
        JTextField firstNameField = new JTextField(currentUser.getFirstName());
        firstNameField.setPreferredSize(new Dimension(200, 25));
        RoundedBorder.createTextFieldRoundedBorder(firstNameField, 10, Color.WHITE, Color.BLACK);
        mainPanel.add(firstNameField, gbc);

        // Last Name
        gbc.gridy++;
        gbc.gridx = 0;
        mainPanel.add(new JLabel("Last Name:"), gbc);

        gbc.gridx = 1;
        JTextField lastNameField = new JTextField(currentUser.getLastName());
        lastNameField.setPreferredSize(new Dimension(200, 25));
        RoundedBorder.createTextFieldRoundedBorder(lastNameField, 10, Color.WHITE, Color.BLACK);
        mainPanel.add(lastNameField, gbc);

        // Phone
        gbc.gridy++;
        gbc.gridx = 0;
        mainPanel.add(new JLabel("Phone Number:"), gbc);

        gbc.gridx = 1;
        JTextField phoneField = new JTextField(currentUser.getPhoneNumber());
        phoneField.setPreferredSize(new Dimension(200, 25));
        RoundedBorder.createTextFieldRoundedBorder(phoneField, 10, Color.WHITE, Color.BLACK);
        mainPanel.add(phoneField, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel();
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        saveButton.setForeground(Color.WHITE);
        saveButton.setBorder(RoundedBorder.createButtonRoundedBorder(saveButton, 10, new Color(102, 153, 204)));

        cancelButton.setForeground(Color.WHITE);
        cancelButton.setBorder(RoundedBorder.createButtonRoundedBorder(cancelButton, 10, new Color(255, 153, 51)));

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        // Save button logic
        saveButton.addActionListener(e -> {
            String password = new String(passwordField.getPassword());
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String phone = phoneField.getText();

            if (password.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || phone.isEmpty()) {
                JOptionPane.showMessageDialog(editProfileFrame, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                currentUser.setPassword(password);
                currentUser.setFirstName(firstName);
                currentUser.setLastName(lastName);
                currentUser.setPhoneNumber(phone);

                boolean success = VolunteerController.updateVolunteerProfile(currentUser);

                if (success) {
                    Session.setLoggedInVolunteer(currentUser);
                    JOptionPane.showMessageDialog(editProfileFrame, "Edit successful!");
                    editProfileFrame.dispose();

                    VolunteerController.refreshVolunteerProfile(informationPanel);
                } else {
                    JOptionPane.showMessageDialog(editProfileFrame, "Edit failed.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        cancelButton.addActionListener(e -> editProfileFrame.dispose());

        JPanel wrapPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        wrapPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        wrapPanel.add(mainPanel);

        JPanel wrapButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        wrapButtonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        wrapButtonPanel.add(buttonPanel);

        editProfileFrame.add(wrapPanel, BorderLayout.CENTER);
        editProfileFrame.add(wrapButtonPanel, BorderLayout.SOUTH);
        editProfileFrame.setVisible(true);
    }
}