package norland.game.main.things;

import norland.game.main.GlRenderer;
import norland.game.main.Thing;
import norland.game.main.projectiles.Cannonball;
import norland.game.main.projectiles.FireSpiral;
import norland.game.main.projectiles.Projectile;
import norland.game.main.projectiles.SpiralProjectile;
import android.util.Log;

public class Frigate extends Thing{
	
	
    private static int cannonDamage=45;
    private static double cannonRange =500;
    private static int cannonSeparation=220;
    private int cannonTimer;
    private static Projectile cannonPrototype;
	


    private static int fireSpiralDamage =35;
    private static int fireSpiralRange=500;
    private static int fireSpiralSeparation=700;
    private static double fireSpiralRotationSpeed=0.015;
    private int fireSpiralTimer;
    private static Projectile fireSpiralPrototype;

    
    private static double[] cannonAngles10;
	
	private static final int DMG=1000;	
	private static final int BoxWidth=200;
	private static final int BoxHeight=100;
	private static final int HEALTH= 12000;
	private static final double TURNING_SPEED= 0.002;
	private static final double moveSpeed=0.5;
	
	public Frigate(int x, int y){
		super(GlRenderer.bitmapFrigate, x, y, moveSpeed,0,BoxWidth,BoxHeight,HEALTH,DMG,false,true);

		cannonPrototype = new Cannonball(cannonDamage, cannonRange, false);
        cannonTimer = 0;
		
        fireSpiralPrototype = new FireSpiral(fireSpiralDamage, fireSpiralRange,fireSpiralRotationSpeed, false);
        fireSpiralTimer = 0;

     
        cannonAngles10 = new double[] { Math.toRadians(50), Math.toRadians(70), Math.toRadians(90) ,Math.toRadians(110), Math.toRadians(130),
        		Math.toRadians(-50), Math.toRadians(-70), Math.toRadians(-90), Math.toRadians(-110), Math.toRadians(-130)};
        
        //For collisions by section
        double tempQuarterBoxWidth = 0.42*adjustedBoxWidth;
        double tempQuarterBoxHeight = 0.42*adjustedBoxHeight;
        quarterHypo = Math.hypot(tempQuarterBoxWidth, tempQuarterBoxHeight)/2;
        quarterLowerHypo=Math.min((tempQuarterBoxHeight  / 2.0), (tempQuarterBoxWidth / 2.0));
	}
	
    private void firePROJ1_10() {
    	for(int i=0;i<10;i++){
        	cannonPrototype.setX(this.getX());
        	cannonPrototype.setY(this.getY());
        	cannonPrototype.setAngle(getAngle() + cannonAngles10[i]);
        	GlRenderer.addProjectile(Projectile.getProjectile(cannonPrototype)); 
    	}
        cannonTimer = 0;
    }
    
    private int burstCounter;
    private void firePROJ2() {
	    burstCounter++;
	    if (burstCounter < 4) {
        	fireSpiralPrototype.setX(this.getX());
        	fireSpiralPrototype.setY(this.getY());
        	for(int i=0;i<4;i++){
            	fireSpiralPrototype.setAngle(fireSpiralPrototype.getAngle()+Math.PI/2);  
            	((SpiralProjectile) fireSpiralPrototype).setRotationSpeed(-fireSpiralRotationSpeed);
            	GlRenderer.addProjectile(Projectile.getProjectile(fireSpiralPrototype));
               	((SpiralProjectile) fireSpiralPrototype).setRotationSpeed(+fireSpiralRotationSpeed);
            	GlRenderer.addProjectile(Projectile.getProjectile(fireSpiralPrototype));
        	}
	    } else {
	        burstCounter = 0;
	        fireSpiralTimer = 0;
	    }
    }
	
    
	//Distances to either side of the viking ship, will choose closest
	private double distA;
	private double distB; 
	private static final double dist=270;
	//Only pubic for testing
	public static double targetX;
	public static double targetY;
    public void update(){

		if (this.getState() == State.ALIVE) {
				 
			cannonTimer++;
			fireSpiralTimer++;
			
			//Get target destination
			distA=distanceTo(GlRenderer.shipLocX+dist*Math.cos(GlRenderer.viking.getAngle()+Math.PI/4),
					GlRenderer.shipLocY+dist*Math.sin(GlRenderer.viking.getAngle()+Math.PI/4));	
			distB=distanceTo(GlRenderer.shipLocX+dist*Math.cos(GlRenderer.viking.getAngle()-Math.PI/4),
					GlRenderer.shipLocY+dist*Math.sin(GlRenderer.viking.getAngle()-Math.PI/4));
			if(distA<distB){
				targetX=GlRenderer.shipLocX+dist*Math.cos(GlRenderer.viking.getAngle()+Math.PI/4);
				targetY=GlRenderer.shipLocY+dist*Math.sin(GlRenderer.viking.getAngle()+Math.PI/4);
				updateDistTo=distA;
			}else{
				targetX=GlRenderer.shipLocX+dist*Math.cos(GlRenderer.viking.getAngle()-Math.PI/4);
				targetY=GlRenderer.shipLocY+dist*Math.sin(GlRenderer.viking.getAngle()-Math.PI/4);
				updateDistTo=distB;
			}
			
			//Update location
			desiredAngle = Math.atan2(targetY-this.getY(),targetX-this.getX());	
			updateDirection();
		    setX(getX() + Math.cos(getAngle())*getAdjustedMoveSpeed());
	        setY(getY() + Math.sin(getAngle())*getAdjustedMoveSpeed());   
		    
		    //Perform an attack if viking is within range
	        if (cannonTimer > cannonSeparation && updateDistTo < cannonPrototype.getRange()) {
	            firePROJ1_10();
	        }
	        
	        if (fireSpiralTimer > fireSpiralSeparation && updateDistTo < fireSpiralRange) {
	            firePROJ2();
	        }
	        
		    		  
		    //Update collision parameters for collisions by section   
	        xCollisionCoord1 = getX()- 0.375*Math.cos(this.getAngle())*this.adjustedBoxWidth;
	        xCollisionCoord2 = getX()- 0.125*Math.cos(this.getAngle())*this.adjustedBoxWidth;
	        xCollisionCoord3 = getX()+ 0.125*Math.cos(this.getAngle())*this.adjustedBoxWidth;
	        xCollisionCoord4 = getX()+ 0.375*Math.cos(this.getAngle())*this.adjustedBoxWidth;
	        yCollisionCoord1 = getY()- 0.375*Math.sin(this.getAngle())*this.adjustedBoxWidth;
	        yCollisionCoord2 = getY()- 0.125*Math.sin(this.getAngle())*this.adjustedBoxWidth;
	        yCollisionCoord3 = getY()+ 0.125*Math.sin(this.getAngle())*this.adjustedBoxWidth;
	        yCollisionCoord4 = getY()+ 0.375*Math.sin(this.getAngle())*this.adjustedBoxWidth;
   
		    //Prevents collision after DYING
		    if (this.respawning < 12)
		    	this.respawning = this.respawning + 1;
		    
		}else if(this.getState() == State.DYING){
			this.dyingTimer++;
		}
			
	}
    
     
    private static double desiredAngle = Double.NaN;
    private static double curAngle;
    private static double dif; 
    //Shifts current angle closer to target angle
    private void updateDirection(){
            // Get the difference between the two angles
            curAngle = getAngle();
            while (curAngle > Math.PI) {
                curAngle -= 2 * Math.PI;
            }
            while (curAngle < -Math.PI) {
                curAngle += 2 * Math.PI;
            }
            dif = desiredAngle - curAngle;
            double ang;
            // The last little bit, just bumps you to exactly what you want.
            if (Math.abs(dif) < TURNING_SPEED) {
                ang = desiredAngle;
            } else {
                // We rotate by as much as we can...in the correct direction.
                if (dif < 0) {
                    if (dif < -Math.PI) {
                        ang = curAngle + TURNING_SPEED;
                    } else {
                        ang = curAngle - TURNING_SPEED;
                    }
                } else {
                    if (dif > Math.PI) {
                        ang = curAngle - TURNING_SPEED;
                    } else {
                        ang = curAngle + TURNING_SPEED;
                    }
                }
            }
            setAngle(ang);
    }
    
    
    //For collisions by section
    double centerDist1;
    double centerDist2;
    double centerDist3;
    double centerDist4;
    double xCollisionCoord1;
    double xCollisionCoord2;
    double xCollisionCoord3;
    double xCollisionCoord4;
    double yCollisionCoord1;
    double yCollisionCoord2;
    double yCollisionCoord3;
    double yCollisionCoord4;
    double quarterHypo;
    double quarterLowerHypo;
    //For collisions by section
    public boolean hasCollided(Thing other, boolean useExact) {
        /* Only things that are alive and not "respawning" can collide */
        if (this.getState() == State.ALIVE && other.getState() == State.ALIVE) {

        	if(other.getClass().getSuperclass()==Thing.class && other.getClass()==Projectile.class){
        		Log.d("Frigate","Testing");
        	}
        	
        	
            //Do a quick radius check. (ie. if 'far away').
            centerDist1 = Math.hypot((xCollisionCoord1 - other.getX()),(yCollisionCoord1 - other.getY()));
	        centerDist2 = Math.hypot((xCollisionCoord2 - other.getX()),(yCollisionCoord2 - other.getY()));
	        centerDist3 = Math.hypot((xCollisionCoord3 - other.getX()),(yCollisionCoord3 - other.getY()));
	        centerDist4 = Math.hypot((xCollisionCoord4 - other.getX()),(yCollisionCoord4 - other.getY()));
	           
	        if (centerDist1 > this.quarterHypo + other.getCollisionH()&&
	            centerDist2 > this.quarterHypo + other.getCollisionH()&&
	            centerDist3 > this.quarterHypo + other.getCollisionH()&&
	            centerDist4 > this.quarterHypo + other.getCollisionH() ){
	            return false;
	        }        
	        if (centerDist1 < (other.getCollisionLH() + quarterLowerHypo)||
	           	centerDist2 < (other.getCollisionLH() + quarterLowerHypo)||
	            centerDist3 < (other.getCollisionLH() + quarterLowerHypo)||
	            centerDist4 < (other.getCollisionLH() + quarterLowerHypo) ){         	
	            return true;
	        }
	        
              
        }
        return false;
    }
    
    
	//private double USEgetCollisionH() {
	//	return super.getCollisionH();
	//}
   // 
	public double getCollisionH() {
		Log.w("Frigate","Test all collisions from with frigate since it has its own hitbox system.");
		return (Double) null;
	}
    
}
