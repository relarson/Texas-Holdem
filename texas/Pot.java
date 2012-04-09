package texas;
import java.awt.*;
import javax.swing.*;

public class Pot extends JComponent
{
    private int amount, ante, bet, split, temp;
    Money chips = new Money();
    Font bigFont;
    public Pot(int a)
    {
        amount = 0;
        ante = a;
    }
    public void ante()
    {
        amount += (ante*2);
        chips.remove(ante);
    }
    public String anteAmt()
    {
        String anteAmt = "$" + ante;
        return anteAmt;
    }
    public void bet(int b)
    {
        bet = b;
        if (bet > chips.chipstack())
            bet = chips.chipstack();
        amount += bet;
        chips.remove(bet);
    }
    public void bet(double b)
    {
        bet = (int) (b + .5);
        if (bet > chips.chipstack())
            bet = chips.chipstack();
        amount += bet;
        chips.remove(bet);
    }
    public void check()
    {
        bet = 0;
    }
    public void call()
    {
        amount += bet;
    }
    public void pay()
    {
        chips.add(amount);
        amount = 0;
    }
    public void compRaise(int craise) //value checked to be under chipstack() at usage
    {
        amount += (2*craise);
        chips.remove(craise);
    }            
    public void reset()
    {
        amount = 0;
    }
    public void splitpot()
    {
        chips.add(amount - (amount/2));
        amount = 0;
    }
    public int potsize()
    {
        return amount;
    }
    public int chipstack()
    {
        return chips.chipstack();
    }
    public void loan(double a)
    {
        int d = (int) (a + .5);
        if (d > 500)
            d = 500;
        chips.loan(d);
    }
    public int getProfit()
    {
        return chips.getProfit();
    }
    public String getProfitString()
    {
        String profitString = "$" + Math.abs(getProfit());
        if (getProfit() < 0)
            return "-" + profitString;
        else
            return profitString;
    }
    public void init(){
        bigFont = new Font("Arial",Font.BOLD,26);
        repaint();
    }
    public void paintComponent(Graphics g)
    {
        g.setColor(Color.WHITE);
        g.setFont(bigFont);
        g.drawString("Chips Left: $" + chipstack(), 100,20);
        g.drawString("Profit: " + getProfitString(), 385,20);
        g.drawString("Amt. in Pot: $" + potsize(), 600,20);
    }
}
        