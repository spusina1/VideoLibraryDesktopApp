package sample;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

//
//        Parent root = FXMLLoader.load(getClass().getResource("prijava.fxml"));
//        primaryStage.setTitle("Aplikacija za videoteku");
//        primaryStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
//        primaryStage.show();

        VideotekaModel model = VideotekaModel.dajInstancu();
        //model.napuni();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("prijava.fxml"));
        loader.setController(new PrijavaController(model));
        Parent root = loader.load();
        primaryStage.setTitle("Videoteka");
        primaryStage.setScene(new Scene(root, 600, 350));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
