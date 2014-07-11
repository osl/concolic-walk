/*
 * Automatically converted from Java to C# using Sharpen.
 */

using Microsoft.VisualStudio.TestTools.UnitTesting;
using Microsoft.Pex.Framework;

namespace Concolic
{
    [TestClass]
    public partial class DART
	{
		// taken from the original DART paper
        [PexMethod(MaxConstraintSolverTime = 5, MaxRunsWithoutNewTests = 500)]
        public static void Test(int x, int y)
		{
			if (x * x * x > 0)
			{
				if (x > 0 && y == 10)
				{
					Abort();
				}
			}
			else
			{
				if (x > 0 && y == 20)
				{
					Abort();
				}
			}
		}

		public static void Abort()
		{
            throw new AssertFailedException();
		}
	}
}
