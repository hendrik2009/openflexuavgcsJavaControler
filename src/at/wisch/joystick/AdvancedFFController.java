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

import java.util.HashSet;

import at.wisch.joystick.ffeffect.*;

/**
 * The interface AdvancedFFController. It extends AdvancedController to contain all the necessary methods for Force Feedback (FF) controllers.
 * 
 * @author Martin Wischenbart
 */
public interface AdvancedFFController extends AdvancedController {

	/**
	 * Gets a HashSet of supported effects by this controller.
	 * 
	 * @return a HashSet containing the classes of supported effects
	 * @see HashSet
	 * @see Effect
	 * 
	 */
	public HashSet<Class<? extends Effect>> getSupportedEffects();
	
	/**
	 * Checks if an effect is supported.
	 * 
	 * @param effect
	 *            an Effect instance
	 * 
	 * @return true, if the Effect is supported by this controller. false, otherwise.
	 * @see Effect
	 */
	public boolean isEffectSupported(Effect effect);
	
	/**
	 * Checks if an effect is supported.
	 * 
	 * @param effectClass
	 *            an Effect class
	 * 
	 * @return true, if the Effect is supported by this controller. false, otherwise.
	 * 
	 * @see Effect
	 */
	public boolean isEffectSupported(Class<? extends Effect> effectClass);
	
	/**
	 * Checks if is querying the effect status is supported.
	 * 
	 * @return true, if is querying effect status supported. false, otherwise.
	 * 
	 * @see Effect
	 */
	public boolean isQueryingEffectStatusSupported();
	
	/**
	 * Gets the number of FF axes a device has. For most controllers that is two. For a flight simulator joystick the axes usually correspond to the X and Y axes of the stick. For a gamepad the axes correspond to rumble motors.
	 * 
	 *  @see at.wisch.joystick.ffeffect.direction.Direction
	 * 
	 * @return the number of FF axes the device has
	 */
	public int getNumOfFFAxes();
	
	/**
	 * Gets the number of effects a device can store.
	 * 
	 * @return the number of storable effects
	 * @see #getPlayableNumOfEffects()
	 */
	public int getStorableNumOfEffects();
	
	/**
	 * Gets the number of effects a device can play simultaneously.
	 * 
	 * @return the number of effects playable at once
	 * @see #getStorableNumOfEffects()
	 */
	public int getPlayableNumOfEffects();
	
	/**
	 * Checks if a device supports setting gain. Gain is a factor to scale the strength of all FF effects on a device. By default the gain is set to 100 (%), which is the maximum (DEFAULT_GAIN_VALUE).
	 * 
	 * @return true, if is gain supported. false, otherwise.
	 * 
	 * @see #setGain(int)
	 * @see #getGain()
	 */
	public boolean isGainSupported();
	
	/**
	 * Gets the currently set gain value.
	 * 
	 * @return the currently set gain (value from 0 to 100)
	 * 
	 * @see #setGain(int)
	 * @see #isGainSupported()
	 */
	public int getGain();
	
	/**
	 * Sets the gain value in %. Change the gain to scale the strength of all FF effects on this device.
	 * 
	 * @param gainValue
	 *            the % gain value: any value from 0 to 100
	 * 
	 * @return true, if successful. false, if not successful or if setting gain is not supported.
	 * @see #isGainSupported()
	 * @see #getGain()
	 */
	public boolean setGain(int gainValue) ;
	
	/**
	 * Checks if auto centering is supported by the device. Auto centering means that a force towards the center of the stick is active at all times. By default autocenter is disabled, i.e. set to 0 (%), which is the minimum (DEFAULT_AUTOCENTER_VALUE).
	 * 
	 * @return true, if autocenter is supported. false, otherwise.
	 * @see #getAutoCenter()
	 * @see #setAutoCenter(int)
	 */
	public boolean isAutocenterSupported();
	
	/**
	 * Gets the currently set autocenter value.
	 * 
	 * @return the currently set autocenter value (value from 0 to 100)
	 * 
	 * @see #isAutocenterSupported()
	 * @see #setAutoCenter(int)
	 */
	public int getAutoCenter();
	
	/**
	 * Sets the auto center value in %. Set auto centering to enable a force towards the center of the stick on this device.
	 * 
	 * @param autocenterValue
	 *            the % autocenter strength: a value from 0 to 100
	 * 
	 * @return true, if successful. false, if not successful or if setting autocenter is not supported.
	 * 
	 * @see #isAutocenterSupported()
	 * @see #getAutoCenter()
	 */
	public boolean setAutoCenter(int autocenterValue) ;
	
	/**
	 * Checks if the device supports pause/unpause for played effects.
	 * 
	 * @return true, if is pause/unpause supported. false, otherwise.
	 * 
	 * @see #pause()
	 * @see #unpause()
	 * @see #isPaused()
	 */
	public boolean isPauseSupported();
	
	/**
	 * Pause all effects that are currently playing.
	 * 
	 * @return true, if successful. false, if not successful or if pause/unpause is not supported.
 	 * @see #isPauseSupported()
	 * @see #unpause()
	 * @see #isPaused()

	 */
	public boolean pause() ;
	
	/**
	 * Resume all effects that were playing before {@link #pause()} was called.
	 * 
	 * @return true, if successful. false, if not successful or if pause/unpause is not supported.
	 * @see #isPauseSupported()
	 * @see #pause()
	 * @see #isPaused()

	 */
	public boolean unpause();
	
	/**
	 * Checks if the device is currently paused.
	 * 
	 * @return true, if the device is paused. false if the device is not paused or if pause/unpause is not supported.
	 * @see #isPauseSupported()
	 * @see #pause()
	 * @see #unpause()
	 * 
	 */
	public boolean isPaused();
	
	/**
	 * Upload a new effect to the device.
	 * 
	 * @param effect
	 *            the Effect
	 * 
	 * @return true, if successful. false, if not successful or if the effect is not supported by this device.
	 * 
	 * @see #isEffectSupported(Effect)
	 * @see #playEffect(Effect, int)
	 * @see #stopEffect(Effect)
	 * @see #destroyEffect(Effect)
	 */
	public boolean newEffect(Effect effect); // upload effect to device
	
	/**
	 * Playback an effect on the device. If the Effect was not uploaded before, it will be uploaded and played immediately.
	 * 
	 * @param effect
	 *            the Effect to be played
	 * @param iterations
	 *            the number of iterations (i.e. how often to play the effect): any positive integer, or INFINITE_TIMES
	 * 
	 * @return true, if successful. false, if not successful or if the effect is not supported by this device.
	 * 
	 *  @see #newEffect(Effect)
	 *  @see #updateEffect(Effect)
	 *  @see #stopEffect(Effect)
	 *  @see #pause()
	 *  @see #unpause()
	 */
	public boolean playEffect(Effect effect, int iterations) ;
	
	/**
	 * Checks if an effect is currently playing.
	 * 
	 * @param effect
	 *            the Effect to check
	 * 
	 * @return true, if the effect is playing at the moment. false, if the effect was not uploaded before or if it is not supported.
	 * 
	 * @see #playEffect(Effect, int)
	 * @see #stopEffect(Effect)
	 * @see #pause()
	 * @see #unpause()
	 *  
	 */
	public boolean isPlaying(Effect effect) ;
	
	/**
	 * Update an effect which was already uploaded to the device. If the Effect was not uploaded before, it will be uploaded. If you change the properties of an effect, you 
	 * always have to call updateEffect
	 * so the changes will be stored on the device. You can update an effect while it is playing, but be aware that sometimes it causes the
	 * effect to restart (i.e. play from the beginning).
	 * 
	 * @param effect
	 *            the Effect to be updated
	 * 
	 * @return true, if successful. false, if not successful or if the effect is not supported by this device.
	 * @see #newEffect(Effect)
	 * @see #playEffect(Effect, int)
	 */
	public boolean updateEffect(Effect effect) ;
	
	/**
	 * Stop an effect which is currently on the device. It does not matter if it is actually played at the moment or not.
	 * 
	 * @param effect
	 *            the Effect to be stopped
	 * 
	 * @return true, if the Effect was stopped (no matter if it was actually being played). false, if the Effect was not on the device or stopping was not successful.
	 * 
	 * @see #stopAll()
	 * @see #destroyEffect(Effect)
	 */
	public boolean stopEffect(Effect effect) ;
	
	/**
	 * Destroy effect: remove an effect from the device. It does not matter if it is being played or not. The Effect object will remain intact and can be uploaded again later or onto other devices. If effects are being played during destruction, they will stop.
	 * 
	 * @param effect
	 *            the Effect to be removed from the device 
	 * 
	 * @return true, if successful. false, if the Effect was not on the device or destroying was not successful.
	 * 
	 * @see #destroyAll()
	 * @see #stopEffect(Effect)
	 */
	public boolean destroyEffect(Effect effect) ; 
	
	/**
	 * Stop all effects that are currently stored on the device. It does not matter how many effects that are and no matter if any of them are being played or not.
	 * 
	 * @return true, if successful. false, if not successful.
	 * @see #stopEffect(Effect)
	 * @see #destroyAll()
	 */
	public boolean stopAll();
	
	/**
	 * Destroy all effects: remove all effects, that are currently stored on the device. It does not matter if they are being played or not. If effects are being played during destruction, they will stop.
	 * 
	 * @return true, if successful. false, if not successful.
	 * @see #destroyEffect(Effect)
	 * @see #stopAll()
	 */
	public boolean destroyAll() ;
	
}