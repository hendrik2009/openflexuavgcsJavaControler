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
 * The Class SphericalDirection. Similar to PolarDirection, a SphericalDirection
 * is encoded by two rotations:<br> 
	 *  1) Degrees from (1, 0) rotated towards (0, 1).<br>
	 *  2) Degrees towards (0, 0, 1) (device needs to have 3 FF axes).<br>
	 * <br> 
	 *   Example: A direction of 27000 for the first angle and 4500 for the 
	 *   second would be directly away from the user (270 degrees 
	 *   clockwise from east) and angling toward the floor (45 degrees
	 *   downward from the tabletop). To counteract a force with this 
	 *   direction, the user would have to push forward and down.
	 *   <br><br>
 * 	The degree values are encoded by hundredths of a 
 *  degree. The cardinal directions would be:<br>
 *   - East: {0, 0}<br>
 *   - South: {9000, 0}<br>
 *   - West: {18000, 0}<br>
 *   - North: {27000, 0}<br>
 * 
 *  @author Martin Wischenbart
 */
public class SphericalDirection extends Direction {
	
	/**
	 * The constant NORTH ({27000, 0}): force comes from top, 3rd axis not used.
	 */
	public static final int[] NORTH = {27000, 0};
	
	/**
	 * The constant NORTHEAST ({31500, 0}): force comes from top-right, 3rd axis not used.
	 */
	public static final int[] NORTHEAST = {31500, 0};
	
	/**
	 * The constant EAST ({0, 0}): force comes from right, 3rd axis not used.
	 */
	public static final int[] EAST = {0, 0};
	
	/**
	 * The constant SOUTHEAST ({4500, 0}): force comes from bottom-right, 3rd axis not used.
	 */
	public static final int[] SOUTHEAST = {4500, 0};
	
	/**
	 * The constant SOUTH ({9000, 0}): force comes from bottom, 3rd axis not used.
	 */
	public static final int[] SOUTH = {9000, 0};
	
	/**
	 * The constant SOUTHWEST ({13500, 0}): force comes from bottom-left, 3rd axis not used.
	 */
	public static final int[] SOUTHWEST = {13500, 0};
	
	/**
	 * The constant WEST ({18000, 0}): force comes from left, 3rd axis not used.
	 */
	public static final int[] WEST = {18000, 0};
	
	/**
	 * The constant NORTHWEST ({22500, 0}): force comes from top-left, 3rd axis not used.
	 */
	public static final int[] NORTHWEST = {22500, 0};
	
	
	
	
	private int[] sphericalCoordinates; // size: 2
	
	/**
	 * Instantiates a new spherical direction with the specified coordinate values.
	 * 
	 * @param sphericalCoordinates
	 *            the spherical coordinates (an int array of size 2, values from 0 to 35999)
	 */
	public SphericalDirection(int[] sphericalCoordinates) {
		super(DIRECTION_SPHERICAL);
		this.setSphericalCoordinates(sphericalCoordinates);
	}
	
	/**
	 * Instantiates a new spherical direction with the default direction (NORTHWEST), 3rd axis not used.
	 */
	public SphericalDirection() {
		super(DIRECTION_SPHERICAL);
		this.setSphericalCoordinates(NORTHWEST); //if nothing is set, the default direction is NORTHWEST
	}

	
	/**
	 * Gets the spherical coordinate values.
	 * 
	 * @return the spherical coordinates (an int array of size 2, values from 0 to 35999)
	 */
	public int[] getSphericalCoordinates() {
		return sphericalCoordinates;
	}


	/**
	 * Sets the spherical coordinate values.
	 * 
	 * @param sphericalCoordinates
	 *            the new spherical coordinates (an int array of size 2, values from 0 to 35999)
	 */
	public void setSphericalCoordinates(int[] sphericalCoordinates) {
		if (sphericalCoordinates.length != 2){
			createFeatureNotSupportedEvent(sphericalCoordinates.length);
			sphericalCoordinates = NORTHWEST;
		}
		if (sphericalCoordinates[0] < 0 || sphericalCoordinates[0] > 35999){
			createFeatureNotSupportedEvent(sphericalCoordinates[0]);
			sphericalCoordinates[0] = NORTHWEST[0];
		}
		if (sphericalCoordinates[1] < 0 || sphericalCoordinates[1] > 35999){
			createFeatureNotSupportedEvent(sphericalCoordinates[1]);
			sphericalCoordinates[1] = NORTHWEST[1];
		}
		this.sphericalCoordinates = sphericalCoordinates;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @return the name of the direction type: "Spherical"
	 */
	@Override
	public String getName() {
		return "Spherical";
	}
	
	@Override
	/**
	 * {@inheritDoc}
	 */
	public CartesianDirection toCartesianDirection(Direction direction) {
		if (sphericalCoordinates[1] == 0) {
			if (sphericalCoordinates[0] == 0) {
				return new CartesianDirection(new int[]{1, 0, 0});
			} else {
				int scalar = 1000000000;
				double phi = ((double)(sphericalCoordinates[0]))/18000*Math.PI;
				return new CartesianDirection(new int[]{
						(int)(Math.cos(phi)*scalar), 
						(int)(Math.sin(phi)*scalar),
						0
														})
				;
			}
		} else {
			int scalar = 1000000000;
			double phi = ((double)(sphericalCoordinates[0]))/18000*Math.PI;
			double coTheta = ((double)(sphericalCoordinates[1]))/18000*Math.PI;
			return new CartesianDirection(new int[]{
					(int)(Math.cos(coTheta)*Math.cos(phi)*scalar), 
					(int)(Math.cos(coTheta)*Math.sin(phi)*scalar),
					(int)(Math.sin(coTheta)*scalar)
													})
			;
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * The as PolarDirection supports only 2 FF axes, the second angle value will be ignored.
	 */
	@Override
	public PolarDirection toPolarDirection(Direction direction) {
		return new PolarDirection((this.sphericalCoordinates[0]+9000)%36000);
	}

	/**
	 * {@inheritDoc}
	  * 
	 * As the Direction is a SphericalDirection it returns <code>this</code> (not a copy, but really this object)
	*/
	@Override
	public SphericalDirection toSphericalDirection(Direction direction) {
		return this; 
	}
	
}