/*
 * Automatically converted from Java to C# using Sharpen.
 */

using Microsoft.VisualStudio.TestTools.UnitTesting;
using Microsoft.Pex.Framework;
using System;

/// <summary>Functions used to test optimization software.</summary>
/// <remarks>
/// Functions used to test optimization software. See J. J. More, B. S. Garbow,
/// and K. E. Hillstrom. Testing unconstrained optimization software. ACM Trans.
/// Math. Software, 7(1):17–41, 1981.
/// Ported from the C# implementation given in K. Lakhotia, N. Tillmann, M.
/// Harmann, and J. de Halleaux. FloPSy - Search-Based Floating Point Constraint
/// Solving for Symbolic Execution" (Table 2).
/// </remarks>
[TestClass]
public partial class Optimization
{
    [PexMethod(MaxConstraintSolverTime = 5, MaxRunsWithoutNewTests = 500)]
    public static void Beale(double x1, double x2)
	{
		if ((1.5 - x1 * (1.0 - x2)) == 0.0)
		{
			System.Console.Out.WriteLine("Solved Beale constraint");
		}
	}

    [PexMethod(MaxConstraintSolverTime = 5, MaxRunsWithoutNewTests = 500)]
    public static void FreudensteinRoth(double x1, double x2)
	{
		if ((-13.0 + x1 + ((5.0 - x2) * x2 - 2.0) * x2) + (-29.0 + x1 + ((x2 + 1.0) * x2 
			- 14.0) * x2) == 0.0)
		{
			System.Console.Out.WriteLine("Solved Freudenstein and Roth constraint");
		}
	}

	// This is public only because JPF keeps generating test cases for it
	// and it is highly annoying to remove them every time we regenerate them.
	public static double Theta(double x1, double x2)
	{
		if (x1 > 0.0)
		{
			return Math.Atan(x2 / x1) / (2 * Math.PI);
		}
		else
		{
			if (x1 < 0.0)
			{
				return (Math.Atan(x2 / x1) / (2 * Math.PI) + 0.5);
			}
		}
		return 0.0;
	}

    [PexMethod(MaxConstraintSolverTime = 5, MaxRunsWithoutNewTests = 500)]
    public static void HelicalValley(double x1, double x2, double x3)
	{
		if (10.0 * (x3 - 10.0 * Theta(x1, x2)) == 0 && (10.0 * (Math.Sqrt(x1 * x1 + x2 * 
			x2) - 1)) == 0.0 && x3 == 0.0)
		{
			System.Console.Out.WriteLine("Solved Helical Valley constraint");
		}
	}

    [PexMethod(MaxConstraintSolverTime = 5, MaxRunsWithoutNewTests = 500)]
    public static void Powell(double x1, double x2)
	{
		if ((Math.Pow(10, 4) * x1 * x2 - 1.0) == 0.0 && (Math.Pow(Math.E, -x1) + Math.Pow
			(Math.E, -x2) - 1.0001) == 0.0)
		{
			System.Console.Out.WriteLine("Solved Powell constraint");
		}
	}

    [PexMethod(MaxConstraintSolverTime = 5, MaxRunsWithoutNewTests = 500)]
    public static void Rosenbrock(double x1, double x2)
	{
		if (Math.Pow((1.0 - x1), 2) + 100.0 * (Math.Pow((x2 - x1 * x1), 2)) == 0.0)
		{
			System.Console.Out.WriteLine("Solved Rosenbrock consraint");
		}
	}

    [PexMethod(MaxConstraintSolverTime = 5, MaxRunsWithoutNewTests = 500)]
    public static void Wood(double x1, double x2, double x3, double x4)
	{
		if ((10.0 * (x2 - x1 * x1)) == 0.0 && (1.0 - x1) == 0.0 && (Math.Sqrt(90) * (x4 -
			 x3 * x3)) == 0.0 && (1.0 - x3) == 0.0 && (Math.Sqrt(10) * (x2 + x4 - 2.0)) == 0.0
			 && (Math.Pow(10, -0.5) * (x2 - x4)) == 0.0)
		{
			System.Console.Out.WriteLine("Solved Wood constraint");
		}
	}
}
