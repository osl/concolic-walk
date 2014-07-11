package rjc;

public class Subsystem214 {
  private double Value1375 = 0;
  private double Gain1432 = 0;

  public void Main21( double[] e_and_edot_2, double NofJets_3, double[] Firefct2_4 )
  {
    double sig_0 = 0;
    double sig_1 = 0;
    double sig_2 = 0;
    double sig_3 = 0;
    double sig_4 = 0;
    int ix_26 = 0;
    int ix_38 = 0;

    sig_3 = e_and_edot_2[ (int)( 1 ) ];
    sig_4 = (double)( 1 ) / NofJets_3 * sig_3 * sig_3;
    sig_2 = e_and_edot_2[ (int)( 0 ) ];
    sig_1 = Value1375;
    sig_0 = sig_4 * Gain1432;
    Firefct2_4[ 0 ] = sig_1 + sig_2 - sig_0;
  }
  public void Init28(  )
  {
    Value1375 = 0.00523599;
    Gain1432 = 636.51;
  }
}
