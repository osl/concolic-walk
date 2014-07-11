package gov.nasa.jpf.symbc.numeric.solvers;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import za.ac.sun.cs.green.Instance;
import za.ac.sun.cs.green.expr.Expression;
import za.ac.sun.cs.green.expr.IntConstant;
import za.ac.sun.cs.green.expr.IntVariable;
import za.ac.sun.cs.green.expr.Operation;
import gov.nasa.jpf.symbc.SymbolicInstructionFactory;
import gov.nasa.jpf.symbc.numeric.BinaryLinearIntegerExpression;
import gov.nasa.jpf.symbc.numeric.Constraint;
import gov.nasa.jpf.symbc.numeric.ConstraintExpressionVisitor;
import gov.nasa.jpf.symbc.numeric.IntegerConstant;
import gov.nasa.jpf.symbc.numeric.SymbolicInteger;

public class SolverTranslator {

	private static Map<ConstraintSequence, Instance> instanceCache = new HashMap<ConstraintSequence, Instance>();

	public static Instance createInstance(Constraint constraint) {
		Constraint c = constraint; // first constraint
		Constraint r = c.getTail(); // rest of constraint
		Instance p = null; // parent instance
		if (r != null) {
			p = instanceCache.get(new ConstraintSequence(r));
			if (p == null) {
				p = createInstance(r);
//				instanceCache.put(r, p);
			}
		}
		Translator translator = new Translator();
		c.accept(translator);
		Instance i = new Instance(SymbolicInstructionFactory.greenSolver, p, translator.getExpression());
		instanceCache.put(new ConstraintSequence(constraint), i);
		return i;
	}

	private final static class ConstraintSequence {

		private final Constraint sequence;

		public ConstraintSequence(Constraint sequence) {
			this.sequence = sequence;
		}

		@Override
		public boolean equals(Object object) {
			Constraint z = ((ConstraintSequence) object).sequence;
			Constraint s = sequence;
			while ((s != null) && (z != null)) {
				if (!s.equals(z)) {
					return false;
				}
				s = s.getTail();
				z = z.getTail();
			}
			return (s == null) && (z == null);
		}

		@Override
		public int hashCode() {
			int h = 0;
			Constraint s = sequence;
			while (s != null) {
				h ^= s.hashCode();
				s = s.getTail();
			}
			return h;
		}

	}

	private final static class Translator extends ConstraintExpressionVisitor {

		private Stack<Expression> stack;

		public Translator() {
			stack = new Stack<Expression>();
		}

		public Expression getExpression() {
			return stack.peek();
		}

		@Override
		public void postVisit(Constraint constraint) {
			Expression l;
			Expression r;
			switch (constraint.getComparator()) {
			case EQ:
				r = stack.pop();
				l = stack.pop();
				stack.push(new Operation(Operation.Operator.EQ, l, r));
				break;
			case NE:
				r = stack.pop();
				l = stack.pop();
				stack.push(new Operation(Operation.Operator.NE, l, r));
				break;
			case LT:
				r = stack.pop();
				l = stack.pop();
				stack.push(new Operation(Operation.Operator.LT, l, r));
				break;
			case LE:
				r = stack.pop();
				l = stack.pop();
				stack.push(new Operation(Operation.Operator.LE, l, r));
				break;
			case GT:
				r = stack.pop();
				l = stack.pop();
				stack.push(new Operation(Operation.Operator.GT, l, r));
				break;
			case GE:
				r = stack.pop();
				l = stack.pop();
				stack.push(new Operation(Operation.Operator.GE, l, r));
				break;
			}
		}

		@Override
		public void postVisit(BinaryLinearIntegerExpression expression) {
			Expression l;
			Expression r;
			switch (expression.getOp()) {
			case PLUS:
				r = stack.pop();
				l = stack.pop();
				stack.push(new Operation(Operation.Operator.ADD, l, r));
				break;
			case MINUS:
				r = stack.pop();
				l = stack.pop();
				stack.push(new Operation(Operation.Operator.SUB, l, r));
				break;
			case MUL:
				r = stack.pop();
				l = stack.pop();
				stack.push(new Operation(Operation.Operator.MUL, l, r));
				break;
			default:
				System.out.println("SolverTranslator : unsupported operation " + expression.getOp());
				throw new RuntimeException();
			}
		}

		@Override
		public void postVisit(IntegerConstant constant) {
			stack.push(new IntConstant(constant.value));
		}

		@Override
		public void postVisit(SymbolicInteger node) {
			stack.push(new IntVariable(node.getName(), node, node._min, node._max));
		}

	}

}
