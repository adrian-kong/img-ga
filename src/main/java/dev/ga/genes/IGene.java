package dev.ga.genes;

import dev.ga.data.IData;
import dev.ga.genes.comp.Gene;

public interface IGene extends Comparable<IGene> {

    IData<?>[] getData();

    double getFitness();

    double computeFitness();

    void mutate();

    <T extends IGene> void crossOver(T gene);

    @Override
    default int compareTo(IGene o) {
        return Double.compare(o.getFitness(), getFitness());
    }
}
