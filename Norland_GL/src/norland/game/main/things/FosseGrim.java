package norland.game.main.things;

import java.util.Random;

import norland.game.main.GlRenderer;
import norland.game.main.Thing;
import norland.game.main.projectiles.GreenNote;
import norland.game.main.projectiles.Projectile;
import norland.game.main.projectiles.RedNote;

public class FosseGrim extends Thing {

	
    private static int noteRange=490;
    private static int noteSeparation=360;
    private int noteTimer;
    
    private static int redNoteDamage =200;
    private static Projectile redNotePrototype;

    private static int greenNoteDamage =-70;
    private static Projectile greenNotePrototype;
    
	private static final int DMG=200;	
	private static final int BoxWidth=70;
	private static final int BoxHeight=70;
	private static final int HEALTH= 800;
	
	public FosseGrim(int x, int y){
		super(GlRenderer.bitmapFosse, x, y, 0, 0,BoxWidth,BoxHeight,HEALTH,DMG,false,true);
		
		redNotePrototype = new RedNote(redNoteDamage, noteRange, false);
		greenNotePrototype = new GreenNote(greenNoteDamage, noteRange, false);
		
        noteTimer = 0;
        r = new Random();
	}
	
	
	private Random r;
    private void firePROJ1() {	
    	redNotePrototype.setX(this.getX());
    	redNotePrototype.setY(this.getY());
    	redNotePrototype.setAngle(r.nextFloat()*2*Math.PI-Math.PI);
    	GlRenderer.addProjectile(Projectile.getProjectile(redNotePrototype));
    }
    private void firePROJ2() {
    	greenNotePrototype.setX(this.getX());
    	greenNotePrototype.setY(this.getY());
    	greenNotePrototype.setAngle(r.nextFloat()*2*Math.PI-Math.PI);
    	GlRenderer.addProjectile(Projectile.getProjectile(greenNotePrototype));
    }

    private int i;
	public void update(){

		if (this.getState() == State.ALIVE) {
			
		    this.dyingTimer = 0;
		            	            		    
		    noteTimer++;
            if (noteTimer > noteSeparation&&this.distanceTo(GlRenderer.shipLocX, GlRenderer.shipLocY) < redNotePrototype.getRange()){
            		
            		double numberOfFires=(1+r.nextFloat()*2);
            		for(i=0;i<numberOfFires;i++){
            			 firePROJ1();
            			 firePROJ2();	 
            		}
                    noteTimer = 0;
            }
            
		    //prevents collision after DYING
		    if (this.respawning < 12)
		    	this.respawning = this.respawning + 1;
		    
		}else if(this.getState() == State.DYING){
			this.dyingTimer++;
		}else if(this.getState() == State.RESPAWNING){
		    this.respawning = this.respawning + 1;
		    if(this.respawning>12){
		    	setState(State.ALIVE);
		        	respawning=0;
		    }
		}	
			
	}/*update*/
	
}
