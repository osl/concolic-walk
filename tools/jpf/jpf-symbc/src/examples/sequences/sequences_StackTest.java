package sequences;

// auto-generated JUnit code:
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class sequences_StackTest {

	private sequences.Stack sequences_stack;

	@Before
	public void setUp() throws Exception {
		sequences_stack = new sequences.Stack();
	}

	@Test
	public void test0() {
		sequences_stack.push(-2147483606);
		sequences_stack.push(0);
	}

	@Test
	public void test1() {
		sequences_stack.push(-2147483606);
		sequences_stack.push(-10000);
	}

	@Test
	public void test2() {
		sequences_stack.push(-2147483606);
		sequences_stack.pop(-2147483606);
	}

	@Test
	public void test3() {
		sequences_stack.push(0);
	}

	@Test
	public void test4() {
		sequences_stack.push(0);
		sequences_stack.push(0);
	}

	@Test
	public void test5() {
		sequences_stack.push(0);
		sequences_stack.push(-10000);
	}

	@Test
	public void test6() {
		sequences_stack.push(0);
		sequences_stack.pop(-2147483606);
	}

	@Test
	public void test7() {
		sequences_stack.push(-10000);
	}

	@Test(expected = java.lang.RuntimeException.class)
	public void test8() {
		sequences_stack.pop(-2147483606);
		//should lead to ##EXCEPTION## "java.lang.RuntimeException: empty stack..."
	}

	@Test
	public void test9() {
		sequences_stack.pop(-2147483606);
	}
}
