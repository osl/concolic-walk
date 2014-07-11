package rjc;

public class Subsystem312 {
  private double Value1452 = 0;
  private double Gain1510 = 0;

  public void Main20( double[] e_and_edot_2, double NofJets_3, double[] Coastfct2_4 )
  {
    double sig_0 = 0;
    double sig_1 = 0;
    double sig_2 = 0;
    double sig_3 = 0;
    double sig_4 = 0;
    int ix_39 = 0;
    int ix_51 = 0;

    sig_3 = e_and_edot_2[ (int)( 1 ) ];
    sig_4 = (double)( 1 ) / NofJets_3 * sig_3 * sig_3;
    sig_2 = e_and_edot_2[ (int)( 0 ) ];
    sig_1 = Value1452;
    sig_0 = sig_4 * Gain1510;
    Coastfct2_4[ 0 ] = sig_1 + sig_2 + sig_0;
  }
  public void Init29(  )
  {
    Value1452 = 0.00523598775598298810000;
    Gain1510 = 795.477373096442990000;
  }
}
