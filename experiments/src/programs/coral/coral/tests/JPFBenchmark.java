package coral.tests;

public class JPFBenchmark {

  public static String success = "success";

  // sin(x) + cos(y) > 1.0
  public static void benchmark01(double x, double y) {
    if (Math.sin(x) + Math.cos(y) > 1) {
      System.out.println("Solved 01");
    }
  }

  // sin(x) - cos(y) < 0.0000000001
  public static void benchmark02(double x, double y) {
    if (Math.sin(x) - Math.cos(y) < 0.0000000001) {
      System.out.println("Solved 02");
    }
  }

  // sin(x) - cos(y) == 0.0
  public static void benchmark03(double x, double y) {
    if (Math.sin(x) - Math.cos(y) == 0) {
      System.out.println("Solved 03");
    }
  }

  // exp(x) > 0.0
  public static void benchmark04(double x) {
    if (Math.exp(x) > 0) {
      System.out.println("Solved 04");
    }
  }

  // sin A + sin B + sin C = 4 * cos A * cos B * cos C
  public static void benchmark05(double x, double y, double z) {
    if (Math.sin(x) + Math.sin(y) + Math.sin(z) == 4 * Math.cos(x)
        * Math.cos(y) * Math.cos(z)) {
      System.out.println("Solved 05");
    }
  }

  // cos A + cos B + cos C > 4 sin A/2 sin B/2 sin C/2
  public static void benchmark06(double x, double y, double z) {
    if (Math.cos(x) + Math.cos(y) + Math.cos(z) > 4 * Math.sin(x / 2)
        * Math.sin(y / 2) * Math.sin(z / 2)) {
      System.out.println("Solved 06");
    }
  }

  // (sin(2x - y)/(cos(2y + y) + 1) = cos(2z + x)/(sin(2w + y) - 1)
  public static void benchmark07(double x, double y, double z, double w) {
    if (Math.sin(2 * x - y) / (Math.cos(2 * y + y) + 1) == Math.cos(2 * z + x)
        / (Math.sin(2 * w + y) - 1)) {
      System.out.println("Solved 07");
    }
  }

  // cos(3x+2y-z) * sin(z+x+y) == cos(z*x*y)
  public static void benchmark08(double x, double y, double z) {
    if (Math.cos(3 * x + 2 * y - z) * Math.sin(z + x + y) == Math
        .cos(z * x * y)) {
      System.out.println("Solved 08");
    }
  }

  // Math.sin(Math.cos(x*y)) < Math.cos(Math.sin(x*y))
  public static void benchmark09(double x, double y) {
    if (Math.sin(Math.cos(x * y)) < Math.cos(Math.sin(x * y))) {
      System.out.println("Solved 09");
    }
  }

  // Math.sin(x*Math.cos(y*Math.sin(z))) > Math.cos(x*Math.sin(y*Math.cos(z)))
  public static void benchmark10(double x, double y, double z) {
    if (Math.sin(x * Math.cos(y * Math.sin(z))) > Math.cos(x
        * Math.sin(y * Math.cos(z)))) {
      System.out.println("Solved 10");
    }
  }

  // asin(x) < cos(y)*cos(z) - Math.atan(w)
  public static void benchmark11(double x, double y, double z, double w) {
	  if(Math.asin(x) < Math.cos(y) * Math.cos(z) - Math.atan(w)) {
      System.out.println("Solved 11");
    }
  }

  // (asin(x) * Math.asin(y))-1 < Math.atan(z) * Math.atan(w)  
  public static void benchmark12(double x, double y, double z, double w) {
	  if((Math.asin(x) * Math.asin(y)) - 1 < Math.atan(z) * Math.atan(w)) {
		  System.out.println("Solved 12");
	  }
  }

  // sin(y) * Math.asin(x) < cos(y)*cos(z) - Math.atan(w)
  public static void benchmark13(double x, double y, double z, double w) {
	if(Math.sin(y) * Math.asin(x) < Math.cos(y)*Math.cos(z) - Math.atan(w)) {
      System.out.println("Solved 13");
    }
  }

  // sin(y) * Math.asin(x) - 300 < cos(y)*cos(z) - Math.atan(w)
  public static void benchmark14(double x, double y, double z, double w) {
	if(Math.sin(y) * Math.asin(x) - 300 < Math.cos(y)*Math.cos(z) - Math.atan(w)) {
      System.out.println("Solved 14");
    }
  }

  // ((Math.asin(1) * Math.asin(cos(9*57)))-1) < (Math.atan(0) * Math.atan(0)) solution x=1,y=513,z=0,w=0
  public static void benchmark15(double x, double y, double z, double w) {
	  if(((Math.asin(1) * Math.asin(Math.cos(9*57)))-1) < (Math.atan(0) * Math.atan(0))) {
		  System.out.println("Solved 15");
    }
  }

	//((((tan_(($V4-$V1))*cos_(sin_(($V4/$V5))))-atan_((($V2+20.0)+$V3)))+asin_(($V2-15.0))) < ((sin_(($V4*$V4))*cos_((($V1*$V4)*$V5)))-tan_((cos_((($V1*$V4)*$V1))+sin_($V4)))))  
	public static void benchmark16(double x, double y, double z, double w, double v) {
		if(Math.tan(w-x)*Math.cos(Math.sin(w/v)) - Math.atan(y + 20 + z) + Math.asin(y-15) < Math.sin(w * w) * Math.cos(x*w*v) - Math.tan(Math.cos(x*w*x)) + Math.sin(w)){
      System.out.println("Solved 16");
    }
	}

  // Math.asin(x) * Math.acos(x) < Math.atan(x)  
  public static void benchmark17(double x) {
	if(Math.asin(x) * Math.acos(x) < Math.atan(x)) {
      System.out.println("Solved 17");
    }
  }

  // (1+Math.acos(x)) < Math.asin(x)
  public static void benchmark18(double x) {
	  if((1+Math.acos(x)) < Math.asin(x)) {
      System.out.println("Solved 18");
    }
  }

  // 3*Math.acos(x) < Math.atan(y) + Math.asin(z)
  public static void benchmark19(double x, double y, double z) {
	  if( 3*Math.acos(x) < Math.atan(y) + Math.asin(z)) {
		  System.out.println("Solved 19");
	  }
  }

  // sin(sin((x*y)) < 0 && cos(2x) > 0.25
  public static void benchmark20(double x, double y) {
	  if(Math.sin(Math.sin(x*y)) < 0 && Math.cos(2*x) > 0.25) {
		  System.out.println("Solved 20");
	  }
  }

  // cos(x*y) < 0 && sin(2x) > 0.25
	public static void benchmark21(double x, double y) {
		if( Math.cos(x*y) < 0 && Math.sin(2*x) > 0.25) {
			System.out.println("Solved 21");
		}
	}

  // (sin_(cos_(($V1*$V2))) < cos_(sin_(($V2*$V3)))) & 
  // ((sin_((($V4*2.0)-$V2))/(cos_((($V6*2.0)+$V7))+1.0)) == (cos_((($V3*2.0)+$V1))/(sin_((($V4*2.0)+$V5))+1.0))))
	public static void benchmark22(double x, double y, double z, double w, double v, double t, double q) {
		if((Math.sin(Math.cos(x*y)) < Math.cos(Math.sin(y*z))) && 
		   Math.sin(w*2.0 -y)/(Math.cos(t*2.0+q)+1.0) == (Math.cos(z*2.0+x)/(Math.sin(w*2.0+v)+1.0))) {
			System.out.println("Solved 22");
		}
	}

  // (sin(2x - y)/(cos(2y + x) + 1) = cos(2z + x)/(sin(2w + y) - 1) &
  // sin(x*y*z*w) > 0 &
  // cos(x*y*z*w) < 0

  public static void benchmark23(double x, double y, double z, double w) {
	  if(Math.sin(2*x - y)/(Math.cos(2*y + x) + 1) == Math.cos(2*z + x)/(Math.sin(2*w + y) - 1) &&
		 Math.sin(x*y*z*w) > 0 && Math.cos(x*y*z*w) < 0) {
      System.out.println("Solved 23");
    }
  }

  //  sin(cos(x*y)) < cos(sin(x*z)) &
  // (sin(2w - y)/(cos(2y + v) + 1) = cos(2z + x)/(sin(2w + v) - 1)
  public static void benchmark25(double x, double y, double z, double w, double v) {
	  if(Math.sin(Math.cos(x*y)) < Math.cos(Math.sin(x*z)) && (Math.sin(2*w - y)/(Math.cos(2*y + v) + 1) == Math.cos(2*z + x)/(Math.sin(2*w + v) - 1))) {
		  System.out.println("Solved 25");
    }
  }

  // sin(cos(x*y)) < cos(sin(x*z)) 
  // (sin(2w - y)/(cos(2y + v) + 1) = cos(2z + x)/(sin(2w + v) - 1) 
  // sin(x*y*z*w) > 0 && cos(x*y*z*w) < 0
	  public static void benchmark26(double x, double y, double z, double w, double v) {
		  if(Math.sin(Math.cos(x*y)) < Math.cos(Math.sin(x*z)) && (Math.sin(2*w - y)/(Math.cos(2*y + v) + 1) == Math.cos(2*z + x)/(Math.sin(2*w + v) - 1)) &&  Math.sin(x*y*z*w) > 0 && Math.cos(x*y*z*w) < 0 ) {
			  System.out.println("Solved 26");
    }
  }

  // sin(x*cos(y*sin(z))) > cos(x*sin(y*cos(z))) && sin(cos(x*y)) < cos(sin(x*y))
  public static void benchmark27(double x, double y, double z) {
	if(Math.sin(x*Math.cos(y*Math.sin(z))) > Math.cos(x*Math.sin(y*Math.cos(z))) && Math.sin(Math.cos(x*y)) < Math.cos(Math.sin(x*y))) {
      System.out.println("Solved 27");
    }
  }

    /* Disabled because JPF does not support Math.log10 (pdinges)
  // log($V1) == 2.0
  public static void benchmark28(double x) {
	  if(Math.log(x) == 2) {
		  System.out.println("Solved 28");
	  }
  }
    */

  public static void benchmark29(double x) {
	  if(Math.exp(x) > 5){
		  System.out.println("Solved 29");
	  }
  }

    /* Disabled because JPF does not support Math.log10 (pdinges)
  // log_10($v1) == 2.0
  public static void benchmark30(double x) {
	  if(Math.log10(x) == 2){
		  System.out.println("Solved 30");
	  }
  }
    */

    /* Disabled because the constraint is linear and the rounding triggers
       a problem with JPF and the Choco solver, generating 5.499999999999
       as a "solution". (pdinges)
  public static void benchmark31(double x) {
	  if(Math.round(x) > 5) {
			  System.out.println("Solved 31");
    }
  }
    */

  public static void benchmark32(double x) {
	  if(Math.sqrt(x) > 5) {
		  System.out.println("Solved 32");
	  }
  }

  // sqrt(sin($V1)) > sqrt(cos($V1))
  public static void benchmark33(double x) {
	  if(Math.sqrt(Math.sin(x)) > Math.sqrt(Math.cos(x))) {
		  System.out.println("Solved 33");
	  }
  }

  // sqrt(sin($V1)) < sqrt(cos($V1))
  public static void benchmark34(double x) {
	  if(Math.sqrt(Math.sin(x)) < Math.sqrt(Math.cos(x))) {
		  System.out.println("Solved 34");
	  }
  }

  //  1.0/sqrt(sin($V1)) > sqrt(cos(exp($V2)))
  public static void benchmark35(double x,double y) {
	  if(1.0/Math.sqrt(Math.sin(x)) > Math.sqrt(Math.cos(Math.exp(y)))){
		  System.out.println("Solved 35");
	  }
  }

    /* Disabled because JPF does not support Math.log10 (pdinges)
  // ((log10($V3)*(1.0/sqrt(sin_($V1)))) == sqrt(cos_(exp($V2))))
  public static void benchmark36(double x,double y,double z) {
	  if(Math.log10(z)*(1.0/Math.sqrt(Math.sin(x))) == Math.sqrt(Math.cos(Math.exp(y)))) {
		  System.out.println("Solved 36");
	  }
  }
    */

    /* Disabled because JPF does not support Math.log10 (pdinges)
  // ((log10(tan_($V3))/(1.0/sqrt(sin_($V1)))) == sqrt(cos_(exp($V2))))
  public static void benchmark37(double x,double y,double z) {
	  if(Math.log10(Math.tan(z))*(1.0/Math.sqrt(Math.sin(x))) == Math.sqrt(Math.cos(Math.exp(y)))) {
		  System.out.println("Solved 37");
	  }
  }
    */

  // (atan2_($V1,$V2) == 1.0)
  public static void benchmark38(double x,double y) {
	  if(Math.atan2(x,y) == 1.0) {
		  System.out.println("Solved 38");
	  }
  }

  // (pow_($V1,$V2) == 1.0)
  public static void benchmark39(double x,double y) {
	  if(Math.pow(x,y) == 1.0) {
		  System.out.println("Solved 39");
	  }
  }

  // pow(x,2) == x + y
  public static void benchmark40(double x,double y) {
	  if(Math.pow(x,2) == x + y) {
		  System.out.println("Solved 40");
	  }
  }

 // pow(x,2) == x + y & x >= -1 & y <=  2
 
  public static void benchmark41(double x,double y) {
	  if(Math.pow(x,2) == x + y &&  x >= -1 && y <=  2 ) {
		  System.out.println("Solved 41");
	  }
  }
  // Math.pow(x,y) > Math.pow(y,x) & x > 1 & y <= 10
   public static void benchmark42(double x,double y) {
	  if(Math.pow(x,y) > Math.pow(y,x) && x > 1 && y <= 10) {
		  System.out.println("Solved 42");
	  }
  }

  // Math.pow(x,y) > Math.pow(y,x) && Math.exp(x,y) > Math.exp(y,x) && y < x ^ 2

  public static void benchmark43(double x,double y) {
	  if(Math.pow(x,y) > Math.pow(y,x) && Math.exp(y) > Math.exp(x) && y < Math.pow(x,2)) {
		  System.out.println("Solved 43");
	  }
  }

  // Math.pow(x,y) > Math.pow(y,x) && Math.exp(x,y) < Math.exp(y,x)

  public static void benchmark44(double x,double y) {
	  if(Math.pow(x,y) > Math.pow(y,x) && Math.exp(y) < Math.exp(x)) {
		  System.out.println("Solved 44");
	  }
  }

	// sqrt(Math.exp(x+y)) < Math.pow(z,x) && x > 0 && y > 1 && z > 1 && y <= x + 2

	public static void benchmark45(double x,double y,double z) {
		if(Math.sqrt(Math.exp(x+y)) < Math.pow(z,x) && x > 0 && y > 1 && z > 1 && y <= x + 2) {
			System.out.println("Solved 45");
		}
	}  	

  // Math.sqrt(e^(x + z)) < z^x && x > 0 && y > 1 && z > 1 && y < 1 && y < x + 2 && w = x + 2

  public static void benchmark46(double x,double y, double z, double w) {
	  if(Math.sqrt(Math.pow(Math.E,(x + z))) < Math.pow(z,x) && x > 0 && y > 1 && z > 1 && y < 1 && y < x + 2 && w == x + 2) {
		  System.out.println("Solved 46");
	  }
  }

  // e ^ (x + y) == e ^ z 
  
  public static void benchmark47(double x,double y,double z) {
	  if(Math.exp(x + y) == Math.exp(z)) {
		  System.out.println("Solved 47");
	  }
  }

  // x + y != z
  public static void benchmark48(double x,double y, double z) {
	  if(x + y != z) {
		  System.out.println("Solved 48");
	  }
  }

  //x^2 + 3*Math.sqrt(y) < x*y && x < y ^ 2 && x + y < 50 //556 possible integer solutions

  public static void benchmark49(double x,double y) {
	  if(Math.pow(x,2) + 3*Math.sqrt(y) < x*y && x < Math.pow(y,2) && x + y < 50) {
		  System.out.println("Solved 49");
	  }
  }

  //x^2 + 3*Math.sqrt(y) < x*y && x < y ^ 2 && x + y < 50 && x = -13 + y //18 possible integer solutions

  public static void benchmark50(double x,double y) {
	  if(Math.pow(x,2) + 3*Math.sqrt(y) < x*y && x < Math.pow(y,2) && x + y < 50 && x == -13 + y) {
		  System.out.println("Solved 50");
	  }
  }

    /* Disabled because JPF does not support Math.log10 (pdinges)
  //x^2 + 3*Math.sqrt(y) < x*y && x < y ^ 2 && x + y < 50 && x = -13 + y && x ^ x < Math.log10(y) //one integer solution
  public static void benchmark51(double x,double y) {
	  if(Math.pow(x,2) + 3*Math.sqrt(y) < x*y && x < Math.pow(y,2) && x + y < 50 && Math.pow(x,x) < Math.log10(y)) {
		  System.out.println("Solved 51");
	  }
  }
    */

  //x ^ tan(y) + z < x * Math.atan(z) && sin(y) + cos(y) + tan(y) >= x - z
	public static void benchmark52(double x,double y, double z) {
	  if(Math.pow(x,Math.tan(y)) + z < x * Math.atan(z) && Math.sin(y) + Math.cos(y) + Math.tan(y) >= x - z) {
		  System.out.println("Solved 52");
	  }
  }

	//x ^ Math.tan(y) + z < x * Math.atan(z) && Math.sin(y) + cos(y) + Math.tan(y) >= x - z && Math.atan(x) + Math.atan(y) > y
	public static void benchmark53(double x,double y, double z) {
		if(Math.pow(x,Math.tan(y)) + z < x * Math.atan(z) && Math.sin(y) + Math.cos(y) + Math.tan(y) >= x - z && Math.atan(x) + Math.atan(y) > y) {
			System.out.println("Solved 53");
	  }
	}

    /* Disabled because JPF does not support Math.log10 (pdinges)
  //x ^ Math.tan(y) + z < x * Math.atan(z) && Math.sin(y) + Math.cos(y) + Math.tan(y) >= x - z && Math.atan(x) + Math.atan(y) > y && Math.log(x^Math.tan(y)) < Math.log(z)
	public static void benchmark54(double x,double y, double z) {
		if(Math.pow(x,Math.tan(y)) + z < x * Math.atan(z) && Math.sin(y) + Math.cos(y) + Math.tan(y) >= x - z && Math.atan(x) + Math.atan(y) > y && Math.log(Math.pow(x,Math.tan(y))) < Math.log(z)) {
			System.out.println("Solved 54");
		}
	}
    */

    /* Disabled because JPF does not support Math.log10 (pdinges)
  //x ^ Math.tan(y) + z < x * Math.atan(z) &&  Math.sin(y) + Math.cos(y) + Math.tan(y) >= x - z &&  Math.atan(x) + Math.atan(y) > y &&  Math.log(x^Math.tan(y)) < Math.log(z) &&  Math.sqrt(y+z) > Math.sqrt(x^(x-y))  
	public static void benchmark55(double x,double y, double z) {
		if(Math.pow(x,Math.tan(y)) + z < x * Math.atan(z) && Math.sin(y) + Math.cos(y) + Math.tan(y) >= x - z && Math.atan(x) + Math.atan(y) > y && Math.log(Math.pow(x,Math.tan(y))) < Math.log(z) &&  Math.sqrt(y+z) > Math.sqrt(Math.pow(x,(x-y)))) {
			System.out.println("Solved 55");
		}
	}
    */

  //x * y + Math.atan(z) * Math.sin(w*t) > x/y + z + Math.tan(w+t)
	public static void benchmark56(double x,double y, double z,double w, double t) {
		if(x * y + Math.atan(z) * Math.sin(w*t) > x/y + z + Math.tan(w+t)) {
			System.out.println("Solved 56");
		}
	}

    /* Disabled because JPF does not support Math.log10 (pdinges)
//x * y + Math.atan(z) * Math.sin(w*t) > x/y + z + Math.tan(w+t) && Math.pow(Math.log10(x),Math.log10(y)) <= Math.pow(Math.log10(z+w+t),Math.tan(w*t))
	public static void benchmark57(double x,double y, double z,double w, double t) {
		if(x * y + Math.atan(z) * Math.sin(w*t) > x/y + z + Math.tan(w+t) && Math.pow(Math.log10(x),Math.log10(y)) <= Math.pow(Math.log10(z+w+t),Math.tan(w*t))) {
			System.out.println("Solved 57");
		}
	}
    */

    /* Disabled because JPF does not support Math.log10 (pdinges)
  //x * y + Math.atan(z) * Math.sin(w*t) > x/y + z + Math.tan(w+t) && Math.pow(Math.log10(x),Math.log10(y)) <= Math.pow(Math.log10(z+w+t),Math.tan(w*t)) && Math.tan(w*(x+y)) + Math.sin(t*(y+z)) > Math.asin(x+y+z) + acos(x+y+z) + Math.atan(x+y+z)
	public static void benchmark58(double x,double y, double z,double w, double t) {
		if(x * y + Math.atan(z) * Math.sin(w*t) > x/y + z + Math.tan(w+t) && Math.pow(Math.log10(x),Math.log10(y)) <= Math.pow(Math.log10(z+w+t),Math.tan(w*t)) && Math.tan(w*(x+y)) + Math.sin(t*(y+z)) > Math.asin(x+y+z) + Math.acos(x+y+z) + Math.atan(x+y+z)) {
			System.out.println("Solved 58");
		}
	}
    */

    /* Disabled because JPF does not support Math.log10 (pdinges)
  //x * y + Math.atan(z) * Math.sin(w*t) > x/y + z + Math.tan(w+t) && Math.pow(Math.log10(x),Math.log10(y)) <= Math.pow(Math.log10(z+w+t),Math.tan(w*t))	&& Math.tan(w*(x+y)) + Math.sin(t*(y+z)) > Math.asin(x+y+z) + Math.acos(x+y+z) + Math.atan(x+y+z) && w = t * 3 / 4
	public static void benchmark59(double x,double y, double z,double w, double t) {
		if(x * y + Math.atan(z) * Math.sin(w*t) > x/y + z + Math.tan(w+t) && Math.pow(Math.log10(x),Math.log10(y)) <= Math.pow(Math.log10(z+w+t),Math.tan(w*t)) && Math.tan(w*(x+y)) + Math.sin(t*(y+z)) > Math.asin(x+y+z) + Math.acos(x+y+z) + Math.atan(x+y+z) && w == t * 3 / 4) {
			System.out.println("Solved 59");
		}
	}
    */

    /* Disabled because JPF does not support Math.log10 (pdinges)
  //x * y + Math.atan(z) * Math.sin(w*t) > x/y + z + Math.tan(w+t) && Math.pow(Math.log10(x),Math.log10(y)) <= Math.pow(Math.log10(z+w+t),Math.tan(w*t))	&& Math.tan(w*(x+y)) + Math.sin(t*(y+z)) > Math.asin(x+y+z) + Math.acos(x+y+z) + Math.atan(x+y+z) && w = t * 3 / 4 && x < 2y - 3z
	public static void benchmark60(double x,double y, double z,double w, double t) {
		if(x * y + Math.atan(z) * Math.sin(w*t) > x/y + z + Math.tan(w+t) && Math.pow(Math.log10(x),Math.log10(y)) <= Math.pow(Math.log10(z+w+t),Math.tan(w*t)) && Math.tan(w*(x+y)) + Math.sin(t*(y+z)) > Math.asin(x+y+z) + Math.acos(x+y+z) + Math.atan(x+y+z) && w == t * 3 / 4 && x < 2*y - 3*z) {
			System.out.println("Solved 60");
		}
	}
    */

  //x + y > z / w && Math.sqrt(x) > z / y && z*2 + w*3 + x*7 < Math.pow(y,6) && z + w > x + y && w < x/y
	public static void benchmark61(double x,double y, double z,double w) {
		if(x + y > z / w && Math.sqrt(x) > z / y && z*2 + w*3 + x*7 < Math.pow(y,6) && z + w > x + y && w < x/y) {
			System.out.println("Solved 61");
		}
	}

  //x + y > z / w && Math.sqrt(x) > z / y && z*2 + w*3 + x*7 < Math.pow(y,6) && z + w > x + y && w < x/y && x > (w+y-z)
	public static void benchmark62(double x,double y, double z,double w) {
		if(x + y > z / w && Math.sqrt(x) > z / y && z*2 + w*3 + x*7 < Math.pow(y,6) && z + w > x + y && w < x/y && x > (w+y-z)) {
			System.out.println("Solved 62");
		}
	}

    /* Disabled because JPF does not support Math.log10 (pdinges)
  //x + y > z / w && Math.sqrt(x) > z / y && Math.log(x*y) > Math.log(t+w+z) && z*2 + w*3 + x*7 < Math.pow(y,6) && z + w > x + y && w < x/y && x > (w+y-z) && Math.log10(t*x) < Math.sqrt(w*y*z)
	public static void benchmark63(double x,double y, double z,double w, double t) {
		if(x + y > z / w && Math.sqrt(x) > z / y && Math.log(x*y) > Math.log(t+w+z) && z*2 + w*3 + x*7 < Math.pow(y,6) && z + w > x + y && w < x/y && x > (w+y-z) && Math.log10(t*x) < Math.sqrt(w*y*z)) {
			System.out.println("Solved 63");
		}
	}
    */

    /* Disabled because JPF does not support Math.log10 (pdinges)
  //x + y > z / (w + t) && Math.sqrt(x) > z / y && Math.log(x*y) > Math.log(t+w+z) && z*2 + w*3 + x*7 < Math.pow(y,t) && z + w > x + y && w < x/y && x > (w+y-z) && Math.log10(t*x) < Math.sqrt(w*y*z) &&	x * (t + y) > Math.log(w*z*3) 

	public static void benchmark64(double x,double y, double z,double w, double t) {
		if(x + y > z / (w + t) && Math.sqrt(x) > z / y && Math.log(x*y) > Math.log(t+w+z) && z*2 + w*3 + x*7 < Math.pow(y,t) && z + w > x + y && w < x/y && x > (w+y-z) && Math.log10(t*x) < Math.sqrt(w*y*z) &&	x * (t + y) > Math.log(w*z*3)) {
			System.out.println("Solved 64");
		}
	}
    */

    /* Disabled because JPF does not support Math.log10 (pdinges)
  //x + y > z / (w + t) && Math.sqrt(x) > z / y && Math.log(x*y) > Math.log(t+w+z) && z*2 + w*3 + x*7 < Math.pow(y,t) && z + w > x + y && w < x/y && x > (w+y-z) && Math.log10(t*x) < Math.sqrt(w*y*z) && x * Math.cos(t + y) > Math.log(w*z*3)
	public static void benchmark65(double x,double y, double z,double w, double t) {
		if(x + y > z / (w + t) && Math.sqrt(x) > z / y && Math.log(x*y) > Math.log(t+w+z) && z*2 + w*3 + x*7 < Math.pow(y,t) && z + w > x + y && w < x/y && x > (w+y-z) && Math.log10(t*x) < Math.sqrt(w*y*z) && x * Math.cos(t + y) > Math.log(w*z*3)) {
			System.out.println("Solved 65");
		}
	}
    */

    /* Disabled because JPF does not support Math.log10 (pdinges)
  //x + y > (z+ t) / (w + t) && Math.sqrt(x) > z / y && Math.log(x*y) > Math.log(t+w+z) && z*2 + w*3 + x*7 < Math.pow(y,t) && z + w > x + y && w < x/y && x > (w+y-z) && Math.log10(t*x) < Math.sqrt(w*y*z) && x * Math.cos(t + y) > Math.log(w*z*3) && Math.cos(t) > Math.cos(y)
	public static void benchmark66(double x,double y, double z,double w, double t) {
		if(x + y > (z+ t) / (w + t) && Math.sqrt(x) > z / y && Math.log(x*y) > Math.log(t+w+z) && z*2 + w*3 + x*7 < Math.pow(y,t) && z + w > x + y && w < x/y && x > (w+y-z) && Math.log10(t*x) < Math.sqrt(w*y*z) && x * Math.cos(t + y) > Math.log(w*z*3) && Math.cos(t) > Math.cos(y)) {
			System.out.println("Solved 66");
		}
	}
    */

    /* Disabled because JPF does not support Math.log10 (pdinges)
  //x - y + Math.tan(v)> (z+ t) / (w + t) && Math.sqrt(x-t) > z / y && Math.log(x*y) > Math.log(t+w+z) && z*2 + w*3 + x*7 < Math.pow(y,t)*Math.cos(v) && z + w > x + y && w < x/y && x > (w+y-z) && Math.log10(t*x) < Math.sqrt(w*y*z) && x * Math.cos(t + y) > Math.log(w*z*3) && Math.cos(t) * Math.sin(v) > Math.cos(y)
	public static void benchmark67(double x,double y, double z,double w, double t, double v) {
		if(x - y + Math.tan(v)> (z+ t) / (w + t) && Math.sqrt(x-t) > z / y && Math.log(x*y) > Math.log(t+w+z) && z*2 + w*3 + x*7 < Math.pow(y,t)*Math.cos(v) && z + w > x + y && w < x/y && x > (w+y-z) && Math.log10(t*x) < Math.sqrt(w*y*z) && x * Math.cos(t + y) > Math.log(w*z*3) && Math.cos(t) * Math.sin(v) > Math.cos(y)) {
			System.out.println("Solved 67");
		}
	}
    */

    /* Disabled because JPF does not support Math.log10 (pdinges)
  //x - y + Math.tan(v)> (z+ t) / (w + t) && Math.sqrt(x-t) > z / y && Math.log(x*y) > Math.log(t+w+z) && z*2 + w*3 + x*7 < Math.pow(y,t)*Math.cos(v) && z + w > x + y && w < x/y && x > (w+y-z) && Math.log10(t*x) < Math.sqrt(w*y*z) && x * Math.cos(t + y) > Math.log(w*z*3) && Math.cos(t) * Math.sin(v) > Math.cos(y)
	public static void benchmark68(double x,double y, double z,double w, double t, double v) {
		if(x - y + Math.tan(v)> (z+ t) / (w + t) && Math.sqrt(x-t) > z / y && Math.log(x*y) > Math.log(t+w+z) && z*2 + w*3 + x*7 < Math.pow(y,t)*Math.cos(v) && z + w > x + y && w < x/y && x > (w+y-z) && Math.log10(t*x) < Math.sqrt(w*y*z) && x * Math.cos(t + y) > Math.log(w*z*3) && Math.cos(t) * Math.sin(v) > Math.cos(y)) {
			System.out.println("Solved 68");
		}
	}
    */

    /* Disabled because JPF does not support Math.log10 (pdinges)
  //x - y + Math.tan(v)> (z+ t) / (w + t) && Math.sqrt(x-t) > z / y && Math.log(x*y) > Math.log(t+w+z) && z*2 + w*3 + x*7 < Math.pow(y,t)*Math.cos(v) && z + w > x + y && w < x/y && x > (w+y-z) && Math.log10(t*x) < Math.sqrt(w*y*z) && x * Math.cos(t + y) > Math.log(w*z*3) && Math.cos(t) * Math.sin(v) > Math.cos(y) && Math.sin(x*y) + Math.sin(z*w) + Math.sin(t*v) < Math.cos(x*y) + Math.cos(z*w) + Math.cos(t*v)
	public static void benchmark69(double x,double y, double z,double w, double t, double v) {
		if(x - y + Math.tan(v)> (z+ t) / (w + t) && Math.sqrt(x-t) > z / y && Math.log(x*y) > Math.log(t+w+z) && z*2 + w*3 + x*7 < Math.pow(y,t)*Math.cos(v) && z + w > x + y && w < x/y && x > (w+y-z) && Math.log10(t*x) < Math.sqrt(w*y*z) && x * Math.cos(t + y) > Math.log(w*z*3) && Math.cos(t) * Math.sin(v) > Math.cos(y) && Math.sin(x*y) + Math.sin(z*w) + Math.sin(t*v) < Math.cos(x*y) + Math.cos(z*w) + Math.cos(t*v)) {
			System.out.println("Solved 69");
		}
	}
    */

  //Math.sin(a) > Math.sin(b) > Math.sin(c) > Math.sin(d) > Math.sin(e) > Math.sin(f) > Math.sin(g) > Math.sin(h) > Math.sin(i) > Math.sin(j) > Math.sin(k) > Math.sin(l) > Math.sin(m) > Math.sin(n) > Math.sin(o) > Math.sin(p) > Math.sin(q) > Math.sin(r) > Math.sin(s) > Math.sin(t) > Math.sin(u) > Math.sin(v) > Math.sin(x) > Math.sin(z)
	public static void benchmark70(double a,double b, double c,double d, double e, double f, double g, double h, double i, double j, double k, double l, double m, double n, double o, double p, double q, double r, double s, double t, double u, double v, double x, double z) {
		if(Math.sin(a) > Math.sin(b) && Math.sin(b) > Math.sin(c) && Math.sin(c) > Math.sin(d) && Math.sin(d) > Math.sin(e) && Math.sin(e) > Math.sin(f) && Math.sin(f) > Math.sin(g) && Math.sin(g) > Math.sin(h) && Math.sin(h) > Math.sin(i) && Math.sin(i) > Math.sin(j) && Math.sin(j) > Math.sin(k) && Math.sin(k) > Math.sin(l) && Math.sin(l) > Math.sin(m) && Math.sin(m) > Math.sin(n) && Math.sin(n) > Math.sin(o) && Math.sin(o) > Math.sin(p) && Math.sin(p) > Math.sin(q) && Math.sin(q) > Math.sin(r) && Math.sin(r) > Math.sin(s) && Math.sin(s) > Math.sin(t) && Math.sin(t) > Math.sin(u) && Math.sin(u) > Math.sin(v) && Math.sin(v) > Math.sin(x) && Math.sin(x) > Math.sin(z)) {
			System.out.println("Solved 70");
		}
	}

	public static void benchmark71(double a,double b, double c,double d, double e, double f, double g, double h, double i, double j, double k, double l) {
		if(Math.sin(a) > Math.sin(b) && Math.sin(b) > Math.sin(c) && Math.sin(c) > Math.sin(d) && Math.sin(d) > Math.sin(e) && Math.sin(e) > Math.sin(f) && Math.sin(f) > Math.sin(g) && Math.sin(g) > Math.sin(h) && Math.sin(h) > Math.sin(i) && Math.sin(i) > Math.sin(j) && Math.sin(j) > Math.sin(k) && Math.sin(k) > Math.sin(l) ) { 
			System.out.println("Solved 71");
		}
	}

	public static void benchmark72(double a,double b, double c,double d, double e, double f, double g) {
		if(Math.sin(a) > Math.sin(b) && Math.sin(b) > Math.sin(c) && Math.sin(c) > Math.sin(d) && Math.sin(d) > Math.sin(e) && Math.sin(e) > Math.sin(f) && Math.sin(f) > Math.sin(g)) { 
			System.out.println("Solved 72");
		}
	}

	public static void benchmark73(double a,double b, double c,double d, double e, double f, double g, double h, double i, double j) {
		if(Math.sin(a) > Math.sin(b) && Math.sin(b) > Math.sin(c) && Math.sin(c) > Math.sin(d) && Math.sin(d) > Math.sin(e) && Math.sin(e) > Math.sin(f) && Math.sin(f) > Math.sin(g) && Math.sin(g) > Math.sin(h) && Math.sin(h) > Math.sin(i) && Math.sin(i) > Math.sin(j)) {
			System.out.println("Solved 73");
		}
	}

	public static void benchmark74(double a,double b, double c,double d, double e, double f, double g, double h) {
		if(Math.sin(a) > Math.sin(b) && Math.sin(b) > Math.sin(c) && Math.sin(c) > Math.sin(d) && Math.sin(d) > Math.sin(e) && Math.sin(e) > Math.sin(f) && Math.sin(f) > Math.sin(g) && Math.sin(g) > Math.sin(h)) {
			System.out.println("Solved 74");
		}
	}

	public static void benchmark75(double a,double b, double c,double d, double e, double f, double g, double h, double i) {
		if(Math.sin(a) > Math.sin(b) && Math.sin(b) > Math.sin(c) && Math.sin(c) > Math.sin(d) && Math.sin(d) > Math.sin(e) && Math.sin(e) > Math.sin(f) && Math.sin(f) > Math.sin(g) && Math.sin(g) > Math.sin(h) && Math.sin(h) > Math.sin(i)) {
			System.out.println("Solved 75");
		}
	}

	public static void benchmark76(double a,double b, double c,double d, double e, double f, double g, double h, double i) {
		if(a > b && b > c && c > d && d > e && e > f && f > g && g > h && h > i) {
			System.out.println("Solved 76");
		}
	}

	public static void benchmark77(double a,double b, double c,double d, double e, double f, double g, double h, double i, double j) {
		if(a > b && b > c && c > d && d > e && e > f && f > g && g > h && h > i && i > j) {
			System.out.println("Solved 77");
		}
	}

	//(0.0 == (pow_((($V1*sin_(((($V2*0.017453292519943295)-($V3*0.017453292519943295))+(((((((pow_($V4,2.0)/(sin_(($V5*0.017453292519943295))/cos_(($V5*0.017453292519943295))))/68443.0)*0.0)/$V4)*-1.0)*$V1)/((pow_($V1,2.0)/(sin_(($V5*0.017453292519943295))/cos_(($V5*0.017453292519943295))))/68443.0)))))-($V4*0.0)),2.0)+pow_((($V1*cos_(((($V2*0.017453292519943295)-($V3*0.017453292519943295))+(((((((pow_($V4,2.0)/(sin_(($V5*0.017453292519943295))/cos_(($V5*0.017453292519943295))))/68443.0)*0.0)/$V4)*-1.0)*$V1)/((pow_($V1,2.0)/(sin_(($V5*0.017453292519943295))/cos_(($V5*0.017453292519943295))))/68443.0)))))-($V4*1.0)),2.0)))

	//AND(AND(,($V6 != 0)),($V8 != 0)))

	public static void benchmark78(double a,double b, double c,double d, double e, int f, int g, int h) {
		if((0.0 == (Math.pow(((a*Math.sin(((b*0.017453292519943295 - c*0.017453292519943295)+(((((((Math.pow(d,2.0)/(Math.sin(e*0.017453292519943295)/Math.cos(e*0.017453292519943295)))/68443.0)*0.0)/d)*-1.0)*a)/((Math.pow(a,2.0)/(Math.sin((e*0.017453292519943295))/Math.cos((e*0.017453292519943295))))/68443.0)))))-(d*0.0)),2.0)+Math.pow(((a*Math.cos((((b*0.017453292519943295)-(c*0.017453292519943295))+(((((((Math.pow(d,2.0)/(Math.sin((e*0.017453292519943295))/Math.cos((e*0.017453292519943295))))/68443.0)*0.0)/d)*-1.0)*a)/((Math.pow(a,2.0)/(Math.sin((e*0.017453292519943295))/Math.cos((e*0.017453292519943295))))/68443.0)))))-d*1.0),2.0))) && f != 0 && h != 0) {
			System.out.println("Solved 78");
		}
	}

//AND((0.0 == (pow_((($V84*sin_(((0.017453292519943295*$V85)-(0.017453292519943295*$V86))))-(0.0*$V87)),2.0)+pow_(($V84*cos_((((0.017453292519943295*$V85)-(0.017453292519943295*$V86))+0.0))),2.0))),($V82 != 0))

	public static void benchmark79(double a,double b, double c,double d, int e) {
		if((0.0 == (Math.pow(((a*Math.sin(((0.017453292519943295*b)-(0.017453292519943295*c))))-(0.0*d)),2.0)+ Math.pow((a*Math.cos((((0.017453292519943295*b)-(0.017453292519943295*c))+0.0))),2.0))) && e != 0) {
			System.out.println("Solved 79");
		}
	}

  //(1.5 - x1 * (1 - x2)) == 0
	public static void benchmark80(double a,double b) {
		if((1.5 - a * (1 - b)) == 0) {
			System.out.println("Solved 80");
		}
	}

  //(-13 + x1 + ((5 - x2) * x2 - 2) * x2) + (-29 + x1 + ((x2 + 1) * x2 - 14) * x2) == 0
	public static void benchmark81(double a,double b) {
		if((-13 + a + ((5 - b) * b - 2) * b) + (-29 + a + ((b + 1) * b - 14) * b) == 0) {
			System.out.println("Solved 81");
		}
	}

  // (Math.Pow(10, 4) * x1 * x2 - 1) == 0 && (Math.Pow(Math.E, -x1) + Math.Pow(Math.E, -x2) - 1.0001) == 0
	public static void benchmark82(double a,double b) {
		if((Math.pow(10, 4) * a * b - 1) == 0 && (Math.pow(Math.E, -a) + Math.pow(Math.E, -b) - 1.0001) == 0) {
			System.out.println("Solved 82");
		}
	}

  // Math.Pow((1 - x1), 2) + 100 * (Math.Pow((x2 - x1 * x1), 2)) == 0
	public static void benchmark83(double a,double b) {
		if(Math.pow((1 - a), 2) + 100 * (Math.pow((b - a * a), 2)) == 0) {
			System.out.println("Solved 83");
		}
	}

  //(10 * (x2 - x1 * x1)) == 0 && (1 - x1) == 0 && (Math.sqrt(90) * (x4 - x3 * x3)) == 0 && (1 - x3) == 0 && (Math.sqrt(10) * (x2 + x4 - 2)) == 0 && (Math.Pow(10, -0.5) * (x2 - x4)) == 0

	public static void benchmark84(double a,double b, double c, double d) {
		if((10 * (b - a * a)) == 0 && (1 - a) == 0 && (Math.sqrt(90) * (d - c * c)) == 0 && (1 - c) == 0 && (Math.sqrt(10) * (b + d - 2)) == 0 && (Math.pow(10, -0.5) * (b - d)) == 0) {
			System.out.println("Solved 84");
		}
	}
	
	public static void benchmark91(double x, double y){
	  if(Math.sin(x) == -Math.sin(y) && Math.sin(x) > 0)
	    System.out.println("Solved 91");
	}



}
