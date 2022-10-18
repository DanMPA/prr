package prr.app.main;

import java.io.FileNotFoundException;
import java.io.IOException;

import prr.core.NetworkManager;
import prr.core.exception.MissingFileAssociationException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
//FIXME add more imports if needed

/**
 * Command to save a file.
 */
class DoSaveFile extends Command<NetworkManager> {

	DoSaveFile(NetworkManager receiver) {
		super(Label.SAVE_FILE, receiver);
		if(!_receiver.hasFileName()){
			addStringField("file", Message.newSaveAs());
		}
	}

	@Override
	protected final void execute() {
		if (_receiver.hasFileName()) {
			try {
				_receiver.save();
			} catch (MissingFileAssociationException | IOException ex) {
				_display.popup(Message.fileNotFound(_receiver.get_fileName()));
			}
		} else {
			try {
				_receiver.saveAs(stringField("file"));
			} catch (MissingFileAssociationException | IOException ex) {
				_display.popup(Message.fileNotFound(stringField("file")));
			}
		}
	}
}
