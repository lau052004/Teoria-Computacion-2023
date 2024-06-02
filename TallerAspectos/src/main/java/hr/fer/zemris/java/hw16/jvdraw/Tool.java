package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

/**
 * The interface defining the methods that must be implemented by every type of
 * drawing tool. State pattern is in use.
 * 
 * @author Damjan Vučina
 */
public interface Tool {

	/**
	 * Mouse has been pressed.
	 *
	 * @param e
	 *            the event
	 */
	public void mousePressed(MouseEvent e);

	/**
	 * Mouse has been released.
	 *
	 * @param e
	 *            the event
	 */
	public void mouseReleased(MouseEvent e);

	/**
	 * Mouse has been clicked.
	 *
	 * @param e
	 *            the event
	 */
	public void mouseClicked(MouseEvent e);

	/**
	 * Mouse has been moved.
	 *
	 * @param e
	 *            the event
	 */
	public void mouseMoved(MouseEvent e);

	/**
	 * Mouse has been dragged.
	 *
	 * @param e
	 *            the event
	 */
	public void mouseDragged(MouseEvent e);

	/**
	 * Paints the geometrical object on the canvas.
	 *
	 * @param g2d
	 *            the g 2 d
	 */
	public void paint(Graphics2D g2d);
}
