import java.util.LinkedList;

public class ChainingHashTable<K, V> {
    class Entry {
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

    private LinkedList<Entry>[] entries;
    private int maxSize, size = 0;
    private boolean reHashing = false;

    public ChainingHashTable(int maxSize) {
        this.entries = new LinkedList[maxSize];
        this.maxSize = maxSize;
    }

    public void put(K key, V value) {
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

    public V get(K key) {
        Entry entry = getEntry(key);

        return (entry == null) ? null : entry.value;
    }

    public void remove(K key) {
        Entry entry = getEntry(key);

        if (entry == null)
            throw new IllegalStateException();

        getBucket(key).remove(entry);
        size--;

        if (!this.reHashing && loadFactor() > 0.75)
            reHash();
    }

    private LinkedList<Entry> getOrCreateBucket(K key) {
        int index = hash(key);

        if (entries[index] == null)
            entries[index] = new LinkedList<>();

        return entries[index];
    }

    private Entry getEntry(K key) {
        LinkedList<Entry> bucket = getBucket(key);

        if (bucket != null)
            for (Entry entry : bucket)
                if (entry.key == key)
                    return entry;

        return null;
    }

    private LinkedList<Entry> getBucket(K key) {
        return entries[hash(key)];
    }

    private int hash(K key) {
        return key.hashCode() % entries.length;
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
