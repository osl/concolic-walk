import cute.Cute;

public class JCuteTargetSurfaceShade {
  public static void main(String[] args){ 
    float rval = cute.Cute.input.Float();
    float gval = cute.Cute.input.Float();
    float bval = cute.Cute.input.Float();
    float a = cute.Cute.input.Float();
    float d = cute.Cute.input.Float();
    float s = cute.Cute.input.Float();
    float n = cute.Cute.input.Float();
    float r = cute.Cute.input.Float();
    float t = cute.Cute.input.Float();
    float index = cute.Cute.input.Float();
    float pX = cute.Cute.input.Float();
    float pY = cute.Cute.input.Float();
    float pZ = cute.Cute.input.Float();
    float nX = cute.Cute.input.Float();
    float nY = cute.Cute.input.Float();
    float nZ = cute.Cute.input.Float();
    float vX = cute.Cute.input.Float();
    float vY = cute.Cute.input.Float();
    float vZ = cute.Cute.input.Float();
    int lType = cute.Cute.input.Integer();
    float lX = cute.Cute.input.Float();
    float lY = cute.Cute.input.Float();
    float lZ = cute.Cute.input.Float();
    float lR = cute.Cute.input.Float();
    float lG = cute.Cute.input.Float();
    float lB = cute.Cute.input.Float();
    
    TestDrivers.surfaceShade(rval, gval, bval, a,
			     d, s, n, r, t, index, pX,
			     pY, pZ, nX, nY, nZ, vX, vY,
			     vZ, lType, lX, lY, lZ, lR, lG, lB);
  }
}
