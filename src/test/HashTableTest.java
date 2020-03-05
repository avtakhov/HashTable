package test;

import main.HashTable;

public class HashTableTest {

    public static void main(String[] args) {
        long m = System.currentTimeMillis();

        final int ITER = 2_000_000;


        HashTable<Integer, Integer> ht = new HashTable<>();
        for (int i = 0; i < ITER; i++) {
            ht.push(i, i + 1);
            // ht.print(System.out);
        }

        for (int i = 0; i < ITER; i++) {
            assert ht.containsKey(i);
            assert ht.get(i) == i + 1;
        }

        System.out.println("Time: " + (double) (System.currentTimeMillis() - m));
    }

}
