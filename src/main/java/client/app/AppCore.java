package client.app;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import java.io.IOException;

import org.json.JSONObject;
import org.springframework.asm.Label;

public class AppCore extends JFrame implements ActionListener {

    JPanel mainPanel;
    JPanel navPanel;
    JPanel pwdPanel;
    
    JList<String> list, dList;
    JScrollPane scrollPane, dScrollPane;
    DefaultListModel<String> model, dmodel;
    JButton addPass, save;
    JTextField message;
    
    int maxSize;
    BufferedImage image;
    String user, data, salt;
    Client client;

    public AppCore(Client client) throws IOException {
        this.client = client;

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        SpringLayout layout = new SpringLayout();
        JFrame frame = new JFrame("FirstLane");

        image = ImageIO.read(getClass().getResource("/client/app/content/logo-no-text.png"));
        setIconImage(image);
        

        mainPanel = new JPanel(layout);
        frame.setContentPane(mainPanel);
        mainPanel.setBackground(Color.WHITE);

        FlowLayout flowLayout = new FlowLayout();
        mainPanel.setLayout(flowLayout);


        // sidebar
        // **************************************************************************************/

        dmodel = new DefaultListModel<>();
        dList = new JList<>(dmodel);
        dList.setFixedCellHeight(50);

        dScrollPane = new JScrollPane(dList);
        dScrollPane.setViewportView(dList);
        dList.setLayoutOrientation(JList.VERTICAL);

        MouseListener mouseListener = new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    String selected = dList.getSelectedValue();
                    System.out.println(selected);

                }
            }
        };

//        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, dScrollPane, -500, SpringLayout.HORIZONTAL_CENTER, mainPanel);
//        layout.putConstraint(SpringLayout.NORTH, dScrollPane, 10, SpringLayout.NORTH, mainPanel);
        dScrollPane.setPreferredSize(new Dimension(250, 600));

 //       LineBorder roundedLineBorder = new LineBorder(new Color(27, 38, 79), 1, true);
 //       dScrollPane.setBorder(roundedLineBorder);

        ImageIcon plus = new ImageIcon("./src/main/java/client/app/content/plus.png");

        addPass = new JButton(plus);
//        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, addPass, -500, SpringLayout.HORIZONTAL_CENTER, mainPanel);
//        layout.putConstraint(SpringLayout.NORTH, addPass, 625, SpringLayout.NORTH, mainPanel);
        addPass.setPreferredSize(new Dimension(250, 50));
        addPass.addActionListener(this);
        // set background to transparent
        addPass.setOpaque(false);
        addPass.setContentAreaFilled(false);
        addPass.setBorderPainted(false);

        navPanel = new JPanel();
        BoxLayout boxLayout = new BoxLayout(navPanel, BoxLayout.Y_AXIS);
        navPanel.setLayout(boxLayout);
        navPanel.setBackground(Color.WHITE);

        navPanel.add(new JLabel("Choose to edit an item or add a new one:"));
        navPanel.add(dScrollPane);
        navPanel.add(addPass);

        // dList.setBorder(roundedLineBorder);
        mainPanel.add(navPanel);

        /**************************************************************************************/

        add(mainPanel, BorderLayout.WEST);
        // setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
        setSize(screenSize.width / 2, screenSize.height / 2);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // get user data from server
        JSONObject userData = client.getUserData();
        user = userData.getString("username");
        System.out.println(user);

        // set frame title to username
        setTitle("FirstLane | " + user);


        // get data from server
        System.out.println(userData);
        data = userData.getString("data");
        salt = userData.getString("salt");

        // decrypt data
        data = client.decrypt(data, salt);
        System.out.println(data);

        // password add/manage pane ******************************************************************************************/\
        pwdPanel = new JPanel();
        pwdPanel.setBackground(Color.WHITE);
       //pwdPanel.setPreferredSize(new Dimension(450, 600));
        pwdPanel.setLayout(new BoxLayout(pwdPanel, BoxLayout.Y_AXIS));
        
        JLabel addLabel = new JLabel("Add Password");
        addLabel.setFont(new Font("Arial", Font.BOLD, 20));
        addLabel.setForeground(new Color(27, 38, 79));
        pwdPanel.add(addLabel);

        JLabel nameLabel = new JLabel("Name");
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        nameLabel.setForeground(new Color(27, 38, 79));
        pwdPanel.add(nameLabel);

        JTextField name = new JTextField();
        name.setFont(new Font("Arial", Font.PLAIN, 16));
        name.setForeground(new Color(27, 38, 79));
        pwdPanel.add(name);

        JButton save = new JButton("Save");
        save.setFont(new Font("Arial", Font.PLAIN, 16));
        save.setForeground(new Color(27, 38, 79));
        pwdPanel.add(save);

        JButton cancel = new JButton("Cancel");
        cancel.setFont(new Font("Arial", Font.PLAIN, 16));
        cancel.setForeground(new Color(27, 38, 79));
        pwdPanel.add(cancel);


        pwdPanel.setVisible(false);


    //    layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, name, 0, SpringLayout.HORIZONTAL_CENTER, editPanel);

        mainPanel.add(pwdPanel);

        // actionlistener for save button
        save.addActionListener(l -> {
            String itemName = name.getText();

            dmodel.addElement(itemName);

            try {
                client.addPass(itemName, "moo", "boo", "url", "notes");
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        });
    
        

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addPass) {
            System.out.println("Add button pressed");
            // open add password window
            pwdPanel.setVisible(true);
           System.out.println("chuk");

        }
    }

}