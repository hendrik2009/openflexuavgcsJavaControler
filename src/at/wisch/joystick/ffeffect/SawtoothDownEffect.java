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
 * The Class SawtoothDownEffect. A periodic effect with a sawtooth-down shaped
 * wave form:
 * 
 * <pre>
 * |\   |\   |\   |\   |\   |\   |\    &lt;--- magnitude (peak value)
 * | \  | \  | \  | \  | \  | \  | \
 * |  \ |  \ |  \ |  \ |  \ |  \ |  \
 * |   \|   \|   \|   \|   \|   \|   \ &lt;--- offset (mean value)
 * </pre>
 * 
 * @author Martin Wischenbart
 */
public class SawtoothDownEffect extends PeriodicEffect {

	/**
	 * Instantiates a new SawtoothDownEffect with the specified parameters.
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
	 *            the period of the wave in ms
	 * @param magnitude
	 *            the magnitude (peak value of the wave)
	 * @param offset
	 *            the offset (mean value of the wave)
	 * @param phase
	 *            the phase (horizontal cycle shift in hundredth of a degree)
	 * 
	 * @see #SawtoothDownEffect()
	 */
	public SawtoothDownEffect(Direction direction,
			int effectLength, int effectDelay, int buttonIndex,
			int buttonInterval, int attackLength, int fadeLength,
			int attackLevel, int fadeLevel, int period, int magnitude,
			int offset, int phase) {
		super(EFFECT_SAWTOOHDOWN, direction, effectLength, effectDelay, buttonIndex,
				buttonInterval, attackLength, fadeLength, attackLevel, fadeLevel,
				period, magnitude, offset, phase);
	}

	
	/**
	 * Instantiates a new SawtoothDownEffect with default parameters.
	 */
	public SawtoothDownEffect(){
		super(EFFECT_SAWTOOHDOWN);
	}
	
	/* (non-Javadoc)
	 * @see at.wisch.joystick.ffeffect.Effect#getName()
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @return the effect type as a String: "Sawtooth-Down"
	 */
	@Override
	public String getName() {
		return "Sawtooth-Down";
	}

		
}
