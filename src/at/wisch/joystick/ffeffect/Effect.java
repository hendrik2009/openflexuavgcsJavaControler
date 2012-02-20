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

import at.wisch.joystick.event.FeatureNotSupportedEvent;
import at.wisch.joystick.event.FeatureNotSupportedEventManager;
import at.wisch.joystick.ffeffect.direction.Direction;
import at.wisch.joystick.ffeffect.direction.PolarDirection;

/**
 * The Class Effect. An Effect object represents an actual force feedback effect
 * to be played on an FFJoystick.<br>
 * <br>
 * To play an effect you have to instantiate it with 'new', after that you can
 * set or change certain parameters.<br>
 * - You can use the default constructor with no parameters, which creates an
 * effect with default settings. After that you can change its parameters using
 * setter methods.<br>
 * - Or you can use the advanced constructor where you submit all properties at
 * initialization. You can change the properties using setter as well.<br>
 * <br>
 * An Effect can be uploaded to several devices. It can also be altered to
 * update the effect on the device. That can be done even while the effect is
 * playing. To get to know how to upload, play, update, pause, stop or delete an
 * effect on the device see {@link at.wisch.joystick.FFJoystick}.
 * 
 * @see at.wisch.joystick.FFJoystick
 * @see at.wisch.joystick.ffeffect.direction.Direction
 * 
 * @author Martin Wischenbart
 */
public abstract class Effect {

	
	/**
	 * The constant EFFECT_CONSTANT: the object is a ConstantEffect (casting is
	 * safe).
	 */
	public static final int EFFECT_CONSTANT = 5100;
	
	/**
	 * The constant EFFECT_SINE: the object is a SineEffect (casting is safe).
	 */
	public static final int EFFECT_SINE = 5101;
	
	/**
	 * The constant EFFECT_SQUARE: the object is a SquareEffect (casting is
	 * safe).
	 */
	public static final int EFFECT_SQUARE = 5102;
	
	/**
	 * The constant EFFECT_TRIANGLE: the object is a TriangleEffect (casting is
	 * safe).
	 */
	public static final int EFFECT_TRIANGLE = 5103;
	
	/**
	 * The constant EFFECT_SAWTOOTHUP: the object is a SawtoothUpEffect (casting
	 * is safe).
	 */
	public static final int EFFECT_SAWTOOTHUP = 5104;
	
	/**
	 * The constant EFFECT_SAWTOOHDOWN: the object is a SawtoothDownEffect
	 * (casting is safe).
	 */
	public static final int EFFECT_SAWTOOHDOWN = 5105;
	
	/**
	 * The constant EFFECT_RAMP: the object is a RampEffect (casting is safe).
	 */
	public static final int EFFECT_RAMP = 5106;
	
	/**
	 * The constant EFFECT_SPRING: the object is a SpringEffect (casting is
	 * safe).
	 */
	public static final int EFFECT_SPRING = 5107;
	
	/**
	 * The constant EFFECT_DAMPER: the object is a DamperEffect (casting is
	 * safe).
	 */
	public static final int EFFECT_DAMPER = 5108;
	
	/**
	 * The constant EFFECT_INERTIA: the object is a InertiaEffect (casting is
	 * safe).
	 */
	public static final int EFFECT_INERTIA = 5109;
	
	/**
	 * The constant EFFECT_FRICTION: the object is a FrictionEffect (casting is
	 * safe).
	 */
	public static final int EFFECT_FRICTION = 5110;
	
	/**
	 * The constant EFFECT_CUSTOM: the object is a CustomEffect (casting is
	 * safe).
	 */
	public static final int EFFECT_CUSTOM = 5111;
	
	
	/**
	 * The constant NO_BUTTON: use this constant to set no trigger button for
	 * the effect.
	 */
	public static final int NO_BUTTON = Integer.MAX_VALUE; // for trigger button
	
	/**
	 * The constant INFINITE_LENGTH: use this constant to set infinite length
	 * for an effect..
	 */
	public static final int INFINITE_LENGTH = Integer.MAX_VALUE; // infinite length for an effect
	
	/**
	 * The constant MAX_LEVEL (32767): this is the maximum parameter value for
	 * some Effect properties: strength, level, ...
	 */
	public static final int MAX_LEVEL = 32767;
	
	/**
	 * The constant MIN_LEVEL (-32768): this is the minimum parameter value (to
	 * the negative side) for some Effect properties.
	 */
	public static final int MIN_LEVEL = -32768;
	
	protected static final int MAX_BUTTONS = MAX_LEVEL;
	
	/**
	 * The constant MAX_DELAY (60000 ms): 60 seconds - the maximum delay for
	 * starting an effect, also the maximum interval for buttons triggering
	 * effects.
	 */
	public static final int MAX_DELAY = 60000; // Uint16 --- 60 seconds
	
	/**
	 * The constant MAX_LENGTH (360000000 ms): 100 hours - the maximum length
	 * for an effect.
	 */
	public static final int MAX_LENGTH = 360000000; // Uint32 --- could be more, but i figured 100 hours is enough
	
	
	private int effectType;
	private static Direction defaultDirection = new PolarDirection(PolarDirection.NORTHWEST);

		
	private int effectLength; // Duration of effect (ms).
	private int effectDelay; // Delay before starting effect. (ms)
	
	private int buttonIndex; // Button that triggers effect. (index)
	private int buttonInterval; // How soon before effect can be triggered again. (ms)
	
		
	protected Effect(int effectType, int effectLength,
			int effectDelay, int buttonIndex, int buttonInterval) {
		super();
		this.effectType = effectType;
		setEffectLength(effectLength);
		setEffectDelay(effectDelay);
		setButtonIndex(buttonIndex);
		setButtonInterval(buttonInterval);
		
	}
	
	/**
	 * Gets the effect type. One of the following: EFFECT_CONSTANT, EFFECT_SINE,
	 * EFFECT_SQUARE, EFFECT_TRIANGLE, EFFECT_SAWTOOTHUP, EFFECT_SAWTOOTHDOWN,
	 * EFFECT_RAMP, EFFECT_SPRING, EFFECT_DAMPER, EFFECT_INERTIA,
	 * EFFECT_FRICTION or EFFECT_CUSTOM.
	 * 
	 * @return the effect type
	 * 
	 * @see #getName()
	 */
	public int getEffectType() {
		return effectType;
	}

	/**
	 * Gets the effect length in ms.
	 * 
	 * @return the effect length in ms
	 * 
	 * @see #setEffectLength(int)
	 */
	public int getEffectLength() {
		return effectLength;
	}

	/**
	 * Sets the effect length in ms. You can also set it to INFINITE_LENGTH to
	 * make the effect last for infinite time or until it is stopped.
	 * 
	 * @param effectLength
	 *            the new effect length (an int from 0 to MAX_LENGTH (360000000
	 *            ms ) or INFINITE_LENGTH)
	 * 
	 * @see #getEffectLength()
	 */
	public void setEffectLength(int effectLength) {
		if (effectLength < 0) {
			createFeatureNotSupportedEvent(effectLength);
			this.effectLength = 250; // 1/4 second
		}else if (effectLength > MAX_LENGTH && effectLength != INFINITE_LENGTH){
			createFeatureNotSupportedEvent(effectLength);
			this.effectLength = MAX_LENGTH;
		} else {
			this.effectLength = effectLength;
		}
	}

	/**
	 * Gets the effect delay before the effect will be started.
	 * 
	 * @return the effect delay
	 * 
	 * @see #setEffectDelay(int)
	 */
	public int getEffectDelay() {
		return effectDelay;
	}

	/**
	 * Sets the effect delay before the effect will be started.
	 * 
	 * @param effectDelay
	 *            the new effect delay (an int from 0 to MAX_DELAY (60000 ms))
	 * 
	 * @see #getEffectDelay()
	 */
	public void setEffectDelay(int effectDelay) {
		if (effectDelay < 0) {
			createFeatureNotSupportedEvent(effectDelay);
			this.effectDelay = 0;
		} else if (effectDelay > MAX_DELAY) {
			createFeatureNotSupportedEvent(effectDelay);
			this.effectDelay = MAX_DELAY;
		} else {
			this.effectDelay = effectDelay;
		}
	}

	/**
	 * Gets the index of the button which is set to trigger the Effect.
	 * 
	 * @return the button index
	 */
	public int getButtonIndex() {
		return buttonIndex;
	}

	/**
	 * Sets the button index, the index of the button which will trigger the
	 * Effect to be played, or NO_BUTTON. Once the effect is uploaded to the
	 * device, pressing this button will play the effect.
	 * 
	 * @param buttonIndex
	 *            the new button index
	 * 
	 * @see #getButtonIndex()
	 * @see at.wisch.joystick.Joystick#getButtonCount()
	 * @deprecated trigger buttons are not supported on all operating systems.
	 *             To stay platform independent you should trigger effects
	 *             manually from events. See
	 *             {@link at.wisch.joystick.event.ControllerButtonEvent}
	 */
	@Deprecated
	public void setButtonIndex(int buttonIndex) {
		if (buttonIndex != NO_BUTTON && (buttonIndex < 0 || buttonIndex > MAX_BUTTONS)) {
			createFeatureNotSupportedEvent(buttonIndex);
			this.buttonIndex = NO_BUTTON;
		} else {
			this.buttonIndex = buttonIndex;
		}
	}

	/**
	 * Gets the currently set button interval.
	 * 
	 * @return the button interval
	 * 
	 * @see #setButtonInterval(int)
	 * @see #getButtonIndex()
	 */
	public int getButtonInterval() {
		return buttonInterval;
	}

	/**
	 * Sets the button interval. This interval defines how soon an effect can be
	 * triggered again by a button after the button was pressed.
	 * 
	 * @param buttonInterval
	 *            the new button interval (an int from 0 to MAX_DELAY (60000 ms
	 *            ))
	 * 
	 * @see #getButtonInterval()
	 * @see #setButtonIndex(int)
	 * @deprecated trigger buttons are not supported on all operating systems.
	 *             To stay platform independent you should trigger effects
	 *             manually from events. See
	 *             {@link at.wisch.joystick.event.ControllerButtonEvent}
	 */
	@Deprecated
	public void setButtonInterval(int buttonInterval) {
		if (buttonInterval < 0) {
			createFeatureNotSupportedEvent(buttonInterval);
			this.buttonInterval = 0;
		} else if (buttonInterval > MAX_DELAY) {
			createFeatureNotSupportedEvent(buttonInterval);
			this.buttonInterval = MAX_DELAY;
		} else {
			this.buttonInterval = buttonInterval;
		}
	}
	
	
	// every kind of effect must implement these 2 methods to make sure
	// that the strength can be set somehow  
	/**
	 * Sets the strength for an effect. Every kind of effect has some way to set
	 * its strength or intensity. This is for convenience only, so we can use
	 * FFJoystick.getSimpleEffect() and later set strength without knowing what
	 * type of effect we actually have. If you know the type of effect, use the
	 * effect specific methods instead.
	 * 
	 * @param strength
	 *            the new strength (an int from 0 to MAX_LEVEL (32767))
	 * 
	 * @see #getStrength()
	 * @see at.wisch.joystick.FFJoystick#getSimpleEffect()
	 */
	public abstract void setStrength(int strength);
	
	/**
	 * Gets the strength. The strength tells us about the intensity of an
	 * effect. Depending on the type of effect the strength is computed
	 * differently. If you know the type of effect, use the effect specific
	 * methods instead.
	 * 
	 * @return the strength
	 * 
	 * @see #setStrength(int)
	 */
	public abstract int getStrength();
	

	
	/**
	 * Gets the effect type as a String.
	 * 
	 * @return the effect type as a String: one of the following: "Constant",
	 *         "Sine", "Square", "Triangle", "SawtoothUp", "SawtoothDown",
	 *         "Ramp", "Spring", "Inertia", "Damper", "Friction" or "Custom"
	 * 
	 * @see #getEffectType()
	 */
	public abstract String getName();

		

	/**
	 * Gets the default direction. The default direction is used for all
	 * Effect objects that are initialized without a direction specified.
	 * If nothing is set, the "default" defaultDirection is PolarDirection.NORTHWEST.
	 * 
	 * @return the default Direction
	 * 
	 * @see #setDefaultDirection(Direction)
	 */
	public static Direction getDefaultDirection(){
		return defaultDirection;
	}
	
	/**
	 * Sets the default direction. The default direction is used for all
	 * Effect objects that are initialized without a direction specified.
	 * 
	 * @param direction
	 *            the direction object
	 * 
	 * @return true, if successful. false, otherwise. 
	 * 
	 * @see #getDefaultDirection()
	 */
	public static boolean setDefaultDirection(Direction direction){
		if (direction != null) {
			defaultDirection = direction;
			return true;
		} else {
			createFeatureNotSupportedEventDirectionIsNull();
			return false;
		}
	}
	
	
	
	protected static void createFeatureNotSupportedEvent(int value) {
		FeatureNotSupportedEventManager.featureNotSupportedEventOccured(new FeatureNotSupportedEvent(-1, FeatureNotSupportedEvent.EFFECTVALUE, value));
	}
	
	protected static void createFeatureNotSupportedEventDirectionIsNull() {
		FeatureNotSupportedEventManager.featureNotSupportedEventOccured(new FeatureNotSupportedEvent(-1, FeatureNotSupportedEvent.DIRECTION_IS_NULL, -1));
	}
	
}