import SimpleOpenNI.*;
import processing.core.*;

import helper.ColorDetector;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;



public class KinectData extends PApplet {
	private static final long serialVersionUID = 1L;

	private final String TRACKING 			= "track"; 
	private final String SET_BG 			= "bgupdate";
	
	SimpleOpenNI context;
	double        zoomF =0.24f;
	float        rotX = radians(180);  // by default rotate the hole scene 180deg around the x-axis, 
	                                   // the data from openni comes upside down
	float        rotY = radians(0);
	
	BufferedImage image;
	//------
	PVector[] currentWorldMap;
	PVector[] bgMap  = new PVector[ 307200 ];
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
	
	String mode		= SET_BG;
	
	ColorDetector detective	;
	int[] memRect ={0,0,0,0}; 

	public void setup()
	{
		frameRate(60);
		 
		size(640,480,P3D);
	   //context = new SimpleOpenNI(this,SimpleOpenNI.RUN_MODE_SINGLE_THREADED);
		context = new SimpleOpenNI(this);

	  // disable mirror
		context.setMirror(true);
	  // enable RGB Image
		context.enableRGB();
	  // enable depthMap generation 
		context.enableDepth();

		stroke(255,255,255);
	  	smooth();
	  	perspective(radians(45), width/height,10,150000);
	  	detective = new ColorDetector(640,480); 
	}

	@SuppressWarnings("deprecation")
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
	  if(mode==TRACKING){
		  trackRedObject(rgbImage);
	  }
	  else if(mode==SET_BG){
		  currentWorldMap 	= context.depthMapRealWorld();
		  
		  int it = 0;
		  while(it< bgMap.length){
			  PVector mem = currentWorldMap[it];
			  bgMap[it] = new PVector(mem.x,mem.y,mem.z);
			  it++;
		  }
		  
		  System.out.print(it);System.out.println(" - BG was reset");
		  mode = TRACKING;
	  }
	  
	}//draw function

	public void trackRedObject(PImage rgbImage){
		// if an image is available search for a red point  
		if(rgbImage!= null){
			
			// drawing the rgbImage
			 this.image(rgbImage,-320,-240);
		  	
		  	// load img data
		 	//rgbImage.loadPixels();
		 	int[] pixArray;
		 	pixArray 	= rgbImage.pixels;
		 		
		 	// return of search is a point or (-1,-1)
		 	int[] redPoint =	detective.detectRed(pixArray);
		  	this.image(rgbImage,-320, -240);
		  	currentWorldMap 	= context.depthMapRealWorld();
		  	
		  	//Draw Active area

		  	int[] vectorApproximation = {0,0,0,0};
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
						PVector realWorldPoint = currentWorldMap[c];
						PVector offSetPoint    = bgMap[c];
						stroke(255,0 ,0);
						if(realWorldPoint.dist(offSetPoint) > 40 ){
							
							// relative zum boden
							/*
							vectorApproximation[0] += (realWorldPoint.x - offSetPoint.x);
							vectorApproximation[1] += (realWorldPoint.y - offSetPoint.y);
							vectorApproximation[2] += (realWorldPoint.z - offSetPoint.z);
							vectorApproximation[3]++;*/
							vectorApproximation[0] += (realWorldPoint.x );
							vectorApproximation[1] += (realWorldPoint.y );
							vectorApproximation[2] += (realWorldPoint.z );
							vectorApproximation[3]++;
							
							point(realWorldPoint.x,realWorldPoint.y,realWorldPoint.z);
						};
						/*if(x == 0){
							System.out.print("in1:"); System.out.print(realWorldPoint.x); 
							System.out.print(" in2:");System.out.print(offSetPoint.x);
							System.out.print(" out:"); System.out.print(vectorApproximation[0]);
						};*/
						
					};
				};
			 };
			 System.out.print(vectorApproximation[0]);System.out.print("--");System.out.print(vectorApproximation[3]);System.out.print("  ");
			 if(vectorApproximation[3]!=0){
				 vectorApproximation[0] = vectorApproximation[0] /vectorApproximation[3];
				 vectorApproximation[1] = vectorApproximation[1] /vectorApproximation[3];
				 vectorApproximation[2] = vectorApproximation[2] /vectorApproximation[3];
			 };
			 System.out.print("X:");System.out.print(vectorApproximation[0]);System.out.print("Y:");System.out.print(vectorApproximation[1]);System.out.print("Z:");System.out.println(vectorApproximation[2]);
			 translate(vectorApproximation[0],vectorApproximation[1],vectorApproximation[2] );
			 stroke(255);
			 fill(0x00ff00);
			 box(20);
			
		  };
	}
	
	// Camera movement via key control
	public void keyPressed()
	{
	  switch(key)
	  {
	  case ' ':
	    mode 	= SET_BG;
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