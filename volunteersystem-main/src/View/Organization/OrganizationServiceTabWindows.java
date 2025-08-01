package View.Organization;

import Controller.Organization.OrganizationController;
import View.RoundedBorder;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Date;
import java.sql.Time;

public class OrganizationServiceTabWindows {
    //ADD COMMUNITY SERVICE WINDOW
    public static void addCommunityServiceWindow(JTable table) {
        JFrame scheduleFrame = new JFrame("Add Community Service"); //Add Community Service Frame

        //Components of Register Frame
        scheduleFrame.setSize(500, 330);
        scheduleFrame.setLocationRelativeTo(null);
        scheduleFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        scheduleFrame.setLayout(new BorderLayout());
        scheduleFrame.setUndecorated(true);

        //Title Bar of Register Frame
        JPanel titlePanel = new JPanel(new BorderLayout());

        //Color Customization
        Color baseColor = Color.decode("#01579B"); //Hexadecimal Color Code
        Color withColorOpacity = new Color(baseColor.getRed(), baseColor.getGreen(), baseColor.getBlue(), 100); //Get Color with Opacity
        titlePanel.setBackground(withColorOpacity); //Set Background Color

        JLabel titleLabel = new JLabel("Add Community Service");
        titleLabel.setBorder(new EmptyBorder(5, 20, 5, 0)); //Padding of Title Label
        titleLabel.setForeground(Color.WHITE); //Text Color of Title Label
        titleLabel.setFont(new Font("Inter", Font.PLAIN, 12)); //Font of Title Label
        titlePanel.add(titleLabel, BorderLayout.WEST);

        //Main Panel of Register Frame
        JPanel mainPanel = new JPanel(new GridBagLayout());

        //Text Field Components
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridy = 0;

        //Service ID Text Label
        gbc.gridx = 0;
        gbc.insets = new Insets(10, 20, 10, 50);
        mainPanel.add(new JLabel("Service ID:"), gbc);

        //Service ID Text Field
        gbc.gridx = 1;
        gbc.insets = new Insets(10, 5, 10, 20);
        JTextField serviceIDField = new JTextField();
        serviceIDField.setPreferredSize(new Dimension(200, 25));
        RoundedBorder.createTextFieldRoundedBorder(serviceIDField,10, Color.WHITE, Color.BLACK);
        mainPanel.add(serviceIDField, gbc);

        //Service Name Text Label
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.insets = new Insets(10, 20, 10, 50);
        mainPanel.add(new JLabel("Service Name:"), gbc);

        //Service Name Text Field
        gbc.gridx = 1;
        gbc.insets = new Insets(10, 5, 10, 20);
        JTextField serviceNameField = new JTextField();
        serviceNameField.setPreferredSize(new Dimension(200, 25));
        RoundedBorder.createTextFieldRoundedBorder(serviceNameField,10, Color.WHITE, Color.BLACK);
        mainPanel.add(serviceNameField, gbc);

        //Service Type Text Label
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.insets = new Insets(10, 20, 10, 50);
        mainPanel.add(new JLabel("Service Type:"), gbc);

        //Start Time Text Field
        gbc.gridx = 1;
        gbc.insets = new Insets(10, 5, 10, 20);
        JTextField serviceTypeField = new JTextField();
        serviceTypeField.setPreferredSize(new Dimension(200, 25));
        RoundedBorder.createTextFieldRoundedBorder(serviceTypeField,10, Color.WHITE, Color.BLACK);
        mainPanel.add(serviceTypeField, gbc);

        //Description Text Label
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.insets = new Insets(10, 20, 10, 50);
        mainPanel.add(new JLabel("Description:"), gbc);

        //End Time Text Field
        gbc.gridx = 1;
        gbc.insets = new Insets(10, 5, 10, 20);
        JTextField descriptionField = new JTextField();
        descriptionField.setPreferredSize(new Dimension(200, 25));
        RoundedBorder.createTextFieldRoundedBorder(descriptionField,10, Color.WHITE, Color.BLACK);
        mainPanel.add(descriptionField, gbc);

        //Button Panel of Register Frame
        JPanel buttonPanel = new JPanel();
        JButton addServiceButton = new JButton("Add Service");
        JButton cancelButton = new JButton("Cancel");

        Color addScheduleButtonColor = new Color(102, 153, 204);
        Color cancelButtonColor = new Color(255, 153, 51);

        addServiceButton.setForeground(Color.WHITE);
        addServiceButton.setBorder(RoundedBorder.createButtonRoundedBorder(addServiceButton, 10, addScheduleButtonColor));

        cancelButton.setForeground(Color.WHITE);
        cancelButton.setBorder(RoundedBorder.createButtonRoundedBorder(cancelButton, 10, cancelButtonColor));

        buttonPanel.add(addServiceButton);
        buttonPanel.add(cancelButton);

        //Button Functions
        addServiceButton.addActionListener((ActionEvent e) -> {
            int serviceIdInt = Integer.parseInt(serviceIDField.getText());
            String serviceName = serviceNameField.getText();
            String serviceType = serviceTypeField.getText();
            String description = descriptionField.getText();

           if (serviceIdInt == 0 || serviceName.isEmpty() || serviceType.isEmpty() || description.isEmpty()) {
               JOptionPane.showMessageDialog(null, "Please fill in all fields!");
           } else {
                OrganizationController.addCommunityService(serviceIdInt, serviceName, serviceType, description, table);
                scheduleFrame.dispose();
           }
        });

        cancelButton.addActionListener((ActionEvent e) -> {
            scheduleFrame.dispose();
        });

        //Wrap Components in Panel
        JPanel wrapPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        wrapPanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));
        wrapPanel.add(mainPanel);

        JPanel wrapButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        wrapButtonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 15));
        wrapButtonPanel.add(buttonPanel);

        //Add Components to Register Frame
        scheduleFrame.add(wrapPanel, BorderLayout.CENTER);
        scheduleFrame.add(wrapButtonPanel, BorderLayout.SOUTH);
        scheduleFrame.add(titlePanel, BorderLayout.NORTH);
        scheduleFrame.setVisible(true);
    }

    public static void editServiceWindow(String serviceId, String serviceName, String serviceType, String description, JTable table) {
        JFrame editCommunityServiceFrame = new JFrame("Edit Community Service"); //Edit Community Service Frame

        //Components of Edit Frame
        editCommunityServiceFrame.setSize(500, 330);
        editCommunityServiceFrame.setLocationRelativeTo(null);
        editCommunityServiceFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        editCommunityServiceFrame.setLayout(new BorderLayout());
        editCommunityServiceFrame.setUndecorated(true);

        //Title Bar of Register Frame
        JPanel titlePanel = new JPanel(new BorderLayout());

        //Color Customization
        Color baseColor = Color.decode("#01579B"); //Hexadecimal Color Code
        Color withColorOpacity = new Color(baseColor.getRed(), baseColor.getGreen(), baseColor.getBlue(), 100); //Get Color with Opacity
        titlePanel.setBackground(withColorOpacity); //Set Background Color

        JLabel titleLabel = new JLabel("Add Community Service");
        titleLabel.setBorder(new EmptyBorder(5, 20, 5, 0)); //Padding of Title Label
        titleLabel.setForeground(Color.WHITE); //Text Color of Title Label
        titleLabel.setFont(new Font("Inter", Font.PLAIN, 12)); //Font of Title Label
        titlePanel.add(titleLabel, BorderLayout.WEST);

        //Main Panel of Register Frame
        JPanel mainPanel = new JPanel(new GridBagLayout());

        //Text Field Components
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridy = 0;

        //Service ID Text Label
        gbc.gridx = 0;
        gbc.insets = new Insets(10, 20, 10, 50);
        mainPanel.add(new JLabel("Service ID:"), gbc);

        //Service ID Text Field
        gbc.gridx = 1;
        gbc.insets = new Insets(10, 5, 10, 20);
        JTextField serviceIDField = new JTextField(serviceId);
        serviceIDField.setPreferredSize(new Dimension(200, 25));
        serviceIDField.setEditable(false); //Make text field not editable
        RoundedBorder.createTextFieldRoundedBorder(serviceIDField,10, Color.WHITE, Color.BLACK);
        mainPanel.add(serviceIDField, gbc);

        //Service Name Text Label
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.insets = new Insets(10, 20, 10, 50);
        mainPanel.add(new JLabel("Service Name:"), gbc);

        //Service Name Text Field
        gbc.gridx = 1;
        gbc.insets = new Insets(10, 5, 10, 20);
        JTextField serviceNameField = new JTextField(serviceName);
        serviceNameField.setPreferredSize(new Dimension(200, 25));
        serviceNameField.setEditable(false); //Make text field not editable
        RoundedBorder.createTextFieldRoundedBorder(serviceNameField,10, Color.WHITE, Color.BLACK);
        mainPanel.add(serviceNameField, gbc);

        //Service Type Text Label
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.insets = new Insets(10, 20, 10, 50);
        mainPanel.add(new JLabel("Service Type:"), gbc);

        //Start Time Text Field
        gbc.gridx = 1;
        gbc.insets = new Insets(10, 5, 10, 20);
        JTextField serviceTypeField = new JTextField(serviceType);
        serviceTypeField.setPreferredSize(new Dimension(200, 25));
        RoundedBorder.createTextFieldRoundedBorder(serviceTypeField,10, Color.WHITE, Color.BLACK);
        mainPanel.add(serviceTypeField, gbc);

        //Description Text Label
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.insets = new Insets(10, 20, 10, 50);
        mainPanel.add(new JLabel("Description:"), gbc);

        //End Time Text Field
        gbc.gridx = 1;
        gbc.insets = new Insets(10, 5, 10, 20);
        JTextField descriptionField = new JTextField(description);
        descriptionField.setPreferredSize(new Dimension(200, 25));
        RoundedBorder.createTextFieldRoundedBorder(descriptionField,10, Color.WHITE, Color.BLACK);
        mainPanel.add(descriptionField, gbc);

        //Button Panel of Register Frame
        JPanel buttonPanel = new JPanel();
        JButton editServiceButton = new JButton("Edit Service");
        JButton cancelButton = new JButton("Cancel");

        Color addScheduleButtonColor = new Color(102, 153, 204);
        Color cancelButtonColor = new Color(255, 153, 51);

        editServiceButton.setForeground(Color.WHITE);
        editServiceButton.setBorder(RoundedBorder.createButtonRoundedBorder(editServiceButton, 10, addScheduleButtonColor));

        cancelButton.setForeground(Color.WHITE);
        cancelButton.setBorder(RoundedBorder.createButtonRoundedBorder(cancelButton, 10, cancelButtonColor));

        buttonPanel.add(editServiceButton);
        buttonPanel.add(cancelButton);

        //Button Functions
        editServiceButton.addActionListener((ActionEvent e) -> {
            int serviceIdInt = Integer.parseInt(serviceIDField.getText());
            String serviceNameUpdated = serviceNameField.getText();
            String serviceTypeUpdated = serviceTypeField.getText();
            String descriptionUpdated = descriptionField.getText();

            if (serviceIdInt == 0 || serviceNameUpdated.isEmpty() || serviceTypeUpdated.isEmpty() || descriptionUpdated.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill in all fields!");
            } else {
                OrganizationController.editCommunityService(serviceIdInt, serviceNameUpdated, serviceTypeUpdated, descriptionUpdated, table);
                editCommunityServiceFrame.dispose();
            }
        });

        cancelButton.addActionListener((ActionEvent e) -> {
            editCommunityServiceFrame.dispose();
        });

        //Wrap Components in Panel
        JPanel wrapPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        wrapPanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));
        wrapPanel.add(mainPanel);

        JPanel wrapButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        wrapButtonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 15));
        wrapButtonPanel.add(buttonPanel);

        //Add Components to Register Frame
        editCommunityServiceFrame.add(wrapPanel, BorderLayout.CENTER);
        editCommunityServiceFrame.add(wrapButtonPanel, BorderLayout.SOUTH);
        editCommunityServiceFrame.add(titlePanel, BorderLayout.NORTH);
        editCommunityServiceFrame.setVisible(true);
    }

    //OPEN SCHEDULE WINDOW FOR ADDING A SCHEDULE TO A COMMUNITY SERVICE
    public static void addScheduleWindow(String serviceId, String serviceName, JTable table) {
        JFrame scheduleFrame = new JFrame("Add Schedule"); //Add Schedule Frame

        //Components of Register Frame
        scheduleFrame.setSize(800, 330);
        scheduleFrame.setLocationRelativeTo(null);
        scheduleFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        scheduleFrame.setLayout(new BorderLayout());
        scheduleFrame.setUndecorated(true);

        //Title Bar of Register Frame
        JPanel titlePanel = new JPanel(new BorderLayout());

        //Color Customization
        Color baseColor = Color.decode("#01579B"); //Hexadecimal Color Code
        Color withColorOpacity = new Color(baseColor.getRed(), baseColor.getGreen(), baseColor.getBlue(), 100); //Get Color with Opacity
        titlePanel.setBackground(withColorOpacity); //Set Background Color

        JLabel titleLabel = new JLabel("Add Schedule");
        titleLabel.setBorder(new EmptyBorder(5, 20, 5, 0)); //Padding of Title Label
        titleLabel.setForeground(Color.WHITE); //Text Color of Title Label
        titleLabel.setFont(new Font("Inter", Font.PLAIN, 12)); //Font of Title Label
        titlePanel.add(titleLabel, BorderLayout.WEST);

        //Main Panel of Register Frame
        JPanel mainPanel = new JPanel(new GridBagLayout());

        //Text Field Components
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridy = 0;

        //Service Name Text Label
        gbc.gridx = 0;
        gbc.insets = new Insets(10, 20, 10, 50);
        mainPanel.add(new JLabel("Service Name:"), gbc);

        //Service Name Text Field
        gbc.gridx = 1;
        gbc.insets = new Insets(10, 5, 10, 20);
        JTextField serviceNameField = new JTextField(serviceName);
        serviceNameField.setPreferredSize(new Dimension(200, 25));
        serviceNameField.setEditable(false); //Make text field not editable
        RoundedBorder.createTextFieldRoundedBorder(serviceNameField,10, Color.WHITE, Color.BLACK);
        mainPanel.add(serviceNameField, gbc);

        //Service ID Text Label
        gbc.gridx = 3;
        gbc.insets = new Insets(10, 20, 10, 50);
        mainPanel.add(new JLabel("Service ID:"), gbc);

        //Service ID Text Field
        gbc.gridx = 4;
        gbc.insets = new Insets(10, 5, 10, 20);
        JTextField serviceIDField = new JTextField(serviceId);
        serviceIDField.setPreferredSize(new Dimension(200, 25));
        RoundedBorder.createTextFieldRoundedBorder(serviceIDField,10, Color.WHITE, Color.BLACK);
        serviceIDField.setEditable(false); //Make text field not editable
        mainPanel.add(serviceIDField, gbc);

        //Schedule Date Text Label
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.insets = new Insets(10, 20, 10, 50);
        mainPanel.add(new JLabel("Schedule Date:"), gbc);

        //Schedule Date Text Field
        gbc.gridx = 1;
        gbc.insets = new Insets(10, 5, 10, 20);
        JTextField scheduleDateField = new JTextField();
        scheduleDateField.setPreferredSize(new Dimension(200, 25));
        RoundedBorder.createTextFieldRoundedBorder(scheduleDateField,10, Color.WHITE, Color.BLACK);
        mainPanel.add(scheduleDateField, gbc);

        //Schedule ID Text Label
        gbc.gridx = 3;
        gbc.insets = new Insets(10, 20, 10, 50);
        mainPanel.add(new JLabel("Schedule ID:"), gbc);

        //Schedule ID Text Field
        gbc.gridx = 4;
        gbc.insets = new Insets(10, 5, 10, 20);
        JTextField scheduleIDField = new JTextField();
        scheduleIDField.setPreferredSize(new Dimension(200, 25));
        RoundedBorder.createTextFieldRoundedBorder(scheduleIDField,10, Color.WHITE, Color.BLACK);
        mainPanel.add(scheduleIDField, gbc);

        //Start Time Text Label
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.insets = new Insets(10, 20, 10, 50);
        mainPanel.add(new JLabel("Start Time:"), gbc);

        //Start Time Text Field
        gbc.gridx = 1;
        gbc.insets = new Insets(10, 5, 10, 20);
        JTextField startTimeField = new JTextField();
        startTimeField.setPreferredSize(new Dimension(200, 25));
        RoundedBorder.createTextFieldRoundedBorder(startTimeField,10, Color.WHITE, Color.BLACK);
        mainPanel.add(startTimeField, gbc);

        //Location Text Label
        gbc.gridx = 3;
        gbc.insets = new Insets(10, 20, 10, 50);
        mainPanel.add(new JLabel("Location:"), gbc);

        //Location Text Field
        gbc.gridx = 4;
        gbc.insets = new Insets(10, 5, 10, 20);
        JTextField locationField = new JTextField();
        locationField.setPreferredSize(new Dimension(200, 25));
        RoundedBorder.createTextFieldRoundedBorder(locationField,10, Color.WHITE, Color.BLACK);
        mainPanel.add(locationField, gbc);

        //End Time Text Label
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.insets = new Insets(10, 20, 10, 50);
        mainPanel.add(new JLabel("End Time:"), gbc);

        //End Time Text Field
        gbc.gridx = 1;
        gbc.insets = new Insets(10, 5, 10, 20);
        JTextField endTimeField = new JTextField();
        endTimeField.setPreferredSize(new Dimension(200, 25));
        RoundedBorder.createTextFieldRoundedBorder(endTimeField,10, Color.WHITE, Color.BLACK);
        mainPanel.add(endTimeField, gbc);

        //Max Volunteers Text Label
        gbc.gridx = 3;
        gbc.insets = new Insets(10, 20, 10, 50);
        mainPanel.add(new JLabel("Max Volunteers:"), gbc);

        //Max Volunteers Text Field
        gbc.gridx = 4;
        gbc.insets = new Insets(10, 5, 10, 20);
        JTextField maxVolunteersField = new JTextField();
        maxVolunteersField.setPreferredSize(new Dimension(200, 25));
        RoundedBorder.createTextFieldRoundedBorder(maxVolunteersField,10, Color.WHITE, Color.BLACK);
        mainPanel.add(maxVolunteersField, gbc);

        //Button Panel of Register Frame
        JPanel buttonPanel = new JPanel();
        JButton addScheduleButton = new JButton("Add Schedule");
        JButton cancelButton = new JButton("Cancel");

        Color addScheduleButtonColor = new Color(102, 153, 204);
        Color cancelButtonColor = new Color(255, 153, 51);

        addScheduleButton.setForeground(Color.WHITE);
        addScheduleButton.setBorder(RoundedBorder.createButtonRoundedBorder(addScheduleButton, 10, addScheduleButtonColor));

        cancelButton.setForeground(Color.WHITE);
        cancelButton.setBorder(RoundedBorder.createButtonRoundedBorder(cancelButton, 10, cancelButtonColor));

        buttonPanel.add(addScheduleButton);
        buttonPanel.add(cancelButton);

        //Button Functions
        addScheduleButton.addActionListener((ActionEvent e) -> {
            int serviceIdInt = Integer.parseInt(serviceIDField.getText());
            int scheduleIdInt = Integer.parseInt(scheduleIDField.getText());
            int maxVolunteersInt = Integer.parseInt(maxVolunteersField.getText());
            Time startTime = Time.valueOf(startTimeField.getText());
            Time endTime = Time.valueOf(endTimeField.getText());
            Date scheduleDate = Date.valueOf(scheduleDateField.getText());
            String location = locationField.getText();

            if (serviceIdInt == 0 || scheduleIdInt == 0 || maxVolunteersInt == 0 || startTime == null || endTime == null || scheduleDate == null || location.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill in all fields!");
            } else {
                OrganizationController.addScheduleToACommunityService(scheduleIdInt, serviceIdInt, scheduleDate, startTime, endTime, location, maxVolunteersInt, table);
                scheduleFrame.dispose();
            }
        });

        cancelButton.addActionListener((ActionEvent e) -> {
            scheduleFrame.dispose();
        });

        //Wrap Components in Panel
        JPanel wrapPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        wrapPanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));
        wrapPanel.add(mainPanel);

        JPanel wrapButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        wrapButtonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 15));
        wrapButtonPanel.add(buttonPanel);

        //Add Components to Register Frame
        scheduleFrame.add(wrapPanel, BorderLayout.CENTER);
        scheduleFrame.add(wrapButtonPanel, BorderLayout.SOUTH);
        scheduleFrame.add(titlePanel, BorderLayout.NORTH);
        scheduleFrame.setVisible(true);
    }

    //EDIT SCHEDULE WINDOW
    public static void editScheduleWindow(String serviceId, String serviceName, String scheduleId, String scheduleDate, String startTime, String endTime, String location, String maxVolunteers, JTable table) {
        JFrame scheduleFrame = new JFrame("Edit Schedule"); //Add Schedule Frame

        //Components of Register Frame
        scheduleFrame.setSize(800, 330);
        scheduleFrame.setLocationRelativeTo(null);
        scheduleFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        scheduleFrame.setLayout(new BorderLayout());
        scheduleFrame.setUndecorated(true);

        //Title Bar of Register Frame
        JPanel titlePanel = new JPanel(new BorderLayout());

        //Color Customization
        Color baseColor = Color.decode("#01579B"); //Hexadecimal Color Code
        Color withColorOpacity = new Color(baseColor.getRed(), baseColor.getGreen(), baseColor.getBlue(), 100); //Get Color with Opacity
        titlePanel.setBackground(withColorOpacity); //Set Background Color

        JLabel titleLabel = new JLabel("Edit Schedule");
        titleLabel.setBorder(new EmptyBorder(5, 20, 5, 0)); //Padding of Title Label
        titleLabel.setForeground(Color.WHITE); //Text Color of Title Label
        titleLabel.setFont(new Font("Inter", Font.PLAIN, 12)); //Font of Title Label
        titlePanel.add(titleLabel, BorderLayout.WEST);

        //Main Panel of Register Frame
        JPanel mainPanel = new JPanel(new GridBagLayout());

        //Text Field Components
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridy = 0;

        //Service Name Text Label
        gbc.gridx = 0;
        gbc.insets = new Insets(10, 20, 10, 50);
        mainPanel.add(new JLabel("Service Name:"), gbc);

        //Service Name Text Field
        gbc.gridx = 1;
        gbc.insets = new Insets(10, 5, 10, 20);
        JTextField serviceNameField = new JTextField(serviceName);
        serviceNameField.setPreferredSize(new Dimension(200, 25));
        serviceNameField.setEditable(false); //Make text field not editable
        RoundedBorder.createTextFieldRoundedBorder(serviceNameField,10, Color.WHITE, Color.BLACK);
        mainPanel.add(serviceNameField, gbc);

        //Service ID Text Label
        gbc.gridx = 3;
        gbc.insets = new Insets(10, 20, 10, 50);
        mainPanel.add(new JLabel("Service ID:"), gbc);

        //Service ID Text Field
        gbc.gridx = 4;
        gbc.insets = new Insets(10, 5, 10, 20);
        JTextField serviceIDField = new JTextField(serviceId);
        serviceIDField.setPreferredSize(new Dimension(200, 25));
        RoundedBorder.createTextFieldRoundedBorder(serviceIDField,10, Color.WHITE, Color.BLACK);
        serviceIDField.setEditable(false); //Make text field not editable
        mainPanel.add(serviceIDField, gbc);

        //Schedule Date Text Label
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.insets = new Insets(10, 20, 10, 50);
        mainPanel.add(new JLabel("Schedule Date:"), gbc);

        //Schedule Date Text Field
        gbc.gridx = 1;
        gbc.insets = new Insets(10, 5, 10, 20);
        JTextField scheduleDateField = new JTextField(scheduleDate);
        scheduleDateField.setPreferredSize(new Dimension(200, 25));
        RoundedBorder.createTextFieldRoundedBorder(scheduleDateField,10, Color.WHITE, Color.BLACK);
        mainPanel.add(scheduleDateField, gbc);

        //Schedule ID Text Label
        gbc.gridx = 3;
        gbc.insets = new Insets(10, 20, 10, 50);
        mainPanel.add(new JLabel("Schedule ID:"), gbc);

        //Schedule ID Text Field
        gbc.gridx = 4;
        gbc.insets = new Insets(10, 5, 10, 20);
        JTextField scheduleIDField = new JTextField(scheduleId);
        scheduleIDField.setPreferredSize(new Dimension(200, 25));
        scheduleIDField.setEditable(false); //Make text field not editable
        RoundedBorder.createTextFieldRoundedBorder(scheduleIDField,10, Color.WHITE, Color.BLACK);
        mainPanel.add(scheduleIDField, gbc);

        //Start Time Text Label
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.insets = new Insets(10, 20, 10, 50);
        mainPanel.add(new JLabel("Start Time:"), gbc);

        //Start Time Text Field
        gbc.gridx = 1;
        gbc.insets = new Insets(10, 5, 10, 20);
        JTextField startTimeField = new JTextField(startTime);
        startTimeField.setPreferredSize(new Dimension(200, 25));
        RoundedBorder.createTextFieldRoundedBorder(startTimeField,10, Color.WHITE, Color.BLACK);
        mainPanel.add(startTimeField, gbc);

        //Location Text Label
        gbc.gridx = 3;
        gbc.insets = new Insets(10, 20, 10, 50);
        mainPanel.add(new JLabel("Location:"), gbc);

        //Location Text Field
        gbc.gridx = 4;
        gbc.insets = new Insets(10, 5, 10, 20);
        JTextField locationField = new JTextField(location);
        locationField.setPreferredSize(new Dimension(200, 25));
        RoundedBorder.createTextFieldRoundedBorder(locationField,10, Color.WHITE, Color.BLACK);
        mainPanel.add(locationField, gbc);

        //End Time Text Label
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.insets = new Insets(10, 20, 10, 50);
        mainPanel.add(new JLabel("End Time:"), gbc);

        //End Time Text Field
        gbc.gridx = 1;
        gbc.insets = new Insets(10, 5, 10, 20);
        JTextField endTimeField = new JTextField(endTime);
        endTimeField.setPreferredSize(new Dimension(200, 25));
        RoundedBorder.createTextFieldRoundedBorder(endTimeField,10, Color.WHITE, Color.BLACK);
        mainPanel.add(endTimeField, gbc);

        //Max Volunteers Text Label
        gbc.gridx = 3;
        gbc.insets = new Insets(10, 20, 10, 50);
        mainPanel.add(new JLabel("Max Volunteers:"), gbc);

        //Max Volunteers Text Field
        gbc.gridx = 4;
        gbc.insets = new Insets(10, 5, 10, 20);
        JTextField maxVolunteersField = new JTextField(maxVolunteers);
        maxVolunteersField.setPreferredSize(new Dimension(200, 25));
        RoundedBorder.createTextFieldRoundedBorder(maxVolunteersField,10, Color.WHITE, Color.BLACK);
        mainPanel.add(maxVolunteersField, gbc);

        //Button Panel of Register Frame
        JPanel buttonPanel = new JPanel();
        JButton editScheduleButton = new JButton("Edit Schedule");
        JButton cancelButton = new JButton("Cancel");

        Color editScheduleButtonColor = new Color(102, 153, 204);
        Color cancelButtonColor = new Color(255, 153, 51);

        editScheduleButton.setForeground(Color.WHITE);
        editScheduleButton.setBorder(RoundedBorder.createButtonRoundedBorder(editScheduleButton, 10, editScheduleButtonColor));

        cancelButton.setForeground(Color.WHITE);
        cancelButton.setBorder(RoundedBorder.createButtonRoundedBorder(cancelButton, 10, cancelButtonColor));

        buttonPanel.add(editScheduleButton);
        buttonPanel.add(cancelButton);

        //Button Functions
        editScheduleButton.addActionListener((ActionEvent e) -> {
            int serviceIdInt = Integer.parseInt(serviceIDField.getText());
            int scheduleIdInt = Integer.parseInt(scheduleIDField.getText());
            int maxVolunteersInt = Integer.parseInt(maxVolunteersField.getText());
            Time startTimeData = Time.valueOf(startTimeField.getText());
            Time endTimeData = Time.valueOf(endTimeField.getText());
            Date scheduleDateData = Date.valueOf(scheduleDateField.getText());
            String locationData = locationField.getText();

           if (serviceIdInt == 0 || scheduleIdInt == 0 || maxVolunteersInt == 0 || startTimeData.toString().isEmpty() || endTimeData.toString().isEmpty() || scheduleDateData.toString().isEmpty() || locationData.isEmpty()) {
               JOptionPane.showMessageDialog(null, "Please fill in all fields!");
           } else {
                OrganizationController.editScheduleOfACommunityService(scheduleIdInt, serviceIdInt, scheduleDate, startTime, endTime, location, maxVolunteersInt, table);
                scheduleFrame.dispose();
           }
        });

        cancelButton.addActionListener((ActionEvent e) -> {
            scheduleFrame.dispose();
        });

        //Wrap Components in Panel
        JPanel wrapPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        wrapPanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));
        wrapPanel.add(mainPanel);

        JPanel wrapButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        wrapButtonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 15));
        wrapButtonPanel.add(buttonPanel);

        //Add Components to Register Frame
        scheduleFrame.add(wrapPanel, BorderLayout.CENTER);
        scheduleFrame.add(wrapButtonPanel, BorderLayout.SOUTH);
        scheduleFrame.add(titlePanel, BorderLayout.NORTH);
        scheduleFrame.setVisible(true);
    }
}
