package rjc;

public class Subsystem36 {
  private double Value1360 = 0;
  private double Gain1417 = 0;

  public void Main10( double[] e_and_edot_2, double NofJets_3, double[] Coastfct2_4 )
  {
    double sig_0 = 0;
    double sig_1 = 0;
    double sig_2 = 0;
    double sig_3 = 0;
    double sig_4 = 0;
    int ix_23 = 0;
    int ix_35 = 0;

    sig_3 = e_and_edot_2[ (int)( 1 ) ];
    sig_4 = (double)( 1 ) / NofJets_3 * sig_3 * sig_3;
    sig_2 = e_and_edot_2[ (int)( 0 ) ];
    sig_1 = Value1360;
    sig_0 = sig_4 * Gain1417;
    Coastfct2_4[ 0 ] = sig_1 + sig_2 + sig_0;
  }
  public void Init19(  )
  {
    Value1360 = 0.00523599;
    Gain1417 = 6365.1;
  }
}
