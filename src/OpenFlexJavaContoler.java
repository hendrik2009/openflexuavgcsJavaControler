import at.wisch.joystick.event.ControllerEventManager;
import helpers.JoystickDataProvider;

public class OpenFlexJavaContoler {

	private static JoystickDataProvider _jsDataProvider;
	
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
		
		
		System.out.println(_jsDataProvider.getTester());
		
		
		
		// Controlldata Loop pulling Data every 1000 ms to set NavigationCommand 
		do {
			
			try {
				Thread.sleep(1000); // check once every second -> wait 1000ms
			} catch (InterruptedException e) {
				e.printStackTrace();
				}
			System.out.println("tick");
			System.out.println(_jsDataProvider.getJsData()[0]);
		} while (true );
		
	}
	
	// Constructor
	public void OpenFlexJavaControler(String[] arg){
		
	}
}
