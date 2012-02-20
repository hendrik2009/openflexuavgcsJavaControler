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

/**
 * The abstract class AbstractController. It implements some of the interface AdvancedController's methods, that just exist for convenience. E.g. getXAxisValue() is "redirected" to getAxisValue(0). It also defines some constants for the classes Joystick and FFJoystick.
 * <br><br>
 * Joystick extends this abstract class, hence it also implements AdvancedController. FFJoystick extends Joystick and implements the interface AdvancedFFController (which is an extension of AdvancedController).
 * 
 * @author Martin Wischenbart
 */
public abstract class AbstractController implements AdvancedController {

	
	
	// #################### CONSTANTS FOR INPUT ######################
	
	/**
	 * The constant POV_CENTERED ({@value} ): POV is in center position.
	 */
	public static final int POV_CENTERED = -1;
	
	/**
	 * The constant POV_UP ({@value} ): POV position is up.
	 */
	public static final int POV_UP = 0;
	
	/**
	 * The constant POV_UP_RIGHT ({@value} ): POV position is up-right.
	 */
	public static final int POV_UP_RIGHT = 4500;
	
	/**
	 * The constant POV_RIGHT ({@value} ): POV position is right.
	 */
	public static final int POV_RIGHT = 9000;
	
	/**
	 * The constant POV_DOWN_RIGHT ({@value} ): POV position is down-right.
	 */
	public static final int POV_DOWN_RIGHT = 13500;
	
	/**
	 * The constant POV_DOWN ({@value} ): POV position is down.
	 */
	public static final int POV_DOWN = 18000;
	
	/**
	 * The constant POV_DOWN_LEFT ({@value} ): POV position is down-left.
	 */
	public static final int POV_DOWN_LEFT = 22500;
	
	/**
	 * The constant POV_LEFT ({@value} ): POV position is left.
	 */
	public static final int POV_LEFT = 27000;
	
	/**
	 * The constant POV_UP_LEFT ({@value} ): POV position is up-left.
	 */
	public static final int POV_UP_LEFT = 31500;
	
	/**
	 * The constant POV_AXIS_NEUTRAL ({@value} ): POV axis is in neutral position (center).
	 */
	public static final int POV_AXIS_NEUTRAL = 0;
	
	/**
	 * The constant POV_AXIS_POSITIVE ({@value} ): POV axis is on the positive side.
	 */
	public static final int POV_AXIS_POSITIVE = 1;
	
	/**
	 * The constant POV_AXIS_NEGATIVE ({@value} ): POV axis is on the negative side.
	 */
	public static final int POV_AXIS_NEGATIVE = -POV_AXIS_POSITIVE;
	
	/**
	 * The constant AXIS_NEUTRAL ({@value} ): Analogue axis is in central position.
	 */
	public static final float AXIS_NEUTRAL = 0f;
	
	/**
	 * The constant AXIS_MAXIMUM ({@value} ): Analogue axis position on maximum (i.e. to the positive side).
	 */
	public static final float AXIS_MAXIMUM = 1f;
	
	/**
	 * The constant AXIS_MINIMUM ({@value} ): Analogue axis position on minimum (i.e. to the negative side).
	 */
	public static final float AXIS_MINIMUM = -1f;
	
	/**
	 * The constant AXIS_DEFAULT_DEAD_ZONE (0.03f): Default dead zone for analogue axes.
	 */
	public static final float AXIS_DEFAULT_DEAD_ZONE = 0.03f;
	
	private static final String[] AXISNAMES = {"X", "Y", "Z", "rX", "rY", "rZ", "U", "V"};
	protected static final int NATIVE_AXIS_MAX_VALUE_POSITIVE = 32767;
	protected static final int NATIVE_AXIS_MAX_VALUE_NEGATIVE = 32768;

	
	// ###################### CONSTANTS FOR FF-CONTROLLERS #######################
	
	/**
	 * The constant INFINITE_TIMES: Play an FF effect infinite times.
	 */
	public static final int INFINITE_TIMES = Integer.MAX_VALUE;
	
	/**
	 * The constant DEFAULT_GAIN_VALUE ({@value} ): Default gain value for FF joysticks.
	 */
	public static final int DEFAULT_GAIN_VALUE = 100;
	
	/**
	 * The constant DEFAULT_AUTOCENTER_VALUE ({@value} ): Default autocenter value for FF joysticks.
	 */
	public static final int DEFAULT_AUTOCENTER_VALUE = 0;
	
	
	// #######################################################################
	
	// empty constructor
	protected AbstractController() {
		super();
	}
	
	// ################## BUTTONS #####################

	/* (non-Javadoc)
	 * @see org.lwjgl.input.Controller#getButtonName(int)
	 */
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getButtonName(int buttonIndex) {
		if (buttonIndex < 0) {
			return "-1"; 
		} else {
			return Integer.toString(buttonIndex+1);
		}
	}	
	

	// ################## POVS #####################
	
	
	
	/* (non-Javadoc)
	 * @see at.wisch.joystick.AdvancedController#getPovName(int)
	 */
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final String getPovName(int povIndex) {
		if (povIndex < 0) {
			return "-1"; 
		} else {
			return Integer.toString(povIndex+1);
		}
	}
	
	
		
	// ------------------------------------------------
		
	// NOTE: these special methods exist only for the first 4 POVs
	
	/* (non-Javadoc)
	 * @see at.wisch.joystick.AdvancedController#getPov1X()
	 */
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final int getPov1X()  {
		return getPovX(0);
	}
	
	/* (non-Javadoc)
	 * @see at.wisch.joystick.AdvancedController#getPov1Y()
	 */
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final int getPov1Y()  {
		return getPovY(0);
	}
	
	/* (non-Javadoc)
	 * @see at.wisch.joystick.AdvancedController#getPov2X()
	 */
	/**
	 * {@inheritDoc}
	 */
@Override
	public final int getPov2X()  {
		return getPovX(1);
	}
	
	/* (non-Javadoc)
	 * @see at.wisch.joystick.AdvancedController#getPov2Y()
	 */
/**
 * {@inheritDoc}
 */
@Override
	public final int getPov2Y()  {
		return getPovY(1);
	}
	
	/* (non-Javadoc)
	 * @see at.wisch.joystick.AdvancedController#getPov3X()
	 */
/**
 * {@inheritDoc}
 */
	@Override
	public final int getPov3X()  {
		return getPovX(2);
	}
	
	/* (non-Javadoc)
	 * @see at.wisch.joystick.AdvancedController#getPov3Y()
	 */
	/**
	 * {@inheritDoc}
	 */
@Override
	public final int getPov3Y()  {
		return getPovY(2);
	}
	
	/* (non-Javadoc)
	 * @see at.wisch.joystick.AdvancedController#getPov4X()
	 */
/**
 * {@inheritDoc}
 */

	@Override
	public final int getPov4X()  {
		return getPovX(3);
	}
	
	/* (non-Javadoc)
	 * @see at.wisch.joystick.AdvancedController#getPov4Y()
	 */
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final int getPov4Y()  {
		return getPovY(3);
	}
	
	// ------------------------------------------------
	
		
	/*
	 * mapping the methods getPovX() and getPovY from the interface Controller to
	 * getPov1X() and getPov1Y() of interface AdvancedController
	 */
	
	/* (non-Javadoc)
	 * @see org.lwjgl.input.Controller#getPovX()
	 */
	/**
	 * {@inheritDoc}
	 * @deprecated uses float where int would be sufficient. Use getPovValueX(0) instead.
	 * @see #getPovDirection(int)
	 */
	@Override
	@Deprecated
	public final float getPovX() {
		return getPov1X();
	}
	
	/* (non-Javadoc)
	 * @see org.lwjgl.input.Controller#getPovY()
	 */
	/**
	 * {@inheritDoc}
	 * @deprecated uses float where int would be sufficient. Use getPovValueY(0) instead.
	 * @see #getPovDirection(int)
	 */
	@Override
	@Deprecated
	public final float getPovY(){
		return getPov1Y();
	}
	
	// #################### AXES ######################
	
	
	
	/* (non-Javadoc)
	 * @see org.lwjgl.input.Controller#getAxisName(int)
	 */
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final String getAxisName(int axisIndex) {
		if (axisIndex < 0) {
			return "-1"; 
		} else if (axisIndex < AXISNAMES.length) {
			return AXISNAMES[axisIndex];
		} else {
			return Integer.toString(axisIndex+1);
		}
	}
	
	// NOTE: these special methods exist only for the first 8 axes
	
	/* (non-Javadoc)
	 * @see org.lwjgl.input.Controller#getXAxisValue()
	 */
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final float getXAxisValue() {
		return getAxisValue(0);
	}

	/* (non-Javadoc)
	 * @see org.lwjgl.input.Controller#getYAxisValue()
	 */
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final float getYAxisValue() {
		return getAxisValue(1);
	}
	
	/* (non-Javadoc)
	 * @see org.lwjgl.input.Controller#getZAxisValue()
	 */
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final float getZAxisValue() {
		return getAxisValue(2);
	}
	
	/* (non-Javadoc)
	 * @see org.lwjgl.input.Controller#getRXAxisValue()
	 */
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final float getRXAxisValue() {
		return getAxisValue(3);
	}

	/* (non-Javadoc)
	 * @see org.lwjgl.input.Controller#getRYAxisValue()
	 */
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final float getRYAxisValue() {
		return getAxisValue(4);
	}

	/* (non-Javadoc)
	 * @see org.lwjgl.input.Controller#getRZAxisValue()
	 */
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final float getRZAxisValue() {
		return getAxisValue(5);
	}
	
	/* (non-Javadoc)
	 * @see at.wisch.joystick.AdvancedController#getUAxisValue()
	 */
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final float getUAxisValue() {
		return getAxisValue(6);
	}
	
	/* (non-Javadoc)
	 * @see at.wisch.joystick.AdvancedController#getVAxisValue()
	 */
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final float getVAxisValue() {
		return getAxisValue(7);
	}
	
	// ------------------------------------------------
	
	/* (non-Javadoc)
	 * @see org.lwjgl.input.Controller#getXAxisDeadZone()
	 */
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final float getXAxisDeadZone() {
		return getDeadZone(0);
	}
	
	/* (non-Javadoc)
	 * @see org.lwjgl.input.Controller#getYAxisDeadZone()
	 */
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final float getYAxisDeadZone() {
		return getDeadZone(1);
	}
	
	/* (non-Javadoc)
	 * @see org.lwjgl.input.Controller#getZAxisDeadZone()
	 */
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final float getZAxisDeadZone() {
		return getDeadZone(2);
	}

	/* (non-Javadoc)
	 * @see org.lwjgl.input.Controller#getRXAxisDeadZone()
	 */
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final float getRXAxisDeadZone() {
		return getDeadZone(3);
	}

	/* (non-Javadoc)
	 * @see org.lwjgl.input.Controller#getRYAxisDeadZone()
	 */
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final float getRYAxisDeadZone() {
		return getDeadZone(4);
	}
	
	/* (non-Javadoc)
	 * @see org.lwjgl.input.Controller#getRZAxisDeadZone()
	 */
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final float getRZAxisDeadZone() {
		return getDeadZone(5);	
	}
	
	/* (non-Javadoc)
	 * @see at.wisch.joystick.AdvancedController#getUAxisDeadZone()
	 */
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final float getUAxisDeadZone() {
		return getDeadZone(6);	
	}
	
	/* (non-Javadoc)
	 * @see at.wisch.joystick.AdvancedController#getVAxisDeadZone()
	 */
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final float getVAxisDeadZone() {
		return getDeadZone(7);	
	}
	
	// ------------------------------------------------
	
	
	/* (non-Javadoc)
	 * @see org.lwjgl.input.Controller#setXAxisDeadZone(float)
	 */
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void setXAxisDeadZone(float zone) {
		setDeadZone(0, zone);
	}

	/* (non-Javadoc)
	 * @see org.lwjgl.input.Controller#setYAxisDeadZone(float)
	 */
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void setYAxisDeadZone(float zone) {
		setDeadZone(1, zone);
	}

	/* (non-Javadoc)
	 * @see org.lwjgl.input.Controller#setZAxisDeadZone(float)
	 */
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void setZAxisDeadZone(float zone) {
		setDeadZone(2, zone);
	}
	
	/* (non-Javadoc)
	 * @see org.lwjgl.input.Controller#setRXAxisDeadZone(float)
	 */
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void setRXAxisDeadZone(float zone) {
		setDeadZone(3, zone);
	}

	/* (non-Javadoc)
	 * @see org.lwjgl.input.Controller#setRYAxisDeadZone(float)
	 */
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void setRYAxisDeadZone(float zone) {
		setDeadZone(4, zone);
	}

	/* (non-Javadoc)
	 * @see org.lwjgl.input.Controller#setRZAxisDeadZone(float)
	 */
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void setRZAxisDeadZone(float zone) {
		setDeadZone(5, zone);
	}
	
	/* (non-Javadoc)
	 * @see at.wisch.joystick.AdvancedController#setUAxisDeadZone(float)
	 */
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void setUAxisDeadZone(float zone) {
		setDeadZone(6, zone);
	}
	
	/* (non-Javadoc)
	 * @see at.wisch.joystick.AdvancedController#setVAxisDeadZone(float)
	 */
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void setVAxisDeadZone(float zone) {
		setDeadZone(7, zone);
	}
	
	
	// ################## TRACKBALLS ###################

	/* (non-Javadoc)
	 * @see at.wisch.joystick.AdvancedController#getBallName(int)
	 */
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final String getBallName(int ballIndex) {
		return Integer.toString(ballIndex+1);
	}
	
	// NOTE: these special methods exist only for the first 4 trackballs
	
	/* (non-Javadoc)
	 * @see at.wisch.joystick.AdvancedController#getBall1Delta()
	 */
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final int[] getBall1Delta() {
		return getBallDelta(0);
	}	
	
	/* (non-Javadoc)
	 * @see at.wisch.joystick.AdvancedController#getBall2Delta()
	 */
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final int[] getBall2Delta() {
		return getBallDelta(1);
	}	
	
	/* (non-Javadoc)
	 * @see at.wisch.joystick.AdvancedController#getBall3Delta()
	 */
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final int[] getBall3Delta() {
		return getBallDelta(2);
	}	
	
	/* (non-Javadoc)
	 * @see at.wisch.joystick.AdvancedController#getBall4Delta()
	 */
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final int[] getBall4Delta() {
		return getBallDelta(3);
	}
	
	
}