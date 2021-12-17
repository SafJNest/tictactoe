package com.safjnest;

import javax.swing.*;

public class App {
    public static void main(String[] args) throws Exception {
    	SwingUtilities.invokeLater(() -> {
            createAndShowGui();
        });
	}
	
    private static void createAndShowGui() {
    	Panel mainPanel = new Panel();
    	FrameClass frameclass = new FrameClass();
		frameclass.add(mainPanel);
    }
}