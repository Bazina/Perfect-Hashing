package OnSpace;

import java.util.LinkedList;

public abstract class OnHashTable<Integer, V> {
    protected final int[] sizes;
    protected int hashingCounter = 0;
    protected int totalEntries = 0;
    protected int maxSize, size = 0;
    protected LinkedList<Entry>[] entries;
    public OnHashTable(int maxSize, boolean matrix) {
        this.maxSize = (matrix) ? nextPowerTwo(maxSize) : maxSize;
        this.sizes = new int[this.maxSize];
    }

    public int getTotalEntries() {
        return totalEntries;
    }

    abstract int hashFunction(int key);

    protected void put(Integer key, V value) {
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

    public void remove(Integer key) {
        Entry entry = getEntry(key);

        if (entry == null)
            throw new IllegalStateException();

        getBucket(key).remove(entry);
        size--;
    }

    protected LinkedList<Entry> getOrCreateBucket(Integer key) {
        int index = hashFunction((int) key);

        if (entries[index] == null)
            entries[index] = new LinkedList<>();

        return entries[index];
    }

    protected LinkedList<Entry> getBucket(Integer key) {
        return entries[hashFunction((int) key)];
    }

    protected Entry getEntry(Integer key) {
        LinkedList<Entry> bucket = getBucket(key);

        if (bucket != null)
            for (Entry entry : bucket)
                if (entry.key == key)
                    return entry;

        return null;
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

    private int nextPowerTwo(int size) {
        int buffer = 2;
        while (buffer < size) buffer *= 2;
        return buffer;
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
