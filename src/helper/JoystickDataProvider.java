package helper;


import java.util.ArrayList;
import at.wisch.joystick.*;
import at.wisch.joystick.event.*;
import at.wisch.joystick.exception.*;
import at.wisch.joystick.test.*;


public class JoystickDataProvider implements ControllerEventListener {
	
	private static ArrayList<Joystick> joysticks;
	private static Joystick joystick;
	
	// buffered Joystickdata 
	// supports 4 axis [0]-[3] x y z rx
	// and up to 20 Buttons [4]-[23]
	// and one pov [24]-[27] 
	// called buttons will only by reset to 0 after external read
	private float[] controller_array;
	

	
	
	//CONSTRUCTOR
	public JoystickDataProvider(String jsName){		
		controller_array 		= new float[28];
		// init and get-joystick methods have to be done within a try-catch-block
		// (these are fatal errors and we need to deal with them)
		try {
			JoystickManager.init();
			joysticks = JoystickManager.getAllJoysticks(); // all joysticks		
			joystick = JoystickManager.getJoystick(); // get the first Joystick		
		} catch (FFJoystickException e) {
			e.printErrorMessage();
		}
		if (joysticks.isEmpty()){
			System.out.println("No controllers found...");
			System.exit(0);
		}
		else{
			System.out.println("Joysticks Active");
		}
	}
	
	// Neseccary interface override ->
	public void controllerEventOccured( AdvancedControllerEvent Evt ){
		parseEvent( Evt );
	}
	
	/** catching Event data and writing changes into controller_array
	 * @param e :AdvancedControllerEvent 
	 */
	private void parseEvent(AdvancedControllerEvent e){
		/**Should write the updates in the controller_array
		 */
		
		
		if(e.isAxis()){
			//controller_array[e.getControlIndex()] = e.value;
			
			// Event associated Value
			int index = e.getControlIndex();
			if(index < 4){
				controller_array[index]   =	e.getSource().getAxisValue(index);
			}
		}
		else if(e.isButton()){
			int index = e.getControlIndex();
			if(e.getSource().isButtonPressed(index)){
				
				controller_array[index+4]   = 1;
			}
			
		}
		else{
			controller_array[23] =(int) e.getSource().getPovX() ;
			controller_array[24] =-(int)e.getSource().getPovY(); 
		}
	}
	public float[] getJsData(){
		float[] outPut 	= controller_array.clone();
		int index 		= 0;
		for(float item : controller_array){
			if(index == 23){break;};
			if(index > 3){
				controller_array[index] = 0;
			}
			
			index++;
		}
		return outPut;
	}
	
}// class
