package View;

import Controller.LoginRegisterController;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RegisterView {
    public static void registerView() {
        JFrame registerFrame = new JFrame("Register Volunteer"); //Register Frame

        //Components of Register Frame
        registerFrame.setSize(500, 350);
        registerFrame.setLocationRelativeTo(null);
        registerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        registerFrame.setLayout(new BorderLayout());
        registerFrame.setUndecorated(true);

        //Title Bar of Register Frame
        JPanel titlePanel = new JPanel(new BorderLayout());

        //Color Customization
        Color baseColor = Color.decode("#01579B"); //Hexadecimal Color Code
        Color withColorOpacity = new Color(baseColor.getRed(), baseColor.getGreen(), baseColor.getBlue(), 100); //Get Color with Opacity
        titlePanel.setBackground(withColorOpacity); //Set Background Color

        JLabel titleLabel = new JLabel("Register Volunteer");
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

        //Email Address Text Label
        gbc.gridx = 0;
        gbc.insets = new Insets(10, 20, 10, 50);
        mainPanel.add(new JLabel("Email Address:"), gbc);

        //Email Address Text Field
        gbc.gridx = 1;
        gbc.insets = new Insets(10, 5, 10, 20);
        JTextField emailField = new JTextField();
        emailField.setPreferredSize(new Dimension(200, 25));
        RoundedBorder.createTextFieldRoundedBorder(emailField,10, Color.WHITE, Color.BLACK);
        mainPanel.add(emailField, gbc);

        //Password Text Label
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.insets = new Insets(10, 20, 10, 50);
        mainPanel.add(new JLabel("Password:"), gbc);

        //Password Text Field
        gbc.gridx = 1;
        gbc.insets = new Insets(10, 5, 10, 20);
        JPasswordField passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(200, 25));
        RoundedBorder.createTextFieldRoundedBorder(passwordField,10, Color.WHITE, Color.BLACK);
        mainPanel.add(passwordField, gbc);

        //First Name Text Label
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.insets = new Insets(10, 20, 10, 50);
        mainPanel.add(new JLabel("First Name:"), gbc);

        //First Name Text Field
        gbc.gridx = 1;
        gbc.insets = new Insets(10, 5, 10, 20);
        JTextField firstNameField = new JTextField();
        firstNameField.setPreferredSize(new Dimension(200, 25));
        RoundedBorder.createTextFieldRoundedBorder(firstNameField,10, Color.WHITE, Color.BLACK);
        mainPanel.add(firstNameField, gbc);

        //Last Name Text Label
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.insets = new Insets(10, 20, 10, 50);
        mainPanel.add(new JLabel("Last Name:"), gbc);

        //Last Name Text Field
        gbc.gridx = 1;
        gbc.insets = new Insets(10, 5, 10, 20);
        JTextField lastNameField = new JTextField();
        lastNameField.setPreferredSize(new Dimension(200, 25));
        RoundedBorder.createTextFieldRoundedBorder(lastNameField,10, Color.WHITE, Color.BLACK);
        mainPanel.add(lastNameField, gbc);

        //Phone Number Text Label
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.insets = new Insets(10, 20, 10, 50);
        mainPanel.add(new JLabel("Phone Number:"), gbc);

        //Phone Number Text Field
        gbc.gridx = 1;
        gbc.insets = new Insets(10, 5, 10, 20);
        JTextField phoneField = new JTextField();
        phoneField.setPreferredSize(new Dimension(200, 25));
        RoundedBorder.createTextFieldRoundedBorder(phoneField,10, Color.WHITE, Color.BLACK);
        mainPanel.add(phoneField, gbc);

        //Button Panel of Register Frame
        JPanel buttonPanel = new JPanel();
        JButton registerButton = new JButton("Register");
        JButton cancelButton = new JButton("Cancel");

        Color registerButtonColor = new Color(102, 153, 204);
        Color cancelButtonColor = new Color(255, 153, 51);

        registerButton.setForeground(Color.WHITE);
        registerButton.setBorder(RoundedBorder.createButtonRoundedBorder(registerButton, 10, registerButtonColor));

        cancelButton.setForeground(Color.WHITE);
        cancelButton.setBorder(RoundedBorder.createButtonRoundedBorder(cancelButton, 10, cancelButtonColor));

        buttonPanel.add(registerButton);
        buttonPanel.add(cancelButton);

        //Button Functions
        registerButton.addActionListener((ActionEvent e) -> {
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String phone = phoneField.getText();

            if (email.isEmpty() || password.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || phone.isEmpty()) {
                JOptionPane.showMessageDialog(registerFrame, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                boolean success = LoginRegisterController.register(email, password, firstName, lastName, phone);

                if (success) {
                    JOptionPane.showMessageDialog(registerFrame, "Registration successful!");
                    LoginView.loginView();
                    registerFrame.dispose();
                } else {
                    JOptionPane.showMessageDialog(registerFrame, "Registration failed. Email might already be taken.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        cancelButton.addActionListener((ActionEvent e) -> {
            LoginView.loginView();
            registerFrame.dispose();
        });

        //Wrap Components in Panel
        JPanel wrapPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        wrapPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        wrapPanel.add(mainPanel);

        JPanel wrapButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        wrapButtonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        wrapButtonPanel.add(buttonPanel);

        //Add Components to Register Frame
        registerFrame.add(wrapPanel, BorderLayout.CENTER);
        registerFrame.add(wrapButtonPanel, BorderLayout.SOUTH);
        registerFrame.add(titlePanel, BorderLayout.NORTH);
        registerFrame.setVisible(true);
    }
}