package ON2.MatrixMethod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static java.lang.Integer.toBinaryString;

public class MatrixON2<Integer, V> {
    private final int maxSize;
    public int hashingCounter = 0;
    private ArrayList<Entry<Integer, V>> entries;
    private int size = 0;
    private char[] x;
    private char[][] h;

    public MatrixON2(int maxSize) {
        this.maxSize = (int) Math.pow(maxSize, 2);
        int b = (int) (Math.log(this.maxSize) / Math.log(2));
        this.entries = new ArrayList<>(this.maxSize);
        x = new char[32];
        h = new char[b][32];
        Arrays.fill(x, '0');
        createMatrix();
        initiate(this.maxSize);
    }

    private void createMatrix() {
        hashingCounter++;
        for (int i = 0; i < h.length; i++) {
            for (int j = 0; j < 32; j++) {
                String alphaNumericString = "01";
                int index = (int) (alphaNumericString.length() * Math.random());
                h[i][j] = alphaNumericString.charAt(index);
            }
        }
    }

    private int hashFunction(int key) {
        int j = 0;
        char[] hx = new char[h.length];
        String keyString = toBinaryString(key);
        for (int i = 0; i < keyString.length(); i++)
            x[i + 32 - keyString.length()] = keyString.charAt(i);

        for (char[] chars : h) {
            int value = 0;
            for (int m = 0; m < 32; m++)
                value += java.lang.Integer.parseInt(chars[m] + "", 2) *
                         java.lang.Integer.parseInt(x[m] + "", 2);
            String dummy = java.lang.Integer.toBinaryString(value);
            hx[j++] = (dummy.charAt(dummy.length() - 1));
        }

        //System.out.println(java.lang.Integer.parseInt(hx.toString(), 2));
        return java.lang.Integer.parseInt(String.valueOf(hx), 2);
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
            createMatrix();
            initiate(this.maxSize);

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

