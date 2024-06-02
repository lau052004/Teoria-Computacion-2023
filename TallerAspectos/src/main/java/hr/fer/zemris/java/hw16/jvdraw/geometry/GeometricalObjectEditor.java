package hr.fer.zemris.java.hw16.jvdraw.geometry;

import java.awt.Color;
import java.util.regex.Pattern;

import javax.swing.JPanel;

import hr.fer.zemris.java.hw16.jvdraw.model.ObjectModelException;

/**
 * The abstract class that serves as a base class for objects representing
 * editor for editing object's attributes.
 * 
 * @author Damjan Vučina
 */
public abstract class GeometricalObjectEditor extends JPanel {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant COLOR_VALIDATOR. */
	private static final String COLOR_VALIDATOR = "#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})";

	/** The regex for validating the color. */
	private Pattern pattern = Pattern.compile(COLOR_VALIDATOR);

	/**
	 * Checks if f fields are correctly filled and if not, throws an instance of
	 * ObjectModelException.
	 * 
	 * @throws ObjectModelException
	 *             if fields are not correctly filled
	 */
	public abstract void checkEditing();

	/**
	 * Accepts editing by writing values from all fields back into given
	 * corresponding object.
	 */
	public abstract void acceptEditing();

	/**
	 * Validates color value.
	 *
	 * @param value
	 *            the value
	 */
	public void validateColorValue(String value) {
		boolean matches = pattern.matcher(value).matches();
		if (!matches) {
			throw new ObjectModelException("Invalid color string");
		}
	}

	/**
	 * Validates coordinates.
	 *
	 * @param value
	 *            the value
	 * @throws ObjectModelException
	 *             if given coordinates are not valid
	 */
	public void validateCoordinates(String value) {
		int parsed = parse(value);
		if (parsed < 0) {
			throw new ObjectModelException("Invalid cooridinates");
		}
	}

	/**
	 * Validates radius.
	 *
	 * @param value
	 *            the value
	 * @throws ObjectModelException
	 *             if given radius is not valid
	 */
	public void validateRadius(String value) {
		int radius = parse(value);
		if (radius <= 0) {
			throw new ObjectModelException("Invalid radius");
		}
	}

	/**
	 * Transforms color to hexadecimal string.
	 *
	 * @param color
	 *            the color
	 * @return the string
	 */
	protected String colorToHexString(Color color) {
		return String.format("#%02X%02X%02X", color.getRed(), color.getGreen(), color.getBlue());
	}

	/**
	 * Helper method used for parsing the String representation of numbers.
	 *
	 * @param value
	 *            the value
	 * @return the int
	 */
	private int parse(String value) {
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException | NullPointerException e) {
			return -1;
		}
	}

}