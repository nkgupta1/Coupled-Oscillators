import java.util.*;
import java.io.*;
public class CoupledOscillator
{
   // public static final int MAX = 100; 
   public static final int NUM = 1;
   // public static final double INCREMENT = 1;
   public static void main(String[] args)
   {
      Oscillator[] array = new Oscillator[NUM];
      for(int i = 0; i < NUM; i++){
         array[i] = new Oscillator();
      }
      array[0].setValue(0);
      pt(array);
      for (int k = 0; k < 2000; k++)
      {

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
         pt(array);
      }
      pt(array);
   }

   public static void pt(Oscillator[] arr)
   {
      for(Oscillator o: arr)
      {
         System.out.println(o);
      }
      // System.out.println();
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
      double val = 1-(Math.pow(value, 0.25)-.1)/Math.pow(max, 0.25);
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
