package a9;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class PaintBrushTool implements Tool, ActionListener {

	private PaintBrushToolUI ui;
	private ImageEditorModel model;
	private List<Coordinate> changed_coordinates_record = new ArrayList<Coordinate>();
	private List<Integer> brush_size_record = new ArrayList<Integer>();

	public PaintBrushTool(ImageEditorModel model) {
		this.model = model;
		ui = new PaintBrushToolUI();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		model.paintAt(e.getX(), e.getY(), ui.getBrushColor(), ui.getBrushSize(), ui.getOpacity());
		changed_coordinates_record.add(new Coordinate(e.getX(), e.getY()));
		brush_size_record.add(ui.getBrushSize());
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		model.paintAt(e.getX(), e.getY(), ui.getBrushColor(), ui.getBrushSize(), ui.getOpacity());
		changed_coordinates_record.add(new Coordinate(e.getX(), e.getY()));
		brush_size_record.add(ui.getBrushSize());
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getName() {
		return "Paint Brush";
	}

	@Override
	public JPanel getUI() {
		return ui;
	}

	public void pasteChosenColor(Pixel pixel_info) {
		ui.updateUIWithChosenColor(pixel_info);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (changed_coordinates_record.size() == 0 && brush_size_record.size() == 0) {
			return;
		} else {
			int x = changed_coordinates_record.get(changed_coordinates_record.size() - 1).getX();
			int y = changed_coordinates_record.get(changed_coordinates_record.size() - 1).getY();
			int brush_size = brush_size_record.get(brush_size_record.size() - 1);
			model.recover(x, y, brush_size);
			changed_coordinates_record.remove(changed_coordinates_record.size() - 1);
			brush_size_record.remove(brush_size_record.size() - 1);
		}

	}
}
