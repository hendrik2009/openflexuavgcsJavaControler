import jogamp.graph.font.typecast.TypecastGlyph.Advance;
import helper.AdvancedVector;
import helper.MatrixGenerator;
import processing.core.PApplet;


public class TesterApplet extends PApplet {

	AdvancedVector v1 = new AdvancedVector(200,0,0);
	AdvancedVector v2 = new AdvancedVector(100,50,0);
	AdvancedVector v3 = new AdvancedVector(100,50,0);
	
	
	public void setup(){
		this.frameRate(30);
		 
		this.size(800,600,P3D);

		this.stroke(255,255,255);
		this.smooth();
		
		
		
		double[][] zMat = MatrixGenerator.getZRotation(-90);
		
		MatrixGenerator.dumpMat(zMat);
		
		v2.scale(4);
		v2.print();
		v2.matrixTransform(zMat);
		v2.print();
		
		
		double[][] yMat = MatrixGenerator.getYRotation(90);
		
		MatrixGenerator.dumpMat(yMat);
	
		v2.print();
		v2.matrixTransform(yMat);
		v2.print();
		
	}
	
	public void draw(){
		background(0,0,0);
		
		// centers origin on screen and gives 1000 distance to observer
		translate(width/2,height/2,-1000);
		// Rotates so that x heads positive right ; y heads positive away in the screen; and z is positive upwards;
		rotateX(radians(90));
		
		
		// Working in my normal coordinates

		
		drawFrustum();
		
		// Yellow
		stroke(255,255,0);
		point(v1.x,v1.y,v1.z);
		
		// Ozean Blue
		stroke(0,255,255);
		
		
		point(v2.x,v2.y,v2.z);
		
		
		
		
	}
	
	public void drawFrustum(){
		
		//draw X-Axis
		this.stroke(255,0,0);
		this.line(0, 0, 0, 100, 0, 0);
		
		this.stroke(0,255,0);
		this.line(0, 0, 0, 0, 100, 0);
		
		this.stroke(0,0,255);
		this.line(0, 0, 0, 0, 0, 100);
	}
	
	// Key control
		public void keyPressed()
		{
		  switch(key)
		  {
		  case 'q':{
			  println("Q");
		  }
		  break;
		  }
		}
}
