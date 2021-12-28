package dev.ga;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;


public class Main {

    public static void main(String[] args) throws Exception {
        new Main().run();
    }

    private void run() throws Exception {
        BufferedImage image = ImageIO.read(new File("peng.png"));

        JFrame frame = buildFrame();

        frame.add(new JCustomPanel(image));

        new Thread(() -> {
            while (true) {
                SwingUtilities.updateComponentTreeUI(frame);
            }
        }).start();
    }

    private static final class JCustomPanel extends JPanel {

        private final BufferedImage image;

        public JCustomPanel(BufferedImage image) {
            this.image = image;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Image im = image.getScaledInstance(200, 200, 0);
            g.drawImage(im, 0, 0, null);
        }

    }

    private JFrame buildFrame() {
        return new JFrame() {{
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setSize(250, 250);
            setVisible(true);
        }};
    }
}
