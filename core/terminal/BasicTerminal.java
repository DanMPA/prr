package prr.core.terminal;

import prr.core.client.Client;

public class BasicTerminal extends Terminal{
    
    public BasicTerminal(String _id, Client _owner) {
        super(_id, _owner);
    }

    public void makeVideoCall(){        
    }
    
    boolean accepVideoCall(){
        return false;
    }

	@Override
	public String toString() {
		String temp = super.toString();
		return String.join("|", "BASIC",temp); 
	}
}
