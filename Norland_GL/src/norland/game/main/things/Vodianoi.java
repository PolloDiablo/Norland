package norland.game.main.things;

import norland.game.main.GlMainMenu;
import norland.game.main.GlRenderer;
import norland.game.main.Thing;
import norland.game.main.projectiles.Cannonball;
import norland.game.main.projectiles.Projectile;


public class Vodianoi extends Thing{
	

    private static int proj1Damage=20;
    private static double proj1Range =310;
    private static int prog1Separation=240;
    private int proj1Timer;
    private static Projectile proj1Prototype;

	
	private static final int DMG=180;	
	private static final int BoxWidth=44;
	private static final int BoxHeight=44;
	private static final int HEALTH= 200;
	private static double SIGHT_RADIUS;
	private boolean hasSeenViking;
	private static final double moveSpeed=0.45;
	
	public Vodianoi(int x, int y){
		super(GlRenderer.bitmapVod, x, y, moveSpeed,0,BoxWidth,BoxHeight,HEALTH,DMG,false,true);
		proj1Prototype = new Cannonball(proj1Damage, proj1Range, false);
        proj1Timer = 0;

        SIGHT_RADIUS=(int)(350*GlMainMenu.widthScale);
        this.hasSeenViking=false;
	}
	
    public void firePROJ1() {
    	proj1Prototype.copyRotationAndAngle(this);
        GlRenderer.addProjectile(Projectile.getProjectile(proj1Prototype));
    }
	
	public void update(){

		if (this.getState() == State.ALIVE) {
				 
			proj1Timer++;
			
			updateDistTo=distanceTo(GlRenderer.shipLocX, GlRenderer.shipLocY);
		    if(updateDistTo < SIGHT_RADIUS || this.hasSeenViking){
		    	this.hasSeenViking=true;
		    	 updateLeadScale= 1 -(1/(0.0001*updateDistTo*updateDistTo+1));
				 updateDirX = (GlRenderer.viking.getX()+GlRenderer.shipLeadX*updateLeadScale - getX());
				 updateDirY = (GlRenderer.viking.getY()+GlRenderer.shipLeadY*updateLeadScale - getY());
				 updateTheta = Math.atan2(updateDirY, updateDirX); 
		    	if(updateDistTo > 130*GlMainMenu.widthScale){
		    		setX(getX() + Math.cos(updateTheta)*getAdjustedMoveSpeed());
	             	setY(getY() + Math.sin(updateTheta)*getAdjustedMoveSpeed());
		    	}
		    	
		    	setAngle(updateTheta);      	    	
		    	
	            if (proj1Timer > prog1Separation && updateDistTo < proj1Prototype.getRange()) {
	                         firePROJ1();
	                         proj1Timer = 0;   
	            }
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
			this.dyingTimer++;
			this.hasSeenViking=false;
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
	
	
}
