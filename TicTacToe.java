import java.util.Scanner;
import java.*;

public class TicTacToe
{
	public static void main(String[] args)
	{
		Controller gameController = new GameController();
		GUIView gui = new GUIView(gameController);
		CLIView cli = new CLIView(gameController);
		gameController.removeObserver(cli);
		Scanner userInput = new Scanner(System.in);
		boolean running = true;
		while (running)
		{
			switch(userInput.nextLine())
			{
				case "add cli":
					gameController.registerObserver(cli);
					break;
				case "remove cli":
					gameController.removeObserver(cli);
					break;
				case "add gui":
					gameController.registerObserver(gui);
					break;
				case "remove gui":
					gameController.removeObserver(gui);
					break;
			}
		}
	}
}