/*A generic class for any type of projectile*/

package norland.game.main.projectiles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import norland.game.main.GlMainMenu;
import norland.game.main.Thing;
import android.graphics.Bitmap;
import android.util.Log;

public abstract class Projectile extends Thing {

    private static int PROJ_HLTH = 1;
    private double range;
    public double getRange(){
    	return this.range;
    }
   

    private boolean isFriendly;

    protected double distanceTravelled;
	public boolean hasBeenInitiated;
 
    static int d=0;
    protected Projectile(Bitmap bitmap, double x, double y, double speed, double angle, double DMG, double rng,
            int BoxW, int BoxH, boolean friendly, boolean isFireDamage,boolean isHullDamage) {
    	
        super(bitmap, x, y, speed, angle, BoxW, BoxH, PROJ_HLTH,DMG,isFireDamage,isHullDamage);
        this.range = rng*GlMainMenu.widthScale;
        distanceTravelled = 0;
        setFriendly(friendly);
        d++;
        hasBeenInitiated=false;
        Log.v("Projectile","NEW Projectile Num: "+ d);   
    }

    
    //Makes a new Projectile based on the prototype
    public abstract Projectile makeProjectileForPool();
    //Resets a pooled Projectile based on its class
    public abstract void resetProjectileFromPool(Projectile prototype);
    
    private static Map<Class<?>, ArrayList<Projectile>> projectileClassMap = new HashMap<Class<?>, ArrayList<Projectile>>();
    public static Projectile getProjectile(Projectile prototype){
    	//If this class of projectile does not have its own arraylist yet
    	if(!projectileClassMap.containsKey(prototype.getClass())){
     		projectileClassMap.put(prototype.getClass(), new ArrayList<Projectile>());
    	}
    	
    	if(projectileClassMap.get(prototype.getClass()).isEmpty()){
    		return prototype.makeProjectileForPool();
    	}else{
    		Projectile p = projectileClassMap.get(prototype.getClass()).get(0);
    		projectileClassMap.get(prototype.getClass()).remove(0);
    		p.reset(prototype.getX(), prototype.getY(), prototype.getBaseMoveSpeed(),prototype.getAngle());
            p.setDamage(prototype.getDamage());
            p.range = prototype.getRange();
            p.distanceTravelled = 0;
            p.setFriendly(prototype.isFriendly()); 
    		
            p.resetProjectileFromPool(prototype);
            
    		return p;
    	}	
    }
   
    public boolean isFriendly() {
        return isFriendly;
    }



    public double getDTrav() {
        return this.distanceTravelled;
    }

    public void setFriendly(boolean isFriendly) {
        this.isFriendly = isFriendly;
    }

    /**
     * Repools the Projectile to its class' pool
     */
	public void repool() {
		projectileClassMap.get(this.getClass()).add(this);
		//Log.d("Repool Called","Pool Size= " + projectilePool.size() + "  Currently Used= " + GlRenderer.projectiles.size() + "," + GlRenderer.wakes.size());
	}
	
	public static void clearAllPools(){
		for(ArrayList<Projectile> pArrayList : projectileClassMap.values()){
			for(int i=0; i<pArrayList.size();i++){
				pArrayList.remove(i);
				i--;		
			}
		}
	}
	
    public void copyRotationAndAngle(Thing other){
    	this.setX(other.getX());
    	this.setY(other.getY());
    	this.setAngle(other.getAngle());
    }

}
