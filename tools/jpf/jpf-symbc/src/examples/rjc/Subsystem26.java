package rjc;

public class Subsystem26 {
  private double Value1429 = 0;
  private double Gain1485 = 0;

  public void Main11( double[] e_and_edot_2, double NofJets_3, double[] Firefct2_4 )
  {
    double sig_0 = 0;
    double sig_1 = 0;
    double sig_2 = 0;
    double sig_3 = 0;
    double sig_4 = 0;
    int ix_34 = 0;
    int ix_46 = 0;

    sig_3 = e_and_edot_2[ (int)( 1 ) ];
    sig_4 = (double)( 1 ) / NofJets_3 * sig_3 * sig_3;
    sig_2 = e_and_edot_2[ (int)( 0 ) ];
    sig_1 = Value1429;
    sig_0 = sig_4 * Gain1485;
    Firefct2_4[ 0 ] = sig_1 + sig_2 - sig_0;
  }
  public void Init18(  )
  {
    Value1429 = 0.00523598775598298810000;
    Gain1485 = 9.09090909090909170000;
  }
}
