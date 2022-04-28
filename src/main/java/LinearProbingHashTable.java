import java.util.ArrayList;

public class LinearProbingHashTable<K, V> {
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

    private ArrayList<Entry<K, V>> entries;
    private int maxSize, size = 0;
    private boolean reHashing = false;

    public LinearProbingHashTable(int maxSize) {
        this.entries = new ArrayList<>(maxSize);
        this.maxSize = maxSize;
        initiate(this.maxSize);
    }

    public void put(K key, V value) {
        Entry<K, V> entry = new Entry<>(key, value);

        if (key.hashCode() < maxSize && this.entries.get(key.hashCode()) != null) {
            this.entries.set(hash1(key), entry);
            return;
        }

        this.entries.set(linearHash(key), entry);
        size++;

        if (!this.reHashing && loadFactor() > 0.5)
            reHash();
    }

    public V get(K key) {
        int index = hash1(key);
        while (entries.get(index) != null) {
            if (entries.get(index).key.equals(key))
                return entries.get(index).value;
            index = (index + 1) % maxSize;
        }
        return null;
    }

    public void remove(K key) {
        if (!contains(key))
            return;

        int index = hash1(key);

        while (entries.get(index).key != key)
            index = (index + 1) % maxSize;

        entries.set(index, null);

        size--;

        if (!this.reHashing && loadFactor() > 0.5)
            reHash();
        else
            for (index = (index + 1) % maxSize; entries.get(index) != null; index = (index + 1) % maxSize) {
                K ketTemp = entries.get(index).key;
                V valueTemp = entries.get(index).value;
                entries.set(index, null);
                size--;
                put(ketTemp, valueTemp);
            }
    }

    private boolean contains(K key) {
        return entries.get(hash1(key)) != null;
    }

    private int linearHash(K key) {
        int index = hash1(key);
        for (int i = 0; entries.get(index) != null; i++) {
            index = (hash1(key) + i) % maxSize;
        }
        return index;
    }

    private int hash1(K key) {
        return key.hashCode() % entries.size();
    }

    private void reHash() {
        ArrayList<Entry<K, V>> entriesCopy = this.entries;
        int tempMaxSize = maxSize * 2;

        this.reHashing = true;
        this.entries = new ArrayList<>(tempMaxSize);
        initiate(tempMaxSize);

        for (int i = 0; i < maxSize; i++)
            if (entriesCopy.get(i) != null)
                put(entriesCopy.get(i).key, entriesCopy.get(i).value);

        this.reHashing = false;
        this.maxSize = maxSize * 2;
    }

    private double loadFactor() {
        return (size * 1.0) / (maxSize * 1.0);
    }

    public ArrayList<Entry<K, V>> getEntries() {
        return entries;
    }

    private void initiate(int size) {
        for (int i = 0; i < size; i++) {
            this.entries.add(null);
        }
    }
}
