/*
 * Automatically converted from Java to C# using Sharpen.
 */

using System;
using Microsoft.VisualStudio.TestTools.UnitTesting;

using System.Collections;
using System.Collections.Generic;
using Microsoft.Pex.Framework;

[TestClass]
public partial class TestDrivers
{
    [PexMethod(MaxConstraintSolverTime = 5, MaxRunsWithoutNewTests = 500)]
    public static void Vector3DNormalize(float x, float y, float z)
	{
		new Vector3D(x, y, z).Normalize();
	}

    [PexMethod(MaxConstraintSolverTime = 5, MaxRunsWithoutNewTests = 500)]
    public static void SurfaceShade(float rval, float gval, float bval, float a, float
		 d, float s, float n, float r, float t, float index, float pX, float pY, float pZ
		, float nX, float nY, float nZ, float vX, float vY, float vZ, int lType, float lX
		, float lY, float lZ, float lR, float lG, float lB)
	{
		Surface surface = new Surface(rval, gval, bval, a, d, s, n, r, t, index);
		Vector3D pVec = new Vector3D(pX, pY, pZ);
		Vector3D nVec = new Vector3D(nX, nY, nZ);
		Vector3D vVec = new Vector3D(vX, vY, vZ);
		Light l = new Light(lType, new Vector3D(lX, lY, lZ), lR, lG, lB);
		ArrayList lights = new ArrayList();
		lights.Add(l);
		surface.Shade(pVec, nVec, vVec, lights, new ArrayList(), new Color(1, 1, 
			1));
	}

    [PexMethod(MaxConstraintSolverTime = 5, MaxRunsWithoutNewTests = 500)]
    public static void RayTrace(float cX, float cY, float cZ, float r, float eyeX, float
		 eyeY, float eyeZ, float dirX, float dirY, float dirZ)
	{
		// Sphere.intersect() does not use the {@code surface} field.
		Sphere sphere = new Sphere(null, new Vector3D(cX, cY, cZ), r);
		ArrayList objects = new ArrayList();
		objects.Add(sphere);
		Vector3D eye = new Vector3D(eyeX, eyeY, eyeZ);
		Vector3D dir = new Vector3D(dirX, dirY, dirZ);
		new Ray(eye, dir).Trace(objects);
	}

    [PexMethod(MaxConstraintSolverTime = 5, MaxRunsWithoutNewTests = 500)]
    public static void SphereIntersect(float rval, float gval, float bval, float a, float
		 d, float s, float n, float r, float t, float index, float x, float y, float z, 
		float rad, float eyeX, float eyeY, float eyeZ, float dirX, float dirY, float dirZ
		)
	{
		Vector3D eye = new Vector3D(eyeX, eyeY, eyeZ);
		Vector3D dir = new Vector3D(dirX, dirY, dirZ);
		Ray ray = new Ray(eye, dir);
		Surface surface = new Surface(rval, gval, bval, a, d, s, n, r, t, index);
		Vector3D center = new Vector3D(x, y, z);
		Sphere sphere = new Sphere(surface, center, rad);
		sphere.Intersect(ray);
	}

    [PexMethod(MaxConstraintSolverTime = 5, MaxRunsWithoutNewTests = 500)]
    public static void SphereShade(float rval, float gval, float bval, float a, float
		 d, float s, float n, float r, float t, float index, float x, float y, float z, 
		float rad, float eyeX, float eyeY, float eyeZ, float dirX, float dirY, float dirZ
		, int lType, float lX, float lY, float lZ, float lR, float lG, float lB, float bgR
		, float bgG, float bgB)
	{
		Vector3D eye = new Vector3D(eyeX, eyeY, eyeZ);
		Vector3D dir = new Vector3D(dirX, dirY, dirZ);
		Ray ray = new Ray(eye, dir);
		Surface surface = new Surface(rval, gval, bval, a, d, s, n, r, t, index);
		Vector3D center = new Vector3D(x, y, z);
		Sphere sphere = new Sphere(surface, center, rad);
		ArrayList objects = new ArrayList();
		objects.Add(sphere);
		Light light = new Light(lType, new Vector3D(lX, lY, lZ), lR, lG, lB);
		ArrayList lights = new ArrayList();
		lights.Add(light);
		Color bgnd = new Color(bgR, bgG, bgB);
		sphere.Shade(ray, lights, objects, bgnd);
	}
}
