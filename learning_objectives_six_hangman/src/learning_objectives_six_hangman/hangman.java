package learning_objectives_six_hangman;

import javax.swing.JOptionPane;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.File;
import java.util.Random;

public class hangman {
	public static void main(String[] args) throws FileNotFoundException {
		run_code code = new run_code();
		code.file_hangman();
	}
}
class run_code {
	int line_num = 0;
	int a = 0;
	int b = 0;
	int error_allowed = 5;
	int errors = 0;
	boolean end_code = false;
	String guess = "";
	String wrong = "";
	String progress = "";
	String key = "";
	String pre_pick;
	char pick;
	String file_directory = "/home/student/";
	String[] options;
	char[] alpha;
	char[] alphabet;
	String pre_alphabet = "abcdefghijklmnopqrstuvwxyz";
	String[] choices = {"again","exit"};
	String[] file_choices = {"desktop","Eclipse"};
	Random random = new Random();
	public boolean file_exists(String file_name) {
		File temp_file = new File(file_name);
		if (temp_file.exists()) {
			return true;
		}
		return false;
	}
	public boolean has(char[] list,char value) {
		for (int zzz = 0;zzz<list.length;zzz++) {
			if (list[zzz] == value) {
				return true;
			}
		}
		return false;
	}
	public void get_key(String file_name) throws FileNotFoundException {
		Scanner file = new Scanner(new File(file_name));
		while (file.hasNextLine()) {
			System.out.println(file.nextLine());
			line_num++;
		}
		file = new Scanner(new File(file_name));
		options = new String[line_num];
		int i = 0;
		while (file.hasNextLine()) {
			options[i] = file.nextLine();
			System.out.println(options[i]);
			i++;
		}
		file.close();
	}
	public String show() {
		progress = "";
		wrong = "";
		for (int xxx = 0;xxx<guess.length();xxx++) {
			if ((has(alpha,Character.toLowerCase(guess.charAt(xxx)))) || (!has(alphabet,Character.toLowerCase(guess.charAt(xxx))))) {
				progress += Character.toString(guess.charAt(xxx));
			}
			else if (guess.charAt(xxx) == ' '){
				progress += " ";
			}
			else {
				progress += "_";
			}
		}
		for (int xxx = 0;xxx<alpha.length;xxx++) {
			if (alpha[xxx] != '\u0000') {
				for (int www = 0;www<guess.length();www++) {
					if (alpha[xxx] == Character.toLowerCase(guess.charAt(www))) {
						break;
					}
					if (www == guess.length()-1) {
						wrong += alpha[xxx];
					}
				}
			}
		}
		return progress + "\nGuessed: " + wrong + "\nLives: " + Integer.toString(error_allowed - errors);
	}
	public boolean choose() {
		while (true) {
			pre_pick = JOptionPane.showInputDialog("Enter a character:");
			while (pre_pick.isEmpty()) {
				pre_pick = JOptionPane.showInputDialog("Enter a character:");
			}
			pick = Character.toLowerCase(pre_pick.charAt(0));
			if (!has(alpha,pick)) {
				a = 0;
				while (alpha[a] != '\u0000') {
					a++;
				}
				alpha[a] = pick;
				break;
			}
			else {
				JOptionPane.showMessageDialog(null,"Already picked that character");
			}
		}
		b = 0;
		for (int yyy = 0;yyy<guess.length();yyy++) {
			if (alpha[a] == Character.toLowerCase(guess.charAt(yyy))) {
				b++;
			}
		}
		if (b == 0) {
			errors++;
			if (errors > error_allowed - 1) {
				JOptionPane.showMessageDialog(null, "You lost");
				return true;
			}
		}
		else {
			for (int yyy = 0;yyy<guess.length();yyy++) {
				if ((has(alpha,Character.toLowerCase(guess.charAt(yyy)))) || (!has(alphabet,Character.toLowerCase(guess.charAt(yyy))))) {
					if (yyy == guess.length()-1) {
						JOptionPane.showMessageDialog(null, "You win");
						return true;
					}
				}
				else {
					break;
				}
			}
		}
		JOptionPane.showMessageDialog(null, "There are " + Integer.toString(b) + " " + Character.toString(pick) + "'s in the word/phrase");
		return false;
	}
	public void file_hangman() throws FileNotFoundException {
		alphabet = new char[pre_alphabet.length()];
		for (int i = 0;i<pre_alphabet.length();i++) {
			alphabet[i] = pre_alphabet.charAt(i);
		}
		String file_choice = (String) JOptionPane.showInputDialog(null,"File's location",null,JOptionPane.QUESTION_MESSAGE,null,file_choices,file_choices[0]);
		if (file_choice == "Eclipse") {
			file_directory = "/home/student/Eclipse/learning_objectives_six_hangman/src/learning_objectives_six_hangman/";
		}
		key = file_directory + JOptionPane.showInputDialog("Enter the file name:");
		while (!file_exists(key)) {
			String choice = (String) JOptionPane.showInputDialog(null,"File not found",null,JOptionPane.QUESTION_MESSAGE,null,choices,choices[0]);
			if (choice == "exit") {
				end_code = true;
				break;
			}
			key = file_directory + JOptionPane.showInputDialog("Enter an existing file name:");
		}
		if (end_code == false) {
			get_key(key);
			while (true) {
				errors = 0;
				guess = options[random.nextInt(options.length)];
				System.out.println(guess);
				alpha = new char[error_allowed+guess.length()];
				JOptionPane.showMessageDialog(null, show());
				while (true) {
					if (choose()) {
						break;
					}
					JOptionPane.showMessageDialog(null, show());
				}
				JOptionPane.showMessageDialog(null, "The word/phrase was: " + guess);
				String choice = (String) JOptionPane.showInputDialog(null,"Game Ended",null,JOptionPane.QUESTION_MESSAGE,null,choices,choices[0]);
				if (choice == "exit") {
					break;
				}
			}
		}
	}
}