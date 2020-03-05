package main;

import base.Pair;

public interface Searcher<T, E> extends Iterable<Pair<T, E>> {

    boolean containsKey(Object key);

    E get(Object key);

    void erase(T key);

    void push(T key, E value);

    int size();

}
