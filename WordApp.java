import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;

import java.util.Scanner;
import java.util.concurrent.*;
//model is separate from the view.

public class WordApp {
//shared variables
	static int noWords=4;
	static int totalWords;

   static int frameX=1000;
	static int frameY=600;
	static int yLimit=480;

	static WordDictionary dict = new WordDictionary(); //use default dictionary, to read from file eventually

	static WordRecord[] words;
	static volatile boolean done;  //must be volatile
	static Score score = new Score();

	static WordPanel w;
   
   static JFrame frame = new JFrame("WordGame");
   static JPanel graphic = new JPanel();
   static JPanel txt = new JPanel();
   static JLabel caught, missed, scr;
   static final JTextField textEntry = new JTextField("",20);
   static JPanel b = new JPanel();
   static JButton startB, endB, quitB;
   
   static String textFromUser = "";
   
	public static void setupGUI(int frameX,int frameY,int yLimit) {
		// Frame init and dimensions
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	frame.setSize(frameX, frameY);
    	
      graphic.setLayout(new BoxLayout(graphic, BoxLayout.PAGE_AXIS)); 
      graphic.setSize(frameX,frameY);
 
		w = new WordPanel(words,yLimit);
		w.setSize(frameX,yLimit+100);
	   graphic.add(w);
	     
	   txt.setLayout(new BoxLayout(txt, BoxLayout.LINE_AXIS)); 
	   caught =new JLabel("Caught: " + score.getCaught() + "    ");
	   missed =new JLabel("Missed:" + score.getMissed()+ "    ");
	   scr =new JLabel("Score:" + score.getScore()+ "    ");    
	   txt.add(caught);
	   txt.add(missed);
	   txt.add(scr);
    
	   //[snip]
	   textEntry.addActionListener(new ActionListener()
	   {
	     public void actionPerformed(ActionEvent evt) {
	         textFromUser = textEntry.getText();
	         //[snip] 
	         textEntry.setText("");
	         textEntry.requestFocus();
	     }
	   });
	   
	   txt.add(textEntry);
	   txt.setMaximumSize( txt.getPreferredSize() );
	   graphic.add(txt);
	    
      b.setLayout(new BoxLayout(b, BoxLayout.LINE_AXIS)); 
      
	   startB = new JButton("Start");;
		// add the listener to the jbutton to handle the "pressed" event
		startB.addActionListener(new ActionListener()
		{
		   public void actionPerformed(ActionEvent e)
		   {
            w.start();
            caught.setText("Caught: " + score.getCaught() + "    ");
	         missed.setText("Missed:" + score.getMissed()+ "    ");
	         scr.setText("Score:" + score.getScore()+ "    ");
            textEntry.requestFocus();  //return focus to the text entry field
		   }
		});
      
		endB = new JButton("End");	
		// add the listener to the jbutton to handle the "pressed" event
		endB.addActionListener(new ActionListener()
		{
		   public void actionPerformed(ActionEvent e)
			{
            w.stop();
			}
	   });
      
      quitB = new JButton("Quit");
		// add the listener to the jbutton to handle the "pressed" event
      quitB.addActionListener(new ActionListener() 
      {
         public void actionPerformed(ActionEvent e) {
            System.exit(0);
         }   
      });
      
      //add jbutton to jpanel
		b.add(startB);
		b.add(endB);
      b.add(quitB);
    
		graphic.add(b);
    	
      frame.setLocationRelativeTo(null);  // Center window on screen.
      frame.add(graphic); //add contents to window
      frame.setContentPane(graphic);     
      //frame.pack();  // don't do this - packs it into small space
      frame.setVisible(true);
	}

	
   public static String[] getDictFromFile(String filename) {
		String [] dictStr = null;
		try {
			Scanner dictReader = new Scanner(new FileInputStream(filename));
			int dictLength = dictReader.nextInt();
			//System.out.println("read '" + dictLength+"'");

			dictStr=new String[dictLength];
			for (int i=0;i<dictLength;i++) {
				dictStr[i]=new String(dictReader.next());
				//System.out.println(i+ " read '" + dictStr[i]+"'"); //for checking
			}
			dictReader.close();
		} catch (IOException e) {
	      System.err.println("Problem reading file " + filename + " default dictionary will be used");
	   }
		return dictStr;
   }

	public static void main(String[] args) {
    	
		//deal with command line arguments
		totalWords=Integer.parseInt(args[0]);  //total words to fall
		noWords=Integer.parseInt(args[1]); // total words falling at any point
		assert(totalWords>=noWords); // this could be done more neatly
		String[] tmpDict=getDictFromFile(args[2]); //file of words
		if (tmpDict!=null)
			dict= new WordDictionary(tmpDict);
		
		WordRecord.dict=dict; //set the class dictionary for the words.
		
		words = new WordRecord[noWords];  //shared array of current words
		
		//[snip]
      
      int x_inc=(int)frameX/noWords;
	  	//initialize shared array of current words
		for (int i=0;i<noWords;i++) {
			words[i]=new WordRecord(dict.getNewWord(),i*x_inc,yLimit);
		}

		setupGUI(frameX, frameY, yLimit);
      
    	//Start WordPanel thread - for redrawing animation
      //w.start();
	}
}
