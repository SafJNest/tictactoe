package com.safjnest;

import java.awt.*;
import java.awt.event.*;
import java.util.Random;

import javax.swing.*;
import javax.swing.border.LineBorder;

public class Panel extends JPanel implements ActionListener {
    private static final int MATRIX = 9;
    private static final int GAP = 6;
    private static final Font LABEL_FONT = new Font(Font.DIALOG, Font.PLAIN, 50);

    JButton[] grid = new JButton[MATRIX];

    Random rnd = new Random();

    JButton x = new JButton("PLAYER");
    JButton o = new JButton("IA");

    public void matchCheck() {
        matchCheck("x");
        matchCheck("o");
    }

    
    public boolean areMovesLeft() {
        for (int i = 0; i < grid.length; i++) 
            if(grid[i].getText().equals(" ")) 
                return true;
        return false;
    }

    public boolean areMovesLeft(String[] board) {
        for (int i = 0; i < board.length; i++)
            if(board[i].equals(" "))
                return true;
        return false;
    }

    public boolean matchCheck(String player) {
        if ((grid[0].getText() == player) && (grid[1].getText() == player) && (grid[2].getText() == player)) {
            win(player, 0, 1, 2);
            return true;
        }
        else if ((grid[0].getText() == player) && (grid[4].getText() == player) && (grid[8].getText() == player)) {
            win(player, 0, 4, 8);
            return true;
        }
        else if ((grid[0].getText() == player) && (grid[3].getText() == player) && (grid[6].getText() == player)) {
            win(player, 0, 3, 6);
            return true;
        }
        else if ((grid[1].getText() == player) && (grid[4].getText() == player) && (grid[7].getText() == player)) {
            win(player, 1, 4, 7);
            return true;
        }
        else if ((grid[2].getText() == player) && (grid[4].getText() == player) && (grid[6].getText() == player)) {
            win(player, 2, 4, 6);
            return true;
        }
        else if ((grid[2].getText() == player) && (grid[5].getText() == player) && (grid[8].getText() == player)) {
            win(player, 2, 5, 8);
            return true;
        }
        else if ((grid[3].getText() == player) && (grid[4].getText() == player) && (grid[5].getText() == player)) {
            win(player, 3, 4, 5);
            return true;
        }
        else if ((grid[6].getText() == player) && (grid[7].getText() == player) && (grid[8].getText() == player)) {
            win(player, 6, 7, 8);
            return true;
        }
        return false;
    }

    public boolean matchCheckString(String[] board, String player) {
        if ((board[0] == player) && (board[1] == player) && (board[2] == player)) {
            return true;
        }
        else if ((board[0] == player) && (board[4] == player) && (board[8] == player)) {
            return true;
        }
        else if ((board[0] == player) && (board[3] == player) && (board[6] == player)) {
            return true;
        }
        else if ((board[1] == player) && (board[4] == player) && (board[7] == player)) {
            return true;
        }
        else if ((board[2] == player) && (board[4] == player) && (board[6] == player)) {
            return true;
        }
        else if ((board[2] == player) && (board[5] == player) && (board[8] == player)) {
            return true;
        }
        else if ((board[3] == player) && (board[4] == player) && (board[5] == player)) {
            return true;
        }
        else if ((board[6] == player) && (board[7] == player) && (board[8] == player)) {
            return true;
        }
        return false;
    }

    public void endGame() {
        for (int i = 0; i < grid.length; i++) {
            grid[i].setEnabled(false);
        }
        o.setEnabled(true);
        x.setEnabled(true);
    }

    public void startGame() {
        for (int i = 0; i < grid.length; i++) {
            grid[i].setText(" ");
            grid[i].setEnabled(true);
            grid[i].setBackground(new Color(220,220,220));
        }
    }

    public void win(String winner, int x1, int x2, int x3) {
        endGame();
        if(winner.equals("o")) {
            grid[x1].setBackground(new Color(255,0,0));
            grid[x2].setBackground(new Color(255,0,0));
            grid[x3].setBackground(new Color(255,0,0));
        } 
        else {
            grid[x1].setBackground(new Color(0,255,0));
            grid[x2].setBackground(new Color(0,255,0));
            grid[x3].setBackground(new Color(0,255,0));
        }
        System.out.println(winner + " is the winner");
    }

    public String[] getBoardArray() {
        String[] array = new String[grid.length]; 
        for(int i = 0; i < array.length; i++) {
            array[i] = grid[i].getText();
        }
        return array;
    }

    public int minimax(String[] board, int depth, boolean isMax) {
        if(matchCheckString(board, "o"))
            return 10 - depth;
        if(matchCheckString(board, "x"))
            return -10 + depth;
        if(areMovesLeft(board) == false)
            return 0;

        if(isMax) {
            int best = -1000;
            for (int i = 0; i < board.length; i++) {
                if(board[i].equals(" ")){
                    board[i] = "o";
                    best = Math.max(best, minimax(board, depth + 1, !isMax));
                    board[i] = " ";
                }
            }
            return best;
        }
        else {
            int best = 1000;
            for (int i = 0; i < board.length; i++) {
                if(board[i].equals(" ")){
                    board[i] = "x";
                    best = Math.min(best, minimax(board, depth + 1, !isMax));
                    board[i] = " ";
                }
            }
            return best;
        }
    }

    public boolean isBoardEmpty() {
        for (int i = 0; i < grid.length; i++)
            if(!(grid[i].getText().equals(" ")))
                return false;
        return true;
    }

    private int randomResponse() {
        int randomThing = rnd.nextInt(9);
        while (!grid[randomThing].getText().equals(" "))
            randomThing = rnd.nextInt(9);
        return randomThing;
    }

    private void iaResponse() {
        int bestMove;
        if(isBoardEmpty()) {
            bestMove = randomResponse();
        }
        else {
            String[] board = getBoardArray();
            int bestVal = -1000;
            bestMove = -1;

            for (int i = 0; i < grid.length; i++) {
                if(board[i].equals(" ")) {
                    board[i] = "o";
                    int moveVal = minimax(board, 0, false);
                    board[i] = " ";
                    if(moveVal > bestVal) {
                        bestMove = i;
                        bestVal = moveVal;
                    }
                }
            }
        }

        grid[bestMove].setText("o");
        grid[bestMove].setEnabled(false);
        grid[bestMove].setBackground(new Color(192,192,192));

    }

    public Panel() {
        UIManager.getDefaults().put("Button.disabledText",Color.BLACK);
        /**
         * SE NON SI SELEZIONA CHI STARTA NON PARTE IL GAME
         */
        JPanel topPanel = new JPanel();
        x.setBorder(new LineBorder(Color.BLACK));
        x.setFocusPainted(false);
        x.addActionListener(this);
        o.setBorder(new LineBorder(Color.BLACK));
        o.setFocusPainted(false);
        o.addActionListener(this);
        topPanel.setLayout(new GridLayout(1, 4, GAP, GAP));
        topPanel.add(new JLabel("  "));
        topPanel.add(x);
        topPanel.add(o);
        topPanel.add(new JLabel("  "));
        JPanel squarePanel = new JPanel(new GridLayout(3, 3, GAP, GAP));
        squarePanel.setBorder(BorderFactory.createEmptyBorder(GAP, GAP, GAP, GAP));
        squarePanel.setBackground(Color.BLACK);

        for (int i = 0; i < grid.length; i++) {
            grid[i] = new JButton(" ");
            grid[i].setFont(LABEL_FONT);
            grid[i].setOpaque(true);
            grid[i].setEnabled(false);
            grid[i].setBackground(new Color(220,220,220));
            grid[i].addActionListener(this);
            grid[i].setFocusPainted(false);

            grid[i].addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    if(((JButton) evt.getSource()).isEnabled())
                    ((JButton) evt.getSource()).setBackground(new Color(192,192,192));
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    if(((JButton) evt.getSource()).isEnabled())
                    ((JButton) evt.getSource()).setBackground(new Color(220,220,220));
                }
            });
            squarePanel.add(grid[i]);
        }

        setLayout(new BorderLayout(10,10));
        add(topPanel,BorderLayout.NORTH);
        add(squarePanel, BorderLayout.CENTER);
    }

    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JButton) {
            if (((JButton) e.getSource()).getText().equals(" ")) {
                ((JButton) e.getSource()).setText("x");
                ((JButton) e.getSource()).setEnabled(false);
                matchCheck("x");
                if (areMovesLeft() == false) {
                    endGame();
                } else {
                    iaResponse();
                    matchCheck("o");
                    if (areMovesLeft() == false)
                        endGame();
                }

            }else if (((JButton) e.getSource()).getText().equals("PLAYER")) {
                x.setBackground(new Color(220,220,220));
                o.setBackground(new Color(192,192,192));
                
                o.setEnabled(false);
                x.setEnabled(false);

                startGame();
            }else if(((JButton) e.getSource()).getText().equals("IA")){
                o.setBackground(new Color(220,220,220));
                x.setBackground(new Color(192,192,192));

                o.setEnabled(false);
                x.setEnabled(false);

                startGame();
                iaResponse();
            }
        }
    }
}
