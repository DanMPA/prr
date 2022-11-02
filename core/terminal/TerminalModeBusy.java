package prr.core.terminal;

import java.io.Serializable;

public class TerminalModeBusy implements TerminalMode,Serializable{
	private static final long serialVersionUID = 202208091753L;
	@Override
	public boolean canStartCommunication() {
		return false;
	}

	@Override
	public boolean canReciveCommunication() {
		return false;
	}

	@Override
	public boolean canEndCommunication() {
		return true;
	}

	@Override
	public String getName() {
		return "BUSY";
	}
	
	
}
