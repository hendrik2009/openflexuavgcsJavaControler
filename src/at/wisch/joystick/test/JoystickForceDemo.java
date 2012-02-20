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



package at.wisch.joystick.test;

import java.util.ArrayList;
import at.wisch.joystick.*;
import at.wisch.joystick.event.*;
import at.wisch.joystick.exception.*;
import at.wisch.joystick.ffeffect.*;

/**
 * The Class JoystickForceDemo. It demonstrates how to use this driver for playing force feedback effects. Look at the source code to get to know how force feedback works.
 * 
 * @see JoystickInputDemo
 * 
 * @author Martin Wischenbart
 */
public class JoystickForceDemo implements FeatureNotSupportedEventListener {

	private static ArrayList<FFJoystick> joysticks;
	
	static JoystickForceDemo jsForceDemo;
	
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
		
		JoystickForceDemo jsForceDemo = new JoystickForceDemo();
		
		/* add a  FeatureNotSupportedEventListener - this is strongly recommended during
		 development! It will tell us if we try to access an axis/button/POV/trackball
		 that does not exist. More importantly it also tells us if we try to create
		 an effect with illegal parameters, or if we try to play an effect that is not
		 supported. The effect will not be played or with different properties and
		 without this listener we won't know why. */
		FeatureNotSupportedEventManager.addFeatureNotSupportedEventListener(jsForceDemo);
		
		// play one effect on each connected FF controller
		playEffects();
		
		closeProgram();
		

	}

	//CONSTRUCTOR
	private JoystickForceDemo() {
		// init and get-joystick methods have to be done within a try-catch-block
		// (these are fatal errors and we need to deal with them)
		try {
			JoystickManager.init();
			joysticks = JoystickManager.getAllFFJoysticks();

		} catch (FFJoystickException e) {
			e.printErrorMessage();
		}
		if (joysticks.isEmpty()){
			System.out.println("No controllers found...");
			System.exit(0);
		}
	}

	private static void playEffects() {
		/* no more try-catch necessary here. if these methods fail they will, 
		   depending on the kind of error, create a FeatureNotSupportedEvent
		   or simply not work */
		   
		System.out.println("Iterating over all force-feedback joysticks...");
		
		FFJoystick js;
		Effect eff;
		
		for (int i = 0; i < joysticks.size(); i++) {
			js = joysticks.get(i);
			
			System.out.println("Joystick "+js.getIndex()+ " ("+js.getName()+"):");
			System.out.println("Supported effects: "+js.getSupportedEffects());

			eff = js.getSimpleEffect();
			System.out.println(" creating effect...");
			if (eff == null) {
				System.out.println("ERROR");
			} else {
				eff.setEffectLength(3000); // 3 seconds
				eff.setStrength(Effect.MAX_LEVEL); // full strength
				
				System.out.println(" uploading effect...");
				//upload the effect to the joystick
				js.newEffect(eff);
				
				System.out.println(" playing effect...");
				//play the effect 1 time
				js.playEffect(eff, 1);
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
		}
	}

	private static void closeProgram() {
		// don't forget to call this method
		System.out.println("Closing program.");
		JoystickManager.close();
	}

	
	
	/* output errors that are not fatal. if these happen e.g. during a game, there
	 is no need for output or stopping. but during development we should always
	 be aware that something went wrong! */ 
	/* (non-Javadoc)
	 * @see at.wisch.joystick.event.FeatureNotSupportedEventListener#featureNotSupportedEventOccured(at.wisch.joystick.event.FeatureNotSupportedEvent)
	 */
	@Override
	public void featureNotSupportedEventOccured(FeatureNotSupportedEvent event) {
		System.out.println(event);
	}

}

