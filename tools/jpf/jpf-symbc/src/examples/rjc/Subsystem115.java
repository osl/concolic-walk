package rjc;

public class Subsystem115 {
  private double Value1371 = 0;
  private double Gain1427 = 0;

  public void Main22( double[] e_and_edot_2, double NofJets_3, double[] Coastfct1_4 )
  {
    double sig_0 = 0;
    double sig_1 = 0;
    double sig_2 = 0;
    double sig_3 = 0;
    double sig_4 = 0;
    int ix_25 = 0;
    int ix_37 = 0;

    sig_3 = e_and_edot_2[ (int)( 1 ) ];
    sig_4 = (double)( 1 ) / NofJets_3 * sig_3 * sig_3;
    sig_2 = e_and_edot_2[ (int)( 0 ) ];
    sig_1 = Value1371;
    sig_0 = sig_4 * Gain1427;
    Coastfct1_4[ 0 ] = -sig_1 + sig_2 - sig_0;
  }
  public void Init27(  )
  {
    Value1371 = 0.00523599;
    Gain1427 = 6365.1;
  }
}
