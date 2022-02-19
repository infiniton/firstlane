package client.app;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

public class LoginGUI {
    public static void main(String[] args) {

        
        //open the Panel
        JFrame frame = new JFrame("FirstLane");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setVisible(true);

        //center the frame
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);

        Container c = frame.getContentPane();
        SpringLayout layout = new SpringLayout();
        c.setLayout(layout);
        c.setBackground(Color.white);        

        //add logo to frame
        ImageIcon logo = new ImageIcon("./src/main/java/client/app/content/logo.png");
        ImageIcon scaledImage = new ImageIcon(logo.getImage().getScaledInstance(logo.getIconWidth() / 20,logo.getIconHeight() / 20, Image.SCALE_SMOOTH));
        JLabel scaledLogo = new JLabel(scaledImage);
        frame.add(scaledLogo);

        //center the middle of logo with middle of frame
        scaledLogo.setPreferredSize(new Dimension(475, 225));


        ImageIcon loginPic = new ImageIcon("./src/main/java/client/app/content/lock-closed-r.png");
        ImageIcon scaledloginButton = new ImageIcon(loginPic.getImage().getScaledInstance(loginPic.getIconWidth() / 4,loginPic.getIconHeight() / 4, Image.SCALE_SMOOTH));
        JButton loginButton = new JButton(scaledloginButton);


        loginButton.setOpaque(false);
        loginButton.setContentAreaFilled(false);
        loginButton.setBorderPainted(false);
        loginButton.setFocusPainted(false);

        c.add(loginButton);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, loginButton, 0, SpringLayout.HORIZONTAL_CENTER, c);
        layout.putConstraint(SpringLayout.NORTH, loginButton, 400, SpringLayout.NORTH, c);
        loginButton.setPreferredSize(new Dimension(360, 50));
        loginButton.addActionListener(new LoginPressed());

        JButton registerButton = new JButton("Register");
        registerButton.setOpaque(false);
        registerButton.setContentAreaFilled(false);
        registerButton.setBorderPainted(false);
        registerButton.setForeground(Color.black);
        registerButton.setFont(new Font("Arial", Font.BOLD, 20));

        c.add(registerButton);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, registerButton, 0, SpringLayout.HORIZONTAL_CENTER, c);
        layout.putConstraint(SpringLayout.NORTH, registerButton, 300, SpringLayout.NORTH, c);
        registerButton.setPreferredSize(new Dimension(360, 50));
        //registerButton.addActionListener(new RegisterPressed());

        //Finish Frame
        frame.setSize(500, 500);
        frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
        frame.setVisible(true);
        //frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    }

    private static class LoginPressed implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
            Client client = new Client();
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            Login login = new Login(client);  
            login.setLocation (dim.width/2-login.getSize().width/2, dim.height/2-login.getSize().height/2);          
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

}
