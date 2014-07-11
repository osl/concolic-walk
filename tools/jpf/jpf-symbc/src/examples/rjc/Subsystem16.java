package rjc;

public class Subsystem16 {
  private double Value1367 = 0;
  private double Gain1422 = 0;

  public void Main23( double[] e_and_edot_2, double NofJets_3, double[] Firefct1_4 )
  {
    double sig_0 = 0;
    double sig_1 = 0;
    double sig_2 = 0;
    double sig_3 = 0;
    double sig_4 = 0;
    int ix_24 = 0;
    int ix_36 = 0;

    sig_3 = e_and_edot_2[ (int)( 1 ) ];
    sig_4 = (double)( 1 ) / NofJets_3 * sig_3 * sig_3;
    sig_2 = e_and_edot_2[ (int)( 0 ) ];
    sig_1 = Value1367;
    sig_0 = sig_4 * Gain1422;
    Firefct1_4[ 0 ] = -sig_1 + sig_2 + sig_0;
  }
  public void Init26(  )
  {
    Value1367 = 0.00523599;
    Gain1422 = 636.51;
  }
}
