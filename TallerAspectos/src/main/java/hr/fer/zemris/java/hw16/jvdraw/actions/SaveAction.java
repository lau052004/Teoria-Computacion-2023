package hr.fer.zemris.java.hw16.jvdraw.actions;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import hr.fer.zemris.java.hw16.jvdraw.JVDraw;
import hr.fer.zemris.java.hw16.jvdraw.geometry.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.geometry.GeometricalObjectSaver;

/**
 * The class responsible for updating the currently drawn image. Delegates the
 * process of saving to the instance of GeometricalObjectSaver class since
 * Visitor design pattern is in use.
 * 
 * @author Damjan Vučina
 */
public class SaveAction extends AbstractAction {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The main window. */
	private JVDraw window;

	/**
	 * Reference to the object in charge of generating JVD representations of images
	 */
	private GeometricalObjectSaver goSaver;

	/**
	 * Instantiates a new save action.
	 *
	 * @param window
	 *            the window
	 */
	public SaveAction(JVDraw window) {
		this.window = window;

		goSaver = new GeometricalObjectSaver();
	}

	/**
	 * Method invoked when save action occured. Updates the currently drawn image.
	 * 
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (window.getImagePath() == null) {
			window.getSaveAsAction().actionPerformed(e);
			return;
		}

		performSaving();
	}

	/**
	 * Initiates saving JVD representation of image by utilizing instance of
	 * GeometricalObjectSaver class
	 */
	public void performSaving() {
		List<GeometricalObject> objects = window.getDocumentModel().getObjects();
		for (GeometricalObject object : objects) {
			object.accept(goSaver);
		}

		try {
			goSaver.save(window.getImagePath());
		} catch (IOException exc) {
			JOptionPane.showMessageDialog(window,
										  "Error saving file",
										  "Error",
										  JOptionPane.ERROR_MESSAGE);
			return;
		}
	}

}
