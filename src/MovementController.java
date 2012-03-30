

import movement.MoveAnimator;
import movement.animations.DroneVector;



public class MovementController {

	public static final int DIRECT 	 = 0; 
	public static final int ANIMATED = 1;  
	public static final int MIXED 	 = 2; 
	
	public static String PS3		= "ps3Controller"  ;
	public static String SW			= "sidewinder";
	public int _currentMode			= 0;
	
	private int _flight_mode 		= 0;
	private final MoveAnimator _animator = new MoveAnimator();
	
	private final DroneController _drone;

	public MovementController(DroneController drone){
		_drone 		= drone;
	}
	public void invokeMove(float[] data ,String name){
		
		
		/*System.out.print(":");
		int a= 0;
		while(a<data.length){
			if(data[a]!= 0){
				System.out.print(a);
				System.out.print(":");
				System.out.print(data[a]);
				System.out.print("  ");
			}
			a++;
		}
		System.out.println("  ");*/
		
		switch(_currentMode){
		 case 0:{
			 if(name==PS3){
				 DirectPS(data);
			 }
			 else if(name==SW){
				 DirectSW(data);
			 }
			 else{
				 System.out.println("NO JOYSTICK MAP !!!!");
			 }
		 }
		 break;
		 case 1:{
			// Emergency Button to switch to direct Control
			 if(name==PS3){
				if(data[13] == 1){
					System.out.println("Switch to direct control");
					_currentMode = DIRECT;
				}
			 }
			 else if(name==SW){
				if(data[10] ==1 ){
					System.out.println("Switch to direct control");
					_currentMode = DIRECT;
				}
			 }	
			 AnimatedFlight(data);
		 }
		 break;
		}
	}
	private void AnimatedFlight(float[] jsData){
		//if(_drone.is_flying){
			DroneVector Vmove = _animator.step();
			moveDrone(Vmove);
/*		}
		else{
			System.out.println("Switch to direct control - no animations on ground");
			_currentMode = DIRECT;
		}*/
	}
	
	private void DirectPS(float[] jsData){
		float factor	= .2f;
		
		
		if(_drone.is_flying){
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
				_drone.move3D( -vY*factor  , -vX*factor, vZ*factor, -dPhi*factor);
			}
			else{
				_drone.stop();
			}
		}// end of just while flying
	
		// switch to animated mode
		if(jsData[13] == 1){
			System.out.print("Switch to animated control");
			_currentMode = ANIMATED;
		}
		
		// start on btn 0
		if( jsData[18] > 0 ){
			if(!_drone.is_flying){
				System.out.println("TakeOFF");
				_drone.takeOff();
			}
		}
		// land on btn 1
		if( jsData[19] > 0 ){
			System.out.println("land");
			_drone.landing();
		}
	}
	private void DirectSW(float[] jsData){
		float factor	= .2f;
		
		
		if(_drone.is_flying){
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
			if( jsData[24] > 0){
				System.out.println("AltUP");
				vZ = jsData[24];
				move =true;
			}
			else if( jsData[24] < 0){
				System.out.println("AltDown");
				vZ = jsData[24];
				move =true;
			}
			
			if(move){
				System.out.println("MOVE!!!!");
				_drone.move3D( -vY*factor  , -vX*factor, vZ*factor, -dPhi*factor);
			}
			else{
				_drone.stop();
			}
		}// end of just while flying
	
		// start / land on btn 0
		if( jsData[5] > 0 ){
			if(!_drone.is_flying){
				System.out.println("TakeOFF");
				_drone.takeOff();
			}
			else{
				System.out.println("land");
				_drone.landing();
			}
		}
		// switch to animated mode
		if(jsData[10] == 1){
			System.out.print("Switch to animated control");
			_currentMode = ANIMATED;
		}
		
	}
	
	
	private void moveDrone(DroneVector V){
		_drone.move3D((float)V.Vx, (float)V.Vy,(float)V.Vz, (float)V.Vrot);
	}
}
