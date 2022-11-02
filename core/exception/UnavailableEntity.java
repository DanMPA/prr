package prr.core.exception;

import prr.core.terminal.TerminalMode;
public class UnavailableEntity extends Exception{
	private TerminalMode  _mode;

	public UnavailableEntity(String destination) {
        super(destination);
    }
	public UnavailableEntity(TerminalMode mode) {
        _mode = mode;
    }
	public TerminalMode getMode(){
		return this._mode;
	}

}
