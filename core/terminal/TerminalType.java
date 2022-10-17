package prr.core.terminal;

public enum TerminalType {
	BASIC,
	FANCY;

	public String toString(){
        switch(this){
        case BASIC :
            return "Baisc";
        case FANCY :
            return "Fancy";
        }
        return null;
    }
}
