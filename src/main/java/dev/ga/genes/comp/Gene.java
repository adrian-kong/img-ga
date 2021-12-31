package dev.ga.genes.comp;

import dev.ga.genes.IGene;
import lombok.Getter;

public abstract class Gene<E> implements IGene<E> {

    @Getter
    protected double fitness = -1;

}
