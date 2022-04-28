package ON2.MatrixMethod;

import java.util.Arrays;

public class MatrixHashFunction {
    public static void main(String[] args) {
        hashFn(1);
        hashFn(2);
        hashFn(3);
        hashFn(4);
        hashFn(5);
        hashFn(6);
        hashFn(7);
        hashFn(8);
    }
    public static int hashFn(int key) {
        String x = Integer.toBinaryString(key);
        char[] z = new char[32];
        Arrays.fill(z, '0');
        String AlphaNumericString = "01";
        char[][] h = new char[3][32];
        StringBuilder hx = new StringBuilder();

        for (int i = 0; i < x.length(); i++)
            z[i + 32 - x.length()] = x.charAt(i);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 32; j++) {
                int index = (int) (AlphaNumericString.length() * Math.random());
                h[i][j] = AlphaNumericString.charAt(index);
            }
        }

        for (char[] chars : h) {
            int value = 0;
            for (int m = 0; m < 32; m++)
                value += Integer.parseInt(chars[m] + "", 2) * Integer.parseInt(z[m] + "", 2);
            String dummy = Integer.toBinaryString(value);
            hx.append(dummy.charAt(dummy.length() - 1));
        }
        System.out.println(Integer.parseInt(hx.toString(), 2));
        return Integer.parseInt(hx.toString(), 2);
    }
}
