package dev.ga.algorithm.impl;

import dev.ga.algorithm.IGeneticAlgorithm;
import dev.ga.data.Pixel;
import dev.ga.genes.IGene;
import dev.ga.genes.impl.ImageGene;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.awt.image.BufferedImage;
import java.util.*;
import java.util.function.IntFunction;
import java.util.stream.IntStream;

@RequiredArgsConstructor
public class ImageGeneticAlgorithm implements IGeneticAlgorithm<BufferedImage> {

    private final int populationSize = 100;

    private final int maxPixels = 1000;

    private final double mutationChance = 0.5d;

    private final IGene[] genes = new IGene[populationSize];

    private final Random random = new Random();

    @Override
    public void input(BufferedImage image) {
        for (int i = 0; i < populationSize; i++) {
            IntFunction<Pixel> pixelGen = o -> new Pixel(random.nextInt(image.getWidth()), random.nextInt(image.getHeight()));
            Pixel[] pixels = IntStream.range(0, maxPixels).mapToObj(pixelGen).toArray(Pixel[]::new);
            genes[i] = new ImageGene(pixels, image);
        }
    }

    @Override
    public IGeneticAlgorithm<BufferedImage> current() {
        Arrays.sort(genes);
        return this;
    }

    @Override
    public IGeneticAlgorithm<BufferedImage> next() {
        Arrays.sort(genes);
        for (int i = 0; i < populationSize / 2; i++) {
            // random mutation
            if (random.nextDouble() < mutationChance) {
                genes[i].mutate();
            }

            // cross over
            IGene gene = genes[random.nextInt(genes.length) - 1];
            gene.crossOver(genes[i]);
            genes[i].computeFitness();
        }

        for (int i = populationSize / 2; i < populationSize; i++) {
            genes[i].computeFitness();
        }

        Arrays.sort(genes);
        return this;
    }
}
