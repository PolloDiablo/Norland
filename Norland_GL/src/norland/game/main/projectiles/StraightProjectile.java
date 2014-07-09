package norland.game.main.projectiles;

import android.graphics.Bitmap;

public abstract class StraightProjectile extends Projectile {
	
    private double xMovement;
    private double yMovement;
	
    protected StraightProjectile(Bitmap bitmap, double x, double y, double speed, double angle, double DMG, double rng,
            int BoxW, int BoxH, boolean friendly, boolean isFireDamage,boolean isHullDamage) {
    	
    	super(bitmap,x,y,speed,angle,DMG,rng,BoxW,BoxH,friendly,isFireDamage,isHullDamage);
    	
        xMovement = Math.cos(getAngle())*getAdjustedMoveSpeed();
        yMovement = Math.sin(getAngle())*getAdjustedMoveSpeed();
    }
	

    public void update() {  	
        if (getState() == State.ALIVE) {	
		    setX( getX() + xMovement);
		    setY( getY() + yMovement);
		    this.distanceTravelled += getAdjustedMoveSpeed();
		    
            if (this.distanceTravelled > (this.getRange())) {
            	this.distanceTravelled=0;
                setState(State.DYING);
            }
        }else if(this.getState() == State.DYING){
			this.dyingTimer++;
		}
    }
    
	@Override
	public void resetProjectileFromPool(Projectile prototype){
		//We don't need the prototype here
	    xMovement = Math.cos(getAngle())*getAdjustedMoveSpeed();
	    yMovement = Math.sin(getAngle())*getAdjustedMoveSpeed();
	}

}
