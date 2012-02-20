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

import java.util.HashMap;
import java.util.HashSet;
import at.wisch.joystick.event.FeatureNotSupportedEvent;
import at.wisch.joystick.exception.*;
import at.wisch.joystick.ffeffect.*;

/**
 * The class FFJoystick. This is the class that actually represents FFJoystick
 * objects, which are necessary for playing force feedback effects on a Joystick. I also inherits all the capabilities for input handling from {@link Joystick}.<br>
 * <br>
 * Attention: do not instantiate FFJoystick objects yourself. Aquire FFJoysticks
 * using {@link JoystickManager}.
 * 
 * @author Martin Wischenbart
 */
public class FFJoystick extends Joystick implements AdvancedFFController{
	
	// capabilities/properties (stored in Java Object) - once the FFJoystick is initialized, they cannot change
	private int hapticDeviceIndex;
	private HashSet<Class<? extends Effect>> supportedEffects;
	private int numOfFFAxes;
	private int playableNumOfEffects;
	private int storableNumOfEffects;
	private boolean queryingEffectSupported;
	private boolean gainSupported;
	private boolean autocenterSupported;
	private boolean pauseSupported;
	
	// effects/settings that can change at runtime
	private int gainValue;
	private int autocenterValue;
	private boolean isPaused;
	private HashMap<Effect, Integer> storedEffects; //effects currently on the device //key = the index the effect has on the device

	
	/* 
	 * This constructor should not be called by the application programmer.
	 * To obtain a ff-joystick object use JoystickMangager.getFFJoystick();
	 */
	
	protected FFJoystick(int joystickIndex, int hapticDeviceIndex) throws FFJoystickException {
		
		super(joystickIndex);

		this.hapticDeviceIndex = hapticDeviceIndex;
		this.isFFJoystick = true;
		setFFCapabilities();
		this.numOfFFAxes = getNumOfAxesNative(hapticDeviceIndex);
		this.playableNumOfEffects = getPlayNumOfEffectsNative(hapticDeviceIndex);
		this.storableNumOfEffects = getStoreNumOfEffectsNative(hapticDeviceIndex);

		gainValue = DEFAULT_GAIN_VALUE; // maximum
		autocenterValue = DEFAULT_AUTOCENTER_VALUE; // disabled
		isPaused = false; // unpaused
		
		this.storedEffects = new HashMap<Effect, Integer>();
		
	}
	
	/* (non-Javadoc)
	 * @see at.wisch.joystick.Joystick#toString()
	 */
	/**
	 * Gets a string containing the joystick's name and the description of the input and FF capabilities.
	 * 
	 * @return the joystick's name and a the description of the input and FF capabilities
	 * @see #getName()
	 * @see #getDescription()
	 * @see #getFFDescription()
	 */
	@Override
	public String toString() {
		return (getName()+": "+getDescription()+", "+getFFDescription());
	}

	/**
	 * Gets a short description of the joystick's capabilities for FF. This includes supported effects and if pause/unpause, setting gain and setting autocenter are supported.
	 * 
	 * @return the String containing the joystick's capabilities for input
	 * 
	 * @see #toString()
	 * @see #getSupportedEffects()
	 * @see #isPauseSupported()
	 * @see #isGainSupported()
	 * @see #isAutocenterSupported()
	 */
	public String getFFDescription() {
		return "effects: "+this.supportedEffects.size()+
		", FF-axes: "+this.numOfFFAxes+  // commented out because it is not really supported on all OS
		((this.isPauseSupported())? (", pausable") : (""))+
		((this.isGainSupported())? (", gain settable") : (""))+
		((this.isAutocenterSupported())? (", autocenter settable") : (""));
	}

	// #############################################################################
	
	// called only once for each FFJoystick: at initialization
	@SuppressWarnings("deprecation")
	private void setFFCapabilities() throws FFJoystickException {
		
		boolean[] capabilities = getFFCapabilitiesNative(hapticDeviceIndex);
		/*
		 * This boolean[] describes the capabilities for FF effects:
		 * EFFECTS:
		 * Index 0:		Constant effect supported
		 * Index 1:		Sine wave effect supported
		 * Index 2:		Square wave effect supported
		 * Index 3:		Triangle wave effect supported
		 * Index 4:		Sawtoothup wave effect supported
		 * Index 5:		Sawtoothdown wave effect supported
		 * Index 6:		Ramp effect supported
		 * Index 7:		Spring effect supported - uses axes position
		 * Index 8:		Damper effect supported - uses axes velocity
		 * Index 9:		Inertia effect supported - uses axes acceleration
		 * Index 10:	Friction effect supported - uses axes movement
		 * Index 11:	Custom effect is supported
		 * OTHER:
		 * Index 12:	Device can set global gain
		 * Index 13:	Device can set autocenter
		 * Index 14:	Device can be queried for effect status
		 * Index 15:	Device can be paused.
		 */
		
		supportedEffects = new HashSet<Class<? extends Effect>>();
		
		if (capabilities[0]) supportedEffects.add(ConstantEffect.class);
		if (capabilities[1]) supportedEffects.add(SineEffect.class);
		if (capabilities[2]) supportedEffects.add(SquareEffect.class);
		if (capabilities[3]) supportedEffects.add(TriangleEffect.class);
		if (capabilities[4]) supportedEffects.add(SawtoothUpEffect.class);
		if (capabilities[5]) supportedEffects.add(SawtoothDownEffect.class);
		if (capabilities[6]) supportedEffects.add(RampEffect.class);
		if (capabilities[7]) supportedEffects.add(SpringEffect.class);
		if (capabilities[8]) supportedEffects.add(DamperEffect.class);
		if (capabilities[9]) supportedEffects.add(InertiaEffect.class);
		if (capabilities[10]) supportedEffects.add(FrictionEffect.class);
		if (capabilities[11]) supportedEffects.add(CustomEffect.class);
		
		if (capabilities[12]) {
			this.gainSupported = true;
		} else {
			this.gainSupported = false;
		}
		if (capabilities[13]) {
			this.autocenterSupported = true;
		} else {
			this.autocenterSupported = false;
		}
		if (capabilities[14]) {
			this.queryingEffectSupported = true;
		} else {
			this.queryingEffectSupported = false;
		}
		if (capabilities[15]) {
			this.pauseSupported = true;
		} else {
			this.pauseSupported = false;
		}
		
	}
	private static native boolean[] getFFCapabilitiesNative(int hapticDeviceIndex) throws FFJoystickException;

	// ############################## SUPPORTED EFFECTS ###########################
	
	/* (non-Javadoc)
	 * @see at.wisch.joystick.AdvancedFFController#getSupportedEffects()
	 */
	/**
	 * {@inheritDoc}
	 */
	@Override
	public HashSet<Class<? extends Effect>> getSupportedEffects() {
		return this.supportedEffects;
	}

	/* (non-Javadoc)
	 * @see at.wisch.joystick.AdvancedFFController#isEffectSupported(at.wisch.joystick.ffeffect.Effect)
	 */
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isEffectSupported(Effect effect) {
		return isEffectSupported(effect.getClass());
	}
	
	/* (non-Javadoc)
	 * @see at.wisch.joystick.AdvancedFFController#isEffectSupported(java.lang.Class)
	 */
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isEffectSupported(Class<? extends Effect> effectClass) {
		return supportedEffects.contains(effectClass);
	}

	/* (non-Javadoc)
	 * @see at.wisch.joystick.AdvancedFFController#isQueryingEffectStatusSupported()
	 */
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isQueryingEffectStatusSupported() {
		return this.queryingEffectSupported;
	}
	
	/* LINUX: naxes = 2; --- "Hardcoded for now, not sure if it's possible to find out." */
	/* (non-Javadoc)
	 * @see at.wisch.joystick.AdvancedFFController#getNumOfFFAxes()
	 */
	 /** 
	  * {@inheritDoc}
	  * @deprecated on some operating systems this method will return a fixed value for any joystick. To be really platform independent work without using it. Simply assume the joystick has 2 FF axes.
	  */
	@Override
	@Deprecated
	public int getNumOfFFAxes() {
		return numOfFFAxes;
	}
	private static native int getNumOfAxesNative(int hapticDeviceIndex) throws FFJoystickException;
	
	
	/* This is not actually supported as thus under windows,
    there is no way to tell the number of EFFECTS that a
    device can hold, so we'll just use a "random" number
    instead and put warnings in SDL_haptic.h */
	/* (non-Javadoc)
	 * @see at.wisch.joystick.AdvancedFFController#getPlayableNumOfEffects()
	 */
	/** 
	  * {@inheritDoc}
	  * @deprecated on some operating systems this method will return a fixed value for any joystick. To be really platform independent work without using it. Simply assume the joystick can play only few effects at once(~ around 10).
	  */
	@Override
	@Deprecated
	public int getPlayableNumOfEffects() {
		return playableNumOfEffects;
	}
	private static native int getPlayNumOfEffectsNative(int hapticDeviceIndex) throws FFJoystickException;


	
	/* This is not actually supported as thus under windows,
    there is no way to tell the number of EFFECTS that a
    device can hold, so we'll just use a "random" number
    instead and put warnings in SDL_haptic.h */
	/* (non-Javadoc)
	 * @see at.wisch.joystick.AdvancedFFController#getStorableNumOfEffects()
	 */
	/** 
	  * {@inheritDoc}
	  * @deprecated on some operating systems this method will return a fixed value for any joystick. To be really platform independent work without using it. Simply assume the joystick can store only few effects (~ around 10).
	  */
	@Override
	@Deprecated
	public int getStorableNumOfEffects() {
		return storableNumOfEffects;
	}	
	private static native int getStoreNumOfEffectsNative(int hapticDeviceIndex) throws FFJoystickException;
	
	
	// ########################### GAIN ###########################
	
	/* (non-Javadoc)
	 * @see at.wisch.joystick.AdvancedFFController#isGainSupported()
	 */
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isGainSupported() {
		return this.gainSupported;
	}
	
	/* (non-Javadoc)
	 * @see at.wisch.joystick.AdvancedFFController#getGain()
	 */
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getGain() {
		if (!isGainSupported()) {
			createFeatureNotSupportedEvent(FeatureNotSupportedEvent.GAIN, 0);
		}
		return gainValue;
	}
	
	/* (non-Javadoc)
	 * @see at.wisch.joystick.AdvancedFFController#setGain(int)
	 */
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean setGain(int gainValue) {
		if (!isGainSupported()) {
			createFeatureNotSupportedEvent(FeatureNotSupportedEvent.GAIN, 0);
			return false;
		}
		boolean success = true;
		if (gainValue < 0 || gainValue > 100) {
			createFeatureNotSupportedEvent(FeatureNotSupportedEvent.GAINVALUE, gainValue);
			gainValue = DEFAULT_GAIN_VALUE;
			success = false;
		}
		this.gainValue = gainValue;
		try {
			setGainNative(hapticDeviceIndex, gainValue);
		} catch (SDLErrorException e) {
			e.printErrorMessage();
			success = false;
		}
		return success;
	}
	
	private static native int setGainNative(int hapticDeviceIndex, int gainValue) throws SDLErrorException;
	
// ########################### AUTOCENTER ###########################
	
	/* (non-Javadoc)
     * @see at.wisch.joystick.AdvancedFFController#isAutocenterSupported()
     */
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isAutocenterSupported() {
		return this.autocenterSupported;
	}
	
	/* (non-Javadoc)
	 * @see at.wisch.joystick.AdvancedFFController#getAutoCenter()
	 */
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getAutoCenter() {
		if (!isAutocenterSupported()) {
			createFeatureNotSupportedEvent(FeatureNotSupportedEvent.AUTOCENTER, 0);
		}
		return autocenterValue;
	}

	/* (non-Javadoc)
	 * @see at.wisch.joystick.AdvancedFFController#setAutoCenter(int)
	 */
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean setAutoCenter(int autocenterValue)  {
		if (!isAutocenterSupported()) {
			createFeatureNotSupportedEvent(FeatureNotSupportedEvent.AUTOCENTER, 0);
			return false;
		}
		boolean success = true;
		if (autocenterValue < 0 || autocenterValue > 100) {
			createFeatureNotSupportedEvent(FeatureNotSupportedEvent.AUTOCENTERVALUE, autocenterValue);
			autocenterValue = DEFAULT_AUTOCENTER_VALUE;
			success = false;
		}
		this.autocenterValue = autocenterValue; 
		try {
			setAutocenterNative(hapticDeviceIndex, autocenterValue);
		} catch (FFJoystickException e) {
			e.printErrorMessage();
			success = false;
		}
		return success;
	}
	private static native int setAutocenterNative(int hapticDeviceIndex, int autocenterValue) throws FFJoystickException;

	
	// ########################### PAUSE ###########################
	
	
	/*
	 * (Un)pausing is not supported atm by linux.
	 */
	/* (non-Javadoc)
	 * @see at.wisch.joystick.AdvancedFFController#isPauseSupported()
	 */
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isPauseSupported() {
		return this.pauseSupported;
	}
	
	/* (non-Javadoc)
	 * @see at.wisch.joystick.AdvancedFFController#pause()
	 */
	/** 
	  * {@inheritDoc}
	  * @deprecated on some operating systems pause/unpause is never supported for any joystick. To be really platform independent work without pause/unpause.
	  */
	@Override
	@Deprecated
	public boolean pause() {
		if(isPauseSupported()) {
			isPaused = true;
			try {
				pause(hapticDeviceIndex);
			} catch (FFJoystickException e) {
				e.printErrorMessage();
				return false;
			}
			return true;
		} else {
			createFeatureNotSupportedEvent(FeatureNotSupportedEvent.PAUSE, 0);
			return false;
		}
	}
	private static native int pause(int hapticDeviceIndex) throws FFJoystickException;
	
	/* (non-Javadoc)
	 * @see at.wisch.joystick.AdvancedFFController#unpause()
	 */
	/** 
	  * {@inheritDoc}
	  * @deprecated on some operating systems pause/unpause is never supported for any joystick. To be really platform independent work without pause/unpause.
	  */
	@Override
	@Deprecated
	public boolean unpause() {
		if(isPauseSupported()) {
			isPaused = false;
			try {
				unpause(hapticDeviceIndex);
			} catch (FFJoystickException e) {
				e.printErrorMessage();
				return false;
			}
			return true;
		} else {
			createFeatureNotSupportedEvent(FeatureNotSupportedEvent.PAUSE, 0);
			return false;
		}
	}
	private static native int unpause(int hapticDeviceIndex) throws FFJoystickException;
	
	/* (non-Javadoc)
	 * @see at.wisch.joystick.AdvancedFFController#isPaused()
	 */
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isPaused() {
		if (!isPauseSupported()){
			createFeatureNotSupportedEvent(FeatureNotSupportedEvent.PAUSE, 0);
		}
		return this.isPaused;
	}


	// ############################# SIMPLE EFFECTS #############################

	
	// hopefully every FF device supports one of these effects
	/**
	 * Gets a simple effect. We assume that every FFJoystick supports an ExtendedEffect. This method returns one of the following:
	 * {@link ConstantEffect}, {@link RampEffect}, {@link SineEffect}, {@link SquareEffect}, {@link TriangleEffect}, 
	 * {@link SawtoothUpEffect}, {@link SawtoothDownEffect},   
	 * 
	 * @return a simple effect of type {@link ExtendedEffect} or null if none is supported
	 */
	public ExtendedEffect getSimpleEffect() {
		if (this.isEffectSupported(ConstantEffect.class)){
			return new ConstantEffect();
		} else if (this.isEffectSupported(RampEffect.class)){
			return new RampEffect();
		}else if (this.isEffectSupported(SineEffect.class)){
			return new SineEffect();
		}else if (this.isEffectSupported(SquareEffect.class)){
			return new SquareEffect();
		}else if (this.isEffectSupported(TriangleEffect.class)){
			return new TriangleEffect();
		} else if (this.isEffectSupported(SawtoothUpEffect.class)){
			return new SawtoothUpEffect();
		} else if (this.isEffectSupported(SawtoothDownEffect.class)){
			return new SawtoothDownEffect();
		} else {
			return null;
		}
	}
	

	// ############################# HANDLING EFFECTS #############################
	
	
	
	private int getEffectIndex(Effect effect) {
		Integer index = this.storedEffects.get(effect);
		if (index == null) return -1;
		return this.storedEffects.get(effect).intValue();
	}
	
	/* (non-Javadoc)
	 * @see at.wisch.joystick.AdvancedFFController#newEffect(at.wisch.joystick.ffeffect.Effect)
	 */
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean newEffect(Effect effect) {
		if (!isEffectSupported(effect)) {
			createFeatureNotSupportedEvent(FeatureNotSupportedEvent.EFFECT, effect.getEffectType());
			return false;
		}
		int index;
		try {
			index = newEffectNative(hapticDeviceIndex, effect, effect.getEffectType());
		} catch (SDLErrorException e) {
			e.printErrorMessage();
			return false;
		}
		this.storedEffects.put(effect, Integer.valueOf(index));
		return true;
	}
	private static native int newEffectNative(int hapticDeviceIndex, Effect effect, int effectType) throws SDLErrorException;
	
	/* (non-Javadoc)
	 * @see at.wisch.joystick.AdvancedFFController#playEffect(at.wisch.joystick.ffeffect.Effect, int)
	 */
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean playEffect(Effect effect, int iterations) {
		int index = getEffectIndex(effect);
		if (index < 0) { // effect not stored yet
			newEffect(effect);
			index = getEffectIndex(effect);
			if (index < 0) { // still not stored (should never happen)
				return false;
			}
		}
		try {
			playEffectNative(this.hapticDeviceIndex, index, iterations);
		} catch (SDLErrorException e) {
			e.printErrorMessage();
			return false;
		}
		return true;
	}
	private static native int playEffectNative(int hapticDeviceIndex, int effectIndex, int iterations) throws SDLErrorException;
	
	/* (non-Javadoc)
	 * @see at.wisch.joystick.AdvancedFFController#isPlaying(at.wisch.joystick.ffeffect.Effect)
	 */
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isPlaying(Effect effect) {
		int index = getEffectIndex(effect);
		if (index < 0) {
			return false;
		}
		try {
			return getEffectStatusNative(this.hapticDeviceIndex, index);
		} catch (SDLErrorException e) {
			e.printErrorMessage();
			return false;
		}
	}
	private static native boolean getEffectStatusNative(int hapticDeviceIndex, int effectIndex) throws SDLErrorException;

	/* (non-Javadoc)
	 * @see at.wisch.joystick.AdvancedFFController#updateEffect(at.wisch.joystick.ffeffect.Effect)
	 */
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean updateEffect(Effect effect) {
		int index = getEffectIndex(effect);
		if (index < 0) { // effect not stored yet
			newEffect(effect);
			index = getEffectIndex(effect);
			if (index < 0) { // still not stored (should never happen)
				return false;
			}
			return true;
		} else {
			try {
				updateEffectNative(hapticDeviceIndex, index, effect, effect.getEffectType());
			} catch (SDLErrorException e) {
				e.printErrorMessage();
				return false;
			}		
			return true;
		}
	}
	
	private static native int updateEffectNative(int hapticDeviceIndex, int effectIndex, Effect effect, int effectType) throws SDLErrorException;

	/* (non-Javadoc)
	 * @see at.wisch.joystick.AdvancedFFController#stopEffect(at.wisch.joystick.ffeffect.Effect)
	 */
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean stopEffect(Effect effect) {
		try {
			int index = getEffectIndex(effect);
			if (index < 0) { //effect is not on the device
				return false;
			}
			stopEffectNative(hapticDeviceIndex, index);
		} catch (SDLErrorException e) {
			e.printErrorMessage();
			return false;
		}
		return true;
	}
	private static native int stopEffectNative(int hapticDeviceIndex, int effectIndex) throws SDLErrorException;
	
	/* (non-Javadoc)
	 * @see at.wisch.joystick.AdvancedFFController#destroyEffect(at.wisch.joystick.ffeffect.Effect)
	 */
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean destroyEffect(Effect effect) {
		int effectIndex = getEffectIndex(effect);
		if (effectIndex < 0) { //effect is not on the device
			return false;
		}
		this.storedEffects.remove(effect);
		destroyEffectNative(this.hapticDeviceIndex, effectIndex);
		return true;
	}
	private static native void destroyEffectNative(int hapticDeviceIndex, int effectIndex);

	
	/* (non-Javadoc)
	 * @see at.wisch.joystick.AdvancedFFController#stopAll()
	 */
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean stopAll() {
		try {
			stopAllNative(hapticDeviceIndex);
		} catch (SDLErrorException e) {
			e.printErrorMessage();
			return false;
		}
		return true;
	}
	private static native int stopAllNative(int hapticDeviceIndex) throws SDLErrorException;
	
	/* (non-Javadoc)
	 * @see at.wisch.joystick.AdvancedFFController#destroyAll()
	 */
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean destroyAll() {
		destroyAllNative(hapticDeviceIndex);
		this.storedEffects.clear();
		return true;
	}
	private static native void destroyAllNative(int hapticDeviceIndex);
	

}
