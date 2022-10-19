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
import prr.core.exception.KeyFormattingExeption;
import prr.core.exception.UnknownKeyException;
import prr.core.exception.UnrecognizedEntryException;
import prr.core.terminal.BasicTerminal;
import prr.core.terminal.FancyTerminal;
import prr.core.terminal.Terminal;

/**
 * Class Store implements a store.
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
	 * 
	 * @param key
	 * @param name
	 * @param taxNumber
	 * @return boolean
	 * @throws DuplicateEntityKeyException
	 */
	public void registerClient(String key, String name, int taxNumber) throws DuplicateEntityKeyException {
		if (!_clients.containsKey(key)) {
			_clients.put(key, new Client(key, name, taxNumber, ClientLevel.NORMAL, true));
		} else {
			throw new DuplicateEntityKeyException(key);
		}
	}

	/**
	 * @param key
	 * @return Client
	 * @throws UnknownKeyException
	 */
	public Client findClient(String key) throws UnknownKeyException {
		if (_clients.containsKey(key)) {
			return _clients.get(key);
		}
		throw new UnknownKeyException(key);
	}

	/**
	 * @param key
	 * @return String
	 * @throws UnknownKeyException
	 */
	public String showClient(String key) throws UnknownKeyException {
		return findClient(key).toString();
	}

	/**
	 * @return Collection<String>
	 */
	public Collection<String> showAllClients() {
		return _clients.values().stream().map(e -> e.toString()).toList();
	}

	/**
	 * @param key
	 * @return Collection<String>
	 * @throws UnknownKeyException
	 */
	public Collection<String> showNotificaions(String key) throws UnknownKeyException {
		return findClient(key).getNotifcations();
	}

	/**
	 * @param key
	 * @param status
	 * @return boolean
	 * @throws UnknownKeyException
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
	 * @param key
	 * @return Terminal
	 * @throws UnknownKeyException
	 */
	public Terminal findTerminal(String key) throws UnknownKeyException {
		if (_terminals.containsKey(key)) {
			return _terminals.get(key);
		}
		throw new UnknownKeyException(key);
	}

	/**
	 * @param id
	 * @return boolean
	 * @throws UnknownKeyException
	 */
	public boolean validTerminalID(String id) throws UnknownKeyException {
		return Pattern.matches("[0-9]{6}", id);
	}

	/**
	 * @param key      Terminal Id
	 * @param clientID Terminal ownerd Id
	 * @param type     Terminal type
	 * @return Terminal
	 * @throws UnknownKeyException
	 * @throws DuplicateEntityKeyException
	 * @throws KeyFormattingExeption
	 */
	public Terminal registerTerminal(String key, String clientID, String type) throws UnknownKeyException,
			DuplicateEntityKeyException, KeyFormattingExeption {
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
			findClient(clientID).addTermina(tempTerminal);
			if (_terminals.containsKey(key)) {
				throw new DuplicateEntityKeyException(key);
			}
			_terminals.put(key, tempTerminal);
			return tempTerminal;
		} else {
			throw new KeyFormattingExeption(key);
		}

	}

	/**
	 * @return Collection<String>
	 */
	public Collection<String> showAllTerminals() {
		return _terminals.values().stream()
								  .map(e -> e.toString())
								  .toList();
		// List<String> allTerminals = new ArrayList<>();
		// for (Terminal terminal : _terminals.values()) {
		// allTerminals.add(terminal.toString());
		// }
		// return allTerminals;
	}

	/**
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
	 * @throws IOException                if there is an IO erro while processing
	 *                                    the text file
	 */
	void importFile(String filename) throws UnrecognizedEntryException, IOException {
		new Parser(this).parseFile(filename);
	}
}
