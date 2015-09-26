package players;

import java.awt.Graphics;
import java.applet.Applet;
import java.util.ArrayList;
import java.util.List;

import lizardSpock.SLArena;
/**
 * A bot that plays RPSLS that gets the most frequent move that the opponent plays,
 * and counters with something appropriate.
 *
 * @author	David Shan
 * @version	December 2014
 *
 */
public class DavidS extends SLPlayer {
	
	private int turn;
	private int opponentRocks;
	private int opponentPapers;
	private int opponentScissors;
	private int opponentLizards;
	private int opponentSpocks;
	
	private int starterMove;
	private int myPreviousMove;
	
	private int[] opponentMovesCount = {opponentRocks, opponentPapers, opponentScissors, opponentLizards, opponentSpocks};
	
	public DavidS (SLArena arena)
	{
		super(arena);
		startGameVariables();
	}
	
	/**
	 * 
	 * <b>How this works</b> 
	 * <p>Using an array that keeps track of the number
	 * of each type of move, get the opponent's most 
     * frequently played move, and that would be the
     * most likely move that the opponent will play next.</p>
	 * 
	 * <p>Counter that move with something appropriate.
	 * To keep it unpredictable, if I already played
	 * Spock to counter Rock, (and his next likely move
	 * is still rock), then play Paper, instead of Spock
	 * again.</p>
	 * 
	 * @return	An integer, representing the move to be played.
	 */
	public int move()
	{
		turn++;
		int myMove = -1;
		int likelyOpponentMove;
		
		// First move of the game: Rock
		if (turn==1)
		{
			myPreviousMove = starterMove;
			return starterMove;	
		}
		
		// Get the move that the opponent plays most frequently,
		// and that is the most likely move
		likelyOpponentMove = getHighestIndex(opponentMovesCount);
		//System.out.println(likelyOpponentMove);
		if (likelyOpponentMove == 0) // If likely move is rock
		{  
			myMove = SLPlayer.SPOCK; // Counter with Spock
			// To keep it unpredictable(ish), if my previous move was already Spock, then throw Paper
			if (myPreviousMove == SLPlayer.SPOCK) 
			{
				myMove = SLPlayer.PAPER;
			}
		}
		else if (likelyOpponentMove == 1) // If likely move is Paper
		{
			myMove = SLPlayer.SCISSORS; // Counter with Scissors
			if (myPreviousMove == SLPlayer.SCISSORS)
			{
				myMove = SLPlayer.LIZARD; // Counter with Lizard instead, if I played Scissors last time
			}
		}
		else if (likelyOpponentMove == 2) // If likely move is Scissors
		{
			myMove = SLPlayer.ROCK; // Counter with Rock
			if (myPreviousMove == SLPlayer.ROCK)
			{
				myMove = SLPlayer.SPOCK; // Counter with Spock instead, if I played Rock last time
			}
		}
		else if (likelyOpponentMove == 3) // If likely move is Lizard
		{
			myMove = SLPlayer.ROCK; // Counter with Rock
			if (myPreviousMove == SLPlayer.ROCK) 
			{
				myMove = SLPlayer.SCISSORS; // Counter with Scissors instead, if I played Rock last time
			}
		}
		else if (likelyOpponentMove == 4) // If likely move is Spock
		{
			myMove = SLPlayer.LIZARD; // Counter with Lizard
			if (myPreviousMove == SLPlayer.LIZARD)
			{
				myMove = SLPlayer.PAPER; // Counter with Paper instead, if I played Lizard last time
			}
		}
		//System.out.println(myMove);
		
		myPreviousMove = myMove; // Set the move I choose as my most recent move
		return myMove;
	}
	
	/**
	 * Gets the opponent's move, and adds it to a data structure
	 * (that keeps track of the number of moves played for each move type)
	 * for use in calculating the player's next move.
	 * 
	 * @param	move	The opponent's move
	 */
	public void opponentMove(int move)
	{
		if (move == SLPlayer.ROCK)
		{
			opponentRocks++;
		}
		else if (move == SLPlayer.PAPER)
		{
			opponentPapers++;
		}
		else if (move == SLPlayer.SCISSORS)
		{
			opponentScissors++;
		}
		else if (move == SLPlayer.LIZARD)
		{
			opponentLizards++;
		}
		else if (move == SLPlayer.SPOCK)
		{
			opponentSpocks++;
		}
		else if (move == SLPlayer.ERROR)
		{
			System.out.println("Error!");		
		}
		opponentMovesCount = updateArray();
	}
	  
	
	private int getHighestIndex(int[] num)
	{
		int highest = num[0];
	    int indexOfHigh = 0;
	    
	    for (int i = 0; i < num.length; i++)
	    {
	    	if (num[i] > highest)
	    	{
	    		highest = num[i];
	    		indexOfHigh = i;
	    	}
	    }
	    return indexOfHigh;
	 }

	private void startGameVariables()
	{
		turn = opponentRocks = opponentPapers = opponentScissors = opponentLizards = opponentSpocks = 0;
		starterMove = SLPlayer.SCISSORS;
	}
	private int[] updateArray()
	{
		int[] thing = {opponentRocks, opponentPapers, opponentScissors, opponentLizards, opponentSpocks};
		return thing;
	}
	@Override
	public String name() {
		// TODO Auto-generated method stub
		return "DdS";
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		
	}
}