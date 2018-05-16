//cs.boisestate.edu/~cs121/projects/p5/
//ftp://cs2/csa/MineWalk/

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


public class MineWalker {
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.getContentPane().add(new MineWalkerPanel());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		frame.setTitle("Game");
		MineWalkerPanel game = new MineWalkerPanel();
	}
}
