package spiro;

import java.awt.Color;
import java.util.ArrayList;

class SpiroObject //Spiro object
    {
        int radius_R, radius_r, oVal;
        ArrayList<Color> spiro_Colours = new ArrayList<Color>();
        ArrayList<Integer> x_Vals = new ArrayList<Integer>();
        ArrayList<Integer> y_Vals = new ArrayList<Integer>();

        SpiroObject(int R, int r, int o, Color[] colours) //Takes constructor for radius of large circle, small circle, and small circle offset
        {
            radius_R = R;
            radius_r = r;
            oVal = o;
            
            for(int i = 0; i < colours.length; ++i)
            {
            	spiro_Colours.add(colours[i]);
            }
            
            spiro_Colours.add(colours[0]);

            calculatePositions(); //Auto call the calculate positions method
        }

        private void calculatePositions() 
        {
        	 float t = 0; //Say some number is 0
             
             while(true) //Until break occurs
             {
                     int x_Temp = (radius_R + radius_r) * (int)Math.cos((double)t) - (radius_r + oVal) * (int)Math.cos((double)(((radius_R + radius_r)/radius_r) * t)); //Get the x coordinate for this value of t
                     int y_Temp = (radius_R + radius_r) * (int)Math.sin((double)t) - (radius_r + oVal) * (int)Math.sin((double)(((radius_R + radius_r)/radius_r) * t)); //Same for y

                     if(t > (2 * Math.PI)) //If the two values equal the original value, we can stop
                     {
                             break; //Break out of the loop
                     }
                    
                     x_Vals.add(x_Temp); //Add the x and y coordinates to the arrays
                     y_Vals.add(y_Temp);
                    
                     t += (2 * Math.PI) / 50000; //Increment t by 2pi over some arbitrarily large number, the larger the number the more accurate the circle will be drawn
             }
        }
    }