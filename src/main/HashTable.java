package main;

import java.util.Comparator;
import java.util.Random;

public class HashTable<T> {

    private void fillTable() {
        for (int i = 0; i < table.length; i++) {
            table[i] = /* change me */ new MyList<>();
        }
    }

    private final Comparator<T> comparator;
    private final int START_CAPACITY = 8;

    private Searcher<T>[] table = (Searcher<T>[]) new Searcher[START_CAPACITY];
    private int size = 0;

    public HashTable() {
        comparator = new Comparator<T>() {
            @Override
            public int compare(T t1, T t2) {
                Comparable<T> x = (Comparable<T>) t1;
                return x.compareTo(t2);
            }
        };
        fillTable();
    }

    public HashTable(Comparator<T> comparator) {
        this.comparator = comparator;
        fillTable();
    }

    private Random random = new Random();
    private long a = random.nextInt();
    private long b = random.nextInt();

    private int getIndex(T element) {
        int p = Integer.MAX_VALUE;
        return (int) (Math.abs(element.hashCode() * a + b) % p % table.length);
    }

    public void push(T element) {
        rebuild();
        table[getIndex(element)].push(element);
        size++;
    }

    public void erase(T element) {
        table[getIndex(element)].erase(element);
        size--;
    }

    public boolean contains(T element) {
        return table[getIndex(element)].contains(element);
    }

    private void rebuild() {
        if (size() * 2 > table.length) {
            a = random.nextInt() | 1;
            b = random.nextInt();
            Searcher<T>[] buffer = table;
            table = (Searcher<T>[]) new Searcher[table.length * 2];
            fillTable();
            for (Searcher<T> ts : buffer) {
                for (T element : ts) {
                    table[getIndex(element)].push(element);
                }
            }
            for (int i = 0; i < table.length; i++) {
                if (table[i].size() >= 20) {
                    SplayTree<T> tree = new SplayTree<>(comparator);
                    for (T element : table[i]) {
                        tree.push(element);
                    }
                    table[i] = tree;
                }
            }
        }
    }

    public int size() {
        return size;
    }
/*
    public void print(PrintStream out) {
        for (Searcher<T> ts : table) {
           ((SplayTree) ts).print(out);
        }
        System.out.println("====================");
    }
 */
}
