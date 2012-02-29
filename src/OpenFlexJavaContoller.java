import java.awt.image.BufferedImage;

import com.shigeodayo.ardrone.ARDrone;
import com.shigeodayo.ardrone.navdata.AttitudeListener;
import com.shigeodayo.ardrone.navdata.BatteryListener;
import com.shigeodayo.ardrone.navdata.DroneState;
import com.shigeodayo.ardrone.navdata.StateListener;
import com.shigeodayo.ardrone.navdata.VelocityListener;
import com.shigeodayo.ardrone.video.ImageListener;

import at.wisch.joystick.event.ControllerEventManager;
import helpers.JoystickDataProvider;

public class OpenFlexJavaContoller {

	private static JoystickDataProvider _jsDataProvider;
	private static ARDrone _drone;
	private static boolean _is_flying;
	
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
		
		// Listeners for all available Data
		_drone.addImageUpdateListener(new ImageListener(){
			@Override
			public void imageUpdated(BufferedImage image) {
				/*if(myPanel!=null){
					myPanel.setImage(image);
					myPanel.repaint();
				}*/
			}
		});
		
		_drone.addAttitudeUpdateListener(new AttitudeListener() {
			@Override
			public void attitudeUpdated(float pitch, float roll, float yaw, int altitude) {
				//System.out.println("pitch: "+pitch+", roll: "+roll+", yaw: "+yaw+", altitude: "+altitude);
			}
		});
		
		_drone.addBatteryUpdateListener(new BatteryListener() {
			@Override
			public void batteryLevelChanged(int percentage) {
				//System.out.println("battery: "+percentage+" %");
			}
		});
				
		_drone.addStateUpdateListener(new StateListener() {
			@Override
			public void stateChanged(DroneState state) {
				//System.out.println("state: "+state.toString());
			}
		});
		
		_drone.addVelocityUpdateListener(new VelocityListener() {
			@Override
			public void velocityChanged(float vx, float vy, float vz) {
				//System.out.println("vx: "+vx+", vy: "+vy+", vz: "+vz);
			}
		});
		
		
		// Controlldata Loop pulling Data every 1000 ms to set NavigationCommand 
		do {
			
			try {
				Thread.sleep(100); // check once every second -> wait 100ms
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
		float factor	= 50;
		
		
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
