package prr.core.terminal;

public interface TerminalMode{
	abstract boolean canStartCommunication();
	abstract boolean canReciveCommunication();
	abstract boolean canEndCommunication();
	abstract String toString();
}
