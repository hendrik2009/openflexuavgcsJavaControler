package movement.animations;

public class CyclingMovement extends MovementObject{

	
	public CyclingMovement(int axis){
		super();
		this._axis = axis;
	}
	
	@Override
	public DroneVector result(double position){
		double x	= 0;
		double y	= 0;
		double z	= 0;
		double rot	= 0;
		switch(this._axis){
			case X_AXIS:
				x= Math.sin(position);
				break;
			case Y_AXIS:
				y= Math.sin(position);
				break;
			case Z_AXIS:
				z= Math.sin(position);
				break;
			case ROT_AXIS:
				rot= Math.sin(position);
				break;
			}
		return new DroneVector(x,y,z,rot);
	}
	
}
