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

import at.wisch.joystick.ffeffect.direction.*;

/**
 * The Class RampEffect. RampEffect is similar to ConstantEffect, but it has
 * different levels at beginning and end: it linearly increases or decreases the
 * level.<br>
 * <br>
 * Note: If you use attack and fade with a RampEffect the effects get added to
 * the ramp effect making the effect become quadratic instead of linear.
 * 
 * @see ConstantEffect
 * 
 * @author Martin Wischenbart
 */
public class RampEffect extends ExtendedEffect {

	int startLevel;           // Beginning strength level. (MIN_LEVEL to MAX_LEVEL)
    int endLevel;             // Ending strength level. (MIN_LEVEL to MAX_LEVEL)
    
	
	/**
	 * Instantiates a new RampEffect with the specified parameters.
	 * 
	 * @param direction
	 *            a Direction object
	 * @param effectLength
	 *            the effect length in ms
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
	 * @param startLevel
	 *            the level (the strength) at the beginning of the effect
	 * @param endLevel
	 *            the level (the strength) at the end of the effect
	 * 
	 * @see #RampEffect()
	 */
	public RampEffect(Direction direction, int effectLength,
			int effectDelay, int buttonIndex, int buttonInterval,
			int attackLength, int fadeLength, int attackLevel, int fadeLevel,
			int startLevel, int endLevel) {
		
		super(EFFECT_RAMP, direction, effectLength, effectDelay, buttonIndex,
				buttonInterval, attackLength, fadeLength, attackLevel,
				fadeLevel);
		setStartLevel(startLevel);
		setEndLevel(endLevel);
	}
	

	/**
	 * Instantiates a new RampEffect with default parameters.
	 * 
	 * @see #RampEffect(Direction, int, int, int, int, int, int, int, int, int,
	 *      int)
	 */
	public RampEffect(){
		this(Effect.getDefaultDirection(), 2000, 0, Effect.NO_BUTTON , 0, 100, 100, 0, 0, (int)(MAX_LEVEL*0.5), MAX_LEVEL);
	}
	
	
	
	
	/**
	 * Sets the effect length in ms. RampEffect does not support
	 * INFINITE_LENGTH.
	 * 
	 * @param effectLength
	 *            the new effect length (an int from 0 to MAX_LENGTH (360000000
	 *            ms ))
	 * 
	 * @see #getEffectLength()
	 */
	@Override
	public void setEffectLength(int effectLength) {
		if (effectLength == INFINITE_LENGTH) {
			createFeatureNotSupportedEvent(effectLength);
			super.setEffectLength(MAX_LENGTH);
		}else{
			super.setEffectLength(effectLength);
		}
	}
	
	
	
	
	/* (non-Javadoc)
	 * @see at.wisch.joystick.ffeffect.Effect#getName()
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @return the effect type as a String: "Ramp"
	 */
	@Override
	public String getName() {
		return "Ramp";
	}

	
	
    
    
	/**
	 * Gets the level (the strength) of the effect at the beginning.
	 * 
	 * @return the level (strength) of the effect at the beginning
	 * 
	 * @see #setStartLevel(int)
	 * @see #getEndLevel()
	 */
	public int getStartLevel() {
		return startLevel;
	}
	
	/**
	 * Sets the level (the strength) at the beginning of the effect.
	 * 
	 * @param startLevel
	 *            the new level (strength) at the beginning of the effect (an
	 *            int from MIN_LEVEL (-32768) to MAX_LEVEL (32767))
	 * 
	 * @see #getStartLevel()
	 * @see #setEndLevel(int)
	 */
	public void setStartLevel(int startLevel) {
		if (startLevel < MIN_LEVEL) {
			createFeatureNotSupportedEvent(startLevel);
			this.startLevel = MIN_LEVEL;
		} else if (startLevel > MAX_LEVEL) {
			createFeatureNotSupportedEvent(startLevel);
			this.startLevel = MAX_LEVEL;
		} else {
			this.startLevel = startLevel;
		}
	}
	
	/**
	 * Gets the level (the strength) at the end of the effect.
	 * 
	 * @return the level (strength) at the end of the effect
	 * 
	 * @see #setEndLevel(int)
	 * @see #getStartLevel()
	 */
	public int getEndLevel() {
		return endLevel;
	}
	
	/**
	 * Sets the level (the strength) at the end of the effect.
	 * 
	 * @param endLevel
	 *            the new level (strength) at the end of the effect (an int from
	 *            MIN_LEVEL (-32768) to MAX_LEVEL (32767))
	 * 
	 * @see #getEndLevel()
	 * @see #setStartLevel(int)
	 */
	public void setEndLevel(int endLevel) {
		if (endLevel < MIN_LEVEL) {
			createFeatureNotSupportedEvent(endLevel);
			this.endLevel = MIN_LEVEL;
		} else if (endLevel > MAX_LEVEL) {
			createFeatureNotSupportedEvent(endLevel);
			this.endLevel = MAX_LEVEL;
		} else {
			this.endLevel = endLevel;
		}
	}
		
	/* (non-Javadoc)
	 * @see at.wisch.joystick.ffeffect.Effect#getStrength()
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * Strength for a RampEffect refers to the end level.
	 * 
	 * @see #getEndLevel()
	 */
	@Override
	public int getStrength() {
		return getEndLevel();
	}

	/* (non-Javadoc)
	 * @see at.wisch.joystick.ffeffect.Effect#setStrength(int)
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * Strength for a RampEffect refers to the end level.
	 * 
	 * @see #setEndLevel(int)
	 */
	@Override
	public void setStrength(int strength) {
		setEndLevel(strength);
	}
    
    
}
