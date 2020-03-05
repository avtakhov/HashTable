package main;

import base.Pair;

import java.io.PrintStream;
import java.util.Comparator;
import java.util.Random;

public class HashTable<T, E> {

    private final Comparator<? super T> comparator;
    private final int p = 1_000_000_000 + 7;
    private final int START_CAPACITY = 8;
    @SuppressWarnings("unchecked")
    private Searcher<T, E>[] table = (Searcher<T, E>[]) new Searcher[START_CAPACITY];
    private int size = 0;
    private long a = nextRandom();
    private long b = nextRandom();

    private Random random = new Random();

    public HashTable() {
        comparator = new Comparator<T>() {
            @Override
            public int compare(T t1, T t2) {
                @SuppressWarnings("unchecked")
                Comparable<T> x = (Comparable<T>) t1;
                return x.compareTo(t2);
            }
        };
        fillTable();
    }

    public HashTable(Comparator<? super T> comparator) {
        this.comparator = comparator;
        fillTable();
    }

    private void fillTable() {
        for (int i = 0; i < table.length; i++) {
            table[i] = /* change me */ new SplayTree<>();
        }
    }

    private int nextRandom() {
        return Math.abs(random.nextInt()) % p;
    }

    private int getIndex(Object element) {

        return (int) (Math.abs(element.hashCode() * a + b) % p % table.length);
    }

    public void push(T element, E value) {
        rebuild();
        table[getIndex(element)].push(element, value);
        size++;
    }

    public void erase(T key) {
        table[getIndex(key)].erase(key);
        size--;
    }

    public boolean containsKey(Object key) {
        // ((SplayTree<T, E>)table[getIndex(key)]).print(System.out);
        return table[getIndex(key)].containsKey(key);
    }

    public E get(Object key) {
        return table[getIndex(key)].get(key);
    }

    private void rebuild() {
        if (size() * 2 > table.length) {
            a = nextRandom() | 1;
            b = nextRandom();
            Searcher<T, E>[] buffer = table;
            table = (Searcher<T, E>[]) new Searcher[table.length * 2];
            fillTable();
            for (Searcher<T, E> ts : buffer) {
                for (Pair<T, E> element : ts) {
                    table[getIndex(element.first)].push(element.first, element.second);
                }
            }
        }
    }

    public int size() {
        return size;
    }

    public void print(PrintStream out) {
        for (Searcher<T, E> ts : table) {
            ((SplayTree) ts).print(out);
        }
        System.out.println("====================");
    }

}
