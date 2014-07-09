package norland.game.main.projectiles;

import norland.game.main.GlRenderer;

public class Cannonball extends StraightProjectile {
	
    private static final int boxWidth = 10;
    private static final int boxHeight = 10;
    private static final double speed = 5;
    private static final boolean isFireDamage=false;
    private static final boolean isHullDamage=false;
    
    
    public Cannonball(double damage,double range, boolean friendly){
    	super(GlRenderer.bitmapCannonball,0,0,speed, 0, damage, range,
    			boxWidth, boxHeight,friendly,isFireDamage,isHullDamage);
    } 
    
    
    private Cannonball(double damage,double range,double x, double y, double angle, boolean friendly){
    	super(GlRenderer.bitmapCannonball,x,y,speed, angle, damage,range,
    			boxWidth, boxHeight,friendly,isFireDamage,isHullDamage);
    	
    } 

    
    //Should be called on a prototype
	@Override
	public Projectile makeProjectileForPool() {
		return new Cannonball(this.getDamage(), this.getRange(),this.getX(),this.getY(),this.getAngle(),this.isFriendly());
	}
}
