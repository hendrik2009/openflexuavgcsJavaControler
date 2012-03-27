import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import processing.core.PApplet;
import processing.core.PImage;

import com.shigeodayo.ardrone.ARDrone;
import com.shigeodayo.ardrone.navdata.AttitudeListener;
import com.shigeodayo.ardrone.navdata.BatteryListener;
import com.shigeodayo.ardrone.navdata.DroneState;
import com.shigeodayo.ardrone.navdata.StateListener;
import com.shigeodayo.ardrone.navdata.VelocityListener;
import com.shigeodayo.ardrone.video.ImageListener;


public class DroneController extends ARDrone {

private PappletControler _display;
	
	private PImage _myImg	= new PImage();	

	public DroneController(String ip,PappletControler display){
		super(ip);

		_display = display;
		
		
		System.out.println("connect drone controller");
		this.connect();
		System.out.println("connect drone navdata");
		this.connectNav();
		System.out.println("connect drone video");
		this.connectVideo();
		System.out.println("start drone");
		this.start();
		
		// Listeners for all available Data
		this.addImageUpdateListener(new ImageListener(){
			@Override
			public void imageUpdated(BufferedImage image) {
				
				_myImg 	= new PImage((Image) image) ;
			
				if(_myImg == null){
					System.out.println("shit");
				}
				else{
					if(_myImg.pixels.length > 0){
						_display.setVideoImg(_myImg);
					}
					else{
						System.out.println("empty");
					}
				}
			}
		});
		
		this.addAttitudeUpdateListener(new AttitudeListener() {
			@Override
			public void attitudeUpdated(float pitch, float roll, float yaw, int altitude) {
				//System.out.println("pitch: "+pitch+", roll: "+roll+", yaw: "+yaw+", altitude: "+altitude);
			}
		});
		
		this.addBatteryUpdateListener(new BatteryListener() {
			@Override
			public void batteryLevelChanged(int percentage) {
				//System.out.println("battery: "+percentage+" %");
			}
		});
				
		this.addStateUpdateListener(new StateListener() {
			@Override
			public void stateChanged(DroneState state) {
				//System.out.println("state: "+state.toString());
			}
		});
		
		this.addVelocityUpdateListener(new VelocityListener() {
			@Override
			public void velocityChanged(float vx, float vy, float vz) {
				//System.out.println("vx: "+vx+", vy: "+vy+", vz: "+vz);
			}
		});
	}
	
}
