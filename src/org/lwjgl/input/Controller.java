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
 * A game controller of some sort that will provide input. The controller
 * presents buttons and axes. Buttons are either pressed or not pressed. Axis
 * provide analogue values.
 * 
 * @author Kevin Glass --- javadoc comments extended by Martin Wischenbart for http://sourceforge.net/projects/ffjoystick4java/ 
 */
public interface Controller {
	/**
	 * Gets the name assigned to this controller.
	 * 
	 * @return the name assigned to this controller
	 */
	public String getName();

	/**
	 * Gets the index of this controller in the collection.
	 * 
	 * @return the index of this controller in the collection
	 */
	public int getIndex();
	
	/**
	 * Retrieve the number of buttons available on this controller.
	 * 
	 * @return the number of buttons available on this controller
	 * @see at.wisch.joystick.AdvancedController#getPovCount()
	 * @see #getAxisCount()
	 * @see at.wisch.joystick.AdvancedController#getBallCount()
	 */
	public int getButtonCount();
	
	/**
	 * Get the name of the specified button.
	 * 
	 * @param buttonIndex the index of the button whose name should be retrieved (button indices start at 0)
	 * 
	 * @return the name of the button requested (typically buttonIndex+1)
	 * @see #getButtonCount()
	 */
	public String getButtonName(int buttonIndex);
	
	/**
	 * Check if a button is currently pressed
	 * 
	 * @param buttonIndex the index of the button to check
	 * @return true if the button is currently pressed, false otherwise
	 */
	public boolean isButtonPressed(int buttonIndex);
	
	/**
	 * Poll the controller for new data. Call this method before you query for inputs from a controller.
	 */
	public void poll();
	
	/** 
	 * Gets the X-Axis value of the first POV on this controller as a float.
	 * 
	 * @return the X-Axis value of the first POV on this controller as float
	 */
	public float getPovX();
	
	/** 
	 * Gets the Y-Axis value of the first POV on this controller as a float.
	 * 
	 * @return the Y-Axis value of the first POV on this controller as float
	 */
	public float getPovY();
	
	/**
	 * Get the dead zone for a specified axis.
	 * 
	 * @param axisIndex the index of the axis for which to retrieve the dead zone (axis indices start with 0)
	 * @return the dead zone for the specified axis: a value from 0 to AXIS_MAXIMUM (1f)
	 * 
	 * @see #setDeadZone(int, float)
	 */
	public float getDeadZone(int axisIndex);
	
	/**
	 * Set the dead zone for the specified axis. Analogue joysticks generally don't tend to return exactly to 0 once you stop holding the stick. Therefore it might seem that you are moving the direction stick, while in fact you are not. The dead zone is a "region" around the center where input is ignored. For example if you set the dead zone to 0.1f the values from -0.1f to 0.1f will be ignored (getAxisValue will return values from -1f to -0.1f, 0f or values from 0.1f to 1f). 
	 * 
	 * @param axisIndex the index of the axis for which to set the dead zone (axis indices start with 0)
	 * @param zone the dead zone to use for the specified axis: a value from 0 to AXIS_MAXIMUM (1f)
	 * 
	 * @see #getDeadZone(int)
	 */
	public void setDeadZone(int axisIndex, float zone);
	
	/**
	 * Retrieve the number of axes available on this controller. Axes can are analogue inputs: their values can be anything from AXIS_MINIMUM to AXIS_MAXIMUM (-1f to 1f)
	 * 
	 * @return the number of axes available on this controller
	 * @see #getButtonCount()
	 * @see at.wisch.joystick.AdvancedController#getPovCount()
	 * @see at.wisch.joystick.AdvancedController#getBallCount()
	 */
	public int getAxisCount();
	
	/**
	 * Get the name that's given to the specified axis 
	 * 
	 * @param axisIndex The index of the axis whose name should be retrieved (axis indices start with 0)
	 * @return the name of the specified axis: for the first axes that is "X", "Y", "Z", "rX", "rY", "rZ", "U", "V", beyond that it's axisIndex+1
	 * 
	 * @see #getAxisCount()
	 */
	public String getAxisName(int axisIndex);
	
	/**
	 * Retrieve the value thats currently available on a specified axis. If the axis does not exist, AXIS_NEUTRAL (0f) will be
	 * returned.
	 * 
	 * It may be useful to get the player to wiggle the joystick
	 * from side to side to get the calibration right. 
	 * 
	 * @param axisIndex the index of axis to be read (axis indices start with 0)
	 * @return the value from the specified axis: AXIS_NEUTRAL, AXIS_MINIMUM, AXIS_MAXIMUM or
	 *         anything between AXIS_MINIMUM and AXIS_MAXIMUM (anything from -1f to 1f)
	 */
	public float getAxisValue(int axisIndex);
	
	/**
	 * Get the value from the X axis if there is one. If no X axis is 
	 * defined AXIS_NEUTRAL will be returned.
	 * 
	 * @return the value from the X axis: AXIS_NEUTRAL, AXIS_MINIMUM, AXIS_MAXIMUM or
	 *         anything between AXIS_MINIMUM and AXIS_MAXIMUM (anything from -1f to 1f)
	 *         
	 * @see #getAxisValue(int)
	 */
	public float getXAxisValue();
	
	/**
	 * Get the dead zone for the X axis.
	 * 
	 * @return the dead zone for the X axis
	 * @see #setXAxisDeadZone(float)       
	 * @see #getDeadZone(int)
	 */
	public float getXAxisDeadZone();
	
	/**
	 * Set the dead zone for the X axis. Typical values are between 0.01f and
	 * 0.2f.
	 * 
	 * @param zone
	 *            the dead zone to use for the X axis: a value from 0f to AXIS_MAXIMUM (1f)
	 *   @see #getXAxisDeadZone()       
	 * @see #setDeadZone(int, float)	
	 * 
	 */
	public void setXAxisDeadZone(float zone);

	/**
	 * Get the value from the Y axis if there is one. If no Y axis is 
	 * defined AXIS_NEUTRAL will be returned.
	 * 
	 * @return the value from the Y axis: AXIS_NEUTRAL, AXIS_MINIMUM, AXIS_MAXIMUM or
	 *         anything between AXIS_MINIMUM and AXIS_MAXIMUM (anything from -1f to 1f)
	 * @see #getAxisValue(int)         
	 */
	public float getYAxisValue();
	
	/**
	 * Get the dead zone for the Y axis.
	 * 
	 * @return the dead zone for the Y axis
	 * @see #setYAxisDeadZone(float)       
	 * @see #getDeadZone(int)
	 */
	public float getYAxisDeadZone();

	/**
	 * Set the dead zone for the Y axis. Typical values are between 0.01f and
	 * 0.2f.
	 * 
	 * @param zone
	 *            the dead zone to use for the Y axis: a value from 0f to AXIS_MAXIMUM (1f)
* 		@see #getYAxisDeadZone()       
	 * @see #setDeadZone(int, float)	
	 */
	
	public void setYAxisDeadZone(float zone);
	
	/**
	 * Get the value from the Z axis if there is one. If no Z axis is 
	 * defined AXIS_NEUTRAL will be returned.
	 * 
	 * @return the value from the Z axis: AXIS_NEUTRAL, AXIS_MINIMUM, AXIS_MAXIMUM or
	 *         anything between AXIS_MINIMUM and AXIS_MAXIMUM (anything from -1f to 1f)
	 *         
	 * @see #getAxisValue(int)        
	 */
	public float getZAxisValue();
	
	/**
	 * Get the dead zone for the Z axis.
	 * 
	 * @return the dead zone for the Z axis
	 * @see #setZAxisDeadZone(float)       
	 * @see #getDeadZone(int)
	 */
	public float getZAxisDeadZone();

	/**
	 * Set the dead zone for the Z axis. Typical values are between 0.01f and
	 * 0.2f.
	 * 
	 * @param zone
	 *            the dead zone to use for the Z axis: a value from 0f to AXIS_MAXIMUM (1f)
     * @see #getZAxisDeadZone()       
	 * @see #setDeadZone(int, float)            
	 *            
	 */
	public void setZAxisDeadZone(float zone);
	
	/**
	 * Get the value from the RX axis if there is one. If no RX axis is 
	 * defined AXIS_NEUTRAL will be returned.
	 * 
	 * @return the value from the RX axis: AXIS_NEUTRAL, AXIS_MINIMUM, AXIS_MAXIMUM or
	 *         anything between AXIS_MINIMUM and AXIS_MAXIMUM (anything from -1f to 1f)
	 * @see #getAxisValue(int)
	 */
	public float getRXAxisValue();
	
	/**
	 * Get the dead zone for the RX axis.
	 * 
	 * @return the dead zone for the RX axis
	 * @see #setRXAxisDeadZone(float)       
	 * @see #getDeadZone(int)
	 */
	public float getRXAxisDeadZone();

	/**
	 * Set the dead zone for the RX axis. Typical values are between 0.01f and
	 * 0.2f.
	 * 
	 * @param zone
	 *            the dead zone to use for the RX axis: a value from 0f to AXIS_MAXIMUM (1f)
	 * @see #getRXAxisDeadZone()       
	 * @see #setDeadZone(int, float)            
	 */
	public void setRXAxisDeadZone(float zone);
	
	/**
	 * Get the value from the RY axis if there is one. If no RY axis is 
	 * defined AXIS_NEUTRAL will be returned.
	 * 
	 * @return the value from the RY axis: AXIS_NEUTRAL, AXIS_MINIMUM, AXIS_MAXIMUM or
	 *         anything between AXIS_MINIMUM and AXIS_MAXIMUM (anything from -1f to 1f)
	 * @see #getAxisValue(int)
	 */
	public float getRYAxisValue();
	
	/**
	 * Get the dead zone for the RY axis.
	 * 
	 * @return the dead zone for the RY axis
	 * @see #setRYAxisDeadZone(float)       
	 * @see #getDeadZone(int)
	 */
	public float getRYAxisDeadZone();

	/**
	 * Set the dead zone for the RY axis. Typical values are between 0.01f and
	 * 0.2f.
	 * 
	 * @param zone
	 *            the dead zone to use for the RY axis: a value from 0f to AXIS_MAXIMUM (1f)
	 * @see #getRYAxisDeadZone()       
	 * @see #setDeadZone(int, float)            
	 */
	public void setRYAxisDeadZone(float zone);
	
	/**
	 * Get the value from the RZ axis if there is one. If no RZ axis is 
	 * defined AXIS_NEUTRAL will be returned.
	 * 
	 * @return the value from the RZ axis: AXIS_NEUTRAL, AXIS_MINIMUM, AXIS_MAXIMUM or
	 *         anything between AXIS_MINIMUM and AXIS_MAXIMUM (anything from -1f to 1f)
	 *         
	 * @see #getAxisValue(int)
	 */
	public float getRZAxisValue();
	
	/**
	 * Get the dead zone for the RZ axis.
	 * 
	 * @return the dead zone for the RZ axis
 	 * @see #setRZAxisDeadZone(float)       
	 * @see #getDeadZone(int)            

	 */
	public float getRZAxisDeadZone();

	/**
	 * Set the dead zone for the RZ axis. Typical values are between 0.01f and
	 * 0.2f.
	 * 
	 * @param zone
	 *            the dead zone to use for the RZ axis: a value from 0f to AXIS_MAXIMUM (1f)
	 * @see #getRZAxisDeadZone()       
	 * @see #setDeadZone(int, float)            
	 */
	public void setRZAxisDeadZone(float zone);
}
