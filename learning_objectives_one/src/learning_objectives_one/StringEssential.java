package learning_objectives_one;

import java.text.DecimalFormat;

public class StringEssential {
	public static void main(String[] args) {
		AveLine target_class = new AveLine();
		String[] ave_list = {"9 10 5 20","11 22 33 44 55 66 77","48 52 29 100 50 29","0","100 90 95 98 100 97"," 1  ab33  5b5 "};
		for (int i = 0;i < ave_list.length;i++) {
			System.out.println(ave_list[i]);
			System.out.println("average = " + target_class.ave_find(ave_list[i]));
			System.out.println("");
		}
	}
}

class AveLine {
	public String ave_find(String arg) {
		DecimalFormat df = new DecimalFormat("0.000");
		double number = 0;
		String number_build = "";
		int num_of_nums = 0;
		boolean mode = false;
		for (int i = 0;i < arg.length();i++) {
			if (Character.isDigit(arg.charAt(i))) {
				mode = true;
				number_build += arg.charAt(i);
			}
			else if (mode == true) {
				mode = false;
				number += Double.parseDouble(number_build);
				number_build = "";
				num_of_nums++;
			}
			if ((mode == true) && (i == arg.length() - 1)) {
				number += Double.parseDouble(number_build);
				num_of_nums++;
			}
		}
		String output = df.format(number / num_of_nums);
		return output;
	}
}
//make variable scoping matter?