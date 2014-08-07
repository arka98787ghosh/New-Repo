public class Hand {
	//remember to reassign these two to zero somewhere later too
	static int fullOrFour;
	static int twoOrThree;
	static int three;
	
	public static int sameSuitCounter(int[] suitIndicator){
		int sameSuitIndicator = 0;
		for(int i=0; i<4 ; i++){
			if(suitIndicator[i] == suitIndicator[i+1])
				sameSuitIndicator++;
		}
		
		return sameSuitIndicator;
	}
	
	public static int sameKindCounter(int[] kindIndicator){
		int sameKindIndicator = 0;
		twoOrThree = 0;
		fullOrFour = 0 ;
		three = 0;
		for(int i=0; i< 4 ; i++){
			if(kindIndicator[i] == kindIndicator[i+1]){
				sameKindIndicator++;
				twoOrThree++;
			}		
		}
		for(int i=0; i< 3 ; i++){
			if(kindIndicator[i] == kindIndicator[i+1] && kindIndicator[i+1] == kindIndicator[i+2]){
				three++;
			}		
		}
		if(kindIndicator[0] != kindIndicator[1] || kindIndicator[3] == kindIndicator[4])
			fullOrFour = 4;
		return sameKindIndicator;
	}
	
	public static boolean straightIndicator(int[] indicesArray){
		int first = indicesArray[0];
		int counter = 0;
		for(int i=0 ; i<5 ; i++){
			if(indicesArray[i] == first++)
				counter++;
		}
		
		if(counter == 5)
			return true;
		else
			return false;
	}
	
	public static boolean royalStraightIndicator(int[] indicesArray){
		int first = indicesArray[0];
		int counter = 0;
		for(int i=0 ; i<5 ; i++){
			if(indicesArray[i] == first++)
				counter++;
		}
		
		if(counter == 5 && first == 8)
			return true;
		else
			return false;
	}
	
	public static boolean isRoyalFlush(int[] kindIndicesArray,int[] suitIndicesArray){
		if(sameSuitCounter(suitIndicesArray) == 4 && royalStraightIndicator(kindIndicesArray))
			return true;
		else
			return false;
	}
	
	public static boolean isStraightFlush(int[] kindIndicesArray,int[] suitIndicesArray){
		if(sameSuitCounter(suitIndicesArray) == 4 && straightIndicator(kindIndicesArray))
			return true;
		else
			return false;
	}
	
	public static boolean isFourOfAKind(int[] indicesArray){
		if(sameKindCounter(indicesArray) == 3 && fullOrFour == 4)
			return true;
		else 
			return false;
	}
	
	public static boolean isFullHouse(int[] indicesArray){
		if(sameKindCounter(indicesArray) == 3 && fullOrFour != 4)
			return true;
		else
			return false;
	}
	
	public static boolean isFlush(int[] suitIndicesArray){
		if(sameSuitCounter(suitIndicesArray) == 4)
			return true;
		else 
			return false;
	}
	
	public static boolean isStraight(int[] indicesArray){
		if(straightIndicator(indicesArray))
			return true;
		else
			return false;
	}
	
	public static boolean isThreeOfAKind(int[] indicesArray){
		if(sameKindCounter(indicesArray) == 2 && twoOrThree == 2 && three == 1)
			return true;
		else
			return false;
	}
	
	public static boolean isTwoPairs(int[] indicesArray){
		if(sameKindCounter(indicesArray) == 2 && twoOrThree == 2)
			return true;
		else 
			return false;
	}
	
	public static boolean isOnePair(int[] indicesArray){
		if(sameKindCounter(indicesArray) == 1 && twoOrThree == 1)
			return true;
		else
			return false;
	}
	
	public static boolean isHighCard(){
		return true;
	}
}
