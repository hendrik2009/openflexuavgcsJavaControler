import helper.AdvancedVector;
import SimpleOpenNI.SimpleOpenNI;
import processing.core.PApplet;
import processing.core.PImage;


public class PappletControler extends PApplet {

	public KinectData _kinect;
	public DakaXController _dakaX;
	
	public static PImage		_liveStream;
	
	//Scene parameter
		double        _zoomF =0.24f;
		float        _rotX =  0;// radians(180);  // by default rotate the hole scene 180deg around the x-axis, the data from openni comes upside down
		float        _rotY = radians(0);
	
	AdvancedVector myVector = new AdvancedVector(0, 0, 0);
	
		
		
	public void setup(){
		
		this.frameRate(30);
		 
		this.size(800,600,P3D);
		if(this.frame != null){
			this.frame.setResizable(true);
		}
		this.stroke(255,255,255);
		this.smooth();
		//this.perspective(radians(45), width/height,10,150000);
		
		_liveStream = new PImage();
		
		_dakaX 			= new DakaXController(this);
		this.add(_dakaX);
		
		SimpleOpenNI.start();
		
		//_kinect 		= new KinectData(this);
		//this.add(_kinect);
		
	}
	public void draw(){
		// clear BG
		background(0,0,0);
		
		_dakaX.draw();
		
		pushMatrix();
		  
		  translate(width/2, height/2, 0);
		  rotateX(_rotX);
		  rotateY(_rotY);
		  scale((float)_zoomF);
		  translate(0,0,-1900);  // set the rotation center of the scene 1000 infront of the camera
		
		  //_kinect.draw();
		  drawScene(_kinect);
		
		popMatrix();
		
		drawUI();
	}

	
	private void drawScene(KinectData inKinect){
		 
	}
	private void drawUI(){
		this.image(_liveStream,240,240);
	/*	if(_liveStream != null ){
			
			
			System.out.println(_liveStream.height);
			this.background(0);
			this.pushMatrix();
			this.rotateX(this.radians(180));
			this.image(_liveStream, 0, 0);
			this.popMatrix();
			
		}*/
	}
	
	public void setVideoImg( PImage img){
		if(img != null){
			_liveStream = new PImage( img.getImage() );
			}
	}
	
	// Key control
	public void keyPressed()
	{
	  switch(key)
	  {
	  case ' ':
	    _kinect.setMode(KinectData.SET_BG);
	    break;
	  }

	  switch(keyCode)
	  {
	  // --- Adjusting functions
		  case 'q':{
			  System.out.print("q");
			  System.out.println(_kinect.DISTANCE);
			  _kinect.DISTANCE += 100;
			  System.out.println(_kinect.DISTANCE);
		  	}
			  break;
			  
		  case 'Q':{
			  System.out.print("q");
			  System.out.println(_kinect.DISTANCE);
			  _kinect.DISTANCE += 100;
			  System.out.println(_kinect.DISTANCE);
		  	}
			  break;
			  
		  case 'w':{
			  System.out.print("w");
			  System.out.println(_kinect.DISTANCE);
			  _kinect.DISTANCE -= 100;
			  System.out.println(_kinect.DISTANCE);
		  }
		  	break;
		  	
		  case 'W':{
			  System.out.print("W");
			  System.out.println(_kinect.DISTANCE);
			  _kinect.DISTANCE -= 100;
			  System.out.println(_kinect.DISTANCE);
		  }
		  	break;
		  	
		  case 't':{
			  System.out.println(_kinect.ROLL);
			  _kinect.ROLL -= 0.2f;
			  System.out.println(_kinect.ROLL);
		  }
		  	break;
		  	
		case 'T':{
			System.out.println(_kinect.ROLL);
			  _kinect.ROLL -= 0.2f;
			  System.out.println(_kinect.ROLL);  
				  }
			break;
			
		case 'z':{
			System.out.println(_kinect.ROLL);
			  _kinect.ROLL += 0.2f;
			  System.out.println(_kinect.ROLL);
		}
			break;
			
		case 'Z':{
			System.out.println(_kinect.ROLL);
			  _kinect.ROLL += 0.2f;
			  System.out.println(_kinect.ROLL);
		  }
		  	break;
		  	
		  //------ Camera Controls
		  case LEFT:
		    _rotY += 0.1f;
		    break;
		    
		  case RIGHT:
		    _rotY -= 0.1f;
		    break;
		    
		  case UP:
			  System.out.print("up");
		    if(keyEvent.isShiftDown())
		      _zoomF += 0.02f;
		    else
		      _rotX += 0.1f;
		    break;
		    
		  case DOWN:
		    if(keyEvent.isShiftDown())
		    {
		      _zoomF -= 0.02f;
		      if(_zoomF < 0.01)
		        _zoomF = 0.01;
		    }
		    else
		      _rotX -= 0.1f;
		    break;
		    
		  case CONTROL:
				  _kinect.setMode(KinectData.DRAW_ALL);
			  break;
	  };
	}; 
}


