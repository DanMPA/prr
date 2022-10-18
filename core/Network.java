package prr.core;

import java.io.IOException;
import java.io.Serializable;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Vector;
import java.util.regex.Pattern;

import prr.core.client.Client;
import prr.core.client.ClientLevel;
import prr.core.exception.DuplicateEntityKeyException;
import prr.core.exception.KeyFormattingExeption;
import prr.core.exception.UnknowKeyException;
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

	private Collection<Client> _clients;
	private Collection<Terminal> _terminals;
	private Collection<Communication> _communication;


	public Network() {
		this._clients = new HashSet<Client>();
		this._terminals = new HashSet<Terminal>();

	}

	
	/** 
	 * @param key
	 * @param name
	 * @param taxNumber
	 * @return boolean
	 * @throws DuplicateEntityKeyException
	 */
	public boolean registerClient(String key, String name, int taxNumber) throws DuplicateEntityKeyException {
		Client tempClient = new Client(key, name, taxNumber, ClientLevel.NORMAL, true);
		if (!_clients.add(tempClient)) {
			throw new DuplicateEntityKeyException(key);
		}
		return true;
	}

	
	/** 
	 * @param key
	 * @return Client
	 * @throws UnknowKeyException
	 */
	public Client findClient(String key) throws UnknowKeyException {
		for (Client client : _clients) {
			if (client.get_key().equals(key)) {
				return client;
			}
		}
		throw new UnknowKeyException(key);
	}

	
	/** 
	 * @param key
	 * @return String
	 * @throws UnknowKeyException
	 */
	public String showClient(String key) throws UnknowKeyException {
		return findClient(key).toString();
	}

	
	/** 
	 * @return Collection<String>
	 */
	public Collection<String> showAllClients() {
		List<String> allClients = new Vector<>();
		for (Client client : _clients) {
			allClients.add(client.toString());
		}
		return allClients;
	}

	
	/** 
	 * @param key
	 * @return Collection<String>
	 * @throws UnknowKeyException
	 */
	public Collection<String> showNotificaions(String key) throws UnknowKeyException {
		Client tempClient = findClient(key);
		return tempClient.getNotifcations();
	}

	
	/** 
	 * @param key
	 * @param status
	 * @return boolean
	 * @throws UnknowKeyException
	 */
	public boolean toggleNotificationStatus(String key, boolean status) throws UnknowKeyException {
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
	 * @throws UnknowKeyException
	 */
	public Terminal findTerminal(String key) throws UnknowKeyException {
		for (Terminal tempTerminal : _terminals) {
			if (tempTerminal.get_id().equals(key)) {
				return tempTerminal;
			}
		}
		throw new UnknowKeyException(key);
	}

	
	/** 
	 * @param id
	 * @return boolean
	 * @throws UnknowKeyException
	 */
	public boolean validTerminalID(String id) throws UnknowKeyException {
		return Pattern.matches("[0-9]{6}", id);
	}

	
	/** 
	 * @param key
	 * @param clientID
	 * @param type
	 * @return Terminal
	 * @throws UnknowKeyException
	 * @throws DuplicateEntityKeyException
	 * @throws KeyFormattingExeption
	 */
	public Terminal registerTerminal(String key, String clientID, String type) throws UnknowKeyException,
			DuplicateEntityKeyException, KeyFormattingExeption {
		if (validTerminalID(key)) {
			Client tempClient = findClient(clientID);
			Terminal tempTerminal;
			switch (type) {
				case "BASIC":
					tempTerminal = new BasicTerminal(key, tempClient);
					if (!_terminals.add(tempTerminal)) {
						throw new DuplicateEntityKeyException(key);
					}
					return tempTerminal;
				case "FANCY":
					tempTerminal = new FancyTerminal(key, tempClient);
					if (!_terminals.add(tempTerminal)) {
						throw new DuplicateEntityKeyException(key);
					}
					return tempTerminal;
				default:
					return null;
			}
		} else {
			throw new KeyFormattingExeption(key);
		}

	}

	
	/** 
	 * @return Collection<String>
	 */
	public Collection<String> showAllTerminals() {
		List<String> allTerminals = new Vector<>();
		for (Terminal terminal : _terminals) {
			allTerminals.add(terminal.toString());
		}
		return allTerminals;
	}

	
	/** 
	 * @return Collection<String>
	 */
	public Collection<String> showTerminalsWithoutCommunications() {
		List<String> terminalsWithoutCommunications = new Vector<>();
		for (Terminal terminal : _terminals) {
			if (terminal.numberCommunications() == 0) {
				terminalsWithoutCommunications.add(terminal.toString());
			}
		}
		return terminalsWithoutCommunications;
	}

	/**
	 * Read text input file and create corresponding domain entities.
	 * 
	 * @param filename name of the text input file
	 * @throws UnrecognizedEntryException if some entry is not correct
	 * @throws IOException                if there is an IO erro while processing
	 *                                    the text file
	 */
	void importFile(String filename) throws UnrecognizedEntryException, IOException /* FIXME maybe other exceptions */ {
		// FIXME implement method
	}
}
