// import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import java.util.ArrayList;
public class CoupledOscillatorGraphics extends JPanel
{
   // public static final int MAX = 100; 
   public static final int NUM = 5;
   public static final int ITERATIONS = 3500;
   static int prev = 0;
   static ArrayList<Pixel> pixels;
   private BufferedImage myImage;
   private Graphics myBuffer;
   private Timer timer;
   private int count = 0;
   Oscillator[] array;
   // public static final double INCREMENT = 1;
   public CoupledOscillatorGraphics()
   {
      myImage = new BufferedImage(600, 400, BufferedImage.TYPE_INT_RGB);
      myBuffer = myImage.getGraphics();

      array = new Oscillator[NUM];
      pixels = new ArrayList<Pixel>();

      for(int i = 0; i < NUM; i++){
         array[i] = new Oscillator();
         array[i].setValue(i*20);
      }
      array[4].setValue(25);

      pt(array);

      timer = new Timer(3, new Listener());
      timer.start();
      setFocusable(true);
   }

   public static void pt(Oscillator[] arr)
   {
      for(Oscillator o: arr)
      {
         System.out.println(o);
      }
      System.out.println();
   }   

   public void paintComponent(Graphics g)
   {
      g.drawImage(myImage, 0, 0, getWidth(), getHeight(), null);
   }
   private class Listener implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
         if(count == 3)
         {
            try
            {
               Thread.sleep(3000);
            }
            catch(InterruptedException a)
            { 

            }
         }
         count++;
         if(count == ITERATIONS)
         {
            pt(array);
            try
            {
               Thread.sleep(4000);
               System.exit(0);
            }
            catch(InterruptedException a)
            { 

            }
         }
         
         
         myBuffer.setColor(Color.BLACK);
         myBuffer.fillRect(0,0,600,400);

         myBuffer.setColor(Color.RED);
         double[] extra = new double[NUM];
         for(int i = 0; i < NUM; i++)
         {
            array[i].increase();
            extra[i] = array[i].extraPer(NUM);
         }
      //leakage....
         for(int i = 0; i < NUM; i++)
         {
            for(int j = 0; j < NUM; j++)
            {
               if(i == j) continue;
               array[j].increase(extra[i]);
            }
         }
         for(int i = 0; i < NUM; i++)
         {
            extra[i] = array[i].extraPer(NUM);
         }
         for(int i = 0; i < NUM; i++)
         {
            for(int j = 0; j < NUM; j++)
            {
               if(i == j) continue;
               array[j].increase(extra[i]);
            }
         }
         // pt(array);
         for(int i = 0; i < NUM; i++)
         {
            myBuffer.fillRect(100*i,0,100,(int)array[i].getValue()*2);
         }

         if(count*500/ITERATIONS != prev)
         {
            double std = stdDev(array);
            prev = count*500/ITERATIONS;
         // System.out.println(std+"");
            pixels.add(new Pixel(prev,(int)(400-std*4)));
            // System.out.println(pixels.get(pixels.size()-1));
         }

         for(int i = 0; i < pixels.size()-1; i++)
         {
            myBuffer.drawLine(pixels.get(i).getX(), pixels.get(i).getY(), pixels.get(i+1).getX(), pixels.get(i+1).getY());
         }

         repaint();

      }
      public double stdDev(Oscillator[] osc)
      {
         double sum = 0; 
         for(int i = 0; i < NUM; i++)
         {
            sum += osc[i].getValue();
         }
         double mean = sum / NUM;

         sum = 0;
         for(int i = 0; i < NUM; i++)
         {
            sum += (osc[i].getValue() - mean) * (osc[i].getValue() - mean);
         }
         mean = sum / NUM;
         return Math.sqrt(mean);
      }
      
   }
}
class Oscillator
{
   private double value;
   private int max = 100;
   private double increment = 1;

   public Oscillator()
   {
      value = (int)(Math.random()*max);
   }
   public Oscillator(int val)
   {
      value = val;
   }
   public void setValue(double val)
   {
      value = val;
   }
   public double getValue()
   {
      return value;
   }
   public void increase()
   {
      double val = 1-(Math.pow(value, 0.1)-.1)/Math.pow(max, 0.1);
      // double val = 3*(1-Math.exp())
      value += increment*val;
   }
   public void increase(double d)
   {
      value += d;
   }
   private boolean overfull()
   {
      return value >= max;
   }
   public double extraPer(int num)
   {
      if(overfull()){
         // double tot = value % max;
         value = 0;
         // double totPer = tot/num;
         // return totPer;
         return 0.3*max/(num-1);
      }
      else{
         return 0;
      }
   }
   public String toString(){
      return "" + value;
   }
}
class Pixel
{
   private int x;
   private int y;

   public Pixel(int x1, int y1)
   {
      x = x1;
      y = y1;

   }
   public int getX() {
      return x;
   }
   public void setX(int x) {
      this.x = x;
   }
   public int getY() {
      return y;
   }
   public void setY(int y) {
      this.y = y;
   }
   public String toString()
   {
      return x + ", " + y;
   }
}