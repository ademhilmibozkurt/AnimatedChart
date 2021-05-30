package sample;

import javafx.scene.layout.Pane;

public abstract class Chart
{
    // getter methods
    abstract String getTitle();
    abstract String getDataSource();
    abstract String getxAxisLabel();
    // units for xAxislabel
    abstract int getUnits(double xMaxValue);
    // maxValue for chart range
    abstract void setMaxValue(int maxValue);
    // for drawing charts
    abstract void draw() throws InterruptedException;
    // reset for new draw
    abstract void reset();
}
