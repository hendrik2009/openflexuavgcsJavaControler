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
 * The Class ExtendedEffect. This is the superclass for certain types of
 * effects, that have some common properties: they need a direction and they
 * support fade-in (attack) and fade-out (fade). To understand those, see
 * {@link ConstantEffect}.<br>
 * <br>
 * Notes:<br>
 * - If attack length and fade level are set to 0, attack and fade is not used,
 * otherwise both are used.<br>
 * - If the length of an effect is INFINITE_LENGTH, fade-out will never happen.
 * 
 * @see ConstantEffect
 * @see Direction
 * 
 * @author Martin Wischenbart
 */
public abstract class ExtendedEffect extends Effect {
	
	private int attackLength; // in ms
	private int fadeLength; // in ms
	private int attackLevel; // [0, MAX_LEVEL]
	private int fadeLevel; // [0, MAX_LEVEL]
	
	private Direction direction;
	
	
	
	protected ExtendedEffect(int effectType, Direction direction,
			int effectLength, int effectDelay, int buttonIndex,
			int buttonInterval, int attackLength, int fadeLength,
			int attackLevel, int fadeLevel) {
		super(effectType, effectLength, effectDelay, buttonIndex,
				buttonInterval);
		setDirection(direction);
		setAttackLength(attackLength);
		setFadeLength(fadeLength);
		setAttackLevel(attackLevel);
		setFadeLevel(fadeLevel);
	}
	

	
	/**
	 * Gets the attack length in ms.
	 * 
	 * @return the attack length in ms
	 * 
	 * @see #setAttackLength(int)
	 */
	public int getAttackLength() {
		return attackLength;
	}

	/**
	 * Sets the attack length in ms.
	 * 
	 * @param attackLength
	 *            the new attack length in ms (an int from 0 to MAX_DELAY (60000
	 *            ms ))
	 * 
	 * @see #getAttackLength()
	 */
	public void setAttackLength(int attackLength) {
		if (attackLength < 0) {
			createFeatureNotSupportedEvent(attackLength);
			this.attackLength = 0;
		} else if (attackLength > MAX_DELAY) {
			createFeatureNotSupportedEvent(attackLength);
			this.attackLength = MAX_DELAY;
		} else {
			this.attackLength = attackLength;
		}
	}

	/**
	 * Gets the fade length in ms.
	 * 
	 * @return the fade length in ms
	 * 
	 * @see #setFadeLength(int)
	 */
	public int getFadeLength() {
		return fadeLength;
	}

	/**
	 * Sets the fade length in ms.
	 * 
	 * @param fadeLength
	 *            the new fade length in ms(an int from 0 to MAX_DELAY (60000 ms
	 *            ))
	 * 
	 * @see #getFadeLength()
	 */
	public void setFadeLength(int fadeLength) {
		if (fadeLength < 0) {
			createFeatureNotSupportedEvent(fadeLength);
			this.fadeLength = 0;
		} else if (fadeLength > MAX_DELAY) {
			createFeatureNotSupportedEvent(fadeLength);
			this.fadeLength = MAX_DELAY;
		} else {
			this.fadeLength = fadeLength;
		}
	}

	/**
	 * Gets the attack level.
	 * 
	 * @return the attack level
	 * 
	 * @see #setAttackLevel(int)
	 */
	public int getAttackLevel() {
		return attackLevel;
	}

	/**
	 * Sets the attack level.
	 * 
	 * @param attackLevel
	 *            the new attack level (an int from 0 to MAX_LEVEL (32767))
	 * 
	 * @see #getAttackLevel()
	 */
	public void setAttackLevel(int attackLevel) {
		if (attackLevel < 0) {
			createFeatureNotSupportedEvent(attackLevel);
			this.attackLevel = 0;
		} else if (attackLevel > MAX_LEVEL) {
			createFeatureNotSupportedEvent(attackLevel);
			this.attackLevel = MAX_LEVEL;
		} else {
			this.attackLevel = attackLevel;
		}
	}

	/**
	 * Gets the fade level.
	 * 
	 * @return the fade level
	 * 
	 * @see #setFadeLevel(int)
	 */
	public int getFadeLevel() {
		return fadeLevel;
	}

	/**
	 * Sets the fade level.
	 * 
	 * @param fadeLevel
	 *            the new fade level (an int from 0 to MAX_LEVEL (32767))
	 * 
	 * @see #getFadeLevel()
	 */
	public void setFadeLevel(int fadeLevel) {
		if (fadeLevel < 0) {
			createFeatureNotSupportedEvent(fadeLevel);
			this.fadeLevel = 0;
		} else if (fadeLevel > MAX_LEVEL) {
			createFeatureNotSupportedEvent(fadeLevel);
			this.fadeLevel = MAX_LEVEL;
		} else {
			this.fadeLevel = fadeLevel;
		}
	}

	
	/**
	 * Gets the direction for the effect.
	 * 
	 * @return a Direction object instance
	 * 
	 * @see #setDirection(Direction)
	 */
	public Direction getDirection() {
		return direction;
	}

	/**
	 * Sets the direction for the effect.
	 * 
	 * @param direction
	 *            a Direction object instance
	 * 
	 * @see #getDirection()
	 */
	public void setDirection(Direction direction) {
		if (direction == null){
			createFeatureNotSupportedEventDirectionIsNull();
			this.direction = new PolarDirection();
		}
		this.direction = direction;
	}

}
