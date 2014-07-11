/* Adapted by Giovanni Denaro from: */
/* -*- Last-Edit: Fri Jan 29 11:13:27 1993 by Tarak S. Goradia; -*- */
/* $Log: tcas.c,v $
 * Revision 1.2 1993/03/12 19:29:50 foster
 **/

class Const{
	public static final int OLEV = 600; /* in feets/minute */
	public static final int MAXALTDIFF = 600; /* max altitude difference in feet */
	public static final int MINSEP = 300; /* min separation in feet */
	public static final int NOZCROSS = 100; /* in feet */
	public static final int NO_INTENT = 0;
	public static final int DO_NOT_CLIMB = 1;
	public static final int DO_NOT_DESCEND = 2;
	public static final int TCAS_TA = 1;
	public static final int OTHER = 2;
	public static final int UNRESOLVED = 0;
	public static final int UPWARD_RA = 1;
	public static final int DOWNWARD_RA = 2;
}

public class Tcas {
	public static int Cur_Vertical_Sep;
	public static int High_Confidence;
	public static int Two_of_Three_Reports_Valid;
	public static int Own_Tracked_Alt;
	public static int Own_Tracked_Alt_Rate;
	public static int Other_Tracked_Alt;
	public static int Alt_Layer_Value; /* 0, 1, 2, 3 */
	public static int [] Positive_RA_Alt_Thresh;
	public static int Up_Separation;
	public static int Down_Separation;

	/* state variables */
	public static int Other_RAC; /* NO_INTENT, DO_NOT_CLIMB, DO_NOT_DESCEND */
	public static int Other_Capability; /* TCAS_TA, OTHER */
	public static int Climb_Inhibit; /* true/false */
	public static int need_upward_RA;
	public static int need_downward_RA;

	public static void initialize(){
		Positive_RA_Alt_Thresh = new int[4];
		Positive_RA_Alt_Thresh[0] = 400;
		Positive_RA_Alt_Thresh[1] = 500;
		Positive_RA_Alt_Thresh[2] = 640;
		Positive_RA_Alt_Thresh[3] = 740;
	}

	public static int ALIM (){return Positive_RA_Alt_Thresh[Alt_Layer_Value];} 

	public static int Inhibit_Biased_Climb (){
		return ((Climb_Inhibit==1)?  Up_Separation + Const.MINSEP /* operand mutation NOZCROSS */ : Up_Separation);
	}

	public static int Non_Crossing_Biased_Climb(){
		int upward_preferred;
		int upward_crossing_situation;
		int result;

		upward_preferred = (Inhibit_Biased_Climb() > Down_Separation)?1:0;
		if (upward_preferred!=0){
			result = (!(Own_Below_Threat()==1) ||
					((Own_Below_Threat()==1) && (!(Down_Separation >= ALIM()))))?1:0;
		}else{
			result = (Own_Above_Threat()==1 &&
					(Cur_Vertical_Sep >= Const.MINSEP) && (Up_Separation >= ALIM()))?1:0;
		} 
		return result;
	}

	public static int Non_Crossing_Biased_Descend(){
		int upward_preferred;
		int upward_crossing_situation;
		int result;

		upward_preferred = (Inhibit_Biased_Climb() > Down_Separation)?1:0;

		if (upward_preferred!=0){
			result = (Own_Below_Threat()==1 &&
					(Cur_Vertical_Sep >= Const.MINSEP) && (Down_Separation >= ALIM()))?1:0;
		}else{
			result = (!(Own_Above_Threat()==1) ||
					((Own_Above_Threat()==1) 
					 && (Up_Separation >= ALIM())))?1:0;
		}
		return result;
	}

	public static int Own_Below_Threat(){
		return ((Own_Tracked_Alt < 
					Other_Tracked_Alt)?1:0);
	}

	public static int Own_Above_Threat(){ 
		return ((Other_Tracked_Alt < 
					Own_Tracked_Alt)?1:0);
	}
	/** 
	 * @assert
	 * LOCATION[RAsComputed] noRAconflict:
	 * !(need_upward_RA == 1 && need_downward_RA==1);
	 */
	public static int alt_sep_test(){
		int enabled, tcas_equipped, intent_not_known;
		int alt_sep;

		enabled = (High_Confidence==1 && (Own_Tracked_Alt_Rate <= Const.OLEV) &&
				(Cur_Vertical_Sep > Const.MAXALTDIFF))?1:0;
		tcas_equipped = (Other_Capability == Const.TCAS_TA)?1:0;
		intent_not_known = (Two_of_Three_Reports_Valid==1 &&
				Other_RAC == Const.NO_INTENT)?1:0;

		alt_sep = Const.UNRESOLVED;
		if (enabled==1 && ((tcas_equipped==1 && intent_not_known==1)
					|| tcas_equipped==0)){
			need_upward_RA = (Non_Crossing_Biased_Climb()==1
					&& Own_Below_Threat()==1)?1:0;
			need_downward_RA = (Non_Crossing_Biased_Descend()==1
					&& Own_Above_Threat()==1)?1:0;
			if (need_upward_RA==1 && need_downward_RA==1)
			   alt_sep = Const.UNRESOLVED;
			else if (need_upward_RA==1) alt_sep = Const.UPWARD_RA;
			else if (need_downward_RA==1) alt_sep = Const.DOWNWARD_RA;
			else alt_sep = Const.UNRESOLVED;
		}
		return alt_sep;
	}

	/* added by Pavan */
	public static void start (String[] argv) {
		initialize();
		Cur_Vertical_Sep = Integer.parseInt(argv[0]);
		High_Confidence = Integer.parseInt(argv[1]);
		Two_of_Three_Reports_Valid = Integer.parseInt(argv[2]);
		Own_Tracked_Alt = Integer.parseInt(argv[3]);
		Own_Tracked_Alt_Rate = Integer.parseInt(argv[4]);
		Other_Tracked_Alt = Integer.parseInt(argv[5]);
		Alt_Layer_Value = Integer.parseInt(argv[6]);
		Up_Separation = Integer.parseInt(argv[7]);
		Down_Separation = Integer.parseInt(argv[8]);
		Other_RAC = Integer.parseInt(argv[9]);
		Other_Capability = Integer.parseInt(argv[10]);
		Climb_Inhibit = Integer.parseInt(argv[11]);
		System.out.println(alt_sep_test());
	}

	public static void main ( String[] argv ) {
		if ( argv.length < 12 ) {
			System.out.println ("Error: Command line arguments are");
			System.out.println ("Cur_Vertical_Sep, High_Confidence, Two_of_Three_Reports_Valid");
			System.out.println ("Own_Tracked_Alt, Own_Tracked_Alt_Rate, Other_Tracked_Alt");
			System.out.println ("Alt_Layer_Value, Up_Separation, Down_Separation");
			System.out.println ("Other_RAC, Other_Capability, Climb_Inhibit");
			System.exit(1);
		}
		start(argv);
		System.exit(0);
	}

	// Added by pdinges
	public static void start_symbolic (int cur_vertical_sep,
					   int high_confidence,
					   int two_of_three_reports_valid,
					   int own_tracked_alt,
					   int own_tracked_alt_rate,
					   int other_tracked_alt,
					   int alt_layer_value,
					   int up_separation,
					   int down_separation,
					   int other_rac,
					   int other_capability,
					   int climb_inhibit) {
		initialize();
		Cur_Vertical_Sep = cur_vertical_sep;
		High_Confidence = high_confidence;
		Two_of_Three_Reports_Valid = two_of_three_reports_valid;
		Own_Tracked_Alt = own_tracked_alt;
		Own_Tracked_Alt_Rate = own_tracked_alt_rate;
		Other_Tracked_Alt = other_tracked_alt;
		Alt_Layer_Value = alt_layer_value;
		Up_Separation = up_separation;
		Down_Separation = down_separation;
		Other_RAC = other_rac;
		Other_Capability = other_capability;
		Climb_Inhibit = climb_inhibit;
		System.out.println(alt_sep_test());
	}


}//end of the class Tcas
