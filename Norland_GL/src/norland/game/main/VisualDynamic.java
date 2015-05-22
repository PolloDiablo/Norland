package norland.game.main;

import java.util.HashMap;
import java.util.Map;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.opengl.GLUtils;

/** Use for text or number displays which require a changing bitmap */
public class VisualDynamic extends Visual {

	private final Canvas canvas;
	private final Paint textPaint;
	// These represent the center of the square in the drawing world
	private final int BoxW;
	private final int BoxH;
	private static final int TEXT_SIZE = 45;

	private static final Map<String, Bitmap> myBitmaps = new HashMap<String, Bitmap>();

	public VisualDynamic(int width, int height) {
		// Create an empty, mutable bitmap
		super(getBitmap(width, height), width, height);

		// Bitmap.createBitmap((int)(width),(int)(height),
		// Bitmap.Config.ARGB_8888)

		// TODO because we are creating new bitmaps, they each are unique.
		// initShape is called for VisualDynamics, so it keeps filling the
		// array...

		// Can we use the bitmapUNum here? (passed in?)

		this.BoxW = width;
		this.BoxH = height;

		// get a canvas to paint over the bitmap
		canvas = new Canvas(getBitmap());
		getBitmap().eraseColor(0);

		// setup the paint
		textPaint = new Paint();
		textPaint.setTextSize(TEXT_SIZE);
		textPaint.setAntiAlias(true);
		textPaint.setARGB(255, 255, 255, 255);
	}

	/**
	 * This either returns an existing bitmap of the correct dimensions to use
	 * for temp-drawing, or creates a new one.
	 * 
	 * @param width
	 * @param height
	 * @return
	 */
	private static Bitmap getBitmap(int width, int height) {
		final String key = width + "," + height;
		final Bitmap toReturn;
		if (myBitmaps.containsKey(key)) {
			toReturn = myBitmaps.get(key);
		} else {
			toReturn = Bitmap.createBitmap((width), (height),
					Bitmap.Config.ARGB_8888);
			myBitmaps.put(key, toReturn);
		}
		return toReturn;
	}

	public void updateBitmap(GL10 gl, Context context, String Text) {
		getBitmap().eraseColor(0);

		// Draw the text centered
		canvas.drawText(Text, (5), (BoxH / 2) + 2, textPaint);

		gl.glBindTexture(GL10.GL_TEXTURE_2D,
				TextureStore.getTexture(textureIndex));

		// Use the Android GLUtils to specify a two-dimensional texture image
		// from our bitmap
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, getBitmap(), 0);
	}

	/**
	 * Make all of these 0-255. >:l
	 * 
	 * @param a
	 * @param r
	 * @param g
	 * @param b
	 */
	public void setTextColour(int a, int r, int g, int b) {
		textPaint.setARGB(a, r, g, b);
	}

}
