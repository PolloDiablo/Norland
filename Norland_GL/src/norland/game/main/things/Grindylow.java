package norland.game.main.things;

import norland.game.main.GlMainMenu;
import norland.game.main.GlRenderer;
import norland.game.main.Thing;

public class Grindylow extends Thing {
	
	private static final double SLOW_AMOUNT=0.50;
	private static final int SLOW_DURATION=48;
	
	private static final int DMG=1;	
	private static final int BoxWidth=90;
	private static final int BoxHeight=90;
	private static final int HEALTH=600;
	private static final double moveSpeed =0.9;
	
	private static double SIGHT_RADIUS;
	private boolean hasSeenViking;

	public Grindylow(double x, double y) {
		super(GlRenderer.bitmapGrindylow, x, y,moveSpeed,0, BoxWidth, BoxHeight, HEALTH, DMG,false,true);
		SIGHT_RADIUS=(int)(235*GlMainMenu.widthScale);
		this.hasSeenViking=false;
	}
	
	public void update(){

		if (this.getState() == State.ALIVE) {
				 	    
		    updateDistTo=distanceTo(GlRenderer.shipLocX, GlRenderer.shipLocY);
		    if(updateDistTo < SIGHT_RADIUS ||this.hasSeenViking){
		    	this.hasSeenViking=true;
				updateLeadScale= 1 -(1/(0.0001*updateDistTo*updateDistTo+1));
				updateDirX = (GlRenderer.viking.getX()+GlRenderer.shipLeadX*updateLeadScale - getX());
				updateDirY = (GlRenderer.viking.getY()+GlRenderer.shipLeadY*updateLeadScale - getY());
				updateTheta = Math.atan2(updateDirY, updateDirX);
				if(updateDistTo<20){
					//Match speed andangle of Viking if right on top
					setX(getX() + Math.cos(updateTheta)*GlRenderer.viking.getAdjustedMoveSpeed()*SLOW_AMOUNT);
		            setY(getY() + Math.sin(updateTheta)*GlRenderer.viking.getAdjustedMoveSpeed()*SLOW_AMOUNT);
				}else{
					setX(getX() + Math.cos(updateTheta)*getAdjustedMoveSpeed());
					setY(getY() + Math.sin(updateTheta)*getAdjustedMoveSpeed());
					     
				}
				setAngle(updateTheta);
		    	
		    }   
		            
		    a1 = this.getAngle()+ (Math.atan2(this.adjustedBoxHeight / 2.0, this.adjustedBoxWidth / 2.0));
		    a2 = this.getAngle()+ (Math.atan2(this.adjustedBoxHeight / 2.0, -this.adjustedBoxWidth / 2.0));
		    a3 = this.getAngle()+ (Math.atan2(-this.adjustedBoxHeight / 2.0, -this.adjustedBoxWidth / 2.0));
		    a4 = this.getAngle()+ (Math.atan2(-this.adjustedBoxHeight / 2.0, this.adjustedBoxWidth / 2.0));
		            
		    getCollisionXs()[0]=Math.cos(a1) * this.getCollisionH() + this.getX();
		    getCollisionXs()[1]=Math.cos(a2) * this.getCollisionH() + this.getX();
		    getCollisionXs()[2]=Math.cos(a3) * this.getCollisionH() + this.getX();
		    getCollisionXs()[3]=Math.cos(a4) * this.getCollisionH() + this.getX();
		        	
		    getCollisionYs()[0]=Math.sin(a1) * this.getCollisionH() + this.getY();
		    getCollisionYs()[1]=Math.sin(a2) * this.getCollisionH() + this.getY();
		    getCollisionYs()[2]=Math.sin(a3) * this.getCollisionH() + this.getY();
		    getCollisionYs()[3]=Math.sin(a4) * this.getCollisionH() + this.getY();
		            
		    getCollisionPoly().setPolyArrays(getCollisionXs(), getCollisionYs());
		            	    
		    if (this.respawning < 12)
		    	this.respawning = this.respawning + 1;// prevents collision
		                                                      // after DYING
		}else if(this.getState() == State.DYING){
			this.hasSeenViking=false;
			this.dyingTimer++;
		}else if(this.getState() == State.DEAD){
			this.hasSeenViking=false;
		}else if(this.getState() == State.RESPAWNING){
		    this.respawning = this.respawning + 1;
		    if(this.respawning>12){
		    	setState(State.ALIVE);
		        	respawning=0;
		    }
		}	
			
	}/*update*/
	
	public static double getSlowAmount() {
		return SLOW_AMOUNT;
	}
	public static int getSlowDuration() {
		return SLOW_DURATION;
	}	

}
