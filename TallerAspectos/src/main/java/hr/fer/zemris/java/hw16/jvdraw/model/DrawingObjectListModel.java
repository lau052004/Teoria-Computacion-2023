package hr.fer.zemris.java.hw16.jvdraw.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import javax.swing.AbstractListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import hr.fer.zemris.java.hw16.jvdraw.geometry.GeometricalObject;

/**
 * The Class that represents a definition for the data model that provides a
 * List with its GeometricalObject contents.
 * 
 * @author Damjan Vučina
 */
//@formatter:off
public class DrawingObjectListModel extends AbstractListModel<GeometricalObject>
									implements DrawingModelListener {

/** The Constant serialVersionUID. */
//@formatter:on
	private static final long serialVersionUID = 1L;

	/** The drawing model. */
	private DrawingModel drawingModel;

	/** The listeners. */
	private List<ListDataListener> listeners = new ArrayList<>();

	/**
	 * Instantiates a new drawing object list model.
	 *
	 * @param drawingModel
	 *            the drawing model
	 */
	public DrawingObjectListModel(DrawingModel drawingModel) {
		this.drawingModel = drawingModel;

		drawingModel.addDrawingModelListener(this);
	}

	/**
	 * Returns the value at the specified index
	 */
	@Override
	public GeometricalObject getElementAt(int index) {
		
		//TODO: AQUI DEBE SER (Problema, retorna todos los que lleva)
		//System.out.println("Pruebaa:");
		//System.out.println(drawingModel.getObject(index));
		
		return drawingModel.getObject(index);
	}

	/**
	 * Returns the length of the list.
	 */
	@Override
	public int getSize() {
		return drawingModel.getSize();
	}

	/**
	 * Adds a listener to the list that's notified each time a change to the data
	 * model occurs.
	 */
	@Override
	public void addListDataListener(ListDataListener l) {
		Objects.requireNonNull(l, "Listener cannot be null");

		listeners.add(l);
	}

	/**
	 * Removes a listener from the list that's notified each time a change to the
	 * data model occurs.
	 */
	@Override
	public void removeListDataListener(ListDataListener l) {
		Objects.requireNonNull(l, "Listener cannot be null");

		listeners.remove(l);
	}

	/**
	 * Method invoked whenever some geometrical objects have been added to the
	 * DocumentModel's collection of geometrical objects.
	 *
	 * @param source
	 *            reference to the source object that acts as a Subject in the
	 *            Observer pattern
	 * @param index0
	 *            the starting index of the newly added objects
	 * @param index1
	 *            the ending index of the newly added objects
	 */
	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, index0, index1);

		notifyListeners(event, l -> l.intervalAdded(event));
	}

	/**
	 * Method invoked whenever some geometrical objects have been removed to the
	 * DocumentModel's collection of geometrical objects.
	 *
	 * @param source
	 *            reference to the source object that acts as a Subject in the
	 *            Observer pattern
	 * @param index0
	 *            the starting index of the removed objects
	 * @param index1
	 *            the ending index of the removed objects
	 */
	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_REMOVED, index0, index1);

		notifyListeners(event, l -> l.intervalRemoved(event));
	}

	/**
	 * Method invoked whenever some geometrical objects in the DocumentModel's
	 * collection of geometrical objects have been changed.
	 *
	 * @param source
	 *            reference to the source object that acts as a Subject in the
	 *            Observer pattern
	 * @param index0
	 *            the starting index of the changed objects
	 * @param index1
	 *            the ending index of the changed objects
	 */
	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		ListDataEvent event = new ListDataEvent(this, ListDataEvent.CONTENTS_CHANGED, index0, index1);

		notifyListeners(event, l -> l.contentsChanged(event));
	}

	/**
	 * Notifies listeners.
	 *
	 * @param event
	 *            the event
	 * @param action
	 *            the action
	 */
	private void notifyListeners(ListDataEvent event, Consumer<ListDataListener> action) {
		for (ListDataListener l : listeners) {
			action.accept(l);
		}
	}

}
