package norland.game.main;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import javax.microedition.khronos.opengles.GL10;
import android.content.Context;
import android.graphics.Bitmap;

/**Visuals have no angle (always 0)*/
public class Visual{
	private FloatBuffer vertexBuffer;   // buffer holding the vertices
	private FloatBuffer textureBuffer; // buffer holding the texture coords
	protected int[] textures = new int[1];	//what?
	private Bitmap bitmap;
	protected Bitmap getBitmap(){
		return bitmap;
	}
	
	//These represent the center of the square in the drawing world
	private double GLdrawX;
	private double GLdrawY;
	
	private float vertices[];
	private static float texture[] ={
			0.0f, 1.0f,		// top left (V2)
			0.0f, 0.0f,		// bottom left (V1)
			1.0f, 1.0f,		// top right (V4)
			1.0f, 0.0f,		// bottom right (V3)
	};
	private int adjustedWidthHalf;
	private int adjustedHeightHalf;
	
	private int textureIndex;


	public Visual(Bitmap bitmap, double width, double height){
		this.bitmap = bitmap;
	    this.adjustedWidthHalf = (int)(width*GlMainMenu.widthScale/2);
	    this.adjustedHeightHalf = (int)(height*GlMainMenu.heightScale/2);    
	}
	
	public void initShape(GL10 gl, Context context){

		this.vertices = new float[]{
				(-adjustedWidthHalf)*GlMainMenu.magicalScreenSizeNumber, (-adjustedHeightHalf)*GlMainMenu.magicalScreenSizeNumber,  0.0f,        // V1 - bottom left
				(-adjustedWidthHalf)*GlMainMenu.magicalScreenSizeNumber, (+adjustedHeightHalf)*GlMainMenu.magicalScreenSizeNumber,  0.0f,        // V2 - top left
				(+adjustedWidthHalf)*GlMainMenu.magicalScreenSizeNumber, (-adjustedHeightHalf)*GlMainMenu.magicalScreenSizeNumber,  0.0f,        // V3 - bottom right
				(+adjustedWidthHalf)*GlMainMenu.magicalScreenSizeNumber, (+adjustedHeightHalf)*GlMainMenu.magicalScreenSizeNumber,  0.0f         // V4 - top right
		};
		
		ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);
		vbb.order(ByteOrder.nativeOrder());
		vertexBuffer = vbb.asFloatBuffer();
		vertexBuffer.put(vertices);
		vertexBuffer.position(0);
		
	    vbb = ByteBuffer.allocateDirect(texture.length * 4);
	    vbb.order(ByteOrder.nativeOrder());
	    textureBuffer = vbb.asFloatBuffer();
	    textureBuffer.put(texture);
	    textureBuffer.position(0);
			
	    textureIndex = TextureStore.requestTexture(gl,bitmap);
	    
	    
	    /*
		// generate one texture pointer
		gl.glGenTextures(1, this.textures, 0);
		// bind the previously generated texture
		gl.glBindTexture(GL10.GL_TEXTURE_2D, this.textures[0]);
		// create nearest filtered texture
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
		// Use Android GLUtils to specify a two-dimensional texture image from our bitmap
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, this.bitmap, 0);*/
			
		
	}

	public void draw(GL10 gl, double x,double y){
		//Update location
		this.GLdrawX = fudgeX(x)*GlMainMenu.magicalScreenSizeNumber;
		this.GLdrawY = fudgeY(y)*GlMainMenu.magicalScreenSizeNumber;
		
		gl.glPushMatrix();
			gl.glTranslatef((float)GLdrawX,(float) -GLdrawY, 0);
			gl.glBindTexture(GL10.GL_TEXTURE_2D, TextureStore.getTexture(textureIndex));
			gl.glVertexPointer(3, GL10.GL_FLOAT, 0, this.vertexBuffer);
			gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, this.textureBuffer);
			// Draw the vertices as triangle strip
			gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, vertices.length / 3);
		gl.glPopMatrix();		
	}
	
	/**Same as above, but allows rotation*/
	public void draw(GL10 gl, double x,double y, double angle){
		//Update location
		this.GLdrawX = fudgeX(x)*GlMainMenu.magicalScreenSizeNumber;
		this.GLdrawY = fudgeY(y)*GlMainMenu.magicalScreenSizeNumber;
		
		gl.glPushMatrix();		
			//Translate and rotate mesh to desired location
			gl.glTranslatef((float) (+GLdrawX), (float) (-GLdrawY), 0);
			gl.glRotatef(-(float)(Math.toDegrees(angle)), 0 , 0, 1);
			gl.glBindTexture(GL10.GL_TEXTURE_2D, TextureStore.getTexture(textureIndex));
			gl.glVertexPointer(3, GL10.GL_FLOAT, 0, this.vertexBuffer);
			gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, this.textureBuffer);
			// Draw the vertices as triangle strip
			gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, vertices.length / 3);		
		gl.glPopMatrix();	
	}
	
	protected double fudgeX(double x){
		return x-GlRenderer.WIDTH_HALF;
	}
	protected double fudgeY(double y){
		return y-GlRenderer.HEIGHT_HALF;
	}
	
}
