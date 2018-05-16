import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;
import javax.swing.Timer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

class MineWalkerPanel extends JPanel implements KeyListener,MouseListener {
	int room_sp = 2;
	long delay = 1000/room_sp;
	int room = 0;
	int[][] keys_pressed;
	int[] keys;
	Animation animation = new Animation();
	Timer timer = new Timer((int)delay,animation);
	int key = 0;
	int mouse_x;
	int mouse_y;
	Long start;
	boolean animating = false;
	//BufferedImage p1_sprite;
	
	Random random = new Random();
	
	int board_x = 300;
	int board_y = 0;
	int board_length = 600;
	int board_size = 6;
	int min_board_size = 3;
	int max_board_size = 15;
	int square_size = (int)board_length/board_size;
	int difficulty = 3;
	int diff_unlocked = 3;
	int max_difficulty = 5;
	int max_lives = 3;
	int lives = max_lives;
	boolean show_mines = false;
	boolean show_path = false;
	type[][] board;
	int[][] mine_count;
	boolean[][] revealed;
	MineFieldButton[] buttons;
	
	RandomWalk walk;
	ArrayList<Point> path;
	HashMap<type,Color> paint_color = new HashMap<type,Color>();
	HashMap<Integer,Color> number_color = new HashMap<Integer,Color>();
	
	public MineWalkerPanel() {
		this.setPreferredSize(new Dimension(board_x+board_length,board_y+board_length));
		addKeyListener(this);
		addMouseListener(this);
		try {
			//p1_sprite = ImageIO.read(new File("red_piece.jpg"));
		}
		catch (Exception e) {
			System.out.println(e);
		}
		//(SP)(ENTER)(ESC)(BACK)(SHIFT)(CTRL)(Le_Up_Ri_Do)
		String temp_keys = "32,10,27,8,16,17,37,38,39,40";
		//add 0-9
		for (int i=0;i<=9;i++) {
			temp_keys += "," + (i+48);
		}
		//adds a-z
		for (int i=0;i<=25;i++) {
			temp_keys += "," + (i+65);
		}
		String[] temp_keys_two = temp_keys.split(",");
		keys = new int[temp_keys_two.length];
		for (int i=0;i<temp_keys_two.length;i++) {
			keys[i] = Integer.parseInt(temp_keys_two[i]);
		}
		keys_pressed = new int[keys.length][2];
		for (int i=0;i<keys.length;i++) {
			keys_pressed[i][0] = keys[i];
			keys_pressed[i][1] = 0;
		}
		
		paint_color.put(type.covered, Color.GRAY);
		paint_color.put(type.empty,Color.WHITE);
		paint_color.put(type.path,Color.BLUE);
		paint_color.put(type.bomb, Color.RED);
		paint_color.put(type.star, Color.YELLOW);
		paint_color.put(type.start, Color.GREEN);
		paint_color.put(type.finish, Color.MAGENTA);
		
		number_color.put(0, Color.GREEN);
		number_color.put(1, Color.ORANGE);
		number_color.put(2, Color.RED);
		number_color.put(3, Color.BLACK);
		number_color.put(4, Color.BLACK);
		
		buttons = new MineFieldButton[9];
		buttons[0] = new buttonGridSize(Integer.toString(board_size),"b_size",
				150,335,100,50,Color.BLACK,Color.WHITE,Color.BLUE,board_size,min_board_size,max_board_size);
		buttons[1] = new buttonDifficulty(Integer.toString(difficulty),"b_diff",
				150,400,100,50,Color.BLACK,Color.WHITE,Color.BLUE,difficulty,max_difficulty,diff_unlocked,max_lives);
		buttons[2] = new buttonRestart("Reset","b_reset",40,335,100,150,Color.BLACK,Color.GRAY,Color.DARK_GRAY);
		buttons[3] = new buttonShowMines("Show Mines","b_show_mines",40,500,220,40,Color.BLACK,Color.LIGHT_GRAY,Color.ORANGE);
		buttons[4] = new buttonShowPath("Show Path","b_show_path",40,550,220,40,Color.BLACK,Color.LIGHT_GRAY,Color.ORANGE);
		buttons[5] = new buttonLabel("Bomb","b_lab_bomb",40,175,220,70,Color.BLACK,paint_color.get(type.bomb),paint_color.get(type.bomb));
		buttons[6] = new buttonLabel("Star","b_lab_star",40,250,220,70,Color.BLACK,paint_color.get(type.star),paint_color.get(type.star));
		buttons[7] = new buttonLabel("Start","b_lab_start",40,25,220,70,Color.BLACK,paint_color.get(type.start),paint_color.get(type.start));
		buttons[8] = new buttonLabel("Finish","b_lab_finish",40,100,220,70,Color.BLACK,paint_color.get(type.finish),paint_color.get(type.finish));
		
		fill_grid(difficulty,board_size);
	}
	public void addNotify() {
		super.addNotify();
		requestFocus();
	}
	enum type {
		covered,
		empty,
		path,
		bomb,
		star,
		start,
		finish
	}
	
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g.clearRect(0, 0, getWidth(),  getHeight());
		for (int a=0;a<board.length;a++) {
			for (int b=0;b<board.length;b++) {
				if (revealed[a][b]) {
					if ((board[a][b] != type.path)||(show_path)) {
						g.setColor(paint_color.get(board[a][b]));
					}
					else {
						g.setColor(paint_color.get(type.empty));
					}
				}
				else if ((board[a][b] == type.start)||(board[a][b] == type.finish)) {
					g.setColor(paint_color.get(board[a][b]));
				}
				else {
					if (((board[a][b] == type.bomb)||(board[a][b] == type.star))&&(show_mines)) {
						g.setColor(paint_color.get(board[a][b]));
					}
					else if ((board[a][b] == type.path)&&(show_path)) {
						g.setColor(paint_color.get(board[a][b]));
					}
					else {
						g.setColor(paint_color.get(type.covered));
					}
				}
				g.fillRect(board_x+(square_size*a),board_y+square_size*b,square_size,square_size);
				if ((revealed[a][b])&&((board[a][b] == type.empty)||(board[a][b] == type.path))) {
					drawStringCentered(g,number_color.get(mine_count[a][b]),1+((int)square_size/3),Integer.toString(mine_count[a][b]),board_x+(square_size*a),board_y+square_size*b,square_size,square_size);
				}
			}
		}
		g.setColor(Color.BLACK);
		for (int i=0;i<board.length;i++) {
			g.drawLine(board_x+i*square_size, board_y, board_x+i*square_size, board_y+board_length);
		}
		for (int i=0;i<board[0].length;i++) {
			g.drawLine(board_x, board_y+i*square_size, board_x+board_length, board_y+i*square_size);
		}
		for (MineFieldButton button:buttons) {
			if (button.visible) {
				if ((button.pressed)||((button.selected)&&(button.selectable))) {
					g.setColor(button.alternate);
				}
				else {
					g.setColor(button.fill);
				}
				g.fillRect(button.x, button.y, button.width, button.height);
			}
			g.setColor(button.outline);
			g.drawRect(button.x, button.y, button.width, button.height);
			drawStringCentered(g,Color.BLACK,button.str_size,button.label, button.x, button.y,button.width,button.height);
			for (StringObject string_obj:button.string_objects) {
				drawStringCentered(g,string_obj.color,string_obj.str_size,string_obj.text,string_obj.x,string_obj.y,string_obj.width,string_obj.height);
			}
		}
	}
	public void drawStringCentered(Graphics g,Color col,int size,String str,int x,int y,int width,int height) {
		g.setColor(col);
		g.setFont(new Font("TimesRoman",Font.PLAIN,size));
		int x_offset = (int)width/2-(int)g.getFontMetrics().stringWidth(str)/2;
		int y_offset = (int)height/2+(int)g.getFontMetrics().getHeight()/2;
		g.drawString(str,x+x_offset,y+y_offset);
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
			if (key_fresh(key)) {
				for (MineFieldButton button:buttons) {
					button.key_pressed(this,key);
				}
			}
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
		System.out.println(key);
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
	public boolean key_fresh(int key) {
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
		mouse_x = e.getX();
		mouse_y = e.getY();
		for (MineFieldButton button:buttons) {
			if (button.pressed(this,mouse_x,mouse_y)) {
				
			}
		}
		repaint();
	}
	public void mouseReleased(MouseEvent e) {
		mouse_x = e.getX();
		mouse_y = e.getY();
		for (MineFieldButton button:buttons) {
			if (button.released(this,mouse_x,mouse_y)) {
				
			}
		}
		repaint();
	}
	public void fill_grid(int var_difficulty,int var_board_size) {
		board = new type[var_board_size][var_board_size];
		mine_count = new int[var_board_size][var_board_size];
		revealed = new boolean[var_board_size][var_board_size];
		square_size = (int)board_length/board_size;
		while (true) {
			for (int a=0;a<board_size;a++) {
				for (int b=0;b<board_size;b++) {
					board[a][b] = type.empty;
					revealed[a][b] = true;
					mine_count[a][b] = 0;
				}
			}
			walk = new RandomWalk(board_size);
			walk.createWalk();
			path = walk.getPath();
			for (int i=0;i<path.size();i++) {
				board[(int)path.get(i).getX()][(int)path.get(i).getY()] = type.path;
			}
			board[0][board_size-1] = type.start;
			board[board_size-1][0] = type.finish;
			if (var_difficulty < 1) {
				var_difficulty = 1;
			}
			if (var_difficulty > max_difficulty) {
				var_difficulty = max_difficulty;
			}
			boolean star_generated = false;
			for (int a=0;a<board_size;a++) {
				for (int b=0;b<board_size;b++) {
					if (board[a][b] == type.empty) {
						if (random.nextInt(10) < var_difficulty) {
							board[a][b] = type.bomb;
						}
						else if ((random.nextInt(10) == 0)&&(!star_generated)) {
							board[a][b] = type.star;
							star_generated = true;
						}
					}
				}
			}
			if (!star_generated) {
				int start_x = random.nextInt(board_size);
				int start_y = random.nextInt(board_size);
				for (int a=start_x;a<board_size;a++) {
					for (int b=start_y;b<board_size;b++) {
						if ((board[a][b] == type.empty) && (!star_generated)) {
							board[a][b] = type.star;
							star_generated = true;
						}
					}
				}
				for (int a=start_x;a<board_size;a++) {
					for (int b=0;b<start_y;b++) {
						if ((board[a][b] == type.empty) && (!star_generated)) {
							board[a][b] = type.star;
							star_generated = true;
						}
					}
				}
				for (int a=0;a<start_x;a++) {
					for (int b=start_y;b<board_size;b++) {
						if ((board[a][b] == type.empty) && (!star_generated)) {
							board[a][b] = type.star;
							star_generated = true;
						}
					}
				}
				for (int a=0;a<start_x;a++) {
					for (int b=0;b<start_y;b++) {
						if ((board[a][b] == type.empty) && (!star_generated)) {
							board[a][b] = type.star;
							star_generated = true;
						}
					}
				}
			}
			if (star_generated) {
				break;
			}
		}
		//code for mine_count
		for (int a=0;a<board.length;a++) {
			for (int b=0;b<board[0].length;b++) {
				if (a != 0) {
					if (board[a-1][b] == type.bomb) {
						mine_count[a][b] += 1;
					}
				}
				if (a != board.length-1) {
					if (board[a+1][b] == type.bomb) {
						mine_count[a][b] += 1;
					}
				}
				if (b != 0) {
					if (board[a][b-1] == type.bomb) {
						mine_count[a][b] += 1;
					}
				}
				//finish
				if (b != board[a].length-1) {
					if (board[a][b+1] == type.bomb) {
						mine_count[a][b] += 1;
					}
				}
				//other three sides
			}
		}
	}
}