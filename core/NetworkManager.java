package prr.core;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import prr.core.exception.ImportFileException;
import prr.core.exception.MissingFileAssociationException;
import prr.core.exception.UnavailableFileException;
import prr.core.exception.UnrecognizedEntryException;

/**
 * Manage access to network and implement load/save operations.
 */
public class NetworkManager {

	/** The network itself. */
	private Network _network = new Network();
	private String _fileName = "";

	public Network getNetwork() {
		return _network;
	}

	/**
	 * @param filename name of the file containing the serialized application's
	 *                 state
	 *                 to load.
	 * @throws UnavailableFileException if the specified file does not exist or
	 *                                  there is
	 *                                  an error while processing this file.
	 */
	public void load(String fileName) throws ClassNotFoundException, UnavailableFileException, IOException {
		try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {
			_network = (Network) in.readObject();
			_fileName = fileName;
		} catch (ClassNotFoundException | IOException ex){
			throw new UnavailableFileException(fileName);
		}
	}

	/**
	 * Saves the serialized application's state into the file associated to the
	 * current network.
	 *
	 * @throws FileNotFoundException           if for some reason the file cannot be
	 *                                         created or opened.
	 * @throws MissingFileAssociationException if the current network does not have
	 *                                         a file.
	 * @throws IOException                     if there is some error while
	 *                                         serializing the state of the network
	 *                                         to disk.
	 */
	public void save() throws MissingFileAssociationException, IOException {
		saveAs(_fileName);
	}

	/**
	 * Saves the serialized application's state into the specified file. The current
	 * network is
	 * associated to this file.
	 *
	 * @param filename the name of the file.
	 * @throws FileNotFoundException           if for some reason the file cannot be
	 *                                         created or opened.
	 * @throws MissingFileAssociationException if the current network does not have
	 *                                         a file.
	 * @throws IOException                     if there is some error while
	 *                                         serializing the state of the network
	 *                                         to disk.
	 */
	public void saveAs(String filename) throws MissingFileAssociationException, IOException {
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
			out.writeObject(_network);
			_fileName =filename;
		}
	}

	/**
	 * Read text input file and create domain entities..
	 * 
	 * @param filename name of the text input file
	 * @throws ImportFileException
	 */
	public void importFile(String filename) throws ImportFileException {
		try {
			_network.importFile(filename);
		} catch (IOException | UnrecognizedEntryException e) {
			throw new ImportFileException(filename, e);
		}
	}

	/**
	 * @return boolean true if the File has name
	 */
	public boolean hasFileName(){
		return !_fileName.isEmpty();
	}
	
	/**
	 * Gets file's name
	 * @return 
	 */
	public String getFileName() {
		return _fileName;
	}
}
