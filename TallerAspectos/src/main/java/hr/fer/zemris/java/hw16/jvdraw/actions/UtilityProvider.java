package hr.fer.zemris.java.hw16.jvdraw.actions;

import java.awt.Color;
import java.awt.Point;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.filechooser.FileNameExtensionFilter;

import hr.fer.zemris.java.hw16.jvdraw.geometry.Circle;
import hr.fer.zemris.java.hw16.jvdraw.geometry.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.geometry.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.geometry.GeometricalObjectSaver;
import hr.fer.zemris.java.hw16.jvdraw.geometry.Line;
import hr.fer.zemris.java.hw16.jvdraw.model.ObjectModelException;

/**
 * Helper class providing the user with various methods for manipulating and
 * generating the JVD files, creating the geometrical objects from their JVD
 * representations as well as saving the JVD files.
 * 
 * The purpose of this class is to remove the code duplication as much as
 * possible.
 * 
 * @author Damjan Vučina
 */
public class UtilityProvider {

	/** The Constant LINE. */
	public static final String LINE = "LINE";

	/** The Constant CIRCLE. */
	public static final String CIRCLE = "CIRCLE";

	/** The Constant FILLED_CIRCLE. */
	public static final String FILLED_CIRCLE = "FCIRCLE";

	/** The Constant ATTRIBUTE_SEPARATOR. */
	public static final String ATTRIBUTE_SEPARATOR = " ";

	/** The Constant GEOM_OBJECT_SEPARATOR. */
	public static final String GEOM_OBJECT_SEPARATOR = "\n";

	/** The Constant JVD_EXTENSION. */
	public static final String JVD_EXTENSION = "jvd";

	/** The Constant EXPORT_EXTENSIONS. */
	public static final String[] EXPORT_EXTENSIONS = new String[] { "jpg", "jpeg", "gif", "png" };

	/** The Constant WHITESPACE. */
	private static final String WHITESPACE = " ";

	/** The regex for validating JVD representation of a line */
	public static final String LINE_REGEX = "LINE\\s(\\d+\\s){2}(\\d+\\s){2}(\\d+\\s\\d+\\s\\d+)";

	/** The regex for validating JVD representation of a circle */
	public static final String CIRCLE_REGEX = "CIRCLE\\s(\\d+\\s){2}(\\d+\\s){1}(\\d+\\s\\d+\\s\\d+)";

	/** The regex for validating JVD representation of a filled circle */
	public static final String FILLED_CIRCLE_REGEX = "FCIRCLE\\s(\\d+\\s){2}(\\d+\\s){1}(\\d+\\s){3}(\\d+\\s\\d+\\s\\d+)";

	/** The jvd filter. */
	private static FileNameExtensionFilter jvdFilter = new FileNameExtensionFilter(".jvd", "jvd");

	/** The export filter. */
	private static FileNameExtensionFilter exportFilter = new FileNameExtensionFilter("jpg, png and gif files", "jpg",
			"jpeg", "png", "gif");

	/** The line pattern. */
	private static Pattern linePattern = Pattern.compile(LINE_REGEX);

	/** The circle pattern. */
	private static Pattern circlePattern = Pattern.compile(CIRCLE_REGEX);

	/** The filled circle pattern. */
	private static Pattern filledCirclePattern = Pattern.compile(FILLED_CIRCLE_REGEX);

	private static GeometricalObjectSaver goSaver = new GeometricalObjectSaver();

	/**
	 * Gets the jvd filter.
	 *
	 * @return the jvd filter
	 */
	public static FileNameExtensionFilter getJvdFilter() {
		return jvdFilter;
	}

	/**
	 * Gets the export filter.
	 *
	 * @return the export filter
	 */
	public static FileNameExtensionFilter getExportFilter() {
		return exportFilter;
	}

	/**
	 * Gets the jvd extension.
	 *
	 * @return the jvd extension
	 */
	public static String getJvdExtension() {
		return JVD_EXTENSION;
	}

	/**
	 * Gets the export extensions.
	 *
	 * @return the export extensions
	 */
	public static String[] getExportExtensions() {
		return EXPORT_EXTENSIONS;
	}

	/**
	 * Generates a list of geometrical objects by reading and inspecting the JVD
	 * file.
	 *
	 * @param jvdLines
	 *            the jvd lines
	 * @return the list
	 */
	public static List<GeometricalObject> fromFile(List<String> jvdLines) {
		List<GeometricalObject> objects = new ArrayList<>();

		for (String jvdLine : jvdLines) {
			if (linePattern.matcher(jvdLine).matches()) {
				objects.add(createLine(extractElements(jvdLine)));

			} else if (circlePattern.matcher(jvdLine).matches()) {
				objects.add(createCircle(extractElements(jvdLine)));

			} else if (filledCirclePattern.matcher(jvdLine).matches()) {
				objects.add(createFilledCircle(extractElements(jvdLine)));

			} else {
				throw new ObjectModelException("Unknown geometrical object in JVD file.");
			}
		}

		return objects;
	}

	/**
	 * Extracts objects' attributes from jvd file.
	 *
	 * @param jvdLine
	 *            the jvd line
	 * @return the int[]
	 */
	//@formatter:off
	private static int[] extractElements(String jvdLine) {
		return Arrays.stream(jvdLine.split(WHITESPACE))
					 .skip(1) //skip LINE/CIRCLE/FCIRCLE identifier
					 .mapToInt(Integer::parseInt)
					 .toArray();
	}

	/**
	 * Creates a filled circle.
	 *
	 * @param elements the elements
	 * @return the filled circle
	 */
	private static FilledCircle createFilledCircle(int[] elements) {
		return new FilledCircle(new Point(elements[0], elements[1]),
				  				new Point(elements[0], elements[1] + elements[2]),
				  				new Color(elements[3], elements[4], elements[5]),
				  				new Color(elements[6], elements[7], elements[8]));
	}

	/**
	 * Creates a circle.
	 *
	 * @param elements the elements
	 * @return the circle
	 */
	private static Circle createCircle(int[] elements) {
		return new Circle(new Point(elements[0], elements[1]),
						  new Point(elements[0], elements[1] + elements[2]),
						  new Color(elements[3], elements[4], elements[5]));
	}
	
	/**
	 * Creates a line.
	 *
	 * @param elements the elements
	 * @return the line
	 */
	private static Line createLine(int[] elements) {
		return new Line(new Point(elements[0], elements[1]),
						new Point(elements[2], elements[3]),
						new Color(elements[4], elements[5], elements[6]));
	}
	//@formatter:on

	/**
	 * Checks if the path is invalid, i.e. requested filename contains unsupported
	 * extension.
	 *
	 * @param path
	 *            the path
	 * @param validExtensions
	 *            the valid extensions
	 * @return true, if is invalid extension
	 */
	public static boolean isInvalidExtension(Path path, List<String> validExtensions) {
		String p = String.valueOf(path);
		int numOfDots = p.length() - p.replace(".", "").length();

		String requestedExtension = null;
		if (numOfDots == 1) {
			requestedExtension = acquireExtension(p);
		}

		return numOfDots > 1 || (numOfDots == 1 && !validExtensions.contains(requestedExtension));
	}

	/**
	 * Acquires extension from a string representation of a path.
	 *
	 * @param p
	 *            the p
	 * @return the string
	 */
	public static String acquireExtension(String p) {
		return p.substring(p.indexOf(".") + 1);
	}

	/**
	 * Checks if is image edited.
	 *
	 * @param savedPath
	 *            the saved path
	 * @param currentlyDrawnObjects
	 *            the currently drawn objects
	 * @return true, if is image edited
	 */
	public static boolean isImageEdited(Path savedPath, List<GeometricalObject> currentlyDrawnObjects) {
		if (currentlyDrawnObjects.isEmpty()) {// canvas is empty
			return false;

		} else {
			if (savedPath == null) {// canvas is not empty and not saved
				return true;

			} else {
				return areJvdRepresentationsDifferent(savedPath, currentlyDrawnObjects);
			}
		}
	}

	/**
	 * Checks whether two jvd representations are different.
	 *
	 * @param savedPath
	 *            the saved path
	 * @param currentlyDrawnObjects
	 *            the currently drawn objects
	 * @return true, if successful
	 */
	private static boolean areJvdRepresentationsDifferent(Path savedPath,
			List<GeometricalObject> currentlyDrawnObjects) {

		String currentlyDrawnJvd = generateJVD(currentlyDrawnObjects);
		List<String> jvdLines = UtilityProvider.loadFile(savedPath);

		List<GeometricalObject> savedObjects = UtilityProvider.fromFile(jvdLines);
		String savedJvd = generateJVD(savedObjects);

		return !savedJvd.equals(currentlyDrawnJvd);
	}

	/**
	 * Initiates generating JVD representation of an image defined by the given
	 * objects
	 * 
	 * @param objects
	 *            objects defining the image
	 * @return JVD representation of an image defined by the given objects
	 */
	private static String generateJVD(List<GeometricalObject> objects) {
		for (GeometricalObject object : objects) {
			object.accept(goSaver);
		}

		return goSaver.getJVD();
	}

	/**
	 * Loads file from the specified path.
	 *
	 * @param filePath
	 *            the file path
	 * @return the list
	 */
	public static List<String> loadFile(Path filePath) {
		List<String> jvdLines = null;

		try {
			jvdLines = Files.readAllLines(filePath, StandardCharsets.UTF_8);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		return jvdLines;
	}

	/**
	 * Checks whether the extension is not set.
	 *
	 * @param savePath
	 *            the save path
	 * @return true, if successful
	 */
	public static boolean extensionNotSet(Path savePath) {
		return !String.valueOf(savePath).contains(".");
	}

}
