package prr.core.terminal;

import java.io.Serializable;

public interface TerminalMode extends Serializable{
	abstract boolean canStartCommunication();
	abstract boolean canReciveCommunication();
	abstract boolean canEndCommunication();
	abstract String toString();
}
