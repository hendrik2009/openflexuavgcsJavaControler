package helper;

public class MatrixGenerator {

	
	public static double[][] getMatrixByEulerAngles(){
		double[][]rotMat ={{1,0,0},{0,1,0},{0,0,1}};
		
		return rotMat;
	}
	public static double[][] getMatrixByQuaternion(){
		double[][]rotMat ={{1,0,0},{0,1,0},{0,0,1}};
		
		return rotMat;
	}
	public static double[][] getMatrixByAngles(){
		double[][]rotMat ={{1,0,0},{0,1,0},{0,0,1}};
		
		return rotMat;
	}
	
	public static double[][] getXRotation(float angle){
		double s = Math.sin(MatrixGenerator.radians(angle));
		double c = Math.cos(MatrixGenerator.radians(angle));
		double[][]rotMat ={{1,0,0},{0,c,s},{0,-s,c}};
		
		
		return rotMat;
	}
	public static double[][] getYRotation(float angle){
		double s = Math.sin(MatrixGenerator.radians(angle));
		double c = Math.cos(MatrixGenerator.radians(angle));
		double[][]rotMat ={{c,0,-s},{0,1,0},{s,0,c}};
		
		return rotMat;
	}
	public static double[][] getZRotation(float angle){
		double s = Math.sin(MatrixGenerator.radians(angle));
		double c = Math.cos(MatrixGenerator.radians(angle));
		double[][]rotMat ={{c,s,0},{-s,c,0},{0,0,1}};	
		return rotMat;
	}
	public static double[][] getZYXRotationMat(float XRot, float YRot, float ZRot){
		double[][]rotMat ={{1,0,0},{0,1,0},{0,0,1}};
		
		rotMat = MatrixGenerator.multMats(rotMat, MatrixGenerator.getZRotation( ZRot) );
		rotMat = MatrixGenerator.multMats(rotMat, MatrixGenerator.getYRotation( YRot) );
		rotMat = MatrixGenerator.multMats(rotMat, MatrixGenerator.getXRotation( XRot) );
		
		return rotMat;
	}
	
	public static double[][] multMats(double[][]a, double[][]b){
		double[][]rotMat ={
				{ a[0][0]*b[0][0] + a[0][1]*b[1][0] + a[0][2]*b[2][0]  ,  a[0][0]*b[0][1] + a[0][1]*b[1][1] + a[0][2]*b[2][1]  , a[0][0]*b[0][2] + a[0][1]*b[1][2] + a[0][2]*b[2][2]},
				
				{ a[1][0]*b[1][0] + a[1][1]*b[1][0] + a[1][2]*b[2][0]  ,  a[1][0]*b[0][1] + a[1][1]*b[1][1] + a[1][2]*b[2][1]  , a[1][0]*b[0][2] + a[1][1]*b[1][2] + a[1][2]*b[2][2]},
				
				{ a[2][0]*b[0][0] + a[2][1]*b[1][0] + a[2][2]*b[2][0]  ,  a[2][0]*b[0][1] + a[2][1]*b[1][1] + a[2][2]*b[2][1]  , a[2][0]*b[0][2] + a[2][1]*b[1][2] + a[2][2]*b[2][2]}
			};
		return rotMat;
	}
	
	public static double[][] addMats(double[][] a, double[][] b){
		double[][] rotMat={
				{a[0][0]+b[0][0] ,a[0][1]+b[0][1], a[0][2]+b[0][2]},
				{a[1][0]+b[1][0] ,a[1][1]+b[1][1], a[1][2]+b[1][2]},
				{a[2][0]+b[2][0] ,a[2][1]+b[2][1], a[2][2]+b[2][2]}
		};
		return rotMat;
	}
	
	public static double radians(float angle){
		return angle/180 * Math.PI;
	}
	
	public static float angle(double radians){
		return (float)( radians/Math.PI * 180 );
	}
	public static void dumpMat(double[][] rotMat){
		for(int a=0; a<3; a++){
			System.out.print("\t");
			System.out.print(rotMat[a][0]);
			System.out.print("\t");
			System.out.print("|");
			System.out.print(rotMat[a][1]);
			System.out.print("\t");		
			System.out.print("|");
			System.out.print(rotMat[a][2]);
			System.out.print("\n");
		}
	}
	
}
