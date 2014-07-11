package gov.nasa.jpf.symbc;

public class DaisyChainControllerNew {
	public static final int MAX_POSITION = 15;//10;
	public static final int MIN_POSITION = 0;
	public static final int STRENGTH1 = 1;
	public static final int STRENGTH5 = 10;

	private static int flapPosition = MIN_POSITION;
	private static int windEffect;
	private static int goalPosition = MIN_POSITION;

	public static void main(String[] args) throws InterruptedException {
		DaisyChainControllerNew daisyChainController = new DaisyChainControllerNew();

		int windStrength = 0;
		int goalPosition = 17;

		daisyChainController.setWindStrength(windStrength);
		daisyChainController.moveFlaps(goalPosition);

		//System.out.println("Current position: " + flapPosition);
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
		FlapActuator flapActuatorStep5 = new FlapActuator(STRENGTH5);
		FlapActuator flapActuatorStep1 = new FlapActuator(STRENGTH1);

		flapActuatorStep5.start();
		flapActuatorStep1.start();

		//flapActuatorStep5.join();
		//flapActuatorStep1.join();
	}

	public static class FlapActuator extends Thread {
		int actuatorStrength;

		public FlapActuator(int step) {
			this.actuatorStrength = step;
		}

		@Override
		public void run() {
			int actuatorEffect = actuatorStrength;
			while (goalPosition - flapPosition>actuatorStrength || goalPosition-flapPosition<-1*actuatorStrength) {
				if (goalPosition > flapPosition) {
					actuatorEffect = actuatorStrength;
				} else {
					actuatorEffect = -1 * actuatorStrength;
				}

				flapPosition = flapPosition + actuatorEffect + windEffect;
				if (flapPosition > MAX_POSITION || flapPosition < MIN_POSITION) {
					assert false;
				}
				//System.out.println(toString() + " moved " + actuatorStrength
				//		+ " up to " + flapPosition + ". Goal: " + goalPosition);
			}
		}

		@Override
		public String toString() {
			return "Actuator[" + actuatorStrength + ']';
		}
	}
}
