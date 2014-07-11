/*
 * Automatically converted from Java to C# using Sharpen.
 */

using Microsoft.VisualStudio.TestTools.UnitTesting;
using Microsoft.Pex.Framework;

namespace Concolic
{
    [TestClass]
    public partial class PowExample
	{
        [PexMethod(MaxConstraintSolverTime = 5, MaxRunsWithoutNewTests = 500)]
        public static void Test(int x, int y)
		{
			int path = 0;
			if (x > 0)
			{
				// Changed from
				// if (y == Math.pow(x, 2.0)) {
				// to avoid double--int conversion (pdinges)
				if (y == x * x)
				{
					System.Console.Out.WriteLine("Solved S0");
					path = 1;
				}
				else
				{
					System.Console.Out.WriteLine("Solved S1");
					path = 2;
				}
				if (y > 8)
				{
					//if (x > 1 && y > 3) {
					if (path == 1)
					{
						System.Console.Out.WriteLine("Solved S0;S3");
					}
					if (path == 2)
					{
						System.Console.Out.WriteLine("Solved S1;S3");
					}
				}
				else
				{
					if (path == 1)
					{
						System.Console.Out.WriteLine("Solved S0;S4");
					}
					if (path == 2)
					{
						System.Console.Out.WriteLine("Solved S1;S4");
					}
				}
			}
		}

		/// <param name="args"></param>
		public static void Main(string[] args)
		{
			// TODO Auto-generated method stub
			Test(2, 10);
		}
	}
}
