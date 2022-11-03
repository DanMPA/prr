package prr.core.terminal;

import java.io.Serializable;

public class TerminalModeSilence implements TerminalMode, Serializable {
	private static final long serialVersionUID = 202208091753L;

	@Override
	public boolean canStartCommunication() {
		return true;
	}

	@Override
	public boolean canReciveCommunication() {
		return false;
	}

	@Override
	public boolean canEndCommunication() {
		return false;
	}

	@Override
	public String getName() {
		return "SILENCE";
	}

}
