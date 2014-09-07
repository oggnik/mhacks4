package matcher;
import java.util.ArrayList;
import model.*;
public class ColorPattern {
	ArrayList <Pattern> patternArray;
	int count;
	public ColorPattern(){
		patternArray = new ArrayList<Pattern>();
		/**
		 * Get pattern for each color and add patterns to patternArray
		 */
		Pattern yellow = new Pattern();
		Pattern red = new Pattern();
		red.higheralpha1 = 0.2;
		red.loweralpha1 = 0.15;
		red.higheralpha2 = 0.3;
		red.loweralpha2 = 0.25;
		red.higherbeta1 = 0.4;
		red.lowerbeta1=0.35;
		red.higherbeta2=0.5;
		red.lowerbeta2=0.45;
		red.highergamma1 = 0.55;
		red.lowergamma1 = 0.51;
		red.highergamma2 = 0.6;
		red.lowergamma2 = 0.56;
		red.higherdelta = 0.7;
		red.lowerdelta = 0.65;
		red.highertheta = 0.8;
		red.lowertheta = 0.75;
		yellow.higheralpha1 = 0.2;
		yellow.loweralpha1 = 0.15;
		yellow.higheralpha2 = 0.3;
		yellow.loweralpha2 = 0.25;
		yellow.higherbeta1 = 0.4;
		yellow.lowerbeta1=0.35;
		yellow.higherbeta2=0.5;
		yellow.lowerbeta2=0.45;
		yellow.highergamma1 = 0.55;
		yellow.lowergamma1 = 0.51;
		yellow.highergamma2 = 0.6;
		yellow.lowergamma2 = 0.56;
		yellow.higherdelta = 0.7;
		yellow.lowerdelta = 0.65;
		yellow.highertheta = 0.8;
		yellow.lowertheta = 0.75;
		Pattern blue = new Pattern();
		
		/**
		 * Code needs to be optimized 
		 */
		patternArray.add(red);
		patternArray.add(yellow);
		//patternArray.add(yellow);
		//patternArray.add(blue);
		
		count+=1;
	}
}
