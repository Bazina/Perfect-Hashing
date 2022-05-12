package On2Space;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public abstract class On2HashTable<Integer, V> {
    protected final int maxSize;
    protected int hashingCounter = 0;
    protected int totalEntries = 0;
    protected ArrayList<Entry> entries;
    protected int size = 0;
    public On2HashTable(int maxSize, boolean matrix) {
        maxSize = (matrix) ? nextPowerTwo(maxSize) : maxSize;
        this.maxSize = (int) Math.pow(maxSize, 2);
        this.entries = new ArrayList<>(this.maxSize);
        this.totalEntries = this.maxSize;
    }

    public int getTotalEntries() {
        return totalEntries;
    }

    abstract int hashFunction(int key);

    abstract void reHash();

    public void putData(List<Pair<Integer, V>> data) {
        for (var item : data) {
            put(item.getKey(), item.getValue());
        }
    }

    public void put(Integer key, V value) {
        size++;
        Entry entry = new Entry(key, value);

        int hash = hashFunction((int) key);

        while (this.entries.get(hash) != null) {
            reHash();
            hash = hashFunction((int) key);
        }
        this.entries.set(hash, entry);
    }

    public V get(Integer key) {
        int hash = hashFunction((int) key);
        Entry entry = this.entries.get(hash);
        return (entry == null) ? null : entry.getValue();
    }

    public void remove(Integer key) {
        if (!contains(key))
            return;

        int hash = hashFunction((int) key);
        this.entries.set(hash, null);
    }

    public boolean contains(Integer key) {
        return get(key) != null;
    }

    public ArrayList<Entry> getEntries() {
        return entries;
    }

    protected boolean rehashingTemplate(List<Entry> data) {
        boolean flag = true;

        for (Entry datum : data) {
            if (datum == null)
                continue;

            int key = (int) datum.getKey();
            int hash = hashFunction(key);

            if (entries.get(hash) != null) {
                flag = false;
                break;
            } else
                entries.set(hash, datum);
        }

        return flag;
    }

    protected void initiate(int size) {
        for (int i = 0; i < size; i++) {
            this.entries.add(null);
        }
    }

    protected int nextPowerTwo(int size) {
        int buffer = 2;
        while (buffer < size) buffer *= 2;
        return buffer;
    }

    public double loadFactor() {
        return (size * 1.0) / (maxSize * 1.0);
    }

    public int getSize() {
        return size;
    }

    public int getHashingCounter() {
        return hashingCounter;
    }

    class Entry {
        private final Integer key;
        private V value;

        public Entry(Integer key, V value) {
            this.key = key;
            this.value = value;
        }

        protected Integer getKey() {
            return key;
        }

        protected V getValue() {
            return value;
        }

        protected void setValue(V value) {
            this.value = value;
        }
    }
}
