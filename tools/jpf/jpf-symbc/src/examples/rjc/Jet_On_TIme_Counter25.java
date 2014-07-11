package rjc;

public class Jet_On_TIme_Counter25 {
  private double Value1402 = 0;

  public void Main34( double ton_2, double Clock_at_tics_3, double Clock_at_Sample_Time_4, boolean[] Stop_jets_5 )
  {
    double sig_0 = 0;
    double sig_1 = 0;
    double sig_2 = 0;

    sig_2 = Clock_at_tics_3 - Clock_at_Sample_Time_4;
    sig_1 = ton_2 - sig_2;
    sig_0 = Value1402;
    Stop_jets_5[ 0 ] = sig_1 > sig_0;
  }
  public void Init35(  )
  {
    Value1402 = 0;
  }
}
