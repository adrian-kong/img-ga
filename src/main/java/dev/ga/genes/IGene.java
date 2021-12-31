package dev.ga.genes;

import dev.ga.data.IData;

public interface IGene<E> extends Comparable<IGene> {

    IData<?>[] getData();

    double getFitness();

    void computeFitness(E data);

    void mutate(E data);

    <T extends IGene> void crossOver(T gene);

    @Override
    default int compareTo(IGene o) {
        return Double.compare(o.getFitness(), getFitness());
    }
}
