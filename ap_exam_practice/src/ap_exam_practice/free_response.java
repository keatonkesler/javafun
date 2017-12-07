package ap_exam_practice;

//started at 9:25
//ended q1 at 9:57
//ended q2 at 10:20 - problem with victory
//fixed problem at 10:30 completely done

import java.util.Scanner;

public class free_response {
	public static void main(String[] args) {
		question_one code_one = new question_one();
		question_two code_two = new question_two();
		int[] var = {1,3,2,7,3};
		int[][] var2 = {{1,3,2,7,3},{10,10,4,6,2},{5,3,5,9,6},{7,6,4,2,1}};
		int[][] var3 = {{1,1,5,3,4},{12,7,6,1,9},{8,11,10,2,5},{3,2,3,0,6}};
		System.out.println(code_one.arraySum(var));
		var = code_one.rowSums(var2);
		for (int i=0;i<var.length;i++) {
			System.out.print(var[i] + " ");
		}
		System.out.print("\n");
		String str = "";
		if (!code_one.isDiverse(var2)) {
			str = "not ";
		}
		System.out.println("mat1 is "+str+"diverse");
		str = "";
		if (!code_one.isDiverse(var3)) {
			str = "not ";
		}
		System.out.println("mat2 is "+str+"diverse");
		Scanner input = new Scanner(System.in);
		System.out.println("the guess is 5 letters");
		code_two.setHint("harps");
		while (true) {
			String temp = code_two.getHint(input.nextLine());
			boolean bool = true;
			for (int z = 0;z<temp.length() && z<code_two.findHint().length();z++) {
				if (temp.charAt(z) != code_two.findHint().charAt(z)) {
					bool = false;
				}
			}
			if (bool) {
				System.out.println("Congratulations");
				break;
			}
			System.out.println(temp);
		}
	}
}
class question_one {
	
	public int arraySum(int[] arr1) {
		int sum = 0;
		for (int i=0;i<arr1.length;i++) {
			sum += arr1[i];
		}
		return sum;
	}
	public int[] rowSums(int[][] mat1) {
		int[] sum_list = new int[mat1.length];
		for (int x=0;x<mat1.length;x++) {
			sum_list[x] = arraySum(mat1[x]);
		}
		return sum_list;
	}
	public boolean isDiverse(int[][] mat2) {
		int[] sums = rowSums(mat2);
		for (int i=0;i<sums.length;i++) {
			for (int x=0;x<sums.length;x++) {
				if ((x != i) && (sums[x] == sums[i])) {
					return false;
				}
			}
		}
		return true;
	}
}
class question_two {
	String hint = "none";
	public void setHint(String var) {
		if (var != "") {
			hint = var.toUpperCase();
		}
	}
	public boolean foundIn(char ch,String str) {
		for (int x = 0;x<str.length();x++) {
			if (ch == str.charAt(x)) {
				return true;
			}
		}
		return false;
	}
	public String findHint() {
		return hint;
	}
	public String getHint(String guess) {
		if (guess.length() < hint.length()) {
			return "guess needs to be at least " + Integer.toString(hint.length()) + " characters long";
		}
		guess = guess.toUpperCase();
		String result = "";
		for (int i = 0;i<hint.length();i++) {
			if (guess.charAt(i) == hint.charAt(i)) {
				result += hint.charAt(i);
			}
			else if (foundIn(guess.charAt(i),hint)) {
				result += "+";
			}
			else {
				result += "*";
			}
		}
		return result;
	}
}