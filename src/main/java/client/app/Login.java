package client.app;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.*;
import javax.imageio.ImageIO;


//import org.json.JSONObject;

public class Login extends JFrame implements ActionListener {
    JPanel panel;
    JLabel usernameLabel, passwordLabel, badLogin, goodLogin;
    JTextField usernameText;
    JPasswordField passwordText;
    JButton loginButton;

    Client client;

    BufferedImage image;

    public Login(Client client) throws IOException {

        JFrame frame = new JFrame("FirstLane");
        this.client = client;

        SpringLayout layout = new SpringLayout();

        panel = new JPanel(layout);
        frame.setContentPane(panel);
        panel.setBackground(Color.WHITE);

        // set frame title to username
        image = ImageIO.read(getClass().getResource("/client/app/content/logo-no-text.png"));
        setIconImage(image);
        

        //add logo to panel
        ImageIcon logo = new ImageIcon("./src/main/java/client/app/content/logo.png");
        ImageIcon scaledImage = new ImageIcon(logo.getImage().getScaledInstance(logo.getIconWidth() / 50,logo.getIconHeight() / 50, Image.SCALE_SMOOTH));
        JLabel scaledLogo = new JLabel(scaledImage);
        panel.add(scaledLogo);

        //center the middle of logo with middle of frame
        scaledLogo.setPreferredSize(new Dimension(475, 125));

        usernameLabel = new JLabel("Username: ");
        panel.add(usernameLabel);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, usernameLabel, 75, SpringLayout.HORIZONTAL_CENTER, panel);
        layout.putConstraint(SpringLayout.NORTH, usernameLabel, 130, SpringLayout.NORTH, panel);
        usernameLabel.setPreferredSize(new Dimension(300, 50));

        usernameText = new JTextField();
        panel.add(usernameText);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, usernameText, 0, SpringLayout.HORIZONTAL_CENTER, panel);
        layout.putConstraint(SpringLayout.NORTH, usernameText, 170, SpringLayout.NORTH, panel);
        usernameText.setPreferredSize(new Dimension(150, 45));

        passwordLabel = new JLabel("Password: ");
        panel.add(passwordLabel);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, passwordLabel, 75, SpringLayout.HORIZONTAL_CENTER, panel);
        layout.putConstraint(SpringLayout.NORTH, passwordLabel, 230, SpringLayout.NORTH, panel);
        passwordLabel.setPreferredSize(new Dimension(300, 50));

        passwordText = new JPasswordField();
        panel.add(passwordText);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, passwordText, 0, SpringLayout.HORIZONTAL_CENTER, panel);
        layout.putConstraint(SpringLayout.NORTH, passwordText, 270, SpringLayout.NORTH, panel);
        passwordText.setPreferredSize(new Dimension(150, 45));

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
        badLogin = new JLabel(scaledXPic);
        badLogin.setVisible(false);

        ImageIcon chekcPic = new ImageIcon("./src/main/java/client/app/content/check.png");
        ImageIcon scaledCheckPic = new ImageIcon(chekcPic.getImage().getScaledInstance(chekcPic.getIconWidth() / 15,
        chekcPic.getIconHeight() / 15, Image.SCALE_SMOOTH));
        goodLogin = new JLabel(scaledCheckPic);
        goodLogin.setVisible(false);



        panel.add(badLogin);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, badLogin, 0, SpringLayout.HORIZONTAL_CENTER, panel);
        layout.putConstraint(SpringLayout.NORTH, badLogin, 350, SpringLayout.NORTH, panel);

        panel.add(goodLogin);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, goodLogin, 0, SpringLayout.HORIZONTAL_CENTER, panel);
        layout.putConstraint(SpringLayout.NORTH, goodLogin, 350, SpringLayout.NORTH, panel);


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
    
    @Override
    public void actionPerformed(ActionEvent e) {

        System.out.println("Login button pressed");

        String username = usernameText.getText();
        String password = String.valueOf(passwordText.getPassword());

        try {
            int res = client.login(username, password);
            System.out.println(res);
            switch (res) {
                case 0:
                    badLogin.setVisible(false);
                    goodLogin.setVisible(true);
                    new AppCore(client);
                    //remove login frame
                    this.dispose();
                    break;

                case 1:
                    goodLogin.setVisible(false);
                    badLogin.setVisible(true);
                    break;
            }

        } catch (Exception ex) {
            badLogin.setVisible(true);
            ex.printStackTrace();
        }

    }
}
