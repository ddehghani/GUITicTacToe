import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.imageio.ImageIO;

public class GameModeJPanel extends JPanel 
{
	private class Cell extends JPanel
	{
		private JLabel label;
		private int row, col;
		private boolean highlighted;

		
		public Cell(int row, int col)
		{
			setOpaque(false);
			addMouseListener(new MouseListener()
			{
				public void mousePressed(MouseEvent e) {
											if (highlighted)
											{
												highlighted = false;
												label.setForeground(Color.WHITE);
												controller.move(row,col);
												if (gameRunning && mode == SINGLEPLAYER)
													controller.playBestMove();
											}}
				public void mouseEntered(MouseEvent e) {
										if (gameRunning && label.getText().equals(" ")){
											label.setText(turn == 0 ? "X" : "O");
											label.setForeground(new Color(0xbfbfbf));
											highlighted = true;
										}}
				public void mouseExited(MouseEvent e) {
										if (highlighted){
										label.setText(" ");
										label.setForeground(Color.WHITE);
										highlighted = false;}}
				public void mouseClicked(MouseEvent e) {}
				public void mouseReleased(MouseEvent e) {}
			});
			setPreferredSize(new Dimension(150,150));
			label = new JLabel();
			label.setAlignmentX(Component.CENTER_ALIGNMENT);
			label.setAlignmentY(Component.CENTER_ALIGNMENT);
			label.setForeground(Color.WHITE);
			try {
				Font eraserDust = Font.createFont(Font.TRUETYPE_FONT, new File("Fonts/EraserDust.ttf"));
  				GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(eraserDust);
  				label.setFont(eraserDust.deriveFont(90f));
			} catch (IOException|FontFormatException e) { e.printStackTrace();}

			setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
			add(Box.createRigidArea(new Dimension(0,25)));
			add(label);
		}

		public void setValue(char value)
		{
			label.setText(value + "");
		}
	}

	public static final int SINGLEPLAYER = 1,
							MULTIPLAYER = 2;
	private Image backgroundImage;
	private JLabel multiTurnLabel;
	private Cell[][] cells;
	private Controller controller;
	private int turn, mode, singlePlayerTurn;
	private boolean gameRunning;

	public GameModeJPanel(int mode, Controller controller)
	{	
		this.controller = controller;
		this.mode = mode;
		singlePlayerTurn = 0;
  		try {
  			backgroundImage = ImageIO.read(new File("Images/GameTable.jpg"));
  		} catch (IOException e) {e.printStackTrace();}
    	setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
    	if (mode == SINGLEPLAYER)
			addSinglePlayerHeader();
		else if (mode == MULTIPLAYER)
			addMultiPlayerHeader();
		else
			throw new IllegalArgumentException("Mode can only be 1 or 2");
		addTableAndFooter();
	}

	public int getSinglePlayerTurn()
	{
		return singlePlayerTurn;
	}

	public void update(char[][] gameTable, int turn)
	{	
		for (int row=0; row<3; row++)
				for (int column=0; column<3; column++)
					cells[row][column].setValue(gameTable[row][column]);
		this.turn = turn;	
		
		int evaluatation = controller.evaluateResult();
		gameRunning = false;
		if (evaluatation == Controller.UNDETEREMINED)
		{
			if (multiTurnLabel != null) //it's a multiplayer game
				multiTurnLabel.setText("Player " + (turn + 1) + ", it's your turn!");
			gameRunning = true;
		}
		else 
		{
			ImageIcon icon = new ImageIcon("Images/icon.png", "tic tac toe");
			if (evaluatation == Controller.PLAYER_ONE_WON || evaluatation == Controller.PLAYER_TWO_WON)
				if (multiTurnLabel != null)
				{
					multiTurnLabel.setText("PLAYER " + evaluatation + " IS THE WINNER!");
					JOptionPane.showMessageDialog(this, "PLAYER " + evaluatation + " IS THE WINNER!", "Game Result", JOptionPane.INFORMATION_MESSAGE, icon);
				}
				else
					JOptionPane.showMessageDialog(this, "You " + (singlePlayerTurn == turn? "LOST! Try harder next time!" : "WON! Well done!" ), "Game Result", JOptionPane.INFORMATION_MESSAGE, icon);
			else //draw
				if (multiTurnLabel != null)
				{
					multiTurnLabel.setText("DRAW!");
					JOptionPane.showMessageDialog(this, "DRAW!", "Game Result", JOptionPane.INFORMATION_MESSAGE, icon);
				}
				else
					JOptionPane.showMessageDialog(this, "DRAW!", "Game Result", JOptionPane.INFORMATION_MESSAGE, icon);
		}
	}	

	private void addSinglePlayerHeader()
	{	
		//creating turn selection radio buttons
		JRadioButton first = new JRadioButton("Play First");
		first.setFont(new Font("Courier", Font.PLAIN, 18));
		first.setForeground(new Color(0xffd700));
    	first.setSelected(true);
    	first.addActionListener(event -> {
    		singlePlayerTurn = 0;
    		controller.startNewGame();
    	});

		JRadioButton second = new JRadioButton("Play Second");
		second.setFont(new Font("Courier", Font.PLAIN, 18));
		second.setForeground(new Color(0xffd700));
		second.addActionListener(event -> {
    		singlePlayerTurn = 1;
    		controller.startNewGame();
    		controller.playBestMove();
    	});

    	ButtonGroup group = new ButtonGroup();
    	group.add(first);
    	group.add(second);

    	JPanel turnPanel = new JPanel();
    	turnPanel.add(first);
    	turnPanel.add(new JLabel("    "));
    	turnPanel.add(second);
    	turnPanel.setOpaque(false);

    	//creating turn label
    	JLabel turnLabel = new JLabel("Choose your turn:");
    	turnLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		turnLabel.setFont(new Font("Courier", Font.PLAIN, 24));
		turnLabel.setForeground(new Color(0xe60000));

		add(Box.createRigidArea(new Dimension(0,45)));
		add(turnLabel);
		add(Box.createRigidArea(new Dimension(0,10)));
		add(turnPanel);
		add(Box.createRigidArea(new Dimension(0,80)));
	}

	private void addMultiPlayerHeader()
	{	
		multiTurnLabel = new JLabel("Player 1, it's your turn!");
    	multiTurnLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		multiTurnLabel.setFont(new Font("Courier", Font.PLAIN, 24));
		multiTurnLabel.setForeground(new Color(0xe60000));

		add(Box.createRigidArea(new Dimension(0,70)));
		add(multiTurnLabel);
		add(Box.createRigidArea(new Dimension(0,95)));
	}

	private void addTableAndFooter()
	{
		JPanel table = new JPanel();
		table.setOpaque(false);
		table.setLayout(new GridLayout(3,3,10,15));
		cells = new Cell[3][3];
		for (int row=0; row<3; row++)
			for (int col=0; col<3; col++)
			{
				cells[row][col] = new Cell(row,col);
				table.add(cells[row][col]);
			}

		JButton restart = new JButton("Start Over!");
		restart.addActionListener(event->{ controller.startNewGame();});
    	restart.setAlignmentX(Component.CENTER_ALIGNMENT);
		restart.setFont(new Font("Courier", Font.PLAIN, 18));
		restart.setForeground(new Color(0xe60000));

		add(table);
		add(Box.createRigidArea(new Dimension(0,95)));
	}

	public void paintComponent(Graphics g) {
    	super.paintComponent(g);
    	g.drawImage(backgroundImage, 0, 0, this);
  	}
}