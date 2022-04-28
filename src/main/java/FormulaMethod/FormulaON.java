package FormulaMethod;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class FormulaON<Integer, V> {
    class Entry {
        private final int key;
        private V value;

        public Entry(int key, V value) {
            this.key = key;
            this.value = value;
        }

        public int getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }
    }

    private LinkedList<Entry>[] entries;
    private int maxSize, size = 0;
    private boolean reHashing = false;

    public FormulaON(int maxSize) {
        this.entries = new LinkedList[maxSize];
        this.maxSize = maxSize;
    }

    public void put(int key, V value) {
        Entry entry = getEntry(key);

        if (entry != null) {
            entry.value = value;
            return;
        }

        size++;
        LinkedList<Entry> bucket = getOrCreateBucket(key);
        if (bucket != null)
            bucket.addLast(new Entry(key, value));

        if (!this.reHashing && loadFactor() > 0.75)
            reHash();
    }

    public V get(int key) {
        Entry entry = getEntry(key);

        return (entry == null) ? null : entry.value;
    }

    public void remove(int key) {
        Entry entry = getEntry(key);

        if (entry == null)
            throw new IllegalStateException();

        getBucket(key).remove(entry);
        size--;

        if (!this.reHashing && loadFactor() > 0.75)
            reHash();
    }

    private LinkedList<Entry> getOrCreateBucket(int key) {
        int index = hash(key);

        if (entries[index] == null)
            entries[index] = new LinkedList<>();

        return entries[index];
    }

    private Entry getEntry(int key) {
        LinkedList<Entry> bucket = getBucket(key);

        if (bucket != null)
            for (Entry entry : bucket)
                if (entry.key == key)
                    return entry;

        return null;
    }

    private LinkedList<Entry> getBucket(int key) {
        return entries[hash(key)];
    }

    private int hash(int key) {
        return key % entries.length;
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
}
