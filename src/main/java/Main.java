import On2Space.FormulaOn2HashTable;
import OnSpace.FormulaOnHashTable;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        FormulaOnHashTable<Integer, Integer> x = new FormulaOnHashTable<>(8);
        //for (int i = 1; i < 101; i++) {
        //    System.out.println("x.put(" + i + "," + i + ");");
        //}
        //x.put(1, 1);
        //x.put(2, 2);
        //x.put(3, 3);
        //x.put(4, 4);
        //x.put(5, 5);
        //x.put(6, 6);
        //x.put(7, 7);

        List<Pair<Integer, Integer>> data = new ArrayList<>();
        data.add(new Pair<>(1, 1));
        data.add(new Pair<>(2, 2));
        data.add(new Pair<>(3, 3));
        data.add(new Pair<>(4, 4));
        data.add(new Pair<>(5, 5));
        data.add(new Pair<>(6, 6));
        data.add(new Pair<>(7, 7));
        data.add(new Pair<>(8, 8));

        x.putData(data);

        System.out.println(x.getTotalEntries());
        System.out.println(x.getHashingCounter());
    }
}