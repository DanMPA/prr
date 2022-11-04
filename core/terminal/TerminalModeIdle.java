package prr.core.terminal;

import java.io.Serializable;

public class TerminalModeIdle implements TerminalMode, Serializable {
	private static final long serialVersionUID = 202208091753L;

	@Override
	public boolean canStartCommunication() {
		return true;
	}

	@Override
	public boolean canReciveCommunication() {
		return true;
	}

	@Override
	public boolean canEndCommunication() {
		return false;
	}

	@Override
	public String toString() {
		return "IDLE";
	}
}
