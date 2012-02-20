/* 
 * Copyright (c) 2002-2008 LWJGL Project
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are 
 * met:
 * 
 * * Redistributions of source code must retain the above copyright 
 *   notice, this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 * * Neither the name of 'LWJGL' nor the names of 
 *   its contributors may be used to endorse or promote products derived 
 *   from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR 
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, 
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, 
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR 
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING 
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.lwjgl.input;

/**
 * An event occuring on a controller.
 * 
 * @author Kevin Glass --- javadoc comments extended and modifiers added by Martin Wischenbart for
 *         http://sourceforge.net/projects/ffjoystick4java/
 */
public abstract class ControllerEvent {
	
	/**
	 * Indicates the event was caused by a button: the object is a ControllerButtonEvent (casting is safe).
	 */
	public static final int BUTTON = 1;
	
	/**
	 * Indicates the event was caused by an analogue axis: the object is a ControllerAxisEvent (casting is safe).
	 */
	public static final int AXIS = 2;
	
	/**
	 * Indicates the event was caused by a POV's X axis: the object is a ControllerPovEvent (casting is safe).
	 */
	public static final int POVX = 3;
	
	/**
	 * Indicates the event was caused by a POV's Y axis: the object is a ControllerPovEvent (casting is safe).
	 */
	public static final int POVY = 4;
	
	/** The Controller that generated the event */
	protected Controller source;
	/** The index of the input (button, POV, axis, trackball) that generated the event */
	protected int index;
	/** Type of control that generated the event: AXIS, BUTTON, POVX, POVY, POV or BALL*/
	private int type;
	/** True if this event was caused by the x axis */
	protected boolean xaxis;
	/** True if this event was caused by the y axis */
	protected boolean yaxis;
	/** The time stamp of this event */
	private long timeStamp;
	
	/**
	 * Create a new event.
	 * 
	 * @param source
	 *            the Controller that generated the event
	 * @param timeStamp
	 *            the timestamp given for this event
	 * @param type
	 *            the type of control generating this event (AXIS, BUTTON, POV, POVX, POVY or BALL)
	 * @param index
	 *            the index of the input that generated the event (e.g. axis index, button index, ...)
	 * @param xaxis
	 *            true if this event was caused by the X-axis. false, otherwise.
	 * @param yaxis
	 *            True if this event was caused by the Y-axis. false, otherwise.
	 */
	protected ControllerEvent(Controller source,long timeStamp, int type,int index,boolean xaxis,boolean yaxis) {
		this.source = source;
		this.timeStamp = timeStamp;
		this.type = type;
		this.index = index;
		this.xaxis = xaxis;
		this.yaxis = yaxis;
	}
	
	/**
	 * Get the time stamp for this event. This value
	 * is used to give the events an order.
	 * 
	 * @return he timestamp given for this event
	 */
	public long getTimeStamp() {
		return timeStamp;
	}
	
	/**
	 * Get the Controller that generated this event.
	 * 
	 * @return the Controller object that generated this event
	 */
	public Controller getSource() {
		return source;
	}
	
	/**
	 * Get the index of the control generating this event.
	 * 
	 * @return the index of the control generating this event (e.g. axis index, button index, ...)
	 */
	public int getControlIndex() {
		return index;
	}
	
	/**
	 * Check if this event was generated by a button.
	 * 
	 * @return true, if this event was generated by a button. false, otherwise.
	 */
	public boolean isButton() {
		return type == BUTTON;
	}

	/**
	 * Check if this event was generated by an axis.
	 * 
	 * @return true, if this event was generated by an axis. false, otherwise.
	 */
	public boolean isAxis() {
		return type == AXIS;
	}

	/**
	 * Check if this event was generated by a POV's X axis.
	 * 
	 * @return true, if this event was generated by a POV's X axis. false, otherwise.
	 */
	public boolean isPovX() {
		return type == POVX;
	}
	
	/**
	 * Check if this event was generated by a POV's Y axis.
	 * 
	 * @return true, if this event was generated by a POV's Y axis. false, otherwise.
	 */
	public boolean isPovY() {
		return type == POVY;
	}
	
	/**
	 * Check if this event was caused by the X axis.
	 * 
	 * @return true, if this event was caused by the X axis. false, otherwise.
	 */
	public boolean isXAxis() {
		return xaxis;
	}

	/**
	 * Check if this event was caused by the Y axis.
	 * 
	 * @return true, if this event was caused by the Y axis. false, otherwise.
	 */
	public boolean isYAxis() {
		return yaxis;
	}
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	/**
	 * Returns a String describing the ControllerEvent. This String contains: the source Controller, type constant and booleans xaxis and yaxis. 
	 * 
	 * @return a String describing the ControllerEvent
	 */
	public String toString() {
		return "["+source+" type="+type+" xaxis="+xaxis+" yaxis="+yaxis+"]";
	}
}
