/**
 *   ForceFeedback Joystick Driver for Java
 *   http://sourceforge.net/projects/ffjoystick4java/
 *
 *   -----------------------------------------------------------------------------
 * 
 *   Copyright (c) 2010, Martin Wischenbart
 *   All rights reserved.
 *   
 *   Redistribution and use in source and binary forms, with or without
 *   modification, are permitted provided that the following conditions are met:
 *    * Redistributions of source code must retain the above copyright
 *      notice, this list of conditions and the following disclaimer.
 *    * Redistributions in binary form must reproduce the above copyright
 *      notice, this list of conditions and the following disclaimer in the
 *      documentation and/or other materials provided with the distribution.
 *    * Neither the name of the ForceFeedback Joystick Driver for Java nor the
 *      names of its contributors may be used to endorse or promote products
 *      derived from this software without specific prior written permission.
 *      
 *   THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 *   AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 *   IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 *   ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 *   LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *   CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 *   SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 *   INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 *   CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 *   ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 *   POSSIBILITY OF SUCH DAMAGE.
 *   
 *   -----------------------------------------------------------------------------
 *   
 *   If you have any suggestions, or if you want to report a bug, please
 *   see http://sourceforge.net/projects/ffjoystick4java/ or contact me
 *   via email.
 *   
 *   Martin Wischenbart
 *   elboato@users.sourceforge.net
 *   
 */


import java.util.ArrayList;
import at.wisch.joystick.*;
import at.wisch.joystick.event.*;
import at.wisch.joystick.exception.*;
import at.wisch.joystick.test.*;

/**
 * The Class JoystickInputDemo. It demonstrates how to use this driver for reading inputs.<br><br>Look at the source code to get to know how input works.
 * 
 * @see JoystickForceDemo
 * 
 * @author Martin Wischenbart
 */
public class JoystickInputDemo implements ControllerEventListener{

	private static JoystickInputDemo jsInputDemo;

	private static ArrayList<Joystick> joysticks;
	private static Joystick joystick;
	
	
	//MAIN
	/**
	 * The main method.
	 * 
	 * @param args
	 *            (no args needed)
	 *            
	 */
	public static void main(String[] args) {
		System.out.println("Initializing JoystickManager and Joysticks...");
		
		jsInputDemo = new JoystickInputDemo();
		
		// add a ControllerEventListener - so later we will be notified of events
		ControllerEventManager.addControllerEventListener(jsInputDemo);
				
		// loop until an exit condition is met - so the program won't close
		waitForExitCondition();
		
		// do a manual poll and output the state (just to show how it's done)
		// (usually we prefer working with events)
		pollForCurrentStateAndOutput();
		
		closeProgram();

	}

	//CONSTRUCTOR
	private JoystickInputDemo() {
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
	}
	

	private static void waitForExitCondition(){
		
		System.out.println("Press the first two buttons on any joystick at once for 1 second\nto exit the program.");
		
		boolean running = true;
		
		do {
			
			try {
				Thread.sleep(1000); // check once every second -> wait 1000ms
			} catch (InterruptedException e) {
				e.printStackTrace();
				}
			
			//check if buttons 0 and 1 are pressed at the same time on any joystick
			for (int i = 0; i < joysticks.size(); i++) {
				joystick = joysticks.get(i);
				joystick.poll();
				if (joystick.isButtonPressed(0) && joystick.isButtonPressed(1)) {
					System.out.println("Buttons 0 and 1 were pressed at the same time on\njoystick "+joystick.getIndex()+". Closing program.");
					running = false;
				}
			}
					
		} while (running );
		
		
		
	}

	private static void pollForCurrentStateAndOutput(){
		
		// we just do that once to show how polling manually is done
		
		joystick.poll();
		
			String buttons = "";
			for (int b = 0; b < joystick.getButtonCount(); b++) {
				if (joystick.isButtonPressed(b)){
					buttons = buttons + " " + joystick.getButtonName(b);
				}

			}
			String axes = " ";
			float f;
			for (int a = 0; a < joystick.getAxisCount(); a++) {
				f = joystick.getAxisValue(a);
				axes = axes+joystick.getAxisName(a)+": "+f+"\n ";

			}
			String povs = "";
			for (int p = 0; p < joystick.getPovCount(); p++) {
				povs = povs+"POV"+joystick.getPovName(p)+": "+joystick.getPovDirection(p)+"\n";

			}
			System.out.println(
					"\nThe current state of "
					+joystick.getName()+":\n"
					+axes
					+povs
					+" Buttons: "+buttons+"\n");

	}
	
	
	private static void closeProgram() {
		// don't forget to call this method
		System.out.println("Closing program.");
		JoystickManager.close();
	}



	// callback method when an event occured on a joystick.
	/* (non-Javadoc)
	 * @see at.wisch.joystick.event.ControllerEventListener#controllerEventOccured(at.wisch.joystick.event.AdvancedControllerEvent)
	 */
	@Override
	public void controllerEventOccured(AdvancedControllerEvent event) {
		
		// we simply output it. but there are different classes of Events - 
		// we could also distinguish between them and do more advanced stuff.
		System.out.println(event);
		

	}

}

