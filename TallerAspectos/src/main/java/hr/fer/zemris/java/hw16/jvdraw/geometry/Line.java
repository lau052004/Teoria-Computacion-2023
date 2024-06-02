package hr.fer.zemris.java.hw16.jvdraw.geometry;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import hr.fer.zemris.java.hw16.jvdraw.JDrawingCanvas;
import hr.fer.zemris.java.hw16.jvdraw.color.IColorProvider;
import hr.fer.zemris.java.hw16.jvdraw.model.DocumentModel;

/**
 * The class that represents a line drawn on the canvas that is defined by its
 * startPoint(start point of the line), endPoint(end point of the line), and
 * fgColor(i.e. color of the line).
 * 
 * @author Damjan Vučina
 */
public class Line extends GeometricalObject {

	/** The Constant LINE. */
	private static final String LINE = "Line";

	/**
	 * Instantiates a new line.
	 *
	 * @param documentModel
	 *            the document model
	 * @param fgColorProvider
	 *            the fg color provider
	 * @param drawingCanvas
	 *            the drawing canvas
	 */
	public Line(DocumentModel documentModel, IColorProvider fgColorProvider, JDrawingCanvas drawingCanvas) {
		super(documentModel, fgColorProvider, drawingCanvas);
	}

	/**
	 * Instantiates a new line.
	 *
	 * @param startPoint
	 *            the start point
	 * @param endPoint
	 *            the end point
	 * @param fgColor
	 *            The reference to the object responsible for tracking down the
	 *            currently selected foreground color.
	 */
	public Line(Point startPoint, Point endPoint, Color fgColor) {
		setStartPoint(startPoint);
		setEndPoint(endPoint);
		setFgColor(fgColor);
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
		return new LineEditor(this);
	}

	/**
	 * Clones current object. Used when adding new objects to the document model's collection
	 *
	 * @return the geometrical object
	 */
	@Override
	public GeometricalObject cloneCurrentObject() {
		return new Line(getStartPoint(), getEndPoint(), getFgColorProvider().getCurrentColor());
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
	 * Generates textual representation of the line.
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append(LINE);
		sb.append(" (").append(getStartPoint().x).append(",");
		sb.append(getStartPoint().y).append(")-");

		sb.append("(").append(getEndPoint().x).append(",");
		sb.append(getEndPoint().y).append(")");

		return sb.toString();
	}
}
