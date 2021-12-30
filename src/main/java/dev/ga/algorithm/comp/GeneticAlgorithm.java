package dev.ga.algorithm.comp;

import dev.ga.algorithm.IGeneticAlgorithm;
import dev.ga.genes.IGene;
import dev.ga.genes.comp.Gene;

import java.util.Arrays;
import java.util.stream.IntStream;

public abstract class GeneticAlgorithm<E, G extends IGene> implements IGeneticAlgorithm<E> {

    protected int populationSize = 100;

    protected double mutationChance = 0.05d;

    protected E data;

    protected G[] genes;

    @Override
    @SuppressWarnings("unchecked")
    public void input(E data) {
        this.data = data;
        genes = (G[]) IntStream.range(0, populationSize).mapToObj(i -> generate()).sorted().toArray();
    }

    public G getBest() {
        return genes[0];
    }

    protected void sort() {
        Arrays.sort(genes);
    }

    protected abstract G generate();

}
