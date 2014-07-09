package norland.game.main.projectiles;

import norland.game.main.Thing;
import android.graphics.Bitmap;
import android.util.Log;

public  abstract class TrackingProjectile extends Projectile{
	
    private Thing target;
	
    protected TrackingProjectile(Bitmap bitmap, double x, double y, double speed, double angle, double DMG, double rng,
            int BoxW, int BoxH, boolean friendly, boolean isFireDamage,boolean isHullDamage, Thing target) {
    	
    	super(bitmap,x,y,speed,angle,DMG,rng,BoxW,BoxH,friendly,isFireDamage,isHullDamage);
    	
    	this.target=target;
    }
	
    public Thing getTarget(){
    	return target;
    }
    public void setTarget(Thing target){
    	this.target=target;
    }

    public void update() {  	
        if (getState() == State.ALIVE) {	
			updateDirX = (target.getX()- getX());
			updateDirY = (target.getY()- getY());
			setAngle(Math.atan2(updateDirY, updateDirX)); 
	    	setX(getX() + Math.cos(getAngle())*getAdjustedMoveSpeed());
            setY(getY() + Math.sin(getAngle())*getAdjustedMoveSpeed());
            
            
		    this.distanceTravelled += getAdjustedMoveSpeed();
		    
		    if(target.getState()==State.DEAD ||target.getState()== State.DYING){
		    	this.setState(State.DYING);
		    }
        	//setX(getX() + Math.cos(getAngle())*getAdjustedMoveSpeed());
            //setY(getY() + Math.sin(getAngle())*getAdjustedMoveSpeed());
        	
        	

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
		try{
			this.target=((TrackingProjectile)prototype).getTarget();
		}catch(Exception e){
			Log.w("TrackingProjectile", "You passed me a prototype which is not a TrackingProjectile >:l");
		}
	}
}
