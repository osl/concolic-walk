import gov.nasa.jpf.symbc.Debug;


public class MyDriverForFields {
    // The test driver
    public static void main(String[] args) {
        MyClassWithFields mc = new MyClassWithFields();
    
        mc.myMethod1();
        Debug.printPC("\nMyClassWithFields.myMethod1 Path Condition: ");
    }
}
