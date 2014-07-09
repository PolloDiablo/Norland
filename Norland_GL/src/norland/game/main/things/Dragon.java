package norland.game.main.things;

import norland.game.main.GlMainMenu;
import norland.game.main.GlRenderer;
import norland.game.main.Thing;
import norland.game.main.projectiles.FireBreath;
import norland.game.main.projectiles.Projectile;

public class Dragon extends Thing {

    private int burstCounter;
    private int proj1Timer;
    private static final int proj1Separation = 180;
    private static Projectile proj1Prototype;


    private static final int DMG = 500; // Collisions should not happen for Dragons
    private static final int HEALTH = 600;
    private static final int SIGHT_RADIUS =(int)(900*GlMainMenu.widthScale);
    private static final int BoxWidth = 64;
    private static final int BoxHeight = 64;
    private static final double moveSpeed = 0.7;

    public Dragon(int x, int y) {
        super(GlRenderer.bitmapDragon, x, y, moveSpeed,0, BoxWidth, BoxHeight, HEALTH,DMG,false,false);    
        proj1Prototype = new FireBreath(false);
        proj1Timer = 0;
        burstCounter = 0;
    }

    public void firePROJ1() {
    	proj1Prototype.copyRotationAndAngle(this);
    	GlRenderer.addProjectile(Projectile.getProjectile(proj1Prototype));
    }

    public void update() {
        if (getState() == State.ALIVE) {
            
            proj1Timer++;
        	
			updateDistTo=distanceTo(GlRenderer.shipLocX, GlRenderer.shipLocY);
		    if(updateDistTo < SIGHT_RADIUS){
		    	 updateLeadScale= 1 -(1/(0.0001*updateDistTo*updateDistTo+1));
				 updateDirX = (GlRenderer.viking.getX()+GlRenderer.shipLeadX*updateLeadScale - getX());
				 updateDirY = (GlRenderer.viking.getY()+GlRenderer.shipLeadY*updateLeadScale - getY());
				 updateTheta = Math.atan2(updateDirY, updateDirX);
		    	
		    	if(updateDistTo > 100*GlMainMenu.widthScale){
		    		setX(getX() + Math.cos(updateTheta)*getAdjustedMoveSpeed());
	             	setY(getY() + Math.sin(updateTheta)*getAdjustedMoveSpeed());
		    	}
		    	setAngle(updateTheta);   
		    	
		    	
	            if (proj1Timer > proj1Separation && updateDistTo < proj1Prototype.getRange()){
	                burstCounter++;
	                if (burstCounter < 5) {
	                    firePROJ1();
	                } else {
	                    burstCounter = 0;
	                    proj1Timer = 0;
	                }
	            }
	            
		    }	

		    if (this.respawning < 12)
		    	this.respawning = this.respawning + 1;
		    
        }else if(this.getState() == State.DYING){
			this.dyingTimer++;
		}else if(this.getState() == State.RESPAWNING){
        	this.respawning = this.respawning + 1;
        	if(this.respawning>12){
        		this.setState(State.ALIVE);
        		respawning=0;
        	}
        }
    }

}
