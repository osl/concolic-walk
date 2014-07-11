import java.util.Vector;

public class TestDrivers {

    public static void vector3DNormalize(float x, float y, float z) {
	new Vector3D(x, y, z).normalize();
    }

    public static void surfaceShade(float rval, float gval, float bval, float a,
				    float d, float s, float n, float r, float t, float index, float pX,
				    float pY, float pZ, float nX, float nY, float nZ, float vX, float vY,
				    float vZ, int lType, float lX, float lY, float lZ, float lR, float lG,
				    float lB) {
	Surface surface = new Surface(rval, gval, bval, a, d, s, n, r, t, index);
	Vector3D pVec = new Vector3D(pX, pY, pZ);
	Vector3D nVec = new Vector3D(nX, nY, nZ);
	Vector3D vVec = new Vector3D(vX, vY, vZ);

	Light l = new Light(lType, new Vector3D(lX, lY, lZ), lR, lG, lB);
	Vector<Light> lights = new Vector<Light>();
	lights.add(l);

	surface.Shade(pVec, nVec, vVec, lights, new Vector<Renderable>(), new Color(1, 1, 1));
    }

    public static void rayTrace(float cX, float cY, float cZ, float r, float eyeX,
				float eyeY, float eyeZ, float dirX, float dirY, float dirZ) {
	// Sphere.intersect() does not use the {@code surface} field.
	Sphere sphere = new Sphere(null, new Vector3D(cX, cY, cZ), r);
	Vector<Renderable> objects = new Vector<Renderable>();
	objects.add(sphere);

	Vector3D eye = new Vector3D(eyeX, eyeY, eyeZ);
	Vector3D dir = new Vector3D(dirX, dirY, dirZ);
	new Ray(eye, dir).trace(objects);
    }

    public static void sphereIntersect(float rval, float gval, float bval, float a,
				       float d, float s, float n, float r, float t, float index, float x,
				       float y, float z, float rad, float eyeX, float eyeY, float eyeZ,
				       float dirX, float dirY, float dirZ) {
	Vector3D eye = new Vector3D(eyeX, eyeY, eyeZ);
	Vector3D dir = new Vector3D(dirX, dirY, dirZ);
	Ray ray = new Ray(eye, dir);

	Surface surface = new Surface(rval, gval, bval, a, d, s, n, r, t, index);
	Vector3D center = new Vector3D(x, y, z);
	Sphere sphere = new Sphere(surface, center, rad);

	sphere.intersect(ray);
    }

    public static void sphereShade(float rval, float gval, float bval, float a,
				   float d, float s, float n, float r, float t, float index, float x,
				   float y, float z, float rad, float eyeX, float eyeY, float eyeZ,
				   float dirX, float dirY, float dirZ, int lType, float lX, float lY, float lZ, float lR, float lG,
				   float lB, float bgR, float bgG, float bgB) {
	Vector3D eye = new Vector3D(eyeX, eyeY, eyeZ);
	Vector3D dir = new Vector3D(dirX, dirY, dirZ);
	Ray ray = new Ray(eye, dir);

	Surface surface = new Surface(rval, gval, bval, a, d, s, n, r, t, index);
	Vector3D center = new Vector3D(x, y, z);
	Sphere sphere = new Sphere(surface, center, rad);
	Vector<Renderable> objects = new Vector<Renderable>();
	objects.add(sphere);

	Light light = new Light(lType, new Vector3D(lX, lY, lZ), lR, lG, lB);
	Vector<Light> lights = new Vector<Light>();
	lights.add(light);

	Color bgnd = new Color(bgR, bgG, bgB);
	sphere.Shade(ray, lights, objects, bgnd);
    }
}
