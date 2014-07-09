package norland.game.main.levels;

import java.util.ArrayList;
import javax.microedition.khronos.opengles.GL10;

import norland.game.main.GlRenderer;
import norland.game.main.Thing;
import norland.game.main.Thing.State;
import norland.game.main.projectiles.Cannonball;
import norland.game.main.projectiles.GreenNote;
import norland.game.main.projectiles.RedNote;
import norland.game.main.projectiles.Bolt;
import norland.game.main.projectiles.Projectile;
import norland.game.main.projectiles.Arrow;
import norland.game.main.projectiles.FireArrow;
import norland.game.main.things.Dragon;
import norland.game.main.things.Grindylow;
import norland.game.main.things.Iceberg;
import norland.game.main.things.Lorelei;
import norland.game.main.things.Rock;
import norland.game.main.things.Viking;
import norland.game.main.things.WaterSprite;
import android.content.Context;
import android.util.Log;

public abstract class LevelSuper implements Level  {
	
	protected int ROCKCOUNT;
	protected int ICEBERGCOUNT;
	
	protected static Thing rocks[];
	protected static Iceberg icebergs[];
	
	protected ArrayList<Thing> enemies;
	public ArrayList<Thing> getEnemies(){
		return enemies;
	}
	protected ArrayList<Thing> friendlies;
	
    protected Thing objective;
	
    /**Creates new ArrayLists for Iceberg, Rock, and Thing.
     * Each level should:
     *  - Add items to any of the above arraylists
     *  - Initialize the objective
     *  - Initialize GlRenerer.OUTSIDE_SIZE
     */
    
    protected static int onlyTestCollisionsEvery4Cycles;
    
	public void addStuff() {
		
		rocks = new Thing[ROCKCOUNT];
		for(int i=0;i<ROCKCOUNT;i++){
			rocks[i] = new Rock();
			GlRenderer.randomizeLocation(rocks[i]);
			rocks[i].update();
		}
        for (Thing r : rocks) {
        	for (Thing k : rocks) {
        		if (r != k) {
        			if (r.hasCollided(k,true)) {
        				k.takeDamage(r.getDamage());
        			}
        		}
        	}
        	 if (r.hasCollided(objective,true)) {
                 GlRenderer.randomizeLocation(r);
             }
        }
        
		icebergs = new Iceberg[ICEBERGCOUNT];
		for(int i=0;i<ICEBERGCOUNT;i++){
			icebergs[i] = new Iceberg();
			GlRenderer.randomizeLocation(icebergs[i]);
		}
			
		enemies=new ArrayList<Thing>();
		friendlies=new ArrayList<Thing>();
		
		onlyTestCollisionsEvery4Cycles=0;
	}
	
	
	/** What update does:
	 * - Calls handlfeTips()
	 * - Projectiles: repooling, all collisions
	 * - Icebergs: 	update, remove distant ones, viking collision, check for dead ones, objective collision
	 * - Rocks: 	update, remove distant ones, viking collision, check for dead ones, objective collision, iceberg collision
	 * - Enemies:   updage, remove distant ones, viking collision, check for dead ones, objective collision, randomize location after dath
	 * - Objective:	update, viking collision
	 * - Aims compass pointer towards objective
	 * - Checks for viking, enemy collision
	 * 
	 * What it does NOT do:
	 * - Projectile updating which is handled by GlRenderer.
	 * 
	 * 
	 */
	
	double boxLeft;
	double boxRight;
	double boxTop;
	double boxBottom;
	private static double dirX;
	private static double dirY;
	private boolean didKill;
	public void update() {
		handleTips();
		
		for(Iceberg i:icebergs){
			i.update();
		}
		for(Thing r: rocks){
			r.update();	
		}
		for(Thing e:enemies){	
			e.update();
		}
		for(Thing f:friendlies){
			f.update();
		}
        objective.update();
        
        boxLeft = GlRenderer.shipLocX-GlRenderer.WIDTH_HALF-GlRenderer.OUTSIDE_SIZE;
		boxRight = GlRenderer.shipLocX+GlRenderer.WIDTH_HALF+GlRenderer.OUTSIDE_SIZE;
		boxTop = GlRenderer.shipLocY-GlRenderer.HEIGHT_HALF-GlRenderer.OUTSIDE_SIZE;
		boxBottom =GlRenderer.shipLocY+GlRenderer.HEIGHT_HALF+GlRenderer.OUTSIDE_SIZE;
        
        
        
		if(onlyTestCollisionsEvery4Cycles%4==0){
		
			//This is important!
			for(int i=0; i<GlRenderer.projectiles.size();i++){
				if(GlRenderer.projectiles.get(i).getState()==State.DEAD){
					//Log.d("Projectiles","repooling");
					GlRenderer.projectiles.remove(i).repool();
					i--;
				}		
			}	
			for(int i=0; i<GlRenderer.wakes.size();i++){
				if(GlRenderer.wakes.get(i).getState()==State.DEAD){
					GlRenderer.wakes.remove(i).repool();
					//Log.d("Wakes","repooling");
					i--;
				}		
			}
	
			Projectile newFireArrow = null;
			//Checks projectile collisions
			for(Projectile p:GlRenderer.projectiles){	
				//Checks for cannon-cannon collisions and arrow-flame collisions
	
					for(Projectile w:GlRenderer.projectiles){
						if( p!=w && p instanceof Cannonball && !p.isFriendly() && w.isFriendly() && w instanceof Cannonball && p.hasCollided(w,false)){
							p.setState(State.DYING);
							w.setState(State.DYING);
						}
						if(p instanceof Arrow && w.getDoesFireDamage() && p.hasCollided(w,false)){
							newFireArrow = p;
							p.setState(State.DEAD);
							w.setState(State.DEAD);
						}
					}
					
			}
			if(newFireArrow != null){
				GlRenderer.copyProjectileToFireArrow(newFireArrow);
			}
			
			//Checks projectile collisions
			for(Projectile p:GlRenderer.projectiles){
				
				if(p.hasCollided(objective,false)){
					p.takeDamage(1);
				}		
				for(Iceberg i:icebergs){	
					if( !(p instanceof GreenNote) && !(p instanceof RedNote) && !(p instanceof Bolt) && p.hasCollided(i,false)){
						didKill = i.takeDamage(p.getDamage());
						if(p.isFriendly() && didKill){
							GlRenderer.bergKillCount++;	
							if(GlRenderer.getLevel().getClass()==Level02.class){
								if(!Level02.hasShownSecondObjectiveTip){
									if(p instanceof Arrow || p instanceof FireArrow){
										Level02.objectiveKillCount++;
									}
								}else{
									if(p instanceof Cannonball){
										Level02.objectiveKillCount++;
									}
								}

							}

						}
						p.takeDamage(i.getDamage());
					}
				}	
				for(Thing r: rocks){
					if(!(p instanceof GreenNote) && !(p instanceof RedNote) && p.hasCollided(r,false)){
						p.takeDamage(r.getDamage());
					}	
				}
				for(Thing e:enemies){
					//Check projectile collisions with enemies
					if(p.isFriendly() && p.hasCollided(e,false)){
						if(p instanceof Arrow && e.getClass() == Grindylow.class){
							//Arrows do not harm grindylows
						}else{
							if(p instanceof Bolt && e.getClass()==Lorelei.class){
								//Ignore collision
							}else if(p instanceof Bolt){
								e.setDefenseBuff(Lorelei.getboltDefenseBuff());
								p.takeDamage(e.getDamage());
								e.takeDamage(p.getDamage());
							}else{
								p.takeDamage(e.getDamage());
								didKill = e.takeDamage(p.getDamage());
								if(p.isFriendly() && didKill && GlRenderer.getLevel().getClass()==level09.class){
									level09.objectiveKillCount++;
								}
							}
						}
					}
				}
				for(Thing f:friendlies){
					if(!p.isFriendly() && p.hasCollided(f, false)){
						p.takeDamage(f.getDamage());
						f.takeDamage(p.getDamage());
					}
				}
				//Check projectile collisions with you
				if(!p.isFriendly() && p.hasCollided(GlRenderer.viking,false)){	
					GlRenderer.viking.takeDamage(p.getDamage(),p.getDoesFireDamage(),p.getDoesHullDamage());
					p.takeDamage(GlRenderer.viking.getDamage());
				}
				///////////////			
			}
			
			for(Iceberg i:icebergs){			
				//Remove icebergs which are too far away
				if(i.getX()<boxLeft || i.getX()>boxRight || i.getY()<boxTop||i.getY()>boxBottom){
					i.setState(State.DEAD);
				}
				//Check for viking/iceberg collision
				if( i.hasCollided(GlRenderer.viking,false)){
					GlRenderer.viking.takeDamage(i.getDamage(),i.getDoesFireDamage(),i.getDoesHullDamage());
					i.takeDamage(GlRenderer.viking.getDamage());
				}
				//Check for iceberg collisions, kill one of them if there is a collision
				for(Iceberg k:icebergs){
					if(i!=k){
						if(i.hasCollided(k,false)){
							i.takeDamage(k.getDamage());
						}
					}
				}
				//Revive icebergs
				if(i.getState()==State.DEAD){
					i.beginAttack=false;
					GlRenderer.randomizeLocation(i);
					i.randomizeSpeed();
					i.revive();
				}
	            //Ensure new icebergs do not overlap objective
	            if (i.hasCollided(objective,true)) {
	            	GlRenderer.randomizeLocation(i);
	            }
				
			}/*For Icebergs*/
		
			for(Thing r: rocks){
			
				if(r.getX()<boxLeft || r.getX()>boxRight || r.getY()<boxTop||r.getY()>boxBottom){
					r.setState(State.DEAD);		
				}		
				// Check for viking/rock collision
				if (r.hasCollided(GlRenderer.viking,false)) {
					if (Viking.SURVIVE_ROCKS) {
						r.setState(State.DYING);
						GlRenderer.viking.makeStuck(Rock.STUCK_AMOUNT, Rock.STUCK_DURATION);
					}else{
						GlRenderer.viking.takeDamage(r.getDamage(),r.getDoesFireDamage(),r.getDoesHullDamage());
					}
				}    
				//Check for dead rocks
				if (r.getState() == State.DEAD) {
					GlRenderer.randomizeLocation(r);
					r.revive();
					r.update();
                	for (Thing k : rocks) {
                		if (r != k) {
                			if (r.hasCollided(k,true)) {
                				k.takeDamage(r.getDamage());
                			}
                		}
                	} 
				} 
            	//Ensure new rocks do not overlap objective
            	if (r.hasCollided(objective,true)) {
            		GlRenderer.randomizeLocation(r);
            		r.update();
            	} 
            	
				//Check for rock/iceberg collisions
				for(Iceberg k:icebergs){
					if(r!=k){
						if(r.hasCollided(k,false)){
							k.takeDamage(r.getDamage());
						}
					}
				}           
			}/*For Rocks*/
		
			//Checks for dead enemies and for enemy collision
			for(Thing e:enemies){	
				if(e.getX()<boxLeft || e.getX()>boxRight || e.getY()<boxTop||e.getY()>boxBottom||e.hasCollided(objective,false)){
						e.setState(State.DEAD);		
				}
				if(e.getState()==State.DEAD){
					GlRenderer.randomizeLocation(e);
					e.revive();
				}
				
				if(e.getClass() != Dragon.class && e.hasCollided(GlRenderer.viking, false)){
					if(e.getClass()==Grindylow.class){
						GlRenderer.viking.takeDamage(e.getDamage(),e.getDoesFireDamage(),e.getDoesHullDamage());
						GlRenderer.viking.makeStuck(Grindylow.getSlowAmount(), Grindylow.getSlowDuration());
					}else{
						GlRenderer.viking.takeDamage(e.getDamage(),e.getDoesFireDamage(),e.getDoesHullDamage());
						e.takeDamage(GlRenderer.viking.getDamage());
					}
				}
			}/*For Enemies*/
			
			for(Thing f:friendlies){
				if(f.getX()<boxLeft || f.getX()>boxRight || f.getY()<boxTop||f.getY()>boxBottom||f.hasCollided(objective,false)){
						f.setState(State.DEAD);		
				}
				if(f.getState()==State.DEAD){
					GlRenderer.randomizeLocation(f);
					f.revive();
				}
			}/*For Friendlies*/
	           
	        if (GlRenderer.viking.hasCollided(objective,true)) {
	            GlRenderer.levelFinished = true;
	        }
			for(Thing k:friendlies){
				if(k.getClass()==WaterSprite.class && GlRenderer.viking.distanceTo(k.getX(), k.getY())<WaterSprite.getAURA_RADIUS()){
					GlRenderer.viking.startAura(2, 2, 0.2, 5);
				}
			}
		}/*Only check collisions every 4 cycles*/
		onlyTestCollisionsEvery4Cycles++;
		
		
		dirX = (objective.getX()-GlRenderer.shipLocX);
        dirY = (objective.getY()-GlRenderer.shipLocY);
        GlRenderer.compassPointer.setAngle(Math.atan2(dirY, dirX));
        
	}
	
	
	/**Must be implemented uniquely in each level*/
	public void handleTips() {}

	/**Draws icebergs, rocks, enemies, and objective*/
	public void onDrawFrame(GL10 gl, Context context) {
			objective.draw(gl);
			//Draw icebergs
			for(Iceberg i:icebergs){
				i.draw(gl);
			}		
			//draw rocks
			for(Thing r:rocks){
				r.draw(gl);
			}		
			//draw enemies
			for(Thing e:enemies){
				e.draw(gl);
			}
			for(Thing f:friendlies){
				f.draw(gl);
			}
	}

	/**Initiates icebergs, rocks, enemies, and objective
	 * - Projectiles are done in GlRenderer 
	 */
	public void initiateShapes(GL10 gl, Context context) {
		try{
			for(Iceberg i:icebergs){
				i.initShape(gl,context);
			}
			for(Thing r:rocks){
				r.initShape(gl,context);
			}	
			for(Thing e:enemies){
				e.initShape(gl,context);
			}
			for(Thing f:friendlies){
				f.initShape(gl,context);
			}
			objective.initShape(gl, context);
		}catch(Exception e){
			Log.e("LevelSuper","Null Pointer during inititiateShapes(), likely because super.addStuff() must be called");
		}
	}

	/**What happens when you finish the level, includes:
	 * Unlocking the next level		
	 * Automatically starting the next level (if it was just unlocked)
	 * Giving out upgrade points (if this is the first time you beat the level, and the level has a reward)
	 * Launching the upgrade screen if you just got more points
	 * Going back to the main menu if you are replaying the level
	 */
	public abstract void finishLevel(Context context);

	/**Loads the correct music into the media player*/
	public abstract void startMusic(Context context);

}
