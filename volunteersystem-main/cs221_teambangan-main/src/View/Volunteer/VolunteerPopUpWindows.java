package View.Volunteer;

import Controller.Volunteer.VolunteerController;
import Model.Session;
import View.RoundedBorder;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Date;
import java.sql.Time;

public class VolunteerPopUpWindows {
    public static void addVolunteerHoursWindow(int serviceID, JTable table) {
        int volunteerID = Session.getLoggedInVolunteerId();

        JFrame scheduleFrame = new JFrame("Add Volunteer Hours"); //Add Community Service Frame

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

        JLabel titleLabel = new JLabel("Add Volunteer Hours");
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
        mainPanel.add(new JLabel("Volunteer ID:"), gbc);

        //Service ID Text Field
        gbc.gridx = 1;
        gbc.insets = new Insets(10, 5, 10, 20);
        JTextField volunteerIDField = new JTextField(String.valueOf(volunteerID));
        volunteerIDField.setEditable(false); //Make text field not editable
        volunteerIDField.setPreferredSize(new Dimension(200, 25));
        RoundedBorder.createTextFieldRoundedBorder(volunteerIDField,10, Color.WHITE, Color.BLACK);
        mainPanel.add(volunteerIDField, gbc);

        //Service Name Text Label
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.insets = new Insets(10, 20, 10, 50);
        mainPanel.add(new JLabel("Service ID:"), gbc);

        //Service Name Text Field
        gbc.gridx = 1;
        gbc.insets = new Insets(10, 5, 10, 20);
        JTextField serviceIDField = new JTextField(String.valueOf(serviceID));
        serviceIDField.setEditable(false); //Make text field not editable
        serviceIDField.setPreferredSize(new Dimension(200, 25));
        RoundedBorder.createTextFieldRoundedBorder(serviceIDField,10, Color.WHITE, Color.BLACK);
        mainPanel.add(serviceIDField, gbc);

        //Service Type Text Label
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.insets = new Insets(10, 20, 10, 50);
        mainPanel.add(new JLabel("Service Date:"), gbc);

        //Start Time Text Field
        gbc.gridx = 1;
        gbc.insets = new Insets(10, 5, 10, 20);
        JTextField serviceDateField = new JTextField();
        serviceDateField.setPreferredSize(new Dimension(200, 25));
        RoundedBorder.createTextFieldRoundedBorder(serviceDateField,10, Color.WHITE, Color.BLACK);
        mainPanel.add(serviceDateField, gbc);

        //Description Text Label
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.insets = new Insets(10, 20, 10, 50);
        mainPanel.add(new JLabel("Service Hours:"), gbc);

        //End Time Text Field
        gbc.gridx = 1;
        gbc.insets = new Insets(10, 5, 10, 20);
        JTextField serviceHoursField = new JTextField();
        serviceHoursField.setPreferredSize(new Dimension(200, 25));
        RoundedBorder.createTextFieldRoundedBorder(serviceHoursField,10, Color.WHITE, Color.BLACK);
        mainPanel.add(serviceHoursField, gbc);

        //Button Panel of Register Frame
        JPanel buttonPanel = new JPanel();
        JButton addHoursButton = new JButton("Add Hours");
        JButton cancelButton = new JButton("Cancel");

        Color addScheduleButtonColor = new Color(102, 153, 204);
        Color cancelButtonColor = new Color(255, 153, 51);

        addHoursButton.setForeground(Color.WHITE);
        addHoursButton.setBorder(RoundedBorder.createButtonRoundedBorder(addHoursButton, 10, addScheduleButtonColor));

        cancelButton.setForeground(Color.WHITE);
        cancelButton.setBorder(RoundedBorder.createButtonRoundedBorder(cancelButton, 10, cancelButtonColor));

        buttonPanel.add(addHoursButton);
        buttonPanel.add(cancelButton);

        //Button Functions
        addHoursButton.addActionListener((ActionEvent e) -> {
            int volunteerIdInt = Integer.parseInt(volunteerIDField.getText());
            int serviceIdInt = Integer.parseInt(serviceIDField.getText());
            String serviceDate = serviceDateField.getText();
            int serviceHours = Integer.parseInt(serviceHoursField.getText());

            if (volunteerIdInt == 0 || serviceIdInt == 0 || serviceDate.isEmpty() || serviceHours == 0) {
                JOptionPane.showMessageDialog(null, "Please fill in all fields!");
            } else {
                VolunteerController.addVolunteerHours(serviceIdInt, serviceDate, serviceHours, table);
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

    public static void editVolunteerHoursWindow(int serviceID, Date serviceDate, int serviceHours, JTable table) {
        int volunteerID = Session.getLoggedInVolunteerId();

        JFrame scheduleFrame = new JFrame("Add Volunteer Hours"); //Add Community Service Frame

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

        JLabel titleLabel = new JLabel("Add Volunteer Hours");
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
        mainPanel.add(new JLabel("Volunteer ID:"), gbc);

        //Service ID Text Field
        gbc.gridx = 1;
        gbc.insets = new Insets(10, 5, 10, 20);
        JTextField volunteerIDField = new JTextField(String.valueOf(volunteerID));
        volunteerIDField.setEditable(false); //Make text field not editable
        volunteerIDField.setPreferredSize(new Dimension(200, 25));
        RoundedBorder.createTextFieldRoundedBorder(volunteerIDField,10, Color.WHITE, Color.BLACK);
        mainPanel.add(volunteerIDField, gbc);

        //Service Name Text Label
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.insets = new Insets(10, 20, 10, 50);
        mainPanel.add(new JLabel("Service ID:"), gbc);

        //Service Name Text Field
        gbc.gridx = 1;
        gbc.insets = new Insets(10, 5, 10, 20);
        JTextField serviceIDField = new JTextField(String.valueOf(serviceID));
        serviceIDField.setEditable(false); //Make text field not editable
        serviceIDField.setPreferredSize(new Dimension(200, 25));
        RoundedBorder.createTextFieldRoundedBorder(serviceIDField,10, Color.WHITE, Color.BLACK);
        mainPanel.add(serviceIDField, gbc);

        //Service Type Text Label
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.insets = new Insets(10, 20, 10, 50);
        mainPanel.add(new JLabel("Service Date:"), gbc);

        //Start Time Text Field
        gbc.gridx = 1;
        gbc.insets = new Insets(10, 5, 10, 20);
        JTextField serviceDateField = new JTextField(String.valueOf(serviceDate));
        serviceDateField.setPreferredSize(new Dimension(200, 25));
        RoundedBorder.createTextFieldRoundedBorder(serviceDateField,10, Color.WHITE, Color.BLACK);
        mainPanel.add(serviceDateField, gbc);

        //Description Text Label
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.insets = new Insets(10, 20, 10, 50);
        mainPanel.add(new JLabel("Service Hours:"), gbc);

        //End Time Text Field
        gbc.gridx = 1;
        gbc.insets = new Insets(10, 5, 10, 20);
        JTextField serviceHoursField = new JTextField(String.valueOf(serviceHours));
        serviceHoursField.setPreferredSize(new Dimension(200, 25));
        RoundedBorder.createTextFieldRoundedBorder(serviceHoursField,10, Color.WHITE, Color.BLACK);
        mainPanel.add(serviceHoursField, gbc);

        //Button Panel of Register Frame
        JPanel buttonPanel = new JPanel();
        JButton editHoursButton = new JButton("Edit Hours");
        JButton cancelButton = new JButton("Cancel");

        Color addScheduleButtonColor = new Color(102, 153, 204);
        Color cancelButtonColor = new Color(255, 153, 51);

        editHoursButton.setForeground(Color.WHITE);
        editHoursButton.setBorder(RoundedBorder.createButtonRoundedBorder(editHoursButton, 10, addScheduleButtonColor));

        cancelButton.setForeground(Color.WHITE);
        cancelButton.setBorder(RoundedBorder.createButtonRoundedBorder(cancelButton, 10, cancelButtonColor));

        buttonPanel.add(editHoursButton);
        buttonPanel.add(cancelButton);

        //Button Functions
        editHoursButton.addActionListener((ActionEvent e) -> {
            int volunteerIdInt = Integer.parseInt(volunteerIDField.getText());
            int serviceIdInt = Integer.parseInt(serviceIDField.getText());
            Date serviceDateData = Date.valueOf(serviceDateField.getText());
            int serviceHoursData = Integer.parseInt(serviceHoursField.getText());

            if (volunteerIdInt == 0 || serviceIdInt == 0 || serviceDateData.equals(0) || serviceHoursData == 0) {
                JOptionPane.showMessageDialog(null, "Please fill in all fields!");
            } else {
                VolunteerController.updateVolunteerHours(serviceIdInt, String.valueOf(serviceDateData), serviceHoursData, table);
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
