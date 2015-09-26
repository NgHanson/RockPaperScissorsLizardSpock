package players;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import lizardSpock.SLArena;
//import spockLizard.SLArena;

import java.util.ArrayList;

//dbinbox.com/jord81
//accesscode:cohen

/**
 * Hanson's player uses an algorithm to generate a move of Rock Paper Scissors Spock or Lizard.
 * Return's ROCK the first round, copy cats for the next 99 rounds then throws moves based on
 * collected data
 *  
 * @author Hanson Ng
 */
public class HansonN extends SLPlayer implements Runnable{
	
	private int animationParam = 0; //determines which word to display next
	private int drawX = 20, drawY = 20; //hold randomly determined location for word
	
	private int roundNumber = 1; // current round number

	private int myMove; // my move 
	private int myPrevMove; //my previous move
	private int prevOppMove; // most recent opponent move 
	
	//variables for the wins 
	private int numWinRockThenOppRock = 0; 
	private int numWinRockThenOppPaper = 0;
	private int numWinRockThenOppScissors = 0;
	private int numWinRockThenOppSpock = 0;
	private int numWinRockThenOppLizard = 0;
	
	private int numWinPaperThenOppRock = 0; 
	private int numWinPaperThenOppPaper = 0;
	private int numWinPaperThenOppScissors = 0;
	private int numWinPaperThenOppSpock = 0;
	private int numWinPaperThenOppLizard = 0;
	
	private int numWinScissorsThenOppRock = 0; 
	private int numWinScissorsThenOppPaper = 0;
	private int numWinScissorsThenOppScissors = 0;
	private int numWinScissorsThenOppSpock = 0;
	private int numWinScissorsThenOppLizard = 0;
	
	private int numWinSpockThenOppRock = 0; 
	private int numWinSpockThenOppPaper = 0;
	private int numWinSpockThenOppScissors = 0;
	private int numWinSpockThenOppSpock = 0;
	private int numWinSpockThenOppLizard = 0;
	
	private int numWinLizardThenOppRock = 0; 
	private int numWinLizardThenOppPaper = 0;
	private int numWinLizardThenOppScissors = 0;
	private int numWinLizardThenOppSpock = 0;
	private int numWinLizardThenOppLizard = 0;
	
	//variables for when I lose
	private int numLoseRockThenOppRock = 0; 
	private int numLoseRockThenOppPaper = 0;
	private int numLoseRockThenOppScissors = 0;
	private int numLoseRockThenOppSpock = 0;
	private int numLoseRockThenOppLizard = 0;
	
	private int numLosePaperThenOppRock = 0; 
	private int numLosePaperThenOppPaper = 0;
	private int numLosePaperThenOppScissors = 0;
	private int numLosePaperThenOppSpock = 0;
	private int numLosePaperThenOppLizard = 0;
	
	private int numLoseScissorsThenOppRock = 0; 
	private int numLoseScissorsThenOppPaper = 0;
	private int numLoseScissorsThenOppScissors = 0;
	private int numLoseScissorsThenOppSpock = 0;
	private int numLoseScissorsThenOppLizard = 0;
	
	private int numLoseSpockThenOppRock = 0; 
	private int numLoseSpockThenOppPaper = 0;
	private int numLoseSpockThenOppScissors = 0;
	private int numLoseSpockThenOppSpock = 0;
	private int numLoseSpockThenOppLizard = 0;
	
	private int numLoseLizardThenOppRock = 0; 
	private int numLoseLizardThenOppPaper = 0;
	private int numLoseLizardThenOppScissors = 0;
	private int numLoseLizardThenOppSpock = 0;
	private int numLoseLizardThenOppLizard = 0;
	
	// the number of times my bot throws that move
	private int numRocks; 
	private int numPaper;
	private int numScissors;
	private int numSpock;
	private int numLizard;
	
	private ArrayList<Integer> arrayMyMoves = new ArrayList<Integer>(); // Array list for my moves
	private ArrayList<Integer> arrayOppMoves = new ArrayList<Integer>(); // Array list for opp moves
	
	/**
	 * Constructor loads sound and starts a thread for animation.
	 * 
	 * @param arena The arena applet
	 */
	public HansonN(SLArena arena) {
		super(arena);
		Thread t = new Thread(this);
		t.start();
	}
	
	/**
	 * Returns the next move.
	 * 
	 * @return The move (ROCK, SCISSORS, PAPER, SPOCK, or LIZARD)
	 */
	public int move() {
		
		if (roundNumber == 1){ //for the very first round
			myMove = SLPlayer.ROCK;
		} else if (roundNumber > 1 && roundNumber < 51) { // rounds 2 - 50  
			myMove = prevOppMove; //copycat
			
			if (roundNumber > 2){ //begin collecting data after 2 rounds because need 2+ rounds
			 dataCollectForPrevRounds();
			}
			
		} else { //after round 50
			
			 dataCollectForPrevRounds(); //continue data collecting
		
			 //every 100 rounds the data resets/modifies to avoid a stacking on one variable
			 //excludes the first time (ROUND 100) after copy cat
			 if (roundNumber > 100){
				 if(roundNumber%100 == 0){
					 collectedDataReset();
				 }
			 }
		
			 //play move according to data collected and if i won or lost the prev round
			 if ( checkIfRoundWin(myPrevMove, prevOppMove) ){
				 playAccordingToDataWin();
			 }else{
				 playAccordingToDataLose();
			 }
			 
		}
		//keep track of what move I played
		increaseNumOfEachMove(myMove);	
		//System.out.println ("Rocks" + numRocks);
		//System.out.println ("Paper" +  numPaper);
		//System.out.println ("Scissors" + numScissors);
		//System.out.println ("Spock" + numSpock);
		//System.out.println ("Lizard" + numLizard);
		
		arrayMyMoves.add(myMove); //stores my move in an array of my moves
		myPrevMove = myMove; //myMove is my previous move for  the next round
		roundNumber++; //increases the round number
		return myMove; //returns the move I will throw
	}

	/**
	 * Increases the corresponding variable for my move chosen. 
	 * 
	 * @param move ROCK, SCISSORS, PAPER, SPOCK, or LIZARD
	 */
	private void increaseNumOfEachMove(int move) {
		if (move == SLPlayer.ROCK) {
			numRocks++;
		} else if (move == SLPlayer.PAPER) {
			numPaper++;
		} else if (move == SLPlayer.SCISSORS) {
			numScissors++;
		} else if (move == SLPlayer.SPOCK) {
			numSpock++;
		} else if (move == SLPlayer.LIZARD) {
			numLizard++;
		}
	}
	
	/**
	 * Checks who won 2 rounds ago, finds out what happens the round after and records it
	 */
	private void dataCollectForPrevRounds(){
		
		int myMoveTwoRoundAgo = arrayMyMoves.get(roundNumber-1 - 2); //my move two rounds ago
		int oppMoveTwoRoundAgo = arrayOppMoves.get(roundNumber-1 - 2 ); //opp move two rounds ago
		
		int oppMoveLastRound = prevOppMove; //opp move last round
				
		// check if won or lost 2 rounds ago 
		boolean prevPrevWin = checkIfRoundWin(myMoveTwoRoundAgo, oppMoveTwoRoundAgo);
		
		//-------------after I win 2 rounds ago what happens the round after----------\\
		if (prevPrevWin){ 

			if (myMoveTwoRoundAgo == SLPlayer.ROCK){ //win with rock 
				//keeps track of what move is played after 
				if (oppMoveLastRound == SLPlayer.ROCK){
					numWinRockThenOppRock++;
				}else if (oppMoveLastRound == SLPlayer.PAPER){
					numWinRockThenOppPaper++;
				}else if (oppMoveLastRound == SLPlayer.SCISSORS){
					numWinRockThenOppScissors++;
				}else if (oppMoveLastRound == SLPlayer.SPOCK){
					numWinRockThenOppSpock++;
				}else if (oppMoveLastRound == SLPlayer.LIZARD){
					numWinRockThenOppLizard++;
				}
				
			}else if (myMoveTwoRoundAgo == SLPlayer.PAPER){ //win with paper
				//keeps track of what move is played after
				if (oppMoveLastRound == SLPlayer.ROCK){
					numWinPaperThenOppRock++;
				}else if (oppMoveLastRound == SLPlayer.PAPER){
					numWinPaperThenOppPaper++;
				}else if (oppMoveLastRound == SLPlayer.SCISSORS){
					numWinPaperThenOppScissors++;
				}else if (oppMoveLastRound == SLPlayer.SPOCK){
					numWinPaperThenOppSpock++;
				}else if (oppMoveLastRound == SLPlayer.LIZARD){
					numWinPaperThenOppLizard++;
				}
				
			}else if (myMoveTwoRoundAgo == SLPlayer.SCISSORS){ //win with scissors
				//keeps track of what move is played after
				if (oppMoveLastRound == SLPlayer.ROCK){
					numWinScissorsThenOppRock++;
				}else if (oppMoveLastRound == SLPlayer.PAPER){
					numWinScissorsThenOppPaper++;
				}else if (oppMoveLastRound == SLPlayer.SCISSORS){
					numWinScissorsThenOppScissors++;
				}else if (oppMoveLastRound == SLPlayer.SPOCK){
					numWinScissorsThenOppSpock++;
				}else if (oppMoveLastRound == SLPlayer.LIZARD){
					numWinScissorsThenOppLizard++;
				}
					
			}else if (myMoveTwoRoundAgo == SLPlayer.SPOCK){ //win with spock
				//keeps track of what move is played after
				if (oppMoveLastRound == SLPlayer.ROCK){
					numWinSpockThenOppRock++;
				}else if (oppMoveLastRound == SLPlayer.PAPER){
					numWinSpockThenOppPaper++;
				}else if (oppMoveLastRound == SLPlayer.SCISSORS){
					numWinSpockThenOppScissors++;
				}else if (oppMoveLastRound == SLPlayer.SPOCK){
					numWinSpockThenOppSpock++;
				}else if (oppMoveLastRound == SLPlayer.LIZARD){
					numWinSpockThenOppLizard++;
				}
			
			}else if (myMoveTwoRoundAgo == SLPlayer.LIZARD){ //win with lizard
				//keeps track of what move is played after
				if (oppMoveLastRound == SLPlayer.ROCK){
					numWinSpockThenOppRock++;
				}else if (oppMoveLastRound == SLPlayer.PAPER){
					numWinSpockThenOppPaper++;
				}else if (oppMoveLastRound == SLPlayer.SCISSORS){
					numWinSpockThenOppScissors++;
				}else if (oppMoveLastRound == SLPlayer.SPOCK){
					numWinSpockThenOppSpock++;
				}else if (oppMoveLastRound == SLPlayer.LIZARD){
					numWinSpockThenOppLizard++;
				}
			}
			
		//----------------after I lose 2 rounds ago what happens prev round---------\\
		}else{ 
			if (myMoveTwoRoundAgo == SLPlayer.ROCK){//lose with rock 
				//keeps track of what move is played after
				if (oppMoveLastRound == SLPlayer.ROCK){
					numLoseRockThenOppRock++;
				}else if (oppMoveLastRound == SLPlayer.PAPER){
					numLoseRockThenOppPaper++;
				}else if (oppMoveLastRound == SLPlayer.SCISSORS){
					numLoseRockThenOppScissors++;
				}else if (oppMoveLastRound == SLPlayer.SPOCK){
					numLoseRockThenOppSpock++;
				}else if (oppMoveLastRound == SLPlayer.LIZARD){
					numLoseRockThenOppLizard++;
				}
				
			}else if (myMoveTwoRoundAgo == SLPlayer.PAPER){ //lose with paper
				//keeps track of what move is played after
				if (oppMoveLastRound == SLPlayer.ROCK){
					numLosePaperThenOppRock++;
				}else if (oppMoveLastRound == SLPlayer.PAPER){
					numLosePaperThenOppPaper++;
				}else if (oppMoveLastRound == SLPlayer.SCISSORS){
					numLosePaperThenOppScissors++;
				}else if (oppMoveLastRound == SLPlayer.SPOCK){
					numLosePaperThenOppSpock++;
				}else if (oppMoveLastRound == SLPlayer.LIZARD){
					numLosePaperThenOppLizard++;
				}
				
			}else if (myMoveTwoRoundAgo == SLPlayer.SCISSORS){ //lose with scissors
				//keeps track of what move is played after
				if (oppMoveLastRound == SLPlayer.ROCK){
					numLoseScissorsThenOppRock++;
				}else if (oppMoveLastRound == SLPlayer.PAPER){
					numLoseScissorsThenOppPaper++;
				}else if (oppMoveLastRound == SLPlayer.SCISSORS){
					numLoseScissorsThenOppScissors++;
				}else if (oppMoveLastRound == SLPlayer.SPOCK){
					numLoseScissorsThenOppSpock++;
				}else if (oppMoveLastRound == SLPlayer.LIZARD){
					numLoseScissorsThenOppLizard++;
				}
					
			}else if (myMoveTwoRoundAgo == SLPlayer.SPOCK){ //lose with spock
				//keeps track of what move is played after
				if (oppMoveLastRound == SLPlayer.ROCK){
					numLoseSpockThenOppRock++;
				}else if (oppMoveLastRound == SLPlayer.PAPER){
					numLoseSpockThenOppPaper++;
				}else if (oppMoveLastRound == SLPlayer.SCISSORS){
					numLoseSpockThenOppScissors++;
				}else if (oppMoveLastRound == SLPlayer.SPOCK){
					numLoseSpockThenOppSpock++;
				}else if (oppMoveLastRound == SLPlayer.LIZARD){
					numLoseSpockThenOppLizard++;
				}
			
			}else if (myMoveTwoRoundAgo == SLPlayer.LIZARD){ //lose with lizard
				//keeps track of what move is played after
				if (oppMoveLastRound == SLPlayer.ROCK){
					numLoseSpockThenOppRock++;
				}else if (oppMoveLastRound == SLPlayer.PAPER){
					numLoseSpockThenOppPaper++;
				}else if (oppMoveLastRound == SLPlayer.SCISSORS){
					numLoseSpockThenOppScissors++;
				}else if (oppMoveLastRound == SLPlayer.SPOCK){
					numLoseSpockThenOppSpock++;
				}else if (oppMoveLastRound == SLPlayer.LIZARD){
					numLoseSpockThenOppLizard++;
				}
			}
		}
	}
			
	/**
	 * Sets my move based on what the opponent played last round if I won 
	 * and the probabilities of what will be played next
	 */
	private void playAccordingToDataWin(){
		
		if (prevOppMove == SLPlayer.ROCK){ //if prev opp move is rock, compare probability of next move
										   //and the move to counter the most probable move
			
			myMove = returnFromGreatestOfFiveMoves (numWinRockThenOppRock, numWinRockThenOppPaper,
										  numWinRockThenOppScissors, numWinRockThenOppSpock,
										  numWinRockThenOppLizard);
			
		}else if (prevOppMove == SLPlayer.PAPER){//if prev opp move is paper, compare probability of next move
			   									 //and the move to counter the most probable move
			
			myMove = returnFromGreatestOfFiveMoves (numWinPaperThenOppRock, numWinPaperThenOppPaper,
					  					  numWinPaperThenOppScissors, numWinPaperThenOppSpock,
					  					  numWinPaperThenOppLizard);
			
		}else if (prevOppMove == SLPlayer.SCISSORS){//if prev opp move is scissors, compare probability of next move
													//and the move to counter the most probable move
			
			myMove = returnFromGreatestOfFiveMoves (numWinScissorsThenOppRock, numWinScissorsThenOppPaper,
					  					  numWinScissorsThenOppScissors, numWinScissorsThenOppSpock,
					  					  numWinScissorsThenOppLizard);
			
		}else if (prevOppMove == SLPlayer.SPOCK){//if prev opp move is spock, compare probability of next move
			   									 //and the move to counter the most probable move
			
			myMove = returnFromGreatestOfFiveMoves (numWinSpockThenOppRock, numWinSpockThenOppPaper,
					  					  numWinSpockThenOppScissors, numWinSpockThenOppSpock,
					  					  numWinSpockThenOppLizard);
			
		}else if (prevOppMove == SLPlayer.LIZARD){//if prev opp move is lizard, compare probability of next move
			   									  //and the move to counter the most probable move
			
			myMove = returnFromGreatestOfFiveMoves (numWinLizardThenOppRock, numWinLizardThenOppPaper,
					  					  numWinLizardThenOppScissors, numWinLizardThenOppSpock,
					  					  numWinLizardThenOppLizard);
		}else{ //for errors
			myMove = SLPlayer.ROCK;
		}
	}
	
	/**
	 * Sets my move based on what the opponent played last round if I lost 
	 * and the probabilities of what will be played next
	 */
	private void playAccordingToDataLose(){
		
		if (prevOppMove == SLPlayer.ROCK){//if prev opp move is rock, compare probability of next move
										  //and the move to counter the most probable move
			
			myMove = returnFromGreatestOfFiveMoves (numLoseRockThenOppRock, numLoseRockThenOppPaper,
										  numLoseRockThenOppScissors, numLoseRockThenOppSpock,
										  numLoseRockThenOppLizard);
			
		}else if (prevOppMove == SLPlayer.PAPER){//if prev opp move is paper, compare probability of next move
			   									 //and the move to counter the most probable move
			
			myMove = returnFromGreatestOfFiveMoves (numLosePaperThenOppRock, numLosePaperThenOppPaper,
					  					  numLosePaperThenOppScissors, numLosePaperThenOppSpock,
					  					  numLosePaperThenOppLizard);
			
		}else if (prevOppMove == SLPlayer.SCISSORS){//if prev opp move is scissors, compare probability of next move
			   										//and the move to counter the most probable move
			
			myMove = returnFromGreatestOfFiveMoves (numLoseScissorsThenOppRock, numLoseScissorsThenOppPaper,
					  					  numLoseScissorsThenOppScissors, numLoseScissorsThenOppSpock,
					  					  numLoseScissorsThenOppLizard);
			
		}else if (prevOppMove == SLPlayer.SPOCK){//if prev opp move is spock, compare probability of next move
			   									 //and the move to counter the most probable move
			
			myMove = returnFromGreatestOfFiveMoves (numLoseSpockThenOppRock, numLoseSpockThenOppPaper,
					  					  numLoseSpockThenOppScissors, numLoseSpockThenOppSpock,
					  					  numLoseSpockThenOppLizard);
			
		}else if (prevOppMove == SLPlayer.LIZARD){//if prev opp move is lizard, compare probability of next move
			   									  //and the move to counter the most probable move
			
			myMove = returnFromGreatestOfFiveMoves (numLoseLizardThenOppRock, numLoseLizardThenOppPaper,
					  					  numLoseLizardThenOppScissors, numLoseLizardThenOppSpock,
					  					  numLoseLizardThenOppLizard);
			
		}else{ //for errors
			myMove = SLPlayer.ROCK;
		}
	}
	
	/**
	 * Finds which move contains the largest integer value and returns the move 
	 * that will counter it. The parameters must be put in order of the variable for 
	 * Rock Paper Scissors Spock then Lizard.
	 * 
	 * @param a Counter variable for rock
	 * @param b Counter variable for paper
	 * @param c Counter variable for scissors
	 * @param d Counter variable for spock
	 * @param e Counter variable for lizard
	 * 
	 * @return Move An integer representing the move that will be played 
	 */
	private int returnFromGreatestOfFiveMoves(int a, int b, int c, int d, int e){
	
		int move = 0;
		
		if (a > b && a > c && a > d && a > e){ //if rock the most played
			if (roundNumber%2 == 0){
				move = SLPlayer.PAPER; //returns paper if even round
			}else{
				move = SLPlayer.SPOCK; //returns spock if odd round
			}
		}else if (b > a && b > c && b > d && b > e){ //if paper the most played
			if (roundNumber%2 == 0){
				move = SLPlayer.SCISSORS; //returns scissors if even round
			}else{
				move = SLPlayer.LIZARD; //returns lizard if odd round
			}			
		}else if (c > a && c > b && c  > d && c > e){ //if scissors the most played
			if (roundNumber%2 == 0){
				move = SLPlayer.ROCK; //returns rock if even round
			}else{
				move = SLPlayer.SPOCK; //returns spock if odd round
			}
		}else if (d > a && d > b && d > c && d > e){ //if spock the most played
			if (roundNumber%2 == 0){
				move = SLPlayer.PAPER; //returns paper if even round
			}else{ 
				move = SLPlayer.LIZARD; //returns lizard if odd round
			}
		}else if (e > a && e > b && e > c && e > d){ //if lizard the most played
			if (roundNumber%2 == 0){
				move = SLPlayer.ROCK; //returns scissors if even round
			}else{
				move = SLPlayer.SCISSORS; //returns rock if odd round
			}
		}else{ // if no one move is greater
		
			if (roundNumber%2 == 0){	
				if (roundNumber%3 == 0){
					move = SLPlayer.ROCK;
				}else if (roundNumber%4 == 0){
					move = SLPlayer.SCISSORS;
				}else{
					move = SLPlayer.LIZARD;
				}
			}else{ // if odd round
				if (roundNumber%3 == 0){
					move = SLPlayer.SPOCK;
				}else{
					move = SLPlayer.PAPER;
				}
			}
		}
		return move; //returns the decided move
	}
	
	/**
	 * Finds which move contains the largest int value and returns that move.
	 * The parameters must be put in order of the variable for 
	 * Rock Paper Scissor Spock then Lizard.
	 * 
	 * @param a Counter variable for rock
	 * @param b Counter variable for paper
	 * @param c Counter variable for scissors
	 * @param d Counter variable for spock
	 * @param e Counter variable for lizard
	 */
	private int greatestOfFiveMoves (int a, int b, int c, int d, int e){
		if (a > b && a > c && a > d && a > e){ //if rock the most played
			return SLPlayer.ROCK; //return rock
		}else if (b > a && b > c && b > d && b > e){ //if paper the most played
			return SLPlayer.PAPER; //return paper		
		}else if (c > a && c > b && c  > d && c > e){ //if scissors the most played
			return SLPlayer.SCISSORS; //return scissors
		}else if (d > a && d > b && d > c && d > e){ //if spock the most played
			return SLPlayer.SPOCK; //return spock
		}else{ //if lizard the most played
			return SLPlayer.LIZARD; //return lizard
		}
	}
	
	/**
	 * Resets collected data
	 * Variables pertaining to each move will be set to zero except for the
	 * variable with the largest value which will be set to one
	 */
	private void collectedDataReset(){
		//------RESETING FOR WIN VARIBALES---------------\\
		
		// for winning with rock
		//find the largest variable for move after winning with rock
		int forWinRock = greatestOfFiveMoves (numWinRockThenOppRock, numWinRockThenOppPaper,
											  numWinRockThenOppScissors, numWinRockThenOppSpock,
											  numWinRockThenOppLizard);
		//set all variables for winning with rock back to 0
		numWinRockThenOppRock = 0; 
		numWinRockThenOppPaper = 0;
		numWinRockThenOppScissors = 0;
		numWinRockThenOppSpock = 0;
		numWinRockThenOppLizard = 0;
				
		//the variable which was the greatest will be set to 1 instead of 0
		if (forWinRock == SLPlayer.ROCK){
			numWinRockThenOppRock = 1;
		}else if (forWinRock == SLPlayer.PAPER){
			numWinRockThenOppPaper = 1;
		}else if (forWinRock == SLPlayer.SCISSORS){
			numWinRockThenOppScissors = 1;
		}else if (forWinRock == SLPlayer.SPOCK){
			numWinRockThenOppSpock = 1;
		}else{
			numWinRockThenOppLizard = 1;
		}
				
		//for winnning with paper
		//find the largest variable for move after winning with paper
		int forWinPaper = greatestOfFiveMoves (numWinPaperThenOppRock, numWinPaperThenOppPaper,
				  							   numWinPaperThenOppScissors, numWinPaperThenOppSpock,
				  							   numWinPaperThenOppLizard);
		//set all variables for winning with paper back to 0		
		numWinPaperThenOppRock = 0; 
		numWinPaperThenOppPaper = 0;
		numWinPaperThenOppScissors = 0;
		numWinPaperThenOppSpock = 0;
		numWinPaperThenOppLizard = 0;
				
		//the variable which was the greatest will be set to 1 instead of 0
		if (forWinPaper == SLPlayer.ROCK){
			numWinPaperThenOppRock = 1;
		}else if (forWinPaper == SLPlayer.PAPER){
			numWinPaperThenOppPaper = 1;
		}else if (forWinPaper == SLPlayer.SCISSORS){
			numWinPaperThenOppScissors = 1;
		}else if (forWinPaper == SLPlayer.SPOCK){
			numWinPaperThenOppSpock = 1;
		}else{
			numWinPaperThenOppLizard = 1;
		}
				
		//for winning with scissors
		//find the largest variable for move after winning with scissors
		int forWinScissors = greatestOfFiveMoves(numWinScissorsThenOppRock, numWinScissorsThenOppPaper,
						  			   					  numWinScissorsThenOppScissors, numWinScissorsThenOppSpock,
						  			   					  numWinScissorsThenOppLizard);
		//set all variables for winning with scissors back to 0			
		numWinScissorsThenOppRock = 0; 
		numWinScissorsThenOppPaper = 0;
		numWinScissorsThenOppScissors = 0;
		numWinScissorsThenOppSpock = 0;
		numWinScissorsThenOppLizard = 0;
		
		//the variable which was the greatest will be set to 1 instead of 0
		if (forWinScissors == SLPlayer.ROCK){
			numWinScissorsThenOppRock = 1;
		}else if (forWinScissors == SLPlayer.PAPER){
			numWinScissorsThenOppPaper = 1;
		}else if (forWinScissors == SLPlayer.SCISSORS){
			numWinScissorsThenOppScissors = 1;
		}else if (forWinScissors == SLPlayer.SPOCK){
			numWinScissorsThenOppSpock = 1;
		}else{
			numWinScissorsThenOppLizard = 1;
		}
				
		//for winning with spock
		//find the largest variable for move after winning with spock
		int forWinSpock = greatestOfFiveMoves (numWinSpockThenOppRock, numWinSpockThenOppPaper,
				  								numWinSpockThenOppScissors, numWinSpockThenOppSpock,
				  								numWinSpockThenOppLizard);
		//set all variables for winning with spock back to 0	
		numWinSpockThenOppRock = 0; 
		numWinSpockThenOppPaper = 0;
		numWinSpockThenOppScissors = 0;
		numWinSpockThenOppSpock = 0;
		numWinSpockThenOppLizard = 0;
	
		//the variable which was the greatest will be set to 1 instead of 0
		if (forWinSpock == SLPlayer.ROCK){
			numWinSpockThenOppRock = 1;
		}else if (forWinSpock == SLPlayer.PAPER){
			numWinSpockThenOppPaper = 1;
		}else if (forWinSpock == SLPlayer.SCISSORS){
			numWinSpockThenOppScissors = 1;
		}else if (forWinSpock == SLPlayer.SPOCK){
			numWinSpockThenOppSpock = 1;
		}else{
			numWinSpockThenOppLizard = 1;
		}
				
		//for winning with lizard
		//find the largest variable for move after winning with lizard
		int forWinLizard = greatestOfFiveMoves (numWinLizardThenOppRock, numWinLizardThenOppPaper,
				  								 numWinLizardThenOppScissors, numWinLizardThenOppSpock,
				  								 numWinLizardThenOppLizard);
		//set all variables for winning with lizard back to 0	
		numWinLizardThenOppRock = 0; 
		numWinLizardThenOppPaper = 0;
		numWinLizardThenOppScissors = 0;
		numWinLizardThenOppSpock = 0;
		numWinLizardThenOppLizard = 0;
	
		//the variable which was the greatest will be set to 1 instead of 0
		if (forWinLizard == SLPlayer.ROCK){
			numWinLizardThenOppRock = 1;
		}else if (forWinLizard == SLPlayer.PAPER){
			numWinLizardThenOppPaper = 1;
		}else if (forWinLizard == SLPlayer.SCISSORS){
			numWinLizardThenOppScissors = 1;
		}else if (forWinLizard == SLPlayer.SPOCK){
			numWinLizardThenOppSpock = 1;
		}else{
			numWinLizardThenOppLizard = 1;
		}
			
		
		//------RESETING FOR LOSE VARIBALES---------------\\
		// for losing with rock
		//find the largest variable for move after losing with rock
		int forLoseRock = greatestOfFiveMoves (numLoseRockThenOppRock, numLoseRockThenOppPaper,
											   numLoseRockThenOppScissors, numLoseRockThenOppSpock,
											   numLoseRockThenOppLizard);
		//set all variables for losing with rock back to 0	
		numLoseRockThenOppRock = 0; 
		numLoseRockThenOppPaper = 0;
		numLoseRockThenOppScissors = 0;
		numLoseRockThenOppSpock = 0;
		numLoseRockThenOppLizard = 0;
		
		//the variable which was the greatest will be set to 1 instead of 0
		if (forLoseRock == SLPlayer.ROCK){
			numLoseRockThenOppRock = 1;
		}else if (forLoseRock == SLPlayer.PAPER){
			numLoseRockThenOppPaper = 1;
		}else if (forLoseRock == SLPlayer.SCISSORS){
			numLoseRockThenOppScissors = 1;
		}else if (forLoseRock == SLPlayer.SPOCK){
			numLoseRockThenOppSpock = 1;
		}else{
			numLoseRockThenOppLizard = 1;
		}
		
		//for losing with paper
		//find the largest variable for move after losing with paper
		int forLosePaper = greatestOfFiveMoves (numLosePaperThenOppRock, numLosePaperThenOppPaper,
				  								numLosePaperThenOppScissors, numLosePaperThenOppSpock,
				  								numLosePaperThenOppLizard);
		//set all variables for losing with paper back to 0
		numLosePaperThenOppRock = 0; 
		numLosePaperThenOppPaper = 0;
		numLosePaperThenOppScissors = 0;
		numLosePaperThenOppSpock = 0;
		numLosePaperThenOppLizard = 0;
		
		//the variable which was the greatest will be set to 1 instead of 0
		if (forLosePaper == SLPlayer.ROCK){
			numLosePaperThenOppRock = 1;
		}else if (forLosePaper == SLPlayer.PAPER){
			numLosePaperThenOppPaper = 1;
		}else if (forLosePaper == SLPlayer.SCISSORS){
			numLosePaperThenOppScissors = 1;
		}else if (forLosePaper == SLPlayer.SPOCK){
			numLosePaperThenOppSpock = 1;
		}else{
			numLosePaperThenOppLizard = 1;
		}
		
		//for losing with scissors
		//find the largest variable for move after losing with scissors
		int forLoseScissors = greatestOfFiveMoves(numLoseScissorsThenOppRock, numLoseScissorsThenOppPaper,
				  			   					  numLoseScissorsThenOppScissors, numLoseScissorsThenOppSpock,
				  			   					  numLoseScissorsThenOppLizard);
		//set all variables for losing with scissors back to 0
		numLoseScissorsThenOppRock = 0; 
		numLoseScissorsThenOppPaper = 0;
		numLoseScissorsThenOppScissors = 0;
		numLoseScissorsThenOppSpock = 0;
		numLoseScissorsThenOppLizard = 0;
		
		//the variable which was the greatest will be set to 1 instead of 0
		if (forLoseScissors == SLPlayer.ROCK){
			numLoseScissorsThenOppRock = 1;
		}else if (forLoseScissors == SLPlayer.PAPER){
			numLoseScissorsThenOppPaper = 1;
		}else if (forLoseScissors == SLPlayer.SCISSORS){
			numLoseScissorsThenOppScissors = 1;
		}else if (forLoseScissors == SLPlayer.SPOCK){
			numLoseScissorsThenOppSpock = 1;
		}else{
			numLoseScissorsThenOppLizard = 1;
		}
		
		//for losing with spock
		//find the largest variable for move after losing with spock
		int forLoseSpock = greatestOfFiveMoves (numLoseSpockThenOppRock, numLoseSpockThenOppPaper,
				  								numLoseSpockThenOppScissors, numLoseSpockThenOppSpock,
				  								numLoseSpockThenOppLizard);
		//set all variables for losing with spock back to 0
		numLoseSpockThenOppRock = 0; 
		numLoseSpockThenOppPaper = 0;
		numLoseSpockThenOppScissors = 0;
		numLoseSpockThenOppSpock = 0;
		numLoseSpockThenOppLizard = 0;
		
		//the variable which was the greatest will be set to 1 instead of 0
		if (forLoseSpock == SLPlayer.ROCK){
			numLoseSpockThenOppRock = 1;
		}else if (forLoseSpock == SLPlayer.PAPER){
			numLoseSpockThenOppPaper = 1;
		}else if (forLoseSpock == SLPlayer.SCISSORS){
			numLoseSpockThenOppScissors = 1;
		}else if (forLoseSpock == SLPlayer.SPOCK){
			numLoseSpockThenOppSpock = 1;
		}else{
			numLoseSpockThenOppLizard = 1;
		}
		
		//for losing with lizard
		//find the largest variable for move after losing with lizard
		int forLoseLizard = greatestOfFiveMoves (numLoseLizardThenOppRock, numLoseLizardThenOppPaper,
				  								 numLoseLizardThenOppScissors, numLoseLizardThenOppSpock,
				  								 numLoseLizardThenOppLizard);
		//set all variables for losing with lizard back to 0
		numLoseLizardThenOppRock = 0; 
		numLoseLizardThenOppPaper = 0;
		numLoseLizardThenOppScissors = 0;
		numLoseLizardThenOppSpock = 0;
		numLoseLizardThenOppLizard = 0;
		
		//the variable which was the greatest will be set to 1 instead of 0
		if (forLoseLizard == SLPlayer.ROCK){
			numLoseLizardThenOppRock = 1;
		}else if (forLoseLizard == SLPlayer.PAPER){
			numLoseLizardThenOppPaper = 1;
		}else if (forLoseLizard == SLPlayer.SCISSORS){
			numLoseLizardThenOppScissors = 1;
		}else if (forLoseLizard == SLPlayer.SPOCK){
			numLoseLizardThenOppSpock = 1;
		}else{
			numLoseLizardThenOppLizard = 1;
		}
	}
	
	/**
	 * Called by the arena to communicate the opponents move to the player. This
	 * is called at the end of a round.
	 * 
	 * @param move ROCK, SCISSORS, PAPER, SPOCK, or LIZARD
	 */
 	public void opponentMove(int move) {
		prevOppMove = move; // the previous move
		arrayOppMoves.add(move);
	}

	/**
	 * Check if the opponent or I won the round
	 * 
	 * @param myMove  The move I played: ROCK, SCISSORS, PAPER, SPOCK, or LIZARD
	 * @param oppMove The move the opp played: ROCK, SCISSORS, PAPER, SPOCK, or LIZARD
	 */
	private boolean checkIfRoundWin(int myMove, int oppMove){
		
		boolean ifWin = true; //boolean for if win or not 
		
		if (myMove == SLPlayer.ROCK){
			if (oppMove == SLPlayer.PAPER || oppMove == SLPlayer.SPOCK){
				ifWin = false;
			}else{
				ifWin = true;
			}
			
		}else if (myMove == SLPlayer.PAPER){
			if (oppMove == SLPlayer.SCISSORS || oppMove == SLPlayer.LIZARD){
				ifWin = false;
			}else{
				ifWin = true;
			}
			
		} else if (myMove == SLPlayer.SCISSORS) {
			if (oppMove == SLPlayer.ROCK || oppMove == SLPlayer.SPOCK){
				ifWin = false;
			}else{
				ifWin = true;
			}
			
		} else if (myMove == SLPlayer.SPOCK) {
			if (oppMove == SLPlayer.PAPER || oppMove == SLPlayer.LIZARD){
				ifWin = false;
			}else{
				ifWin = true;
			}
			
		} else if (myMove == SLPlayer.LIZARD) {
			if (oppMove == SLPlayer.ROCK || oppMove == SLPlayer.SCISSORS){
				ifWin = false;
			}else{
				ifWin = true;
			}	
		}
			return ifWin;
	}	

	/**
	 * Draws the player's logo
	 * 
	 * @param g The graphics context
	 */
	public void draw(Graphics g) {
		String letter;
		g.setColor(Color.BLUE);
		g.fillRect(0, 0, arena.getPlayerScreenWidth(), arena
				.getPlayerScreenHeight());
		g.setColor(Color.RED);
		g.fillRect(10, 10, arena.getPlayerScreenWidth() - 20, arena
				.getPlayerScreenHeight() - 20);
		g.setColor(Color.BLACK);
		g.setFont(new Font("MonoSpaced", Font.BOLD, 20));
		if (animationParam == 0)
			letter = "Hanson";
		else if (animationParam == 1)
			letter = "Will";
		else
			letter = "Win";
		g.drawString(letter, drawX, drawY);
	}

	/**
	 * Returns the player's name
	 * 
	 * @return the name of the player
	 */
	public String name() {
		return "HansonNg";
	}
	
	/**
	 * Handles logo animation
	 */
	public void run() {
		while (true) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
			int oldAP = animationParam;
			do
				animationParam = (int) (Math.random() * 3);
			while (animationParam == oldAP);
			drawX = (int) (Math.random() * (arena.getPlayerScreenWidth() - 120) + 10);
			drawY = (int) (Math.random() * (arena.getPlayerScreenHeight() - 50) + 32);
		}
	}
}
