package sample;

public class Bar implements Comparable<Bar>
{
    // properties
    private String caption;
    private String name;
    private String category;
    private int value;
    private double startX;
    private double endX;
    
    // Bar classes Constructor
    public Bar(String caption, String name, int value, String category)
    {
        this.caption = caption;
        this.category = category;
        this.name = name;
        this.value = value;
    }
    
    // getter methods
    public String getName()
    {
        return this.name;
    }
    
    public String getCategory()
    {
        return this.category;
    }
    
    public int getValue()
    {
        return this.value;
    }
    
    public String getCaption()
    {
        return this.caption;
    }
    
    public void setStartX(double startX)
    {
        this.startX = startX;
    }
    
    public void setEndX(double endX)
    {
        this.endX = endX;
    }
    
    public double getStartX()
    {
        return startX;
    }
    
    public double getEndX()
    {
        return endX;
    }
    
    // compareTo overriding
    @Override
    public int compareTo(Bar bar)
    {
        if (this.value < bar.value)
        {
            return 1;
        }
        else return 0;
    }
}
