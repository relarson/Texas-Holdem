/**
 * Play Texas Holdum against the computer.
 * 
 * Ross Larson 
 * 
 * Current Version: 1.9 Beta
 *      as of May 28th, 2008
 * 
 * Notes:
 *      None
 *
 * Future Plans:
 *      Make the computer determine its hand according to the "available" info after each bet (AI).
 *
 * Version History
 *      1.9 Beta: (May 29th, 2008)
 *          1) GUI is almost complete
 *              a) Just a few bugs with dialog boxes
 *      1.8 Beta: (May 15th, 2008)
 *          1) GUI is in Progress
 *          2) Fixed error in profit
 *      1.7 Beta: (May 5th, 2008)
 *          1) Finished Preliminary testing
 *              a) Ready to be Beta tested by Sam, Sidney, Tyler and any others.
 *      1.6 Beta: (May 5th, 2008)
 *          1) Started Beta Testing
 *          2) Worked out kinks in Hands.
 *              a) Handchecker no longer has access to Hands, 
 *                  i) It wasn't sending info to correct Hands object.
 *                      1) It was going to its, not the one Dealer_Shoe created.
 *              b) Added storeCards() and getCards() to handChecker
 *                  i) storeCards() called in Handhecker
 *                  ii) getCards() called by Dealer_Shoe
 *                      1) Sends its int[] to hands.unPack();
 *      1.5 Beta: (May 4th, 2008)
 *          1) Strted working on GUI
 *          2) Started trying to make ties only occur if actually tie 
 *              a) ex: before pair(2) and pair(A) tied, now pair(A) wins
 *          3) Added "Hand" Class to generate String of hand.
 *              a) Accesed through Dealer_Shoe
 *          4) Created a getCards() in HandChecker
 *              a) Allows Hand to get Full House and Two Pair Cards easily
 *                  i) Some errors: Displaying Two Pairs and Full Houses
 *              b) Others  hands can be determined by reversing HandCheckers handvalue formulas
 *      1.4 Beta: (May 2nd, 2008)
 *          1) I was displaying wrong cards
 *              a) had been displaying burnt cards
 *                  i) Why HandCheker "re-broke" after version 1.3 Beta
 *                      1) That was addition of "burning"
 *      1.6 Alpha: (May 2nd, 2008)
 *          1) Added printout of arrays containing the face and suit values of cards
 *              a) Problem is only in community cards.
 *                  i) problem is in Card class, Dealer_Shoe, or HandChecker.
 *                      1) But hole cards are un affected, and the getArray() results match up, so that lends to HandChecker being ok
 *                      2) Since hole cards getting numbers fine, i think error is in Dealer_Shoe, somehow
 *                  ii) HandChecker looks to be off the hook, added this to getFaceValue() and getSuitValue()
 *                      1) Didn't Help
 *                  iii) Commented out the random card generator
 *                      1) Didn't help
 *      1.5 Alpha: (May 2nd, 2008)
 *          1) Added access to int[]s in Handchecker via Dealer_Shoe.
 *          2) Added printout of an int[]
 *              a) store() in Handchecker has problems.
 *      1.4 Alpha: (May 1st, 2008)
 *          1)HandChecker has some errors
 *              a) Reverts program back to Alpha build
 *                  i) Picks up @ v1.4
 *      1.3 Beta: (Feb. 27th, 2008)
 *          1) Made the game burn a card where it is supposed too.
 *      1.2 Beta: (Feb. 24th, 2008)
 *          1) Removed multiple decks (from driver, shoe still has adjustablility for other games)
 *          2) Removed shuffle card from 1 deck shoes
 *          3) Made shoe's newhand() method shuffle after each hand if only 1 deck in shoe
 *          4) Caught bad input for bets and loan amount
 *              a) ONLY IF bad input is a double.
 *                  i) It rounds (adds .5 then converts to int)
 *      1.1 Beta: (Feb. 18th, 2008)
 *          1) Added Profit system.
 *          2) Added Variable Loan Amounts
 *              a) Asks user for amount.
 *          3) Beta testing of:
 *              a) Handchecker
 *              b) Profit System
 *              c) Variable Loans
 *              d) Shuffle Card
 *          4) Added Version History, used memory and old backups to recount versions.
 *              a) Some older versions may have been large compilations of versions.
 *              b) This is the best i could do.
 *      1.0 Beta: (Feb. 17th, 2008)
 *          1)HandChecker functions fully
 *              a) Changes program from Alpha versions to Beta versions
 *                  i) Beta testing can now commence
 *              b) The HandChecker now resets after each hand
 *                  i) That was main problem. shoe.cardreturn(); now calls several methods.
 *                      1) dealt.newhand();
 *                          a) returns an array of cards back to shoe
 *                      2) check.newhand();
 *                          a) makes all spots in Handchecker arrays = 0
 *                          b) resets "a" to 0
 *          2) Added variable ante amount via Pot object creation 
 *              a) hardcode variability via driver, not user variability.
 *          3) Made ante amount a String that is recived from a Pot class method. (works with #2)
 *          4) Beta testing of:
 *              a) Handchecker
 *      1.3 Alpha: (Feb. 16th, 2008)
 *          1) Made the main body of the program easily commentable to allow for swifter checking of the functionality of HandChecker.
 *      1.2 Alpha: (Feb. 15th, 2008)
 *          1) Added commented test outputs, to test comments are removed.
 *          2) Deleted deck class, a shoe object with one deck in it was an option, no need for deck class
 *      1.1 Alpha: (Dec. 11th, 2007)
 *          1) Added support classes
 *          2) HandChecker was not finished
 *          3) Changed to a shoe or deck based game.
 *      1.0 Alpha: (Oct. 17th, 2007)
 *          1) Made initial program, did not have full functionality, driver did everything.
 */
package texas;
import java.util.Scanner;
public class Play_TextBased
{
    public static void main (String [] args) throws InterruptedException
    {
        Scanner scan = new Scanner(System.in);
        String again = "" + "y";
        String phand = new String();
        String chand = new String();
        Pot pot = new Pot(1);
        Card hole1 = null;
        Card hole2 = null;
        Card comp1 = null;
        Card comp2 = null;
        Card flop1 = null;
        Card flop2 = null;
        Card flop3 = null;
        Card turn = null;
        Card river = null;
        int phandvalue = -1, chandvalue=-1;
        
        System.out.println ("Welcome to the Ross Larson JAVA Casino!");

        System.out.println ("Good luck. You will be playing heads up agaisnt the house.");
        System.out.println("The house has unlimited cash so just quit when you want to.");
        System.out.println("This game utilizes whole number amounts. \nIf you type in a decimal number, it will round it.");
        // /**
        System.out.print ("Preparing chips & Shuffling cards");
        for (int row = 0; row <10; row ++)
        {
            System.out.print(" .");
            Thread.sleep(200);
        }
        // */
        char repeat = (again.toLowerCase()).charAt(0);
        Dealer_Shoe shoe = new Dealer_Shoe(1);
        shoe.shuffle();
        while (repeat == 'y')
        {
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
            phandvalue = shoe.CheckHand("Player");
            phand = shoe.getHand(phandvalue);
            chandvalue = shoe.CheckHand("Comp");
            chand = shoe.getHand(chandvalue);
            // System.out.println("phv: " + phandvalue + "\nchv: " + chandvalue);
            
            // /**
            System.out.println ("\n\nYou have $" + pot.chipstack() + ", I will put both of our " + pot.anteAmt() + " antes in.");
            pot.ante();
            System.out.println ("Pot = $" + pot.potsize());
            System.out.println ("Your Chip Stack = $" + pot.chipstack());
            System.out.println ("\nHole cards:");
            System.out.println ("   " + hole1);
            System.out.println ("   " + hole2);
            // System.out.println ("\n   " + comp1); //comp card
            // System.out.println ("   " + comp2); // comp card
            System.out.print ("\nPlace your bet. (0 to check): ");
            int bet = (int) (scan.nextDouble() + .5);
            if (bet < 0)
                bet = 0;
            pot.bet(bet);
            
            System.out.println ("Pot = $" + pot.potsize());
            System.out.println ("Your Chip Stack = $" + pot.chipstack());
            System.out.println ("\nThe Flop:");
            Thread.sleep(500);
            System.out.println ("   " + flop1);
            Thread.sleep(500);
            System.out.println ("   " + flop2);
            Thread.sleep(500);
            System.out.println ("   " + flop3);
            System.out.print ("\nPlace your bet. (0 to check): ");
            bet = (int) (scan.nextDouble() + .5);
            if (bet < 0)
                bet = 0;
            pot.bet(bet);

            System.out.println ("Pot = $" + pot.potsize());
            System.out.println ("Your Chip Stack = $" + pot.chipstack());
            System.out.println ("\nThe Turn:");
            Thread.sleep(500);
            System.out.println ("   " + turn);
            System.out.print ("\nPlace your bet. (0 to check): ");
            bet = (int) (scan.nextDouble() + .5);
            if (bet < 0)
                bet = 0;
            pot.bet(bet);

            System.out.println ("Pot = $" + pot.potsize());
            System.out.println ("Your Chip Stack = $" + pot.chipstack());
            System.out.println ("\nThe River:");
            Thread.sleep(500);
            System.out.println ("   " + river); 
            System.out.print ("\nPlace your bet. (0 to check): ");
            bet = (int) (scan.nextDouble() + .5);
            if (bet < 0)
                bet = 0;
            pot.bet(bet);
            
            System.out.println ("Pot = $" + pot.potsize());
            // */ 
            // System.out.println ("\n" + hole1 + "\n" + hole2 + "\n \n" + comp1 + "\n" + comp2 + "\n \n"
            //                     + flop1 + "\n" + flop2 + "\n" + flop3 + "\n" + turn + "\n" + river + "\n");
            /**
            for (int num : shoe.getArray("pface"))
                System.out.print(num);
            System.out.print("\n");
            */
           
            if (phandvalue > chandvalue)
            {
                System.out.println("You win! You had a " + phand + " and the computer had a " + chand);
                // System.out.println("phandvalue: " + phandvalue + " chandvalue: " + chandvalue);
                pot.pay();
                System.out.println ("Your Chip Stack = $" + pot.chipstack());
                System.out.println ("Your Cumulative Profit = " + pot.getProfitString());
            }
            else if (chandvalue > phandvalue)
            {
                System.out.println("You lose. The computer had a " + chand + " and you had a " + phand);
                // System.out.println("phandvalue: " + phandvalue + " chandvalue: " + chandvalue);
                pot.reset();
                System.out.println ("Your Chip Stack = $" + pot.chipstack());
                System.out.println ("Your Cumulative Profit = " + pot.getProfitString());
            }
            else
            {
                System.out.println("Tie. Split pot. Both you and the computer had a " + chand);
                // System.out.println("phandvalue: " + phandvalue + " chandvalue: " + chandvalue);
                pot.splitpot();
                System.out.println ("Your Chip Stack = $" + pot.chipstack());
                System.out.println ("Your Cumulative Profit = " + pot.getProfitString());
            }
            System.out.print ("\nAgain? (y/n): ");
            again = scan.next();
            again = again.toLowerCase();
            repeat = again.charAt(0);
            shoe.cardreturn();
            if (pot.chipstack() == 0 && repeat == 'y')
            {
                System.out.print("Would you like to get a loan? (y/n): ");
                String loan = "";
                loan += scan.next();
                loan = loan.toLowerCase();
                char loanchar = loan.charAt(0);
                if (loanchar == 'y')
                {
                    System.out.println("Your Cumulative Profit will take a hit when you get a loan, \n as it is a loss");
                    System.out.print ("Amount: ");
                    int amt = (int) (scan.nextDouble() + .5);
                    pot.loan(amt);
                }
                else
                    repeat = 'n';
            }         
        }
    }
}