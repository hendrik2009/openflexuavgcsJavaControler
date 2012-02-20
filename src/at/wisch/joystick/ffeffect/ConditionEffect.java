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


package at.wisch.joystick.ffeffect;

import at.wisch.joystick.AbstractController;

/**
 * The Class ConditionEffect. For ConditionEffect the direction is handled by
 * condition internals instead of a direction member.<br>
 * <br>
 * Array indices for
 * rightSat, leftSat, rightCoef, leftCoef, deadband and center give the index of
 * the FF-axis (Array size is always 3):<br>
 * - [0] is the X-axis<br>
 * - [1] is the Y-axis<br>
 * - [2] is the Z-axis<br>
 * The term 'right' refers to the positive side of an axis and 'left' 
 * refers to the negative side of an axis.
 * 
 * @author Martin Wischenbart
 */
public abstract class ConditionEffect extends Effect {
	
	
	private int[] rightSat; //size: 3    	/*< Level when joystick is to the positive side. */ unsigned
	private int[] leftSat;  //size: 3    	/*< Level when joystick is to the negative side. */ unsigned
	private int[] rightCoef; //size: 3   	/*< How fast to increase the force towards the positive side. */ signed
	private int[] leftCoef; //size: 3    	/*< How fast to increase the force towards the negative side. */ signed
	private float[] deadband; //size: 3
	private float[] center; //size: 3
	
	
	// we have separate arrays for data exchange with the native code
	@SuppressWarnings("unused")
	private int[] deadbandInt; //size: 3     	/**< Size of the dead zone. */ unsigned
	@SuppressWarnings("unused")
	private int[] centerInt; //size: 3        	/**< Position of the dead zone. */ signed
	
	
    private static final int NATIVE_AXIS_POSITIVE_FACTOR = MAX_LEVEL;
    private static final int NATIVE_AXIS_NEGATIVE_FACTOR = -1 * MIN_LEVEL; // NATIVE_AXIS_NEGATIVE_FACTOR is positive
    
    
	protected ConditionEffect(int effectType, int effectLength, int effectDelay, int buttonIndex,
			int buttonInterval, int[] rightSat, int[] leftSat,
			int[] rightCoef, int[] leftCoef, float[] deadband, float[] center) {
		super(effectType, effectLength, effectDelay, buttonIndex,
				buttonInterval);
		setRightSat(rightSat);
		setLeftSat(leftSat);
		setRightCoef(rightCoef);
		setLeftCoef(leftCoef);
		setDeadband(deadband);
		setCenter(center);
	}
	
	protected ConditionEffect(int effectType) {
		this(effectType, 3000, 0, Effect.NO_BUTTON, 0,
				new int[]{MAX_LEVEL, MAX_LEVEL, MAX_LEVEL},
				new int[]{MAX_LEVEL, MAX_LEVEL, MAX_LEVEL},
				new int[]{(int)(MAX_LEVEL*0.9), (int)(MAX_LEVEL*0.9), (int)(MAX_LEVEL*0.9)},
				new int[]{(int)(MAX_LEVEL*0.9), (int)(MAX_LEVEL*0.9), (int)(MAX_LEVEL*0.9)},
  				new float[]{AbstractController.AXIS_DEFAULT_DEAD_ZONE, AbstractController.AXIS_DEFAULT_DEAD_ZONE, AbstractController.AXIS_DEFAULT_DEAD_ZONE},
				new float[]{0, 0, 0});
		
	}
	
    
	/**
	 * Gets the rightSat array: Level when joystick is to the positive side.
	 * 
	 * @return the rightSat (array of size 3)
	 * 
	 * @see #setRightSat(int[])
	 */
	public int[] getRightSat() {
		return rightSat;
	}
	
	/**
	 * Sets the rightSat array: Level when joystick is to the positive side.
	 * 
	 * @param rightSat
	 *            the new rightSat (array of size 3, values from 0 to MAX_LEVEL
	 *            (32767))
	 * 
	 * @see #getRightSat()
	 */
	public void setRightSat(int[] rightSat) {
		if (rightSat.length != 3){
			createFeatureNotSupportedEvent(rightSat.length);
			rightSat = new int[]{MAX_LEVEL, MAX_LEVEL, MAX_LEVEL};
		}
		for (int i = 0; i < 2; i++) {
			if (rightSat[i] < 0) {
				createFeatureNotSupportedEvent(rightSat[i]);
				rightSat[i] = 0;
			}
			if (rightSat[i] > MAX_LEVEL) {
				createFeatureNotSupportedEvent(rightSat[i]);
				rightSat[i] = MAX_LEVEL;
			}
		}
		this.rightSat = rightSat;
	}
	
	/**
	 * Gets the leftSat array: Level when joystick is to the negative side.
	 * 
	 * @return the leftSat (array of size 3)
	 * 
	 * @see #setLeftSat(int[])
	 */
	public int[] getLeftSat() {
		return leftSat;
	}
	
	/**
	 * Sets the leftSat array: Level when joystick is to the negative side.
	 * 
	 * @param leftSat
	 *            the new leftSat (array of size 3, values from 0 to MAX_LEVEL
	 *            (32767))
	 * 
	 * @see #getLeftSat()
	 */
	public void setLeftSat(int[] leftSat) {
		if (leftSat.length != 3){
			createFeatureNotSupportedEvent(leftSat.length);
			leftSat = new int[]{MAX_LEVEL, MAX_LEVEL, MAX_LEVEL};
		}
		for (int i = 0; i < 2; i++) {
			if (leftSat[i] < 0) {
				createFeatureNotSupportedEvent(leftSat[i]);
				leftSat[i] = 0;
			}
			if (leftSat[i] > MAX_LEVEL) {
				createFeatureNotSupportedEvent(leftSat[i]);
				leftSat[i] = MAX_LEVEL;
			}
		}
		this.leftSat = leftSat;
	}
	
	/**
	 * Gets the rightCoef array: How fast to increase the force towards the
	 * positive side.
	 * 
	 * @return the rightCoef (array of size 3)
	 * 
	 * @see #setRightCoef(int[])
	 */
	public int[] getRightCoef() {
		return rightCoef;
	}
	
	/**
	 * Sets the rightCoef array: How fast to increase the force towards the
	 * positive side.
	 * 
	 * @param rightCoef
	 *            the new rightCoef (array of size 3, values from MIN_LEVEL
	 *            (-32768) to MAX_LEVEL (32767))
	 * 
	 * @see #getRightCoef()
	 */
	public void setRightCoef(int[] rightCoef) {
		if (rightCoef.length != 3){
			createFeatureNotSupportedEvent(rightCoef.length);
			rightCoef = new int[]{(int)(MAX_LEVEL*0.9), (int)(MAX_LEVEL*0.9), (int)(MAX_LEVEL*0.9)};
		}
		for (int i = 0; i < 2; i++) {
			if (rightCoef[i] < MIN_LEVEL) {
				createFeatureNotSupportedEvent(rightCoef[i]);
				rightCoef[i] = MIN_LEVEL;
			}
			if (rightCoef[i] > MAX_LEVEL) {
				createFeatureNotSupportedEvent(rightCoef[i]);
				rightCoef[i] = MAX_LEVEL;
			}
		}
		this.rightCoef = rightCoef;
	}
	
	/**
	 * Gets the leftCoef array: How fast to increase the force towards the
	 * negative side.
	 * 
	 * @return the leftCoef (array of size 3)
	 * 
	 * @see #setLeftCoef(int[])
	 */
	public int[] getLeftCoef() {
		return leftCoef;
	}
	
	/**
	 * Sets the leftCoef array: How fast to increase the force towards the
	 * negative side.
	 * 
	 * @param leftCoef
	 *            the new leftCoef (array of size 3, values from MIN_LEVEL
	 *            (-32768) to MAX_LEVEL (32767))
	 * 
	 * @see #getLeftCoef()
	 */
	public void setLeftCoef(int[] leftCoef) {
		if (leftCoef.length != 3){
			createFeatureNotSupportedEvent(leftCoef.length);
			leftCoef = new int[]{(int)(MAX_LEVEL*0.5), (int)(MAX_LEVEL*0.5), (int)(MAX_LEVEL*0.5)};
		}
		for (int i = 0; i < 2; i++) {
			if (leftCoef[i] < MIN_LEVEL) {
				createFeatureNotSupportedEvent(leftCoef[i]);
				leftCoef[i] = MIN_LEVEL;
			}
			if (leftCoef[i] > MAX_LEVEL) {
				createFeatureNotSupportedEvent(leftCoef[i]);
				leftCoef[i] = MAX_LEVEL;
			}
		}
		this.leftCoef = leftCoef;
	}
	
	/**
	 * Gets the deadband. Deadband is a zone around the center where no effect
	 * is played, similar to {@link at.wisch.joystick.Joystick#getDeadZone(int)}
	 * .
	 * 
	 * @return the array of deadband values for condition effects
	 * 
	 * @see #setDeadband(float[])
	 * @see #getCenter()
	 */
	public float[] getDeadband() {
		return deadband;
	}

	/**
	 * Sets the deadband. 3 dead zone values, 1 for each axis. Deadband is a
	 * zone around the center where no effect is played, similar to
	 * {@link at.wisch.joystick.Joystick#setDeadZone(int, float)}.
	 * 
	 * @param deadband
	 *            the new deadband values array (array of size 3, values from 0
	 *            to AXIS_MAXIMUM (1f))
	 * 
	 * @see #getDeadband()
	 * @see #setCenter(float[])
	 */
	public void setDeadband(float[] deadband) {
		if (deadband.length != 3){
			createFeatureNotSupportedEvent(deadband.length);
			deadband = new float[]{AbstractController.AXIS_DEFAULT_DEAD_ZONE, AbstractController.AXIS_DEFAULT_DEAD_ZONE, AbstractController.AXIS_DEFAULT_DEAD_ZONE};
		}
		for (int i = 0; i < 2; i++) {
			if (deadband[i] < AbstractController.AXIS_NEUTRAL) {
				createFeatureNotSupportedEvent(convertAxisValueToNative(deadband[i]));
				deadband[i] = 0;
			}
			if (deadband[i] > AbstractController.AXIS_MAXIMUM) {
				createFeatureNotSupportedEvent(convertAxisValueToNative(deadband[i]));
				deadband[i] = AbstractController.AXIS_MAXIMUM;
			}
		}
		this.deadband = deadband;
		this.deadbandInt = new int[]{ convertAxisValueToNative(deadband[0]),
										convertAxisValueToNative(deadband[1]),
										convertAxisValueToNative(deadband[2])};
	}
	
	/**
	 * Gets the center for condition effects. Default is {0, 0, 0}. Condition
	 * effects point towards/away from that center and the deadband is around
	 * that center.
	 * 
	 * @return the array of center values for condition effects (array of size 3
	 *         (X-, Y- and Z-axis))
	 * 
	 * @see #setCenter(float[])
	 * @see #getDeadband()
	 */
	public float[] getCenter() {
		return center;
	}
	
	/**
	 * Sets the center for condition effects. Default is {0, 0, 0}. Condition
	 * effects point towards/away from that center and the deadband is around
	 * that center.
	 * 
	 * @param center
	 *            the new center (array of size 3 (X-, Y- and Z-axis), values
	 *            from AXIS_MINIMUM (-1f) to AXIS_MAXIMUM(1f))
	 * 
	 * @see #getCenter()
	 * @see #setDeadband(float[])
	 */
	public void setCenter(float[] center) {
		if (center.length != 3){
			createFeatureNotSupportedEvent(center.length);
			center = new float[]{0, 0, 0};
		}
		for (int i = 0; i < 2; i++) {
			if (center[i] < AbstractController.AXIS_MINIMUM) {
				createFeatureNotSupportedEvent(convertAxisValueToNative(center[i]));
				center[i] = AbstractController.AXIS_MINIMUM;
			}
			if (center[i] > AbstractController.AXIS_MAXIMUM) {
				createFeatureNotSupportedEvent(convertAxisValueToNative(center[i]));
				center[i] = AbstractController.AXIS_MAXIMUM;
			}
		}
		this.center = center;
		this.centerInt = new int[]{ convertAxisValueToNative(center[0]),
									convertAxisValueToNative(center[1]),
									convertAxisValueToNative(center[2])};
	}

	
	
	
	
	
	/* (non-Javadoc)
	 * @see at.wisch.joystick.ffeffect.Effect#getStrength()
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * Strength for a ConditionEffect refers to the rightSat and leftSat values.
	 * @see #setRightSat(int[])
	 * @see #setLeftSat(int[])
	 */
	@Override
	public int getStrength() {
		return (int)((this.rightSat[0]+this.rightSat[1]+this.rightSat[2]+this.leftSat[0]+this.leftSat[1]+this.leftSat[2])/6);
	}

	// be aware: applying setStrength can alter the behavour of the effect drastically
	/* (non-Javadoc)
	 * @see at.wisch.joystick.ffeffect.Effect#setStrength(int)
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * Strength for a ConditionEffect refers to the rightSat and leftSat values. Be aware that applying setStrength can alter the behaviour of the effect, because all rightSat and leftSat values will be set to the same.
	 * 
	 * @see #setRightSat(int[])
	 * @see #setLeftSat(int[])
	 */
	@Override
	public void setStrength(int strength) {
		this.rightSat = new int[]{strength, strength, strength};
		this.leftSat = new int[]{strength, strength, strength};
	}
	
	private static int convertAxisValueToNative(float axisValue) {
		if (axisValue >= 0) {
			return (int)(axisValue*NATIVE_AXIS_POSITIVE_FACTOR);
		} else {
			return (int)(axisValue*NATIVE_AXIS_NEGATIVE_FACTOR);
		}
	}
	
	

	
}
