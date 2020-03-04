package main;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class MyList<E> implements Searcher<E> {

    List<E> list = new LinkedList<>();

    @Override
    public boolean contains(E element) {
        return list.contains(element);
    }

    @Override
    public void erase(E element) {
        list.remove(element);
    }

    @Override
    public void push(E element) {
        list.add(element);
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public Iterator<E> iterator() {
        return list.iterator();
    }
}
