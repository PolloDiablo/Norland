package norland.game.main.projectiles;

import norland.game.main.GlRenderer;
//TODO create "WaveProjectile"
public class GreenNote extends StraightProjectile {
	
    private static final int boxWidth = 34;
    private static final int boxHeight = 34;
    private static final double speed = 0.4;
    private static final boolean isFireDamage=false;
    private static final boolean isHullDamage=false;
    
    
    public GreenNote(double damage,double range, boolean friendly){
    	super(GlRenderer.bitmapGreenNote,0,0,speed, 0, damage, range,
    			boxWidth, boxHeight,friendly,isFireDamage,isHullDamage);
    } 
    
    
    private GreenNote(double damage,double range,double x, double y, double angle, boolean friendly){
    	super(GlRenderer.bitmapGreenNote,x,y,speed, angle, damage,range,
    			boxWidth, boxHeight,friendly,isFireDamage,isHullDamage);
    	
    } 

    
    //Should be called on a prototype
	@Override
	public Projectile makeProjectileForPool() {
		return new GreenNote(this.getDamage(), this.getRange(),this.getX(),this.getY(),this.getAngle(),this.isFriendly());
	}
}
