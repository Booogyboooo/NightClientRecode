package io.booogyboooo.nightclient.nightscript.util;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public enum ArithmeticType {
	ADD("+"), SUBTRACT("-"), MULTIPLY("*"), POW("^"), DIVIDE("/"), REMAINDER("%");
	
	private String symbol;
	private Map<String, BiFunction<Double, Double, Double>> operations = new HashMap<>();

	private ArithmeticType(String symbol) {
		this.symbol = symbol;
		this.operations.put("+", (a, b) -> a + b);
		this.operations.put("-", (a, b) -> a - b);
		this.operations.put("*", (a, b) -> a * b);
		this.operations.put("^", (a, b) -> Math.pow(a, b));
		this.operations.put("/", (a, b) -> a / b);
		this.operations.put("%", (a, b) -> a % b);
	}
	
	public String getOperator() {
		return this.symbol;
	}
	
	public Double eval(double a, double b) {
		return this.operations.get(this.symbol).apply(a, b);
	}
	
	public static ArithmeticType getArithmeticType(String math) {
		for (ArithmeticType type : ArithmeticType.values()) {
			if (math.contains(type.getOperator())) {
				return type;
			}
		}
		return null;
	}
}
