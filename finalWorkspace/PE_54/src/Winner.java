import java.util.Arrays;

public class Winner {
	static char[] kind = { '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J',
			'Q', 'K', 'A' };
	static char[] suits = { 'C', 'D', 'H', 'S' };
	static char[] kind1 = new char[5];
	static int[] kind1Indices = new int[5];
	static char[] suit1 = new char[5];
	static int[] suit1Indices = new int[5];
	static char[] kind2 = new char[5];
	static int[] kind2Indices = new int[5];
	static char[] suit2 = new char[5];
	static int[] suit2Indices = new int[5];
	static boolean playerOne;
	static boolean playerTwo;
	static boolean losing;
	static int highestCardPointer = 4;
	static int id;
	static int numId = 0;

	public static void initialize(String[] hands) {
		playerOne = false;
		playerTwo = false;
		losing = false;
		highestCardPointer = 4;
		for (int i = 0; i < hands.length; i++) {
			if (i < 5) {
				kind1[i] = hands[i].charAt(0);
				// System.out.print(kind1[i]);
				suit1[i] = hands[i].charAt(1);
				// System.out.print(suit1[i]);
			} else {
				kind2[i - 5] = hands[i].charAt(0);
				// System.out.print(kind2[i-5]);
				suit2[i - 5] = hands[i].charAt(1);
				// System.out.print(suit2[i-5]);
			}
		}
		// System.out.println();

		for (int i = 0; i < kind1.length; i++) {
			for (int j = 0; j < kind.length; j++) {
				if (kind1[i] == kind[j])
					kind1Indices[i] = j;
			}
		}

		for (int i = 0; i < kind2.length; i++) {
			for (int j = 0; j < kind.length; j++) {
				if (kind2[i] == kind[j])
					kind2Indices[i] = j;
			}
		}

		for (int i = 0; i < suit1.length; i++) {
			for (int j = 0; j < suits.length; j++) {
				if (suit1[i] == suits[j])
					suit1Indices[i] = j;
			}
		}

		for (int i = 0; i < suit2.length; i++) {
			for (int j = 0; j < suits.length; j++) {
				if (suit2[i] == suits[j])
					suit2Indices[i] = j;
			}
		}
		Arrays.sort(kind1Indices);
		Arrays.sort(kind2Indices);
		Arrays.sort(suit1Indices);
		Arrays.sort(suit2Indices);
	}

	public static int getId(int[] kindIndicesArray, int[] suitIndicesArray) {
		numId++;
		if (Hand.isRoyalFlush(kindIndicesArray, suitIndicesArray)) {
			id = 9;
		} else if (Hand.isStraightFlush(kindIndicesArray, suitIndicesArray)) {
			id = 8;
		} else if (Hand.isFourOfAKind(kindIndicesArray)) {
			id = 7;
		} else if (Hand.isFullHouse(kindIndicesArray)) {
			id = 6;
		} else if (Hand.isFlush(suitIndicesArray)) {
			id = 5;
		} else if (Hand.isStraight(kindIndicesArray)) {
			id = 4;
		} else if (Hand.isThreeOfAKind(kindIndicesArray)) {
			//System.out.println("reaching");
			id = 3;
		} else if (Hand.isTwoPairs(kindIndicesArray)) {
			id = 2;
		} else if (Hand.isOnePair(kindIndicesArray)) {
			id = 1;
		} else if (Hand.isHighCard()) {
			id = 0;
		}

		return id;
	}

	public static void winner() {
		int id1 = getId(kind1Indices, suit1Indices);
		int id2 = getId(kind2Indices, suit2Indices);
		// System.out.println(id1+";;;;"+id2);
		if (id1 > id2) {
			playerOne = true;
		} else if (id1 == id2) {
			if (id1 == 1) {
				int pairId1 = 0;
				int pairId2 = 0;
				for (int i = 0; i < 4; i++) {
					if (kind1Indices[i] == kind1Indices[i + 1]) {
						pairId1 = kind1Indices[i];

					}
					if (kind2Indices[i] == kind2Indices[i + 1]) {
						pairId2 = kind2Indices[i];

					}
				}
				if (pairId1 > pairId2)
					playerOne = true;
				else if (pairId1 < pairId2)
					playerTwo = true;
				else {
					// System.out.println("reaching here");
					for (int i = 4; i >= 0; i--) {
						if (kind1Indices[i] > kind2Indices[i]) {
							playerOne = true;
							break;
						} else if (kind1Indices[i] < kind2Indices[i]) {
							playerTwo = true;
							break;
						}
					}
				}

			} else if (id2 == 2) {

				System.out.println("reaching here");
				for (int i = 4; i >= 0; i--) {
					if (kind1Indices[i] > kind2Indices[i]) {
						playerOne = true;
						break;
					} else if (kind1Indices[i] < kind2Indices[i]) {
						playerTwo = true;
						break;
					}
				}
				for (int i = 4; i >= 0; i--) {
					System.out.print(kind1Indices[i]);
				}
				System.out.println();
				for (int i = 4; i >= 0; i--) {
					System.out.print(kind2Indices[i]);
				}
				System.out.println();
			} else {
				for (int i = 4; i >= 0; i--) {
					if (kind1Indices[i] > kind2Indices[i]) {
						playerOne = true;
						break;
					} else if (kind1Indices[i] < kind2Indices[i]) {
						playerTwo = true;
						break;
					}

				}
			}

		} else
			playerTwo = true;
	}
}
