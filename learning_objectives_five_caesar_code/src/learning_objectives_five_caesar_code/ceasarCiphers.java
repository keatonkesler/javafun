package learning_objectives_five_caesar_code;

import javax.swing.JOptionPane;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.File;

public class ceasarCiphers {
	public static void main(String[] args) throws FileNotFoundException {
		///*
		run_code code = new run_code();
		code.setAlpha("abcdefghijklmnopqrstuvwxyz");
		String[] choices = {"encode","decode","set code","crack","exit"};
		while (true) {
			String choice = (String) JOptionPane.showInputDialog(null,null,null,JOptionPane.QUESTION_MESSAGE,null,choices,choices[0]);
			if (choice == "set code") {
				code.user_pick_alpha();
			}
			else if (choice == "encode") {
				JOptionPane.showMessageDialog(null,code.user_encode());
			}
			else if (choice == "decode") {
				JOptionPane.showMessageDialog(null, code.user_decode());
			}
			else if (choice == "crack") {
				JOptionPane.showMessageDialog(null, code.user_crack());
			}
			else if (choice == "set_reading") {
				code.set_reading();
			}
			else if (choice == "exit") {
				break;
			}
		}
		//*/
		/*
		String filename = JOptionPane.showInputDialog("Enter the file name:");
		Scanner file = new Scanner(new File("/home/student/" + filename));
		String aLine = file.nextLine();
		*/
	}
}
class run_code {
	char[] alpha;
	char[] alpha2;
	boolean read_text = true;
	public boolean is_int(String value) {
		try {
			int i = Integer.parseInt(value);
			return true;
		}
		catch(NumberFormatException e) {
			return false;
		}
	}
	public int index_of(char[] list,char character) {
		for (int i = 0;i < list.length;i++) {
			if (list[i] == character) {
				return i;
			}
		}
		return -1;
	}
	public void setAlpha(String set_alpha) {
		alpha = new char[set_alpha.length()];
		alpha2 = new char[set_alpha.length()];
		for (int i = 0;i < set_alpha.length();i++) {
			alpha[i] = Character.toLowerCase(set_alpha.charAt(i));
		}
		for (int i = 0;i < set_alpha.length();i++) {
			alpha2[i] = Character.toUpperCase(set_alpha.charAt(i));
		}
	}
	public void user_pick_alpha() {
		String[] code_choices = {"custom","abcdefghijklmnopqrstuvwxyz","abcdefghijklmnopqrstuvwxyz1234567890-=_+,<.>/?'\";:[{]}\\|`~!@#$%^&*()","qwertyuiopasdfghjklzxcvbnm","qwertyuiopasdfghjklzxcvbnm1234567890,<.>/?;:'\"[{]}\\|-_=+!@#$%^&*()`~"};
		String choice = (String) JOptionPane.showInputDialog(null,null,null,JOptionPane.QUESTION_MESSAGE,null,code_choices,code_choices[0]);
		if (choice == "custom") {
			user_set_alpha();
		}
		else {
			setAlpha(choice);
		}
	}
	public void user_set_alpha() {
		String set_alpha = JOptionPane.showInputDialog("Enter a code key (blank for alphabet)");
		if (set_alpha.isEmpty()) {
			set_alpha = "abcdefghijklmnopqrstuvwxyz";
		}
		setAlpha(set_alpha);
	}
	public String user_encode() {
		String term = JOptionPane.showInputDialog("Enter a text");
		while (term.isEmpty()) {
			term = JOptionPane.showInputDialog("Enter a text");
		}
		String shift_text = JOptionPane.showInputDialog("Shift by what number?");
		while (!is_int(shift_text)) {
			shift_text = JOptionPane.showInputDialog("Shift by what number?");
		}
		int shift = Integer.parseInt(shift_text);
		return encode(term,shift);
	}
	public String user_decode() {
		String term = JOptionPane.showInputDialog("Enter a text");
		while (term.isEmpty()) {
			term = JOptionPane.showInputDialog("Enter a text");
		}
		String shift_text = JOptionPane.showInputDialog("How much was is shifted?");
		while (!is_int(shift_text)) {
			shift_text = JOptionPane.showInputDialog("How much was is shifted?");
		}
		int shift = (Integer.parseInt(shift_text))*-1;
		return encode(term,shift);
	}
	public void set_reading() {
		String[] read_choices = {"text","file"};
		String choice = (String) JOptionPane.showInputDialog(null,null,null,JOptionPane.QUESTION_MESSAGE,null,read_choices,read_choices[0]);
		if (choice == "text") {
			read_text = true;
		}
		else {
			read_text = false;
		}
	}
	public String user_crack() {
		String term = JOptionPane.showInputDialog("Enter a text");
		while (term.isEmpty()) {
			term = JOptionPane.showInputDialog("Enter a text");
		}
		String key = JOptionPane.showInputDialog("Enter a key");
		while (term.isEmpty()) {
			key = JOptionPane.showInputDialog("Enter a key");
		}
		return crack(term,key);
	}
	public String crack(String term,String key) {
		int shift = 0;
		String temp_term = "";
		boolean key_found = false;
		while (shift < alpha.length) {
			temp_term = encode(term,shift);
			for (int i = 0;i < temp_term.length();i++) {
				if ((temp_term.charAt(i) == key.charAt(0)) && ((i + key.length())) <= temp_term.length()) {
					key_found = true;
					for (int a = 0;a < key.length();a++) {
						if (temp_term.charAt(i+a) != key.charAt(a)) {
							key_found = false;
							break;
						}
					}
				}
				if (key_found == true) {
					break;
				}
			}
			if (key_found == true) {
				break;
			}
			shift++;
		}
		if (key_found == true) {
			return encode(term,shift);
		}
		return "Key not found in text/file";
	}
	public String encode(String term,int shift) {
		String result = "";
		int new_index = 0;
		boolean lower = true;
		boolean real = true;
		for (int i = 0;i < term.length();i++) {
			real = true;
			lower = false;
			if (index_of(alpha,term.charAt(i)) != -1) {
				new_index = index_of(alpha,term.charAt(i)) + shift;
				lower = true;
			}
			else if (index_of(alpha2,term.charAt(i)) != -1){
				new_index = index_of(alpha2,term.charAt(i)) + shift;
				lower = false;
			}
			else {
				result += term.charAt(i);
				real = false;
			}
			while (new_index > alpha.length - 1) {
				new_index -= alpha.length;
			}
			while (new_index < 0) {
				new_index += alpha.length;
			}
			if (lower == true) {
				result += alpha[new_index];
			}
			else if (real == true) {
				result += alpha2[new_index];
			}
		}
		return result;
	}
}

