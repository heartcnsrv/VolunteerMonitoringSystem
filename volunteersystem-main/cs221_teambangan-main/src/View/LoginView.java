package View;

import Controller.LoginRegisterController;
import View.Organization.OrganizationView;
import View.Volunteer.VolunteerView;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
public class LoginView {
    public static void chooseUser() {
        JFrame frame = new JFrame("Choose User");

        //Components of Choose User Frame
        frame.setSize(300, 100);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton volunteer = new JButton("Volunteer");
        JButton organization = new JButton("Organization");

        JPanel buttonPanel = new JPanel();

        volunteer.setForeground(Color.WHITE);
        volunteer.setBorder(RoundedBorder.createButtonRoundedBorder(volunteer, 10, new Color(102, 153, 204)));

        organization.setForeground(Color.WHITE);
        organization.setBorder(RoundedBorder.createButtonRoundedBorder(organization, 10, new Color(255, 153, 51)));

        buttonPanel.add(volunteer);
        buttonPanel.add(organization);

        //Wrap Components in Panel
        JPanel wrapPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        wrapPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        wrapPanel.add(buttonPanel);

        volunteer.addActionListener((ActionEvent e) -> {
            loginView();
            frame.dispose();
        });

        organization.addActionListener((ActionEvent e) -> {
            organizationLoginView();
            frame.dispose();
        });

        frame.add(wrapPanel);
        frame.setVisible(true);
    }

    public static void loginView() {
        JFrame loginFrame = new JFrame("Login Volunteer"); //Register Frame

        //Components of Register Frame
        loginFrame.setSize(500, 250);
        loginFrame.setLocationRelativeTo(null);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setLayout(new BorderLayout());
        loginFrame.setUndecorated(true);

        //Title Bar of Register Frame
        JPanel titlePanel = new JPanel(new BorderLayout());

        //Color Customization
        Color baseColor = Color.decode("#01579B"); //Hexadecimal Color Code
        Color withColorOpacity = new Color(baseColor.getRed(), baseColor.getGreen(), baseColor.getBlue(), 100); //Get Color with Opacity
        titlePanel.setBackground(withColorOpacity); //Set Background Color

        JLabel titleLabel = new JLabel("Login Volunteer");
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
        emailField.setMargin(new Insets(5, 10, 5, 10));
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
        passwordField.setMargin(new Insets(5, 10, 5, 10));
        RoundedBorder.createTextFieldRoundedBorder(passwordField,10, Color.WHITE, Color.BLACK);
        mainPanel.add(passwordField, gbc);

        //Button Panel of Login Frame
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        JButton loginButton = new JButton("Login");
        JButton backButton = new JButton("Back");

        Color loginButtonColor = new Color(102, 153, 204);
        Color backButtonColor = new Color(255, 153, 51);

        loginButton.setForeground(Color.WHITE);
        loginButton.setBorder(RoundedBorder.createButtonRoundedBorder(loginButton, 10, loginButtonColor));

        backButton.setForeground(Color.WHITE);
        backButton.setBorder(RoundedBorder.createButtonRoundedBorder(backButton, 10, backButtonColor));

        //Button Components
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.gridy = 0;

        //Login Button
        constraints.gridx = 0;
        constraints.insets = new Insets(10, 0, 10, 0);
        buttonPanel.add(loginButton, constraints);

        //Back Button
        constraints.gridx = 1;
        constraints.insets = new Insets(10, 0, 10, 0);
        buttonPanel.add(backButton, constraints);

        //Register Link
        JLabel registerLink = new JLabel("Register an Account");
        constraints.gridy= 1;
        constraints.gridx = 0;
        constraints.insets = new Insets(10, 0, 0, 0);
        buttonPanel.add(registerLink, constraints);

        //Button Functions
        loginButton.addActionListener((ActionEvent e) -> {
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());

            boolean isLoggedIn = LoginRegisterController.login(email, password);

            if (isLoggedIn) {
                JOptionPane.showMessageDialog(loginFrame, "Login successful!");
                VolunteerView.volunteerView();
                loginFrame.dispose();
            } else {
                JOptionPane.showMessageDialog(loginFrame, "Invalid email or password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        });

        registerLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                RegisterView.registerView();
                loginFrame.dispose();
            }
        });

        backButton.addActionListener((ActionEvent e) -> {
            LoginView.chooseUser();
            loginFrame.dispose();
        });

        //Wrap Components in Panel
        JPanel wrapPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        wrapPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        wrapPanel.add(mainPanel);

        JPanel wrapButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        wrapButtonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        wrapButtonPanel.add(buttonPanel);

        //Add Components to Register Frame
        loginFrame.add(wrapPanel, BorderLayout.CENTER);
        loginFrame.add(wrapButtonPanel, BorderLayout.SOUTH);
        loginFrame.add(titlePanel, BorderLayout.NORTH);
        loginFrame.setVisible(true);
    }

    public static void organizationLoginView() {
        JFrame loginFrame = new JFrame("Login Organization"); //Register Frame

        //Components of Register Frame
        loginFrame.setSize(500, 250);
        loginFrame.setLocationRelativeTo(null);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setLayout(new BorderLayout());
        loginFrame.setUndecorated(true);

        //Title Bar of Register Frame
        JPanel titlePanel = new JPanel(new BorderLayout());

        //Color Customization
        Color baseColor = Color.decode("#01579B"); //Hexadecimal Color Code
        Color withColorOpacity = new Color(baseColor.getRed(), baseColor.getGreen(), baseColor.getBlue(), 100); //Get Color with Opacity
        titlePanel.setBackground(withColorOpacity); //Set Background Color

        JLabel titleLabel = new JLabel("Login Organization");
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
        mainPanel.add(new JLabel("Organization Name:"), gbc);

        //Email Address Text Field
        gbc.gridx = 1;
        gbc.insets = new Insets(10, 5, 10, 20);
        JTextField organizationNameField = new JTextField();
        organizationNameField.setPreferredSize(new Dimension(200, 25));
        RoundedBorder.createTextFieldRoundedBorder(organizationNameField,10, Color.WHITE, Color.BLACK);
        mainPanel.add(organizationNameField, gbc);

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

        //Button Panel of Login Frame
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        JButton loginButton = new JButton("Login");
        JButton backButton = new JButton("Back");

        Color loginButtonColor = new Color(102, 153, 204);
        Color backButtonColor = new Color(255, 153, 51);

        loginButton.setForeground(Color.WHITE);
        loginButton.setBorder(RoundedBorder.createButtonRoundedBorder(loginButton, 10, loginButtonColor));

        backButton.setForeground(Color.WHITE);
        backButton.setBorder(RoundedBorder.createButtonRoundedBorder(backButton, 10, backButtonColor));

        //Button Components
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.gridy = 0;

        //Login Button
        constraints.gridx = 0;
        constraints.insets = new Insets(10, 0, 10, 0);
        buttonPanel.add(loginButton, constraints);

        //Back Button
        constraints.gridx = 1;
        constraints.insets = new Insets(10, 0, 10, 0);
        buttonPanel.add(backButton, constraints);

        //Button Functions
        loginButton.addActionListener((ActionEvent e) -> {
            String orgName = organizationNameField.getText();
            String password = new String(passwordField.getPassword());

            boolean isLoggedIn = LoginRegisterController.loginOrganization(orgName, password);

            if (isLoggedIn) {
                JOptionPane.showMessageDialog(loginFrame, "Login successful!");
                OrganizationView.organizationView();
                loginFrame.dispose();
            } else {
                JOptionPane.showMessageDialog(loginFrame, "Invalid orgName or password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        });

        backButton.addActionListener((ActionEvent e) -> {
            LoginView.chooseUser();
            loginFrame.dispose();
        });

        //Wrap Components in Panel
        JPanel wrapPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        wrapPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        wrapPanel.add(mainPanel);

        JPanel wrapButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        wrapButtonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        wrapButtonPanel.add(buttonPanel);

        //Add Components to Register Frame
        loginFrame.add(wrapPanel, BorderLayout.CENTER);
        loginFrame.add(wrapButtonPanel, BorderLayout.SOUTH);
        loginFrame.add(titlePanel, BorderLayout.NORTH);
        loginFrame.setVisible(true);
    }
}