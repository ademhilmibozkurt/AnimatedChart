package sample;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;

import java.util.ArrayList;
import java.util.TreeMap;

public class LineChart extends Chart
{
    //properties
    private static final Color[] COLORS = Colorize();

    private String title;
    private String dataSource;
    private String xAxisTitle;
    private String caption;
    private TreeMap<String, Color> colorOf; // map category to color
    private ArrayList<String> names;       // line names
    private ArrayList<Integer> values;    // line values
    private ArrayList<Color> colors;     // line colors
    private ArrayList<String> categories;
    private int maximumValue;
    private boolean isSetMaxValue;
    private Pane pane = new Pane();
    
    // constructor
    public LineChart(String title, String xAxisLabel, String dataSource)
    {
        if (title == null) throw new IllegalArgumentException("NULL name");
        if (xAxisLabel == null) throw new IllegalArgumentException("NULL x-axis label");
        if (dataSource == null) throw new IllegalArgumentException("NULL data source");
    
        this.title = title;
        this.xAxisTitle = xAxisLabel;
        this.dataSource = dataSource;
        colorOf = new TreeMap<String, Color>();
        reset();
    }
    
    // chart head name
    @Override
    String getTitle()
    {
        return this.title;
    }
    
    // source that data come from
    @Override
    String getDataSource()
    {
        return this.dataSource;
    }
    
    // xAxisLabels name
    @Override
    String getxAxisLabel()
    {
        return this.xAxisTitle;
    }

    public void addLine(Line line)
    {
        if (line.getName() == null) throw new IllegalArgumentException("NULL name");
        if (line.getValue() <= 0) throw new IllegalArgumentException("Value must be positive!");
        if (line.getCategory() == null) throw new IllegalArgumentException("NULL category");
        
        // category colorize
        if (!colorOf.containsKey(line.getCategory()))
        {
            // set a color to category
            colorOf.put(line.getCategory(), COLORS[colorOf.size() % COLORS.length]);
        }
        
        // adding to arraylists
        Color color = colorOf.get(line.getCategory()); // now color is same as categories color
        names.add(line.getName());
        values.add(line.getValue());
        colors.add(color);
        categories.add(line.getCategory());
    }
    
    @Override
    void setMaxValue(int maxValue)
    {
        if (maximumValue <= 0)
        {
            throw new IllegalArgumentException("xAxis value must be positive!");
        }
        this.isSetMaxValue = true;
        this.maximumValue = maximumValue;
    }

    @Override
    int getUnits(double xMaxValue)
    {
        int units = 1;
        while (Math.floor(xMaxValue / units) >= 8)
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
    
    @Override
    void draw()
    {
        // same as BarChart
        if (names.isEmpty()) throw new IllegalArgumentException(); // if names is null jump over
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
        
        Scale scale = new Scale();
        scale.setPivotX(-0.1*xMax);scale.setPivotY(-0.1*numOfBars);
        scale.setX(1.2*xMax);scale.setY(1.25*numOfBars);
    
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
    
        // draw axes
        int units = getUnits(xMax);
        double holdY = 140;
        for (int unit = ((int)xMax-(int)xMax%1000); unit> 0; unit -= units)
        {
            Rectangle axisBar = new Rectangle();
            Text axisText = new Text();
            axisBar.setFill(Color.rgb(150,150,140));
            axisText.setFont(Font.font("SansSerif", FontPosture.ITALIC, 16));
            axisText.setFill(Color.rgb(150,150,140));
        
            axisBar.setX(70);
            axisBar.setY(holdY);
            axisBar.setWidth(numOfBars*60);
            axisBar.setHeight(0.4);
            pane.getChildren().add(axisBar);
        
            axisText.setX(10);
            axisText.setY(holdY +15);
            axisText.setText(String.format("%d", unit));
            holdY += Math.ceil(xMax/units)*10;
            pane.getChildren().add(axisText);
        }
    
        // draw caption
        Text captionText = new Text(950, 700, caption);
        captionText.setFill(Color.LIGHTGRAY);
        if (caption.length() <= 4) captionText.setFont(Font.font("SansSerif", FontWeight.BOLD, 90));
        else if (caption.length() <= 8) captionText.setFont(Font.font("SansSerif", FontWeight.BOLD, 50));
        else  captionText.setFont(Font.font("SansSerif", FontWeight.BOLD, 40));
        pane.getChildren().add(captionText);
    
        // draw dataSource
        Text sourceText = new Text(captionText.getX(), captionText.getY()+20, dataSource);
        sourceText.setFill(Color.DARKGRAY);
        sourceText.setFont(Font.font("SansSerif", FontWeight.BLACK, 14));
        pane.getChildren().add(sourceText);
    
        // draw Categories
        int holdC = 20;
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
        
        // draw Lines
        holdY = 140;
        double holdX = 10;
        for (int i=0; i< names.size(); i++)
        {
            String name = names.get(i);
            int value = values.get(i);
            Color color = colors.get(i);
    
            Rectangle line = new Rectangle();
            line.setFill(color);
            line.setX(50);
            line.setY(holdY);
            line.setWidth(value/50);
            line.setHeight(5);
            pane.getChildren().add(line);
        
            int fontSize = (int) Math.ceil(16 * 10.0/numOfBars);
            Text lineText = new Text(value/50+20, holdY+10, String.format("%d",value));
            lineText.setFill(Color.BLACK);
            lineText.setFont(Font.font("SansSerif", FontWeight.BOLD, fontSize));
            holdY += 60;
            pane.getChildren().add(lineText);
        }
    }
    
    @Override
    void reset()
    {
        names = new ArrayList<String>();
        values = new ArrayList<Integer>();
        colors = new ArrayList<Color>();
        categories = new ArrayList<String>();
        caption = "";
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
