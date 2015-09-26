package players;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.applet.Applet;

import java.util.ArrayList;

import lizardSpock.ApplicationAudioClip;
import lizardSpock.SLArena;

public class RhysY extends SLPlayer implements Runnable{

	private int lastMyMove;
	private int lastOppMove;
	private ArrayList<Integer> oppMoves = new ArrayList<Integer>();
	private ArrayList<Integer> myMoves =  new ArrayList<Integer>();

	private int animationParam = 0;
	private int drawX = 20, drawY = 20;
	private int turnNum = 0;
	
	
	public RhysY(SLArena arena) {
		super(arena);
		Thread t = new Thread(this);
		t.start();
	}
	

	public int move() {
		int myMove = 0;
		turnNum++;
		checkOpp();
		if(turnNum == 1)
			myMove = SLPlayer.ROCK;
		else
			myMove = whatBeats(lastOppMove); 
		//System.out.println("Last opponent move: " + lastOppMove);
		//System.out.println("My move: " + myMove);
		return myMove;
	}


	public void opponentMove(int move) {
		lastOppMove = move;
		oppMoves.add(move);
	}


	public String name() {
		return "Get wrecked Jr.";
	}

	
	private int checkOpp()
	{
		int rockNum = 0;
		int scissorNum = 0;
		int paperNum = 0;
		int spockNum = 0;
		int lizardNum = 0;
		int errorNum = 0;
		int returnNum = 0;
		int counter = -2;
		
		for(int move: oppMoves)
		{
			counter++;
			if(move == lastOppMove)
			{
				if(oppMoves.size() > counter){
					int nextMove = oppMoves.get(counter + 1);
					if(nextMove == SLPlayer.ROCK)
						rockNum++;
					else if( nextMove == SLPlayer.SCISSORS)
						scissorNum++;
					else if( nextMove == SLPlayer.PAPER)
						paperNum++;
					else if( nextMove == SLPlayer.SPOCK)
						spockNum++;
					else if (nextMove == SLPlayer.LIZARD)
						lizardNum++;
					else if (nextMove == SLPlayer.ERROR)
						errorNum++;
				}
			}
			
		}
		
		if(rockNum > scissorNum && rockNum > paperNum && rockNum > spockNum && rockNum > lizardNum && rockNum > errorNum)
			returnNum = SLPlayer.ROCK;
		else if (scissorNum > rockNum && scissorNum > paperNum && scissorNum > spockNum && scissorNum > lizardNum && scissorNum > errorNum)
			returnNum = SLPlayer.SCISSORS;
		else if (paperNum > rockNum && paperNum > scissorNum && paperNum > spockNum && paperNum > lizardNum && paperNum > errorNum)
			returnNum = SLPlayer.PAPER;
		else if (spockNum > rockNum && spockNum > scissorNum && spockNum > paperNum && spockNum > lizardNum && spockNum > errorNum)
			returnNum = SLPlayer.SPOCK;
		else if (lizardNum > rockNum && lizardNum > scissorNum && lizardNum > paperNum && lizardNum > spockNum && lizardNum > errorNum)
			returnNum = SLPlayer.LIZARD;
		else if (errorNum > rockNum && errorNum > scissorNum && errorNum > paperNum && errorNum > spockNum && errorNum > lizardNum)
			returnNum = SLPlayer.ERROR;
		
		return returnNum;
	}
	
	public int whatBeats(int opponentMove)
	{
		int curMove = 0;
			
		if(opponentMove == SLPlayer.ROCK)
		{
			if(turnNum % 2 == 0)
				curMove = SLPlayer.PAPER;
			else
				curMove = SLPlayer.SPOCK;
		}else if (opponentMove == SLPlayer.SCISSORS)
		{
			if(turnNum % 2 == 0)
				curMove = SLPlayer.SPOCK;
			else
				curMove = SLPlayer.ROCK;
		}
		else if (opponentMove == SLPlayer.PAPER)
		{
			if(turnNum % 2 == 0)
				curMove = SLPlayer.SCISSORS;
			else
				curMove = SLPlayer.LIZARD;
		} else if (opponentMove == SLPlayer.SPOCK)
		{
			if(turnNum % 2 == 0)
				curMove = SLPlayer.LIZARD;
			else
				curMove = SLPlayer.PAPER;
		}
		else if (opponentMove == SLPlayer.LIZARD)
		{
			if(turnNum % 2 == 0)
				curMove = SLPlayer.ROCK;
			else
				curMove = SLPlayer.SCISSORS;
		}
		else if (opponentMove == SLPlayer.ERROR)
		{
			if(turnNum % 2 == 0)
				curMove = SLPlayer.PAPER;
			else
				curMove = SLPlayer.SCISSORS;
		}
		return curMove;
	}

	/**
	 * Draws the player's logo
	 *
	 * @param g
	 *            the graphics context
	 */

	public void draw(Graphics g) {
		String letter;
		g.setColor(Color.RED);
		g.fillRect(0, 0, arena.getPlayerScreenWidth(), arena
				.getPlayerScreenHeight());
		g.setColor(Color.GREEN);
		g.fillRect(10, 10, arena.getPlayerScreenWidth() - 20, arena
				.getPlayerScreenHeight() - 20);
		g.setColor(Color.BLACK);
		g.setFont(new Font("MonoSpaced", Font.BOLD, 20));
		if (animationParam == 0)
			letter = "Rhys";
		else if (animationParam == 1)
			letter = "Will";
		else
			letter = "Win";
		g.drawString(letter, drawX, drawY);
	}
	
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