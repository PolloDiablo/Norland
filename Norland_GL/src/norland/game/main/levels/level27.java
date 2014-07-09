package norland.game.main.levels;

import java.util.Random;

import norland.game.main.GlMainMenu;
import norland.game.main.GlRenderer;
import norland.game.main.R;
import norland.game.main.Thing;
import android.content.Context;
import android.media.MediaPlayer;

public class level27 extends LevelSuper{

	public void addStuff() {

		ROCKCOUNT = 0;
		ICEBERGCOUNT = 0;
		GlRenderer.OUTSIDE_SIZE=(int) (250*GlMainMenu.widthScale);
			
		Random num = new Random();
        int z = (int) (4200*GlMainMenu.widthScale);   		
        double rad = num.nextFloat()*2*Math.PI - Math.PI; 
        int x = (int)(z*Math.cos(rad));
        int y = (int)(z*Math.sin(rad));
        objective = new Thing(GlRenderer.bitmapIsland1, x, y, 512, 512);	
        objective.update();
        
		super.addStuff();
		
		GlRenderer.weaponsOn=true;
	}

	public void finishLevel(Context context) {
		// TODO Auto-generated method stub
	}

	public void startMusic(Context context) {
		// TODO Auto-generated method stub
		GlMainMenu.mediaPlayer = MediaPlayer.create(context, R.raw.norlandmaintitle);
	}
}
