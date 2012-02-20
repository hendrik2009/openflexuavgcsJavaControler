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


package at.wisch.joystick.exception;

/**
 * The Class SDLErrorException. Indicates that an error happened during a call to the native SDL subsystem.
 * 
 * @author Martin Wischenbart
 */
public class SDLErrorException extends FFJoystickException {

	private static final long serialVersionUID = -5431737913433699103L;
	
	private String errorMessage;
	
	/**
	 * Instantiates a new SDLErrorException with an unknown reason.
	 */
	public SDLErrorException() {
		super();
		this.errorMessage = "SDL: unknown message";
	}
	
	/**
	 * Instantiates a new SDLErrorException with a detailed error message.
	 * 
	 * @param errorMessage
	 *            the error message from the native SDL error
	 */
	public SDLErrorException(String errorMessage) {
		super();
		this.errorMessage = "SDL: "+errorMessage;
	}
	
	/* (non-Javadoc)
	 * @see at.wisch.joystick.exception.FFJoystickException#getErrorMessage()
	 */
	/**
	 * {@inheritDoc} The message's prefix will be "SDL: ".
	 */
	@Override
	public String getErrorMessage(){
		return errorMessage;
	}

}
