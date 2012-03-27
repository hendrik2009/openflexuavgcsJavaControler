import helpers.JoystickDataProvider;

import java.util.Timer;
import java.util.TimerTask;

import processing.core.PApplet;
import at.wisch.joystick.event.ControllerEventManager;

public class OpenFlexJavaContoller extends PApplet {

	// Physical Devices
	
//Joystick inputs
	public static JoystickDataProvider _jsDataProvider;
	
// Ar Drone Controller
	public static DroneController _drone;
	public static boolean _is_flying;
	
// Kinect / DakaX	
	public static PappletControler _appletControl; 
	
	public static KinectData _kinect;
	public static DakaXController _dakaX;
	
	
/*
 * Physical Controller
 * 	- JS
 * 	- AR Drone
 * 	- DakaX
 * 	- Kinect
 * 
 * Create movement generator:
 * 		-> Joystick input // last control override 
 * 		-> PathController
 * 		<- Ar Drone moves
 * 		<- camera moves
 * 
 * Create Positioning Controller
 * 		-> DakaX rotData
 * 		-> ArDrone posData
 * 		<- PosData Paket (V;Rot;Pos)
 * 
 * Create PathController
 * 		-> PosData
 * 		-> Flightpath Vector
 * 		<- current move Vector
 * 
 * Create FlightpathController
 * 		-> Flightplan
 * 		-> Timeline
 * 
 * !!Create Flex interface
 * 		-> UI inputs
 * 		<- VideoData
 * 		<- PosData
 * 		<- Enviromentmap
 * 		<- StatusData
 * 
 * Navmapper Rasterized flight command presets? 
 * Space restrictions
 * 
 * 
 * 
 */
	
	/**
	 * 
	 * Based on GNU licensed projects and provided under same conditions
	 * 
	 * This class is a wrapper combining:
	 * 	AR DRONE FOR P5
	 *  FF JOYSTICK 
	 *  SIMPLE OPEN NI
	 * 
	 *  The Goal is to provide a full functional control system for the AR Drone
	 *  with Joystick inputs / Navigation data / Video
	 *  to be accesed by a Flex Air application to start visual Studies on data representation for modern drones in
	 *  an advanced graphical enviroment.
	 * 
	 * @author H.Kršger
	 * 
	 * 
	 * @param arg
	 */
	public static void main(String[] arg){

		final OpenFlexJavaContoller thisClass = new OpenFlexJavaContoller();
		thisClass.setVisible(true);
		
		// Controlldata Loop pulling Data every 100 ms to set NavigationCommand 
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
	        public void run() {
				thisClass.navigateByDirectControl( _jsDataProvider.getJsData() );
	        }
	    }, 0, 100);
		
	};
	
	// Constructor
	public OpenFlexJavaContoller(){
		this.initControllers();
	}
	
	public void initControllers(){	

	// starts Kinect & DakaX
		_appletControl	= new PappletControler();
		_appletControl.main( new String[]{"PappletControler"});
		_kinect 		=  _appletControl._kinect;
		_dakaX		    =  _appletControl._dakaX;
		
		
	// Connecting to Joystick
		_jsDataProvider 			= new JoystickDataProvider("");
		ControllerEventManager.addControllerEventListener( _jsDataProvider );
	//connecting Drone
		_drone 						= new DroneController("192.168.1.1", _appletControl);
	}

	static void navigateByDirectControl(float[] jsData){
		float factor	= 20;
		
		
		if(_is_flying){
			float vX 	= 0;	// Left right
			float vY 	= 0;	// Forward Backward
			float vZ 	= 0;	// Up Down
			float dPhi 	= 0;
			
			boolean move 	= false;
			
			// Axis Controlles
			
			// Left right on x-axis
			if( jsData[0] > 0.04){
				System.out.println("rightStrave");
				vX = jsData[0];
				move =true;
			}
			else if( jsData[0] < -0.04){
				System.out.println("leftStrave");
				vX = jsData[0];
				move =true;
			}
			
			// For- Back -wards on y-axis
			if( jsData[1] > 0){
				System.out.println("BACK");
				vY = jsData[1];
				move =true;
			}
			else if( jsData[1] < 0){
				System.out.println("FORWARD");
				vY = jsData[1];
				move =true;
			}
			
			// Lateral rotation
			if( jsData[2] > 0){
				System.out.println("TurnR");
				dPhi = jsData[2];
				move =true;
			}
			else if( jsData[2] < 0){
				System.out.println("TurnL");
				dPhi = jsData[2];
				move =true;
			}
			
			// Vertical speed
			if( jsData[3] > 0){
				System.out.println("AltUP");
				vZ = jsData[3];
				move =true;
			}
			else if( jsData[3] < 0){
				System.out.println("AltDown");
				vZ = jsData[3];
				move =true;
			}
			
			if(move){
				System.out.println("MOVE!!!!");
				_drone.move3D( (int)(-vY*factor)  , (int)(-vX*factor), (int)(vZ*factor), (int)(-dPhi*factor));
			}
			else{
				_drone.stop();
			}
		}// end of just while flying
	
		// start on btn 0
		if( jsData[18] > 0 ){
			if(!_is_flying){
				System.out.println("TakeOFF");
				_is_flying		= true;
				_drone.takeOff();
			}
		}
		// land on btn 1
		if( jsData[19] > 0 ){
			System.out.println("land");
			_is_flying		= false;
			_drone.landing();
		}
	}
	

}
