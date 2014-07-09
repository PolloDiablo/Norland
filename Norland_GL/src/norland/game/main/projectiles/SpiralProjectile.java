package norland.game.main.projectiles;
import android.graphics.Bitmap;

public abstract class SpiralProjectile extends Projectile {
	
	private double rotationSpeed;

    protected SpiralProjectile(Bitmap bitmap, double x, double y, double speed, double angle, double DMG, double rng,
            int BoxW, int BoxH, boolean friendly, boolean isFireDamage,boolean isHullDamage,double rotationSpeed) {
    	
    	super(bitmap,x,y,speed,angle,DMG,rng,BoxW,BoxH,friendly,isFireDamage,isHullDamage);
    
    	this.rotationSpeed=rotationSpeed;
    }
	
    public void update() {
    	
        if (getState() == State.ALIVE) {      	
		    setX( getX() + Math.cos(getAngle())*getAdjustedMoveSpeed());
		    setY( getY() + Math.sin(getAngle())*getAdjustedMoveSpeed());
		    this.setAngle(getAngle()+rotationSpeed);
		    this.distanceTravelled += getAdjustedMoveSpeed();
		    
            if (this.distanceTravelled > (getRange())) {
            	this.distanceTravelled=0;
                setState(State.DYING);
            }
        }else if(this.getState() == State.DYING){
			this.dyingTimer++;
		}
    }
    
    public double getRotationSpeed(){
    	return rotationSpeed;
    }
    public void setRotationSpeed(double rotationSpeed){
    	this.rotationSpeed = rotationSpeed;
    }
    
	@Override
	public void resetProjectileFromPool(Projectile p){
		//Nothing special 
	}
}
