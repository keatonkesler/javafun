package connect_four;

import java.awt.event.*;
import java.awt.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.*;

public class ConnectFour {
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.getContentPane().add(new keyboard());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		keyboard keys = new keyboard();
	}
}
class keyboard extends JPanel implements KeyListener {
	int room_sp = 30;
	long delay = 1000/room_sp;
	int[][] keys_pressed;
	int[] keys = {65,83,87,68,70,32,10,27,37,38,39,40};
	Animation animation = new Animation();
	Timer timer = new Timer((int)delay,animation);
	token[][] board = new token[7][6];
	token victor = token.NONE;
	token origin = token.NONE;
	int place_x = 0;
	int place_y = 0;
	int place = 0;
	int top_y = 0;
	int key = 0;
	Long start;
	boolean player_turn_1 = true;
	boolean player_won = false;
	boolean animating = false;
	BufferedImage player;
	BufferedImage p1_sprite;
	BufferedImage p2_sprite;
	public keyboard() {
		this.setPreferredSize(new Dimension(265,265));
		addKeyListener(this);
		try {
			p1_sprite = ImageIO.read(new File("red_piece.jpg"));
			p2_sprite = ImageIO.read(new File("yellow_piece.jpg"));
		}
		catch (Exception e) {
			System.out.println(e);
		}
		keys_pressed = new int[keys.length][2];
		for (int i=0;i<keys.length;i++) {
			keys_pressed[i][0] = keys[i];
			keys_pressed[i][1] = 0;
		}
		clear_board();
	}
	public enum token {
		RED,
		YELLOW,
		NONE;
	}
	public void addNotify() {
		super.addNotify();
		requestFocus();
	}
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g.clearRect(0, 0, getWidth(),  getHeight());
		if (!player_won) {
			player = p2_sprite;
			if (player_turn_1) {
				player = p1_sprite;
			}
			place = (place_x*35) + 10;
			g2.drawImage(player, place, place_y+5, 35,35,this);
		}
		else {
			g.drawString(victor.name() + " Victory", 130, 20);
		}
		for (int i=0;i<board.length;i++) {
			for (int a=0;a<board[i].length;a++) {
				if (board[i][a] != token.NONE) {
					if (board[i][a] == token.RED) {
						player = p1_sprite;
					}
					else if (board[i][a] == token.YELLOW) {
						player = p2_sprite;
					}
					if (!animating || ((place_x != i) || (top_y != a))) {
						g2.drawImage(player, 10+(i*35), 45+(a*35), 35, 35, this);
					}
				}
			}
		}
		g.setColor(Color.blue);
		for (int i=0,a=10,b=45;i<7;i++,a+=35) {
			g.drawRect(a, b, 35, 35*6);
		}
		for (int i=0,a=10,b=45;i<6;i++,b+=35) {
			g.drawRect(a, b, 35*7, 35);
		}
	}
	private class Animation implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (animating) {
				place_y += 3;
				if (place_y > (top_y*35) + 40) {
					place_y = 0;
					animating = false;
					if (player_victory()) {
						player_won = true;
						if (player_turn_1) {
							victor = token.RED;
						}
						else {
							victor = token.YELLOW;
						}
					}
					else if (board_filled()) {
						player_won = true;
						victor = token.NONE;
					}
					else {
						if (player_turn_1) {
							player_turn_1 = false;
						}
						else {
							player_turn_1 = true;
						}
					}
				}
				repaint();
				timer.start();
			}
		}
	}
	public void keyPressed(KeyEvent e) {
		if (!animating) {
			key = e.getKeyCode();
			if (!player_won) {
				if (key_fresh(key,37)) {
					place_x--;
					if (place_x < 0) {
						place_x = 0;
					}
				}
				else if (key_fresh(key,39)) {
					place_x++;
					if (place_x > 6) {
						place_x = 6;
					}
				}
				else if (key_fresh(key,32)) {
					if (place_token(player_turn_1)) {
						animating = true;
						timer.start();
					}
				}
			}
			else {
				if (key_fresh(key,32)) {
					restart_game();
				}
			}
		}
		if (key_fresh(key,27)) {
			restart_game();
		}
		repaint();
	}
	public void keyReleased(KeyEvent e) {
		key = e.getKeyCode();
		for (int i=0;i<keys.length;i++) {
			if (key == keys_pressed[i][0]) {
				keys_pressed[i][1] = 0;
			}
		}
	}
	public void keyTyped(KeyEvent e) {}
	public boolean key_fresh(int key, int num) {
		if (key == num) {
			for (int i=0;i<keys.length;i++) {
				if (key == keys_pressed[i][0]) {
					if (keys_pressed[i][1] == 0) {
						keys_pressed[i][1] = 1;
						return true;
					}
					else {
						return false;
					}
				}
			}
		}
		return false;
	}
	public void clear_board() {
		for (int i=0;i<board.length;i++) {
			for (int a=0;a<board[i].length;a++) {
				board[i][a] = token.NONE;
			}
		}
	}
	public void restart_game() {
		player_won = false;
		victor = token.NONE;
		player_turn_1 = true;
		place_x = 0;
		place_y = 0;
		animating = false;
		clear_board();
	}
	public boolean place_token(boolean turn) {
		for (int i=board[0].length-1;i>=0;i--) {
			if (board[place_x][i] == token.NONE) {
				top_y = i;
				if (turn) {
					board[place_x][i] = token.RED;
				}
				else {
					board[place_x][i] = token.YELLOW;
				}
				return true;
			}
		}
		return false;
	}
	public boolean player_victory() {
		for (int i=0;i<board.length;i++) {
			for (int a=0;a<board[i].length-3;a++) {
				if (board[i][a] != token.NONE) {
					origin = board[i][a];
					if ((board[i][a+3] == origin)&&(board[i][a+2] == origin)&&(board[i][a+1] == origin)) {
						return true;
					}
				}
			}
		}
		for (int i=0;i<board.length-3;i++) {
			for (int a=0;a<board[i].length;a++) {
				if (board[i][a] != token.NONE) {
					origin = board[i][a];
					if ((board[i+3][a] == origin)&&(board[i+2][a] == origin)&&(board[i+1][a] == origin)) {
						return true;
					}
				}
			}
		}
		for (int i=0;i<board.length-3;i++) {
			for (int a=3;a<board[i].length;a++) {
				if (board[i][a] != token.NONE) {
					origin = board[i][a];
					if ((board[i+3][a-3] == origin)&&(board[i+2][a-2] == origin)&&(board[i+1][a-1] == origin)) {
						return true;
					}
				}
			}
		}
		for (int i=0;i<board.length-3;i++) {
			for (int a=0;a<board[i].length-3;a++) {
				if (board[i][a] != token.NONE) {
					origin = board[i][a];
					if ((board[i+3][a+3] == origin)&&(board[i+2][a+2] == origin)&&(board[i+1][a+1] == origin)) {
						return true;
					}
				}
			}
		}
		return false;
	}
	public boolean board_filled() {
		for (int i=0;i<board.length;i++) {
			for (int a=0;a<board[i].length;a++) {
				if (board[i][a] == token.NONE) {
					return false;
				}
			}
		}
		return true;
	}
}