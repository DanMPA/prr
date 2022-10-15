package prr.core;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Vector;
import java.io.IOException;

import prr.app.exception.DuplicateClientKeyException;
import prr.app.exception.UnknownClientKeyException;
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

  public Client findClient(String key) throws UnknownClientKeyException{
    for (Client client : _clients) {
      if(client.get_key() == key){
        return client;
      }
    }
    throw new UnknownClientKeyException(key);
  }

  public String showClient(String key) throws UnknownClientKeyException{
    return findClient(key).toString();
  }

  public Collection<String> showAllClients(){
    List<String> allClients = new Vector<>(); 
    for (Client client : _clients) {
      allClients.add(client.toString());
    }
    return allClients;
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

