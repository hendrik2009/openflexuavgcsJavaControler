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

/**
 * The Class PolarDirection. A PolarDirection is specified by polar coordinates in degrees, clockwise, starting from NORTH.
 * <br><br>
 * The degree value is encoded by hundredths of a 
 *  degree starting from north and turning clockwise. The cardinal directions would be:<br>
 *   - North: 0 (0 degrees)<br>
 *   - East: 9000 (90 degrees)<br>
 *   - South: 18000 (180 degrees)<br>
 *   - West: 27000 (270 degrees)
 *   
 *   @author Martin Wischenbart
 */
public class PolarDirection extends Direction {
	
	/**
	 * The constant NORTH ({@value} ): force comes from top.
	 */
	public static final int NORTH = 0;
	
	/**
	 * The constant NORTHEAST ({@value} ): force comes from top-right.
	 */
	public static final int NORTHEAST = 4500;
	
	/**
	 * The constant EAST ({@value} ): force comes from right.
	 */
	public static final int EAST = 9000;
	
	/**
	 * The constant SOUTHEAST ({@value} ): force comes from bottom-right.
	 */
	public static final int SOUTHEAST = 13500;
	
	/**
	 * The constant SOUTH ({@value} ) force comes from bottom.
	 */
	public static final int SOUTH = 18000;
	
	/**
	 * The constant SOUTHWEST ({@value} ): force comes bottom down-left.
	 */
	public static final int SOUTHWEST = 22500;
	
	/**
	 * The constant WEST ({@value} ): force comes from left.
	 */
	public static final int WEST = 27000;
	
	/**
	 * The constant NORTHWEST ({@value} ): force comes from top-left.
	 */
	public static final int NORTHWEST = 31500;
	// but there are many more: every value from 0 to 35999 is valid
	
	
	private int polarDirection;
	
	/**
	 * Instantiates a new polar direction with the specified direction value.
	 * 
	 * @param polarDirection
	 *            the polar direction (values from 0 to 35999)
	 */
	public PolarDirection(int polarDirection) {
		super(DIRECTION_POLAR);
		this.setPolarDirection(polarDirection);
	}
	
	/**
	 * Instantiates a new polar direction with the default direction (NORTHWEST).
	 */
	public PolarDirection() {
		super(DIRECTION_POLAR);
		this.setPolarDirection(NORTHWEST); //if nothing is set, the default direction is NORTHWEST
	}
	
	/**
	 * Gets the polar direction value.
	 * 
	 * @return the polar direction (values from 0 to 35999)
	 */
	public int getPolarDirection() {
		return polarDirection;
	}


	/**
	 * Sets the polar direction value.
	 * 
	 * @param polarDirection
	 *            the new polar direction (values from 0 to 35999)
	 */
	public void setPolarDirection(int polarDirection) {
		if (polarDirection < 0 || polarDirection > 35999){
			createFeatureNotSupportedEvent(polarDirection);
			this.polarDirection = NORTHWEST;
		} else {
			this.polarDirection = polarDirection;
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @return the name of the direction type: "Polar"
	 */
	@Override
	public String getName() {
		return "Polar";
	}

	@Override
	/**
	 * {@inheritDoc}
	 * 
	 * The as PolarDirection supports only 2 FF axes, the third coordinate value will be 0.
	 */
	public CartesianDirection toCartesianDirection(Direction direction) {
		// as in SphericalDirection.toCartesianDirection, but subtract 90 degrees first as in this.toSphericalDirection
		int sphericalCoordinate1 = (this.polarDirection+27000)%36000;
		if (sphericalCoordinate1 == 0) {
			return new CartesianDirection(new int[]{1, 0, 0});
		} else {
			int scalar = 1000000000;
			double phi = ((double)(sphericalCoordinate1))/18000*Math.PI;
			return new CartesianDirection(new int[]{
					(int)(Math.cos(phi)*scalar), 
					(int)(Math.sin(phi)*scalar),
					0
													})
			;
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * As the Direction is a PolarDirection it returns <code>this</code> (not a copy, but really this object)
	 */
	@Override
	public PolarDirection toPolarDirection(Direction direction) {
		return this;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * The as PolarDirection supports only 2 FF axes, the second angle value will be 0.
	 */
	@Override
	public SphericalDirection toSphericalDirection(Direction direction) {
		return new SphericalDirection(new int[]{(this.polarDirection+27000)%36000, 0});
	}
}