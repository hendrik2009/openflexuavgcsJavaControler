import helper.JoystickDataProvider;

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
	public static MovementController _mover;
	
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
				thisClass.navigate( _jsDataProvider.getJsData() );
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
	//
		_mover 						= new MovementController(_drone);
	}

	static void navigate(float[] jsData){
		_mover.invokeMove(jsData, MovementController.SW);
	}
	

}
