import cute.Cute;

public class JCuteTargetSphereIntersect {
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

    TestDrivers.sphereIntersect(rval, gval, bval, a,
				d, s, n, r, t, index,
				cX, cY, cZ, rad,
				eyeX, eyeY, eyeZ,
				dirX, dirY, dirZ);
  }
}
