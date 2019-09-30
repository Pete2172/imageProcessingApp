package com.frames;

import com.imageprocessing.ImageProcess;
import javafx.scene.control.ComboBox;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame {

    private ImageProcess proc;
    private ImagePanel panel;
    JComboBox<String> combo;
    private JSlider slider;

    public MainWindow() {
        super("Image Processing Application");
        proc = new ImageProcess("londyn.jpg");
        panel = new ImagePanel(proc.getOriginalImage());
        panel.setVisible(true);

        slider = new JSlider();
        slider.setMaximum(40);
        slider.setValue(0);
        ChangeListener sliderAction = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider slid = (JSlider) e.getSource();
               // proc.brightnessChange(slid.getValue());
               // proc.negative();
               // proc.printToning(slid.getValue());
                        slid.setEnabled(false);
                        proc.printToning(slid.getValue());
                        changeImage();
                        slid.setEnabled(true);

            }
        };
        slider.addChangeListener(sliderAction);


        String[] options = {"threshold", "brightness", "negative", "grayscale", "toning"};
        combo = new JComboBox<>(options);
        createLayout();

        pack();
    }
    private void createLayout(){
        add(panel, BorderLayout.CENTER);
        add(new JButton("Save"), BorderLayout.EAST);
        add(combo, BorderLayout.NORTH);
        add(slider, BorderLayout.SOUTH);
    }

    private void changeImage(){
       // proc.greyScale();

        panel.image = proc.getProcessedImage();
        panel.repaint();
    }
}
