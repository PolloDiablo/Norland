package norland.game.main.menus;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import norland.game.main.GlMainMenu;
import norland.game.main.GlRenderer;
import norland.game.main.Thing;

/**
 * This class is quite similar to MenuItem
 * But it is a bit smarter in how it handles the vertical placement of each menu item
 * 
 * @author Jeremy
 *
 */
public class UpgradeMenuItem {

	public enum UpgradeMenuItemType {TITLE,POINTS,BUTTON,TEXT,PLUS,MINUS,BOTTOM};
	//TODO The only reason this class doesn't extend Thing is because I wanted a switch in the constructor
	private Thing thing;
	
	public Thing getThing(){
		return thing;
	}
	
	// Locations and sizes for buttons/titles in the upgrade menu
	private final static double titleX = GlRenderer.WIDTH_HALF;
	private final static double titleY = GlMainMenu.heightScale*75;
	private final static double titleWidth = 512;
	private final static double titleHeight = 128;
	
	private final static double rowOffset = 50;
	private final static double rowSpacing = 100;
	
	private final static double pointsX = GlRenderer.WIDTH_HALF-GlMainMenu.widthScale*25;
	private final static double pointsWidth = 512;
	private final static double pointsHeight = 64;
	
	private final static double buttonX = GlRenderer.WIDTH_HALF;
	private final static double textX = GlMainMenu.widthScale*135;
	private final static double textWidth = 256;
	private final static double textHeight = 64;
	
	private final static double plusX = GlRenderer.WIDTH-GlMainMenu.widthScale*45;
	private final static double plusWidth = 64;
	private final static double plusHeight = 64;
	
	private final static double minusX = GlRenderer.WIDTH-GlMainMenu.widthScale*115;
	private final static double minusWidth = 64;
	private final static double minusHeight = 64;

	private final static double buttonBottomX = GlMainMenu.WIDTH/2;
	private final static double buttonBottomY = GlMainMenu.HEIGHT-GlMainMenu.heightScale*100;
	private final static double buttonBottomWidth = 256;
	private final static double buttonBottomHeight = 64;
	
	/**
	 * Values for row:
	 * 	0  			-> Title row
	 * 	1,2,3, etc.	-> Additional rows
	 *  <0			-> Bottom row
	 * */
	public UpgradeMenuItem(Bitmap bitmap, UpgradeMenuItemType buttonType, int row){
		double x;
		double y;
		double width;
		double height;
		
		// Set x location and size of button based on type
		switch(buttonType){
		case TITLE:
			x = titleX;
			width = titleWidth;
			height = titleHeight;
			break;
		case POINTS:
			x = pointsX;
			width = pointsWidth;
			height = pointsHeight;
			break;
		case BUTTON:
			x = buttonX;
			width = textWidth;
			height = textHeight;
			break;
		case TEXT:
			x = textX;
			width = textWidth;
			height = textHeight;
			break;
		case PLUS:
			x = plusX;
			width = plusWidth;
			height = plusHeight;
			break;
		case MINUS:
			x = minusX;
			width = minusWidth;
			height = minusHeight;
			break;
		case BOTTOM:
			x = buttonBottomX;
			width = buttonBottomWidth;
			height = buttonBottomHeight;
			break;
		default:
			Log.w("UpgradeMenuItem","Unknown menu item type given.");
			x = 0;
			width = 0;
			height = 0;
			break;
		}
		
		// Set y location
		if(row == 0){
			y =  titleY;
		}else if(row <0){
			y =  buttonBottomY;
		}else{
			// Offset each additional row by 100
			y =  GlMainMenu.heightScale*(rowOffset + rowSpacing*(row));
		}
		
		// Create the button
		thing = new Thing(bitmap,x,y,width,height);
	}
	
	public void update() {
		thing.update();
	}
	
	public void draw(GL10 gl) {
		thing.draw(gl);
	}
	
	public void initShape(GL10 gl,Context context) {
		thing.initShape(gl, context);
	}
	
}
	
