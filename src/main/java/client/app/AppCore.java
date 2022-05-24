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

/***
 * Launched upon successful login
 * Displays the main application frame
 */
public class AppCore extends JFrame implements ActionListener {

    JPanel mainPanel, addPanel, navPanel, sidePanel, gapPanel;

    JList<String> list, passList;
    JScrollPane scrollPane, passScrollPane;
    DefaultListModel<String> model, listModel;
    JButton addPass, save, logout;
    JTextField message, name, username, password, url, notes;
    JSONObject storedPasswords, data;

    int maxSize, selectedIndex;
    BufferedImage image;
    String user, salt, nameStr, usernameStr, urlStr, passStr, notesStr, selectedUUID, passPanelMode;
    Client client;
    boolean passwordSelected, isDark, windowsOS;
    SpringLayout layout;
    GridBagConstraints c;
    Color fontColor, addPanelBg, color232;


    public AppCore(Client client) throws IOException {

        UIManager.LookAndFeelInfo looks[] = UIManager.getInstalledLookAndFeels();

        try {
            UIManager.setLookAndFeel(looks[3].getClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.client = client;

        passwordSelected = false;

        layout = new SpringLayout();
        JFrame frame = new JFrame("FirstLane");

        image = ImageIO.read(getClass().getResource("/client/app/content/logo-no-text.png"));

        // if os is windows, set color232 to white
        color232 = new Color(232, 232, 232);

        try {
            // set icon for mac os (and other systems which do support this method)
            final Taskbar taskbar = Taskbar.getTaskbar();

            taskbar.setIconImage(image);
        } catch (final UnsupportedOperationException e) {
            System.out.println("The os does not support: 'taskbar.setIconImage'");
        } catch (final SecurityException e) {
            System.out.println("There was a security exception for: 'taskbar.setIconImage'");
        }
        setIconImage(image);

        mainPanel = new JPanel(layout);
        frame.setContentPane(mainPanel);
        mainPanel.setBackground(Color.WHITE);

        mainPanel.setLayout(new GridBagLayout());
        c = new GridBagConstraints();

        // navPanel sidebar
        // **************************************************************************************/

        listModel = new DefaultListModel<>();
        passList = new JList<>(listModel);
        passList.setFixedCellHeight(50);
        passList.setLayoutOrientation(JList.VERTICAL);
        passList.setBackground(color232);

        passScrollPane = new JScrollPane(passList);
        passScrollPane.setViewportView(passList);
        passScrollPane.setBackground(Color.white);
        // passScrollPane.setMinimumSize(new Dimension(200, 300));
        // create a bottom border for the scrollpane
        passScrollPane.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));

        ImageIcon plus = new ImageIcon(ImageIO.read(Startup.class.getResource("/client/app/content/plus.png")));

        addPass = new JButton(plus);
        addPass.addActionListener(this);
        addPass.setOpaque(false);
        addPass.setContentAreaFilled(false);
        addPass.setBorderPainted(false);

        navPanel = new JPanel();
        BoxLayout boxLayout = new BoxLayout(navPanel, BoxLayout.Y_AXIS);
        // set boxlayout width to 250
        navPanel.setPreferredSize(new Dimension(275, 400));
        navPanel.setLayout(boxLayout);
        navPanel.setBackground(color232);
        navPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.GRAY));

        // navPanel.add(new JLabel("Choose to edit an item or add a new one:"));
        addPass.setSize(new Dimension(250, 50));
        addPass.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        navPanel.add(passScrollPane, BorderLayout.CENTER);
        navPanel.add(addPass);

        // sidePanel
        sidePanel = new JPanel();
        sidePanel.setPreferredSize(new Dimension(75, 400));
        sidePanel.setLayout(new GridBagLayout());
        sidePanel.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.GRAY));
        sidePanel.setBackground(color232);

        // add button to sidePanel
        JButton darkMode = new JButton();
        darkMode.setIcon(
                new ImageIcon(ImageIO.read(Startup.class.getResource("/client/app/content/darkMode-50px.png"))));
        darkMode.setBorderPainted(false);
        c = new GridBagConstraints();
        darkMode.setOpaque(false);
        darkMode.setContentAreaFilled(false);
        darkMode.setBorderPainted(false);

        c.gridx = 0;
        c.gridy = 1;
        // add top padding
        c.insets = new Insets(0, 0, 0, 0);
        sidePanel.add(darkMode, c);

        // add button to sidePanel
        logout = new JButton();
        logout.setIcon(
                new ImageIcon(ImageIO.read(Startup.class.getResource("/client/app/content/darkLock-closed-50px.png"))));
        logout.setBorderPainted(false);
        c = new GridBagConstraints();
        logout.setOpaque(false);
        logout.setContentAreaFilled(false);
        logout.setBorderPainted(false);
        c.gridx = 0;
        c.gridy = 2;
        // add top padding
        c.anchor = GridBagConstraints.PAGE_END;
        c.weighty = 10;
        c.insets = new Insets(0, 0, 0, 0);

        sidePanel.add(logout, c);

        logout.addActionListener(this);
        isDark = false;

        darkMode.addActionListener(l -> {
            if (!isDark) {
                navPanel.setBackground(new Color(32, 34, 37));
                sidePanel.setBackground(new Color(32, 34, 37));
                mainPanel.setBackground(new Color(56, 57, 62));
                passList.setBackground(new Color(47, 49, 54));
                passList.setForeground(Color.WHITE);
                fontColor = new Color(255, 255, 255);
                addPanelBg = new Color(56, 57, 62);

                try {
                    /*
                     * settings.setIcon(new ImageIcon(ImageIO.read(
                     * Startup.class.getResource(
                     * "/client/app/content/settings-50px-background-inverse.png"))));
                     */
                    darkMode.setIcon(new ImageIcon(
                            ImageIO.read(Startup.class.getResource("/client/app/content/darkMode-50px-inverse.png"))));
                    logout.setIcon(new ImageIcon(ImageIO
                            .read(Startup.class.getResource("/client/app/content/darkLock-closed-50px-inverse.png"))));

                } catch (IOException e) {
                    e.printStackTrace();
                }

                passPanelMode = "dark";
                showPasswordPanel("", "", "", "", "", passPanelMode);
                isDark = true;
            } else {
                navPanel.setBackground(color232);
                sidePanel.setBackground(color232);
                mainPanel.setBackground(Color.WHITE);
                passList.setBackground(color232);
                passList.setForeground(new Color(0, 0, 0));
                fontColor = new Color(0, 0, 0);
                addPanelBg = new Color(255, 255, 255);
                try {
                    /*
                     * settings.setIcon(new ImageIcon(ImageIO
                     * .read(Startup.class.getResource(
                     * "/client/app/content/settings-50px-background.png"))));
                     */
                    darkMode.setIcon(new ImageIcon(
                            ImageIO.read(Startup.class.getResource("/client/app/content/darkMode-50px.png"))));
                    logout.setIcon(new ImageIcon(
                            ImageIO.read(Startup.class.getResource("/client/app/content/darkLock-closed-50px.png"))));

                } catch (IOException e) {
                    e.printStackTrace();

                }

                passPanelMode = null;
                showPasswordPanel("", "", "", "", "", passPanelMode);
                isDark = false;

            }
        });

        c = new GridBagConstraints();
        c.gridx = 2;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.weightx = 1;
        c.weighty = 1;

        c.fill = GridBagConstraints.VERTICAL;
        c.anchor = GridBagConstraints.WEST;

        // c.anchor = GridBagConstraints.PAGE_END;

        mainPanel.add(navPanel, c);

        // mainPanel.add(navPanel);
        showPasswordPanel("", "", "", "", "", passPanelMode);
        // put sidePanel on the right side of the mainPanel
        layout.putConstraint(SpringLayout.EAST, sidePanel, 0, SpringLayout.EAST, mainPanel);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, addPanel, 0, SpringLayout.HORIZONTAL_CENTER, mainPanel);

        // contraints for sidepanel to keep it on the right side of the mainPanel
        c = new GridBagConstraints();

        c.gridx = 8;
        c.gridy = 0;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.weightx = 1;
        c.weighty = 1;

        // make sidepanel full height
        c.fill = GridBagConstraints.VERTICAL;
        c.anchor = GridBagConstraints.PAGE_END;
        c.insets = new Insets(0, 0, 0, 0);

        mainPanel.add(sidePanel, c);

        /**************************************************************************************/

        add(mainPanel, BorderLayout.WEST);
        // setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
        setSize(760, 430);
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
        try {
            salt = userData.getString("salt");
            data = new JSONObject(client.decrypt(userData.getString("data"), salt));
            System.out.println("\ndecrypted data: " + data);
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }

        // parse data
        if (data != null) {
            for (String key : data.keySet()) {

                // add item name to list
                // add itemname and uuid to new json object
                JSONObject item = data.getJSONObject(key);
                String itemName = item.getString("name");
                String itemURL = item.getString("url");
                // add to list alphabetically
                listModel.addElement("  " + itemName + " | " + itemURL);

            }
        }

        // password display panel
        // **************************************************************************************/\
        passList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting()) {
                    System.out.println("\nclick detected");

                    selectedIndex = passList.getSelectedIndex();
                    // get password from data at selected index
                    JSONObject passData = data.getJSONObject(data.keySet().toArray()[selectedIndex].toString());

                    System.out.println("calling showpasswordpanel...");
                    // get password data
                    nameStr = passData.getString("name");
                    usernameStr = passData.getString("username");
                    urlStr = passData.getString("url");
                    passStr = passData.getString("password");
                    notesStr = passData.getString("notes");
                    showPasswordPanel(nameStr, usernameStr, urlStr, passStr, notesStr, passPanelMode);
                    addPanel.setVisible(true);
                    System.out.println("showpasswordpanel called");

                }
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addPass) {
            System.out.println("Add button pressed");
            // open add password window
            // add "New Password" to list
            listModel.addElement("  New Password");
            passList.setSelectedValue("  New Password", true);
            showPasswordPanel("", "", "", "", "", passPanelMode);
            addPanel.setVisible(true);

        } else if (e.getSource() == logout) {
            System.out.println("Logout button pressed");
            // logout
            client.logout();
        }
    }

    public void showPasswordPanel(String nameText, String usernameText, String urlText, String passwordText,
            String notesText, String mode) {

        if (addPanel != null) {
            addPanel.setVisible(false);
        }

        Color color;

        if (mode == null) {
            color = new Color(255, 255, 255);
        } else {
            color = new Color(65, 68, 74);
        }

        int leftPadding = 10;
        int textWidth = 29;
        int gridWidth = 3;

        addPanel = new JPanel();
        addPanel.setBackground(addPanelBg);
        addPanel.setPreferredSize(new Dimension(410, 400));
        addPanel.setLayout(new GridBagLayout());
        c = new GridBagConstraints();

        JLabel addLabel = new JLabel("Add Password");
        addLabel.setFont(new Font("Arial", Font.BOLD, 20));
        addLabel.setForeground(fontColor);
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.gridwidth = gridWidth;
        c.anchor = GridBagConstraints.WEST;
        c.insets = new Insets(0, leftPadding, 0, 0);
        addPanel.add(addLabel, c);

        JLabel nameLabel = new JLabel("Name");
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        nameLabel.setForeground(fontColor);
        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 1;
        c.gridwidth = gridWidth;
        c.anchor = GridBagConstraints.WEST;
        c.insets = new Insets(0, leftPadding, 0, 0);

        addPanel.add(nameLabel, c);

        JTextField name = new JTextField(nameText, textWidth);
        name.setFont(new Font("Arial", Font.PLAIN, 16));
        name.setForeground(fontColor);
        name.setBackground(color);
        name.setMinimumSize(new Dimension(10 * textWidth, 24));
        c.gridx = 0;
        c.gridy = 2;
        c.weightx = 1;
        c.gridwidth = gridWidth;
        c.anchor = GridBagConstraints.WEST;
        c.insets = new Insets(0, leftPadding, 0, 0);

        addPanel.add(name, c);

        JLabel urlLabel = new JLabel("URL");
        urlLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        urlLabel.setForeground(fontColor);
        c.gridx = 0;
        c.gridy = 3;
        c.weightx = 1;
        c.gridwidth = gridWidth;

        c.anchor = GridBagConstraints.WEST;
        c.insets = new Insets(0, leftPadding, 0, 0);

        addPanel.add(urlLabel, c);

        JTextField url = new JTextField(urlText, textWidth);
        url.setFont(new Font("Arial", Font.PLAIN, 16));
        url.setForeground(fontColor);
        url.setBackground(color);
        url.setMinimumSize(new Dimension(10 * textWidth, 24));

        c.gridx = 0;
        c.gridy = 4;
        c.weightx = 1;
        c.gridwidth = gridWidth;

        c.anchor = GridBagConstraints.WEST;
        c.insets = new Insets(0, leftPadding, 0, 0);

        addPanel.add(url, c);

        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        usernameLabel.setForeground(fontColor);
        c.gridx = 0;
        c.gridy = 5;
        c.weightx = 1;
        c.gridwidth = gridWidth;

        c.anchor = GridBagConstraints.WEST;
        c.insets = new Insets(0, leftPadding, 0, 0);

        addPanel.add(usernameLabel, c);

        JTextField username = new JTextField(usernameText, textWidth);
        username.setFont(new Font("Arial", Font.PLAIN, 16));
        username.setForeground(fontColor);
        username.setBackground(color);
        username.setMinimumSize(new Dimension(10 * textWidth, 24));
        c.gridx = 0;
        c.gridy = 6;
        c.weightx = 1;
        c.gridwidth = gridWidth;

        c.anchor = GridBagConstraints.WEST;
        c.insets = new Insets(0, leftPadding, 0, 0);

        addPanel.add(username, c);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        passwordLabel.setForeground(fontColor);
        c.gridx = 0;
        c.gridy = 7;
        c.weightx = 1;
        c.gridwidth = gridWidth;

        c.anchor = GridBagConstraints.WEST;
        c.insets = new Insets(0, leftPadding, 0, 0);

        addPanel.add(passwordLabel, c);

        JTextField password = new JTextField(passwordText, textWidth);
        password.setFont(new Font("Arial", Font.PLAIN, 16));
        password.setForeground(fontColor);
        password.setBackground(color);
        password.setMinimumSize(new Dimension(10 * textWidth, 24));
        c.gridx = 0;
        c.gridy = 8;
        c.weightx = 1;
        c.gridwidth = gridWidth;

        c.anchor = GridBagConstraints.WEST;
        c.insets = new Insets(0, leftPadding, 0, 0);

        addPanel.add(password, c);

        JLabel notesLabel = new JLabel("Notes");
        notesLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        notesLabel.setForeground(fontColor);
        c.gridx = 0;
        c.gridy = 9;
        c.weightx = 1;
        c.gridwidth = gridWidth;

        c.anchor = GridBagConstraints.WEST;
        c.insets = new Insets(0, leftPadding, 0, 0);

        addPanel.add(notesLabel, c);

        JTextField notes = new JTextField(notesText, textWidth);
        notes.setFont(new Font("Arial", Font.PLAIN, 16));
        notes.setForeground(fontColor);
        notes.setBackground(color);
        notes.setMinimumSize(new Dimension(10 * textWidth, 24));
        c.gridx = 0;
        c.gridy = 10;
        c.weightx = 1;
        c.gridwidth = gridWidth;

        c.anchor = GridBagConstraints.WEST;
        c.insets = new Insets(0, leftPadding, 0, 0);

        addPanel.add(notes, c);

        JButton save = new JButton("Save as new");
        save.setPreferredSize(new Dimension(125, 25));
        save.setFont(new Font("Arial", Font.PLAIN, 16));
        save.setForeground(new Color(27, 38, 79));
        c.gridx = 0;
        c.gridy = 11;
        c.weightx = 1;
        c.gridwidth = 2;

        c.anchor = GridBagConstraints.WEST;
        c.insets = new Insets(0, leftPadding, 0, 0);

        addPanel.add(save, c);

        JButton delete = new JButton("Delete");
        delete.setPreferredSize(new Dimension(100, 25));

        delete.setFont(new Font("Arial", Font.PLAIN, 16));
        delete.setForeground(new Color(27, 38, 79));
        c.gridx = 0;
        c.gridy = 11;
        c.weightx = 0.67;
        c.anchor = GridBagConstraints.WEST;
        c.insets = new Insets(0, leftPadding + 125, 0, 0);

        addPanel.add(delete, c);

        JButton cancel = new JButton("Cancel");
        cancel.setPreferredSize(new Dimension(100, 25));
        cancel.setFont(new Font("Arial", Font.PLAIN, 16));
        cancel.setForeground(new Color(27, 38, 79));
        c.gridx = 0;
        c.gridy = 11;
        c.weightx = 0.33;
        c.anchor = GridBagConstraints.WEST;
        c.insets = new Insets(0, leftPadding + 225, 0, 0);

        addPanel.add(cancel, c);

        // actionlistener for save button
        save.addActionListener(l -> {
            try {
                String itemName = name.getText()/* + UUID.randomUUID().toString() */;

                // check for changes in the fields
                if (!(name.getText().equals(nameText)) || !(url.getText().equals(urlText))
                        || !(username.getText().equals(usernameText))
                        || !(password.getText().equals(passwordText)) || !(notes.getText().equals(notesText))) {
                    System.out.println("changes detected");
                    System.out.println(name.getText() + " should equal " + nameText);
                    System.out.println(url.getText() + " should equal " + urlText);
                    System.out.println(username.getText() + " should equal " + usernameText);
                    System.out.println(password.getText() + " should equal " + passwordText);
                    System.out.println(notes.getText() + " should equal " + notesText);

                    listModel.addElement(itemName);
                    // remove "New Password" from list

                    listModel.removeElement("New Password");
                    data = client.addPass(itemName, username.getText(), password.getText(), url.getText(),
                            notes.getText());

                    // remove all elements from list
                    listModel.removeAllElements();

                    if (data != null) {
                        for (String key : data.keySet()) {
                            // add item name to list
                            // add itemname and uuid to new json object
                            JSONObject item = data.getJSONObject(key);
                            String passName = item.getString("name");
                            String itemURL = item.getString("url");
                            listModel.addElement("  " + passName + " | " + itemURL);
                        }
                    }

                } else {
                    System.out.println("no changes detected");
                    // popup window saying no changes detected
                    int dialogResult = JOptionPane.showConfirmDialog(null,
                            "No changes have been made to the password, do you still want to save as new?", "FirstLane",
                            JOptionPane.YES_NO_OPTION);
                    if (dialogResult == JOptionPane.YES_OPTION) {
                        listModel.addElement(itemName);
                        // remove "New Password" from list

                        listModel.removeElement("New Password");
                        data = client.addPass(itemName, username.getText(), password.getText(), url.getText(),
                                notes.getText());

                        // remove all elements from list
                        listModel.removeAllElements();

                        if (data != null) {
                            for (String key : data.keySet()) {
                                // add item name to list
                                // add itemname and uuid to new json object
                                JSONObject item = data.getJSONObject(key);
                                String passName = item.getString("name");
                                String itemURL = item.getString("url");
                                listModel.addElement("  " + passName + " | " + itemURL);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // actionlistener for delete button
        delete.addActionListener(l -> {
            try {
                selectedIndex = passList.getSelectedIndex();
                System.out.println("delete button pressed");
                // yes or no dialog
                int dialogResult = JOptionPane.showConfirmDialog(null,
                        "You are about to delete this password. Do you wish to proceed?", "FirstLane",
                        JOptionPane.YES_NO_OPTION);
                if (dialogResult == JOptionPane.YES_OPTION) {
                    System.out.println("yes");
                    // delete password
                    data = client.deletePass(selectedIndex);
                    // remove from list
                    listModel.removeElement("  " + nameText + " | " + urlText);
                    showPasswordPanel("", "", "", "", "", passPanelMode);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // actionlistener for cancel button
        cancel.addActionListener(l -> {
            // reset text fields
            name.setText(nameText);
            username.setText(usernameText);
            url.setText(urlText);
            password.setText(passwordText);
            notes.setText(notesText);

        });

        addPanel.setVisible(true);

        // grid layout constraints to keep it centered
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 3;
        c.gridy = 0;
        c.gridwidth = 2;
        c.gridheight = 1;

        mainPanel.add(addPanel, c);

        System.out.println("\nadd panel added");

    }

}