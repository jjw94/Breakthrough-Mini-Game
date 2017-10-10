/**
 * @author Jeremy Wong & Gary Cunningham
 * Course: ISTE 121
 * Mini Project: Breakthrough Game
 * Class description: This is a program that allows 2 people to play the game, Breakthrough. For those unfamiliar with
 * the rules, we have included an About Game section in the menu bar that will teach new players the rules.
 * This program uses a 2-dimensional array to store pictures in the form of a 8x8 board. Then, using a tracker,
 * we are able to tell if the user is selecting the piece he/she is moving or if they are selecting the piece they are 
 * moving to. We also keep track of what both pieces are. Using that info and a series of if and else if statements, we are 
 * able to make sure that the user is making valid moves (showing error messages for illegal moves and allowing
 * legal moves to go through). The game ends once one person reaches the other side of the board. This prevents the players
 * from moving anymore pieces until a new game has been started.
 */
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;

public class GUI extends JFrame implements ActionListener{
   JLabel jlTurn;
   JButton jbNew;
   ImageIcon mario, space, luigi;
   String turn = "Mario";
   String winner = null;
   JButton jb = null;
   JButton jb2 = null;
   ImageIcon pic = null;
   ImageIcon pic2 = null;
   int row2 = 0;
   int col2 = 0;
   int row = 0;
   int col = 0;
   int tracker = 0;
   int mWins = 0;
   int lWins = 0;
   int totalGames = 0;
   JPanel jpCenter;
   JMenuItem jmiNew, jmiStat, jmiExit, jmiAbout;
   JButton [][] buttons;
   
   //Method that builds the GUI and fills the board game with the images
   public GUI(){
      setTitle("Breakthrough Game");
      setLocation(100, 100);
      
      JPanel jpNorth = new JPanel();
      jpCenter = new JPanel(new GridLayout(8, 8));
      JPanel jpSouth = new JPanel();
      
      //Creates menu and menu options
      JMenuBar menu = new JMenuBar();
      setJMenuBar(menu);
      
      JMenu jmFile = new JMenu("File");
      jmFile.setMnemonic('F');
      jmiNew = new JMenuItem("New Game");
      jmiNew.setMnemonic('N');
      jmiStat = new JMenuItem("Stats");
      jmiStat.setMnemonic('S');
      jmiExit = new JMenuItem("Exit");
      jmiExit.setMnemonic('E');
      jmFile.add(jmiNew);
      jmFile.add(jmiStat);
      jmFile.add(jmiExit);
      menu.add(jmFile);
      
      JMenu jmHelp = new JMenu("Help");
      jmHelp.setMnemonic('H');
      jmiAbout = new JMenuItem("About Game");
      jmiAbout.setMnemonic('A');
      jmHelp.add(jmiAbout);
      menu.add(jmHelp);
      
      //Creates the intro text and also whose turn it is
      jlTurn = new JLabel("Welcome To Breakthrough!: It is " + turn + "'s turn.");
      jpNorth.add(jlTurn);
      
      //Adds images to JButton and uses 2d array to create the board using those JButtons
      mario = new ImageIcon("images/mario.jpg");
      luigi = new ImageIcon("images/luigi.jpg");
      space = new ImageIcon("images/space.jpg");
      buttons = new JButton[8][8];
      for(int row = 0; row < 8; row++){
         for(int col = 0; col < 8; col++){
            if(row < 2){
               buttons[row][col] = new JButton(mario);
            }
            else if(row >= 2 && row < 6){
               buttons[row][col] = new JButton(space);
            }
            else if(row == 6 || row == 7){
               buttons[row][col] = new JButton(luigi);
            }
            jpCenter.add(buttons[row][col]);
         }
      }
      
      //Creates the New Game bottom on the bottom of the GUI
      jbNew = new JButton("New Game");
      jbNew.setMnemonic('N');
      jpSouth.add(jbNew);
      
      //Registers listeners
      jmiNew.addActionListener(this);
      jmiStat.addActionListener(this);
      jbNew.addActionListener(this);
      jmiAbout.addActionListener(this);
      jmiExit.addActionListener(this);
      for(int row = 0; row < 8; row++){
         for(int col = 0; col < 8; col++){
            buttons[row][col].addActionListener(this);
         }
      }
      
      //Adds JPanels to BorderLayout
      add(jpNorth, BorderLayout.NORTH);
      add(jpCenter, BorderLayout.CENTER);
      add(jpSouth, BorderLayout.SOUTH);
      
      pack();
      setVisible(true);
      setDefaultCloseOperation(EXIT_ON_CLOSE);
   }
   
   //Main method to run game
   public static void main(String []args){
      new GUI();
   }
   
   //ActionEvent
   public void actionPerformed(ActionEvent ae){
      Object pressed = ae.getSource();
      //Resets the board and starts a new game
      if(ae.getSource() == jbNew || ae.getSource() == jmiNew){
         for(int row = 0; row < 8; row++){
            for(int col = 0; col < 8; col++){
               if(row < 2){
                  buttons[row][col].setIcon(mario);
               }
               else if(row >= 2 && row < 6){
                  buttons[row][col].setIcon(space);
               }
               else if(row == 6 || row == 7){
                  buttons[row][col].setIcon(luigi);
               }
            }
         }
         turn = "Mario";
         winner = null;
         tracker = 0;
         //System.out.println("Resetted");
      }
      //Displays the stats for mario and luigi
      else if(ae.getSource() == jmiStat){
         String overallWinner = "";
         //double mPercentWin = 0.0;
         //double lPercentWin = 0.0;
         //double games = (double)totalGames;
         //mPercentWin = mWins/totalGames * 100;
         //lPercentWin = lWins/totalGames * 100;
         //String mPercent = mPercentWin + "%%";
         //String lPercent = lPercentWin + "%%";
         if(mWins > lWins){
            overallWinner = "Overall Winner is Mario!";
         }
         else if(mWins < lWins){
            overallWinner = "Overall Winner is Luigi!";
         }
         else if(mWins == lWins){
            overallWinner = "It's a tie!!";
         }
         //String stats = String.format("Games played: %d\n" + "Games won by Mario: %d\n" + "Percent of games won by Mario: %s\n" + "Games won by Luigi: %d\n" + "Percent of games won by Luigi: %s\n\n" + "%s", totalGames, mWins, mPercent, lWins, lPercent, overallWinner);
         String stats = String.format("Games played: %d\n" + "Games won by Mario: %d\n" + "Games won by Luigi: %d\n" + "%s", totalGames, mWins, lWins, overallWinner);
         JOptionPane.showMessageDialog(null, stats);
      }
      
      //Exits the game
      else if(ae.getSource() == jmiExit){
         System.exit(0);
      }
      //Displays information about the game
      else if(ae.getSource() == jmiAbout){
         String about = String.format("Rules: \n" + "  Win by moving one piece to the opposite side. Pieces move one space foward\n" +
         "  or diagonally foward, and capture diagonally foward.\n" + "Capabilities:\n" + "- Pieces can move one square straight foward\n" +
         "- Pieces can move one square diagonally foward\n" + "- Pieces can capture opponent square diagonnaly forward\n" +
         "- The first player whose has a piece reaches the other side is the winner\n" + "Constraints:\n" + "- Cannot move onto/capture your own piece\n" +
         "- Cannot capture opponent piece straight foward\n" + "- Cannot move backwards");
         JOptionPane.showMessageDialog(null, about);
      }
      //Playing the game
      else{
         //When the player picks where he wants to move his piece, this checks if that move is allowed
         if(tracker == 1){
            System.out.println("EVEN");
            System.out.println(tracker);
            jb2 = (JButton)pressed;
            //Finds the location (rows and columns) of the space that the user is moving to
            for(int _row = 0; _row < 8; _row++){
               for(int _col = 0; _col < 8; _col++){
                  if(buttons[_row][_col] == jb2){
                     row2 = _row;
                     col2 = _col;
                  }
               }
            }
            pic2 = (ImageIcon)buttons[row2][col2].getIcon();
            //System.out.println(jb);
            //System.out.println(jb2);
            //System.out.println(pic.getDescription());
            //System.out.println(pic2.getDescription());
            
            //If the user is moving Mario, then the program goes through here
            if(turn.equals("Mario")){
               //Allows user to move mario foward one space and checks if mario won or not
               if(pic2.getDescription().equals("images/space.jpg") && row2 == row + 1 && col2 == col){
                  buttons[row][col].setIcon(space);
                  buttons[row2][col2].setIcon(mario);
                  turn = "Luigi";
                  tracker = 0;
                  if(row2 == 7){
                     JOptionPane.showMessageDialog(null, "MARIO WINS!!!");
                     winner = "Mario";
                     mWins++;
                     totalGames++;
                  }
               }
               //Allows user to move mario foward one space diagonally and checks if mario won or not
               else if(pic2.getDescription().equals("images/space.jpg") && row2 == row + 1 && (col2 == col + 1 || col2 == col - 1)){
                  buttons[row][col].setIcon(space);
                  buttons[row2][col2].setIcon(mario);
                  turn = "Luigi";
                  tracker = 0;
                  if(row2 == 7){
                     JOptionPane.showMessageDialog(null, "MARIO WINS!!!");
                     winner = "Mario";
                     mWins++;
                     totalGames++;
                  }
               }
               //Allows user to move mario foward one space diagonnaly and capture an opponent piece, and checks if mario won or not
               else if(pic2.getDescription().equals("images/luigi.jpg") && row2 == row + 1 && (col2 == col + 1 || col2 == col - 1)){
                  buttons[row][col].setIcon(space);
                  buttons[row2][col2].setIcon(mario);
                  turn = "Luigi";
                  tracker = 0;
                  if(row2 == 7){
                     JOptionPane.showMessageDialog(null, "MARIO WINS!!!");
                     winner = "Mario";
                     mWins++;
                     totalGames++;
                  }
               }
               //Shows error if user tries to move mario backwards
               else if(row2 < row){
                  JOptionPane.showMessageDialog(null, "You cannot move backwards!");
                  tracker = 0;
               }
               //Shows error if user tries to capture an opponent's piece straight foward
               else if(pic2.getDescription().equals("images/luigi.jpg") && row2 == row + 1 && col2 == col){
                  JOptionPane.showMessageDialog(null, "You cannot capture opponent piece straight foward");
                  tracker = 0;
               }
               //Shows error if user tries to capture his own piece
               else if(pic2.getDescription().equals("images/mario.jpg")){
                  JOptionPane.showMessageDialog(null, "You cannot move onto / capture your own piece");
                  tracker = 0;
               }
               //Shows user an error if he makes some other type of illegal move
               else{
                  JOptionPane.showMessageDialog(null, "You cannot make that move.\n Please check 'About Game' if you are unsure of rules.");
                  tracker = 0;
               }
            }
            //If the user is moving Luigi, then the program goes through here
            else if(turn.equals("Luigi")){
               //Allows user to move luigi foward one space and checks if mario won or not
               if(pic2.getDescription().equals("images/space.jpg") && row2 == row - 1 && col2 == col){
                  buttons[row][col].setIcon(space);
                  buttons[row2][col2].setIcon(luigi);
                  turn = "Mario";
                  tracker = 0;
                  if(row2 == 0){
                     JOptionPane.showMessageDialog(null, "LUIGI WINS!!!");
                     winner = "Luigi";
                     lWins++;
                     totalGames++;
                  }
               }
               //Allows user to move luigi foward one space diagonally and checks if mario won or not
               else if(pic2.getDescription().equals("images/space.jpg") && row2 == row - 1 && (col2 == col + 1 || col2 == col - 1)){
                  buttons[row][col].setIcon(space);
                  buttons[row2][col2].setIcon(luigi);
                  turn = "Mario";
                  tracker = 0;
                  if(row2 == 0){
                     JOptionPane.showMessageDialog(null, "LUIGI WINS!!!");
                     winner = "Luigi";
                     lWins++;
                     totalGames++;
                  }
               }
               //Allows user to move luigi foward one space diagonnaly and capture an opponent piece, and checks if mario won or not
               else if(pic2.getDescription().equals("images/mario.jpg") && row2 == row - 1 && (col2 == col + 1 || col2 == col - 1)){
                  buttons[row][col].setIcon(space);
                  buttons[row2][col2].setIcon(luigi);
                  turn = "Mario";
                  tracker = 0;
                  if(row2 == 0){
                     JOptionPane.showMessageDialog(null, "LUIGI WINS!!!");
                     winner = "Luigi";
                     lWins++;
                     totalGames++;
                  }
               }
               //Shows error if user tries to move luigi backwards
               else if(row2 > row){
                  JOptionPane.showMessageDialog(null, "You cannot move backwards!");
                  tracker = 0;
               }
               //Shows error if user tries to capture an opponent's piece straight foward
               else if(pic2.getDescription().equals("images/mario.jpg") && row2 == row - 1 && col2 == col){
                  JOptionPane.showMessageDialog(null, "You cannot capture opponent piece straight foward");
                  tracker = 0;
               }
               //Shows error if user tries to capture his own piece
               else if(pic2.getDescription().equals("images/mario.jpg")){
                  JOptionPane.showMessageDialog(null, "You cannot move onto / capture your own piece");
                  tracker = 0;
               }
               //Shows user an error if he makes some other type of illegal move
               else{
                  JOptionPane.showMessageDialog(null, "You cannot make that move.\n Please check 'About Game' if you are unsure of rules.");
                  tracker = 0;
               }
            }
         }
         //The player selects which piece he wants to move
         else{
            //System.out.println("ODD");
            //System.out.println(tracker);
            jb = (JButton)pressed;
            //Finds the location (rows and columns) of the piece that the user is moving
            for(int _row = 0; _row < 8; _row++){
               for(int _col = 0; _col < 8; _col++){
                  if(buttons[_row][_col] == jb){
                     row = _row;
                     col = _col;
                  }
               }
            }
            pic = (ImageIcon)buttons[row][col].getIcon();
            //Checks to see if game is over or not
            if(winner != null){
               JOptionPane.showMessageDialog(null, "Game Over\nPlease Start a New Game");
            }
            //Lets users go if moving mario during mario's turn
            else if(pic.getDescription().equals("images/mario.jpg") && turn.equals("Mario")){
               tracker = 1;
               System.out.println("correct");
            }
            //Lets users go if moving luigi during luigi's turn
            else if(pic.getDescription().equals("images/luigi.jpg") && turn.equals("Luigi")){
               tracker = 1;
               System.out.println("correct");
            }
            //Shows error if user is moving luigi during mario's turn
            else if(pic.getDescription().equals("images/luigi.jpg") && turn.equals("Mario")){
               JOptionPane.showMessageDialog(null, "It is Mario's turn!");
            }
            //Shows error if user is moving mario during luigi's turn
            else if(pic.getDescription().equals("images/mario.jpg") && turn.equals("Luigi")){
               JOptionPane.showMessageDialog(null, "It is Luigi's turn!");
            }
            //Shows error if user is trying to move an empty space
            else if(pic.getDescription().equals("images/space.jpg") && turn.equals("Mario")){
               JOptionPane.showMessageDialog(null, "You cannot move this piece!");
            }
         }
      }
      //System.out.println("row: " + row + " col: " + col);
      //System.out.println("row2: " + row2 + " col2: " + col2);
   }
}