import iadZhdk.dakaX.DakaX;
import processing.core.PApplet;


public class DakaXController extends PApplet {

	static final int CMD_HOME_ROT			= DakaX.DAKAX_MSG_ID_USER + 1;
	static final int CMD_RESET_ROT			= DakaX.DAKAX_MSG_ID_USER + 2;
	static final int CMD_SET_ROLL			= DakaX.DAKAX_MSG_ID_USER + 3;
	static final int CMD_SET_PITCH			= DakaX.DAKAX_MSG_ID_USER + 4;
	static final int CMD_SET_YAW			= DakaX.DAKAX_MSG_ID_USER + 5;
	
	public DakaX _board;
	
	
	// INIT
	public void setup(){
		String portName = "";
		String os = System.getProperty("os.name").toLowerCase();
		  if(os.indexOf( "nix") >=0 || os.indexOf( "nux") >=0)
		  {  // linux/unix
		    //portName = "/dev/ttyUSB0";
		   // portName = "/dev/rfcomm0"; 
		    portName = "/dev/rfcomm1";
		  }
		  else if(os.indexOf( "mac" ) >= 0)
		  {  // mac
		    //portName = "/dev/tty.usbserial-FTF5HT10"; // cable
		    portName = "/dev/tty.dakaX_Bt-DevB";  // bluetooth
		  }
		  else
		  {  // windows
		    portName = "COM9";  // cable
		    portName = "COM43";  // bluetooth
		  }
		int baudrate 	= 230400;
		_board 	= new DakaX((PApplet)this,portName,baudrate);
		if(_board.isOpen() == false)
		  {
			println("Can't open SerialPort !");
			//exit();
		  }
		
	}
	
	// MAIN
	/*public void draw(){
		  // update the sensor data
		  _board.update();
	}*/
	
	// COMMANDS
	public void command(int cmd, int[] val){
		switch(cmd)
		  {
		  case CMD_HOME_ROT:
		    // set home position
		    _board.setHomeRot();
		    break;
		  case CMD_RESET_ROT:
		    // reset home position
		    _board.resetHomeRot();
		    break;
		  case CMD_SET_ROLL :
		    // off pin d0
		    _board.serialCom().beginSend(CMD_SET_ROLL);
		    _board.serialCom().sendByte(val[0]);
		    _board.serialCom().endSend();
		    println("D0 -> ON");
		    break;
		  case CMD_SET_PITCH:
		     _board.serialCom().beginSend(CMD_SET_PITCH);
		     _board.serialCom().sendByte(val[0]);
		    _board.serialCom().endSend();
		    println("DO -> OFF");
		    break;
		  case CMD_SET_YAW:
		    _board.serialCom().beginSend(CMD_SET_YAW);
		    _board.serialCom().sendByte(val[0]);
		    _board.serialCom().endSend();
		    println("AO -> " + 0);
		    break;
		    default:
		    	System.out.println("DakaXController::Command: ERROR - CMD -:"+cmd+" notfound" );
		  
		  }
	}
	
}
