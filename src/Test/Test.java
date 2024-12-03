/* package Test;

import java.sql.Connection;
import java.sql.DriverManager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Test extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("LoginText.fxml"));
        primaryStage.setTitle("JavaFX App with Scene Builder");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    

    public static void main(String[] args) {
        launch(args);

        
        final String DB_URL = "jdbc:sqlite:database.db";
        try(Connection conn = DriverManager.getConnection(DB_URL))
        {
            System.out.println("successful");
        }
        catch(Exception SQLException)
        {
            System.out.println("unsuccessful");
        }
    }
} */
