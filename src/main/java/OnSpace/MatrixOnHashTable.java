package OnSpace;

import On2Space.MatrixOn2HashTable;
import javafx.util.Pair;

import java.util.*;

import static java.lang.Integer.toBinaryString;

public class MatrixOnHashTable<Integer, V> extends OnHashTable<Integer, V> {
    private ArrayList<MatrixOn2HashTable<Integer, V>> enhancedData;

    private char[] x;
    private char[][] h;

    public MatrixOnHashTable(int maxSize) {
        super(maxSize, true);
        this.entries = new LinkedList[this.maxSize];
        this.enhancedData = new ArrayList<>();

        int b = (int) (Math.log(maxSize) / Math.log(2));
        x = new char[32];
        h = new char[b][32];
        Arrays.fill(x, '0');

        createMatrix();
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

    @Override
    protected int hashFunction(int key) {
        int j = 0;
        char[] hx = new char[h.length];
        String keyString = toBinaryString(key);

        Arrays.fill(x, '0');
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

        return java.lang.Integer.parseInt(String.valueOf(hx), 2);
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
            MatrixOn2HashTable<Integer, V> table = new MatrixOn2HashTable<>(currentSize);
            for (var item : entries[i]) {
                table.put(item.getKey(), item.getValue());
            }
            enhancedData.set(i, table);
        }
    }

    public V get(Integer key) {
        int hash1 = hashFunction((int) key);
        return enhancedData.get(hash1).get(key);
    }
}
