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


package at.wisch.joystick.event;

import java.util.Iterator;
import java.util.ArrayList;

import at.wisch.joystick.JoystickManager;
import at.wisch.joystick.exception.*;

/**
 * The Class ControllerEventManager. This class keeps a collection of ControllerEventListeners. ControllerEventListeners can be added and removed, and if a ControllerEvent occurs, all the registered listeners are called back.<br>
 * <br>
 * If we add a ControllerEventListener and automatic event polling is not enabled yet, it will be enabled. If we remove the last ControllerEventListener, automatic event polling will be disabled.
 * 
 *  @author Martin Wischenbart
 */
public class ControllerEventManager {
	
	private static ArrayList<ControllerEventListener> listeners;
	
	static {
		listeners = new ArrayList<ControllerEventListener>();
	}
	
	/**
	 * Adds a ControllerEventListener to the collection.
	 * 
	 * @param listener
	 *            the ControllerEventListener
	 * 
	 * @return true, if successful. false, if adding was not successful or if event polling could not be enabled.
	 *
	 *@see ControllerEventListener
	 */
	public static boolean addControllerEventListener(ControllerEventListener listener){
		// if it is empty now (we are inserting the first listener) we start to poll
		if (listeners.isEmpty())
			try {
				JoystickManager.enableJoystickEventPolling();
			} catch (FFJoystickException e) {
				e.printStackTrace();
				return false;
			}
		return listeners.add(listener);
	}
	
	/**
	 * Removes the ControllerEventListener from the collection.
	 * 
	 * @param listener
	 *            the ControllerEventListener
	 * 
	 * @return true, if successful. false, if removing was not successful or if event polling could not be disabled.
	 *
	 *@see ControllerEventListener
	 */
	public static boolean removeControllerEventListener(ControllerEventListener listener){
		boolean returnValue = listeners.remove(listener);
		// if it is empty now, we don't need to poll anymore....
		if (listeners.isEmpty())
			try {
				JoystickManager.disableJoystickEventPolling();
			} catch (FFJoystickException e) {
				e.printStackTrace();
				return false;
			}
		return returnValue; 
	}
	
	/**
	 * Invokes the callback method for all registered listeners.
	 * 
	 * @param event
	 *            the AdvancedControllerEvent
	 *            
	 * @see ControllerEventListener#controllerEventOccured(AdvancedControllerEvent)           
	 */
	public static void controllerEventOccured(AdvancedControllerEvent event){
		Iterator<ControllerEventListener> iter = listeners.iterator();
		while (iter.hasNext()) {
			ControllerEventListener listener = iter.next();
			listener.controllerEventOccured(event);
		}
	}
}
