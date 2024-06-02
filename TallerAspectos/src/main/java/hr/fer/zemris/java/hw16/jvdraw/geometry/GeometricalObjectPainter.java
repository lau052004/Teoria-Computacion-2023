package hr.fer.zemris.java.hw16.jvdraw.geometry;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

/**
 * The class representing the object responsible for drawing each object on the
 * drawing canvas.
 * 
 * @author Damjan Vučina
 */
public class GeometricalObjectPainter implements GeometricalObjectVisitor {

	/** The Constant DEFAULT_STROKE. */
	public static final int DEFAULT_STROKE = 2;

	/** The drawing object. */
	private Graphics2D g2d;

	/**
	 * Instantiates a new geometrical object painter.
	 */
	public GeometricalObjectPainter() {
	}

	/**
	 * Gets the drawing object.
	 *
	 * @return the drawing object
	 */
	public Graphics2D getG2d() {
		return g2d;
	}

	/**
	 * Sets the drawing object
	 *
	 * @param g2d
	 *            the new drawing object
	 */
	public void setG2d(Graphics2D g2d) {
		this.g2d = g2d;
	}

	/**
	 * Invoked when a Line has been visited. Draws given Line on the canvas.
	 *
	 * @param line
	 *            the line
	 */
	@Override
	public void visit(Line line) {
		setUpStroke(g2d);

		g2d.setColor(line.getFgColor());
		g2d.drawLine(line.getStartPoint().x, line.getStartPoint().y, line.getEndPoint().x, line.getEndPoint().y);
	}

	/**
	 * Invoked when a Circle has been visited. Draws given Circle on the canvas.
	 *
	 * @param line
	 *            the line
	 */
	@Override
	public void visit(Circle circle) {
		setUpStroke(g2d);
		int radius = circle.calculateRadius();

		g2d.setColor(circle.getFgColor());
		g2d.drawOval(circle.getStartPoint().x - radius, circle.getStartPoint().y - radius, 2 * radius, 2 * radius);
	}

	/**
	 * Invoked when a FilledCircle has been visited. Draws given FilledCircle on the canvas.
	 *
	 * @param line
	 *            the line
	 */
	@Override
	public void visit(FilledCircle filledCircle) {
		setUpStroke(g2d);
		int radius = filledCircle.calculateRadius();

		g2d.setColor(filledCircle.getBgColor());
		g2d.fillOval(filledCircle.getStartPoint().x - radius, filledCircle.getStartPoint().y - radius, 2 * radius,
				2 * radius);

		visit((Circle) filledCircle);
	}

	/**
	 * Sets the up stroke.
	 *
	 * @param g2d
	 *            the new up stroke
	 */
	private void setUpStroke(Graphics2D g2d) {
		g2d.setStroke(new BasicStroke(DEFAULT_STROKE));
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	}

}
