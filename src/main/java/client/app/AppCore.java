package client.app;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import java.io.IOException;

import org.json.JSONObject;

public class AppCore extends JFrame implements ActionListener {

    JPanel panel;

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

        panel = new JPanel(layout);
        frame.setContentPane(panel);
        panel.setBackground(Color.WHITE);

        // sidebar
        // **************************************************************************************/

        dmodel = new DefaultListModel<>();
        dList = new JList<>(dmodel);
        dList.setFixedCellHeight(50);

        addPass = new JButton("+");

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

        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, dScrollPane, -500, SpringLayout.HORIZONTAL_CENTER, panel);
        layout.putConstraint(SpringLayout.NORTH, dScrollPane, 10, SpringLayout.NORTH, panel);
        dScrollPane.setPreferredSize(new Dimension(250, 600));

        LineBorder roundedLineBorder = new LineBorder(new Color(27, 38, 79), 1, true);
        dScrollPane.setBorder(roundedLineBorder);

        // dList.setBorder(roundedLineBorder);
        panel.add(dScrollPane);

        ImageIcon plus = new ImageIcon("./src/main/java/client/app/content/plus.png");

        addPass = new JButton(plus);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, addPass, -500, SpringLayout.HORIZONTAL_CENTER, panel);
        layout.putConstraint(SpringLayout.NORTH, addPass, 625, SpringLayout.NORTH, panel);
        addPass.setPreferredSize(new Dimension(250, 50));
        addPass.addActionListener(this);
        // set background to transparent
        addPass.setOpaque(false);
        addPass.setContentAreaFilled(false);
        addPass.setBorderPainted(false);

        panel.add(addPass);
        /**************************************************************************************/

        add(panel, BorderLayout.CENTER);
        // setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
        setSize(screenSize.width / 2, screenSize.height / 2);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // get user data from server
        JSONObject userData = client.getUserData();
        user = userData.getString("user");
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

        // password add pane
        // ******************************************************************************************
        // /
        JPanel addPane = new JPanel();
        addPane.setBackground(Color.WHITE);
        addPane.setPreferredSize(new Dimension(250, 600));
        addPane.setLayout(new BoxLayout(addPane, BoxLayout.Y_AXIS));

        JLabel addLabel = new JLabel("Add Password");
        addLabel.setFont(new Font("Arial", Font.BOLD, 20));
        addLabel.setForeground(new Color(27, 38, 79));
        addPane.add(addLabel);

        JLabel nameLabel = new JLabel("Name");
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        nameLabel.setForeground(new Color(27, 38, 79));
        addPane.add(nameLabel);

        JTextField name = new JTextField();
        name.setFont(new Font("Arial", Font.PLAIN, 16));
        name.setForeground(new Color(27, 38, 79));
        addPane.add(name);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addPass) {
            System.out.println("Add button pressed");
            // open add password window

        }
    }

}