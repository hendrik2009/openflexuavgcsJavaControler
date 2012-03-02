import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import sample.ARDroneTest;

import com.shigeodayo.ardrone.ARDrone;
import com.shigeodayo.ardrone.navdata.AttitudeListener;
import com.shigeodayo.ardrone.navdata.BatteryListener;
import com.shigeodayo.ardrone.navdata.DroneState;
import com.shigeodayo.ardrone.navdata.StateListener;
import com.shigeodayo.ardrone.navdata.VelocityListener;
import com.shigeodayo.ardrone.video.ImageListener;

import at.wisch.joystick.event.ControllerEventManager;
import helpers.JoystickDataProvider;

public class OpenFlexJavaContoller extends JFrame {


	public static JoystickDataProvider _jsDataProvider;
	public static ARDrone _drone;
	public static boolean _is_flying;
	
	private MyPanel myPanel;
	
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
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run() {
				System.out.println("Preinit");
				final OpenFlexJavaContoller thisClass = new OpenFlexJavaContoller();
				
				thisClass.addWindowListener(new WindowAdapter(){
					@Override
					public void windowOpened(WindowEvent e) {
						System.out.println("WindowOpened");
					}
					@Override
					public void windowClosing(WindowEvent e) {
						//thisClass.dispose();
					}
				});
				thisClass.setVisible(true);
				
				// Controlldata Loop pulling Data every 100 ms to set NavigationCommand 
				Timer timer = new Timer();
				timer.scheduleAtFixedRate(new TimerTask() {
			        public void run() {
			        	try {
							Thread.sleep(100); // check once every second -> wait 100ms
						} catch (InterruptedException e) {
							e.printStackTrace();
							}
						System.out.println("tick");
						thisClass.navigateByDirectControl( _jsDataProvider.getJsData() );
						System.out.println("");
			        }
			    }, 0, 100);
			}
		});
	};
	
	// Constructor
	public OpenFlexJavaContoller(){
		System.out.println("Controller");
		this.initController();
	}
	
	public void initController(){	
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
				if(myPanel!=null){
					myPanel.setImage(image);
					myPanel.repaint();
				}
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
		this.setTitle("ardrone");
		this.setSize(640, 480);
		this.add(getMyPanel());
		
		
	}
	
	
	
	static void navigateByDirectControl(float[] jsData){
		System.out.println("New Joydata");
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
	
	private JPanel getMyPanel(){
		if(myPanel==null){
			myPanel=new MyPanel();
		}
		return myPanel;
	}
	
	/**
	 * •`‰æ—p‚Ìƒpƒlƒ‹
	 * @author shigeo
	 *
	 */
	private class MyPanel extends JPanel{
		private static final long serialVersionUID = -7635284252404123776L;

		/** ardrone video image */
		private BufferedImage image=null;
		
		public void setImage(BufferedImage image){
			// Brighten the image by 30%
			float scaleFactor = 1.3f;
			RescaleOp op = new RescaleOp(scaleFactor, 0, null);
			image = op.filter(image, null);
			this.image=image;
		}
		
		public void paint(Graphics g){
			g.setColor(Color.white);
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
			if(image!=null)
				g.drawImage(image, 0, 0,640,480,null); //image.getWidth(), image.getHeight(), null);
			
		}
	}
}
