package io.booogyboooo.nightclient.nightscript.util;

import java.util.regex.Pattern;

import io.booogyboooo.nightclient.NightClientRecode;
import io.booogyboooo.nightclient.nightscript.NightScript;
import io.booogyboooo.nightclient.nightscript.general.LineType;
import io.booogyboooo.nightclient.nightscript.general.LineWrapper;

public class DataParser {
	public static Object parseData(NightScript nightscript, String dat) {
		dat = dat.stripLeading().stripTrailing();
		if (dat.length() == 0) {
			return null;
		}
		if (Character.isDigit(dat.charAt(0))) {
			String num = dat.strip();
			if (isNumber(num)) {
				return Double.parseDouble(num);
			} else {
				NightClientRecode.LOGGER.error("Cant parse number: " + num);
			}
		} else if (dat.startsWith("\"")) {
			return dat.substring(1, dat.length() - 1);
		} else if (isAlphabetic(dat)) {
			String name = dat.strip();
			Object val = nightscript.retrive(name);
			if (val != null) {
				return val;
			} else {
				NightClientRecode.LOGGER.error("Cant parse var: " + name);
			}
		} else if (dat.startsWith("(")) {
			String pdat = dat.substring(1, dat.length() - 1);
			LogicType logicType = LogicType.getLogicType(pdat);
			if (logicType == null) {
				ArithmeticType arithmeticType = ArithmeticType.getArithmeticType(pdat);
				String left = pdat.split(Pattern.quote(arithmeticType.getOperator()))[0];
				String right = pdat.split(Pattern.quote(arithmeticType.getOperator()))[1];
				Double leftNumber = (Double) DataParser.parseData(nightscript, left);
				Double rightNumber = (Double) DataParser.parseData(nightscript, right);
				return arithmeticType.eval(leftNumber, rightNumber);
			} else {
				String left = pdat.split(Pattern.quote(logicType.getOperator()))[0];
				String right = pdat.split(Pattern.quote(logicType.getOperator()))[1];
				Double leftNumber = (Double) DataParser.parseData(nightscript, left);
				Double rightNumber = (Double) DataParser.parseData(nightscript, right);
				return logicType.eval(leftNumber, rightNumber);
			}
		} else if (dat.startsWith("[")) {
			String pdat = dat.substring(1, dat.length() - 1);
			LineWrapper line = new LineWrapper(pdat, 0);
			LineType type = line.getLineType();
			return type.execute(line.getLine(), nightscript);
		} else {
			NightClientRecode.LOGGER.error("Cant parse data: " + dat);
		}
		return null;
	}
	
	private static boolean isNumber(String str) {
	    try {
	    	Double.parseDouble(str);
	        return true;
	    } catch (NumberFormatException e) {
	        return false;
	    }
	}
	
	public static boolean isAlphabetic(String str) {
	    return str.chars().allMatch(Character::isLetter);
	}

}