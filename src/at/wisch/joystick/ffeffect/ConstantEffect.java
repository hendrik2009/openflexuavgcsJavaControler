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
 * The Class ConstantEffect. The most simple type of effect. It has a constant
 * level of strength, and optional fade-in and fade-out.<br>
 * <br>
 * Here we have an example of a constant effect evolution in time:<br>
 * 
 * <pre>
 * Strength
 * &circ;
 * |
 * |    effect level --&gt;  _________________
 * |                     /                 \
 * |                    /                   \
 * |                   /                     \
 * |                  /                       \
 * | attack level --&gt; |                        \
 * |                  |                        |  &lt;---  fade level
 * |                  |                        |
 * +--------------------------------------------------&gt; Time
 *                    [--]                 [---]
 *                attack length          fade length
 * 
 * [-----------------][------------------------]
 *        delay                length
 * </pre>
 * 
 * Note that either the attack level or the fade level may be above the effect
 * level.
 * 
 * @see Effect
 * @see Direction
 * 
 * @author Martin Wischenbart
 */
public class ConstantEffect extends ExtendedEffect {

	private int level; // from MIN_LEVEL to MAX_LEVEL
	
	/**
	 * Instantiates a new ConstantEffect with the specified parameters.
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
	 * @param level
	 *            the level (the strength) of the effect
	 * 
	 * @see #ConstantEffect()
	 */
	public ConstantEffect(Direction direction,
			int effectLength, int effectDelay, int buttonIndex,
			int buttonInterval, int attackLength, int fadeLength,
			int attackLevel, int fadeLevel, int level) {
		super(EFFECT_CONSTANT, direction, effectLength, effectDelay, buttonIndex,
				buttonInterval, attackLength, fadeLength, attackLevel,
				fadeLevel);
		setLevel(level);
	}
	
	/**
	 * Instantiates a new ConstantEffect with default parameters.
	 * 
	 * @see #ConstantEffect(Direction, int, int, int, int, int, int, int, int,
	 *      int)
	 */
	public ConstantEffect(){
		this(Effect.getDefaultDirection(), 2000, 0, Effect.NO_BUTTON , 0, 0, 0, 0, 0, (int)(MAX_LEVEL));
	}
	
	/**
	 * Gets the level (the strength) of the effect.
	 * 
	 * @return the level (strength) of the effect
	 * 
	 * @see #setLevel(int)
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * Sets the level (the strength) of the effect.
	 * 
	 * @param level
	 *            the new level (strength) of the effect (an int from MIN_LEVEL
	 *            (-32768) to MAX_LEVEL (32767))
	 * 
	 * @see #getLevel()
	 */
	public void setLevel(int level) {
		if (level < MIN_LEVEL) {
			createFeatureNotSupportedEvent(level);
			this.level = MIN_LEVEL;
		} else if (level > MAX_LEVEL) {
			createFeatureNotSupportedEvent(level);
			this.level = MAX_LEVEL;
		} else {
			this.level = level;
		}
	}

	/* (non-Javadoc)
	 * @see at.wisch.joystick.ffeffect.Effect#getStrength()
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * Strength for a ConstantEffect refers to the level.
	 * 
	 * @see #getLevel()
	 */
	@Override
	public int getStrength() {
		return getLevel();
	}

	/* (non-Javadoc)
	 * @see at.wisch.joystick.ffeffect.Effect#setStrength(int)
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * Strength for a ConstantEffect refers to the level.
	 * 
	 * @see #setLevel(int)
	 */
	@Override
	public void setStrength(int strength) {
		setLevel(strength);
	}

	/* (non-Javadoc)
	 * @see at.wisch.joystick.ffeffect.Effect#getName()
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @return the effect type as a String: "Constant"
	 */
	@Override
	public String getName() {
		return "Constant";
	}

	
}
