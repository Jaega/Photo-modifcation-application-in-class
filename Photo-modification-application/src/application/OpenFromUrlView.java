package a9;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class OpenFromUrlView extends JPanel {
	
	private JTextField url_text_field;
	private JButton open_button;
	private JPanel url_text_field_panel;
	
	public OpenFromUrlView(JFrame url_frame, ImageEditorModel model) {
		
		setLayout(new BorderLayout());
		
		url_text_field = new JTextField();
		url_text_field.setPreferredSize(new Dimension(500, 10));
		
		open_button = new JButton("Open");
		open_button.setEnabled(false);
		
		
		url_text_field.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) {
				if(!open_button.isEnabled()) {
					open_button.setEnabled(true);
				}
				
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				open_button.setEnabled(false);
				if(url_text_field.getText().length() != 0) {
					open_button.setEnabled(true);
				}
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
			}
			
		});
		
		url_text_field_panel = new JPanel();
		url_text_field_panel.setLayout(new BorderLayout());
		url_text_field_panel.add(url_text_field, BorderLayout.CENTER);
		url_text_field_panel.add(open_button, BorderLayout.EAST);
		add(url_text_field_panel, BorderLayout.CENTER);
	}

	public void addActionListener(ImageEditorController image_editor_controller) {
		open_button.addActionListener(image_editor_controller);
		
	}
	
	
	public JButton getOpenButton() {
		return open_button;
	}
	
	public String getUrl() {
		String url = url_text_field.getText();
		if(url == null) {
			throw new RuntimeException("Please type in a url.");
		}
		return url;
	}


}
