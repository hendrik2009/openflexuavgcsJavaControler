package movement.animations;

public class DroneVector {
	
	public double Vx   = 0;
	public double Vy   = 0;
	public double Vz   = 0;
	public double Vrot = 0;
	
	
	public DroneVector(){
		
	}
	public DroneVector(double x, double y, double z, double rot){
		Vx 		= x;
		Vy 		= y;
		Vz 		= z;
		Vrot 	= rot;
	}
	public DroneVector(DroneVector v){
		Vx 		= v.Vx;
		Vy 		= v.Vy;
		Vz 		= v.Vz;
		Vrot 	= v.Vrot;
	}
	public void add(DroneVector v){
		Vx	= Vx + v.Vx;
		Vy	= Vy + v.Vy;
		Vz 	= Vz + v.Vz;
		Vrot = Vrot + v.Vrot;
	}
	public void scale(float factor){
		Vx = Vx*factor;
		Vy = Vy*factor;
		Vz = Vz*factor;
		Vrot = Vrot*factor;
	}
}
