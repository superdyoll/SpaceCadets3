package spiro;

import java.awt.*;
import java.awt.image.BufferedImage;

public class TheDragon
{
    private Graphics buffer; //Used for double buffering
    private BufferedImage offScreen; //Image that buffer will draw
    private int paintIterator = 100, frame_Width, frame_Height; //Keep track of the number of pixels you have drawn
    private float last_Time = 0f, delta_Time = 0f, pixels_Per_Sec = 500;
    private SpiroObject current_Spiro; //Array to contain all spiros in case we draw multiple objects

    public TheDragon(int width, int height, Graphics b, BufferedImage b_Image, SpiroObject spiro) //Pass these variables on construction and you won't have to worry about anything other than getimage()
    {
    	frame_Width = width;
    	frame_Height = height;
        offScreen = b_Image; //Some arbitrary screen size TODO: change this to screen dimensions
        buffer = b; //Assign the buffer to get the graphics from off screen
        current_Spiro = spiro;
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
                    buffer.setColor(Color.black);//getColour());
                    buffer.drawLine(x_Pos, y_Pos, x_Pos+1, y_Pos+1); //Draw the pixel
             }
        }

        return offScreen; //Draw the image
    }
}