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
	 * @author H.Kr�ger
	 * 
	 * 
	 * @param arg
	 */
	public static void main(String[] arg){
		
		
		
		// Connecting to Joystick
		_jsDataProvider 			= new JoystickDataProvider("");
		ControllerEventManager.addControllerEventListener( _jsDataProvider );
		
		//connecting Drone
		//_drone 						= new ARDrone();
		
		// Controlldata Loop pulling Data every 1000 ms to set NavigationCommand 
		do {
			
			try {
				Thread.sleep(1000); // check once every second -> wait 1000ms
			} catch (InterruptedException e) {
				e.printStackTrace();
				}
			System.out.println("tick");
			for ( float item :_jsDataProvider.getJsData()){
				System.out.println(item);
			}
			System.out.println("tickEND");
			System.out.println("");
					//_jsDataProvider.getJsData()[0]);
		} while (true );
		
	}
	
	// Constructor
	public void OpenFlexJavaControler(String[] arg){
		
	}
}
