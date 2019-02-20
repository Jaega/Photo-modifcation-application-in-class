package a9;

import java.util.ArrayList;
import java.util.List;

public class ImageEditorModel {

	private Picture original;
	private ObservablePicture current;
	private List<Picture> changed_area_record;
	
	public ImageEditorModel(Picture f) {
		original = f;
		current = original.copy().createObservable();
		changed_area_record = new ArrayList<Picture>();
	}

	public ObservablePicture getCurrent() {
		return current;
	}

	public Pixel getPixel(int x, int y) {
		return current.getPixel(x, y);
	}

	public ObservablePicture getMagnifiedPicture(int x, int y) {
		Picture magnified_picture = new PictureImpl(64, 64);
		//Using 4 pixels to present 1 pixel to reach magnification effect.
		for (int i = y - 16; i < y + 16; i++) {
			for (int j = x - 16; j < x + 16; j++) {
				Pixel copied_pixel = new ColorPixel(1.0, 1.0, 1.0);

				if (!(i < 0 || i > current.getHeight() || j < 0 || j > current.getWidth())) {
					//For magnifying area that is out of the picture boundary, substitute with white pixel.
					copied_pixel = current.getPixel(j, i);
				}

				for (int a = 2 * (i - (y - 16)); a < 2 * (i - (y - 16) + 1); a++) {
					for (int b = 2 * (j - (x - 16)); b < 2 * (j - (x - 16) + 1); b++) {
						magnified_picture.setPixel(b, a, copied_pixel);
					}
				}

			}
		}
		ObservablePicture observable_magnified_picture = magnified_picture.copy().createObservable();

		return observable_magnified_picture;
	}

	public void paintAt(int x, int y, Pixel brushColor, int brush_size, int opacity) {
		current.suspendObservable();
		
		double opacity_percentage = opacity/100.0;
		
		//Create a new picture to record the area information before the change;
		Picture pic = new PictureImpl(2*brush_size, 2*brush_size);
		

		for (int xpos = x - brush_size + 1; xpos <= x + brush_size - 1; xpos++) {
			for (int ypos = y - brush_size + 1; ypos <= y + brush_size - 1; ypos++) {
				if (xpos >= 0 && xpos < current.getWidth() && ypos >= 0 && ypos < current.getHeight()) {
					Pixel original_pixel = original.getPixel(xpos, ypos);
					
					//Blend the original pixel with brush color.
					double original_red = original_pixel.getRed();
					double original_green = original_pixel.getGreen();
					double original_blue = original_pixel.getBlue();
					
					double brush_red = brushColor.getRed();
					double brush_green = brushColor.getGreen();
					double brush_blue = brushColor.getBlue();
					
					double result_red = (1-opacity_percentage)*brush_red + opacity_percentage*original_red;
					double result_green = (1-opacity_percentage)*brush_green + opacity_percentage*original_green;
					double result_blue = (1-opacity_percentage)*brush_blue + opacity_percentage*original_blue;
					
					Pixel result_pixel = new ColorPixel(result_red, result_green, result_blue);
					pic.setPixel(xpos-(x-brush_size+1), ypos-(y-brush_size+1), current.getPixel(xpos, ypos));
					current.setPixel(xpos, ypos, result_pixel);
					
				}
			}
		}
		changed_area_record.add(pic);

		current.resumeObservable();
	}

	public void recover(int x, int y, int brush_size) {
		current.suspendObservable();

		for (int xpos = x - brush_size + 1; xpos <= x + brush_size - 1; xpos++) {
			for (int ypos = y - brush_size + 1; ypos <= y + brush_size - 1; ypos++) {
				if (xpos >= 0 && xpos < current.getWidth() && ypos >= 0 && ypos < current.getHeight()) {
					//Recover the changed area with previously recorded area info.
					current.setPixel(xpos, ypos, changed_area_record.get(changed_area_record.size()-1).getPixel(xpos-(x-brush_size+1), ypos-(y-brush_size+1)));
				}
			}
		}
		changed_area_record.remove(changed_area_record.size()-1);

		current.resumeObservable();
	}
	
	public void setPicture(Picture f) {
		original = f;
		current = original.copy().createObservable();
	}
}
