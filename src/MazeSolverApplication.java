import java.util.Scanner;

public class MazeSolverApplication {
	private static Scanner s;

	public static void main(String[] args) {
		s = new Scanner(System.in);
		MazeSolverLogics logs = new MazeSolverLogics();
		System.out.println("Enter the file name");
		String fName = s.nextLine();
		logs.readInText(fName);
		logs.solveMaze();

	}
}
