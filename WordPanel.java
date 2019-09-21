import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.CountDownLatch;

import javax.swing.JButton;
import javax.swing.JPanel;

public class WordPanel extends JPanel implements Runnable {
	public static volatile boolean done;
	private WordRecord[] words;
	private int noWords;
	private int maxY;
   private Thread animator;
   
	@Override
	public void paintComponent(Graphics g) {
       int width = getWidth();
       int height = getHeight();
       //System.out.println("Width: " + width + " Height: " + height);
       g.clearRect(0,0,width,height);
       g.setColor(Color.red);
       g.fillRect(0,maxY-10,width,height);
   
       g.setColor(Color.black);
       g.setFont(new Font("Helvetica", Font.PLAIN, 26));
       //draw the words
       //animation must be added
       if (!done) {
         for (int i=0;i<noWords;i++){	    	
       	   g.drawString(words[i].getWord(),words[i].getX(),words[i].getY());	
       	   //g.drawString(words[i].getWord(),words[i].getX(),words[i].getY());
         }
       }  
	}
		
	WordPanel(WordRecord[] w, int maxY) {
      this.words= new WordRecord[w.length];  
      for (int i = 0; i < w.length; i++) {
         this.words[i] = new WordRecord(w[i]);
      }
          
		noWords = w.length;
		done=false;
		this.maxY=maxY;		
	}
	
   public void reset() {for (WordRecord w : words) {w.resetWord();} WordApp.score.resetScore();} // Reset game from begginning}
   
   public void start() {
      done = false;
      reset();
      if (animator == null) {
         animator = new Thread(this);
         animator.start();
      } 
   }
   
   public void stop() {
      done = true;
      if (animator != null) {
         animator.stop();
         animator = null;
      }
      repaint();
   }
   
	public void run() {
	   // add in code to animate this
      while (true) { 
        // Repainting the screen 
        // calls the paint function
        update();        
        repaint();
        
        try {
          // creating a pause of 1 second 
          // so that the movement is recognizable 
          Thread.sleep(1000); 
        } 
        catch (InterruptedException ie) { 
          System.out.println(ie); 
        } 
      }  
	}
   
   private void update() {
      for (WordRecord word: words) {
         if (word.getY() <= (maxY-15)) {
            if (!word.matchWord(WordApp.textFromUser)){
               word.drop(word.getSpeed()/100);
            }
            else {
               WordApp.score.caughtWord(WordApp.textFromUser.length());
               WordApp.caught.setText("Caught: " + WordApp.score.getCaught() + "    ");
               WordApp.scr.setText("Score:" + WordApp.score.getScore()+ "    ");
            }
         }
         else {
            word.resetWord();
            WordApp.score.missedWord();
            WordApp.missed.setText("Missed:" + WordApp.score.getMissed() + "    ");
         }
      }
      
      if (WordApp.score.getTotal() == WordDictionary.size) {System.out.println("Game Over!"); 
         System.out.println("Caught: " + WordApp.score.getCaught());
	      System.out.println("Missed:" + WordApp.score.getMissed());
	      System.out.println("Score:" + WordApp.score.getScore());
         System.exit(0);
      }
   }
}

