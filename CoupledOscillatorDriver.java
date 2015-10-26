   import javax.swing.JFrame;
   public class CoupledOscillatorDriver
   {
      public static void main(String[] args)
      { 
         JFrame frame = new JFrame("Linear Project");
         frame.setSize(600,400);    //makes the mouse location correct
         frame.setLocation(0, 0);
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         CoupledOscillatorGraphics p = new CoupledOscillatorGraphics();
         frame.setContentPane(p);
         p.requestFocus();
         frame.setVisible(true);  
      }
   }