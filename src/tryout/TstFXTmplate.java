package tryout;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.chart.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.Group;
 
public class TstFXTmplate extends Application {
 
    @Override public void start(Stage stage) {
        stage.setTitle("FX Template");
        stage.setWidth(700);
        stage.setHeight(500);
 
        //Group root = new Group();
        FlowPane root = new FlowPane();
        Scene scene = new Scene(root);
        //Scene scene = new Scene(root,700,700);
        scene.setFill(Color.PALEGOLDENROD);

        Text msg = new Text("Hello JavaFX");
        //msg.setFill(Color.BLUE);
        root.getChildren().add(msg);

        stage.setScene(scene);
        stage.show();
    }
 
    public static void main(String[] args) {
        launch(args);
    }
}

