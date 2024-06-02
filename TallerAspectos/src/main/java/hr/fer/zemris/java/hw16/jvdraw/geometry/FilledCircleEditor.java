package hr.fer.zemris.java.hw16.jvdraw.geometry;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import hr.fer.zemris.java.hw16.jvdraw.model.ObjectModelException;

/**
 * The class that represents an editor used for editing the attributes of a
 * filled circle previously drawn on the canvas.
 * 
 * @author Damjan Vučina
 */
public class FilledCircleEditor extends CircleEditor {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The filled circle. */
	private FilledCircle filledCircle;
	
	/** The background color. */
	private JTextField bgColor;

	/**
	 * Instantiates a new filled circle editor.
	 *
	 * @param filledCircle the filled circle
	 */
	public FilledCircleEditor(FilledCircle filledCircle) {
		super(filledCircle);
		this.filledCircle = filledCircle;
		
		initializeFilledCircle();
		setUpFilledCircle();
	}
	
	/**
	 * Initializes filled circle.
	 */
	protected void initializeFilledCircle() {
		bgColor = new JTextField(colorToHexString(filledCircle.getBgColor()));
	}
	
	/**
	 * Sets the up filled circle editor.
	 */
	protected void setUpFilledCircle() {
		JPanel bgColorPanel = new JPanel();
		bgColorPanel.setBorder(BorderFactory.createTitledBorder("Background"));

		bgColorPanel.add(new JLabel("color:"));
		bgColorPanel.add(bgColor);
		
		add(bgColorPanel);
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
		super.checkEditing();
		
		validateColorValue(bgColor.getText());
	}

	/**
	 * Accepts editing by writing values from all fields back into given
	 * corresponding object.
	 */
	@Override
	public void acceptEditing() {
		filledCircle.setBgColor(Color.decode(bgColor.getText()));
		
		super.acceptEditing();
	}

}
