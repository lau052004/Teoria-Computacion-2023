package hr.fer.zemris.java.hw16.jvdraw.model;

/**
 * Interface that defines the methods that need to be implemented by any object
 * that wishes to get notified by any changes in the DocumentModel's collection
 * of geometrical objects.
 * 
 * @author Damjan Vučina
 */
public interface DrawingModelListener {

	/**
	 * Method invoked whenever some geometrical objects have been added to the DocumentModel's collection of
	 * geometrical objects.
	 *
	 * @param source
	 *            reference to the source object that acts as a Subject in the Observer pattern
	 * @param index0
	 *            the starting index of the newly added objects
	 * @param index1
	 *            the ending index of the newly added objects
	 */
	public void objectsAdded(DrawingModel source, int index0, int index1);

	/**
	 * Method invoked whenever some geometrical objects have been removed to the DocumentModel's collection of
	 * geometrical objects.
	 *
	 * @param source
	 *            reference to the source object that acts as a Subject in the Observer pattern
	 * @param index0
	 *            the starting index of the removed objects
	 * @param index1
	 *            the ending index of the removed objects
	 */
	public void objectsRemoved(DrawingModel source, int index0, int index1);

	/**
	 * Method invoked whenever some geometrical objects in the DocumentModel's collection of
	 * geometrical objects have been changed.
	 *
	 * @param source
	 *            reference to the source object that acts as a Subject in the Observer pattern
	 * @param index0
	 *            the starting index of the changed objects
	 * @param index1
	 *            the ending index of the changed objects
	 */
	public void objectsChanged(DrawingModel source, int index0, int index1);
}
