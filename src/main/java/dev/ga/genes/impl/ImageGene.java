package dev.ga.genes.impl;

import dev.ga.data.IData;
import dev.ga.data.impl.Pixel;
import dev.ga.genes.IGene;
import dev.ga.genes.comp.Gene;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

import static dev.ga.util.ImageUtil.getLuminance;

@AllArgsConstructor
public class ImageGene extends Gene<BufferedImage> {

    @Getter
    @Setter
    private Pixel[] positions;

    @Override
    public IData<Pixel>[] getData() {
        return positions;
    }

    @Override
    public void computeFitness(BufferedImage image) {
        fitness = Arrays.stream(positions)
                .parallel()
                .mapToDouble(pix -> getLuminance(image, pix.getX(), pix.getY()))
                .filter(lum -> lum != 0)
                .map(Math::log)
                .sum();

    }

    @Override
    public void mutate(BufferedImage image) {
        for (Pixel pixel : positions) {
            pixel.setX(ThreadLocalRandom.current().nextInt(image.getWidth()));
            pixel.setY(ThreadLocalRandom.current().nextInt(image.getHeight()));
        }
    }

    @Override
    public <T extends IGene> void crossOver(T gene) {
        for (int i = 0; i < getData().length; i++) {
            getData()[i].crossOver((Pixel) gene.getData()[i].getData());
        }
    }
}
