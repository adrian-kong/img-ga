package dev.ga.genes.comp;

import dev.ga.genes.IGene;

public abstract class Gene implements IGene {

    private double fitness = -1;

    @Override
    public double getFitness() {
        return fitness == -1 ? computeFitness() : fitness;
    }

    @Override
    public double computeFitness() {
        return fitness;
    }

    @Override
    public void mutate() {

    }
}
