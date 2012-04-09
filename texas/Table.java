package texas;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;
import java.lang.ClassLoader;

public class Table extends JComponent
{
    private Dealer_Shoe shoe = new Dealer_Shoe(1);
    private Kickers kicker = new Kickers();
    Color tableColor;
    Color logoColor;
    Font hugeFont;
    Font bigFont;
    Font mediumFont;
    boolean[] test = new boolean[9]; //0 = win, 1 = lose, 2 = tie, 3 = flop, 4 = turn, 5 = river
                                       //6 = hand finished, 7 = player hand, 8 = comp hand
    /*
     *  List of possible names for the Computer opponent.
     *  HAL = HAL 9000 from 2001: A Space Oddessy
     *  WOPR = Program from War Games
     *  Deep Blue = Chess Engine, played Gary Kasparov
     *  Ross is me
     *  Sam, Sidney, Mr. Kintzel, and Tyler are all people I know.
     *  The Turk = Legend of an early chess engine, proven to not have existed
     *      name of Chess engine on "Terminator: The Sarah Connor Chronicles"
     *  Skynet = Super Smart Machine that takes over world in Terminator movies
     *  Chinook = "unbeatable" checkers engine
     *  Paul A. Foerster = Math book Writer {read: Super nerd}, Super Genius, has 
     *      terrible names for his people in MAth problems (Ella Vader, really?)
     */
    String[] Opponents = {"HAL", "WOPR", "Deep Blue", "Ross", "Sam", "Sidney",
                            "Mr. Kintzel", "Tyler", "The Turk", "Skynet", "Chinook",
                            "Paul A. Foerster"};
    String player = "";
    String comp = getComp();
    String ph = "";
    String ch = "";
        
    Image back;
    
    Card hole1 = null;
    Card hole2 = null;
    Card comp1 = null;
    Card comp2 = null;
    Card flop1 = null;
    Card flop2 = null;
    Card flop3 = null;
    Card turn = null;
    Card river = null;
    int phandvalue = -1, chandvalue = -1;
    String phand = new String();
    String chand = new String();
    
    public void init()
    {
        tableColor = new Color(0,64,0);
        logoColor = new Color(0,0,200);
        hugeFont = new Font("Arial",Font.BOLD,36);
        bigFont = new Font("Arial",Font.BOLD,26);
        mediumFont = new Font("Arial",Font.BOLD,20);
        for(boolean a : test)
            a = false;
        back = new ImageIcon("redback.jpg").getImage();
        System.out.println(new ImageIcon("redback.jpg").getDescription());
    }
    public String getComp()
    {
        return Opponents[(int) (Math.random()*Opponents.length)];
    }
    public void playerName(String str)
    {
        player = str;
    }
    public void loadImage(BufferedImage pic, String picname)
    {
        try {
           pic = ImageIO.read(new File(picname));
       } catch (IOException e) {
       }
    }
    public Card[] sendComp()
    {
        Card[] comp = new Card[2];
        comp[0] = comp1;
        comp[1] = comp2;
        return comp;
    }
    public Card[] sendFlop()
    {
        Card[] comp = new Card[3];
        comp[0] = flop1;
        comp[1] = flop2;
        comp[2] = flop3;
        return comp;
    }
    public Card[] sendTurn()
    {
        Card[] comp = new Card[4];
        comp[0] = flop1;
        comp[1] = flop2;
        comp[2] = flop3;
        comp[3] = turn;
        return comp;
    }
    public Card[] sendRiver()
    {
        Card[] comp = new Card[5];
        comp[0] = flop1;
        comp[1] = flop2;
        comp[2] = flop3;
        comp[3] = turn;
        comp[4] = river;
        return comp;
    }
    public void newHand()
    {
        for(int a = 0 ; a < 9 ; a++) // the for(boolean a : test) a = false; wasn't working
            test[a] = false;        // so i switch to the "old-school" way and it worked
        shoe.shuffle();
        for (int a=0; a<9; a++)
        {
            shoe.deal();
        }
        hole1 = shoe.show(0);
        hole2 = shoe.show(1);
        comp1 = shoe.show(2);
        comp2 = shoe.show(3);
        flop1 = shoe.show(5);
        flop2 = shoe.show(6);
        flop3 = shoe.show(7);
        turn = shoe.show(9);
        river = shoe.show(11);
        //System.out.println ("\n" + hole1 + "\n" + hole2 + "\n \n" + comp1 + "\n" + comp2 + "\n \n"
        //                   + flop1 + "\n" + flop2 + "\n" + flop3 + "\n" + turn + "\n" + river + "\n");
        phandvalue = shoe.CheckHand("Player");
        phand = shoe.getHand(phandvalue);
        // System.out.println(phand + ": " + phandvalue);
        chandvalue = shoe.CheckHand("Comp");
        chand = shoe.getHand(chandvalue);
        kicker.storeTP(shoe.getTP());
        // System.out.println(chand + ": " + chandvalue);
        kicker.sendCards(shoe.getAvailCards());
        shoe.cardreturn();
    }
    public int getResult()
    {
        int kickval = 0;
        if (phandvalue > chandvalue)
        {
            kicker.Done();
            return win();
        }
        else if (chandvalue > phandvalue)
        {
            kicker.Done();
            return lose();
        }
        else
        {
            kicker.sendVal(phandvalue);
            kickval = kicker.getWinner();
            phand += kicker.toStringPlayer();
            chand += kicker.toStringComp();
            kicker.Done();
            if (kickval == -1)
                return lose();
            else if (kickval == 1)
                return win();
            else return tie();
        }
    }
    public int win()
    {
        test[0] = true;
        return 1;
    }
    public int lose()
    {
        test[1] = true;
        return -1;
    }
    public int tie()
    {
        test[2] = true;
        return 0;
    }
    public void flop()
    {
        test[3] = true;
    }
    public void turn()
    {
        test[4] = true;
    }
    public void river()
    {
        test[5] = true;
    }
    public void handFinished()
    {
        test[6] = true;
    }
    public void displayHands()
    {
        test[7] = true;
        test[8] = true;
    }
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.setColor(tableColor);
        g.fillRect(200,30,500,500);
        g.fillArc(75,30,250,500,90,180);
        g.fillArc(575,30,250,500,270,180);
        g.setColor(Color.BLACK);
        g.fillRect(375,55,75,100); // comp hole 1
        g.fillRect(450,55,75,100); // comp hole 2
        g.fillRect(375,405,75,100); // player hole 1
        g.fillRect(450,405,75,100); // player hole 2
        g.fillRect(213,230,75,100); // flop 1
        g.fillRect(313,230,75,100); // flop 2
        g.fillRect(413,230,75,100); // flop 3
        g.fillRect(513,230,75,100); // turn
        g.fillRect(613,230,75,100); // river
        g.setFont(hugeFont);
        g.setColor(Color.CYAN);
        g.drawString("Ross Larson",342,215);
        g.drawString("JAVA Casino",340, 371);
        g.setFont(bigFont);
        g.setColor(Color.RED);
        g.drawString(comp,450 - ((g.getFontMetrics(bigFont).stringWidth(comp))/2),20);
        g.drawString(player,450 - ((g.getFontMetrics(bigFont).stringWidth(player))/2), 555);
        g.setColor(Color.BLACK);
        if (test[5])
        {
            g.drawImage(back,375,55,75,100,this); // comp 1
            g.drawImage(back,450,55,75,100,this); // comp2
            hole1.setCoOrdinates(375,405);
            hole1.paintComponent(g);
            hole2.setCoOrdinates(450,405);
            hole2.paintComponent(g);
            flop1.setCoOrdinates(213,230);
            flop1.paintComponent(g);
            flop2.setCoOrdinates(313,230);
            flop2.paintComponent(g);
            flop3.setCoOrdinates(413,230);
            flop3.paintComponent(g);
            turn.setCoOrdinates(513,230);
            turn.paintComponent(g);
            river.setCoOrdinates(613,230);
            river.paintComponent(g);
        }
        else if (test[4])
        {
            g.drawImage(back,375,55,75,100,this); // comp1
            g.drawImage(back,450,55,75,100,this); // comp2
            g.drawImage(back,613,230,75,100,this); // river
            hole1.setCoOrdinates(375,405);
            hole1.paintComponent(g);
            hole2.setCoOrdinates(450,405);
            hole2.paintComponent(g);
            flop1.setCoOrdinates(213,230);
            flop1.paintComponent(g);
            flop2.setCoOrdinates(313,230);
            flop2.paintComponent(g);
            flop3.setCoOrdinates(413,230);
            flop3.paintComponent(g);
            turn.setCoOrdinates(513,230);
            turn.paintComponent(g);
        }
        else if (test[3])
        {
            g.drawImage(back,375,55,75,100,this); // comp1
            g.drawImage(back,450,55,75,100,this); // comp2
            g.drawImage(back,513,230,75,100,this); // turn
            g.drawImage(back,613,230,75,100,this); // river
            hole1.setCoOrdinates(375,405);
            hole1.paintComponent(g);
            hole2.setCoOrdinates(450,405);
            hole2.paintComponent(g);
            flop1.setCoOrdinates(213,230);
            flop1.paintComponent(g);
            flop2.setCoOrdinates(313,230);
            flop2.paintComponent(g);
            flop3.setCoOrdinates(413,230);
            flop3.paintComponent(g);
        }
        else
        {
            g.drawImage(back,375,55,75,100,this); // comp1
            g.drawImage(back,450,55,75,100,this); // comp2
            g.drawImage(back,213,230,75,100,this); // flop1
            g.drawImage(back,313,230,75,100,this); // flop2
            g.drawImage(back,413,230,75,100,this); // flop3
            g.drawImage(back,513,230,75,100,this); // turn
            g.drawImage(back,613,230,75,100,this); // river
            hole1.setCoOrdinates(375,405);
            hole1.paintComponent(g);
            hole2.setCoOrdinates(450,405);
            hole2.paintComponent(g);
        }
        if (test[0] && test[6])
        {
            g.setFont(bigFont);
            g.setColor(Color.RED);
            g.drawString("YOU WIN!", 550, 440);
            comp1.setCoOrdinates(375,55);
            comp1.paintComponent(g);
            comp2.setCoOrdinates(450,55);
            comp2.paintComponent(g);
        }
        else if (test[1] && test[6])
        {
            g.setFont(bigFont);
            g.setColor(Color.RED);
            g.drawString("YOU LOSE!", 550, 440);
            comp1.setCoOrdinates(375,55);
            comp1.paintComponent(g);
            comp2.setCoOrdinates(450,55);
            comp2.paintComponent(g);
        }
        else if (test[2] && test[6])
        {
            g.setFont(bigFont);
            g.setColor(Color.RED);
            g.drawString("TIE, SPLIT POT!", 550, 440);
            comp1.setCoOrdinates(375,55);
            comp1.paintComponent(g);
            comp2.setCoOrdinates(450,55);
            comp2.paintComponent(g);
        }
        if (test[7] && test[6])
        {
            g.setFont(mediumFont);
            g.setColor(Color.WHITE);
            g.drawString(phand, 180, 395);
        }
        if (test[8] && test[6])
        {
            g.setFont(mediumFont);
            g.setColor(Color.WHITE);
            g.drawString(chand, 180, 180);
        }
    }
        
}