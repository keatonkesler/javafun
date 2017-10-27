package learning_objectives_five_caesar_code;

public class ceasarCiphers {
	public static void main(String[] args) {
		run_code code = new run_code();
		code.setAlpha("abcdefghijklmnopqrstuvwxyz");
		System.out.println(code.encode("Cat",1));
		code.setAlpha("abcdefghijklmnopqrstuvwxYz.!,$?");
		System.out.println(code.encode("See spot. See spot run?",7));
		System.out.println(code.encode("AbCdEf ghijk LMNOP qrst UVWxyz .!,$?",25));
	}
}
class run_code {
	char[] alpha;
	char[] alpha2;
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