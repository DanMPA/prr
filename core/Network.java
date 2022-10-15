package prr.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.io.IOException;

import prr.app.exception.DuplicateClientKeyException;
import prr.core.client.Client;
import prr.core.client.ClientLevel;
import prr.core.exception.UnrecognizedEntryException;
import prr.core.terminal.Terminal;

/**
 * Class Store implements a store.
 */
public class Network implements Serializable {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 202208091753L;

  private Collection<Client> _clients;
  private Collection<Terminal> _terminals;
  private Collection<Communication> _communication;


  public Network() {
    this._clients = new HashSet<Client>();
  }

  public boolean registerClient(String key, String name, int taxNumber) throws DuplicateClientKeyException{
    Client tempClient = new Client(key,name,taxNumber,ClientLevel.NORMAL,true);
    if(!_clients.add(tempClient)){
      throw new DuplicateClientKeyException(key);
    }
    
    return true;

  }



  /**
   * Read text input file and create corresponding domain entities.
   * 
   * @param filename name of the text input file
   * @throws UnrecognizedEntryException if some entry is not correct
   * @throws IOException if there is an IO erro while processing the text file
   */
  void importFile(String filename) throws UnrecognizedEntryException, IOException /* FIXME maybe other exceptions */  {
    //FIXME implement method
  }
}

