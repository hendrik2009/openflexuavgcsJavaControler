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

package at.wisch.joystick.ffeffect.direction;

import at.wisch.joystick.event.FeatureNotSupportedEvent;
import at.wisch.joystick.event.FeatureNotSupportedEventManager;

/**
 * The Class Direction. An object Direction specifies the direction from where
 * the force comes.<br>
 * <br>
 * Most Controllers have 2 or 3 FF axes:<br>
 * - X, Y and Z axis<br>
 * - different rumble motors<br>
 * <br>
 * The following diagram represents the cardinal directions:
 * 
 * <pre>
 *                 .--.
 *                 |__| .-------.
 *                 |=.| |.-----.|
 *                 |--| ||     ||
 *                 |  | |'-----'|
 *                 |__|&tilde;')_____('
 *                  [ COMPUTER ]
 *  
 *                     North
 *                       &circ;
 *                       |
 *                       |
 *        West &lt;----[ HAPTIC ]----&gt; East
 *                       |
 *                       |
 *                       v
 *                     South
 *  
 *                    [ USER ]
 *                      \|||/
 *                      (o o)
 *                ---ooO-(_)-Ooo---
 * </pre>
 * 
 * Note: Because on some operating systems there is an issue with the order of
 * the axes you might want to use PolarDirection objects to stay fully platform
 * independent, if possible. On the other hand, be aware that some controllers,
 * which have 3 FF axes, do not support PolarDirection.
 * 
 * @author Martin Wischenbart
 */
public abstract class Direction {

	private int directionType;
	
	
	/**
	 * The constant DIRECTION_POLAR: the object is a PolarDirection (casting is
	 * safe).
	 */
	public static final int DIRECTION_POLAR = 5067;
	
	/**
	 * The constant DIRECTION_SPHERICAL: the object is a SphericalDirection
	 * (casting is safe).
	 */
	public static final int DIRECTION_SPHERICAL = 5068;
	
	/**
	 * The constant DIRECTION_CARTESIAN: the object is a CartesianDirection
	 * (casting is safe).
	 */
	public static final int DIRECTION_CARTESIAN = 5069;

	
	protected Direction(int directionType) {
		this.directionType = directionType;
	}


	/**
	 * Gets the direction type.
	 * 
	 * @return the direction type: one of the following: DIRECTION_POLAR,
	 *         DIRECTION_SPHERICAL, DIRECTION_CARTESIAN
	 * 
	 * @see #getName()
	 */
	public int getDirectionType() {
		return directionType;
	}
	
	/**
	 * Gets the direction type as a String.
	 * 
	 * @return the name of the direction type: one of the following: "Polar",
	 *         "Spherical" or "Cartesian"
	 * 
	 * @see #getDirectionType()
	 */
	public abstract String getName();
	
	
	protected static void createFeatureNotSupportedEvent(int value) {
		FeatureNotSupportedEventManager.featureNotSupportedEventOccured(new FeatureNotSupportedEvent(-1, FeatureNotSupportedEvent.DIRECTIONVALUE, value));
	}
	
	/**
	 * Converts the Direction to PolarDirection. If the object already is of
	 * type PolarDirection, it will return that object.
	 * 
	 * @param direction
	 *            the Direction to be converted
	 * 
	 * @return the PolarDirection
	 */
	public abstract PolarDirection toPolarDirection(Direction direction);
	
	/**
	 * Converts the Direction to SphericalDirection. If the object already is of
	 * type SphericalDirection, it will return that object.
	 * 
	 * @param direction
	 *            the Direction to be converted
	 * 
	 * @return the SphericalDirection
	 */
	public abstract SphericalDirection toSphericalDirection(Direction direction);
	
	/**
	 * Converts the Direction to CartesianDirection. If the object already is of
	 * type CartesianDirection, it will return that object.
	 * 
	 * @param direction
	 *            the Direction to be converted
	 * 
	 * @return the CartesianDirection
	 */
	public abstract CartesianDirection toCartesianDirection(Direction direction);
	
	
	
	
}
