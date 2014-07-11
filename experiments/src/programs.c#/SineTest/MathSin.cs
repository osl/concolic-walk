/*
 * Automatically converted from Java to C# using Sharpen.
 */

using System;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using Microsoft.Pex.Framework;

namespace Concolic
{
    [TestClass]
    public partial class MathSin
	{
		public const int IEEE_MAX = 2047;

		public const int IEEE_BIAS = 1023;

		public const int IEEE_MANT = 52;

		public const double sixth = 1.0 / 6.0;

		public const double half = 1.0 / 2.0;

		public const double mag52 = 1024.0 * 1024.0 * 1024.0 * 1024.0 * 1024.0 * 4.0;

		public const double magic = 1024.0 * 1024.0 * 1024.0 * 1024.0 * 1024.0 * 4.0;

		public static readonly double[] P = new double[] { -0.64462136749e-9, 0.5688203332688e-7
			, -0.359880911703133e-5, 0.16044116846982831e-3, -0.468175413106023168e-2, 0.7969262624561800806e-1
			, -0.64596409750621907082, 0.15707963267948963959e1 };

		public static double _2_pi_hi;

		public static double _2_pi_lo;

		public static double pi2_lo;

		public static double pi2_hi_hi;

		public static double pi2_hi_lo;

		public static double pi2_lo_hi;

		public static double pi2_lo_lo;

		public static double pi2_hi;

		public static double pi2_lo2;

		public const double X_EPS = 1e-4;

		// = 2.0/Math.PI ;
		// = Math.PI/2;
		//========================================================
		public virtual double Mysin(double x)
		{
			double retval;
			double x_org;
			double x2;
			int md_b_sign;
			int xexp;
			int sign = 0;
			int md_b_m1;
			int md_b_m2;
			// convert into the different parts
			//
			// x is symbolic.  We have to call
			// {@code doubleToRawLongBits} via the helper to build
			// a {@code FunctionExpressio} in the
			// {@code ConcreteExecutionListener}.
			//long l_x = Double.doubleToRawLongBits(x);
			long l_x = BitConverter.DoubleToInt64Bits(x);
			//   <32> <20> <11> <1>
			// sign
			md_b_sign = (int)((l_x >> 63) & 1);
			// exponent:
			xexp = (int)((l_x >> 52) & unchecked((int)(0x7FF)));
			int xexp0 = (int)((l_x >> 52) & unchecked((int)(0x7FF)));
			md_b_m2 = (int)(l_x & unchecked((int)(0xFFFFFFFF)));
			md_b_m1 = (int)((l_x >> 31) & unchecked((int)(0xFFFFF)));
			System.Console.Out.WriteLine("input=" + x);
			//System.out.println("raw="+l_x);
			System.Console.Out.WriteLine("sign=" + md_b_sign);
			System.Console.Out.WriteLine("exp=" + xexp);
			System.Console.Out.WriteLine("exp_raw=" + xexp0);
			System.Console.Out.WriteLine("exp (unbiased)=" + (xexp - IEEE_BIAS));
			System.Console.Out.WriteLine("m1=" + md_b_m1);
			System.Console.Out.WriteLine("m2=" + md_b_m2);
			//----------end-of-conversion------------
			if (IEEE_MAX == xexp)
			{
				System.Console.Out.WriteLine("NAN-on-INF");
				if (md_b_m1 > 0 || md_b_m2 > 0)
				{
					System.Console.Out.WriteLine("unnormalized");
					retval = x;
				}
				else
				{
					System.Console.Out.WriteLine("NaN");
					retval = double.NaN;
				}
				return retval;
			}
			else
			{
				if (0 == xexp)
				{
					System.Console.Out.WriteLine("+-0, denormal");
					if (md_b_m1 > 0 || md_b_m2 > 0)
					{
						System.Console.Out.WriteLine("denormal");
						x2 = x * x;
						return x - x2;
					}
					else
					{
						System.Console.Out.WriteLine("+-0");
						return x;
					}
				}
				else
				{
					if (xexp <= (IEEE_BIAS - IEEE_MANT - 2))
					{
						System.Console.Out.WriteLine("very small");
						return x;
					}
					else
					{
						if (xexp <= (IEEE_BIAS - IEEE_MANT / 4))
						{
							System.Console.Out.WriteLine("small");
							return x * (1.0 - x * x * sixth);
						}
					}
				}
			}
			if (md_b_sign == 1)
			{
				x = -x;
				sign = 1;
			}
			x_org = x;
			System.Console.Out.WriteLine("CURRENT\n\n");
			if (xexp < IEEE_BIAS)
			{
				System.Console.Out.WriteLine("less-than pi/2");
			}
			else
			{
				if (xexp <= (IEEE_BIAS + IEEE_MANT))
				{
					System.Console.Out.WriteLine("must bring into range...");
					double xm;
					double x3 = 0.0;
					double x4 = 0.0;
					double x5 = 0.0;
					double x6 = 0.0;
					double a1 = 0.0;
					double a2 = 0.0;
					int bot2;
					double xn_d;
					double md;
					// should be bit union
					xm = Math.Floor(x * _2_pi_hi + half);
					System.Console.Out.WriteLine("xm (int) = " + xm);
					xn_d = xm + mag52;
					System.Console.Out.WriteLine("xn_d = " + xn_d);
					// C: bot2 = xn.b.m2 & 3u;
					// bot2 is the lower 3 bits of M2
					long l_xn = BitConverter.DoubleToInt64Bits(xn_d);
					int xn_m2 = (int)(l_xn & unchecked((int)(0xFFFFFFFF)));
					bot2 = xn_m2 & 3;
					System.Console.Out.WriteLine("bot2 = " + bot2);
					//>>>>>>>>>>>>>>>>>>>>>                split(a1,a2,xm);
					System.Console.Out.WriteLine("splitting: " + xm);
					long l_x1 = BitConverter.DoubleToInt64Bits(xm);
					//   <32> <20> <11> <1>
					// sign
					int md_b_sign1 = (int)((l_x1 >> 63) & 1);
					// exponent:
					int xexp1 = (int)((l_x1 >> 52) & unchecked((int)(0x7FF)));
					int md_b_m21 = (int)(l_x1 & unchecked((int)(0xFFFFFFFF)));
					int md_b_m11 = (int)((l_x1 >> 31) & unchecked((int)(0xFFFFF)));
					System.Console.Out.WriteLine("raw=" + l_x1);
					System.Console.Out.WriteLine("sign=" + md_b_sign1);
					System.Console.Out.WriteLine("exp=" + xexp1);
					System.Console.Out.WriteLine("exp (unbiased)=" + (xexp1 - IEEE_BIAS));
					System.Console.Out.WriteLine("m1=" + md_b_m11);
					System.Console.Out.WriteLine("m2=" + md_b_m21);
					// 	md.b.m2 &= 0xfc000000u;		\
					// md_b_m2 = (int)(l_x1 & 0xFFFFFFFF);
					l_x1 &= unchecked((long)(0xFC000000L));
					a1 = BitConverter.DoubleToInt64Bits(l_x1);
					// 	lo = (v) - hi;	/* bot 26 bits */
					a2 = xm - a1;
					System.Console.Out.WriteLine("in split: a1=" + a1);
					System.Console.Out.WriteLine("in split: a2=" + a2);
					//>>>>>>>>>>> exactmul2(x3,x4, xm,a1,a2, pi2_hi,pi2_hi_hi,pi2_hi_lo);
					x3 = (xm) * (pi2_hi);
					x4 = (((a1 * pi2_hi_hi - x3) + a1 * pi2_hi_lo) + pi2_hi_hi * a2) + a2 * pi2_hi_lo;
					//>>>>>>>>>>>  exactmul2(x5,x6, xm,a1,a2, pi2_lo,pi2_lo_hi,pi2_lo_lo);
					x5 = (xm) * (pi2_lo);
					x6 = (((a1 * pi2_lo_hi - x5) + a1 * pi2_lo_lo) + pi2_lo_hi * a2) + a2 * pi2_lo_lo;
					x = ((((x - x3) - x4) - x5) - x6) - xm * pi2_lo2;
					switch (bot2)
					{
						case 0:
						{
							//++++++++++++++++++++++++++++++++++++++++++++++
							if (x < 0.0)
							{
								x = -x;
								//sign ^= 1;
								if (sign == 1)
								{
									sign = 0;
								}
								else
								{
									sign = 1;
								}
							}
							break;
						}

						case 1:
						{
							if (x < 0.0)
							{
								x = pi2_hi + x;
							}
							else
							{
								x = pi2_hi - x;
							}
							break;
						}

						case 2:
						{
							if (x < 0.0)
							{
								x = -x;
							}
							else
							{
								//sign ^= 1;
								if (sign == 1)
								{
									sign = 0;
								}
								else
								{
									sign = 1;
								}
							}
							break;
						}

						case 3:
						{
							// sign ^= 1;
							if (sign == 1)
							{
								sign = 0;
							}
							else
							{
								sign = 1;
							}
							if (x < 0.0)
							{
								x = pi2_hi + x;
							}
							else
							{
								x = pi2_hi - x;
							}
							break;
						}

						default:
						{
							break;
						}
					}
				}
				else
				{
					System.Console.Out.WriteLine("T_LOSS ");
					retval = 0.0;
					if (sign == 1)
					{
						retval = -retval;
					}
					return retval;
				}
			}
			//---------everything between 0..pi/2
			x = x * _2_pi_hi;
			if (x > X_EPS)
			{
				System.Console.Out.WriteLine("x > EPS");
				x2 = x * x;
				x *= ((((((((P)[0] * (x2) + (P)[1]) * (x2) + (P)[2]) * (x2) + (P)[3]) * (x2) + (P
					)[4]) * (x2) + (P)[5]) * (x2) + (P)[6]) * (x2) + (P)[7]);
			}
			else
			{
				System.Console.Out.WriteLine("x <= EPS");
				x *= pi2_hi;
			}
			if (sign == 1)
			{
				x = -x;
			}
			System.Console.Out.WriteLine("final return");
			return x;
		}

		public MathSin()
		{
			//===============AUX========================
			//#define MD(v,hi,lo) md.i.i1 = hi; md.i.i2 = lo; v = md.d;
			//	  MD(    pi_hi, 0x400921FBuL,0x54442D18uL);/* top 53 bits of PI	*/
			// pi_hi = Double.longBitsToDouble((long)0x400921FB54442D18L);
			// System.out.println("pi_hi = " + pi_hi);
			//	  MD(    pi_lo, 0x3CA1A626uL,0x33145C07uL);/* next 53 bits of PI*/
			// pi_lo = Double.longBitsToDouble((long)0x3CA1A62633145C07L);
			// System.out.println("pi_lo = " + pi_lo);
			//	  MD(   pi2_hi, 0x3FF921FBuL,0x54442D18uL);/* top 53 bits of PI/2 */
			pi2_hi = BitConverter.DoubleToInt64Bits(unchecked((long)(0x3FF921FB54442D18L)));
			System.Console.Out.WriteLine("pi2_hi = " + pi2_hi);
			//	  MD(   pi2_lo, 0x3C91A626uL,0x33145C07uL);/* next 53 bits of PI/2*/
			pi2_lo = BitConverter.DoubleToInt64Bits(unchecked((long)(0x3C91A62633145C07L)));
			System.Console.Out.WriteLine("pi2_lo = " + pi2_lo);
			//	  MD(  pi2_lo2, 0xB91F1976uL,0xB7ED8FBCuL);/* next 53 bits of PI/2*/
			pi2_lo2 = BitConverter.DoubleToInt64Bits(unchecked((long)(0xB91F1976B7ED8FBCL)));
			System.Console.Out.WriteLine("pi2_lo2 = " + pi2_lo2);
			//	  MD( _2_pi_hi, 0x3FE45F30uL,0x6DC9C883uL);/* top 53 bits of 2/pi */
			_2_pi_hi = BitConverter.DoubleToInt64Bits(unchecked((long)(0x3FE45F306DC9C883L)));
			System.Console.Out.WriteLine("_2_pi_hi = " + _2_pi_hi);
			//	  MD( _2_pi_lo, 0xBC86B01EuL,0xC5417056uL);/* next 53 bits of 2/pi*/
			_2_pi_lo = BitConverter.DoubleToInt64Bits(unchecked((long)(0xBC86B01EC5417056L)));
			System.Console.Out.WriteLine("_2_pi_lo = " + _2_pi_lo);
			//>>>>>	  split(pi2_hi_hi,pi2_hi_lo,pi2_hi);
			double a1;
			double a2;
			double xm;
			xm = pi2_hi;
			System.Console.Out.WriteLine("splitting: " + xm);
			long l_x1 = BitConverter.DoubleToInt64Bits(xm);
			//   <32> <20> <11> <1>
			// sign
			int md_b_sign1 = (int)((l_x1 >> 63) & 1);
			// exponent:
			int xexp1 = (int)((l_x1 >> 52) & unchecked((int)(0x7FF)));
			int md_b_m21 = (int)(l_x1 & unchecked((int)(0xFFFFFFFF)));
			int md_b_m11 = (int)((l_x1 >> 31) & unchecked((int)(0xFFFFF)));
			System.Console.Out.WriteLine("raw=" + l_x1);
			System.Console.Out.WriteLine("sign=" + md_b_sign1);
			System.Console.Out.WriteLine("exp=" + xexp1);
			System.Console.Out.WriteLine("exp (unbiased)=" + (xexp1 - IEEE_BIAS));
			System.Console.Out.WriteLine("m1=" + md_b_m11);
			System.Console.Out.WriteLine("m2=" + md_b_m21);
			// 	md.b.m2 &= 0xfc000000u;		\
			// md_b_m2 = (int)(l_x1 & 0xFFFFFFFF);
			l_x1 &= unchecked((long)(0xFC000000L));
			a1 = BitConverter.DoubleToInt64Bits(l_x1);
			// 	lo = (v) - hi;	/* bot 26 bits */
			a2 = xm - a1;
			System.Console.Out.WriteLine("in split: a1=" + a1);
			System.Console.Out.WriteLine("in split: a2=" + a2);
			pi2_hi_hi = a1;
			pi2_hi_lo = a2;
			//>>>>>	  split(pi2_lo_hi,pi2_lo_lo,pi2_lo);
			xm = pi2_lo;
			System.Console.Out.WriteLine("splitting: " + xm);
			// xm is a concrete value; no need to invoke the helper (pdinges)
			l_x1 = BitConverter.DoubleToInt64Bits(xm);
			//l_x1 = MathSin.helperdoubleToRawBits(xm);
			//   <32> <20> <11> <1>
			// sign
			md_b_sign1 = (int)((l_x1 >> 63) & 1);
			// exponent:
			xexp1 = (int)((l_x1 >> 52) & unchecked((int)(0x7FF)));
			md_b_m21 = (int)(l_x1 & unchecked((int)(0xFFFFFFFF)));
			md_b_m11 = (int)((l_x1 >> 31) & unchecked((int)(0xFFFFF)));
			System.Console.Out.WriteLine("raw=" + l_x1);
			System.Console.Out.WriteLine("sign=" + md_b_sign1);
			System.Console.Out.WriteLine("exp=" + xexp1);
			System.Console.Out.WriteLine("exp (unbiased)=" + (xexp1 - IEEE_BIAS));
			System.Console.Out.WriteLine("m1=" + md_b_m11);
			System.Console.Out.WriteLine("m2=" + md_b_m21);
			// 	md.b.m2 &= 0xfc000000u;		\
			// md_b_m2 = (int)(l_x1 & 0xFFFFFFFF);
			l_x1 &= unchecked((long)(0xFC000000L));
			a1 = BitConverter.DoubleToInt64Bits(l_x1);
			// 	lo = (v) - hi;	/* bot 26 bits */
			a2 = xm - a1;
			System.Console.Out.WriteLine("in split: a1=" + a1);
			System.Console.Out.WriteLine("in split: a2=" + a2);
			pi2_lo_hi = a1;
			pi2_lo_lo = a2;
		}


		//====================================================
		public virtual void TESTIT(double arg)
		{
			double ms = Mysin(arg);
			System.Console.Out.WriteLine("SIN(" + arg + "):\t" + Math.Sin(arg) + "\tmysin: " 
				+ ms);
		}

		/// <param name="args"></param>
		public static void Main(string[] args)
		{
			// TODO Auto-generated method stub
			Concolic.MathSin sin = new Concolic.MathSin();
			if (sin.Mysin(0.0) == 0.0)
			{
				System.Console.Out.WriteLine("it is zero");
			}
		}

        [PexMethod(MaxConstraintSolverTime = 5, MaxRunsWithoutNewTests = 500)]
        public static void test(double arg)
        {
			Concolic.MathSin sin = new Concolic.MathSin();
            sin.Mysin(arg);
        }
	}
}
