package org.sdms.View;

import org.sdms.Model.DBHandler;
import org.sdms.Controller.Translator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * The ConnectionView class represents the front-end connection interface
 * of the application, allowing users to connect to a database by providing
 * login credentials and a database URL.
 * <p>
 * This class also handles the interaction logic for actions performed in
 * the connection window, including the ability to change languages and
 * connect to the database.
 * </p>
 */
public class ConnectionView {

    /**
     * The main JFrame that holds the connection window.
     */
    public static JFrame connectionFrame;

    /**
     * The text field where the user enters the login.
     */
    private JTextField loginField;

    /**
     * The password field where the user enters the password.
     */
    private JPasswordField passwordField;

    /**
     * The text field where the user enters the database URL.
     */
//    private JTextField databaseUrlField;

    /**
     * Launches the application.
     * <p>
     * This method sets the language and loads the messages from the XML
     * before displaying the connection window.
     * </p>
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                // Set the language to English by default
                Translator.setLanguage(Translator.Language.ENG);

                // Load messages for the selected language
                Translator.getMessagesFromXML();

                try {
                    ConnectionView window = new ConnectionView();
                    window.connectionFrame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Creates the connection view window and makes it visible.
     */
    public ConnectionView() {
        initialize();
        connectionFrame.setVisible(true); // Make the window visible during construction
    }

    /**
     * Initializes the contents of the connection window.
     * <p>
     * This method sets up the layout, adds the necessary components such as
     * labels, text fields, and buttons, and defines the behavior for
     * user interactions.
     * </p>
     */
    private void initialize() {
        // Create the main window
        connectionFrame = new JFrame();
        connectionFrame.setBounds(100, 100, 640, 480);
        connectionFrame.setResizable(false);
        connectionFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        connectionFrame.setTitle(Translator.getValue("sdms"));

        // Create the blue-colored panel at the top
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(5, 175, 5));
        connectionFrame.getContentPane().add(topPanel, BorderLayout.PAGE_START);

        // Label displaying the connection instruction
        JLabel connectText = new JLabel(Translator.getValue("connectText"));
        connectText.setForeground(new Color(255, 255, 255));
        connectText.setFont(new Font("Arial", Font.PLAIN, 25));
        topPanel.add(connectText);

        // Bottom panel where the login, password fields, and buttons will be
        JPanel bottomPanel = new JPanel();
        connectionFrame.getContentPane().add(bottomPanel, BorderLayout.CENTER);

        // Label displaying the instruction to enter login
        JLabel loginText = new JLabel(Translator.getValue("loginText"));
        loginText.setBounds(68, 134, 162, 25);
        loginText.setFont(new Font("Arial", Font.PLAIN, 12));

        // Label displaying the instruction to enter password
        JLabel passwordText = new JLabel(Translator.getValue("passwordText"));
        passwordText.setBounds(68, 174, 162, 25);
        passwordText.setFont(new Font("Arial", Font.PLAIN, 12));

        // Initialize the login text field
        loginField = new JTextField();
        loginField.setName("loginField");
        loginField.setBounds(240, 139, 330, 20);
        loginField.setColumns(10);

        // Initialize the password text field
        passwordField = new JPasswordField();
        passwordField.setName("passwordField");
        passwordField.setBounds(240, 179, 330, 20);

//        // Initialize the database URL text field
//        databaseUrlField = new JTextField();
//        databaseUrlField.setName("databaseUrlField");
//        databaseUrlField.setText("jdbc:mysql://localhost:3306/studentsdb");
//        databaseUrlField.setColumns(10);
//        databaseUrlField.setBounds(240, 96, 330, 20);

        // Label displaying the instruction to enter database URL
        JLabel databaseUrlText = new JLabel(Translator.getValue("databaseUrlText"));
        databaseUrlText.setFont(new Font("Arial", Font.PLAIN, 12));
        databaseUrlText.setBounds(68, 91, 162, 25);

        // Button to change the application language
        JButton changeLanguageButton = new JButton(Translator.getValue("changeLanguage"));
        changeLanguageButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Translator.Language selectedLanguage = (Translator.Language) JOptionPane.showInputDialog(
                        null,
                        Translator.getValue("sdms"),
                        Translator.getValue("selectLanguage"),
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        Translator.Language.values(),
                        Translator.Language.ENG.toString()
                );

                if (selectedLanguage != null) {
                    Translator.setLanguage(selectedLanguage);
                }

                // Reload messages based on the selected language
                Translator.getMessagesFromXML();
                connectionFrame.dispose();
                new ConnectionView();
            }
        });
        changeLanguageButton.setFont(new Font("Arial", Font.PLAIN, 12));
        changeLanguageButton.setBackground(new Color(200, 200, 200));
        changeLanguageButton.setOpaque(true);
        changeLanguageButton.setBorderPainted(false);
        changeLanguageButton.setFocusPainted(false);
        changeLanguageButton.setBounds(480, 365, 135, 25);
        bottomPanel.add(changeLanguageButton);

        // Button to connect to the database
        JButton connectButton = new JButton(Translator.getValue("connectButton"));
        connectButton.setName("connectButton");
        connectButton.setFont(new Font("Arial", Font.PLAIN, 20));
        connectButton.setBackground(new Color(100, 200, 100));
        connectButton.setOpaque(true);
        connectButton.setBorderPainted(false);
        connectButton.setFocusPainted(false);
        connectButton.setBounds(221, 290, 190, 42);

        // Define action for the connect button
        connectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // If any of the fields are empty, show an error message
                if (loginField.getText().equals("")) {
                    JOptionPane.showMessageDialog(new JFrame(), Translator.getValue("fillEmptyFields"),
                            Translator.getValue("error"), JOptionPane.ERROR_MESSAGE);
                } else {
                    // Set login, password, and database URL for DBHandler
                    DBHandler.setLogin(loginField.getText());
                    DBHandler.setPassword(passwordField.getText());
//                    DBHandler.setDatabaseUrl(databaseUrlField.getText());

                    // Try to create the tables in the database
                    if (DBHandler.createTables()) {
                        JOptionPane.showMessageDialog(new JFrame(), Translator.getValue("connectionEstablished"),
                                Translator.getValue("success"), JOptionPane.INFORMATION_MESSAGE);

                        // Open the management view and close the connection window
                        ManagementView.main(null);
                        connectionFrame.dispose();
                    } else {
                        JOptionPane.showMessageDialog(new JFrame(), Translator.getValue("connectionNotEstablished"),
                                Translator.getValue("error"), JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        // Set the layout and add components to the bottom panel
        bottomPanel.setLayout(null);
        bottomPanel.add(passwordText);
        bottomPanel.add(loginText);
        bottomPanel.add(passwordField);
        bottomPanel.add(loginField);
        bottomPanel.add(connectButton);
//        bottomPanel.add(databaseUrlText);
//        bottomPanel.add(databaseUrlField);
    }
}
