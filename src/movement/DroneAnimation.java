package movement;

import movement.animations.DroneVector;
import movement.animations.MovementAlgorithm;
import movement.animations.MovementObject;

public class DroneAnimation {

	private boolean _loop	= true;
	
	//params
	private int _axis		= -1; // 0 = x 1= y 2 = z 3 = rotation	
	private int _length 		= 200;// steps to run one animation -- timer calls each 100ms so its 2 seconds
	private float _strength	= 0; // movement intensity
	
	// moveloop
	private MovementObject _obj;
	private int _curPos 	=0;
	
	DroneVector[] _moveMap;
	
	/**Stores a Movement and maps it to the given length
	 * 
	 */
	public DroneAnimation(){
		
	}
	public DroneAnimation(boolean loop, int length, MovementObject obj, float strength){
		_loop = loop;
		_length = length;
		_strength = strength;
		_obj = obj;
				
		createMap();
	}
	public DroneVector increment(){
		_curPos++;
		_curPos = _curPos%_length;
		return _moveMap[_curPos];
	}
	public DroneVector decrement(){
		_curPos++;
		_curPos = _curPos%_length;
		return _moveMap[_curPos];
	}
	
	private void createMap(){
		_moveMap	= new DroneVector[_length];
		double stepVar = Math.PI*2/_length;
		double position = 0;
		for(int i=0; i<_length; i++){
			_moveMap[i] = _obj.result( position );
			_moveMap[i].scale(_strength);
			position += stepVar;
		}
		_curPos = 0;
	}
}
