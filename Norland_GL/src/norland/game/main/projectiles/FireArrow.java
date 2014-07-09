package norland.game.main.projectiles;

import norland.game.main.GlRenderer;

public class FireArrow extends StraightProjectile{
	
    private static final int boxWidth = 22;
    private static final int boxHeight = 6;
    private static final double speed = 5;
    private static final boolean isFireDamage=true;
    private static final boolean isHullDamage=false;
    
    
    public FireArrow(double damage,double range, boolean friendly){
    	super(GlRenderer.bitmapFireArrow,0,0,speed, 0, damage, range,
    			boxWidth, boxHeight,friendly,isFireDamage,isHullDamage);
    } 
    
    
    private FireArrow(double damage,double range,double x, double y, double angle, boolean friendly){
    	super(GlRenderer.bitmapFireArrow,x,y,speed, angle, damage,range,
    			boxWidth, boxHeight,friendly,isFireDamage,isHullDamage);
    	
    } 
 
    //Should be called on a prototype
	@Override
	public Projectile makeProjectileForPool() {
		return new FireArrow(this.getDamage(), this.getRange(),this.getX(),this.getY(),this.getAngle(),this.isFriendly());
	}
}
