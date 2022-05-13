package OnSpace;

import On2Space.FormulaOn2HashTable;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class FormulaOnHashTable<Integer, V> extends OnHashTable<Integer, V> {
    private final int prime;
    private int hashA, hashB;
    private ArrayList<FormulaOn2HashTable<Integer, V>> enhancedData;

    public FormulaOnHashTable(int maxSize) {
        super(maxSize, false);
        this.entries = new LinkedList[maxSize];
        this.maxSize = maxSize;
        this.enhancedData = new ArrayList<>();

        prime = (int) Math.pow(2, 31) - 1;
        randFactors();
    }

    private void randFactors() {
        hashingCounter++;
        Random rand = new Random();
        hashA = Math.abs(rand.nextInt()) % prime;
        hashB = (Math.abs(rand.nextInt()) + 1) % (prime - 1);
    }

    @Override
    protected int hashFunction(int key) {
        return (int) ((((long) hashA * key) + hashB) % prime) % maxSize;
    }

    public void putData(List<Pair<Integer, V>> data) {
        for (var item : data) {
            put(item.getKey(), item.getValue());
        }

        buildData();
    }

    private void buildData() {
        for (int i = 0; i < this.maxSize; i++) {
            this.enhancedData.add(null);
        }

        for (int i = 0; i < entries.length; i++) {
            if (entries[i] == null) continue;
            int currentSize = sizes[i];
            FormulaOn2HashTable<Integer, V> table = new FormulaOn2HashTable<>(currentSize);
            for (var item : entries[i]) {
                table.put(item.getKey(), item.getValue());
            }
            enhancedData.set(i, table);
            this.totalEntries += table.getTotalEntries();
            this.hashingCounter += table.getHashingCounter();
        }
    }

    public V get(Integer key) {
        int hash1 = hashFunction((int) key);
        return enhancedData.get(hash1).get(key);
    }
}
