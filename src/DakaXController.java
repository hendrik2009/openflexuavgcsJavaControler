import iadZhdk.dakaX.DakaX;
import processing.core.PApplet;


public class DakaXController extends PApplet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	static final int CMD_SET_PITCH			= DakaX.DAKAX_MSG_ID_USER + 3;
	static final int CMD_SET_YAW			= DakaX.DAKAX_MSG_ID_USER + 4;
	static final int CMD_SET_ROLL			= DakaX.DAKAX_MSG_ID_USER + 2;
	static final int CMD_HOME_ROT			= DakaX.DAKAX_MSG_ID_USER + 6;
	static final int CMD_RESET_ROT			= DakaX.DAKAX_MSG_ID_USER + 5;
	
	public int yaw = 128;
	public int pitch = 128;
	public int roll = 128;
	
	
	public DakaX _board;
	
	public final PApplet _parent;
	
	// INIT
	public DakaXController(PApplet parent){
		
		_parent	= parent;
		
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
		_board 	= new DakaX(_parent ,portName,baudrate);
		if(_board.isOpen() == false)
		  {
			System.out.println("Can't open SerialPort !");
		  }
	}
	
	// MAIN
	public void draw(){
		  _board.update();
	}
	
	// COMMANDS
	public void command(int cmd, int val){
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
			  System.out.println("setRoll");
		    _board.serialCom().beginSend(CMD_SET_ROLL);
		    _board.serialCom().sendByte(val);
		    _board.serialCom().endSend();
		    break;
		  case CMD_SET_PITCH:
		     _board.serialCom().beginSend(CMD_SET_PITCH);
		     _board.serialCom().sendByte(val);
		    _board.serialCom().endSend();
		    break;
		  case CMD_SET_YAW:
		    _board.serialCom().beginSend(CMD_SET_YAW);
		    _board.serialCom().sendByte(val);
		    _board.serialCom().endSend();
		    break;
		    default:
		    	System.out.println("DakaXController::Command: ERROR - CMD -:"+cmd+" notfound" );
		  
		  }
	}
	
	public void setRPY(int yaw, int pitch, int roll){
		this.command(CMD_SET_YAW,yaw);
		this.command(CMD_SET_PITCH,pitch);
		this.command(CMD_SET_ROLL,roll);
	}
}
