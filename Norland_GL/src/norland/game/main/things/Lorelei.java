package norland.game.main.things;

import norland.game.main.GlMainMenu;
import norland.game.main.GlRenderer;
import norland.game.main.Thing;
import norland.game.main.projectiles.Bolt;
import norland.game.main.projectiles.Projectile;
import norland.game.main.projectiles.TrackingProjectile;


public class Lorelei extends Thing {

    private static int boltDamage =-120;
    private static int boltRange = 330;
    private static int boltSeparation=140;
    private static double boltDefenseBuff=0.5;
    private int boltTimer;
    private static Projectile boltPrototype;
	
	
	private static final int DMG=5000;	
	private static final int BoxWidth=80;
	private static final int BoxHeight=40;
	private static final int HEALTH= 300;
	private static double SIGHT_RADIUS;
	//Matches target's movement speed
	
	public Lorelei(int x, int y) {
		super(GlRenderer.bitmapLorelei, x, y, 0, 0,BoxWidth,BoxHeight,HEALTH,DMG,false,true);

		//Count these projectiles as "friendly" so they collide with enemies
		boltPrototype = new Bolt(boltDamage, boltRange, true);  
        boltTimer = 0;
        
        SIGHT_RADIUS=(int)(470*GlMainMenu.widthScale);
        hasTarget = false;
	}
	

    public void firePROJ1() {
    	boltPrototype.copyRotationAndAngle(this);
    	((TrackingProjectile) boltPrototype).setTarget(this.target);
        GlRenderer.addProjectile(Projectile.getProjectile(boltPrototype)); 
    }
	
	private static double shipLeadX;
	private static double shipLeadY;
    
    private boolean hasTarget;
    private Thing target;
    private double targetLoc; //Only heal if target is active
	public void update(){

		if (this.getState() == State.ALIVE) {
			boltTimer++;		
		    if(!hasTarget){
		    	for(Thing e: GlRenderer.getLevel().getEnemies()){	

		    		if(e.getClass()!=FosseGrim.class && e.getClass()!=Lorelei.class && e.getState()==State.ALIVE){
			    		if(this.distanceTo(e.getX(), e.getY())<SIGHT_RADIUS){
			    			this.target=e;
			    			targetLoc=e.getX();
			    			this.hasTarget=true;
			    			setMoveSpeed(1.02*target.getBaseMoveSpeed());
			    			break;
			    		}
		    		}
		    	}
		    }else{	
		    	//Follow Target
		    	updateDistTo=distanceTo(target.getX(), target.getY());
			    if(updateDistTo <= SIGHT_RADIUS){		    	
			    	 updateLeadScale= 1 -(1/(0.0001*updateDistTo*updateDistTo+1));
			    	 
			    	 shipLeadX = Math.cos(target.getAngle())*target.getCollisionBoxWidth()*0.7*target.getBaseMoveSpeed();
			         shipLeadY = Math.sin(target.getAngle())*target.getCollisionBoxWidth()*0.7*target.getBaseMoveSpeed();
			    	 
					 updateDirX = (target.getX()+shipLeadX*updateLeadScale - getX());
					 updateDirY = (target.getY()+shipLeadY*updateLeadScale - getY());
					 updateTheta = Math.atan2(updateDirY, updateDirX); 
			    	if(updateDistTo > 220*GlMainMenu.widthScale){
			    		setX(getX() + Math.cos(updateTheta)*getAdjustedMoveSpeed());
		             	setY(getY() + Math.sin(updateTheta)*getAdjustedMoveSpeed());
			    	}
			    	
			    	setAngle(Math.atan2(updateDirY, updateDirX));      	    	
			    	
		            if (boltTimer > boltSeparation && updateDistTo < boltPrototype.getRange() && targetLoc != target.getX()) {
		                    firePROJ1();
		                    boltTimer = 0;   
		            }
		            
			    }else{
			    	hasTarget=false;
			    }
			    //Check for dead target
			    if(target.getState()==State.DEAD ||target.getState()== State.DYING){
			    	hasTarget=false;
			    }
			   
		    }

         	updateCollisionPolygon();
		    
		    if (this.respawning < 12)
		    	this.respawning = this.respawning + 1;// prevents collision
		                                                      // after DYING
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

	public static double getboltDefenseBuff() {
		return boltDefenseBuff;
	}

}
