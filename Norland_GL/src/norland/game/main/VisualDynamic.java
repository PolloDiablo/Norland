package norland.game.main;

import javax.microedition.khronos.opengles.GL10;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.opengl.GLUtils;

/**Use for text or number displays which require a changing bitmap*/
public class VisualDynamic extends Visual{
	
	private Canvas canvas;
	Paint textPaint;
	Drawable background;	
	//These represent the center of the square in the drawing world
	private int BoxW;
	private int BoxH;
	
	public VisualDynamic(int width, int height){
		// Create an empty, mutable bitmap
		super(Bitmap.createBitmap((int)(width),(int)(height), Bitmap.Config.ARGB_8888),width,height);
	    this.BoxW = width;
	    this.BoxH = height;
	    
		// get a canvas to paint over the bitmap
		canvas = new Canvas(getBitmap());
		getBitmap().eraseColor(0);
	}	
	
	public void createBitmap(GL10 gl, Context context, int id, int textSize){
		// get a background image from resources
		// note the image format must match the bitmap format
		background = context.getResources().getDrawable(id);
		background.setBounds(0, 0,BoxW,BoxH);
		background.draw(canvas); // draw the background to our bitmap

		// Draw the text
		textPaint = new Paint();
		textPaint.setTextSize((int)(textSize));
		textPaint.setAntiAlias(true);
		textPaint.setARGB(255, 255, 255, 255);

		//Generate one texture pointer...
		gl.glGenTextures(1, textures, 0);
		//...and bind it to our array
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);
		//Create Nearest Filtered Texture
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
		//Different possible texture parameters, e.g. GL10.GL_CLAMP_TO_EDGE
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_REPEAT);
		//Use the Android GLUtils to specify a two-dimensional texture image from our bitmap
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, getBitmap(), 0);

	}

	public void updateBitmap(GL10 gl, Context context, String Text){

		getBitmap().eraseColor(0);
		background.draw(canvas); // draw the background to our bitmap

		// draw the text centered
		canvas.drawText(Text,(float)( 5),(BoxH/2)+2, textPaint);

		gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);
		//Create Nearest Filtered Texture
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
		//Different possible texture parameters, e.g. GL10.GL_CLAMP_TO_EDGE
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_REPEAT);
		//Use the Android GLUtils to specify a two-dimensional texture image from our bitmap
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, getBitmap(), 0);
		
	}	
	
	/** 
	 * Make all of these 0-255. >:l
	 * @param a
	 * @param r
	 * @param g
	 * @param b
	 */
	public void setTextColour(int a, int r, int g, int b){
		textPaint.setARGB(a, r, g, b);
	}

}
