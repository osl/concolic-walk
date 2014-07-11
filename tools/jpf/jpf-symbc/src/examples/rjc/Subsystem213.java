package rjc;

public class Subsystem213 {
  private double Value1448 = 0;
  private double Gain1505 = 0;

  public void Main21( double[] e_and_edot_2, double NofJets_3, double[] Firefct2_4 )
  {
    double sig_0 = 0;
    double sig_1 = 0;
    double sig_2 = 0;
    double sig_3 = 0;
    double sig_4 = 0;
    int ix_38 = 0;
    int ix_50 = 0;

    sig_3 = e_and_edot_2[ (int)( 1 ) ];
    sig_4 = (double)( 1 ) / NofJets_3 * sig_3 * sig_3;
    sig_2 = e_and_edot_2[ (int)( 0 ) ];
    sig_1 = Value1448;
    sig_0 = sig_4 * Gain1505;
    Firefct2_4[ 0 ] = sig_1 + sig_2 - sig_0;
  }
  public void Init28(  )
  {
    Value1448 = 0.00523598775598298810000;
    Gain1505 = 79.5477373096443190000;
  }
}
