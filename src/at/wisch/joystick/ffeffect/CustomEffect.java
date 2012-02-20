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

import at.wisch.joystick.ffeffect.direction.Direction;
import at.wisch.joystick.ffeffect.direction.PolarDirection;

/**
 * The Class CustomEffect. A custom force feedback effect is much like a
 * periodic effect, where the application can define it's exact shape.<br>
 * <br>
 * If channels is one, the effect is rotated using the defined direction.
 * Otherwise it uses the samples in data for the different axes. In other words:
 * If there is only a single channel, the effect is rotated in the direction
 * specified by Direction. If there is more than one channel, rotation is not
 * applied. Note: Not all devices support rotation of custom effects.<br>
 * <br>
 * The first channel is applied to the first axis associated with the effect,
 * the second to the second, and so on. If there are fewer channels than axes,
 * nothing is associated with the extra axes. If multiple channels are provided,
 * the data values are interleaved. For example, if channelsToUse is 3, the
 * first element of the array belongs to the first channel, the second to the
 * second, and the third to the third.<br>
 * 
 * @deprecated CustomEffect is not supported for all controllers on all 
 * 			   operating systems. Use a series of other effects instead.
 * 
 * @author Martin Wischenbart
 */
@Deprecated
public class CustomEffect extends ExtendedEffect {

	private int period;          // Sample period. (in ms)
	private int channelsToUse;   // Axes to use, minimum one.
	private int numOfSamples;    // Amount of samples. (If channelsToUse == 1, numOfSamples is the length of the array 'data'.
	private int[] data;          // Length is be channelsToUse * numOfSamples.
	

	// constructor does not need channesToUse if a direction is submitted
	/**
	 * Instantiates a new CustomEffect with the specified parameters. This
	 * constructor has a parameter Direction, therefore channelsToUse is not
	 * needed an will be set to 1.
	 * 
	 * @param direction
	 *            a Direction object
	 * @param effectLength
	 *            the effect length in ms (or INFINITE_LENGTH)
	 * @param effectDelay
	 *            the delay before the effect starts in ms
	 * @param buttonIndex
	 *            the trigger button index (or NO_BUTTON)
	 * @param buttonInterval
	 *            the trigger button interval in ms
	 * @param attackLength
	 *            the attack length (fade-in) in ms
	 * @param fadeLength
	 *            the fade length (fade-out) in ms
	 * @param attackLevel
	 *            the level from where to start the fade-in
	 * @param fadeLevel
	 *            the level where the fade-out fades to
	 * @param period
	 *            the sample period in ms
	 * @param data
	 *            the data array (array size is numOfSamples, values are from 0
	 *            to MAX_LEVEL (32767))
	 *            
	 * @see #CustomEffect(int, int, int, int, int, int, int, int, int, int, int[]) 
	 *  
	 *            
	 */
	public CustomEffect(Direction direction,
			int effectLength, int effectDelay, int buttonIndex,
			int buttonInterval, int attackLength, int fadeLength,
			int attackLevel, int fadeLevel, int period, 
			int[] data) {
		super(EFFECT_CUSTOM, direction, effectLength, effectDelay, buttonIndex,
				buttonInterval, attackLength, fadeLength, attackLevel, fadeLevel);
		this.setPeriod(period);
		this.setSamples(data);
	}
	
	// constructor with channelsToUse >= 2 does not need direction
	/**
	 * Instantiates a new CustomEffect with the specified parameters. This
	 * constructor has a parameter channelsToUse, therefore Direction is not
	 * needed.
	 * 
	 * @param effectLength
	 *            the effect length in ms (or INFINITE_LENGTH)
	 * @param effectDelay
	 *            the delay before the effect starts in ms
	 * @param buttonIndex
	 *            the trigger button index (or NO_BUTTON)
	 * @param buttonInterval
	 *            the trigger button interval in ms
	 * @param attackLength
	 *            the attack length (fade-in) in ms
	 * @param fadeLength
	 *            the fade length (fade-out) in ms
	 * @param attackLevel
	 *            the level from where to start the fade-in
	 * @param fadeLevel
	 *            the level where the fade-out fades to
	 * @param period
	 *            the sample period in ms
	 * @param channelsToUse
	 *            the number of channels/axes to use (2 or more)
	 * @param data
	 *            the data array (array size is channelsToUse * numOfSamples,
	 *            values are from 0 to MAX_LEVEL (32767))
	 * @see #CustomEffect(Direction, int, int, int, int, int, int, int, int, int, int[])           
	 */
	public CustomEffect(
			int effectLength, int effectDelay, int buttonIndex,
			int buttonInterval, int attackLength, int fadeLength,
			int attackLevel, int fadeLevel, int period, int channelsToUse,
			int[] data) {
		super(EFFECT_CUSTOM, new PolarDirection(), effectLength, effectDelay, buttonIndex,
				buttonInterval, attackLength, fadeLength, attackLevel, fadeLevel);
		this.setPeriod(period);
		this.setSamples(data, channelsToUse);
	}
	

	/**
	 * Instantiates a new custom effect with default parameters, using 2
	 * channels/axes.
	 * 
	 * @see #CustomEffect(Direction)
	 */
	public CustomEffect() {
		this(6000, 0, NO_BUTTON , 0, 0, 0, 0, 0, 2000, 2,
				new int[]{MAX_LEVEL, MAX_LEVEL, 
							(int)(MAX_LEVEL*0.67), (int)(MAX_LEVEL*0.67),
							(int)(MAX_LEVEL*0.34), (int)(MAX_LEVEL*0.34)});
	}
		
		
	/**
	 * Instantiates a new custom effect with default parameters, using a
	 * Direction object.
	 * 
	 * @param direction
	 *            a Direction object
	 *            
	 * @see #CustomEffect()           
	 */
	public CustomEffect(Direction direction) {
		this(direction, 6000, 0, NO_BUTTON , 0, 0, 0, 0, 0, 2000, 
				new int[]{MAX_LEVEL, 
							(int)(MAX_LEVEL*0.67),
							(int)(MAX_LEVEL*0.34)});
	}
	
	
	
	/**
	 * Gets the sample period in ms.
	 * 
	 * @return the sample period in ms
	 * 
	 * @see #setPeriod(int)
	 */
	public int getPeriod() {
		return period;
	}

	/**
	 * Sets the sample period in ms.
	 * 
	 * @param period
	 *            the new sample period in ms (value from 0 to MAX_DELAY (60000))
	 *            
	 * @see #getPeriod()           
	 */
	public void setPeriod(int period) {
		if (period < 0) {
			createFeatureNotSupportedEvent(period);
			this.period = 100; // 100 ms
		}else if (period > MAX_DELAY){
			createFeatureNotSupportedEvent(period);
			this.period = MAX_DELAY;
		} else {
			this.period = period;
		}
	}

	/**
	 * Gets the number of channels to use.
	 * 
	 * @return the channels to use
	 * 
	 * @see #getNumOfSamples()
	 * @see #setSamples(int[], int)
	 *
	 */
	public int getChannelsToUse() {
		return channelsToUse;
	}

	/**
	 * Gets the number of samples.
	 * 
	 * @return the number of samples
	 * 
	 * @see #setSamples(int[], int)
	 * @see #setSamples(int[], Direction)
	 * @see #setSamples(int[])
	 */
	public int getNumOfSamples() {
		return numOfSamples;
	}


	/**
	 * Gets the data array. If channelsToUse is >= 2, the values are interleaved.
	 * 
	 * @return the data
	 * 
	 * @see #setSamples(int[], int)
	 * @see #setSamples(int[], Direction)
	 * @see #setSamples(int[])
	 * 
	 */
	public int[] getData() {
		return data;
	}

	
	// channelsToUse >= 2
	/**
	 * Sets the data array and channelsToUse. If channels to use
	 * is 1, the effect will be rotated using the direction. Make sure the length
	 * of the data array is an integral multiple of channelsToUse, then numOfSamples
	 * will be set automatically (data.length / channelsToUse).
	 * 
	 * @param data
	 *            the data (array size is channelsToUse * numOfSamples,
	 *            values are from 0 to MAX_LEVEL (32767))
	 * @param channelsToUse
	 *            the channels to use (value from 1 to 255)
	 * @see #setSamples(int[], Direction)
	 * @see #setSamples(int[])
	 * @see #getData()
	 * @see #getChannelsToUse()
	 * @see #getNumOfSamples()
	 *            
	 */
	public void setSamples(int[] data, int channelsToUse) {
		if (channelsToUse < 1 || channelsToUse > 255) {
			createFeatureNotSupportedEvent(channelsToUse);
			this.channelsToUse = 1;
		} else {
			this.channelsToUse = channelsToUse;
		}
		this.numOfSamples = data.length/channelsToUse;
		if (data.length%channelsToUse != 0) { // data.length not an integral multiple of the channelsToUse
			createFeatureNotSupportedEvent(data.length);
		}
		for (int i = 0; i < this.numOfSamples * this.channelsToUse; i++ ){
			if (data[i] < 0) {
				createFeatureNotSupportedEvent(data[i]);
				data[i] = 0;
			}else if (data[i] > MAX_LEVEL){
				createFeatureNotSupportedEvent(data[i]);
				data[i] = MAX_LEVEL;
			}
		}
		this.data = data;
	}
	
	// channelsToUse == 1 (and will be set to 1)
	/**
	 * Sets the data array. If you use this method, channelsToUse will be
	 * set to 1 and numOfSamples will be set to the size of the data array. The
	 * direction for the effect needs to be set separately.
	 * 
	 * @param data
	 *            the data array (values are from 0
	 *            to MAX_LEVEL (32767))
	 * @see #setSamples(int[], Direction)           
	 * @see #setSamples(int[], int)      
	 * @see #getData()
	 * @see #getDirection()     
	 */
	public void setSamples(int[] data) {
		this.channelsToUse = 1;
		this.numOfSamples = data.length;
		for (int i = 0; i < numOfSamples; i++ ){
			if (data[i] < 0) {
				createFeatureNotSupportedEvent(data[i]);
				data[i] = 0;
			}else if (data[i] > MAX_LEVEL){
				createFeatureNotSupportedEvent(data[i]);
				data[i] = MAX_LEVEL;
			}
		}
		this.data = data;
	}
	
	// channelsToUse == 1 (and will be set to 1)
	/**
	 * Sets the data array. If you use this method, channelsToUse will be
	 * set to 1 and numOfSamples will be set to the size of the data array.
	 *  
	 * @param data
	 *            the data array (values are from 0
	 *            to MAX_LEVEL (32767))
	 * @param dir
	 *            a Direction object
	 * @see #setSamples(int[])
	 * @see #setSamples(int[], int)
	 * @see #getData()
	 * @see #getDirection()           
	 */
	public void setSamples(int[] data, Direction dir) {
		setDirection(dir);
		setSamples(data);
	}


	// strengh of customEffect refers to the first data sample for the first axis
	/* (non-Javadoc)
	 * @see at.wisch.joystick.ffeffect.Effect#getStrength()
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * Strength for a ConstantEffect refers to the first frame for the first 
	 * axis of data (data[0]).
	 * 
	 * @see #getData()
	 */
	@Override
	public int getStrength() {
		return data[0];
	}

	/* (non-Javadoc)
	 * @see at.wisch.joystick.ffeffect.Effect#setStrength(int)
	 */
	@Override
	/**
	 * {@inheritDoc}
	 * 
	 * Strength for a ConstantEffect refers to the first frame for the first
	 * axis of data (data[0]).
	 * 
	 * @see #setSamples(int[])
	 */
	public void setStrength(int strength) {
		data[0] = strength;
	}

	/* (non-Javadoc)
	 * @see at.wisch.joystick.ffeffect.Effect#getName()
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @return the effect type as a String: "Custom"
	 */
	@Override
	public String getName() {
		return "Custom";
	}

	
	
	
}
