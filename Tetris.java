import java.applet.AudioClip;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Random;

import javax.swing.AbstractAction;
import javax.swing.JApplet;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.Timer;
@SuppressWarnings("serial")

public class Tetris extends JApplet {
	
	static PieceContainer pc = new PieceContainer();
	AudioClip denied;
	static AudioClip tetrisA;
	static AudioClip tetrisB;
	static AudioClip tetoroika;
	Board gameBoard = new Board();
	Container content = getContentPane();
	static InfoPanel iPanel = new InfoPanel();
	static Boolean playing = false;
	static Boolean pieceFalling = false;
	static int userScore = 0;
	static Boolean musicPlaying = true;
	static int song = 1;
	static Timer move;
	static ActionListener down;
	static int speed = 800;
	
	public void init() {
		
		denied = getAudioClip(getCodeBase(),"denied.wav");
		tetrisA = getAudioClip(getCodeBase(), "TetrisA.mid");
		tetrisB = getAudioClip(getCodeBase(), "TetrisB.mid");
		tetoroika = getAudioClip(getCodeBase(), "Tetoroika.mid");
		
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(600,600));
		setSize(600,600);
		JLayeredPane layers = getLayeredPane();
		layers.setLayout(null);
		layers.add(gameBoard);
		layers.setLayer(gameBoard, 1);
		layers.add(pc);
		layers.setLayer(pc, 2);
		layers.add(iPanel);
		layers.setLayer(iPanel, 3);
		layers.setVisible(true);
		
	}	
	
	public void start() {
		
		AbstractAction MoveLeft = new AbstractAction() {

			public void actionPerformed(ActionEvent e) {
				pc.moveBlockLeft();
				
			}
			
		};
		AbstractAction MoveRight = new AbstractAction() {

			public void actionPerformed(ActionEvent e) {
				pc.moveBlockRight();
				
			}
			
		};
		AbstractAction RotateShape = new AbstractAction() {

			public void actionPerformed(ActionEvent e) {
				if (!pc.rotateBlock()) {
					denied.play();
				}
				
				
			}
			
		};
	
		AbstractAction Down = new AbstractAction() {

			public void actionPerformed(ActionEvent e) {
				pc.moveBlockDown();
				
				
			}
			
		};
		
		AbstractAction Drop = new AbstractAction() {

			public void actionPerformed(ActionEvent e) {
				pc.dropBlock();
				
			}
			
		};

		AbstractAction beginOrPause = new AbstractAction() {

			public void actionPerformed(ActionEvent e) {
				beginOrPause();
				
			}
			
		};
		
		down = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (PieceContainer.currentBlock.pieceType == pieceTypes.None) {
					nextPiece();
				}
				pieceFalling = true;
				pc.moveBlockDown();
				if (pieceFalling == false) {
					nextPiece();
				}
				
			}
		};
		
		AbstractAction changeMusic = new AbstractAction() {

			public void actionPerformed(ActionEvent e) {
				changeMusic();
				
			}
			
		};

		
		pc.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "MoveLeft");
		pc.getActionMap().put("MoveLeft", MoveLeft);
		pc.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "MoveRight");
		pc.getActionMap().put("MoveRight", MoveRight);
		pc.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "Rotate");
		pc.getActionMap().put("Rotate", RotateShape);
		pc.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), "Drop");
		pc.getActionMap().put("Drop", Drop);
		pc.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "Down");
		pc.getActionMap().put("Down", Down);
		pc.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "beginOrPause");
		pc.getActionMap().put("beginOrPause", beginOrPause);
		pc.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, 0), "changeMusic");
		pc.getActionMap().put("changeMusic", changeMusic);
		
		move = new Timer(speed,down);
		
		if (musicPlaying) {
			tetrisA.loop();
		}
		
	}
	
	
	public void nextPiece() {
	
		if (InfoPanel.preview.display == pieceTypes.None) {
			Random rand = new Random();
			int randomBlock = rand.nextInt(71);
			if (randomBlock <= 10) {
				PieceContainer.currentBlock = new Pieces(pieceTypes.Line, new Point(125,0), 1);
			}
			if (randomBlock > 10 && randomBlock <= 20) {
				PieceContainer.currentBlock = new Pieces(pieceTypes.Square, new Point(125,0), 1);
			}
			if (randomBlock > 20 && randomBlock <= 30) {
				PieceContainer.currentBlock = new Pieces(pieceTypes.L, new Point(125,0), 1);
			}
			if (randomBlock > 30 && randomBlock <= 40) {
				PieceContainer.currentBlock = new Pieces(pieceTypes.RL, new Point(125,0), 1);
			}
			if (randomBlock > 40 && randomBlock <= 50) {
				PieceContainer.currentBlock = new Pieces(pieceTypes.S, new Point(125,0), 1);
			}
			if (randomBlock > 50 && randomBlock <= 60) {
				PieceContainer.currentBlock = new Pieces(pieceTypes.Z, new Point(125,0), 1);
			}
			if (randomBlock > 60 && randomBlock <= 70) {
				PieceContainer.currentBlock = new Pieces(pieceTypes.T, new Point(125,0), 1);
			}
			randomBlock = rand.nextInt(71);
			if (randomBlock <= 10) {
				InfoPanel.preview.display = pieceTypes.Line;
			}
			if (randomBlock > 10 && randomBlock <= 20) {
				InfoPanel.preview.display = pieceTypes.Square;
			}
			if (randomBlock > 20 && randomBlock <= 30) {
				InfoPanel.preview.display = pieceTypes.L;
			}
			if (randomBlock > 30 && randomBlock <= 40) {
				InfoPanel.preview.display = pieceTypes.RL;
			}
			if (randomBlock > 40 && randomBlock <= 50) {
				InfoPanel.preview.display = pieceTypes.S;
			}
			if (randomBlock > 50 && randomBlock <= 60) {
				InfoPanel.preview.display = pieceTypes.Z;
			}
			if (randomBlock > 60 && randomBlock <= 70) {
				InfoPanel.preview.display = pieceTypes.T;
			}
			pieceFalling = true;
			pc.repaint();
			InfoPanel.preview.repaint();
			return;
		} else {
			Random rand = new Random();
			int randomBlock = rand.nextInt(71);
			PieceContainer.currentBlock = new Pieces(InfoPanel.preview.display, new Point(125,0), 1);
			randomBlock = rand.nextInt(71);
			if (randomBlock <= 10) {
				InfoPanel.preview.display = pieceTypes.Line;
			}
			if (randomBlock > 10 && randomBlock <= 20) {
				InfoPanel.preview.display = pieceTypes.Square;
			}
			if (randomBlock > 20 && randomBlock <= 30) {
				InfoPanel.preview.display = pieceTypes.L;
			}
			if (randomBlock > 30 && randomBlock <= 40) {
				InfoPanel.preview.display = pieceTypes.RL;
			}
			if (randomBlock > 40 && randomBlock <= 50) {
				InfoPanel.preview.display = pieceTypes.S;
			}
			if (randomBlock > 50 && randomBlock <= 60) {
				InfoPanel.preview.display = pieceTypes.Z;
			}
			if (randomBlock > 60 && randomBlock <= 70) {
				InfoPanel.preview.display = pieceTypes.T;
			}
			pieceFalling = true;
			InfoPanel.preview.repaint();
			pc.repaint();
		}
	}
	
	public void beginOrPause() {
		if (!playing) {
		move.start(); //600 faster speed = acceptable
		playing = true;
		} else {
			move.stop();
			playing = false;
		}
	}
	
	public static void changeMusic() {
		
		if (musicPlaying) {
			if (song == 1) {
				song++;
				tetrisA.stop();
				tetrisB.loop();
				return;
			}
			if (song == 2) {
				song++;
				tetrisB.stop();
				tetoroika.loop();
				return;
			}
			if (song == 3) {
				song++;
				tetoroika.stop();
				return;
			}
			if (song == 4) {
				song = 1;
				tetrisA.loop();
				return;
			}
		} 
	}
	
	public static void gameOver() {
		
		playing = false;
		move.stop();
		JOptionPane.showMessageDialog(null, "Game over! Your ending score: " + userScore + ", with " + PieceContainer.linesCleared + " lines cleared!");
		System.exit(0);
	}
	
}
