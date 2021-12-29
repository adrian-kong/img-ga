package dev.ga.genes.comp;

import dev.ga.genes.IGene;

public abstract class Gene implements IGene {

    protected double fitness = -1;

    @Override
    public double getFitness() {
        return fitness == -1 ? fitness = computeFitness() : fitness;
    }

    @Override
    public double computeFitness() {
        return fitness;
    }
}
