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

import java.util.ArrayList;
import java.util.Iterator;

import at.wisch.joystick.exception.*;

/**
 * The class JoystickManager. This is the main class for handling and recieving
 * {@link Joystick} and {@link FFJoystick} objects. <br>
 * <br>
 * Some methods of this class will throw {@link FFJoystickException}. Be aware
 * that these are fatal errors. If these errors occur, neiter input, nor FF will
 * work.<br>
 * <br>
 * To get an introduction how to use this class, take a look at the sourcecode
 * of JoystickInputDemo and JoystickForceDemo. 
 * 
 * @see at.wisch.joystick.test.JoystickInputDemo
 * @see at.wisch.joystick.test.JoystickForceDemo
 * 
 * @author Martin Wischenbart
 */
public class JoystickManager{

	private static final String consolePrefix = "[joystick]";

	private static boolean initialized = false;

	private static int numOfJoysticks;
	private static ArrayList<Joystick> joysticks;
	private static ArrayList<FFJoystick> ffjoysticks;

	private static EventPollThread eventPoller;
	private static boolean eventPollingEnabled;


	// empty constructor
	protected JoystickManager() {
		super();
	}
	
	
	/**
	 * Initialize the native JoystickManager subsystem.
	 * 
	 * @throws FFJoystickException
	 *             During init() this is a fatal error. We will not be able to
	 *             get joystick objects later on.
	 */
	public static void init() throws FFJoystickException { 

		System.out.println(consolePrefix+" Loading library ffjoystick... ");
		System.loadLibrary("ffjoystick");

		joysticks = new ArrayList<Joystick>();
		ffjoysticks = new ArrayList<FFJoystick>();

		System.out.println(consolePrefix+" Initializing SDL... ");
		sdlInitSDL();
		sdlInitJoysticks();
		sdlInitEvents();
		initialized = true;

	}


	// ##################################### INIT ########################################

	private static void sdlInitSDL() throws SDLErrorException {
		sdlInitNative();
	}
	private static native int sdlInitNative() throws SDLErrorException;

	private static void sdlInitJoysticks() throws FFJoystickException {
		numOfJoysticks = getNumOfJoysticks();
		System.out.println(consolePrefix+" "+numOfJoysticks+" joystick(s) present");
		for (int j = 0; j < numOfJoysticks; j++){
			openJoystick(j);
			int ffJoystickIndex = openFFJoystick(j);
			if (ffJoystickIndex < 0) {
				// ordinary joystick
				Joystick js = new Joystick(j);
				joysticks.add(js);
			} else {
				// FF joystick
				FFJoystick ffjs = new FFJoystick(j, ffJoystickIndex);
				joysticks.add(ffjs);
				ffjoysticks.add(ffjs);
			}
		}
	}

	/**
	 * Reopen joysticks. If the connection to the joysticks was lost, e.g.
	 * because of another application, you can try to reopen the connection.
	 * This is especially helpful for FFJoysticks.
	 */
	public static void reopenJoysticks() {
		for (int j = 0; j < numOfJoysticks; j++){
			try {
				openJoystick(j);
				openFFJoystick(j);
			} catch (SDLErrorException e) {
				// we do not mind if reopen did not work - maybe the joystick was unplugged
				// e.printErrorMessage();
			}
		}
	}

	private static native int getNumOfJoysticks();
	private static native int openJoystick(int joystickIndex) throws SDLErrorException;
	private static native int openFFJoystick(int joystickIndex) throws SDLErrorException;


	// ##################################### JOYSTICKS ########################################

	/**
	 * Gets a joystick. If there are more than one joysticks connected, this will usually return the first one (i.e. the one with the lowest index).
	 * 
	 * @return a Joystick object
	 * 
	 * @throws JoystickManagerNotInitializedException
	 *             The joystick manager was not initialized. You have to call {@link #init()} first.
	 * @throws JoystickNotFoundException
	 *             No joystick was found.
	 */
	public static Joystick getJoystick() throws JoystickManagerNotInitializedException, JoystickNotFoundException{
		if (!initialized) {
			throw new JoystickManagerNotInitializedException();
		} else if (joysticks.isEmpty()) {
			throw new JoystickNotFoundException();
		} else {
			return joysticks.get(0);
		}
	}

	/**
	 * Gets a force feedback joystick. If there are more than one FF joysticks connected, this will usually return the first one (i.e. the one with the lowest index).
	 * 
	 * @return a FFJoystick object
	 * 
	 * @throws JoystickManagerNotInitializedException
	 *             The joystick manager was not initialized. You have to call {@link #init()} first.
	 * @throws JoystickNotFoundException
	 *             No force feedback joystick was found.
	 */
	public static FFJoystick getFFJoystick() throws JoystickManagerNotInitializedException, JoystickNotFoundException {
		if (!initialized) {
			throw new JoystickManagerNotInitializedException();
		} else if (ffjoysticks.isEmpty()) {
			throw new JoystickNotFoundException();
		} else {
			return ffjoysticks.get(0);
		}
	}

	/**
	 * Gets a joystick with a certain index.
	 * 
	 * @return a Joystick object
	 * 
	 * @param joystickIndex
	 *            the joystick's index
	 * 
	 * @throws JoystickManagerNotInitializedException
	 *             The joystick manager was not initialized. You have to call {@link #init()} first.
	 * @throws JoystickNotFoundException
	 *             There is no joystick with that joystickIndex in the collection.
	 */ 
	public static Joystick getJoystick(int joystickIndex) throws JoystickManagerNotInitializedException, JoystickNotFoundException {
		if (!initialized) {
			throw new JoystickManagerNotInitializedException();
		} else if (joysticks.isEmpty() || joystickIndex < 0 || joystickIndex >= joysticks.size()) {
			throw new JoystickNotFoundException();
		} else {
			return joysticks.get(joystickIndex);
		}
	}

	/**
	 * Gets an ArrayList containing all joysticks.
	 * 
	 * @return an ArrayList of Joystick objects
	 * 
	 * @throws JoystickManagerNotInitializedException
	 *             The joystick manager was not initialized. You have to call {@link #init()} first.
	 */
	public static ArrayList<Joystick> getAllJoysticks() throws JoystickManagerNotInitializedException {
		if (!initialized) throw new JoystickManagerNotInitializedException();
		return joysticks;
	}

	/**
	 * Gets an ArrayList containing all force feedback joysticks.
	 * 
	 * @return an ArrayList of FFJoystick objects
	 * 
	 * @throws JoystickManagerNotInitializedException
	 *             The joystick manager was not initialized. You have to call {@link #init()} first.
	 */
	public static ArrayList<FFJoystick> getAllFFJoysticks() throws JoystickManagerNotInitializedException {
		if (!initialized) throw new JoystickManagerNotInitializedException();
		return ffjoysticks;
	}


	// ####################################### PAUSE/STOP JOYSTICKS ##########################################

	/**
	 * Pause all effects on all FF joysticks, that are currently playing. (i.e. call {@link at.wisch.joystick.FFJoystick#pause()} on every FF joystick)
	 */ 
	@SuppressWarnings("deprecation")
	public static void pauseAll() {
		Iterator<FFJoystick> iter = ffjoysticks.iterator();
		FFJoystick js;
		while (iter.hasNext()){
			js = iter.next();
			js.pause();
		}
	}

	/**
	 * Resume all effects on all FF joysticks, that were playing before they were paused. (i.e. call {@link at.wisch.joystick.FFJoystick#unpause()} on every FF joystick)
	 */ 
	@SuppressWarnings("deprecation")
	public static void unpauseAll() {
		Iterator<FFJoystick> iter = ffjoysticks.iterator();
		FFJoystick js;
		while (iter.hasNext()){
			js = iter.next();
			js.unpause();
		}
	}

	/**
	 * Stop all effects on all FF joysticks. (i.e. call {@link at.wisch.joystick.FFJoystick#stopAll()} on every FF joystick)
	 */
	public static void stopAllJoysticks() {
		Iterator<FFJoystick> iter = ffjoysticks.iterator();
		FFJoystick js;
		while (iter.hasNext()){
			js = iter.next();
			js.stopAll();
		}
	}


	// ##################################### EVENT POLLING ########################################

	private static int sdlInitEvents()throws FFJoystickException  {
		eventPollingEnabled = false;
		return sdlInitEventsNative();
	}

	private static native int sdlInitEventsNative()throws SDLErrorException ;


	/**
	 * Checks if event polling is enabled.
	 * 
	 * @return true, if is event polling enabled. false, if disabled.
	 */
	public static boolean isEventPollingEnabled() {
		return eventPollingEnabled;
	}

	/**
	 * Enable joystick input event polling. Once enabled, every input event will cause controllerEventOccured(event) to be called on all listeners that were previously registered using ControllerEventManager.
	 * 
	 * @return true, if successful. false, otherwise.
	 * 
	 * @throws JoystickManagerNotInitializedException
	 *             The joystick manager was not initialized. You have to call {@link #init()} first.
	 * @throws FFJoystickException
	 *             Another error occured, probably fatal. Event polling will not work.
	 *             
	 * @see at.wisch.joystick.event.ControllerEventListener#controllerEventOccured(AdvancedControllerEvent)
	 * @see at.wisch.joystick.event.ControllerEventManager#addControllerEventListener(ControllerEventListener)            
	 */
	public static boolean enableJoystickEventPolling() throws JoystickManagerNotInitializedException, FFJoystickException {
		if (!initialized) {
			throw new JoystickManagerNotInitializedException();
		}
		int success = enableJoystickEventPollingNative();
		if (success < 0){
			return false;
		}else {
			eventPollingEnabled = true;
			eventPoller = new EventPollThread();
			eventPoller.start();
			return true;
		}
	}
	private static native int enableJoystickEventPollingNative()throws SDLErrorException ;

	/**
	 * Disable joystick input event polling.
	 * 
	 * @return true, if successful. false, otherwise.
	 * 
	 * @throws FFJoystickException
	 *             the FF joystick exception
	 */
	public static boolean disableJoystickEventPolling() throws FFJoystickException {
		if (!initialized) {
			throw new JoystickManagerNotInitializedException();
		}
		int success = disableJoystickEventPollingNative();

		if (success < 0){
			return false;
		}else {
			eventPollingEnabled = false;
			return true;
		}
	}
	private static native int disableJoystickEventPollingNative()throws SDLErrorException ;


	// ####################################### CLOSING ##########################################

	/**
	 * Close the native JoystickManager subsystem. All FF effects on all joysticks will be stopped first and event polling will be disabled.
	 */
	public static void close() {
		System.out.println(consolePrefix+" Closing...");
		try {
			if (isEventPollingEnabled()) {
				disableJoystickEventPolling();
			}
			stopAllJoysticks();
		} catch (FFJoystickException e) {
			e.printErrorMessage();
		}
		initialized = false;
		closeSDLnative();
	}


	private static native void closeSDLnative();

}






// ####################################################################################################
// ##################################### class EventPollThread ########################################
// ####################################################################################################

class EventPollThread extends Thread {

	private static final String consolePrefix = "[joystick-event]"; 

	int[] returnedArray;

	@Override
	public void run() {
		do {
			try {
				returnedArray = pollForEvent();
				if (returnedArray[0] != 0) { //we have an event
					Joystick.createControllerEvent(returnedArray);
				}
				Thread.sleep(1);
			} catch (SDLErrorException e) {
				System.err.print(consolePrefix+" Error in EventPollThread: ");
				e.printErrorMessage();
			} catch (InterruptedException e) {
				System.err.println(consolePrefix+" EventPollThread was interrupted.");
				e.printStackTrace();
			}

			// Even if event polling is already disabled, we keep on processing
			// events until there are no more events in the queue.
		} while (JoystickManager.isEventPollingEnabled() || returnedArray[0] != 0);
	}

	private native int[] pollForEvent() throws SDLErrorException ;


}
