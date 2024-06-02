package hr.fer.zemris.java.hw16.jvdraw.color;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.JButton;
import javax.swing.JColorChooser;

/**
 * The class representing the button used for changing the foreground/background
 * color. The class that implements this interface is responsible for notifying
 * all registered listeners whenever the user performs a color update. Observer
 * pattern is in use.
 * 
 * @author Damjan Vučina
 */
public class JColorArea extends JButton implements IColorProvider {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant DEFAULT_DIMENSION. */
	public static final int DEFAULT_DIMENSION = 15;

	/** The selected color. */
	private Color selectedColor;

	/** The listeners. */
	private List<ColorChangeListener> listeners;

	/**
	 * Instantiates a new j color area.
	 *
	 * @param selectedColor
	 *            the selected color
	 * @param tooltip
	 *            the tooltip
	 */
	public JColorArea(Color selectedColor, String tooltip) {
		listeners = new ArrayList<>();

		this.selectedColor = selectedColor;
		setBackground(selectedColor);
		setSize(getPreferredSize());

		setToolTipText(tooltip);

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Color newColor = JColorChooser.showDialog(JColorArea.this,
											  			  "Please choose a color",
											  			  getCurrentColor());
				if (newColor == null) {
					return;
				}

				Color oldColor = getCurrentColor();
				setSelectedColor(newColor);
				repaint();

				notifyListeners(oldColor, newColor);
			}
		});
	}

	/**
	 * Notify listeners.
	 *
	 * @param oldColor
	 *            the old color
	 * @param newColor
	 *            the new color
	 */
	private void notifyListeners(Color oldColor, Color newColor) {
		for (ColorChangeListener listener : listeners) {
			listener.newColorSelected(this, oldColor, newColor);
		}
	}

	/**
	 * Gets the selected color.
	 *
	 * @return the selected color
	 */
	public Color getSelectedColor() {
		return selectedColor;
	}

	/**
	 * Sets the selected color.
	 *
	 * @param selectedColor
	 *            the new selected color
	 */
	public void setSelectedColor(Color selectedColor) {
		this.selectedColor = selectedColor;

		setBackground(selectedColor);
	}

	/**
	 * Gets the preferred size of the component.
	 */
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(DEFAULT_DIMENSION, DEFAULT_DIMENSION);
	}

	/**
	 * Gets current color.
	 */
	@Override
	public Color getCurrentColor() {
		return selectedColor;
	}

	/**
	 * Adds the color change listener.
	 *
	 * @param l
	 *            the listener
	 */
	@Override
	public void addColorChangeListener(ColorChangeListener l) {
		Objects.requireNonNull(l, "Cannot add null listener.");

		listeners.add(l);
	}

	/**
	 * Removes the color change listener.
	 *
	 * @param l
	 *            the listener
	 */
	@Override
	public void removeColorChangeListener(ColorChangeListener l) {
		Objects.requireNonNull(l, "Cannot remove null listener.");

		listeners.remove(l);
	}

	/**
	 * Generates a String representation of the currently selected color.
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("(");
		sb.append(selectedColor.getRed()).append(", ");
		sb.append(selectedColor.getGreen()).append(", ");
		sb.append(selectedColor.getBlue());
		sb.append(")");

		return sb.toString();
	}

}
