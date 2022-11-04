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
import prr.core.communication.Communication;
import prr.core.exception.DuplicateEntityKeyException;
import prr.core.exception.InvalidCommunicationExpextion;
import prr.core.exception.KeyFormattingExemption;
import prr.core.exception.UnavailableEntity;
import prr.core.exception.UnknownKeyException;
import prr.core.exception.UnrecognizedEntryException;
import prr.core.exception.UnsupportedCommunicationExceptionDestination;
import prr.core.exception.UnsupportedCommunicationExceptionOrigin;
import prr.core.notification.Notification;
import prr.core.terminal.Terminal;
import prr.core.terminal.BasicTerminal;
import prr.core.terminal.FancyTerminal;

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
		_clients.put(key, new Client(key, name, taxNumber, true));
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
	 * Show all clients, sorted by the given comparator.
	 * 
	 * @param comparatorOfClient A comparator that will be used to sort the
	 *                           clients.
	 * @return A collection of clients.
	 */
	public Collection<Client> showClients(
			Comparator<Client> comparatorOfClient) {
		return ShowEntities.showAll(_clients.values(), comparatorOfClient);
	}

	/**
	 * Show all clients, sorted by the given comparator, filtered by the given
	 * predicate.
	 * 
	 * @param comparatorOfClient A comparator that will be used to sort the
	 *                           clients.
	 * @param clientFilter       a predicate that filters the clients to be
	 *                           shown.
	 * @return A collection of clients.
	 */
	public Collection<Client> showClients(Comparator<Client> comparatorOfClient,
			Predicate<Client> clientFilter) {
		return ShowEntities.showFiltered(_clients.values(), clientFilter,
				comparatorOfClient);
	}

	/**
	 * Gets all the notifications of a specific Client.
	 * 
	 * @param key
	 * @return Collection<String>
	 * @throws UnknownKeyException if client don't exist in network.
	 */
	public Collection<Notification> showNotifications(String key)
			throws UnknownKeyException {
		Collection<Notification> notifications = new ArrayList<>();
		notifications.addAll(findClient(key).getNotifications());
		findClient(key).removeNotifications();
		return notifications;
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

	/**
	 * Show all terminals, sorted by the given comparator.
	 * 
	 * @param comparatorOfTerminals a comparator to sort the terminals by.
	 * @return A collection of terminals.
	 */
	public Collection<Terminal> showTerminals(
			Comparator<Terminal> comparatorOfTerminals) {
		return ShowEntities.showAll(_terminals.values(), comparatorOfTerminals);
	}

	/**
	 * Show all terminals that are not broken, sorted by their name.
	 * 
	 * @param coparatorOfTerminals A comparator that will be used to sort the
	 *                             terminals.
	 * @param terminalFilter       a predicate that filters the terminals to be
	 *                             shown.
	 * @return A collection of terminals.
	 */
	public Collection<Terminal> showTerminals(
			Comparator<Terminal> coparatorOfTerminals,
			Predicate<Terminal> terminalFilter) {
		return ShowEntities.showFiltered(_terminals.values(), terminalFilter,
				coparatorOfTerminals);
	}

	/**
	 * This function returns a collection of all the communications, sorted by
	 * the given comparator
	 * 
	 * @param compaterOfCommuncations a comparator that will be used to sort the
	 *                                collection of communications.
	 * @return A collection of all the communications in the system.
	 */
	public Collection<Communication> showCommunications(
			Comparator<Communication> compaterOfCommuncations) {
		return ShowEntities.showAll(_allCommunication, compaterOfCommuncations);
	}

	/**
	 * Show all communications that satisfy the given predicate, sorted by the
	 * given comparator.
	 * 
	 * @param communicationFilter     A predicate that filters the
	 *                                communications.
	 * @param compaterOfCommuncations A comparator that will be used to sort the
	 *                                result.
	 * @return A collection of communications.
	 */
	public Collection<Communication> showCommunications(
			Predicate<Communication> communicationFilter,
			Comparator<Communication> compaterOfCommuncations) {
		return ShowEntities.showFiltered(_allCommunication, communicationFilter,
				compaterOfCommuncations);
	}

	/**
	 * "Show the communications of a client, sorted by a given comparator,
	 * filtered by a given function."
	 * 
	 * 
	 * @param clientKey               the key of the client whose communications
	 *                                we want to see
	 * @param compaterOfCommuncations a comparator that compares two
	 *                                communications.
	 * @param terminalCommunications  a function that takes a terminal and
	 *                                returns a stream of communications.
	 * @return A list of communications sorted by the comparator.
	 */
	public Collection<Communication> showCommunications(String clientKey,
			Comparator<Communication> compaterOfCommuncations,
			Function<Terminal, Stream<Communication>> terminalCommunications)
			throws UnknownKeyException {
		return findClient(clientKey).getCommunications(terminalCommunications)
				.stream().sorted(compaterOfCommuncations).toList();
	}

	/**
	 * If the destination terminal can receive text communication from the
	 * origin terminal, then add a new text communication to the list of all
	 * communications and change the level of the owner of the origin terminal.
	 * 
	 * @param origin         The terminal that is sending the message.
	 * @param destinationKey The key of the terminal that will receive the
	 *                       communication.
	 * @param message        The message to be sent.
	 */
	public void generateTextCommunication(Terminal origin,
			String destinationKey, String message)
			throws UnknownKeyException, UnavailableEntity {
		Terminal destination = findTerminal(destinationKey);
		if (destination.canReciveTextCommunication(origin)) {
			_allCommunication
					.add(origin.makeTextCommunication(destination, message));
			origin.getOwner().getClientLevel().changeLevel(origin.getOwner());
		} else {
			throw new UnavailableEntity(destination.getMode());
		}
	}

	/**
	 * Generate an interactive communication between two terminals.
	 * 
	 * @param origin              The terminal that is initiating the
	 *                            communication.
	 * @param destinationKey      The key of the destination terminal.
	 * @param communcticationType "VOICE" or "VIDEO"
	 */
	public void generateInteractiveCommunication(Terminal origin,
			String destinationKey, String communcticationType)
			throws UnknownKeyException, UnavailableEntity,
			UnsupportedCommunicationExceptionOrigin,
			UnsupportedCommunicationExceptionDestination {
		Terminal destination = findTerminal(destinationKey);

		if (communcticationType.equals("VOICE")
				&& destination.canReciveVoiceCommunication(origin)) {
			_allCommunication.add(origin.makeVoiceCommunication(destination));
		} else if (communcticationType.equals("VIDEO")
				&& destination.canReciveVideoCommunication(origin)) {
			_allCommunication.add(origin.makeVideoCommunication(destination));
		} else {
			throw new UnavailableEntity(destination.getMode());
		}
	}

	/**
	 * Find a communication by its id
	 * 
	 * @param id The id of the communication to find.
	 * @return A communication object.
	 */
	public Communication findCommunication(int id)
			throws InvalidCommunicationExpextion {
		for (Communication aCommunication : _allCommunication) {
			if (aCommunication.getId() == id) {
				return aCommunication;
			}
		}
		throw new InvalidCommunicationExpextion(String.valueOf(id));
	}

	/**
	 * For each client, add their debts to the total debt.
	 * 
	 * @return The total debt of all clients.
	 */
	public double getAllDebts() {
		double totalDebt = 0;
		for (Client aClient : _clients.values()) {
			totalDebt += aClient.getDebts();
		}
		return totalDebt;
	}

	/**
	 * For each client in the clients map, add the client's payments to the
	 * total payments.
	 * 
	 * @return The total amount of payments made by all clients.
	 */
	public double getAllPayments() {
		double totalPayments = 0;
		for (Client aClient : _clients.values()) {
			totalPayments += aClient.getPayments();
		}
		return totalPayments;
	}

	/**
	 * If the terminal is valid, then return true.
	 * 
	 * @param terminal The terminal that is being used to communicate with the
	 *                 player.
	 * @param id       The id of the terminal you want to check.
	 * @return A boolean value.
	 */
	public boolean validCommunication(Terminal terminal, int id) {
		return terminal.validCommunication(id);
	}

	/**
	 * If the communication is valid, pay it.
	 * 
	 * @param terminal the terminal that is paying the communication
	 * @param id       the id of the communication
	 */
	public void payCommunication(Terminal terminal, int id)
			throws InvalidCommunicationExpextion {
		if (validCommunication(terminal, id)) {
			terminal.payDebt(findCommunication(id).paidCommunication());
		}
	}

	/**
	 * Read text input file and create corresponding domain entities.
	 * 
	 * @param filename name of the text input file
	 * @throws UnrecognizedEntryException if some entry is not correct
	 * @throws IOException                if there is an IO error while
	 *                                    processing the text file
	 */
	public void importFile(String filename)
			throws UnrecognizedEntryException, IOException {
		new Parser(this).parseFile(filename);
	}
}
