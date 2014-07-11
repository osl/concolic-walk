/*
 * Automatically converted from Java to C# using Sharpen.
 */

using Microsoft.VisualStudio.TestTools.UnitTesting;
using Microsoft.Pex.Framework;
using System;

[TestClass]
public partial class EffectiveJavaHashCode
{
    private readonly int x;

    private readonly long y;

    private readonly int z;

    public EffectiveJavaHashCode(int x, long y, int z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public override int GetHashCode()
    {
        int h = x;
        h = h * 31 + (int)(y ^ ((long)(((ulong)y) >> 32)));
        h = h * 31 + z;
        return h;
    }

    [PexMethod(MaxConstraintSolverTime = 5, MaxRunsWithoutNewTests = 500)]
    public static void TestCollision1(int x1, long y1, int z1, int x2, long y2, int z2
        )
    {
        EffectiveJavaHashCode o1 = new EffectiveJavaHashCode(x1, y1, z1);
        EffectiveJavaHashCode o2 = new EffectiveJavaHashCode(x2, y2, z2);
        if (o1.GetHashCode() == o2.GetHashCode())
        {
            System.Console.Out.WriteLine("Solved hash collision 1");
        }
    }

    [PexMethod(MaxConstraintSolverTime = 5, MaxRunsWithoutNewTests = 500)]
    public static void TestCollision2(long y1, int z1, long y2, int z2)
    {
        EffectiveJavaHashCode o1 = new EffectiveJavaHashCode(1, y1, z1);
        EffectiveJavaHashCode o2 = new EffectiveJavaHashCode(2, y2, z2);
        if (o1.GetHashCode() == o2.GetHashCode())
        {
            System.Console.Out.WriteLine("Solved hash collision 2");
        }
    }

    [PexMethod(MaxConstraintSolverTime = 5, MaxRunsWithoutNewTests = 500)]
    public static void TestCollision3(long y1, long y2)
    {
        // The hashes can collide only if the lower 5 bits of z1/z2 match
        EffectiveJavaHashCode o1 = new EffectiveJavaHashCode(1234, y1, 3141);
        EffectiveJavaHashCode o2 = new EffectiveJavaHashCode(5678, y2, 3141);
        if (o1.GetHashCode() == o2.GetHashCode())
        {
            System.Console.Out.WriteLine("Solved hash collision 3");
        }
    }

    [PexMethod(MaxConstraintSolverTime = 5, MaxRunsWithoutNewTests = 500)]
    public static void TestCollision4(int x1, long y1, int z1)
    {
        EffectiveJavaHashCode o1 = new EffectiveJavaHashCode(1234, BitConverter.DoubleToInt64Bits(3.14159E123), 3141);
        EffectiveJavaHashCode o2 = new EffectiveJavaHashCode(x1, y1, z1);
        if (o1.GetHashCode() == o2.GetHashCode())
        {
            System.Console.Out.WriteLine("Solved hash collision 4");
        }
    }

    [PexMethod(MaxConstraintSolverTime = 5, MaxRunsWithoutNewTests = 500)]
    public static void TestCollision5(long y1, int z1)
    {
        EffectiveJavaHashCode o1 = new EffectiveJavaHashCode(1234, BitConverter.DoubleToInt64Bits(3.14159E123), 3141);
        EffectiveJavaHashCode o2 = new EffectiveJavaHashCode(5678, y1, z1);
        if (o1.GetHashCode() == o2.GetHashCode())
        {
            System.Console.Out.WriteLine("Solved hash collision 5");
        }
    }
}
