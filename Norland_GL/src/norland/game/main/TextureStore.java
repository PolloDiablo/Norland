package norland.game.main;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import android.graphics.Bitmap;
import android.opengl.GLUtils;
import android.util.Log;

public class TextureStore {

	private static final int MAX_TEXTURES = 100;
	private static int[] textures = new int[MAX_TEXTURES];
	private static ArrayList<Bitmap> currentBitmaps = new ArrayList<Bitmap>();

	public static int requestTexture(GL10 gl, Bitmap bitmap){
		if( currentBitmaps.contains(bitmap) ){
			// This bitmap has a texture already, return its index
			return currentBitmaps.indexOf(bitmap);
		}else{
			// This bitmap needs a texture, create one
			currentBitmaps.add(bitmap);
			// New items always added to the end
			int textureIndex = currentBitmaps.size()-1;
			
			if(textureIndex >= MAX_TEXTURES){
				Log.w("TextureStore","Too many textures");
			}else{
				Log.w("TextureStore","Textures: " + textureIndex);
			}
			
			
			gl.glGenTextures(1, textures, textureIndex);
			//...and bind it to our array
			gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[textureIndex]);
			//Create Nearest Filtered Texture
			gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
			gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
			//Different possible texture parameters, e.g. GL10.GL_CLAMP_TO_EDGE
			gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_REPEAT);
			//Use the Android GLUtils to specify a two-dimensional texture image from our bitmap
			GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);

			return textureIndex;
		}

	}
	
	public static int getTexture(int textureIndex){
		return textures[textureIndex];
	}
	
	public static void resetTextures(){
		currentBitmaps.clear();
	}
	
}
