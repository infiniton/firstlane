import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import org.json.simple.JSONObject;

public class Login extends JFrame implements ActionListener {
    JPanel panel;

    JLabel usernameLabel, passwordLabel, message;

    JTextField usernameText;
    JPasswordField passwordText;

    JButton loginButton;

    Client client;

    public Login(Client client) {
        this.client = client;

        SpringLayout layout = new SpringLayout();

        panel = new JPanel(layout);
        panel.setBackground(Color.WHITE);

        usernameLabel = new JLabel("Username: ");
        panel.add(usernameLabel);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, usernameLabel, 75, SpringLayout.HORIZONTAL_CENTER, panel);
        layout.putConstraint(SpringLayout.NORTH, usernameLabel, 300, SpringLayout.NORTH, panel);
        usernameLabel.setPreferredSize(new Dimension(360, 50));

        usernameText = new JTextField();
        panel.add(usernameText);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, usernameText, 0, SpringLayout.HORIZONTAL_CENTER, panel);
        layout.putConstraint(SpringLayout.NORTH, usernameText, 350, SpringLayout.NORTH, panel);
        usernameText.setPreferredSize(new Dimension(150, 45));

        passwordLabel = new JLabel("Password: ");
        panel.add(passwordLabel);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, passwordLabel, 75, SpringLayout.HORIZONTAL_CENTER, panel);
        layout.putConstraint(SpringLayout.NORTH, passwordLabel, 400, SpringLayout.NORTH, panel);
        passwordLabel.setPreferredSize(new Dimension(360, 50));

        passwordText = new JPasswordField();
        panel.add(passwordText);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, passwordText, 0, SpringLayout.HORIZONTAL_CENTER, panel);
        layout.putConstraint(SpringLayout.NORTH, passwordText, 450, SpringLayout.NORTH, panel);
        passwordText.setPreferredSize(new Dimension(150, 45));

        loginButton = new JButton("Login");
        panel.add(loginButton);

        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, loginButton, 0, SpringLayout.HORIZONTAL_CENTER, panel);
        layout.putConstraint(SpringLayout.NORTH, loginButton, 500, SpringLayout.NORTH, panel);
        loginButton.setPreferredSize(new Dimension(360, 50));

        loginButton.setOpaque(false);
        loginButton.setContentAreaFilled(false);
        loginButton.setBorderPainted(false);
        loginButton.setForeground(Color.white);
        loginButton.setFont(new Font("Arial", Font.BOLD, 20));

        message = new JLabel();

        panel.add(message);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, message, 0, SpringLayout.HORIZONTAL_CENTER, panel);
        layout.putConstraint(SpringLayout.NORTH, message, 550, SpringLayout.NORTH, panel);
        message.setPreferredSize(new Dimension(360, 50));

        loginButton.addActionListener(this);
        add(panel, BorderLayout.CENTER);
        setSize(500, 500);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    

        // center the frame
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);

        // set the frame to be un-resizable
        this.setResizable(false);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {

            String username = usernameText.getText();
            String password = String.valueOf(passwordText.getPassword());

            JSONObject json = new JSONObject();
            json = PasswordUtils.secure(username, password);

            // secure the password with password utils
            json.put("type", "login");
            json.put("user", (String) json.get("user"));
            json.put("hash", (String) json.get("hash"));
            json.put("salt", (String) json.get("salt"));

            try {
                client.login(username, password);
                JSONObject res = client.getResponse();
                String exit = res.get("data").toString();
                switch (exit) {
                    case "0":
                        message.setText("Login successful.");
                        break;
                    case "1":
                        message.setText("Invalid user.");
                        break;
                    case "2":
                        message.setText("Invalid password.");
                        break;
                    case "3":
                        message.setText("Login failed. Please try again.");
                        break;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
    }
}
