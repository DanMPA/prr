package prr.core;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;

import prr.core.client.Client;
import prr.core.client.ClientLevel;
import prr.core.exception.DuplicateEntityKeyException;
import prr.core.exception.KeyFormattingExemption;
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
	private Collection<Communication> _communication;

	public Network() {
		this._clients = new TreeMap<String, Client>();
		this._terminals = new TreeMap<String, Terminal>();
	}

	/**
	 * Registers a client.
	 * 
	 * @param key       Clients uniq identifier.
	 * @param name      Clients name.
	 * @param taxNumber Client taxNumber
	 * @throws DuplicateEntityKeyException if the client exist in the network
	 */
	public void registerClient(String key, String name, int taxNumber) throws DuplicateEntityKeyException {
		if (_clients.containsKey(key))
			throw new DuplicateEntityKeyException(key);
		_clients.put(key, new Client(key, name, taxNumber, ClientLevel.NORMAL, true));
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

	/**
	 * Gets all the clients in the network.
	 * 
	 * @return Collection<String> of clients in there string format.
	 */
	public Collection<String> showAllClients() {
		return _clients.values().stream().map(e -> e.toString()).toList();
	}

	/**
	 * Gets all the notifications of a specific Client.
	 * 
	 * @param key
	 * @return Collection<String>
	 * @throws UnknownKeyException if client don't exist in network.
	 */
	public Collection<String> showNotifications(String key) throws UnknownKeyException {
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
	public boolean toggleNotificationStatus(String key, boolean status) throws UnknownKeyException {
		Client client = findClient(key);
		if (client.is_receiveNotification() == status) {
			return false;
		} else {
			client.set_receiveNotification(status);
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
		return Pattern.matches("[0-9]{6}", id);
	}

	/**
	 * @param key      Terminal Id
	 * @param clientID Terminal owner Id
	 * @param type     Terminal type
	 * @return Terminal
	 * @throws UnknownKeyException
	 * @throws DuplicateEntityKeyException
	 * @throws KeyFormattingExemption
	 */
	public Terminal registerTerminal(String key, String clientID, String type) throws UnknownKeyException,
			DuplicateEntityKeyException, KeyFormattingExemption {
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

	/**
	 * Gets all terminals in their string format.
	 * 
	 * @return Collection<String>
	 */
	public Collection<String> showAllTerminals() {
		return _terminals.values().stream()
				.map(e -> e.toString())
				.toList();
	}

	/**
	 * Gets all terminals in their string format if they have no communications.
	 * 
	 * @return Collection<String>
	 */
	public Collection<String> showTerminalsWithoutCommunications() {
		return _terminals.values().stream()
				.filter(e -> e.numberCommunications() == 0)
				.map(e -> e.toString())
				.toList();
	}

	/**
	 * Read text input file and create corresponding domain entities.
	 * 
	 * @param filename name of the text input file
	 * @throws UnrecognizedEntryException if some entry is not correct
	 * @throws IOException                if there is an IO error while processing
	 *                                    the text file
	 */
	void importFile(String filename) throws UnrecognizedEntryException, IOException {
		new Parser(this).parseFile(filename);
	}
}
