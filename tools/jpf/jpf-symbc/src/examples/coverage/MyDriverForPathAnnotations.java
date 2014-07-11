package coverage;

public class MyDriverForPathAnnotations {
    // The test driver
    public static void main(String[] args) {
        MyClassWithPathAnnotations mca = new MyClassWithPathAnnotations();
        mca.myMethod(1, 2);
    }
}
