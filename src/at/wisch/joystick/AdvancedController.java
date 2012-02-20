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


package at.wisch.joystick;

import org.lwjgl.input.Controller;


/**
 * The interface AdvancedController. AdvancedController extends the interface Controller to support
 * more controller capabilities (e.g. allows unlimited amount of axes, buttons,
 * POVs and trackballs).<br>
 * <br>
 * It also allows checking for force feedback (FF) support.
 * 
 * @author Martin Wischenbart
 */
public interface AdvancedController extends Controller {

	
	/**
	 * Checks if the device supports force feedback (FF).<br>
	 * <br>
	 * If this method returns true, it is safe to cast the AdvancedController to an {@link AdvancedFFController}.
	 * 
	 * @return true, if the controller was recognized as a FF device. false, if
	 *         the device does not have FF capabilities.
	 *         
	 * @see AdvancedFFController
	 */
	public boolean isFFJoystick();
	
	// #################### POVS ######################
	
	/**
	 * Gets the device's point-of-view (POV) count. POV is also known as "hat".
	 * 
	 * POVs are digital sticks, that can be in the center, up, down, left, right
	 * or combinations (up-left, up-right, down-left, down-right).
	 * 
	 * @return the number of POVs on the device
	 * 
	 * @see #getButtonCount()
	 * @see #getAxisCount()
	 * @see #getBallCount()
	 */
	public int getPovCount();
	
	/**
	 * Gets the POV's name.
	 * 
	 * @param povIndex
	 *            the POV index (POV indices start from 0)
	 * 
	 * @return the POV's name (which is just povIndex+1)
	 * 
	 * @see #getPovCount()
	 */
	public String getPovName(int povIndex);
	
	/**
	 * Gets the X coordinate's value of a POV. (X axis of a POV is the
	 * left-right-direction)
	 * 
	 * @param povIndex
	 *            the POV index (POV indices start from 0)
	 * 
	 * @return the POV's X coordinate: POV_AXIS_NEGATIVE for left,
	 *         POV_AXIS_NEUTRAL for center, POV_AXIS_POSITIVE for right
	 *         
	 * @see #getPovDirection(int)
	 */
	public int getPovX(int povIndex) ;
	
	/**
	 * Gets the Y coordinate's value of a POV. (Y axis of a POV is the
	 * up-down-direction)
	 * 
	 * @param povIndex
	 *            the POV index (POV indices start from 0)
	 * 
	 * @return the POV's Y coordinate: POV_AXIS_NEGATIVE for up,
	 *         POV_AXIS_NEUTRAL for center, POV_AXIS_POSITIVE for down
	 *         
	 * @see #getPovDirection(int)
	 */
	public int getPovY(int povIndex) ;
	
	/**
	 * Gets the direction of a POV.
	 * 
	 * @param povIndex
	 *            the POV index (POV indices start from 0)
	 * 
	 * @return the POVs direction: POV_CENTERED for center, or one of the
	 *         following: POV_DOWN, POV_DOWN_LEFT, POV_DOWN_RIGHT, POV_LEFT,
	 *         POV_RIGHT, POV_UP, POV_UP_LEFT, POV_UP_RIGHT
	 *         
	 * @see #getPovX(int)
	 * @see #getPovY(int)
	 */
	public int getPovDirection(int povIndex) ;
	
	// NOTE: these special methods exist only for the first 4 POVs
	
	/**
	 * Gets the first POV's X coordinate value.
	 * 
	 * @return getPovValueX(0)
	 * 
	 * @see #getPovX(int)            
	 */
	public int getPov1X() ;
	
	/**
	 * Gets the first POV's Y coordinate value.
	 * 
	 * @return getPovValueY(0)
	 * 
	 * @see #getPovY(int)            
	 */
	public int getPov1Y() ;
	
	/**
	 * Gets the second POV's X coordinate value.
	 * 
	 * @return getPovValueX(1)
	 * 
	 * @see #getPovX(int)            
	 */
	public int getPov2X() ;
	
	/**
	 * Gets the second POV's Y coordinate value.
	 * 
	 * @return getPovValueY(1)
	 * 
	 * @see #getPovY(int)            
	 */
	public int getPov2Y() ;
	
	/**
	 * Gets the third POV's X coordinate value.
	 * 
	 * @return getPovValueX(2)
	 * 
	 * @see #getPovX(int)            
	 */
	public int getPov3X() ;
	
	/**
	 * Gets the third POV's Y coordinate value.
	 * 
	 * @return getPovValueY(2)
	 * 
	 * @see #getPovY(int)            
	 */
	public int getPov3Y() ;
	
	/**
	 * Gets the fourth POV's X coordinate value.
	 * 
	 * @return getPovValueX(3)
	 * 
	 * @see #getPovX(int)            
	 */
	public int getPov4X() ;
	
	/**
	 * Gets the fourth POV's Y coordinate value.
	 * 
	 * @return getPovValueY(3)
	 * 	 *             
	 * @see #getPovY(int)            
	 */
	public int getPov4Y() ;
	
	// #################### AXES ######################
	
	// NOTE: these special methods exist only for the first 8 axes
	
	/**
	 * Get the value from the U axis if there is one. If the axis does not exist AXIS_NEUTRAL will be
	 * returned.
	 * 
	 * @return the U axis value: AXIS_NEUTRAL, AXIS_MINIMUM, AXIS_MAXIMUM or
	 *         anything between AXIS_MINIMUM and AXIS_MAXIMUM
	 * @see #getAxisValue(int)
	 */
	public float getUAxisValue() ;
	
	/**
	 * Get the value from the V axis if there is one. If the axis does not exist AXIS_NEUTRAL will be
	 * returned.
	 * 
	 * @return the V axis value: AXIS_NEUTRAL, AXIS_MINIMUM, AXIS_MAXIMUM or
	 *         anything between AXIS_MINIMUM and AXIS_MAXIMUM
	 * @see #getAxisValue(int)
	 */
	public float getVAxisValue() ;
	
	/**
	 * Get the dead zone for the U axis.
	 * 
	 * @return the dead zone for the U axis
	 * 
	 * @see #setUAxisDeadZone(float)       
	 * @see #getDeadZone(int)

	 */
	public float getUAxisDeadZone() ;
	
	/**
	 * Get the dead zone for the V axis.
	 * 
	 * @return the dead zone for the V axis
	 * 
	 * @see #setVAxisDeadZone(float)       
	 * @see #getDeadZone(int)

	 */
	public float getVAxisDeadZone() ;
	
	/**
	 * Set the dead zone for the U axis. Typical values are between 0.01f and
	 * 0.2f.
	 * 
	 * @param zone
	 *            the dead zone to use for the U axis: a value from 0f to AXIS_MAXIMUM
	 *            
	 * @see #getUAxisDeadZone()       
	 * @see #setDeadZone(int, float)	
	 */
	public void setUAxisDeadZone(float zone) ;	
	
	/**
	 * Set the dead zone for the V axis. Typical values are between 0.01f and
	 * 0.2f.
	 * 
	 * @param zone
	 *            the dead zone to use for the V axis: a value from 0f to AXIS_MAXIMUM
	 * @see #getVAxisDeadZone()       
	 * @see #setDeadZone(int, float)	           
	 */
	public void setVAxisDeadZone(float zone) ;
	
	// ################## TRACKBALLS ###################
	
	/**
	 * Gets the number of trackballs (balls) a device has.
	 * 
	 * @return the number of trackballs on the device
	 * 
	 * 	 * @see #getButtonCount()
	 * @see #getPovCount()
	 * @see #getAxisCount()

	 */
	public int getBallCount();
	
	/**
	 * Gets the trackball's name.
	 * 
	 * @param ballIndex
	 *            the trackball index (ball indices start from 0)
	 * 
	 * @return the trackball's name (which is just ballIndex+1)
	 * 
	 * @see #getBallCount()
	 */
	public String getBallName(int ballIndex) ;
	
	/**
	 * Gets the delta for the trackball specified by ballIndex. The delta represents the movements in X and Y directions.
	 * 
	 * @param ballIndex
	 *            the trackball index (ball indices start from 0)
	 * 
	 * @return the trackball delta: index [0] represents the X movement, index [1] represents the Y movement (the size of the array will always be 2)
	 */
	public int[] getBallDelta(int ballIndex) ;
	
	// NOTE: the following special methods exist only for the first 4 trackballs
	/**
	 * Gets the delta for the first trackball (ballIndex 0)
	 * 
	 * @return delta for the first trackball
	 * @see #getBallDelta(int)
	 */
	public int[] getBall1Delta() ;	
	
	/**
	 * Gets the delta for the second trackball (ballIndex 1)
	 * 
	 * @return delta for the second trackball
	 * 
	 * @see #getBallDelta(int)
	 */
	public int[] getBall2Delta() ;	
	
	/**
	 * Gets the delta for the third trackball (ballIndex 2)
	 * 
	 * @return delta for the third trackball
	 * @see #getBallDelta(int)
	 */
	public int[] getBall3Delta() ;
	
	/**
	 * Gets the delta for the fourth trackball (ballIndex 3)
	 * 
	 * @return delta for the fourth trackball
	 * @see #getBallDelta(int)
	 */
	public int[] getBall4Delta() ;

	
}
