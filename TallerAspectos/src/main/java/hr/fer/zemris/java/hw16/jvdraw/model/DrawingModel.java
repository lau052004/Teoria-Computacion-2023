package hr.fer.zemris.java.hw16.jvdraw.model;

import hr.fer.zemris.java.hw16.jvdraw.geometry.GeometricalObject;

/**
 * The interface that defines the methods that need to be implemented by the
 * object that acts as a storage for objects drawn on the canvas.
 * 
 * @author Damjan Vučina
 */
public interface DrawingModel {

	/**
	 * Gets the size of the collection of objects.
	 *
	 * @return the size
	 */
	int getSize();

	/**
	 * Gets the specific object.
	 *
	 * @param index
	 *            the index
	 * @return the object
	 */
	GeometricalObject getObject(int index);

	/**
	 * Adds the object to the collection.
	 *
	 * @param object
	 *            the to be added object
	 */
	void add(GeometricalObject object);

	/**
	 * Adds the drawing model listener.
	 *
	 * @param l
	 *            the listener
	 */
	void addDrawingModelListener(DrawingModelListener l);

	/**
	 * Removes the drawing model listener.
	 *
	 * @param l
	 *            the listener
	 */
	void removeDrawingModelListener(DrawingModelListener l);

	/**
	 * Removes the specified object from the collection.
	 *
	 * @param object
	 *            the object to be removed from the collection
	 */
	void remove(GeometricalObject object);

	/**
	 * Changes the ordinal number of the specified object in the collection by a
	 * chosen offset. NOTICE: Objects are drawn on the canvas starting from the
	 * object that was first inserted to the collection
	 *
	 * @param object
	 *            the object
	 * @param offset
	 *            the offset
	 */
	void changeOrder(GeometricalObject object, int offset);
}
