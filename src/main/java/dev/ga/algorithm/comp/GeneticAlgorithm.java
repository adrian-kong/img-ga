package dev.ga.algorithm.comp;

import dev.ga.algorithm.IGeneticAlgorithm;
import dev.ga.genes.IGene;
import dev.ga.genes.comp.Gene;
import dev.ga.genes.impl.ImageGene;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public abstract class GeneticAlgorithm<E, G extends IGene> implements IGeneticAlgorithm<E> {

    protected int populationSize = 50;

    protected double mutationChance = 0.05d;

    protected E data;

    protected G[] genes;

    @Override
    @SuppressWarnings("unchecked")
    public void input(Class<?> clazz, E data) {
        this.data = data;
        genes = ((G[]) Array.newInstance(clazz, populationSize));
        for (int i = 0; i < populationSize; i++) {
            genes[i] = generate();
        }
        sort();
    }

    public G getBest() {
        return genes[0];
    }

    protected void sort() {
        Arrays.sort(genes);
    }

    protected abstract G generate();

}
