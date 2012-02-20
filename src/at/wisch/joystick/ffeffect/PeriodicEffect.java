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

/**
 * The Class PeriodicEffect. A periodic effect a wave-shaped effect that repeats
 * itself over time. The type determines the shape of the wave and the
 * parameters determine the dimensions of the wave: period, magnitude, offset
 * and phase.
 * 
 * @author Martin Wischenbart
 */
public abstract class PeriodicEffect extends ExtendedEffect {
	
	int period;      // Period of the wave. (ms)
    int magnitude;   // Peak value. (MIN_LEVEL to MAX_LEVEL)
    int offset;      // Mean value of the wave. (MIN_LEVEL to MAX_LEVEL)
    int phase;       // Horizontal shift given by hundredth of a degree
     
    
    protected PeriodicEffect(int effectType, Direction direction,
			int effectLength, int effectDelay, int buttonIndex,
			int buttonInterval, int attackLength, int fadeLength,
			int attackLevel, int fadeLevel, int period, int magnitude,
			int offset, int phase) {
		super(effectType, direction, effectLength, effectDelay, buttonIndex,
				buttonInterval, attackLength, fadeLength, attackLevel,
				fadeLevel);
		setPeriod(period);
		setMagnitude(magnitude);
		setOffset(offset);
		setPhase(phase);
	}
	
    protected PeriodicEffect(int effectType) {
    	this(effectType, Effect.getDefaultDirection(), 3000, 0, Effect.NO_BUTTON , 0, 0, 0, 0, 0, 1000, (int)(MAX_LEVEL), (int)(MAX_LEVEL*0), 0);
    }
    
	/**
	 * Gets the period of the wave in ms.
	 * 
	 * @return the period of the wave in ms
	 */
	public int getPeriod() {
		return period;
	}
	
	/**
	 * Sets the period of the wave in ms.
	 * 
	 * @param period
	 *            the new period of the wave in ms (value from 0 to MAX_DELAY
	 *            (60000))
	 */
	public void setPeriod(int period) {
		if (period < 0) {
			createFeatureNotSupportedEvent(period);
			this.period = 100; // 100 ms
		} else if (period > MAX_DELAY) {
			createFeatureNotSupportedEvent(period);
			this.period = MAX_DELAY;
		} else {
			this.period = period;
		}
	}
	
	/**
	 * Gets the magnitude (the peak value of the wave).
	 * 
	 * @return the magnitude (peak value of the wave)
	 */
	public int getMagnitude() {
		return magnitude;
	}
	
	/**
	 * Sets the magnitude (the peak value of the wave).
	 * 
	 * @param magnitude
	 *            the new magnitude (peak value of the wave) (value from
	 *            MIN_LEVEL (-32768) to MAX_LEVEL(32767))
	 */
	public void setMagnitude(int magnitude) {
		if (magnitude < MIN_LEVEL) {
			createFeatureNotSupportedEvent(magnitude);
			this.magnitude = MIN_LEVEL;
		} else if (magnitude > MAX_LEVEL) {
			createFeatureNotSupportedEvent(magnitude);
			this.magnitude = MAX_LEVEL;
		} else {
			this.magnitude = magnitude;
		}
	}
	
	/**
	 * Gets the offset (the mean value of the wave).
	 * 
	 * @return the offset (mean value of the wave)
	 */
	public int getOffset() {
		return offset;
	}
	
	/**
	 * Sets the offset (the mean value of the wave).
	 * 
	 * @param offset
	 *            the new offset (mean value of the wave) (value from MIN_LEVEL
	 *            (-32768) to MAX_LEVEL(32767))
	 */
	public void setOffset(int offset) {
		if (offset < MIN_LEVEL) {
			createFeatureNotSupportedEvent(offset);
			this.offset = MIN_LEVEL;
		} else if (offset > MAX_LEVEL) {
			createFeatureNotSupportedEvent(offset);
			this.offset = MAX_LEVEL;
		} else {
			this.offset = offset;
		}
	}
	
	/**
	 * Gets the phase (the horizontal cycle shift in hundredth of a degree).
	 * 
	 * @return the phase (horizontal cycle shift in hundredth of a degree)
	 */
	public int getPhase() {
		return phase;
	}
	
	/**
	 * Sets the phase (the horizontal cycle shift in hundredth of a degree).<br>
	 * <br>
	 * Examples:<br>
	 * - 0: No phase displacement. (0 degrees)<br>
	 * - 9000: Displaced 25% of it's period. (90 degrees)<br>
	 * - 18000: Displaced 50% of it's period. (180 degrees)<br>
	 * - 27000: Displaced 75% of it's period. (270 degrees)<br>
	 * 
	 * @param phase
	 *            the new phase (horizontal cycle shift in hundredth of a
	 *            degree) (value from 0 to 35999)
	 */
	public void setPhase(int phase) {
		if (phase < 0 || phase > 35999) {
			createFeatureNotSupportedEvent(phase);
			this.phase = 0;
		} else {
			this.phase = phase;
		}
	}

	/* (non-Javadoc)
	 * @see at.wisch.joystick.ffeffect.Effect#getStrength()
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * Strength for a PeriodicEffect refers to the magnitude.
	 * 
	 * @see #getMagnitude()
	 */
	@Override
	public int getStrength() {
		return getMagnitude();
	}

	/* (non-Javadoc)
	 * @see at.wisch.joystick.ffeffect.Effect#setStrength(int)
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * Strength for a PeriodicEffect refers to the magnitude.
	 * 
	 * @see #setMagnitude(int)
	 */
	@Override
	public void setStrength(int strength) {
		setMagnitude(strength);
	}
	
}
