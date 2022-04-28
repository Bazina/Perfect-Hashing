package On2Space;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static java.lang.Integer.toBinaryString;

public class MatrixOn2HashTable<Integer, V> extends On2HashTable<Integer, V> {
    private char[] x;
    private char[][] h;

    public MatrixOn2HashTable(int maxSize) {
        super(maxSize, true);

        int b = (int) (Math.log(this.maxSize) / Math.log(2));
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

    @Override
    protected void reHash() {
        List<Entry> data = new ArrayList<>(this.entries);
        boolean reHashing = false;
        while (!reHashing) {
            this.entries = new ArrayList<>();
            createMatrix();
            initiate(this.maxSize);

            if (rehashingTemplate(data))
                reHashing = true;
        }
    }
}

