package com.company;

import java.awt.*;

/**
 * Functional interface needed to pass proper lambda expressions,
 * modifying pixels of images
 */
@FunctionalInterface
public interface ColorInterface {
    Color colorProcess(Color x);
}
