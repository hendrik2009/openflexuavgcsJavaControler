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

/**
 * The Class InertiaEffect. ConditionEffect based on axes acceleration.
 * 
 * @author Martin Wischenbart
 */
public class InertiaEffect extends ConditionEffect {

	/**
	 * Instantiates a new InertiaEffect with the specified parameters.
	 * 
	 * @param effectLength
	 *            the effect length
	 * @param effectDelay
	 *            the effect delay
	 * @param buttonIndex
	 *            the button index
	 * @param buttonInterval
	 *            the button interval
	 * @param rightSat
	 *            rightSat array: Level when joystick is to the positive side
	 * @param leftSat
	 *            leftSat array: Level when joystick is to the negative side
	 * @param rightCoef
	 *            rightCoef array: How fast to increase the force towards the
	 *            positive side
	 * @param leftCoef
	 *            leftCoef array: How fast to increase the force towards the
	 *            negative side
	 * @param deadband
	 *            the deadband zone around the center
	 * @param center
	 *            the center
	 * 
	 * @see #InertiaEffect()
	 */
	public InertiaEffect(
			int effectLength, int effectDelay, int buttonIndex,
			int buttonInterval, int[] rightSat, int[] leftSat,
			int[] rightCoef, int[] leftCoef, float[] deadband, float[] center) {
		super(EFFECT_INERTIA, 
				effectLength, effectDelay, buttonIndex,
				buttonInterval, rightSat, leftSat, rightCoef, leftCoef,
				deadband, center);
	}

	/**
	 * Instantiates a new InertiaEffect with default parameters.
	 * 
	 * @see #InertiaEffect(int, int, int, int, int[], int[], int[], int[],
	 *      float[], float[])
	 */
	public InertiaEffect(){
		super(EFFECT_INERTIA);
	}

	/* (non-Javadoc)
	 * @see at.wisch.joystick.ffeffect.Effect#getName()
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @return the effect type as a String: "Inertia"
	 */
	@Override
	public String getName() {
		return "Inertia";
	}

}
