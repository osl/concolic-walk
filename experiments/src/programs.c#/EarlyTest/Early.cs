/*
 * Automatically converted from Java to C# using Sharpen.
 */

using Microsoft.VisualStudio.TestTools.UnitTesting;
using Microsoft.Pex.Framework;

[TestClass]
public partial class Early
{
    [PexMethod(MaxConstraintSolverTime = 5, MaxRunsWithoutNewTests = 500)]
    public static void CommitEarly(int a, int b)
	{
		int c = a * b;
		if (b > 2 && b == c)
		{
			System.Console.Out.WriteLine("Solved early commitment");
		}
	}
}
