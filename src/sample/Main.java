package sample;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        VideotekaModel model = VideotekaModel.dajInstancu();

        Locale.setDefault(new Locale("bs","BA"));
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("prijava.fxml"), bundle);
        loader.setController(new PrijavaController(model));
        Parent root = loader.load();
        primaryStage.setTitle("Videoteka");
        primaryStage.setScene(new Scene(root, 600, 350));
        //stage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
