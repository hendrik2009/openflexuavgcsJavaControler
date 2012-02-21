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



package at.wisch.joystick.test;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;


import at.wisch.joystick.*;
import at.wisch.joystick.event.*;
import at.wisch.joystick.exception.*;
import at.wisch.joystick.ffeffect.*;
import at.wisch.joystick.ffeffect.direction.*;

/**
 * The JoystickTest GUI. It creates a graphical user interface to view the inputs of different joysticks and play various force feedback effects.
 * 
 * @see JoystickInputDemo
 * @see JoystickInputDemo
 * 
 * @author Martin Wischenbart
 */
public class JoystickTest extends JFrame implements ControllerEventListener, FeatureNotSupportedEventListener {

	private static final long serialVersionUID = 7989078461104748962L;

	private static JoystickTest jsTest;
	private static boolean running;

	private static JLabel stateLabel;
	private static String stateText;
	private static JMenu ffMenu, strengthMenu, directionMenu, effectMenu1, effectMenu2;
	private static JMenuItem startEventlistenerAction, stopEventlistenerAction, stopFFAction, simpleFFAction;

	private static ArrayList<Joystick> joysticks;
	private static Joystick joystick;
	static FFJoystick ffjoystick;
	private static ExtendedEffect simpleEffect;


	//MAIN
	/**
	 * The main method.
	 * 
	 * @param args
	 *            (no args needed)
	 */
	public static void main(String[] args) {
		System.out.println("Initializing JoystickManager and Joysticks...");
		jsTest = new JoystickTest();

		FeatureNotSupportedEventManager.addFeatureNotSupportedEventListener(jsTest);

		System.out.println("Creating Window...");
		createWindow();
		running = true;
		showDataLoop();

	}

	//CONSTRUCTOR
	private JoystickTest() {
		try {
			JoystickManager.init();
			joysticks = JoystickManager.getAllJoysticks();
			joystick = JoystickManager.getJoystick();

		} catch (FFJoystickException e) {
			e.printErrorMessage();
		}
		if (joysticks.isEmpty()) System.exit(0);
	}


	private static void closeProgram() {
		running = false;
		JoystickManager.close();
		jsTest.dispose();
	}


	private static void createWindow() {

		// MAIN WINDOW PROPERTIES

		jsTest.setTitle("JoystickTest");
		stateLabel = new JLabel();
		stateLabel.setHorizontalAlignment(JLabel.CENTER);

		JScrollPane scrollPane = new JScrollPane(stateLabel);
		jsTest.setContentPane(scrollPane);
		jsTest.setSize(400, 300);
		jsTest.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e)
			{
				JoystickTest.closeProgram();
			}
		});

		// Create a menubar
		JMenuBar menuBar = new JMenuBar();

		// Define and add menus
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		JMenu joystickMenu = new JMenu("Joystick");
		menuBar.add(joystickMenu);
		ffMenu = new JMenu("ForceFeedback");
		menuBar.add(ffMenu);

		// Add the menubar to the frame
		jsTest.setJMenuBar(menuBar);




		// MENUITEMS FILE

		startEventlistenerAction = new JMenuItem("Enable Event Listening");
		stopEventlistenerAction = new JMenuItem("Disable Event Listening");
		stopEventlistenerAction.setEnabled(false);
		JMenuItem exitAction = new JMenuItem("Exit");
		fileMenu.add(startEventlistenerAction);
		fileMenu.add(stopEventlistenerAction);
		fileMenu.addSeparator();
		fileMenu.add(exitAction);


		// Add listeners to the menu items
		startEventlistenerAction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				startEventlistenerAction.setEnabled(false);
				ControllerEventManager.addControllerEventListener(jsTest);
				stopEventlistenerAction.setEnabled(true);
			}
		});

		stopEventlistenerAction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				stopEventlistenerAction.setEnabled(false);
				ControllerEventManager.removeControllerEventListener(jsTest);
				startEventlistenerAction.setEnabled(true);
			}
		});
		exitAction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JoystickTest.closeProgram();
			}
		});


		// MENUITEMS JOYSTICK

		Joystick js;
		for (int i = 0; i < joysticks.size(); i++) {
			js = joysticks.get(i);
			JMenuItem joystickSelector = new JMenuItem(js.getIndex()+": "+js.getName());
			joystickSelector.addActionListener(new JoystickSelectionActionListener(js));
			joystickMenu.add(joystickSelector);
		}
		joystickMenu.addSeparator();
		JMenuItem joystickReopen = new JMenuItem("Reopen Joysticks");
		joystickReopen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JoystickManager.reopenJoysticks();
			}
		});
		joystickMenu.add(joystickReopen);

		setJoystick(joystick);

		stateText = "(no data)";
		stateLabel.setText(stateText);
		jsTest.setVisible(true);

	}

	/**
	 * Sets the Joystick used for displaying input and playing effects.
	 * 
	 * @param js
	 *            the joystick object
	 */
	public static void setJoystick(Joystick js){
		if (ffjoystick != null) {
			ffjoystick.destroyAll();
			ffMenu.removeAll();
		}
		joystick = js;
		System.out.println("Selected "+js.getName());
		System.out.println(" -> "+js.getDescription());
		if (joystick.isFFJoystick()) {
			
			ffjoystick = (FFJoystick)joystick;
			System.out.print(" -> ForceFeedback supported: ");
			System.out.println(ffjoystick.getFFDescription());

			if(ffjoystick.getName().equals("Logitech Force 3D Pro USB")) {
				// we set different dead zones for different axes
				ffjoystick.setDeadZone(0, 0.01f);
				ffjoystick.setDeadZone(1, 0.01f);
				ffjoystick.setDeadZone(2, 0.2f);
				ffjoystick.setDeadZone(3, 0.2f);
			}
			// we could add special adjustments for other joysticks here....
			
			JoystickTest.initFFMenu();
			ffMenu.setEnabled(true);
		} else {
			System.out.println(" -> ForceFeedback not supported");
			
			ffMenu.setEnabled(false);
		}
	}


	@SuppressWarnings("deprecation")
	private static void initFFMenu(){

			
		
		
			// MENUITEMS FORCEFEEDBACK

			stopFFAction = new JMenuItem("Stop All");
			stopFFAction.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					JoystickTest.ffjoystick.stopAll();
				}
			});
			ffMenu.add(stopFFAction);
			ffMenu.addSeparator();



			//Effect.setDefaultDirection(new SphericalDirection(new int[]{0, 9000}));
			
			// create a simple effect (+ also store it on button 1 (index 0)
			simpleEffect = ffjoystick.getSimpleEffect();
			if (simpleEffect == null) {
				System.out.println("No ExtendedEffect is supported!");
			} else {
				System.out.println("Uploading Simple Effect... (Button 1)");
				simpleEffect.setEffectLength(1000);
				simpleEffect.setStrength((int)(Effect.MAX_LEVEL*0.75));
				simpleEffect.setButtonIndex(0);
				if (ffjoystick.newEffect(simpleEffect)){
					simpleFFAction = new JMenuItem("Play Simple Effect once");
					simpleFFAction.addActionListener(new PlayEffectActionListener(simpleEffect));
					ffMenu.add(simpleFFAction);
					simpleFFAction = new JMenuItem("Play Simple Effect inf.");
					simpleFFAction.addActionListener(new PlayEffectInfActionListener(simpleEffect));
					ffMenu.add(simpleFFAction);

					strengthMenu = new JMenu("Set Strength");
					for (int i = 10; i <= 100; i+=10){
						JMenuItem menuitem = new JMenuItem(i+"%");
						menuitem.addActionListener(
							new StrengthSelectionActionListener(simpleEffect, 
									(int)(Effect.MAX_LEVEL*i*0.01)));
						strengthMenu.add(menuitem);
					}
					ffMenu.add(strengthMenu);
					
					
					directionMenu = new JMenu("Set Direction");
					JMenuItem menuitem;
					menuitem = new JMenuItem("North-West");
					menuitem.addActionListener(
							new DirectionSelectionActionListener(simpleEffect, 
									new SphericalDirection(SphericalDirection.NORTHWEST)));
					directionMenu.add(menuitem);
					menuitem = new JMenuItem("North");
					menuitem.addActionListener(
							new DirectionSelectionActionListener(simpleEffect, 
									new SphericalDirection(SphericalDirection.NORTH)));
					directionMenu.add(menuitem);
					menuitem = new JMenuItem("North-East");
					menuitem.addActionListener(
							new DirectionSelectionActionListener(simpleEffect, 
									new SphericalDirection(SphericalDirection.NORTHEAST)));
					directionMenu.add(menuitem);
					menuitem = new JMenuItem("East");
					menuitem.addActionListener(
							new DirectionSelectionActionListener(simpleEffect, 
									new SphericalDirection(SphericalDirection.EAST)));
					directionMenu.add(menuitem);
					menuitem = new JMenuItem("South-East");
					menuitem.addActionListener(
							new DirectionSelectionActionListener(simpleEffect, 
									new SphericalDirection(SphericalDirection.SOUTHEAST)));
					directionMenu.add(menuitem);
					menuitem = new JMenuItem("South");
					menuitem.addActionListener(
							new DirectionSelectionActionListener(simpleEffect, 
									new SphericalDirection(SphericalDirection.SOUTH)));
					directionMenu.add(menuitem);
					menuitem = new JMenuItem("South-West");
					menuitem.addActionListener(
							new DirectionSelectionActionListener(simpleEffect, 
									new SphericalDirection(SphericalDirection.SOUTHWEST)));
					directionMenu.add(menuitem);
					menuitem = new JMenuItem("West");
					menuitem.addActionListener(
							new DirectionSelectionActionListener(simpleEffect, 
									new SphericalDirection(SphericalDirection.WEST)));
					directionMenu.add(menuitem);
					ffMenu.add(directionMenu);
					ffMenu.addSeparator();
					
				}
			
			Iterator<Class<? extends Effect>> iter = ffjoystick.getSupportedEffects().iterator();
			Effect effect;
			JMenuItem effectMenuItem;
			effectMenu1 = new JMenu("Play Effect once");
			effectMenu2 = new JMenu("Play Effect inf.");
			
			int buttonIndex = 1;
			while (iter.hasNext()) {
				try {
					effect = iter.next().newInstance();
					System.out.print("Uploading Effect: "+effect.getName());
					if (buttonIndex < ffjoystick.getButtonCount()) {
						effect.setButtonIndex(buttonIndex);
						System.out.println(" (Button "+(++buttonIndex)+")"); //increment first, because Index 0 means button 1, 1 means 2, ...
					} else {
						System.out.println("");
					}
					if (ffjoystick.newEffect(effect)) {
						// uploading newEffect was successful, but the eventlistener makes the output
						effectMenuItem = new JMenuItem(effect.getName());
						effectMenuItem.addActionListener(new PlayEffectActionListener(effect));
						effectMenu1.add(effectMenuItem);
						effectMenuItem = new JMenuItem(effect.getName());
						effectMenuItem.addActionListener(new PlayEffectInfActionListener(effect));
						effectMenu2.add(effectMenuItem);
					}

				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
			ffMenu.add(effectMenu1);
			ffMenu.add(effectMenu2);
			
		}
	}
	


	private static void showDataLoop() {

		String buttons, axes, povs; // trackballs not supported yet

		do {
			joystick.poll();
			buttons = "";
			for (int b = 0; b < joystick.getButtonCount(); b++) {
				if (joystick.isButtonPressed(b)){
					buttons = buttons + " " + joystick.getButtonName(b);
				}

			}
			axes = "";
			float f;
			for (int a = 0; a < joystick.getAxisCount(); a++) {
				f = joystick.getAxisValue(a);
				if (f != 0f) f += 0.0000305f; // to make the displayed number look more nicely (ignore rounding error)
				axes = axes+joystick.getAxisName(a)+": "+f+"<br>";

			}
			povs = "";
			for (int p = 0; p < joystick.getPovCount(); p++) {
				povs = povs+"POV"+joystick.getPovName(p)+": "+joystick.getPovDirection(p)+"<br>";

			}
			stateLabel.setText(
					"<html>"
					+joystick.getName()+"<br>"
					+axes
					+povs
					+"Buttons: "+buttons+"</html>");
			
			//update the state every X ms -> sleep for some time
			try {
				System.out.println("tick");
				Thread.sleep(100); //X
			} catch (InterruptedException e) {
				e.printStackTrace();
				running = false;
			}			
		} while (running );
	}



	/* (non-Javadoc)
	 * @see at.wisch.joystick.event.ControllerEventListener#controllerEventOccured(at.wisch.joystick.event.AdvancedControllerEvent)
	 */
	@Override
	public void controllerEventOccured(AdvancedControllerEvent event) {
		System.out.println(event);

	}

	/* (non-Javadoc)
	 * @see at.wisch.joystick.event.FeatureNotSupportedEventListener#featureNotSupportedEventOccured(at.wisch.joystick.event.FeatureNotSupportedEvent)
	 */
	@Override
	public void featureNotSupportedEventOccured(FeatureNotSupportedEvent event) {
		System.out.println(event);
	}

}



class JoystickSelectionActionListener implements ActionListener{

	private Joystick js;

	JoystickSelectionActionListener( Joystick js ) {
		this.js = js;
	}
	public void actionPerformed(ActionEvent e) {
		JoystickTest.setJoystick(js);
	}
}


class StrengthSelectionActionListener implements ActionListener{

	private Effect effect;
	private int strength;

	StrengthSelectionActionListener( Effect effect, int strength ) {
		this.effect = effect;
		this.strength = strength;
	}
	
	public void actionPerformed(ActionEvent e) {
		effect.setStrength(strength);
		JoystickTest.ffjoystick.updateEffect(effect);
	}
}



class DirectionSelectionActionListener implements ActionListener{

	private ExtendedEffect effect;
	private Direction direction;

	DirectionSelectionActionListener( ExtendedEffect effect, Direction direction ) {
		this.effect = effect;
		this.direction = direction;
	}
	
	public void actionPerformed(ActionEvent e) {
		effect.setDirection(direction);
		JoystickTest.ffjoystick.updateEffect(effect);
		
	}
}


class PlayEffectActionListener implements ActionListener{

	private Effect effect;
	
	PlayEffectActionListener( Effect effect ) {
		this.effect = effect;
	}
	
	public void actionPerformed(ActionEvent e) {
		JoystickTest.ffjoystick.playEffect(effect, 1);
		
	}
}

class PlayEffectInfActionListener implements ActionListener{

	private Effect effect;
	
	PlayEffectInfActionListener( Effect effect ) {
		this.effect = effect;
	}
	
	public void actionPerformed(ActionEvent e) {
		JoystickTest.ffjoystick.playEffect(effect, FFJoystick.INFINITE_TIMES);
		
	}
}