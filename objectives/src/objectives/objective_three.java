package objectives;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class objective_three {

	public static void main(String[] args) throws FileNotFoundException {
		run_code origin = new run_code();
		System.out.println(origin.div_2(""));
	}

}
class run_code {
	public String div_2(String file_name) throws FileNotFoundException {
		Scanner file = new Scanner(new File("/home/student/git/javafun/objectives/src/objectives/labxx.dat"));
		int size = file.nextInt();
		String result = "";
		int num = 0;
		for(int i=0;i < size;i++) {
			num = file.nextInt();
			if (num % 2 == 0) {
				result += Integer.toString(num) + " is even\n";
			}
			else {
				result += Integer.toString(num) + " is odd\n";
			}
		}
		file.close();
		return result;
	}
}