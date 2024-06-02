package hr.fer.zemris.java.hw16.jvdraw.actions;

import java.awt.event.ActionEvent;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import hr.fer.zemris.java.hw16.jvdraw.JVDraw;
import hr.fer.zemris.java.hw16.jvdraw.geometry.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.model.ObjectModelException;

/**
 * The class responsible for exiting the program making sure the currently drawn
 * image does not get lost in the process.
 * 
 * @author Damjan Vučina
 */
public class ExitAction extends AbstractAction {
	public static final String CORRUPT_JVD_EXIT = " If you want to exit without saving press YES, if you want to save image to a new location press NO.";
	public static final String CORRUPT_JVD_OPEN = " If you want to continue without saving press YES, if you want to save image to a new location press NO.";

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The main window. */
	private JVDraw window;

	/**
	 * Instantiates a new exit action.
	 *
	 * @param window
	 *            the window
	 */
	public ExitAction(JVDraw window) {
		this.window = window;
	}

	/**
	 * Method invoked when exit action occurred. Exits the program making sure the
	 * currently drawn image does not get lost in the process.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		checkIfImageIsEdited(e, window -> window.dispose(), CORRUPT_JVD_EXIT);

		window.dispose();
	}

	/**
	 * Helper method used by both OpenAction and ExitAction. Checks whether the
	 * currently open image has been edited.
	 * 
	 * @param e
	 *            invoker
	 * @param action
	 *            action to be performed in case of corrupt jvd file
	 * @param errorMessage
	 *            message to be written out in case of corrupt jvd file
	 */
	public void checkIfImageIsEdited(ActionEvent e, Consumer<JVDraw> action, String errorMessage) {
		List<GeometricalObject> currentlyDrawnObjects = window.getDocumentModel().getObjects();
		Path savedPath = window.getImagePath();

		boolean isEdited = false;
		try {
			if (savedPath != null && Files.notExists(savedPath)) {
				throw new ObjectModelException("File " + String.valueOf(savedPath) + " has been deleted.");
			}

			isEdited = UtilityProvider.isImageEdited(savedPath, currentlyDrawnObjects);

		} catch (ObjectModelException exc) {
			String exitMessage = exc.getMessage() + errorMessage;
			int exitResult = JOptionPane.showConfirmDialog(window,
														   exitMessage,
														   "Error reading file",
														   JOptionPane.YES_NO_OPTION);

			if (exitResult == JOptionPane.YES_OPTION) {
				action.accept(window);

			} else if (exitResult == JOptionPane.NO_OPTION) {
				window.getSaveAsAction().actionPerformed(e);

			} else {
				return;
			}
		}

		if (isEdited) {
			//@formatter:off
			int saveResult = JOptionPane.showConfirmDialog(
										 window,
										 "Do you want to save file before closing?",
										 savedPath == null ? "File not saved" : "File edited",
										 JOptionPane.YES_NO_OPTION);
			//@formatter:on

			if (saveResult == JOptionPane.YES_OPTION) {
				if (savedPath == null) {
					window.getSaveAsAction().actionPerformed(e);
				} else {
					window.getSaveAction().actionPerformed(e);
				}
			}
		}
	}
}
