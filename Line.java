package sample;

public class Line implements Comparable<Line>
{
    // properties
    private String name;
    private String category;
    private int value;
    
    // contstructor
    public Line(String name, int value, String category)
    {
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
    
    // comparable override
    @Override
    public int compareTo(Line line)
    {
        if (line.value == this.value && line.name == this.name && line.category == this.category)
        {
            return 1;
        }
        return 0;
    }
}
