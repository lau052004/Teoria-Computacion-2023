package hr.fer.zemris.java.hw16.jvdraw.color;

import java.awt.Color;

/**
 * The interface that defines the methods that need to be implemented by the
 * object that is in control of the currently set colors. The class that
 * implements this interface is responsible for notifying all registered
 * listeners whenever the user performs a color update.
 * 
 * @author Damjan Vučina
 */
public interface IColorProvider {

	/**
	 * Gets the current color.
	 *
	 * @return the current color
	 */
	public Color getCurrentColor();

	/**
	 * Adds the color change listener.
	 *
	 * @param l
	 *            the l
	 */
	public void addColorChangeListener(ColorChangeListener l);

	/**
	 * Removes the color change listener.
	 *
	 * @param l
	 *            the l
	 */
	public void removeColorChangeListener(ColorChangeListener l);
}
