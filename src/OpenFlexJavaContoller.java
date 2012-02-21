import com.shigeodayo.ardrone.ARDrone;

import at.wisch.joystick.event.ControllerEventManager;
import helpers.JoystickDataProvider;

public class OpenFlexJavaContoller {

	private static JoystickDataProvider _jsDataProvider;
	private static ARDrone _drone;
	
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
		
		
		
		// Connecting to Joystick
		_jsDataProvider 			= new JoystickDataProvider("");
		ControllerEventManager.addControllerEventListener( _jsDataProvider );
		
		//connecting Drone
		_drone 						= new ARDrone("192.168.1.1");
		System.out.println("connect drone controller");
		_drone.connect();
		System.out.println("connect drone navdata");
		_drone.connectNav();
		System.out.println("connect drone video");
		_drone.connectVideo();
		System.out.println("start drone");
		_drone.start();
		
		// Controlldata Loop pulling Data every 1000 ms to set NavigationCommand 
		do {
			
			try {
				Thread.sleep(100); // check once every second -> wait 1000ms
			} catch (InterruptedException e) {
				e.printStackTrace();
				}
			System.out.println("tick");
			navigateByDirectControl( _jsDataProvider.getJsData() );
				
			System.out.println("tickEND");
			System.out.println("");
					//_jsDataProvider.getJsData()[0]);
		} while (true );
	}
	
	// Constructor
	public void OpenFlexJavaControler(String[] arg){
		
	}
	
	static void navigateByDirectControl(float[] jsData){
		
		/*
		// Left right on x-axis
		if( jsData[0] > 0){
			System.out.println("rightStrave");
			_drone.goRight(1);
		}
		else if( jsData[0] < 0){
			System.out.println("leftStrave");
			_drone.goLeft(1);
		}
		else{
			_drone.stop();
		}
		
		// For- Back -wards on y-axis
		if( jsData[1] > 0){
			System.out.println("BACK");
		}
		else if( jsData[1] < 0){
			System.out.println("FORWARD");
		}
		*/
		
		
		// start on btn 0
		if( jsData[4] > 0 ){
			System.out.println("start");
			_drone.takeOff();
		}
		// land on btn 1
		if( jsData[5] > 0 ){
			System.out.println("land");
			_drone.landing();
		}
		
		// start on btn 0
		if( jsData[6] > 0 ){
			System.out.println("l");
			_drone.spinLeft(100);
		}
		// land on btn 1
		if( jsData[7] > 0 ){
			System.out.println("r");
			_drone.spinRight(100);
		}
		
	}
}
