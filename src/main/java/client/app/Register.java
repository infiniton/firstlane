package client.app;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

//import org.json.JSONObject;

public class Register extends JFrame implements ActionListener {
    JPanel panel;

    JLabel usernameLabel, passwordLabel, rPasswordLabel, badReg, goodReg, badPass, badRPass, message;

    JTextField usernameText;
    JPasswordField passwordText, rPasswordText;

    JButton loginButton;

    Client client;

    public Register(Client client) {
        this.client = client;

        SpringLayout layout = new SpringLayout();

        panel = new JPanel(layout);
        panel.setBackground(Color.WHITE);

        //add logo to panel
        ImageIcon logo = new ImageIcon("./src/main/java/client/app/content/logo.png");
        ImageIcon scaledImage = new ImageIcon(logo.getImage().getScaledInstance(logo.getIconWidth() / 50,logo.getIconHeight() / 50, Image.SCALE_SMOOTH));
        JLabel scaledLogo = new JLabel(scaledImage);
        panel.add(scaledLogo);

        //center the middle of logo with middle of frame
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, scaledLogo, 0, SpringLayout.HORIZONTAL_CENTER, panel);
        scaledLogo.setPreferredSize(new Dimension(475, 125));


        usernameLabel = new JLabel("Username: ");
        panel.add(usernameLabel);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, usernameLabel, 75, SpringLayout.HORIZONTAL_CENTER, panel);
        layout.putConstraint(SpringLayout.NORTH, usernameLabel, 110, SpringLayout.NORTH, panel);
        usernameLabel.setPreferredSize(new Dimension(300, 50));

        usernameText = new JTextField();
        panel.add(usernameText);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, usernameText, 0, SpringLayout.HORIZONTAL_CENTER, panel);
        layout.putConstraint(SpringLayout.NORTH, usernameText, 150, SpringLayout.NORTH, panel);
        usernameText.setPreferredSize(new Dimension(150, 45));

        passwordLabel = new JLabel("Password: ");
        panel.add(passwordLabel);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, passwordLabel, 75, SpringLayout.HORIZONTAL_CENTER, panel);
        layout.putConstraint(SpringLayout.NORTH, passwordLabel, 190, SpringLayout.NORTH, panel);
        passwordLabel.setPreferredSize(new Dimension(300, 50));

        passwordText = new JPasswordField();
        panel.add(passwordText);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, passwordText, 0, SpringLayout.HORIZONTAL_CENTER, panel);
        layout.putConstraint(SpringLayout.NORTH, passwordText, 230, SpringLayout.NORTH, panel);
        passwordText.setPreferredSize(new Dimension(150, 45));

        rPasswordLabel = new JLabel("Repeat Password: ");
        panel.add(rPasswordLabel);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, rPasswordLabel, 75, SpringLayout.HORIZONTAL_CENTER, panel);
        layout.putConstraint(SpringLayout.NORTH, rPasswordLabel, 270, SpringLayout.NORTH, panel);
        rPasswordLabel.setPreferredSize(new Dimension(300, 50));

        rPasswordText = new JPasswordField();
        panel.add(rPasswordText);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, rPasswordText, 0, SpringLayout.HORIZONTAL_CENTER, panel);
        layout.putConstraint(SpringLayout.NORTH, rPasswordText, 310, SpringLayout.NORTH, panel);
        rPasswordText.setPreferredSize(new Dimension(150, 45));

        ImageIcon loginPic = new ImageIcon("./src/main/java/client/app/content/lock-closed-r.png");
        ImageIcon scaledloginButton = new ImageIcon(loginPic.getImage().getScaledInstance(loginPic.getIconWidth() / 4,
                loginPic.getIconHeight() / 4, Image.SCALE_SMOOTH));
        JButton loginButton = new JButton(scaledloginButton);
        panel.add(loginButton);

        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, loginButton, 0, SpringLayout.HORIZONTAL_CENTER, panel);
        layout.putConstraint(SpringLayout.NORTH, loginButton, 400, SpringLayout.NORTH, panel);
        loginButton.setPreferredSize(new Dimension(50, 50));

        loginButton.setOpaque(false);
        loginButton.setContentAreaFilled(false);
        loginButton.setBorderPainted(false);
        loginButton.setForeground(Color.white);

        ImageIcon xPic = new ImageIcon("./src/main/java/client/app/content/redX.png");
        ImageIcon scaledXPic = new ImageIcon(xPic.getImage().getScaledInstance(xPic.getIconWidth() / 15,
                xPic.getIconHeight() / 15, Image.SCALE_SMOOTH));
        badReg = new JLabel(scaledXPic);
        badReg.setVisible(false);

        ImageIcon chekcPic = new ImageIcon("./src/main/java/client/app/content/check.png");
        ImageIcon scaledCheckPic = new ImageIcon(chekcPic.getImage().getScaledInstance(chekcPic.getIconWidth() / 15,
        chekcPic.getIconHeight() / 15, Image.SCALE_SMOOTH));
        goodReg = new JLabel(scaledCheckPic);
        goodReg.setVisible(false);



        panel.add(badReg);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, badReg, 0, SpringLayout.HORIZONTAL_CENTER, panel);
        layout.putConstraint(SpringLayout.NORTH, badReg, 350, SpringLayout.NORTH, panel);

        badPass = new JLabel(scaledXPic);
        badPass.setVisible(false);

        badRPass = new JLabel(scaledXPic);
        badRPass.setVisible(false);

        panel.add(badPass);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, badPass, 100, SpringLayout.HORIZONTAL_CENTER, panel);
        layout.putConstraint(SpringLayout.NORTH, badPass, 240, SpringLayout.NORTH, panel);

        panel.add(badRPass);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, badRPass, 100, SpringLayout.HORIZONTAL_CENTER, panel);
        layout.putConstraint(SpringLayout.NORTH, badRPass, 320, SpringLayout.NORTH, panel);



        panel.add(goodReg);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, goodReg, 0, SpringLayout.HORIZONTAL_CENTER, panel);
        layout.putConstraint(SpringLayout.NORTH, goodReg, 350, SpringLayout.NORTH, panel);


        loginButton.addActionListener(this);

        message = new JLabel();

        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, message, 10, SpringLayout.HORIZONTAL_CENTER, panel);
        layout.putConstraint(SpringLayout.NORTH, message, 350, SpringLayout.NORTH, panel);
        message.setPreferredSize(new Dimension(220, 45));
  
        panel.add(message);

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
    
    @Override
    public void actionPerformed(ActionEvent e) {

        System.out.println("Register button pressed");

        String username = usernameText.getText();
        String password = String.valueOf(passwordText.getPassword());
        String rPassword = String.valueOf(rPasswordText.getPassword());

        if(password.equals(rPassword)){
            badPass.setVisible(false);
            badRPass.setVisible(false);
            try {
                int res = client.register(username, password);
                System.out.println(res);
                switch (res) {
                    case 0:
                        badReg.setVisible(false);
                        goodReg.setVisible(true);
                        //registration successful popup
                        JOptionPane.showMessageDialog(null, "Registration successful!", "Registration", JOptionPane.INFORMATION_MESSAGE);
                        new Login(client);
                        //remove login frame
                        this.dispose();
                        break;
    
                    case 1:
                        goodReg.setVisible(false);
                        badReg.setVisible(true);                       
                        break;
                }
    
            } catch (Exception ex) {
                badReg.setVisible(true);
                ex.printStackTrace();
            }
        }
        else{
            badPass.setVisible(true);
            badRPass.setVisible(true);
            message.setText("Passwords do not match. Try again.");
            
        }



    }
}
