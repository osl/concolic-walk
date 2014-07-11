import java.util.*;

/***************************************************
*
*   An instructional Ray-Tracing Renderer written
*   for MIT 6.837  Fall '98 by Leonard McMillan.
*
*   A fairly primitive Ray-Tracing program written
*   on a Sunday afternoon before Monday's class.
*   Everything is contained in a single file. The
*   structure should be fairly easy to extend, with
*   new primitives, features and other such stuff.
*
*   I tend to write things bottom up (old K&R C
*   habits die slowly). If you want the big picture
*   scroll to the applet code at the end and work
*   your way back here.
*
****************************************************/

class Color {
    // Simple replacement for {@link java.awt.Color} to remove the AWT
    // dependency.
    public float r, g, b;

    public Color(float r, float g, float b) {
	this.r = r;
	this.g = g;
	this.b = b;
    }

    public float getRed() {
	return r;
    }

    public float getGreen() {
	return g;
    }

    public float getBlue() {
	return b;
    }
}


// A simple vector class
class Vector3D {
    public float x, y, z;

    // constructors
    public Vector3D( ) {
    }

    public Vector3D(float x, float y, float z) {
        this.x = x; this.y = y; this.z = z;
    }

    public Vector3D(Vector3D v) {
        x = v.x;
        y = v.y;
        z = v.z;
    }

    // methods
    public final float dot(Vector3D B) {
        return (x*B.x + y*B.y + z*B.z);
    }

    public final float dot(float Bx, float By, float Bz) {
        return (x*Bx + y*By + z*Bz);
    }

    public static final float dot(Vector3D A, Vector3D B) {
        return (A.x*B.x + A.y*B.y + A.z*B.z);
    }

    public final Vector3D cross(Vector3D B) {
        return new Vector3D(y*B.z - z*B.y, z*B.x - x*B.z, x*B.y - y*B.x);
    }

    public final Vector3D cross(float Bx, float By, float Bz) {
        return new Vector3D(y*Bz - z*By, z*Bx - x*Bz, x*By - y*Bx);
    }

    public final static Vector3D cross(Vector3D A, Vector3D B) {
        return new Vector3D(A.y*B.z - A.z*B.y, A.z*B.x - A.x*B.z, A.x*B.y - A.y*B.x);
    }

    public final float length( ) {
        return (float) Math.sqrt(x*x + y*y + z*z);
    }

    public final static float length(Vector3D A) {
        return (float) Math.sqrt(A.x*A.x + A.y*A.y + A.z*A.z);
    }

    public final void normalize( ) {
        float t = x*x + y*y + z*z;
        if (t != 0 && t != 1) t = (float) (1 / Math.sqrt(t));
        x *= t;
        y *= t;
        z *= t;
    }

    public final static Vector3D normalize(Vector3D A) {
        float t = A.x*A.x + A.y*A.y + A.z*A.z;
        if (t != 0 && t != 1) t = (float)(1 / Math.sqrt(t));
        return new Vector3D(A.x*t, A.y*t, A.z*t);
    }

    public String toString() {
        return new String("["+x+", "+y+", "+z+"]");
    }
}


class Ray {
    public static final float MAX_T = Float.MAX_VALUE;
    Vector3D origin;
    Vector3D direction;
    float t;
    Renderable object;

    public Ray(Vector3D eye, Vector3D dir) {
        origin = new Vector3D(eye);
        direction = Vector3D.normalize(dir);
    }

    public boolean trace(Vector objects) {
        Enumeration objList = objects.elements();
        t = MAX_T;
        object = null;
        while (objList.hasMoreElements()) {
            Renderable object = (Renderable) objList.nextElement();
            object.intersect(this);
        }
        return (object != null);
    }

    // The following method is not strictly needed, and most likely
    // adds unnecessary overhead, but I prefered the syntax
    //
    //            ray.Shade(...)
    // to
    //            ray.object.Shade(ray, ...)
    //
    public final Color Shade(Vector lights, Vector objects, Color bgnd) {
        return object.Shade(this, lights, objects, bgnd);
    }

    public String toString() {
        return ("ray origin = "+origin+"  direction = "+direction+"  t = "+t);
    }
}

// All the public variables here are ugly, but I
// wanted Lights and Surfaces to be "friends"
class Light {
    public static final int AMBIENT = 0;
    public static final int DIRECTIONAL = 1;
    public static final int POINT = 2;

    public int lightType;
    public Vector3D lvec;           // the position of a point light or
                                    // the direction to a directional light
    public float ir, ig, ib;        // intensity of the light source

    public Light(int type, Vector3D v, float r, float g, float b) {
        lightType = type;
        ir = r;
        ig = g;
        ib = b;
        if (type != AMBIENT) {
            lvec = v;
            if (type == DIRECTIONAL) {
                lvec.normalize();
            }
        }
    }
}


class Surface {
    public float ir, ig, ib;        // surface's intrinsic color
    public float ka, kd, ks, ns;    // constants for phong model
    public float kt, kr, nt;
    private static final float TINY = 0.001f;
    private static final float I255 = 0.00392156f;  // 1/255

    public Surface(float rval, float gval, float bval, float a, float d, float s, float n, float r, float t, float index) {
        ir = rval; ig = gval; ib = bval;
        ka = a; kd = d; ks = s; ns = n;
        kr = r*I255; kt = t; nt = index;
    }

    public Color Shade(Vector3D p, Vector3D n, Vector3D v, Vector lights, Vector objects, Color bgnd) {
        Enumeration lightSources = lights.elements();

        float r = 0;
        float g = 0;
        float b = 0;
        while (lightSources.hasMoreElements()) {
            Light light = (Light) lightSources.nextElement();
            if (light.lightType == Light.AMBIENT) {
                r += ka*ir*light.ir;
                g += ka*ig*light.ig;
                b += ka*ib*light.ib;
            } else {
                Vector3D l;
                if (light.lightType == Light.POINT) {
                    l = new Vector3D(light.lvec.x - p.x, light.lvec.y - p.y, light.lvec.z - p.z);
                    l.normalize();
                } else {
                    l = new Vector3D(-light.lvec.x, -light.lvec.y, -light.lvec.z);
                }

                // Check if the surface point is in shadow
                Vector3D poffset = new Vector3D(p.x + TINY*l.x, p.y + TINY*l.y, p.z + TINY*l.z);
                Ray shadowRay = new Ray(poffset, l);
                if (shadowRay.trace(objects))
                    break;

                float lambert = Vector3D.dot(n,l);
                if (lambert > 0) {
                    if (kd > 0) {
                        float diffuse = kd*lambert;
                        r += diffuse*ir*light.ir;
                        g += diffuse*ig*light.ig;
                        b += diffuse*ib*light.ib;
                    }
                    if (ks > 0) {
                        lambert *= 2;
                        float spec = v.dot(lambert*n.x - l.x, lambert*n.y - l.y, lambert*n.z - l.z);
                        if (spec > 0) {
                            spec = ks*((float) Math.pow((double) spec, (double) ns));
                            r += spec*light.ir;
                            g += spec*light.ig;
                            b += spec*light.ib;
                        }
                    }
                }
            }
        }

        // Compute illumination due to reflection
        if (kr > 0) {
            float t = v.dot(n);
            if (t > 0) {
                t *= 2;
                Vector3D reflect = new Vector3D(t*n.x - v.x, t*n.y - v.y, t*n.z - v.z);
                Vector3D poffset = new Vector3D(p.x + TINY*reflect.x, p.y + TINY*reflect.y, p.z + TINY*reflect.z);
                Ray reflectedRay = new Ray(poffset, reflect);
                if (reflectedRay.trace(objects)) {
                    Color rcolor = reflectedRay.Shade(lights, objects, bgnd);
                    r += kr*rcolor.getRed();
                    g += kr*rcolor.getGreen();
                    b += kr*rcolor.getBlue();
                } else {
                    r += kr*bgnd.getRed();
                    g += kr*bgnd.getGreen();
                    b += kr*bgnd.getBlue();
                }
            }
        }

        // Add code for refraction here

        r = (r > 1f) ? 1f : r;
        g = (g > 1f) ? 1f : g;
        b = (b > 1f) ? 1f : b;
        return new Color(r, g, b);
    }
}


// An object must implement a Renderable interface in order to
// be ray traced. Using this interface it is straight forward
// to add new objects
interface Renderable {
    public boolean intersect(Ray r);
    public Color Shade(Ray r, Vector lights, Vector objects, Color bgnd);
    public String toString();
}

// An example "Renderable" object
class Sphere implements Renderable {
    Surface surface;
    Vector3D center;
    float radius;
    float radSqr;

    public Sphere(Surface s, Vector3D c, float r) {
        surface = s;
        center = c;
        radius = r;
        radSqr = r*r;
    }

    public boolean intersect(Ray ray) {
        float dx = center.x - ray.origin.x;
        float dy = center.y - ray.origin.y;
        float dz = center.z - ray.origin.z;
        float v = ray.direction.dot(dx, dy, dz);

        // Do the following quick check to see if there is even a chance
        // that an intersection here might be closer than a previous one
        if (v - radius > ray.t)
            return false;

        // Test if the ray actually intersects the sphere
        float t = radSqr + v*v - dx*dx - dy*dy - dz*dz;
        if (t < 0)
            return false;

        // Test if the intersection is in the positive
        // ray direction and it is the closest so far
        t = v - ((float) Math.sqrt(t));
        if ((t > ray.t) || (t < 0))
            return false;

        ray.t = t;
        ray.object = this;
        return true;
    }

    public Color Shade(Ray ray, Vector lights, Vector objects, Color bgnd) {
        // An object shader doesn't really do too much other than
        // supply a few critical bits of geometric information
        // for a surface shader. It must must compute:
        //
        //   1. the point of intersection (p)
        //   2. a unit-length surface normal (n)
        //   3. a unit-length vector towards the ray's origin (v)
        //
        float px = ray.origin.x + ray.t*ray.direction.x;
        float py = ray.origin.y + ray.t*ray.direction.y;
        float pz = ray.origin.z + ray.t*ray.direction.z;

        Vector3D p = new Vector3D(px, py, pz);
        Vector3D v = new Vector3D(-ray.direction.x, -ray.direction.y, -ray.direction.z);
        Vector3D n = new Vector3D(px - center.x, py - center.y, pz - center.z);
        n.normalize();

        // The illumination model is applied
        // by the surface's Shade() method
        return surface.Shade(p, n, v, lights, objects, bgnd);
    }

    public String toString() {
        return ("sphere "+center+" "+radius);
    }
}

