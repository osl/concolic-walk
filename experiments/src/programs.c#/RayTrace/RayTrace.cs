/*
 * Automatically converted from Java to C# using Sharpen.
 */

using System;
using System.Collections;
using System.Collections.Generic;

/// <summary>
/// An instructional Ray-Tracing Renderer written
/// for MIT 6.837  Fall '98 by Leonard McMillan.
/// </summary>
/// <remarks>
/// An instructional Ray-Tracing Renderer written
/// for MIT 6.837  Fall '98 by Leonard McMillan.
/// A fairly primitive Ray-Tracing program written
/// on a Sunday afternoon before Monday's class.
/// Everything is contained in a single file. The
/// structure should be fairly easy to extend, with
/// new primitives, features and other such stuff.
/// I tend to write things bottom up (old K&R C
/// habits die slowly). If you want the big picture
/// scroll to the applet code at the end and work
/// your way back here.
/// </remarks>
public class Color
{
	public float r;

	public float g;

	public float b;

	public Color(float r, float g, float b)
	{
		// Simple replacement for {@link java.awt.Color} to remove the AWT
		// dependency.
		this.r = r;
		this.g = g;
		this.b = b;
	}

	public virtual float GetRed()
	{
		return r;
	}

	public virtual float GetGreen()
	{
		return g;
	}

	public virtual float GetBlue()
	{
		return b;
	}
}

public class Vector3D
{
	public float x;

	public float y;

	public float z;

	public Vector3D()
	{
	}

	public Vector3D(float x, float y, float z)
	{
		// A simple vector class
		// constructors
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vector3D(Vector3D v)
	{
		x = v.x;
		y = v.y;
		z = v.z;
	}

	// methods
	public float Dot(Vector3D B)
	{
		return (x * B.x + y * B.y + z * B.z);
	}

	public float Dot(float Bx, float By, float Bz)
	{
		return (x * Bx + y * By + z * Bz);
	}

	public static float Dot(Vector3D A, Vector3D B)
	{
		return (A.x * B.x + A.y * B.y + A.z * B.z);
	}

	public Vector3D Cross(Vector3D B)
	{
		return new Vector3D(y * B.z - z * B.y, z * B.x - x * B.z, x * B.y - y * B.x);
	}

	public Vector3D Cross(float Bx, float By, float Bz)
	{
		return new Vector3D(y * Bz - z * By, z * Bx - x * Bz, x * By - y * Bx);
	}

	public static Vector3D Cross(Vector3D A, Vector3D B)
	{
		return new Vector3D(A.y * B.z - A.z * B.y, A.z * B.x - A.x * B.z, A.x * B.y - A.y
			 * B.x);
	}

	public float Length()
	{
		return (float)Math.Sqrt(x * x + y * y + z * z);
	}

	public static float Length(Vector3D A)
	{
		return (float)Math.Sqrt(A.x * A.x + A.y * A.y + A.z * A.z);
	}

	public void Normalize()
	{
		float t = x * x + y * y + z * z;
		if (t != 0 && t != 1)
		{
			t = (float)(1 / Math.Sqrt(t));
		}
		x *= t;
		y *= t;
		z *= t;
	}

	public static Vector3D Normalize(Vector3D A)
	{
		float t = A.x * A.x + A.y * A.y + A.z * A.z;
		if (t != 0 && t != 1)
		{
			t = (float)(1 / Math.Sqrt(t));
		}
		return new Vector3D(A.x * t, A.y * t, A.z * t);
	}

	public override string ToString()
	{
		return "[" + x + ", " + y + ", " + z + "]";
	}
}

public class Ray
{
	public const float MAX_T = float.MaxValue;

	internal Vector3D origin;

	internal Vector3D direction;

	internal float t;

	internal Renderable @object;

	public Ray(Vector3D eye, Vector3D dir)
	{
		origin = new Vector3D(eye);
		direction = Vector3D.Normalize(dir);
	}

	public virtual bool Trace(ArrayList objects)
	{
		IEnumerator objList = objects.GetEnumerator();
		t = MAX_T;
		@object = null;
		while (objList.MoveNext())
		{
			Renderable @object2 = (Renderable)objList.Current;
			@object2.Intersect(this);
		}
		return (@object != null);
	}

	// The following method is not strictly needed, and most likely
	// adds unnecessary overhead, but I prefered the syntax
	//
	//            ray.Shade(...)
	// to
	//            ray.object.Shade(ray, ...)
	//
	public Color Shade(ArrayList lights, ArrayList objects, Color bgnd)
	{
		return @object.Shade(this, lights, objects, bgnd);
	}

	public override string ToString()
	{
		return ("ray origin = " + origin + "  direction = " + direction + "  t = " + t);
	}
}

public class Light
{
	public const int AMBIENT = 0;

	public const int DIRECTIONAL = 1;

	public const int POINT = 2;

	public int lightType;

	public Vector3D lvec;

	public float ir;

	public float ig;

	public float ib;

	public Light(int type, Vector3D v, float r, float g, float b)
	{
		// All the public variables here are ugly, but I
		// wanted Lights and Surfaces to be "friends"
		// the position of a point light or
		// the direction to a directional light
		// intensity of the light source
		lightType = type;
		ir = r;
		ig = g;
		ib = b;
		if (type != AMBIENT)
		{
			lvec = v;
			if (type == DIRECTIONAL)
			{
				lvec.Normalize();
			}
		}
	}
}

public class Surface
{
	public float ir;

	public float ig;

	public float ib;

	public float ka;

	public float kd;

	public float ks;

	public float ns;

	public float kt;

	public float kr;

	public float nt;

	private const float TINY = 0.001f;

	private const float I255 = 0.00392156f;

	public Surface(float rval, float gval, float bval, float a, float d, float s, float
		 n, float r, float t, float index)
	{
		// surface's intrinsic color
		// constants for phong model
		// 1/255
		ir = rval;
		ig = gval;
		ib = bval;
		ka = a;
		kd = d;
		ks = s;
		ns = n;
		kr = r * I255;
		kt = t;
		nt = index;
	}

	public virtual Color Shade(Vector3D p, Vector3D n, Vector3D v, ArrayList lights, 
		ArrayList objects, Color bgnd)
	{
		IEnumerator lightSources = lights.GetEnumerator();
		float r = 0;
		float g = 0;
		float b = 0;
		while (lightSources.MoveNext())
		{
			Light light = (Light)lightSources.Current;
			if (light.lightType == Light.AMBIENT)
			{
				r += ka * ir * light.ir;
				g += ka * ig * light.ig;
				b += ka * ib * light.ib;
			}
			else
			{
				Vector3D l;
				if (light.lightType == Light.POINT)
				{
					l = new Vector3D(light.lvec.x - p.x, light.lvec.y - p.y, light.lvec.z - p.z);
					l.Normalize();
				}
				else
				{
					l = new Vector3D(-light.lvec.x, -light.lvec.y, -light.lvec.z);
				}
				// Check if the surface point is in shadow
				Vector3D poffset = new Vector3D(p.x + TINY * l.x, p.y + TINY * l.y, p.z + TINY * 
					l.z);
				Ray shadowRay = new Ray(poffset, l);
				if (shadowRay.Trace(objects))
				{
					break;
				}
				float lambert = Vector3D.Dot(n, l);
				if (lambert > 0)
				{
					if (kd > 0)
					{
						float diffuse = kd * lambert;
						r += diffuse * ir * light.ir;
						g += diffuse * ig * light.ig;
						b += diffuse * ib * light.ib;
					}
					if (ks > 0)
					{
						lambert *= 2;
						float spec = v.Dot(lambert * n.x - l.x, lambert * n.y - l.y, lambert * n.z - l.z);
						if (spec > 0)
						{
							spec = ks * ((float)Math.Pow((double)spec, (double)ns));
							r += spec * light.ir;
							g += spec * light.ig;
							b += spec * light.ib;
						}
					}
				}
			}
		}
		// Compute illumination due to reflection
		if (kr > 0)
		{
			float t = v.Dot(n);
			if (t > 0)
			{
				t *= 2;
				Vector3D reflect = new Vector3D(t * n.x - v.x, t * n.y - v.y, t * n.z - v.z);
				Vector3D poffset = new Vector3D(p.x + TINY * reflect.x, p.y + TINY * reflect.y, p
					.z + TINY * reflect.z);
				Ray reflectedRay = new Ray(poffset, reflect);
				if (reflectedRay.Trace(objects))
				{
					Color rcolor = reflectedRay.Shade(lights, objects, bgnd);
					r += kr * rcolor.GetRed();
					g += kr * rcolor.GetGreen();
					b += kr * rcolor.GetBlue();
				}
				else
				{
					r += kr * bgnd.GetRed();
					g += kr * bgnd.GetGreen();
					b += kr * bgnd.GetBlue();
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

public interface Renderable
{
	// An object must implement a Renderable interface in order to
	// be ray traced. Using this interface it is straight forward
	// to add new objects
	bool Intersect(Ray r);

	Color Shade(Ray r, ArrayList lights, ArrayList objects, Color bgnd);

	string ToString();
}

public class Sphere : Renderable
{
	internal Surface surface;

	internal Vector3D center;

	internal float radius;

	internal float radSqr;

	public Sphere(Surface s, Vector3D c, float r)
	{
		// An example "Renderable" object
		surface = s;
		center = c;
		radius = r;
		radSqr = r * r;
	}

	public virtual bool Intersect(Ray ray)
	{
		float dx = center.x - ray.origin.x;
		float dy = center.y - ray.origin.y;
		float dz = center.z - ray.origin.z;
		float v = ray.direction.Dot(dx, dy, dz);
		// Do the following quick check to see if there is even a chance
		// that an intersection here might be closer than a previous one
		if (v - radius > ray.t)
		{
			return false;
		}
		// Test if the ray actually intersects the sphere
		float t = radSqr + v * v - dx * dx - dy * dy - dz * dz;
		if (t < 0)
		{
			return false;
		}
		// Test if the intersection is in the positive
		// ray direction and it is the closest so far
		t = v - ((float)Math.Sqrt(t));
		if ((t > ray.t) || (t < 0))
		{
			return false;
		}
		ray.t = t;
		ray.@object = this;
		return true;
	}

	public virtual Color Shade(Ray ray, ArrayList lights, ArrayList objects, Color bgnd
		)
	{
		// An object shader doesn't really do too much other than
		// supply a few critical bits of geometric information
		// for a surface shader. It must must compute:
		//
		//   1. the point of intersection (p)
		//   2. a unit-length surface normal (n)
		//   3. a unit-length vector towards the ray's origin (v)
		//
		float px = ray.origin.x + ray.t * ray.direction.x;
		float py = ray.origin.y + ray.t * ray.direction.y;
		float pz = ray.origin.z + ray.t * ray.direction.z;
		Vector3D p = new Vector3D(px, py, pz);
		Vector3D v = new Vector3D(-ray.direction.x, -ray.direction.y, -ray.direction.z);
		Vector3D n = new Vector3D(px - center.x, py - center.y, pz - center.z);
		n.Normalize();
		// The illumination model is applied
		// by the surface's Shade() method
		return surface.Shade(p, n, v, lights, objects, bgnd);
	}

	public override string ToString()
	{
		return ("sphere " + center + " " + radius);
	}
}
