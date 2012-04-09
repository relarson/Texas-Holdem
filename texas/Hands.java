package texas;
public class Hands
{
    String[] hands = {"High Card", "Pair", "Two Pair", "Three of a Kind",
                            "Straight", "Flush", "Full House", "Four of a Kind",
                            "Straight Flush", "Royal Flush"};
    String[] straights = {"(A-5)", "(2-6)", "(3-7)", "(4-8)", "(5-9)",
                         "(6-10)", "(7-J)", "(8-Q)", "(9-K)", "(10-A)"};
    String[] cards = {"(2's)", "(3's)", "(4's)", "(5's)", "(6's)", "(7's)", "(8's)",
                        "(9's)", "(10's)", "(J's)", "(Q's)", "(K's)", "(A's)"};
    String[] cards2 = {"2", "3", "4", "5", "6", "7", "8",
                        "9", "10", "J", "Q", "K", "A"};
    int[] fHouse = new int[2];
    int[] tPair = new int[2];
    private int pc1, pc2, fc1, fc2;
    public Hands()
    {
    }
    public void unPack(int[] arr)
    {
        pc1 = arr[0]-1;
        pc2 = arr[1]-1;
        fc1 = arr[2]-1;
        fc2 = arr[3]-1;
    }
    public String getHand(int val)
    {
        if (val == 309)
            return hands[9];
        else if ((val < 309) && (val > 299))
            return hands[8] + straights[val-300];
        else if ((val < 300) && (val > 286))
            return hands[7] + cards[val-287];
        else if ((val < 287) && (val > 129))
        {
            if (fc1 < 0 || fc2 < 0)
                return "Error(FH)";
            else
                return hands[6] + "(" + cards2[fc1] + "'s full of " + cards2[fc2] + "'s)";
        }
        else if ((val < 130) && (val > 121))
            return hands[5] + "(" + cards2[val-117] + " high)";
        else if ((val < 122) && (val > 111))
            return hands[4] + straights[val-112];
        else if ((val < 112) && (val > 98))
            return hands[3] + cards[val-99];
        else if ((val < 99) && (val > 20))
        {
            if ((pc1 < 0) || (pc2 < 0))
                return "Error(2P)";
            else
                return hands[2] + "(" + cards2[pc1] + "'s and " + cards2[pc2] + "'s)";
        }
        else if ((val < 21) && (val > 7))
            return hands[1] + cards[val-8];
        else if ((val < 8) && (val > -1))
            return hands[0] + "(" + cards2[val+5] + ")";
        else return "Error(val<0)";        
    }   
}