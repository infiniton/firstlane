package client.app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.io.IOException;

import org.json.JSONObject;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class AppCore extends JFrame implements ActionListener {
     
    JPanel panel;
    
    JList<String> list;
    JScrollPane scrollPane;
    DefaultListModel<String> model;
    int maxSize;

    JButton addPass;
    JList<String> dList;
    JScrollPane dScrollPane;
    DefaultListModel<String> dmodel;

    String user;
    Client client;

    public AppCore(Client client) throws IOException {
        this.client = client;

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        SpringLayout layout = new SpringLayout();

        panel = new JPanel(layout);
        panel.setBackground(Color.WHITE);

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
    }

}