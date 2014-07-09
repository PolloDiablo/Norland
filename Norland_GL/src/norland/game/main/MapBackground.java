package norland.game.main;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;


import android.content.Context;
import android.graphics.Bitmap;

public class MapBackground {
	
	private Thing[] tileList = new Thing[4];	//all of them
	private double tileLocations[][] = new double[4][2];	//for now I track this within here.
	
	private List<Integer> activeTiles = new ArrayList<Integer>(4);
	private List<Integer> spareTiles = new ArrayList<Integer>(4);
	
	private double corners[][] = new double[4][2];
	private boolean cornerCovered[];
	
	private int tileWidth;
	private int tileHeight;
	
		
	public MapBackground(Bitmap bitmap, int x, int y, double width, double height) {
		this.tileWidth = (int) (width*GlMainMenu.widthScale);
		this.tileHeight = (int) (height*GlMainMenu.heightScale);
		
		//Set the first location/corners
		updateLocation(x,y);
		
		//Create all the tiles in an initial pattern
		tileList[0] = new Thing(bitmap,getGridCenterX(corners[0][0]),getGridCenterY(corners[0][1]),width,height);
		tileLocations[0][0] = getGridCenterX(corners[0][0]);
		tileLocations[0][1] = getGridCenterY(corners[0][1]);
		
		//These one are setup off screen.
		tileList[1] = new Thing(bitmap,getGridCenterX(corners[1][0]),getGridCenterY(corners[1][1]),width,height);
		tileLocations[1][0] = getGridCenterX(corners[1][0]);
		tileLocations[1][1] = getGridCenterY(corners[1][1]);
		
		tileList[2] = new Thing(bitmap,getGridCenterX(corners[2][0]),getGridCenterY(corners[2][1]),width,height);
		tileLocations[2][0] = getGridCenterX(corners[2][0]);
		tileLocations[2][1] = getGridCenterY(corners[2][1]);

		tileList[3] = new Thing(bitmap,getGridCenterX(corners[3][0]),getGridCenterY(corners[3][1]),width,height);
		tileLocations[3][0] = getGridCenterX(corners[3][0]);
		tileLocations[3][1] = getGridCenterY(corners[3][1]);
		
		//Set one as active, as it is already setup in correct location
		activeTiles.add(0);
		activeTiles.add(1);
		activeTiles.add(2);
		activeTiles.add(3);
		
		//Add the others to spare list
		//spareTiles.add(1);
		//spareTiles.add(2);
		//spareTiles.add(3);
		
	}
	
	private void updateLocation(double x, double y)
	{
		//In theory these are the screen corners (ie. what must be visible).
		corners[0][0] = x-tileWidth/2;
		corners[0][1] = y+tileHeight/2;
		
		corners[1][0] = x+tileWidth/2;
		corners[1][1] = y+tileHeight/2;
		
		corners[2][0] = x+tileWidth/2;
		corners[2][1] = y-tileHeight/2;
		
		corners[3][0] = x-tileWidth/2;
		corners[3][1] = y-tileHeight/2;
	}
	
	public void initShape(GL10 gl, Context context)
	{
		for(Thing v:tileList)
		{
			v.initShape(gl, context);
		}
	}
	
	//TODO: we might need/want to create an interface for draw instead of only having it in certain classes.
	public void draw(GL10 gl, double shipLocX, double shipLocY){
		//Do location update
		updateLocation(shipLocX, shipLocY);

		//This means looking at the location and making sure that all 4 corners are bounded 
		//Reset the active-passive lists
		activeTiles.clear();
		spareTiles.clear();
		
		//Go through list of tiles and place the ones that are still ok.
		cornerCovered = new boolean[]{false,false,false,false};
		
		
		for(int i=0;i<4;i++)
		{
			if(isUsed(i))
			{
				//then put in active list
				activeTiles.add(i);
				
			}else{
				//put in spare list
				spareTiles.add(i);
			}
		}
		
		//Now go through and use the cornerCovered to determine which spaces to fill next, and with what tiles
		while(!isCovered())
		{
			int cur = spareTiles.remove(0);	//This is the one we are going to move
			for(int j=0;j<4;j++)
			{
				if(!cornerCovered[j])
				{
					tileLocations[cur][0] = getGridCenterX(corners[j][0]);
					tileLocations[cur][1] = getGridCenterY(corners[j][1]);
					cornerCovered[j] = true;
					activeTiles.add(cur);
					break;	//now to loop again and see if we are done.
				}
			}
		}
		boolean test=false; //Prints "ERR" if activeTiles is empty
		//Now draw all the active tiles
		
		for(int i=0;i<activeTiles.size();i++)
		{
			int cur = activeTiles.get(i);
			tileList[cur].setX(tileLocations[cur][0]);
			tileList[cur].setY(tileLocations[cur][1]);
			tileList[cur].draw(gl);
			//Log.i("map bg","Drawing Active Tile ("+tileLocations[cur][0]+","+tileLocations[cur][1]+")");
			test=true;
		}
		if(test==false) System.err.println("ERR");
		
	}
	
	private double getGridCenterY(double f) {
		int mults = (int) ((Math.abs(f))/tileHeight);
		double sign = f==0?1:Math.signum(f);
		double retVal = (mults+0.5)*tileHeight*sign;
		//Log.w("GRIDY","in val="+f+", ret="+retVal+"  sign:"+sign);
		return retVal;
	}

	private double getGridCenterX(double f) {
		int mults = (int) ((Math.abs(f))/tileWidth);
		double sign = f==0?1:Math.signum(f);
		double retVal = (mults+0.5)*tileWidth*sign;
		//Log.w("GRIDX","in val="+f+", ret="+retVal+"  sign:"+sign);
		return retVal;
	}

	private boolean isCovered()
	{
		return cornerCovered[0]&&cornerCovered[1]&&cornerCovered[2]&&cornerCovered[3];
	}
	
	private boolean isUsed(int visID)
	{
		//Thing v = tileList[visID];
		double tileX = tileLocations[visID][0];
		double tileY = tileLocations[visID][1];
		double lx = tileX-tileWidth/2;
		double rx = tileX+tileWidth/2;
		double ty = tileY+tileHeight/2;
		double by = tileY-tileHeight/2;
		
		//check all corners
		boolean isUsed = false;
		for(int i=0;i<4;i++)
		{
			double pX = corners[i][0];
			double pY = corners[i][1];
			if(lx<=pX && pX<rx && by<=pY && pY<ty) {
				isUsed = true;
				cornerCovered[i] = true;
			}
		}
		return isUsed;
	}

}
