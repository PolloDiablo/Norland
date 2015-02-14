package norland.game.main.menus;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import norland.game.main.GlMainMenu;
import norland.game.main.Thing;

public class MenuItem{
	
	// Stores the original location of the button (before camera starts panning around)
	private final double originalX;
	private final double originalY;
	
	// The only reason this class doesn't extend Thing is because I wanted a switch in the constructor
	private Thing thing;
	
	public Thing getThing(){
		return thing;
	}
	
	
	public enum ButtonType {TITLEREG,TITLEWIDE,TITLEHUGE,BETA,B1,B2,B3,B4,B5,BOTTOM,PREV,NEXT};
	
	//These are the given location and size of each button/title, this ensures consistency between menus
	private final static double titleX = GlMainMenu.WIDTH/2;
	private final static double titleY = GlMainMenu.heightScale*100;
	private final static double titleWidthRegular = 256;
	private final static double titleWidthLarge = 512;
	private final static double titleHeight = 64;
	private final static double titleHeightLarge = 128;
	
	private final static double betaX = GlMainMenu.widthScale*160 + GlMainMenu.WIDTH/2;
	private final static double betaY = GlMainMenu.heightScale*125;
	private final static double betaWidth = 128;
	private final static double betaHeight = 32;

	private final static double button1X = GlMainMenu.WIDTH/2;
	private final static double button1Y = GlMainMenu.heightScale*250;
	private final static double button2X = GlMainMenu.WIDTH/2;
	private final static double button2Y = GlMainMenu.heightScale*350;
	private final static double button3X = GlMainMenu.WIDTH/2;
	private final static double button3Y = GlMainMenu.heightScale*450;
	private final static double button4X = GlMainMenu.WIDTH/2;
	private final static double button4Y = GlMainMenu.heightScale*550;
	private final static double button5X = GlMainMenu.WIDTH/2;
	private final static double button5Y = GlMainMenu.heightScale*650;
	private final static double buttonBottomX = GlMainMenu.WIDTH/2;
	private final static double buttonBottomY = GlMainMenu.HEIGHT-GlMainMenu.heightScale*100;
	private final static double buttonWidth = 256;
	private final static double buttonHeight = 64;
	
	private final static double prevX = 0.25*GlMainMenu.WIDTH;
	private final static double prevY = GlMainMenu.HEIGHT-GlMainMenu.heightScale*200;
	private final static double prevWidth = 128;
	private final static double prevHeight = 64;
	private final static double nextX = 0.75*GlMainMenu.WIDTH;
	private final static double nextY = GlMainMenu.HEIGHT-GlMainMenu.heightScale*200;
	private final static double nextWidth = 128;
	private final static double nextHeight = 64;


	public MenuItem(Bitmap bitmap, ButtonType buttonType){
		double width;
		double height;
		
		// Set location and size of button based on type
		switch(buttonType){
		case TITLEREG:
			originalX = titleX;
			originalY = titleY;
			width = titleWidthRegular;
			height = titleHeight;
			break;
		case TITLEWIDE:
			originalX = titleX;
			originalY = titleY;
			width = titleWidthLarge;
			height = titleHeight;
			break;
		case TITLEHUGE:
			originalX = titleX;
			originalY = titleY;
			width = titleWidthLarge;
			height = titleHeightLarge;
			break;
		case BETA:
			originalX = betaX;
			originalY = betaY;
			width = betaWidth;
			height = betaHeight;
			break;
		case B1:
			originalX = button1X;
			originalY = button1Y;
			width = buttonWidth;
			height = buttonHeight;
			break;
		case B2:
			originalX = button2X;
			originalY = button2Y;
			width = buttonWidth;
			height = buttonHeight;
			break;
		case B3:
			originalX = button3X;
			originalY = button3Y;
			width = buttonWidth;
			height = buttonHeight;
			break;
		case B4:
			originalX = button4X;
			originalY = button4Y;
			width = buttonWidth;
			height = buttonHeight;
			break;
		case B5:
			originalX = button5X;
			originalY = button5Y;
			width = buttonWidth;
			height = buttonHeight;
			break;
		case BOTTOM:
			originalX = buttonBottomX;
			originalY = buttonBottomY;
			width = buttonWidth;
			height = buttonHeight;
			break;
		case PREV:
			originalX = prevX;
			originalY = prevY;
			width = prevWidth;
			height = prevHeight;
			break;
		case NEXT:
			originalX = nextX;
			originalY = nextY;
			width = nextWidth;
			height = nextHeight;
			break;
		default:
			Log.w("MenuItem","Unknown menu item type given.");
			originalX = 0;
			originalY = 0;
			width = 0;
			height = 0;
			break;
		}
		
		// Create the button
		thing = new Thing(bitmap,originalX,originalY,width,height);
	}

	public void update() {
		// TODO should just need to call update once, at first, not every cycle!
		// Plus do the regular ol' update
		thing.update();
	}
	
	public void draw(GL10 gl) {
		thing.draw(gl);
	}
	
	public void initShape(GL10 gl,Context context) {
		thing.initShape(gl, context);
	}

}
