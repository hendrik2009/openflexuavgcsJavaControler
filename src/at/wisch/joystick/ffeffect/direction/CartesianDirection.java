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
 * The Class CartesianDirection. The direction is encoded by three positions: X axis, Y axis and Z axis (with 3 FF axes).<br>
 * <br>
 * The coordinate values can be any positive or negative int value. The cardinal directions would be:<br>
 * - North: {0, -1, 0}<br>
 * - East: {-1, 0, 0}<br>
 * - South: {0, 1, 0}<br>
 * - West: {1, 0, 0}<br>
 * Some other examples:<br>
 * - {1, -2, 0} would be North-North-East<br>
 * - {1, 2, 0) would be the same as {2, 4, 0} (coordinates define only direction, not magnitude or distance)<br>
 * 
 *  @author Martin Wischenbart
 */
public class CartesianDirection extends Direction {
	
	/**
	 * The constant NORTH ({0, -1, 0}): force comes from top, 3rd axis not used.
	 */
	public static final int[] NORTH = {0, -1, 0};
	
	/**
	 * The constant NORTHEAST ({1, -1, 0}): force comes from top-right, 3rd axis not used.
	 */
	public static final int[] NORTHEAST = {1, -1, 0};
	
	/**
	 * The constant EAST ({1, 0, 0}): force comes from right, 3rd axis not used.
	 */
	public static final int[] EAST = {1, 0, 0};
	
	/**
	 * The constant SOUTHEAST ({1, 1, 0}): force comes from bottom-right, 3rd axis not used.
	 */
	public static final int[] SOUTHEAST = {1, 1, 0};
	
	/**
	 * The constant SOUTH ({0, 1, 0}): force comes from bottom, 3rd axis not used.
	 */
	public static final int[] SOUTH = {0, 1, 0};
	
	/**
	 * The constant SOUTHWEST ({-1, 1, 0}): force comes from bottom-left, 3rd axis not used.
	 */
	public static final int[] SOUTHWEST = {-1, 1, 0};
	
	/**
	 * The constant WEST ({-1, 0, 0}): force comes from left, 3rd axis not used.
	 */
	public static final int[] WEST = {-1, 0, 0};
	
	/**
	 * The constant NORTHWEST ({-1, -1, 0}): force comes from top-left, 3rd axis not used.
	 */
	public static final int[] NORTHWEST = {-1, -1, 0};
	
	
	private int[] cartesianCoordinates; // size: 3
	
	/**
	 * Instantiates a new cartesian direction with the specified coordinate values.
	 * 
	 * @param cartesianCoordinates
	 *            the cartesian coordinates for X, Y and Z axes (an int array of size 3, any int values)
	 */
	public CartesianDirection(int[] cartesianCoordinates) {
		super(DIRECTION_CARTESIAN);
		this.setCartesianCoordinates(cartesianCoordinates);
	}
	
	/**
	 * Instantiates a new cartesian direction with the default direction (NORTHWEST), 3rd axis not used.
	 */
	public CartesianDirection() {
		super(DIRECTION_CARTESIAN);
		this.setCartesianCoordinates(NORTHWEST); //if nothing is set, the default direction is NORTHWEST
	}
	
	// native datatype is Sint32 - so there is no need to check if the values are too large
	// -2,147,483,648 to 2,147,483,647
	/**
	 * Sets the cartesian coordinates.
	 * 
	 * @param cartesianCoordinates
	 *            the new cartesian coordinates for X, Y and Z axes (an int array of size 3, any int values)
	 */
	public void setCartesianCoordinates(int[] cartesianCoordinates) {
		if (cartesianCoordinates.length != 3){
			createFeatureNotSupportedEvent(cartesianCoordinates.length);
			cartesianCoordinates = NORTHWEST;
		}
		this.cartesianCoordinates = cartesianCoordinates;
	}
	
	/**
	 * Sets the cartesian X axis coordinate.
	 * 
	 * @param cartesianCoordinate
	 *            the new cartesian X axis coordinate (any int value)
	 */
	public void setCartesianXCoordinate(int cartesianCoordinate) {
		this.cartesianCoordinates[0] = cartesianCoordinate;
	}
	
	/**
	 * Sets the cartesian Y axis coordinate.
	 * 
	 * @param cartesianCoordinate
	 *            the new cartesian Y axis coordinate (any int value)
	 */
	public void setCartesianYCoordinate(int cartesianCoordinate) {
		this.cartesianCoordinates[1] = cartesianCoordinate;
	}

	/**
	 * Sets the cartesian Z axis coordinate.
	 * 
	 * @param cartesianCoordinate
	 *            the new cartesian Z axis coordinate (any int value)
	 */
	public void setCartesianZCoordinate(int cartesianCoordinate) {
		this.cartesianCoordinates[2] = cartesianCoordinate;
	}



	/**
	 * Gets the cartesian coordinates.
	 * 
	 * @return the cartesian coordinates for X, Y and Z axes (an int array of size 3, any int values)
	 */
	public int[] getCartesianCoordinates() {
		return cartesianCoordinates;
	}

	/**
	 * Gets the cartesian X axis coordinate.
	 * 
	 * @return the cartesian X axis coordinate
	 */
	public int getCartesianXCoordinate() {
		return cartesianCoordinates[0];
	}
	
	/**
	 * Gets the cartesian Y axis coordinate.
	 * 
	 * @return the cartesian Y axis coordinate
	 */
	public int getCartesianYCoordinate() {
		return cartesianCoordinates[1];
	}

	/**
	 * Gets the cartesian Z axis coordinate.
	 * 
	 * @return the cartesian Z axis coordinate
	 */
	public int getCartesianZCoordinate() {
		return cartesianCoordinates[2];
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @return the name of the direction type: "Cartesian"
	 */
	@Override
	public String getName() {
		return "Cartesian";
	}
	
	@Override
	/**
	 * {@inheritDoc}
	 * As the Direction is a CartesianDirection it returns <code>this</code> (not a copy, but really this object)
	 */
	public CartesianDirection toCartesianDirection(Direction direction) {
		return this;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * The as PolarDirection supports only 2 FF axes, the third coordinate value will be ignored.
	 */
	@Override
	public PolarDirection toPolarDirection(Direction direction) {
		// like the first array value in toSphericalDirection, but add another 90 degrees, as in SphericalDirection.toPolarDirection()
		return new PolarDirection((int)(((Math.atan2(this.cartesianCoordinates[1], this.cartesianCoordinates[0])*18000/Math.PI)+45000)%36000));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SphericalDirection toSphericalDirection(Direction direction) {
		return new SphericalDirection(new int[]{(int)(((Math.atan2(this.cartesianCoordinates[1], this.cartesianCoordinates[0])*18000/Math.PI)+36000)%36000), 
				(int)(((Math.asin(this.cartesianCoordinates[2]/Math.sqrt((this.cartesianCoordinates[0]*this.cartesianCoordinates[0]+
						this.cartesianCoordinates[1]*this.cartesianCoordinates[1]+
						this.cartesianCoordinates[2]*this.cartesianCoordinates[2])))*18000/Math.PI)+36000)%36000)
		});
	}
}