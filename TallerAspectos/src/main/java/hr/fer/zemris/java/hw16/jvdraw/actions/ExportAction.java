package hr.fer.zemris.java.hw16.jvdraw.actions;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import hr.fer.zemris.java.hw16.jvdraw.JVDraw;
import hr.fer.zemris.java.hw16.jvdraw.geometry.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.geometry.GeometricalObjectBBCalculator;
import hr.fer.zemris.java.hw16.jvdraw.geometry.GeometricalObjectPainter;

import static hr.fer.zemris.java.hw16.jvdraw.JDrawingCanvas.CANVAS_COLOR;

/**
 * The object responsible for exporting the currently drawn image in jpg, png or
 * gif format.
 * 
 * @author Damjan Vučina
 */
public class ExportAction extends AbstractAction {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The main window. */
	private JVDraw window;

	/**
	 * The object reponsible for calcualting the minimal bounding rectangle that
	 * covers all the drawn objects.
	 */
	private GeometricalObjectBBCalculator bbCalculator;

	/**
	 * Instantiates a new export action.
	 *
	 * @param window
	 *            the window
	 */
	public ExportAction(JVDraw window) {
		this.window = window;

		bbCalculator = new GeometricalObjectBBCalculator();
	}

	/**
	 * Method invoked when export action occured. Exports the currently drawn image
	 * in jpg, png or gif format.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		List<GeometricalObject> objects = window.getDocumentModel().getObjects();
		if (objects.isEmpty()) {
			JOptionPane.showMessageDialog(window, "Cannot export objects from empty canvas.", "Export not available",
					JOptionPane.INFORMATION_MESSAGE);
			return;

		}

		JFileChooser jfc = new JFileChooser();
		jfc.setDialogTitle("Export image");
		jfc.setFileFilter(UtilityProvider.getExportFilter());
		int dialogResult = jfc.showSaveDialog(window);

		if (dialogResult == JFileChooser.CANCEL_OPTION) {
			JOptionPane.showMessageDialog(window,
										  "Exporting cancelled.",
										  "Info",
										  JOptionPane.INFORMATION_MESSAGE);
			return;

		}

		Path savePath = jfc.getSelectedFile().toPath();
		if (UtilityProvider.isInvalidExtension(savePath, Arrays.asList(UtilityProvider.getExportExtensions()))) {
			JOptionPane.showMessageDialog(
						window,
						"Requested file name is not valid." + " Supported file extensions: .jpg, .jpeg, .png and .gif",
						"Invalid file name",
						JOptionPane.WARNING_MESSAGE);
			return;
		}

		if (UtilityProvider.extensionNotSet(savePath)) {
			savePath = Paths.get(String.valueOf(savePath) + "." + UtilityProvider.getExportExtensions()[0]);
			JOptionPane.showMessageDialog(window,
										  "JPG extension will be set by default.",
										  "Extension info",
										  JOptionPane.INFORMATION_MESSAGE);
		}

		for (GeometricalObject object : objects) {
			object.accept(bbCalculator);
		}
		Rectangle boundingBox = bbCalculator.getBoundingBox();

		BufferedImage bufferedImage = new BufferedImage(boundingBox.width,
														boundingBox.height,
														BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D g2d = bufferedImage.createGraphics();

		g2d.translate(-boundingBox.x, -boundingBox.y);

		g2d.setColor(CANVAS_COLOR);
		g2d.fillRect(boundingBox.x, boundingBox.y, boundingBox.width, boundingBox.height);

		GeometricalObjectPainter goPainter = window.getDrawingCanvas().getGoPainter();
		goPainter.setG2d(g2d);
		for (GeometricalObject object : objects) {
			object.accept(goPainter);
		}
		g2d.dispose();

		if (dialogResult == JFileChooser.APPROVE_OPTION) {
			int overwriteResult = 0;
			if (Files.exists(savePath)) {
				overwriteResult = JOptionPane.showConfirmDialog(
											  jfc,
											  "File already exists. Do you want to overwrite it?",
											  "File exists",
											  JOptionPane.YES_NO_OPTION);
				
				if (overwriteResult == JOptionPane.NO_OPTION) {
					return;
				}
			}
		}

		try {
			ImageIO.write(bufferedImage,
						  UtilityProvider.acquireExtension(String.valueOf(savePath)),
						  savePath.toFile());
			
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(window, "Error exporting image.", "Error occurred",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		JOptionPane.showMessageDialog(window, "Image successfully exported.", "Image exported",
				JOptionPane.INFORMATION_MESSAGE);
	}

}
