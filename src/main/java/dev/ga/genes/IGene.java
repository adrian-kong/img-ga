package dev.ga.genes;

import dev.ga.data.IData;
import dev.ga.genes.comp.Gene;

import java.util.Comparator;

public interface IGene {

    IData<?>[] getData();

    double getFitness();

    double computeFitness();

    void mutate();

    <T extends IGene> void crossOver(T gene);
}
