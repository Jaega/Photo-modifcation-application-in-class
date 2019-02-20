package a9;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ImageEditorController implements ToolChoiceListener, MouseListener, MouseMotionListener, ActionListener {

	private ImageEditorView view;
	private ImageEditorModel model;
	private Tool current_tool;
	private PixelInspectorTool inspector_tool;
	private PaintBrushTool paint_brush_tool;
	private OpenFromUrlController open_from_url_controller;
	private JFrame url_frame;

	public ImageEditorController(ImageEditorView view, ImageEditorModel model) {
		this.view = view;
		this.model = model;

		inspector_tool = new PixelInspectorTool(model);
		paint_brush_tool = new PaintBrushTool(model);
		

		view.addToolChoiceListener(this);
		view.addMouseListener(this);
		view.addMouseMotionListener(this);
		view.addActionListener(this);
		view.addActionListener(paint_brush_tool);

		this.toolChosen(view.getCurrentToolName());
	}

	@Override
	public void toolChosen(String tool_name) {
		if (tool_name.equals("Pixel Inspector")) {
			view.installToolUI(inspector_tool.getUI());
			current_tool = inspector_tool;
		} else if (tool_name.equals("Paint Brush")) {
			view.installToolUI(paint_brush_tool.getUI());
			current_tool = paint_brush_tool;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		current_tool.mouseClicked(e);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		current_tool.mousePressed(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		current_tool.mouseReleased(e);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		current_tool.mouseEntered(e);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		current_tool.mouseExited(e);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		current_tool.mouseDragged(e);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		current_tool.mouseMoved(e);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton source_button = ((JButton) e.getSource());
		if (source_button.equals(view.getCopyColorButton())) {
			Pixel pixel_info = inspector_tool.getPixelInfo();
			paint_brush_tool.pasteChosenColor(pixel_info);
		} else if (source_button.equals(view.getOpenFromUrlButton())) {
			//Configure a new frame that allows user to enter url
			if (url_frame == null) {
				url_frame = new JFrame();
				url_frame.setTitle("Open Picture from URl");
				url_frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

				OpenFromUrlView open_from_url_view = new OpenFromUrlView(url_frame, model);
				open_from_url_controller = new OpenFromUrlController(this, open_from_url_view, model);

				JPanel top_panel = new JPanel();
				top_panel.setLayout(new BorderLayout());
				top_panel.add(open_from_url_view, BorderLayout.CENTER);
				url_frame.setContentPane(top_panel);
				url_frame.pack();
			}
			url_frame.setVisible(true);

		} else if (source_button.equals(open_from_url_controller.getUI().getOpenButton())) {
			Picture f;
			try {
				//Change the picture and close the url window
				f = PictureImpl.readFromURL(open_from_url_controller.getUI().getUrl());
				model.setPicture(f);
				view.setPictureView(model.getCurrent());
				url_frame.dispose();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}

	}

}
