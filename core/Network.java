package prr.core;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import prr.core.client.Client;
import prr.core.client.ClientLevel;
import prr.core.communication.Communication;
import prr.core.exception.DuplicateEntityKeyException;
import prr.core.exception.KeyFormattingExemption;
import prr.core.exception.UnavailableEntity;
import prr.core.exception.UnknownKeyException;
import prr.core.exception.UnrecognizedEntryException;
import prr.core.terminal.BasicTerminal;
import prr.core.terminal.FancyTerminal;
import prr.core.terminal.Terminal;

/**
 * Class Network that represents a amazing Network named prr a company by cats.
 */
public class Network implements Serializable {

	/** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

	private Map<String, Client> _clients;
	private Map<String, Terminal> _terminals;
	private List<Communication> _allCommunication;

	public Network() {
		this._clients = new TreeMap<>();
		this._terminals = new TreeMap<>();
		this._allCommunication = new ArrayList<>();
	}

	/**
	 * Registers a client.
	 * 
	 * @param key       Clients uniq identifier.
	 * @param name      Clients name.
	 * @param taxNumber Client taxNumber
	 * @throws DuplicateEntityKeyException if the client exist in the network
	 */
	public void registerClient(String key, String name, int taxNumber)
			throws DuplicateEntityKeyException {
		if (_clients.containsKey(key))
			throw new DuplicateEntityKeyException(key);
		_clients.put(key,
				new Client(key, name, taxNumber, ClientLevel.NORMAL, true));
	}

	/**
	 * Finds a Client in the network.
	 * 
	 * @param key
	 * @return Client
	 * @throws UnknownKeyException if client don't exists in the network.
	 */
	public Client findClient(String key) throws UnknownKeyException {
		if (_clients.containsKey(key))
			return _clients.get(key);
		throw new UnknownKeyException(key);
	}

	/**
	 * Shows a client by a given key, returns a string representation.
	 * 
	 * @param key
	 * @return String
	 * @throws UnknownKeyException if client don't exist in network.
	 */
	public String showClient(String key) throws UnknownKeyException {
		return findClient(key).toString();
	}

	
	public Collection<Client> showClients(Comparator<Client> comparatorOfClient) {
		return _clients.values().stream()
					.sorted(comparatorOfClient)
					.toList();
	}

	// Dont know if working at the moment still need to see if it sorts.
	public Collection<Client> showClients(Comparator<Client> comparatorOfClient,Predicate<Client> clientFilter) {
		return _clients.values().stream()
				.filter(clientFilter)
				.sorted(comparatorOfClient)
				.toList();
	}
	
	/**
	 * Gets all the notifications of a specific Client.
	 * 
	 * @param key
	 * @return Collection<String>
	 * @throws UnknownKeyException if client don't exist in network.
	 */
	public Collection<String> showNotifications(String key)
			throws UnknownKeyException {
		return findClient(key).getNotifications();
	}

	/**
	 * Toggle the client status to given status.
	 * 
	 * @param key
	 * @param status
	 * @return boolean true if successful in changing status.
	 * @throws UnknownKeyException if client don't exist in network.
	 */
	public boolean toggleNotificationStatus(String key, boolean status)
			throws UnknownKeyException {
		Client client = findClient(key);
		if (client.isReceiveNotification() == status) {
			return false;
		} else {
			client.setReceiveNotification(status);
			return true;
		}
	}

	/**
	 * Finds a Terminal by given key.
	 * 
	 * @param key
	 * @return Terminal
	 * @throws UnknownKeyException if terminal don't exist in network.
	 */
	public Terminal findTerminal(String key) throws UnknownKeyException {
		if (_terminals.containsKey(key)) {
			return _terminals.get(key);
		}
		throw new UnknownKeyException(key);
	}

	/**
	 * Verifies if a terminal exist in the network.
	 * 
	 * @param key
	 * @throws UnknownKeyException if terminal don't exist in network.
	 */
	public void terminalExists(String key) throws UnknownKeyException {
		findTerminal(key);
	}

	/**
	 * Verifies if Terminal id is formatted correctly
	 * 
	 * @param id
	 * @return boolean
	 * @throws UnknownKeyException if terminal key is not a number and 6 digits
	 *                             long.
	 */
	public boolean validTerminalID(String id) throws UnknownKeyException {
		return Pattern.matches("\\d{6}", id);
	}

	/**
	 * Registers a terminal based on their type
	 * 
	 * @param key      Terminal Id
	 * @param clientID Terminal owner Id
	 * @param type     Terminal type
	 * @return Terminal
	 * @throws UnknownKeyException         if the key is not valid
	 * @throws DuplicateEntityKeyException if the key already exists
	 * @throws KeyFormattingExemption      if the key is not valid
	 */
	public Terminal registerTerminal(String key, String clientID, String type)
			throws UnknownKeyException, DuplicateEntityKeyException,
			KeyFormattingExemption {
		if (validTerminalID(key)) {
			Terminal tempTerminal;
			switch (type) {
			case "BASIC":
				tempTerminal = new BasicTerminal(key, findClient(clientID));
				break;
			case "FANCY":
				tempTerminal = new FancyTerminal(key, findClient(clientID));
				break;
			default:
				return null;
			}
			findClient(clientID).addTerminal(tempTerminal);
			if (_terminals.containsKey(key)) {
				throw new DuplicateEntityKeyException(key);
			}
			_terminals.put(key, tempTerminal);
			return tempTerminal;
		} else {
			throw new KeyFormattingExemption(key);
		}

	}


	public Collection<Terminal> showTerminals(Comparator<Terminal> coparatorOfTerminals) {
		return _terminals.values().stream()
				.sorted(coparatorOfTerminals)
				.toList();
	}

	public Collection<Terminal> showTerminal(Comparator<Terminal> coparatorOfTerminals,Predicate<Terminal> terminalFilter) {
		return _terminals.values().stream()
				.filter(terminalFilter)
				.sorted(coparatorOfTerminals)
				.toList();
	}

	public Collection<Communication> showCommunications(Comparator<Communication> compaterOfCommuncations){
		return _allCommunication.stream()
				.sorted(compaterOfCommuncations)
				.toList();
	}

	public Collection<Communication> showCommunications(String clientKey,Comparator<Communication> compaterOfCommuncations,Function<Terminal,Stream<Communication>> terminalCommunications) throws UnknownKeyException {
		return findClient(clientKey).getCommunications(terminalCommunications).stream()
				.sorted(compaterOfCommuncations)
				.toList();
	}

	public void generateTextCommunication(Terminal origin,String destinationKey,String message) throws UnknownKeyException, UnavailableEntity{
		Terminal destenation = findTerminal(destinationKey);
		if(destenation.canReciveCommunication()){
			_allCommunication.add(origin.makeTexCommunication(destenation, message));
		} else{
			throw new UnavailableEntity(destinationKey);
		}
	}

	public void generateInteractiveCommunication(Terminal origin,String destinationKey){

	}

	/**
	 * Read text input file and create corresponding domain entities.
	 * 
	 * @param filename name of the text input file
	 * @throws UnrecognizedEntryException if some entry is not correct
	 * @throws IOException                if there is an IO error while
	 *                                    processing the text file
	 */
	void importFile(String filename)
			throws UnrecognizedEntryException, IOException {
		new Parser(this).parseFile(filename);
	}
}
