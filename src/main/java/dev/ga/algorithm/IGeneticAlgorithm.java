package dev.ga.algorithm;

public interface IGeneticAlgorithm<E> {

    void input(E data);

    IGeneticAlgorithm<E> next();

    default IGeneticAlgorithm<E> current() {
        return this;
    }
}
