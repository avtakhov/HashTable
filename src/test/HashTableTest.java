package test;

import main.*;

public class HashTableTest {

    public static void main(String[] args) {
        long m = System.currentTimeMillis();
        HashTable<Integer> ht = new HashTable<>();
        final int ITER = 3000000;
        for (int i = 0; i < ITER; i++) {
            ht.push(i);
            // ht.print(System.out);
        }

        for (int i = 0; i < ITER; i++) {
            assert ht.contains(i);
        }

        System.out.println("Time: " + (double) (System.currentTimeMillis() - m));
    }

}
