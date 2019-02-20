package a9;

import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PixelInspectorUI extends JPanel {

	private JLabel x_label;
	private JLabel y_label;
	private JLabel pixel_info;
	private JPanel magnifying_panel;
	private JLabel magnifying_label;
	private PictureView magnifying_area;
	
	
	public PixelInspectorUI() {
		
		x_label = new JLabel("X: ");
		y_label = new JLabel("Y: ");
		pixel_info = new JLabel("(r,g,b)");
		
		magnifying_label = new JLabel("Magnifying glass: ");
		magnifying_area = new PictureView(new ObservablePictureImpl(64,64));
		
		magnifying_panel = new JPanel();
		magnifying_panel.setLayout(new GridLayout(1,2));
		magnifying_panel.add(magnifying_label);
		magnifying_panel.add(magnifying_area);

		setLayout(new GridLayout(4,1));
		
		add(x_label);
		add(y_label);
		add(pixel_info);
		add(magnifying_panel);
	}
	
	public void setInfo(int x, int y, Pixel p) {
		x_label.setText("X: " + x);
		y_label.setText("Y: " + y);
		pixel_info.setText(String.format("(%3.2f, %3.2f, %3.2f)", p.getRed(), p.getBlue(), p.getGreen()));		
	}

	public void magnify(ObservablePicture magnified_picture) {
		magnifying_area.setPicture(magnified_picture);
		
	}
}
