import cute.Cute;

public class JCuteTargetRayTrace {
  public static void main(String[] args){ 
    float cX = cute.Cute.input.Float();
    float cY = cute.Cute.input.Float();
    float cZ = cute.Cute.input.Float();
    float r = cute.Cute.input.Float();
    float eyeX = cute.Cute.input.Float();
    float eyeY = cute.Cute.input.Float();
    float eyeZ = cute.Cute.input.Float();
    float dirX = cute.Cute.input.Float();
    float dirY = cute.Cute.input.Float();
    float dirZ = cute.Cute.input.Float();
    TestDrivers.rayTrace(cX, cY, cZ, r, eyeX, eyeY, eyeZ, dirX, dirY, dirZ);
  }
}
