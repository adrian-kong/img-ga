package dev.ga.algorithm.impl;

import dev.ga.algorithm.IGeneticAlgorithm;
import dev.ga.data.Pixel;
import dev.ga.genes.IGene;
import dev.ga.genes.comp.Gene;
import dev.ga.genes.impl.ImageGene;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.awt.image.BufferedImage;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.IntFunction;
import java.util.stream.IntStream;

@RequiredArgsConstructor
public class ImageGeneticAlgorithm implements IGeneticAlgorithm<BufferedImage> {

    private final int populationSize = 100;

    private final int maxPixels = 10000;

    private final double mutationChance = 0.05d;

    private final IGene[] genes = new IGene[populationSize];

    private final ThreadPoolExecutor threadPool = (ThreadPoolExecutor) Executors.newCachedThreadPool();

    @Override
    public void input(BufferedImage image) {
        for (int i = 0; i < populationSize; i++) {
            IntFunction<Pixel> pixelGen = o -> new Pixel(ThreadLocalRandom.current().nextInt(image.getWidth()),
                    ThreadLocalRandom.current().nextInt(image.getHeight()));
            Pixel[] pixels = IntStream.range(0, maxPixels).mapToObj(pixelGen).toArray(Pixel[]::new);
            genes[i] = new ImageGene(pixels, image);
        }
        sortArray();
    }

    public ImageGene getBest() {
        return ((ImageGene) genes[0]);
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

        sortArray();
        System.out.println("Time taken: " + (System.currentTimeMillis() - lastMs) / 1000f);
        return this;
    }

    private void sortArray() {
        Arrays.parallelSort(genes, Comparator.comparingDouble(IGene::getFitness));
    }
}
