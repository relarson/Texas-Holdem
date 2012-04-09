package texas;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class AI
{
    private Dealer_Shoe shoe = new Dealer_Shoe(1);
    private Kickers kicker = new Kickers();
    private boolean[] betrnd = new boolean[4];
    Card hole1 = null;
    Card hole2 = null;
    Card comp1 = null;
    Card comp2 = null;
    Card flop1 = null;
    Card flop2 = null;
    Card flop3 = null;
    Card turn = null;
    Card river = null;
    int phandvalue = -1, chandvalue = -1, kickval = 0;
    private double wins=0, loses=0, ties=0, total = 0;
    private double HS;
    private boolean fold = false, call = false; 
    private boolean raise0 = false, raise1 = false, raise2 = false, raise3 = false, raise4 = false, raise5 = false;
    String phand = new String();
    String chand = new String();
    
    public AI()
    {
    }
    public void newHand()
    { 
        shoe.shuffle();
        for (int j = 0 ; j < 4 ; j++)
            betrnd[j] = false; 
        total = 0;
        wins = 0;
        loses = 0;
        ties = 0;
    }
    public void sendHole(Card[] cd)
    {
        comp1 = cd[0];
        comp2 = cd[1];
        betrnd[0] = true;
        total = 0;
        wins = 0;
        loses = 0;
        ties = 0;
        //System.out.println(comp1 + "\n" + comp2);
    }
    public void sendFlop(Card[] crds)
    {
        flop1 = crds[0];
        flop2 = crds[1];
        flop3 = crds[2];
        betrnd[1] = true;
        total = 0;
        wins = 0;
        loses = 0;
        ties = 0;
        //System.out.println(flop1 + "\n" + flop2+ "\n" + flop3);
    }
    public void sendTurn(Card[] crds)
    {
        flop1 = crds[0];
        flop2 = crds[1];
        flop3 = crds[2];
        turn = crds[3];
        betrnd[2] = true;
        total = 0;
        wins = 0;
        loses = 0;
        ties = 0;
        //System.out.println(flop1 + "\n" + flop2+ "\n" + flop3 + "\n" + turn);
    }
    public void sendRiver(Card[] crds)
    {
        flop1 = crds[0];
        flop2 = crds[1];
        flop3 = crds[2];
        turn = crds[3];
        river = crds[4];
        betrnd[3] = true;
        total = 0;
        wins = 0;
        loses = 0;
        ties = 0;
        //System.out.println(flop1 + "\n" + flop2+ "\n" + flop3 + "\n" + turn + "\n" + river);
    }
    public String calc(int timesx)
    {
        int pert = (int) (Math.random()*100);
        int pert2 = (int) (Math.random()*100);
        int raiseamt = 0;
        HS = getHandStrength(timesx);
        int HandSt = (int) (HS*100);
        /**
        System.out.println("Total: " + total + ", Wins: " + wins + ", Loses: " + loses
                            + ", Ties: " + ties);
        System.out.println("HandSt: " + HandSt);
        System.out.println("%: " + pert);
        System.out.println("");
        */
        call = false;
        fold = false;
        raise0 = false;
        raise1 = false;
        raise2 = false;
        raise3 = false;
        raise4 = false;
        raise5 = false;
        if (HandSt < 20)
        {
            if (pert <=90)
                fold = true;
            else
            {
                raise5 = true;
            }
        }
        else if (HandSt < 45)
        {
            if (pert <=65)
                fold = true;
            else if ((pert > 65) && (pert <= 85))
                call = true;
            else
            {
                raise2 = true;
            }
        }
        else if (HandSt < 65)
        {
            if (pert <= 60)
                call = true;
            else
            {
                if (pert2 < 40)
                    raise0 = true;
                else if (pert2 < 65)
                    raise1 = true;
                else if (pert2 < 80)
                    raise2 = true;
                else if (pert2 < 90)
                    raise3 = true;
                else if (pert2 < 95)
                    raise4 = true;
                else
                    raise5 = true;
            }
        }
        else
        {
            if (pert <= 30)
                call = true;
            else
            {
                raise5 = true;
            }
        }
        if (fold)
        {
            //System.out.println("Fold");
            return "Fold";
        }
        else if (raise0)
            return "Raise0";
        else if (raise1)
            return "Raise1";
        else if (raise2)
            return "Raise2";
        else if (raise3)
            return "Raise3";
        else if (raise4)
            return "Raise4";
        else if (raise5)
            return "Raise5";
        else 
        {
            //System.out.println("Call");
            return "Call";
        }
    }
    public double getHandStrength(int times)
    {
    for (int i = 0 ; i < times ; i++)
    {
        for (int a=0; a<9; a++)
        {
            shoe.deal();
        }
        if (betrnd[3])
        {
            hole1 = shoe.show(0);
            hole2 = shoe.show(1);
        }
        else if (betrnd[2])
        {
            hole1 = shoe.show(0);
            hole2 = shoe.show(1);
            river = shoe.show(11);
        }
        else if (betrnd[1])
        {
            hole1 = shoe.show(0);
            hole2 = shoe.show(1);
            turn = shoe.show(9);
            river = shoe.show(11);
        }
        else
        {
            hole1 = shoe.show(0);
            hole2 = shoe.show(1);
            flop1 = shoe.show(5);
            flop2 = shoe.show(6);
            flop3 = shoe.show(7);
            turn = shoe.show(9);
            river = shoe.show(11);
        }
        phandvalue = shoe.CheckHand("Player");
        phand = shoe.getHand(phandvalue);
        chandvalue = shoe.CheckHand("Comp");
        chand = shoe.getHand(chandvalue);
        kicker.storeTP(shoe.getTP());
        kicker.sendCards(shoe.getAvailCards());
        shoe.cardreturn();
        if (phandvalue > chandvalue)
        {
            kicker.Done();
            wins++;
        }
        else if (chandvalue > phandvalue)
        {
            kicker.Done();
            loses++;
        }
        else
        {
            kicker.sendVal(phandvalue);
            kickval = kicker.getWinner();
            phand += kicker.toStringPlayer();
            chand += kicker.toStringComp();
            kicker.Done();
            if (kickval == -1)
                loses++;
            else if (kickval == 1)
                wins++;
            else ties++;
        }
    }
        total = wins + loses + ties;
        return wins/total;
    }
}
