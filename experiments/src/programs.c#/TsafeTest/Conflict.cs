/*
 * Automatically converted from Java to C# using Sharpen.
 */

using System;
using Tsafe;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using Microsoft.Pex.Framework;

namespace Tsafe
{
    [TestClass]
    public partial class Conflict
	{
		private const double degToRad = Math.PI / 180.0;

		private const double g = 68443.0;

		//import gov.nasa.jpf.continuity.SymbolicRealVars;
        [PexMethod(MaxConstraintSolverTime = 5, MaxRunsWithoutNewTests = 500)]
        public static double Snippet(double psi1, double vA, double vC, double xC0, double
			 yC0, double psiC, double bank_ang)
		{
			string PATH = string.Empty;
			double dmin = 999;
			double dmst = 2;
			double psiA = psi1 * degToRad;
			int signA = 1;
			int signC = 1;
			if (psiA < 0)
			{
				PATH += "psiA<0\n";
				signA = -1;
			}
			else
			{
				PATH += "psiA>=0\n";
			}
			double rA = Math.Pow(vA, 2.0) / Math.Tan(bank_ang * degToRad) / g;
			double rC = Math.Pow(vC, 2.0) / Math.Tan(bank_ang * degToRad) / g;
			double t1 = Math.Abs(psiA) * rA / vA;
			double dpsiC = signC * t1 * vC / rC;
			double xA = signA * rA * (1 - Math.Cos(psiA));
			double yA = rA * signA * Math.Sin(psiA);
			double xC = xC0 + signC * rC * (Math.Cos(psiC) - Math.Cos(psiC + dpsiC));
			double yC = yC0 - signC * rC * (Math.Sin(psiC) - Math.Sin(psiC + dpsiC));
			double xd1 = xC - xA;
			double yd1 = yC - yA;
			double d = Math.Sqrt(Math.Pow(xd1, 2.0) + Math.Pow(yd1, 2.0));
			double minsep;
			// min sep in turn                        
			if (d < dmin)
			{
				PATH += "d < dmin\n";
				dmin = d;
			}
			else
			{
				PATH += "d >= dmin\n";
			}
			if (dmin < dmst)
			{
				PATH += "dmin < dmst\n";
				minsep = dmin;
			}
			else
			{
				PATH += "dmin >= dmst\n";
				minsep = dmst;
			}
			System.Console.Out.WriteLine(">>> PATH: " + PATH);
			return minsep;
		}

		public static void Main(string[] args)
		{
			//    double aPsi = SymbolicRealVars.getSymbolicReal(0.0, 360.0, "aPsi");
			//    double aV = SymbolicRealVars.getSymbolicReal(-100.0, 100.0, "av");
			//    double c1X = SymbolicRealVars.getSymbolicReal(-100.0, 100.0, "c1X");
			//    double c1Y = SymbolicRealVars.getSymbolicReal(-100.0, 100.0, "c1Y");
			//    double c1Psi = SymbolicRealVars.getSymbolicReal(-100.0, 100.0, "c1Psi");
			//    double c1V = SymbolicRealVars.getSymbolicReal(-100.0, 100.0, "c1v");
			//    double cpA0_1_bankAng = SymbolicRealVars.getSymbolicReal(0.0, 50.0, "cpA0_1_bankAng");
			//    if ((aV > 0.0) && (cpA0_1_bankAng > 0.0)) {
			//      double minsep_sec_result = snippet(aPsi, aV, c1V, c1X, c1Y, c1Psi, cpA0_1_bankAng);
			double minsep_sec_result = Snippet(0, 0, 0, 0, 0, 0, 0);
		}
		//      SymbolicRealVars.notePathFunction("minsep_sec_result");
		//    }
	}
}
