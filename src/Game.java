import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


public class Game {

	public static int numSticks =12;
	static int compChoice;
	static int pChoice;
	static Random r = new Random();
	static int[][] learn = new int[4][12];
	static boolean winner=false;
	static int turn=1;
	static String fileName;
	static ArrayList<String> record = new ArrayList<String>();
	static String file;  //in case user types in a wrong file name;
	
	
	//consists the game implementaion of NIM
	public static void game() throws IOException{
		while(!winner){
			if(turn%2==1){
				System.out.println("It's the Computer Player's turn: ");
				System.out.println();
				if (numSticks>= 0){
					compChoice = bestMove();
					numSticks -= compChoice;
					System.out.println("The computer has chosen to take away " +compChoice + " sticks.");
					System.out.println("There are " + numSticks + " sticks left." );
					System.out.println();
					turn++;
				}
				if (numSticks == 0){
					winner = true;
					System.out.println();
					System.out.println("Well, you lost! :(:(:(:(:(");
					updatedArray(1);
				}

		}
			else if (turn % 2 == 0){
				
				System.out.println("It's your turn!");
				System.out.println("There are " + numSticks + " sticks left.");
				System.out.println();
				if (numSticks >= 4){
					System.out.println("Pick the number of sticks to take away from the pile. You can pick any number from 1 to 4!" );
					System.out.println();
					Scanner a = new Scanner(System.in);
					pChoice = a.nextInt();
					while (pChoice < 1 || pChoice> 4){
						System.out.println("ERROR! Invalid Choice! Pick a number from 1 to 4!");
						Scanner b = new Scanner(System.in);
						pChoice = b.nextInt();
					}
					
						numSticks -= pChoice;
						System.out.println("You took away " + pChoice + " sticks." );
						System.out.println("There are " + numSticks + " sticks left." );
						System.out.println();
						turn++;
						if (numSticks == 0){
							winner = true;
							updatedArray(-1);
							System.out.println();
							System.out.println("YAY!! you win!!");
						}
					
				
				}
				else if (numSticks == 3){
					System.out.println("Pick the number of sticks to take away from the pile. You can pick any number from 1 to 3!");
					Scanner a = new Scanner(System.in);
					pChoice = a.nextInt();
					System.out.println();
					while (pChoice < 1 || pChoice> 3){
						System.out.println("ERROR! Invalid Choice! Pick a number from 1 to 3!");
						Scanner b = new Scanner(System.in);
						pChoice = b.nextInt();
					}
					
						numSticks -= pChoice;
						System.out.println( "You took away " + pChoice + " sticks.");
						System.out.println("There are " + numSticks + " sticks left." );
						System.out.println();
						turn++;
						if (numSticks == 0){
							winner = true;
							updatedArray(-1);
							System.out.println("YAY!! you win!!");
						}
					
				}
				else if (numSticks == 2){
					System.out.println("Pick the number of sticks to take away from the pile. You can pick any number from 1 to 2!" );
					Scanner a = new Scanner(System.in);
					pChoice = a.nextInt();
					System.out.println();
					while (pChoice < 1 || pChoice> 2){
						System.out.println("ERROR! Invalid Choice! Pick a number from 1 to 3!" );
						Scanner b = new Scanner(System.in);
						pChoice = b.nextInt();
					}
					
					
						numSticks -= pChoice;
						System.out.println("You took away " + pChoice + " sticks." );
						System.out.println("There are " + numSticks + " sticks left." );
						System.out.println();
						turn++;
						
						if (numSticks == 0){
							winner = true;
							updatedArray(-1);
							System.out.println("YAY!! you win!!" );
						}
					
				}
				else if (numSticks == 1){
					System.out.println("Pick the number of sticks to take away from the pile. You can only pick the number 1!" );
					Scanner a = new Scanner(System.in);
					pChoice = a.nextInt();
					System.out.println();
					while (pChoice!=1){
						System.out.println("ERROR! Invalid Choice! You literally need to pick the number 1 to win this!");
						Scanner b = new Scanner(System.in);
						pChoice = b.nextInt();
					}

					
						numSticks -= pChoice;
						System.out.println("You took away the last stick." );
						System.out.println("There are no sticks left.");
						System.out.println();
							winner = true;
							updatedArray(-1);
							System.out.println("YAY!! you win!!");
						}
					
				}
		
			}
		
			/*for(int i=0;i<4;i++){
				for(int j=0;j<12;j++){
					System.out.print(learn[i][j]+" ");
				}
				System.out.println();
			}
			*/
		
		
			FileWriter x = new FileWriter(file);
			for(int i=0;i<4;i++){
				for(int j=0;j<12;j++){
					x.write(Integer.toString(learn[i][j]));
					x.write(" ");
					}
				x.write("\n");
				}
			x.close();
			
		}
	
	//This function updates the array based on who won, by incrementing or decrementing
	//the values of the learn array by 1.
	public static void updatedArray(int win){
		int r;
		int c;
		String[] l;
		for(int i=0; i<record.size(); i++){
			l=(record.get(i)).split(" ");
			r= Integer.parseInt(l[0]);
			c= Integer.parseInt(l[1]);
			
			learn[r][c]=learn[r][c]+ win;
		}
	}
	
	
	//This function calculates the best move for the computer everytime
	//based on what the computer has learned from previous games.
	
	public static int bestMove(){
		int cChoice=1; //the value is 1 beacuse the least we can choose is 1
		int r=3; //there are 4 rows
		int value = learn[r][numSticks-1];
		
		
		//find the largest payoff value in a particular column based on the number of sticks left
		for(int i=0; i<4;i++){
			if(learn[i][numSticks-1] > value){
			cChoice = 4-i; //computer will take these many stciks out
			r=i;
			value=learn[i][numSticks-1];
			}
		}
		
		//recording every move the computer makes into an arraylist of strings
		record.add(Integer.toString(r)+" "+Integer.toString(numSticks-1));
		
		return cChoice;
	}
	
	
	//This function resets the Array in the file to initial state.
	public static void cSlate() throws IOException{
		FileWriter right = new FileWriter(fileName);
		
		right.write("-1 -1 -1 1 0 0 0 0 0 0 0 0"+"\n");
		right.write("-1 -1 1 -1 0 0 0 0 0 0 0 0"+"\n");
		right.write("-1 1 -1 -1 0 0 0 0 0 0 0 0"+"\n");
		right.write("1 -1 -1 -1 0 0 0 0 0 0 0 0"+"\n");
		
		right.close();
	}

//main function
	//call game() function
	public static void main(String[] args) throws IOException {
		
		Scanner s = new Scanner(System.in);
		System.out.println("Enter the game text file");
		fileName = s.nextLine();
		file=fileName;
		
		//checking if the file entered exists or not
		//if it doesn't exist, the default file with a clean slate of values will be used
		try {
			FileReader f = new FileReader(fileName);
			Scanner read= new Scanner(f);
			String l="";
			String[] la;
			
			for(int i=0;i<4;i++){
				l=read.nextLine();
				la=l.split(" ");
				for(int j = 0; j<la.length; j++){
					learn[i][j]=Integer.parseInt(la[j]);
				}
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("No such directory exists!");
			fileName= "default.txt";
			
		}
		game();
		
		System.out.println("Getting Tough to win yet?");
		System.out.println("Type x to reset the difficulty of the game!");
		System.out.println("Or you can restart the game with similar difficulty");
		Scanner a = new Scanner(System.in);
		if(a.nextLine().equals("x")){
			cSlate();
			System.out.println("Run it again");
		}
		
		
	}

}
