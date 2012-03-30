package movement.animations;


public interface MovementAlgorithm {
	
	final static int  X_AXIS = 0;
	final static int  Y_AXIS = 1;
	final static int  Z_AXIS = 2;
	final static int  ROT_AXIS = 3;
	
	public DroneVector result(double position);
	
}
