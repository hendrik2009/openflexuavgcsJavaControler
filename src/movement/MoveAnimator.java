package movement;

import movement.animations.CyclingMovement;
import movement.animations.DroneVector;
import movement.animations.LinearMovement;
import movement.animations.MovementObject;

public class MoveAnimator {
	
	private MovementObject	_obj				= new LinearMovement(MovementObject.X_AXIS);
	private DroneAnimation _testAnim 			= new DroneAnimation(true,200,_obj,0.2f);
	
	private MovementObject	_obj2				= new CyclingMovement(MovementObject.X_AXIS);
	private DroneAnimation _testAnim2 			= new DroneAnimation(true,200,_obj2,0.2f);
	private DroneAnimation[] _animationList ={_testAnim,_testAnim2,null,null,null,null,null,null,null,null};
	
	public MoveAnimator(){
		
	}
	public DroneVector step(){
		DroneVector output = new DroneVector();
		
		for(int i=0; i<_animationList.length; i++){
			if(_animationList[i] !=null){
				output.add(_animationList[i].increment());
			}
		}	
		return output;
	}
	//to do
	public void addAnimation(DroneAnimation A){
		
	}
	public void removeAnimation(int pos){
		
	}
}