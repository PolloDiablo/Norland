package norland.game.main.projectiles;

import norland.game.main.GlRenderer;

public class LeftWake extends StraightProjectile {
    private static final int boxWidth = 16;
    private static final int boxHeight = 16;
    private static final double speed = 0.11;
    private static final double damage = 0;
    private static final double range = 30;
    private static final boolean friendly=true;
    private static final boolean isFireDamage=false;
    private static final boolean isHullDamage=false;
    
    
    public LeftWake(){
    	super(GlRenderer.bitmapWakeL,0,0,speed, 0, damage, range,
    			boxWidth, boxHeight,friendly,isFireDamage,isHullDamage);
    } 
    
    
    private LeftWake(double x, double y, double angle){
    	super(GlRenderer.bitmapWakeL,x,y,speed, angle, damage,range,
    			boxWidth, boxHeight,friendly,isFireDamage,isHullDamage);
    	
    } 
 
    //Should be called on a prototype
	@Override
	public Projectile makeProjectileForPool() {
		return new LeftWake(this.getX(),this.getY(),this.getAngle());
	}
}
