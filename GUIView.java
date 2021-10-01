import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUIView extends View
{
	public static final int MAIN_MENU = 1,
							SINGLEPLAYER = 2,
							MULTIPLAYER = 3;
	private int state;
	private GameModeJPanel gamePanel;
	private JFrame activeFrame;
	

	public GUIView(Controller controller)
	{	
		super(controller);
		controller.registerObserver(this);
		/*try {
  			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
 		} catch(Exception e) {
  			e.printStackTrace(); 
		}*/
		setState(MAIN_MENU);
	}

	public void update(char[][] gameTable, int turn)
	{	
		if (gamePanel != null)
			gamePanel.update(gameTable,turn);
	}

	public void setState(int newState)
	{	
		if (activeFrame != null)
			activeFrame.dispose();
		switch (newState)
		{
			case MAIN_MENU:
				activeFrame = createMainMenuFrame();
				break;
			case SINGLEPLAYER:
				activeFrame  = createSinglePlayerFrame();
				break;
			case MULTIPLAYER:
				activeFrame = createMultiPlayerFrame();
				break;
			default:
				throw new IllegalArgumentException("State can only be 1, 2, or 3!");
		}
		activeFrame.setVisible(true);
		controller.startNewGame();
		state = newState;
	}

	private JFrame createMainMenuFrame()
	{	
		JFrame mainMenuFrame = new JFrame();
		mainMenuFrame.setTitle("Tic Tac Toe");
		mainMenuFrame.setSize(500, 639);
		mainMenuFrame.setResizable(false);
		mainMenuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainMenuFrame.setUndecorated(true);
		mainMenuFrame.getContentPane().add(new MainMenuJPanel(this),BorderLayout.CENTER);
		centerTheFrame(mainMenuFrame);
		return mainMenuFrame;
	}

	private JFrame createSinglePlayerFrame()
	{
		JFrame singlePlayerFrame = new JFrame();
		singlePlayerFrame.setTitle("Tic Tac Toe (Single Player Mode)");
		singlePlayerFrame.setSize(500, 800);
		singlePlayerFrame.setResizable(false);
		singlePlayerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gamePanel = new GameModeJPanel(GameModeJPanel.SINGLEPLAYER, controller);
		singlePlayerFrame.getContentPane().add(gamePanel,BorderLayout.PAGE_START);
		createMenuBar(singlePlayerFrame);
		centerTheFrame(singlePlayerFrame);
		return singlePlayerFrame;
	}

	private JFrame createMultiPlayerFrame()
	{
		JFrame multiplayerFrame = new JFrame();
		multiplayerFrame.setTitle("Tic Tac Toe (Multi Player Mode)");
		multiplayerFrame.setSize(500, 800);
		multiplayerFrame.setResizable(false);
		multiplayerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gamePanel = new GameModeJPanel(GameModeJPanel.MULTIPLAYER, controller);
		multiplayerFrame.getContentPane().add(gamePanel,BorderLayout.PAGE_START);
		createMenuBar(multiplayerFrame);
		centerTheFrame(multiplayerFrame);
		return multiplayerFrame;
	}

	private void createMenuBar(JFrame frame)
	{	
		//Game menu
		JMenuItem refreshItem = new JMenuItem("New game");
		refreshItem.addActionListener(event -> {controller.startNewGame();
			if (gamePanel.getSinglePlayerTurn() == 1)
				controller.playBestMove();
		});
		JMenuItem mainMenuItem = new JMenuItem("Back to main menu");
		mainMenuItem.addActionListener(event -> {setState(MAIN_MENU);});
		JMenuItem quitItem = new JMenuItem("Quit");
		quitItem.addActionListener(event -> {exit();});
		
		JMenu gameMenu = new JMenu("Game");
		gameMenu.add(refreshItem);
		gameMenu.add(mainMenuItem);
		gameMenu.add(quitItem);

		//Help Menu
		JMenuItem instructionsItem = new JMenuItem("Game instructions");
		instructionsItem.addActionListener(event -> {
			JOptionPane.showMessageDialog(activeFrame, "Welcome to my Tic Tac Toe simulator! The object of Tic Tac Toe is to get three in a row.\n" + 
														"You play on a three by three game board. The first player is known as X and the second is O.\n" + 
														"Players alternate placing Xs and Os on the game board until either oppent has three in a row or all nine squares are filled.\n" + 
														"X always goes first, and in the event that no one has three in a row, the stalemate is called a draw game.", "Instructions", 
				JOptionPane.INFORMATION_MESSAGE, 
				new ImageIcon("Images/icon.png", "tic tac toe"));
		});
		JMenuItem aboutMeItem = new JMenuItem("About me");
		aboutMeItem.addActionListener(event -> {
			JOptionPane.showMessageDialog(activeFrame, "My name is Davood Dehghani.\nI'm a technology enthusiast! My main interests are"
				+ " programming and networking.\nVisit my GitHub: https://github.com/ddehghani", "About Me", 
				JOptionPane.INFORMATION_MESSAGE, 
				new ImageIcon("Images/me.jpg", "tic tac toe"));
		});
		
		JMenu helpMenu = new JMenu("Help");
		helpMenu.add(instructionsItem);
		helpMenu.add(aboutMeItem);

		JMenuBar menuBar = new JMenuBar();
		menuBar.add(gameMenu);
		//menuBar.add(fileMenu);
		menuBar.add(helpMenu);
		frame.setJMenuBar(menuBar);
	}

	public void exit()
	{
		System.exit(0);
	}

	private void centerTheFrame(JFrame f)
	{
		Rectangle bounds = f.getGraphicsConfiguration().getBounds();
        Insets insets = Toolkit.getDefaultToolkit().getScreenInsets(f.getGraphicsConfiguration());
        bounds.x += insets.left;
        bounds.y += insets.top;
        bounds.width -= insets.left + insets.right;
        bounds.height -= insets.top + insets.bottom;
        int minY = bounds.y, 
        	maxY = bounds.y + bounds.height - f.getHeight(),
        	minX = bounds.x,
        	maxX = bounds.x + bounds.width - f.getWidth();
        f.setLocation((minX + maxX)/2,(minY + maxY)/2);
	}
}