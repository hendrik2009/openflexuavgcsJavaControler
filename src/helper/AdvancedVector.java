package helper;

import processing.core.PVector;

public class AdvancedVector extends PVector {

		public AdvancedVector(float x, float y, float z){
			super(x,y,z);
			
		}
		public AdvancedVector(AdvancedVector v){
			super(v.x,v.y,v.z);
		}
		
		/**
		 * Transforms the vector by applying a 3x3 rotation matrix
		 * 
		 * @param rotMatrix	3x3Matrix
		 */
		public void matrixTransform(double[][] rotMatrix){
			float outX = 0;
			float outY = 0;
			float outZ = 0;
			
			outX = (float) ( x*rotMatrix[0][0] + y* rotMatrix[0][1] + z*rotMatrix[0][2] );
			outY = (float) ( x*rotMatrix[1][0] + y* rotMatrix[1][1] + z*rotMatrix[1][2] );
			outZ = (float) ( x*rotMatrix[2][0] + y* rotMatrix[2][1] + z*rotMatrix[2][2] );
			
			x = outX;
			y = outY;
			z = outZ;
		}
		
		
		/**
		 * Rotates the vector by given normalized Quarternion
		 * Q - Order: (w,x,y,z)
		 * 
		 * @param quarternion
		 */
		public void quarternionRotation(float[] q){
			
			double[][] rotMatrix ={{1,0,0},{0,1,0},{0,0,1}};
			
			
			float 	 xx      = q[1] * q[1];
			float    xy      = q[1] * q[2];
			float    xz      = q[1] * q[3];
			float    xw      = q[1] * q[0];

			float    yy      = q[2] * q[2];
			float    yz      = q[2] * q[3];
			float    yw      = q[2] * q[0];

			float    zz      = q[3] * q[3];
			float    zw      = q[3] * q[0];

			rotMatrix[0][0]  = 1 - 2 * ( yy + zz );
			rotMatrix[0][1]  =     2 * ( xy - zw );
			rotMatrix[0][2]  =     2 * ( xz + yw );

			rotMatrix[1][0]  =     2 * ( xy + zw );
			rotMatrix[1][1]  = 1 - 2 * ( xx + zz );
			rotMatrix[1][2]  =     2 * ( yz - xw );

			rotMatrix[2][0]  =     2 * ( xz - yw );
			rotMatrix[2][1]  =     2 * ( yz + xw );
			rotMatrix[2][2]  = 1 - 2 * ( xx + yy );

			this.matrixTransform(rotMatrix);
		}
		
		/**
		 * Returns a rotation Matrix with the rotation from vector direction to direction of inVector.
		 * 
		 * @param v2
		 * @return rotation Matrix
		 */
		double[][] angleTo(AdvancedVector v2) {
			// turn vectors into unit vectors
			AdvancedVector n1 = this.norm();
			AdvancedVector n2 = v2.norm(); 	
			
			AdvancedVector vs = new AdvancedVector(n1);
			
			vs = (AdvancedVector) vs.cross(n2); // axis multiplied by sin	
			
			AdvancedVector v = new AdvancedVector(vs);
			v = v.norm(); // axis of rotation
		   
			float ca = PVector.dot(n1, n2) ; // cos angle	
			AdvancedVector vt = new AdvancedVector(v);	
			
			vt.scale((1.0f - ca));	
			
			double[][] rotM = {{1,0,0},{0,1,0},{0,0,1}};
			
			rotM[0][0] = vt.x * v.x + ca;
			rotM[1][1] = vt.y * v.y + ca;
			rotM[2][2] = vt.z * v.z + ca;	
			
			vt.x *= v.y;
			vt.z *= v.x;
			vt.y *= v.z;
			
			rotM[0][1] = vt.x - vs.z;
			rotM[0][2] = vt.z + vs.y;
			rotM[1][0] = vt.x + vs.z;
			rotM[1][2] = vt.y - vs.x;
			rotM[2][0] = vt.z - vs.y;
			rotM[2][1] = vt.y + vs.x;
			return rotM;
		}
		/**
		 * Normalizes Vector to length 1
		 * 
		 * @return normalized Vector
		 */
		public AdvancedVector norm(){
			
			float mag = this.mag();
			float Xout = this.x/mag;
			float Yout = this.y/mag;
			float Zout = this.z/mag;
			return new AdvancedVector(Xout,Yout,Zout);
		}
		
		/**
		 * Scales Vector by float
		 * 
		 * @param factor
		 */
		public void scale(float factor){
			if(factor<0){
				factor *= -1;
			};
			x *= factor;
			y *= factor;
			z *= factor;
		}
		
		/**
		 * 
		 */
		public void print(){
			System.out.print("X:");
			System.out.print(this.x);
			System.out.print(" Y:");
			System.out.print(this.y);
			System.out.print(" Z:");
			System.out.println(z);
		}
}
