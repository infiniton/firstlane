package client.app;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import org.json.JSONObject;
import org.springframework.security.crypto.password.PasswordEncoder;


public class Register {

    private PasswordEncoder passwordEncoder;
    public Object registerButton;
    public String usernameText;
    public String passwordText;

    // TODO - build registration ui


    public void actionPerformed(ActionEvent e) {
        // TODO - replace login button with register button
        if (e.getSource() == registerButton) {

            //String username = usernameText.getText();
            //String password = passwordEncoder.encode(/(passwordText.getPassword());
            String username = "oopsie";
            String password = "abcd";
            JSONObject json = new JSONObject();

            // secure the password with password utils
            json.put("type", "register");
            json.put("user", (String) json.get("user"));
            json.put("password", password);

            try {
                //client.register(username, password);
                // TODO - complete client registration
                // TODO - check response

                System.out.println("Registration successful");
                
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
    }


    
}
