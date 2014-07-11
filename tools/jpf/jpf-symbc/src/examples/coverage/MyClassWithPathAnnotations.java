package coverage;

public class MyClassWithPathAnnotations {
    public int myMethod(int x, int y) {
        StringBuilder path = new StringBuilder();
        int z = x + y;
        if (z > 0) {
            path.append("z>0 ");
            z = 1;
        } else {
            path.append("z<=0 ");
            z = z - x;
        }

        if (x < 0) {
            path.append("x<0 ");
            z = z * 2;
        } else if (x < 10) {
            path.append("0<=x<10 ");
            z = z + 2;
        } else {
            path.append("x>=10 ");
            z = -z;
        }

        if (y < 5) {
            path.append("y<5 ");
            z = z - 12;
        } else {
            path.append("y>=5 ");
            z = z - 30;
        }
        CheckCoverage.processTestCase(path.toString());
        return z;
    }
}

