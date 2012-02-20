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

/**
 * The Class FeatureNotSupportedEvent. A FeatureNotSupportedEvent occurs
 * when a method is called with an illegal parameter, like requesting a joystick
 * that does not exist, asking for a button's state which does not even exist,
 *  setting gain on a device that does not support it, trying to set a parameter
 *  for an FF effect that is not allowed, etc.<br>
 *  <br>
 *  It is strongly recommended to listen to FeatureNotSupportedEvents, at least during development.
 *  
 *  @see FeatureNotSupportedEventManager
 *  @see FeatureNotSupportedEventListener
 * 
 * @author Martin Wischenbart
 */
public class FeatureNotSupportedEvent {

	/**
	 * The constant GAIN: device does not support setting gain.
	 */
	public static final int GAIN = 821;
	
	/**
	 * The constant GAINVALUE: illegal value for setting gain was submitted.
	 */
	public static final int GAINVALUE = 822;
	
	/**
	 * The constant AUTOCENTER: device does not support setting autocenter.
	 */
	public static final int AUTOCENTER = 823;
	
	/**
	 * The constant AUTOCENTERVALUE: illegal value for setting autocenter was submitted.
	 */
	public static final int AUTOCENTERVALUE = 824;
	
	/**
	 * The constant PAUSE: device does not support pause/unpause.
	 */
	public static final int PAUSE = 827;
	
	/**
	 * The constant EFFECT: device does not support the submitted type of effect.
	 */
	public static final int EFFECT = 831;
	
	/**
	 * The constant EFFECTVALUE: illegal value or array size for effect property was submitted.
	 */
	public static final int EFFECTVALUE = 832;
	
	/**
	 * The constant DIRECTION_IS_NULL: null was submitted, while it should have been an instance of Direction.
	 */
	public static final int DIRECTION_IS_NULL = 837;
	
	/**
	 * The constant DIRECTIONVALUE: illegal value or array size for direction property was submitted.
	 */
	public static final int DIRECTIONVALUE = 838;

	
	
	
	
	
	private int deviceIndex;
	private int feature;
	private int value;

	/**
	 * Instantiates a new FeatureNotSupportedEvent.
	 * 
	 * @param deviceIndex
	 *            the index of the Joystick where the event occured
	 * @param feature
	 *            the feature that is not supported (one of the constants defined in this class)
	 * @param value
	 *            the value for the feature that is not supported (not always used)
	 */
	public FeatureNotSupportedEvent(int deviceIndex, int feature, int value) {
		super();
		this.deviceIndex = deviceIndex;
		this.feature = feature;
		this.value = value;
	}

	/**
	 * Gets the the index of the Joystick where the event occured.
	 * 
	 * @return the device index where the event occured
	 */
	public int getDeviceIndex() {
		return deviceIndex;
	}
	
	/**
	 * Gets the feature that is not supported. That is one of the constants that are defined in this class.
	 * 
	 * @return the feature constant (one of the constants defined in this class)
	 */
	public int getFeature() {
		return feature;
	}

	/**
	 * Gets the value for the feature that is not supported (not always used).
	 * 
	 * @return the value for the feature that is not supported (usually 0 if not used)
	 */
	public int getValue() {
		return value;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	/**
	 * 
	 * Returns a String describing the FeatureNotSupportedEvent. Examples: "Feature not supported: Joystick 0 Button -5 does not exist.", "Feature not supported: Joystick 1 does not support setting autocenter."
	 * 
	 * @return a String describing the FeatureNotSupportedEvent
	 */
	@Override
	public String toString(){
		String fs;
		switch (feature){
		case (AdvancedControllerEvent.BUTTON): {
			fs = "Joystick "+deviceIndex+" Button "+value+" does not exist.";
			break; }
		case (AdvancedControllerEvent.AXIS): {
			fs = "Joystick "+deviceIndex+" Axis "+value+" does not exist.";
			break; }
		case (AdvancedControllerEvent.POV): {
			fs = "Joystick "+deviceIndex+" POV "+value+" does not exist.";
			break; }
		case (AdvancedControllerEvent.BALL): {
			fs = "Joystick "+deviceIndex+" Trackball "+value+" does not exist.";
			break; }
		case (GAIN): {
			fs = "Joystick "+deviceIndex+" does not support setting gain.";
			break; }
		case (AUTOCENTER): {
			fs = "Joystick "+deviceIndex+" does not support setting autocenter.";
			break; }
		case (GAINVALUE): {
			fs = "Joystick "+deviceIndex+" - illegal value for setting gain: "+value;
			break; }
		case (AUTOCENTERVALUE): {
			fs = "Joystick "+deviceIndex+" - illegal value for setting autocenter: "+value;
			break; }
		case (PAUSE): {
			fs = "Joystick "+deviceIndex+" does not support pause/unpause.";
			break; }
		case (DIRECTIONVALUE): {
			fs = "Illegal value or array size for direction property: "+value+".";
			break; }
		case (DIRECTION_IS_NULL): {
			fs = "Direction must not be NULL.";
			break; }
		case (EFFECT): {
			fs = "Type of effect ("+value+") not supported by joystick "+deviceIndex+".";
			break; }
		case (EFFECTVALUE): {
			fs = "Illegal value or array size for effect property: "+value+".";
			break; }
		default: {
			fs = "Unknown error."; }
		}
		String s = "Feature not supported: "+fs;
		return s;
	}



}
