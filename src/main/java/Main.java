import MatrixMethod.MatrixON2;

public class Main {
    public static void main(String[] args) {
        MatrixON2<Integer, Integer> x = new MatrixON2<>(8);
//        for (int i = 1; i < 101; i++) {
//            System.out.println("x.put(" + i + "," + i + ");");
//        }
        x.put(1, 1);
        x.put(2, 2);
        x.put(3, 3);
        x.put(4, 4);
        x.put(5, 5);
        x.put(6, 6);
        x.put(7, 7);
        x.put(8, 8);


        System.out.println(x.hashingCounter);
    }
}