public class GameController extends Controller
{	
	private char[][] gameTable;
	private int turn;

	public void notifyObservers()
	{
		for (Observer o : observers)
			o.update(gameTable,turn); 
	}

	public void startNewGame()
	{
		gameTable = new char[3][3];
		for (int row=0; row<3; row++)
			for (int column=0; column<3; column++)
				gameTable[row][column] = ' ';
		turn = PLAYER_ONE_TURN;
		notifyObservers();
	}

	public boolean move(int row, int col)
	{	
		if (gameTable[row][col] != ' ')
			return false;
		gameTable[row][col] = (turn == PLAYER_ONE_TURN ? 'X' : 'O');
		turn = (turn + 1)%2;
		notifyObservers();
		return true;
	}

	public void playBestMove()
	{	
		if (getEmptyCellCount(gameTable) == 9)
		{
			move((int) (Math.random() * 3),(int) (Math.random() * 3));
			return;
		}

		char currentLetter = (turn == PLAYER_ONE_TURN ? 'X' : 'O');
		int maxUtility = Integer.MIN_VALUE;
		int row = 0, column = 0;
		for (int i=0; i<3; i++)
			for(int j=0; j<3; j++)
				if (gameTable[i][j] == ' ' )
				{	
					gameTable[i][j] = currentLetter;
					int utility = minimax(gameTable, (turn + 1)%2, turn);
					gameTable[i][j] = ' ';
					if (utility > maxUtility) 
					{
						row = i;
						column = j;
						maxUtility = utility;
					}
					//uncomment to see util density
					//System.out.println(i + "  " + j + " | " + utility + "\n");
				}
		move(row,column);
	}

	public int evaluateResult()
	{
		return getResult(gameTable);
	}

	private int getResult(char[][] gameTable) // 1: player 1 won 2: player 2 won 3: tie 4: ongoing
	{
		for (int i=0; i<3; i++)
		{	
			//check rows
			if ( (gameTable[i][0] == gameTable[i][1]) && (gameTable[i][0] == gameTable[i][2]) )
			{
				if (gameTable[i][0] == 'X')
					return PLAYER_ONE_WON;
				else if (gameTable[i][0] == 'O')
					return PLAYER_TWO_WON;			
			}
			//check columns
			if ( (gameTable[0][i] == gameTable[1][i]) && (gameTable[1][i] == gameTable[2][i]) )
			{
				if (gameTable[0][i] == 'X')
					return PLAYER_ONE_WON;
				else if (gameTable[0][i] == 'O')
					return PLAYER_TWO_WON;				
			}
		}
		//check / and \
		if ( ((gameTable[0][0] == gameTable[1][1]) && (gameTable[1][1] == gameTable[2][2])) ||
				((gameTable[0][2] == gameTable[1][1]) && (gameTable[1][1] == gameTable[2][0])) )
		{
			if (gameTable[1][1] == 'X')
				return PLAYER_ONE_WON;
			else if (gameTable[1][1] == 'O')
				return PLAYER_TWO_WON;
		}
		//if no winner
		return (getEmptyCellCount(gameTable) == 0 ? DRAW : UNDETEREMINED);
	}

	private int getEmptyCellCount(char[][] gameTable)
	{	
		int result = 0;
		for (int i=0; i<3; i++)
			for(int j=0; j<3; j++)
				if (gameTable[i][j] == ' ')
					result++;
		return result;
	}

	private int minimax(char[][] table, int currentTurn, int computerTurn)
	{
		int currentResult = getResult(table), 
			utilMultiplier = getEmptyCellCount(table) + 1, 
			maxUtility = Integer.MIN_VALUE, 
			minUtility = Integer.MAX_VALUE;

		if (currentResult == DRAW) //draw
			return 0;
		if (currentResult - 1  == computerTurn) //won
			return 1 * utilMultiplier;
		if (currentResult - 1  == (computerTurn + 1)%2) //lost
			return -1 * utilMultiplier;
		
		for (int i=0; i<3; i++)
			for(int j=0; j<3; j++)
				if (table[i][j] == ' ')
				{
					table[i][j] = (currentTurn == PLAYER_ONE_TURN ? 'X' : 'O');
					int utility = minimax(table, (currentTurn + 1)%2, computerTurn);
					table[i][j] = ' ';
					if (utility > maxUtility)
						maxUtility = utility;
					if (utility < minUtility)
						minUtility = utility;
				}
		return (currentTurn == computerTurn ? maxUtility : minUtility);
	}
}