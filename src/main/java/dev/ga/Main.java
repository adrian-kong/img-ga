package dev.ga;

import dev.ga.algorithm.impl.ImageGeneticAlgorithm;
import dev.ga.data.IData;
import dev.ga.data.Pixel;
import dev.ga.genes.impl.ImageGene;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.concurrent.Executor;


public class Main {

    public static void main(String[] args) throws Exception {
//        new Main().run();
        new Frame().run();
    }

    private void run() throws Exception {
        BufferedImage image = ImageIO.read(new File("peng.png"));

        JFrame frame = buildFrame();

        frame.add(new JCustomPanel(image));

        new Timer(0, e -> SwingUtilities.updateComponentTreeUI(frame)).start();

    }

    private static final class JCustomPanel extends JPanel {

        private final BufferedImage image;

        private final double scaledWidth;
        private final double scaledHeight;

        private final ImageGeneticAlgorithm algorithm;

        public JCustomPanel(BufferedImage image) {
            this.image = image;
            scaledWidth = 200d / image.getWidth();
            scaledHeight = 200d / image.getHeight();

            algorithm = new ImageGeneticAlgorithm();
            algorithm.input(image);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            System.out.println(algorithm.getBest().getFitness());
            for (IData<Pixel> datum : algorithm.getBest().getData()) {
                Pixel pixel = datum.getData();
                g.drawRect(((int) (pixel.getX() * scaledWidth)), ((int) (pixel.getY() * scaledHeight)), 1, 1);
            }
            algorithm.next();
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
