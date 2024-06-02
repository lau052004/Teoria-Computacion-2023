package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JComponent;

import hr.fer.zemris.java.hw16.jvdraw.geometry.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.geometry.GeometricalObjectPainter;
import hr.fer.zemris.java.hw16.jvdraw.model.DocumentModel;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModelListener;

/**
 * The class that represents a drawing canvas allowing the user to draw lines,
 * circles and filled circles. It acts as a listener in the Observer pattern
 * where DocumentModel class operates as a subject notifying this class whenever
 * a new object is added to the collection. As a result, newly added object is
 * to be drawn on the canvas.
 * 
 * @author Damjan Vučina
 */
public class JDrawingCanvas extends JComponent implements DrawingModelListener {

	/** The color of the canvas' background */
	public static final Color CANVAS_COLOR = Color.WHITE;

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * The reference to the object responsible for storing the currently drawn
	 * objects. This objects acts as a Subject in the Observer pattern.
	 */
	private DocumentModel documentModel;

	/**
	 * The reference to the object responsible for drawing each object on the
	 * drawing canvas.
	 */
	private GeometricalObjectPainter goPainter;

	/** The reference to the main frame of the program. */
	private JVDraw info;

	/**
	 * Gets the object responsible for drawing each object on the drawing canvas.
	 *
	 * @return the object responsible for drawing each object on the drawing canvas.
	 */
	public GeometricalObjectPainter getGoPainter() {
		return goPainter;
	}

	/**
	 * Instantiates a new drawing canvas.
	 *
	 * @param info
	 *            The reference to the main frame of the program.
	 * @param documentModel
	 *            The reference to the object responsible for storing the currently
	 *            drawn objects. This objects acts as a Subject in the Observer
	 *            pattern.
	 */
	public JDrawingCanvas(JVDraw info, DocumentModel documentModel) {
		this.info = info;
		this.documentModel = documentModel;
		goPainter = new GeometricalObjectPainter();

		documentModel.addDrawingModelListener(this);
		addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				info.getCurrentTool().mouseClicked(e);
			}
		});

		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				info.getCurrentTool().mouseMoved(e);
			}
		});

	}

	/**
	 * Gets the main window of this program.
	 *
	 * @return The reference to the object responsible for storing the currently
	 *         drawn objects. This objects acts as a Subject in the Observer
	 *         pattern.
	 */
	public JVDraw getInfo() {
		return info;
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
		repaint();
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
		repaint();
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
		repaint();
	}

	/**
	 * Method responsible for invoking the UI delegate's paint method, if the UI
	 * delegate is non-null. The result of the invocation of this method is updating
	 * the canvas to its up to date state. Delegates to GeometricalObjectPainter
	 * class for printing the objects.
	 */
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

		g2d.setColor(CANVAS_COLOR);
		g2d.fillRect(0, 0, getWidth(), getHeight());

		goPainter.setG2d(g2d);
		for (GeometricalObject object : documentModel.getObjects()) {
			object.accept(goPainter);
		}

		info.getCurrentTool().paint(g2d);
	}
}
