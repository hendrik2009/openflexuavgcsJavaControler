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

/**
 * The Class FeatureNotSupportedEventManager. This class keeps a collection of FeatureNotSupportedEventListeners. FeatureNotSupportedEventListeners can be added and removed, and if a FeatureNotSupportedEvent occurs, all the registered listeners are called back.
 * 
 * @see FeatureNotSupportedEventListener
 * @see FeatureNotSupportedEvent 
 * 
 * @author Martin Wischenbart
 */
public class FeatureNotSupportedEventManager {
	
	private static ArrayList<FeatureNotSupportedEventListener> listeners;
	
	static {
		listeners = new ArrayList<FeatureNotSupportedEventListener>();
	}
	
	
	/**
	 * Adds a FeatureNotSupportedEventListener to the collection.
	 * 
	 * @param listener
	 *            the FeatureNotSupportedEventListener
	 * 
	 * @return true, if successful. false, if adding was not successful.
	 *
	 *@see FeatureNotSupportedEventListener
	 */
	public static boolean addFeatureNotSupportedEventListener(FeatureNotSupportedEventListener listener) {
		return listeners.add(listener);
	}

	/**
	 * Removes the FeatureNotSupportedEventListener from the collection.
	 * 
	 * @param listener
	 *            the FeatureNotSupportedEventListener
	 * 
	 * @return true, if successful. false, if removing was not successful.
	 *
	 *@see FeatureNotSupportedEventListener
	 */
	public static boolean removeFeatureNotSupportedEventListener (FeatureNotSupportedEventListener listener) {
		return listeners.remove(listener);
	}
	
	/**
	 * Invokes the callback method for all registered listeners.
	 * 
	 * @param event
	 *            the FeatureNotSupportedEvent
	 *            
	 * @see FeatureNotSupportedEventListener#featureNotSupportedEventOccured(FeatureNotSupportedEvent)           
	 */
	public static void featureNotSupportedEventOccured(FeatureNotSupportedEvent event){
		Iterator<FeatureNotSupportedEventListener> iter = listeners.iterator();
		while (iter.hasNext()) {
			FeatureNotSupportedEventListener listener = iter.next();
			listener.featureNotSupportedEventOccured(event);
		}
	}
}
