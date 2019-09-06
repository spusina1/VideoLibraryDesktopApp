package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import static sample.LoginController.appLanguage;

public class MenuController {

    public GridPane pane;


    VideoLibraryModel model = VideoLibraryModel.getInstance();


    /*
    Svaka od metoda se poziva prilikom klika na neki button
    prozpra menu. U skaldu s odabirom buttona otvara se odgovarajuci novi prozor.
     */

    public void filmChoice(javafx.event.ActionEvent actionEvent) throws IOException {

        Locale.setDefault(appLanguage);
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("films.fxml"), bundle);

        loader.setController(new FilmsController(model));
        Parent root =  loader.load();

        Stage primaryStage = new Stage();
        primaryStage.setTitle("Filmovi");
        primaryStage.setScene(new Scene(root, 300, 300));
        primaryStage.show();


    }


    public void serialChoice(javafx.event.ActionEvent actionEvent) throws IOException {

        Locale.setDefault(appLanguage);

        ResourceBundle bundle = ResourceBundle.getBundle("Translation");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("series.fxml"), bundle);

        loader.setController(new SeriesController(model));
        Parent root =  loader.load();

        Stage primaryStage = new Stage();
        primaryStage.setTitle("Serije");
        primaryStage.setScene(new Scene(root, 300, 350));
        primaryStage.show();


    }


    public void reviewData(javafx.event.ActionEvent actionEvent) throws IOException {


        Locale.setDefault(appLanguage);

        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("users.fxml"), bundle);

        loader.setController(new UsersController(model));
        Parent root =  loader.load();

        Stage primaryStage = new Stage();
        primaryStage.setTitle("Podaci o korisniku");
        primaryStage.setScene(new Scene(root, 300, 600));
        primaryStage.show();


    }


    public void signOutUser(javafx.event.ActionEvent actionEvent) {

      model.setCurrentUser(null);
       // Platform.exit();

        Stage primaryStage = (Stage)pane.getScene().getWindow();
        primaryStage.close();

    }

}
