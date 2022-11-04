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
	public String getMode(){
		return this._mode.toString();
	}

}
