/*The ship*/

package norland.game.main.things;

import norland.game.main.GlRenderer;
import norland.game.main.Thing;
import norland.game.main.projectiles.Arrow;
import norland.game.main.projectiles.Cannonball;
import norland.game.main.projectiles.CenterWake;
import norland.game.main.projectiles.FireArrow;
import norland.game.main.projectiles.LeftWake;
import norland.game.main.projectiles.Mine;
import norland.game.main.projectiles.Projectile;
import norland.game.main.projectiles.RightWake;
import android.graphics.Bitmap;
import android.util.Log;

public class Viking extends Thing {

    public static int arrowDamage;
    private static double arrowRange =500;
    public static int arrowSeparation;
    public static boolean makeFireArrows;
    private int arrowTimer;
    private static Projectile arrowPrototype;
    
    public static int fireArrowDamage;
    public static double fireArrowRange =500;
    private static Projectile fireArrowPrototype;  

    //private static double PROJ2_SPD = 7;
    private static int cannonDamage;
    private static double cannonRange;
    private static int cannonSeparation;
    private int cannonTimer;
    public static int cannonAmount;
    public static boolean cannonUber;
    private static Projectile cannonPrototype;
    
    private static int mineSeparation=250;
    private int mineTimer;
    private static Projectile minePrototype;

    /**How much you are slowed by while stuck (ranges from 0 to 1).*/
    private static double STUCK_VAL;
    /**Duration that ship is slowed after collision*/
    private static double STUCK_MAX_TIME;
    private static boolean STUCK;
    private static double stuckTime;

    // Damage you do to things you collide with
    public static int DMG;
    public static int VIKING_HLTH;
    public static float HEALTH_REGEN;
    private static double regenMultiplier;
    private static double regenMaxMultiplier;
    private static int buffCounter;
    public static boolean SHOW_ARMOR_ICON;
    public static boolean HEALTH_REGEN_100;
    public static int FIRE_RESIST;
    public static int HULL_RESIST;
    public static boolean SURVIVE_ROCKS;
    public static double TURNING_SPEED;
    public static double MAX_MOVE_SPEED;
    public static boolean UTILITY_UBER;
    private static double[] cannonAngles8;
    private static double[] cannonAngles6;
    private static double[] cannonAngles4;
    public static int BoxWidth = 60;
    public static int BoxHeight = 30;

    public Viking(Bitmap bitmap, int x, int y) {
        super(bitmap, x, y, 0, 0, BoxWidth, BoxHeight, VIKING_HLTH,DMG,false,true);

        arrowPrototype = new Arrow(arrowDamage, arrowRange, true);
        fireArrowDamage =arrowDamage+200;
        fireArrowPrototype = new FireArrow(fireArrowDamage, fireArrowRange, true);;
        arrowTimer = 0;

        //Range and damage are set through the upgrade menu prior to this
        cannonPrototype = new Cannonball(cannonDamage, cannonRange, true);
        
        //This stops the cooldown timers in GlRenderer from displaying at the start of a level
        cannonTimer = cannonSeparation;
        
        minePrototype  = new Mine();
        mineTimer = 0;
     
        cannonAngles8 = new double[] { Math.toRadians(60), Math.toRadians(80), Math.toRadians(100), Math.toRadians(120),
        		Math.toRadians(-60), Math.toRadians(-80), Math.toRadians(-100), Math.toRadians(-120) };
        cannonAngles6 = new double[] { Math.toRadians(70), Math.toRadians(90), Math.toRadians(110), 
        		Math.toRadians(-70), Math.toRadians(-90), Math.toRadians(-110)};
        cannonAngles4 = new double[] { Math.toRadians(77), Math.toRadians(103), Math.toRadians(-77), Math.toRadians(-103) };

        
        leftWakePrototype = new LeftWake();
        centerWakePrototype  = new CenterWake();
        rightWakePrototype  = new RightWake();
        
        STUCK = false;
        stuckTime = 0;
        regenMultiplier=1;
        regenMaxMultiplier=1;
        SHOW_ARMOR_ICON=false;
        buffCounter=0;
    }

    private static double dirX;
    private static double dirY;
    private static double theta;
    
    public void firePROJ1(float locX, float locY) {
    	//Determine the angle to fire the arrow
    	//RECALL that: locX and locY are relative to the screen, so we must compare against center of screen
        dirX = (locX - GlRenderer.WIDTH_HALF);
        dirY = (locY - GlRenderer.HEIGHT_HALF);
        theta = (float) Math.atan2(dirY, dirX);

        if (makeFireArrows) {
        	fireArrowPrototype.setX(this.getX());
        	fireArrowPrototype.setY(this.getY());
        	fireArrowPrototype.setAngle(theta);
        	GlRenderer.addProjectile(Projectile.getProjectile(fireArrowPrototype));
        }else{
        	arrowPrototype.setX(this.getX());
        	arrowPrototype.setY(this.getY());
        	arrowPrototype.setAngle(theta);
        	GlRenderer.addProjectile(Projectile.getProjectile(arrowPrototype));
        }
        arrowTimer = 0;
    }
    
    private void firePROJ3() {
    	minePrototype.setX(this.getX());
    	minePrototype.setY(this.getY());
    	minePrototype.setAngle(getAngle()+Math.PI);
        GlRenderer.addProjectile(Projectile.getProjectile(minePrototype));
    }
    
    private void firePROJ2(double theta) {
    	cannonPrototype.setX(this.getX());
    	cannonPrototype.setY(this.getY());
    	cannonPrototype.setAngle(theta);
    	GlRenderer.addProjectile(Projectile.getProjectile(cannonPrototype));
    }


    public void fireCannons(){
        if (Viking.cannonAmount == 8) {
        	for(int i=0;i<8;i++){
    	        theta = this.getAngle() + cannonAngles8[i];
    	        firePROJ2(theta);
        	}
        } else if (Viking.cannonAmount == 6) {
        	for(int i=0;i<6;i++){
    	        theta = this.getAngle() + cannonAngles6[i];
    	        firePROJ2(theta);
        	}
        } else if (Viking.cannonAmount == 4) {
        	for(int i=0;i<4;i++){
    	        theta = getAngle() + cannonAngles4[i];      
    	        firePROJ2(theta);
        	}
        } else {
            Log.d("Viking", "Cannon Count Error");
        }
        cannonTimer = 0;
    } 
    
    
    private static Projectile leftWakePrototype;
    private static Projectile centerWakePrototype;
    private static Projectile rightWakePrototype;

    private void createLeftWake(){
    	leftWakePrototype.setX(this.getX());
    	leftWakePrototype.setY(this.getY());
    	leftWakePrototype.setAngle(getAngle()-Math.PI/2);
        GlRenderer.addWake(Projectile.getProjectile(leftWakePrototype));
    }
    private void createCenterWake(){
    	centerWakePrototype.setX(this.getX());
    	centerWakePrototype.setY(this.getY());
    	centerWakePrototype.setAngle(getAngle()+Math.PI);
        GlRenderer.addWake(Projectile.getProjectile(centerWakePrototype));
    }
    private void createRightWake(){	
    	rightWakePrototype.setX(this.getX());
    	rightWakePrototype.setY(this.getY());
    	rightWakePrototype.setAngle(getAngle()+Math.PI/2);
        GlRenderer.addWake(Projectile.getProjectile(rightWakePrototype));
    }  

    
    private int wakeCounter;
    

    public void update() {
        if (getState() == State.ALIVE) {


        	wakeCounter++;
        	if(wakeCounter>14/(MAX_MOVE_SPEED) && this.getAngle()!= 0){
        		wakeCounter=0;
        		createLeftWake();
        		createCenterWake();
        		createRightWake();
        	}
        	
        	if(UTILITY_UBER && mineTimer>mineSeparation && this.getAngle()!= 0 && GlRenderer.weaponsOn && !STUCK){
        		mineTimer=0;
        		firePROJ3();
        	}
        	
            if (getHealth() > VIKING_HLTH+0.4) {
                //Log.d("Viking", "The Viking's max health was lowered.");
                setHealth(VIKING_HLTH);
            }
            if(buffCounter!=0){
            	buffCounter--;
            	if(buffCounter==0){
                	SHOW_ARMOR_ICON=false;
                	Viking.regenMultiplier=1;
                	Viking.regenMaxMultiplier=1;
            	}
            }
            
            
            
            if (STUCK) {
                if (stuckTime >= STUCK_MAX_TIME) {
                    STUCK = false;
                    stuckTime = 0;
                } else {
                    stuckTime++;
                }
                // No Health regen while stuck
                setX(getX() + Math.cos(getAngle())*getAdjustedMoveSpeed()*STUCK_VAL);
                setY(getY() + Math.sin(getAngle())*getAdjustedMoveSpeed()*STUCK_VAL);

            } else {
                // Only regen to full health if you have that upgrade
                if (HEALTH_REGEN_100) {
                    if (getHealth() < (VIKING_HLTH)) {
                        setHealth(getHealth() + HEALTH_REGEN*regenMultiplier);
                    }
                } else {
                    if (getHealth() < (VIKING_HLTH * 0.5*regenMaxMultiplier)) {
                        setHealth(getHealth() + HEALTH_REGEN*regenMultiplier);
                    }
                }
                setX(getX() + Math.cos(getAngle())*getAdjustedMoveSpeed());
                setY(getY() + Math.sin(getAngle())*getAdjustedMoveSpeed());
            }
            
          	a1 = this.getAngle()+ (Math.atan2(this.adjustedBoxHeight / 2.0, this.adjustedBoxWidth / 2.0));
        	a2 = this.getAngle()+ (Math.atan2(this.adjustedBoxHeight / 2.0, -this.adjustedBoxWidth / 2.0));
        	a3 = this.getAngle()+ (Math.atan2(-this.adjustedBoxHeight / 2.0, -this.adjustedBoxWidth / 2.0));
        	a4 = this.getAngle()+ (Math.atan2(-this.adjustedBoxHeight / 2.0, this.adjustedBoxWidth / 2.0));
            
        	getCollisionXs()[0]=Math.cos(a1) * this.getCollisionH() + this.getX();
        	getCollisionXs()[1]=Math.cos(a2) * this.getCollisionH() + this.getX();
        	getCollisionXs()[2]=Math.cos(a3) * this.getCollisionH() + this.getX();
        	getCollisionXs()[3]=Math.cos(a4) * this.getCollisionH() + this.getX();
        	
        	getCollisionYs()[0]=Math.sin(a1) * this.getCollisionH() + this.getY();
        	getCollisionYs()[1]=Math.sin(a2) * this.getCollisionH() + this.getY();
        	getCollisionYs()[2]=Math.sin(a3) * this.getCollisionH() + this.getY();
        	getCollisionYs()[3]=Math.sin(a4) * this.getCollisionH() + this.getY();
            
        	getCollisionPoly().setPolyArrays(getCollisionXs(), getCollisionYs());
        	
            arrowTimer++;
            cannonTimer++;
            mineTimer++;

        }else if(this.getState() == State.DYING){
			this.dyingTimer++;
		}else if(this.getState() == State.RESPAWNING){
        	this.respawning = this.respawning + 1;
        	if(this.respawning>12){
        		this.setState(State.ALIVE);
        		respawning=0;
        	}
        }
        
    }

    //Used for upgrades
    public void setArrowDamage(int dmg) {
       arrowDamage=dmg;
    }
    public static void setProj2Damage(int dmg) {
    	cannonDamage=dmg;
    }
    public static void setProj2Range(double rng) {
        cannonRange=rng;
    }
    public static void setProj2Separation(int sep) {
        cannonSeparation=sep;
    }


    //Checked by GlRenderer onTouchEvent
    public int getArrowSeparation() {
        return arrowSeparation;
    }
    public int getArrowTimer() {
        return arrowTimer;
    }
    public int getProj2Separation() {
        return cannonSeparation;
    }
    public int getProj2Timer() {
        return cannonTimer;
    }
    




    public boolean takeDamage(double incomingDamage, boolean isFireDamage, boolean isHullDamage) {
        if (isFireDamage) {
            incomingDamage = (double) (incomingDamage * (100 - FIRE_RESIST) * 0.01);
            //Log.d("Viking", "Calc DMG= " + incomingDamage + " FIRE_RESIST= " + FIRE_RESIST);
        }
        if (isHullDamage) {
            incomingDamage = (double) (incomingDamage * (100 - HULL_RESIST) * 0.01);
            //Log.d("Viking", "Calc DMG= " + incomingDamage + " HULL_RESIST= " + HULL_RESIST);
        }
        // Log.d("Viking", "Damage taken adjusted by resist:  " +
        // incomingDamage);
        this.setHealth(this.getHealth() - incomingDamage*(1-defenseBuff));
        if (this.getHealth() <= 0) {
            this.setState(State.DYING);
            return true;
        } else
            return false;

    }

    /**Slow amount between (0,1), and directly multiplies the viking move speed
     * ie. 0 would stop you moving */
    public void makeStuck(double slowAmount, double duration) {
        STUCK = true;
        STUCK_VAL = slowAmount;
        STUCK_MAX_TIME = duration;
        stuckTime = 0;
    }
    
    /** 
     * @param regenBuff - Multiplies health regen speed, should be > 1 for positive effect
     * @param maxRegenBuff - Multiplies health regen max, should be within (1,2)
     * @param defenseBuff - Reduces incoming damage by *(1-defenseBuff)
     * @param buffLength - How long the effect lasts after leaving the aura
     */ 
    public void startAura(double regenBuff,double maxRegenBuff, double defenseBuff, int buffLength){
    	SHOW_ARMOR_ICON=true;
    	buffCounter=buffLength;
    	Viking.regenMultiplier=regenBuff;
    	regenMaxMultiplier=maxRegenBuff;
    	this.setDefenseBuff(defenseBuff);
    }

}
