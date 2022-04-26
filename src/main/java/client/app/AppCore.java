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

    JPanel mainPanel, addPanel, navPanel, sidePanel;

    JList<String> list, passList;
    JScrollPane scrollPane, passScrollPane;
    DefaultListModel<String> model, listModel;
    JButton addPass, save;
    JTextField message, name, username, password, url, notes;
    JSONObject storedPasswords, data;

    int maxSize, selectedIndex;
    BufferedImage image;
    String user, salt, nameStr, usernameStr, urlStr, passStr, notesStr, selectedUUID;
    Client client;
    boolean passwordSelected;

    final Taskbar taskbar = Taskbar.getTaskbar();

    public AppCore(Client client) throws IOException {
        this.client = client;

        passwordSelected = false;

        // Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        SpringLayout layout = new SpringLayout();
        JFrame frame = new JFrame("FirstLane");

        image = ImageIO.read(getClass().getResource("/client/app/content/logo-no-text.png"));

        try {
            // set icon for mac os (and other systems which do support this method)
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

        FlowLayout flowLayout = new FlowLayout();
        mainPanel.setLayout(flowLayout);

        // navPanel sidebar
        // **************************************************************************************/

        listModel = new DefaultListModel<>();
        passList = new JList<>(listModel);
        passList.setFixedCellHeight(50);
        passList.setLayoutOrientation(JList.VERTICAL);

        passScrollPane = new JScrollPane(passList);
        passScrollPane.setViewportView(passList);
        // create a bottom border for the scrollpane
        passScrollPane.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));

        ImageIcon plus = new ImageIcon("./src/main/java/client/app/content/plus.png");

        addPass = new JButton(plus);
        addPass.addActionListener(this);
        addPass.setBorderPainted(false);

        navPanel = new JPanel();
        BoxLayout boxLayout = new BoxLayout(navPanel, BoxLayout.Y_AXIS);
        // set boxlayout width to 250
        navPanel.setPreferredSize(new Dimension(275, 600));
        navPanel.setLayout(boxLayout);
        navPanel.setBackground(Color.WHITE);
        navPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.GRAY));

        // navPanel.add(new JLabel("Choose to edit an item or add a new one:"));
        addPass.setSize(new Dimension(250, 50));
        addPass.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        navPanel.add(passScrollPane);
        navPanel.add(addPass);

        mainPanel.add(navPanel);
        showPasswordPanel("", "", "", "", "");

        /*
         * sidePanel = new JPanel();
         * BoxLayout sideBoxLayout = new BoxLayout(sidePanel, BoxLayout.Y_AXIS);
         * // set boxlayout width to 275
         * sidePanel.setPreferredSize(new Dimension(275, 600));
         * sidePanel.setLayout(sideBoxLayout);
         * sidePanel.setBackground(Color.WHITE);
         * sidePanel.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.GRAY));
         * mainPanel.add(sidePanel);
         */

        /**************************************************************************************/

        add(mainPanel, BorderLayout.WEST);
        // setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
        setSize(740, 635);
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
                listModel.addElement(itemName + " | " + itemURL);
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
                    showPasswordPanel(nameStr, usernameStr, urlStr, passStr, notesStr);
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
            listModel.addElement("New Password");
            passList.setSelectedValue("New Password", true);
            showPasswordPanel("", "", "", "", "");
            addPanel.setVisible(true);

        }
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

        JButton delete = new JButton("Delete");
        delete.setFont(new Font("Arial", Font.PLAIN, 16));
        delete.setForeground(new Color(27, 38, 79));
        addPanel.add(delete);

        JButton cancel = new JButton("Cancel");
        cancel.setFont(new Font("Arial", Font.PLAIN, 16));
        cancel.setForeground(new Color(27, 38, 79));
        addPanel.add(cancel);

        // actionlistener for save button
        save.addActionListener(l -> {
            try {
                // check for changes in the fields
                if (name.getText().equals(nameText) || url.getText() != urlText || username.getText() != usernameText
                        || password.getText() != passwordText || notes.getText() != notesText) {
                    System.out.println("changes detected");
                    String itemName = name.getText()/* + UUID.randomUUID().toString() */;
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
                            listModel.addElement(passName + " | " + itemURL);
                        }
                    }

                } else {
                    System.out.println("no changes detected");
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
                    listModel.removeElement(nameText);
                    showPasswordPanel("", "", "", "", "");
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

        mainPanel.add(addPanel);

        System.out.println("\nadd panel added");

    }

}