package sample;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.*;

public class BarChart extends Chart
{
    //properties
    private static final Color[] COLORS = Colorize();

    private String title; // barCharts title
    private String dataSource;
    private String xAxisTitle;
    private ArrayList<Bar> barList;
    private TreeMap<String,Color> colorOf; // map category to color. treemap is red-black tree that makes compare
    private ArrayList<String> names;       // bar names
    private ArrayList<Integer> values;    // bar values
    private ArrayList<String> categories;
    private ArrayList<Color> colors;     // bar colors
    private int maximumValue = 0;       // max value for block implement automatically
    private boolean isSetMaxValue;     // max value setted controller
    private Pane pane = new Pane();
    
    // constructor
    public BarChart(String title, String xAxisTitle, String dataSource)
    {
        if (title == null) throw new IllegalArgumentException("NULL name");
        if (xAxisTitle == null) throw new IllegalArgumentException("NULL x-axis label");
        if (dataSource == null) throw new IllegalArgumentException("NULL data source");
        
        this.title = title;
        this.dataSource = dataSource;
        this.xAxisTitle = xAxisTitle;
        colorOf = new TreeMap<String, Color>();
    }

    // max value
    @Override
    void setMaxValue(int maximumValue)
    {
        if (maximumValue <= 0)
        {
            throw new IllegalArgumentException("xAxis value must be positive!");
        }
        this.isSetMaxValue = true;
        this.maximumValue = maximumValue;
    }
    
    @Override
    String getTitle()
    {
        return this.title;
    }
    
    @Override
    String getDataSource()
    {
        return this.dataSource;
    }
    
    @Override
    String getxAxisLabel()
    {
        return this.xAxisTitle;
    }
    
    // for adding with Bar class
    public void addBar(ArrayList<Bar> barList)
    {
        reset();
        for (int i=0; i < barList.size(); i++)
        {
            if (barList.get(i).getName() == null) throw new IllegalArgumentException("NULL name");
            if (barList.get(i).getValue() <= 0) throw new IllegalArgumentException("Value must be positive!");
            if (barList.get(i).getCategory() == null) throw new IllegalArgumentException("NULL category");
    
            // category colorize
            if (!colorOf.containsKey(barList.get(i).getCategory()))
            {
                // set a color to category
                colorOf.put(barList.get(i).getCategory(), COLORS[colorOf.size() % COLORS.length]);
            }
    
            // adding to arraylists
            Color color = colorOf.get(barList.get(i).getCategory()); // now color is same as categories color
            names.add(barList.get(i).getName());
            values.add(barList.get(i).getValue());
            colors.add(color);
            categories.add(barList.get(i).getCategory());
        }
    
        this.barList = barList;
    }

    // units for xAxisLabel that about range
    @Override
    int getUnits(double xMaxValue) // xMaxValue is biggest value of xAxisLabel
    {
        int units = 1;
        while (Math.floor(xMaxValue / units) >= 9)
        {
            if (units % 9 == 2)
            {
                units = units * 5 / 2;
            }
            else
            {
                units = units * 2;
            }
        }
        return units;
    }
    
    Pane getPane()
    {
        EventHandler<ActionEvent> handler = e -> draw();
        Timeline animation = new Timeline(
                new KeyFrame(Duration.millis (1000) , handler ));
        animation.setCycleCount(Timeline.INDEFINITE );
        animation.play ();
        
        return pane;
    }

@Override
    void draw()
    {
        pane.getChildren().clear();
        if (names.isEmpty()) throw new IllegalArgumentException(); // if names is null jump over
        // at least 8 bars
        int numOfBars = Math.max(8,names.size());
    
        // set the scale of the coordinate axes
        // the minimum value of double (-1.0/0.0)
        double xMax = Double.NEGATIVE_INFINITY;
        for (int value : values)
        {
            if (value > xMax)
            {
                xMax = value;
            }
        }
        if (isSetMaxValue) // if isSetMaxValue exist then
        {
            xMax = maximumValue;
        }
        
        // draw title
        Text titleText = new Text(500, 30, title);
        titleText.setFont(Font.font("SansSerif", FontWeight.BLACK, 22));
        titleText.setFill(Color.BLACK);
        pane.getChildren().add(titleText);
        
        // draw xAxisLabel
        Text labelText = new Text(10,85, xAxisTitle);
        labelText.setFill(Color.GRAY);
        labelText.setFont(Font.font("SansSerif", FontWeight.LIGHT, 16));
        labelText.resize(0, numOfBars);
        pane.getChildren().add(labelText);
    
        // draw Categories
        int holdC = 550;
        for (int i=1; i< categories.size(); i++)
        {
            if (categories.get(i-1) == categories.get(i))
            {
                continue;
            }
            Text categoryText = new Text(holdC +20, 835, categories.get(i-1));
            categoryText.setFont(Font.font("SansSerif", FontWeight.BLACK, 14));
            categoryText.setFill(Color.BLACK);
            pane.getChildren().add(categoryText);
        
            holdC += categories.get(i-1).length()*11;
            Circle categoryCircle = new Circle(holdC,  830, 14, colors.get(i));
            pane.getChildren().add(categoryCircle);
        }
        
        // draw axes
        int units = getUnits(xMax);
        double holdX = 10;
        for (int unit = 0; unit <= xMax; unit += units)
        {
            Rectangle axisBar = new Rectangle();
            Text axisText = new Text();
            axisBar.setFill(Color.rgb(150, 150, 140));
            axisText.setFont(Font.font("SansSerif", FontPosture.ITALIC, 16));
            axisText.setFill(Color.rgb(150, 150, 140));
    
            axisBar.setX(holdX);
            axisBar.setY(160);
            axisBar.setWidth(0.4);
            axisBar.setHeight(numOfBars * 60);
            pane.getChildren().add(axisBar);
    
            axisText.setX(holdX);
            axisText.setY(140);
            axisText.setText(String.format("%d", unit));
            holdX += numOfBars*110/numOfBars;
            pane.getChildren().add(axisText);
        }
        
        // draw Bar and Caption
        double holdY = 163;
        for (int i=0; i< barList.size(); i++)
        {
            Color color = colors.get(i);
            barList.get(i).setStartX(10);
            barList.get(i).setEndX(numOfBars*110*((double)barList.get(i).getValue()/units)/numOfBars);
            //barList.get(i).setStartX(barList.get(i).getEndX());
            //barList.get(j).get(j).setStartX(barList.get(i).get(j).getEndX());
            //barList.get(j).get(j).setEndX(numOfBars*110*((double)barList.get(i).get(j).getValue()/units)/numOfBars);
            
            Rectangle bar = new Rectangle();
            bar.setX(barList.get(i).getStartX());
            bar.setY(holdY);
            bar.setFill(color);
            bar.setHeight(50*10.0/numOfBars);
            bar.setWidth(barList.get(i).getEndX());
            pane.getChildren().add(bar);
    
            int fontSize = (int) Math.ceil(16 * 10.0/numOfBars);
            Text barText = new Text(numOfBars*110*((double)barList.get(i).getValue()/units)/numOfBars + 15, holdY+30, String.format("%d",barList.get(i).getValue()));
            barText.setFill(Color.BLACK);
            barText.setFont(Font.font("SansSerif", FontWeight.BOLD, fontSize));
            pane.getChildren().add(barText);
            
            Text nameText = new Text(20, holdY+30, barList.get(i).getName());
            nameText.setFill(Color.BLACK);
            nameText.setFont(Font.font("SansSerif", FontWeight.BOLD, fontSize));
            pane.getChildren().add(nameText);
            holdY += 60;
    
            // draw caption
            Text captionText = new Text(950, 700, barList.get(i).getCaption());
            captionText.setFill(Color.LIGHTGRAY);
            if (barList.get(i).getCaption().length() <= 4) captionText.setFont(Font.font("SansSerif", FontWeight.BOLD, 90));
            else if (barList.get(i).getCaption().length() <= 8) captionText.setFont(Font.font("SansSerif", FontWeight.BOLD, 50));
            else  captionText.setFont(Font.font("SansSerif", FontWeight.BOLD, 40));
            pane.getChildren().add(captionText);
    
            // draw dataSource
            Text sourceText = new Text(captionText.getX(), captionText.getY()+20, dataSource);
            sourceText.setFill(Color.DARKGRAY);
            sourceText.setFont(Font.font("SansSerif", FontWeight.BLACK, 14));
            pane.getChildren().add(sourceText);
            
            
            
        }
        
        /*// draw Bar
        double holdY = 163;
        for (int i=0; i< names.size(); i++)
        {
            String name = names.get(i);
            int value = values.get(i);
            Color color = colors.get(i);
            
            Rectangle bar = new Rectangle();
            bar.setX(10);
            bar.setY(holdY);
            bar.setFill(color);
            bar.setHeight(50);
            bar.setWidth(numOfBars*110*((double)value/units)/numOfBars);
            pane.getChildren().add(bar);
            
            int fontSize = (int) Math.ceil(16 * 10.0/numOfBars);
            Text barText = new Text(numOfBars*110*((double)value/units)/numOfBars + 15, holdY+30, String.format("%d",value));
            barText.setFill(Color.BLACK);
            barText.setFont(Font.font("SansSerif", FontWeight.BOLD, fontSize));
            pane.getChildren().add(barText);
            
            Text nameText = new Text(20, holdY+30, name);
            nameText.setFill(Color.BLACK);
            nameText.setFont(Font.font("SansSerif", FontWeight.BOLD, fontSize));
            pane.getChildren().add(nameText);
            
            holdY += 60;
        }*/
    }
    
    
    // reset the bar values for redrawing
    @Override
    void reset()
    {
        names = new ArrayList<String>();
        values = new ArrayList<Integer>();
        colors = new ArrayList<Color>();
        categories = new ArrayList<String>();
        barList = new ArrayList<Bar>();
    }
    
    static Color[] Colorize()
    {
        // colors for colorize
        String[] hex20 = {
                "#40E0D0", "#708090", "#FA8072", "#4169E1",
                "#FF0000", "#800080", "#DB7093", "#98FB98",
                "#EEE8AA", "#FFA500", "#FDF5E6", "#000080",
                "#FFE4E1", "#F5FFFA", "#C71585", "#00FA9A",
                "#7B68EE", "#778899", "#FFA07A", "#F08080",
        };
        
        Color[] colors = new Color[hex20.length];
        for (int i=0; i< hex20.length; i++)
        {
            colors[i] = Color.web(hex20[i]);
        }  // colors created
        return colors;
    }
}




