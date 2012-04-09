package texas;
import java.util.ArrayList;
public class Dealer_Shoe
{
    ArrayList<Card> shoe = new ArrayList<Card>();
    DealtCards dealt = new DealtCards();
    HandChecker check = new HandChecker();
    Hands hands = new Hands();
    int e, decks, b = 0;
    public Dealer_Shoe()
    {
        decks = 8;
        for (int d=1; d <= 8; d++)
        {
            for (int b=0; b < 4; b++)
            {
                for (int a=1; a < 14; a++)
                {
                    shoe.add(new Card (a, b));
                }
            }
        }
        e = (int) (Math.random()*(shoe.size()));
        shoe.add(new Card(0));
    }
    public Dealer_Shoe(int f)
    {
        decks = f;
        for (int d=1; d <= decks; d++)
        {
            for (int b=0; b < 4; b++)
            {
                for (int a=1; a < 14; a++)
                {
                    shoe.add(new Card (a, b));
                }
            }
        }
        e = (int) (Math.random()*(shoe.size()));
        if (decks > 1)
            shoe.add(new Card(0));
    }
    public void shuffle()
    {
        for (int c=1; c < 25001; c++)
        {
            e = (int) (Math.random()*(shoe.size()));
            shoe.add(shoe.remove(e));
        }
    }
    public void deal()
    {
        Card a = shoe.remove(0);
        if (a.getFaceValue() == -1)
        {
            shoe.add(a);
            shuffle();
            deal();
            // System.out.println("Shuffle Card");
        }
        else if (b == 4 || b == 8 || b == 10)
        {
            b++;
            dealt.dealt(a);
            deal();
        }
        else
        {
            check.store(a);
            dealt.dealt(a);
            b++;
        }
    }
    public int numLeft()
    {
        return shoe.size();
    }
    public Card show(int g)
    {
        return dealt.show(g);
    }
    public void cardreturn()
    {
        Card[] cardreturn = dealt.newhand();
        check.newhand();
        for (int h=0; h<12; h++)
        {
            shoe.add(cardreturn[h]);
        }
        b = 0;
        if (decks == 1)
            shuffle();
    }
    public int CheckHand(String a)
    {   
        return check.CheckHand(a);
    }
    public int[] getArray(String arr)
    {
        return check.getArray(arr);
    }
    public String getHand(int val)
    {
        hands.unPack(check.getCards());
        return hands.getHand(val);
    }
    public Card[] getAvailCards()
    {
        return dealt.newhand();
    }
    public int[] getTP()
    {
        return check.getCards();
    }
}