package helper;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;

import processing.core.PImage;
import processing.core.PVector;


public class ColorDetector {

	private static int 	_width			=1;
	private static int 	_height;
	public static int[] _rect			= {0,0,0,0};
	
	public ColorDetector(int width, int height){
		_width 		= width;
		_height 	= height;
	}
	public int[] detectRed(int[] pixels, int[] rect){
		return ColorDetector.searchRed(pixels, rect);
	}
	public int[] detectRed(int[] pixels){
		int[] rect ={0,0,_width,_height};
		return ColorDetector.searchRed(pixels, rect);
	}
	private static int[] searchRed(int[] pixels, int[] rect){
		
		int inx1				= rect[0];
		int iny1				= rect[1];
		
		int dx					= rect[2] -rect[0];
		int dy					= rect[3] -rect[1];
		
		int[] targetCoor		= {-1,-1};

		int[] xArray	= {0,0};
		int[] yArray	= {0,0};
		boolean lastWasRed 		= false;
		
		int x;
		int y;
		int c	= 0;
		for(y=iny1;y< iny1+dy; y++){
			for (x = inx1; x < inx1+dx; x++){
				c = y*_width +x;
				
			    if ( checkPixel(pixels[c])!= 0 ){
				    if(lastWasRed){
			    		xArray[0] += x;
			    		xArray[1]++;
			    		yArray[0] += y;
			    		yArray[1]++;
			    	}
				    else{			    	
				    	pixels[c] = (int) ( (0xbb) << 24 +( (pixels[c] >> 16) & 0xff << 16 ) + ((pixels[c] >> 8) & 0xff)<< 8 + (pixels[c]) & 0xff  ) ;
				    }
			    	lastWasRed = true;
			    }
			    else{
			    	pixels[c] = (int) ( (0xbb) << 24 +( (pixels[c] >> 16) & 0xff << 16 ) + ( ( (pixels[c] >> 8) & 0xff)<< 8 ) + (pixels[c]) & 0xff  ) ;
			    	lastWasRed = false;
			    }
			}
		}	
		//System.out.println(c);
		if(xArray[1]>0){
			targetCoor[0]	= xArray[0] /xArray[1];
			targetCoor[1]	= yArray[0] / yArray[1];
		}
		return targetCoor; 
	}
	public static int checkPixel(int pixel) {
	    int alpha = (pixel >> 24) & 0xff;
	    int red = (pixel >> 16) & 0xff;
	    int green = (pixel >> 8) & 0xff;
	    int blue = (pixel) & 0xff;
	    
	    if ((red> 220 ) && (green < 160) && (blue < 120) ){ //&& (green < 120) 
	    	//System.out.print("dist:");System.out.println(ref.dist(target));
	    	return pixel;
	    }
	    else{
	    	//System.out.print("dist:");System.out.println(ref.dist(target));
	    	return 0;
	    }
	}
}
