package client.app;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import java.io.IOException;

import javax.imageio.ImageIO;

//import org.json.JSONObject;

public class Register extends JFrame implements ActionListener {
    JPanel panel;

    JLabel usernameLabel, passwordLabel, rPasswordLabel, badReg, goodReg, badPass, badRPass, message;

    JTextField usernameText;
    JPasswordField passwordText, rPasswordText;

    JButton loginButton;

    Client client;

    public Register(Client client) throws IOException {
        this.client = client;

        SpringLayout layout = new SpringLayout();

        panel = new JPanel(layout);
        panel.setBackground(Color.WHITE);

        //add logo to panel
        BufferedImage logoImage = ImageIO.read(Startup.class.getResource("/client/app/content/logo.png"));
        ImageIcon logo = new ImageIcon(logoImage);
        ImageIcon scaledImage = new ImageIcon(logo.getImage().getScaledInstance(logo.getIconWidth() / 50,
                logo.getIconHeight() / 50, Image.SCALE_SMOOTH));
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

        BufferedImage registerImage = ImageIO.read(Startup.class.getResource("/client/app/content/lock-closed-r.png"));
        ImageIcon registerPic = new ImageIcon(registerImage);
        ImageIcon scaledloginButton = new ImageIcon(registerPic.getImage().getScaledInstance(registerPic.getIconWidth() / 4,
                registerPic.getIconHeight() / 4, Image.SCALE_SMOOTH));
        JButton registerButton = new JButton(scaledloginButton);
        panel.add(registerButton);

        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, registerButton, 0, SpringLayout.HORIZONTAL_CENTER, panel);
        layout.putConstraint(SpringLayout.NORTH, registerButton, 400, SpringLayout.NORTH, panel);
        registerButton.setPreferredSize(new Dimension(50, 50));

        registerButton.setOpaque(false);
        registerButton.setContentAreaFilled(false);
        registerButton.setBorderPainted(false);
        registerButton.setForeground(Color.white);

        BufferedImage xBufferedImage = ImageIO.read(Startup.class.getResource("/client/app/content/redX.png"));
        ImageIcon xPic = new ImageIcon(xBufferedImage);
        ImageIcon scaledXPic = new ImageIcon(xPic.getImage().getScaledInstance(xPic.getIconWidth() / 15,
                xPic.getIconHeight() / 15, Image.SCALE_SMOOTH));
        badReg = new JLabel(scaledXPic);
        badReg.setVisible(false);

        BufferedImage chkBufferedImage = ImageIO.read(Startup.class.getResource("/client/app/content/check.png"));
        ImageIcon checkPic = new ImageIcon(chkBufferedImage);
        ImageIcon scaledCheckPic = new ImageIcon(checkPic.getImage().getScaledInstance(checkPic.getIconWidth() / 15,
                checkPic.getIconHeight() / 15, Image.SCALE_SMOOTH));
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


        registerButton.addActionListener(this);

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
