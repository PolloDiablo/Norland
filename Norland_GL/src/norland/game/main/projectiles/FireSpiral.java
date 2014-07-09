package norland.game.main.projectiles;

import norland.game.main.GlRenderer;

public class FireSpiral extends SpiralProjectile {
	
    private static final int boxWidth = 64;
    private static final int boxHeight = 16;
    private static final double speed = 3;
    private static final boolean isFireDamage=true;
    private static final boolean isHullDamage=false;
    
    
    public FireSpiral(int damage,double range, double rotationSpeed, boolean friendly){
    	super(GlRenderer.bitmapFireBreath,0,0,speed, 0, damage, range,
    			boxWidth, boxHeight,friendly,isFireDamage,isHullDamage, rotationSpeed);
    } 
    
    
    private FireSpiral(double x, double y, double angle,double damage, double range, double rotationSpeed, boolean friendly){
    	super(GlRenderer.bitmapFireBreath,x,y,speed, angle, damage, range,
    			boxWidth, boxHeight,friendly,isFireDamage,isHullDamage, rotationSpeed);
    	
    } 

    
    //Should be called on a prototype
	@Override
	public Projectile makeProjectileForPool() {
		return new FireSpiral(this.getX(),this.getY(),this.getAngle(), this.getDamage(), this.getRange(),this.getRotationSpeed(),this.isFriendly());
	}

}
