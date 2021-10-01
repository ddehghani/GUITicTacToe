import java.util.*;

public abstract class Controller implements Subject
{	
	public static final int PLAYER_ONE_TURN = 0,
							PLAYER_TWO_TURN = 1,
							PLAYER_ONE_WON = 1,
							PLAYER_TWO_WON = 2,
							DRAW = 3,
							UNDETEREMINED = 4;

	protected ArrayList<Observer> observers = new ArrayList<Observer>();
	public void registerObserver(Observer observer)
	{
		observers.add(observer);
	}

	public void removeObserver(Observer observer)
	{
		observers.remove(observer);
	}
	
	public abstract void startNewGame();
	public abstract boolean move(int row, int col);
	public abstract void playBestMove();
	public abstract int evaluateResult();
}