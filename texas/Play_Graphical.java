package texas;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
 
public class Play_Graphical
{
    private static final String BET = "Bet";
    private static final String CHECK = "Check";
    private static final String DEAL = "Deal";
    private static final String ALLIN = "All In";
    private static final String QUIT = "Quit";
    private JPanel mainPanel = new JPanel();
    private Table table = new Table();
    private Pot pot = new Pot(1);
    private AI ai = new AI();
    WindowUtilities winUtil = new WindowUtilities();
    Object[] possibilities = null;
    static String name = "";
    String amt = " ";
    String temp = "";
    String AIresult = "";
    int count = 0, l;
    int raise;
    int raiseamt = 0;
    
    JButton allinButton = new JButton(ALLIN);
    JButton betButton = new JButton(BET);
    JButton dealButton = new JButton(DEAL);
    JButton checkButton = new JButton(CHECK);
    JButton quitButton = new JButton(QUIT);
    
    
    public Play_Graphical()
    {
        winUtil.setNativeLookAndFeel();
        if (name == null)
            System.exit(0);
        else if (name.equals(""))
            name = "Player 1";
        table.playerName(name);
        table.newHand();
        pot.ante();
        
        JPanel centerPanel = createCenterPanel();
        JPanel moneyPanel = createMoneyPanel();
        JPanel buttonPanel = createButtonPanel();
        
        mainPanel.setBackground(Color.BLACK);
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.add(centerPanel, BorderLayout.NORTH);
        mainPanel.add(moneyPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
    }
    
    private JPanel createCenterPanel()
    {
        JPanel centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        table.setPreferredSize(new Dimension(900, 575));
        table.init();
        centerPanel.add(table);
        return centerPanel;
    }
    
    private JPanel createMoneyPanel()
    {
        JPanel moneyPanel = new JPanel();
        moneyPanel.setOpaque(false);
        pot.setPreferredSize(new Dimension(900,40));
        moneyPanel.setBackground(Color.pink);
        moneyPanel.setForeground(Color.pink);
        pot.init();
        moneyPanel.add(pot);
        return moneyPanel;
    }
    
    private JPanel createButtonPanel()
    {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        allinButton.addActionListener(new ButtonListener());
        betButton.addActionListener(new ButtonListener());
        dealButton.addActionListener(new ButtonListener());
        checkButton.addActionListener(new ButtonListener());
        quitButton.addActionListener(new ButtonListener());
        allinButton.setActionCommand("All In");
        betButton.setActionCommand("Bet");
        dealButton.setActionCommand("Deal");
        checkButton.setActionCommand("Check");
        quitButton.setActionCommand("Quit");
        Font btnFont = betButton.getFont().deriveFont(Font.BOLD, 20);
        allinButton.setFont(btnFont);
        betButton.setFont(btnFont);
        dealButton.setFont(btnFont);
        checkButton.setFont(btnFont);
        quitButton.setFont(btnFont);
        JPanel gridPanel = new JPanel(new GridLayout(1, 0, 25, 0));
        gridPanel.setOpaque(false);
        allinButton.setBackground(Color.BLACK);
        betButton.setBackground(Color.BLACK);
        dealButton.setBackground(Color.BLACK);
        checkButton.setBackground(Color.BLACK);
        quitButton.setBackground(Color.BLACK);
        dealButton.setEnabled(false);
        gridPanel.add(betButton);
        gridPanel.add(allinButton);
        gridPanel.add(dealButton);
        gridPanel.add(checkButton);
        gridPanel.add(quitButton);
        buttonPanel.add(gridPanel);
        return buttonPanel;
    }
    private class ButtonListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent e)
        {
            String command = e.getActionCommand();
            double loanamt = 0.0, betamt =0.0;
            boolean cancel = false;
            if ((command != null) && (command.equals("Bet")))
            {
                WindowUtilities.setNativeLookAndFeel();
                while ((temp != null) && (temp.equals("")))
                {
                    temp = (String)JOptionPane.showInputDialog(
                        mainPanel,
                        "How much are you betting?\n"
                         + "Note: amounts are rounded to nearest dollar.",
                        "Enter Bet Amount",
                        JOptionPane.QUESTION_MESSAGE);
                }
                if ((temp != null) && (temp.length() > 0))
                    amt += temp;
                else count--;                    
                if (amt.length() > 1)
                {
                    if (amt.charAt(1) == '$')
                        amt = amt.substring(2,amt.length());
                    else amt = amt.substring(1,amt.length());
                    try {pot.bet(Double.parseDouble(amt));}
                    catch (Exception exc) {amt = " ";
                        JOptionPane.showMessageDialog(null, "Ah Hah! Stop your shinanigans Mr. Kintzel\n" 
                                                    + "Your exception got caught! (It was user error anyway)"
                                                    , "I call Shinanigans on you Mr Kintzel!", JOptionPane.INFORMATION_MESSAGE);
                        count--;
                        temp = "";
                        actionPerformed(e);
                    }
                }
                else
                    temp = "";                
                amt = " ";
                temp = "";
                //pot.call();
                count ++;
                if (count == 1)
                {
                    table.flop();
                    ai.sendHole(table.sendComp());
                }
                else if (count == 2)
                {
                    table.turn();
                    ai.sendFlop(table.sendFlop());
                }
                else if (count == 3)
                {
                    table.river();
                    ai.sendTurn(table.sendTurn());
                }
                else
                    ai.sendRiver(table.sendRiver());
                // start comment here if no AI, remove following comemnts to re add seperate thread
                //Timer calcpause = new Timer (50, new ActionListener()
                    //{
                        //public void actionPerformed(ActionEvent e)
                       // {
                            AIresult = ai.calc(150);
                       // }                 
                   //});
                   //calcpause.setInitialDelay(50);
                   //calcpause.setRepeats(false);
                   //calcpause.start();
                if (AIresult.equals("Fold"))
                {
                    //System.out.println("Fold (Graph)");
                    pot.pay();
                    JOptionPane.showMessageDialog(null, "Computer Folds" , "Computer Fold", JOptionPane.INFORMATION_MESSAGE);
                    count = 0;
                    table.newHand();
                    table.repaint();
                    pot.repaint();
                    dealButton.setEnabled(false);
                    betButton.setEnabled(true);
                    checkButton.setEnabled(true);
                    allinButton.setEnabled(true);
                    table.repaint();
                    pot.ante();
                }
                else if (AIresult.equals("Call"))
                {
                    //System.out.println("Call (Graph)");
                    pot.call();
                }
                else
                {
                    pot.call();
                    AIresult = AIresult.substring(5,AIresult.length());
                    //System.out.println(AIresult);
                    raise = Integer.parseInt(AIresult);
                    if (raise == 0)
                        raiseamt = (int) (pot.chipstack() * (Math.random()*.25)+.1);
                    else if (raise == 1)
                        raiseamt = (int) (pot.chipstack() * ((Math.random()*.2)+.3));
                    else if (raise == 2)
                        raiseamt = (int) (pot.chipstack() * ((Math.random()*.2)+.45));
                    else if (raise == 3)
                        raiseamt = (int) (pot.chipstack() * ((Math.random()*.2)+.6));
                    else if (raise == 4)
                        raiseamt = (int) (pot.chipstack() * ((Math.random()*.15)+.75));
                    else if (raise == 5)
                        raiseamt = (int) (pot.chipstack() * ((Math.random()*.15)+.85));
                    Object[] options = {"Call", "Fold"};
                    int crdec = JOptionPane.showOptionDialog(mainPanel,
                        "The Computer raises $" + raiseamt,
                        "The Computer raises.",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,     //do not use a custom Icon
                        options,  //the titles of buttons
                        options[0]); //default button title
                    if (crdec == 0)
                        pot.compRaise(raiseamt);
                    else
                    {
                        pot.reset();
                        dealButton.setEnabled(true);
                        betButton.setEnabled(false);
                        checkButton.setEnabled(false);
                        allinButton.setEnabled(false);
                    }
                        
                }
                // end comment here to take out AI

                    pot.repaint();
                    table.repaint();
                
                if (pot.chipstack() == 0)
                {
                    betButton.setEnabled(false);
                    allinButton.setEnabled(false);
                }
            }
            else if ((command != null) && (command.equals("Deal")))
            {
                count = 0;
                table.newHand();
                table.repaint();
                pot.repaint();
                dealButton.setEnabled(false);
                betButton.setEnabled(true);
                checkButton.setEnabled(true);
                allinButton.setEnabled(true);
                pot.ante();
            }
            else if ((command != null) && (command.equals("All In")))
            {
                betamt = pot.chipstack();
                pot.bet(betamt);
                //pot.call();
                count ++;
                if (count == 1)
                {
                    table.flop();
                    ai.sendHole(table.sendComp());
                }
                else if (count == 2)
                {
                    table.turn();
                    ai.sendFlop(table.sendFlop());
                }
                else if (count == 3)
                {
                    table.river();
                    ai.sendTurn(table.sendTurn());
                }
                else
                    ai.sendRiver(table.sendRiver());
                // start comment here if no AI  remove following comemnts to re add seperate thread
                //Timer calcpause = new Timer (50, new ActionListener()
                   // {
                        //public void actionPerformed(ActionEvent e)
                        //{
                            AIresult = ai.calc(150);
                       // }                 
                   //});
                   //calcpause.setInitialDelay(50);
                  // calcpause.setRepeats(false);
                  // calcpause.start();
                
                if (AIresult.equals("Fold"))
                {
                    //System.out.println("Fold (Graph)");
                    pot.pay();
                    JOptionPane.showMessageDialog(null, "Computer Folds" , "Computer Fold", JOptionPane.INFORMATION_MESSAGE);
                    count = 0;
                    table.newHand();
                    table.repaint();
                    pot.repaint();
                    dealButton.setEnabled(false);
                    betButton.setEnabled(true);
                    checkButton.setEnabled(true);
                    allinButton.setEnabled(true);
                    table.repaint();
                    pot.ante();
                }
                else
                {
                    //System.out.println("Call (Graph)");
                    betButton.setEnabled(false);
                    allinButton.setEnabled(false);
                    pot.call();
                }
                // end comment here to take out AI
               
                pot.repaint();
                table.repaint();
 
            }
            
            else if ((command != null) && (command.equals("Quit")))
            {
                int n = JOptionPane.showConfirmDialog(
                        mainPanel,
                        "Are you sure you want to quit?",
                        "Quit?",
                        JOptionPane.YES_NO_OPTION);
                if (n == 0)
                {
                    JOptionPane.showMessageDialog(null, "Goodbye! I hope you had fun playing\n" 
                                                    + "Ross Larson JAVA Casino: Texas Hold 'em"
                                                    , "Goodbye!", JOptionPane.INFORMATION_MESSAGE);
                    System.exit(0);
                }
            }
            else
            {
                pot.check();
                ai.sendHole(table.sendComp());
                count ++;
                if (count == 1)
                {
                    table.flop();
                    ai.sendFlop(table.sendFlop());
                }
                else if (count == 2)
                {
                    table.turn();
                    ai.sendTurn(table.sendTurn());
                }
                else if (count == 3)
                {
                    table.river();
                    ai.sendRiver(table.sendRiver());
                }
                else
                    ai.sendRiver(table.sendRiver());
                // start comment here if no AI  remove following comemnts to re add seperate thread
                //Timer calcpause = new Timer (50, new ActionListener()
                   // {
                        //public void actionPerformed(ActionEvent e)
                        //{
                            AIresult = ai.calc(150);
                       // }                 
                   //});
                   //calcpause.setInitialDelay(50);
                  // calcpause.setRepeats(false);
                  // calcpause.start();
                char AIsub = AIresult.charAt(0);
                if (AIsub == 'R')
                {
                    pot.call();
                    AIresult = AIresult.substring(5,AIresult.length());
                    //System.out.println(AIresult);
                    raise = Integer.parseInt(AIresult);
                    if (raise == 0)
                        raiseamt = (int) (pot.chipstack() * (Math.random()*.25)+.1);
                    else if (raise == 1)
                        raiseamt = (int) (pot.chipstack() * ((Math.random()*.2)+.3));
                    else if (raise == 2)
                        raiseamt = (int) (pot.chipstack() * ((Math.random()*.2)+.45));
                    else if (raise == 3)
                        raiseamt = (int) (pot.chipstack() * ((Math.random()*.2)+.6));
                    else if (raise == 4)
                        raiseamt = (int) (pot.chipstack() * ((Math.random()*.15)+.75));
                    else if (raise == 5)
                        raiseamt = (int) (pot.chipstack() * ((Math.random()*.15)+.85));
                    Object[] options = {"Call", "Fold"};
                    int crdec = JOptionPane.showOptionDialog(mainPanel,
                        "The Computer raises $" + raiseamt,
                        "The Computer raises.",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,     //do not use a custom Icon
                        options,  //the titles of buttons
                        options[0]); //default button title
                    if (crdec == 0)
                        pot.compRaise(raiseamt);
                    else
                    {
                        pot.reset();
                        dealButton.setEnabled(true);
                        betButton.setEnabled(false);
                        checkButton.setEnabled(false);
                        allinButton.setEnabled(false);
                    }
                        
                }
                else
                {
                    //System.out.println("Call (Graph)");
                    pot.call();
                }
                // end comment here to take out AI
                pot.repaint();
                table.repaint();
            }
            if (count >= 4)
            {
                table.handFinished();
                table.displayHands();
                int result = table.getResult();
                if (result ==1)
                    pot.pay();
                else if (result == -1)
                    pot.reset();
                else pot.splitpot(); 
                dealButton.setEnabled(true);
                betButton.setEnabled(false);
                checkButton.setEnabled(false);
                allinButton.setEnabled(false);
                pot.repaint();
                table.repaint(); 
                if (pot.chipstack() == 0)
                {
                    WindowUtilities.setNativeLookAndFeel();
                    String loan = "$";
                    Timer pauser = new Timer (2000, new ActionListener()
                    {
                        public void actionPerformed(ActionEvent e)
                        {
                            l = JOptionPane.showConfirmDialog(
                                null,
                                "Do you want a $500 loan?",
                                "Would you like a loan?",
                                JOptionPane.YES_NO_OPTION);
                            if (l == 0)
                                pot.loan(500.0);
                            else
                            {
                                WindowUtilities.setNativeLookAndFeel();
                                int n = JOptionPane.showConfirmDialog(
                                    mainPanel,
                                    "Are you sure you want to quit?",
                                    "Quit?",
                                    JOptionPane.YES_NO_OPTION);
                                if (n == 0)
                                {
                                    JOptionPane.showMessageDialog(null, "Goodbye! I hope you had fun playing\n" 
                                                        + "Ross Larson JAVA Casino: Texas Hold 'em"
                                                        , "Goodbye!", JOptionPane.INFORMATION_MESSAGE);
                                    System.exit(0);
                                }
                                else if (pot.chipstack() == 0)
                                {
                                    Object[] options = {"$500 Loan",
                                                "Quit"};
                                    int h = JOptionPane.showOptionDialog(mainPanel,
                                        "You have no money. You must get a $500 loan or quit.",
                                        "You are out of money.",
                                        JOptionPane.YES_NO_OPTION,
                                        JOptionPane.QUESTION_MESSAGE,
                                        null,     //do not use a custom Icon
                                        options,  //the titles of buttons
                                        options[0]); //default button title
                                    if (h == 0)
                                        pot.loan(500.0);
                                    else
                                    {
                                        JOptionPane.showMessageDialog(null, "Goodbye! I hope you had fun playing\n" 
                                                        + "Ross Larson JAVA Casino: Texas Hold 'em"
                                                        , "Goodbye!", JOptionPane.INFORMATION_MESSAGE);
                                        System.exit(0);
                                    }
                                } 
                            }
                        }                 
                   });
                   pauser.setInitialDelay(2500);
                   pauser.setRepeats(false);
                   pauser.start(); 
                }
            }
        }
    }
 
    public JPanel getPanel()
    {
        return mainPanel;
    }
    
    private static void createAndShowGUI()
    {
        JFrame frame = new JFrame("Ross Larson JAVA Casino: Texas Hold 'em");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        WindowUtilities.setNativeLookAndFeel();
        name = (String)JOptionPane.showInputDialog(
                    null,
                    "Welcome to the Ross Larson JAVA Casino!\n"
                    + "What is your name?",
                    "Name Entry",
                   JOptionPane.QUESTION_MESSAGE);
        frame.add(new Play_Graphical().getPanel());
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(900, 750);
        frame.setLocationRelativeTo(null);
        frame.pack();
        if (screen.getHeight() <= 800)
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
        frame.toFront();
    }
 
    public static void main(String[] args) 
    {
        javax.swing.SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                createAndShowGUI();
            }
        });
 
    }
}