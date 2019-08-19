package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;

public class GlavniIzbornikController {

    VideotekaModel model = VideotekaModel.dajInstancu();

    public void izborFilma(javafx.event.ActionEvent actionEvent) throws IOException {


        FXMLLoader loader = new FXMLLoader(getClass().getResource("filmovi.fxml"));

        loader.setController(new FimoviController(model));
        Parent root =  loader.load();

        Stage primaryStage = new Stage();
        primaryStage.setTitle("Filmovi");
        primaryStage.setScene(new Scene(root, 300, 300));
        primaryStage.show();


    }


    public void izborSerije(javafx.event.ActionEvent actionEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("serije.fxml"));

        loader.setController(new SerijeController(model));
        Parent root =  loader.load();

        Stage primaryStage = new Stage();
        primaryStage.setTitle("Serije");
        primaryStage.setScene(new Scene(root, 300, 350));
        primaryStage.show();


    }


    public void pregledajPodatke(javafx.event.ActionEvent actionEvent) throws IOException {


        FXMLLoader loader = new FXMLLoader(getClass().getResource("korisnici.fxml"));

        loader.setController(new KorisniciController(model));
        Parent root =  loader.load();

        Stage primaryStage = new Stage();
        primaryStage.setTitle("Podaci o korisniku");
        primaryStage.setScene(new Scene(root, 300, 600));
        primaryStage.show();


    }


    public void odjaviKorisnika(javafx.event.ActionEvent actionEvent) {



    }

}
