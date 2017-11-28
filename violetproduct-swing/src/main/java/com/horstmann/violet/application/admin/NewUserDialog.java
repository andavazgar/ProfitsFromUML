package com.horstmann.violet.application.admin;

import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class NewUserDialog extends JDialog {
    
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private String filepath = "violetproduct-swing/src/main/java/com/horstmann/violet/application/admin/LoginCredentialFile.txt";
    
    /**
     * Create the application.
     */
    public NewUserDialog(JFrame parent) {
        super(parent);
        
        initialize();
        setCenterLocation(parent);
    }
    
    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        this.setTitle("Create New Account");
        this.setLocationRelativeTo(null);
        this.setModal(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setBounds(100, 100, 450, 300);
        this.getContentPane().setLayout(null);
        
        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setBounds(70, 40, 65, 23);
        this.getContentPane().add(lblUsername);
        
        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setBounds(70, 70, 65, 23);
        this.getContentPane().add(lblPassword);
        
        JLabel lblConfirmPassword = new JLabel("Confirm Password:");
        lblConfirmPassword.setBounds(70, 100, 130, 23);
        this.getContentPane().add(lblConfirmPassword);
        
        usernameField = new JTextField();
        usernameField.setBounds(200, 40, 170, 23);
        this.getContentPane().add(usernameField);
        usernameField.setColumns(10);
        
        passwordField = new JPasswordField();
        passwordField.setBounds(200, 70, 170, 23);
        this.getContentPane().add(passwordField);
        
        confirmPasswordField = new JPasswordField();
        confirmPasswordField.setBounds(200, 100, 170, 23);
        this.getContentPane().add(confirmPasswordField);
        
        JButton btnCreateAccount = new JButton("Create Account");
        btnCreateAccount.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                if (usernameField.getText().equals("") || passwordField.getPassword().length == 0 || confirmPasswordField.getPassword().length == 0) {
                    JOptionPane.showMessageDialog(null, "Error: form is not complete.");
                }
                else if (!Arrays.equals(passwordField.getPassword(), confirmPasswordField.getPassword())) {
                    JOptionPane.showMessageDialog(null, "Error: password does not match with confirm password.");
                }
                else if (checkUsername(usernameField, filepath)) {
                    JOptionPane.showMessageDialog(null, "Error: username already taken.");
                }
                else {
                    registerNewLogin(usernameField, passwordField, filepath);
                    JOptionPane.showMessageDialog(null, "Username and password registered!");
                    dispose();
                }
            }
        });
        btnCreateAccount.setBounds(31, 153, 163, 23);
        this.getContentPane().add(btnCreateAccount);
        
        JButton btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                dispose();
            }
        });
        btnCancel.setBounds(322, 153, 89, 23);
        this.getContentPane().add(btnCancel);
        
        
    }
    
    // The method below takes care of creating a new account for a user.
    private static void registerNewLogin(JTextField username, JPasswordField password, String filepath) {
        try {
            File file = new File(filepath);
            FileWriter writer = new FileWriter(file, true);
            PrintWriter printw = new PrintWriter(writer);
            
            // Prints the new user info onto the given file.
            String pass = String.valueOf(password.getPassword());
            printw.println(username.getText() + "," + pass);
            printw.close();
        }
        catch (IOException e) {        // Catches IOException, handles it.
            JOptionPane.showMessageDialog(null, "Error: IOException Error. System exiting.");
        }
        
    }
    
    // The method below checks if a username already exists.
    private static boolean checkUsername(JTextField username, String filepath) {
        
        boolean usernameTaken = false;
        
        try {
            String temporaryUsername = "";
            Scanner input = new Scanner(new File(filepath));
            input.useDelimiter("[,\n]");
            while (input.hasNext() && !usernameTaken) {
                temporaryUsername = input.next();
                
                if (temporaryUsername.trim().equals(username.getText())) {
                    usernameTaken = true;
                }
            }
            input.close();
        }
        catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Error: Login Credential File Not Found.");
        }
        
        return usernameTaken;
    }
    
    private void setCenterLocation(JFrame parent) {
        setLocation((parent.getWidth() - getWidth()) / 2, (parent.getHeight() - getHeight()) / 2);
    }
    
}
