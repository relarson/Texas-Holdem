package texas;
public class DealtCards 
{
    private int a=0;
    Card[] dealt = new Card[12]; 
    // spots 0&1=human 2&3=comp 4,8,&10=burnt 5-7=flop 9=turn 11=river
    Card[] burnt = new Card[3];
    public DealtCards()
    {  
    }
    public void dealt(Object obj)
    {
        dealt[a] = (Card) obj;
        a++;
        if (a >=12)
            a = 0;
    }
    public Card show(int b)
    {
        return dealt[b];
    }
    public Card[] newhand()
    {
        return dealt;
    }
}
	    