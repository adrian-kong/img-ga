package dev.ga.genes.impl;

import dev.ga.data.IData;
import dev.ga.data.Pixel;
import dev.ga.genes.IGene;
import dev.ga.genes.comp.Gene;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.stream.IntStream;

@AllArgsConstructor
public class ImageGene extends Gene {

    @Getter
    @Setter
    private Pixel[] positions;

    private BufferedImage image;

    @Override
    public IData<?>[] getData() {
        return positions;
    }

    @Override
    public double computeFitness() {

        double fitness = 0;

        for (Pixel pixel : positions) {
            int rgb = image.getRGB(pixel.getX(), pixel.getY());
            var r = (rgb >> 16) & 0xff;
            var g = (rgb >> 8) & 0xff;
            var b = rgb & 0xff;
            double y = 0.2126 * r + 0.7152 * g + 0.0722 * b; // ITU BT.709 Photometric/digital luminance
            fitness += y;
        }

        return fitness;
    }

    @Override
    public <T extends IGene> void crossOver(T gene) {
        Arrays.stream(getData()).forEach(a -> a.crossOver(gene.getData()));
    }
}
