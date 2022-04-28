import FormulaMethod.ON2;

public class Main {
    public static void main(String[] args) {
        ON2<Integer , Integer> x = new ON2<>(10) ;
//        for (int i = 1; i < 51; i++) {
//            System.out.println("x.put(" + i + "," + i + ");");
//        }
        x.put(1,1);
        x.put(2,2);
        x.put(3,3);
        x.put(4,4);
        x.put(5,5);
        x.put(6,6);
        x.put(7,7);
        x.put(8,8);
        x.put(9,9);
        x.put(10,10);

        System.out.println(x.no);
    }
}