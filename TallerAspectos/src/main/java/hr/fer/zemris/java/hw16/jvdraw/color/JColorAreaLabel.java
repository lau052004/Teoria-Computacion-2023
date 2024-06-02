package hr.fer.zemris.java.hw16.jvdraw.color;

import java.awt.Color;

import javax.swing.JLabel;

/**
 * The class responsible for writing out the currently selected colors. This
 * class acts as a listener in the Observer pattern and gets notified by the
 * JColorArea when the user updates foreground or background color.
 * 
 * @author Damjan Vučina
 */
public class JColorAreaLabel extends JLabel implements ColorChangeListener {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant FOREGROUND_COLOR. */
	public static final String FOREGROUND_COLOR = "Foreground color";

	/** The Constant BACKGROUND_COLOR. */
	public static final String BACKGROUND_COLOR = "Background color";

	/**
	 * The reference to the object responsible for notifying all registered
	 * listeners whenever the user performs an update of the foreground color.
	 */
	private IColorProvider fgColorProvider;

	/**
	 * The reference to the object responsible for notifying all registered
	 * listeners whenever the user performs an update of the background color.
	 */
	private IColorProvider bgColorProvider;

	/** The text of the label. */
	private String text;

	/**
	 * Instantiates a new j color area label.
	 *
	 * @param fgColorProvider
	 *            The reference to the object responsible for notifying all
	 *            registered listeners whenever the user performs an update of the
	 *            foreground color.
	 * @param bgColorProvider
	 *            The reference to the object responsible for notifying all
	 *            registered listeners whenever the user performs an update of the
	 *            background color.
	 */
	public JColorAreaLabel(IColorProvider fgColorProvider, IColorProvider bgColorProvider) {
		this.fgColorProvider = fgColorProvider;
		this.bgColorProvider = bgColorProvider;

		fgColorProvider.addColorChangeListener(this);
		bgColorProvider.addColorChangeListener(this);

		updateText();
	}

	/**
	 * Gets the text displayed on the label.
	 */
	public String getText() {
		return text;
	}

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
	@Override
	public void newColorSelected(IColorProvider source, Color oldColor, Color newColor) {
		updateText();
	}

	/**
	 * Updates text on the label.
	 */
	private void updateText() {
		StringBuilder sb = new StringBuilder();

		sb.append(FOREGROUND_COLOR).append(": ");
		sb.append(fgColorProvider.toString()).append(", ");
		sb.append(BACKGROUND_COLOR).append(": ");
		sb.append(bgColorProvider.toString()).append(".");

		text = sb.toString();

		setText(text);
	}
}
