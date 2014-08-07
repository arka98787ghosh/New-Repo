import java.io.FileNotFoundException;

public class Game {
	//static String[] playerOneHand = new String[5];
	//static String[] playerTwoHand = new String[5];
	static String[] hands = new String[10];
	static int winCountPlayerOne = 0;
	static int winCountPlayerTwo = 0;
	static int losing = 0;
	
	public static void main(String args[]) throws FileNotFoundException {
		ReadHands.hands();
		for (int i = 0; i < 1000; i++) {
			hands = ReadHands.getOneHand(i);
			//Hand.what(playerOneHand);
			//playerOneHighCard = Hand.getHighCardValue();
			//System.out.println(Hand.id);
			//Hand.what(playerTwoHand);
			//playerTwoHighCard = Hand.getHighCardValue();
			//System.out.println(Hand.id);
			/*
			 * if(Hand.what(playerOneHand) < Hand.what(playerTwoHand))
			 * winCountPlayerOne++; else if(Hand.what(playerOneHand) >
			 * Hand.what(playerTwoHand)) winCountPlayerOne+=0; else if(
			 * playerOneHighCard > playerTwoHighCard) winCountPlayerOne++;
			 
			for(int j = 0; j<10 ;j++){
				System.out.print(hands[j]);
			}
			System.out.println();
			*/
			Winner.initialize(hands);
			Winner.winner();
			
			if(Winner.playerOne){
				winCountPlayerOne++;
			}else if(Winner.playerTwo){
				winCountPlayerTwo++;
			}
			
			if(Winner.losing)
				losing++;
			
		}
		System.out.println(winCountPlayerOne+";"+winCountPlayerTwo+";"+losing+";"+Winner.numId);
	}
}
