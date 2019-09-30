package com.imageprocessing;

import javax.imageio.ImageIO;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Class, which creates an object modifying image
 */
public class ImageProcess {
    /**
     * object BufferedImage, which contains image read from file
     * it provides useful operations on pixels
     */
    private BufferedImage new_image;
    private BufferedImage current_image;

    /**
     * Basic class constructor it takes a path of image file,
     * copies contens of image to BufferedImage object
     * @param path_name - name of the file path
     */
    public ImageProcess(String path_name){
        File input = new File(path_name);
        try{
            /**
             * creating new BufferedImage variable
             * */
            current_image = ImageIO.read(input);
            new_image = new BufferedImage(current_image.getWidth(), current_image.getHeight(), BufferedImage.TYPE_INT_RGB);
        }
        catch (IOException e){
            System.err.println("Something went wrong with reading a file...\n");
            e.printStackTrace();
        }
    }

    /**
     * Some kind of "factory constructor", it creates an object of class, processes an image
     * and saves it to a file
     * @param path_name - path of image file
     * @param process - name of the image process
     * @param filter - value of filter in proper image processing
     * @param file_name - path of the new image file
     */
    public ImageProcess(String path_name, String process, int filter, String file_name){
        /**
        calling a basic constructor, which creates an instance of ImageProcess class
         */
        this(path_name);
        /**
         * checking an operation and processing an image
         */
        if(process.equals("thres")){
            thresholding(filter);
        }
        if(process.equals("greyscale")){
            greyScale();
        }
        if(process.equals("negative")){
            negative();
        }
        if(process.equals("printtoning")){
            printToning(filter);
        }
        if(process.equals("brightness")){
            brightnessChange(filter);
        }
        /**
         * saving new image to a file
         */
        saveImageToFile(file_name);
    }

    /**
     * Main method, which processes image by given lambda expression
     * It changes pixels of the image the way it's defined in functional interface
     * @param f- functional interface which takes Color value and returns color value (de facto lambda expression)
     */
    private void process(ColorInterface f){
        Color color;
        for(int i = 0; i < new_image.getWidth(); i++){
            for(int j = 0; j < new_image.getHeight(); j++){
                color = new Color(current_image.getRGB(i, j)); // creating color object, which gathers pixel information
                new_image.setRGB(i, j, f.colorProcess(color).getRGB());
            }
        }
    }

    /**
     * Threshold operation
     * It changes an image to black & white picture with only 0-1 values of pixels.
     * Pixels are changing based on pattern below:
     * value of pixel is
     * 1 if value > threshold_value
     * 0 if value < threshold_value
     * @param thr - value of threshold
     */
    public void thresholding(int thr){
        process(x -> (getAverageRGBValue(x) >= thr) ? Color.WHITE : Color.BLACK);
    }

    /**
     * Negative operation
     * It changes an image to negative picture. Every RGB value of pixel is changed by pattern below:
     * red = 255 - red
     * green = 255 - green
     * blue = 255 - blue
     */
    public void negative(){
        process(x -> new Color(255 - x.getRed(), 255 - x.getGreen(), 255 - x.getBlue()));
    }

    /**
     * Scaling to grey
     * It creates an grey scaled image. Every RGB value is given as average of all RGB colors in pixel.
     */
    public void greyScale(){
        process(x -> new Color(getAverageRGBValue(x), getAverageRGBValue(x), getAverageRGBValue(x)));
    }

    /**
     * Changing brightness of image
     * Method makes an image lighter by given level.
     * The brightness value is added to every RGB color in every pixel.
     * @param c - brightness level
     */
    public void brightnessChange(int c){
        process(x -> new Color(
                (x.getRed() + c < 0) ? 0 : Math.min(x.getRed() + c, 255) , // we need to check if color is out of 0-255 range
                (x.getGreen() + c < 0) ? 0 : Math.min(x.getGreen() + c, 255) ,
                (x.getBlue() + c < 0) ? 0 : Math.min(x.getBlue() + c, 255)
        ));
    }

    /**
     * Creating sepia-image
     * Method creats an sepia-image by given value of filter
     * @param c - value of toning
     */
    public void printToning(int c){
        if( c <= 40) {
            BufferedImage temp = current_image;
            greyScale();
            current_image = new_image;
            process(x -> new Color(
                    (x.getRed() + 2 * c < 0) ? 0 : Math.min(x.getRed() + 2 * c, 255), // checking if value is out of 0-255 range
                    (x.getGreen() + c < 0) ? 0 : Math.min(x.getGreen() + c, 255),
                    x.getBlue()
            ));
            current_image = temp;
        }
        else
            System.out.println("Value of filter needs to be from 20 to 40!");
    }

    /**
     * Method calculates an average RGB value of pixel
     * @param color- Color object, which has information of color
     * @return - average RGB value
     */
    private int getAverageRGBValue(Color color){
        return (color.getBlue() + color.getRed() + color.getGreen())/3;
    }

    /**
     * Method saves modified image to a new file
     * @param name - path of a new file
     */
    public void saveImageToFile(String name){
        try {
            File outFile = new File(name);
            ImageIO.write(new_image, "jpg", outFile);
        }
        catch (IOException e){
            System.err.println("Something went wrong with creating a new image file...");
        }
    }
    public BufferedImage getOriginalImage(){
        return current_image;
    }
    public BufferedImage getProcessedImage(){
        return new_image;
    }


}
