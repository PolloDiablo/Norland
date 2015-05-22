package norland.game.main.levels;


import java.util.Random;

import javax.microedition.khronos.opengles.GL10;

import norland.game.main.GlMainMenu;
import norland.game.main.GlRenderer;
import norland.game.main.MainMenu_Activity;
import norland.game.main.R;
import norland.game.main.Thing;
import norland.game.main.VisualDynamic;
import norland.game.main.projectiles.Projectile;
import norland.game.main.things.Frigate;
import norland.game.main.things.Iceberg;
import android.content.Context;
import android.media.MediaPlayer;

public class level08 extends LevelSuper{

	private VisualDynamic objectiveNum;
	private Frigate boss;
	
	private boolean hasKilledBoss; //TODO
	
	public void addStuff() {
		
		ROCKCOUNT = 10;
		ICEBERGCOUNT = 35;
		GlRenderer.OUTSIDE_SIZE=(int) (360*GlMainMenu.widthScale);

		Random num = new Random();
		//Sketchy work-around
        int z = (int) (50000*GlMainMenu.widthScale);   		
        double rad = num.nextFloat()*2*Math.PI - Math.PI; 
        int x = (int)(z*Math.cos(rad));
        int y = (int)(z*Math.sin(rad));
        
        // TODO, this is only here to make levelsuper happy... bad
        objective = new Thing(GlRenderer.bitmapIsland1, x, y, 512, 512);
        objective.update();
        
		super.addStuff();
		
		objectiveNum = new VisualDynamic(140, 140);
		boss = new Frigate(0,0);
		GlRenderer.randomizeLocation(boss);
		
		GlRenderer.weaponsOn=true;
        GlRenderer.showCompass=true;
        GlRenderer.showCompassDistance=false;     
        	
		hasKilledBoss=false;

	}

	private static double dirX;
	private static double dirY;
	public void update() {
		super.update();
		dirX = (boss.getX()-GlRenderer.shipLocX);
        dirY = (boss.getY()-GlRenderer.shipLocY);
        GlRenderer.compassPointer.setAngle(Math.atan2(dirY, dirX));
		
		boss.update();

		//Boss ignores icebergs and rocks
		for(Iceberg i:icebergs){
			if(boss.hasCollided(i, false)){
				i.takeDamage(boss.getDamage());
			}
		}
		for(Thing r: rocks){
			if(boss.hasCollided(r, false)){
				r.takeDamage(boss.getDamage());
			}
		}
		for(Projectile p:GlRenderer.projectiles){	
			if(p.isFriendly() && boss.hasCollided(p,false)){
				p.takeDamage(boss.getDamage());
				hasKilledBoss = boss.takeDamage(p.getDamage());	
			}
		}
		if(boss.hasCollided(GlRenderer.viking,false)){
			GlRenderer.viking.takeDamage(boss.getDamage(), false, true);
		}
		
		if(hasKilledBoss){
			GlRenderer.levelFinished = true;
		}
		
		
	}
  
    private int percent;
	public void onDrawFrame(GL10 gl, Context context) {
		super.onDrawFrame(gl, context);		
		boss.draw(gl);	
		if(boss.getHealth()>0){
			percent=(int)(100*boss.getHealth()/boss.getMaximumHealth());
			if(percent==0) percent=1;
			objectiveNum.updateBitmap(gl, context, percent + "%");
		}else{
			objectiveNum.updateBitmap(gl, context,"0%");
		}
    	objectiveNum.draw(gl, (GlRenderer.shipLocX + GlRenderer.WIDTH_HALF -GlMainMenu.widthScale*65), 
    				GlRenderer.shipLocY-GlRenderer.HEIGHT_HALF+  GlMainMenu.heightScale*(45));   
	}
    public void initiateShapes(GL10 gl,Context context){
    	super.initiateShapes(gl, context);
    	objectiveNum.initShape(gl, context);
		boss.initShape(gl, context);
    }
    
    

	public void finishLevel(Context context) {
		if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_levelUnlock, 1) < 9){
			context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).edit().putInt(GlMainMenu.LOCAL_levelUnlock, 9).commit();
			if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_upgradesTotal, 0)<8){
				context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).edit().putInt(GlMainMenu.LOCAL_upgradesTotal, 8).commit();
			}
			GlMainMenu.setStartNextLevel(true);
			GlRenderer.startUpgradeScreen=true;
		}else{
			GlRenderer.backToMenu=true;
		}
		
	}

	public void startMusic(Context context) {
		GlMainMenu.mediaPlayer = MediaPlayer.create(context, R.raw.gamebossfrigatetheme);
	}

}
