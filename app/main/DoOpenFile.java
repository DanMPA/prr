package prr.app.main;

import prr.core.NetworkManager;
import prr.core.exception.UnavailableFileException;

import java.io.IOException;

import prr.app.exception.FileOpenFailedException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Command to open a file.
 */
class DoOpenFile extends Command<NetworkManager> {

	DoOpenFile(NetworkManager receiver) {
		super(Label.OPEN_FILE, receiver);
		addStringField("file", Message.openFile());
	}

	@Override
	protected final void execute() throws CommandException {
		try {
			_receiver.load(stringField("file"));
		} catch (UnavailableFileException | IOException | ClassNotFoundException ex) {
			throw new FileOpenFailedException(ex);
		}
	}
}
