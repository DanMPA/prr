package prr.core.terminal;

import java.io.Serializable;

import prr.core.client.Client;


/**
 * Abstract terminal.
 */
abstract public class Terminal implements Serializable /* FIXME maybe addd more interfaces */{

  /** Serial number for serialization. */
  private static final long serialVersionUID = 202208091753L;
	
	private String _id;
	private double _debt;
	private double _payments;
	private TerminalMode _mode;
  private Terminal[] _frindsTerminal;
  private Client _owner;


  
  /**
   * Checks if this terminal can start a new communication.
   *
   * @return true if this terminal is neither off neither busy, false otherwise.
   **/
  public boolean canStartCommunication(){
    return true;
  }
  
  /**
   * Checks if this terminal can end the current interactive communication.
   *
   * @return true if this terminal is busy (i.e., it has an active interactive communication) and
   *          it was the originator of this communication.
   **/
  public boolean canEndCommunication() {
    return true;
  }
  public void makeSMS(Terminal to,String message){

  }
  public void acceptSMS(Terminal from){

  }

  public void makeVoiceCall(Terminal to){

  }

  public void acceptVoiceCall(Terminal from){

  } 

  public void makeVideoCall(Terminal to){

  }

  public void acceptVideoCall(Terminal from){

  } 

  public void endOngoingCommunication(int size){

  }

  public boolean setToIdle(){
    return false;
  }

  public boolean setToSilent(){
    return false;
  }

  public boolean setToOff(){
    return false;
  }

  public boolean turnOff(){
    return false;
  }

}
