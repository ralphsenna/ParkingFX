package appmyphotoshop;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class AppMyPhotoshop extends Application 
{
    @Override
    public void start(Stage stage) throws Exception 
    {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLMenu.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.getIcons().add(new Image("file:src/icone.jpg"));
        stage.setTitle("AppMyPhotoshop");
        stage.setMaximized(true);
        stage.show();
    }

    public static void main(String[] args) 
    {
        launch(args);
    }
}
