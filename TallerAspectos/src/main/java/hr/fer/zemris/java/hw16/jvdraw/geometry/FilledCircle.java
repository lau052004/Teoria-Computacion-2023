package hr.fer.zemris.java.hw16.jvdraw.geometry;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

import hr.fer.zemris.java.hw16.jvdraw.JDrawingCanvas;
import hr.fer.zemris.java.hw16.jvdraw.color.IColorProvider;
import hr.fer.zemris.java.hw16.jvdraw.model.DocumentModel;

/**
 * The class that represents a filled circle drawn on the canvas that is defined
 * by its startPoint(i.e. center), endPoint(i.e. a Point that belongs to the
 * circle), fgColor(i.e. color of the circular) and bgColor(i.e. color of the
 * circle).
 * 
 * @author Damjan Vučina
 */
public class FilledCircle extends Circle {

	/** The Constant FILLED_CIRCLE. */
	private static final String FILLED_CIRCLE = "Filled circle";

	/** The background color. */
	private Color bgColor;

	/**
	 * The reference to the object responsible for tracking down the currently
	 * selected background color.
	 */
	private IColorProvider bgColorProvider;

	/**
	 * Instantiates a new filled circle.
	 *
	 * @param documentModel
	 *            the document model
	 * @param fgColorProvider
	 *            The reference to the object responsible for tracking down the
	 *            currently selected foreground color.r
	 * @param bgColorProvider
	 *            The reference to the object responsible for tracking down the
	 *            currently selected background color.
	 * @param drawingCanvas
	 *            the drawing canvas
	 */
	//@formatter:off
	public FilledCircle(DocumentModel documentModel,
						IColorProvider fgColorProvider,
						IColorProvider bgColorProvider,
						JDrawingCanvas drawingCanvas) {
		
		super(documentModel, fgColorProvider, drawingCanvas);
		this.bgColorProvider = bgColorProvider;
	}
	
	/**
	 * Instantiates a new filled circle.
	 *
	 * @param startPoint the start point
	 * @param endPoint the end point
	 * @param fgColor the fg color
	 * @param bgColor the bg color
	 */
	public FilledCircle(Point startPoint, Point endPoint, Color fgColor, Color bgColor) {
		super(startPoint, endPoint, fgColor);

		setBgColor(bgColor);
	}
	//@formatter:on

	/**
	 * Sets the bg color.
	 *
	 * @param bgColor
	 *            the new bg color
	 */
	public void setBgColor(Color bgColor) {
		this.bgColor = bgColor;
	}

	/**
	 * Gets the bg color.
	 *
	 * @return the bg color
	 */
	public Color getBgColor() {
		return bgColor != null ? bgColor : bgColorProvider.getCurrentColor();
	}

	/**
	 * Gets the bg color provider.
	 *
	 * @return the bg color provider
	 */
	public IColorProvider getBgColorProvider() {
		return bgColorProvider;
	}

	/**
	 * Sets the background color provider.
	 *
	 * @param bgColorProvider
	 *            the new background color provider
	 */
	public void setBgColorProvider(IColorProvider bgColorProvider) {
		this.bgColorProvider = bgColorProvider;
	}

	/**
	 * Method used by GeometricalObjects allowing them to send references
	 * to the object in charge of drawing on the canvas. Necessary since Visitor pattern is in use.
	 *
	 * @param v the visitor
	 */
	@Override
	public void accept(GeometricalObjectVisitor v) {
		v.visit(this);
	}

	/**
	 * Creates the geometrical object editor.
	 *
	 * @return the geometrical object editor
	 */
	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		return new FilledCircleEditor(this);
	}

	/**
	 * Paints the geometrical object on the canvas.
	 *
	 * @param g2d
	 *            the g 2 d
	 */
	@Override
	public void paint(Graphics2D g2d) {
		if (startPointSet) {
			GeometricalObjectPainter goPainter = getDrawingCanvas().getGoPainter();
			goPainter.setG2d(g2d);
			goPainter.visit(this);
		}
	}

	/**
	 * Clones current object. Used when adding new objects to the document model's collection
	 *
	 * @return the geometrical object
	 */
	//@formatter:off
	@Override
	public GeometricalObject cloneCurrentObject() {
		return new FilledCircle(getStartPoint(), 
								getEndPoint(), 
								getFgColorProvider().getCurrentColor(),
								getBgColorProvider().getCurrentColor());
	}
	//@formatter:on

	/**
	 * Generates textual representation of the filled circle.
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		Color color = getBgColor();

		sb.append(FILLED_CIRCLE);
		sb.append(" (").append(getCenter().x).append(",");
		sb.append(getCenter().y).append("), ");
		sb.append(calculateRadius()).append(", ");
		sb.append(String.format("#%02X%02X%02X", color.getRed(), color.getGreen(), color.getBlue()));

		return sb.toString();
	}
}
