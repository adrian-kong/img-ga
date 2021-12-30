package dev.ga.algorithm.impl;

import dev.ga.algorithm.IGeneticAlgorithm;
import dev.ga.algorithm.comp.GeneticAlgorithm;
import dev.ga.data.Pixel;
import dev.ga.genes.IGene;
import dev.ga.genes.impl.ImageGene;
import lombok.RequiredArgsConstructor;

import java.awt.image.BufferedImage;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.IntFunction;
import java.util.stream.IntStream;

@RequiredArgsConstructor
public class ImageGeneticAlgorithm extends GeneticAlgorithm<BufferedImage, ImageGene> {

    private final int populationSize = 100;

    private final int maxPixels = 50_000;

    private final double mutationChance = 0.05d;

    private final IGene[] genes = new IGene[populationSize];

    private final ThreadPoolExecutor threadPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(12);

    public ImageGene getBest() {
        return ((ImageGene) genes[0]);
    }

    @Override
    protected ImageGene generate() {
        IntFunction<Pixel> pixelGen = o -> new Pixel(ThreadLocalRandom.current().nextInt(data.getWidth()),
                ThreadLocalRandom.current().nextInt(data.getHeight()));
        Pixel[] pixels = IntStream.range(0, maxPixels).mapToObj(pixelGen).toArray(Pixel[]::new);
        return new ImageGene(pixels, data);
    }

    @Override
    public IGeneticAlgorithm<BufferedImage> next() {
        long lastMs = System.currentTimeMillis();

        CountDownLatch countDownLatch = new CountDownLatch(populationSize / 2);
        for (int i = 0; i < populationSize / 2; i++) {
            int finalI = i;
            threadPool.submit(() -> {
                // random mutation
                if (ThreadLocalRandom.current().nextDouble() < mutationChance) {
                    genes[finalI].mutate();
                }

                // cross over
                IGene gene = genes[populationSize / 2 + ThreadLocalRandom.current().nextInt(populationSize / 2)];
                gene.crossOver(genes[finalI]);
                gene.computeFitness();
                genes[finalI].computeFitness();
                countDownLatch.countDown();
            });
        }
        try {
            countDownLatch.await();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        sort();
        System.out.println("Time taken: " + (System.currentTimeMillis() - lastMs) / 1000f);
        return this;
    }
}
