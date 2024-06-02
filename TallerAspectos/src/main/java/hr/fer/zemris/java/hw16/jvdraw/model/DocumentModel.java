package hr.fer.zemris.java.hw16.jvdraw.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import hr.fer.zemris.java.hw16.jvdraw.geometry.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.geometry.GeometricalObjectListener;
import static hr.fer.zemris.java.hw16.jvdraw.JVDraw.SHIFT_UP;
import static hr.fer.zemris.java.hw16.jvdraw.JVDraw.SHIFT_DOWN;

/**
 * The class that represents a collection of geometrical objects currently drawn
 * on the canvas.
 * 
 *  This class acts as an Observer pattern Listener over the
 * GeometrialObject instance which operates as a Subject. Whenever a new
 * geometrical object is drawn on the canvas, instance of this class registers
 * itself as a listener on that object, so it can get notified whenever an
 * update is made to that object's attributes.
 * 
 *  This class also acts as a Subject
 * in the above mentioned pattern since it notifies an instance of DrawingCanvas
 * class that a new object has been added to the collection so it can be drawn
 * on the canvas.
 * 
 * @author Damjan Vučina
 */
public class DocumentModel implements DrawingModel, GeometricalObjectListener {

	/** The currently drawn objects. */
	private List<GeometricalObject> objects;

	/** The listeners. */
	private List<DrawingModelListener> listeners;

	/**
	 * Instantiates a new document model.
	 */
	public DocumentModel() {
		objects = new ArrayList<>();
		listeners = new ArrayList<>();
	}

	/**
	 * Gets the currently drawn objects.
	 *
	 * @return the currently drawn objects
	 */
	public List<GeometricalObject> getObjects() {
		return objects;
	}

	/**
	 * Gets the listeners.
	 *
	 * @return the listeners
	 */
	public List<DrawingModelListener> getListeners() {
		return listeners;
	}

	/**
	 * Sets the currently drawn objects.
	 *
	 * @param objects
	 *            the currently drawn objects
	 */
	public void setObjects(List<GeometricalObject> objects) {
		this.objects = objects;
	}

	/**
	 * Sets the listeners.
	 *
	 * @param listeners
	 *            the new listeners
	 */
	public void setListeners(List<DrawingModelListener> listeners) {
		this.listeners = listeners;
	}

	/**
	 * Gets the size of the collection of objects.
	 *
	 * @return the size
	 */
	@Override
	public int getSize() {
		return objects.size();
	}

	/**
	 * Gets the specific object.
	 *
	 * @param index
	 *            the index
	 * @return the object
	 */
	@Override
	public GeometricalObject getObject(int index) {
		if (index < 0 || index >= objects.size()) {
			throw new IllegalArgumentException(
					"Valid indices are from 0 to " + (objects.size() - 1) + ", was: " + index);
		}

		return objects.get(index);
	}

	/**
	 * Adds the object to the collection.
	 *
	 * @param object
	 *            the to be added object
	 */
	@Override
	public void add(GeometricalObject object) {
		Objects.requireNonNull(object, "Cannot add null object");
		
		//TODO: Por aca depronto es
		//System.out.println("Se creo un nuevo objeto");
		//System.out.println("No se que es: "+object.getStartPoint());
		
		//System.out.println("Figura: " + object.toString());
		//System.out.println("Color: " + object.getFgColor());
		//System.out.println("Coordenada Inicio: " + object.getStartPoint());
		//System.out.println("Coordenada Final: " + object.getEndPoint());
		

		objects.add(object);
		object.addGeometricalObjectListener(this);

		int modificationIndex = objects.size() - 1;
		for (DrawingModelListener listener : listeners) {
			listener.objectsAdded(this, modificationIndex, modificationIndex);
		}

	}

	/**
	 * Removes the specified object from the collection.
	 *
	 * @param object
	 *            the object to be removed from the collection
	 */
	@Override
	public void remove(GeometricalObject object) {
		Objects.requireNonNull(object, "Cannot remove null object.");

		int modificationIndex = objects.indexOf(object);
		for (DrawingModelListener listener : listeners) {
			listener.objectsRemoved(this, modificationIndex, modificationIndex);
		}

		objects.remove(object);
	}

	/**
	 * Adds the drawing model listener.
	 *
	 * @param l
	 *            the listener
	 */
	@Override
	public void addDrawingModelListener(DrawingModelListener l) {
		Objects.requireNonNull(l, "Cannot add null listener.");

		listeners.add(l);
	}

	/**
	 * Removes the drawing model listener.
	 *
	 * @param l
	 *            the listener
	 */
	@Override
	public void removeDrawingModelListener(DrawingModelListener l) {
		Objects.requireNonNull(l, "Cannot remove null listener.");

		listeners.remove(l);
	}

	/**
	 * Method invoked whenever a geometrical object's attribute change.
	 *
	 * @param o
	 *            the object whose attributes have changed
	 */
	@Override
	public void geometricalObjectChanged(GeometricalObject o) {
		Objects.requireNonNull(o, "Geometrical object cannot be null");

		int modificationIndex = objects.indexOf(o);
		for (DrawingModelListener listener : listeners) {
			listener.objectsChanged(this, modificationIndex, modificationIndex);
		}
	}

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
	@Override
	public void changeOrder(GeometricalObject object, int offset) {
		if (offset != SHIFT_UP && offset != SHIFT_DOWN) {
			throw new IllegalArgumentException("Shifting offset must be 1 or -1, was: " + offset);
		}

		int oldIndex = objects.indexOf(object);
		int newIndex = oldIndex + offset;
		if (newIndex >= 0 && newIndex < objects.size()) {
			Collections.swap(objects, oldIndex, newIndex);

			for (DrawingModelListener listener : listeners) {
				listener.objectsChanged(this, Math.min(oldIndex, newIndex), Math.max(oldIndex, newIndex));
			}
		}
	}
}
