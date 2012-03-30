package movement.animations;


public class LinearMovement extends MovementObject {	
	
	public LinearMovement( int InAxis){
		super();
		this._axis 	= InAxis;
		double x	= 0;
		double y	= 0;
		double z	= 0;
		double rot	= 0;
		switch(this._axis){
			case X_AXIS:
				x= 1;
				break;
			case Y_AXIS:
				y= 1;
				break;
			case Z_AXIS:
				z= 1;
				break;
			case ROT_AXIS:
				rot= 1;
				break;
			}
		this._currentV	= new DroneVector(x,y,z,rot);
	}

	@Override
	public DroneVector result(double position) {
	
		// TODO Auto-generated method stub
		return new DroneVector(_currentV);
	}

}
