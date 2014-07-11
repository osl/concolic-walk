/*
 * Automatically converted from Java to C# using Sharpen.
 */

using Concolic;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using Microsoft.Pex.Framework;

namespace Concolic
{
    [TestClass]
    public partial class TestStatCalc
	{
        [PexMethod(MaxConstraintSolverTime = 5, MaxRunsWithoutNewTests = 500)]
        public static void Run(int val)
		{
			System.Console.Out.WriteLine("adding value");
			StatCalculator.AddValue(val);
			StatCalculator.AddValue(val);
			StatCalculator.AddValue(val);
			StatCalculator.AddValue(val);
			//stat.addValue(val);
			if (StatCalculator.GetMedian() == 3)
			{
				System.Console.Out.WriteLine("median value is 3");
			}
			else
			{
				System.Console.Out.WriteLine("median value is not 3");
			}
			if (StatCalculator.GetStandardDeviation() <= 0.82915619758885)
			{
				System.Console.Out.WriteLine("std deviation is .10");
			}
			else
			{
				System.Console.Out.WriteLine("std deviation not found");
			}
		}

		public static void Main(string[] args)
		{
			Run(4);
		}
	}
}
