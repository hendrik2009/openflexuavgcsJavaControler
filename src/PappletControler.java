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
		
		this.frameRate(25);
		 
		this.size(800,600,P3D);
		
		if(this.frame != null){
			this.frame.setResizable(true);
		}
		
		this.stroke(255,255,255);
		//this.smooth();
		
		_liveStream = new PImage();
		
		_dakaX 			= new DakaXController(this);
		this.add(_dakaX);
		
		SimpleOpenNI.start();
		
		_kinect 		= new KinectData(this,2);
		this.add(_kinect);
		
	}
	public void draw(){
		
		// clear BG
		background(0,0,0);
		
		_dakaX.draw();
		
		pushMatrix();	//----------- PUSH MAT
		  
		  translate(width/2, height/2, - _kinect.DISTANCE/2);

		  rotateX(_rotX);
		  rotateY(_rotY);
		  scale((float)_zoomF);
		
		  _kinect.draw();

		
		popMatrix();	//----------- POP MAT
		
		// Drone Image 
		this.image(_liveStream,240,240);
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


