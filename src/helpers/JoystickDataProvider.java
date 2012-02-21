package helpers;


import java.util.ArrayList;
import at.wisch.joystick.*;
import at.wisch.joystick.event.*;
import at.wisch.joystick.exception.*;
import at.wisch.joystick.test.*;


public class JoystickDataProvider implements ControllerEventListener {
	
	public static ArrayList<Joystick> joysticks;
	public static Joystick joystick;
	
	public float[] controler_array;
	
	// aufesetzen eines reading loops fŸr die Joystick inputs
		// finde alle Joysticks
		// connect to PS3 Controller if available
		// connect to MS Sidewinder if available
		// connect to any JS if Available
		// THROW ERROR IF NO Controller
	
		//check if controller
			// Set up Data array
			// add Listener
			// on update write new value to Array
	
	
	
	// Public function fŸr den read Data request von aussen
		// return Data Array or null for connection lost
	
	//CONSTRUCTOR
	public JoystickDataProvider(String jsName){
		System.out.println("Constructor Called??");
		
		controler_array 		= new float[50];

		// init and get-joystick methods have to be done within a try-catch-block
		// (these are fatal errors and we need to deal with them)
		try {
			JoystickManager.init();
			joysticks = JoystickManager.getAllJoysticks(); // all joysticks
			
			// search for suiting joystick
			
			joystick = JoystickManager.getJoystick(); // get the first Joystick

		} catch (FFJoystickException e) {
			e.printErrorMessage();
		}
		if (joysticks.isEmpty()){
			System.out.println("No controllers found...");
			System.exit(0);
		}
	}
	
	public String getTester(){
		return "Jup";
	}
	
	// Neseccary interface override ->
	public void controllerEventOccured( AdvancedControllerEvent Evt ){
		//System.out.println("WAS GEHT");
		// we simply output it. but there are different classes of Events - 
		// we could also distinguish between them and do more advanced stuff.
		//System.out.println(event);
		parseEvent( Evt );
		
	}
	private void parseEvent(AdvancedControllerEvent e){
		/**Should write the updates in the controler_array
		 *  no switches neccessary... ofset and write to array
		 */
		if(e.isAxis()){
			//controller_array[e.getControlIndex()] = e.value;
			
			// Event associated Value
			controler_array[e.getControlIndex()]   =	e.getSource().getAxisValue(e.getControlIndex());
			
			/*switch(e.getControlIndex()){
			case 0: System.out.println("x");
					System.out.println(e.AXIS);
				break;
			case 1: //System.out.println("y");
				break;
			case 2: //System.out.println("z");
				break;
			case 3: //System.out.println("throttle");
				break;
			}*/
		}
		else if(e.isButton()){
			switch(e.getControlIndex()){
			case 0: //System.out.println("button0");
				break; 
			case 1: //System.out.println("button1");
				break;
			}
		}
	}
	
	public float[] getJsData(){
		return controler_array;
	}
	
}// class
