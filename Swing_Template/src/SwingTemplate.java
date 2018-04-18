//package

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

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.*;

public class SwingTemplate {
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
	Long start;
	boolean animating = false;
	//BufferedImage p1_sprite;
	public keyboard() {
		this.setPreferredSize(new Dimension(265,265));
		addKeyListener(this);
		addMouseListener(this);
		try {
			//p1_sprite = ImageIO.read(new File("red_piece.jpg"));
		}
		catch (Exception e) {
			System.out.println(e);
		}
		keys_pressed = new int[keys.length][2];
		for (int i=0;i<keys.length;i++) {
			keys_pressed[i][0] = keys[i];
			keys_pressed[i][1] = 0;
		}
	}
	public void addNotify() {
		super.addNotify();
		requestFocus();
	}
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g.clearRect(0, 0, getWidth(),  getHeight());
		//code
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
	//MouseEvent.getX() MouseEvent.getY()
		public void mouseClicked(MouseEvent e) {
			mouse_x = e.getX();
			mouse_y = e.getY();
			System.out.println(mouse_x + " " + mouse_y +" ");
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
}