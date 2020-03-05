package main;

import base.Pair;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class MyList<T, E> implements Searcher<T, E> {

    List<Pair<T, E>> list = new LinkedList<Pair<T, E>>();

    @Override
    public boolean containsKey(Object key) {
        return false;
    }

    @Override
    public E get(Object key) {
        return null;
    }

    @Override
    public void erase(T key) {

    }

    @Override
    public void push(T key, E value) {

    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public Iterator<Pair<T, E>> iterator() {
        return list.iterator();
    }
}
