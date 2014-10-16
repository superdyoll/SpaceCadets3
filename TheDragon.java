/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spiro;

import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;
 
public class TheDragon extends JFrame
{
        Graphics buffer; //Used for double buffering
        Image offScreen; //Image that buffer will draw
        private int paintIterator = 0; //Keep track of the number of pixels you have drawn
        private ArrayList<spiroObject> allSpiros = new ArrayList<spiroObject>(); //Array to contain all spiros in case we draw multiple objects
       
        public void init() //First method called
        {
                offScreen = createImage(400, 400); //Some arbitrary screen size TODO: change this to screen dimensions
                buffer = offScreen.getGraphics(); //Assign the buffer to get the graphics from off screen
        }
 
        public void paint(Graphics g)
        {
                buffer.clearRect(0, 0, 400, 400); //Clear screen
                buffer.setColor(Color.orange); //Set colour of pixels to orange TODO: Make the colour change
               
                for(int i = 0; i < allSpiros.size(); ++i) //For all the spiros
                {
                        if(allSpiros.get(i).x_Vals.size() <= paintIterator) //If the paint iterator hasn't exceeded the number of pixels the shape actually contains
                        {
                                for(int j = 0; j < paintIterator; ++j) //For all positions up to the paint iterator
                                {
                                        int x_Pos = allSpiros.get(i).x_Vals.get(j); //Get the x and y values of the current pixel coordinate
                                        int y_Pos = allSpiros.get(i).y_Vals.get(j);
                                       
                                        buffer.drawLine(x_Pos, y_Pos, x_Pos, y_Pos); //Draw the pixel
                                }
                        }
                }
               
                ++paintIterator; //Increase the paint iterator
               
                g.drawImage(offScreen, 0, 0, null); //Draw the image
        }
       
        class spiroObject //Spiro object
        {
                int radius_R, radius_r, oVal;
                ArrayList<Integer> x_Vals = new ArrayList<Integer>();
                ArrayList<Integer> y_Vals = new ArrayList<Integer>();
               
                spiroObject(int R, int r, int o) //Takes constructor for radius of large circle, small circle, and small circle offset
                {
                        radius_R = R;
                        radius_r = r;
                        oVal = o;
                        calculatePositions(); //Auto call the calculate positions method
                }
               
                private void calculatePositions()
                {
                        int t = 0; //Say some number is 0
                       
                        while(true) //Until break occurs
                        {
                                int x_Temp = (radius_R + radius_r) * (int)Math.cos((double)t) - radius_r * (int)Math.cos((double)(((radius_R + radius_r)/radius_r) * t)); //Get the x coordinate for this value of t
                                int y_Temp = (radius_R + radius_r) * (int)Math.sin((double)t) - radius_r * (int)Math.sin((double)(((radius_R + radius_r)/radius_r) * t)); //Same for y
 
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