package norland.game.main.things;

import java.util.Random;
import norland.game.main.GlMainMenu;
import norland.game.main.GlRenderer;
import norland.game.main.Thing;


public class Iceberg extends Thing{
	
	private static final int DMG=450;	
	private static final int BoxWidth=44;
	private static final int BoxHeight=44;
	private static final int ICEBERG_HEALTH= 350;
	private static final double evilIcebergMoveSpeed=0.54;
	private static double SIGHT_RADIUS;
	
	public Iceberg(){
		super(GlRenderer.bitmapIceberg, 0, 0, 0, 0,BoxWidth,BoxHeight,ICEBERG_HEALTH,DMG,false,true);
		SIGHT_RADIUS=220*GlMainMenu.widthScale;
		randomizeSpeed();
		beginAttack=false;
	}
	
	public void randomizeSpeed(){
		Random r = new Random();
		setMoveSpeed(0.04+r.nextFloat()*0.18);
		setAngle((r.nextFloat()-0.5)*2*Math.PI);
	}

    public boolean beginAttack;
	
    public void update(){
		if(!GlRenderer.evilIcebergs){
			super.update();
		}else{
			setMoveSpeed(evilIcebergMoveSpeed);
			 if (this.getState() == State.ALIVE) {
				
				 	updateDistTo=distanceTo(GlRenderer.shipLocX, GlRenderer.shipLocY);
		            if(updateDistTo < SIGHT_RADIUS ||beginAttack){
		            	beginAttack=true;
				    	updateLeadScale= 1 -(1/(0.0001*updateDistTo*updateDistTo+1));
						updateDirX = (GlRenderer.viking.getX()+GlRenderer.shipLeadX*updateLeadScale - getX());
						updateDirY = (GlRenderer.viking.getY()+GlRenderer.shipLeadY*updateLeadScale - getY());
						updateTheta = Math.atan2(updateDirY, updateDirX); 
						setX(getX() + Math.cos(updateTheta)*getAdjustedMoveSpeed());
		             	setY(getY() + Math.sin(updateTheta)*getAdjustedMoveSpeed());
		             	setAngle(updateTheta); 
		            }
		            
		         	updateCollisionPolygon();
		            
		            if (this.respawning < 12)
		                this.respawning = this.respawning + 1;// prevents collision
		                                                      // after DYING
		        }else if(this.getState() == State.DYING){
					this.dyingTimer++;
				}else if(this.getState() == State.RESPAWNING){
		        	this.respawning = this.respawning + 1;
		        	if(this.respawning>12){
		        		setState(State.ALIVE);
		        		respawning=0;
		        	}
		        }	
			
			 
		}/*else*/
	}
    
    
}
