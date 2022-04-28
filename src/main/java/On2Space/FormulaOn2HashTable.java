package On2Space;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FormulaOn2HashTable<Integer, V> extends On2HashTable<Integer, V> {
    private final int prime;
    private int hashA, hashB;

    public FormulaOn2HashTable(int maxSize) {
        super(maxSize, false);

        initiate(this.maxSize);

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

    protected void reHash() {
        List<Entry> data = new ArrayList<>(this.entries);
        boolean reHashing = false;
        while (!reHashing) {
            this.entries = new ArrayList<>();
            initiate(this.maxSize);
            this.randFactors();

            if (rehashingTemplate(data))
                reHashing = true;
        }
    }
}
