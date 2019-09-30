package com.company;

import com.frames.MainWindow;
import com.imageprocessing.ImageProcess;

import javax.swing.*;

/***
 * Application processes images by given name
 */
public class Main {

    public static void main(String[] args) {
	// write your code here
        MainWindow win = new MainWindow();
        win.setResizable(true);
        win.setVisible(true);
        win.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
