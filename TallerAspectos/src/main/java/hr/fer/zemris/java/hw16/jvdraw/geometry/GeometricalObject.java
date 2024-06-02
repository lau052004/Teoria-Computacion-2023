package hr.fer.zemris.java.hw16.jvdraw.geometry;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import hr.fer.zemris.java.hw16.jvdraw.JDrawingCanvas;
import hr.fer.zemris.java.hw16.jvdraw.Tool;
import hr.fer.zemris.java.hw16.jvdraw.color.IColorProvider;
import hr.fer.zemris.java.hw16.jvdraw.model.DocumentModel;

/**
 * The abstract class that serves as a base class for objects that are drawn on
 * the canvas. It defines methods for acquiring objects' attributes such as
 * startPoint and endPoint as well as various other methods necessary for the
 * proper functioning of all the services.
 * 
 * This includes methods for calculating distance between two points and
 * (de)registering listeners since this class acts as a Subject in the Observer
 * pattern notifying listeners about changes that have occured on an object's
 * attributes.
 * 
 * This class also defines a handful of methods that need to be implemented by
 * its subclasses.
 * 
 * @author Damjan Vučina
 * 
 */
public abstract class GeometricalObject implements Tool {

	/** The listeners. */
	private List<GeometricalObjectListener> listeners = new ArrayList<>();

	/** The start point. */
	private Point startPoint;

	/** The end point. */
	private Point endPoint;

	/** The start point set. */
	boolean startPointSet;

	/** The document model. */
	private DocumentModel documentModel;

	/**
	 * The reference to the object responsible for tracking down the currently
	 * selected foreground color.
	 */
	private IColorProvider fgColorProvider;

	/** The drawing canvas. */
	private JDrawingCanvas drawingCanvas;

	/** The foreground color. */
	private Color fgColor;

	/**
	 * Instantiates a new geometrical object.
	 *
	 * @param documentModel
	 *            the document model
	 * @param fgColorProvider
	 *            The reference to the object responsible for tracking down the
	 *            currently selected background color.
	 * @param drawingCanvas
	 *            the drawing canvas
	 */
	//@formatter:off
	public GeometricalObject(DocumentModel documentModel,
							 IColorProvider fgColorProvider,
							 JDrawingCanvas drawingCanvas) {
		
		this.documentModel = documentModel;
		this.fgColorProvider = fgColorProvider;
		this.drawingCanvas = drawingCanvas;
	}
	//@formatter:off

	/**
	 * Instantiates a new geometrical object.
	 */
	public GeometricalObject() {
		//TODO: Aqui se crea el objeto. Eliminar esto.
		//System.out.println("Objeto creado.");
	}

	/**
	 * Method used by GeometricalObjects allowing them to send references
	 * to the object in charge of drawing on the canvas. Necessary since Visitor pattern is in use.
	 *
	 * @param v the visitor
	 */
	public abstract void accept(GeometricalObjectVisitor v);

	/**
	 * Creates the geometrical object editor.
	 *
	 * @return the geometrical object editor
	 */
	public abstract GeometricalObjectEditor createGeometricalObjectEditor();

	/**
	 * Clones current object. Used when adding new objects to the document model's collection
	 *
	 * @return the geometrical object
	 */
	public abstract GeometricalObject cloneCurrentObject();

	/**
	 * Sets the foreground color.
	 *
	 * @param fgColor the new foreground color
	 */
	public void setFgColor(Color fgColor) {
		this.fgColor = fgColor;
	}
	
	/**
	 * Gets the document model.
	 *
	 * @return the document model
	 */
	public DocumentModel getDocumentModel() {
		return documentModel;
	}

	/**
	 * Gets the foreground color.
	 *
	 * @return the foreground color
	 */
	public Color getFgColor() {
		return fgColor != null ? fgColor : fgColorProvider.getCurrentColor();
	}

	/**
	 * Gets the drawing canvas.
	 *
	 * @return the drawing canvas
	 */
	public JDrawingCanvas getDrawingCanvas() {
		return drawingCanvas;
	}

	/**
	 * Gets the foreground color provider.
	 *
	 * @return the foreground color provider
	 */
	public IColorProvider getFgColorProvider() {
		return fgColorProvider;
	}

	/**
	 * Sets the foreground color provider.
	 *
	 * @param fgColorProvider the new foreground color provider
	 */
	public void setFgColorProvider(IColorProvider fgColorProvider) {
		this.fgColorProvider = fgColorProvider;
	}
	
	/**
	 * Gets the start point.
	 *
	 * @return the start point
	 */
	public Point getStartPoint() {
		return startPoint;
	}

	/**
	 * Checks if is start point set.
	 *
	 * @return true, if is start point set
	 */
	public boolean isStartPointSet() {
		return startPointSet;
	}

	/**
	 * Sets the start point.
	 *
	 * @param startPoint the new start point
	 */
	public void setStartPoint(Point startPoint) {
		this.startPoint = startPoint;
	}

	/**
	 * Gets the end point.
	 *
	 * @return the end point
	 */
	public Point getEndPoint() {
		return endPoint;
	}
	
	/**
	 * Calculate points distance.
	 *
	 * @return the distance
	 */
	//@formatter:off
	public int calculatePointsDistance() {
		return (int) Math.sqrt(
					 Math.pow((getStartPoint().x - getEndPoint().x), 2) +
				     Math.pow((getStartPoint().y - getEndPoint().y), 2));
	}
	//@formatter:on

	/**
	 * Sets the end point.
	 *
	 * @param endPoint
	 *            the new end point
	 */
	public void setEndPoint(Point endPoint) {
		this.endPoint = endPoint;
	}

	/**
	 * Adds the geometrical object listener.
	 *
	 * @param l
	 *            the listener
	 */
	public void addGeometricalObjectListener(GeometricalObjectListener l) {
		Objects.requireNonNull(l, "Cannot add null listener.");

		listeners.add(l);
	}

	/**
	 * Removes the geometrical object listener.
	 *
	 * @param l
	 *            the listener
	 */
	public void removeGeometricalObjectListener(GeometricalObjectListener l) {
		Objects.requireNonNull(l, "Cannot remove null listener.");

		listeners.remove(l);
	}

	/**
	 * Notifies listeners.
	 */
	public void notifyListeners() {
		for (GeometricalObjectListener listener : listeners) {
			listener.geometricalObjectChanged(this);
		}
	}

	/**
	 * Mouse has been pressed.
	 *
	 * @param e
	 *            the event
	 */
	@Override
	public void mousePressed(MouseEvent e) {
	}

	/**
	 * Mouse has been released.
	 *
	 * @param e
	 *            the event
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
	}

	/**
	 * Mouse has been clicked.
	 *
	 * @param e
	 *            the event
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		Point clickedPoint = e.getPoint();

		if (!startPointSet) {
			setStartPoint(clickedPoint);
			startPointSet = true;

		} else {
			if (!getStartPoint().equals(clickedPoint)) {
				setEndPoint(clickedPoint);
				startPointSet = false;
				documentModel.add(cloneCurrentObject());
			}
		}
	}

	/**
	 * Mouse has been moved.
	 *
	 * @param e
	 *            the event
	 */
	@Override
	public void mouseMoved(MouseEvent e) {
		if (startPointSet) {
			setEndPoint(e.getPoint());
			setFgColor(fgColorProvider.getCurrentColor());
			drawingCanvas.repaint();
		}
	}

	/**
	 * Mouse has been dragged.
	 *
	 * @param e
	 *            the event
	 */
	@Override
	public void mouseDragged(MouseEvent e) {
	}
}
