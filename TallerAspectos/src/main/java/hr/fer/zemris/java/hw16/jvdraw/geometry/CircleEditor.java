package hr.fer.zemris.java.hw16.jvdraw.geometry;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import hr.fer.zemris.java.hw16.jvdraw.model.ObjectModelException;

/**
 * The class that represents an editor used for editing the attributes of a
 * circle previously drawn on the canvas.
 * 
 * @author Damjan Vučina
 */
public class CircleEditor extends GeometricalObjectEditor {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The object. */
	private GeometricalObject object;

	/** The abscissa of the circle's center. */
	private JTextField centerX;

	/** The ordinate of the circle's center*/
	private JTextField centerY;

	/** The radius. */
	private JTextField radius;

	/** The foreground color. */
	private JTextField fgColor;

	/**
	 * Instantiates a new circle editor.
	 *
	 * @param object
	 *            the object
	 */
	public CircleEditor(GeometricalObject object) {
		this.object = object;

		initializeCircle();
		setUpCircle();
	}

	/**
	 * Sets the up circle editor.
	 */
	protected void setUpCircle() {
		setLayout(new GridLayout(1, 0));

		JPanel centerPanel = new JPanel();
		centerPanel.setBorder(BorderFactory.createTitledBorder("Edit Center"));

		centerPanel.add(new JLabel("x:"));
		centerPanel.add(centerX);
		centerPanel.add(new JLabel("y:"));
		centerPanel.add(centerY);

		JPanel radiusPanel = new JPanel();
		radiusPanel.setBorder(BorderFactory.createTitledBorder("Edit Radius"));

		radiusPanel.add(new JLabel("r:"));
		radiusPanel.add(radius);

		JPanel fgColorPanel = new JPanel();
		fgColorPanel.setBorder(BorderFactory.createTitledBorder("Foreground"));

		fgColorPanel.add(new JLabel("color:"));
		fgColorPanel.add(fgColor);

		add(centerPanel);
		add(radiusPanel);
		add(fgColorPanel);
	}

	/**
	 * Initializes circle editor.
	 */
	protected void initializeCircle() {
		centerX = new JTextField(String.valueOf(object.getStartPoint().x));
		centerY = new JTextField(String.valueOf(object.getStartPoint().y));
		radius = new JTextField(String.valueOf(object.calculatePointsDistance()));

		fgColor = new JTextField(colorToHexString(object.getFgColor()));
	}

	/**
	 * Checks if f fields are correctly filled and if not, throws an instance of
	 * ObjectModelException.
	 * 
	 * @throws ObjectModelException
	 *             if fields are not correctly filled
	 */
	@Override
	public void checkEditing() {
		validateCoordinates(centerX.getText());
		validateCoordinates(centerY.getText());
		validateRadius(radius.getText());
		validateColorValue(fgColor.getText());
	}

	/**
	 * Accepts editing by writing values from all fields back into given
	 * corresponding object.
	 */
	//@formatter:off
	@Override
	public void acceptEditing() {
		object.setStartPoint(new Point(Integer.parseInt(centerX.getText()), Integer.parseInt(centerY.getText())));
		object.setEndPoint(new Point(Integer.parseInt(centerX.getText()),
									 Integer.parseInt(centerY.getText()) + Integer.parseInt(radius.getText())));
		
		object.setFgColor(Color.decode(fgColor.getText()));
		
		object.notifyListeners();
	}
	//@formatter:on
}
