import java.awt.Color;

class StringObject {
	int x;
	int y;
	int width;
	int height;
	String text;
	Color color;
	int str_size;
	public StringObject(int var_x,int var_y,int var_width,int var_height,String var_text, Color var_color,int var_size) {
		x = var_x;
		y = var_y;
		width = var_width;
		height = var_height;
		text = var_text;
		color = var_color;
		str_size = var_size;
	}
}
class MineFieldButton {
	boolean visible = true;
	boolean pressed = false;
	boolean selected = false;
	boolean state_set = false;
	boolean selectable = false;
	StringObject[] string_objects = new StringObject[0];
	
	String label;
	String id;
	int x;
	int y;
	int width;
	int height;
	Color outline;
	Color fill;
	Color alternate;
	int str_size = 13;
	
	public MineFieldButton(String var_label,String var_id,int var_x,int var_y,
			int var_width,int var_height,Color var_outline,Color var_fill,Color var_alternate,boolean var_state_set) {
		label = var_label;
		id = var_id;
		x = var_x;
		y = var_y;
		width = var_width;
		height = var_height;
		outline = var_outline;
		fill = var_fill;
		alternate = var_alternate;
		state_set = var_state_set;
	}
	public boolean pressed(MineWalkerPanel pan,int mouse_x,int mouse_y) {
		if (selected) {
			was_unselected(pan);
		}
		if (!state_set) {
			selected = false;
		}
		if ((visible)&&(!pressed)){
			if ((mouse_x > x)&&(mouse_y > y)) {
				if ((mouse_x < x+width)&&(mouse_y < y+height)) {
					if (state_set) {
						if (selected) {
							selected = false;
						}
						else {
							selected = true;
						}
					}
					pressed = true;
					was_pressed(pan);
					return true;
				}
			}
		}
		return false;
	}
	public boolean released(MineWalkerPanel pan,int mouse_x,int mouse_y) {
		if (!state_set) {
			selected = false;
		}
		if ((visible)&&(pressed)) {
			if ((mouse_x > x)&&(mouse_y > y)) {
				if ((mouse_x < x+width)&&(mouse_y < y+height)) {
					pressed = false;
					if (!state_set) {
						selected = true;
					}
					was_unpressed(pan);
					was_selected(pan);
					return true;
				}
			}
		}
		
		pressed = false;
		return false;
	}
	public void key_pressed(MineWalkerPanel pan,int key_num) {}
	public void was_pressed(MineWalkerPanel pan) {}
	public void was_unpressed(MineWalkerPanel pan) {}
	public void was_selected(MineWalkerPanel pan) {}
	public void was_unselected(MineWalkerPanel pan) {}
}
class buttonGridSize extends MineFieldButton {
	int min_board;
	int max_board;
	public buttonGridSize(String var_label,String var_id,int var_x,int var_y,
			int var_width,int var_height,Color var_outline,Color var_fill,Color var_alternate,int var_grid_size, int var_min_board, int var_max_board) {
		
		super(var_label,var_id,var_x,var_y,
				var_width,var_height,var_outline,var_fill,var_alternate,false);
		selectable = true;
		label = Integer.toString(var_grid_size);
		min_board = var_min_board;
		max_board = var_max_board;
		string_objects = new StringObject[1];
		string_objects[0] = new StringObject(x,y-20,width,height,"Grid Size",Color.BLACK,15);
	}
	public void key_pressed(MineWalkerPanel pan,int key_num) {
		if (selected) {
			if ((key_num >= 48)&&(key_num <= 57)) {
				if (label.charAt(label.length()-1) == '0') {
					label = label.substring(0,label.length()-1) + Integer.toString(key_num-48);
				}
				else {
					label += Integer.toString(key_num-48);
				}
				if ((Integer.parseInt(label) > max_board)&&(label.length() > 1)) {
					label = label.substring(label.length()-1,label.length());
					if (Integer.parseInt(label) < min_board) {
						label += "0";
						if ((Integer.parseInt(label) > max_board)||(Integer.parseInt(label) < min_board)) {
							label = Integer.toString(min_board);
						}
					}
				}
				else if (Integer.parseInt(label) < min_board) {
					label = Integer.toString(min_board);
				}
				pan.board_size = Integer.parseInt(label);
			}
		}
	}
}
class buttonDifficulty extends MineFieldButton {
	int max_difficulty;
	int difficulty_unlocked;
	int max_lives;
	int lives;
	public buttonDifficulty(String var_label,String var_id,int var_x,int var_y,
			int var_width,int var_height,Color var_outline,Color var_fill,Color var_alternate,int var_difficulty,int var_max_difficulty,int var_difficulty_unlocked,int var_max_lives) {
		
		super(var_label,var_id,var_x,var_y,
				var_width,var_height,var_outline,var_fill,var_alternate,false);
		selectable = true;
		label = Integer.toString(var_difficulty);
		max_difficulty = var_max_difficulty;
		difficulty_unlocked = var_difficulty_unlocked;
		max_lives = var_max_lives;
		lives = max_lives;
		string_objects = new StringObject[2];
		string_objects[0] = new StringObject(x,y-20,width,height,"Difficulty",Color.BLACK,15);
		string_objects[1] = new StringObject(x,y+height,width,height,"Lives: "+Integer.toString(lives),Color.BLACK,15);
	}
	public void key_pressed(MineWalkerPanel pan,int key_num) {
		if (selected) {
			if ((key_num >= 48)&&(key_num <= 57)) {
				label = Integer.toString(key_num-48);
				if (Integer.parseInt(label) > max_difficulty) {
					label = Integer.toString(max_difficulty);
				}
				else if (Integer.parseInt(label) < 1) {
					label = "1";
				}
				pan.difficulty = Integer.parseInt(label);
			}
		}
	}
}
class buttonRestart extends MineFieldButton {
	public buttonRestart(String var_label,String var_id,int var_x,int var_y,int var_width,int var_height,Color var_outline,Color var_fill,Color var_alternate) {
		super(var_label,var_id,var_x,var_y,var_width,var_height,var_outline,var_fill,var_alternate,false);
	}
	public void was_pressed(MineWalkerPanel pan) {
		pan.fill_grid(pan.difficulty,pan.board_size);
	}
}
class buttonShowMines extends MineFieldButton {
	public buttonShowMines(String var_label,String var_id,int var_x,int var_y,int var_width,int var_height,Color var_outline,Color var_fill,Color var_alternate) {
		super(var_label,var_id,var_x,var_y,var_width,var_height,var_outline,var_fill,var_alternate,true);
		selectable = true;
	}
	public void was_pressed(MineWalkerPanel pan) {
		if (pan.show_mines) {
			pan.show_mines = false;
		}
		else {
			pan.show_mines = true;
		}
	}
}
class buttonShowPath extends MineFieldButton {
	public buttonShowPath(String var_label,String var_id,int var_x,int var_y,int var_width,int var_height,Color var_outline,Color var_fill,Color var_alternate) {
		super(var_label,var_id,var_x,var_y,var_width,var_height,var_outline,var_fill,var_alternate,true);
		selectable = true;
	}
	public void was_pressed(MineWalkerPanel pan) {
		if (pan.show_path) {
			pan.show_path = false;
		}
		else {
			pan.show_path = true;
		}
	}
}
class buttonLabel extends MineFieldButton {
	public buttonLabel(String var_label,String var_id,int var_x,int var_y,int var_width,int var_height,Color var_outline,Color var_fill,Color var_alternate) {
		super(var_label,var_id,var_x,var_y,var_width,var_height,var_outline,var_fill,var_alternate,true);
		str_size = 40;
	}
}