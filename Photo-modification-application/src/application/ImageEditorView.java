package a9;

import java.awt.BorderLayout;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ImageEditorView extends JPanel {
	
	private JButton open_from_url_button;
	private JFrame main_frame;
	private PictureView frame_view;
	private ImageEditorModel model;
	private ToolChooserWidget chooser_widget;
	private JPanel tool_ui_panel;
	private JPanel tool_ui;
	private JButton copy_color_button;
	private JButton undo_button;
	
	public ImageEditorView(JFrame main_frame, ImageEditorModel model) {
		this.main_frame = main_frame;
		
		setLayout(new BorderLayout());
		
		open_from_url_button = new JButton("Open from Url");
		
		frame_view = new PictureView(model.getCurrent());
		add(open_from_url_button, BorderLayout.NORTH);
		add(frame_view, BorderLayout.CENTER);
		
		tool_ui_panel = new JPanel();
		tool_ui_panel.setLayout(new BorderLayout());
		
		chooser_widget = new ToolChooserWidget();
		
		tool_ui_panel.add(chooser_widget, BorderLayout.NORTH);
		
		add(tool_ui_panel, BorderLayout.SOUTH);
		
		copy_color_button = new JButton("Copy Color");
		undo_button = new JButton("Undo");
		tool_ui_panel.add(copy_color_button, BorderLayout.SOUTH);
		tool_ui_panel.add(undo_button, BorderLayout.EAST);
		tool_ui = null;
	}

	public void addToolChoiceListener(ToolChoiceListener l) {
		chooser_widget.addToolChoiceListener(l);
	}
	
	public String getCurrentToolName() {
		return chooser_widget.getCurrentToolName();
	}

	public void installToolUI(JPanel ui) {
		if (tool_ui != null) {
			tool_ui_panel.remove(tool_ui);
		}
		tool_ui = ui;
		tool_ui_panel.add(tool_ui, BorderLayout.CENTER);
		validate();
		main_frame.pack();
	}
	
	@Override
	public void addMouseMotionListener(MouseMotionListener l) {
		frame_view.addMouseMotionListener(l);
	}
	
	@Override
	public void removeMouseMotionListener(MouseMotionListener l) {
		frame_view.removeMouseMotionListener(l);
	}

	@Override
	public void addMouseListener(MouseListener l) {
		frame_view.addMouseListener(l);
	}
	
	public void removeMouseListener(MouseListener l) {
		frame_view.removeMouseListener(l);
	}

	public void addActionListener(ImageEditorController image_editor_controller) {
		copy_color_button.addActionListener(image_editor_controller);
		open_from_url_button.addActionListener(image_editor_controller);
	}
	
	public void addActionListener(PaintBrushTool paint_brush_tool) {
		undo_button.addActionListener(paint_brush_tool);
	}
	
	public JButton getCopyColorButton() {
		return copy_color_button;
	}
	
	public JButton getUndoButton() {
		return undo_button;
	}
	
	public JButton getOpenFromUrlButton() {
		return open_from_url_button;
	}
	
	public void setPictureView(ObservablePicture ob_pic) {
		this.frame_view.setPicture(ob_pic);
		main_frame.pack();
	}

}
