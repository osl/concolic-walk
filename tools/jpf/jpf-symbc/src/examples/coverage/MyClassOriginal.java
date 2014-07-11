package coverage;

public class MyClassOriginal {
    public int myMethod(int x, int y) {
        int z = x + y;
        if (z > 0) {
            z = 1;
        } else {
            z = z - x;
        }

        if (x < 0) {
            z = z * 2;
        } else if (x < 10) {
            z = z + 2;
        } else {
            z = -z;
        }

        if (y < 5) {
            z = z - 12;
        } else {
            z = z - 30;
        }
        return z;
    }
}