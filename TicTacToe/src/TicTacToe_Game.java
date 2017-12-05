import javax.swing.JOptionPane;

public class TicTacToe_Game {

	public static void main(String[] args) {
		run_code code = new run_code();
		code.play_game();
	}

}
class run_code {
	char[][] board;
	char[] rows;
	int[] columns;
	char[] alphabet = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
	String[] option_exit = {"Continue","Exit"};
	String str = "";
	String str2 = "";
	int num = 0;
	int num2 = 0;
	int num3 = 0;
	char row_guess = ' ';
	char ch = 'X';
	int row_guess_num = -1;
	int column_guess = 0;
	int turn = 1;
	int player_won = 0;
	boolean bool = false;
	boolean exit_game = false;
	
	public int char_found_in(char arg,char[] list) {
		for (int aaa = 0;aaa<list.length;aaa++) {
			if (list[aaa] == arg) {
				return aaa;
			}
		}
		return -1;
	}
	public boolean int_found_in(int arg,int[] list) {
		for (int aaa = 0;aaa<list.length;aaa++) {
			if (list[aaa] == arg) {
				return true;
			}
		}
		return false;
	}
	public boolean new_game(int size_x,int size_y) {
		if ((size_x < 3) || (size_x > 26) || (size_y < 3) || (size_y > 26)) {
			return false;
		}
		board = new char[size_y][size_x];
		rows = new char[size_x];
		columns = new int[size_y];
		num = 0;
		for (int aaa = 0; aaa < size_y;aaa++) {
			for (int bbb = 0; bbb < size_x;bbb++) {
				rows[bbb] = alphabet[bbb];
				columns[aaa] = num;
				board[aaa][bbb] = ' ';
			}
			num++;
		}
		return true;
	}
	public void show_game(char[][] list) {
		str = "Player Turn: " + Integer.toString(turn) + "\n\n   ";
		str2 = "";
		for (int aaa=0;aaa<list[0].length;aaa++) {
			str += " " + rows[aaa] + "     ";
		}
		str += "\n";
		for (int aaa = 0;aaa<list[0].length;aaa++) {
			str2 += "------";
		}
		for (int aaa = 0;aaa<list.length;aaa++) {
			str += Integer.toString(aaa);
			if (aaa < 10) {
				str += "  ";
			}
			for (int bbb = 0;bbb<list[0].length;bbb++) {
				str += list[aaa][bbb];
				if (list[aaa][bbb] == ' ') {
					str += " ";
				}
				if (bbb != list[0].length-1) {
					str += "  |   ";
				}
			}
			if (aaa != list.length-1) {
				str += "\n   " + str2 + "\n";
			}
		}
		str += "\n ";
		str2 = (String) JOptionPane.showInputDialog(null,str,null,JOptionPane.QUESTION_MESSAGE,null,option_exit,option_exit[0]);
		if (str2 == "Exit") {
			exit_game = true;
		}
	}
	public boolean place_token(int player,char[][] list) {
		if (!((player == 1) || (player == 2))) {
			return false;
		}
		while (true) {
			while (true) {
				str = JOptionPane.showInputDialog("Player " + player + " Enter a Coordinate: ");
				if (str.length() > 1) {
					if (char_found_in(Character.toLowerCase(str.charAt(0)),rows) != -1) {
						try {
							column_guess = Integer.parseInt(str.substring(1, str.length()));
							row_guess_num = char_found_in(Character.toLowerCase(str.charAt(0)),rows);
							if (int_found_in(column_guess,columns)) {
								break;
							}
						}
						catch (NumberFormatException e) {};
					}
				}
			}
			if (list[column_guess][row_guess_num] == ' ') {
				if (player == 1) {
					list[column_guess][row_guess_num] = 'X';
				}
				else {
					list[column_guess][row_guess_num] = 'O';
				}
				break;
			}
			else {
				JOptionPane.showMessageDialog(null, "can't place there");
			}
			show_game(list);
		}
		return true;
	}
	public boolean check_game_over(char[][] list) {
		//check if  an entire row or column is all one player's, or the two diagonal directions, if so say which player won and show the board
		num = list.length;
		num2 = list[0].length;
		if (num < num2) {
			num3 = num;
		}
		else {
			num3 = num2;
		}
		for (int aaa=0;aaa<num;aaa++) {
			for(int bbb=0;bbb<num2;bbb++) {
				if (list[aaa][bbb] == 'X') {
					ch = 'X';
					player_won = 1;
				}
				else if (list[aaa][bbb] == 'O') {
					ch = 'O';
					player_won = 2;
				}
				if (aaa <= num-num3) {
					for(int ccc = aaa;ccc<bbb+num3;ccc++) {
						if (list[ccc][bbb] != ch) {
							break;
						}
						if (ccc == aaa+num3-1) {
							return true;
						}
					}
				}
				if (bbb <= num2-num3) {
					for(int ccc = bbb;ccc<bbb+num3;ccc++) {
						if (list[aaa][ccc] != ch) {
							break;
						}
						if (ccc == bbb+num3-1) {
							return true;
						}
					}
				}
				if ((aaa >= num3-1) && (bbb <= num2-num3)) {
					for(int ccc = 0;ccc<num3;ccc++) {
						if (list[aaa-ccc][bbb+ccc] != ch) {
							break;
						}
						if (ccc == num3-1) {
							return true;
						}
					}
				}
				if ((aaa <= num-num3) && (bbb <= num2-num3)) {
					for(int ccc = 0;ccc<num3;ccc++) {
						if (list[aaa+ccc][bbb+ccc] != ch) {
							break;
						}
						if (ccc == num3-1) {
							return true;
						}
					}
				}
			}
		}
		bool = false;
		for (int aaa=0;aaa<list.length;aaa++) {
			for (int bbb=0;bbb<list[0].length;bbb++) {
				if (list[aaa][bbb] == ' ') {
					bool = true;
					break;
				}
				else if ((aaa == list.length-1) && (bbb == list[0].length-1)) {
					player_won = 0;
					return true;c
				}
			}
			if (bool == true) {
				break;
			}
		}
		return false;
	}
	public void play_game() {
		while (true) {
			turn = 1;
			while (true) {
				str = JOptionPane.showInputDialog("What of size board width? (3-26)");
				str2 = JOptionPane.showInputDialog("What of size board height? (3-26)");
				try {
					num = Integer.parseInt(str);
					num2 = Integer.parseInt(str2);
					if (new_game(num,num2) == true) {
						break;
					}
				}
				catch (NumberFormatException e){};
			}
			show_game(board);
			if (exit_game == true) {
				break;
			}
			while (true) {
				place_token(turn,board);
				if (exit_game == true) {
					break;
				}
				if (turn == 1) {
					turn = 2;
				}
				else {
					turn = 1;
				}
				show_game(board);
				if (exit_game == true) {
					break;
				}
				if (check_game_over(board)) {
					break;
				}
			}
			if (exit_game == true) {
				break;
			}
			else {
				str2 = "Player " + Integer.toString(player_won) + " Won the Game";
				if (player_won == 0) {
					str2 = "The Players Tied";
				}
				str = (String) JOptionPane.showInputDialog(null,"Play Again?",str2,JOptionPane.QUESTION_MESSAGE,null,option_exit,option_exit[0]);
				if (str == "Exit") {
					break;
				}
			}
		}
	}
}