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

import org.lwjgl.input.Controller;

/**
 * The Class ControllerButtonEvent. If this event occurs, it means that a button was pressed or released.
 * 
 * @see ControllerEventManager
 * 
 * @author Martin Wischenbart
 */
public class ControllerButtonEvent extends AdvancedControllerEvent {

	private boolean buttonState;
	
	/**
	 * Instantiates a new ControllerButtonEvent.
	 * 
	 * @param source
	 *            the Controller that generated the event
	 * @param timeStamp
	 *            the timestamp given for this event
	 * @param buttonIndex
	 *            the index of the button that changed its state
	 * @param buttonState
	 *            the button state (true = pressed, false = released)
	 */
	public ControllerButtonEvent(Controller source, long timeStamp, 
			int buttonIndex, boolean buttonState) {
		super(source, timeStamp, BUTTON, buttonIndex, false, false);
		this.buttonState = buttonState;
	}

	/**
	 * Gets the button state (true = pressed, false = released). Use getControlIndex() to get the button index.
	 * 
	 * @return true, the if the button is now pressed. false, if it is now released.
	 * 
	 * @see #getControlIndex()
	 */
	public boolean getButtonState() {
		return buttonState;
	}

	/* (non-Javadoc)
	 * @see org.lwjgl.input.ControllerEvent#toString()
	 */
	/**
	 * Returns a String describing the ControllerButtonEvent. This String contains the Joystick index, the button index and the new button state. 
	 * 
	 * @return a String describing the ControllerButtonEvent
	 */
	@Override
	public String toString() {
		return ("JS# "+source.getIndex()+" Button "+index+" is now "+((buttonState)?("pressed"):("released")));
	}
	
}
