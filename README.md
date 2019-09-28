# FallingWords
Implemented a multithreaded typing game. Ensuring thread saftey and sufficient concurrency. 

## Contents
 - WordDictionary.java - Organise source data for the falling words
 - Score.java - Module to keep the score for the game
 - WordRecord.java - Module to represent a falling word and its state
 - WordPanel.java - JPanel to animate the falling words
 - UpdateThread.java - Setting up each falling word as a separate thread and move the position
 - CaughtThread.java - Setting up a separate thread for caught words
 - WordApp.java - Initialising the gui and handling click events
 - example_dict.txt - Example source data for the falling words
 - makefile - Project compilation

## Operation
This game operates as follows: <br/>
  - When the start button is pressed, a specified number of words (a command line
    parameter) start falling at the same time from the top of the panel, but fall at different
    speeds – some faster, some slower. <br/>
  - Words disappear when they reach the red zone, whereupon the Missed counter is
    incremented by one and a new word starts falling (with a different speed).
  - The user attempts to type all the words before they hit the red zone, pressing enter
    after each one. <br/>
  - If a user types a word correctly, that word disappears, then the Caught counter is
    incremented by one and the Score is incremented by the length of the word. A new
    word then starts falling (with a different speed). <br/>
  - If a user types a word incorrectly, it is ignored. <br/>
  - The game continues until the specified maximum number of words (a command-line
    parameter) is reached (whereupon a suitable message should be displayed) or the user
    presses the End button (which should simply stop the current game and clear the
    screen). The user can then play again, beginning a new game by pressing the Start
    button. <br/>
  - The user presses the Quit button to end the game <br/>
  
  ## Running the Project
  Compliation done through ```make``` command.
  - ```make``` will compile the all the project
  - ```make ARGS="arg[0] arg[1] arg[2]" run``` will both complie project and run the game <br/>
  - e.g ```make ARGS="10 2 example_dict.txt" run``` will run the game for 2 word per screen and example_dict.txt
