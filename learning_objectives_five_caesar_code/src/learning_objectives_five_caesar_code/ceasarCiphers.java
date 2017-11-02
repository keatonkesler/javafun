package learning_objectives_five_caesar_code;

import javax.swing.JOptionPane;

public class ceasarCiphers {
	public static void main(String[] args) {
		run_code code = new run_code();
		while (true) {
			code.user_set_alpha();
			JOptionPane.showMessageDialog(null,code.user_encode());
		}
	}
}
class run_code {
	char[] alpha;
	char[] alpha2;
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