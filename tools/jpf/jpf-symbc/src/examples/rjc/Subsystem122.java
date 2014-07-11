package rjc;

public class Subsystem122 {
  private double Value1463 = 0;
  private double Gain1520 = 0;

  public void Main32( double[] e_and_edot_2, double NofJets_3, double[] Coastfct1_4 )
  {
    double sig_0 = 0;
    double sig_1 = 0;
    double sig_2 = 0;
    double sig_3 = 0;
    double sig_4 = 0;
    int ix_41 = 0;
    int ix_53 = 0;

    sig_3 = e_and_edot_2[ (int)( 1 ) ];
    sig_4 = (double)( 1 ) / NofJets_3 * sig_3 * sig_3;
    sig_2 = e_and_edot_2[ (int)( 0 ) ];
    sig_1 = Value1463;
    sig_0 = sig_4 * Gain1520;
    Coastfct1_4[ 0 ] = -sig_1 + sig_2 - sig_0;
  }
  public void Init37(  )
  {
    Value1463 = 0.00523598775598298810000;
    Gain1520 = 6365.09762626099750000;
  }
}
