package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.*;

public class Main extends Application {

    public static void main(String[] args)
    {
        launch(args);
    }
    
    @Override
    public void start(Stage stage) throws Exception{
        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        //Group groupRoot = new Group();
        Pane pane = new Pane();
        Canvas canvas = new Canvas(1200, 900);
        pane.getChildren().add(canvas);
        pane.setMaxSize(1200, 900);
        pane.setMinSize(1200, 900);
        stage.setTitle("Animated Charts");
        
        String title = "The 10 most populous cities";
        String xAxis = "Population (thousands)";
        String source = "Source: United Nations";
        
        BarChart barChart = new BarChart(title, xAxis, source);
        ArrayList<ArrayList<Bar>> barList = new ArrayList<>();
        
        ArrayList<Bar> barList1 = new ArrayList<>();
        barList1.add(new Bar("2018", "Mumbai", 22120, "South Asia"));
        barList1.add(new Bar("2018","Delhi", 27890,  "South Asia"));
        barList1.add(new Bar("2018","Tokyo",  38194, "East Asia"));
        barList1.add(new Bar("2018","Shangai", 25779, "East Asia"));
        barList1.add(new Bar("2018","Beijing", 22674, "East Asia"));
        barList1.add(new Bar("2018","Sao Paolo", 21698, "Latin America"));
        barList1.add(new Bar("2018","Mexico City", 21520, "Latin America"));
        barList1.add(new Bar("2018","Osaka", 20409, "East Asia"));
        barList1.add(new Bar("2018","Cairo", 19850, "Middle East"));
        barList1.add(new Bar("2018" ,"Dhaka", 19603, "South Asia"));
        
        ArrayList<Bar> barList2 = new ArrayList<>();
        barList2.add(new Bar("2019", "Mumbai", 22120, "South Asia"));
        barList2.add(new Bar("2019","Delhi", 27890,  "South Asia"));
        barList2.add(new Bar("2019","Tokyo",  39194, "East Asia"));
        barList2.add(new Bar("2019","Shangai", 26779, "East Asia"));
        barList2.add(new Bar("2019","Beijing", 28674, "East Asia"));
        barList2.add(new Bar("2019","Sao Paolo", 29698, "Latin America"));
        barList2.add(new Bar("2019","Mexico City", 20520, "Latin America"));
        barList2.add(new Bar("2019","Osaka", 20134, "East Asia"));
        barList2.add(new Bar("2019","Cairo", 21850, "Middle East"));
        barList2.add(new Bar("2019" ,"Dhaka", 39603, "South Asia"));
        
        ArrayList<Bar> barList3 = new ArrayList<>();
        barList3.add(new Bar("2020", "Mumbai", 25120, "South Asia"));
        barList3.add(new Bar("2020","Delhi", 30890,  "South Asia"));
        barList3.add(new Bar("2020","Tokyo",  41194, "East Asia"));
        barList3.add(new Bar("2020","Shangai", 15779, "East Asia"));
        barList3.add(new Bar("2020","Beijing", 25674, "East Asia"));
        barList3.add(new Bar("2020","Sao Paolo", 21498, "Latin America"));
        barList3.add(new Bar("2020","Mexico City", 28520, "Latin America"));
        barList3.add(new Bar("2020","Osaka", 30409, "East Asia"));
        barList3.add(new Bar("2020","Cairo", 29850, "Middle East"));
        barList3.add(new Bar("2020" ,"Dhaka", 20603, "South Asia"));
        
        barList.add(barList1);
        barList.add(barList2);
        barList.add(barList3);
        
        /*barList.add(new Bar("Mumbai", 25230, "South Asia"));
        barList.add(new Bar("Delhi", 28890,  "South Asia"));
        barList.add(new Bar("Tokyo",  39194, "East Asia"));
        barList.add(new Bar("Shangai", 27779, "East Asia"));
        barList.add(new Bar("Beijing", 25674, "East Asia"));
        barList.add(new Bar("Sao Paolo", 22698, "Latin America"));
        barList.add(new Bar("Mexico City", 32520, "Latin America"));
        barList.add(new Bar("Osaka", 28409, "East Asia"));
        barList.add(new Bar("Cairo", 21850, "Middle East"));
        barList.add(new Bar("Dhaka", 16603, "South Asia"));*/
        
        for (int i=0; i< barList.size(); i++)
        {
            pane.getChildren().clear();
            barChart.addBar(barList.get(i));
            pane.getChildren().add(barChart.getPane());
        }
        
        stage.setScene(new Scene(pane));
        /*LineChart lineChart = new LineChart(title, xAxis, source);
        
        ArrayList<Line> lineList = new ArrayList<>();
        lineList.add(new Line("Mumbai", 22120, "South Asia"));
        lineList.add(new Line("Delhi", 27890,  "South Asia"));
        lineList.add(new Line("Tokyo",  38194, "East Asia"));
        lineList.add(new Line("Shangai", 25779, "East Asia"));
        lineList.add(new Line("Beijing", 22674, "East Asia"));
        lineList.add(new Line("Sao Paolo", 21698, "Latin America"));
        lineList.add(new Line("Mexico City", 21520, "Latin America"));
        lineList.add(new Line("Osaka", 20409, "East Asia"));
        lineList.add(new Line("Cairo", 19850, "Middle East"));
        lineList.add(new Line("Dhaka", 19603, "South Asia"));
        
        for (Line line: lineList)
        {
            lineChart.addLine(line);
        }
        pane.getChildren().add(lineChart.draw());*/
        
        /* add the bars to the bar chart
        lineChart.add("Mumbai",      22120, "South Asia");
        lineChart.add("Delhi",       27890, "South Asia");
        lineChart.add("Tokyo",       38194, "East Asia");
        lineChart.add("Shanghai",    25779, "East Asia");
        lineChart.add("Beijing",     22674, "East Asia");
        lineChart.add("São Paulo",   21698, "Latin America");
        lineChart.add("Mexico City", 21520, "Latin America");
        lineChart.add("Osaka",       20409, "East Asia");
        lineChart.add("Cairo",       19850, "Middle East");
        lineChart.add("Dhaka",       19603, "South Asia");
        
        pane.getChildren().add(lineChart.draw());*/
    
        
        stage.setResizable(false);
        stage.show(); // show the stage page
    }

    public static Scene WelcomeGridPane()
    {
        GridPane welcomeGrid = new GridPane();
        welcomeGrid.setAlignment(Pos.CENTER);
        Scene scene = new Scene(welcomeGrid, 1000,700);
    
        Text sceneTitle = new Text("Hoşgeldiniz.");
        sceneTitle.setFont(Font.font("Arial",30));
        welcomeGrid.add(sceneTitle, 0,0,2,1);
        
        Label path = new Label("Yolu Girin ");
        welcomeGrid.add(path, 0,2);
        
        TextField userInput = new TextField();
        welcomeGrid.add(userInput, 1,2);
        
        Button button = new Button("Onayla");
        button.setFont(Font.font("Tahoma"));
        welcomeGrid.add(button, 1,4);
        
        button.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent actionEvent)
            {
            
            }
        });
        
        return scene;
    }
}
