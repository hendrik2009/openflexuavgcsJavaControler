import SimpleOpenNI.*;
import processing.core.*;

import helper.ColorDetector;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;



public class KinectData extends PApplet {
	private static final long serialVersionUID = 1L;

	
	SimpleOpenNI context;
	double        zoomF =0.3f;
	float        rotX = radians(180);  // by default rotate the hole scene 180deg around the x-axis, 
	                                   // the data from openni comes upside down
	float        rotY = radians(0);
	
	BufferedImage image;
	//------
	PVector[] basicWorldMap;
	PVector[] lowPathMap;
	boolean init    = true;
	float   dnorm    = 			1000;
	float   dmin     =			999999;
	float   dmax     =			-999999 ; 
	float accurency  =          600;
	int   count      =          0;
	int   countMax   =          100;
	boolean helper    =true;
	boolean drawBG 	  =false;
	//-----
	
	ColorDetector detective	;
	int[] memRect ={0,0,0,0}; 

	public void setup()
	{
		frameRate(60);
		 
		size(512,384,P3D);
	   //context = new SimpleOpenNI(this,SimpleOpenNI.RUN_MODE_SINGLE_THREADED);
		context = new SimpleOpenNI(this);

	  // disable mirror
		context.setMirror(true);
		context.enableRGB();
	  // enable depthMap generation 
		context.enableDepth();

		stroke(255,255,255);
	  	smooth();
	  	perspective(radians(45), width/height,10,150000);
	  	detective = new ColorDetector(640,480); 
	}

	public void draw()
	{
	  // update the cam
	  context.update();
	  
	  background(0,0,0);
	  translate(width/2, height/2, 0);
	  rotateX(rotX);
	  rotateY(rotY);
	  scale((float)zoomF);
	  translate(0,0,-1000);  // set the rotation center of the scene 1000 infront of the camera
	  stroke(255);

	  context.drawCamFrustum();
	  
	  // draws Video Image
	  PImage rgbImage = context.rgbImage();
	  if(rgbImage!= null){
		  this.image(rgbImage,-320,-240);
	  	// if an image is available search for a red point
	  
	  
	  	// load img data
	 	rgbImage.loadPixels();
	 	int[] pixArray;
	 	pixArray 	= rgbImage.pixels;
	 		
	 	// return of search is a redpoint or (-1,-1)
	 	int[] redPoint =	detective.detectRed(pixArray);
	  	this.image(rgbImage,-320, -240);
	  	basicWorldMap 	= context.depthMapRealWorld();
	  	
	  	
	  	//Draw Active area
	  	ArrayList<Integer> dont	= new ArrayList(); 
	  	if(redPoint[0]>0 && redPoint[1]>0){
	  		rect(-320 + redPoint[0], -240 + redPoint[1],10,10);	 	
	  		
	  		int range = 50;
			for(int y= -range; y<range ; y++){
				for(int x = -range; x<range; x++){
					int realX = redPoint[0]-x;
					int realY = redPoint[1]-y;
					
					// checking for meaningfull positions
					realX = (realX > 0)? (realX < 639)? realX : 639  : 0;
					realY = (realY > 0)? (realY < 479)? realY : 479  : 0;
					
					int c	  = realY*640 + realX;
					PVector realWorldPoint = basicWorldMap[c];
					stroke(255,0 ,0);
					dont.add(c);
					point(realWorldPoint.x,realWorldPoint.y,realWorldPoint.z);
				};
			};
		 };
		 /*
		 int i=0;
		 while(i<basicWorldMap.length){
			 PVector realWorldPoint = basicWorldMap[i];
			 stroke(realWorldPoint.mag());
			 if(dont.indexOf(i)<0){
				 point(realWorldPoint.x,realWorldPoint.y,realWorldPoint.z);
			 }
			 i++;
		 }*/
	  }; 
	}//draw function

	public void keyPressed()
	{
	  switch(key)
	  {
	  case ' ':
	    context.setMirror(!context.mirror());
	    break;
	  }

	  switch(keyCode)
	  {
	  case LEFT:
	    rotY += 0.1f;
	    break;
	  case RIGHT:
	    // zoom out
	    rotY -= 0.1f;
	    break;
	  case UP:
	    if(keyEvent.isShiftDown())
	      zoomF += 0.02f;
	    else
	      rotX += 0.1f;
	    break;
	  case DOWN:
	    if(keyEvent.isShiftDown())
	    {
	      zoomF -= 0.02f;
	      if(zoomF < 0.01)
	        zoomF = 0.01;
	    }
	    else
	      rotX -= 0.1f;
	    break;
	  case CONTROL:
		  drawBG = !drawBG;
		break;
	  }
	} 

}