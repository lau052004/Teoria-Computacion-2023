package hr.fer.zemris.java.hw16.jvdraw.geometry;

import static java.lang.Math.min;

import java.awt.Point;
import java.awt.Rectangle;
import static java.lang.Math.abs;
import static hr.fer.zemris.java.hw16.jvdraw.geometry.GeometricalObjectPainter.DEFAULT_STROKE;

/**
 * The class that is responsible for calculating the minimal rectangle that
 * encapsulates all the objects currently drawn on the canvas. This is necessary
 * for exporting the image so once exported, the blank space is minimal.
 * 
 * @author Damjan Vučina
 */
public class GeometricalObjectBBCalculator implements GeometricalObjectVisitor {

	/** The Constant MARGIN. */
	private static final int MARGIN = 1;

	/** The bounding rectangle. */
	private Rectangle boundingRectangle;

	/**
	 * Invoked when a Line has been visited.
	 *
	 * @param line
	 *            the line
	 */
	@Override
	public void visit(Line line) {
		int topLeftX = min(line.getStartPoint().x, line.getEndPoint().x);
		int topLeftY = min(line.getStartPoint().y, line.getEndPoint().y);
		int width = abs(line.getStartPoint().x - line.getEndPoint().x);
		int height = abs(line.getStartPoint().y - line.getEndPoint().y);

		Rectangle lineRectangle = new Rectangle(topLeftX, topLeftY, width, height);
		updateBoundingRectangle(lineRectangle);
	}

	/**
	 * Updates bounding rectangle.
	 *
	 * @param objectRectangle
	 *            the object rectangle
	 */
	private void updateBoundingRectangle(Rectangle objectRectangle) {
		if (boundingRectangle == null) {
			boundingRectangle = objectRectangle;

		} else {
			boundingRectangle = boundingRectangle.union(objectRectangle);
		}
	}

	/**
	 * Invoked when a Circle has been visited.
	 *
	 * @param circle
	 *            the circle
	 */
	@Override
	public void visit(Circle circle) {
		int radius = circle.calculateRadius();
		Point center = circle.getCenter();

		int topLeftX = center.x - radius;
		int topLeftY = center.y - radius;
		int diameter = 2 * radius;

		Rectangle circleRectangle = new Rectangle(topLeftX, topLeftY, diameter, diameter);
		updateBoundingRectangle(circleRectangle);
	}

	/**
	 * Invoked when a FilledCircle has been visited..
	 *
	 * @param filledCircle
	 *            the filled circle
	 */
	@Override
	public void visit(FilledCircle filledCircle) {
		visit((Circle) filledCircle);
	}

	/**
	 * Gets the bounding box.
	 *
	 * @return the bounding box
	 */
	//@formatter:off
	public Rectangle getBoundingBox() {
		Rectangle result = new Rectangle(boundingRectangle.x,
										 boundingRectangle.y,
										 boundingRectangle.width,
										 boundingRectangle.height);
		resetBoundingRectangle();
		return result;// return value if margin pixels are not supposed to
		// be taken into account
		// return considerMarginPixels();
	}
	//@formatter:on

	/**
	 * Takes margin pixels into account when returning the bounding rectangle.
	 * NOTICE: not used by default
	 *
	 * @return the rectangle
	 */
	//@formatter:off
	@SuppressWarnings("unused")
	private Rectangle considerMarginPixels() {
		Rectangle result = new Rectangle(boundingRectangle.x - MARGIN,
										 boundingRectangle.y - MARGIN,
										 boundingRectangle.width + DEFAULT_STROKE + MARGIN,
										 boundingRectangle.height + DEFAULT_STROKE + MARGIN);
		resetBoundingRectangle();
		return result;
	}
	//@formatter:off

	/**
	 * Resets bounding rectangle.
	 */
	public void resetBoundingRectangle() {
		boundingRectangle = null;
	}

}
