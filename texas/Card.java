package texas;
import java.awt.*;
import javax.swing.*;
public class Card extends JComponent
{
    private int card, suitnum, x, y;
    private String suit;
    private String face;
    
    Image Clubs = new ImageIcon("pictures/clubs.jpg").getImage();
    Image Diamonds = new ImageIcon("pictures/diamonds.jpg").getImage();
    Image Hearts = new ImageIcon("pictures/hearts.jpg").getImage();
    Image Spades = new ImageIcon("pictures/spades.jpg").getImage(); 
    
    Font mediumFont = new Font("Arial",Font.BOLD,20);
    
    private String paintSuit = "";
    private int paintFace;    
    
    public Card()
    {        
        card = (int) (Math.random()*13 + 1);
        suitnum = (int) (Math.random()*4);
        suit = getSuit();
        face = getFace();
        System.out.println("Here's the problem");
    }  
    public Card (int c, int f)
    {
        card = c;
        suitnum = f;
        suit = getSuit();
        face = getFace();
    }
    public Card (int c)
    {
        card = -1;
        suit = null;
        face = null;
    }  
    public String getFace() 
    {
       String[] faceage = {"Ace", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Jack", "Queen", "King"};
       return faceage[card-1];
    }
    public String getSuit()
    {
        String[] suitage = {"Clubs", "Spades", "Diamonds", "Hearts"};
        return suitage[suitnum];
    }
    public int getFaceValue() // 1 - 13
    {
        return this.card;
    }
    public int getSuitValue() // 0 - 4
    {
        return this.suitnum;
    }
    public String toString()
    {
        return face + " of " + suit + ".";
    }   
    public boolean equals(Object obj)
    {
        Card c = (Card) obj;
        return (c.getFace().equals(face) && c.getSuit().equals(suit));
    }
    public void setCoOrdinates(int a, int b)
    {
        x = a;
        y = b;
        paintSuit = this.getSuit();
        paintFace = this.getFaceValue();
    }
    public void paintComponent(Graphics g)
    {
        g.setColor(Color.BLACK);
        g.setFont(mediumFont);
        if (paintSuit.equals("Clubs"))
            g.drawImage(Clubs,x,y,75,100,this);
        else if (paintSuit.equals("Spades"))
            g.drawImage(Spades,x,y,75,100,this);
        else if (paintSuit.equals("Diamonds"))
        {
            g.drawImage(Diamonds,x,y,75,100,this);
            g.setColor(Color.RED);
        }
        else
        {
            g.drawImage(Hearts,x,y,75,100,this);
            g.setColor(Color.RED);
        }
        if (paintFace < 10 && paintFace > 1)
        {
            g.drawString("" + paintFace, x + 5, y + 20);
            g.drawString("" + paintFace, x + 55, y + 95);
        }
        else if (paintFace == 10)
        {
            g.drawString("" + paintFace, x + 5, y + 20);
            g.drawString("" + paintFace, x + 53, y + 95);
        }
        else
        {
            g.drawString("" + getFace().charAt(0), x + 5, y + 18);
            g.drawString("" + getFace().charAt(0), x + 55, y + 95);
        }
    }
}