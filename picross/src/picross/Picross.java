package picross;

import java.awt.event.*;
import java.awt.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.concurrent.ThreadLocalRandom;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.*;

public class Picross {
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.getContentPane().add(new keyboard());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		frame.setTitle("Game");
		keyboard keys = new keyboard();
	}
}
class keyboard extends JPanel implements KeyListener,MouseListener {
	int room_sp = 30;
	long delay = 1000/room_sp;
	int[][] keys_pressed;
	int[] keys = {65,83,87,68,70,32,10,27,37,38,39,40};
	Animation animation = new Animation();
	Timer timer = new Timer((int)delay,animation);
	int key = 0;
	int mouse_x;
	int mouse_y;
	int temp_1;
	int temp_2;
	Long start;
	boolean animating = false;
	
	int width;
	int height;
	boolean[][] map;
	boolean[][] board;
	int[] hint_x;
	int[] hint_y;
	boolean chain = false;
	int counter = 0;
	int hint_index = 0;
	win won = win.none;
	String won_line = "";
	
	//BufferedImage p1_sprite;
	
	public keyboard() {
		width = ThreadLocalRandom.current().nextInt(8,13);
		height = ThreadLocalRandom.current().nextInt(8,13);
		this.setPreferredSize(new Dimension((width*32)+5+(6*16),(height*32)+5+(6*16)));
		addKeyListener(this);
		addMouseListener(this);
		keys_pressed = new int[keys.length][2];
		for (int i=0;i<keys.length;i++) {
			keys_pressed[i][0] = keys[i];
			keys_pressed[i][1] = 0;
		}
		map = new boolean[width][height];
		board = new boolean[width][height];
		hint_x = new int[(int)width*height/2];
		hint_y = new int[hint_x.length];
		for (int i=0;i<hint_y.length;i++) {
			hint_x[i] = 0;
			hint_y[i] = 0;
		}
		for (int a=0;a<map.length;a++) {
			for (int b=0;b<map[a].length;b++) {
				board[a][b] = false;
				if (ThreadLocalRandom.current().nextInt(1,101) < 51) {
					map[a][b] = true;
					chain = true;
					counter++;
					if (b == map[a].length-1) {
						chain = false;
						hint_y[hint_index] = counter;
						counter = 0;
						hint_index++;
					}
				}
				else {
					map[a][b] = false;
					if (chain) {
						chain = false;
						hint_y[hint_index] = counter;
						counter = 0;
						hint_index++;
					}
				}
				if (b == map[a].length-1) {
					hint_y[hint_index] = -1;
					hint_index++;
				}
			}
		}
		chain = false;
		counter = 0;
		hint_index = 0;
		for (int b=0;b<map[0].length;b++) {
			for (int a=0;a<map.length;a++) {
				if (map[a][b]) {
					chain = true;
					counter++;
					if (a == map.length-1) {
						chain = false;
						hint_x[hint_index] = counter;
						counter = 0;
						hint_index++;
					}
				}
				else if (chain) {
					chain = false;
					hint_x[hint_index] = counter;
					counter = 0;
					hint_index++;
				}
				if (a == map.length-1) {
					hint_x[hint_index] = -1;
					hint_index++;
				}
			}
		}
	}
	public void addNotify() {
		super.addNotify();
		requestFocus();
	}
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g.clearRect(0, 0, getWidth(),  getHeight());
		for (int a=0;a<board.length;a++) {
			for (int b=0;b<board[a].length;b++) {
				if (board[a][b]) {
					g.setColor(Color.blue);
					/*if (map[a][b]) { 		//debug
						g.setColor(Color.magenta);
					}*/
				}
				/*else if (map[a][b]) { 	//debug
					g.setColor(Color.red);
				}*/
				else {
					g.setColor(Color.white);
				}
				g.fillRect((a*32)+5, (b*32)+5, 32, 32);
				g.setColor(Color.black);
				g.drawRect((a*32)+5, (b*32)+5, 32, 32);
			}
		}
		for (int i=0,a=0,b=0;i<hint_y.length;i++) {
			if (hint_y[i] > 0) {
				g.drawString(Integer.toString(hint_y[i]), 5+10+(a*32), 10+16+(board[0].length*32)+(b*16));
				b++;
			}
			else if (hint_y[i] == -1) {
				a++;
				b=0;
			}
		}
		for (int i=0,a=0,b=0;i<hint_x.length;i++) {
			if (hint_x[i] > 0) {
				g.drawString(Integer.toString(hint_x[i]), 10+(board.length*32)+(a*16), 5+16+5+(b*32));
				a++;
			}
			else if (hint_x[i] == -1) {
				b++;
				a=0;
			}
		}
		if (won == win.natural) {
			won_line = "You Won!!!";
		}
		else if (won == win.alternate) {
			won_line = "Alternate Solution Found";
		}
		else {
			won_line = "";
		}
		g.drawString(won_line, 10+(board.length*32), 20+(board[0].length*32));
	}
	private class Animation implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (animating) {
				//code
				if (true) {
					animating = false;
					//code
				}
				repaint();
				timer.start();
			}
		}
	}
	public void keyPressed(KeyEvent e) {
		key = e.getKeyCode();
		if (!animating) {
			if (key_fresh(key,32)) {
				//code
			}
		}
		if (key_fresh(key,27)) {
			//code
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
	enum win {
		natural,
		alternate,
		none;
	}
	public void mouseClicked(MouseEvent e) {
		mouse_x = e.getX();
		mouse_y = e.getY();
		if ((mouse_x > 5)&&(mouse_x<(board.length*32)+5)&&(mouse_y>5)&&(mouse_y<(board[0].length*32)+5)) {
			temp_1 = (int)((mouse_x-5)/32);
			temp_2 = (int)((mouse_y-5)/32);
			if ((temp_1 >= 0)&&(temp_1<board.length)&&(temp_2 >= 0)&&(temp_2<board[0].length)) {
				if (board[temp_1][temp_2]) {
					board[temp_1][temp_2] = false;
				}
				else {
					board[temp_1][temp_2] = true;
				}
				if (check_victory()) {
					won = win.natural;
				}
				else if (check_alternate_victory()) {
					won = win.alternate;
				}
				else {
					won = win.none;
				}
				repaint();
			}
		}
		//code
	}
	public void mouseEntered(MouseEvent e) {
		
	}
	public void mouseExited(MouseEvent e) {
		
	}
	public void mousePressed(MouseEvent e) {
		
	}
	public void mouseReleased(MouseEvent e) {
		
	}
	public boolean check_victory() {
		for (int a=0;a<board.length;a++) {
			for (int b=0;b<board.length;b++) {
				if (board[a][b] != map[a][b]) {
					return false;
				}
			}
		}
		return true;
	}
	public boolean check_alternate_victory() {
		
		return false;
	}
}