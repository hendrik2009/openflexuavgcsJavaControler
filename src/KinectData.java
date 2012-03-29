import helper.AdvancedVector;
import helper.ColorDetector;
import helper.MatrixGenerator;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import SimpleOpenNI.SimpleOpenNI;
import SimpleOpenNI.StrVector;



public class KinectData extends PApplet {
	
	
	// Distance and rotation calibration to set distance and angles between kinects
	public float PITCH;
	public float DISTANCE;
	public float HEIGHT;
	public float ROLL;
	
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

	
	private static final long serialVersionUID = 1L;
	private final int _kinectCount;

	
	
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

		PITCH			= -23;
		ROLL			= 0.8f;
		DISTANCE		= 4500;
		HEIGHT			= 20;
		
		
		// start OpenNI, loads the library
		SimpleOpenNI.start();

		// print all the cams 
		StrVector strList = new StrVector();
		SimpleOpenNI.deviceNames(strList);
		for(int i=0;i<strList.size();i++){
			println(i + ":" + strList.get(i));

		}
		
		// init cameras
		_context1 = new SimpleOpenNI(0,_parent);
		_context2 = new SimpleOpenNI(1,_parent);
		
	//Camera Settings 
		// disable mirror
		_context1.setMirror(false);
		_context2.setMirror(false);

		// enable RGB Image
		_context1.enableRGB();
		_context2.enableRGB();

		// enable depthMap generation 
		_context1.enableDepth();
		_context2.enableDepth();

		// Creates a color tracker
		detective = new ColorDetector(640,480); 
		
		System.out.println("KinectData::Setup - complete");
	}


	public void draw()
	{
		// update the cameras !! if its only one call update on instance if it is two call static updateAll on SimpleOpenNI
		SimpleOpenNI.updateAll();


	// Scene Build starts

		
		PImage rgbImage = _context1.rgbImage();
		PImage rgbImage1 = _context2.rgbImage();
		
		_parent.pushMatrix(); //------------------v
		_parent.translate(0,0,-DISTANCE/2);
 		_parent.rotateX(radians(PITCH));
		_context1.drawCamFrustum();
		_parent.popMatrix(); //-------------------^
		
		//3d distance Kinect1 to Kinect2
		_parent.pushMatrix(); //-------------v
		_parent.translate(0, -HEIGHT, DISTANCE/2);
		_parent.rotateY(_parent.radians(180));
		_parent.rotateX(radians(PITCH));
		_parent.rotateZ(radians(ROLL));
		_context2.drawCamFrustum();
		_parent.popMatrix(); //--------------^
		
		
		if(mode==TRACKING){
			
			_parent.pushMatrix(); //------------------v
			_parent.translate(0,0,-DISTANCE/2);
	 		_parent.rotateX(radians(PITCH));
			trackRedObject(rgbImage,_context1,1);
			_parent.popMatrix(); //-------------------^
			
			//3d distance Kinect1 to Kinect2
			_parent.pushMatrix(); //-------------v
			_parent.translate(0, -HEIGHT, DISTANCE/2);
			_parent.rotateY(_parent.radians(180));
			_parent.rotateX(radians(PITCH));
			_parent.rotateZ(radians(ROLL));
			trackRedObject(rgbImage1,_context2,2);
			_parent.popMatrix(); //--------------^
			
			
			
		}
		
		else if(mode==SET_BG){

			_currentWorldMap1 	= _context1.depthMapRealWorld();
			_currentWorldMap2 	= _context2.depthMapRealWorld();

			int it = 0;
			// Resets the Background Map
			while(it< _bgMap1.length){

				PVector mem = _currentWorldMap1[it];
				_bgMap1[it] = new PVector(mem.x,mem.y,mem.z);

				PVector mem2 = _currentWorldMap2[it];
				_bgMap2[it] = new PVector(mem2.x,mem2.y,mem2.z);

				it++;
			}

			System.out.print(it);System.out.println(" - BGs were reset");
			mode = TRACKING;
		}
		
		else if(mode == DRAW_ALL){

			// Draws tracked and BG points
			
			_parent.pushMatrix(); //------------------v
			_parent.translate(0,0,-DISTANCE/2);
	 		_parent.rotateX(radians(PITCH));
			trackRedObject(rgbImage,_context1,1);
			_parent.popMatrix(); //-------------------^
			//3d distance Kinect1 to Kinect2
			_parent.pushMatrix(); //-------------v
			_parent.translate(0, -HEIGHT, DISTANCE/2);
			_parent.rotateY(_parent.radians(180));
			_parent.rotateX(radians(PITCH));
			_parent.rotateZ(radians(ROLL));
			trackRedObject(rgbImage1,_context2,2);
			_parent.popMatrix(); //--------------^
			
			
			_parent.stroke(200);
			_parent.translate(0,0,0);
			
			PVector mem;
			PVector mem2;
			
			int it = 0;
			while(it< _bgMap1.length){
				
				_parent.pushMatrix(); //------------------v
				_parent.translate(0,0,-DISTANCE/2);
		 		_parent.rotateX(radians(PITCH));
				mem = _bgMap1[it];
				_parent.point(mem.x,mem.y,mem.z);
				_parent.popMatrix(); //-------------------^

				//3d distance Kinect1 to Kinect2
				_parent.pushMatrix(); //-------------v
				_parent.translate(0, -HEIGHT, DISTANCE/2);
				_parent.rotateY(_parent.radians(180));
				_parent.rotateX(radians(PITCH));
				_parent.rotateZ(radians(ROLL));
				mem2 = _bgMap2[it];
				_parent.point(mem2.x,mem2.y,mem2.z);
				_parent.popMatrix(); //--------------^
				
				
				it++;
			}
		}//End DRAW ALL
		
		_parent.pushMatrix(); //------------------v
		_parent.translate(0,0,-DISTANCE/2);
 		_parent.rotateX(radians(PITCH));
		drawTrackingIndicator(currentPos1[0],currentPos1[1],currentPos1[2]);
		_parent.popMatrix(); //-------------------^
		
		//3d distance Kinect1 to Kinect2
		_parent.pushMatrix(); //-------------v
		_parent.translate(0, -HEIGHT, DISTANCE/2);
		_parent.rotateY(_parent.radians(180));
		_parent.rotateX(radians(PITCH));
		_parent.rotateZ(radians(ROLL));
		drawTrackingIndicator(currentPos2[0],currentPos2[1],currentPos2[2]);
		_parent.popMatrix(); //--------------^
		
		
		
		// Final Position approx
		if(currentPos1[0]!=-1 && currentPos2[0]!=-1){
			AdvancedVector v1 = new AdvancedVector(currentPos1[0],currentPos1[1],currentPos1[2]);
			AdvancedVector v2 = new AdvancedVector(currentPos2[0],currentPos2[1],currentPos2[2]);
			
		}
		else if(currentPos1[0]!=-1){
			AdvancedVector v1 = new AdvancedVector(currentPos1[0],currentPos1[1],currentPos1[2]);
		}
		else if(currentPos2[0]!=-1){
			AdvancedVector v2 = new AdvancedVector(currentPos2[0],currentPos2[1],currentPos2[2]);
		}
		else{
			// null
		}

	}//draw function

	
	/**
	 * Tracks a red object in 3d Space 
	 * 
	 * @param rgbImage
	 * @param inKinect
	 * @param nr
	 */
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

		}// END if rgbImage
	}

	/**Pushes a Matrix an then draws a pylon heading towards x axis based on coordinates
	 * 
	 * @param x
	 * @param y
	 * @param z
	 */
	public void drawTrackingIndicator(float x, float y, float z){
		_parent.pushMatrix();	
		_parent.translate(x,y,z);
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

	}
	
	/**Sets the current Render mode 
	 * 
	 * @param inMode
	 */
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

}