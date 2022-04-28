package FormulaMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FormulaON<Integer, V> {
    private final int maxSize;
    private final int prime;
    public int hashingCounter = 0;
    private ArrayList<Entry<Integer, V>> entries;
    private int size = 0, hashA, hashB;

    public FormulaON(int maxSize) {
        this.maxSize = (int) Math.pow(maxSize, 2);
        this.entries = new ArrayList<>(this.maxSize);
        initiate(this.maxSize);
        prime = (int) Math.pow(2, 31) - 1;
        randFactors();
    }

    private void randFactors() {
        hashingCounter++;
        Random rand = new Random();
        hashA = Math.abs(rand.nextInt()) % prime;
        hashB = (Math.abs(rand.nextInt()) + 1) % (prime - 1);
    }

    private int hashFunction(int key) {
        return (int) ((((long) hashA * key) + hashB) % prime) % maxSize;
    }

    public void put(Integer key, V value) {
        size++;
        Entry<Integer, V> entry = new Entry<>(key, value);

        int hash = hashFunction((int) key);

        while (this.entries.get(hash) != null) {
            reHash();
            hash = hashFunction((int) key);
        }
        this.entries.set(hash, entry);
    }

    public V get(Integer key) {
        int hash = hashFunction((int) key);
        Entry<Integer, V> entry = this.entries.get(hash);
        return (entry == null) ? null : entry.value;
    }

    public void remove(Integer key) {
        if (!contains(key))
            return;

        int hash = hashFunction((int) key);
        this.entries.set(hash, null);
    }

    private boolean contains(Integer key) {
        return get(key) != null;
    }

    private void reHash() {
        List<Entry<Integer, V>> data = new ArrayList<>(this.entries);
        boolean reHashing = false;
        while (!reHashing) {
            this.entries = new ArrayList<>();
            initiate(this.maxSize);
            this.randFactors();

            boolean flag = true;

            for (Entry<Integer, V> datum : data) {
                if (datum == null)
                    continue;

                int key = (int) datum.key;
                int hash = hashFunction(key);

                if (entries.get(hash) != null) {
                    flag = false;
                    break;
                } else
                    entries.set(hash, datum);
            }

            if (flag)
                reHashing = true;
        }
    }

    private double loadFactor() {
        return (size * 1.0) / (maxSize * 1.0);
    }

    public ArrayList<Entry<Integer, V>> getEntries() {
        return entries;
    }

    private void initiate(int size) {
        for (int i = 0; i < size; i++) {
            this.entries.add(null);
        }
    }

    static class Entry<K, V> {
        private final K key;
        private V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }
    }
}
