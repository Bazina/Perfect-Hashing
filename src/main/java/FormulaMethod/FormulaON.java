package FormulaMethod;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class FormulaON<Integer, V> {
    private final int prime;
    public int hashingCounter = 0;
    private int maxSize, size = 0;
    private boolean reHashing = false;
    private int hashA, hashB;

    private LinkedList<Entry>[] entries;
    private ArrayList<FormulaON2<Integer, V>> enhancedData;
    private final int[] sizes;

    public FormulaON(int maxSize) {
        this.entries = new LinkedList[maxSize];
        this.enhancedData = new ArrayList<>();
        this.maxSize = maxSize;
        this.sizes = new int[maxSize];

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

    public void putData(List<Pair<Integer , V>> data) {
        for (var item : data) {
            put(item.getKey(), item.getValue());
        }

        buildData();
    }

    private void put(Integer key, V value) {
        Entry entry = getEntry(key);

        if (entry != null) {
            entry.value = value;
            return;
        }

        size++;
        sizes[hashFunction((int) key)]++;
        LinkedList<Entry> bucket = getOrCreateBucket(key);
        if (bucket != null)
            bucket.addLast(new Entry(key, value));
    }

    private void buildData() {
        for (int i = 0; i < this.maxSize; i++) {
            this.enhancedData.add(null);
        }

        for (int i = 0; i < entries.length; i++) {
            if(entries[i] == null) continue;
            int currentSize = sizes[i];
            FormulaON2<Integer , V> table = new FormulaON2<>(currentSize);
            for (var item : entries[i]) {
                table.put(item.key , item.value);
            }
            enhancedData.set(i , table) ;
        }
    }

    public V get(Integer key) {
        int hash1 = hashFunction((int)key) ;
        return enhancedData.get(hash1).get(key) ;
    }

//    public void remove(Integer key) {
//        Entry entry = getEntry(key);
//
//        if (entry == null)
//            throw new IllegalStateException();
//
//        getBucket(key).remove(entry);
//        size--;
//
//        if (!this.reHashing && loadFactor() > 0.75)
//            reHash();
//    }

    private LinkedList<Entry> getOrCreateBucket(Integer key) {
        int index = hashFunction((int) key);

        if (entries[index] == null)
            entries[index] = new LinkedList<>();

        return entries[index];
    }

    private Entry getEntry(Integer key) {
        LinkedList<Entry> bucket = getBucket(key);

        if (bucket != null)
            for (Entry entry : bucket)
                if (entry.key == key)
                    return entry;

        return null;
    }

    private LinkedList<Entry> getBucket(Integer key) {
        return entries[hashFunction((int) key)];
    }

    private void reHash() {
        LinkedList<Entry>[] entriesCopy = this.entries.clone();

        this.reHashing = true;
        this.entries = new LinkedList[maxSize * 2];

        for (int i = 0; i < maxSize; i++)
            if (entriesCopy[i] != null)
                for (Entry entry : entriesCopy[i])
                    put(entry.key, entry.value);

        this.maxSize = maxSize * 2;
    }

    public double loadFactor() {
        return (size * 1.0) / (maxSize * 1.0);
    }

    public LinkedList<Entry>[] getEntries() {
        return entries;
    }

    class Entry {
        private final Integer key;
        private V value;

        public Entry(Integer key, V value) {
            this.key = key;
            this.value = value;
        }

        public Integer getKey() {
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
