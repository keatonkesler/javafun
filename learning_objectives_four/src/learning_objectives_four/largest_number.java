package learning_objectives_four;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class largest_number {
	public static void main(String[] args) throws FileNotFoundException {
		run_code origin = new run_code();
		System.out.println(origin.find_largest("/home/student/git/javafun/learning_objectives_four/src/learning_objectives_four/code_file.dat"));
	}
}
class run_code {
	public String find_largest(String file_name) throws FileNotFoundException {
		int largest = 0;
		String result = "";
		String number = "";
		int ii = 0;
		int line_size = 0;
		String line = "";
		boolean start = false;
		
		Scanner file = new Scanner(new File(file_name));
		int size = file.nextInt();
		file.nextLine();
		for (int a=0;a<size;a++) {
			line = file.nextLine();
			line_size = line.length();
			List<Integer> numbers = new ArrayList<Integer>();
			for (int i=0;i < line_size;i++) {
				if ((Character.isDigit(line.charAt(i))) || (line.charAt(i) == '-')) {
					number = "";
					start = false;
					if (i == 0) {
						start = true;
					}
					else if ((!Character.isDigit(line.charAt(i-1))) && (line.charAt(i-1) != '-')) {
						start = true;
					}
					if (start == true) {
						ii = i;
						while ((Character.isDigit(line.charAt(ii))) || ((ii == i) && (line.charAt(ii) == '-'))) {
							number += Character.toString(line.charAt(ii));
							ii++;
							if (ii == line.length()) {
								break;
							}
						}
						numbers.add(Integer.parseInt(number));
					}
				}
			}
			largest = 0;
			for (int b=0;b < numbers.size();b++) {
				if (numbers.get(b) > largest) {
					largest = numbers.get(b);
				}
			}
			result += line + " - Largest == " + Integer.toString(largest) + "\n";
		}
		file.close();
		return result;
	}
}