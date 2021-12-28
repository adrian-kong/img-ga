package dev.ga.agent;

import java.util.Comparator;

public interface IGene extends Comparable<IGene> {

    int getFitness();

    void computeFitness();

    void mutate();

    @Override
    default int compareTo(IGene o) {
        return Integer.compare(o.getFitness(), getFitness());
    }
}
