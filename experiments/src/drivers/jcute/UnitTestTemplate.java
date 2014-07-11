import junit.framework.TestCase;

/**
 * Generated {@link TestCase} for {@link TARGET_CLASS}.
 * 
 * Each test method first sets up the {@link #input} array and then
 * registers this test class as input provider for {@link cute.Cute}.
 * Thus, the test driver (that is, the method under test) will
 * retrieve the {@code input} array contents when it requests inputs
 * from {@code Cute}.
 * 
 * The template is based on jCUTE's {@code Template.java}.
 */ 

public class TEST_CLASS extends TestCase implements cute.Input {
    private Object[] input;
    private int i;

    public TEST_CLASS (String name) {
        super(name);
    }

    // Generated test methods
    TEST_METHODS

    // Implement {@link cute.Input} interface that the test driver
    // uses to request input values.

    public boolean Boolean() {
        return ((Boolean)input[i++]).booleanValue();
    }

    public short Short() {
        return ((Short)input[i++]).shortValue();
    }

    public int Integer() {
        return ((Integer)input[i++]).intValue();
    }

    public long Long() {
        return ((Long)input[i++]).longValue();
    }

    public float Float() {
        return ((Float)input[i++]).floatValue();
    }

    public double Double() {
        return ((Double)input[i++]).doubleValue();
    }

    public char Character() {
        return ((Character)input[i++]).charValue();
    }

    public byte Byte() {
        return ((Byte)input[i++]).byteValue();
    }

    public Object Object(String type) {
        return input[i++];
    }
    
    public Object ObjectShallow(String type) {
        return input[i++];
    }
}
