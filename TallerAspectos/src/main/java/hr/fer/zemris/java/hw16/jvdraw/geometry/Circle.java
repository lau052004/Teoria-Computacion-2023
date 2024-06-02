package hr.fer.zemris.java.hw16.jvdraw.geometry;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

import hr.fer.zemris.java.hw16.jvdraw.JDrawingCanvas;
import hr.fer.zemris.java.hw16.jvdraw.color.IColorProvider;
import hr.fer.zemris.java.hw16.jvdraw.model.DocumentModel;

/**
 * The class that represents a circle drawn on the canvas that is defined by its
 * startPoint(i.e. center), endPoint(i.e. a Point that belongs to the circle),
 * and fgColor(i.e. color of the circular).
 * 
 * @author Damjan Vučina
 */
public class Circle extends GeometricalObject {

	/** The Constant CIRCLE. */
	private static final String CIRCLE = "Circle";

	/**
	 * Instantiates a new circle.
	 *
	 * @param documentModel
	 *            the document model
	 * @param fgColorProvider
	 *            the fg color provider
	 * @param drawingCanvas
	 *            the drawing canvas
	 */
	public Circle(DocumentModel documentModel, IColorProvider fgColorProvider, JDrawingCanvas drawingCanvas) {
		super(documentModel, fgColorProvider, drawingCanvas);
	}

	/**
	 * Instantiates a new circle.
	 *
	 * @param startPoint
	 *            the start point(i.e. center)
	 * @param endPoint
	 *            the end point(i.e. a Point that belongs to the circle)
	 * @param fgColor
	 *            the fg color(i.e. color of the circle)
	 */
	public Circle(Point startPoint, Point endPoint, Color fgColor) {
		setStartPoint(startPoint);
		setEndPoint(endPoint);
		setFgColor(fgColor);
	}

	/**
	 * Method used by GeometricalObjects allowing them to send references to the
	 * object in charge of drawing on the canvas. Necessary since Visitor pattern is
	 * in use.
	 *
	 * @param v
	 *            the visitor
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
		return new CircleEditor(this);
	}

	/**
	 * Calculates radius.
	 *
	 * @return the radius
	 */
	//@formatter:off
	public int calculateRadius() {
		return (int) Math.sqrt(
					 Math.pow((getStartPoint().x - getEndPoint().x), 2) +
					 Math.pow((getStartPoint().y - getEndPoint().y), 2));
	}
	//@formatter:on

	/**
	 * Clones current object. Used when adding new objects to the document model's
	 * collection
	 *
	 * @return the geometrical object
	 */
	@Override
	public GeometricalObject cloneCurrentObject() {
		return new Circle(getStartPoint(), getEndPoint(), getFgColorProvider().getCurrentColor());
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
	 * Gets the center.
	 *
	 * @return the center
	 */
	public Point getCenter() {
		return getStartPoint();
	}

	/**
	 * Generates textual representation of the circle.
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append(CIRCLE);
		sb.append(" (").append(getCenter().x).append(",");
		sb.append(getCenter().y).append("), ");
		sb.append(calculateRadius());

		return sb.toString();
	}

}
