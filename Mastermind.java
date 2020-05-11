import java.util.Random;
import java.util.Scanner;

public class Mastermind {
	private char[] secret = new char[4];
	private char[][] guesses = new char[10][4];
	private char[] colors = {'R','G','B','Y','O','P'};
	private boolean win = false;

	//default constructor
	public Mastermind() {}

	//play game - which is made up of:
	//generate a secret code
	public char[] genSecret() {
		Random rand = new Random();
		char[] res = new char[4];
		//for loop + random number generator to generate a random 4 character secret
		for (int i=0; i<4; i++) {
			int colorIndex=rand.nextInt(colors.length);
			res[i] = colors[colorIndex];
		}

		return res;
	}

	//method to get user guesses
	public char[] askUser(int guessNum) {
		Scanner scan = new Scanner(System.in);
		char res[] = new char[4];

		System.out.println("Guess #" + guessNum + ":");
		for (int i=0; i<4; i++) {
			System.out.print("Slot #" + (i+1) + ": (R,G,B,Y,O,P)\n> ");
			//gets the first letter of scan.next() to use as the user's guess
			res[i] = scan.next().toUpperCase().charAt(0);
			//check to make sure the user's guess is a valid color
			if ((res[i]!='R')&&(res[i]!='G')&&(res[i]!='B')&&(res[i]!='Y')&&(res[i]!='O')&&(res[i]!='P')) {
				System.out.println("\n" + res[i] + " is not a valid color option (R,G,B,Y,O,P)");
				//subtract 1 from i so that the user has to redo their guess if it isn't a valid guess
				i--;
			}
		}

		return res;
	}

	//check the user's guess
	public void checkGuess(char[] guess) {
		char[] secretCopy = new char[4];
		for (int i=0; i<secret.length; i++) {
			secretCopy[i] = secret[i];
		}
		int[] res = new int[4];

		//first checking to see if any pieces of the guess are the right color and in the right spot
		for (int i=0; i<4; i++) {
			if (guess[i] == secretCopy[i]) {
				res[i] = 1;
			}
		}

		//now checking to see if any pieces of the guess are the right color but in the wrong spot
		for (int i=0; i<4; i++) {
			//making sure none of the 1's get overwritten
			if (res[i] != 1) {
				for (int j=0; j<4; j++) {
					if (guess[i] == secretCopy[j]) {
						res[i] = 2;
						break;
					}
				}
			}
		}

		//loop to print out res (with tab spacing in between each)
		for (int i=0; i<4; i++) {
			System.out.print(res[i] + "\t");
		}
		System.out.println("\n");

		//the player wins if they get all of the colors right and in the right position
		if ((res[0] == 1) && (res[1] == 1) && (res[2] == 1) && (res[3] == 1)) win = true;
	}

	public void playGame() {
		//comments and/or instructions
		secret = genSecret();
		System.out.println("Secret Generated!\nKey: 0 = wrong color; 1 = right color, right position; 2 = right color, wrong position\n");
//		System.out.println("------------------SECRET:-------------------------");
//		for (int i=0; i<secret.length; i++) {
//			System.out.print(secret[i] + "\t");
//		}
//		System.out.println("\n--------------------------------------------------");

		int numGuesses = 0;
		//gets the user's guesses until they've either won or reached 10 guesses
		while ((numGuesses<10) && (!win)) {
			char[] guess = askUser(numGuesses+1);
			guesses[numGuesses] = guess;
			checkGuess(guess);
			numGuesses++;
		}
		
		if (win) {
			System.out.println("Congratulations! You won after " + numGuesses + " guess(es)!");
		} else {
			System.out.println("That's " + numGuesses + " guesses... Better luck next time?");
		}
	}
}
