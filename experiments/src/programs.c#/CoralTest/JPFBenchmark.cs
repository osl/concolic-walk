/*
 * Automatically converted from Java to C# using Sharpen.
 */

using System;
using Coral.Tests;
using Microsoft.Pex.Framework;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace Coral.Tests
{
    [TestClass]
    public partial class JPFBenchmark
	{
		public static string success = "success";

		// sin(x) + cos(y) > 1.0
        [PexMethod(MaxConstraintSolverTime = 5, MaxRunsWithoutNewTests = 500)]
		public static void Benchmark01(double x, double y)
		{
			if (Math.Sin(x) + Math.Cos(y) > 1)
			{
				System.Console.Out.WriteLine("Solved 01");
			}
		}

		// sin(x) - cos(y) < 0.0000000001
        [PexMethod(MaxConstraintSolverTime = 5, MaxRunsWithoutNewTests = 500)]
		public static void Benchmark02(double x, double y)
		{
			if (Math.Sin(x) - Math.Cos(y) < 0.0000000001)
			{
				System.Console.Out.WriteLine("Solved 02");
			}
		}

		// sin(x) - cos(y) == 0.0
        [PexMethod(MaxConstraintSolverTime = 5, MaxRunsWithoutNewTests = 500)]
		public static void Benchmark03(double x, double y)
		{
			if (Math.Sin(x) - Math.Cos(y) == 0)
			{
				System.Console.Out.WriteLine("Solved 03");
			}
		}

		// exp(x) > 0.0
        [PexMethod(MaxConstraintSolverTime = 5, MaxRunsWithoutNewTests = 500)]
		public static void Benchmark04(double x)
		{
			if (Math.Exp(x) > 0)
			{
				System.Console.Out.WriteLine("Solved 04");
			}
		}

		// sin A + sin B + sin C = 4 * cos A * cos B * cos C
        [PexMethod(MaxConstraintSolverTime = 5, MaxRunsWithoutNewTests = 500)]
		public static void Benchmark05(double x, double y, double z)
		{
			if (Math.Sin(x) + Math.Sin(y) + Math.Sin(z) == 4 * Math.Cos(x) * Math.Cos(y) * Math
				.Cos(z))
			{
				System.Console.Out.WriteLine("Solved 05");
			}
		}

		// cos A + cos B + cos C > 4 sin A/2 sin B/2 sin C/2
        [PexMethod(MaxConstraintSolverTime = 5, MaxRunsWithoutNewTests = 500)]
		public static void Benchmark06(double x, double y, double z)
		{
			if (Math.Cos(x) + Math.Cos(y) + Math.Cos(z) > 4 * Math.Sin(x / 2) * Math.Sin(y / 
				2) * Math.Sin(z / 2))
			{
				System.Console.Out.WriteLine("Solved 06");
			}
		}

		// (sin(2x - y)/(cos(2y + y) + 1) = cos(2z + x)/(sin(2w + y) - 1)
        [PexMethod(MaxConstraintSolverTime = 5, MaxRunsWithoutNewTests = 500)]
		public static void Benchmark07(double x, double y, double z, double w)
		{
			if (Math.Sin(2 * x - y) / (Math.Cos(2 * y + y) + 1) == Math.Cos(2 * z + x) / (Math
				.Sin(2 * w + y) - 1))
			{
				System.Console.Out.WriteLine("Solved 07");
			}
		}

		// cos(3x+2y-z) * sin(z+x+y) == cos(z*x*y)
		[PexMethod(MaxConstraintSolverTime = 5, MaxRunsWithoutNewTests = 500)]
		public static void Benchmark08(double x, double y, double z)
		{
			if (Math.Cos(3 * x + 2 * y - z) * Math.Sin(z + x + y) == Math.Cos(z * x * y))
			{
				System.Console.Out.WriteLine("Solved 08");
			}
		}

		// Math.sin(Math.cos(x*y)) < Math.cos(Math.sin(x*y))
		[PexMethod(MaxConstraintSolverTime = 5, MaxRunsWithoutNewTests = 500)]
		public static void Benchmark09(double x, double y)
		{
			if (Math.Sin(Math.Cos(x * y)) < Math.Cos(Math.Sin(x * y)))
			{
				System.Console.Out.WriteLine("Solved 09");
			}
		}

		// Math.sin(x*Math.cos(y*Math.sin(z))) > Math.cos(x*Math.sin(y*Math.cos(z)))
		[PexMethod(MaxConstraintSolverTime = 5, MaxRunsWithoutNewTests = 500)]
		public static void Benchmark10(double x, double y, double z)
		{
			if (Math.Sin(x * Math.Cos(y * Math.Sin(z))) > Math.Cos(x * Math.Sin(y * Math.Cos(
				z))))
			{
				System.Console.Out.WriteLine("Solved 10");
			}
		}

		// asin(x) < cos(y)*cos(z) - Math.atan(w)
		[PexMethod(MaxConstraintSolverTime = 5, MaxRunsWithoutNewTests = 500)]
		public static void Benchmark11(double x, double y, double z, double w)
		{
			if (Math.Asin(x) < Math.Cos(y) * Math.Cos(z) - Math.Atan(w))
			{
				System.Console.Out.WriteLine("Solved 11");
			}
		}

		// (asin(x) * Math.asin(y))-1 < Math.atan(z) * Math.atan(w)  
		[PexMethod(MaxConstraintSolverTime = 5, MaxRunsWithoutNewTests = 500)]
		public static void Benchmark12(double x, double y, double z, double w)
		{
			if ((Math.Asin(x) * Math.Asin(y)) - 1 < Math.Atan(z) * Math.Atan(w))
			{
				System.Console.Out.WriteLine("Solved 12");
			}
		}

		// sin(y) * Math.asin(x) < cos(y)*cos(z) - Math.atan(w)
		[PexMethod(MaxConstraintSolverTime = 5, MaxRunsWithoutNewTests = 500)]
		public static void Benchmark13(double x, double y, double z, double w)
		{
			if (Math.Sin(y) * Math.Asin(x) < Math.Cos(y) * Math.Cos(z) - Math.Atan(w))
			{
				System.Console.Out.WriteLine("Solved 13");
			}
		}

		// sin(y) * Math.asin(x) - 300 < cos(y)*cos(z) - Math.atan(w)
		[PexMethod(MaxConstraintSolverTime = 5, MaxRunsWithoutNewTests = 500)]
		public static void Benchmark14(double x, double y, double z, double w)
		{
			if (Math.Sin(y) * Math.Asin(x) - 300 < Math.Cos(y) * Math.Cos(z) - Math.Atan(w))
			{
				System.Console.Out.WriteLine("Solved 14");
			}
		}

		// ((Math.asin(1) * Math.asin(cos(9*57)))-1) < (Math.atan(0) * Math.atan(0)) solução; x=1,y=513,z=0,w=0
		[PexMethod(MaxConstraintSolverTime = 5, MaxRunsWithoutNewTests = 500)]
		public static void Benchmark15(double x, double y, double z, double w)
		{
			if (((Math.Asin(1) * Math.Asin(Math.Cos(9 * 57))) - 1) < (Math.Atan(0) * Math.Atan
				(0)))
			{
				System.Console.Out.WriteLine("Solved 15");
			}
		}

		//((((tan_(($V4-$V1))*cos_(sin_(($V4/$V5))))-atan_((($V2+20.0)+$V3)))+asin_(($V2-15.0))) < ((sin_(($V4*$V4))*cos_((($V1*$V4)*$V5)))-tan_((cos_((($V1*$V4)*$V1))+sin_($V4)))))  
		[PexMethod(MaxConstraintSolverTime = 5, MaxRunsWithoutNewTests = 500)]
		public static void Benchmark16(double x, double y, double z, double w, double v)
		{
			if (Math.Tan(w - x) * Math.Cos(Math.Sin(w / v)) - Math.Atan(y + 20 + z) + Math.Asin
				(y - 15) < Math.Sin(w * w) * Math.Cos(x * w * v) - Math.Tan(Math.Cos(x * w * x))
				 + Math.Sin(w))
			{
				System.Console.Out.WriteLine("Solved 16");
			}
		}

		// Math.asin(x) * Math.acos(x) < Math.atan(x)  
		[PexMethod(MaxConstraintSolverTime = 5, MaxRunsWithoutNewTests = 500)]
		public static void Benchmark17(double x)
		{
			if (Math.Asin(x) * Math.Acos(x) < Math.Atan(x))
			{
				System.Console.Out.WriteLine("Solved 17");
			}
		}

		// (1+Math.acos(x)) < Math.asin(x)
		[PexMethod(MaxConstraintSolverTime = 5, MaxRunsWithoutNewTests = 500)]
		public static void Benchmark18(double x)
		{
			if ((1 + Math.Acos(x)) < Math.Asin(x))
			{
				System.Console.Out.WriteLine("Solved 18");
			}
		}

		// 3*Math.acos(x) < Math.atan(y) + Math.asin(z)
		[PexMethod(MaxConstraintSolverTime = 5, MaxRunsWithoutNewTests = 500)]
		public static void Benchmark19(double x, double y, double z)
		{
			if (3 * Math.Acos(x) < Math.Atan(y) + Math.Asin(z))
			{
				System.Console.Out.WriteLine("Solved 19");
			}
		}

		// sin(sin((x*y)) < 0 && cos(2x) > 0.25
		[PexMethod(MaxConstraintSolverTime = 5, MaxRunsWithoutNewTests = 500)]
		public static void Benchmark20(double x, double y)
		{
			if (Math.Sin(Math.Sin(x * y)) < 0 && Math.Cos(2 * x) > 0.25)
			{
				System.Console.Out.WriteLine("Solved 20");
			}
		}

		// cos(x*y) < 0 && sin(2x) > 0.25
		[PexMethod(MaxConstraintSolverTime = 5, MaxRunsWithoutNewTests = 500)]
		public static void Benchmark21(double x, double y)
		{
			if (Math.Cos(x * y) < 0 && Math.Sin(2 * x) > 0.25)
			{
				System.Console.Out.WriteLine("Solved 21");
			}
		}

		// (sin_(cos_(($V1*$V2))) < cos_(sin_(($V2*$V3)))) & 
		// ((sin_((($V4*2.0)-$V2))/(cos_((($V6*2.0)+$V7))+1.0)) == (cos_((($V3*2.0)+$V1))/(sin_((($V4*2.0)+$V5))+1.0))))
		[PexMethod(MaxConstraintSolverTime = 5, MaxRunsWithoutNewTests = 500)]
		public static void Benchmark22(double x, double y, double z, double w, double v, 
			double t, double q)
		{
			if ((Math.Sin(Math.Cos(x * y)) < Math.Cos(Math.Sin(y * z))) && Math.Sin(w * 2.0 -
				 y) / (Math.Cos(t * 2.0 + q) + 1.0) == (Math.Cos(z * 2.0 + x) / (Math.Sin(w * 2.0
				 + v) + 1.0)))
			{
				System.Console.Out.WriteLine("Solved 22");
			}
		}

		// (sin(2x - y)/(cos(2y + x) + 1) = cos(2z + x)/(sin(2w + y) - 1) &
		// sin(x*y*z*w) > 0 &
		// cos(x*y*z*w) < 0
		[PexMethod(MaxConstraintSolverTime = 5, MaxRunsWithoutNewTests = 500)]
		public static void Benchmark23(double x, double y, double z, double w)
		{
			if (Math.Sin(2 * x - y) / (Math.Cos(2 * y + x) + 1) == Math.Cos(2 * z + x) / (Math
				.Sin(2 * w + y) - 1) && Math.Sin(x * y * z * w) > 0 && Math.Cos(x * y * z * w) <
				 0)
			{
				System.Console.Out.WriteLine("Solved 23");
			}
		}

		//  sin(cos(x*y)) < cos(sin(x*z)) &
		// (sin(2w - y)/(cos(2y + v) + 1) = cos(2z + x)/(sin(2w + v) - 1)
		[PexMethod(MaxConstraintSolverTime = 5, MaxRunsWithoutNewTests = 500)]
		public static void Benchmark25(double x, double y, double z, double w, double v)
		{
			if (Math.Sin(Math.Cos(x * y)) < Math.Cos(Math.Sin(x * z)) && (Math.Sin(2 * w - y)
				 / (Math.Cos(2 * y + v) + 1) == Math.Cos(2 * z + x) / (Math.Sin(2 * w + v) - 1)))
			{
				System.Console.Out.WriteLine("Solved 25");
			}
		}

		// sin(cos(x*y)) < cos(sin(x*z)) 
		// (sin(2w - y)/(cos(2y + v) + 1) = cos(2z + x)/(sin(2w + v) - 1) 
		// sin(x*y*z*w) > 0 && cos(x*y*z*w) < 0
		[PexMethod(MaxConstraintSolverTime = 5, MaxRunsWithoutNewTests = 500)]
		public static void Benchmark26(double x, double y, double z, double w, double v)
		{
			if (Math.Sin(Math.Cos(x * y)) < Math.Cos(Math.Sin(x * z)) && (Math.Sin(2 * w - y)
				 / (Math.Cos(2 * y + v) + 1) == Math.Cos(2 * z + x) / (Math.Sin(2 * w + v) - 1))
				 && Math.Sin(x * y * z * w) > 0 && Math.Cos(x * y * z * w) < 0)
			{
				System.Console.Out.WriteLine("Solved 26");
			}
		}

		// sin(x*cos(y*sin(z))) > cos(x*sin(y*cos(z))) && sin(cos(x*y)) < cos(sin(x*y))
		[PexMethod(MaxConstraintSolverTime = 5, MaxRunsWithoutNewTests = 500)]
		public static void Benchmark27(double x, double y, double z)
		{
			if (Math.Sin(x * Math.Cos(y * Math.Sin(z))) > Math.Cos(x * Math.Sin(y * Math.Cos(
				z))) && Math.Sin(Math.Cos(x * y)) < Math.Cos(Math.Sin(x * y)))
			{
				System.Console.Out.WriteLine("Solved 27");
			}
		}

		[PexMethod(MaxConstraintSolverTime = 5, MaxRunsWithoutNewTests = 500)]
		public static void Benchmark29(double x)
		{
			if (Math.Exp(x) > 5)
			{
				System.Console.Out.WriteLine("Solved 29");
			}
		}

		[PexMethod(MaxConstraintSolverTime = 5, MaxRunsWithoutNewTests = 500)]
		public static void Benchmark32(double x)
		{
			if (Math.Sqrt(x) > 5)
			{
				System.Console.Out.WriteLine("Solved 32");
			}
		}

		// sqrt(sin($V1)) > sqrt(cos($V1))
		[PexMethod(MaxConstraintSolverTime = 5, MaxRunsWithoutNewTests = 500)]
		public static void Benchmark33(double x)
		{
			if (Math.Sqrt(Math.Sin(x)) > Math.Sqrt(Math.Cos(x)))
			{
				System.Console.Out.WriteLine("Solved 33");
			}
		}

		// sqrt(sin($V1)) < sqrt(cos($V1))
		[PexMethod(MaxConstraintSolverTime = 5, MaxRunsWithoutNewTests = 500)]
		public static void Benchmark34(double x)
		{
			if (Math.Sqrt(Math.Sin(x)) < Math.Sqrt(Math.Cos(x)))
			{
				System.Console.Out.WriteLine("Solved 34");
			}
		}

		//  1.0/sqrt(sin($V1)) > sqrt(cos(exp($V2)))
		[PexMethod(MaxConstraintSolverTime = 5, MaxRunsWithoutNewTests = 500)]
		public static void Benchmark35(double x, double y)
		{
			if (1.0 / Math.Sqrt(Math.Sin(x)) > Math.Sqrt(Math.Cos(Math.Exp(y))))
			{
				System.Console.Out.WriteLine("Solved 35");
			}
		}

		// (atan2_($V1,$V2) == 1.0)
		[PexMethod(MaxConstraintSolverTime = 5, MaxRunsWithoutNewTests = 500)]
		public static void Benchmark38(double x, double y)
		{
			if (Math.Atan2(x, y) == 1.0)
			{
				System.Console.Out.WriteLine("Solved 38");
			}
		}

		// (pow_($V1,$V2) == 1.0)
		[PexMethod(MaxConstraintSolverTime = 5, MaxRunsWithoutNewTests = 500)]
		public static void Benchmark39(double x, double y)
		{
			if (Math.Pow(x, y) == 1.0)
			{
				System.Console.Out.WriteLine("Solved 39");
			}
		}

		// pow(x,2) == x + y
		[PexMethod(MaxConstraintSolverTime = 5, MaxRunsWithoutNewTests = 500)]
		public static void Benchmark40(double x, double y)
		{
			if (Math.Pow(x, 2) == x + y)
			{
				System.Console.Out.WriteLine("Solved 40");
			}
		}

		// pow(x,2) == x + y & x >= -1 & y <=  2
		[PexMethod(MaxConstraintSolverTime = 5, MaxRunsWithoutNewTests = 500)]
		public static void Benchmark41(double x, double y)
		{
			if (Math.Pow(x, 2) == x + y && x >= -1 && y <= 2)
			{
				System.Console.Out.WriteLine("Solved 41");
			}
		}

		// Math.pow(x,y) > Math.pow(y,x) & x > 1 & y <= 10
		[PexMethod(MaxConstraintSolverTime = 5, MaxRunsWithoutNewTests = 500)]
		public static void Benchmark42(double x, double y)
		{
			if (Math.Pow(x, y) > Math.Pow(y, x) && x > 1 && y <= 10)
			{
				System.Console.Out.WriteLine("Solved 42");
			}
		}

		// Math.pow(x,y) > Math.pow(y,x) && Math.exp(x,y) > Math.exp(y,x) && y < x ^ 2
		[PexMethod(MaxConstraintSolverTime = 5, MaxRunsWithoutNewTests = 500)]
		public static void Benchmark43(double x, double y)
		{
			if (Math.Pow(x, y) > Math.Pow(y, x) && Math.Exp(y) > Math.Exp(x) && y < Math.Pow(
				x, 2))
			{
				System.Console.Out.WriteLine("Solved 43");
			}
		}

		// Math.pow(x,y) > Math.pow(y,x) && Math.exp(x,y) < Math.exp(y,x)
		[PexMethod(MaxConstraintSolverTime = 5, MaxRunsWithoutNewTests = 500)]
		public static void Benchmark44(double x, double y)
		{
			if (Math.Pow(x, y) > Math.Pow(y, x) && Math.Exp(y) < Math.Exp(x))
			{
				System.Console.Out.WriteLine("Solved 44");
			}
		}

		// sqrt(Math.exp(x+y)) < Math.pow(z,x) && x > 0 && y > 1 && z > 1 && y <= x + 2
		[PexMethod(MaxConstraintSolverTime = 5, MaxRunsWithoutNewTests = 500)]
		public static void Benchmark45(double x, double y, double z)
		{
			if (Math.Sqrt(Math.Exp(x + y)) < Math.Pow(z, x) && x > 0 && y > 1 && z > 1 && y <=
				 x + 2)
			{
				System.Console.Out.WriteLine("Solved 45");
			}
		}

		// Math.sqrt(e^(x + z)) < z^x && x > 0 && y > 1 && z > 1 && y < 1 && y < x + 2 && w = x + 2
		[PexMethod(MaxConstraintSolverTime = 5, MaxRunsWithoutNewTests = 500)]
		public static void Benchmark46(double x, double y, double z, double w)
		{
			if (Math.Sqrt(Math.Pow(Math.E, (x + z))) < Math.Pow(z, x) && x > 0 && y > 1 && z 
				> 1 && y < 1 && y < x + 2 && w == x + 2)
			{
				System.Console.Out.WriteLine("Solved 46");
			}
		}

		// e ^ (x + y) == e ^ z 
		[PexMethod(MaxConstraintSolverTime = 5, MaxRunsWithoutNewTests = 500)]
		public static void Benchmark47(double x, double y, double z)
		{
			if (Math.Exp(x + y) == Math.Exp(z))
			{
				System.Console.Out.WriteLine("Solved 47");
			}
		}

		// x + y != z
		[PexMethod(MaxConstraintSolverTime = 5, MaxRunsWithoutNewTests = 500)]
		public static void Benchmark48(double x, double y, double z)
		{
			if (x + y != z)
			{
				System.Console.Out.WriteLine("Solved 48");
			}
		}

		//x^2 + 3*Math.sqrt(y) < x*y && x < y ^ 2 && x + y < 50 //556 possible integer solutions
		[PexMethod(MaxConstraintSolverTime = 5, MaxRunsWithoutNewTests = 500)]
		public static void Benchmark49(double x, double y)
		{
			if (Math.Pow(x, 2) + 3 * Math.Sqrt(y) < x * y && x < Math.Pow(y, 2) && x + y < 50)
			{
				System.Console.Out.WriteLine("Solved 49");
			}
		}

		//x^2 + 3*Math.sqrt(y) < x*y && x < y ^ 2 && x + y < 50 && x = -13 + y //18 possible integer solutions
		[PexMethod(MaxConstraintSolverTime = 5, MaxRunsWithoutNewTests = 500)]
		public static void Benchmark50(double x, double y)
		{
			if (Math.Pow(x, 2) + 3 * Math.Sqrt(y) < x * y && x < Math.Pow(y, 2) && x + y < 50
				 && x == -13 + y)
			{
				System.Console.Out.WriteLine("Solved 50");
			}
		}

		//x ^ tan(y) + z < x * Math.atan(z) && sin(y) + cos(y) + tan(y) >= x - z
		[PexMethod(MaxConstraintSolverTime = 5, MaxRunsWithoutNewTests = 500)]
		public static void Benchmark52(double x, double y, double z)
		{
			if (Math.Pow(x, Math.Tan(y)) + z < x * Math.Atan(z) && Math.Sin(y) + Math.Cos(y) 
				+ Math.Tan(y) >= x - z)
			{
				System.Console.Out.WriteLine("Solved 52");
			}
		}

		//x ^ Math.tan(y) + z < x * Math.atan(z) && Math.sin(y) + cos(y) + Math.tan(y) >= x - z && Math.atan(x) + Math.atan(y) > y
		[PexMethod(MaxConstraintSolverTime = 5, MaxRunsWithoutNewTests = 500)]
		public static void Benchmark53(double x, double y, double z)
		{
			if (Math.Pow(x, Math.Tan(y)) + z < x * Math.Atan(z) && Math.Sin(y) + Math.Cos(y) 
				+ Math.Tan(y) >= x - z && Math.Atan(x) + Math.Atan(y) > y)
			{
				System.Console.Out.WriteLine("Solved 53");
			}
		}

		//x * y + Math.atan(z) * Math.sin(w*t) > x/y + z + Math.tan(w+t)
		[PexMethod(MaxConstraintSolverTime = 5, MaxRunsWithoutNewTests = 500)]
		public static void Benchmark56(double x, double y, double z, double w, double t)
		{
			if (x * y + Math.Atan(z) * Math.Sin(w * t) > x / y + z + Math.Tan(w + t))
			{
				System.Console.Out.WriteLine("Solved 56");
			}
		}

		//x + y > z / w && Math.sqrt(x) > z / y && z*2 + w*3 + x*7 < Math.pow(y,6) && z + w > x + y && w < x/y
/* Disabled because it sends Pex into an infinite loop (pdinges)
		[PexMethod(MaxConstraintSolverTime = 5, MaxRunsWithoutNewTests = 500)]
		public static void Benchmark61(double x, double y, double z, double w)
		{
			if (x + y > z / w && Math.Sqrt(x) > z / y && z * 2 + w * 3 + x * 7 < Math.Pow(y, 
				6) && z + w > x + y && w < x / y)
			{
				System.Console.Out.WriteLine("Solved 61");
			}
		}
*/

		//x + y > z / w && Math.sqrt(x) > z / y && z*2 + w*3 + x*7 < Math.pow(y,6) && z + w > x + y && w < x/y && x > (w+y-z)
/* Disabled because it sends Pex into an infinite loop (pdinges)
		[PexMethod(MaxConstraintSolverTime = 5, MaxRunsWithoutNewTests = 500)]
		public static void Benchmark62(double x, double y, double z, double w)
		{
			if (x + y > z / w && Math.Sqrt(x) > z / y && z * 2 + w * 3 + x * 7 < Math.Pow(y, 
				6) && z + w > x + y && w < x / y && x > (w + y - z))
			{
				System.Console.Out.WriteLine("Solved 62");
			}
		}
*/

		//Math.sin(a) > Math.sin(b) > Math.sin(c) > Math.sin(d) > Math.sin(e) > Math.sin(f) > Math.sin(g) > Math.sin(h) > Math.sin(i) > Math.sin(j) > Math.sin(k) > Math.sin(l) > Math.sin(m) > Math.sin(n) > Math.sin(o) > Math.sin(p) > Math.sin(q) > Math.sin(r) > Math.sin(s) > Math.sin(t) > Math.sin(u) > Math.sin(v) > Math.sin(x) > Math.sin(z)
		[PexMethod(MaxConstraintSolverTime = 5, MaxRunsWithoutNewTests = 500)]
		public static void Benchmark70(double a, double b, double c, double d, double e, 
			double f, double g, double h, double i, double j, double k, double l, double m, 
			double n, double o, double p, double q, double r, double s, double t, double u, 
			double v, double x, double z)
		{
			if (Math.Sin(a) > Math.Sin(b) && Math.Sin(b) > Math.Sin(c) && Math.Sin(c) > Math.
				Sin(d) && Math.Sin(d) > Math.Sin(e) && Math.Sin(e) > Math.Sin(f) && Math.Sin(f) 
				> Math.Sin(g) && Math.Sin(g) > Math.Sin(h) && Math.Sin(h) > Math.Sin(i) && Math.
				Sin(i) > Math.Sin(j) && Math.Sin(j) > Math.Sin(k) && Math.Sin(k) > Math.Sin(l) &&
				 Math.Sin(l) > Math.Sin(m) && Math.Sin(m) > Math.Sin(n) && Math.Sin(n) > Math.Sin
				(o) && Math.Sin(o) > Math.Sin(p) && Math.Sin(p) > Math.Sin(q) && Math.Sin(q) > Math
				.Sin(r) && Math.Sin(r) > Math.Sin(s) && Math.Sin(s) > Math.Sin(t) && Math.Sin(t)
				 > Math.Sin(u) && Math.Sin(u) > Math.Sin(v) && Math.Sin(v) > Math.Sin(x) && Math
				.Sin(x) > Math.Sin(z))
			{
				System.Console.Out.WriteLine("Solved 70");
			}
		}

		[PexMethod(MaxConstraintSolverTime = 5, MaxRunsWithoutNewTests = 500)]
		public static void Benchmark71(double a, double b, double c, double d, double e, 
			double f, double g, double h, double i, double j, double k, double l)
		{
			if (Math.Sin(a) > Math.Sin(b) && Math.Sin(b) > Math.Sin(c) && Math.Sin(c) > Math.
				Sin(d) && Math.Sin(d) > Math.Sin(e) && Math.Sin(e) > Math.Sin(f) && Math.Sin(f) 
				> Math.Sin(g) && Math.Sin(g) > Math.Sin(h) && Math.Sin(h) > Math.Sin(i) && Math.
				Sin(i) > Math.Sin(j) && Math.Sin(j) > Math.Sin(k) && Math.Sin(k) > Math.Sin(l))
			{
				System.Console.Out.WriteLine("Solved 71");
			}
		}

		[PexMethod(MaxConstraintSolverTime = 5, MaxRunsWithoutNewTests = 500)]
		public static void Benchmark72(double a, double b, double c, double d, double e, 
			double f, double g)
		{
			if (Math.Sin(a) > Math.Sin(b) && Math.Sin(b) > Math.Sin(c) && Math.Sin(c) > Math.
				Sin(d) && Math.Sin(d) > Math.Sin(e) && Math.Sin(e) > Math.Sin(f) && Math.Sin(f) 
				> Math.Sin(g))
			{
				System.Console.Out.WriteLine("Solved 72");
			}
		}

		[PexMethod(MaxConstraintSolverTime = 5, MaxRunsWithoutNewTests = 500)]
		public static void Benchmark73(double a, double b, double c, double d, double e, 
			double f, double g, double h, double i, double j)
		{
			if (Math.Sin(a) > Math.Sin(b) && Math.Sin(b) > Math.Sin(c) && Math.Sin(c) > Math.
				Sin(d) && Math.Sin(d) > Math.Sin(e) && Math.Sin(e) > Math.Sin(f) && Math.Sin(f) 
				> Math.Sin(g) && Math.Sin(g) > Math.Sin(h) && Math.Sin(h) > Math.Sin(i) && Math.
				Sin(i) > Math.Sin(j))
			{
				System.Console.Out.WriteLine("Solved 73");
			}
		}

		[PexMethod(MaxConstraintSolverTime = 5, MaxRunsWithoutNewTests = 500)]
		public static void Benchmark74(double a, double b, double c, double d, double e, 
			double f, double g, double h)
		{
			if (Math.Sin(a) > Math.Sin(b) && Math.Sin(b) > Math.Sin(c) && Math.Sin(c) > Math.
				Sin(d) && Math.Sin(d) > Math.Sin(e) && Math.Sin(e) > Math.Sin(f) && Math.Sin(f) 
				> Math.Sin(g) && Math.Sin(g) > Math.Sin(h))
			{
				System.Console.Out.WriteLine("Solved 74");
			}
		}

		[PexMethod(MaxConstraintSolverTime = 5, MaxRunsWithoutNewTests = 500)]
		public static void Benchmark75(double a, double b, double c, double d, double e, 
			double f, double g, double h, double i)
		{
			if (Math.Sin(a) > Math.Sin(b) && Math.Sin(b) > Math.Sin(c) && Math.Sin(c) > Math.
				Sin(d) && Math.Sin(d) > Math.Sin(e) && Math.Sin(e) > Math.Sin(f) && Math.Sin(f) 
				> Math.Sin(g) && Math.Sin(g) > Math.Sin(h) && Math.Sin(h) > Math.Sin(i))
			{
				System.Console.Out.WriteLine("Solved 75");
			}
		}

		[PexMethod(MaxConstraintSolverTime = 5, MaxRunsWithoutNewTests = 500)]
		public static void Benchmark76(double a, double b, double c, double d, double e, 
			double f, double g, double h, double i)
		{
			if (a > b && b > c && c > d && d > e && e > f && f > g && g > h && h > i)
			{
				System.Console.Out.WriteLine("Solved 76");
			}
		}

		[PexMethod(MaxConstraintSolverTime = 5, MaxRunsWithoutNewTests = 500)]
		public static void Benchmark77(double a, double b, double c, double d, double e, 
			double f, double g, double h, double i, double j)
		{
			if (a > b && b > c && c > d && d > e && e > f && f > g && g > h && h > i && i > j)
			{
				System.Console.Out.WriteLine("Solved 77");
			}
		}

		//(0.0 == (pow_((($V1*sin_(((($V2*0.017453292519943295)-($V3*0.017453292519943295))+(((((((pow_($V4,2.0)/(sin_(($V5*0.017453292519943295))/cos_(($V5*0.017453292519943295))))/68443.0)*0.0)/$V4)*-1.0)*$V1)/((pow_($V1,2.0)/(sin_(($V5*0.017453292519943295))/cos_(($V5*0.017453292519943295))))/68443.0)))))-($V4*0.0)),2.0)+pow_((($V1*cos_(((($V2*0.017453292519943295)-($V3*0.017453292519943295))+(((((((pow_($V4,2.0)/(sin_(($V5*0.017453292519943295))/cos_(($V5*0.017453292519943295))))/68443.0)*0.0)/$V4)*-1.0)*$V1)/((pow_($V1,2.0)/(sin_(($V5*0.017453292519943295))/cos_(($V5*0.017453292519943295))))/68443.0)))))-($V4*1.0)),2.0)))
		//AND(AND(,($V6 != 0)),($V8 != 0)))
		[PexMethod(MaxConstraintSolverTime = 5, MaxRunsWithoutNewTests = 500)]
		public static void Benchmark78(double a, double b, double c, double d, double e, 
			int f, int g, int h)
		{
			if ((0.0 == (Math.Pow(((a * Math.Sin(((b * 0.017453292519943295 - c * 0.017453292519943295
				) + (((((((Math.Pow(d, 2.0) / (Math.Sin(e * 0.017453292519943295) / Math.Cos(e *
				 0.017453292519943295))) / 68443.0) * 0.0) / d) * -1.0) * a) / ((Math.Pow(a, 2.0
				) / (Math.Sin((e * 0.017453292519943295)) / Math.Cos((e * 0.017453292519943295))
				)) / 68443.0))))) - (d * 0.0)), 2.0) + Math.Pow(((a * Math.Cos((((b * 0.017453292519943295
				) - (c * 0.017453292519943295)) + (((((((Math.Pow(d, 2.0) / (Math.Sin((e * 0.017453292519943295
				)) / Math.Cos((e * 0.017453292519943295)))) / 68443.0) * 0.0) / d) * -1.0) * a) 
				/ ((Math.Pow(a, 2.0) / (Math.Sin((e * 0.017453292519943295)) / Math.Cos((e * 0.017453292519943295
				)))) / 68443.0))))) - d * 1.0), 2.0))) && f != 0 && h != 0)
			{
				System.Console.Out.WriteLine("Solved 78");
			}
		}

		//AND((0.0 == (pow_((($V84*sin_(((0.017453292519943295*$V85)-(0.017453292519943295*$V86))))-(0.0*$V87)),2.0)+pow_(($V84*cos_((((0.017453292519943295*$V85)-(0.017453292519943295*$V86))+0.0))),2.0))),($V82 != 0))
		[PexMethod(MaxConstraintSolverTime = 5, MaxRunsWithoutNewTests = 500)]
		public static void Benchmark79(double a, double b, double c, double d, int e)
		{
			if ((0.0 == (Math.Pow(((a * Math.Sin(((0.017453292519943295 * b) - (0.017453292519943295
				 * c)))) - (0.0 * d)), 2.0) + Math.Pow((a * Math.Cos((((0.017453292519943295 * b
				) - (0.017453292519943295 * c)) + 0.0))), 2.0))) && e != 0)
			{
				System.Console.Out.WriteLine("Solved 79");
			}
		}

		//(1.5 - x1 * (1 - x2)) == 0
		[PexMethod(MaxConstraintSolverTime = 5, MaxRunsWithoutNewTests = 500)]
		public static void Benchmark80(double a, double b)
		{
			if ((1.5 - a * (1 - b)) == 0)
			{
				System.Console.Out.WriteLine("Solved 80");
			}
		}

		//(-13 + x1 + ((5 - x2) * x2 - 2) * x2) + (-29 + x1 + ((x2 + 1) * x2 - 14) * x2) == 0
		[PexMethod(MaxConstraintSolverTime = 5, MaxRunsWithoutNewTests = 500)]
		public static void Benchmark81(double a, double b)
		{
			if ((-13 + a + ((5 - b) * b - 2) * b) + (-29 + a + ((b + 1) * b - 14) * b) == 0)
			{
				System.Console.Out.WriteLine("Solved 81");
			}
		}

		// (Math.Pow(10, 4) * x1 * x2 - 1) == 0 && (Math.Pow(Math.E, -x1) + Math.Pow(Math.E, -x2) - 1.0001) == 0
		[PexMethod(MaxConstraintSolverTime = 5, MaxRunsWithoutNewTests = 500)]
		public static void Benchmark82(double a, double b)
		{
			if ((Math.Pow(10, 4) * a * b - 1) == 0 && (Math.Pow(Math.E, -a) + Math.Pow(Math.E
				, -b) - 1.0001) == 0)
			{
				System.Console.Out.WriteLine("Solved 82");
			}
		}

		// Math.Pow((1 - x1), 2) + 100 * (Math.Pow((x2 - x1 * x1), 2)) == 0
		[PexMethod(MaxConstraintSolverTime = 5, MaxRunsWithoutNewTests = 500)]
		public static void Benchmark83(double a, double b)
		{
			if (Math.Pow((1 - a), 2) + 100 * (Math.Pow((b - a * a), 2)) == 0)
			{
				System.Console.Out.WriteLine("Solved 83");
			}
		}

		//(10 * (x2 - x1 * x1)) == 0 && (1 - x1) == 0 && (Math.sqrt(90) * (x4 - x3 * x3)) == 0 && (1 - x3) == 0 && (Math.sqrt(10) * (x2 + x4 - 2)) == 0 && (Math.Pow(10, -0.5) * (x2 - x4)) == 0
		[PexMethod(MaxConstraintSolverTime = 5, MaxRunsWithoutNewTests = 500)]
		public static void Benchmark84(double a, double b, double c, double d)
		{
			if ((10 * (b - a * a)) == 0 && (1 - a) == 0 && (Math.Sqrt(90) * (d - c * c)) == 0
				 && (1 - c) == 0 && (Math.Sqrt(10) * (b + d - 2)) == 0 && (Math.Pow(10, -0.5) * 
				(b - d)) == 0)
			{
				System.Console.Out.WriteLine("Solved 84");
			}
		}

		[PexMethod(MaxConstraintSolverTime = 5, MaxRunsWithoutNewTests = 500)]
		public static void Benchmark91(double x, double y)
		{
			if (Math.Sin(x) == -Math.Sin(y) && Math.Sin(x) > 0)
			{
				System.Console.Out.WriteLine("Solved 91");
			}
		}
	}
}
