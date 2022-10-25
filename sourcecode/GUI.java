/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package knightstourgui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Caleb Probst
 */
public class GUI {
    private final Color offWhite = new Color(210,210,210);
    private Color PRIM_COLOR = Color.RED;
    private Color SECOND_COLOR = Color.BLACK;
    private Color ACTIVATION_COLOR = Color.GREEN;
    private Color LABEL_COLOR = Color.BLACK;
    private final Font TITLE_FONT = new Font("DejaVu Sans Mono", Font.BOLD, 32);
    private final Font LABEL_FONT = new Font("DejaVu Sans Mono", Font.BOLD, 24);
    private final Font BUTTON_FONT = new Font("DejaVu Sans Mono", Font.PLAIN, 12);
    private final Font COMBO_FONT = new Font("DejaVu Sans Mono", Font.BOLD, 12);
    private final Font ERROR_FONT = new Font("DejaVu Sans Mono", Font.BOLD, 44);
    private final Font NUMBER_FONT = new Font("DejaVu Sans Mono", Font.ITALIC, 26);
    private final int CHESS_BOARD = 8;
    private int MAX_TIMER = 2000;
    private int MIN_TIMER = 100;
    private JFrame window;
    private JPanel mPanel, bPanel, tPanel, rPanel, cPanel;
    private JLabel currMove, speedValueLabel;
    private JLabel[][] displayNumbers;
    private JButton start;
    private JSlider speed;
    private JComboBox tours;
    private JTextField directory;
    private JPanel[][] display;
    private String textDirectory;
    private int speedValue, timerValue, numberOfTours, tourNumber, currentMove, moveDup;
    private boolean isValidDir=true, buttonStarted = false, timerStart = false, range, dup, validMove;
    private String[] moves;
    private Timer timer;
    private JMenuBar bar;
    private JMenu tools, about, otherFunc;
    private JMenuItem maxSpeed, minSpeed, changePrimColor, changeSecondColor, changeActivationColor, changeLabelColor, author, header, chess3d, programLang, openVM, freeMoney; 
    private Color[] colorOptions = new Color[]{Color.RED,Color.BLUE,Color.CYAN,Color.BLACK, 
        Color.DARK_GRAY,Color.GREEN,Color.LIGHT_GRAY, Color.MAGENTA,Color.ORANGE,Color.PINK,
        Color.WHITE,Color.YELLOW};
    
    /**
     * This is the instance of the GUI. This runs the entire program.
     */
    public GUI (){
        // Initialize frame
        window = new JFrame("Knights Tour Simulation");
        window.setSize(900,600);
        window.setLocationRelativeTo(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Create Panels
        mPanel = new JPanel();
        mPanel.setLayout(new BorderLayout());
        mPanel.setBackground(offWhite);
        bPanel = new JPanel();
        bPanel.setLayout(new GridLayout(0,4,10,10));
        bPanel.setBackground(offWhite);
        tPanel = new JPanel();
        tPanel.setBackground(offWhite);
        cPanel = new JPanel();
        cPanel.setLayout(new GridLayout(CHESS_BOARD,CHESS_BOARD,5,5));
        cPanel.setBackground(Color.BLACK);
        rPanel = new JPanel();
        rPanel.setBackground(offWhite);
        display = new JPanel[CHESS_BOARD][CHESS_BOARD];
        displayNumbers = new JLabel[CHESS_BOARD][CHESS_BOARD];
        for (int i = 0; i < display.length; i++) {
            for (int j = 0; j < display[i].length; j++) {
                display[i][j] = new JPanel();
                displayNumbers[i][j] = new JLabel("");
                if (i % 2 == j % 2){
                   display[i][j].setBackground(PRIM_COLOR);
                   display[i][j].add(displayNumbers[i][j]);
                   displayNumbers[i][j].setFont(NUMBER_FONT);
                }
                else{
                   display[i][j].setBackground(SECOND_COLOR);
                   display[i][j].add(displayNumbers[i][j]);
                   displayNumbers[i][j].setFont(NUMBER_FONT);
                }
            }
        }
        
        // Create other graphical components
        speed = new JSlider();
        speed.setMaximum(100);
        speed.setMinimum(1);
        directory = new JTextField("Please enter a directory:");
        directory.setFont(BUTTON_FONT);
        start = new JButton("Start");
        start.setFont(BUTTON_FONT);
        start.setEnabled(false);
        tours = new JComboBox();
        tours.setFont(COMBO_FONT);
        currMove = new JLabel("Current Move:");
        currMove.setFont(TITLE_FONT);
        speed.setValue(1);
        speed.setEnabled(false);
        speedValueLabel = new JLabel("Speed: 1");
        speedValueLabel.setFont(LABEL_FONT);
        
        // Create menubar, menus, and items
        bar = new JMenuBar();
        tools = new JMenu("Tools");
        about = new JMenu("About");
        otherFunc = new JMenu("Other functionality");
        bar.add(tools);
        bar.add(about);
        bar.add(otherFunc);
        maxSpeed = new JMenuItem("Change maximum speed");
        minSpeed = new JMenuItem("Change minimum speed");
        changePrimColor = new JMenuItem("Change primary color");
        changeSecondColor = new JMenuItem("Change secondary color");
        changeActivationColor = new JMenuItem("Change activation color");
        changeLabelColor = new JMenuItem("Change label color");
        tools.add(maxSpeed);
        tools.add(minSpeed);
        tools.addSeparator();
        tools.add(changePrimColor);
        tools.add(changeSecondColor);
        tools.add(changeActivationColor);
        tools.add(changeLabelColor);
        author = new JMenuItem("About the author");
        header = new JMenuItem("About the file");
        about.add(author);
        about.add(header);
        chess3d = new JMenuItem("Change to 3D Chess");
        programLang = new JMenuItem("Change programming language");
        openVM = new JMenuItem("Open a Linux virtual machine");
        freeMoney = new JMenuItem("FREE MONEY!!!!");
        otherFunc.add(chess3d);
        otherFunc.add(programLang);
        otherFunc.add(openVM);
        otherFunc.add(freeMoney);
        
        // Add tours to JComboBox
        tours.addItem("Please select a knights tour:");
        
        // Add functionality to text fieldc
        directory.addActionListener(new TextListener());
        
        // Add functionality to speed slider
        speed.addChangeListener(new ValueListener());
        
        // Add functionality to the ComboBox
        tours.addActionListener(new ItemChangeListener());
        
        // Add functionality to the Start button
        start.addActionListener(new ButtonChangeListener());
        
        // Add functionality to the main display panel
        timer = new Timer(MAX_TIMER, new TimerListener());
        timer.start();
        
        // Add functionality to tool menu items
        maxSpeed.addActionListener(new MaxSpeedListener());
        minSpeed.addActionListener(new MinSpeedListener());
        changeSecondColor.addActionListener(new SecondColorListener());
        changePrimColor.addActionListener(new PrimColorListener());
        changeActivationColor.addActionListener(new ActivationColorListener());
        changeLabelColor.addActionListener(new LabelColorListener());
        
        // Add functionality to about menu items
        author.addActionListener(new AuthorListener());
        header.addActionListener(new HeaderListener());
        
        // Add functionality to other functionality menu items (hehe)
        chess3d.addActionListener(new ChessListener());
        programLang.addActionListener(new ProgramLanguageListener());
        openVM.addActionListener(new OpenVMListener());
        freeMoney.addActionListener(new FreeMoneyListener());
        
        
        // Add the panels
        window.add(mPanel);
        mPanel.add(bPanel, BorderLayout.PAGE_END);
        mPanel.add(tPanel, BorderLayout.PAGE_START);
        mPanel.add(cPanel, BorderLayout.CENTER);
        mPanel.add(rPanel, BorderLayout.LINE_END);
        for (JPanel[] jpa : display) {
            for (JPanel disp : jpa) {
                cPanel.add(disp);
            }
        }
        
        // Add right graphical components (selection of knights tour)
        rPanel.add(tours);
        // Add center graphical components (display)
        // Add top graphical components (Display of current move)
        tPanel.add(currMove);
        // Add bottom graphical components (speed slider, directory input, start button
        bPanel.add(speed);
        bPanel.add(speedValueLabel);
        bPanel.add(directory);
        directory.setToolTipText("Please enter a directory");
        bPanel.add(start);
        
        // Set visibility
        window.setVisible(true);
        window.setJMenuBar(bar);
    } // End of GUI instance
    
    /**
     * This is the listener for the text box where the directory is entered.
     */
    public class TextListener implements ActionListener{

        /**
         *
         * @param e This is the event parameter for the Action Listener
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            textDirectory = directory.getText();
            // Create scanner fromFile using String directory
            Scanner fromFile = null;
            try {
                fromFile = new Scanner(new File(textDirectory));
            }
            catch (FileNotFoundException ex){
                directory.setText("Could not find file.");
                isValidDir=false;
            }
            if (isValidDir){
            while (fromFile.hasNext()){
                numberOfTours++;
                fromFile.nextLine();
            }
            fromFile.close();
            try{
                tours.removeAllItems();
            }
            catch (NullPointerException ex){}
            tours.addItem("Please select a knights tour:");
            for(int i = 0; i<numberOfTours; i++){
                tours.addItem("Tour #" + (i+1));
            }
            numberOfTours=0;
            try {
                fromFile = new Scanner(new File(textDirectory));
            }
            catch (FileNotFoundException ex){
                System.out.println("File not found, please try again");
                System.exit(0);
            }
            
        }
            else {
                isValidDir=true;
                try{
                    tours.removeAllItems();
                }
                catch (NullPointerException ex){}
                tours.addItem("Please select a knights tour:");
            }
        }
    }

    /**
     * This is the listener for the slider. It updates when the slider is moved.
     */
    public class ValueListener implements ChangeListener{

        /**
         *
         * @param e This is the parameter for the change listener
         */
        @Override
        public void stateChanged(ChangeEvent e) {
            speedValue = speed.getValue();
            timerValue = (MAX_TIMER+(MAX_TIMER-MIN_TIMER)/(speed.getMaximum()-
                    speed.getMinimum()))-(MAX_TIMER-MIN_TIMER)/(speed.getMaximum()-
                    speed.getMinimum())*speed.getValue();
            speedValueLabel.setText("Speed: " + speedValue);
            timer.setDelay(timerValue);
        }
        
    }

    /**
     * This is the listener for the combo box, where the tour is selected.
     */
    public class ItemChangeListener implements ActionListener{

        /**
         *
         * @param e This is the parameter for the action listener
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (tours.getSelectedItem().equals("Please select a knights tour:")){
                start.setEnabled(false);
                speed.setEnabled(false);
            }
            else{
                start.setEnabled(true);
                String selected = tours.getSelectedItem().toString();
                tourNumber = Integer.parseInt(selected.replaceAll("[^0-9]", ""));
            }
        }
        
    }

    /**
     * This is the listener for the start/stop button.
     */
    public class ButtonChangeListener implements ActionListener{

        /**
         *
         * @param e This is the event variable for the action listener
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            buttonStarted = !buttonStarted;
            if (buttonStarted){
                speed.setEnabled(true);
                directory.setEnabled(false);
                tours.setEnabled(false);
                start.setText("Stop");
                startTour();
            }
            else{
                speed.setEnabled(false);
                directory.setEnabled(true);
                tours.setEnabled(true);
                start.setText("Start");
                timerStart = false;
            }
        }

        /**
         * This is what starts the tour. When the button is clicked, this method 
         * is ran.
         */
        public void startTour (){
            resetBoard();
            currentMove = 0;
            Scanner fromFile = null;
            try {
                fromFile = new Scanner(new File(textDirectory));
            }
            catch (FileNotFoundException ex){
                System.out.println("File not found, please try again");
                System.exit(0);
            }
            for(int i=0; i<(tourNumber-1); i++){
                fromFile.nextLine();
            }
            String unparsed = fromFile.nextLine();
            moves = parseString(unparsed);
            timerStart = true;
        }

        /**
         * This resets the board. This method is called once a new tour has 
         * been started.
         */
        public void resetBoard(){
            currMove.setFont(TITLE_FONT);
            currMove.setForeground(Color.BLACK);
            currMove.setText("Current Move: ");
            for (int i = 0; i < display.length; i++) {
            for (int j = 0; j < display[i].length; j++) {
                if (i % 2 == j % 2){
                   display[i][j].setBackground(PRIM_COLOR);
                   displayNumbers[i][j].setText("");
                }
                else{
                   display[i][j].setBackground(SECOND_COLOR);
                   displayNumbers[i][j].setText("");
                }
            }
        }
        }

        /**
        * This class is meant to unparse any string that is thrown at it. 
        * It takes each string of data given through the unparsed parameter 
        * and parses it into an array of length 64, as that is the area of a 
        * chess board.
        * @param unparsed This is the unparsed string, requires a space in between 
        * each of the elements, For ex: "C2 B3 E5"
        * @return An array of strings, split into their respective values. For ex: 
        * {C2, B3, E5}
        */
        public String[] parseString(String unparsed){
            Scanner in = new Scanner(unparsed);
            String[] array = new String[CHESS_BOARD*CHESS_BOARD];
            for (int i = 0; i < CHESS_BOARD*CHESS_BOARD; i++) {
                try{
                    array[i] = in.next();
                }
                catch(NoSuchElementException e){
                    System.out.println("This file does not include 64 moves, incomplete tour.");
                    System.exit(0);
                }
            }
            return array;
        }
        
    }

    /**
     * This is the listener that controls the display. The delay is given by the 
     * slider value.
     */
    public class TimerListener implements ActionListener {

        /**
         *
         * @param e This is the variable for the action listener
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (timerStart && currentMove<CHESS_BOARD*CHESS_BOARD){
                range = false;
                dup = false;
                validMove = false;
                moveDup = -2;
                range = withinRange(moves[currentMove]);
                if (currentMove == 0);
                else if(range && currentMove!=0){
                    validMove = isValidMove(moves[currentMove-1], moves[currentMove]);
                }
                if (range && (validMove || currentMove==0)){
                    moveDup = checkDup(moves);
                    if (moveDup == -1){
                        display[moves[currentMove].charAt(0)-'A'][moves[currentMove].charAt(1)-'1'].setBackground(ACTIVATION_COLOR);
                        currMove.setText("Current Move: " + moves[currentMove]);
                        displayNumbers[moves[currentMove].charAt(0)-'A'][moves[currentMove].charAt(1)-'1'].setText("" + ++currentMove);
                        displayNumbers[moves[currentMove-1].charAt(0)-'A'][moves[currentMove-1].charAt(1)-'1'].setForeground(LABEL_COLOR);
                        afterTour(currentMove);
                    }
                    else{
                        try{
                            currMove.setFont(ERROR_FONT);
                            currMove.setForeground(PRIM_COLOR);
                            currMove.setText("ERROR: INVALID TOUR");
                            currentMove = CHESS_BOARD*CHESS_BOARD;
                            afterTour(CHESS_BOARD*CHESS_BOARD);
                        }
                        catch (ArrayIndexOutOfBoundsException ex){}
                    }
                }
            }
        }

        /**
         * This is what happens after the tour has finished. It enables/disables 
         * things on the GUI
         * @param bound This parameter is the number of the current move the 
         * piece is on.
         */
        public void afterTour(int bound){
            if (bound == CHESS_BOARD*CHESS_BOARD){
                start.setEnabled(true);
                start.setText("Start");
                speed.setEnabled(false);
                tours.setEnabled(true);
                directory.setEnabled(true);
            }
        }

        /**
        * This class is made to check that the move given is within a certain 
        * range of the chess board. The standard is an 8x8 board. 
        * The chessboard has the following format:
        * <p>
        * &nbsp;&nbsp;&nbsp;A&nbsp; B &nbsp;C &nbsp;D &nbsp;E &nbsp;F &nbsp;G &nbsp;H<br>
        * 1<br>
        * 2<br>
        * 3<br>
        * 4<br>
        * 5<br>
        * 6<br>
        * 7<br>
        * 8<br>
        * </p>
        * @param move A singular chess move given by the program, in the format 
        * Letter (horizontal)Number (vertical).
        * @return This class will return a true or false value:<br>
        * True for if the move is within range.<br>
        * False for if the move is not within range.
        */
        public boolean withinRange(String move){
            char horiz = move.charAt(0);
            char vert = move.charAt(1);
            if (move.length()>2){
                return false;
            }
            return ((horiz>='A' && vert>='1') && (horiz<=('A'+(CHESS_BOARD-1)) && vert<=('1'+(CHESS_BOARD-1))));
        }

        /**
        * This class is made to verify that the current move is a valid move for a 
        * knight. A knight may only move by 1 or 2 in the vertical direction 
        * and 2 or 1 in the horizontal direction, respectively. This class 
        * takes the last move, preMove, and makes an array of all the possible moves 
        * and compares the current move to that array.
        * @param preMove This is the previous move of the knight, before the one 
        * that it verifies.
        * @param move This is the current move of the knight that we are verifying.
        * @return This class returns a true or false value:<br>
        * True for if the move is valid for a knight.<br>
        * False for if the move is NOT valid for a knight.
        */
        public boolean isValidMove(String preMove, String move){
            char pHoriz = preMove.charAt(0);
            char pVert = preMove.charAt(1);
            char nHoriz = move.charAt(0);
            char nVert = move.charAt(1);
            char[][] possMoves = new char[8][2];
            possMoves[0][0] = (char)(pHoriz+1);
            possMoves[1][0] = (char)(pHoriz+2);
            possMoves[2][0] = (char)(pHoriz+2);
            possMoves[3][0] = (char)(pHoriz+1);
            possMoves[4][0] = (char)(pHoriz-1);
            possMoves[5][0] = (char)(pHoriz-2);
            possMoves[6][0] = (char)(pHoriz-1);
            possMoves[7][0] = (char)(pHoriz-2);

            possMoves[0][1] = (char)(pVert+2);
            possMoves[1][1] = (char)(pVert+1);
            possMoves[2][1] = (char)(pVert-1);
            possMoves[3][1] = (char)(pVert-2);
            possMoves[4][1] = (char)(pVert-2);
            possMoves[5][1] = (char)(pVert-1);
            possMoves[6][1] = (char)(pVert+2);
            possMoves[7][1] = (char)(pVert+1);
            // Check Horizontal
            int stopValue1 = -1;
            int stopValue2 = -1;
            for (int i = 0; i < possMoves.length; i++) {
                if (nHoriz==possMoves[i][0]){
                    if (stopValue1 == -1){
                        stopValue1 = i;
                    }
                    else {
                        stopValue2 = i;
                    }
                }
            }
            // Check Vertical from horizontal component
            try{
                return (nVert == possMoves[stopValue1][1]) || (nVert == possMoves[stopValue2][1]);
            }
            catch (ArrayIndexOutOfBoundsException e){
                return false;
            }
        }

        /**
        * This class checks to see if any of the moves in the array are duplicates. 
        * It first creates a virtual board of (boolean array) false values. This array 
        * is then used to see if a value has been used yet, if it hasn't, the value 
        * is set to true, if it has been used, it returns the move that it stopped on.
        * If the class reaches the end, it returns the string "No dup" which is 
        * then handled by the program.
        * @param moves This is the array of moves that it analyzes.
        * @return This class returns a String:<br>
        * If it reaches the end of the array with no duplications, it returns 
        * "No dup", which is then handled by the program.<br>
        * If it finds a duplication, it returns the FIRST duplicated value it finds.
        */
        public int checkDup(String[] moves){
            // Create and initialize the board
            boolean[][] board = new boolean[CHESS_BOARD][CHESS_BOARD];
            for (boolean[] b : board) {
                for (int j = 0; j < b.length; j++) {
                    b[j] = false;
                }
            }
            // Take all values and set as true only if false
            for (int i = 0; i<moves.length; i++) {
                char horiz = moves[i].charAt(0);
                char vert = moves[i].charAt(1);
                try{
                    if (!board[horiz-'A'][vert-'1']) {
                        board[horiz-'A'][vert-'1']=true;
                    } else {
                        return i;
                    }
                }
                catch (ArrayIndexOutOfBoundsException e){
                    return i;
                }
            }
            return -1;
        }
    }

    /**
     * This is the listener for the max speed menu item
     */
    public class MaxSpeedListener implements ActionListener{

        /**
         *
         * @param e This is the event variable for the action listener
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            int time = Integer.parseInt(JOptionPane.showInputDialog(null, "Please enter a value for the maximum speed, in milliseconds", "Set maximum speed"));
            if (time < 0 || time<MIN_TIMER){
                throw new IllegalArgumentException("Invalid value for MAX_TIMER");
            }
            else{
                MAX_TIMER = time;
                timer.setDelay((MAX_TIMER+(MAX_TIMER-MIN_TIMER)/(speed.getMaximum()-
                    speed.getMinimum()))-(MAX_TIMER-MIN_TIMER)/(speed.getMaximum()-
                    speed.getMinimum())*speed.getValue());
            }
        }
    }

    /**
     * This is the listener for the minimum speed menu item
     */
    public class MinSpeedListener implements ActionListener{

        /**
         *
         * @param e This is the event variable for the action listener
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            int time = Integer.parseInt(JOptionPane.showInputDialog(null, "Please enter a value for the minimum speed, in milliseconds", "Set minimum speed"));
            if (time < 0 || time>MAX_TIMER){
                throw new IllegalArgumentException("Invalid value for MIN_TIMER");
            }
            else{
                MIN_TIMER = time;
                timer.setDelay((MAX_TIMER+(MAX_TIMER-MIN_TIMER)/(speed.getMaximum()-
                    speed.getMinimum()))-(MAX_TIMER-MIN_TIMER)/(speed.getMaximum()-
                    speed.getMinimum())*speed.getValue());
            }
        }
    }

    /**
     * This is the listener for the primary color menu item listener
     */
    public class PrimColorListener implements ActionListener{

        /**
         *
         * @param e This is the event variable for the action listener
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            /*private Color[] colorOptions = new Color[]{Color.RED,Color.BLUE,Color.CYAN,Color.BLACK, 
        Color.DARK_GRAY,Color.GREEN,Color.LIGHT_GRAY, Color.MAGENTA,Color.ORANGE,Color.PINK,
        Color.WHITE,Color.YELLOW};*/
            String[] colorNames = new String[]{"Red", "Blue", "Cyan", "Black",
            "Dark Gray", "Green", "Light Gray", "Magenta", "Orange", "Pink", "White",
            "Yellow", "Random!"};
            String input = (String)JOptionPane.showInputDialog(null, "Please pick a primary color", 
                    "Primary Color Change",JOptionPane.PLAIN_MESSAGE,
                    null, colorNames, colorNames[0]);
            if (input == null){}
            else {
                switch (input){
                    case "Red":         PRIM_COLOR=colorOptions[0]; break;
                    case "Blue":        PRIM_COLOR=colorOptions[1]; break;
                    case "Cyan":        PRIM_COLOR=colorOptions[2]; break;
                    case "Black":       PRIM_COLOR=colorOptions[3]; break;
                    case "Dark Gray":   PRIM_COLOR=colorOptions[4]; break;
                    case "Green":       PRIM_COLOR=colorOptions[5]; break;
                    case "Light Gray":  PRIM_COLOR=colorOptions[6]; break;
                    case "Magenta":     PRIM_COLOR=colorOptions[7]; break;
                    case "Orange":      PRIM_COLOR=colorOptions[8]; break;
                    case "Pink":        PRIM_COLOR=colorOptions[9]; break;
                    case "White":       PRIM_COLOR=colorOptions[10]; break;
                    case "Yellow":      PRIM_COLOR=colorOptions[11]; break;
                    case "Random!":     PRIM_COLOR=randColor(); break;
                }
                updateColorGrid();
            }
        }

        /**
         * This picks a random color
         * @return Returns random color
         */
        public Color randColor(){
            Color randomColor = null;
            Random rand = new Random();
            randomColor = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
            return randomColor;
        }

        /**
         * This updates the grid of the selected color.
         */
        public void updateColorGrid(){
            for (int i = 0; i < display.length; i++) {
            for (int j = 0; j < display[i].length; j++) {
                if (i % 2 == j % 2){
                   display[i][j].setBackground(PRIM_COLOR);
                }
                else{
                   display[i][j].setBackground(SECOND_COLOR);
                }
            }
        }
        }
    }

    /**
     * Listener for the secondary color menu item.
     */
    public class SecondColorListener implements ActionListener{

        /**
         *
         * @param e This is the event variable for the action listener
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            /*private Color[] colorOptions = new Color[]{Color.RED,Color.BLUE,Color.CYAN,Color.BLACK, 
        Color.DARK_GRAY,Color.GREEN,Color.LIGHT_GRAY, Color.MAGENTA,Color.ORANGE,Color.PINK,
        Color.WHITE,Color.YELLOW};*/
            String[] colorNames = new String[]{"Red", "Blue", "Cyan", "Black",
            "Dark Gray", "Green", "Light Gray", "Magenta", "Orange", "Pink", "White",
            "Yellow", "Random!"};
            String input = (String)JOptionPane.showInputDialog(null, "Please pick a secondary color", 
                    "Secondary Color Change",JOptionPane.PLAIN_MESSAGE,
                    null, colorNames, colorNames[0]);
            if (input == null){}
            else{
                switch (input){
                    case "Red":         SECOND_COLOR=colorOptions[0]; break;
                    case "Blue":        SECOND_COLOR=colorOptions[1]; break;
                    case "Cyan":        SECOND_COLOR=colorOptions[2]; break;
                    case "Black":       SECOND_COLOR=colorOptions[3]; break;
                    case "Dark Gray":   SECOND_COLOR=colorOptions[4]; break;
                    case "Green":       SECOND_COLOR=colorOptions[5]; break;
                    case "Light Gray":  SECOND_COLOR=colorOptions[6]; break;
                    case "Magenta":     SECOND_COLOR=colorOptions[7]; break;
                    case "Orange":      SECOND_COLOR=colorOptions[8]; break;
                    case "Pink":        SECOND_COLOR=colorOptions[9]; break;
                    case "White":       SECOND_COLOR=colorOptions[10]; break;
                    case "Yellow":      SECOND_COLOR=colorOptions[11]; break;
                    case "Random!":     SECOND_COLOR=randColor(); break;
                }
                updateColorGrid();
            }
        }

        /**
         * This picks a random color
         * @return Returns random color
         */
        public Color randColor(){
            Color randomColor = null;
            Random rand = new Random();
            randomColor = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
            return randomColor;
        }

        /**
         * This updates the grid of the selected color.
         */
        public void updateColorGrid(){
            for (int i = 0; i < display.length; i++) {
            for (int j = 0; j < display[i].length; j++) {
                if (i % 2 == j % 2){
                   display[i][j].setBackground(PRIM_COLOR);
                }
                else{
                   display[i][j].setBackground(SECOND_COLOR);
                }
            }
        }
        }
    }

    /**
     * This is the listener for the activation color menu item
     */
    public class ActivationColorListener implements ActionListener{

        /**
         *
         * @param e This is the event variable for the action listener
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            /*private Color[] colorOptions = new Color[]{Color.RED,Color.BLUE,Color.CYAN,Color.BLACK, 
        Color.DARK_GRAY,Color.GREEN,Color.LIGHT_GRAY, Color.MAGENTA,Color.ORANGE,Color.PINK,
        Color.WHITE,Color.YELLOW};*/
            String[] colorNames = new String[]{"Red", "Blue", "Cyan", "Black",
            "Dark Gray", "Green", "Light Gray", "Magenta", "Orange", "Pink", "White",
            "Yellow", "Random!"};
            String input = (String)JOptionPane.showInputDialog(null, "Please pick an activation color", 
                    "Activation Color Change",JOptionPane.PLAIN_MESSAGE,
                    null, colorNames, colorNames[0]);
            if (input == null){}
            else{
                switch (input){
                    case "Red":         ACTIVATION_COLOR=colorOptions[0]; break;
                    case "Blue":        ACTIVATION_COLOR=colorOptions[1]; break;
                    case "Cyan":        ACTIVATION_COLOR=colorOptions[2]; break;
                    case "Black":       ACTIVATION_COLOR=colorOptions[3]; break;
                    case "Dark Gray":   ACTIVATION_COLOR=colorOptions[4]; break;
                    case "Green":       ACTIVATION_COLOR=colorOptions[5]; break;
                    case "Light Gray":  ACTIVATION_COLOR=colorOptions[6]; break;
                    case "Magenta":     ACTIVATION_COLOR=colorOptions[7]; break;
                    case "Orange":      ACTIVATION_COLOR=colorOptions[8]; break;
                    case "Pink":        ACTIVATION_COLOR=colorOptions[9]; break;
                    case "White":       ACTIVATION_COLOR=colorOptions[10]; break;
                    case "Yellow":      ACTIVATION_COLOR=colorOptions[11]; break;
                    case "Random!":     ACTIVATION_COLOR=randColor(); break;
                }
            }
        }

        /**
         * This picks a random color
         * @return Returns random color
         */
        public Color randColor(){
            Color randomColor = null;
            Random rand = new Random();
            randomColor = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
            return randomColor;
        }
    }

    /**
     * This is the listener for the label color menu item
     */
    public class LabelColorListener implements ActionListener{

        /**
         *
         * @param e This is the event variable for the action listener
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            /*private Color[] colorOptions = new Color[]{Color.RED,Color.BLUE,Color.CYAN,Color.BLACK, 
        Color.DARK_GRAY,Color.GREEN,Color.LIGHT_GRAY, Color.MAGENTA,Color.ORANGE,Color.PINK,
        Color.WHITE,Color.YELLOW};*/
            String[] colorNames = new String[]{"Red", "Blue", "Cyan", "Black",
            "Dark Gray", "Green", "Light Gray", "Magenta", "Orange", "Pink", "White",
            "Yellow", "Random!"};
            String input = (String)JOptionPane.showInputDialog(null, "Please pick a label color", 
                    "Activation Color Change",JOptionPane.PLAIN_MESSAGE,
                    null, colorNames, colorNames[0]);
            if (input == null){}
            else{
                switch (input){
                    case "Red":         LABEL_COLOR=colorOptions[0]; break;
                    case "Blue":        LABEL_COLOR=colorOptions[1]; break;
                    case "Cyan":        LABEL_COLOR=colorOptions[2]; break;
                    case "Black":       LABEL_COLOR=colorOptions[3]; break;
                    case "Dark Gray":   LABEL_COLOR=colorOptions[4]; break;
                    case "Green":       LABEL_COLOR=colorOptions[5]; break;
                    case "Light Gray":  LABEL_COLOR=colorOptions[6]; break;
                    case "Magenta":     LABEL_COLOR=colorOptions[7]; break;
                    case "Orange":      LABEL_COLOR=colorOptions[8]; break;
                    case "Pink":        LABEL_COLOR=colorOptions[9]; break;
                    case "White":       LABEL_COLOR=colorOptions[10]; break;
                    case "Yellow":      LABEL_COLOR=colorOptions[11]; break;
                    case "Random!":     LABEL_COLOR=randColor(); break;
                }
            }
        }

        /**
         * This picks a random color
         * @return Returns random color
         */
        public Color randColor(){
            Color randomColor = null;
            Random rand = new Random();
            randomColor = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
            return randomColor;
        }
    }

    /**
     * This is the listener for the author menu item.
     */
    public class AuthorListener implements ActionListener{

        /**
         *
         * @param e This is the event variable for the action listener
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(null, "Author: Caleb Probst", "About the Author", JOptionPane.PLAIN_MESSAGE);
        }
        
    }

    /**
     * This is the listener for the header menu item
     */
    public class HeaderListener implements ActionListener{

        /**
         *
         * @param e This is the event variable for the action listener
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(null, "Name: Caleb Probst\nDate Created: November 25th, 2021\nDate Finished: December 10th, 2021");
        }
        
    }

    /**
     * This is the listener for the 3D Chess mode menu item
     */
    public class ChessListener implements ActionListener{

        /**
         *
         * @param e This is the event variable for the action listener
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                Desktop.getDesktop().browse(new URL("https://www.youtube.com/watch?v=dQw4w9WgXcQ").toURI());
            } catch (Exception ex) {}
        }
        
    }

    /**
     * THis is the listener for the changing of a programming language menu item
     */
    public class ProgramLanguageListener implements ActionListener{

        /**
         *
         * @param e This is the event variable for the action listener
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                Desktop.getDesktop().browse(new URL("https://www.youtube.com/watch?v=XqZsoesa55w").toURI());
            } catch (Exception ex) {}
        }
        
    }

    /**
     * This is the listener for the Open VM menu item.
     */
    public class OpenVMListener implements ActionListener{

        /**
         *
         * @param e This is the event variable for the action listener
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                Desktop.getDesktop().browse(new URL("https://www.youtube.com/watch?v=ZEbWIpN135U").toURI());
            } catch (Exception ex) {}
        }
        
    }

    /**
     * This is the listener for the free money menu item.
     */
    public class FreeMoneyListener implements ActionListener{

        /**
         *
         * @param e This is the event variable for the action listener
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                Desktop.getDesktop().browse(new URL("https://www.youtube.com/watch?v=1YAf8hFX0M0").toURI());
            } catch (Exception ex) {}
        }
        
    }
}
