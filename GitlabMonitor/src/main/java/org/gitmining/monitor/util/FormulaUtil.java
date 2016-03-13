package org.gitmining.monitor.util;

import java.util.HashMap;
import java.util.Map;

public class FormulaUtil {
	private FormulaUtil() {
	}

	public static double calFormula(String formula, Map<String, Double> vars) {
		try {
			double result = 0.0;
			if (formula == null) {
				return result;
			}
			int len = formula.length();
			int level = 0;
			String validOperators = "+-*/%^";
			for (int i = len - 1; i >= 0; i--) {
				if (formula.charAt(i) == '(') {
					level++;
				} else if (formula.charAt(i) == ')') {
					level--;
				} else if (formula.charAt(i) == '+' && level == 0) {
					if (i != 0
							&& !validOperators.contains(Character
									.toString(formula.charAt(i - 1)))) {
						return calFormula(formula.substring(0, i),vars)
								+ calFormula(formula.substring(i + 1),vars);
					}
				} else if (formula.charAt(i) == '-' && level == 0) {
					if (i != 0
							&& !validOperators.contains(Character
									.toString(formula.charAt(i - 1)))) {
						return calFormula(formula.substring(0, i),vars)
								- calFormula(formula.substring(i + 1),vars);
					}
				}
			}
			for (int i = len - 1; i >= 0; i--) {
				if (formula.charAt(i) == '(') {
					level++;
				} else if (formula.charAt(i) == ')') {
					level--;
				} else if (formula.charAt(i) == '*' && level == 0) {
					if (i != 0
							&& !validOperators.contains(Character
									.toString(formula.charAt(i - 1)))) {
						return calFormula(formula.substring(0, i),vars)
								* calFormula(formula.substring(i + 1),vars);
					}
				} else if (formula.charAt(i) == '/' && level == 0) {
					if (i != 0
							&& !validOperators.contains(Character
									.toString(formula.charAt(i - 1)))) {
						return calFormula(formula.substring(0, i),vars)
								/ calFormula(formula.substring(i + 1),vars);
					}
				} else if (formula.charAt(i) == '%' && level == 0) {
					if (i != 0
							&& !validOperators.contains(Character
									.toString(formula.charAt(i - 1)))) {
						return calFormula(formula.substring(0, i),vars)
								% calFormula(formula.substring(i + 1),vars);
					}
				}
			}

			for (int i = 0; i < len; i++) {
				if (formula.charAt(i) == '(') {
					level++;
				} else if (formula.charAt(i) == ')') {
					level--;
				} else if (formula.charAt(i) == '^' && level == 0) {
					if (i != 0
							&& !validOperators.contains(Character
									.toString(formula.charAt(i - 1)))) {
						return Math.pow(calFormula(formula.substring(0, i),vars),
								calFormula(formula.substring(i + 1), vars));
					}
				}
			}

			if (formula.charAt(0) == '+') {
				return calFormula(formula.substring(1),vars);
			} else if (formula.charAt(0) == '-') {
				return 0 - calFormula(formula.substring(1),vars);
			} else if (formula.charAt(0) == '('
					&& formula.charAt(formula.length() - 1) == ')') {
				return calFormula(formula.substring(1, len - 1),vars);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		String tmp = formula.trim();
		if(vars.containsKey(tmp)){
			return vars.get(tmp);
		}
		return Double.parseDouble(formula);
	}
}
