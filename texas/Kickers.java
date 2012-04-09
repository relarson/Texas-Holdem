package texas;
public class Kickers
{
    private int winner, value, numc;
    private Card[] phand = new Card[7];
    private Card[] chand = new Card[7];
    private int[] pface = new int[13];
    private int[] cface = new int[13];
    private int[] pks = new int[4]; // max possible kickers = 4 (with High card)
    private int[] cks = new int[4]; // same as pks, but for comp, not player
    private int[] tp = new int[4];
    private String kick = ", ";
    String[] cards = {"2", "3", "4", "5", "6", "7", "8",
                        "9", "10", "J", "Q", "K", "A"};
    
    public Kickers()
    {
        
    }
    public void sendVal(int a)
    {
        value = a;
        for (int v : pks)
            v = -1;
        for (int w : cks)
            w = -1;
        if ((value >= 300) || ((value >= 112) && (value <= 286)))
            numc = 0;
        else if ((value >= 287) && (value <= 299))
            numc = 1;
        else if (value >= 99)
            numc = 2;
        else if (value >= 21)
            numc = 1;
        else if (value >= 8)
            numc = 3;
        else numc = 4;
    }
    public void sendCards(Card[] cards)
    {
        for (int p = 0 ; p < 7 ; p++)
        {
            if (p < 2)
            {
                phand[p] = cards[p];
                chand[p] = cards[p+2];
            }
            else if ((p >= 2) && (p <= 4))
            {
                phand[p] = cards[p+3];
                chand[p] = cards[p+3];
            }
            else if (p == 5)
            {
                phand[p] = cards[p+4];
                chand[p] = cards[p+4];
            }
            else
            {
                phand[p] = cards[p+5];
                chand[p] = cards[p+5];
            }
        }
        split(phand, chand);
    }
    public void split(Card[] pcrds, Card[] ccrds)
    {
        for (Card c : pcrds)
            pface[c.getFaceValue()-1] ++;
        for (Card c : ccrds)
            cface[c.getFaceValue()-1] ++;
        /*
        System.out.print("\nPface-Kick: ");
        for (int pfv : pface)
            System.out.print(pfv + " ");
        System.out.print("\nCface-Kick: ");
        for (int cfv : cface)
            System.out.print(cfv + " ");
        System.out.print("\n");
        */
           
    }
    public int getWinner()
    {
        if ((value >= 300) || ((value >= 112) && (value <= 286)))
            return 0;
        else if ((value >= 287) && (value <= 299))
            return FourKind();
        else if (value >= 99)
            return ThreeKind();
        else if (value >= 21)
            return TwoPair();
        else if (value >= 8)
            return Pair();
        else return HighCard(value);
    }
    public String toStringPlayer()
    {
        /*
        System.out.print("\nPks: ");
        for (int at : pks)
            System.out.print (at + " ");
        System.out.print("\nCks: ");
        for (int at : cks)
            System.out.print (at + " ");
        System.out.print("\n");
        */
       
        kick = ", ";
        for (int ct = 0 ; ct < numc ; ct++)
            kick += cards[pks[ct]-1] + " ";
        kick += "kicker";
        if (numc > 1)
            kick += "s";
        if (numc == 0)
            return "";
        else return kick;
    }
    public String toStringComp()
    {
        kick = ", ";
        for (int ct = 0 ; ct < numc ; ct++)
            kick += cards[cks[ct]-1] + " ";
        kick += "kicker";
        if (numc > 1)
            kick += "s";
        if (numc == 0)
            return "";
        else return kick;
    }
    public void Done()
    {
        for (int pf = 0; pf < 13 ; pf ++)
            pface[pf] = 0;
        for (int cf = 0; cf < 13 ; cf ++)
            cface[cf] = 0;
    }
    public void storeTP(int[] arr)
    {
         tp = arr;   
    }
    public int FourKind() // watch could be multiple of kickers
    {
        for (int i = 1 ; i < 13 ; i++)
        {
            if ((pface[i] > 0) && (pface[i] < 4))
                pks[0] = i;
            if ((cface[i] > 0) && (cface[i] < 4))
                cks[0] = i;
        }
        if ((pface[0] > 0) && (pface[0] < 4))
            pks[0] = 13;
        if ((cface[0] > 0) && (cface[0] < 4))
            cks[0] = 13;
        if (pks[0] > cks[0])
            return 1;
        else if (cks[0] > pks[0])
            return -1;
        else return 0;
    }
    public int ThreeKind() // can't have two of same amount, otherwise it would be Full House, not ToK
    {
        boolean Pstop = false, Cstop = false;
        int pfirst = -1, cfirst = -1;
        for (int i = 12 ; i > 0 ; i--)
        {
                if ((pface[i] == 1) && !Pstop)
                {
                    pks[0] = i;
                    pfirst = i;
                    Pstop = true;
                }
                if ((cface[i] == 1) && !Cstop)
                {
                    cks[0] = i;
                    cfirst = i;
                    Cstop = true;
                }
        }
        for (int i = 12 ; i > 0 ; i--)
        {
                if ((pface[i] == 1) && (i != pfirst) && !Pstop)
                {
                    pks[1] = i;
                    Pstop = true;
                }
                if ((cface[i] == 1) && (i != cfirst) && !Cstop)
                {
                    cks[1] = i;
                    Cstop = true;
                }
        }
        if (pface[0] == 1)
        {
            pks[1] = pks[0];
            pks[0] = 13;
        }
        if (cface[0] == 1)
        {
            cks[1] = cks[0];
            cks[0] = 13;
        }
        if (pks[0] > cks[0])
            return 1;
        else if (cks[0] > pks[0])
            return -1;
        else if (pks[1] > cks[1])
            return 1;
        else if (cks[1] > pks[1])
            return -1;
        else return 0;
    }
    public int TwoPair() // can only be one kicker picked, it can have multiples (only 2),
    {                           // however it cannot match one in the two pair.
        int fpair = -1, spair = -1;
        fpair = tp[0];
        spair = tp[1];
        for (int i = 1 ; i < 13 ; i++)
        {
            if ((pface[i] == 1) || ((pface[i] == 2) && (i != fpair) && (i != spair)))
                pks[0] = i;
            if ((cface[i] == 1) || ((cface[i] == 2) && (i != fpair) && (i != spair)))
                cks[0] = i;
        }
        if ((pface[0] == 1) || ((pface[0] == 2) && (fpair != 13)))
            pks[0] = 13;
        if ((cface[0] == 1) || ((cface[0] == 2) && (fpair != 13)))
            cks[0] = 13;
        if (pks[0] > cks[0])
            return 1;
        else if (cks[0] > pks[0])
            return -1;
        else return 0;
    }
    public int Pair() // 3 kcikers picked, none can have multiples
    {
        int pfir = 0 , psec = 0 , cfir = 0 , csec = 0 ;
        for (int f = 1 ; f < 13 ; f ++)
        {
            if (pface[f] == 1)
            {
                pks[0] = f;
                pfir = f;
            }
            if (cface[f] == 1)
            {
                cks[0] = f;
                cfir = f;
            }
        }
        for (int g = 1 ; g < 13 ; g ++)
        {
            if ((pface[g] == 1) && (g != pfir))
            {
                pks[1] = g;
                psec = g;
            }
            if ((cface[g] == 1) && (g != cfir))
            {
                cks[1] = g;
                csec = g;
            }
        }
        for (int k = 1 ; k < 13 ; k ++)
        {
            if ((pface[k] == 1) && (k != psec) && (k != pfir))
                pks[2] = k;
            if ((cface[k] == 1) && (k != csec) && (k != cfir))
                cks[2] = k;
        }
        if (pface[0] == 1)
        {
            pks[2] = pks[1];
            pks[1] = pks[0];
            pks[0] = 13;
        }
        if (cface[0] == 1)
        {
            cks[2] = cks[1];
            cks[1] = cks[0];
            cks[0] = 13;
        }
        if (pks[0] > cks[0])
            return 1;
        else if (cks[0] > pks[0])
            return -1;
        else if (pks[1] > cks[1])
            return 1;
        else if (cks[1] > pks[1])
            return -1;
        else if (pks[2] > cks[2])
            return 1;
        else if (cks[2] > pks[2])
            return -1;
        else return 0;
        
    }
    public int HighCard(int val) // 4 kickers picked, no multiples
    {
        int pfir = 0 , psec = 0 , pthi = 0 , cfir = 0 , csec = 0 , cthi = 0;
        for (int f = 1 ; f < (val + 6) ; f ++)
        {
            if (pface[f] == 1)
            {
                pks[0] = f;
                pfir = f;
            }
            if (cface[f] == 1)
            {
                cks[0] = f;
                cfir = f;
            }
        }
        for (int g = 1 ; g < (val + 6) ; g ++)
        {
            if ((pface[g] == 1) && (g != pfir))
            {
                pks[1] = g;
                psec = g;
            }
            if ((cface[g] == 1) && (g != cfir))
            {
                cks[1] = g;
                csec = g;
            }
        }
        for (int h = 1 ; h < (val + 6) ; h ++)
        {
            if ((pface[h] == 1) && (h != psec) && (h != pfir))
            {
                pks[2] = h;
                pthi = h;
            }
            if ((cface[h] == 1) && (h != csec) && (h != cfir))
            {
                cks[2] = h;
                cthi = h;
            }
        }
        for (int k = 1 ; k < (val + 6) ; k ++)
        {
            if ((pface[k] == 1) && (k != pthi) && (k != psec) && (k != pfir))
                pks[3] = k;
            if ((cface[k] == 1) && (k != cthi) && (k != csec) && (k != cfir))
                cks[3] = k;
        }
        if (pks[0] > cks[0])
            return 1;
        else if (cks[0] > pks[0])
            return -1;
        else if (pks[1] > cks[1])
            return 1;
        else if (cks[1] > pks[1])
            return -1;
        else if (pks[2] > cks[2])
            return 1;
        else if (cks[2] > pks[2])
            return -1;
        else if (pks[3] > cks[3])
            return 1;
        else if (cks[3] > pks[3])
            return -1;
        else return 0;
        
    }
}