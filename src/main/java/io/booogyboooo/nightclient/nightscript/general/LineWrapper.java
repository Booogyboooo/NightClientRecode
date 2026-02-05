package io.booogyboooo.nightclient.nightscript.general;

public class LineWrapper {
	private int depth;
	private String line;

	public LineWrapper(String line, int depth) {
		this.line = line;
		this.depth = depth;
	}
	
	public String getLine() {
		return this.line;
	}
	
	public LineType getLineType() {
		return LineType.getType(this.line);
	}
	
	public int getDepth() {
		return depth;
	}
}