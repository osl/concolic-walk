package rjc;

public class Subsystem17 {
  private double Value1425 = 0;
  private double Gain1480 = 0;

  public void Main12( double[] e_and_edot_2, double NofJets_3, double[] Coastfct1_4 )
  {
    double sig_0 = 0;
    double sig_1 = 0;
    double sig_2 = 0;
    double sig_3 = 0;
    double sig_4 = 0;
    int ix_33 = 0;
    int ix_45 = 0;

    sig_3 = e_and_edot_2[ (int)( 1 ) ];
    sig_4 = (double)( 1 ) / NofJets_3 * sig_3 * sig_3;
    sig_2 = e_and_edot_2[ (int)( 0 ) ];
    sig_1 = Value1425;
    sig_0 = sig_4 * Gain1480;
    Coastfct1_4[ 0 ] = -sig_1 + sig_2 - sig_0;
  }
  public void Init17(  )
  {
    Value1425 = 0.00523598775598298810000;
    Gain1480 = 90.9090909090909070000;
  }
}
