import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.imageio.ImageIO;

public class MainMenuJPanel extends JPanel 
{
	private class MenuLabel extends JLabel
	{
		public MenuLabel(String label)
		{
			super(label);
			setAlignmentX(Component.CENTER_ALIGNMENT);
			setFont(new Font("Courier", Font.PLAIN, 24));
			setForeground(new Color(0xe60000));
		}
	}
	private Image backgroundImage;
	private GUIView view;

  	public MainMenuJPanel(GUIView v){
  		this.view = v;
  		try {
    		backgroundImage = ImageIO.read(new File("Images/MainMenu.jpg"));
    	}
    	catch (IOException e)
		{
			System.out.println("Image you not be found!");
		}
    	setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
    	MenuLabel singlePlayer = new MenuLabel("Play Against The Computer");
    	MenuLabel multiPlayer = new MenuLabel("Play With A Friend");
    	MenuLabel exit = new MenuLabel("Exit The Game");
    	singlePlayer.addMouseListener(new MouseListener()
		{
			public void mousePressed(MouseEvent e) {view.setState(GUIView.SINGLEPLAYER);}
			public void mouseEntered(MouseEvent e) {singlePlayer.setForeground(new Color(0xffd700));}
			public void mouseExited(MouseEvent e) {singlePlayer.setForeground(new Color(0xe60000));}
			public void mouseClicked(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}
		});
		multiPlayer.addMouseListener(new MouseListener()
		{
			public void mousePressed(MouseEvent e) {view.setState(GUIView.MULTIPLAYER);}
			public void mouseEntered(MouseEvent e) {multiPlayer.setForeground(new Color(0xffd700));}
			public void mouseExited(MouseEvent e) {multiPlayer.setForeground(new Color(0xe60000));}
			public void mouseClicked(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}
		});
		exit.addMouseListener(new MouseListener()
		{
			public void mousePressed(MouseEvent e) {view.exit();}
			public void mouseEntered(MouseEvent e) {exit.setForeground(new Color(0xffd700));}
			public void mouseExited(MouseEvent e) {exit.setForeground(new Color(0xe60000));}
			public void mouseClicked(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}
		});
    	add(Box.createRigidArea(new Dimension(0,440)));
		add(singlePlayer);
		add(Box.createRigidArea(new Dimension(0,40)));
		add(multiPlayer);
		add(Box.createRigidArea(new Dimension(0,40)));
		add(exit);
  	}

  	public void paintComponent(Graphics g) {
    	super.paintComponent(g);
    	g.drawImage(backgroundImage, 0, 0, this);
  	}
}