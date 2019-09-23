package com.company;

/***
 * Application processes images by given name
 */
public class Main {

    public static void main(String[] args) {
	// write your code here
     String[] names = {"thres", "greyscale", "negative", "printtoning", "brightness"};
     int[] filters = {100, 0, 0, 30, 100};

     for(int i = 0; i < names.length; i++){
         ImageProcess img = new ImageProcess("pikachu.jpg", names[i], filters[i], "out images/pikachu_" + names[i] + ".jpg");
         img = new ImageProcess("londyn.jpg", names[i], filters[i], "out images/london_" + names[i] + ".jpg");
     }
    }
}
