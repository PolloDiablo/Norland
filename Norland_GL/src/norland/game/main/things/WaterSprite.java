package norland.game.main.things;

import norland.game.main.GlMainMenu;
import norland.game.main.GlRenderer;
import norland.game.main.Thing;

public class WaterSprite extends Thing {

	//Only enemies and enemy projectiles should collide with these
	private static final int DMG=200;	
	private static final int BoxWidth=50;
	private static final int BoxHeight=50;
	private static final int HEALTH= 100;
	private static double AURA_RADIUS;
	
	public WaterSprite(int x, int y) {
		super(GlRenderer.bitmapSprite, x, y, 0,0,BoxWidth,BoxHeight,HEALTH,DMG,false,true);
        AURA_RADIUS=((int)(320*GlMainMenu.widthScale));
	}

	public static double getAURA_RADIUS() {
		return AURA_RADIUS;
	}
	

}
