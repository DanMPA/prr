package prr.app.main;

import java.io.IOException;

import prr.app.exception.FileOpenFailedException;
import prr.core.NetworkManager;
import prr.core.exception.MissingFileAssociationException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;

/**
 * Command to save a file.
 */
class DoSaveFile extends Command<NetworkManager> {

	DoSaveFile(NetworkManager receiver) {
		super(Label.SAVE_FILE, receiver);
	}

	@Override
	protected final void execute() throws FileOpenFailedException {
		if (_receiver.hasFileName()) {
			try {
				_receiver.save();
			} catch (MissingFileAssociationException | IOException ex) {
				_display.popup(Message.fileNotFound(_receiver.getFileName()));
			}
		} else {

			String file =Form.requestString(Message.newSaveAs());
			try {
				_receiver.saveAs(file);
			} catch (MissingFileAssociationException | IOException ex) {
				_display.popup(Message.fileNotFound(_receiver.getFileName()));	
			}
		}
	}
}
