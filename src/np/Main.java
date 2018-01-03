package np;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application
{
    
    @Override
    public void start(Stage stage) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getResource("N_puzzle.fxml"));
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.setMinHeight(500);
        stage.setMinWidth(700);
        stage.setMaxHeight(500);
        stage.setMaxWidth(700);
        stage.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
    
}
