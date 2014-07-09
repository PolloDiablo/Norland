package norland.game.main.things;

import java.util.Random;

import android.graphics.Bitmap;
import norland.game.main.GlRenderer;
import norland.game.main.Thing;

public class Rock extends Thing{
	
private static int DMG=1200;
public static double STUCK_DURATION=75;
public static double STUCK_AMOUNT=0;
	
	private static final int BoxWidth=44;
	private static final int BoxHeight=44;
	
	private static int ROCK_HEALTH= 1;
	
	public Rock(){
		super(chooseBitmap(), 0, 0, 0, 0,BoxWidth,BoxHeight,ROCK_HEALTH,DMG,false,true);

	}
	
	private static Bitmap chooseBitmap(){
		Random r = new Random();
		double z =r.nextDouble();
		if(z>0.666){
			return GlRenderer.bitmapRock;
		}else if(z<0.333){
			return GlRenderer.bitmapRock2;
		}else{
			return GlRenderer.bitmapRock3;
		}	
	}

}
