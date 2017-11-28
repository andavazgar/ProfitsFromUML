package com.horstmann.violet.application.admin;

import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.util.Arrays;
import java.util.Scanner;

public class LoginDialog extends JDialog {
    
    private JTextField textField;
    private JPasswordField passwordField;
    private String filepath = "violetproduct-swing/src/main/java/com/horstmann/violet/application/admin/LoginCredentialFile.txt";
    
    /**
     * Create the application.
     */
    public LoginDialog(JFrame parent) {
        super(parent);
        
        initialize();
        setCenterLocation(parent);
    }
    
    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        this.setTitle("Login");
        this.setLocationRelativeTo(null);
        this.setModal(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setBounds(100, 100, 450, 300);
        this.getContentPane().setLayout(null);
        
        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setBounds(90, 70, 82, 23);
        this.getContentPane().add(lblUsername);
        
        textField = new JTextField();
        textField.setBounds(165, 70, 206, 23);
        this.getContentPane().add(textField);
        textField.setColumns(10);
        
        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setBounds(90, 105, 82, 23);
        this.getContentPane().add(lblPassword);
        
        passwordField = new JPasswordField();
        passwordField.setBounds(165, 105, 206, 23);
        this.getContentPane().add(passwordField);
        
        JButton btnLogin = new JButton("Login");
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                if (verifyLogin(textField, passwordField, filepath)) {
                    JOptionPane.showMessageDialog(null, "Successfully logged in!");
                    dispose();
                }
                else {
                    JOptionPane.showMessageDialog(null, "Error: incorrect username and/or password.");
                }
            }
        });
        btnLogin.setBounds(40, 176, 89, 23);
        this.getContentPane().add(btnLogin);
        
        JButton btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                dispose();
            }
        });
        btnCancel.setBounds(318, 176, 89, 23);
        this.getContentPane().add(btnCancel);
    }
    
    private static boolean verifyLogin(JTextField username, JPasswordField password, String filepath) {
        boolean loginInfoFound = false;
        String temporaryUsername = "";
        String temporaryPassword = "";
        
        try {
            Scanner input = new Scanner(new File(filepath));    // Scans the Credential file.
            input.useDelimiter("[,\n]");                        // Splits up the values.
            
            while (input.hasNext() && !loginInfoFound) {        // Loops until the username and password are correct.
                temporaryUsername = input.next();
                temporaryPassword = input.next();
                
                if (temporaryUsername.trim().equals(username.getText().trim()) && Arrays.equals(temporaryPassword.trim().toCharArray(), password.getPassword())) {
                    loginInfoFound = true;                        // Username and password are correct!
                    return true;
                }
            }
            input.close();                            // Closes scanner.
        }
        catch (Exception e) {            // Returns the error if the username and/or password are wrong.
            JOptionPane.showMessageDialog(null, "Error: incorrect username and/or password.");
        }
        return false;
    }
    
    private void setCenterLocation(JFrame parent) {
        setLocation((parent.getWidth() - getWidth()) / 2, (parent.getHeight() - getHeight()) / 2);
    }
}
