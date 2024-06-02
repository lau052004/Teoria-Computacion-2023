package hr.fer.zemris.java.hw16.jvdraw.geometry;

import static hr.fer.zemris.java.hw16.jvdraw.actions.UtilityProvider.LINE;

import java.awt.Color;
import java.awt.Point;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static hr.fer.zemris.java.hw16.jvdraw.actions.UtilityProvider.CIRCLE;
import static hr.fer.zemris.java.hw16.jvdraw.actions.UtilityProvider.FILLED_CIRCLE;
import static hr.fer.zemris.java.hw16.jvdraw.actions.UtilityProvider.GEOM_OBJECT_SEPARATOR;
import static hr.fer.zemris.java.hw16.jvdraw.actions.UtilityProvider.ATTRIBUTE_SEPARATOR;

/**
 * The class responsible for generating JVD representation of an image and
 * saving such file. Instances of this class are capable of drawing lines,
 * circles and filled circles. Visitor design pattern is in use.
 * 
 * @author Damjan Vučina
 */
public class GeometricalObjectSaver implements GeometricalObjectVisitor {

	/** The current JVD representation of image */
	private StringBuilder sbImage = new StringBuilder();

	/**
	 * Generates JVD representation of the given line.
	 */
	@Override
	public void visit(Line line) {
		sbImage.append(LINE);
		sbImage.append(acquireLineRepresentation(line));

		appendSeparator();
	}

	/**
	 * Generates JVD representation of the given circle.
	 */
	@Override
	public void visit(Circle circle) {
		sbImage.append(CIRCLE);
		sbImage.append(acquireCircleRepresentation(circle));

		appendSeparator();
	}

	/**
	 * Generates JVD representation of the given filled circle.
	 */
	@Override
	public void visit(FilledCircle filledCircle) {
		sbImage.append(FILLED_CIRCLE);
		sbImage.append(acquireFilledCircleRepresentation(filledCircle));

		appendSeparator();
	}

	/**
	 * Appends separator to the current JVD representation of the image.
	 */
	private void appendSeparator() {
		sbImage.append(GEOM_OBJECT_SEPARATOR);
	}

	/**
	 * Acquires circle jvd representation.
	 *
	 * @param object
	 *            the object
	 * @return the string
	 */
	public static String acquireCircleRepresentation(Circle object) {
		StringBuilder sbCircle = new StringBuilder();

		sbCircle.append(ATTRIBUTE_SEPARATOR);
		sbCircle.append(extractCoordinates(object.getCenter()));
		sbCircle.append(ATTRIBUTE_SEPARATOR);
		sbCircle.append(object.calculateRadius());
		sbCircle.append(ATTRIBUTE_SEPARATOR);
		sbCircle.append(extractColor(object.getFgColor()));

		return sbCircle.toString();
	}

	/**
	 * Acquires filled circle jvd representation.
	 *
	 * @param object
	 *            the object
	 * @return the string
	 */
	public static String acquireFilledCircleRepresentation(FilledCircle object) {
		StringBuilder sbFilledCircle = new StringBuilder(acquireCircleRepresentation(object));

		sbFilledCircle.append(ATTRIBUTE_SEPARATOR);
		sbFilledCircle.append(extractColor(object.getBgColor()));

		return sbFilledCircle.toString();
	}

	/**
	 * Acquires line jvd representation.
	 *
	 * @param object
	 *            the object
	 * @return the string
	 */
	public static String acquireLineRepresentation(Line object) {
		StringBuilder sbLine = new StringBuilder();

		sbLine.append(ATTRIBUTE_SEPARATOR);
		sbLine.append(extractCoordinates(object.getStartPoint()));
		sbLine.append(ATTRIBUTE_SEPARATOR);
		sbLine.append(extractCoordinates(object.getEndPoint()));
		sbLine.append(ATTRIBUTE_SEPARATOR);
		sbLine.append(extractColor(object.getFgColor()));

		return sbLine.toString();
	}

	/**
	 * Extracts color.
	 *
	 * @param color
	 *            the color
	 * @return the object
	 */
	private static Object extractColor(Color color) {
		StringBuilder sbColor = new StringBuilder();

		sbColor.append(color.getRed());
		sbColor.append(ATTRIBUTE_SEPARATOR);
		sbColor.append(color.getGreen());
		sbColor.append(ATTRIBUTE_SEPARATOR);
		sbColor.append(color.getBlue());

		return sbColor.toString();
	}

	/**
	 * Extracts String representation of coordinates from Point object.
	 *
	 * @param point
	 *            the point
	 * @return the string
	 */
	private static String extractCoordinates(Point point) {
		StringBuilder sbCoordinates = new StringBuilder();

		sbCoordinates.append(point.x);
		sbCoordinates.append(ATTRIBUTE_SEPARATOR);
		sbCoordinates.append(point.y);

		return sbCoordinates.toString();
	}

	/**
	 * Saves JVD representation of the image to the path specified.
	 *
	 * @param savePath
	 *            the save path
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public void save(Path savePath) throws IOException {
		String jvdRepresentation = getJVD();

		byte[] bytes = jvdRepresentation.getBytes(StandardCharsets.UTF_8);
		Files.write(savePath, bytes);
	}

	/**
	 * Helper method that gets the jvd representation of the image. Used by the
	 * Utility class for comparing JVD representations of files.
	 *
	 * @return the jvd representation of image
	 */
	public String getJVD() {
		String jvd = sbImage.toString();
		sbImage.setLength(0);

		return jvd;
	}
}
