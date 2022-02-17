import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Graphics2D;

public class Panel extends JPanel {
    // PROPERTIES
    private final int DEFAULT_WIDTH = 500;
    private final int DEFAULT_HEIGHT = 500
    ;
    private final Color BACK_COLOR = Color.WHITE;
    private int x1, y1, x2, y2; // coordinates of the mouse

    private static int[][] pic;

    private static BufferedImage image = new BufferedImage(400, 400, BufferedImage.TYPE_INT_RGB);

    private static Graphics2D g2d = image.createGraphics();

    private MouseHandler handler;
    private static Graphics g;

    // CONSTRUCTOR
    public Panel() {

        setBackground(BACK_COLOR);
        setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));

        handler = new MouseHandler();

        g2d.setColor(Color.white);

        this.addMouseListener(handler);
        this.addMouseMotionListener(handler);
    }

    private void drawGraphicsOn() {
        g = getGraphics();
    }

    private class MouseHandler extends MouseAdapter {
        public void mousePressed(MouseEvent e) {
            x1 = e.getX();
            y1 = e.getY();

            x2 = x1;
            y2 = y1;
        }

       public void mouseDragged(MouseEvent e){
            x1 = e.getX();
            y1 = e.getY();
            g.drawLine(x1, y1, x2, y2);
            g2d.drawLine(x1, y1, x2, y2);

            x2 = x1;
            y2 = y1;


       }
    }

}
