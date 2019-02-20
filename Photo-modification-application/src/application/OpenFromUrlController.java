package a9;


public class OpenFromUrlController {
	
	private ImageEditorController image_editor_controller;
	private OpenFromUrlView ui;
	private ImageEditorModel model;
	
	public OpenFromUrlController(ImageEditorController image_editor_controller, OpenFromUrlView ui, ImageEditorModel model) {
		this.image_editor_controller = image_editor_controller;
		this.ui = ui;
		this.model = model;
		ui.addActionListener(image_editor_controller);
	}
	
	public OpenFromUrlView getUI() {
		return ui;
	}
	
	

}
