package rjc;

public class Subsystem23 {
  private double Value1459 = 0;
  private double Gain1515 = 0;

  public void Main33( double[] e_and_edot_2, double NofJets_3, double[] Firefct1_4 )
  {
    double sig_0 = 0;
    double sig_1 = 0;
    double sig_2 = 0;
    double sig_3 = 0;
    double sig_4 = 0;
    int ix_40 = 0;
    int ix_52 = 0;

    sig_3 = e_and_edot_2[ (int)( 1 ) ];
    sig_4 = (double)( 1 ) / NofJets_3 * sig_3 * sig_3;
    sig_2 = e_and_edot_2[ (int)( 0 ) ];
    sig_1 = Value1459;
    sig_0 = sig_4 * Gain1515;
    Firefct1_4[ 0 ] = -sig_1 + sig_2 + sig_0;
  }
  public void Init36(  )
  {
    Value1459 = 0.00523598775598298810000;
    Gain1515 = 636.509762626099810000;
  }
}
