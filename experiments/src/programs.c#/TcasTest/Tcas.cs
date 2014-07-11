/*
 * Automatically converted from Java to C# using Sharpen.
 */

using Microsoft.VisualStudio.TestTools.UnitTesting;
using Microsoft.Pex.Framework;

internal class Const
{
	public const int OLEV = 600;

	public const int MAXALTDIFF = 600;

	public const int MINSEP = 300;

	public const int NOZCROSS = 100;

	public const int NO_INTENT = 0;

	public const int DO_NOT_CLIMB = 1;

	public const int DO_NOT_DESCEND = 2;

	public const int TCAS_TA = 1;

	public const int OTHER = 2;

	public const int UNRESOLVED = 0;

	public const int UPWARD_RA = 1;

	public const int DOWNWARD_RA = 2;
}

[TestClass]
public partial class Tcas
{
	public static int Cur_Vertical_Sep;

	public static int High_Confidence;

	public static int Two_of_Three_Reports_Valid;

	public static int Own_Tracked_Alt;

	public static int Own_Tracked_Alt_Rate;

	public static int Other_Tracked_Alt;

	public static int Alt_Layer_Value;

	public static int[] Positive_RA_Alt_Thresh;

	public static int Up_Separation;

	public static int Down_Separation;

	public static int Other_RAC;

	public static int Other_Capability;

	public static int Climb_Inhibit;

	public static int need_upward_RA;

	public static int need_downward_RA;

	public static void Initialize()
	{
		Positive_RA_Alt_Thresh = new int[4];
		Positive_RA_Alt_Thresh[0] = 400;
		Positive_RA_Alt_Thresh[1] = 500;
		Positive_RA_Alt_Thresh[2] = 640;
		Positive_RA_Alt_Thresh[3] = 740;
	}

	public static int ALIM()
	{
		return Positive_RA_Alt_Thresh[Alt_Layer_Value];
	}

	public static int Inhibit_Biased_Climb()
	{
		return ((Climb_Inhibit == 1) ? Up_Separation + Const.MINSEP : Up_Separation);
	}

	public static int Non_Crossing_Biased_Climb()
	{
		int upward_preferred;
		int upward_crossing_situation;
		int result;
		upward_preferred = (Inhibit_Biased_Climb() > Down_Separation) ? 1 : 0;
		if (upward_preferred != 0)
		{
			result = (!(Own_Below_Threat() == 1) || ((Own_Below_Threat() == 1) && (!(Down_Separation
				 >= ALIM())))) ? 1 : 0;
		}
		else
		{
			result = (Own_Above_Threat() == 1 && (Cur_Vertical_Sep >= Const.MINSEP) && (Up_Separation
				 >= ALIM())) ? 1 : 0;
		}
		return result;
	}

	public static int Non_Crossing_Biased_Descend()
	{
		int upward_preferred;
		int upward_crossing_situation;
		int result;
		upward_preferred = (Inhibit_Biased_Climb() > Down_Separation) ? 1 : 0;
		if (upward_preferred != 0)
		{
			result = (Own_Below_Threat() == 1 && (Cur_Vertical_Sep >= Const.MINSEP) && (Down_Separation
				 >= ALIM())) ? 1 : 0;
		}
		else
		{
			result = (!(Own_Above_Threat() == 1) || ((Own_Above_Threat() == 1) && (Up_Separation
				 >= ALIM()))) ? 1 : 0;
		}
		return result;
	}

	public static int Own_Below_Threat()
	{
		return ((Own_Tracked_Alt < Other_Tracked_Alt) ? 1 : 0);
	}

	public static int Own_Above_Threat()
	{
		return ((Other_Tracked_Alt < Own_Tracked_Alt) ? 1 : 0);
	}

	/// <assert>
	/// LOCATION[RAsComputed] noRAconflict:
	/// !(need_upward_RA == 1 && need_downward_RA==1);
	/// </assert>
	public static int Alt_sep_test()
	{
		int enabled;
		int tcas_equipped;
		int intent_not_known;
		int alt_sep;
		enabled = (High_Confidence == 1 && (Own_Tracked_Alt_Rate <= Const.OLEV) && (Cur_Vertical_Sep
			 > Const.MAXALTDIFF)) ? 1 : 0;
		tcas_equipped = (Other_Capability == Const.TCAS_TA) ? 1 : 0;
		intent_not_known = (Two_of_Three_Reports_Valid == 1 && Other_RAC == Const.NO_INTENT
			) ? 1 : 0;
		alt_sep = Const.UNRESOLVED;
		if (enabled == 1 && ((tcas_equipped == 1 && intent_not_known == 1) || tcas_equipped
			 == 0))
		{
			need_upward_RA = (Non_Crossing_Biased_Climb() == 1 && Own_Below_Threat() == 1) ? 
				1 : 0;
			need_downward_RA = (Non_Crossing_Biased_Descend() == 1 && Own_Above_Threat() == 1
				) ? 1 : 0;
			if (need_upward_RA == 1 && need_downward_RA == 1)
			{
				alt_sep = Const.UNRESOLVED;
			}
			else
			{
				if (need_upward_RA == 1)
				{
					alt_sep = Const.UPWARD_RA;
				}
				else
				{
					if (need_downward_RA == 1)
					{
						alt_sep = Const.DOWNWARD_RA;
					}
					else
					{
						alt_sep = Const.UNRESOLVED;
					}
				}
			}
		}
		return alt_sep;
	}

	public static void Start(string[] argv)
	{
		Initialize();
		Cur_Vertical_Sep = System.Convert.ToInt32(argv[0]);
		High_Confidence = System.Convert.ToInt32(argv[1]);
		Two_of_Three_Reports_Valid = System.Convert.ToInt32(argv[2]);
		Own_Tracked_Alt = System.Convert.ToInt32(argv[3]);
		Own_Tracked_Alt_Rate = System.Convert.ToInt32(argv[4]);
		Other_Tracked_Alt = System.Convert.ToInt32(argv[5]);
		Alt_Layer_Value = System.Convert.ToInt32(argv[6]);
		Up_Separation = System.Convert.ToInt32(argv[7]);
		Down_Separation = System.Convert.ToInt32(argv[8]);
		Other_RAC = System.Convert.ToInt32(argv[9]);
		Other_Capability = System.Convert.ToInt32(argv[10]);
		Climb_Inhibit = System.Convert.ToInt32(argv[11]);
		System.Console.Out.WriteLine(Alt_sep_test());
	}

	public static void Main(string[] argv)
	{
		if (argv.Length < 12)
		{
			System.Console.Out.WriteLine("Error: Command line arguments are");
			System.Console.Out.WriteLine("Cur_Vertical_Sep, High_Confidence, Two_of_Three_Reports_Valid"
				);
			System.Console.Out.WriteLine("Own_Tracked_Alt, Own_Tracked_Alt_Rate, Other_Tracked_Alt"
				);
			System.Console.Out.WriteLine("Alt_Layer_Value, Up_Separation, Down_Separation");
			System.Console.Out.WriteLine("Other_RAC, Other_Capability, Climb_Inhibit");
			System.Environment.Exit(1);
		}
		Start(argv);
		System.Environment.Exit(0);
	}

	// Added by pdinges
    [PexMethod(MaxConstraintSolverTime = 5, MaxRunsWithoutNewTests = 500)]
    public static void Start_symbolic(int cur_vertical_sep, int high_confidence, int 
		two_of_three_reports_valid, int own_tracked_alt, int own_tracked_alt_rate, int other_tracked_alt
		, int alt_layer_value, int up_separation, int down_separation, int other_rac, int
		 other_capability, int climb_inhibit)
	{
		Initialize();
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
		System.Console.Out.WriteLine(Alt_sep_test());
	}
	//end of the class Tcas
}
