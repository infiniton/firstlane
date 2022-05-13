package client.app;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.*;

public class Settings extends JFrame /*implements ActionListener */{

    JPanel panel;
    JLabel colorLabel, fontLabel;
    JButton backButton, saveButton;
    JComboBox<String> colorCombo;

    Client client;

    BufferedImage image;

    public Settings(Client client) throws IOException {
        JFrame frame = new JFrame("FirstLane Setings | " + client.getUsername());
        this.client = client;
        String[] colorOptions = {"Dark", "Light"};

        GridBagLayout layout = new GridBagLayout();

        panel = new JPanel(layout);
        frame.setContentPane(panel);
        panel.setBackground(Color.WHITE);

        colorLabel = new JLabel("Color");        
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        panel.add(colorLabel, c);

        colorCombo = new JComboBox<String>(colorOptions);
        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        panel.add(colorCombo, c);

        add(panel);
        setSize(300, 600);
        setVisible(true);

    }

}
