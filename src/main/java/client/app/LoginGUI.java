package client.app;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import java.io.*;
import javax.imageio.ImageIO;

public class LoginGUI {

    static JFrame frame;
    
    public static void main(String[] args) throws IOException {

        //open the Panel
        frame = new JFrame("FirstLane");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setVisible(true);

        File imagepath = new File("./src/main/java/client/app/content/logo-no-text.png");
        BufferedImage image = ImageIO.read(imagepath);
        frame.setIconImage(image);

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

        JButton loginButton = new JButton("Login");

        loginButton.setOpaque(false);
        loginButton.setContentAreaFilled(false);
        loginButton.setBorderPainted(true);
        loginButton.setFocusPainted(false);
        loginButton.setFont(new Font("Arial", Font.BOLD, 32));
        // set text color
        loginButton.setForeground(new Color(27, 38, 79));
        // button border thickness and color 8E73FF
        loginButton.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, new Color(142, 115, 255)));

        c.add(loginButton);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, loginButton, 0, SpringLayout.HORIZONTAL_CENTER, c);
        layout.putConstraint(SpringLayout.NORTH, loginButton, 350, SpringLayout.NORTH, c);
        //button should be the width of the text
        loginButton.setPreferredSize(new Dimension(loginButton.getText().length()*25, 50));
        loginButton.addActionListener(new LoginPressed());

        JButton registerButton = new JButton("Register");
        registerButton.setOpaque(false);
        registerButton.setContentAreaFilled(false);
        registerButton.setBorderPainted(false);
        registerButton.setForeground(Color.black);

        c.add(registerButton);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, registerButton, 0, SpringLayout.HORIZONTAL_CENTER, c);
        layout.putConstraint(SpringLayout.NORTH, registerButton, 400, SpringLayout.NORTH, c);
        registerButton.setPreferredSize(new Dimension(360, 50));
        registerButton.addActionListener(new RegisterPressed());

        //Finish Frame
        frame.setSize(500, 500);
        frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    private static class LoginPressed implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
            Client client = new Client();
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            Login login = new Login(client);  
            login.setLocation (dim.width/2-login.getSize().width/2, dim.height/2-login.getSize().height/2);

            frame.dispose();
        
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private static class RegisterPressed implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
            Client client = new Client();
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            Register register = new Register(client);  
            register.setLocation (dim.width/2-register.getSize().width/2, dim.height/2-register.getSize().height/2);
            

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

}
