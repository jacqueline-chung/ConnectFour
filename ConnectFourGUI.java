/*Names: Jacqueline Chung & Lisa Z
 * Date: November 13 - 30, 2015
 * Teacher: ICS4U Agrighetti (P2)
 * Description: Connect Four Game is supposed to have three modes: player vs. player, player vs. computer, and computer vs. player.
 * The Computer (AI) has three levels: easy, medium, and hard. The hard level must win if it goes first.
 * The game tracks who's winning overall and score after every game. It indicates who won the game.
 * It's up the player(s) to beat their opponent to get row in a row.
 * The GUI has images, buttons, and tabs to make it interactive and user-friendly and visually appealing.
 *  ///current one
 */

//These imports are required to use GUI components
import javax.swing.*;
import java.awt.*; 
import java.awt.event.*;
import java.util.Scanner;//import scanner
import java.util.Random; //required for Random generator
import javax.swing.JTabbedPane; //import for tabs
import javax.swing.ImageIcon; //for images
//The imports are required to use Timer components
import java.util.Timer;
import java.util.TimerTask;
import java.awt.Container;
import javax.swing.BoxLayout;

////////////////////////////////////////*********ACTION LINSTENER CLASS**********\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

public class ConnectFourGUI extends JFrame implements ActionListener { //Jframe and Actionlistener needed for buttons and frame
  
  //declare counters for scores
  int AIScore = 0, player1Score = 0, player2Score = 0, totalGames = 0, AIWon = 0, player1Won = 0, player2Won = 0;
  
  final static int HEIGHT = 6;//global variable
  final static int WIDTH = 7;//global variable
  final static int BOTTOM_ROW = HEIGHT - 1;//global variable
  static int GAMESELECT=2;//DROPDOWN menu
  static int AI_SCORE, PLAYER1_SCORE, PLAYER2_SCORE, GAMES_PLAYED = 0;
  
  static int nextMoveLocation = -1;
  final static int maxDepth = 8;
  
  static int time = 0; //times member represent calling times.
  static Timer timer = new Timer("Printer"); //Taking an instance of Timer class
  MyTask t = new MyTask(); //Taking an instance of class contains your repeated method.
  
  //declare panels for tabpan1
  JPanel pan1 = new JPanel(); //for title
  JPanel buttonPan = new JPanel(); //for board
  static JPanel boardPan = new JPanel(); //for board
  JPanel pan2 = new JPanel(); //for scores
  JPanel typePan = new JPanel(); //for game types
  
  //declare panels for tabpan2
  JPanel pan3 = new JPanel(); //for statistics
  
  //declare panels for tabpan3
  JPanel pan4 = new JPanel(); //for instructions, info about authors 
  JPanel pan5 = new JPanel(); //for instructions, info about authors 
  JPanel pan6 = new JPanel(); //for instructions, info about authors 
  JPanel pan7 = new JPanel(); //for instructions, info about authors 
  
  //JPanel tab panels
  JPanel tabpan1 = new JPanel(); //connect-4 screen with scores and statistics
  JPanel tabpan2 = new JPanel(); //about/help info, instructions to game
  JPanel tabpan3 = new JPanel(); //statistics
  
  // Create tabbed panes 
  JTabbedPane Tabbed = new JTabbedPane(JTabbedPane.TOP); //declare tabbed pane
  
  /////////////////////*********LABELS**********\\\\\\\\\\\\\\\\\\
  
  //add labels Home tab 
  JLabel titleLabel = new JLabel("CONNECT FOUR", JLabel.CENTER);
  JLabel timerLabel = new JLabel("Timer: " + time + "s", JLabel.RIGHT);
  
  //static 2d array
  static JLabel[][] boardLabel = new JLabel[HEIGHT][WIDTH];//declare customized labels as a global variable
  static JLabel[][] imgLabel = new JLabel[HEIGHT][WIDTH];//declare customized labels as a global variable
  
  //GridLayout Decalred
  GridLayout Array_Layout = new GridLayout(HEIGHT, WIDTH);//declare Grid Layout according to row and column input from user
  
  //Help/Info Labels for Info/Help tab 
  JLabel help1Label = new JLabel("CONNECT FOUR GAME ©");
  JLabel help2Label = new JLabel("Authors: Jacqueline Chung and Lisa Zaprudskaya");
  JLabel help3Label = new JLabel("Date: December 1, 2015");
  JLabel help4Label = new JLabel("Instructions: The object of the game is to connect four horizontally, vertically or diagonally using buttons within 5 minutes.", JLabel.CENTER);
  
  //STATISTICS labels
  JLabel stat1Label = new JLabel("PLAYER 1 WINS : 0 ");
  JLabel stat1_1Label = new JLabel("PLAYER 1 WIN PERCENTAGE: 0%");
  JLabel stat2Label = new JLabel("PLAYER 2 WINS : 0 ");
  JLabel stat2_2Label = new JLabel("PLAYER 2 WIN PERCENTAGE: 0%");
  JLabel stat3Label = new JLabel("AI PLAYER WINS : 0 ");
  JLabel stat3_3Label = new JLabel("AI PLAYER WIN PERCENTAGE: 0%");
  
  /////////////////////*********BUTTONS**********\\\\\\\\\\\\\\\\
  
  //add buttons for reset, column 1-7 for home tab 
  JButton resetButton = new JButton("Reset Board");
  static JButton col1Button = new JButton("Column 1");
  static JButton col2Button = new JButton("Column 2");
  static JButton col3Button = new JButton("Column 3");
  static JButton col4Button = new JButton("Column 4");
  static JButton col5Button = new JButton("Column 5");
  static JButton col6Button = new JButton("Column 6");
  static JButton col7Button = new JButton("Column 7");
  
  ////////////////////*********FONTS**********\\\\\\\\\\\\\\\\
  
  //declare fonts for the title and date
  Font font1 = new Font("Serif", Font.BOLD, 26);//title
  Font font2 = new Font("Serif", Font.ITALIC, 14);//comments
  Font font3 = new Font("SansSerif",Font.BOLD, 20);//statistics
  
  ////////////////////*********IMAGES**********\\\\\\\\\\\\\\\\
  
  //declare imagesicons
  static final ImageIcon icon = new ImageIcon("white circle.png");
  static final ImageIcon icon1 = new ImageIcon("red circle.png");
  static final ImageIcon icon2 = new ImageIcon("yellow circle.png");
  
  ////////////////////*********COMBOBOX/DIALOG BOX**********\\\\\\\\\\\\\\\\
  
  //comboboxes
  static final JComboBox gameTypeList = new JComboBox();
  static final JComboBox levelList = new JComboBox();
  
  //dialog box to give messages
  static int mc = JOptionPane.WARNING_MESSAGE; //declare option pane as a global variable
  JOptionPane optionPane = new JOptionPane();
  
  ////////////////////*********SCANNER AND OTHER VARIABLES**********\\\\\\\\\\\\\\\\
  
  static Scanner myScanner = new Scanner(System.in);//declare scanner class as a global variable
  
  //declare variables to check in console and prevent repetition
  Random rand = new Random();
  static String[][] board = new String[HEIGHT][WIDTH];
  static int counter = 0;
  
  ////////////////////////////////////////*********CONSTRUCTOR**********\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\ 
  
  public ConnectFourGUI() {  //a special method called a constructor. It must have the same name as your class. This is where all your code goes to set up the frame 
    setTitle("Connect Four - Jacqueline & Lisa"); //Create a window with a title
    setSize(715, 720); // set the window size    
    setResizable(false); // prevents user from changing the size 
    
    // set layout 
    setLayout(new GridLayout());
    
    //set layouts for tabpan1
    tabpan1.setLayout(new FlowLayout());
    boardPan.setLayout(Array_Layout);
    pan2.setLayout(new FlowLayout());
    
    //set layouts for tabpan2
    pan3.setLayout(new BoxLayout(pan3, BoxLayout.Y_AXIS));
    
    //set layouts for tabpan3
    tabpan3.setLayout(new BoxLayout(tabpan3, BoxLayout.Y_AXIS));
    pan4.setLayout(new BoxLayout(pan4, BoxLayout.Y_AXIS));
    pan5.setLayout(new BoxLayout(pan5, BoxLayout.Y_AXIS));
    pan6.setLayout(new BoxLayout(pan6, BoxLayout.Y_AXIS)); 
    pan7.setLayout(new BoxLayout(pan7, BoxLayout.Y_AXIS));
    
    //get content
    getContentPane().add(Tabbed);    
    
    //set font for the title for both tabbed panes
    titleLabel.setFont(font1);
    
    //set font for help labels
    help1Label.setFont(font3);
    help2Label.setFont(font2);
    help3Label.setFont(font2);
    help4Label.setFont(font2);
    
    //set font for stat
    stat1Label.setFont(font3);
    stat2Label.setFont(font3);
    stat3Label.setFont(font3);
    
    //set colour background for the tabs
    tabpan1.setBackground(Color.orange);
    tabpan2.setBackground(Color.green);
    tabpan3.setBackground(Color.pink);
    
    //in tabpan1
    pan1.setBackground(Color.orange); //title
    buttonPan.setBackground(Color.orange); //board
    boardPan.setBackground(Color.black); //board
    pan2.setBackground(Color.white); //score
    resetButton.setBackground(Color.pink);
    
    //in tabpan2
    pan3.setBackground(Color.green);
    
    //in tabpan3
    pan4.setBackground(Color.pink);
    pan5.setBackground(Color.pink);
    pan6.setBackground(Color.pink);
    pan7.setBackground(Color.pink);
    
    ////////////////////////////////////////*********DROP DOWN MENU**********\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\ 
    gameTypeList.addItem("Player vs. Player");
    gameTypeList.addItem("Player vs. Computer");
    gameTypeList.addItem("Computer vs. Player");
    levelList.addItem("Easy");
    levelList.addItem("Challenging");
    
    //Create the combo box, select item at index 0
    //Indices start at 0 to 2
    gameTypeList.setSelectedIndex(1);
    gameTypeList.addActionListener(this);//call action listener
    gameTypeList.setEditable(false);//not editable
    
    levelList.setSelectedIndex(0);
    levelList.addActionListener(this);
    levelList.setEditable(false);
    
    timer.schedule(t, 1000, 1000); //call timer method
    
    ////////////////////////////////////////*********ActionListener (JComboBox)**********\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    
    gameTypeList.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        // Get the source of the component, which is our combo box.//
        Object selected = gameTypeList.getSelectedItem();//check game type being selected or not
        Object selected_level = levelList.getSelectedItem();//check level type being selected or not
        
        //If "Easy" is selected on the level drop down menu
        if(selected_level.toString().equals("Easy")){
          int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to change level? Your board will reset automatically.");//message
        
          if (response == 0) {
            System.out.println("Yes");//if user says yes
            if (selected.equals("Player vs. Computer")){//and the Player vs. Computer, continue to Player vs Computer, easy mode
              GAMESELECT=2; 
            }else if (selected.equals("Computer vs. Player")){//and the Computer vs. Player, continue to Player vs Computer, easy mode
              GAMESELECT=3;
            }
            resetBOARD();//reset the board
          } else if (response == 1) {
            System.out.println("No");//do not continue
          } else if (response == 2) {
            System.out.println("Cancel");//cancel
          } 
          
          //If "Challenging" is selected on the level drop down menu
        }else if(selected_level.toString().equals("Challenging")){
          int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to change level? Your board will reset automatically.");  
         
          if (response == 0) {
            System.out.println("Yes");
            if (selected.equals("Player vs. Computer")){//and the Player vs. Computer, continue to Player vs Computer, challenging mode
              GAMESELECT=4; 
            }else if(selected.equals("Player vs. Computer")){//and the Computer vs. Player, continue to Player vs Computer, cahllenging mode
              GAMESELECT=2;
            }
            resetBOARD();//reset the board
          } else if (response == 1) {
            System.out.println("No");
          } else if (response == 2) {
            System.out.println("Cancel");
          }
        }
        
        //if "Player vs. Player" selected on the game type drop down menu
        if(selected.toString().equals("Player vs. Player")) {
          System.out.println("Player vs. Player chosen");
          GAMESELECT=1;
          int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to change your game type? Your board will reset automatically."); //show message that the board will reset
          System.out.println("Are you sure you want to change your game type? Your board will reset automatically."); //test in console
          
          if (response == 0) {
            System.out.println("Yes");
            resetBOARD();//reset the board
            counter = 0;
          } else if (response == 1) {
            System.out.println("No");
          } else if (response == 2) {
            System.out.println("Cancel");
          }
          levelList.setEnabled(false); //set disabled
          
          //if "Player vs. Player" selected on the game type drop down menu
        } else if(selected.toString().equals("Player vs. Computer")) {
          System.out.println("Player vs. Computer chosen ");
          GAMESELECT=2;
          int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to change your game type? Your board will reset automatically.");
          
          if (response == 0) {
            System.out.println("Yes");
            resetBOARD();//reset the board
          } else if (response == 1) {
            System.out.println("No");
          } else if (response == 2) {
            System.out.println("Cancel");
          }
          levelList.setEnabled(true); //set enabled
          
          //if "Computer vs. Player" selected on the game type drop down menu  
        } else if (selected.toString().equals("Computer vs. Player")) {
          System.out.println("Computer vs. Player chosen");
          GAMESELECT=3;
          int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to change your game type? Your board will reset automatically.");
          
          if (response == 0) {
            System.out.println("Yes");
            resetBOARD();//reset the board
            AIturnSIMPLE();
          } else if (response == 1) {
            System.out.println("No");
          } else if (response == 2) {
            System.out.println("Cancel");
          }
          levelList.setEnabled(true); //set enabled
          
          //if "Player vs. Computer" selected on the game type drop down menu
        }else if (selected.toString().equals("Player vs. Computer")) {
          System.out.println("Player vs. Computer (challenging) chosen");
          GAMESELECT=4;
          int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to change your game type? Your board will reset automatically.");
          
          if (response == 0) {
            System.out.println("Yes");
            resetBOARD();//reset the board
          } else if (response == 1) {
            System.out.println("No");
          } else if (response == 2) {
            System.out.println("Cancel");
          }
          levelList.setEnabled(true); //set enabled       
        }     
      }   
    });
    getContentPane().add(gameTypeList);//get contenet
    getContentPane().add(levelList);//get content
    
    //////////////////***ADD COMPONENTS***\\\\\\\\\\\\\\\\\\\\
    //add tabs
    add(tabpan1);
    add(tabpan2);
    add(tabpan3);
    
    //add panels
    //in tabpan1
    tabpan1.add(pan1);
    tabpan1.add(buttonPan); //board
    tabpan1.add(typePan); //for drop down menu
    tabpan1.add(boardPan); //board
    tabpan1.add(pan2);
    
    //in tabpan2
    tabpan2.add(pan3);
    
    //in tabpan3
    tabpan3.add(pan4);
    tabpan3.add(pan5);
    tabpan3.add(pan6);
    tabpan3.add(pan7);
    
    // Add all the components within the tab panel
    //labels
    pan1.add(titleLabel);
    
    //add buttons for positions
    buttonPan.add(col1Button);
    buttonPan.add(col2Button);
    buttonPan.add(col3Button);
    buttonPan.add(col4Button);
    buttonPan.add(col5Button);
    buttonPan.add(col6Button);
    buttonPan.add(col7Button);
    
    //////////////////***TEST ON CONSOLE***\\\\\\\\\\\\\\\\\\\\
    System.out.print("White circles...\n"); //test on console
    
    for (int row = 0; row < HEIGHT; row ++) {
      for (int col = 0; col < WIDTH; col ++) { 
        //test on console
        board[row][col] = "O";
        System.out.print(board[row][col] + "");
        
        //Create the label with image, centered
        imgLabel[row][col] = new JLabel(icon, JLabel.CENTER);
        boardPan.add(imgLabel[row][col]); //add
      }
      System.out.println(); //test on console
    }
    System.out.print("Yellow circles..."); //test on console
    
    //////////////////***ADD COMPONENTS***\\\\\\\\\\\\\\\\\\\\    
    //timer
    pan2.add(timerLabel);
    //buttons
    pan2.add(resetButton);
    //add drop down menus
    typePan.add(gameTypeList);
    typePan.add(levelList);
    
    //add all component in tabpan2
    pan3.add(stat1Label);
    pan3.add(stat1_1Label);
    pan3.add(stat2Label);
    pan3.add(stat2_2Label);
    pan3.add(stat3Label);
    pan3.add(stat3_3Label);
    
    //add all component in tabpan3
    pan4.add(help1Label);
    pan5.add(help2Label);
    pan6.add(help3Label);
    pan7.add(help4Label);
    
    // Adds actionListener to the Buttons 
    resetButton.addActionListener(this);
    col1Button.addActionListener(this);
    col2Button.addActionListener(this);
    col3Button.addActionListener(this);
    col4Button.addActionListener(this);
    col5Button.addActionListener(this);
    col6Button.addActionListener(this);
    col7Button.addActionListener(this);
    
    //set Tab titles
    Tabbed.addTab("Home", tabpan1);
    Tabbed.addTab("Statistics", tabpan2);
    Tabbed.addTab("Help/Info", tabpan3);
    
    setVisible(true); // makes it visible 
  }
  
  //////////////////***IMAGES IN FILE***\\\\\\\\\\\\\\\\\\\\
  /** Returns an ImageIcon, or null if the path was invalid. */
  protected static ImageIcon createImageIcon(String path,
                                             String description) {
    java.net.URL imgURL = ConnectFourGUI.class.getResource(path);
    
    if (imgURL != null) {
      return new ImageIcon(imgURL, description);
    } else {
      System.err.println("Couldn't find file: " + path);
      return null;
    }
  }
  
  //////////////////***ACTION EVENTS***\\\\\\\\\\\\\\\\\\\\
  public void actionPerformed(ActionEvent event) {
    String command = event.getActionCommand();
    
    if (command.equals("Reset Board")) { //if user clicks Restart Button
      System.out.println("Restart button pressed"); //test on console
      JOptionPane.showMessageDialog (null, "Are you sure you want to restart the game?", "Warning", mc); //show message to ask if the player is certain they want to restart
      resetBOARD();//reset the board
      enableCOLUMN_buttons();
      counter = 0; //to start with red again
      
      //reset timer
      time = 0;
      timerLabel.setText("Timer: " + time + "s"); //set to zero again
      
      ////////////////////////////////////////*********COLUMN 1**********\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\     
    } else if (command.equals("Column 1")) { //if user clicks column 1 Button
      System.out.println("Column 1 pressed"); //test in console
      
      int column = 0;
      displayArray(board); //display current board
      
      if(GAMESELECT==1) {//PLAYER VS PLAYER
        System.out.println("Game Select 1"); //test in console
        
        counter++; //add 1 to determine turns
        
        //check if even or odd to take turns
        if (counter % 2 == 0) { //if even Y
          DropY(column);
          
          if(!CheckY()){//determines if Y has won after that turn
            System.out.println("YELLOW WON");
            GAMES_PLAYED++;
            PLAYER2_SCORE++;
            //calculatePERCENTAGE(GAMES_PLAYED, PLAYER2_SCORE); 
            stat2Label.setText("PLAYER 2 WINS :" + PLAYER2_SCORE);
            stat2_2Label.setText("PLAYER 2 WIN PERCENTAGE : " + calculatePERCENTAGE(GAMES_PLAYED, PLAYER2_SCORE) + "%");
            disableCOLUMN_buttons();
          }  
        } else if (counter % 2 != 0) { //if odd R   
          DropR(column);
          
          if(!CheckR()){//determines if RED has won after that turn
            System.out.println("RED WON");
            GAMES_PLAYED++;
            PLAYER1_SCORE++;
            stat1Label.setText("PLAYER 1 WINS :  " + PLAYER1_SCORE);
            stat1_1Label.setText("PLAYER 1 WIN PERCENTAGE : " + calculatePERCENTAGE(GAMES_PLAYED, PLAYER1_SCORE) + "%");
            disableCOLUMN_buttons();
          } 
        }
        displayArray(board); //display current board
        
      } else  if (GAMESELECT == 2) {//PLAYER VS. COMPUTER
        System.out.println("Game Select 2"); //test in console 
        DropR(column); //call red method  
        
        if(!CheckR()){//determines if RED has won after that turn
          System.out.println("RED WON"); //test in console
          GAMES_PLAYED++; //add to total games played
          PLAYER1_SCORE++; //add to score
          stat1Label.setText("PLAYER 1 WINS :  " + PLAYER1_SCORE); //output player's score
          stat1_1Label.setText("PLAYER 1 WIN PERCENTAGE : " + calculatePERCENTAGE(GAMES_PLAYED, PLAYER1_SCORE) + "%");
          disableCOLUMN_buttons(); //disable buttons
        } else {
          AIturnSIMPLE2();
          if(!CheckY()){//determines if AI has won after that turn
            System.out.println("AI WON");
            GAMES_PLAYED++;
            AI_SCORE++;
            stat3Label.setText("AI PLAYER WINS :" + AI_SCORE);
            stat3_3Label.setText("AI PLAYER WIN PERCENTAGE : " + calculatePERCENTAGE(GAMES_PLAYED, AI_SCORE) + "%");
            disableCOLUMN_buttons();
          }
        } 
      } else  if(GAMESELECT==3) {//COMPUTER VS PLAYER
        System.out.println("Game Select 3"); //test in console
        counter++; //add 1 to determine turns 
        DropY(column);      
        
        if(!CheckY()){//determines if AI has won after that turn
          System.out.println("YELLOW WON");
          GAMES_PLAYED++;
          PLAYER2_SCORE++;
          stat2Label.setText("PLAYER 2 WINS :" + PLAYER2_SCORE);
          stat2_2Label.setText("PLAYER 2 WIN PERCENTAGE : " + calculatePERCENTAGE(GAMES_PLAYED, PLAYER2_SCORE) + "%");
          disableCOLUMN_buttons();
        } else {
          AIturnSIMPLE();
          if(!CheckR()){//determines if RED has won after that turn
            System.out.println("AI WON");
            GAMES_PLAYED++;
            AI_SCORE++;
            stat3Label.setText("AI PLAYER WINS :" + AI_SCORE);
            stat3_3Label.setText("AI PLAYER WIN PERCENTAGE : " + calculatePERCENTAGE(GAMES_PLAYED, AI_SCORE) + "%");
            disableCOLUMN_buttons();
          }
        }
      }else if(GAMESELECT==4) {
        boolean AIturn = false; //creates boolean to determine if it's AI's turn
        
        if (counter % 2 != 0) {
          DropR(column);//activates player's turn, then prints board
          counter++;  
          
          if(!CheckR()){//determines if AI has won after that turn
            System.out.println("RED WON"); //test in console
            GAMES_PLAYED++; //add to total games played
            PLAYER1_SCORE++; //add to score
            stat1Label.setText("PLAYER 1 WINS :  " + PLAYER1_SCORE); //output player's score
            stat1_1Label.setText("PLAYER 1 WIN PERCENTAGE : " + calculatePERCENTAGE(GAMES_PLAYED, PLAYER1_SCORE) + "%");
            disableCOLUMN_buttons();
          } 
        }
        
        if ((counter % 2 == 0)&& (counter<5)) {
          AIturnSIMPLE2();
          counter++;
          
          if(!CheckY()){ //determines if player has won after that turn
            System.out.println("YELLOW WON");
            GAMES_PLAYED++;
            AI_SCORE++;
            stat3Label.setText("AI PLAYER WINS :" + AI_SCORE);
            stat3_3Label.setText("AI WIN PERCENTAGE : " + calculatePERCENTAGE(GAMES_PLAYED, AI_SCORE) + "%");
            disableCOLUMN_buttons();
          }
        }else if ((counter % 2 == 0)) {
          AIturnMEDIUM();//activates player's turn, then prints board
          counter++;       
          
          if(!CheckY()){ //determines if player has won after that turn
            System.out.println("YELLOW WON");
            GAMES_PLAYED++;
            AI_SCORE++;
            stat3Label.setText("AI PLAYER WINS :" + AI_SCORE);
            stat3_3Label.setText("AI WIN PERCENTAGE : " + calculatePERCENTAGE(GAMES_PLAYED, AI_SCORE) + "%");
            disableCOLUMN_buttons();
          }
          
        } 
        displayArray(board);   
      }
      ////////////////////////////////////////*********COLUMN 2**********\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\ 
    } else if (command.equals("Column 2")) { //if user clicks Restart Button
      System.out.println("Column 2 pressed");
      int column=1;
      int marker = 1;
      System.out.println("Marker : " +  marker); //test in console
      counter++; //add 1 to determine turns 
      //check if even or odd to take turns
      if(GAMESELECT==1) {//PLAYER VS PLAYER
        //check if even or odd to take turns
        
        if (counter % 2 == 0) { //if even Y 
          DropY(column);
          if(!CheckY()){//determines if Y has won after that turn
            System.out.println("YELLOW WON");
            GAMES_PLAYED++;
            PLAYER2_SCORE++;
            stat2Label.setText("PLAYER 2 WINS :" + PLAYER2_SCORE);
            stat2_2Label.setText("PLAYER 2 WIN PERCENTAGE : " + calculatePERCENTAGE(GAMES_PLAYED, PLAYER2_SCORE) + "%");
            disableCOLUMN_buttons();
          } 
        } else if (counter % 2 != 0) { //if odd R   
          DropR(column);
          
          if(!CheckR()){//determines if RED has won after that turn
            System.out.println("RED WON");
            GAMES_PLAYED++;
            PLAYER1_SCORE++;
            stat1Label.setText("PLAYER 1 WINS : " + PLAYER1_SCORE);
            stat1_1Label.setText("PLAYER 1 WIN PERCENTAGE : " + calculatePERCENTAGE(GAMES_PLAYED, PLAYER1_SCORE) + "%");
            disableCOLUMN_buttons();
          } 
        }
        displayArray(board); //display current board
        
      } else  if (GAMESELECT == 2) {//PLAYER VS. COMPUTER
        System.out.println("Game Select 2"); //test in console
        DropR(column);  
        
        if(!CheckR()){//determines if RED has won after that turn
          System.out.println("RED WON");
          GAMES_PLAYED++;
          PLAYER1_SCORE++;
          stat1Label.setText("PLAYER 1 WINS : " + PLAYER1_SCORE);
          stat1_1Label.setText("PLAYER 1 WIN PERCENTAGE : " + calculatePERCENTAGE(GAMES_PLAYED, PLAYER1_SCORE) + "%");
          disableCOLUMN_buttons();
        } else {
          AIturnSIMPLE2();
          
          if(!CheckY()){//determines if AI has won after that turn
            System.out.println("AI WON");
            GAMES_PLAYED++;
            AI_SCORE++;
            stat3Label.setText("AI PLAYER WINS :" + AI_SCORE);
            stat3_3Label.setText("AI PLAYER WIN PERCENTAGE : " + calculatePERCENTAGE(GAMES_PLAYED, AI_SCORE) + "%");
            disableCOLUMN_buttons();
          }
        }
        
      } else if(GAMESELECT==3) {//COMPUTER VS PLAYER
        System.out.println("Game Select 3"); //test in console 
        counter++; //add 1 to determine turns
        DropY(column);   
        
        if(!CheckY()){//determines if AI has won after that turn
          System.out.println("YELLOW WON");
          GAMES_PLAYED++;
          PLAYER2_SCORE++;
          stat2Label.setText("PLAYER 2 WINS :" + PLAYER2_SCORE);
          stat2_2Label.setText("PLAYER 2 WIN PERCENTAGE : " + calculatePERCENTAGE(GAMES_PLAYED, PLAYER2_SCORE) + "%");
          disableCOLUMN_buttons();
        } else {
          AIturnSIMPLE();
          
          if(!CheckR()){//determines if RED has won after that turn
            System.out.println("AI WON");
            GAMES_PLAYED++;
            AI_SCORE++;
            stat3Label.setText("AI PLAYER WINS :" + AI_SCORE);
            stat3_3Label.setText("AI PLAYER WIN PERCENTAGE : " + calculatePERCENTAGE(GAMES_PLAYED, AI_SCORE) + "%");
            disableCOLUMN_buttons();
          }
        }
      }else if(GAMESELECT==4) { 
        boolean AIturn = false; //creates boolean to determine if it's AI's turn
        
        if (counter % 2 != 0) {
          DropR(column);//activates player's turn, then prints board
          counter++;
          
          if(!CheckR()){//determines if AI has won after that turn
            System.out.println("RED WON"); //test in console
            GAMES_PLAYED++; //add to total games played
            PLAYER1_SCORE++; //add to score
            stat1Label.setText("PLAYER 1 WINS :  " + PLAYER1_SCORE); //output player's score
            stat1_1Label.setText("PLAYER 1 WIN PERCENTAGE : " + calculatePERCENTAGE(GAMES_PLAYED, PLAYER1_SCORE) + "%");
            disableCOLUMN_buttons();
          } 
        }
        if ((counter % 2 == 0)&& (counter<5)) {//XYXYX
          AIturnSIMPLE2();
          counter++;   
          
          if(!CheckY()){ //determines if player has won after that turn
            System.out.println("YELLOW WON");
            GAMES_PLAYED++;
            AI_SCORE++;
            stat3Label.setText("AI PLAYER WINS :" + AI_SCORE);
            stat3_3Label.setText("AI WIN PERCENTAGE : " + calculatePERCENTAGE(GAMES_PLAYED, AI_SCORE) + "%");
            disableCOLUMN_buttons();
          }
        } else if ((counter % 2 == 0)) {
          AIturnMEDIUM();//activates player's turn, then prints board
          counter++;   
          
          if(!CheckY()){ //determines if player has won after that turn
            System.out.println("YELLOW WON");
            GAMES_PLAYED++;
            AI_SCORE++;
            stat3Label.setText("AI PLAYER WINS :" + AI_SCORE);
            stat3_3Label.setText("AI WIN PERCENTAGE : " + calculatePERCENTAGE(GAMES_PLAYED, AI_SCORE) + "%");
            disableCOLUMN_buttons();
          }
        }
        displayArray(board); 
      }
      ////////////////////////////////////////*********COLUMN 3**********\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\ 
    } else if (command.equals("Column 3")) { //if user clicks Restart Button
      System.out.println("Column 3 pressed");
      int marker = 1;
      int column=2;
      System.out.println("Marker : " +  marker); //test in console
      counter++; //add 1 to determine turns
      
      //check if even or odd to take turns
      if(GAMESELECT==1) {//PLAYER VS PLAYER
        //check if even or odd to take turns
        if (counter % 2 == 0) { //if even Y
          DropY(column);
          
          if(!CheckY()){//determines if Y has won after that turn
            System.out.println("YELLOW WON");
            GAMES_PLAYED++;
            PLAYER2_SCORE++;
            stat2Label.setText("PLAYER 2 WINS :" + PLAYER2_SCORE);
            stat2_2Label.setText("PLAYER 2 WIN PERCENTAGE : " + calculatePERCENTAGE(GAMES_PLAYED, PLAYER2_SCORE) + "%");
            disableCOLUMN_buttons();
          } 
        } else if (counter % 2 != 0) { //if odd R   
          DropR(column);
          
          if(!CheckR()){//determines if RED has won after that turn
            System.out.println("RED WON");
            GAMES_PLAYED++;
            PLAYER1_SCORE++;
            stat1Label.setText("PLAYER 1 WINS : " + PLAYER1_SCORE);
            stat1_1Label.setText("PLAYER 1 WIN PERCENTAGE : " + calculatePERCENTAGE(GAMES_PLAYED, PLAYER1_SCORE) + "%");
            disableCOLUMN_buttons();
          } 
        }
        displayArray(board); //display current board
      } else  if (GAMESELECT == 2) {//PLAYER VS. COMPUTER
        System.out.println("Game Select 2"); //test in console
        DropR(column);  
        
        if(!CheckR()){//determines if RED has won after that turn
          System.out.println("RED WON");
          GAMES_PLAYED++;
          PLAYER1_SCORE++;
          stat1Label.setText("PLAYER 1 WINS : " + PLAYER1_SCORE);
          stat1_1Label.setText("PLAYER 1 WIN PERCENTAGE : " + calculatePERCENTAGE(GAMES_PLAYED, PLAYER1_SCORE) + "%");
          disableCOLUMN_buttons();
        } else {
          AIturnSIMPLE2();
          
          if(!CheckY()){//determines if AI has won after that turn
            System.out.println("AI WON");
            GAMES_PLAYED++;
            AI_SCORE++;
            stat3Label.setText("AI PLAYER WINS :" + AI_SCORE);
            stat3_3Label.setText("AI PLAYER WIN PERCENTAGE : " + calculatePERCENTAGE(GAMES_PLAYED, AI_SCORE) + "%");
            disableCOLUMN_buttons();
          }
        }
      } else  if(GAMESELECT==3) {//COMPUTER VS PLAYER  
        System.out.println("Game Select 3"); //test in console
        counter++; //add 1 to determine turns
        DropY(column);     
        
        if(!CheckY()){//determines if AI has won after that turn
          System.out.println("YELLOW WON");
          GAMES_PLAYED++;
          PLAYER2_SCORE++;
          stat2Label.setText("PLAYER 2 WINS :" + PLAYER2_SCORE);
          stat2_2Label.setText("PLAYER 2 WIN PERCENTAGE : " + calculatePERCENTAGE(GAMES_PLAYED, PLAYER2_SCORE) + "%");
          disableCOLUMN_buttons();
        } else {
          
          AIturnSIMPLE();
          if(!CheckR()){//determines if RED has won after that turn
            System.out.println("AI WON");
            GAMES_PLAYED++;
            AI_SCORE++;
            stat3Label.setText("AI PLAYER WINS :" + AI_SCORE);
            stat3_3Label.setText("AI PLAYER WIN PERCENTAGE : " + calculatePERCENTAGE(GAMES_PLAYED, AI_SCORE) + "%");
            disableCOLUMN_buttons();
          }
        }
      }else if(GAMESELECT==4) {  
        boolean AIturn = false; //creates boolean to determine if it's AI's turn
        if (counter % 2 != 0) {
          DropR(column);//activates player's turn, then prints board
          counter++;
          
          if(!CheckR()){//determines if AI has won after that turn
            System.out.println("RED WON"); //test in console
            GAMES_PLAYED++; //add to total games played
            PLAYER1_SCORE++; //add to score
            stat1Label.setText("PLAYER 1 WINS :  " + PLAYER1_SCORE); //output player's score
            stat1_1Label.setText("PLAYER 1 WIN PERCENTAGE : " + calculatePERCENTAGE(GAMES_PLAYED, PLAYER1_SCORE) + "%");
            disableCOLUMN_buttons();
          } 
        }
        if ((counter % 2 == 0)&& (counter<5)) {//XYXYX
          AIturnSIMPLE2();
          counter++;         
          
          if(!CheckY()){ //determines if player has won after that turn
            System.out.println("YELLOW WON");
            GAMES_PLAYED++;
            AI_SCORE++;
            stat3Label.setText("AI PLAYER WINS :" + AI_SCORE);
            stat3_3Label.setText("AI WIN PERCENTAGE : " + calculatePERCENTAGE(GAMES_PLAYED, AI_SCORE) + "%");
            disableCOLUMN_buttons();
          } else if ((counter % 2 == 0)) {
            AIturnMEDIUM();//activates player's turn, then prints board
            counter++;       
            
            if(!CheckY()){ //determines if player has won after that turn
              System.out.println("YELLOW WON");
              GAMES_PLAYED++;
              AI_SCORE++;
              stat3Label.setText("AI PLAYER WINS :" + AI_SCORE);
              stat3_3Label.setText("AI WIN PERCENTAGE : " + calculatePERCENTAGE(GAMES_PLAYED, AI_SCORE) + "%");
              disableCOLUMN_buttons();
            }
          }
          displayArray(board); 
        }
      }
        ////////////////////////////////////////*********COLUMN 4**********\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\ 
      } else if (command.equals("Column 4")) { //if user clicks Restart Button
        System.out.println("Column 4 pressed"); //test in console
        int marker = 1;
        int column=3;
        System.out.println("Marker : " +  marker); //test in console
        counter++; //add 1 to determine turns
        //check if even or odd to take turns
        if(GAMESELECT==1) {//PLAYER VS PLAYER
          //check if even or odd to take turns
          if (counter % 2 == 0) { //if even Y
            DropY(column);
            
            if(!CheckY()){//determines if Y has won after that turn
              System.out.println("YELLOW WON");
              GAMES_PLAYED++;
              PLAYER2_SCORE++;
              stat2Label.setText("PLAYER 2 WINS :" + PLAYER2_SCORE);
              stat2_2Label.setText("PLAYER 2 WIN PERCENTAGE : " + calculatePERCENTAGE(GAMES_PLAYED, PLAYER2_SCORE) + "%");
              disableCOLUMN_buttons();
            } 
            
          } else if (counter % 2 != 0) { //if odd R   
            DropR(column);
            
            if(!CheckR()){//determines if RED has won after that turn
              System.out.println("RED WON");
              GAMES_PLAYED++;
              PLAYER1_SCORE++;
              stat1Label.setText("PLAYER 1 WINS : " + PLAYER1_SCORE);
              stat1_1Label.setText("PLAYER 1 WIN PERCENTAGE : " + calculatePERCENTAGE(GAMES_PLAYED, PLAYER1_SCORE) + "%");
              disableCOLUMN_buttons();
            } 
          }
          displayArray(board); //display current board
        } else  if (GAMESELECT == 2) {//PLAYER VS. COMPUTER
          System.out.println("Game Select 2"); //test in console
          DropR(column);  
          
          if(!CheckR()){//determines if RED has won after that turn
            System.out.println("RED WON");
            GAMES_PLAYED++;
            PLAYER1_SCORE++;
            stat1Label.setText("PLAYER 1 WINS : " + PLAYER1_SCORE);
            stat1_1Label.setText("PLAYER 1 WIN PERCENTAGE : " + calculatePERCENTAGE(GAMES_PLAYED, PLAYER1_SCORE) + "%");
            disableCOLUMN_buttons();
          } else {
            AIturnSIMPLE2();
            
            if(!CheckY()){//determines if AI has won after that turn
              System.out.println("AI WON");
              GAMES_PLAYED++;
              AI_SCORE++;
              stat3Label.setText("AI PLAYER WINS :" + AI_SCORE);
              stat3_3Label.setText("AI PLAYER WIN PERCENTAGE : " + calculatePERCENTAGE(GAMES_PLAYED, AI_SCORE) + "%");
              disableCOLUMN_buttons();
            }
          }
        } else  if(GAMESELECT==3) {//COMPUTER VS PLAYER
          System.out.println("Game Select 3"); //test in console
          counter++; //add 1 to determine turns
          DropY(column);  
          
          if(!CheckY()){//determines if AI has won after that turn
            System.out.println("YELLOW WON");
            GAMES_PLAYED++;
            PLAYER2_SCORE++;
            stat2Label.setText("PLAYER 2 WINS :" + PLAYER2_SCORE);
            stat2_2Label.setText("PLAYER 2 WIN PERCENTAGE : " + calculatePERCENTAGE(GAMES_PLAYED, PLAYER2_SCORE) + "%");
            disableCOLUMN_buttons();
          } else {
            AIturnSIMPLE();
            
            if(!CheckR()){//determines if RED has won after that turn
              System.out.println("AI WON");
              GAMES_PLAYED++;
              AI_SCORE++;
              stat3Label.setText("AI PLAYER WINS :" + AI_SCORE);
              stat3_3Label.setText("AI PLAYER WIN PERCENTAGE : " + calculatePERCENTAGE(GAMES_PLAYED, AI_SCORE) + "%");
              disableCOLUMN_buttons();
            }
          }
        }else if(GAMESELECT==4) { 
          boolean AIturn = false; //creates boolean to determine if it's AI's turn
          if (counter % 2 != 0) {
            DropR(column);//activates player's turn, then prints board
            counter++;
            
            if(!CheckR()){//determines if AI has won after that turn
              System.out.println("RED WON"); //test in console
              GAMES_PLAYED++; //add to total games played
              PLAYER1_SCORE++; //add to score
              stat1Label.setText("PLAYER 1 WINS :  " + PLAYER1_SCORE); //output player's score
              stat1_1Label.setText("PLAYER 1 WIN PERCENTAGE : " + calculatePERCENTAGE(GAMES_PLAYED, PLAYER1_SCORE) + "%");
              disableCOLUMN_buttons();
            } 
          }
          if ((counter % 2 == 0)&& (counter<5)) {//XYXYX
            AIturnSIMPLE2();
            counter++;     
            
            if(!CheckY()){ //determines if player has won after that turn
              System.out.println("YELLOW WON");
              GAMES_PLAYED++;
              AI_SCORE++;
              stat3Label.setText("AI PLAYER WINS :" + AI_SCORE);
              stat3_3Label.setText("AI WIN PERCENTAGE : " + calculatePERCENTAGE(GAMES_PLAYED, AI_SCORE) + "%");
              disableCOLUMN_buttons();
            }
          }else if ((counter % 2 == 0)) {   
            AIturnMEDIUM();//activates player's turn, then prints board
            counter++;
            
            if(!CheckY()){ //determines if player has won after that turn
              System.out.println("YELLOW WON");
              GAMES_PLAYED++;
              AI_SCORE++;
              stat3Label.setText("AI PLAYER WINS :" + AI_SCORE);
              stat3_3Label.setText("AI WIN PERCENTAGE : " + calculatePERCENTAGE(GAMES_PLAYED, AI_SCORE) + "%");
              disableCOLUMN_buttons();
            }
          }
          displayArray(board); 
        }
        ////////////////////////////////////////*********COLUMN 5**********\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\ 
      } else if (command.equals("Column 5")) { //if user clicks Restart Button
        System.out.println("Column 5 pressed"); //test in console
        int marker = 1;
        int column=4;
        System.out.println("Marker : " +  marker); //test in console 
        counter++; //add 1 to determine turns
        
        //check if even or odd to take turns
        if(GAMESELECT==1) {//PLAYER VS PLAYER
          
          //check if even or odd to take turns
          if (counter % 2 == 0) { //if even Y
            DropY(column);
            
            if(!CheckY()){//determines if Y has won after that turn
              System.out.println("YELLOW WON");
              GAMES_PLAYED++;
              PLAYER2_SCORE++;
              stat2Label.setText("PLAYER 2 WINS :" + PLAYER2_SCORE);
              stat2_2Label.setText("PLAYER 2 WIN PERCENTAGE : " + calculatePERCENTAGE(GAMES_PLAYED, PLAYER2_SCORE) + "%");
              disableCOLUMN_buttons();
            } 
          } else if (counter % 2 != 0) { //if odd R   
            DropR(column);
            
            if(!CheckR()){//determines if RED has won after that turn
              System.out.println("RED WON");
              GAMES_PLAYED++;
              PLAYER1_SCORE++;
              stat1Label.setText("PLAYER 1 WINS : " + PLAYER1_SCORE);
              stat1_1Label.setText("PLAYER 1 WIN PERCENTAGE : " + calculatePERCENTAGE(GAMES_PLAYED, PLAYER1_SCORE) + "%");
              disableCOLUMN_buttons();
            } 
          }
          displayArray(board); //display current board
        } else  if (GAMESELECT == 2) {//PLAYER VS. COMPUTER
          System.out.println("Game Select 2"); //test in console 
          DropR(column);  
          
          if(!CheckR()){//determines if RED has won after that turn
            System.out.println("RED WON");
            GAMES_PLAYED++;
            PLAYER1_SCORE++;
            stat1Label.setText("PLAYER 1 WINS : " + PLAYER1_SCORE);
            stat1_1Label.setText("PLAYER 1 WIN PERCENTAGE : " + calculatePERCENTAGE(GAMES_PLAYED, PLAYER1_SCORE) + "%");
            disableCOLUMN_buttons();
          } else {
            AIturnSIMPLE2();
            
            if(!CheckY()){//determines if AI has won after that turn
              System.out.println("AI WON");
              GAMES_PLAYED++;
              AI_SCORE++;
              stat3Label.setText("AI PLAYER WINS :" + AI_SCORE);
              stat3_3Label.setText("AI PLAYER WIN PERCENTAGE : " + calculatePERCENTAGE(GAMES_PLAYED, AI_SCORE) + "%");
              disableCOLUMN_buttons();
            }
          }
        } else  if(GAMESELECT==3) {//COMPUTER VS PLAYER
          System.out.println("Game Select 3"); //test in console
          counter++; //add 1 to determine turns
          DropY(column);       
          
          if(!CheckY()){//determines if AI has won after that turn
            System.out.println("YELLOW WON");
            GAMES_PLAYED++;
            PLAYER2_SCORE++;
            stat2Label.setText("PLAYER 2 WINS :" + PLAYER2_SCORE);
            stat2_2Label.setText("PLAYER 2 WIN PERCENTAGE : " + calculatePERCENTAGE(GAMES_PLAYED, PLAYER2_SCORE) + "%");
            disableCOLUMN_buttons();
          } else {
            AIturnSIMPLE();
            
            if(!CheckR()){//determines if RED has won after that turn
              System.out.println("AI WON");
              GAMES_PLAYED++;
              AI_SCORE++;
              stat3Label.setText("AI PLAYER WINS :" + AI_SCORE);
              stat3_3Label.setText("AI PLAYER WIN PERCENTAGE : " + calculatePERCENTAGE(GAMES_PLAYED, AI_SCORE) + "%");
              disableCOLUMN_buttons();
            }
          }
        }else if(GAMESELECT==4) {
          boolean AIturn = false; //creates boolean to determine if it's AI's turn
          if (counter % 2 != 0) {
            DropR(column);//activates player's turn, then prints board
            counter++;
            
            if(!CheckR()){//determines if AI has won after that turn
              System.out.println("RED WON"); //test in console
              GAMES_PLAYED++; //add to total games played
              PLAYER1_SCORE++; //add to score
              stat1Label.setText("PLAYER 1 WINS :  " + PLAYER1_SCORE); //output player's score
              stat1_1Label.setText("PLAYER 1 WIN PERCENTAGE : " + calculatePERCENTAGE(GAMES_PLAYED, PLAYER1_SCORE) + "%");
              disableCOLUMN_buttons();
            } 
          }
          if ((counter % 2 == 0)&& (counter<5)) {//XYXYX
            AIturnSIMPLE2();
            counter++;      
            
            if(!CheckY()){ //determines if player has won after that turn
              System.out.println("YELLOW WON");
              GAMES_PLAYED++;
              AI_SCORE++;
              stat3Label.setText("AI PLAYER WINS :" + AI_SCORE);
              stat3_3Label.setText("AI WIN PERCENTAGE : " + calculatePERCENTAGE(GAMES_PLAYED, AI_SCORE) + "%");
              disableCOLUMN_buttons();
            }
          }else if ((counter % 2 == 0)) {          
            AIturnMEDIUM();//activates player's turn, then prints board
            counter++;     
            
            if(!CheckY()){ //determines if player has won after that turn
              System.out.println("YELLOW WON");
              GAMES_PLAYED++;
              AI_SCORE++;
              stat3Label.setText("AI PLAYER WINS :" + AI_SCORE);
              stat3_3Label.setText("AI WIN PERCENTAGE : " + calculatePERCENTAGE(GAMES_PLAYED, AI_SCORE) + "%");
              disableCOLUMN_buttons();
            }
          }
          displayArray(board); 
        }
        ////////////////////////////////////////*********COLUMN 6**********\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\ 
      } else if (command.equals("Column 6")) { //if user clicks Restart Button
        System.out.println("Column 6 pressed");//test in console
        int column=5;
        int marker = 1;
        System.out.println("Marker : " +  marker); //test in console
        counter++; //add 1 to determine turns
        
        //check if even or odd to take turns
        if(GAMESELECT==1) {//PLAYER VS PLAYER
          
          //check if even or odd to take turns
          if (counter % 2 == 0) { //if even Y
            DropY(column);
            
            if(!CheckY()){//determines if Y has won after that turn
              System.out.println("YELLOW WON");
              GAMES_PLAYED++;
              PLAYER2_SCORE++;
              stat2Label.setText("PLAYER 2 WINS :" + PLAYER2_SCORE);
              stat2_2Label.setText("PLAYER 2 WIN PERCENTAGE : " + calculatePERCENTAGE(GAMES_PLAYED, PLAYER2_SCORE) + "%");
              disableCOLUMN_buttons();
            } 
          } else if (counter % 2 != 0) { //if odd R   
            DropR(column);
            
            if(!CheckR()){//determines if RED has won after that turn
              System.out.println("RED WON");
              GAMES_PLAYED++;
              PLAYER1_SCORE++;
              stat1Label.setText("PLAYER 1 WINS : " + PLAYER1_SCORE);
              stat1_1Label.setText("PLAYER 1 WIN PERCENTAGE : " + calculatePERCENTAGE(GAMES_PLAYED, PLAYER1_SCORE) + "%");
              disableCOLUMN_buttons();
            } 
          }
          displayArray(board); //display current board
        } else  if (GAMESELECT == 2) {//PLAYER VS. COMPUTER
          System.out.println("Game Select 2"); //test in console
          DropR(column);  
          
          if(!CheckR()){//determines if RED has won after that turn
            System.out.println("RED WON");
            GAMES_PLAYED++;
            PLAYER1_SCORE++;
            stat1Label.setText("PLAYER 1 WINS : " + PLAYER1_SCORE);
            stat1_1Label.setText("PLAYER 1 WIN PERCENTAGE : " + calculatePERCENTAGE(GAMES_PLAYED, PLAYER1_SCORE) + "%");
            disableCOLUMN_buttons();
          } else {
            AIturnSIMPLE2();
            
            if(!CheckY()){//determines if AI has won after that turn
              System.out.println("AI WON");
              GAMES_PLAYED++;
              AI_SCORE++;
              stat3Label.setText("AI PLAYER WINS :" + AI_SCORE);
              stat3_3Label.setText("AI PLAYER WIN PERCENTAGE : " + calculatePERCENTAGE(GAMES_PLAYED, AI_SCORE) + "%");
              disableCOLUMN_buttons();
            }
          }
        } else  if(GAMESELECT==3) {//COMPUTER VS PLAYER
          System.out.println("Game Select 3"); //test in console
          counter++; //add 1 to determine turns
          DropY(column);    
          
          if(!CheckY()){//determines if AI has won after that turn
            System.out.println("YELLOW WON");
            GAMES_PLAYED++;
            PLAYER2_SCORE++;
            stat2Label.setText("PLAYER 2 WINS :" + PLAYER2_SCORE);
            stat2_2Label.setText("PLAYER 2 WIN PERCENTAGE : " + calculatePERCENTAGE(GAMES_PLAYED, PLAYER2_SCORE) + "%");
            disableCOLUMN_buttons();
          } else {
            AIturnSIMPLE();
            
            if(!CheckR()){//determines if RED has won after that turn
              System.out.println("AI WON");
              GAMES_PLAYED++;
              AI_SCORE++;
              stat3Label.setText("AI PLAYER WINS :" + AI_SCORE);
              stat3_3Label.setText("AI PLAYER WIN PERCENTAGE : " + calculatePERCENTAGE(GAMES_PLAYED, AI_SCORE) + "%");
              disableCOLUMN_buttons();
            }
          }
        }else if(GAMESELECT==4) {
          boolean AIturn = false; //creates boolean to determine if it's AI's turn
          if (counter % 2 != 0) {
            DropR(column);//activates player's turn, then prints board
            counter++;
            
            if(!CheckR()){//determines if AI has won after that turn
              System.out.println("RED WON"); //test in console
              GAMES_PLAYED++; //add to total games played
              PLAYER1_SCORE++; //add to score
              stat1Label.setText("PLAYER 1 WINS :  " + PLAYER1_SCORE); //output player's score
              stat1_1Label.setText("PLAYER 1 WIN PERCENTAGE : " + calculatePERCENTAGE(GAMES_PLAYED, PLAYER1_SCORE) + "%");
              disableCOLUMN_buttons();
            } 
          }
          if ((counter % 2 == 0)&& (counter<5)) {//XYXYX
            AIturnSIMPLE2();
            counter++;
            
            if(!CheckY()){ //determines if player has won after that turn
              System.out.println("YELLOW WON");
              GAMES_PLAYED++;
              AI_SCORE++;
              stat3Label.setText("AI PLAYER WINS :" + AI_SCORE);
              stat3_3Label.setText("AI WIN PERCENTAGE : " + calculatePERCENTAGE(GAMES_PLAYED, AI_SCORE) + "%");
              disableCOLUMN_buttons();
            }
          }else if ((counter % 2 == 0)) {  
            AIturnMEDIUM();//activates player's turn, then prints board
            counter++;
            
            if(!CheckY()){ //determines if player has won after that turn
              System.out.println("YELLOW WON");
              GAMES_PLAYED++;
              AI_SCORE++;
              stat3Label.setText("AI PLAYER WINS :" + AI_SCORE);
              stat3_3Label.setText("AI WIN PERCENTAGE : " + calculatePERCENTAGE(GAMES_PLAYED, AI_SCORE) + "%");
              disableCOLUMN_buttons();
            }
          }
          displayArray(board); 
        }
        ////////////////////////////////////////*********COLUMN 7**********\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\ 
      } else if (command.equals("Column 7")) { //if user clicks Restart Button
        System.out.println("Column 7 pressed"); //test in console
        int column=6;
        int marker = 1;
        System.out.println("Marker : " +  marker); //test in console
        counter++; //add 1 to determine turns
        
        //check if even or odd to take turns
        if(GAMESELECT==1) {//PLAYER VS PLAYER
          
          //check if even or odd to take turns
          if (counter % 2 == 0) { //if even Y
            DropY(column);
            
            if(!CheckY()){//determines if Y has won after that turn
              System.out.println("YELLOW WON");
              GAMES_PLAYED++;
              PLAYER2_SCORE++;
              stat2Label.setText("PLAYER 2 WINS :" + PLAYER2_SCORE);
              stat2_2Label.setText("PLAYER 2 WIN PERCENTAGE : " + calculatePERCENTAGE(GAMES_PLAYED, PLAYER2_SCORE) + "%");
              disableCOLUMN_buttons();
            } 
          } else if (counter % 2 != 0) { //if odd R   
            DropR(column);
            
            if(!CheckR()){//determines if RED has won after that turn
              System.out.println("RED WON");
              GAMES_PLAYED++;
              PLAYER1_SCORE++;
              stat1Label.setText("PLAYER 1 WINS : " + PLAYER1_SCORE);
              stat1_1Label.setText("PLAYER 1 WIN PERCENTAGE : " + calculatePERCENTAGE(GAMES_PLAYED, PLAYER1_SCORE) + "%");
              disableCOLUMN_buttons();
            } 
          }
          displayArray(board); //display current board
        } else  if (GAMESELECT == 2) {//PLAYER VS. COMPUTER
          System.out.println("Game Select 2"); //test in console
          DropR(column);      
          
          if(!CheckR()){//determines if RED has won after that turn
            System.out.println("RED WON");
            GAMES_PLAYED++;
            PLAYER1_SCORE++;
            stat1Label.setText("PLAYER 1 WINS : " + PLAYER1_SCORE);
            stat1_1Label.setText("PLAYER 1 WIN PERCENTAGE : " + calculatePERCENTAGE(GAMES_PLAYED, PLAYER1_SCORE) + "%");
            disableCOLUMN_buttons();
          } else {
            AIturnSIMPLE2();
            
            if(!CheckY()){//determines if AI has won after that turn
              System.out.println("AI WON");
              GAMES_PLAYED++;
              AI_SCORE++;
              stat3Label.setText("AI PLAYER WINS :" + AI_SCORE);
              stat3_3Label.setText("AI PLAYER WIN PERCENTAGE : " + calculatePERCENTAGE(GAMES_PLAYED, AI_SCORE) + "%");
              disableCOLUMN_buttons();
            }
          }
        } else  if(GAMESELECT==3) {//COMPUTER VS PLAYER
          System.out.println("Game Select 3"); //test in console
          counter++; //add 1 to determine turns
          DropY(column);        
          
          if(!CheckY()){//determines if AI has won after that turn
            System.out.println("YELLOW WON");
            disableCOLUMN_buttons();
          } else {
            AIturnSIMPLE();
            if(!CheckR()){//determines if RED has won after that turn
              System.out.println("AI WON");
              disableCOLUMN_buttons();
            }
          }
        }else if(GAMESELECT==4) { 
          boolean AIturn = false; //creates boolean to determine if it's AI's turn
          if (counter % 2 != 0) {
            DropR(column);//activates player's turn, then prints board
            counter++;
            
            if(!CheckR()){//determines if AI has won after that turn
              System.out.println("RED WON"); //test in console
              GAMES_PLAYED++; //add to total games played
              PLAYER1_SCORE++; //add to score
              stat1Label.setText("PLAYER 1 WINS :  " + PLAYER1_SCORE); //output player's score
              stat1_1Label.setText("PLAYER 1 WIN PERCENTAGE : " + calculatePERCENTAGE(GAMES_PLAYED, PLAYER1_SCORE) + "%");
              disableCOLUMN_buttons();
            } 
          }
          if ((counter % 2 == 0)&& (counter<5)) {//XYXYX
            AIturnSIMPLE2();
            counter++;       
            
            if(!CheckY()){ //determines if player has won after that turn
              System.out.println("YELLOW WON");
              GAMES_PLAYED++;
              AI_SCORE++;
              stat3Label.setText("AI PLAYER WINS :" + AI_SCORE);
              stat3_3Label.setText("AI WIN PERCENTAGE : " + calculatePERCENTAGE(GAMES_PLAYED, AI_SCORE) + "%");
              disableCOLUMN_buttons();
            }
          }else if ((counter % 2 == 0)) {
            AIturnMEDIUM();//activates player's turn, then prints board
            counter++;  
            
            if(!CheckY()){ //determines if player has won after that turn
              System.out.println("YELLOW WON");
              GAMES_PLAYED++;
              AI_SCORE++;
              stat3Label.setText("AI PLAYER WINS :" + AI_SCORE);
              stat3_3Label.setText("AI WIN PERCENTAGE : " + calculatePERCENTAGE(GAMES_PLAYED, AI_SCORE) + "%");
              disableCOLUMN_buttons();
            }
          }
          displayArray(board); 
        }
      }
    }
      
    
    
    ////////////////////////////////////////*********DISPLAY BOARD**********\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    public static void displayArray (String[][] board) {  //display board
      for (int row = 0; row < HEIGHT; row ++) {
        for (int col = 0; col < WIDTH; col ++) { 
          //test on console
          System.out.print(board[row][col] + "");
        }
        System.out.println(); //test on console
      }
    }
    ////////////////////////////////////////*********DROP RED CHIP**********\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\     
    public static void DropR(int column){
      boolean isCol_1_Enabled=true;//check if col1 is enabled
      boolean isCol_2_Enabled=true;//check if col2 is enabled
      boolean isCol_3_Enabled=true;//check if col3 is enabled
      boolean isCol_4_Enabled=true;//check if col4 is enabled
      boolean isCol_5_Enabled=true;//check if col5 is enabled
      boolean isCol_6_Enabled=true;//check if col6 is enabled
      boolean isCol_7_Enabled=true;//check if col7 is enabled
      int marker=1;
      //shows whos turn
      System.out.println("Player 1 turn");
      while(true){//loop trhough till false    
        if (board[BOTTOM_ROW][column] == "O") { //checks to see if space is blank, puts R there if it is
          board[BOTTOM_ROW][column] = "R";
          imgLabel[BOTTOM_ROW][column].setIcon(icon1);
          break; //breaks loop after placing
        }else if(board[BOTTOM_ROW][column] == "R" || board[BOTTOM_ROW][column] == "Y"){ //if space isn't blank, checks to see if one above is
          if(board[BOTTOM_ROW - marker][column] == "O"){ //puts R if blank
            board[BOTTOM_ROW - marker][column] = "R";
            //disable button if column is full            
            if ((BOTTOM_ROW - marker) == 0) {
              if (column == 0) {
                col1Button.setEnabled(false);//disable button
                isCol_1_Enabled=false;//set is ButtonEnabled to false
              } else if (column == 1) {
                col2Button.setEnabled(false);//disable button
                isCol_2_Enabled=false;//set is ButtonEnabled to false
              } else if (column == 2) {
                col3Button.setEnabled(false);//disable button
                isCol_3_Enabled=false;//set is ButtonEnabled to false
              } else if (column == 3) {
                col4Button.setEnabled(false);//disable button
                isCol_4_Enabled=false;//set is ButtonEnabled to false
              } else if (column == 4) {
                col5Button.setEnabled(false);//disable button
                isCol_5_Enabled=false;//set is ButtonEnabled to false
              } else if (column == 5) {
                col6Button.setEnabled(false);//disable button
                isCol_6_Enabled=false;//set is ButtonEnabled to false
              } else if (column == 6) {
                col7Button.setEnabled(false);//disable button
                isCol_7_Enabled=false;//set is ButtonEnabled to false
              }
            }
            if((!isCol_1_Enabled)&&(!isCol_2_Enabled)&&(!isCol_3_Enabled)&&(!isCol_4_Enabled)&&(!isCol_5_Enabled)&&(!isCol_6_Enabled)&&(!isCol_7_Enabled)){
              System.out.println("Draw");//if all buttons are siabled and there are no wins, call a draw
            }
            imgLabel[BOTTOM_ROW - marker][column].setIcon(icon1);//place red CHIP
            break; //breaks loop after placing
          }
        }
        marker ++; //adds one to counter if the space wasn't blank, then loops again
        System.out.println("Marker R: " +  marker); //test in console
        System.out.println("Red added");
        if(marker == HEIGHT){ //checks to see if at end of column
          System.out.println("That column is full");
          break;
        }
      }
    }
    
    ////////////////////////////////////////*********DROP YELLOW CHIP**********\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\     
    public static void DropY(int column){
      boolean isCol_1_Enabled=true;//check if col1 is enabled
      boolean isCol_2_Enabled=true;//check if col2 is enabled
      boolean isCol_3_Enabled=true;//check if col3 is enabled
      boolean isCol_4_Enabled=true;//check if col4 is enabled
      boolean isCol_5_Enabled=true;//check if col5 is enabled
      boolean isCol_6_Enabled=true;//check if col6 is enabled
      boolean isCol_7_Enabled=true;//check if col7 is enabled
      int marker = 1;
      //shows whos turn
      System.out.println("Player 2 turn");
      //loop while true
      while(true){
        if (board[BOTTOM_ROW][column] == "O") { //checks to see if space is blank, puts X there if it is
          board[BOTTOM_ROW][column] = "Y";
          imgLabel[BOTTOM_ROW][column].setIcon(icon2);//place Yellow Chip
          break; //breaks loop after placing
        } else if(board[BOTTOM_ROW][column] == "R" || board[BOTTOM_ROW][column] == "Y"){ //if space isn't blank, checks to see if one above is
          if(board[BOTTOM_ROW - marker][column] == "O"){ //puts X if blank
            board[BOTTOM_ROW - marker][column] = "Y";
            System.out.println("BOTTOM_ROW - marker: " + (BOTTOM_ROW - marker));
            //disable button if column is full
            if ((BOTTOM_ROW - marker) == 0) {
              if (column == 0) {
                col1Button.setEnabled(false);//disable button
                isCol_1_Enabled=false;//set is ButtonEnabled to false
              } else if (column == 1) {
                col2Button.setEnabled(false);//disable button
                isCol_2_Enabled=false;//set is ButtonEnabled to false
              } else if (column == 2) {
                col3Button.setEnabled(false);//disable button
                isCol_3_Enabled=false;//set is ButtonEnabled to false
              } else if (column == 3) {
                col4Button.setEnabled(false);//disable button
                isCol_4_Enabled=false;//set is ButtonEnabled to false
              } else if (column == 4) {
                col5Button.setEnabled(false);//disable button
                isCol_5_Enabled=false;//set is ButtonEnabled to false
              } else if (column == 5) {
                col6Button.setEnabled(false);//disable button
                isCol_6_Enabled=false;//set is ButtonEnabled to false
              } else if (column == 6) {
                col7Button.setEnabled(false);//disable button
                isCol_7_Enabled=false;//set is ButtonEnabled to false
              }
            }
            if((!isCol_1_Enabled)&&(!isCol_2_Enabled)&&(!isCol_3_Enabled)&&(!isCol_4_Enabled)&&(!isCol_5_Enabled)&&(!isCol_6_Enabled)&&(!isCol_7_Enabled)){
              System.out.println("Draw");//if all columns are full and there are no wins, call a DRAW
            }
            imgLabel[BOTTOM_ROW - marker][column].setIcon(icon2);//place YELLOW chip
            break; //breaks loop after placing
          }
        }
        marker ++; //adds one to counter if the space wasn't blank, then loops again
        System.out.println("Marker Y: " +  marker); //test in console
        System.out.println("Yellow added");
        if(marker == HEIGHT){ //checks to see if at end of column
          System.out.println("That column is full");
          break;
        }
      }
    }
    
    ////////////////////////////////////////*********SIMPLE (random) AI (RED)**********\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\     
    public static void AIturnSIMPLE(){
      boolean isCol_1_Enabled=true;//check if col1 is enabled
      boolean isCol_2_Enabled=true;//check if col2 is enabled
      boolean isCol_3_Enabled=true;//check if col3 is enabled
      boolean isCol_4_Enabled=true;//check if col4 is enabled
      boolean isCol_5_Enabled=true;//check if col5 is enabled
      boolean isCol_6_Enabled=true;//check if col6 is enabled
      boolean isCol_7_Enabled=true;//check if col7 is enabled
      //creates a counter
      int counter = 1;
      //shows whos turn
      System.out.println("Simple AI");
      //gets input
      Random randomGenerator = new Random();
      int column = randomGenerator.nextInt(6);
      int marker=1;
      //shows whos turn
      System.out.println("Player 1 turn");
      //loop while trye
      while(true){       
        if (board[BOTTOM_ROW][column] == "O") { //checks to see if space is blank, puts R there if it is
          board[BOTTOM_ROW][column] = "R";
          imgLabel[BOTTOM_ROW][column].setIcon(icon1);//place red chip
          break; //breaks loop after placing
        }else if(board[BOTTOM_ROW][column] == "R" || board[BOTTOM_ROW][column] == "Y"){ //if space isn't blank, checks to see if one above is
          if(board[BOTTOM_ROW - marker][column] == "O"){ //puts R if blank
            board[BOTTOM_ROW - marker][column] = "R";
            //if column is full disbale button
            if ((BOTTOM_ROW - marker) == 0) {
              if (column == 0) {
                col1Button.setEnabled(false);//disable button
                isCol_1_Enabled=false;//set is ButtonEnabled to false
              } else if (column == 1) {
                col2Button.setEnabled(false);//disable button
                isCol_2_Enabled=false;//set is ButtonEnabled to false
              } else if (column == 2) {
                col3Button.setEnabled(false);//disable button
                isCol_3_Enabled=false;//set is ButtonEnabled to false
              } else if (column == 3) {
                col4Button.setEnabled(false);//disable button
                isCol_4_Enabled=false;//set is ButtonEnabled to false
              } else if (column == 4) {
                col5Button.setEnabled(false);//disable button
                isCol_5_Enabled=false;//set is ButtonEnabled to false
              } else if (column == 5) {
                col6Button.setEnabled(false);//disable button
                isCol_6_Enabled=false;//set is ButtonEnabled to false
              } else if (column == 6) {
                col7Button.setEnabled(false);//disable button
                isCol_7_Enabled=false;//set is ButtonEnabled to false
              }
            }
            if((!isCol_1_Enabled)&&(!isCol_2_Enabled)&&(!isCol_3_Enabled)&&(!isCol_4_Enabled)&&(!isCol_5_Enabled)&&(!isCol_6_Enabled)&&(!isCol_7_Enabled)){
              System.out.println("Draw");//if all columns full and no wins, call DRAW
            }
            imgLabel[BOTTOM_ROW - marker][column].setIcon(icon1);//drop RED chip
            break; //breaks loop after placing
          }
        }
        marker ++; //adds one to counter if the space wasn't blank, then loops again
        System.out.println("Marker R: " +  marker); //test in console
        System.out.println("Red added");
        if(marker == HEIGHT){ //checks to see if at end of column
          System.out.println("That column is full");
          AIturnSIMPLE();
          break;
        }
      }
    }
    
    ////////////////////////////////////////*********SIMPLE (random) AI (YELLOW)**********\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\     
    public static void AIturnSIMPLE2(){
      boolean isCol_1_Enabled=true;//check if col1 is enabled
      boolean isCol_2_Enabled=true;//check if col2 is enabled
      boolean isCol_3_Enabled=true;//check if col3 is enabled
      boolean isCol_4_Enabled=true;//check if col4 is enabled
      boolean isCol_5_Enabled=true;//check if col5 is enabled
      boolean isCol_6_Enabled=true;//check if col6 is enabled
      boolean isCol_7_Enabled=true;//check if col7 is enabled
      //creates a counter
      int counter = 1;
      //shows whos turn
      System.out.println("Simple AI");
      //gets input
      Random randomGenerator = new Random();
      int column = randomGenerator.nextInt(6);
      int marker = 1;
      //shows whos turn
      System.out.println("Player 2 turn");
      //loop while true
      while(true){
        if (board[BOTTOM_ROW][column] == "O") { //checks to see if space is blank, puts X there if it is
          board[BOTTOM_ROW][column] = "Y";
          imgLabel[BOTTOM_ROW][column].setIcon(icon2);//place YELLOW chip
          break; //breaks loop after placing
        } else if(board[BOTTOM_ROW][column] == "R" || board[BOTTOM_ROW][column] == "Y"){ //if space isn't blank, checks to see if one above is
          if(board[BOTTOM_ROW - marker][column] == "O"){ //puts X if blank
            board[BOTTOM_ROW - marker][column] = "Y";
            System.out.println("BOTTOM_ROW - marker: " + (BOTTOM_ROW - marker));
            //if column is full disbale button
            if ((BOTTOM_ROW - marker) == 0) {
              if (column == 0) {
                col1Button.setEnabled(false);//disable button
                isCol_1_Enabled=false;//set is ButtonEnabled to false
              } else if (column == 1) {
                col2Button.setEnabled(false);//disable button
                isCol_2_Enabled=false;//set is ButtonEnabled to false
              } else if (column == 2) {
                col3Button.setEnabled(false);//disable button
                isCol_3_Enabled=false;//set is ButtonEnabled to false
              } else if (column == 3) {
                col4Button.setEnabled(false);//disable button
                isCol_4_Enabled=false;//set is ButtonEnabled to false
              } else if (column == 4) {
                col5Button.setEnabled(false);//disable button
                isCol_5_Enabled=false;//set is ButtonEnabled to false
              } else if (column == 5) {
                col6Button.setEnabled(false);//disable button
                isCol_6_Enabled=false;//set is ButtonEnabled to false
              } else if (column == 6) {
                col7Button.setEnabled(false);//disable button
                isCol_7_Enabled=false;//set is ButtonEnabled to false
              }
            }
            if((!isCol_1_Enabled)&&(!isCol_2_Enabled)&&(!isCol_3_Enabled)&&(!isCol_4_Enabled)&&(!isCol_5_Enabled)&&(!isCol_6_Enabled)&&(!isCol_7_Enabled)){
              System.out.println("Draw");//if all columns full and now wins, call a draw
            }
            imgLabel[BOTTOM_ROW - marker][column].setIcon(icon2);
            break; //breaks loop after placing
          }
        }
        marker ++; //adds one to counter if the space wasn't blank, then loops again
        System.out.println("Marker Y: " +  marker); //test in console
        System.out.println("Yellow added");
        if(marker == HEIGHT){ //checks to see if at end of column
          System.out.println("That column is full");
          AIturnSIMPLE2();
          break;
        }
      }
    } 
    ////////////////////////////////////////*********CHECK HORIZONTAL (RED)**********\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\     
    public static boolean CheckRHorizontal(){
      //creates boolean to act as flag
      boolean flag = true;
      //creates counter
      int counter = 0;
      while(flag){
        //goes through board horizontally
        for(int height = 0; HEIGHT > height; height += 1){
          for(int width = 0; WIDTH > width; width += 1){
            if(board[height][width] == "R"){ //if it finds an R, add 1 to counter
              counter += 1;
            }else{
              counter = 0; // if next piece is not an R, set counter to 0
            }
            if(counter >= 4){
              System.out.println("Player 1 wins"); //if counter is greater or equal to 4, player wins
              JOptionPane.showMessageDialog (null, "Player 1 wins", "Message", mc);
              flag = false;
            }
          }
        }
        break;
      }
      return flag;
    }
    ////////////////////////////////////////*********CHECK VERTICAL (RED)**********\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\     
    public static boolean CheckRVertical(){
      //creates boolean to act as flag
      boolean flag = true;
      //creates counter
      int counter = 0;
      while(flag){
        //goes through board vertically
        for(int width = 0; WIDTH > width; width += 1){
          for(int height = 0; HEIGHT > height; height += 1){
            if(board[height][height] == "R"){ //if it finds an R, add 1 to counter
              counter += 1;
            }else{
              counter = 0; // if next piece is not an R, set counter to 0
            }
            if(counter >= 4){
              System.out.println("Player 1 wins"); //if counter is greater or equal to 4, player wins
              JOptionPane.showMessageDialog (null, "Player 1 wins", "Message", mc);
              flag = false;
            }
          }
        }
        break;
      }
      return flag;
    }
    
    ////////////////////////////////////////*********CHECK VERTICAL (YELLOW)**********\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\     
    public static boolean CheckYVertical(){
      //creates boolean to act as flag
      boolean flag = true;
      //creates counter
      int counter = 0;
      while(flag){
        //goes through board vertically
        for(int width = 0; WIDTH > width; width += 1){
          for(int height = 0; HEIGHT > height; height += 1){
            if(board[height][width] == "Y"){ //if it finds an Y, add 1 to counter
              counter += 1;
            }else{
              counter = 0; // if next piece is not an Y, set counter to 0
            }
            if(counter >= 4){
              System.out.println("Player 2 wins"); //if counter is greater or equal to 4, player wins
              JOptionPane.showMessageDialog (null, "Player 2 wins", "Message", mc);
              flag = false;
            }
          }
        }
        break;
      }
      return flag;
    }
    
    ////////////////////////////////////////*********CHECK HORIZONTAL (YELLOW)**********\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\ 
    public static boolean CheckYHorizontal(){
      //creates boolean to act as flag
      boolean flag = true;
      //creates counter
      int counter = 0;
      while(flag){
        //goes through board vertically
        for(int height = 0; HEIGHT > height; height += 1){
          for(int width = 0; WIDTH > width; width += 1){
            if(board[height][width] == "Y"){ //if it finds an Y, add 1 to counter
              counter += 1;
            } else {
              counter = 0; // if next piece is not an Y, set counter to 0
            }
            if(counter >= 4){
              System.out.println("Player 2 wins"); //if counter is greater or equal to 4, player wins
              JOptionPane.showMessageDialog (null, "Player 2 wins", "Message", mc);
              flag = false;
            }
          }
        }
        break;
      }
      return flag;
    }
    ////////////////////////////////////////*********CHECK DIAGONAL (RED forward)**********\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\     
    public static boolean CheckRDiagonalForward(){
      //flag
      boolean flag = true;
      //counter
      int counter = 0;
      //check boolean
      Boolean check = false;
      //checkers
      int checkColumn = 6;
      int checkRow = 1;
      while(flag){ //goes through until an R is found
        for(int height = 0; HEIGHT > height; height += 1){
          for(int width = 0; WIDTH > width; width += 1){
            if(board[height][width] == "R"){ //if R is found, add one to counter and go into loop
              counter = 1;
              System.out.println("Counter: " + counter);
              check = true;
              while(check){ //goes through diagonally looking for Rs
                if((height - checkRow >= 0)  && (width + checkColumn < 6)){
                  if(board[height - checkRow][width + checkColumn] == "R"){
                    counter ++; //if R is found, add 1 to counter
                    System.out.println("Counter: " + counter);
                  } else {
                    counter = 0;
                  }
                }
                //adds 1 to checkers
                checkColumn++;
                checkRow += 1;
                if(checkColumn == 0 || checkRow == WIDTH -1){ //if outside of board, break
                  check = false;
                  break;
                }
                if(counter >= 4){
                  System.out.println("Player 1 wins"); //if counter is greater or equal to 4, player wins
                  JOptionPane.showMessageDialog (null, "Player 1 wins", "Message", mc);
                  check = false;
                  flag = false;
                  break;
                }
              }
              break;
            }
            if(counter >= 4){
              flag = false;
              break;
            }
            //resets counter and checkers          
            checkColumn = 6;
            checkRow = 1;
            counter = 0;
          }
        }
        break;
      }
      return flag;    
    }
    
    ////////////////////////////////////////*********CHECK DIAGONAL (YELLOW forward)**********\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\ 
    public static boolean CheckYDiagonalForward(){
      //flag
      boolean flag = true;
      //counter
      int counter = 0;
      //check boolean
      Boolean check = false;
      //checkers
      int checkColumn = 6;
      int checkRow = 1;
      while(flag){ //goes through until an Y is found
        for(int height = 0; HEIGHT > height; height += 1){
          for(int width = 0; WIDTH > width; width += 1){
            if(board[height][width] == "Y"){ //if Y is found, add one to counter and go into loop
              counter = 1;
              System.out.println("Counter: " + counter);
              check = true;
              while(check){ //goes through diagonally looking for Ys
                if((height - checkRow >= 0)  && (width + checkColumn < 6)){
                  if(board[height - checkRow][width + checkColumn] == "Y"){
                    counter ++; //if Y is found, add 1 to counter
                    System.out.println("Counter: " + counter);
                  } else {
                    counter = 0;
                  }
                }    
                //adds 1 to checkers
                checkColumn++;
                checkRow += 1;
                if(checkColumn == 0 || checkRow == WIDTH -1){ //if outside of board, break
                  check = false;
                  break;
                }
                if(counter >= 4){
                  System.out.println("Player 2 wins"); //if counter is greater or equal to 4, player wins
                  JOptionPane.showMessageDialog (null, "Player 2 wins", "Message", mc);
                  check = false;
                  flag = false;
                  break;
                }
              }
            }
            if(counter >= 4){
              flag = false;
              break;
            }
            //resets counter and checkers
            checkColumn = 1;
            checkRow = 1;
          }
        }
        break;
      }
      return flag;
    }
    
    ////////////////////////////////////////*********CHECK DIAGONAL (RED back)**********\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    public static boolean CheckRDiagonalBack(){
      //flag
      boolean flag = true;
      //counter
      int counter = 0;
      //check boolean
      Boolean check = false;
      //checkers
      int checkColumn = 1;
      int checkRow = 1;
      while(flag){ //goes through until an R is found
        for(int height = 0; HEIGHT > height; height += 1){
          for(int width = 0; WIDTH > width; width += 1){
            if(board[height][width] == "R"){ //if R is found, add one to counter and go into loop
              counter += 1;
              check = true;
              while(check){ //goes through diagonally looking for Rs
                if(width - checkColumn >= 0 && height - checkRow >= 0){
                  if(board[height - checkRow][width - checkColumn] == "R"){
                    counter += 1; //if R is found, add 1 to counter
                  }
                }
                //adds 1 to checkers
                checkColumn += 1;
                checkRow += 1;
                if(checkColumn == 0 || checkRow == WIDTH -1){ //if outside of board, break
                  check = false;
                  break;
                }
                if(counter >= 4){
                  System.out.println("Player 1 wins"); //if counter is greater or equal to 4, player wins
                  JOptionPane.showMessageDialog (null, "Player 1 wins", "Message", mc);
                  check = false;
                  flag = false;
                  break;
                }
              }
            }
            if(counter >= 4){
              flag = false;
              break;
            }
            //resets counter and checkers
            counter = 0;
            checkColumn = 1;
            checkRow = 1;
          }
        }
        break;
      }
      return flag;
    }
    
    ///////////////////////////////////////*********CHECK DIAGONAL (YELLOW back)**********\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\    
    public static boolean CheckYDiagonalBack(){
      //flag
      boolean flag = true;
      //counter
      int counter = 0;
      //check boolean
      Boolean check = false;
      //checkers
      int checkColumn = 1;
      int checkRow = 1;
      while(flag){
        //goes through until an Y is found
        for(int height = 0; HEIGHT > height; height += 1){
          for(int width = 0; WIDTH > width; width += 1){
            if(board[height][width] == "Y"){ //if Y is found, add one to counter and go into loop
              counter += 1;
              check = true;
              while(check){ //goes through diagonally looking for Os
                if(width - checkColumn >= 0 && height - checkRow >= 0){
                  if(board[height - checkRow][width - checkColumn] == "Y"){
                    counter += 1; //if Y is found, add 1 to counter
                  }
                }
                //adds 1 to checkers
                checkColumn += 1;
                checkRow += 1;
                if(checkColumn == 0 || checkRow == WIDTH -1){ //if outside of board, break
                  check = false;
                  break;
                }
                if(counter >= 4){
                  System.out.println("Player 2 wins"); //if counter is greater or equal to 4, player wins
                  JOptionPane.showMessageDialog (null, "Player 2 wins", "Message", mc);
                  check = false;
                  flag = false;
                  break;
                }
              }
            }
            if(counter >= 4){
              flag = false;
              break;
            }
            //resets counter and checkers
            counter = 0;
            checkColumn = 1;
            checkRow = 1;
          }
        }
        break;
      }
      return flag;
    } 
    /////////////////////////////////////////////*********MEDIUM AI**********\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\     
    public static void AIturnMEDIUM(){
      //variables needed for the method
      boolean block=false;//block set to false
      boolean prevent_user_win=false;//smart place to prevent 
      int col;//column integer (where to put the chip)
      
      //RED: R R R _ check
      for (int i = 5;i>=0;i--){
        for (int j = 0;j<3;j++){
          if (board[i][j]!="O" && board[i][j+1]=="R" &&board[i][j+2]=="R" && board[i][j+3]=="O"){//RRRO
            col=j+3;//colum is the last position
            block =true;//AI found a wining position for user
            for(int x=5;x>=0;x--){
              if(board[x][col]=="O"){//if the RRR_ last slot is empty, put a Y chip to block user
                board[x][col]="Y";//place yellow chip
                break;
              }
            }
          }
        }
      }  
      //RED COLUMN WIN CHECK 
      // O
      // R
      // R
      // R
      for (int j = 0;j<7;j++){
        for (int i = 5;i>=3;i--){
          if (board[i][j]!="O" && board[i-1][j]=="R" &&board[i-2][j]=="R" && board[i-3][j]=="O"){//if three RED chips are found stack vertically, attempt to block it
            col=j;//colum equals the column where the stack is found
            block = true;//AI found a wining position for user
            for(int x=5;x>=0;x--){
              if(board[x][col]=="O"){
                board[x][col]="Y";
                break;
              }
            }
          }
        }
      }  
      //RED: R _ R R check
      for (int i = 5;i>=0;i--){
        for (int j = 0;j<3;j++){
          if (board[i][j]!="O" && board[i][j+2]=="R" &&board[i][j+3]=="R" && board[i][j+1]=="O"){//RORR
            col=j+1;//column is the secon one
            block =true;//AI found a wining position for user
            for(int x=5;x>=0;x--){
              if(board[x][col]=="O"){//if slot is empty, place chip there
                board[x][col]="Y";
                break;
              }
            }
          }
        }
      }  
      //RED: R R _ R check
      for (int i = 5;i>=0;i--){
        for (int j = 0;j<3;j++){
          if (board[i][j]!="O" && board[i][j+1]=="R" &&board[i][j+3]=="R" && board[i][j+2]=="O"){//RROR
            col=j+2;//column is the third one
            block =true;//AI found a wining position for user
            for(int x=5;x>=0;x--){
              if(board[x][col]=="O"){//if slot is empty, place chip there
                board[x][col]="Y";
                break;
              }
            }
          }
        }
      }  
      if(!block){
        for (int i = 5;i>=0;i--){
          for (int j = 0;j<3;j++){
            if (board[i][j]!="O" && board[i][j+1]=="Y" &&board[i][j+2]=="Y" && board[i][j+3]=="O"){//YYYO
              col=j+3;
              prevent_user_win=true;
              for(int x=5;x>=0;x--){
                if(board[x][col]=="O"){//if slot is empty, place chip there
                  board[x][col]="Y";
                  break;
                }
              }
            }
          }
        }   
        for (int j = 0;j<7;j++){
          for (int i = 5;i>=3;i--){
            if (board[i][j]!="O" && board[i-1][j]=="Y" &&board[i-2][j]=="Y" && board[i][j-3]=="Y"){//vertically: OYYY
              col=j;
              prevent_user_win=true;
              for(int x=5;x>=0;x--){
                if(board[x][col]=="O"){//if slot is empty, place chip there
                  board[x][col]="Y";
                  break;
                }
              }
            }
          }
        }
      }
    }

    ////////////////////////////////////////*********CHECK WINS (RED)**********\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\    
    public static boolean CheckR(){
      //creates flag
      boolean flag = true;
      //checks all Rs at once, for clearner main loop
      if(!CheckRVertical() || !CheckRHorizontal()|| !CheckRDiagonalBack()|| !CheckRDiagonalForward()){
        flag = false;
      }
      return flag;
    }
    
    ////////////////////////////////////////*********CHECK WINS (YELLOW)**********\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\    
    public static boolean CheckY(){
      //creates flag
      boolean flag = true;
      //checks all Os at once, for clearner main loop
      if(!CheckYVertical() || !CheckYHorizontal() || !CheckYDiagonalBack() || !CheckYDiagonalForward()){
        flag = false;
      }
      return flag;
    }
    ////////////////////////////////////////*********DISABLE COLUMNS**********\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    public static boolean disableCOLUMN_buttons(){
      boolean enabled=true;
      col1Button.setEnabled(false);//disable column 1 button
      col2Button.setEnabled(false);//disable column 2 button
      col3Button.setEnabled(false);//disable column 3 button
      col4Button.setEnabled(false);//disable column 4 button
      col5Button.setEnabled(false);//disable column 5 button
      col6Button.setEnabled(false);//disable column 6 button
      col7Button.setEnabled(false);//disable column 7 button
      enabled=false;
      return enabled;
    }
    ////////////////////////////////////////*********ENABLE COLUMNS**********\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\ 
    public static boolean enableCOLUMN_buttons(){
      boolean enabled=false;
      col1Button.setEnabled(true);//enable column 1 button
      col2Button.setEnabled(true);//enable column 2 button
      col3Button.setEnabled(true);//enable column 3 button
      col4Button.setEnabled(true);//enable column 4 button
      col5Button.setEnabled(true);//enable column 5 button
      col6Button.setEnabled(true);//enable column 6 button
      col7Button.setEnabled(true);//enable column 7 button
      enabled=true;
      return enabled;
    }
    ////////////////////////////////////////*********RESET BOARD **********\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\ 
    
    public static void resetBOARD(){
      System.out.print("Reseting board...\n"); //test on console
      
      for (int row = 0; row < HEIGHT; row ++) {
        for (int col = 0; col < WIDTH; col ++) { 
          //test on console
          board[row][col] = "O";
          System.out.print(board[row][col] + "");
          
          //Create the label with image, centered
          imgLabel[row][col].setIcon(icon);
        }
        System.out.println(); //test on console
      }
      //reset board
      time = 0;
    }
    
    
    ////////////////////////////////////////*********DISPLAY BOARD**********\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\     
    public static void displayBoard() {
      //prints the board
      for (int height = 0; height < HEIGHT; height ++) {
        for (int width = 0; width < WIDTH ; width ++) {
          System.out.print(board[height][width]);
        }
        System.out.println();
      }
      System.out.println();
    }
    
    ////////////////////////////////////////*********SCORE**********\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    public static int calculatePERCENTAGE(int total_games, int player_games){   
      int percentage = (player_games/total_games)*100;//calculate percentage
      return percentage;//return percentage
    }
    
    ////////////////////////////////////////*********TIMER**********\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    class MyTask extends TimerTask {
      public void run() {
        time++;
        if (time <= 300) { //until 5 minutes
          timerLabel.setText("Timer: " + time + "s"); //refresh
        } else {
          System.out.println("Timer stops now...");
          time = 0;
          JOptionPane.showMessageDialog (null, "Time's up!", "Warning", mc); //show
          resetBOARD();//reset the board
        }
      }
    }
    
    ////////////////////////////////////////*********GUI**********\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\   
    public static void main(String args[]) throws Exception {  //main method
      ConnectFourGUI frame = new ConnectFourGUI(); //one line of code to create the frame  
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //when closed it resets interactions
    } //end main method
  }
