package norland.game.main.projectiles;

import norland.game.main.GlRenderer;

public class FireBreath extends StraightProjectile {

    private static final int boxWidth = 64;
    private static final int boxHeight = 16;
    private static final double speed = 3;
    private static final int damage = 20;
    private static double range=150;
    private static final boolean isFireDamage=true;
    private static final boolean isHullDamage=false;
    
    
    public FireBreath(boolean friendly){
    	super(GlRenderer.bitmapFireBreath,0,0,speed, 0, damage, range,
    			boxWidth, boxHeight,friendly,isFireDamage,isHullDamage);
    } 
    
    
    private FireBreath(double x, double y, double angle, boolean friendly){
    	super(GlRenderer.bitmapFireBreath,x,y,speed, angle, damage, range,
    			boxWidth, boxHeight,friendly,isFireDamage,isHullDamage);
    	
    } 

    
    //Should be called on a prototype
	@Override
	public Projectile makeProjectileForPool() {
		return new FireBreath(this.getX(),this.getY(),this.getAngle(),this.isFriendly());
	}
    
}
