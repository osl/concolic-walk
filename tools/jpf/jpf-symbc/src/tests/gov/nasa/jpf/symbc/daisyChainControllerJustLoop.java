package gov.nasa.jpf.symbc;

public class daisyChainControllerJustLoop {
	public static final int MAX_POSITION = 15;//10;
	public static final int MIN_POSITION = 0;
	public static final int STRENGTH1 = 1;
	public static final int STRENGTH5 = 10;

	private static int flapPosition = MIN_POSITION;
	private static int windEffect;
	private static int goalPosition = MIN_POSITION;

	public static void main(String[] args) throws InterruptedException {
		daisyChainControllerJustLoop daisyChainController = new daisyChainControllerJustLoop();

		int windStrength = 0;
		int goalPosition = 17;

		daisyChainController.setWindStrength(windStrength);
		daisyChainController.moveFlaps(goalPosition);

	//	System.out.println("Current position: " + flapPosition);
	}

	private void setWindStrength(int windStrength) {
		windEffect = windStrength;
	}

	@Preconditions("position==position")
	public void moveFlaps(int position) throws InterruptedException {
		goalPosition = position;
		actuate();
	}

	private void actuate() throws InterruptedException {

		FlapActuator flapActuatorStep1 = new FlapActuator(STRENGTH1);


		flapActuatorStep1.run();

		//flapActuatorStep5.join();
		//flapActuatorStep1.join();
	}

	public static class FlapActuator{
		int actuatorStrength;

		public FlapActuator(int step) {
			this.actuatorStrength = step;
		}

		public void run() {
			int actuatorEffect = actuatorStrength;
			//while (goalPosition - flapPosition>actuatorStrength || goalPosition-flapPosition<-1*actuatorStrength) {
				
			while (abs(goalPosition - flapPosition)>actuatorStrength){ 
			
				actuatorEffect=sgn(goalPosition-flapPosition)*actuatorStrength;
				/*if (goalPosition > flapPosition) {
					actuatorEffect = actuatorStrength;
				} else {
					actuatorEffect = -1 * actuatorStrength;
				}*/

				flapPosition = flapPosition + actuatorEffect + windEffect;
				if (flapPosition > MAX_POSITION || flapPosition < MIN_POSITION) {
					assert false;
				}
				//System.out.println(toString() + " moved " + actuatorStrength
				//		+ " up to " + flapPosition + ". Goal: " + goalPosition);
			}
		}

		private int abs(int value){
			if(value>=0){
				return value;
			}else{
				return -1*value;
			}
		}
		
		private int sgn(int value){
			return (value>=0)?1:-1;
		}
		
		@Override
		public String toString() {
			return "Actuator[" + actuatorStrength + ']';
		}
	}
}
