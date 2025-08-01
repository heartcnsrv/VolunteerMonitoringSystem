package View.Organization;

import Controller.Organization.OrganizationController;
import Controller.Organization.OrganizationRefreshTable;
import Controller.Volunteer.VolunteerController;
import Model.Organization;
import Model.Session;
import Model.Volunteer;
import View.LoginView;
import View.RoundedBorder;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;

public class OrganizationView {
    private static JTable hourLogsTable, participationTable;

    public static void organizationView() {
        // Main Frame Setup
        JFrame organizationFrame = new JFrame("Organization");
        organizationFrame.setSize(1000, 700);
        organizationFrame.setLocationRelativeTo(null);
        organizationFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        organizationFrame.setLayout(new BorderLayout());

        //Navigation Panel
        JPanel navigationPanel = new JPanel();
        navigationPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 5));
        navigationPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        navigationPanel.setBackground(new Color(11, 80, 140));
        navigationPanel.setPreferredSize(new Dimension(150, 0));

        //NAvigation Buttons
        JButton ServiceButton = new JButton("Service");
        JButton volunteersButton = new JButton("Volunteers");
        JButton volunteerLogsButton = new JButton("Logs");
        JButton organizationProfileButton = new JButton("Profile");
        JButton logoutButton = new JButton("Logout");

        //Customize buttons
        customizeButton(ServiceButton);
        customizeButton(volunteersButton);
        customizeButton(volunteerLogsButton);
        customizeButton(organizationProfileButton);
        customizeButton(logoutButton);

        //Add navigation panel contents to panel
        navigationPanel.add(ServiceButton);
        navigationPanel.add(volunteersButton);
        navigationPanel.add(volunteerLogsButton);
        navigationPanel.add(organizationProfileButton);
        navigationPanel.add(logoutButton);

        organizationFrame.add(navigationPanel, BorderLayout.WEST);

        //Main panel and card layout
        CardLayout cardLayout = new CardLayout();
        JPanel contentPanel = new JPanel(cardLayout);

        //Spacing between main panel and nav panel
        contentPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

        organizationFrame.add(contentPanel, BorderLayout.CENTER);

        //Create Panels for CardLayout
        JPanel communityServicePanel = createServicePanel();
        JPanel volunteersPanel = createVolunteersPanel();
        JPanel volunteerLogsPanel = createVolunteerLogsPanel();
        JPanel organizationProfilePanel = createOrganizationProfile();

        //Add Panels to main panel of the frame
        contentPanel.add(communityServicePanel, "Service");
        contentPanel.add(volunteersPanel, "Volunteers");
        contentPanel.add(volunteerLogsPanel, "VolunteerLogs");
        contentPanel.add(organizationProfilePanel, "OrganizationProfile");

        //Set the default view as community service panel
        cardLayout.show(contentPanel, "CommunityService");

        //Button Functions
        ServiceButton.addActionListener(e -> cardLayout.show(contentPanel, "Service"));
        volunteersButton.addActionListener(e -> cardLayout.show(contentPanel, "Volunteers"));
        volunteerLogsButton.addActionListener(e -> {
            cardLayout.show(contentPanel, "VolunteerLogs");
            OrganizationRefreshTable.refreshVolunteerParticipationRecordsTable(participationTable);
            OrganizationRefreshTable.refreshVolunteerHourLogsTable(hourLogsTable);
        });
        organizationProfileButton.addActionListener(e -> cardLayout.show(contentPanel, "OrganizationProfile"));

        logoutButton.addActionListener(e -> {
            organizationFrame.dispose();
            LoginView.organizationLoginView();
        });

        organizationFrame.setVisible(true);
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

    //Create Panels for Each Button
    private static JPanel createServicePanel() {
        JPanel servicePanel = new JPanel(new BorderLayout());
        servicePanel.setBackground(new Color(241, 195, 91));

        // Community Service Section
        JPanel communityServiceContainer = new JPanel(new BorderLayout(10, 10));
        communityServiceContainer.setOpaque(false);

        //Community Service Title
        JLabel titleLabel = new JLabel("Community Service");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 0));
        communityServiceContainer.add(titleLabel, BorderLayout.NORTH);

        //Community Service Table
        String[] serviceColumns = {"Service ID", "Service Name", "Service Type", "Description"};
        DefaultTableModel serviceModel = new DefaultTableModel(new Object[0][4], serviceColumns);
        JTable serviceTable = new JTable(serviceModel);
        serviceTable.setRowHeight(30);
        serviceTable.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane serviceScrollPane = new JScrollPane(serviceTable);
        serviceScrollPane.setPreferredSize(new Dimension(600, 250));
        OrganizationController.populateCommunityService(serviceTable);
        communityServiceContainer.add(serviceScrollPane, BorderLayout.CENTER); //Location of community service panel

        // Schedule Section
        JPanel scheduleContainer = new JPanel(new BorderLayout(10, 10));
        scheduleContainer.setOpaque(false);

        //Schedule Label
        JLabel scheduleLabel = new JLabel("Schedule");
        scheduleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        scheduleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 0));
        scheduleContainer.add(scheduleLabel, BorderLayout.NORTH);

        //Schedule Table
        String[] scheduleColumns = {"Schedule ID", "Service ID", "Schedule Date", "Start Time", "End Time", "Location", "Max Volunteers"};
        DefaultTableModel scheduleModel = new DefaultTableModel(new Object[0][7], scheduleColumns);
        JTable scheduleTable = new JTable(scheduleModel);
        scheduleTable.setRowHeight(30);
        scheduleTable.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane scheduleScrollPane = new JScrollPane(scheduleTable);
        scheduleScrollPane.setPreferredSize(new Dimension(600, 250));
        OrganizationController.populateSchedule(scheduleTable);
        scheduleContainer.add(scheduleScrollPane, BorderLayout.CENTER);

        //Set color of cells
        scheduleTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                           boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBackground(row % 2 == 0 ? new Color(255, 255, 255) : new Color(206, 229, 242));
                return c;
            }
        });

        //Set color of cells
        serviceTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                           boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBackground(row % 2 == 0 ? new Color(255, 255, 255) : new Color(206, 229, 242));
                return c;
            }
        });

        // Community Service Button Panel
        JPanel serviceButtonPanel = new JPanel();
        serviceButtonPanel.setLayout(new BoxLayout(serviceButtonPanel, BoxLayout.Y_AXIS));
        String[] serviceButtons = {"Add Service", "Edit Service", "Add Schedule"};
        for (String btnText : serviceButtons) {
            JButton button = new JButton(btnText);
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            button.setMaximumSize(new Dimension(150, 40));
            serviceButtonPanel.add(button);
            serviceButtonPanel.add(Box.createVerticalStrut(10));
            serviceButtonPanel.setOpaque(false);

            Color buttonColor = new Color(102, 153, 204);
            button.setForeground(Color.WHITE); //Text color


            //Make button corners rounded
            button.setBorder(RoundedBorder.createButtonRoundedBorder(button, 10, buttonColor));

            //BUTTON FUNCTIONS
            button.addActionListener((ActionEvent e) -> {
                switch (btnText) {
                    case "Add Service":
                        OrganizationServiceTabWindows.addCommunityServiceWindow(serviceTable);
                        break;

                    case "Edit Service":
                        int selectedRow = serviceTable.getSelectedRow();
                        if (selectedRow == -1) {
                            JOptionPane.showMessageDialog(null, "Please select a service before editing it.");
                            return;
                        }

                        String serviceIdServ = serviceTable.getValueAt(selectedRow, 0).toString();
                        String serviceNameServ = serviceTable.getValueAt(selectedRow, 1).toString();
                        String serviceType = serviceTable.getValueAt(selectedRow, 2).toString();
                        String description = serviceTable.getValueAt(selectedRow, 3).toString();

                        OrganizationServiceTabWindows.editServiceWindow(serviceIdServ, serviceNameServ, serviceType, description, serviceTable);
                        break;

                    case "Add Schedule":
                        int selectedRowToAdd = serviceTable.getSelectedRow();
                        if (selectedRowToAdd == -1) {
                            JOptionPane.showMessageDialog(null, "Please select a community service before adding a schedule.");
                        } else {
                            String serviceId = serviceTable.getValueAt(selectedRowToAdd, 0).toString();
                            String serviceName = serviceTable.getValueAt(selectedRowToAdd, 1).toString();

                            // Open Add Schedule Window
                            OrganizationServiceTabWindows.addScheduleWindow(serviceId, serviceName, scheduleTable);
                        }
                        break;
                }
            });
        }

        communityServiceContainer.add(serviceButtonPanel, BorderLayout.EAST);

        //Schedule Button Panel
        JPanel scheduleButtonPanel = new JPanel();
        scheduleButtonPanel.setLayout(new BoxLayout(scheduleButtonPanel, BoxLayout.Y_AXIS));
        String[] scheduleButtons = {"Edit Schedule"};
        for (String btnText : scheduleButtons) {
            JButton button = new JButton(btnText);
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            button.setMaximumSize(new Dimension(150, 40));
            scheduleButtonPanel.add(button);
            scheduleButtonPanel.add(Box.createVerticalStrut(10));
            scheduleButtonPanel.setOpaque(false);

            // Button Customization
            Color buttonColor = new Color(102, 153, 204);
            button.setForeground(Color.WHITE); //Text color


            //Make button corners rounded
            button.setBorder(RoundedBorder.createButtonRoundedBorder(button, 10, buttonColor));

            button.addActionListener((ActionEvent e) -> {
                switch (btnText) {
                    case "Edit Schedule":
                        int selectedRow = scheduleTable.getSelectedRow();
                        if (selectedRow == -1) {
                            JOptionPane.showMessageDialog(null, "Please select a schedule before editing it.");
                            return;
                        }

                        String scheduleId = scheduleTable.getValueAt(selectedRow, 0).toString();
                        String serviceId = scheduleTable.getValueAt(selectedRow, 1).toString();
                        String scheduleDate = scheduleTable.getValueAt(selectedRow, 2).toString();
                        String startTime = scheduleTable.getValueAt(selectedRow, 3).toString();
                        String endTime = scheduleTable.getValueAt(selectedRow, 4).toString();
                        String location = scheduleTable.getValueAt(selectedRow, 5).toString();
                        String maxVolunteers = scheduleTable.getValueAt(selectedRow, 6).toString();

                        String serviceName = OrganizationController.getServiceNameById(serviceId);
                        if (serviceName == null) {
                            JOptionPane.showMessageDialog(null, "Error: Could not retrieve service name for the selected schedule.");
                            return;
                        }

                        OrganizationServiceTabWindows.editScheduleWindow(serviceId, serviceName, scheduleId, scheduleDate, startTime, endTime, location, maxVolunteers, scheduleTable);
                        break;
                }
            });
        }
        scheduleContainer.add(scheduleButtonPanel, BorderLayout.EAST);

        // Main Panel Layout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(2, 1, 20, 20));
        mainPanel.setOpaque(false);
        mainPanel.add(communityServiceContainer);
        mainPanel.add(scheduleContainer);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        servicePanel.add(mainPanel, BorderLayout.CENTER);

        return servicePanel;
    }

    private static JPanel createVolunteersPanel() {
        JPanel volunteersPanel = new JPanel(new BorderLayout());
        volunteersPanel.setBackground(new Color(241, 195, 91));

        // Community Service Section
        JPanel registeredVolunteersContainer = new JPanel(new BorderLayout(10, 10));
        registeredVolunteersContainer.setOpaque(false);

        //Community Service Title
        JLabel titleLabel = new JLabel("Registered Volunteers");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 0));
        registeredVolunteersContainer.add(titleLabel, BorderLayout.NORTH);

        //Community Service Table
        String[] registeredVolunteersColumns = {"Volunteer ID", "First Name", "Last Name", "Email", "Phone Number", "Status", "Date Joined"};
        DefaultTableModel registeredVolunteersModel = new DefaultTableModel(new Object[0][7], registeredVolunteersColumns);
        JTable registeredVolunteersTable = new JTable(registeredVolunteersModel);
        registeredVolunteersTable.setRowHeight(30);
        registeredVolunteersTable.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane registeredVolunteersScrollPane = new JScrollPane(registeredVolunteersTable);
        registeredVolunteersScrollPane.setPreferredSize(new Dimension(600, 250));

        OrganizationController.populateRegisteredVolunteersTable(registeredVolunteersTable);

        registeredVolunteersContainer.add(registeredVolunteersScrollPane, BorderLayout.CENTER); //Location of community service panel

        //Set color of cells
        registeredVolunteersTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                           boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBackground(row % 2 == 0 ? new Color(255, 255, 255) : new Color(206, 229, 242));
                return c;
            }
        });

        //Registered Volunteers Button Panel
        JPanel registeredVolunteersButtonPanel = new JPanel();
        registeredVolunteersButtonPanel.setLayout(new BoxLayout(registeredVolunteersButtonPanel, BoxLayout.Y_AXIS));
        String[] registeredVolunteersButtons = {"Active", "Inactive"};
        for (String btnText : registeredVolunteersButtons) {
            JButton button = new JButton(btnText);
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            button.setMaximumSize(new Dimension(150, 40));
            registeredVolunteersButtonPanel.add(button);
            registeredVolunteersButtonPanel.add(Box.createVerticalStrut(10));
            registeredVolunteersButtonPanel.setOpaque(false);

            Color buttonColor = new Color(102, 153, 204);
            button.setForeground(Color.WHITE); //Text color


            //Make button corners rounded
            button.setBorder(RoundedBorder.createButtonRoundedBorder(button, 10, buttonColor));

            //BUTTON FUNCTIONS
            button.addActionListener((ActionEvent e) -> {
                switch (btnText) {
                    case "Active":
                        OrganizationController.updateRegisteredVolunteerStatus(registeredVolunteersTable, true);
                        break;

                    case "Inactive":
                        OrganizationController.updateRegisteredVolunteerStatus(registeredVolunteersTable, false);
                        break;
                }
            });
        }

        registeredVolunteersContainer.add(registeredVolunteersButtonPanel, BorderLayout.EAST);

        //Participation Requests Section
        JPanel requestsContainer = new JPanel(new BorderLayout(10, 10));
        requestsContainer.setOpaque(false);

        //Schedule Label
        JLabel requestsLabel = new JLabel("Volunteer Participation Requests");
        requestsLabel.setFont(new Font("Arial", Font.BOLD, 20));
        requestsLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 0));
        requestsContainer.add(requestsLabel, BorderLayout.NORTH);

        //Schedule Table
        String[] requestsColumns = {"Volunteer ID", "Service ID", "Schedule ID", "Confirmation Status", "Role"};
        DefaultTableModel requestsModel = new DefaultTableModel(new Object[0][5], requestsColumns);
        JTable requestsTable = new JTable(requestsModel);
        requestsTable.setRowHeight(30);
        requestsTable.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane requestsScrollPane = new JScrollPane(requestsTable);
        requestsScrollPane.setPreferredSize(new Dimension(600, 250));

        OrganizationController.populateVolunteerParticipationRequestsTable(requestsTable);
        requestsContainer.add(requestsScrollPane, BorderLayout.CENTER);

        //Set color of cells
        requestsTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                           boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBackground(row % 2 == 0 ? new Color(255, 255, 255) : new Color(206, 229, 242));
                return c;
            }
        });

        //Schedule Button Panel
        JPanel requestsButtonPanel = new JPanel();
        requestsButtonPanel.setLayout(new BoxLayout(requestsButtonPanel, BoxLayout.Y_AXIS));
        String[] requestsButtons = {"Confirm"};
        for (String btnText : requestsButtons) {
            JButton button = new JButton(btnText);
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            button.setMaximumSize(new Dimension(150, 40));
            requestsButtonPanel.add(button);
            requestsButtonPanel.add(Box.createVerticalStrut(10));
            requestsButtonPanel.setOpaque(false);

            // Button Customization
            Color buttonColor = new Color(102, 153, 204);
            button.setForeground(Color.WHITE); //Text color


            //Make button corners rounded
            button.setBorder(RoundedBorder.createButtonRoundedBorder(button, 10, buttonColor));

            button.addActionListener((ActionEvent e) -> {
                OrganizationController.updateVolunteerParticipationRequestsStatus(requestsTable);
            });
        }
        requestsContainer.add(requestsButtonPanel, BorderLayout.EAST);

        // Main Panel Layout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(2, 1, 20, 20));
        mainPanel.setOpaque(false);
        mainPanel.add(registeredVolunteersContainer);
        mainPanel.add(requestsContainer);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        volunteersPanel.add(mainPanel, BorderLayout.CENTER);

        return volunteersPanel;
    }

    private static JPanel createVolunteerLogsPanel() {
        JPanel volunteerLogsPanel = new JPanel(new BorderLayout());
        volunteerLogsPanel.setBackground(new Color(241, 195, 91));

        // Main Panel to hold both tables
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(2, 1, 20, 20));
        mainPanel.setOpaque(false);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // === Volunteer Participation Records Section ===
        JPanel volunteerParticipationContainer = new JPanel(new BorderLayout(10, 10));
        volunteerParticipationContainer.setOpaque(false);

        // Volunteer Participation Records Title
        JLabel participationLabel = new JLabel("Volunteer Participation Records");
        participationLabel.setFont(new Font("Arial", Font.BOLD, 20));
        participationLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 0));
        volunteerParticipationContainer.add(participationLabel, BorderLayout.NORTH);

        // Volunteer Participation Records Table
        String[] volunteerParticipationColumns = {"Volunteer ID", "Service ID", "Schedule ID", "Location", "First Name", "Last Name", "Phone Number"};
        DefaultTableModel volunteerParticipationModel = new DefaultTableModel(new Object[0][7], volunteerParticipationColumns);
        participationTable = new JTable(volunteerParticipationModel);
        participationTable.setRowHeight(30);
        participationTable.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane volunteerParticipationScrollPane = new JScrollPane(participationTable);
        volunteerParticipationScrollPane.setPreferredSize(new Dimension(600, 250));

        OrganizationController.populateVolunteerParticipationRecordsTable(participationTable);
        volunteerParticipationContainer.add(volunteerParticipationScrollPane, BorderLayout.CENTER);

        // Set alternating row colors
        participationTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                           boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBackground(row % 2 == 0 ? new Color(255, 255, 255) : new Color(206, 229, 242));
                return c;
            }
        });

        // === Volunteer Hour Logs Section ===
        JPanel volunteerHourLogsContainer = new JPanel(new BorderLayout(10, 10));
        volunteerHourLogsContainer.setOpaque(false);

        // Volunteer Hour Logs Title
        JLabel hourLogsLabel = new JLabel("Volunteer Hour Logs");
        hourLogsLabel.setFont(new Font("Arial", Font.BOLD, 20));
        hourLogsLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 0));
        volunteerHourLogsContainer.add(hourLogsLabel, BorderLayout.NORTH);

        // Volunteer Hour Logs Table
        String[] hourLogsColumns = {"Volunteer ID", "Service ID", "Schedule ID", "Hours ID", "Date Logged", "Hours Logged"};
        DefaultTableModel hourLogsModel = new DefaultTableModel(new Object[0][6], hourLogsColumns);
        hourLogsTable = new JTable(hourLogsModel);
        hourLogsTable.setRowHeight(30);
        hourLogsTable.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane hourLogsScrollPane = new JScrollPane(hourLogsTable);
        hourLogsScrollPane.setPreferredSize(new Dimension(600, 250));

        OrganizationController.populateVolunteerHourLogsTable(hourLogsTable);
        volunteerHourLogsContainer.add(hourLogsScrollPane, BorderLayout.CENTER);

        // Set alternating row colors
        hourLogsTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                           boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBackground(row % 2 == 0 ? new Color(255, 255, 255) : new Color(206, 229, 242));
                return c;
            }
        });

        // Add both sections to the main panel
        mainPanel.add(volunteerParticipationContainer);
        mainPanel.add(volunteerHourLogsContainer);

        // Add the main panel to the volunteer logs panel
        volunteerLogsPanel.add(mainPanel, BorderLayout.CENTER);

        return volunteerLogsPanel;
    }

    private static JPanel createOrganizationProfile() {
        JPanel profilePanel = new JPanel(new BorderLayout());
        Color baseColor = Color.decode("#F1CC5C");
        profilePanel.setBackground(baseColor);
        profilePanel.setPreferredSize(new Dimension(820, 700));

        //Top Bar Panel
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setPreferredSize(new Dimension(820, 50));
        topBar.setOpaque(false);
        topBar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel labelP = new JLabel("Organization Profile");
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

        //Add the image to the profile picture panel using a JLabel
        JLabel profileLabel = new JLabel(profile);
        profilePicturePanel.add(profileLabel, BorderLayout.CENTER);

        //Gather Volunteer Information from the Database
        Organization currentOrganization = Session.getLoggedInOrganization();

        //Profile Information Panel
        JPanel informationPanel = new JPanel(new GridBagLayout());
        informationPanel.setOpaque(false);

        //Text Components
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridy = 0;

        //Organization ID Label
        gbc.gridx = 0;
        gbc.insets = new Insets(10, 20, 10, 50);
        informationPanel.add(new JLabel("Organization ID:"), gbc);

        //Organization ID Information from Database
        gbc.gridx = 1;
        gbc.insets = new Insets(10, 5, 10, 20);
        informationPanel.add(new JLabel(String.valueOf(currentOrganization.getOrgID())), gbc);

        //Organization Name Label
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.insets = new Insets(10, 20, 10, 50);
        informationPanel.add(new JLabel("Organization Name:"), gbc);

        //Organization Name Information from Database
        gbc.gridx = 1;
        gbc.insets = new Insets(10, 5, 10, 20);
        informationPanel.add(new JLabel(currentOrganization.getOrgName()), gbc);

        //Contact Info Label
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.insets = new Insets(10, 20, 10, 50);
        informationPanel.add(new JLabel("Contact Info:"), gbc);

        //Contact Information from Database
        gbc.gridx = 1;
        gbc.insets = new Insets(10, 5, 10, 20);
        informationPanel.add(new JLabel(currentOrganization.getContactInfo()), gbc);

        //Website Label
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.insets = new Insets(10, 20, 10, 50);
        informationPanel.add(new JLabel("Website:"), gbc);

        //Website Information from Database
        gbc.gridx = 1;
        gbc.insets = new Insets(10, 5, 10, 20);
        informationPanel.add(new JLabel(currentOrganization.getWebsite()), gbc);

        //Address Label
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.insets = new Insets(10, 20, 10, 50);
        informationPanel.add(new JLabel("Address:"), gbc);

        //Address Information from Database
        gbc.gridx = 1;
        gbc.insets = new Insets(10, 5, 10, 20);
        informationPanel.add(new JLabel(currentOrganization.getAddress()), gbc);

        //Mission Statement Label
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.insets = new Insets(10, 20, 10, 50);
        informationPanel.add(new JLabel("Mission Statement:"), gbc);

        //Mission Statement from Database
        gbc.gridx = 1;
        gbc.insets = new Insets(10, 5, 10, 20);
        informationPanel.add(new JLabel(currentOrganization.getMissionStatement()), gbc);

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
        JFrame editProfileFrame = new JFrame("Edit Organization Profile");
        editProfileFrame.setSize(500, 350);
        editProfileFrame.setLocationRelativeTo(null);
        editProfileFrame.setLayout(new BorderLayout());

        Organization currentUser = Session.getLoggedInOrganization();

        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridy = 0;

        //Organization
        gbc.gridx = 0;
        gbc.insets = new Insets(10, 20, 10, 50);
        mainPanel.add(new JLabel("Organization Name:"), gbc);

        gbc.gridx = 1;
        gbc.insets = new Insets(10, 5, 10, 20);
        JTextField nameField = new JTextField(String.valueOf(currentUser.getOrgName()));
        nameField.setPreferredSize(new Dimension(200, 25));
        RoundedBorder.createTextFieldRoundedBorder(nameField, 10, Color.WHITE, Color.BLACK);
        mainPanel.add(nameField, gbc);

        //Password
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

        //Contact Information
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.insets = new Insets(10, 20, 10, 50);
        mainPanel.add(new JLabel("Contact Information:"), gbc);

        gbc.gridx = 1;
        gbc.insets = new Insets(10, 5, 10, 20);
        JTextField contactInfoField = new JTextField(currentUser.getContactInfo());
        contactInfoField.setPreferredSize(new Dimension(200, 25));
        RoundedBorder.createTextFieldRoundedBorder(contactInfoField, 10, Color.WHITE, Color.BLACK);
        mainPanel.add(contactInfoField, gbc);

        //Website
        gbc.gridy++;
        gbc.gridx = 0;
        mainPanel.add(new JLabel("Website:"), gbc);

        gbc.gridx = 1;
        JTextField websiteField = new JTextField(currentUser.getWebsite());
        websiteField.setPreferredSize(new Dimension(200, 25));
        RoundedBorder.createTextFieldRoundedBorder(websiteField, 10, Color.WHITE, Color.BLACK);
        mainPanel.add(websiteField, gbc);

        //Address
        gbc.gridy++;
        gbc.gridx = 0;
        mainPanel.add(new JLabel("Address:"), gbc);

        gbc.gridx = 1;
        JTextField addressField = new JTextField(currentUser.getAddress());
        addressField.setPreferredSize(new Dimension(200, 25));
        RoundedBorder.createTextFieldRoundedBorder(addressField, 10, Color.WHITE, Color.BLACK);
        mainPanel.add(addressField, gbc);

        //Mission Statement
        gbc.gridy++;
        gbc.gridx = 0;
        mainPanel.add(new JLabel("Mission Statement:"), gbc);

        gbc.gridx = 1;
        JTextField missionStatementField = new JTextField(currentUser.getMissionStatement());
        missionStatementField.setPreferredSize(new Dimension(200, 25));
        RoundedBorder.createTextFieldRoundedBorder(missionStatementField, 10, Color.WHITE, Color.BLACK);
        mainPanel.add(missionStatementField, gbc);

        //Buttons
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
            String orgName = nameField.getText();
            String contactInfo = contactInfoField.getText();
            String address = addressField.getText();
            String password = passwordField.getText();
            String website = websiteField.getText();
            String missionStatement = missionStatementField.getText();

            if (orgName.isEmpty() || contactInfo.isEmpty() || address.isEmpty() || password.isEmpty() || website.isEmpty() || missionStatement.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill in all fields.");
            } else {
                currentUser.setOrgName(orgName);
                currentUser.setContactInfo(contactInfo);
                currentUser.setAddress(address);
                currentUser.setPassword(password);
                currentUser.setWebsite(website);
                currentUser.setMissionStatement(missionStatement);

                boolean success = OrganizationController.updateOrganizationProfile(currentUser);

                if (success) {
                    Session.setLoggedInOrganization(currentUser);
                    JOptionPane.showMessageDialog(null, "Profile updated successfully.");
                    editProfileFrame.dispose();

                    OrganizationController.refreshOrganizationProfile(informationPanel);
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to update profile.");
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