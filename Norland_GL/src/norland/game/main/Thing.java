package norland.game.main;

import javax.microedition.khronos.opengles.GL10;
import android.content.Context;
import android.graphics.Bitmap;

/**Thing, it is the most basic object. All enemies, projectiles,buttons etc. extend thing*/
public class Thing {

    /**The current x coord (center).*/
    private double x;
    public double getX(){return x;}
    public void setX(double x){this.x = x;}
    
    /**The current y coord (center)*/
    private double y;
    public double getY(){return y;}
    public void setY(double y){this.y = y;}
    
    /**The base move speed of the Thing*/
    private double baseMoveSpeed;
    /**Returns moveSpeed (the base amount, not adjusted)*/
    public double getBaseMoveSpeed(){
    	return baseMoveSpeed;
    }  
    /**The adjusted move speed of the Thing*/
    private double adjustedMoveSpeed;
    public double getAdjustedMoveSpeed(){
    	return adjustedMoveSpeed;
    }  
    /**Sets move speed and adjusts it to the screen size*/
    protected void setMoveSpeed(double speed){
    	baseMoveSpeed=speed;
    	adjustedMoveSpeed=speed*GlMainMenu.widthScale;
    }
    
    /**The angle of movement in radians.*/
    private double angle;
    /**Returns angle of movement in radians.*/
    public double getAngle() {return angle;}

    /**Set angle of movement in radians.*/
    public void setAngle(double newAngle) {angle = newAngle;}
    
    /**The square representing this thing.*/
    private Visual mySquare;
    
    /**The box width, adjusted to screen size.*/
    protected int adjustedBoxWidth;
    public int getCollisionBoxWidth() 	{return adjustedBoxWidth;}
    
    /**The box height, adjusted to screen size.*/
    protected int adjustedBoxHeight;
    public int getCollisionBoxHeight() 	{return adjustedBoxHeight;}

    private double collisionH;
	public double getCollisionH(){return collisionH;}
    private double collisionLH;
	public double getCollisionLH(){return collisionLH;}
    private double collisionXs[];
	protected double[] getCollisionXs(){return collisionXs;}
    private double collisionYs[];
	protected double[] getCollisionYs(){return collisionYs;}
	private MyPoly collisionPoly;
	protected MyPoly getCollisionPoly(){return collisionPoly;}
    
    public enum State {
        RESPAWNING, ALIVE, DYING, DEAD
    }
    private State state;
    public State getState() 			{return state;}
    public void setState(State state)	{this.state = state;}
    /**Keeps track of how long this object has been dying*/
    protected int dyingTimer; 
    /**Used to add a delay between DYING/ALIVE, to fix a post-death-collision bug*/
    protected int respawning;
     
    private boolean doesFireDamage;
	public boolean getDoesFireDamage(){return doesFireDamage;}
    private boolean doesHullDamage;
	public boolean getDoesHullDamage(){return doesHullDamage;}

    private double maximumHealth;
    public double getMaximumHealth(){return maximumHealth;}
    private double currentHealth;
    public double getHealth(){return currentHealth;}
   
    private double damage;
    /**Returns the amount of damage the Thing does in a collision*/
    public double getDamage() {return damage;}
    /**Sets the amount of damage the Thing does in a collision*/
    protected void setDamage(double damage) {this.damage = damage;}

  


    protected Thing(Bitmap bitmap, double x, double y, double speed, double angle, double width, double height, double hlth, double damage,
    		boolean doesFireDamage, boolean doesHullDamage){
    	this(bitmap,x,y,width,height);
    	this.baseMoveSpeed=speed;
        this.adjustedMoveSpeed = speed*GlMainMenu.widthScale;
        this.angle = angle;
    	this.maximumHealth = hlth;
    	this.currentHealth=maximumHealth;
    	this.damage=damage;  
    	this.doesFireDamage=doesFireDamage;
        this.doesHullDamage=doesHullDamage; 
    }
    
    /**Used for Things that do not ever use health, damage, etc. in collisions
     * This includes visual elements like buttons*/
    public Thing(Bitmap bitmap, double x, double y, double width, double height){
    	this.maximumHealth = 1;
    	this.currentHealth=maximumHealth;
    	this.damage=0;
    	defenseBuff=0;
        adjustedBoxWidth = (int) (width*GlMainMenu.widthScale);
        adjustedBoxHeight = (int) (height*GlMainMenu.heightScale);
    	mySquare=new Visual(bitmap,width,height);
        this.collisionH= Math.hypot(this.adjustedBoxWidth, this.adjustedBoxHeight)/2.0;
        this.collisionLH= Math.min((this.adjustedBoxHeight / 2.0), (this.adjustedBoxWidth / 2.0));
        this.collisionXs = new double[4];
        this.collisionYs = new double[4];
        this.collisionPoly = new MyPoly();
    	alpha= (float) 1.0;
    	//By default, speed is zero, angle is zero
    	reset(x,y,0,0);   
    }
    
    //The bitmap and collision box of a reset Thing (Projectile) should not change.
    protected void reset(double x, double y, double speed, double angle){
        this.x = x;
        this.y = y;
        alpha= (float) 1.0;
        currentHealth = maximumHealth;
        this.state = State.ALIVE;
        this.respawning = 0;
        this.baseMoveSpeed=speed;
        this.adjustedMoveSpeed = speed*GlMainMenu.widthScale;
        this.angle = angle;
    }

    /**
     * Initialize the shape.
     * 
     * @param gl
     * @param context
     */
    public void initShape(GL10 gl, Context context) {
        mySquare.initShape(gl, context);
    }
    //private void reInitShape(GL10 gl, Context context) {
    //    mySquare.reInit(gl, context);
    //}
    
	private float alpha;
	
	public void alphaReduce(GL10 gl) {
    	if(alpha>0){
    		alpha = (float) (alpha-0.005);
    	}
	}
    
    public void draw(GL10 gl) {

        /* If object is alive, draw it */
        if (this.state == State.ALIVE || this.state == State.RESPAWNING) {
        	
        	gl.glEnable(GL10.GL_BLEND);
        	gl.glColor4f(alpha,alpha,alpha,alpha);  	
            mySquare.draw(gl, x, y, angle);
        }
        /* If object is dying, draw the death animation */
        if (this.state == State.DYING) {
            deathSequence(gl);
        }
    }

    /**TODOmake a death animation or something, probably should be an abstract method.*/
    protected void deathSequence(GL10 gl) {
        if (this.dyingTimer >= 40) { // ### is "time" spent dying, based on number of update()s
            this.state = State.DEAD;
            this.dyingTimer = 0;
            this.respawning = 0;
        }
    }

    protected static double a1;
    protected static double a2;
    protected static double a3;
    protected static double a4;
    
    protected static double updateDirX;
    protected static double updateDirY;
    protected static double updateDistTo;
    protected static double updateLeadScale;
    protected static double updateTheta;
    
    public void update() {
        if (this.state == State.ALIVE) {
        	
		    x += Math.cos(angle)*adjustedMoveSpeed;
         	y += Math.sin(angle)*adjustedMoveSpeed;        
            
          	a1 = this.angle+ (Math.atan2(this.adjustedBoxHeight / 2.0, this.adjustedBoxWidth / 2.0));
        	a2 = this.angle+ (Math.atan2(this.adjustedBoxHeight / 2.0, -this.adjustedBoxWidth / 2.0));
        	a3 = this.angle+ (Math.atan2(-this.adjustedBoxHeight / 2.0, -this.adjustedBoxWidth / 2.0));
        	a4 = this.angle+ (Math.atan2(-this.adjustedBoxHeight / 2.0, this.adjustedBoxWidth / 2.0));
            
        	getCollisionXs()[0]=Math.cos(a1) * this.getCollisionH() + this.x;
        	getCollisionXs()[1]=Math.cos(a2) * this.getCollisionH() + this.x;
        	getCollisionXs()[2]=Math.cos(a3) * this.getCollisionH() + this.x;
        	getCollisionXs()[3]=Math.cos(a4) * this.getCollisionH() + this.x;
        	
        	getCollisionYs()[0]=Math.sin(a1) * this.getCollisionH() + this.y;
        	getCollisionYs()[1]=Math.sin(a2) * this.getCollisionH() + this.y;
        	getCollisionYs()[2]=Math.sin(a3) * this.getCollisionH() + this.y;
        	getCollisionYs()[3]=Math.sin(a4) * this.getCollisionH() + this.y;
            
        	getCollisionPoly().setPolyArrays(getCollisionXs(), getCollisionYs());

            if (this.respawning < 12)
                this.respawning = this.respawning + 1;// prevents collision
                                                      // after DYING
        }else if(this.state == State.DYING){
			this.dyingTimer++;
		}else if(this.state == State.RESPAWNING){
        	this.respawning = this.respawning + 1;
        	if(this.respawning>12){
        		setState(State.ALIVE);
        		respawning=0;
        	}
        }
        
    }

    
    static double centerDist;
    public boolean hasCollided(Thing other, boolean useExact) {
        /* Only things that are alive and not "respawning" can collide */
        if (this.state == State.ALIVE && other.state == State.ALIVE) {

            // First do a quick radius check. (ie. if 'far away').
            centerDist = Math.hypot(
                    (this.x - other.getX()),
                    (this.y - other.getY()));
            if (centerDist > this.getCollisionH() + other.getCollisionH()) {
                return false;
            }
            // definitely collided check
            if (centerDist < (other.getCollisionLH() + this.getCollisionLH())) {
                return true;
            }

            if(useExact){
            	// Now, we have the 2 polygons, and we can start to check if they
            	// overlap.
            	// We do this by seeing if either contains the other corners.
            	// TODO: Jeremy, can you think of a situation where this doesn't
            	// hold?
            	for (int i = 0; i < 4; i++) {
            		if (this.getCollisionPoly().containsPoint(other.getCollisionXs()[i], other.getCollisionYs()[i])) {
            			return true;
            		}
            		if (other.getCollisionPoly().containsPoint(this.getCollisionXs()[i], this.getCollisionYs()[i])) {
            			return true;
            		}
            	}
            	return false;
            }//If useExact   
        }
        return false;
    }

    static double angTotal;
    static double dot;
    static double h1;
    static double h2;
    static double ang;
    public class MyPoly {
        double xs[];
        double ys[];
        double vxs[];
        double vys[];

        public MyPoly() {
        	 vxs = new double[4];
        	 vys = new double[4];
        }
        public void setPolyArrays(double xs[], double ys[]){
        	 this.xs = xs;
             this.ys = ys;
        }
        /**
         * This returns true if the point is inside the polygon.
         * 
         * @param x
         * @param y
         * @return
         */
        public boolean containsPoint(double x, double y) {
            // So...There are a few ways to do this...one is to use convex hull
            // method.
            // (basically you use the cross product to determine that the the
            // point is on the same side of all the lines...as far as I know.
            // I have done this before...but am too lazy to figure it out right
            // now.

            // Another way is to split it into 2 triangles, and do the above
            // method.
            // Actually, this way is cool because the formula is simpler I
            // think...I swear I derived one for this at some point...
            // Ugh...googling is just telling me to cross product...sigh.
            // Fun side-note (I actually figured out the whole cross product
            // thing in physics class one day on my own...but later learned
            // about it actually being used to do these kinds of things. :)

            // Anyways...am going to attempt to use the dot product to figure
            // this bad-boy out ;)
            // The idea is that if you draw vectors from this point, to all the
            // corners of the polygon, then figure out the angles between
            // them...that none should be above 180 degrees.
            // If one is, then the point is outside the square.
            // aside: I just came up with this...but it too is probably on the
            // internet somewhere ;)
            // but where is the fun in that :P
            // take back...this doesn't quite work...because of acos not knowing
            // which side is which, and therefore never returning > 180.
            // HOWEVER, what does work is accumulating the total and making sure
            // it equals 360. If it is less than this, it is outside.

            vxs[0]=xs[0] - x;
            vxs[1]=xs[1] - x;
            vxs[2]=xs[2] - x;
            vxs[3]=xs[3] - x;
            vys[0]=ys[0] - y;
            vys[1]=ys[1] - y;
            vys[2]=ys[2] - y;
            vys[3]=ys[3] - y;

            angTotal = 0;
            for (int i = 0; i < 4; i++) {
                int j = (i + 1) % 4; // vector 2
                dot = vxs[i] * vxs[j] + vys[i] * vys[j];
                h1 = Math.hypot(vxs[i], vys[i]);
                h2 = Math.hypot(vxs[j], vys[j]);
                ang = Math.acos(dot / (h1 * h2));
                // if (ang > Math.PI) {
                // return false;
                // }
                angTotal += ang;
            }
            return angTotal >= (Math.PI * 2 - 1e-6); // IF almost exactly 360,
                                                     // then ok. :)
        }
    }

    /**A one-time buff which is applied to the next incoming damage.
     * Number between (0,1), where 1 would block all incoming damage*/
    protected double defenseBuff;   
    
    /**Apply defenseBuff*/
	public void setDefenseBuff(double defenseBuff) {
		this.defenseBuff = defenseBuff;
	}
	
    /** Subtracts incoming damage from health, changes to dying state if health<0
     * 
     * @param incomingDamage
     * @return
     */
	public boolean takeDamage(double incomingDamage) {
		//Only use the defenseBuff for bad incomingDamage
		if(incomingDamage>=0){
			currentHealth = currentHealth - incomingDamage*(1-defenseBuff);
	        defenseBuff=0;
		}else{
			//Healing
			currentHealth = currentHealth - incomingDamage;
			if(currentHealth>2*maximumHealth){ //Max bonus health is double
				currentHealth=2*maximumHealth;
			}
		}

        if (currentHealth <= 0) {
            this.state = State.DYING;
            return true;
        } else
            return false;

    }

    /**Returns the distance between center of this and a point*/
    public  double distanceTo(double otherX, double otherY) {
        return Math.sqrt(Math.pow(otherX - this.x, 2) + Math.pow(otherY - this.y, 2));
    }

    /**Used to restore to original health after a revive, also for health regen*/
    protected void setHealth(double health) {
        this.currentHealth = health;
    }

    public void revive() {
        setState(State.RESPAWNING);
        setHealth(maximumHealth);
    }   
    
}
