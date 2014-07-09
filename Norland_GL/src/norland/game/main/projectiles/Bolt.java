package norland.game.main.projectiles;

import norland.game.main.GlRenderer;
import norland.game.main.Thing;

public class Bolt extends TrackingProjectile {
	
    private static final int boxWidth = 20;
    private static final int boxHeight = 10;
    private static final double speed = 6.5;
    private static final boolean isFireDamage=false;
    private static final boolean isHullDamage=false;
    
    
    public Bolt(double damage,double range, boolean friendly){
    	super(GlRenderer.bitmapLoreleiProj,0,0,speed, 0, damage, range,
    			boxWidth, boxHeight,friendly,isFireDamage,isHullDamage,null);
    }     
    
    private Bolt(double damage,double range,double x, double y, double angle, Thing target,boolean friendly){
    	super(GlRenderer.bitmapLoreleiProj,x,y,speed, angle, damage,range,
    			boxWidth, boxHeight,friendly,isFireDamage,isHullDamage,target);
    	
    } 

    
    //Should be called on a prototype
	@Override
	public Projectile makeProjectileForPool() {
		return new Bolt(this.getDamage(), this.getRange(),this.getX(),this.getY(),this.getAngle(), this.getTarget(),this.isFriendly());
	}
}
