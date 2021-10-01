import java.util.Scanner;

public class CLIView extends View
{	
	private int turn;
	public CLIView(Controller controller)
	{	
		super(controller);
		controller.registerObserver(this);
		turn = 0;
	}
	
	public void update(char[][] gameTable, int turn)
	{	
		clearScreen();
		preview(gameTable);
		this.turn = turn;
	}

	private void preview(char[][] gameTable)
	{
		System.out.println("\n       1       2       3");
		System.out.println("   *************************");
		System.out.println("   *       *       *       *");
		System.out.println(" A *   " + gameTable[0][0] + "   *   " + gameTable[0][1] + "   *   " + gameTable[0][2] + "   *");
		System.out.println("   *       *       *       *");
		System.out.println("   *************************");
		System.out.println("   *       *       *       *");
		System.out.println(" B *   " + gameTable[1][0] + "   *   " + gameTable[1][1] + "   *   " + gameTable[1][2] + "   *");
		System.out.println("   *       *       *       *");
		System.out.println("   *************************");
		System.out.println("   *       *       *       *");
		System.out.println(" C *   " + gameTable[2][0] + "   *   " + gameTable[2][1] + "   *   " + gameTable[2][2] + "   *");
		System.out.println("   *       *       *       *");
		System.out.println("   *************************\n");
	}

	private void clearScreen() 
	{  
    	System.out.print("\033[H\033[2J");  
    	System.out.flush();  
   	}

   	/*
	public void startGame()
	{
		while(controller.evaluateResult() == Controller.UNDETEREMINED)
		{
			getMoveFromUser();
		}
	}

	private void getMoveFromUser()
	{
		Scanner userInput = new Scanner(System.in);
		System.out.print("Enter the row letter followed by column number (ex. B1, A3): ");
		String move = userInput.nextLine();
		boolean inputValid = false;
		while (!inputValid)
		{
			//input check
			while ( (move.length() != 2) || 
					!( move.substring(0,1).equalsIgnoreCase("A") || move.substring(0,1).equalsIgnoreCase("B") || move.substring(0,1).equalsIgnoreCase("C")) ||
					!( move.substring(1,2).equals("1") || move.substring(1,2).equals("2") || move.substring(1,2).equals("3")) )
			{
				System.out.println("Wrong input format.");
				System.out.print("Enter the row letter followed by column number (ex. B1, A3): ");
				move = userInput.nextLine();
			}
			//determine row and column
			int column = Integer.parseInt(move.substring(1,2)) - 1,
			row = switch (move.charAt(0))
				{	
					case 'a', 'A'-> 0;
					case 'b', 'B'-> 1;
					default -> 2;
				};
			if (!controller.move(row,column)) //move
			{
				System.out.println("The cell you have chosen is already taken!");
				System.out.print("Enter the row letter followed by column number (ex. B1, A3): ");
				move = userInput.nextLine();
			}
		}
	}
	*/
}