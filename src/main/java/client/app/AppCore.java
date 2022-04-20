package client.app;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import java.io.IOException;

import org.json.JSONObject;

public class AppCore extends JFrame implements ActionListener {

    JPanel mainPanel, addPanel, navPanel;

    JList<String> list, dList;
    JScrollPane scrollPane, dScrollPane;
    DefaultListModel<String> model, dmodel;
    JButton addPass, save;
    JTextField message, name, username, password, url, notes;

    int maxSize;
    BufferedImage image;
    String user, data, salt, usernameStr, urlStr, passStr, notesStr;
    Client client;
    boolean passwordSelected;

    public AppCore(Client client) throws IOException {
        this.client = client;

        passwordSelected = false;

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

        //layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, dScrollPane, 0, SpringLayout.HORIZONTAL_CENTER, mainPanel);
        dScrollPane.setPreferredSize(new Dimension(250, 600));


        ImageIcon plus = new ImageIcon("./src/main/java/client/app/content/plus.png");

        addPass = new JButton(plus);
        //center addpass button
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, addPass, 0, SpringLayout.HORIZONTAL_CENTER, mainPanel);

        addPass.setPreferredSize(new Dimension(250, 50));
        addPass.addActionListener(this);
        // set background to transparent
        addPass.setOpaque(false);
        addPass.setContentAreaFilled(false);
        addPass.setBorderPainted(false);

        navPanel = new JPanel();
        BoxLayout boxLayout = new BoxLayout(navPanel, BoxLayout.Y_AXIS);
        //set boxlayout width to 250
        navPanel.setPreferredSize(new Dimension(300, 800));
        navPanel.setLayout(boxLayout);
        navPanel.setBackground(Color.WHITE);

        navPanel.add(new JLabel("Choose to edit an item or add a new one:"));
        navPanel.setAlignmentX(addPass.CENTER_ALIGNMENT);

        navPanel.add(dScrollPane);
        navPanel.add(addPass);

        // dList.setBorder(roundedLineBorder);
        mainPanel.add(navPanel);

        /**************************************************************************************/

        add(mainPanel, BorderLayout.WEST);
        // setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
        setSize(screenSize.width - 200, screenSize.height - 200);
        // setResizable(false);
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
        try {
            salt = userData.getString("salt");
            data = userData.getString("data");

            // decrypt data
            data = client.decrypt(data, salt);
            System.out.println("\ndecrypted data: " + data);
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }

        // parse data
        if (data != null) {
            JSONObject json = new JSONObject(data);
            // for each item in data
            for (String key : json.keySet()) {
                // add item name to list
                dmodel.addElement(key);

            }
        }

        // password display panel
        // **************************************************************************************/\
        dList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting()) {
                    System.out.println("\nclick detected");
                    try {
                        JSONObject userData = client.getUserData();
                        salt = userData.getString("salt");
                        data = userData.getString("data");
                        data = client.decrypt(data, salt);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    System.out.println("listener triggered");

                    String selected = dList.getSelectedValue();
                    System.out.println("selected: " + selected);
                    if (selected != null && !selected.equals("New Password")) {
                        // make json object from data
                        JSONObject json = new JSONObject(data);
                        // get password
                        JSONObject passData = json.getJSONObject(selected);
                        System.out.println("calling viewpanel...");
                        // get password data
                        usernameStr = passData.getString("username");
                        urlStr = passData.getString("url");
                        passStr = passData.getString("password");
                        notesStr = passData.getString("notes");
                        showPasswordPanel(selected, usernameStr, urlStr, passStr, notesStr);
                        addPanel.setVisible(true);
                        System.out.println("showpasswordpanel called");

                    }

                }

            }
        });

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addPass) {
            System.out.println("Add button pressed");
            // open add password window
            showPasswordPanel("", "", "", "", "");
            addPanel.setVisible(true);
            // add "New Password" to list
            dmodel.addElement("New Password");
            dList.setSelectedValue("New Password", true);

        }
    }

    public boolean isPasswordSelected() {
        return this.passwordSelected;
    }

    public void showPasswordPanel(String nameText, String usernameText, String urlText, String passwordText,
            String notesText) {

        if (addPanel != null) {
            addPanel.setVisible(false);
        }

        addPanel = new JPanel();
        addPanel.setBackground(Color.WHITE);
        addPanel.setPreferredSize(new Dimension(450, 400));
        addPanel.setLayout(new BoxLayout(addPanel, BoxLayout.Y_AXIS));

        JLabel addLabel = new JLabel("Add Password");
        addLabel.setFont(new Font("Arial", Font.BOLD, 20));
        addLabel.setForeground(new Color(27, 38, 79));
        addPanel.add(addLabel);

        JLabel nameLabel = new JLabel("Name");
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        nameLabel.setForeground(new Color(27, 38, 79));
        addPanel.add(nameLabel);

        JTextField name = new JTextField(nameText);
        name.setFont(new Font("Arial", Font.PLAIN, 16));
        name.setForeground(new Color(27, 38, 79));
        addPanel.add(name);

        JLabel urlLabel = new JLabel("URL");
        urlLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        urlLabel.setForeground(new Color(27, 38, 79));
        addPanel.add(urlLabel);

        JTextField url = new JTextField(urlText);
        url.setFont(new Font("Arial", Font.PLAIN, 16));
        url.setForeground(new Color(27, 38, 79));
        addPanel.add(url);

        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        usernameLabel.setForeground(new Color(27, 38, 79));
        addPanel.add(usernameLabel);

        JTextField username = new JTextField(usernameText);
        username.setFont(new Font("Arial", Font.PLAIN, 16));
        username.setForeground(new Color(27, 38, 79));
        addPanel.add(username);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        passwordLabel.setForeground(new Color(27, 38, 79));
        addPanel.add(passwordLabel);

        JTextField password = new JTextField(passwordText);
        password.setFont(new Font("Arial", Font.PLAIN, 16));
        password.setForeground(new Color(27, 38, 79));
        addPanel.add(password);

        JLabel notesLabel = new JLabel("Notes");
        notesLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        notesLabel.setForeground(new Color(27, 38, 79));
        addPanel.add(notesLabel);

        JTextField notes = new JTextField(notesText);
        notes.setFont(new Font("Arial", Font.PLAIN, 16));
        notes.setForeground(new Color(27, 38, 79));
        addPanel.add(notes);

        JButton save = new JButton("Save");
        save.setFont(new Font("Arial", Font.PLAIN, 16));
        save.setForeground(new Color(27, 38, 79));
        addPanel.add(save);

        JButton cancel = new JButton("Cancel");
        cancel.setFont(new Font("Arial", Font.PLAIN, 16));
        cancel.setForeground(new Color(27, 38, 79));
        addPanel.add(cancel);

        // actionlistener for save button
        save.addActionListener(l -> {
            String itemName = name.getText();

            dmodel.addElement(itemName);
            // remove "New Password" from list
            dmodel.removeElement("New Password");

            try {
                System.out.println("hi");
                client.addPass(itemName, username.getText(), password.getText(), url.getText(), notes.getText());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        addPanel.setVisible(true);

        mainPanel.add(addPanel);
        System.out.println("\nadd panel added");

    }

}