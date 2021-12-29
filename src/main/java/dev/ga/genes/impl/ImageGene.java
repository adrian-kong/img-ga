package dev.ga.genes.impl;

import dev.ga.data.IData;
import dev.ga.data.Pixel;
import dev.ga.genes.IGene;
import dev.ga.genes.comp.Gene;
import dev.ga.util.MathUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

@AllArgsConstructor
public class ImageGene extends Gene {

    @Getter
    @Setter
    private Pixel[] positions;

    private BufferedImage image;

    @Override
    public IData<Pixel>[] getData() {
        return positions;
    }

    @Override
    public double computeFitness() {
        fitness = 0;
        for (Pixel pixel : positions) {
            int rgb = image.getRGB(pixel.getX(), pixel.getY());
            var r = (rgb >> 16) & 0xff;
            var g = (rgb >> 8) & 0xff;
            var b = rgb & 0xff;
            float y = 0.2126f * r + 0.7152f * g + 0.0722f * b; // ITU BT.709 Photometric/digital luminance
            if (y != 0) {
//                fitness += y;
                fitness += MathUtils.log2(y);
            }
        }

        return fitness;
    }

    @Override
    public void mutate() {
        for (Pixel pixel : positions) {
            if (ThreadLocalRandom.current().nextBoolean()) {
                pixel.setX(ThreadLocalRandom.current().nextInt(image.getWidth()));
                pixel.setY(ThreadLocalRandom.current().nextInt(image.getHeight()));
            }
        }
    }

    @Override
    public <T extends IGene> void crossOver(T gene) {
        for (int i = 0; i < getData().length; i++) {
            getData()[i].crossOver((Pixel) gene.getData()[i].getData());
        }
    }
}
