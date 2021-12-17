package com.safjnest;

import javax.swing.*;

public class FrameClass extends JFrame {

    public FrameClass() {

        setSize(500, 500);
        setLocationRelativeTo(null);
        //pack();
        setVisible(true);
        setTitle("Tic tac toe");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
