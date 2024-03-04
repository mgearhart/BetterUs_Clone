package com.example.betterus_tutorial.ui;

public class GraphicsManager {
    private static GraphicsManager instance;

    private GraphicsManager(){} // GOOD

    // For singleton functionality
    public static GraphicsManager getInstance(){ // GOOD
        if(instance == null) instance = new GraphicsManager();
        return instance;
    }

    // Creates a confetti effect
    public void createConfetti(){} // WIP
}
