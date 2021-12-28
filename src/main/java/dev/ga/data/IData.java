package dev.ga.data;

public interface IData<E> {

    E getData();

    void crossOver(IData<?>[] data);

}
