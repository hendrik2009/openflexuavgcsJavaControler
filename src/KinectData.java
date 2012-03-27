import helper.ColorDetector;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import SimpleOpenNI.SimpleOpenNI;
import SimpleOpenNI.StrVector;



public class KinectData extends PApplet {
	private static final long serialVersionUID = 1L;

	// Current Tracked Position
	public int[] currentpos  = {0,0,0};
	public int[] currentPos1 = {-1,-1,-1};
	public int[] currentPos2 = {-1,-1,-1};
	
	// Camera modes
	public static final int TRACKING 		= 0; 
	public static final int SET_BG 			= 1;
	public static final int DRAW_ALL		= 2 ;
	
	// DisplayApplet
	public final PApplet _parent;
	
	// Kinect interface
	private SimpleOpenNI _context1;
	private SimpleOpenNI _context2;
	
	// 3D Maos
	private PVector[] _currentWorldMap1;
	private PVector[] _currentWorldMap2;
	private PVector[] _bgMap1  = new PVector[ 307200 ];
	private PVector[] _bgMap2  = new PVector[ 307200 ];
	
	// Tracking mode
	private int mode		= SET_BG;
	
	// Color Tracker and positions
	private ColorDetector detective	;
	private int[] memRect1 ={0,0,0,0};
	private int[] memRect2 ={0,0,0,0};
	
	// Distance and rotation calibration
	public float OPENING_ANGLE;
	public float DISTANCE;
	public float HEIGHT;
	public float ROLL;
	
	final int _kinectCount;
	
	/**
	 * KinectData needs 2 kinect controller to work!</br>
	 * It trackes one red pattern in 3d space and lets you find its position in avirtual space.</br>
	 * Distance vector of the two kinects and the rotation to each other have to be mesured and set to run.
	 * 
	 * @param parent Papplet to run the Rendering in
	 */
	
	public KinectData( PApplet parent, int kinectCount)
	{
		System.out.print("KinectData::Setup - start");
		_parent = parent;	
		_kinectCount = kinectCount;
		
		OPENING_ANGLE	= -23;
		ROLL			= -4;
		DISTANCE		= 5000;
		HEIGHT			= 20;
		// start OpenNI, loads the library
		  SimpleOpenNI.start();
		  
		  // print all the cams 
		  StrVector strList = new StrVector();
		  SimpleOpenNI.deviceNames(strList);
		  for(int i=0;i<strList.size();i++)
		    println(i + ":" + strList.get(i));

		_context1 = new SimpleOpenNI(0,_parent);		
	  // disable mirror
		_context1.setMirror(false);
	  // enable RGB Image
		_context1.enableRGB();
	  // enable depthMap generation 
		_context1.enableDepth();
		
		if(_kinectCount >1){
			_context2 = new SimpleOpenNI(1,_parent);
			_context2.setMirror(false);
			_context2.enableRGB();
			_context2.enableDepth();
		}

	  // Creates a color tracker
	  	detective = new ColorDetector(640,480); 
	  	System.out.println("KinectData::Setup - complete");
	}


	public void draw()
	{
	  // update the cameras !! if its only one call update on instance if it is two call static updateAll on SimpleOpenNI
	
		if(_kinectCount >1){
			SimpleOpenNI.updateAll();
		}
		else{
			_context1.update();
		}
		
	  // Scene Build starts
	  _parent.pushMatrix();//a
	  _parent.rotateX(radians(OPENING_ANGLE));
	  
	  _context1.drawCamFrustum();	  
	 
 // Start Tracking on Kinect 1	  
	  // add Video Image
	  PImage rgbImage = _context1.rgbImage();

	  if(mode==TRACKING){
		  trackRedObject(rgbImage,_context1,1);
	  }
	  else if(mode==SET_BG){
		  _currentWorldMap1 	= _context1.depthMapRealWorld();
		  int it = 0;
		  // Resets the Background Map
		  while(it< _bgMap1.length){
			  PVector mem = _currentWorldMap1[it];
			  _bgMap1[it] = new PVector(mem.x,mem.y,mem.z);
			  it++;
		  }
		  System.out.print(it);System.out.println(" - BG was reset");
	  }
	  else if(mode == DRAW_ALL){
		  // Draws tracked and BG points
		  trackRedObject(rgbImage,_context1,1);
		  
		  _parent.stroke(200);
		  _parent.translate(0,0,0);
		  int it = 0;
		  while(it< _bgMap1.length){
			  PVector mem = _bgMap1[it];
			  _parent.point(mem.x,mem.y,mem.z);
			  it++;
		  }
	  }
	  
	  _parent.pushMatrix();	
	  _parent.translate(currentPos1[0],currentPos1[1],currentPos1[2]);
	  _parent.stroke(255);
	  _parent.fill(0, 255, 0, 255);
		
	  _parent.beginShape(QUAD_STRIP);
	  _parent.vertex(150, 0, 0);
	  _parent.vertex(0, 25, 0);
	  _parent.vertex(0,0,25);
	 	
	  _parent.vertex(150, 0, 0);
	  _parent.vertex(0, -25, 0);
	  _parent.vertex(0,0,25);
	 	
	  _parent.vertex(150, 0, 0);
	  _parent.vertex(0, -25, 0);
	  _parent.vertex(0,0,-25);
	 
	  _parent.vertex(150, 0, 0);
	  _parent.vertex(0, 25, 0);
	  _parent.vertex(0,0,-25);
	 
	  _parent.vertex(150, 0, 0);
	  _parent.vertex(0, 25, 0);
	  _parent.vertex(0,0,-25);
	  _parent.endShape();
	  _parent.popMatrix();
	  
	  _parent.popMatrix();//aEnd

	  
	  _parent.pushMatrix();//b
	  
	  //3d distance Kinect1 to Kinect2
	  _parent.translate(0, -HEIGHT, DISTANCE);
	  _parent.rotateY(_parent.radians(180));
	  _parent.rotateX(radians(OPENING_ANGLE));
	  _parent.rotateZ(radians(ROLL));
	  
 // Start Tracking on Kinect 2	  
	// add Video Image
		  
	  
	  PImage rgbImage1 = _context2.rgbImage();
		 
	   _context2.drawCamFrustum();
	   
		  // Start Tracking
		  if(mode==TRACKING){
			  trackRedObject(rgbImage1,_context2,2);
		  }
		  else if(mode==SET_BG){
			  _currentWorldMap2 	= _context2.depthMapRealWorld();
			  int it = 0;
			  // Resets the Background Map
			  while(it< _bgMap2.length){
				  PVector mem = _currentWorldMap2[it];
				  _bgMap2[it] = new PVector(mem.x,mem.y,mem.z);
				  it++;
			  }
			  System.out.print(it);System.out.println(" - BG was reset");
			  mode = TRACKING;
		  }
		  else if(mode == DRAW_ALL){
			  // Draws tracked and BG points
			  trackRedObject(rgbImage1,_context2,2);
			  _parent.stroke(160);  
			  _parent.translate(0,0,0);
			  int it = 0;
			  while(it< _bgMap2.length){
				  PVector mem = _bgMap2[it];
				  _parent.point(mem.x,mem.y,mem.z);
				  it++;
			  }
		  }
		  _parent.pushMatrix();	
			  _parent.translate(currentPos2[0],currentPos2[1],currentPos2[2]);
			  _parent.stroke(255);
			  _parent.fill(255,0 , 0, 255);
				
			  _parent.beginShape(QUAD_STRIP);
			  _parent.vertex(150, 0, 0);
			  _parent.vertex(0, 25, 0);
			  _parent.vertex(0,0,25);
			 	
			  _parent.vertex(150, 0, 0);
			  _parent.vertex(0, -25, 0);
			  _parent.vertex(0,0,25);
			 	
			  _parent.vertex(150, 0, 0);
			  _parent.vertex(0, -25, 0);
			  _parent.vertex(0,0,-25);
			 
			  _parent.vertex(150, 0, 0);
			  _parent.vertex(0, 25, 0);
			  _parent.vertex(0,0,-25);
			 
			  _parent.vertex(150, 0, 0);
			  _parent.vertex(0, 25, 0);
			  _parent.vertex(0,0,-25);
			  _parent.endShape();
		  _parent.popMatrix();
		  
	  _parent.popMatrix();//bEnd
	 
	  if(currentPos1[0]!=-1 && currentPos2[0]!=-1){
		  PVector v1 = new PVector(currentPos1[0],currentPos1[1],currentPos1[2]);
		  PVector v2 = new PVector(currentPos2[0],currentPos2[1],currentPos2[2]);
		  
		  
		  
		  PVector vRes = new PVector();  
	  }
	  else if(currentPos1[0]!=-1){
		  
	  }
	  else if(currentPos2[0]!=-1){
		  
	  }
	  
	}//draw function

	public void trackRedObject(PImage rgbImage, SimpleOpenNI inKinect, int nr){
		// if an image is available search for a red point  
		if(rgbImage!= null){

		  	// load img data
		 	int[] pixArray;
		 	pixArray 	= rgbImage.pixels;
		 		
		 	// return of search is a point or (-1,-1)
		 	int[] redPoint =	detective.detectRed(pixArray);
		 	
		 	// drawing the rgbImage
 			_parent.pushMatrix();
 			_parent.rotateX(_parent.radians(180));
 			_parent.image(rgbImage,-320,-240);
 		  	_parent.popMatrix();
 		  	
		  	if(nr==1){
		  		_currentWorldMap1 	= inKinect.depthMapRealWorld();
		  	}
		  	else{
		  		_currentWorldMap2 	= inKinect.depthMapRealWorld();
		  	}
		  	//Draw Active area

		  	int[] vectorApproximation = {0,0,0,0};
		  	// if pattern was found
		  	if(redPoint[0]>0 && redPoint[1]>0){
		  		
		  		//search in range before and after given point in x & y axis
		  		int range = 50;
				for(int y= -range; y<range ; y++){
					for(int x = -range; x<range; x++){
						
						int realX = redPoint[0]-x;
						int realY = redPoint[1]-y;
						
						// checking for meaningfull positions correcting out of image positions
						realX = (realX > 0)? (realX < 639)? realX : 639  : 0;
						realY = (realY > 0)? (realY < 479)? realY : 479  : 0;
						
						// current point of array
						int c	  = realY*640 + realX;
						PVector realWorldPoint;
						PVector offSetPoint;
						if(nr==1){
							 realWorldPoint = _currentWorldMap1[c];
							 offSetPoint    = _bgMap1[c];
						}
						else{
							 realWorldPoint = _currentWorldMap2[c];
							 offSetPoint    = _bgMap2[c];
						}
							_parent.stroke(255,0 ,0);
						double curLength = realWorldPoint.mag();
						double offLength = offSetPoint.mag();
						
						// if both are initialized with messured data
						if(curLength>0 && offLength > 0){
							// is the current point an offset worth recognition to the Backgroundmap
							if((realWorldPoint.dist(offSetPoint) > offSetPoint.mag()*0.2) ){
								// add coordinates to approximation Array
								vectorApproximation[0] += (realWorldPoint.x );
								vectorApproximation[1] += (realWorldPoint.y );
								vectorApproximation[2] += (realWorldPoint.z );
								vectorApproximation[3]++;
								_parent.point(realWorldPoint.x,realWorldPoint.y,realWorldPoint.z);
							};
						};
					};
				};
			 };
			 // if more than at least one point was found approximate position
			 if(vectorApproximation[3]!=0){
				 vectorApproximation[0] = vectorApproximation[0] /vectorApproximation[3];
				 vectorApproximation[1] = vectorApproximation[1] /vectorApproximation[3];
				 vectorApproximation[2] = vectorApproximation[2] /vectorApproximation[3];
				 if(nr==1){
					 currentPos1[0] = vectorApproximation[0];
					 currentPos1[1] = vectorApproximation[1];
					 currentPos1[2] = vectorApproximation[2];		
				 }
				 else{
					 currentPos2[0] = vectorApproximation[0];
					 currentPos2[1] = vectorApproximation[1];
					 currentPos2[2] = vectorApproximation[2];
				 }
			 }
			 else{
				 if(nr==1){
					 currentPos1[0] = -1;
					 currentPos1[1] = -1;
					 currentPos1[2] = -1;
				 }
				 else{
					 currentPos2[0] = -1;
					 currentPos2[1] = -1;
					 currentPos2[2] = -1;
				 }
			 }
			 
		  };
	}
	
	public void setMode(int inMode ){
		switch(inMode){
		case TRACKING:
			mode = TRACKING;
			break;
		case SET_BG:
			mode = SET_BG;
			break;
		case DRAW_ALL:
			mode = DRAW_ALL;
			break;
		default:
			System.out.print("KinectData::setMode: - Error unknown mode");
			System.out.println(inMode);
		}
	}

	/*
	// Key control
	public void keyPressed()
	{
	  switch(_parent.key)
	  {
	  case ' ':
	    mode 	= SET_BG;
	    break;
	  }

	  switch(_parent.keyCode)
	  {
	  case LEFT:
	    _rotY += 0.1f;
	    break;
	  case RIGHT:
	    // zoom out
	    _rotY -= 0.1f;
	    break;
	  case UP:
	    if(_parent.keyEvent.isShiftDown())
	      _zoomF += 0.02f;
	    else
	      _rotX += 0.1f;
	    break;
	  case DOWN:
	    if(_parent.keyEvent.isShiftDown())
	    {
	      _zoomF -= 0.02f;
	      if(_zoomF < 0.01)
	        _zoomF = 0.01;
	    }
	    else
	      _rotX -= 0.1f;
	    break;
	  case CONTROL:
		  mode 	= (mode == DRAW_ALL)? TRACKING : DRAW_ALL;
		  break;
	  };
	}; */

}