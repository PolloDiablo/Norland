package norland.game.main.projectiles;

import norland.game.main.GlMainMenu;
import norland.game.main.GlRenderer;

public class Mine extends StraightProjectile{
	
    private static final int boxWidth = 22;
    private static final int boxHeight = 22;
    private static final double speed = 0.04;
    private static final double damage = 750;
    private static final int range = (int)(100*GlMainMenu.widthScale);
    private static final boolean isFireDamage=false;
    private static final boolean isHullDamage=true;
    
    
    public Mine(){
    	super(GlRenderer.bitmapMine,0,0,speed, 0, damage, range,
    			boxWidth, boxHeight,true,isFireDamage,isHullDamage);
    } 
    
    
    private Mine(double x, double y, double angle){
    	super(GlRenderer.bitmapMine,x,y,speed, angle, damage,range,
    			boxWidth, boxHeight,true,isFireDamage,isHullDamage);
    	
    } 
 
    //Should be called on a prototype
	@Override
	public Projectile makeProjectileForPool() {
		return new Mine(this.getX(),this.getY(),this.getAngle());
	}
}
