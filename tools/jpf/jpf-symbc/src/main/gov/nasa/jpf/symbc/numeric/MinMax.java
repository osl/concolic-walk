//
//Copyright (C) 2006 United States Government as represented by the
//Administrator of the National Aeronautics and Space Administration
//(NASA).  All Rights Reserved.
//
//This software is distributed under the NASA Open Source Agreement
//(NOSA), version 1.3.  The NOSA has been approved by the Open Source
//Initiative.  See the file NOSA-1.3-JPF at the top of the distribution
//directory tree for the complete NOSA document.
//
//THE SUBJECT SOFTWARE IS PROVIDED "AS IS" WITHOUT ANY WARRANTY OF ANY
//KIND, EITHER EXPRESSED, IMPLIED, OR STATUTORY, INCLUDING, BUT NOT
//LIMITED TO, ANY WARRANTY THAT THE SUBJECT SOFTWARE WILL CONFORM TO
//SPECIFICATIONS, ANY IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR
//A PARTICULAR PURPOSE, OR FREEDOM FROM INFRINGEMENT, ANY WARRANTY THAT
//THE SUBJECT SOFTWARE WILL BE ERROR FREE, OR ANY WARRANTY THAT
//DOCUMENTATION, IF PROVIDED, WILL CONFORM TO THE SUBJECT SOFTWARE.
//

package gov.nasa.jpf.symbc.numeric;

import java.util.HashMap;
import java.util.Map;
import gov.nasa.jpf.Config;

public class MinMax {
//	public static int MININT = -1000000;// Integer.MIN_VALUE;//-10000;
//	public static int MAXINT = 1000000;// Integer.MAX_VALUE;//10000;
//	public static double MINDOUBLE = -10000.0;// -1.0e8;//Double.MIN_VALUE;
//	public static double MAXDOUBLE = 10000.0;// 1.0e8; //Double.MAX_VALUE;

	public static int Debug_no_path_constraints = 0;
	public static int Debug_no_path_constraints_sat = 0;
	public static int Debug_no_path_constraints_unsat = 0;

	public static int UniqueId = 0; // Unique id for each SymbolicInteger or
									// SymbolicReal created

	public static void reset() {
		UniqueId = 0;
	}

	/**
	 * Lower bound on symbolic integer variables.
	 */
	private static int minInt = -1000000;

	/**
	 * Upper bound on symbolic integer variables.
	 */
	private static int maxInt = 1000000;

	/**
	 * Lower bound on symbolic real variables.
	 */
	private static double minDouble = -8;

	/**
	 * Upper bound on symbolic real variables.
	 */
	private static double maxDouble = 7;

	/**
	 * Mapping from variable names to minimum integer values.
	 */
	private static Map<String, Integer> varMinIntMap;

	/**
	 * Mapping from variable names to maximum integer values.
	 */
	private static Map<String, Integer> varMaxIntMap;

	/**
	 * Mapping from variable names to minimum real values.
	 */
	private static Map<String, Double> varMinDoubleMap;

	/**
	 * Mapping from variable names to maximum real values.
	 */
	private static Map<String, Double> varMaxDoubleMap;

	/**
	 * Collects information about the lower and upper bounds on integer and real
	 * symbolic variables, and specific bounds for variables, given by name. The
	 * default bounds are now specified as follows:
	 * 
	 * <pre>
	 * symbolic.min_int = 0
	 * symbolic.max_int = 999
	 * symbolic.min_double = -0.5
	 * symbolic.max_double = 1.5
	 * </pre>
	 * 
	 * To specific a overriding bound for symbolic variables "<code>abc</code>
	 * " and "<code>xyz</code>", we write
	 * 
	 * <pre>
	 * symbolic.min_int_abc = 10
	 * symbolic.max_int_abc = 99
	 * symbolic.min_double_xyz = -0.9
	 * symbolic.max_double_xyz = 0.9
	 * </pre>
	 * 
	 * @param config
	 *            the JPF configuration file
	 */
	public static void collectMinMaxInformation(Config config) {
		int x = config.getInt("symbolic.min_int", Integer.MAX_VALUE);
		if (x != Integer.MAX_VALUE) {
			minInt = x;
		}

		x = config.getInt("symbolic.max_int", Integer.MIN_VALUE);
		if (x != Integer.MIN_VALUE) {
			maxInt = x;
		}
		assert minInt < maxInt : "Illegal integer range";

		double z = config.getDouble("symbolic.min_double", Double.MAX_VALUE);
		if (z != Double.MAX_VALUE) {
			minDouble = z;
		}

		z = config.getDouble("symbolic.max_double", Double.MIN_VALUE);
		if (z != Double.MIN_VALUE) {
			maxDouble = z;
		}
		assert minDouble < maxDouble : "Illegal double range";

		// Collect specific integer bounds by variable name
		varMinIntMap = new HashMap<String, Integer>();
		varMaxIntMap = new HashMap<String, Integer>();
		int prefixLength = "symbolic.min_int_".length();
		for (String k : config.getKeysStartingWith("symbolic.min_int_")) {
			String name = k.substring(prefixLength);
			x = config.getInt(k, Integer.MAX_VALUE);
			if (x != Integer.MAX_VALUE) {
				varMinIntMap.put(name, x);
			}
		}
		for (String k : config.getKeysStartingWith("symbolic.max_int_")) {
			String name = k.substring(prefixLength);
			x = config.getInt(k, Integer.MIN_VALUE);
			if (x != Integer.MIN_VALUE) {
				varMaxIntMap.put(name, x);
			}
		}
		for (String k : varMinIntMap.keySet()) {
			int min = varMinIntMap.get(k);
			int max = maxInt;
			if (varMaxIntMap.containsKey(k)) {
				max = varMaxIntMap.get(k);
			}
			assert min < max : "Illegal range for \"" + k + "\"";
		}
		for (String k : varMaxIntMap.keySet()) {
			int min = minInt;
			int max = varMaxIntMap.get(k);
			if (varMinIntMap.containsKey(k)) {
				min = varMinIntMap.get(k);
			}
			assert min < max : "Illegal range for \"" + k + "\"";
		}

		// Collect specific real bounds by variable name
		varMinDoubleMap = new HashMap<String, Double>();
		varMaxDoubleMap = new HashMap<String, Double>();
		prefixLength = "symbolic.min_double_".length();
		for (String k : config.getKeysStartingWith("symbolic.min_double_")) {
			String name = k.substring(prefixLength);
			z = config.getDouble(k, Double.MAX_VALUE);
			if (z != Double.MAX_VALUE) {
				varMinDoubleMap.put(name, z);
			}
		}
		for (String k : config.getKeysStartingWith("symbolic.max_double_")) {
			String name = k.substring(prefixLength);
			z = config.getDouble(k, Double.MIN_VALUE);
			if (z != Double.MIN_VALUE) {
				varMaxDoubleMap.put(name, z);
			}
		}
		for (String k : varMinDoubleMap.keySet()) {
			double min = varMinDoubleMap.get(k);
			double max = maxDouble;
			if (varMaxDoubleMap.containsKey(k)) {
				max = varMaxDoubleMap.get(k);
			}
			assert min < max : "Illegal range for \"" + k + "\"";
		}
		for (String k : varMaxDoubleMap.keySet()) {
			double min = minDouble;
			double max = varMaxDoubleMap.get(k);
			if (varMinDoubleMap.containsKey(k)) {
				min = varMinDoubleMap.get(k);
			}
			assert min < max : "Illegal range for \"" + k + "\"";
		}

		// Display the bounds collected from the configuration
		System.out.println("symbolic.min_int=" + minInt);
		for (String k : varMinIntMap.keySet()) {
			System.out.println("symbolic.min_int_" + k + "="
					+ varMinIntMap.get(k));
		}
		System.out.println("symbolic.max_int=" + maxInt);
		for (String k : varMaxIntMap.keySet()) {
			System.out.println("symbolic.max_int_" + k + "="
					+ varMaxIntMap.get(k));
		}
		System.out.println("symbolic.min_double=" + minDouble);
		for (String k : varMinDoubleMap.keySet()) {
			System.out.println("symbolic.min_double_" + k + "="
					+ varMinDoubleMap.get(k));
		}
		System.out.println("symbolic.max_double=" + maxDouble);
		for (String k : varMaxDoubleMap.keySet()) {
			System.out.println("symbolic.max_double_" + k + "="
					+ varMaxDoubleMap.get(k));
		}
	}

	/**
	 * Return the minimum integer value that a given variable can assume.
	 * 
	 * @param varname the name of the variable
	 * @return the minimum value of the variable
	 */
	public static int getVarMinInt(String varname) {
		if (varname.endsWith("_SYMINT")) {
			varname = varname.replaceAll("_[0-9][0-9]*_SYMINT", "");
		}
		return varMinIntMap.containsKey(varname) ? varMinIntMap.get(varname) : minInt;
	}

	/**
	 * Return the maximum integer value that a given variable can assume.
	 * 
	 * @param varname the name of the variable
	 * @return the maximum value of the variable
	 */
	public static int getVarMaxInt(String varname) {
		if (varname.endsWith("_SYMINT")) {
			varname = varname.replaceAll("_[0-9][0-9]*_SYMINT", "");
		}
		return varMaxIntMap.containsKey(varname) ? varMaxIntMap.get(varname) : maxInt;
	}
	
	/**
	 * Return the minimum real value that a given variable can assume.
	 * 
	 * @param varname the name of the variable
	 * @return the minimum value of the variable
	 */
	public static double getVarMinDouble(String varname) {
		if (varname.endsWith("_SYMINT")) {
			varname = varname.replaceAll("_[0-9][0-9]*_SYMINT", "");
		}
		return varMinDoubleMap.containsKey(varname) ? varMinDoubleMap.get(varname) : minDouble;
	}
	
	/**
	 * Return the maximum real value that a given variable can assume.
	 * 
	 * @param varname the name of the variable
	 * @return the maximum value of the variable
	 */
	public static double getVarMaxDouble(String varname) {
		if (varname.endsWith("_SYMINT")) {
			varname = varname.replaceAll("_[0-9][0-9]*_SYMINT", "");
		}
		return varMaxDoubleMap.containsKey(varname) ? varMaxDoubleMap.get(varname) : maxDouble;
	}

}
