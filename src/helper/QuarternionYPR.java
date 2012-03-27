package helper;

public class QuarternionYPR {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.print("Prog started!");
		System.out.println("");

		QuarternionYPR calc	= new QuarternionYPR();
		
		// Qresult is the resulting rotation after each step
		double[] Qresult = {1,0,0,0};
		int runner = 0;

		

		// creating first rotation
		double[] Qzero =  calc.YPRtoQuaternion(179,0,0);
		
		Qresult = calc.QuaternionMult(Qresult, Qzero);
		double[] YPR = calc.getYPRbyQ(Qresult);
		double dumm = YPR[0]+YPR[1]+YPR[2];
		
		// creating second rotation
		double[] Qone = calc.YPRtoQuaternion(0, 0, 90);
		Qresult = calc.QuaternionMult( Qresult, Qone);		
		YPR = calc.getYPRbyQ(Qresult);
		dumm = YPR[0]+YPR[1]+YPR[2];

		// creating third rotation
		double[] Qtwo = calc.YPRtoQuaternion(180,0,0);
		Qresult = calc.QuaternionMult( Qresult, Qtwo);
		YPR = calc.getYPRbyQ(Qresult);
		dumm = YPR[0]+YPR[1]+YPR[2];

		double dummer =dumm +1;
		
	//Servo LOOP
		// get YPR0 by given Q from DakaX
			//->old fashion dirty way 
		// create new Q1 byYPR(0 ,YRP0[1],YPR0[2]);
		// create new Q2 byYPR(offsetYaw,0,0);
		
		// set servoYaw(offsetYAW);
		
		//create Q3 by mult(Q1,Q2);
		//create YPR1(Q3);
		
		// set servoPitch(YPR1[1]+OffsetPitch );
		
		//create Q4byYPR(0,0ffsetPitch,0); 
		//create Q5 by mult (Q3,Q4);
		
		//create YPR2(Q5);
		
		//set servoRoll(YPR2[2] + offsetRoll);
		
		
		
	}

	public QuarternionYPR (){

	}

	/**
	 * Returns current Euler -  Angles
	 * 
	 * @param Q - Rotation Quarternion
	 * @return double[Yaw, Pitch, Roll]
	 */
	double[] getYPRbyQ(double Q[]){
		double[] YPR	= {0,0,0};
		//YAW
		YPR[0]	= (int)angle( Math.asin(-2*(Q[1]*Q[3] - Q[0]*Q[2])) );
		//PITCH
		YPR[1] 	= (int)angle( Math.atan2(2*(Q[2]*Q[3] + Q[0]*Q[1]), Q[0]*Q[0] - Q[1]*Q[1] - Q[2]*Q[2] + Q[3]*Q[3]) );
		//ROLL
		YPR[2]	= (int)angle(Math.atan2(2*(Q[1]*Q[2] + Q[0]*Q[3]), Q[0]*Q[0] + Q[1]*Q[1] - Q[2]*Q[2] - Q[3]*Q[3]));
		return YPR;
	}


	double[] YPRtoQuaternion( double yaw , double pitch , double roll){

		double[] Q = {0,0,0,0};
		double sR  = Math.sin(radians(roll/2));
		double sP  = Math.sin(radians(pitch/2));
		double sY  = Math.sin(radians(yaw/2));
		double cR  = Math.cos(radians(roll/2));
		double cP  = Math.cos(radians(pitch/2));
		double cY  = Math.cos(radians(yaw/2));

		double cRcY=cR*cY;
		double sRsY=sR*sY;

		double w = (double) (cRcY*cP + sRsY*sP);
		double x = (double) (cRcY*sP - sRsY*cP);
		double y = (double) (cR*sY*cP + sR*cY*sP);
		double z = (double) (sR*cY*cP - cR*sY*sP);

		Q[0]   = w ;
		Q[1]   = x ;
		Q[2]   = y ;
		Q[3]   = z ;


		//Q = ( ( Q[0]*Q[0] + Q[1]*Q[1] + Q[2]*Q[2] + Q[3]*Q[3]  ) > 1 )? normalize(Q) : Q;

		return(Q);
	} 


	/**
	 * Quaternion multiplycation</br>
	 * This performce a rotaion Q2 to an given rotation Q1.
	 * 
	 * @param Q1
	 * @param Q2
	 * @return
	 */
	double[] QuaternionMult(double Q1[], double Q2[]){
		double result[] ={0,0,0,0}; 
		result[0]  =  Q1[0]*Q2[0] - Q1[1]*Q2[1] - Q1[2]*Q2[2] - Q1[3]*Q2[3]; 
		result[1]  =  Q1[0]*Q2[1] + Q1[1]*Q2[0] + Q1[2]*Q2[3] - Q1[3]*Q2[2];
		result[2]  =  Q1[0]*Q2[2] - Q1[1]*Q2[3] + Q1[2]*Q2[0] + Q1[3]*Q2[1];
		result[3]  =  Q1[0]*Q2[3] + Q1[1]*Q2[2] - Q1[2]*Q2[1] + Q1[3]*Q2[0];
		return(result);
	}

	/**
	 * Returns normal 
	 * @param Qin
	 * @return
	 */
	double[] normalize(double[] Qin){
		double[] Qout={0,0,0,0};
		double mag =Math.sqrt( Qin[0]*Qin[0] + Qin[1]*Qin[1] + Qin[2]*Qin[2] + Qin[3]*Qin[3]  );
		Qout[0] 	= Qin[0]/ mag;
		Qout[1] 	= Qin[1]/ mag;
		Qout[2] 	= Qin[2]/ mag;
		Qout[3] 	= Qin[3]/ mag;
		return(Qout);
	}
	/**
	 * Angle to radians transform.
	 * 
	 * @param angle 0-360
	 * @return radians 0-2*pi
	 */
	double radians(double angle){
		return   angle* Math.PI/180;
	}

	/**
	 * Radians to Angle transform
	 * 
	 * @param rad	0- 2*Pi
	 * @return angle 0-360;
	 */

	double angle(double rad){
		return (double)( rad / Math.PI * 180);
	}
}
