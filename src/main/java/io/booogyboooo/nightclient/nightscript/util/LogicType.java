package io.booogyboooo.nightclient.nightscript.util;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiPredicate;

public enum LogicType {
	EQUALS("=="), LESSTHAN("<<"), GREATERTHAN(">>"), EQUALORGREATER(">="), EQUALORLESS("<="), NOTEQUAL("!=");
	
	private String symbol;
	private Map<String, BiPredicate<Double, Double>> operations = new HashMap<>();

	private LogicType(String symbol) {
		this.symbol = symbol;
		this.operations.put("==", (a, b) -> a.equals(b));
		this.operations.put("!=", (a, b) -> !a.equals(b));
		this.operations.put("<=", (a, b) -> a <= b);
		this.operations.put(">=", (a, b) -> a >= b);
		this.operations.put("<<", (a, b) -> a < b);
		this.operations.put(">>", (a, b) -> a > b);
	}
	
	public String getOperator() {
		return this.symbol;
	}
	
	public Integer eval(double a, double b) {
		return this.operations.get(this.symbol).test(a, b) ? 1 : 0; // 1 = true | 0 = false
	}
	
	public static LogicType getLogicType(String logic) {
		for (LogicType type : LogicType.values()) {
			if (logic.contains(type.getOperator())) {
				return type;
			}
		}
		return null;
	}
}
