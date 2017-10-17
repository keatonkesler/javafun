package learning_objectives_four;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

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
		for (int a=0;a<size;a++) {
			line = file.nextLine();
			line_size = line.length();
			Integer[] numbers;
			for (int i=0;i < line_size;i++) {
				if (Character.isDigit(line.charAt(i))) {
					number = "";
					start = false;
					if (i == 0) {
						start = true;
					}
					else if (!Character.isDigit(line.charAt(i-1))) {
						start = true;
					}
					if (start == true) {
						ii = i;
						while (!Character.isDigit(line.charAt(ii))) {
							number += line.charAt(ii);
							ii++;
							if (i == line.length()) {
								break;
							}
						}
						numbers[numbers.length] = String.toInteger(number);
					}
				}
			}
			largest = 0;
			for (int b=0;b < numbers.length;b++) {
				if (numbers[b] > largest) {
					largest = numbers[b];
				}
			}
			result += line + " - Largest == " + Integer.toString(largest) + "\n";
		}
		file.close();
		return result;
	}
}