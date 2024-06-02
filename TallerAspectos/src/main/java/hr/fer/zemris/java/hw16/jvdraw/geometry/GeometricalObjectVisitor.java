package hr.fer.zemris.java.hw16.jvdraw.geometry;

/**
 * The Interface that defines the methods that need to be implemented by the
 * classes partaking in Visitor design pattern.
 * 
 * This is a way of separating an algorithm from an object structure on which it
 * operates. A practical result of this separation is the ability to add new
 * operations to existent object structures without modifying the structures. It
 * is one way to follow the open/closed principle.
 * 
 * @author Damjan Vučina
 */
public interface GeometricalObjectVisitor {

	/**
	 * Invoked when a Line has been visited.
	 *
	 * @param line
	 *            the line
	 */
	void visit(Line line);

	/**
	 *Invoked when a Circle has been visited.
	 *
	 * @param circle
	 *            the circle
	 */
	void visit(Circle circle);

	/**
	 * Invoked when a FilledCircle has been visited..
	 *
	 * @param filledCircle
	 *            the filled circle
	 */
	void visit(FilledCircle filledCircle);
}
