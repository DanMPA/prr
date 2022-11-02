package prr.core.terminal;

public enum TerminalMode {
    BUSY,
    IDLE,
    SILENT,
    OFF;    

	public String toString(){
		switch (this){
			case BUSY:
				return "BUSY";
			case IDLE:
				return "IDLE";
			case SILENT:
				return "SILENCE";
			case  OFF:
				return "OFF";
			default:
				return null;
		}
	}
}
