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



package at.wisch.joystick;

import at.wisch.joystick.event.*;
import at.wisch.joystick.exception.*;


/**
 * The class Joystick. This is the class that actually represents Joystick
 * objects, which are necessary for recieving input from a Joystick. <br>
 * <br>
 * Attention: do not instantiate Joystick objects yourself. Aquire Joysticks
 * using {@link JoystickManager}.
 * 
 * @author Martin Wischenbart
 */
public class Joystick extends AbstractController {


	private int joystickIndex;
	private String joystickName;
	private int noOfButtons;
	private int noOfPovs;
	private int noOfAxes;
	private int noOfBalls;

	protected boolean isFFJoystick;
	
	private float axisDeadZone[]; //size: noOfAxes
	private boolean invertAxis[]; // size: noOfAxes
	private boolean previousAxisValueZero[]; // size: noOfAxes
	private boolean invertPov[][]; // size: noOfPovs x 2
	private boolean invertBall[][]; // size: noOfBalls x 2

	/*
	 * This constructor should not be called by the application programmer.
	 * To obtain a joystick object use JoystickMangager.getJoystick();
	 */
	protected Joystick(int joystickIndex) throws FFJoystickException {
		
		super();
		
		this.joystickIndex = joystickIndex;
		
		this.joystickName = getJoystickName(joystickIndex);
		this.noOfButtons = getNoOfButtons(joystickIndex);
		this.noOfPovs = getNoOfPovs(joystickIndex);
		this.noOfAxes = getNoOfAxes(joystickIndex);
		this.noOfBalls = getNoOfBalls(joystickIndex);
		
		this.isFFJoystick = false;

		axisDeadZone = new float[noOfAxes];
		invertAxis = new boolean[noOfAxes]; // size: noOfAxes
		previousAxisValueZero = new boolean[noOfAxes]; // size: noOfAxes
		for (int a = 0; a < noOfAxes; a++) {
			axisDeadZone[a] = AXIS_DEFAULT_DEAD_ZONE;
			invertAxis[a] = false;
			previousAxisValueZero[a] = true;
		}

		invertPov = new boolean[noOfPovs][2]; // size: noOfPovs x 2
		for (int p = 0; p < noOfPovs; p++) {
			invertPov[p][0] = false;
			invertPov[p][1] = false;
		}

		invertBall = new boolean[noOfBalls][2]; // size: noOfBalls x 2
		for (int b = 0; b < noOfBalls; b++) {
			invertBall[b][0] = false;
			invertBall[b][1] = false;
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	/**
	 * Gets a string containing the joystick's name and the description of the input capabilities.
	 * 
	 * @return the joystick's name and a the description of the input capabilities
	 * @see #getName()
	 * @see #getDescription()
	 */
	@Override
	public String toString() {
		return (getName()+" ("+getDescription()+")");
	}

	
	
	// ####################################### JOYSTICK PROPERTIES ##########################################

	
	private static native String getJoystickName(int joystickIndex) throws FFJoystickException ;
	private static native int getNoOfButtons(int joystickIndex) throws FFJoystickException ;
	private static native int getNoOfPovs(int joystickIndex) throws FFJoystickException ;
	private static native int getNoOfAxes(int joystickIndex) throws FFJoystickException ;
	private static native int getNoOfBalls(int joystickIndex) throws FFJoystickException ;
	
	// ############################################# BASIC ############################################

	/* (non-Javadoc)
	 * @see org.lwjgl.input.Controller#getIndex()
	 */
	/**
	 * {@inheritDoc}
	 * @see #getDescription()
	 */
	@Override
	public int getIndex() {
		return joystickIndex;
	}

	/* (non-Javadoc)
	 * @see org.lwjgl.input.Controller#getName()
	 */
	/**
	 * {@inheritDoc}
	 * @see #toString()
	 */
	@Override
	public String getName() {
		return joystickName;
	}

	/* (non-Javadoc)
	 * @see at.wisch.joystick.AdvancedController#isFFJoystick()
	 */
	/**
	 * Checks if the device supports force feedback (FF).<br>
	 * <br>
	 * If this method returns true, it is safe to cast the Joystick to an {@link FFJoystick}.
	 * 
	 * {@inheritDoc}
	 * @see FFJoystick
	 */
	@Override
	public boolean isFFJoystick() {
		return isFFJoystick;
	}

	/**
	 * Gets a short description of the joystick's capabilities for input. This includes the joystick index, number of buttons, number of POVs, number of axes and number of trackballs.
	 * 
	 * @return the String containing the joystick's capabilities for input
	 * 
	 * @see #toString()
	 * @see #getIndex()
	 * @see #getButtonCount()
	 * @see #getPovCount()
	 * @see #getAxisCount()
	 * @see #getBallCount()
	 */
	public String getDescription() {
		return ("#"+joystickIndex+", "+noOfButtons+" buttons, "+noOfPovs+" POVs, "+noOfAxes+" axes, "+noOfBalls+" trackballs");
	}

	/* (non-Javadoc)
	 * @see org.lwjgl.input.Controller#poll()
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * Note that direcly after initialization polling does not work yet. You have press or move something on the controller first. 
	 */
	@Override
	public native void poll();


	// ############################################ BUTTONS ###########################################


	/* (non-Javadoc)
	 * @see org.lwjgl.input.Controller#getButtonCount()
	 */
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getButtonCount() {
		return noOfButtons;
	}

	/* (non-Javadoc)
	 * @see org.lwjgl.input.Controller#isButtonPressed(int)
	 */
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isButtonPressed(int buttonIndex)  {
		if (buttonIndex > this.getButtonCount() || buttonIndex < 0) {
			createFeatureNotSupportedEvent(AdvancedControllerEvent.BUTTON, buttonIndex);
			return false;
		}
		return isButtonPressedNative(joystickIndex, buttonIndex);
	}

	private static native boolean isButtonPressedNative(int joystickIndex, int buttonIndex);


	// ############################################## POVS #############################################


	
	/* (non-Javadoc)
	 * @see at.wisch.joystick.AdvancedController#getPovCount()
	 */
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getPovCount() {
		return noOfPovs;
	}

	
	/* (non-Javadoc)
	 * @see at.wisch.joystick.AdvancedController#getPovValueX(int)
	 */
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getPovX(int povIndex)  {
		if (povIndex > this.getPovCount() || povIndex < 0) {
			createFeatureNotSupportedEvent(AdvancedControllerEvent.POV, povIndex);
			return POV_AXIS_NEUTRAL;
		} else {
			return getInvertedPov(povIndex, 0, getPovValueXNative(this.joystickIndex, povIndex));
		}
	}
	
	
	/* (non-Javadoc)
	 * @see at.wisch.joystick.AdvancedController#getPovValueY(int)
	 */
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getPovY(int povIndex)  {
		if (povIndex > this.getPovCount() || povIndex < 0) {
			createFeatureNotSupportedEvent(AdvancedControllerEvent.POV, povIndex);
			return POV_AXIS_NEUTRAL;
		} else {
			return getInvertedPov(povIndex, 1, getPovValueYNative(this.joystickIndex, povIndex));
		}
	}


	private native int getPovValueXNative (int joystickIndex, int povIndex);
	private native int getPovValueYNative (int joystickIndex, int povIndex);



	//inversion does not affect the value of direction
	//inversion only affects POVs if the axes are accessed separately
	
	/* (non-Javadoc)
	 * @see at.wisch.joystick.AdvancedController#getPovDirection(int)
	 */
	/**
	 * {@inheritDoc}<br>
	 * <br>
	 * Attention: {@link Joystick#setInvertPov(int, int, boolean)} does not affect the direction, but only the return value of {@link #getPovX(int)} and {@link #getPovY(int)}
	 */
	@Override
	public int getPovDirection(int povIndex)  {
		if (povIndex > this.getPovCount() || povIndex < 0) {
			createFeatureNotSupportedEvent(AdvancedControllerEvent.POV, povIndex);
			return POV_CENTERED;
		}
		return getPovDirectionNative(joystickIndex, povIndex);
	}

	private native int getPovDirectionNative (int joystickIndex, int povIndex);

	private int getInvertedPov(int povIndex, int xORy, int value)  {
		if (getInvertPov(povIndex,xORy)) value*=-1;
		return value;
	}

	/**
	 * Check if a POV's axis is being inverted.
	 * 
	 * @param povIndex
	 *            the POV's index
	 * @param xORy
	 *            0 for X, 1 for Y
	 * 
	 * @return tells if the device's axis xORy on POV povIndex is set to be inverted or not.
	 * 
	 * @see #setInvertPov(int, int, boolean)
	 */
	public boolean getInvertPov(int povIndex, int xORy)  {
		if (povIndex > this.getPovCount() || povIndex < 0) {
			createFeatureNotSupportedEvent(AdvancedControllerEvent.POV, povIndex);
			return false;
		}
		return invertPov[povIndex][xORy];
	}

	/**
	 * Sets if a POV's axis is to be inverted or not.<br>
	 * <br>
	 * Attention: Inverting a POV's axis does not affect the return value of {@link #getPovDirection(int)}
	 * 
	 * @param povIndex
	 *            the POV's index
	 * @param xORy
	 *            0 for X, 1 for Y
	 * @param invert
	 *            true for do invert, false for do not invert (default) 
	 * @see #getInvertPov(int, int)
	 */
	public void setInvertPov(int povIndex, int xORy, boolean invert)  {
		if (povIndex > this.getPovCount() || povIndex < 0) {
			createFeatureNotSupportedEvent(AdvancedControllerEvent.POV, povIndex);
		} else {
			this.invertPov[povIndex][xORy] = invert;
		}
	}



	// ############################################## AXES ############################################ 

	/* (non-Javadoc)
	 * @see org.lwjgl.input.Controller#getAxisCount()
	 */
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getAxisCount() {
		return noOfAxes;
	}	

	/* (non-Javadoc)
	 * @see org.lwjgl.input.Controller#getAxisValue(int)
	 */
	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getAxisValue(int axisIndex)  {
		if (axisIndex > this.getAxisCount() || axisIndex < 0){
			createFeatureNotSupportedEvent(AdvancedControllerEvent.AXIS, axisIndex);
			return AXIS_NEUTRAL;
		}
		float value = convertAxisValue(getAxisValueNative(joystickIndex, axisIndex));
		return invertAxis(axisIndex, ignoreDeadZone(axisIndex, value));
	}

	
	/**
	 * Convert axis value to the value that is passed to the native library.
	 * 
	 * @param joystickIndex
	 *            the joystick index
	 * @param axisIndex
	 *            the axis index
	 * 
	 * @return the axis value used for the native library (an int)
	 */
	private static native int getAxisValueNative(int joystickIndex, int axisIndex);

	
	/**
	 * Converts the native axis value back to a float.
	 * 
	 * @param axisValueNative
	 *            the native axis value 
	 * 
	 * @return the axis value as float scaled to be within [-1, 1]
	 */
	private static float convertAxisValue(int axisValueNative) {
		if (axisValueNative < 0) {
			return ((float)axisValueNative)/NATIVE_AXIS_MAX_VALUE_POSITIVE; 
		} else {
			return ((float)axisValueNative)/NATIVE_AXIS_MAX_VALUE_NEGATIVE; 
		}
	}


	private float ignoreDeadZone(int axisIndex, float axisValue) {
		return ignoreDeadZone(this.joystickIndex, axisIndex, axisValue);
	}

	/**
	 * Ignore dead zone. In fact it "rounds" values from -deadZone to deadZone to 0
	 * 
	 * @param joystickIndex
	 *            the joystick index
	 * @param axisIndex
	 *            the axis index
	 * @param value
	 *            the axis value
	 * 
	 * @return the axis value with all values within the dead zone "rounded" to 0
	 */
	private float ignoreDeadZone(int joystickIndex, int axisIndex, float value) {
		float deadZone = getDeadZone(axisIndex);
		if (value < deadZone && value > -deadZone) value = AXIS_NEUTRAL;
		return value;
	}

	/* (non-Javadoc)
	 * @see org.lwjgl.input.Controller#getDeadZone(int)
	 */
	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getDeadZone(int axisIndex) {
		if (axisIndex > this.getAxisCount() || axisIndex < 0){
			createFeatureNotSupportedEvent(AdvancedControllerEvent.AXIS, axisIndex);
			return AXIS_DEFAULT_DEAD_ZONE;
		} else {
			return axisDeadZone[axisIndex];
		}
	}
	
	/* (non-Javadoc)
	 * @see org.lwjgl.input.Controller#setDeadZone(int, float)
	 */
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setDeadZone(int axisIndex, float zone) {
		if (axisIndex > this.getAxisCount() || axisIndex < 0 || zone < AXIS_NEUTRAL || zone > AXIS_MAXIMUM){
			createFeatureNotSupportedEvent(AdvancedControllerEvent.AXIS, axisIndex);
		} else {
			axisDeadZone[axisIndex] = zone;
		}
	}


	private float invertAxis(int axisIndex, float value) {
		if (getInvertAxis(axisIndex)) value*=-1;
		return value;
	}

	/**
	 * Check if an analogue axis is being inverted.
	 * 
	 * @param axisIndex
	 *            the axis' index
	 * 
	 * @return tells if the device's analogue axis axisIndex is set to be inverted or not.
	 * 
	 * @see #setInvertAxis(int, boolean)
	 */
	public boolean getInvertAxis(int axisIndex) {
		if (axisIndex > this.getAxisCount() || axisIndex < 0){
			createFeatureNotSupportedEvent(AdvancedControllerEvent.AXIS, axisIndex);
			return false;
		} else {
			return invertAxis[axisIndex];
		}
	}

	/**
	 * Sets if an analogue axis is to be inverted or not.
	 * 
	 * @param axisIndex
	 *            the axis' index
	 * @param invert
	 *            true for do invert, false for do not invert (default) 
	 * @see #getInvertAxis(int)
	 */
	public void setInvertAxis(int axisIndex, boolean invert) {
		if (axisIndex > this.getAxisCount() || axisIndex < 0){
			createFeatureNotSupportedEvent(AdvancedControllerEvent.AXIS, axisIndex);
		} else {
			this.invertAxis[axisIndex] = invert;
		}
	}


	// ######################################### TRACKBALLS ########################################### 


	/* (non-Javadoc)
	 * @see at.wisch.joystick.AdvancedController#getBallCount()
	 */
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getBallCount() {
		return noOfBalls;
	}

	/* (non-Javadoc)
	 * @see at.wisch.joystick.AdvancedController#getBallDelta(int)
	 */
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int[] getBallDelta(int ballIndex) {
		if (ballIndex > getBallCount() || ballIndex < 0) {
			createFeatureNotSupportedEvent(AdvancedControllerEvent.BALL, ballIndex);
			return new int[]{0,0};
		} else {
			int[] deltas = getBallDeltaNative(joystickIndex, ballIndex);
			return invertBall(ballIndex, deltas);
		}
	}

	private static native int[] getBallDeltaNative(int joystickIndex, int ballIndex) ;
	
	private int[] invertBall(int ballIndex, int[] deltas) {
		if (getInvertBall(ballIndex,0)) deltas[0]*=-1;
		if (getInvertBall(ballIndex,1)) deltas[1]*=-1;
		return deltas;
	}


	/**
	 * Check if a trackball's axis is being inverted.
	 * 
	 * @param ballIndex
	 *            the trackball's index
	 * @param xORy
	 *            0 for X, 1 for Y
	 * 
	 * @return tells if the device's axis xORy on trackball ballIndex is set to be inverted or not.
	 * 
	 * @see #setInvertBall(int, int, boolean)
	 */
	public boolean getInvertBall(int ballIndex, int xORy) {
		if (ballIndex > getBallCount() || ballIndex < 0) {
			createFeatureNotSupportedEvent(AdvancedControllerEvent.BALL, ballIndex);
			return false;
		} else {
			return invertBall[ballIndex][xORy];
		}
	}

	/**
	 * Sets if a trackball's axis is to be inverted or not.
	 * 
	 * @param ballIndex
	 *            the trackball's index
	 * @param xORy
	 *            0 for X, 1 for Y
	 * @param invert
	 *            true for do invert, false for do not invert (default) 
	 * @see #getInvertBall(int, int)
	 */
	public void setInvertBall(int ballIndex, int xORy, boolean invert) {
		if (ballIndex > getBallCount() || ballIndex < 0) {
			createFeatureNotSupportedEvent(AdvancedControllerEvent.BALL, ballIndex);
		} else {
			this.invertBall[ballIndex][xORy] = invert;
		}
	}

	// ######################################### EVENTS ###########################################

	/*
	 * Creates a controller event.
	 */
	protected static boolean createControllerEvent(int[] returnedArray) {

		AdvancedControllerEvent event = null;
		Joystick joystick;
		try {
			joystick = JoystickManager.getJoystick(returnedArray[1]);
		} catch (FFJoystickException e) {
			e.printErrorMessage();
			return false;
		}

		long timestamp = System.currentTimeMillis();
		
		switch (returnedArray[2]) {
	
		case AdvancedControllerEvent.BUTTON : {
			boolean buttonPressed;
			if (returnedArray[4] == 0) {
				buttonPressed = false; 
			} else {
				buttonPressed = true;
			}
			event = new ControllerButtonEvent(joystick, timestamp, returnedArray[3], buttonPressed);
			ControllerEventManager.controllerEventOccured(event);
			break; }
		
		case AdvancedControllerEvent.POV : {
			event = new ControllerPovEvent(joystick, timestamp, returnedArray[3], returnedArray[4]);
			ControllerEventManager.controllerEventOccured(event);
			break; }
		
		case AdvancedControllerEvent.AXIS : {
			float value = joystick.ignoreDeadZone(returnedArray[3], (joystick.invertAxis(returnedArray[3], Joystick.convertAxisValue(returnedArray[4])))); 
			event = new ControllerAxisEvent(joystick, timestamp, returnedArray[3], value);
			if (value != AXIS_NEUTRAL){ // the current value is not zero
				ControllerEventManager.controllerEventOccured(event);
			} else { // the current value is zero
				if (joystick.previousAxisValueZero[returnedArray[3]]) { // the previous value was also zero
					// do nothing = IGNORE EVENT
				} else {
					ControllerEventManager.controllerEventOccured(event);
				}
			}
			joystick.previousAxisValueZero[returnedArray[3]] = (value == AXIS_NEUTRAL);
			break; }
		
		case AdvancedControllerEvent.BALL : {
			event = new ControllerBallEvent(joystick, timestamp, returnedArray[3], joystick.invertBall(returnedArray[3], new int[]{returnedArray[4], returnedArray[5]}));
			ControllerEventManager.controllerEventOccured(event);
			break; }

		}
		return true;
	}



	/*
	 * Creates a FeatureNotSupportedEvent
	 */
	protected void createFeatureNotSupportedEvent(int feature, int value) {
		FeatureNotSupportedEventManager.featureNotSupportedEventOccured(new FeatureNotSupportedEvent(this.getIndex(), feature, value));
	}


}