/*
 * Automatically converted from Java to C# using Sharpen.
 */

using System;
using System.Collections;
using Concolic;

namespace Concolic
{
	/// <summary>This class serves as a way to calculate the median of a list of values.</summary>
	/// <remarks>
	/// This class serves as a way to calculate the median of a list of values.  It
	/// is not threadsafe.
	/// </remarks>
	[System.Serializable]
	public class StatCalculator
	{
		internal static ArrayList values = new ArrayList();

		internal static double sum = 0;

		internal static double sumOfSquares = 0;

		internal static double mean = 0;

		internal static double deviation = 0;

		internal static int count = 0;

		public static void Clear()
		{
			values.Clear();
			sum = 0;
			sumOfSquares = 0;
			mean = 0;
			deviation = 0;
			count = 0;
		}


		public static double GetMedian()
		{
			return (double)values[values.Count / 2];
		}

		public static double GetMean()
		{
			return mean;
		}

		public static double GetStandardDeviation()
		{
			return deviation;
		}

		public static double GetMin()
		{
            return (double)values[0];
		}

		public static double GetMax()
		{
            return (double)values[count - 1];
		}

		public static int GetCount()
		{
			return count;
		}

		public static void AddValue(double val)
		{
			//	if((double) val.intValue() > 10) {
			//		System.out.println("10");
			//	}
			int index = values.BinarySearch(val);
			if (index >= 0 && index < values.Count)
			{
				values.Insert(index, val);
			}
			else
			{
				if (index == values.Count || values.Count == 0)
				{
					values.Add(val);
				}
				else
				{
					values.Insert((index * (-1)) - 1, val);
				}
			}
			count++;
			System.Console.Out.WriteLine("stat ");
			double currentVal = val;
			sum += currentVal;
			sumOfSquares += currentVal * currentVal;
			mean = sum / count;
			deviation = Math.Sqrt((sumOfSquares / count) - (mean * mean));
		}
	}
}
