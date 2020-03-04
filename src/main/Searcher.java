package main;

public interface Searcher<T> extends Iterable<T> {

    boolean contains(T element);

    void erase(T element);

    void push(T element);

    int size();
}
