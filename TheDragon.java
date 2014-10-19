import java.awt.*;
import java.awt.image.BufferedImage;

import javax.swing.*;

import java.util.ArrayList;

public class TheDragon extends JInternalFrame {

    Graphics buffer; //Used for double buffering
    BufferedImage offScreen; //Image that buffer will draw
    private int paintIterator = 100, frame_Width, frame_Height; //Keep track of the number of pixels you have drawn
    private float last_Time = 0f, delta_Time = 0f, pixels_Per_Sec = 500;
    private SpiroObject current_Spiro; //Array to contain all spiros in case we draw multiple objects

    public TheDragon(int width, int height) //First method called
    {
    	frame_Width = width;
    	frame_Height = height;
        offScreen = (BufferedImage) createImage(frame_Width, frame_Height); //Some arbitrary screen size TODO: change this to screen dimensions
        buffer = offScreen.getGraphics(); //Assign the buffer to get the graphics from off screen
    }
    
    private Color getColour()
    {
    	float current_Pos = (1f / (float)current_Spiro.x_Vals.size()) * (float)paintIterator;
    	Color a = null, b = null;
    	
    	for(int i = 0; i < current_Spiro.spiro_Colours.size(); ++i)
    	{
    		if(current_Pos * (float)current_Spiro.spiro_Colours.size() <= i)
    		{    			
    			a = current_Spiro.spiro_Colours.get(i - 1);
    			b = current_Spiro.spiro_Colours.get(i);
    			float percentage_Colour_A = (1f / ((float)current_Spiro.x_Vals.size() / current_Spiro.spiro_Colours.size()) * i - 1) * (float)paintIterator;
    			float percentage_Colour_B = (1f / ((float)current_Spiro.x_Vals.size() / current_Spiro.spiro_Colours.size()) * i) * (float)paintIterator;

    			current_Pos = (1f / (current_Pos - (float)current_Spiro.x_Vals.size() / current_Spiro.spiro_Colours.size()) * i) * (float)paintIterator;
    			break;
    		}
    	}
    	
    	Color current_Hue = null;
    	
    	if(a != null && b != null)
    	{
    		float temp_R = ((((1f / 255) * a.getRed()) - ((1f / 255) * b.getRed())) * current_Pos) + ((1f / 255f) * b.getRed());
    		float temp_G = ((((1f / 255) * a.getGreen()) - ((1f / 255) * b.getGreen())) * current_Pos) + ((1f / 255f) * b.getGreen());
    		float temp_B = ((((1f / 255) * a.getBlue()) - ((1f / 255) * b.getBlue())) * current_Pos) + ((1f / 255f) * b.getBlue());
    		
    		current_Hue = new Color(temp_R, temp_G, temp_B, 1f);
    	}
    	
    	if(current_Hue == null)
    	{
    		return Color.white;
    	}
    	else
    	{
    		return current_Hue;
    	}
    }

    public BufferedImage getImage() 
    {
    	buffer.clearRect(0, 0, frame_Width, frame_Height);
    	
    	if(last_Time != 0f)
    	{
    		delta_Time = System.nanoTime() - last_Time;
    	}
    	
    	last_Time = System.nanoTime();
    	
    	int pixels_To_Draw = (int)(pixels_Per_Sec * delta_Time);
    	
    	paintIterator += pixels_To_Draw;
    	
        if (current_Spiro.x_Vals.size() <= paintIterator) //If the paint iterator hasn't exceeded the number of pixels the shape actually contains
        {
             for (int j = 0; j < paintIterator; ++j) //For all positions up to the paint iterator
             {
                    int x_Pos = current_Spiro.x_Vals.get(j); //Get the x and y values of the current pixel coordinate
                    int y_Pos = current_Spiro.y_Vals.get(j);
                    buffer.setColor(getColour());
                    buffer.drawLine(x_Pos, y_Pos, x_Pos+1, y_Pos+1); //Draw the pixel
             }
        }

        return offScreen; //Draw the image
    }

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
        	 int t = 0; //Say some number is 0
             
             while(true) //Until break occurs
             {
                     int x_Temp = (radius_R + radius_r) * (int)Math.cos((double)t) - (radius_r + oVal) * (int)Math.cos((double)(((radius_R + radius_r)/radius_r) * t)); //Get the x coordinate for this value of t
                     int y_Temp = (radius_R + radius_r) * (int)Math.sin((double)t) - (radius_r + oVal) * (int)Math.sin((double)(((radius_R + radius_r)/radius_r) * t)); //Same for y

                     if(x_Temp == x_Vals.get(0) && y_Temp == y_Vals.get(0)) //If the two values equal the original value, we can stop
                     {
                             break; //Break out of the loop
                     }
                    
                     x_Vals.add(x_Temp); //Add the x and y coordinates to the arrays
                     y_Vals.add(y_Temp);
                    
                     ++t; //Increment t by 1
             }
        }
    }
}