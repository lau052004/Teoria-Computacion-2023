package hr.fer.zemris.java.hw16.jvdraw.color;

import java.awt.Color;

/**
 * The interface that defines the methods that need to be implemented by objects
 * that desire to be notified when user changes the foreground or background
 * color using the toolbar. Observer pattern is in action.
 *
 * @author Damjan Vučina
 */
public interface ColorChangeListener {

	/**
	 * Method invoked when a new color has been selected.
	 *
	 * @param source
	 *            the source of the update
	 * @param oldColor
	 *            the old color
	 * @param newColor
	 *            the new color
	 */
	void newColorSelected(IColorProvider source, Color oldColor, Color newColor);
}
