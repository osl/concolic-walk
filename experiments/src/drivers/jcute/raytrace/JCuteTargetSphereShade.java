import cute.Cute;

public class JCuteTargetSphereShade {
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
    float cX = cute.Cute.input.Float();
    float cY = cute.Cute.input.Float();
    float cZ = cute.Cute.input.Float();
    float rad = cute.Cute.input.Float();
    float eyeX = cute.Cute.input.Float();
    float eyeY = cute.Cute.input.Float();
    float eyeZ = cute.Cute.input.Float();
    float dirX = cute.Cute.input.Float();
    float dirY = cute.Cute.input.Float();
    float dirZ = cute.Cute.input.Float();
    int lType = cute.Cute.input.Integer();
    float lX = cute.Cute.input.Float();
    float lY = cute.Cute.input.Float();
    float lZ = cute.Cute.input.Float();
    float lR = cute.Cute.input.Float();
    float lG = cute.Cute.input.Float();
    float lB = cute.Cute.input.Float();
    float bgR = cute.Cute.input.Float();
    float bgG = cute.Cute.input.Float();
    float bgB = cute.Cute.input.Float();

    TestDrivers.sphereShade(rval, gval, bval, a,
			    d, s, n, r, t, index,
			    cX, cY, cZ, rad,
			    eyeX, eyeY, eyeZ,
			    dirX, dirY, dirZ,
			    lType, lX, lY, lZ, lR, lG, lB,
			    bgR, bgG, bgB);
  }
}
