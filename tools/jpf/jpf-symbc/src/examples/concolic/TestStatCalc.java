package concolic;

public class TestStatCalc {
	
	public void run(int val) {
		
		System.out.println("adding value");
		StatCalculator.addValue(val);
		StatCalculator.addValue(val);
		StatCalculator.addValue(val);
		StatCalculator.addValue(val);
		//stat.addValue(val);
		if(StatCalculator.getMedian().intValue() == 3) {
			System.out.println("median value is 3");
		} else {
			System.out.println("median value is not 3");
		}
		if(StatCalculator.getStandardDeviation() <= 0.82915619758885) {
			System.out.println("std deviation is .10");
		} else {
			System.out.println("std deviation not found");
		}
	}
	
	public static void main(String[] args) {
		TestStatCalc stat = new TestStatCalc();
		stat.run(4);
		
	}
	
}