import java.io.*;
import Maze.stacks.*;

public class MazeSolverLogics {
	private char[][] mazeArray = new char[10][10];
	private BufferedReader br;
	private int currentLocationX;
	private int currentLocationY;
	private LinkedStack<Object> ls = new LinkedStack<Object>();
	private boolean up = true;
	private boolean down = true;
	private boolean left = true;
	private boolean right = true;
	private String previousLocation = null;
	private boolean backTrack = false;

	// gets the file name of the file from the same directory as the application
	// is in
	// and reads it into a 2d array
	public char[][] readInText(String fName) {
		try {
			br = new BufferedReader(new FileReader(fName));

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("File not found exception");
		}

		try {
			for (int y = 0; y <= 9; y++) {
				String s = br.readLine();
				for (int x = 0; x <= 9; x++) {
					mazeArray[x][y] = s.charAt(x);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("IO Exception");
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return mazeArray;
	}

	// combination of all the methods needed to solve the maze
	// used a do/while loop instead of recursively calling the "move" method
	public void solveMaze() {
		char finishedCheck;
		boolean hasEnd;
		hasEnd = checkForEnd();
		if(hasEnd == true){
		findAsterik();
		do {
			finishedCheck = move();
		} while (finishedCheck == '1' || finishedCheck == '0'
				|| finishedCheck == '*');
		print();
		}else{
			System.out.println("This maze has no solution... the program will now terminate "); 
		}
	}

	// finds the asterisk in the file set the current location to those
	// coordinates and pushes it to the stack
	public void findAsterik() {
		char astrik;
		for (int y = 0; y <= 9; y++) {
			for (int x = 0; x <= 9; x++) {
				astrik = mazeArray[x][y];
				if (astrik == '*') {
					currentLocationX = x;
					currentLocationY = y;
					String start = toString(currentLocationX, currentLocationY);
					ls.push(start);
					break;
				}

			}
		}
	}

	// checks to the right of current location to see if possible to move right
	public char checkRight() {
		char checkR = 0;
		boolean c = false;
		String s = toString(currentLocationX + 1, currentLocationY);
		c = ls.contains(s);
		if (currentLocationX != 9) {
			checkR = mazeArray[currentLocationX + 1][currentLocationY];

			if (checkR == '1' || previousLocation == "right" || c == true) {
				right = false;
			} else if (checkR == '0' && previousLocation != "right") {
				right = true;
			}
		} else if (currentLocationX == 9) {
			right = false;
		}
		return checkR;
	}

	// checks to the left of current location to see if left is a possible
	// direction
	public char checkLeft() {
		char checkL = 0;
		boolean c = false;
		String s = toString(currentLocationX - 1, currentLocationY);
		c = ls.contains(s);
		if (currentLocationX != 0) {
			checkL = mazeArray[currentLocationX - 1][currentLocationY];
			if (checkL == '1' || previousLocation == "left" || c == true) {
				left = false;
			} else if (checkL == '0' && previousLocation != "left") {
				left = true;
			}
		} else if (currentLocationX == 0) {
			left = false;
		}
		return checkL;
	}

	// checks above the current location to see if up is a possible direction to
	// move
	public char checkUp() {
		char checkU = 0;
		boolean c = false;
		String s = toString(currentLocationX, currentLocationY - 1);
		c = ls.contains(s);
		if (currentLocationY != 0) {
			checkU = mazeArray[currentLocationX][currentLocationY - 1];
			if (checkU == '1' || previousLocation == "up" || c == true) {
				up = false;
			} else if (checkU == '0' && previousLocation != "up") {
				up = true;
			}
		} else if (currentLocationY == 0) {
			up = false;
		}
		return checkU;
	}

	// checks below the current location to see if down is a possible location
	// to move
	public char checkDown() {
		char checkD = 0;
		boolean c = false;
		String s = toString(currentLocationX, currentLocationY + 1);
		c = ls.contains(s);
		if (currentLocationY != 9) {
			checkD = mazeArray[currentLocationX][currentLocationY + 1];
			if (checkD == '1' || previousLocation == "down" || c == true) {
				down = false;
			} else if (checkD == '0' && previousLocation != "down") {
				down = true;
			}

		} else if (currentLocationY == 9) {
			down = false;
		}
		return checkD;
	}

	// takes in two integers and returns a formated string in the form of a
	// coordinate pair
	public String toString(int x, int y) {
		String xX = String.valueOf(x);
		String yY = String.valueOf(y);
		String xY = ("(" + xX + ", " + yY + ")");
		return xY;
	}

	// responsible for moving current location in the maze pushing answers to
	// stack and Popping things that are dead ends
	public char move() {
		char u, d, l, r;
		String location = "";
		u = checkUp();
		d = checkDown();
		l = checkLeft();
		r = checkRight();
		if (u == 'e' || u == 'E') {
			currentLocationY--;
			location = toString(currentLocationX, currentLocationY);
			ls.push(location);
		} else if (d == 'e' || d == 'E') {
			currentLocationY++;
			location = toString(currentLocationX, currentLocationY);
			ls.push(location);
		} else if (l == 'e' || l == 'E') {
			currentLocationX--;
			location = toString(currentLocationX, currentLocationY);
			ls.push(location);
		} else if (r == 'e' || r == 'E') {
			currentLocationX++;
			location = toString(currentLocationX, currentLocationY);
			ls.push(location);

		} else if (up == true) {

			currentLocationY--;
			location = toString(currentLocationX, currentLocationY);
			ls.push(location);
			previousLocation = "down";
			backTrack = false;

		} else if (down == true) {
			currentLocationY++;
			location = toString(currentLocationX, currentLocationY);
			ls.push(location);
			previousLocation = "up";
			backTrack = false;

		} else if (left == true) {
			currentLocationX--;
			location = toString(currentLocationX, currentLocationY);
			ls.push(location);
			previousLocation = "right";
			backTrack = false;

		} else if (right == true) {

			currentLocationX++;
			location = toString(currentLocationX, currentLocationY);
			ls.push(location);
			previousLocation = "left";
			backTrack = false;

		} else if (left == false && right == false && up == false
				&& down == false) {
			backTrack();
		}
		char check = mazeArray[currentLocationX][currentLocationY];
		return check;
	}

	// responsible for moving backwards in the array and popping the stack
	// only called if all variables are false
	public void backTrack() {
		ls.pop();
		mazeArray[currentLocationX][currentLocationY] = '1';
		updateLocation();
		if (backTrack == false) {
			if (previousLocation == "right") {
				changeDirection();
			} else if (previousLocation == "left") {
				changeDirection();
			} else if (previousLocation == "up") {
				changeDirection();
			} else if (previousLocation == "down") {
				changeDirection();
			}
			backTrack = true;
		}
	}

	// updates the current location variables to the location on top of the
	// stack
	public void updateLocation() {
		Object a = ls.top();
		String coordinate = a.toString();
		String x = String.valueOf(coordinate.charAt(1));
		String y = String.valueOf(coordinate.charAt(4));
		int xX = Integer.parseInt(x);
		int yY = Integer.parseInt(y);
		currentLocationX = xX;
		currentLocationY = yY;
	}

	// puts answers in correct order and prints them out in the terminal
	public void print() {
		int size = ls.counter;
		Object[] answer = new Object[size];
		for (int i = 0; i < size; i++) {
			Object a = ls.top();
			ls.pop();
			answer[i] = a;
		}
		for (int j = size - 1; j >= 0; j--) {
			Object b;
			b = answer[j];
			System.out.println(b);
		}
	}

	// makes the previous direction the opposite of what it is
	public void changeDirection() {
		if (previousLocation == "up") {
			previousLocation = "down";
		} else if (previousLocation == "down") {
			previousLocation = "up";
		} else if (previousLocation == "right") {
			previousLocation = "left";
		} else if (previousLocation == "left") {
			previousLocation = "right";
		}
	}
	
	//checks maze to see if it has an exit
	// then checks exit to see if it has a path to it
	// if both are true returns true
	//else returns false
	public boolean checkForEnd(){
		boolean end = true;
		
		for(int x = 0; x <= 9; x++){
			for(int y = 0; y <= 9; y++){
				char a = mazeArray[x][y];
				if(a == 'e' || a == 'E'){
					boolean solvable = isSolvable(x, y);
					if(solvable){
					end = true;
					x = 10;
					break;
					}else{
						continue;
					}
				}else{
					 end = false;
				}
			}
		}
		return end;
		
	}
	
	//checks the maze to see if the location passed has a 0 surrounding it
	//if it does returns true 
	//else returns false
	public boolean isSolvable(int x, int y){
		int tempX = 10;
		int tempY = 10;
		char r, l, u, d;
		boolean possible = false;
		if (x <= 9 && x >= 0) {
			tempX = x;
		}
		if (y <= 9 && y >= 0) {
			tempY = y;
		}

		if (tempX != 9 && tempX != 10) {
			r = mazeArray[tempX + 1][tempY];
			if (r == '0') {
				possible = true;
			}
		}
		if (tempX != 0 && tempX != 10) {
			l = mazeArray[tempX - 1][tempY];
			if(l == '0'){
				possible = true;
			}
		}
		if (tempY != 0 && tempY != 10) {
			u = mazeArray[tempX][tempY - 1];
			if(u == '0'){
				possible = true;
			}
		}
		if (tempY != 9 && tempY != 10) {
			d = mazeArray[tempX][tempY + 1];
			if(d == '0'){
				possible = true;
			}
		}
		return possible;
	}
}
