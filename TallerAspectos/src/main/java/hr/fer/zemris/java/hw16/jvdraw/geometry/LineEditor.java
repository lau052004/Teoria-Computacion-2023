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
 * line previously drawn on the canvas.
 * 
 * @author Damjan Vučina
 */
public class LineEditor extends GeometricalObjectEditor {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The line. */
	private Line line;
	
	/** The abcissa of the line's start point. */
	private JTextField startPointX;
	
	/** The ordinate of the line's start point. */
	private JTextField startPointY;
	
	/** The abcissa of the line's end point. */
	private JTextField endPointX;
	
	/** The ordinate of the line's end point. */
	private JTextField endPointY;
	
	/** The foreground color. */
	private JTextField fgColor;

	/**
	 * Instantiates a new line editor.
	 *
	 * @param line the line
	 */
	public LineEditor(Line line) {
		this.line = line;

		initialize();
		setUp();
	}

	/**
	 * Initializes line editor.
	 */
	private void initialize() {
		startPointX = new JTextField(String.valueOf(line.getStartPoint().x));
		startPointY = new JTextField(String.valueOf(line.getStartPoint().y));

		endPointX = new JTextField(String.valueOf(line.getEndPoint().x));
		endPointY = new JTextField(String.valueOf(line.getEndPoint().y));

		fgColor = new JTextField(colorToHexString(line.getFgColor()));
	}

	/**
	 * Sets up the line editor.
	 */
	private void setUp() {
		setLayout(new GridLayout(1, 0));

		JPanel startPointPanel = new JPanel();
		startPointPanel.setBorder(BorderFactory.createTitledBorder("Edit Start Point"));

		startPointPanel.add(new JLabel("x:"));
		startPointPanel.add(startPointX);
		startPointPanel.add(new JLabel("y:"));
		startPointPanel.add(startPointY);

		JPanel endPointPanel = new JPanel();
		endPointPanel.setBorder(BorderFactory.createTitledBorder("Edit End Point"));

		endPointPanel.add(new JLabel("x:"));
		endPointPanel.add(endPointX);
		endPointPanel.add(new JLabel("y:"));
		endPointPanel.add(endPointY);

		JPanel colorPanel = new JPanel();
		colorPanel.setBorder(BorderFactory.createTitledBorder("Edit Color"));

		colorPanel.add(new JLabel("color:"));
		colorPanel.add(fgColor);

		add(startPointPanel);
		add(endPointPanel);
		add(colorPanel);
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
		validateCoordinates(startPointX.getText());
		validateCoordinates(startPointY.getText());
		validateCoordinates(endPointX.getText());
		validateCoordinates(endPointY.getText());

		validateColorValue(fgColor.getText());
	}

	/**
	 * Accepts editing by writing values from all fields back into given
	 * corresponding object.
	 */
	@Override
	public void acceptEditing() {
		line.setStartPoint(new Point(Integer.parseInt(startPointX.getText()), Integer.parseInt(startPointY.getText())));
		line.setEndPoint(new Point(Integer.parseInt(endPointX.getText()), Integer.parseInt(endPointY.getText())));
		line.setFgColor(Color.decode(fgColor.getText()));
		
		line.notifyListeners();
	}
}