package client.app;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import org.json.JSONObject;

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
        layout.putConstraint(SpringLayout.NORTH, usernameLabel, 110, SpringLayout.NORTH, panel);
        usernameLabel.setPreferredSize(new Dimension(360, 50));

        usernameText = new JTextField();
        panel.add(usernameText);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, usernameText, 0, SpringLayout.HORIZONTAL_CENTER, panel);
        layout.putConstraint(SpringLayout.NORTH, usernameText, 150, SpringLayout.NORTH, panel);
        usernameText.setPreferredSize(new Dimension(150, 45));

        passwordLabel = new JLabel("Password: ");
        panel.add(passwordLabel);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, passwordLabel, 75, SpringLayout.HORIZONTAL_CENTER, panel);
        layout.putConstraint(SpringLayout.NORTH, passwordLabel, 210, SpringLayout.NORTH, panel);
        passwordLabel.setPreferredSize(new Dimension(360, 50));

        passwordText = new JPasswordField();
        panel.add(passwordText);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, passwordText, 0, SpringLayout.HORIZONTAL_CENTER, panel);
        layout.putConstraint(SpringLayout.NORTH, passwordText, 250, SpringLayout.NORTH, panel);
        passwordText.setPreferredSize(new Dimension(150, 45));

        ImageIcon loginPic = new ImageIcon("./src/main/java/client/app/content/lock-closed-r.png");
        ImageIcon scaledloginButton = new ImageIcon(loginPic.getImage().getScaledInstance(loginPic.getIconWidth() / 4,loginPic.getIconHeight() / 4, Image.SCALE_SMOOTH));
        JButton loginButton = new JButton(scaledloginButton);        
        panel.add(loginButton);


        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, loginButton, 0, SpringLayout.HORIZONTAL_CENTER, panel);
        layout.putConstraint(SpringLayout.NORTH, loginButton, 400, SpringLayout.NORTH, panel);
        loginButton.setPreferredSize(new Dimension(360, 50));

        loginButton.setOpaque(false);
        loginButton.setContentAreaFilled(false);
        loginButton.setBorderPainted(false);
        loginButton.setForeground(Color.white);


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

            System.out.println("Login button pressed");

            String username = usernameText.getText();
            String password = String.valueOf(passwordText.getPassword());

            try {
                client.login(username, password);
                // TODO - complete client registration
                // TODO - check response                
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        
    }
}
