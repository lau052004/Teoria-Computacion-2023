package hr.fer.zemris.java.hw16.jvdraw.geometry;

/**
 * The interface that defines the methods that need to be implemented by an
 * object that desires to be notified when an update to some object's attributes
 * occurs.
 *
 * @author Damjan Vučina
 */
public interface GeometricalObjectListener {

	/**
	 * Method invoked whenever a geometrical object's attribute change.
	 *
	 * @param o
	 *            the object whose attributes have changed
	 */
	void geometricalObjectChanged(GeometricalObject o);
}
