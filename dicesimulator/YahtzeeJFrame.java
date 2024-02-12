package dicesimulator;

import static dicesimulator.YahtzeeJFrame.NUM_DICE;
import static dicesimulator.YahtzeeJFrame.NUM_SIDES;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
/**
 *
 * @author Brayden Gilbert 
 */                          
public class YahtzeeJFrame extends javax.swing.JFrame {
    
    public static final int NUM_DICE = 5;
    public static final int NUM_SIDES = 6;

    private enum uiStates {
        RESET_GAME,
        BEFORE_FIRST_ROLL,
        BEFORE_SECOND_ROLL,
        BEFORE_THIRD_ROLL,
        AFTER_THIRD_ROLL,
        SCORING,
        GAME_OVER
    }
    
    //Lower Score Category Constants
    public static final int THREE_OF_A_KIND = 0;
    public static final int FOUR_OF_A_KIND = 1;
    public static final int FULL_HOUSE = 2;
    public static final int SMALL_STRAIGHT = 3;
    public static final int LARGE_STRAIGHT = 4;
    public static final int YAHTZEE = 5;
    public static final int CHANCE = 6;
    
    /**
     * Creates new form NewJFrame
     * @throws java.lang.Exception
     */
    public YahtzeeJFrame() throws Exception{
        myDice = new Dice(NUM_DICE, NUM_SIDES);
        initComponents();
        
        //create an array of jbuttons to fill with the dice buttons
        holdButtons = new DiceToggleButton[NUM_DICE];
        holdButtons[0] = holdButton1;
        holdButtons[1] = holdButton2;
        holdButtons[2] = holdButton3;
        holdButtons[3] = holdButton4;
        holdButtons[4] = holdButton5;
        
        //initiates the upper score array based on the size of the gamemodels variables for the size
        upperScoreButtonArray = new JToggleButton[GameModel.NUM_UPPER_SCORE_CATS + 1];
        upperScoreButtonArray[1] = this.jToggleButtonOnes; //note: 1st element is ignored
        upperScoreButtonArray[2] = this.jToggleButtonTwos;
        upperScoreButtonArray[3] = this.jToggleButtonThrees;
        upperScoreButtonArray[4] = this.jToggleButtonFours;
        upperScoreButtonArray[5] = this.jToggleButtonFives;
        upperScoreButtonArray[6] = this.jToggleButtonSixes;
        
        upperScoreTextboxArray = new JTextField[GameModel.NUM_UPPER_SCORE_CATS + 1];
        upperScoreTextboxArray[1] = this.txtOnes;
        upperScoreTextboxArray[2] = this.txtTwos;
        upperScoreTextboxArray[3] = this.txtThrees;
        upperScoreTextboxArray[4] = this.txtFours;
        upperScoreTextboxArray[5] = this.txtFives;
        upperScoreTextboxArray[6] = this.txtSixes;
        
        
        //lower score buttons 
        lowerScoreButtonArray = new JToggleButton[GameModel.NUM_LOWER_SCORE_CATS];
        lowerScoreButtonArray[THREE_OF_A_KIND] = this.jToggleButton3ofaKind;
        lowerScoreButtonArray[FOUR_OF_A_KIND] = this.jToggleButton4ofaKind;
        lowerScoreButtonArray[FULL_HOUSE] = this.jToggleButtonFullHouse;
        lowerScoreButtonArray[SMALL_STRAIGHT] = this.jToggleButtonSmStraight;
        lowerScoreButtonArray[LARGE_STRAIGHT] = this.jToggleButtonLgStraight;
        lowerScoreButtonArray[YAHTZEE] = this.jToggleButtonYahtzee;
        lowerScoreButtonArray[CHANCE] = this.jToggleButtonChance;
        
        //lower score textboxes
        lowerScoreTextboxArray = new JTextField[GameModel.NUM_LOWER_SCORE_CATS];
        lowerScoreTextboxArray[THREE_OF_A_KIND] = this.txt3ofaKind;
        lowerScoreTextboxArray[FOUR_OF_A_KIND] = this.txt4ofaKind;
        lowerScoreTextboxArray[FULL_HOUSE] = this.txtFullHouse;
        lowerScoreTextboxArray[SMALL_STRAIGHT] = this.txtSmStraight;
        lowerScoreTextboxArray[LARGE_STRAIGHT] = this.txtLgStraight;
        lowerScoreTextboxArray[YAHTZEE] = this.txtYahtzee;
        lowerScoreTextboxArray[CHANCE] = this.txtChance;
        
        //initiate the holdArray for hold buttons
        holdArray = new boolean[NUM_DICE]; //Java intializes all these to false
        game = new GameModel();  
        manageUIState(uiStates.RESET_GAME);
        manageUIState(uiStates.BEFORE_FIRST_ROLL);
    }
    
    
    public void manageUIState(uiStates nextState) {
        switch (nextState) {
            case RESET_GAME:  
                game.clearAllUpperScoringCats();
                game.clearAllLowerScoringCats();
                game.clearUsedScoringCats();
                clearAllTextBoxes();
                resetScoreToggleButtons();
                break;
            case BEFORE_FIRST_ROLL:
                clearAndDisableHoldButtons();
                clearHoldArray();
                disableAllScoreButtons();
                rollButton.setEnabled(true);
                break;
            case BEFORE_SECOND_ROLL:
                setHoldButtonsEnabledState(true);
                enableAllUnusedScoreButtons();
                break;
            case BEFORE_THIRD_ROLL:
                break;
            case AFTER_THIRD_ROLL:
                setHoldButtonsEnabledState(false);
                rollButton.setEnabled(false);
                break;
            case SCORING:
                setHoldButtonsEnabledState(false);
                game.nextTurn();
                break;
            case GAME_OVER:
                break;
            default:
                JOptionPane.showMessageDialog(this, "Something suspicious happened in the UiState manager");
                break;
        }
        currentUIState = nextState;
    }
        
        private void clearAndDisableHoldButtons() {
            for (DiceToggleButton element : holdButtons) {
                element.setText("");
                element.setEnabled(false);
                element.setSelected(false);//maybe doesn't do anything
            }
        }
        
        private void setHoldButtonsEnabledState(boolean isEnabled) {
            int i;
            
            for(i = 0; i < myDice.getNumDice(); i++) {
                holdButtons[i].setEnabled(isEnabled);
            }
        }
        
        private void clearHoldArray() {
            for (int i = 0; i < holdArray.length; i++) {
                holdArray[i] = false;
            }
        } 
        
        private void enableHoldButtons() { //for each loop
            for (DiceToggleButton holdButton : holdButtons) {
                holdButton.setEnabled(true);
            }
            /* Same thing as above but this is a normal for loop 
            for (int i = 0; i < holdButtons.length; i++) {
                holdButtons[i].setEnabled(true);
            }
            */
        }

        private void disableAllScoreButtons() {
            int i;
            
            for (i = 1; i <= GameModel.NUM_UPPER_SCORE_CATS; i++) {
                this.upperScoreButtonArray[i].setEnabled(false);
            }
            for (i = 0; i < GameModel.NUM_LOWER_SCORE_CATS; i++) {
                this.lowerScoreButtonArray[i].setEnabled(false);
            }
        }
        
        public void enableAllUnusedScoreButtons() {
           int i;
           
           for (i = 1; i <= GameModel.NUM_UPPER_SCORE_CATS; i++) {
               if(!game.getUsedUpperScoringCatState(i)) {
                   this.upperScoreButtonArray[i].setEnabled(true);
               }
           }
            
            for (i = 0; i < GameModel.NUM_LOWER_SCORE_CATS; i++) {
                if(!game.getUsedLowerScoringCatState(i)) {
                    this.lowerScoreButtonArray[i].setEnabled(true);
                }
            }                     
        }
        
        /**
         * Make each of the text boxes have a blank string in them
         * Make the total text boxes be blank as well 
         */
        private void clearAllTextBoxes() {  // FIX: doesn't clear the graphical UI when game resets just the text on the DiceButtons
            int i;
            
            for(i = 1; i <= GameModel.NUM_UPPER_SCORE_CATS; i++) {
                upperScoreTextboxArray[i].setText("");
            }
            txtUpperScore.setText("");
            txtTotalUpperScore.setText("");
            
            for(i = 0; i < GameModel.NUM_LOWER_SCORE_CATS; i++) {
                lowerScoreTextboxArray[i].setText("");
            }      
            txtUpperScore.setText("");
            txtGrandTotal.setText("");
        }
        
        public void resetScoreToggleButtons() {
            int i;
            
            for(i = 1; i < GameModel.NUM_UPPER_SCORE_CATS; i++) {
                upperScoreButtonArray[i].setEnabled(false);
            }
            
            for(i = 0; i < GameModel.NUM_LOWER_SCORE_CATS; i++) {
                lowerScoreButtonArray[i].setEnabled(false);
            }            
        }
        
        private void scoreUpperCategory(int category) {
            int score = 0;
            int i;

            //set the category so it looks used
            game.setUsedUpperScoreCat(category, true); 

            //manage the UI to the scoring state
            manageUIState(uiStates.SCORING);

            for (i = 0; i < NUM_DICE; i++) {
                if (myDice.getDieValue(i) == category) {
                    score += category;
                }
            }

            //set the instance field in the game model that holds the score
            //for the ones category
            game.setUpperScoreCat(category, score);

            //show the computed score in the UI
            this.upperScoreTextboxArray[category].setText("" + score);

            this.showTotals();

            if (game.getCurrentTurnNum() < GameModel.MAX_NUM_TURNS) { //needs to add GameModel.currentTurnNum() method
                manageUIState(uiStates.BEFORE_FIRST_ROLL);
            }
            else {
                manageUIState(uiStates.GAME_OVER);
            }
        }
        
        private void endLowerScoring() {
            this.showTotals();        
            //Check end of game
            if (game.getCurrentTurnNum() < GameModel.MAX_NUM_TURNS) {
                manageUIState(uiStates.BEFORE_FIRST_ROLL);
            }
            else {
                manageUIState(uiStates.GAME_OVER);
            }
        }
        
        
    /*
        rollButton = new javax.swing.JButton();
        jButtonScore = new javax.swing.JButton();
        holdButton1 = new javax.swing.DiceToggleButton(myDice.getDie(0));
        holdButton2 = new javax.swing.DiceToggleButton(myDice.getDie(1));
        holdButton3 = new javax.swing.DiceToggleButton(myDice.getDie(2));
        holdButton4 = new javax.swing.DiceToggleButton(myDice.getDie(3));
        holdButton5 = new javax.swing.DiceToggleButton(myDice.getDie(4));
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
        
    /*   
        holdButton1 = new DiceToggleButton(myDice.getDie(0));
        holdButton2 = new DiceToggleButton(myDice.getDie(1));
        holdButton3 = new DiceToggleButton(myDice.getDie(2));
        holdButton4 = new DiceToggleButton(myDice.getDie(3));
        holdButton5 = new DiceToggleButton(myDice.getDie(4));
    */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        rollButton = new javax.swing.JButton();
        holdButton1 = new DiceToggleButton(myDice.getDie(0));
        holdButton2 = new DiceToggleButton(myDice.getDie(1));
        holdButton3 = new DiceToggleButton(myDice.getDie(2));
        holdButton4 = new DiceToggleButton(myDice.getDie(3));
        holdButton5 = new DiceToggleButton(myDice.getDie(4));
        jToggleButtonOnes = new javax.swing.JToggleButton();
        jToggleButtonTwos = new javax.swing.JToggleButton();
        jToggleButtonThrees = new javax.swing.JToggleButton();
        jToggleButtonFours = new javax.swing.JToggleButton();
        jToggleButtonFives = new javax.swing.JToggleButton();
        jToggleButtonSixes = new javax.swing.JToggleButton();
        jToggleButtonYahtzee = new javax.swing.JToggleButton();
        jToggleButton3ofaKind = new javax.swing.JToggleButton();
        jToggleButton4ofaKind = new javax.swing.JToggleButton();
        jToggleButtonFullHouse = new javax.swing.JToggleButton();
        jToggleButtonSmStraight = new javax.swing.JToggleButton();
        jToggleButtonLgStraight = new javax.swing.JToggleButton();
        jToggleButtonChance = new javax.swing.JToggleButton();
        txtOnes = new javax.swing.JTextField();
        txtTwos = new javax.swing.JTextField();
        txtThrees = new javax.swing.JTextField();
        txtFours = new javax.swing.JTextField();
        txtFives = new javax.swing.JTextField();
        txtSixes = new javax.swing.JTextField();
        txt3ofaKind = new javax.swing.JTextField();
        txt4ofaKind = new javax.swing.JTextField();
        txtFullHouse = new javax.swing.JTextField();
        txtSmStraight = new javax.swing.JTextField();
        txtLgStraight = new javax.swing.JTextField();
        txtYahtzee = new javax.swing.JTextField();
        txtChance = new javax.swing.JTextField();
        txtUpperScore = new javax.swing.JTextField();
        txtTotalUpperScore = new javax.swing.JTextField();
        txtLowerScore = new javax.swing.JTextField();
        txtGrandTotal = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jButtonNewGame = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        rollButton.setText("Roll");
        rollButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rollButtonActionPerformed(evt);
            }
        });

        holdButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                holdButton1ActionPerformed(evt);
            }
        });

        holdButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                holdButton3ActionPerformed(evt);
            }
        });

        holdButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                holdButton4ActionPerformed(evt);
            }
        });

        holdButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                holdButton5ActionPerformed(evt);
            }
        });

        holdButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                holdButton2ActionPerformed(evt);
            }
        });

        jToggleButtonOnes.setText("Ones");
        jToggleButtonOnes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButtonOnesActionPerformed(evt);
            }
        });

        jToggleButtonTwos.setText("Twos");
        jToggleButtonTwos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButtonTwosActionPerformed(evt);
            }
        });

        jToggleButtonThrees.setText("Threes");
        jToggleButtonThrees.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButtonThreesActionPerformed(evt);
            }
        });

        jToggleButtonFours.setText("Fours");
        jToggleButtonFours.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButtonFoursActionPerformed(evt);
            }
        });

        jToggleButtonFives.setText("Fives");
        jToggleButtonFives.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButtonFivesActionPerformed(evt);
            }
        });

        jToggleButtonSixes.setText("Sixes");
        jToggleButtonSixes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButtonSixesActionPerformed(evt);
            }
        });

        jToggleButtonYahtzee.setText("Yahtzee");
        jToggleButtonYahtzee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButtonYahtzeeActionPerformed(evt);
            }
        });

        jToggleButton3ofaKind.setText("3 of a Kind");
        jToggleButton3ofaKind.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton3ofaKindActionPerformed(evt);
            }
        });

        jToggleButton4ofaKind.setText("4 of a Kind");
        jToggleButton4ofaKind.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton4ofaKindActionPerformed(evt);
            }
        });

        jToggleButtonFullHouse.setText("Full House");
        jToggleButtonFullHouse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButtonFullHouseActionPerformed(evt);
            }
        });

        jToggleButtonSmStraight.setText("Sm Straight");
        jToggleButtonSmStraight.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButtonSmStraightActionPerformed(evt);
            }
        });

        jToggleButtonLgStraight.setText("Lg Straight");
        jToggleButtonLgStraight.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButtonLgStraightActionPerformed(evt);
            }
        });

        jToggleButtonChance.setText("Chance");
        jToggleButtonChance.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButtonChanceActionPerformed(evt);
            }
        });

        txtOnes.setEditable(false);

        txtTwos.setEditable(false);

        txtThrees.setEditable(false);

        txtFours.setEditable(false);

        txtFives.setEditable(false);

        txtSixes.setEditable(false);

        txt3ofaKind.setEditable(false);

        txt4ofaKind.setEditable(false);

        txtFullHouse.setEditable(false);

        txtSmStraight.setEditable(false);

        txtLgStraight.setEditable(false);

        txtYahtzee.setEditable(false);

        txtChance.setEditable(false);

        txtUpperScore.setEditable(false);

        txtTotalUpperScore.setEditable(false);

        txtLowerScore.setEditable(false);

        txtGrandTotal.setEditable(false);

        jLabel1.setText("Upper Score");

        jLabel3.setText("Upper Total");

        jLabel4.setText("Lower Total");

        jLabel5.setText("Grand Total");

        jButtonNewGame.setText("New Game");
        jButtonNewGame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNewGameActionPerformed(evt);
            }
        });

        jLabel6.setText("Lower Section");

        jLabel7.setText("Upper Section");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(holdButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(holdButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(holdButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(holdButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jToggleButtonTwos, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtTwos, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jToggleButtonThrees, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtThrees, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jToggleButtonFours, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtFours, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jToggleButtonFives, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtFives, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jToggleButtonOnes, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtOnes, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jToggleButtonSixes, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(txtSixes, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(18, 18, 18)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addComponent(txtUpperScore, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(txtTotalUpperScore, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(16, 16, 16)
                                        .addComponent(jButtonNewGame, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(101, 101, 101)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel4)
                                    .addComponent(jToggleButton3ofaKind, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jToggleButton4ofaKind, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jToggleButtonFullHouse, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jToggleButtonSmStraight, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                                    .addComponent(jToggleButtonLgStraight, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jToggleButtonYahtzee, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jToggleButtonChance, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addComponent(jLabel5)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(163, 163, 163)
                        .addComponent(rollButton, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtGrandTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(holdButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt3ofaKind, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt4ofaKind, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFullHouse, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSmStraight, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtLgStraight, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtYahtzee, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtChance, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtLowerScore, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(61, 61, 61)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel6)
                .addGap(70, 70, 70))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(holdButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(holdButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(holdButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(holdButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(holdButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addComponent(rollButton, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jToggleButtonOnes, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtOnes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jToggleButtonTwos, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTwos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jToggleButtonThrees, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtThrees, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jToggleButtonFours, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtFours, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jToggleButtonFives, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtFives, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jToggleButtonSixes, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSixes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jToggleButton3ofaKind, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt3ofaKind, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jToggleButton4ofaKind, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt4ofaKind, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jToggleButtonFullHouse, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtFullHouse, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jToggleButtonSmStraight, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSmStraight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jToggleButtonLgStraight, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtLgStraight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jToggleButtonYahtzee, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtYahtzee, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jToggleButtonChance, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtChance, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtLowerScore, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtUpperScore, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtGrandTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(txtTotalUpperScore, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addComponent(jButtonNewGame, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void rollButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rollButtonActionPerformed
        //myDice.roll();
        int i, rollValue;
        for (i = 0; i < NUM_DICE; i++) {
            if (!holdArray[i]) {
                rollValue = myDice.rollDie(i);
                holdButtons[i].setText("" + rollValue);
                //myDice.rollDie(i);
                //game.getMyDice().getDie(i).roll(); //works
            }
        }
        
        
         
        switch (currentUIState) {
            case BEFORE_FIRST_ROLL -> manageUIState(uiStates.BEFORE_SECOND_ROLL);
            case BEFORE_SECOND_ROLL -> manageUIState(uiStates.BEFORE_THIRD_ROLL);
            case BEFORE_THIRD_ROLL -> manageUIState(uiStates.AFTER_THIRD_ROLL);
            default -> {}
        }
    }//GEN-LAST:event_rollButtonActionPerformed

    private void holdButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_holdButton1ActionPerformed
        holdArray[0] = !holdArray[0];
    }//GEN-LAST:event_holdButton1ActionPerformed

    private void holdButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_holdButton2ActionPerformed
        holdArray[1] = !holdArray[1];
    }//GEN-LAST:event_holdButton2ActionPerformed

    private void holdButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_holdButton3ActionPerformed
        holdArray[2] = !holdArray[2];
    }//GEN-LAST:event_holdButton3ActionPerformed

    private void holdButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_holdButton4ActionPerformed
        holdArray[3] = !holdArray[3];
    }//GEN-LAST:event_holdButton4ActionPerformed

    private void holdButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_holdButton5ActionPerformed
        holdArray[4] = !holdArray[4];
    }//GEN-LAST:event_holdButton5ActionPerformed

    private void jToggleButtonOnesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButtonOnesActionPerformed
        scoreUpperCategory(1);
    }//GEN-LAST:event_jToggleButtonOnesActionPerformed

    private void jToggleButtonThreesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButtonThreesActionPerformed
        scoreUpperCategory(3);
    }//GEN-LAST:event_jToggleButtonThreesActionPerformed

    private void jToggleButtonFoursActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButtonFoursActionPerformed
        scoreUpperCategory(4);
    }//GEN-LAST:event_jToggleButtonFoursActionPerformed

    private void jToggleButtonFivesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButtonFivesActionPerformed
        scoreUpperCategory(5);
    }//GEN-LAST:event_jToggleButtonFivesActionPerformed

    private void jToggleButtonSixesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButtonSixesActionPerformed
        scoreUpperCategory(6);
    }//GEN-LAST:event_jToggleButtonSixesActionPerformed

    private void jToggleButtonYahtzeeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButtonYahtzeeActionPerformed
        int score = 0;
        
        game.setUsedLowerScoreCat(YAHTZEE, true);
        manageUIState(uiStates.SCORING);
        
        if (game.isOfAKind(myDice, 5)) {
            score = game.addEmUp(myDice);
        }
        game.setLowerScoreCat(YAHTZEE, score);
        this.lowerScoreTextboxArray[YAHTZEE].setText("" + score);
        endLowerScoring();
    }//GEN-LAST:event_jToggleButtonYahtzeeActionPerformed

    private void jToggleButton3ofaKindActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton3ofaKindActionPerformed
        int score = 0;
        
        game.setUsedLowerScoreCat(THREE_OF_A_KIND, true);
        manageUIState(uiStates.SCORING);
        
        if (game.is3ofaKind(myDice)) {
            score = game.addEmUp(myDice);
        }
        game.setLowerScoreCat(THREE_OF_A_KIND, score);
        this.lowerScoreTextboxArray[THREE_OF_A_KIND].setText("" + score);
        endLowerScoring();
          
    }//GEN-LAST:event_jToggleButton3ofaKindActionPerformed

    private void jToggleButtonFullHouseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButtonFullHouseActionPerformed
        int score = 0;
        
        game.setUsedLowerScoreCat(FULL_HOUSE, true);
        manageUIState(uiStates.SCORING);
        
        if (game.isFullHouse(myDice)) {
            score = game.addEmUp(myDice);
        }
        game.setLowerScoreCat(FULL_HOUSE, score);
        this.lowerScoreTextboxArray[FULL_HOUSE].setText("" + score);
        endLowerScoring();
    }//GEN-LAST:event_jToggleButtonFullHouseActionPerformed

    private void jToggleButtonSmStraightActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButtonSmStraightActionPerformed
        int score = 0;
        
        game.setUsedLowerScoreCat(SMALL_STRAIGHT, true);
        manageUIState(uiStates.SCORING);
        
        if (game.isSmallStraight(myDice)) {
            score = game.addEmUp(myDice);
        }
        game.setLowerScoreCat(SMALL_STRAIGHT, score);
        this.lowerScoreTextboxArray[SMALL_STRAIGHT].setText("" + score);
        endLowerScoring();
    }//GEN-LAST:event_jToggleButtonSmStraightActionPerformed

    private void jToggleButtonLgStraightActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButtonLgStraightActionPerformed
        int score = 0;
        
        game.setUsedLowerScoreCat(LARGE_STRAIGHT, true);
        manageUIState(uiStates.SCORING);
        
        if (game.isLargeStraight(myDice)) {
            score = game.addEmUp(myDice);
        }
        game.setLowerScoreCat(LARGE_STRAIGHT, score);
        this.lowerScoreTextboxArray[LARGE_STRAIGHT].setText("" + score);
        endLowerScoring();
    }//GEN-LAST:event_jToggleButtonLgStraightActionPerformed

    private void jToggleButtonChanceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButtonChanceActionPerformed
        int score = 0;
        
        game.setUsedLowerScoreCat(CHANCE, true);
        manageUIState(uiStates.SCORING);
        
        score = game.addEmUp(myDice);
        
        game.setLowerScoreCat(CHANCE, score);
        this.lowerScoreTextboxArray[CHANCE].setText("" + score);
        
        endLowerScoring();
    }//GEN-LAST:event_jToggleButtonChanceActionPerformed

    private void jToggleButton4ofaKindActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton4ofaKindActionPerformed
        int score = 0;
        
        game.setUsedLowerScoreCat(FOUR_OF_A_KIND, true);
        manageUIState(uiStates.SCORING);
        
        if (game.is4ofaKind(myDice)) {
            score = game.addEmUp(myDice);
        }
        game.setLowerScoreCat(FOUR_OF_A_KIND, score);
        this.lowerScoreTextboxArray[FOUR_OF_A_KIND].setText("" + score);
        
        endLowerScoring();
    }//GEN-LAST:event_jToggleButton4ofaKindActionPerformed

    private void jButtonNewGameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNewGameActionPerformed
        manageUIState(uiStates.RESET_GAME);
        manageUIState(uiStates.BEFORE_FIRST_ROLL);
    }//GEN-LAST:event_jButtonNewGameActionPerformed

    private void jToggleButtonTwosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButtonTwosActionPerformed
        scoreUpperCategory(2);    
    }//GEN-LAST:event_jToggleButtonTwosActionPerformed
    
    public void showTotals() {
        game.updateTotals(); // update the totals
        int upperTotal = game.getSumUpperScores() + game.getBonus();
        
        txtUpperScore.setText("" + game.getSumUpperScores()); // Set the upper score to  
        txtTotalUpperScore.setText("" + upperTotal);
        txtLowerScore.setText("" + game.getSumLowerScores());
        txtGrandTotal.setText("" + game.getGrandTotal());  
        /*
        game.updateTotals(); // update the totals
        txtUpperScore.setText("" + game.getSumUpperScores()); // Set the upper score to  
        txtTotalUpperScore.setText("" + game.getSumUpperScores() + game.getBonus());
        txtLowerScore.setText("" + game.getSumLowerScores());
        txtGrandTotal.setText("" + game.getGrandTotal());  
        */
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(YahtzeeJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(YahtzeeJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(YahtzeeJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(YahtzeeJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run(){      
                try {   
                    new YahtzeeJFrame().setVisible(true);
                } catch (Exception ex) {
                    System.out.println("fdshjfshuiesfgiu");
                    Logger.getLogger(YahtzeeJFrame.class.getName()).log(Level.SEVERE, ex.toString(), ex);
                }
            }
        });
    }
	// DiceToggleButton
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private DiceToggleButton holdButton1;
    private DiceToggleButton holdButton2;
    private DiceToggleButton holdButton3;
    private DiceToggleButton holdButton4;
    private DiceToggleButton holdButton5;
    private javax.swing.JButton jButtonNewGame;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JToggleButton jToggleButton3ofaKind;
    private javax.swing.JToggleButton jToggleButton4ofaKind;
    private javax.swing.JToggleButton jToggleButtonChance;
    private javax.swing.JToggleButton jToggleButtonFives;
    private javax.swing.JToggleButton jToggleButtonFours;
    private javax.swing.JToggleButton jToggleButtonFullHouse;
    private javax.swing.JToggleButton jToggleButtonLgStraight;
    private javax.swing.JToggleButton jToggleButtonOnes;
    private javax.swing.JToggleButton jToggleButtonSixes;
    private javax.swing.JToggleButton jToggleButtonSmStraight;
    private javax.swing.JToggleButton jToggleButtonThrees;
    private javax.swing.JToggleButton jToggleButtonTwos;
    private javax.swing.JToggleButton jToggleButtonYahtzee;
    private javax.swing.JButton rollButton;
    private javax.swing.JTextField txt3ofaKind;
    private javax.swing.JTextField txt4ofaKind;
    private javax.swing.JTextField txtChance;
    private javax.swing.JTextField txtFives;
    private javax.swing.JTextField txtFours;
    private javax.swing.JTextField txtFullHouse;
    private javax.swing.JTextField txtGrandTotal;
    private javax.swing.JTextField txtLgStraight;
    private javax.swing.JTextField txtLowerScore;
    private javax.swing.JTextField txtOnes;
    private javax.swing.JTextField txtSixes;
    private javax.swing.JTextField txtSmStraight;
    private javax.swing.JTextField txtThrees;
    private javax.swing.JTextField txtTotalUpperScore;
    private javax.swing.JTextField txtTwos;
    private javax.swing.JTextField txtUpperScore;
    private javax.swing.JTextField txtYahtzee;
    // End of variables declaration//GEN-END:variables
/*
   	private DiceToggleButton jToggleButton1;
	private DiceToggleButton jToggleButton2;
	private DiceToggleButton jToggleButton3;
	private DiceToggleButton jToggleButton4;
	private DiceToggleButton jToggleButton5;     
*/      
    private final DiceToggleButton[] holdButtons;
    private final JToggleButton[] upperScoreButtonArray;
    private final JToggleButton[] lowerScoreButtonArray;
    private final JTextField[] upperScoreTextboxArray;
    private final JTextField[] lowerScoreTextboxArray;
    private final boolean holdArray[];   
    private final Dice myDice;
    private final GameModel game;
    private uiStates currentUIState;
}
