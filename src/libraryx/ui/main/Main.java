package libraryx.ui.main;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import libraryx.database.databasehandler;

public class Main extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/libraryx/ui/login/Login.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
        stage.setTitle("LibraryX login");
        
        new Thread(() -> {
            databasehandler.getInstance();
        }).start();
        
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
