import On2Space.FormulaOn2HashTable;
import On2Space.MatrixOn2HashTable;
import OnSpace.FormulaOnHashTable;
import OnSpace.MatrixOnHashTable;
import javafx.util.Pair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class MainTest {
    List<Pair<Integer, Integer>> data = new ArrayList<>();

    @BeforeEach
    void setUp() {
        for (int i = 0; i < 100; i++)
            data.add(new Pair<>(i, i));
    }

    @Test
    void matrixTest1() {
        MatrixOn2HashTable<Integer, Integer> x = new MatrixOn2HashTable<>(100);

        x.putData(data);

        Assertions.assertEquals(16384, x.getTotalEntries());
        Assertions.assertTrue(x.getHashingCounter() <= 2);
    }

    @Test
    void matrixTest2() {
        MatrixOnHashTable<Integer, Integer> x = new MatrixOnHashTable<>(100);

        x.putData(data);

        System.out.println("Tables Size = " + x.getTotalEntries());
        System.out.println("Rehashing Counter = " + x.getHashingCounter());
        Assertions.assertTrue(x.getTotalEntries() <= 4 * 128);
        Assertions.assertTrue(x.getHashingCounter() <= 100);
    }

    @Test
    void formulaTest1() {
        FormulaOn2HashTable<Integer, Integer> x = new FormulaOn2HashTable<>(100);

        x.putData(data);

        Assertions.assertEquals(10000, x.getTotalEntries());
        Assertions.assertTrue(x.getHashingCounter() <= 2);
    }

    @Test
    void formulaTest2() {
        FormulaOnHashTable<Integer, Integer> x = new FormulaOnHashTable<>(100);

        x.putData(data);

        System.out.println("Tables Size = " + x.getTotalEntries());
        System.out.println("Rehashing Counter = " + x.getHashingCounter());
        Assertions.assertTrue(x.getTotalEntries() <= 3 * 100);
        Assertions.assertTrue(x.getHashingCounter() <= 2 * 100);
    }

}