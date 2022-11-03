package prr.core.terminal;

public enum TerminalType {
	BASIC, FANCY;

	@Override
	public String toString() {
		switch (this) {
		case BASIC:
			return "Basic";
		case FANCY:
			return "Fancy";
		default:
			return "";
		}

	}
}
