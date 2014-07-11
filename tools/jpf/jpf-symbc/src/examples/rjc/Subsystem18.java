package rjc;

public class Subsystem18 {
  private double Value1352 = 0;
  private double Gain1407 = 0;

  public void Main12( double[] e_and_edot_2, double NofJets_3, double[] Coastfct1_4 )
  {
    double sig_0 = 0;
    double sig_1 = 0;
    double sig_2 = 0;
    double sig_3 = 0;
    double sig_4 = 0;
    int ix_21 = 0;
    int ix_33 = 0;

    sig_3 = e_and_edot_2[ (int)( 1 ) ];
    sig_4 = (double)( 1 ) / NofJets_3 * sig_3 * sig_3;
    sig_2 = e_and_edot_2[ (int)( 0 ) ];
    sig_1 = Value1352;
    sig_0 = sig_4 * Gain1407;
    Coastfct1_4[ 0 ] = -sig_1 + sig_2 - sig_0;
  }
  public void Init17(  )
  {
    Value1352 = 0.00523599;
    Gain1407 = 6365.1;
  }
}
