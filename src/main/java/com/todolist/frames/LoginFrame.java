package com.todolist.frames;

import javax.swing.*;

import com.todolist.listeners.LoginSuccessListener;
import com.todolist.services.UserService;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.ArrayList;

public class LoginFrame extends JFrame {
    
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private ArrayList<LoginSuccessListener> loginSuccessListeners = new ArrayList<>();

    public LoginFrame(Connection connection) {
        
        UserService userService = new UserService(connection);

        // Set the title of the frame
        setTitle("Login");
        // Set the size of the frame
        setSize(350, 150);
        // Set the default close operation
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Center the frame on the screen
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Create a panel to hold the components
        JPanel panel = new JPanel(new GridLayout(3, 2));
        
        // Create the email label and field
        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField();
        emailField.setText("alice@example.com");
        
        // Create the password label and field
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();
        passwordField.setText("password123");
        
        // Create the login button
        loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());

                try {
                  int userId = userService.login(email, password);

                  if (userId == 0){
                    throw new Exception("Login failed, incorrect credential");
                  }

                  for (LoginSuccessListener listener : loginSuccessListeners){
                    listener.loginSuccess(userId);
                  }
                } catch (Exception e) {
                  e.printStackTrace();
                }
            }
        });
        
        // Add the components to the panel
        panel.add(emailLabel);
        panel.add(emailField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(new JLabel()); // Empty cell to align the button
        panel.add(loginButton);
        
        // Add the panel to the frame
        add(panel);
    }

    public void addLoginSuccessListeners(LoginSuccessListener loginSuccessListener){
      loginSuccessListeners.add(loginSuccessListener);
    }
}
