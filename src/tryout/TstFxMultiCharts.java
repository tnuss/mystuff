package tryout;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class TstFxMultiCharts extends Application {

  @Override
  public void start(Stage primaryStage) {

    Double[] data = {0.1, 0.4, 0.5, 0.7, 0.9, 1.0};

    LineChart<Number, Number> lc = createLineChart(data);
    LineChart<Number, Number> lc1 = createLineChart(data);
    LineChart<Number, Number> lc2 = createLineChart(data);

//	    lc1.setScaleX(.5);
//	    lc.setScaleX(.5);
//	    lc2.setScaleX(.5);
//	    lc1.setScaleY(.25);
//	    lc.setScaleY(.25);
//	    lc2.setScaleY(.25);
	    
    HBox root = new HBox();
    root.getChildren().addAll(lc, lc1, lc2);
    root.setScaleX(.4);
    root.setScaleY(.25);

    Scene scene = new Scene(root, 800, 600);
    //primaryStage.setMaximized(true);
    
    primaryStage.setTitle("Hello World!");
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    launch(args);
  }

  private LineChart<Number, Number> createLineChart(Double[] axisValues) {
    //defining the axes
    final NumberAxis xAxis = new NumberAxis();
    final NumberAxis yAxis = new NumberAxis();
    xAxis.setLabel("Time");
    //creating the chart
    final LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);

    lineChart.setTitle("Axis' values");
    //defining a series
    XYChart.Series<Number, Number> series = new LineChart.Series<>();
    series.setName("X Axis");
    //populating the series with data
    Number iN ;
    Number dN;
    for (Integer i = 0; i < axisValues.length; i++) {
    	iN = i;
    	dN = axisValues[i];
    	//XYChart.Data<Number, Number> data = new LineChart.Data<>(i, axisValues[i]);
    	XYChart.Data<Number, Number> data = new LineChart.Data<>(iN, dN);
        series.getData().add(data);
    }
    lineChart.getData().add(series);
    return lineChart;
  }
}